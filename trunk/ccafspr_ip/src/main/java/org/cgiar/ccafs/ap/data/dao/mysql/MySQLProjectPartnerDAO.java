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

    String query = "DELETE FROM project_partners pp WHERE id = ?";

    try (Connection connection = databaseManager.getConnection()) {
      int rowsDeleted = databaseManager.makeChangeSecure(connection, query, new Object[] {id});
      if (rowsDeleted >= 0) {
        LOG.debug("<< deleteProjectPartner():{}", true);
        return true;
      }
    } catch (SQLException e) {
      LOG.error("-- deleteProjectPartner() > There was a problem deleting the project partner with id {}.",
        new Object[] {id, e});
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

    try (Connection connection = databaseManager.getConnection()) {
      int rowsDeleted =
        databaseManager.makeChangeSecure(connection, query.toString(), new Object[] {projectId, institutionId});
      if (rowsDeleted >= 0) {
        LOG.debug("<< deleteProjectPartner():{}", true);
        return true;
      }
    } catch (SQLException e) {
      LOG
        .error(
          "-- deleteProjectPartner() > There was a problem deleting the Project Partner of projectId {} and InstitutionId {}.",
          new Object[] {projectId, institutionId, e});
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

  private int saveData(String query, Object[] data) {
    int generatedId = -1;

    try (Connection con = databaseManager.getConnection()) {
      int ipElementAdded = databaseManager.makeChangeSecure(con, query, data);
      if (ipElementAdded > 0) {
        // get the id assigned to this new record.
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", con);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();

      }
    } catch (SQLException e) {
      LOG.error("-- saveData() > There was a problem saving information into the database. \n{}", e);
    }
    return generatedId;
  }

  @Override
  public int saveProjectPartner(Map<String, Object> projectPartnerData) {
    LOG.debug(">> createProjectPartner(projectPartnerData)", projectPartnerData);

    StringBuilder query = new StringBuilder();
    query
      .append("INSERT INTO project_partners (id, project_id, partner_id, contact_name, contact_email, responsabilities, is_leader ) ");
    query.append("VALUES (?, ?, ?, ?, ?, ?, ?) ");

    Object[] values = new Object[7];
    values[0] = projectPartnerData.get("id");
    values[1] = projectPartnerData.get("project_id");
    values[2] = projectPartnerData.get("partner_id");
    values[3] = projectPartnerData.get("contact_name");
    values[4] = projectPartnerData.get("contact_email");
    values[5] = projectPartnerData.get("responsabilities");
    values[6] = projectPartnerData.get("is_leader");

    int result = saveData(query.toString(), values);
    LOG.debug("<< createProjectPartner():{}", result);
    return result;
  }
}
