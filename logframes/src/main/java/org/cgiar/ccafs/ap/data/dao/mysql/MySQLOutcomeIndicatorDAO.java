package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.OutcomeIndicatorDAO;

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


public class MySQLOutcomeIndicatorDAO implements OutcomeIndicatorDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLOutcomeIndicatorDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLOutcomeIndicatorDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getOutcomeIndicators(int year) {
    LOG.debug(">> getOutcomeIndicators( year={} )", year);

    List<Map<String, String>> outcomeIndicatorsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT oi.`id`, oi.`code`, oi.`description`, t.`id` as 'theme_id', ");
    query.append("t.`code` as 'theme_code' ");
    query.append("FROM `outcome_indicators` oi ");
    query.append("INNER JOIN `themes` t ON  oi.theme_id = t.id ");
    query.append("INNER JOIN `logframes` l ON t.logframe_id = l.id ");
    query.append("WHERE l.year = ");
    query.append(year);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> outcomeIndicatorData = new HashMap<String, String>();
        outcomeIndicatorData.put("id", rs.getString("id"));
        outcomeIndicatorData.put("code", rs.getString("code"));
        outcomeIndicatorData.put("description", rs.getString("description"));
        outcomeIndicatorData.put("theme_id", rs.getString("theme_id"));
        outcomeIndicatorData.put("theme_code", rs.getString("theme_code"));

        outcomeIndicatorsDataList.add(outcomeIndicatorData);
      }
      rs.close();
    } catch (SQLException e) {
      String errorMsg =
        "-- getOutcomeIndicators() >> There was an exception getting the list of outcome indicators for year {}";
      LOG.error(errorMsg, year, e);
    }

    LOG.debug("<< getOutcomeIndicators( ) : outcomeIndicatorsDataList.size={} ", outcomeIndicatorsDataList.size());
    return outcomeIndicatorsDataList;
  }
}
