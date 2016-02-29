package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.IndicatorReportDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    query.append("it.name as 'indicator_type_name', ir.next_target ");

    query.append("FROM `crp_indicators` i ");
    query.append("LEFT JOIN `crp_indicator_reports` ir ON i.id = ir.indicator_id AND ir.year = ");
    query.append(year);
    query.append(" AND ir.liaison_institution_id = ");
    query.append(activityLeaderId);
    query.append(" LEFT JOIN `crp_indicator_types` it ON i.indicator_type_id = it.id  ");
    query.append(" where i.serial not in ('ind01','ind02','ind03','ind04','ind05','ind06')");
    query.append(" ORDER BY i.id  ");

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
      LOG.error(
        "-- getIndicatorReports() > There was an exception trying to get the indicator reports of the leader {} for year {}",
        vars);
    }

    LOG.debug("<< getIndicatorReports():indicatorReportDataList.size={}", indicatorReportDataList.size());
    return indicatorReportDataList;
  }

  @Override
  public List<Map<String, String>> getIndicatorTypes() {
    // TODO Auto-generated method stub
    List<Map<String, String>> indicatorTypeDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * from crp_indicator_types ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> indicatorReportData = new HashMap<String, String>();
        indicatorReportData.put("id", rs.getString("id"));
        indicatorReportData.put("name", rs.getString("name"));
        indicatorTypeDataList.add(indicatorReportData);
      }
      rs.close();
    } catch (SQLException e) {

      LOG.error("-- getIndicatorTypes");
    }

    return indicatorTypeDataList;
  }

  @Override
  public boolean saveIndicatorReport(Map<String, String> indicatorReportData, int activityLeaderId, int year) {
    Object[] params = new Object[] {indicatorReportData.size(), activityLeaderId, year};
    LOG.debug(">> saveIndicatorsReport(indicatorsReport.size={}, activityLeaderId={}, year={})", params);
    boolean saved = false;

    Object[] values = new Object[6];

    values[0] = indicatorReportData.get("target");
    values[1] = indicatorReportData.get("actual");
    values[2] = indicatorReportData.get("support_links");
    values[3] = indicatorReportData.get("deviation");
    values[4] = indicatorReportData.get("next_target");

    values[5] = indicatorReportData.get("id");
    // The current target should be defined the previous year, by this reason, it is not
    // included in the query.
    StringBuilder query = new StringBuilder();
    query.append("update   crp_indicator_reports set  target=?, actual=?, ");
    query.append(" support_links=?, deviation=?, next_target=?  ");
    query.append(" where `id`=? ");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.saveData(query.toString(), values);
      if (rows > 0) {
        saved = true;
      }


    } catch (SQLException e) {
      LOG.error("-- saveIndicatorReport() > There was an exception trying to save an indicator's report.", e);
    }

    return saved;
  }
}
