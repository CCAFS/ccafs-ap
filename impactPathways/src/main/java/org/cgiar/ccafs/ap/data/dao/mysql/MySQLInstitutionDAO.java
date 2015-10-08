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

import org.cgiar.ccafs.ap.data.dao.InstitutionDAO;
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
 * @author Hector Fabio Tobón R.
 * @author Javier Andrés Gallego.
 */
public class MySQLInstitutionDAO implements InstitutionDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLInstitutionDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLInstitutionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteProjectPartnerContributeInstitution(int projectPartnerID, int institutionID) {
    LOG.debug(">> deleteProjectPartnerContributeInstitution(projectPartnerID={}, institutionID={})", projectPartnerID,
      institutionID);

    String query =
      "DELETE FROM project_partner_contributions WHERE project_partner_id = ? AND contribution_institution_id = ?";

    int rowsUpdated = databaseManager.delete(query, new Object[] {projectPartnerID, institutionID});
    if (rowsUpdated >= 0) {
      LOG.debug("<< deleteProjectPartnerContributeInstitution():{}", true);
      return true;
    }

    LOG.debug("<< deleteProjectPartnerContributeInstitution:{}", false);
    return false;

  }

  @Override
  public List<Map<String, String>> getAllInstitutions() {
    LOG.debug(">> getAllInstitutions( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, i.is_ppa, i.website_link, i.institution_type_id,i.program_id, ");
    query.append("lc.id as loc_elements_id, lc.name as loc_elements_name,lc.code as loc_elements_code, ");
    query.append("it.name as institution_type_name, it.acronym as institution_type_acronym, ");
    query.append("ip.id as program_id, ip.name as program_name, ip.acronym as program_acronym ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN institution_types it ON it.id=i.institution_type_id ");
    query.append("LEFT JOIN loc_elements lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs ip ON ip.id=i.program_id ");
    query.append("ORDER BY i.acronym, i.name, loc_elements_name ASC ");

    LOG.debug("-- getAllInstitutions() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getAllInstitutionTypes() {
    List<Map<String, String>> institutionsTypeDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT it.* ");
    query.append("FROM institution_types it ");
    query.append("ORDER BY it.name ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> institutionTypeData = new HashMap<String, String>();
        institutionTypeData.put("id", rs.getString("id"));
        institutionTypeData.put("name", rs.getString("name"));
        institutionTypeData.put("acronym", rs.getString("acronym"));

        institutionsTypeDataList.add(institutionTypeData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions type for the user {}.", e);
    }

    return institutionsTypeDataList;
  }

  @Override
  public List<Map<String, String>> getAllPPAInstitutions() {
    LOG.debug(">> getAllPPAInstitutions( )");

    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, i.is_ppa, i.website_link, i.institution_type_id, i.program_id, ");
    query.append("lc.id as loc_elements_id, lc.name as loc_elements_name,lc.code as loc_elements_code, ");
    query.append("it.name as institution_type_name, it.acronym as institution_type_acronym, ");
    query.append("ip.id as program_id, ip.name as program_name, ip.acronym as program_acronym ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN institution_types it ON it.id=i.institution_type_id ");
    query.append("LEFT JOIN loc_elements lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs ip ON ip.id=i.program_id ");
    query.append("WHERE i.is_ppa = 1 ");
    query.append("ORDER BY i.acronym, i.name, loc_elements_name ASC ");

    LOG.debug("-- getAllPPAInstitutions() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> institutionsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> institutionData = new HashMap<String, String>();
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
        institutionData.put("is_ppa", Boolean.toString(rs.getBoolean("is_ppa")));
        institutionData.put("website_link", rs.getString("website_link"));
        institutionData.put("loc_elements_id", rs.getString("loc_elements_id"));
        institutionData.put("loc_elements_name", rs.getString("loc_elements_name"));
        institutionData.put("loc_elements_code", rs.getString("loc_elements_code"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_name", rs.getString("institution_type_name"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("program_name", rs.getString("program_name"));
        institutionData.put("program_acronym", rs.getString("program_acronym"));

        institutionsList.add(institutionData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
    }

    LOG.debug("<< executeQuery():getAllInstitutions.size={}", institutionsList.size());
    return institutionsList;
  }

  @Override
  public Map<String, String> getInstitution(int institutionID) {
    Map<String, String> institutionData = new HashMap<String, String>();
    LOG.debug(">> getInstitution( institutionID = {} )", institutionID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, i.is_ppa, i.website_link, i.institution_type_id,i.program_id, ");
    query.append("lc.id as loc_elements_id, lc.name as loc_elements_name,lc.code as loc_elements_code, ");
    query.append("it.name as institution_type_name, it.acronym as institution_type_acronym, ");
    query.append("ip.id as program_id, ip.name as program_name, ip.acronym as program_acronym ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN institution_types it ON it.id=i.institution_type_id ");
    query.append("LEFT JOIN loc_elements lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs ip ON ip.id=i.program_id ");
    query.append("WHERE i.id = ");
    query.append(institutionID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
        institutionData.put("is_ppa", Boolean.toString(rs.getBoolean("is_ppa")));
        institutionData.put("website_link", rs.getString("website_link"));
        institutionData.put("loc_elements_id", rs.getString("loc_elements_id"));
        institutionData.put("loc_elements_name", rs.getString("loc_elements_name"));
        institutionData.put("loc_elements_code", rs.getString("loc_elements_code"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_name", rs.getString("institution_type_name"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("program_name", rs.getString("program_name"));
        institutionData.put("program_acronym", rs.getString("program_acronym"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", institutionID, e);
    }

    LOG.debug("-- getInstitution() > Calling method executeQuery to get the results");
    return institutionData;
  }

  @Override
  public List<Map<String, String>> getInstitutionsByTypeAndCountry(int typeID, int countryID) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, i.is_ppa, i.website_link, i.institution_type_id,i.program_id, ");
    query.append("lc.id as loc_elements_id, lc.name as loc_elements_name,lc.code as loc_elements_code, ");
    query.append("it.name as institution_type_name, it.acronym as institution_type_acronym, ");
    query.append("ip.id as program_id, ip.name as program_name, ip.acronym as program_acronym ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN institution_types it ON it.id=i.institution_type_id ");
    query.append("LEFT JOIN loc_elements lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs ip ON ip.id=i.program_id ");

    if (countryID != -1 || typeID != -1) {
      query.append("WHERE ");

      // Add the conditions if exists
      if (countryID != -1) {
        query.append("lc.id = '" + countryID + "' ");
      } else {
        query.append(" 1 ");
      }
      if (typeID != -1) {
        query.append(" AND it.id = '" + typeID + "' ");
      }
    }

    query.append(" ORDER BY i.acronym, i.name, loc_elements_name ASC ");

    LOG.debug("-- getAllInstitutions() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getInstitutionsByUser(int userID) {
    List<Map<String, String>> institutionsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT e.id as 'employee_id', ");
    query.append("i.id as 'institution_id', ");
    query.append("i.name as 'institution_name', ");
    query.append("i.acronym as 'institution_acronym', ");
    query.append("i.is_ppa as 'institution_is_ppa', ");
    query.append("i.website_link as 'institution_website_link', ");
    query.append("p.id as 'program_id', ");
    query.append("p.name as 'program_name', ");
    query.append("p.acronym as 'program_acronym',");
    query.append("it.id as 'institution_type_id', ");
    query.append("it.acronym as 'institution_type_acronym' ");
    query.append("FROM employees e ");
    query.append("INNER JOIN institutions i ON i.id = e.institution_id ");
    query.append("LEFT JOIN ip_programs p ON p.id = i.program_id ");
    query.append("LEFT JOIN institution_types it ON it.id = i.institution_type_id ");
    query.append("WHERE e.user_id = ");
    query.append(userID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> institutionData = new HashMap<String, String>();
        institutionData.put("id", rs.getString("institution_id"));
        institutionData.put("name", rs.getString("institution_name"));
        institutionData.put("acronym", rs.getString("institution_acronym"));
        institutionData.put("is_ppa", Boolean.toString(rs.getBoolean("institution_is_ppa")));
        institutionData.put("website_link", rs.getString("institution_website_link"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("program_name", rs.getString("program_name"));
        institutionData.put("program_acronym", rs.getString("program_acronym"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));

        institutionsDataList.add(institutionData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", userID, e);
    }

    return institutionsDataList;
  }

  @Override
  public Map<String, String> getInstitutionType(int institutionTypeID) {
    Map<String, String> institutionData = new HashMap<String, String>();
    LOG.debug(">> getTypeInstitutionById( institutionID = {} )", institutionTypeID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT it.* ");
    query.append("FROM institution_types it ");
    query.append("WHERE it.id =  ");
    query.append(institutionTypeID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", institutionTypeID, e);
    }

    LOG.debug("-- getTypeInstitutionById() > Calling method executeQuery to get the results");
    return institutionData;
  }

  @Override
  public List<Map<String, Object>> getProjectLeadingInstitutions() {
    List<Map<String, Object>> institutionsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT i.id as 'institution_id', ");
    query.append("i.name as 'institution_name', ");
    query.append("i.acronym as 'institution_acronym', ");
    query.append("i.website_link as 'institution_website_link', ");
    query.append("pp.id as 'project_partner_id', ");
    query.append("ppp.contact_type as 'partner_person_type', ");
    query.append("pp.project_id as 'project_id', ");
    query.append("le.id as 'country_id', ");
    query.append("le.name as 'country_name', ");
    query.append("(SELECT ");
    query.append("(SELECT GROUP_CONCAT('P', p.id ORDER BY p.id asc SEPARATOR ', ') ");
    query.append("FROM project_partners pp ");
    query.append("INNER JOIN projects p ON p.id = pp.project_id ");
    query.append("WHERE pp.institution_id = ins.id ");
    query.append("GROUP BY pp.institution_id ");
    query.append(") ");
    query.append("FROM institutions ins ");
    query.append("WHERE ins.id = i.id ");
    query.append(") as 'projects' ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN project_partners pp ON pp.institution_id = i.id ");
    query.append("INNER JOIN project_partner_persons ppp ON ppp.project_partner_id = pp.id ");
    query.append("INNER JOIN users u ON u.id = ppp.user_id ");
    query.append("LEFT JOIN loc_elements le ON le.id = i.country_id ");
    query.append("WHERE ppp.contact_type = 'PL' ");
    query.append("ORDER BY i.id");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> institutionData = new HashMap<String, Object>();
        institutionData.put("id", rs.getString("institution_id"));
        institutionData.put("name", rs.getString("institution_name"));
        institutionData.put("acronym", rs.getString("institution_acronym"));
        institutionData.put("website_link", rs.getString("institution_website_link"));
        institutionData.put("project_partner_id", rs.getString("project_partner_id"));
        institutionData.put("partner_person_type", rs.getString("partner_person_type"));
        institutionData.put("project_id", rs.getString("project_id"));
        institutionData.put("country_id", rs.getString("country_id"));
        institutionData.put("country_name", rs.getString("country_name"));
        institutionData.put("projects", rs.getString("projects"));

        institutionsDataList.add(institutionData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the ProjectPartnersinstitutions.", e);
    }

    return institutionsDataList;
  }

  @Override
  public List<Map<String, Object>> getProjectPartnerInstitutions() {
    List<Map<String, Object>> institutionsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT i.id as 'institution_id', ");
    query.append("i.name as 'institution_name', ");
    query.append("i.acronym as 'institution_acronym', ");
    query.append("i.website_link as 'institution_website_link', ");
    query.append("pp.id as 'project_partner_id', ");
    query.append("pp.project_id as 'project_id', ");
    query.append("le.id as 'country_id', ");
    query.append("le.name as 'country_name', ");
    query.append("it.id as institution_type_id, it.acronym as 'institution_type_acronym', ");
    query.append("it.name as 'institution_type_name', ");
    query.append("(SELECT ");
    query.append("(SELECT GROUP_CONCAT('P', p.id ORDER BY p.id asc SEPARATOR ', ') ");
    query.append("FROM project_partners pp ");
    query.append("INNER JOIN projects p ON p.id = pp.project_id ");
    query.append("WHERE pp.institution_id = ins.id ");
    query.append("GROUP BY pp.institution_id ");
    query.append(") ");
    query.append("FROM institutions ins ");
    query.append("WHERE ins.id = i.id ");
    query.append(") as 'projects' ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN project_partners pp ON pp.institution_id = i.id ");
    query.append("LEFT JOIN loc_elements le ON le.id = i.country_id ");
    query.append("LEFT JOIN institution_types it ON i.institution_type_id = it.id ");
    query.append("ORDER BY i.id");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, Object> institutionData = new HashMap<String, Object>();
        institutionData.put("id", rs.getString("institution_id"));
        institutionData.put("name", rs.getString("institution_name"));
        institutionData.put("acronym", rs.getString("institution_acronym"));
        institutionData.put("website_link", rs.getString("institution_website_link"));
        institutionData.put("project_partner_id", rs.getString("project_partner_id"));
        institutionData.put("project_id", rs.getString("project_id"));
        institutionData.put("country_id", rs.getString("country_id"));
        institutionData.put("country_name", rs.getString("country_name"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));
        institutionData.put("institution_type_name", rs.getString("institution_type_name"));
        institutionData.put("projects", rs.getString("projects"));

        institutionsDataList.add(institutionData);
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the ProjectPartnersinstitutions.", e);
    }

    return institutionsDataList;
  }

  @Override
  public Map<String, String> getUserMainInstitution(int userID) {
    Map<String, String> institutionData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, i.is_ppa, i.website_link, ");
    query.append("p.id as program_id, p.name as program_name, p.acronym as 'program_acronym', ");
    query.append("it.id as institution_type_id, it.acronym as 'institution_type_acronym'");
    query.append("FROM institutions i ");
    query.append("LEFT JOIN ip_programs p ON i.program_id = p.id ");
    query.append("LEFT JOIN institution_types it ON i.institution_type_id = it.id ");
    query.append("INNER JOIN employees e ON i.id = e.institution_id ");
    query.append("INNER JOIN users u ON e.user_id = u.id ");
    query.append("WHERE u.id = ");
    query.append(userID);
    query.append(" AND e.is_main = TRUE");
    query.append(" GROUP BY i.id ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
        institutionData.put("is_ppa", Boolean.toString(rs.getBoolean("is_ppa")));
        institutionData.put("website_link", rs.getString("website_link"));
        institutionData.put("program_id", rs.getString("program_id"));
        institutionData.put("program_name", rs.getString("program_name"));
        institutionData.put("program_acronym", rs.getString("program_acronym"));
        institutionData.put("institution_type_id", rs.getString("institution_type_id"));
        institutionData.put("institution_type_acronym", rs.getString("institution_type_acronym"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the institutions for the user {}.", userID, e);
    }
    return institutionData;
  }

  @Override
  public int saveProjectPartnerContributeInstitution(Map<String, Object> contributionData) {
    LOG.debug(">> saveProjectPartnerContributeInstitution(contributionData)", contributionData);
    StringBuilder query = new StringBuilder();
    Object[] values;
    // Inserting new record
    query.append("INSERT IGNORE INTO project_partner_contributions (project_partner_id, contribution_institution_id) ");
    query.append("VALUES (?, ?) ");

    values = new Object[2];
    values[0] = contributionData.get("project_partner_id");
    values[1] = contributionData.get("contribution_institution_id");

    int result = databaseManager.saveData(query.toString(), values);
    LOG.debug("<< saveProjectPartnerContributeInstitution():{}", result);
    return result;
  }

  @Override
  public boolean validateLastOneInstitution(int projectPartnerID) {
    LOG.debug(">> validateLastOneInstitution( projectPartnerID = {} )", projectPartnerID);

    StringBuilder query = new StringBuilder();

    query
      .append("SELECT COUNT(*) FROM project_partners pp WHERE pp.project_id = (SELECT pp2.project_id FROM project_partners pp2 WHERE pp2.id = ");
    query.append(projectPartnerID);
    query.append(") AND pp.partner_id = (SELECT pp3.partner_id FROM project_partners pp3 WHERE pp3.id = ");
    query.append(projectPartnerID);
    query.append(") AND pp.is_active = 1");
    boolean isLastOne = false;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);

      if (rs.next()) {
        if (rs.getInt(1) <= 1) {
          isLastOne = true;
        }
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception validating last one institution for project partner id = {}.", projectPartnerID, e);
    }

    LOG.debug("-- validateLastOneInstitution() > Calling method executeQuery to get the results");
    return isLastOne;
  }


}
