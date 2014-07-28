package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.ProjectPartnerDAO;

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

public class MySQLProjectPartnerDAO implements ProjectPartnerDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectPartnerDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteProjectPartner(int id) {
    LOG.debug(">> deleteProjectPartner(id={})", id);

    String query = "DELETE FROM project_partners WHERE id = ?";

    int rowsDeleted = databaseManager.delete(query, new Object[] {id});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectPartner():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectPartner:{}", false);
    return false;
  }

  @Override
  public boolean deleteProjectPartner(int projectId, int institutionId) {
    LOG.debug(">> deleteProjectPartner(projectId={}, institutionId={})", projectId, institutionId);

    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM project_partners pp ");
    query.append("WHERE pp.project_id = ? AND pp.partner_id = ?");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {projectId, institutionId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteProjectPartner():{}", true);
      return true;
    }
    LOG.debug("<< deleteProjectPartner():{}", false);
    return false;
  }


  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> projectPartnerList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> projectPartnerData = new HashMap<String, String>();
        projectPartnerData.put("id", rs.getString("id"));
        projectPartnerData.put("project_id", rs.getString("project_id"));
        projectPartnerData.put("partner_id", rs.getString("partner_id"));
        projectPartnerData.put("contact_name", rs.getString("contact_name"));
        projectPartnerData.put("contact_email", rs.getString("contact_email"));
        projectPartnerData.put("responsabilities", rs.getString("responsabilities"));

        projectPartnerList.add(projectPartnerData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():projectPartnerList.size={}", projectPartnerList.size());
    return projectPartnerList;
  }

  public List<Map<String, String>> getProjectPartners(int projectID) {
    LOG.debug(">> getProjectPartners projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT pp.*   ");
    query.append("FROM project_partners as pp ");
    query.append("WHERE pp.project_id= ");
    query.append(projectID);


    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }


  @Override
  public int saveProjectPartner(Map<String, Object> projectPartnerData) {
    LOG.debug(">> createProjectPartner(projectPartnerData)", projectPartnerData);
    StringBuilder query = new StringBuilder();
    Object[] values;
    if (projectPartnerData.get("id") == null) {
      // Insert new record
      query
        .append("INSERT INTO project_partners (id, project_id, partner_id, contact_name, contact_email, responsabilities) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?) ");
      values = new Object[6];
      values[0] = projectPartnerData.get("id");
      values[1] = projectPartnerData.get("project_id");
      values[2] = projectPartnerData.get("partner_id");
      values[3] = projectPartnerData.get("contact_name");
      values[4] = projectPartnerData.get("contact_email");
      values[5] = projectPartnerData.get("responsabilities");
    } else {
      // update record
      query
        .append("UPDATE project_partners SET project_id = ?, partner_id = ?, contact_name = ?, contact_email = ?, responsabilities = ? ");
      query.append("WHERE id = ? ");
      values = new Object[6];
      values[0] = projectPartnerData.get("project_id");
      values[1] = projectPartnerData.get("partner_id");
      values[2] = projectPartnerData.get("contact_name");
      values[3] = projectPartnerData.get("contact_email");
      values[4] = projectPartnerData.get("responsabilities");
      values[5] = projectPartnerData.get("id");
    }

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< createProjectPartner():{}", result);
    return result;
  }
}
