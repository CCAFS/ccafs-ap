/*****************************************************************
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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ProjectPartnerDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

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

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
public class MySQLProjectPartnerDAO implements ProjectPartnerDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectPartnerDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
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

  @Override
  public boolean deleteProjectPartner(int id, int userID, String justification) {
    StringBuilder query = new StringBuilder();
    // updating record is_active to false.
    query.append("UPDATE project_partners SET is_active = 0, modified_by = ?, modification_justification = ? ");
    query.append("WHERE id = ? ");
    Object[] values = new Object[3];
    values[0] = userID;
    values[1] = justification;
    values[2] = id;

    int result = databaseManager.saveData(query.toString(), values);
    if (result >= 0) {
      LOG.debug("<< deleteProject():{}", true);

      // Then we need delete all the tables that are referencing the projects table.
      return databaseManager.deleteOnCascade("project_partners", "id", id, userID, justification);
    }

    LOG.debug("<< deleteProjectPartner:{}", false);
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
        projectPartnerData.put("institution_id", rs.getString("institution_id"));
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

  @Override
  public Map<String, String> getProjectPartner(int partnerID) {
    LOG.debug(">> getProjectPartner projectID = {} )", partnerID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT pp.*   ");
    query.append("FROM project_partners as pp ");
    query.append("WHERE pp.id = ");
    query.append(partnerID);
    query.append(" AND pp.is_active = 1 ");

    LOG.debug("-- getProjectPartner() > Calling method executeQuery to get the results");
    List<Map<String, String>> data = this.getData(query.toString());
    if (data.size() > 0) {
      return data.get(0);
    }
    return new HashMap<String, String>();
  }

  @Override
  public List<Map<String, String>> getProjectPartnerContributors(int projectPartnerID) {
    LOG.debug(">> getProjectPartnerContributors( )");

    StringBuilder query = new StringBuilder();

    query.append("SELECT ppc.* ");
    query.append("FROM project_partner_contributions ppc ");
    query.append("WHERE ppc.project_partner_id = ");
    query.append(projectPartnerID);
    query.append(" AND ppc.is_active = 1");


    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> partnerContributionsDataList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> partnerContributionsData = new HashMap<String, String>();
        partnerContributionsData.put("id", rs.getString("id"));
        partnerContributionsData.put("project_partner_id", rs.getString("project_partner_id"));
        partnerContributionsData.put("project_partner_contributor_id", rs.getString("project_partner_contributor_id"));
        partnerContributionsDataList.add(partnerContributionsData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query.toString();

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():projectPartnerList.size={}", partnerContributionsDataList.size());
    return partnerContributionsDataList;
  }


  @Override
  public List<Map<String, String>> getProjectPartners(int projectID) {
    LOG.debug(">> getProjectPartners projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT pp.*   ");
    query.append("FROM project_partners as pp ");
    query.append("WHERE pp.project_id = ");
    query.append(projectID);
    query.append(" AND pp.is_active = 1 ");

    LOG.debug("-- getProject() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectPartners(int projectID, String projectPartnerType) {
    LOG.debug(">> getProjectPartners projectID = {},  projectPartnerType = {})", new Object[] {projectID,
      projectPartnerType});

    StringBuilder query = new StringBuilder();
    query.append("SELECT *   ");
    query.append("FROM project_partners ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND partner_type = '");
    query.append(projectPartnerType);
    query.append("'");
    query.append(" AND is_active = 1 ");
    query.append("ORDER BY partner_id");

    LOG.debug("-- getProjectPartners() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public int saveProjectPartner(Map<String, Object> projectPartnerData) {
    LOG.debug(">> saveProjectPartner(projectPartnerData)", projectPartnerData);
    StringBuilder query = new StringBuilder();
    Object[] values;
    if (projectPartnerData.get("id") == null) {
      // Insert new record
      query
        .append("INSERT INTO project_partners (id, project_id, institution_id, created_by, modified_by, modification_justification) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?) ");
      values = new Object[6];
      values[0] = projectPartnerData.get("id");
      values[1] = projectPartnerData.get("project_id");
      values[2] = projectPartnerData.get("institution_id");
      values[3] = projectPartnerData.get("created_by");
      values[4] = projectPartnerData.get("modified_by");
      values[5] = projectPartnerData.get("modification_justification");
    } else {
      // update record
      query
        .append("UPDATE project_partners SET project_id = ?, institution_id = ?, modified_by = ?, modification_justification = ? ");
      query.append("WHERE id = ? ");
      values = new Object[5];
      values[0] = projectPartnerData.get("project_id");
      values[1] = projectPartnerData.get("institution_id");
      values[2] = projectPartnerData.get("modified_by");
      values[3] = projectPartnerData.get("modification_justification");
      values[4] = projectPartnerData.get("id");
    }

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveProjectPartner():{}", result);
    return result;
  }

  @Override
  public int saveProjectPartnerContribution(Map<String, Object> partnerContributionData) {
    LOG.debug(">> saveProjectPartnerContribution({})", partnerContributionData);
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_partner_contributions (project_partner_id, project_partner_contributor_id, ");
    query.append("created_by, modified_by, modification_justification) VALUES (?, ");
    query.append("( SELECT id FROM project_partners WHERE institution_id=? AND project_id=? ) ");
    query.append(",?,?,?) ON DUPLICATE KEY UPDATE is_active = TRUE, created_by=VALUES(created_by), ");
    query.append("modified_by = VALUES(modified_by), modification_justification=VALUES(modification_justification) ");

    Object[] values = new Object[6];
    values[0] = partnerContributionData.get("project_partner_id");
    values[1] = partnerContributionData.get("institution_id");
    values[2] = partnerContributionData.get("project_id");
    values[3] = partnerContributionData.get("user_id");
    values[4] = partnerContributionData.get("user_id");
    values[5] = partnerContributionData.get("justification");

    int result = databaseManager.saveData(query.toString(), values);

    LOG.debug("<< saveProjectPartnerContribution():{}", result);
    return result;
  }
}
