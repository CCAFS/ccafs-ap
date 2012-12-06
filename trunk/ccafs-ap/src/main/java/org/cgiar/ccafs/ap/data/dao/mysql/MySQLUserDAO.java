package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.UserDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLUserDAO implements UserDAO {

  private DAOManager dbManager;

  @Inject
  public MySQLUserDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public Map<String, String> getUser(String email) {
    Map<String, String> userData = new HashMap<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT u.*, al.id as leader_id, al.acronym as leader_acronym, al.name as leader_name, "
          + "lt.id as leader_type_id, lt.name as leader_type_name FROM users u "
          + "INNER JOIN activity_leaders al ON u.activity_leader_id = al.id "
          + "INNER JOIN leader_types lt ON al.led_activity_id = lt.id WHERE u.email = '" + email + "'";
      ResultSet rs = dbManager.makeQuery(query, connection);
      if (rs.next()) {
        userData.put("id", rs.getString("id"));
        userData.put("email", email);
        userData.put("password", rs.getString("password"));
        userData.put("role", rs.getString("role"));
        userData.put("leader_id", rs.getString("leader_id"));
        userData.put("leader_acronym", rs.getString("leader_acronym"));
        userData.put("leader_name", rs.getString("leader_name"));
        userData.put("leader_type_id", rs.getString("leader_type_id"));
        userData.put("leader_type_name", rs.getString("leader_type_name"));
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return userData;
  }
}
