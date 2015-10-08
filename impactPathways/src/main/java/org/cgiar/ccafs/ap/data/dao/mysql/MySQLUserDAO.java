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

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.UserDAO;
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
 * @author Hernán David Carvajal.
 * @author Héctor Fabio Tobón R.
 */
public class MySQLUserDAO implements UserDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLUserDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLUserDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public List<Map<String, String>> getAllEmployees() {
    LOG.debug(">> getAllEmployees()");
    List<Map<String, String>> employeesList = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id,e.id as employee_id, u.first_name, u.last_name, ");
      query.append("u.email, e.institution_id, e.role_id, r.name as role_name, r.acronym as role_acronym ");
      query.append("FROM employees e ");
      query.append("INNER JOIN users u  ON e.user_id=u.id ");
      query.append("INNER JOIN roles r  ON e.role_id=r.id ");
      query.append("ORDER BY u.last_name");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> employeeData = new HashMap<>();
        employeeData.put("id", rs.getString("id"));
        employeeData.put("employee_id", rs.getString("employee_id"));
        employeeData.put("first_name", rs.getString("first_name"));
        employeeData.put("last_name", rs.getString("last_name"));
        employeeData.put("email", rs.getString("email"));
        employeeData.put("institution_id", rs.getString("institution_id"));
        employeeData.put("role_id", rs.getString("role_id"));
        employeeData.put("role_name", rs.getString("role_name"));
        employeeData.put("role_acronym", rs.getString("role_acronym"));
        employeesList.add(employeeData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getAllEmployees() > There was an error getting the data for Employees {}.", e);
      return null;
    }
    LOG.debug("<< getAllEmployees():{}", employeesList);
    return employeesList;
  }

  @Override
  public List<Map<String, String>> getAllOwners() {
    LOG.debug(">> getAllOwners()");
    List<Map<String, String>> projectContactPersonList = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.first_name, u.last_name, u.email ");
      query.append("FROM users u ");
      query.append("INNER JOIN liaison_users lu ON u.id = lu.user_id ");
      query.append("GROUP BY u.id ");
      query.append("ORDER BY u.last_name ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> projectContactPersonData = new HashMap<>();
        projectContactPersonData.put("id", rs.getString("id"));
        projectContactPersonData.put("first_name", rs.getString("first_name"));
        projectContactPersonData.put("last_name", rs.getString("last_name"));
        projectContactPersonData.put("email", rs.getString("email"));
        projectContactPersonList.add(projectContactPersonData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getAllOwners() > There was an error getting the data for All Project Owners {}.", e);
      return null;
    }
    LOG.debug("<< getAllOwners():{}", projectContactPersonList);
    return projectContactPersonList;
  }

  @Override
  public List<Map<String, String>> getAllOwners(int programId) {
    LOG.debug(">> getAllOwners()");
    List<Map<String, String>> projectContactPersonList = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT ins.id as institution_id, emp.id as employee_id, ");
      query.append("u.id, u.first_name, u.last_name, u.email, ");
      query.append("ro.id as role_id, ro.name as role_name, ro.acronym as role_acronym ");
      query.append("FROM users u ");
      query.append("INNER JOIN employees emp ON u.id=emp.user_id ");
      query.append("INNER JOIN roles ro ON emp.role_id=ro.id ");
      query.append("INNER JOIN institutions ins ON emp.institution_id = ins.id ");
      query.append("WHERE (ro.id= '");
      query.append(APConstants.ROLE_MANAGEMENT_LIAISON);
      query.append("' OR ro.id= '");
      query.append(APConstants.ROLE_COORDINATING_UNIT);
      query.append("') AND ins.program_id = ");
      query.append(programId);
      query.append(" ORDER BY u.last_name, ins.name ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> projectContactPersonData = new HashMap<>();
        projectContactPersonData.put("institution_id", rs.getString("institution_id"));
        projectContactPersonData.put("id", rs.getString("id"));
        projectContactPersonData.put("employee_id", rs.getString("employee_id"));
        projectContactPersonData.put("first_name", rs.getString("first_name"));
        projectContactPersonData.put("last_name", rs.getString("last_name"));
        projectContactPersonData.put("email", rs.getString("email"));
        projectContactPersonData.put("role_id", rs.getString("role_id"));
        projectContactPersonData.put("role_name", rs.getString("role_name"));
        projectContactPersonData.put("role_acronym", rs.getString("role_acronym"));
        projectContactPersonList.add(projectContactPersonData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getAllOwners() > There was an error getting the data for All Project Owners {}.", e);
      return null;
    }
    LOG.debug("<< getAllOwners():{}", projectContactPersonList);
    return projectContactPersonList;
  }


  @Override
  public List<Map<String, String>> getAllUsers() {
    LOG.debug(">> getAllUsers()");
    List<Map<String, String>> projectLeadersList = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.first_name, u.last_name, u.email ");
      query.append("FROM users u ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> projectLeaderData = new HashMap<>();
        projectLeaderData.put("id", rs.getString("id"));
        projectLeaderData.put("first_name", rs.getString("first_name"));
        projectLeaderData.put("last_name", rs.getString("last_name"));
        projectLeaderData.put("email", rs.getString("email"));
        projectLeadersList.add(projectLeaderData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getAllUsers() > There was an error getting the data for Project Leaders {}.", e);
      return null;
    }
    LOG.debug("<< getAllUsers():{}", projectLeadersList);
    return projectLeadersList;
  }

  @Override
  public String getEmailByUsername(String username) {
    LOG.debug(">> getEmailByUsername (username={})", new Object[] {username});
    String email = null;
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT email FROM users WHERE username = '");
      query.append(username);
      query.append("' ");
      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        email = rs.getString(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getEmailByUsername() > There was an error getting the data for the user with usernmae {}.",
        username, e);
    }
    LOG.debug("<< getEmailByUsername():{}", email);
    return email;
  }

  @Override
  public Map<String, String> getUser(int userId) {
    LOG.debug(">> getUser(userId={})", userId);
    Map<String, String> userData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT u.id, u.password, u.is_ccafs_user, u.last_login, ");
      query.append("u.first_name, u.last_name, u.email, u.is_active ");
      query.append("FROM users u ");
      query.append("WHERE u.id = '");
      query.append(userId);
      query.append("'; ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        userData.put("id", "" + userId);
        userData.put("password", rs.getString("password"));
        userData.put("is_ccafs_user", rs.getString("is_ccafs_user"));
        userData.put("last_login", rs.getString("last_login"));
        userData.put("first_name", rs.getString("first_name"));
        userData.put("last_name", rs.getString("last_name"));
        userData.put("email", rs.getString("email"));
        userData.put("is_active", rs.getString("is_active"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUser() > There was an error getting the data for user with id {}.", userId, e);
    }
    LOG.debug("<< getUser():{}", userData);
    return userData;

  }

  @Override
  public Map<String, String> getUser(String email) {
    LOG.debug(">> getUser(email={})", email);
    Map<String, String> userData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT * ");
      query.append("FROM users u ");
      query.append("WHERE u.email = '");
      query.append(email);
      query.append("'; ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        userData.put("id", rs.getString("id"));
        userData.put("password", rs.getString("password"));
        userData.put("is_ccafs_user", rs.getString("is_ccafs_user"));
        userData.put("last_login", rs.getString("last_login"));
        userData.put("first_name", rs.getString("first_name"));
        userData.put("last_name", rs.getString("last_name"));
        userData.put("username", rs.getString("username"));
        userData.put("email", rs.getString("email"));
        userData.put("is_active", rs.getString("is_active"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUser() > There was an error getting the data for user {}.", email, e);
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
    if (rows < 0) {
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
  public int saveUser(Map<String, Object> userData) {
    LOG.debug(">> saveUser(userData)", userData);
    StringBuilder query = new StringBuilder();
    Object[] values;
    if (userData.get("id") == null) {
      // Insert new record
      query.append(
        "INSERT INTO users (id, first_name, last_name, username, email, password, is_ccafs_user, created_by, is_active) ");
      query.append("VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ");
      values = new Object[9];
      values[0] = userData.get("id");
      values[1] = userData.get("first_name");
      values[2] = userData.get("last_name");
      values[3] = userData.get("username");
      values[4] = userData.get("email");
      values[5] = userData.get("password");
      values[6] = userData.get("is_ccafs_user");
      values[7] = userData.get("created_by");
      values[8] = userData.get("is_active");
    } else {
      // update record
      query.append(
        "UPDATE users SET first_name = ?, last_name = ?, username = ?, email = ?, password = ?, is_ccafs_user = ?, is_active = ? ");
      query.append("WHERE id = ? ");
      values = new Object[8];
      values[0] = userData.get("first_name");
      values[1] = userData.get("last_name");
      values[2] = userData.get("username");
      values[3] = userData.get("email");
      values[4] = userData.get("password");
      values[5] = userData.get("is_ccafs_user");
      values[6] = userData.get("is_active");
      values[7] = userData.get("id");
    }

    int result = dbManager.saveData(query.toString(), values);
    LOG.debug("<< saveUser(userData):{}", result);
    return result;
  }

  @Override
  public List<Map<String, String>> searchUser(String searchValue) {
    LOG.debug(">> searchUser(searchValue={})", searchValue);
    List<Map<String, String>> users = new ArrayList<>();

    try (Connection connection = dbManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT u.* ");
      query.append("FROM users u ");
      query.append("WHERE ");
      query.append("first_name like '%" + searchValue + "%' ");
      query.append("OR last_name like '%" + searchValue + "%' ");
      query.append("OR email like '%" + searchValue + "%'");
      query.append("GROUP BY email ");
      query.append("ORDER BY CASE ");
      query.append("WHEN email like '" + searchValue + "%' THEN 0 ");
      query.append("WHEN email like '% %" + searchValue + "% %' THEN 1 ");
      query.append("WHEN email like '%" + searchValue + "' THEN 2 ");
      query.append("WHEN last_name like '" + searchValue + "%' THEN 3 ");
      query.append("WHEN last_name like '% %" + searchValue + "% %' THEN 4 ");
      query.append("WHEN last_name like '%" + searchValue + "' THEN 5 ");
      query.append("WHEN first_name like '" + searchValue + "%' THEN 6 ");
      query.append("WHEN first_name like '% %" + searchValue + "% %' THEN 7 ");
      query.append("WHEN first_name like '%" + searchValue + "' THEN 8 ");
      query.append("ELSE 9 ");
      query.append("END, email, last_name, first_name ");

      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> userData = new HashMap<>();
        userData.put("id", rs.getString("id"));
        userData.put("first_name", rs.getString("first_name"));
        userData.put("last_name", rs.getString("last_name"));
        userData.put("email", rs.getString("email"));
        userData.put("is_active", rs.getString("is_active"));
        users.add(userData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- searchUser() > There was an error looking for users with the following search value {}.",
        searchValue, e);
      return null;
    }
    LOG.debug("<< searchUser():{}", users);
    return users;
  }
}
