package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.RoleDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLRoleDAO implements RoleDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLRoleDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLRoleDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getRole(int userID, int institutionID) {
    Map<String, String> roleData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT r.id, r.name, r.acronym ");
    query.append("FROM roles r ");
    query.append("INNER JOIN employees e ON r.id = e.role_id ");
    query.append("WHERE user_id = ");
    query.append(userID);
    query.append(" AND institution_id = ");
    query.append(institutionID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        roleData.put("id", rs.getString("id"));
        roleData.put("name", rs.getString("name"));
        roleData.put("acronym", rs.getString("acronym"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception raised getting the role of the user {}", userID, e);
    }
    return roleData;
  }
}
