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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.InstitutionDAO;

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
  public List<Map<String, String>> getAllInstitutions() {
    LOG.debug(">> getAllInstitutions( )");

    StringBuilder query = new StringBuilder();
    query
      .append("SELECT i.id, i.name, i.acronym, i.contact_person_name, i.contact_person_email, i.institution_type_id,i.program_id, ");
    query.append("lc.id as loc_elements_id, lc.name as loc_elements_name,lc.code as loc_elements_code, ");
    query.append("it.name as institution_type_name, it.acronym as institution_type_acronym, ");
    query.append("ip.id as program_id, ip.name as program_name, ip.acronym as program_acronym ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN institution_types it ON it.id=i.institution_type_id ");
    query.append("LEFT JOIN loc_elements lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs ip ON ip.id=i.program_id ");
    query.append("ORDER BY i.acronym, i.name, loc_elements_name ASC ");

    LOG.debug("-- getAllInstitutions() > Calling method executeQuery to get the results");
    return getData(query.toString());
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
        institutionData.put("contact_person_name", rs.getString("contact_person_name"));
        institutionData.put("contact_person_email", rs.getString("contact_person_email"));
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
    query.append("SELECT i.id, i.name, i.acronym, i.institution_type_id,i.program_id, ");
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
    query
      .append("SELECT i.id, i.name, i.acronym, i.contact_person_name, i.contact_person_email, i.institution_type_id,i.program_id, ");
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
    return getData(query.toString());
  }

  @Override
  public List<Map<String, String>> getInstitutionsByUser(int userID) {
    List<Map<String, String>> institutionsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();

    query.append("SELECT e.id as 'employee_id', ");
    query.append("i.id 'institution_id', ");
    query.append("i.name as 'institution_name', ");
    query.append("i.acronym as 'institution_acronym',");
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
  public Map<String, String> getUserMainInstitution(int userID) {
    Map<String, String> institutionData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, ");
    query.append("p.id as program_id, p.name as program_name, p.acronym as 'program_acronym', ");
    query.append("it.id as institution_type_id, it.acronym as 'institution_type_acronym' ");
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


}
