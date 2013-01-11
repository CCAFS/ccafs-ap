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


public class MySQLOutputSummaryDAO implements OutputSummaryDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLOutputSummaryDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getOutputSummariesList(int activityLeaderId) {
    List<Map<String, String>> outputSummariesDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT id, description from output_summaries";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> outputSummaryData = new HashMap<String, String>();
        outputSummaryData.put("id", rs.getString("id"));
        outputSummaryData.put("description", rs.getString("descritption"));
        outputSummariesDataList.add(outputSummaryData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (outputSummariesDataList.isEmpty()) {
      return null;
    }
    return outputSummariesDataList;
  }

}
