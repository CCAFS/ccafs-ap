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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLMilestoneStatusDAO implements MilestoneStatusDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLMilestoneStatusDAO.class);
  DAOManager databaseManager;

  @Inject
  public MySQLMilestoneStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getMilestoneStatus() {
    LOG.debug(">> getMilestoneStatus()");
    List<Map<String, String>> milestoneStatusDataList = new ArrayList<>();
    String query = "SELECT * FROM milestone_status";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> milestoneStatusData = new HashMap<>();
        milestoneStatusData.put("id", rs.getString("id"));
        milestoneStatusData.put("status", rs.getString("status"));
        milestoneStatusDataList.add(milestoneStatusData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getMilestoneStatus() > There was an error getting the list of milestone status.", e);
    }

    LOG.debug("<< getMilestoneStatus():milestoneStatusDataList.size={}", milestoneStatusDataList.size());
    return milestoneStatusDataList;
  }
}
