package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.RPLSynthesisReportDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLRPLSynthesisReportDAO implements RPLSynthesisReportDAO {

  private DAOManager dbManager;

  @Inject
  public MySQLRPLSynthesisReportDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public Map<String, Object> getRPLSynthesisReport(int leaderId, int logframeId) {
    Map<String, Object> synthesisReport = new HashMap<String, Object>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT * FROM rpl_synthesis_reports WHERE activity_leader_id = " + leaderId + " AND logframe_id = "
          + logframeId;
      ResultSet rs = dbManager.makeQuery(query, connection);
      if (rs.next()) {
        synthesisReport.put("id", rs.getInt("id"));
        synthesisReport.put("ccafs_sites", rs.getString("ccafs_sites"));
        synthesisReport.put("cross_center", rs.getString("cross_center"));
        synthesisReport.put("regional", rs.getString("regional"));
        synthesisReport.put("decision_support", rs.getString("decision_support"));
        synthesisReport.put("activity_leader_id", rs.getString("activity_leader_id"));
        synthesisReport.put("logframe_id", rs.getString("logframe_id"));
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (synthesisReport.size() > 0) {
      return synthesisReport;
    }
    return null;
  }

  @Override
  public boolean saveRPLSynthesisReport(Map<String, Object> synthesisReport) {
    try (Connection connection = dbManager.getConnection()) {
      // If synthesis report has a default id, the synthesis need to be added to the database.
      // Otherwise it needs to be updated.
      if (((int) synthesisReport.get("id")) == -1) {
        String preparedInsertQuery =
          "INSERT INTO rpl_synthesis_reports (ccafs_sites, cross_center, regional, decision_support, activity_leader_id, logframe_id) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
        Object[] values = new Object[6];
        values[0] = synthesisReport.get("ccafs_sites");
        values[1] = synthesisReport.get("cross_center");
        values[2] = synthesisReport.get("regional");
        values[3] = synthesisReport.get("decision_support");
        values[4] = synthesisReport.get("activity_leader_id");
        values[5] = synthesisReport.get("logframe_id");
        int rows = dbManager.makeChangeSecure(connection, preparedInsertQuery, values);
        if (rows <= 0) {
          // TODO - Add log error problem.
          return false;
        }
      } else {
        String preparedUpdateQuery =
          "UPDATE rpl_synthesis_reports SET ccafs_sites = ?, cross_center = ?, regional = ?, decision_support = ? "
            + "WHERE activity_leader_id = ? AND logframe_id = ?";
        Object[] values = new Object[6];
        values[0] = synthesisReport.get("ccafs_sites");
        values[1] = synthesisReport.get("cross_center");
        values[2] = synthesisReport.get("regional");
        values[3] = synthesisReport.get("decision_support");
        values[4] = synthesisReport.get("activity_leader_id");
        values[5] = synthesisReport.get("logframe_id");
        int rows = dbManager.makeChangeSecure(connection, preparedUpdateQuery, values);
        if (rows <= 0) {
          // TODO - Add log error problem.
          return false;
        }
      }

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return true;
  }

}
