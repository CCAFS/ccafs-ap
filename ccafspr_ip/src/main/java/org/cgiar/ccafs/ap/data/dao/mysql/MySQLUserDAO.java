package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.UserDAO;

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

public class MySQLUserDAO implements UserDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLUserDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLUserDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public List<Map<String, String>> getAllUsers() {
    LOG.debug(">> getProjectLeader()");
    List<Map<String, String>> projectLeadersList = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.username, pe.first_name, pe.last_name, pe.email ");
      query.append("FROM users u ");
      query.append("INNER JOIN persons pe  ON u.person_id=pe.id");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> projectLeaderData = new HashMap<>();
        projectLeaderData.put("id", rs.getString("id"));
        projectLeaderData.put("username", rs.getString("username"));
        projectLeaderData.put("first_name", rs.getString("first_name"));
        projectLeaderData.put("last_name", rs.getString("last_name"));
        projectLeaderData.put("email", rs.getString("email"));
        projectLeadersList.add(projectLeaderData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getProjectLeader() > There was an error getting the data for Project Leaders {}.", e);
      return null;
    }
    LOG.debug("<< getProjectLeader():{}", projectLeadersList);
    return projectLeadersList;
  }

  @Override
  public int getEmployeeID(int userId, int institutionId, int roleId) {
    LOG
    .debug(">> getEmployeeID (userId={}, institutionId={}, roleId={})", new Object[] {userId, institutionId, roleId});
    int result = -1;
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT id FROM employees WHERE user_id = ");
      query.append(userId);
      query.append(" AND institution_id = ");
      query.append(institutionId);
      query.append(" AND role_id = ");
      query.append(roleId);
      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        result = rs.getInt("id");
      } else {
        result = 0;
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getEmployeeID() > There was an error getting the data for the user with id {}.", userId, e);
    }
    LOG.debug("<< getEmployeeID():{}", result);
    return result;
  }

  @Override
  public Map<String, String> getImportantUserByProject(int projectID) {
    LOG.debug(">> getImportantUserByProject(projectID={})", projectID);
    Map<String, String> projectContactPersonData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT ins.id as institution_id,u.id, u.username,  ");
      query.append("pe.first_name, pe.last_name, pe.email ");
      query.append("FROM users u ");
      query.append("INNER JOIN persons pe  ON u.person_id=pe.id ");
      query.append("INNER JOIN employees emp ON u.id=emp.user_id ");
      query.append("INNER JOIN projects pro ON emp.id=pro.project_owner_id ");
      query.append("INNER JOIN institutions ins ON emp.institution_id = ins.id ");
      query.append("WHERE pro.id= ");
      query.append(projectID);

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {

        projectContactPersonData.put("institution_id", rs.getString("institution_id"));
        projectContactPersonData.put("id", rs.getString("id"));
        projectContactPersonData.put("username", rs.getString("username"));
        projectContactPersonData.put("first_name", rs.getString("first_name"));
        projectContactPersonData.put("last_name", rs.getString("last_name"));
        projectContactPersonData.put("email", rs.getString("email"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUser() > There was an error getting the data for user with id {}.", projectID, e);
    }
    LOG.debug("<< getImportantUserByProject():{}", projectContactPersonData);
    return projectContactPersonData;
  }


  @Override
  public List<Map<String, String>> getImportantUsers() {
    LOG.debug(">> getImportantUsers()");
    List<Map<String, String>> projectContactPersonList = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT ins.id as institution_id, ");
      query.append("u.id, u.username, pe.first_name, pe.last_name, pe.email ");
      query.append("FROM users u ");
      query.append("INNER JOIN persons pe  ON u.person_id=pe.id ");
      query.append("INNER JOIN employees emp ON u.id=emp.user_id ");
      query.append("INNER JOIN roles ro ON emp.role_id=ro.id ");
      query.append("INNER JOIN institutions ins ON emp.institution_id = ins.id ");
      query.append("WHERE ro.id= ");
      query.append(APConstants.ROLE_FLAGSHIP_PROGRAM_LEADER);
      query.append(" OR ro.id= ");
      query.append(APConstants.ROLE_REGIONAL_PROGRAM_LEADER);
      query.append(" OR ro.id= ");
      query.append(APConstants.ROLE_COORDINATING_UNIT);
      query.append(" ORDER BY pe.last_name, ins.name ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> projectContactPersonData = new HashMap<>();
        projectContactPersonData.put("institution_id", rs.getString("institution_id"));
        projectContactPersonData.put("id", rs.getString("id"));
        projectContactPersonData.put("username", rs.getString("username"));
        projectContactPersonData.put("first_name", rs.getString("first_name"));
        projectContactPersonData.put("last_name", rs.getString("last_name"));
        projectContactPersonData.put("email", rs.getString("email"));
        projectContactPersonList.add(projectContactPersonData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getImportantUsers() > There was an error getting the data for Project Contact Persons {}.", e);
      return null;
    }
    LOG.debug("<< getImportantUsers():{}", projectContactPersonList);
    return projectContactPersonList;
  }

  @Override
  public Map<String, String> getUser(int userId) {
    LOG.debug(">> getUser(userId={})", userId);
    Map<String, String> userData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.username, u.password, u.is_ccafs_user, u.last_login, ");
      query.append("p.first_name, p.last_name, p.email, p.phone ");
      query.append("FROM users u ");
      query.append("INNER JOIN persons p ON u.person_id = p.id ");
      query.append("WHERE u.id = '");
      query.append(userId);
      query.append("'; ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        userData.put("id", "" + userId);
        userData.put("username", rs.getString("username"));
        userData.put("password", rs.getString("password"));
        userData.put("is_ccafs_user", rs.getString("is_ccafs_user"));
        userData.put("last_login", rs.getString("last_login"));
        userData.put("first_name", rs.getString("first_name"));
        userData.put("last_name", rs.getString("last_name"));
        userData.put("email", rs.getString("email"));
        userData.put("phone", rs.getString("phone"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUser() > There was an error getting the data for user with id {}.", userId, e);
    }
    LOG.debug("<< getUser():{}", userData);
    return userData;

  }

  @Override
  public Map<String, String> getUser(String username) {
    LOG.debug(">> getUser(username={})", username);
    Map<String, String> userData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.username, u.password, u.is_ccafs_user, u.last_login, ");
      query.append("p.first_name, p.last_name, p.email, p.phone ");
      query.append("FROM users u ");
      query.append("INNER JOIN persons p ON u.person_id = p.id ");
      query.append("WHERE u.username = '");
      query.append(username);
      query.append("'; ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        userData.put("id", rs.getString("id"));
        userData.put("username", username);
        userData.put("password", rs.getString("password"));
        userData.put("is_ccafs_user", rs.getString("is_ccafs_user"));
        userData.put("last_login", rs.getString("last_login"));
        userData.put("first_name", rs.getString("first_name"));
        userData.put("last_name", rs.getString("last_name"));
        userData.put("email", rs.getString("email"));
        userData.put("phone", rs.getString("phone"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUser() > There was an error getting the data for user {}.", username, e);
    }
    LOG.debug("<< getUser():{}", userData);
    return userData;
  }

  @Override
  public boolean saveLastLogin(Map<String, String> userData) {
    LOG.debug(">> saveLastLogin(userData={})", userData);
    String query = "UPDATE users SET last_login = ? WHERE id = ?;";
    Object[] values = new Object[2];
    values[0] = userData.get("last_login");
    values[1] = userData.get("user_id");


    int rows = dbManager.saveData(query, values);
    if (rows <= 0) {
      LOG.warn("-- saveLastLogin() > There was an error saving the last login for the user {} into the database.",
        userData.get("user_id"));
      LOG.warn("Query: {}", query);
      LOG.warn("Values: {}", values);
      return false;
    }

    LOG.debug("<< saveLastLogin():true");
    return true;
  }

  @Override
  public boolean saveUser(Map<String, String> userData) {
    LOG.debug(">> saveUser(userData={})", userData);
    String query = "INSERT INTO users (name, email, password, activity_leader_id, role) VALUES (?, ?, ?, ?);";
    Object[] values = new Object[4];
    values[0] = userData.get("name");
    values[0] = userData.get("email");
    values[1] = userData.get("password");
    values[2] = userData.get("activity_leader_id");
    values[3] = userData.get("role");


    int rows = dbManager.saveData(query, values);
    if (rows <= 0) {
      LOG.warn("-- saveUser() > There was an error saving a new user into the database.");
      LOG.warn("Query: {}", query);
      LOG.warn("Values: {}", values);

      LOG.debug("<< saveUser():false");
      return false;
    }
    LOG.debug("<< saveUser():true");
    return true;
  }
}
