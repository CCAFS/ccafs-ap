package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.MilestoneDAO;

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


public class MySQLMilestoneDAO implements MilestoneDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLMilestoneDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLMilestoneDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getMilestone(int milestoneID) {
    LOG.debug(">> getMilestone(milestoneID={})", milestoneID);
    Map<String, String> milestone = new HashMap<>();
    // Querying milestone record.
    String query =
      "SELECT m.id, m.code, m.year, m.description, op.code as 'output_code', "
        + "op.description as 'output_description', obj.code as 'objective_code', "
        + "obj.description as 'objective_description', obj.outcome_description as 'objective_outcome_description', "
        + "th.code as 'theme_code', th.description as 'theme_description', lf.name as 'logframe_name' "
        + "FROM milestones m, outputs op, objectives obj, themes th, logframes lf "
        + "WHERE m.output_id = op.id AND op.objective_id = obj.id AND obj.theme_id = th.id "
        + "AND th.logframe_id = lf.id " + "AND m.id=" + milestoneID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        milestone.put("id", rs.getString("id"));
        milestone.put("code", rs.getString("code"));
        milestone.put("description", rs.getString("description"));
        milestone.put("year", rs.getString("year"));
        milestone.put("output_code", rs.getString("output_code"));
        milestone.put("output_description", rs.getString("output_description"));
        milestone.put("objective_code", rs.getString("objective_code"));
        milestone.put("objective_description", rs.getString("objective_description"));
        milestone.put("outcome_description", rs.getString("objective_outcome_description"));
        milestone.put("theme_code", rs.getString("theme_code"));
        milestone.put("theme_description", rs.getString("theme_description"));
        milestone.put("logframe_name", rs.getString("logframe_name"));
      }
      rs.close();

    } catch (SQLException e) {
      LOG.error("-- getMilestone() > There was an error getting the data for 'milestones' {}.", milestoneID, e);
    }

    if (milestone.isEmpty()) {
      LOG.debug("<< getMilestone():null");
      return null;
    }

    LOG.debug("<< getMilestone():{}", milestone.toString());
    return milestone;
  }

  @Override
  public List<Map<String, String>> getMilestoneList(int logframeID) {
    LOG.debug(">> getMilestoneList(logframeID={})", logframeID);
    List<Map<String, String>> milestoneDataList = new ArrayList<>();
    String query =
      "SELECT m.id, m.code, m.year, m.description FROM milestones m " + "INNER JOIN outputs op ON m.output_id = op.id "
        + "INNER JOIN objectives ob ON op.objective_id = ob.id " + "INNER JOIN themes t ON ob.theme_id = t.id "
        + "INNER JOIN logframes lf ON t.logframe_id = lf.id " + "WHERE lf.id = " + logframeID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> milestoneData = new HashMap<>();
        milestoneData.put("id", rs.getString("id"));
        milestoneData.put("code", rs.getString("code"));
        milestoneData.put("description", rs.getString("description"));
        milestoneData.put("year", rs.getString("year"));
        milestoneDataList.add(milestoneData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getMilestoneList() > There was an error getting the milestone list for logframe {}", logframeID, e);
    }

    if (milestoneDataList.isEmpty()) {
      LOG.warn("-- getMilestoneList() > It was not found milestone list corresponding to the logframe {}", logframeID);
    }

    LOG.debug("<< getMilestoneList():milestoneDataList.size={}", milestoneDataList.size());
    return milestoneDataList;
  }
}
