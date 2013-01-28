package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.MilestoneStatusDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLMilestoneStatusDAO implements MilestoneStatusDAO {

  DAOManager databaseManager;

  @Inject
  public MySQLMilestoneStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getMilestoneStatus() {
    List<Map<String, String>> milestoneStatusDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * FROM milestone_status";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> milestoneStatusData = new HashMap<>();
        milestoneStatusData.put("id", rs.getString("id"));
        milestoneStatusData.put("status", rs.getString("status"));
        milestoneStatusDataList.add(milestoneStatusData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return milestoneStatusDataList;
  }
}
