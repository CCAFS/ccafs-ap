package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.MilestoneReportDAO;

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


public class MySQLMilestoneReportDAO implements MilestoneReportDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLMilestoneReportDAO.class);
  DAOManager databaseManager;

  @Inject
  public MySQLMilestoneReportDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getMilestoneReportListForSummary(int logframeId, int themeId, int milestoneId) {
    Object[] debugParams = {logframeId, themeId, milestoneId};
    LOG.debug(">> getMilestoneReportListForSummary(logframeId={}, themeId={}, milestoneId={})", debugParams);

    List<Map<String, String>> milestoneReportDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT mr.id, mr.tl_description, mr.rpl_description, ");
    query.append("ms.id as 'milestone_status_id', ms.status as 'milestone_status_name', ");
    query.append("m.id as 'milestone_id', m.code as 'milestone_code', m.description as 'milestone_description', ");
    query.append("op.id as 'output_id', op.code as 'output_code', op.description as 'output_description', ");
    query.append("ob.id as 'objective_id', ob.code as 'objective_code', ob.description as 'objective_description', ");
    query.append("ob.outcome_description as 'outcome_description', ");
    query.append("th.id as 'theme_id', th.code as 'theme_code', th.description as 'theme_description', ");
    query.append("lf.id as 'logframe_id' ");
    query.append("FROM milestone_reports mr ");
    query.append("LEFT JOIN milestone_status ms ON mr.milestone_status_id = ms.id ");
    query.append("RIGHT JOIN milestones m ON mr.milestone_id = m.id ");
    query.append("INNER JOIN activities ac ON m.id = ac.milestone_id ");
    query.append("INNER JOIN outputs op ON m.output_id=op.id ");
    query.append("INNER JOIN objectives ob ON op.objective_id = ob.id ");
    query.append("INNER JOIN themes th ON ob.theme_id = th.id ");
    query.append("INNER JOIN logframes lf ON th.logframe_id = lf.id ");
    query.append("WHERE lf.id = " + logframeId + " AND m.year = lf.year ");

    if (themeId != -1) {
      query.append("AND th.id = " + themeId + " ");
    }

    if (milestoneId != -1) {
      query.append("AND m.id = " + milestoneId + " ");
    }

    query.append("GROUP BY m.id ORDER BY th.code, ob.code, op.code ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> milestoneReportData = new HashMap<>();
        milestoneReportData.put("id", rs.getString("id"));
        milestoneReportData.put("tl_description", rs.getString("tl_description"));
        milestoneReportData.put("rpl_description", rs.getString("rpl_description"));
        milestoneReportData.put("milestone_status_id", rs.getString("milestone_status_id"));
        milestoneReportData.put("milestone_status_name", rs.getString("milestone_status_name"));
        milestoneReportData.put("milestone_id", rs.getString("milestone_id"));
        milestoneReportData.put("milestone_code", rs.getString("milestone_code"));
        milestoneReportData.put("milestone_description", rs.getString("milestone_description"));
        milestoneReportData.put("output_id", rs.getString("output_id"));
        milestoneReportData.put("output_code", rs.getString("output_code"));
        milestoneReportData.put("output_description", rs.getString("output_description"));
        milestoneReportData.put("objective_id", rs.getString("objective_id"));
        milestoneReportData.put("objective_code", rs.getString("objective_code"));
        milestoneReportData.put("objective_description", rs.getString("objective_description"));
        milestoneReportData.put("outcome_description", rs.getString("outcome_description"));
        milestoneReportData.put("theme_id", rs.getString("theme_id"));
        milestoneReportData.put("theme_code", rs.getString("theme_code"));
        milestoneReportData.put("theme_description", rs.getString("theme_description"));
        milestoneReportData.put("logframe_id", rs.getString("logframe_id"));
        milestoneReportDataList.add(milestoneReportData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getMilestoneReportListForSummary() > There was an error getting the list of milestones related to the RPL {} and logframe {} for year {}.",
          debugParams, e);
    }

    LOG.debug("<< getMilestoneReportListForSummary():milestoneReportDataList.size={}", milestoneReportDataList.size());
    return milestoneReportDataList;
  }

  @Override
  public List<Map<String, String>> getRPLMilestoneReportList(int activityLeaderId, int logframeId, int currentYear) {
    Object[] debugParams = {activityLeaderId, logframeId, currentYear};
    LOG.debug(">> getRPLMilestoneReportList(activityLeaderId={}, logframeId={}, currentYear={})", debugParams);

    List<Map<String, String>> milestoneReportDataList = new ArrayList<>();
    String query =
      "SELECT mr.id, mr.tl_description, mr.rpl_description, ms.id as 'milestone_status_id', "
        + "ms.status as 'milestone_status_name', m.id as 'milestone_id', m.code as 'milestone_code', "
        + "m.description as 'milestone_description', op.id as 'output_id', op.code as 'ouput_code', "
        + "obj.id as 'objective_id', obj.code as 'objective_code', th.id as 'theme_id', th.code as 'theme_code'"
        + "FROM milestone_reports mr " + "LEFT JOIN milestone_status ms ON mr.milestone_status_id = ms.id "
        + "RIGHT JOIN milestones m ON mr.milestone_id = m.id " + "INNER JOIN activities ac ON m.id = ac.milestone_id "
        + "INNER JOIN activity_leaders al ON ac.activity_leader_id=al.id "
        + "INNER JOIN outputs op ON m.output_id=op.id " + "INNER JOIN objectives obj ON op.objective_id = obj.id "
        + "INNER JOIN themes th ON obj.theme_id = th.id " + "INNER JOIN logframes lf ON th.logframe_id = lf.id "
        + "WHERE al.id=" + activityLeaderId + " AND lf.id=" + logframeId + " AND m.year = " + currentYear
        + " GROUP BY m.id ORDER BY th.code, obj.code, op.code";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> milestoneReportData = new HashMap<>();
        milestoneReportData.put("id", rs.getString("id"));
        milestoneReportData.put("tl_description", rs.getString("tl_description"));
        milestoneReportData.put("rpl_description", rs.getString("rpl_description"));
        milestoneReportData.put("milestone_status_id", rs.getString("milestone_status_id"));
        milestoneReportData.put("milestone_status_name", rs.getString("milestone_status_name"));
        milestoneReportData.put("milestone_id", rs.getString("milestone_id"));
        milestoneReportData.put("milestone_code", rs.getString("milestone_code"));
        milestoneReportData.put("milestone_description", rs.getString("milestone_description"));
        milestoneReportData.put("output_id", rs.getString("output_id"));
        milestoneReportData.put("ouput_code", rs.getString("ouput_code"));
        milestoneReportData.put("objective_id", rs.getString("objective_id"));
        milestoneReportData.put("objective_code", rs.getString("objective_code"));
        milestoneReportData.put("theme_id", rs.getString("theme_id"));
        milestoneReportData.put("theme_code", rs.getString("theme_code"));
        milestoneReportDataList.add(milestoneReportData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getRPLMilestoneReportList() > There was an error getting the list of milestones related to the RPL {} and logframe {} for year {}.",
          debugParams, e);
    }

    LOG.debug("<< getRPLMilestoneReportList():milestoneReportDataList.size={}", milestoneReportDataList.size());
    return milestoneReportDataList;
  }

  @Override
  public List<Map<String, String>> getTLMilestoneReportList(int activityLeaderId, int logframeId, int year) {
    Object[] debugParams = {activityLeaderId, logframeId, year};
    LOG.debug(">> getTLMilestoneReportList(activityLeaderId={}, logframeId={}, year={})", debugParams);

    List<Map<String, String>> milestoneReportDataList = new ArrayList<>();
    String themeCode = getTLrelatedTheme(activityLeaderId, logframeId);
    String query =
      "SELECT mr.id, mr.tl_description, mr.rpl_description, ms.id as 'milestone_status_id', "
        + "ms.status as 'milestone_status_name', m.id as 'milestone_id', m.code as 'milestone_code', "
        + "m.description as 'milestone_description', op.id as 'output_id', op.code as 'ouput_code', "
        + "obj.id as 'objective_id', obj.code as 'objective_code', th.id as 'theme_id', th.code as 'theme_code' "
        + "FROM milestone_reports mr " + "LEFT JOIN milestone_status ms ON mr.milestone_status_id = ms.id "
        + "RIGHT JOIN milestones m ON mr.milestone_id = m.id " + "INNER JOIN outputs op ON m.output_id=op.id "
        + "INNER JOIN objectives obj ON op.objective_id = obj.id " + "INNER JOIN themes th ON obj.theme_id = th.id "
        + "INNER JOIN logframes lf ON th.logframe_id = lf.id " + "WHERE lf.id= " + logframeId + " AND m.year = " + year
        + " AND lf.year = " + year + " AND th.code = " + themeCode.substring(0, 1);

    // If the theme code is 4 add the objective code
    if (themeCode.substring(0, 1).equals("4")) {
      query += " AND obj.code = " + themeCode.substring(2);
    }
    query.concat(" GROUP BY m.id ORDER BY th.code, obj.code, op.code");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> milestoneReportData = new HashMap<>();
        milestoneReportData.put("id", rs.getString("id"));
        milestoneReportData.put("tl_description", rs.getString("tl_description"));
        milestoneReportData.put("rpl_description", rs.getString("rpl_description"));
        milestoneReportData.put("milestone_status_id", rs.getString("milestone_status_id"));
        milestoneReportData.put("milestone_status_name", rs.getString("milestone_status_name"));
        milestoneReportData.put("milestone_id", rs.getString("milestone_id"));
        milestoneReportData.put("milestone_code", rs.getString("milestone_code"));
        milestoneReportData.put("milestone_description", rs.getString("milestone_description"));
        milestoneReportData.put("output_id", rs.getString("output_id"));
        milestoneReportData.put("ouput_code", rs.getString("ouput_code"));
        milestoneReportData.put("objective_id", rs.getString("objective_id"));
        milestoneReportData.put("objective_code", rs.getString("objective_code"));
        milestoneReportData.put("theme_id", rs.getString("theme_id"));
        milestoneReportData.put("theme_code", rs.getString("theme_code"));
        milestoneReportDataList.add(milestoneReportData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG
        .error(
          "-- getTLMilestoneReportList() > There was an error getting the list of milestones related to the TL {} and logframe {} for year {}.",
          debugParams, e);
    }

    LOG.debug("<< getRPLMilestoneReportList():milestoneReportDataList.size={}", milestoneReportDataList.size());
    return milestoneReportDataList;
  }

  @Override
  public String getTLrelatedTheme(int themeLeaderID, int currentPlanningLogframeID) {
    LOG.debug(">> getTLrelatedTheme(themeLeaderID={}, currentPlanningLogframeID={})", themeLeaderID,
      currentPlanningLogframeID);
    String themeCode = null;
    String query =
      "SELECT m.code FROM activities a " + "INNER JOIN milestones m ON a.milestone_id = m.id "
        + "INNER JOIN outputs op ON m.output_id = op.id " + "INNER JOIN objectives ob ON op.objective_id = ob.id "
        + "INNER JOIN themes th ON ob.theme_id = th.id " + "INNER JOIN logframes lf ON th.logframe_id = lf.id "
        + "WHERE a.activity_leader_id = " + themeLeaderID + " AND lf.id = " + currentPlanningLogframeID;

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        themeCode = rs.getString("code");
      }
    } catch (SQLException e) {
      LOG.error("-- getTLrelatedTheme() > There was an error getting the theme related to the theme leader {}",
        themeLeaderID, e);
    }

    if (themeCode != null) {
      // If theme is 4, it needs specify the objective
      if (themeCode.charAt(0) == '4') {
        return themeCode.substring(0, 3);
      } else {
        // For the other themes return only the theme code
        return themeCode.substring(0, 1);
      }
    }

    LOG.debug("<< getTLrelatedTheme():'{}'", themeCode);
    return themeCode;
  }

  @Override
  public boolean saveMilestoneReportList(List<Map<String, Object>> milestoneReportDataList) {
    LOG.debug(">> saveMilestoneReportList(milestoneReportDataList={})", milestoneReportDataList);
    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {
      String query;
      Object[] values;
      int rows;

      for (Map<String, Object> milestoneReportData : milestoneReportDataList) {
        values = new Object[4];
        values[0] = milestoneReportData.get("milestone_id");
        values[1] = milestoneReportData.get("milestone_status_id");
        values[2] = milestoneReportData.get("tl_description");
        values[3] = milestoneReportData.get("rpl_description");

        // If there is not an id defined, just add as a new record. Otherwise, update it
        if (((int) milestoneReportData.get("id")) == -1) {
          query =
            "INSERT INTO milestone_reports (milestone_id, milestone_status_id, tl_description, rpl_description) "
              + "VALUES (?, ?, ?, ?)";
        } else {
          query =
            "UPDATE milestone_reports SET milestone_id = ?, milestone_status_id = ?, tl_description = ?, "
              + "rpl_description = ? WHERE id = " + milestoneReportData.get("id");
        }

        // Make the query into the database
        rows = databaseManager.makeChangeSecure(con, query, values);
        if (rows < 0) {
          problem = true;
          LOG.warn("There was an problem saving records into 'milestone_reports' table.");
        }
      }
    } catch (SQLException e) {
      LOG.error("-- saveMilestoneReportList() > There was an error saving records into 'milestone_reports' table.", e);
    }

    LOG.debug("<< saveMilestoneReportList():{}", !problem);
    return !problem;
  }
}
