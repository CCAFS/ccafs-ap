/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.RPLSynthesisReportDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLRPLSynthesisReportDAO implements RPLSynthesisReportDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLRPLSynthesisReportDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLRPLSynthesisReportDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public Map<String, Object> getRPLSynthesisReport(int leaderId, int logframeId) {
    LOG.debug(">> getRPLSynthesisReport(leaderId={}, logframeId={})", leaderId, logframeId);
    Map<String, Object> synthesisReport = new HashMap<String, Object>();
    String query =
      "SELECT * FROM rpl_synthesis_reports WHERE activity_leader_id = " + leaderId + " AND logframe_id = " + logframeId;
    try (Connection connection = dbManager.getConnection()) {
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
      LOG.error("-- getRPLSynthesisReport() > There was an error getting the RPL synthesis report for leader {}.",
        leaderId, e);
    }
    if (synthesisReport.isEmpty()) {
      LOG.debug("<< getRPLSynthesisReport():null");
      return null;
    }

    LOG.debug("<< getRPLSynthesisReport():{}", synthesisReport);
    return synthesisReport;
  }

  @Override
  public boolean saveRPLSynthesisReport(Map<String, Object> synthesisReport) {
    LOG.debug(">> saveRPLSynthesisReport(synthesisReport={})", synthesisReport);

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
          LOG.warn("-- saveRPLSynthesisReport() > It wasn't possible save the RPL synthesis report data");
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
          LOG.warn("-- saveRPLSynthesisReport() > There was an error updating the 'rpl_synthesis_reports' table.");
          LOG.debug("<< saveRPLSynthesisReport():false");
          return false;
        }
      }

    } catch (SQLException e) {
      LOG.error("-- saveRPLSynthesisReport() > There was an error updating a record into the 'rpl_synthesis_reports'",
        e);
    }

    LOG.debug("<< saveRPLSynthesisReport():true");
    return true;
  }

}
