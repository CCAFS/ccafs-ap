package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IndicatorReportDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLIndicatorReportDAO implements IndicatorReportDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(IndicatorReportDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIndicatorReportDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getIndicatorReports(int activityLeaderId, int year) {
    LOG.debug(">> getIndicatorReports(activityLeaderId={}, year={})", activityLeaderId, year);
    List<Map<String, String>> indicatorReportDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ir.id, ir.target, ir.actual, ir.support_links, ");
    query.append("ir.deviation, i.id as 'indicator_id', i.serial as 'indicator_serial', ");
    query.append("i.name as 'indicator_name', i.description as 'indicator_description', ");
    query.append("i.is_active as 'indicator_active', it.id as 'indicator_type_id', ");
    query.append("it.name as 'indicator_type_name', ");
    query.append("(SELECT `target` FROM `indicator_reports` ir2 ");
    query.append("WHERE ir2.indicator_id = ir.indicator_id AND ir2.year = (ir.year + 1)  ");
    query.append("AND ir2.activity_leader_id = ir.activity_leader_id) as 'next_target' ");
    query.append("FROM `indicators` i ");
    query.append("LEFT JOIN `indicator_reports` ir ON i.id = ir.indicator_id AND ir.year = ");
    query.append(year);
    query.append(" AND ir.activity_leader_id = ");
    query.append(activityLeaderId);
    query.append(" LEFT JOIN `indicator_types` it ON i.indicator_type_id = it.id ");
    query.append("ORDER BY i.id");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorReportData = new HashMap<String, String>();
        indicatorReportData.put("id", rs.getString("id"));
        indicatorReportData.put("target", rs.getString("target"));
        indicatorReportData.put("next_target", rs.getString("next_target"));
        indicatorReportData.put("actual", rs.getString("actual"));
        indicatorReportData.put("support_links", rs.getString("support_links"));
        indicatorReportData.put("deviation", rs.getString("deviation"));
        indicatorReportData.put("indicator_id", rs.getString("indicator_id"));
        indicatorReportData.put("indicator_serial", rs.getString("indicator_serial"));
        indicatorReportData.put("indicator_name", rs.getString("indicator_name"));
        indicatorReportData.put("indicator_description", rs.getString("indicator_description"));
        indicatorReportData.put("indicator_active", rs.getString("indicator_active"));
        indicatorReportData.put("indicator_type_id", rs.getString("indicator_type_id"));
        indicatorReportData.put("indicator_type_name", rs.getString("indicator_type_name"));
        indicatorReportDataList.add(indicatorReportData);
      }
      rs.close();
    } catch (SQLException e) {
      Object[] vars = new Object[] {activityLeaderId, year, e};
      LOG
        .error(
          "-- getIndicatorReports() > There was an exception trying to get the indicator reports of the leader {} for year {}",
          vars);
    }

    LOG.debug("<< getIndicatorReports():indicatorReportDataList.size={}", indicatorReportDataList.size());
    return indicatorReportDataList;
  }

  @Override
  public boolean saveIndicatorReport(Map<String, String> indicatorReportData, int activityLeaderId, int year) {
    Object[] params = new Object[] {indicatorReportData.size(), activityLeaderId, year};
    LOG.debug(">> saveIndicatorsReport(indicatorsReport.size={}, activityLeaderId={}, year={})", params);
    boolean saved = false;

    Object[] values = new Object[8];
    values[0] = indicatorReportData.get("id");
    values[1] = indicatorReportData.get("target");
    values[2] = indicatorReportData.get("actual");
    values[3] = indicatorReportData.get("support_links");
    values[4] = indicatorReportData.get("deviation");
    values[5] = activityLeaderId;
    values[6] = indicatorReportData.get("indicator_id");
    values[7] = year;

    // The current target should be defined the previous year, by this reason, it is not
    // included in the query.
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO `indicator_reports` (`id`, `target`, `actual`, ");
    query.append("`support_links`, `deviation`, `activity_leader_id`, `indicator_id`, ");
    query.append("`year`) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
    query.append(" ON DUPLICATE KEY UPDATE target = VALUES(target), actual = VALUES(actual), ");
    query.append(" support_links = VALUES(support_links), deviation = VALUES(deviation), ");
    query.append(" activity_leader_id = VALUES(activity_leader_id), ");
    query.append(" indicator_id = VALUES(indicator_id), year = VALUES(year) ");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query.toString(), values);

      if (rows < 0) {
        LOG.error("-- saveIndicatorReport() > There was an error trying to save an indicator's report.");
        LOG.error("Values: {}", Arrays.toString(values));
      } else {
        // If the indicator for the current year was inserted successfully, update the target for the next year
        query = new StringBuilder();
        query.append("UPDATE `indicator_reports` SET `target` = ? WHERE `activity_leader_id` = ? ");
        query.append("AND indicator_id = ? AND `year` = ? ");

        values = new Object[4];
        values[0] = indicatorReportData.get("next_target");
        values[1] = activityLeaderId;
        values[2] = indicatorReportData.get("indicator_id");
        values[3] = year + 1;
        rows = databaseManager.makeChangeSecure(con, query.toString(), values);

        // If there was no record to update, then insert a new record
        if (rows <= 0) {
          query = new StringBuilder();
          query.append("INSERT INTO `indicator_reports` (`target`, `activity_leader_id`, `indicator_id`, `year`) ");
          query.append("VALUES (?, ?, ?, ?) ");
          query.toString();

          // We can use the same values used to the previous query
          rows = databaseManager.makeChangeSecure(con, query.toString(), values);
        }

        saved = true;
      }

    } catch (SQLException e) {
      LOG.error("-- saveIndicatorReport() > There was an exception trying to save an indicator's report.", e);
    }

    LOG.debug("<< saveIndicatorReport():{}", saved);
    return saved;
  }
}
