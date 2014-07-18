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
