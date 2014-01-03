package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.OutcomeIndicatorReportDAO;

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


public class MySQLOutcomeIndicatorReportDAO implements OutcomeIndicatorReportDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLOutcomeIndicatorReportDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLOutcomeIndicatorReportDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getOutcomeIndicatorReports(int year) {
    LOG.debug(">> getOutcomeIndicatorReports(year={})", year);

    List<Map<String, String>> outcomeIndicatorReports = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT oir.id, oir.achievements, oir.evidence, ");
    query.append("oi.id as 'outcome_indicator_id', oi.code as 'outcome_indicator_code', ");
    query.append("oi.description as 'outcome_indicator_description', ");
    query.append("al.id as 'leader_id', al.acronym as 'leader_acronym', ");
    query.append("th.id as 'theme_id', th.code as 'theme_code' ");
    query.append("FROM outcome_indicators oi ");
    query.append("LEFT JOIN outcome_indicator_reports oir ON oi.id = oir.outcome_indicator_id ");
    query.append("LEFT JOIN activity_leaders al ON oir.activity_leader_id = al.id ");
    query.append("INNER JOIN themes th ON oi.theme_id = th.id ");
    query.append("INNER JOIN logframes l ON th.logframe_id = l.id ");
    query.append("WHERE l.year = ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> oiReportData = new HashMap<String, String>();
        oiReportData.put("id", rs.getString("id"));
        oiReportData.put("achievements", rs.getString("achievements"));
        oiReportData.put("evidence", rs.getString("evidence"));
        oiReportData.put("outcome_indicator_id", rs.getString("outcome_indicator_id"));
        oiReportData.put("outcome_indicator_code", rs.getString("outcome_indicator_code"));
        oiReportData.put("outcome_indicator_description", rs.getString("outcome_indicator_description"));
        oiReportData.put("leader_id", rs.getString("leader_id"));
        oiReportData.put("leader_acronym", rs.getString("leader_acronym"));
        oiReportData.put("theme_id", rs.getString("theme_id"));
        oiReportData.put("theme_code", rs.getString("theme_code"));

        outcomeIndicatorReports.add(oiReportData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getOutcomeIndicatorReports() >> There was an exception getting the outcome indicator reports.", e);
    }

    LOG.debug("<< getOutcomeIndicatorReports():outcomeIndicatorReports.size={}", outcomeIndicatorReports.size());
    return outcomeIndicatorReports;
  }

  @Override
  public boolean saveOutcomeIndicators(List<Map<String, String>> outcomeIndicatorsData) {
    LOG.debug(">> saveOutcomeIndicators(outcomeIndicatorsData={})", outcomeIndicatorsData);
    boolean saved = true;
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO `outcome_indicator_reports` (`id`, `outcome_indicator_id`, `achievements`, `evidence`, ");
    query.append("`activity_leader_id`) VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE ");
    query.append("outcome_indicator_id = VALUES(outcome_indicator_id), `achievements` = VALUES(`achievements`), ");
    query.append("evidence = VALUES(evidence), `activity_leader_id` = VALUES(`activity_leader_id`);");

    for (Map<String, String> oir : outcomeIndicatorsData) {
      Object[] values = new Object[5];
      values[0] = oir.get("id");
      values[1] = oir.get("outcome_indicator_id");
      values[2] = oir.get("achievements");
      values[3] = oir.get("evidence");
      values[4] = oir.get("leader_id");

      try (Connection con = databaseManager.getConnection()) {
        int affectedRows = databaseManager.makeChangeSecure(con, query.toString(), values);
        if (affectedRows < 0) {
          saved = false;
        }
      } catch (SQLException e) {
        LOG.error("-- saveOutcomeIndicators() >> There was an exception saving the outcome indicators report.", e);
      }
    }

    LOG.debug("<< saveOutcomeIndicators():{}", saved);
    return saved;
  }
}
