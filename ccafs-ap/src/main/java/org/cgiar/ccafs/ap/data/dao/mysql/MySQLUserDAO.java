/*
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
 */

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.UserDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
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
  public Map<String, String> getUser(String email) {
    LOG.debug(">> getUser(email={})", email);
    Map<String, String> userData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT u.*, al.id as leader_id, al.acronym as leader_acronym, al.name as leader_name, "
          + "lt.id as leader_type_id, lt.name as leader_type_name, " + "t.id as 'theme_id', t.code as 'theme_code', "
          + "r.id as 'region_id', r.name as 'region_name' " + "FROM users u "
          + "INNER JOIN activity_leaders al ON u.activity_leader_id = al.id "
          + "INNER JOIN leader_types lt ON al.led_activity_id = lt.id " + "LEFT JOIN themes t ON al.theme_id = t.id "
          + "LEFT JOIN regions r ON al.region_id = r.id " + "WHERE u.email = '" + email + "'";
      ResultSet rs = dbManager.makeQuery(query, connection);
      if (rs.next()) {
        userData.put("id", rs.getString("id"));
        userData.put("name", rs.getString("name"));
        userData.put("email", email);
        userData.put("password", rs.getString("password"));
        userData.put("role", rs.getString("role"));
        userData.put("leader_id", rs.getString("leader_id"));
        userData.put("leader_acronym", rs.getString("leader_acronym"));
        userData.put("leader_name", rs.getString("leader_name"));
        userData.put("leader_type_id", rs.getString("leader_type_id"));
        userData.put("leader_type_name", rs.getString("leader_type_name"));
        userData.put("theme_id", rs.getString("theme_id"));
        userData.put("theme_code", rs.getString("theme_code"));
        userData.put("region_id", rs.getString("region_id"));
        userData.put("region_name", rs.getString("region_name"));
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

    try (Connection con = dbManager.getConnection()) {
      int rows = dbManager.makeChangeSecure(con, query, values);
      if (rows <= 0) {
        LOG.warn("-- saveLastLogin() > There was an error saving the last login for the user {} into the database.",
          userData.get("user_id"));
        LOG.warn("Query: {}", query);
        LOG.warn("Values: {}", values);
        return false;
      }

    } catch (SQLException e) {
      LOG.error("-- saveLastLogin() > There was an error saving the last login for the user {} into the database.",
        userData.get("user_id"), e);
      LOG.debug("<< saveLastLogin():false");
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

    try (Connection con = dbManager.getConnection()) {
      int rows = dbManager.makeChangeSecure(con, query, values);
      if (rows <= 0) {
        LOG.warn("-- saveUser() > There was an error saving a new user into the database.");
        LOG.warn("Query: {}", query);
        LOG.warn("Values: {}", values);

        LOG.debug("<< saveUser():false");
        return false;
      }

    } catch (SQLException e) {
      LOG.error("-- saveUser() > There was a problem saving a new user into the database. \n{}", e);
    }
    LOG.debug("<< saveUser():true");
    return true;
  }
}
