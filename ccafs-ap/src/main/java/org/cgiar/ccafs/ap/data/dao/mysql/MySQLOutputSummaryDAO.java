package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.OutputSummaryDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLOutputSummaryDAO implements OutputSummaryDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLOutputSummaryDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getOutputSummary(int outputId, int activityLeaderId) {
    Map<String, String> outputSummaryData = new HashMap<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT id, description FROM output_summaries WHERE output_id=" + outputId + " AND activity_leader_id="
          + activityLeaderId;
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        outputSummaryData.put("id", rs.getString("id"));
        outputSummaryData.put("description", rs.getString("description"));
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
    }

    if (outputSummaryData.isEmpty()) {
      return null;
    } else {
      return outputSummaryData;
    }
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
          // TODO - Add log about the problem ?
        }
      }
    } catch (SQLException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
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
          // TODO - Add log about the problem ?
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return !problem;
  }
}
