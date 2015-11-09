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
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
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


  @Override
  public boolean deleteProjectPartnerContributions(int id) {
    String query = "UPDATE  project_partner_contributions SET is_active = FALSE WHERE id = ?";
    int result = databaseManager.saveData(query, new Object[] {id});

    return result != -1;
  }

  @Override
  public Map<String, String> getAllProjectPartnersPersonsWithTheirInstitution() {
    StringBuilder query = new StringBuilder();
    LOG.debug("getAllProjectPartnersPersonWithTheirPartners");
    query.append("SELECT ppp.id  as partner_person_id ,IFNULL(CONCAT(i.acronym, ' - ',  i.name), i.name) ");
    query.append("AS institution_name FROM  institutions i ");
    query.append("INNER JOIN  project_partners pp ON i.id = pp.institution_id  ");
    query.append("INNER JOIN project_partner_persons ppp ON pp.id = ppp.project_partner_id ");
    query.append("WHERE pp.is_active = 1 AND ppp.is_active = 1 ");
    query.append("ORDER BY ppp.id");

    Map<String, String> projectPartnerData = new HashMap<String, String>();
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);

      while (rs.next()) {
        projectPartnerData.put(rs.getString("partner_person_id"), rs.getString("institution_name"));
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    return projectPartnerData;
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
  public Map<String, String> getProjectPartnerByPersonID(int projectPartnerPersonID) {
    Map<String, String> projectPartnerData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT pp.id, i.id as 'institution_id', i.name as 'institution_name',  ");
    query.append("i.acronym as 'institution_acronym', ppp.responsibilities, ppp.contact_type, ");
    query.append("ppp.id as 'partner_person_id', u.id as 'user_id', u.first_name, u.last_name, u.email ");
    query.append("FROM project_partners pp ");
    query.append("INNER JOIN project_partner_persons ppp ON pp.id = ppp.project_partner_id ");
    query.append("INNER JOIN institutions i ON pp.institution_id = i.id ");
    query.append("INNER JOIN users u ON ppp.user_id = u.id ");
    query.append("WHERE ppp.id = ");
    query.append(projectPartnerPersonID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        projectPartnerData.put("id", rs.getString("id"));
        projectPartnerData.put("institution_id", rs.getString("institution_id"));
        projectPartnerData.put("institution_name", rs.getString("institution_name"));
        projectPartnerData.put("institution_acronym", rs.getString("institution_acronym"));
        projectPartnerData.put("partner_person_id", rs.getString("partner_person_id"));
        projectPartnerData.put("responsibilities", rs.getString("responsibilities"));
        projectPartnerData.put("contact_type", rs.getString("contact_type"));
        projectPartnerData.put("user_id", rs.getString("user_id"));
        projectPartnerData.put("first_name", rs.getString("first_name"));
        projectPartnerData.put("last_name", rs.getString("last_name"));
        projectPartnerData.put("email", rs.getString("email"));
      }
    } catch (SQLException e) {
      LOG.error("getProjectPartnerByPersonID > Exception raised trying to get the project partner linked to "
        + "the partner person {}.", projectPartnerPersonID, e);
    }

    return projectPartnerData;
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
  public int saveProjectPartner(Map<String, Object> projectPartnerData) {
    LOG.debug(">> saveProjectPartner(projectPartnerData)", projectPartnerData);
    StringBuilder query = new StringBuilder();
    Object[] values;
    if (projectPartnerData.get("id") == null) {
      // Insert new record
      query.append("INSERT INTO project_partners (id, project_id, institution_id, created_by, modified_by, ");
      query.append("modification_justification) VALUES (?, ?, ?, ?, ?, ?) ");
      query.append("ON DUPLICATE KEY UPDATE is_active=TRUE, modified_by=VALUES(modified_by),  ");
      query.append("modification_justification=VALUES(modification_justification) ");

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

    // If the record already exists but was inactive we need to get the id
    if (result == 0 && projectPartnerData.get("id") == null) {
      query.setLength(0);
      query.append("SELECT id FROM project_partners WHERE project_id = ");
      query.append(projectPartnerData.get("project_id"));
      query.append(" and institution_id = ");
      query.append(projectPartnerData.get("institution_id"));
      try (Connection con = databaseManager.getConnection()) {
        ResultSet rs = databaseManager.makeQuery(query.toString(), con);
        if (rs.next()) {
          result = rs.getInt("id");
        }
      } catch (SQLException e) {
        LOG.error("There was an error getting the partner id of project {} with the insitution {}.",
          projectPartnerData.get("project_id"), projectPartnerData.get("institution_id"));
      }
    }

    LOG.debug("<< saveProjectPartner():{}", result);
    return result;
  }

  @Override
  public int saveProjectPartnerContribution(Map<String, Object> partnerContributionData) {
    LOG.debug(">> saveProjectPartnerContribution({})", partnerContributionData);
    StringBuilder query = new StringBuilder();


    query.append("INSERT INTO project_partner_contributions (project_partner_id, project_partner_contributor_id, ");
    query.append("created_by, modified_by, modification_justification) VALUES (?, ");
    query.append("( SELECT id FROM project_partners WHERE institution_id=? AND project_id=? AND is_active = TRUE) ");
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

  @Override
  public List<Map<String, Object>> summaryGetActivePartners() {
    List<Map<String, Object>> projectPartnersData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id as 'institution_id', ");
    query.append("group_concat(DISTINCT pp.project_id SEPARATOR ',') as 'project_ids', ");
    query.append("group_concat(DISTINCT ip.id SEPARATOR ',') as 'ip_programs', ");
    query.append("i.name as 'institution_name', ");
    query.append("i.acronym as 'institution_acronym', ");
    query.append("i.website_link as 'institution_website', ");
    query.append("i.institution_type_id as 'institution_type_id', ");
    query.append("it.name as 'institution_type_name', ");
    query.append("it.acronym as 'institution_type_acronym' , ");
    query.append(" le.code   as 'country_code' ,  ");
    query.append(" le.name   as 'country_name' , ");
    query.append(" re.id   as 'region_id' ,  ");
    query.append(" re.name   as 'region_name', ");
    query.append(" i.city   as 'city' ");
    query.append("FROM project_partners pp ");
    query.append("INNER JOIN institutions i ON i.id = pp.institution_id ");
    query.append("INNER JOIN institution_types it ON it.id = i.institution_type_id ");
    query.append("INNER JOIN projects p ON p.id = pp.project_id  ");
    query.append("INNER JOIN project_focuses pf ON pf.project_id = p.id ");
    query.append("INNER JOIN ip_programs ip ON ip.id = pf.program_id ");
    query.append("INNER JOIN loc_elements le ON i.country_id = le.id ");
    query.append("INNER JOIN loc_elements re ON le.parent_id = re.id ");
    query.append("WHERE pp.is_active = 1 ");
    query.append("AND p.is_active = 1 ");
    query.append("AND pf.is_active = 1 ");
    query.append("GROUP BY i.id ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> projectPartnerData = new HashMap<>();
        projectPartnerData.put("id", rs.getInt("institution_id"));
        projectPartnerData.put("project_ids", rs.getString("project_ids"));
        projectPartnerData.put("ip_programs", rs.getString("ip_programs"));
        projectPartnerData.put("institution_name", rs.getString("institution_name"));
        projectPartnerData.put("institution_acronym", rs.getString("institution_acronym"));
        projectPartnerData.put("institution_website", rs.getString("institution_website"));
        projectPartnerData.put("institution_type_id", rs.getString("institution_type_id"));
        projectPartnerData.put("institution_type_name", rs.getString("institution_type_name"));
        projectPartnerData.put("institution_type_acronym", rs.getString("institution_type_acronym"));
        projectPartnerData.put("country_code", rs.getString("country_code"));
        projectPartnerData.put("country_name", rs.getString("country_name"));
        projectPartnerData.put("region_id", rs.getString("region_id"));
        projectPartnerData.put("region_name", rs.getString("region_name"));
        projectPartnerData.put("city", rs.getString("city"));
        projectPartnersData.add(projectPartnerData);
      }
    } catch (SQLException e) {
      LOG.error("summaryGetActivePartners > Exception raised trying to get the project partners for the XML", e);
    }

    return projectPartnersData;
  }

  @Override
  public List<Map<String, Object>> summaryGetNotLoggedInPartners() {
    List<Map<String, Object>> projectPartnersData = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT u.id as 'user_id' , CONCAT( u.last_name, ', ', u.first_name) as 'name', u.email as 'email', ");
    query.append("(SELECT ");
    query.append("(SELECT GROUP_CONCAT(ppp.contact_type SEPARATOR ', ') ");
    query.append("FROM project_partners pp ");
    query.append("INNER JOIN project_partner_persons ppp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE ppp.user_id = u.id AND ppp.is_active = 1 AND pp.is_active = 1 ");
    query.append(") ");
    query.append("FROM institutions ins ");
    query.append("WHERE ins.id = i.id ");
    query.append(") as 'contact_type', ");
    query.append("(SELECT ");
    query.append("(SELECT GROUP_CONCAT('P', p.id ORDER BY p.id asc SEPARATOR ', ') ");
    query.append("FROM projects p ");
    query.append("INNER JOIN project_partners pp ON p.id = pp.project_id ");
    query.append("INNER JOIN project_partner_persons ppp ON ppp.project_partner_id = pp.id ");
    query.append("WHERE ppp.user_id = u.id AND ppp.is_active = 1 AND pp.is_active = 1 AND p.is_active = 1 ");
    query.append(") ");
    query.append("FROM institutions ins ");
    query.append("WHERE ins.id = i.id ");
    query.append(") as 'project_id' ");
    query.append("FROM project_partners pp ");
    query.append("INNER JOIN institutions i ON pp.institution_id = i.id ");
    query.append("INNER JOIN project_partner_persons ppp ON ppp.project_partner_id = pp.id ");
    query.append("INNER JOIN users u ON u.id = ppp.user_id ");
    query.append("INNER JOIN projects p ON pp.project_id = p.id ");
    query.append("WHERE pp.is_active = 1 AND u.last_login IS NULL AND p.is_active = 1 AND ppp.is_active = 1 ");
    query.append("GROUP BY u.id ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> projectPartnerData = new HashMap<>();
        projectPartnerData.put("user_id", rs.getInt("user_id"));
        projectPartnerData.put("name", rs.getString("name"));
        projectPartnerData.put("email", rs.getString("email"));
        projectPartnerData.put("contact_type", rs.getString("contact_type"));
        projectPartnerData.put("project_id", rs.getString("project_id"));
        projectPartnersData.add(projectPartnerData);
      }
    } catch (SQLException e) {
      LOG.error("summaryGetNotLoggedInPartners > Exception raised trying to get the project partners for the XML", e);
    }

    return projectPartnersData;
  }
}
