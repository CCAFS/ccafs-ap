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
      .append("SELECT i.id, i.name, i.acronym, i.contact_person_name, i.contact_person_email, i.institution_type_id,i.program_id,  ");
    query.append("lc.id as loc_elements_id, lc.name as loc_elements_name,lc.code as loc_elements_code, ");
    query.append("it.name as institution_type_name, it.acronym as institution_type_acronym, ");
    query.append("ip.id as program_id, ip.name as program_name, ip.acronym as program_acronym ");
    query.append("FROM institutions i ");
    query.append("INNER JOIN institution_types it ON it.id=i.institution_type_id ");
    query.append("INNER JOIN loc_elements      lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs        ip ON ip.id=i.program_id ");

    LOG.debug("-- getAllInstitutions() > Calling method executeQuery to get the results");
    return getData(query.toString());
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
    query.append("INNER JOIN loc_elements      lc ON lc.id=i.country_id ");
    query.append("LEFT JOIN ip_programs        ip ON ip.id=i.program_id ");
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
  public List<Map<String, String>> getInstitutionsByUser(int userID) {
    List<Map<String, String>> institutionsDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT i.id, i.name, i.acronym, ");
    query.append("p.id as program_id, p.name as program_name, p.acronym as 'program_acronym', ");
    query.append("it.id as institution_type_id, it.acronym as 'institution_type_acronym' ");
    query.append("FROM institutions i ");
    query.append("LEFT JOIN ip_programs p ON i.program_id = p.id ");
    query.append("LEFT JOIN institution_types it ON i.institution_type_id = it.id ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> institutionData = new HashMap<String, String>();
        institutionData.put("id", rs.getString("id"));
        institutionData.put("name", rs.getString("name"));
        institutionData.put("acronym", rs.getString("acronym"));
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
