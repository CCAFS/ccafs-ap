package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.OutputSummaryDAO;

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


public class MySQLOutputSummaryDAO implements OutputSummaryDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLOutputSummaryDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLOutputSummaryDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getOutputSummariesList(int activityLeaderId, int logframeId) {
    List<Map<String, String>> outputSummaryDataList = new ArrayList<>();
    String query =
      "SELECT os.id, os.description, m.code, o.id as 'output_id', o.code as 'output_code', "
        + "o.description as 'output_description', obj.id as 'objective_id', obj.code as 'objective_code',"
        + "th.id as 'theme_id', th.code as 'theme_code' FROM `output_summaries` os "
        + "RIGHT JOIN outputs o on (os.output_id = o.id AND  os.activity_leader_id = " + activityLeaderId + ") "
        + "INNER JOIN milestones m on o.id = m.output_id " + "INNER JOIN activities a on m.id = a.milestone_id "
        + "INNER JOIN activity_leaders al on a.activity_leader_id = al.id "
        + "INNER JOIN objectives obj on o.objective_id = obj.id " + "INNER JOIN themes th on obj.theme_id = th.id "
        + "INNER JOIN logframes l on th.logframe_id = l.id " + "WHERE al.id = " + activityLeaderId + " and l.id ="
        + logframeId + " GROUP BY o.id ORDER BY th.code, obj.code, o.code";

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> outputSummaryData = new HashMap<>();
        outputSummaryData.put("id", rs.getString("id"));
        outputSummaryData.put("description", rs.getString("description"));
        outputSummaryData.put("code", rs.getString("code"));
        outputSummaryData.put("output_id", rs.getString("output_id"));
        outputSummaryData.put("output_code", rs.getString("output_code"));
        outputSummaryData.put("output_description", rs.getString("output_description"));
        outputSummaryData.put("objective_id", rs.getString("objective_id"));
        outputSummaryData.put("objective_code", rs.getString("objective_code"));
        outputSummaryData.put("theme_id", rs.getString("theme_id"));
        outputSummaryData.put("theme_code", rs.getString("theme_code"));
        outputSummaryDataList.add(outputSummaryData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting information from 'output_summaries' table. \n{}", query, e);
    }

    return outputSummaryDataList;

  }

  @Override
  public boolean saveOutputsSummaryList(List<Map<String, Object>> outputsSummaryData) {
    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {
      for (Map<String, Object> osData : outputsSummaryData) {
        String preparedQuery =
          "INSERT INTO output_summaries (description, output_id, activity_leader_id) VALUES (?, ?, ?)";
        Object[] data = new Object[3];
        data[0] = osData.get("description");
        data[1] = osData.get("output_id");
        data[2] = osData.get("activity_leader_id");
        int rows = databaseManager.makeChangeSecure(con, preparedQuery, data);
        if (rows < 0) {
          problem = true;
          LOG.error("There was a problem inserting records into 'output_summaries' table.");
        }
      }
    } catch (SQLException e) {
      LOG.error("There was an error inserting records into 'output_summaries' table.", e);
    }
    return !problem;
  }

  @Override
  public boolean updateOutputsSummaryList(List<Map<String, Object>> outputsSummaryData) {
    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {
      for (Map<String, Object> osData : outputsSummaryData) {
        String preparedQuery =
          "UPDATE output_summaries SET description = ? WHERE output_id = ? AND activity_leader_id = ?";
        Object[] data = new Object[3];
        data[0] = osData.get("description");
        data[1] = osData.get("output_id");
        data[2] = osData.get("activity_leader_id");
        int rows = databaseManager.makeChangeSecure(con, preparedQuery, data);
        if (rows < 0) {
          problem = true;
          LOG.error("There was a problem updating records into 'output_summaries' table.");
        }
      }
    } catch (SQLException e) {
      LOG.error("There was an error updating records into 'output_summaries' table.", e);
    }
    return !problem;
  }
}
