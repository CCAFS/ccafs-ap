package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.UserRoleDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLUserRoleDAO implements UserRoleDAO {

  private DAOManager databaseManager;
  private static Logger LOG = LoggerFactory.getLogger(MySQLUserRoleDAO.class);

  @Inject
  public MySQLUserRoleDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getUserRole(String name) {
    LOG.debug(">> getUserRole(name={})", name);

    Map<String, String> userRoleData = new HashMap<>();
    String query = "SELECT * FROM `UserRoles` WHERE name = `" + name + "`; ";

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        userRoleData.put("id", rs.getString("id"));
        userRoleData.put("name", rs.getString("name"));
      } else {
        userRoleData = null;
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getUserRole() >> There was an exception trying to get the role information.", e);
    }

    LOG.debug("<< getUserRole():{}");
    return userRoleData;
  }
}
