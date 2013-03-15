package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ContactPersonDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

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


public class MySQLContactPersonDAO implements ContactPersonDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLContactPersonDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLContactPersonDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getContactPersons(int activityID) {
    List<Map<String, String>> contactPersonsDB = new ArrayList<>();
    String query = "SELECT * FROM contact_person WHERE activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        HashMap<String, String> cps = new HashMap<>();
        cps.put("id", rs.getString("id"));
        cps.put("email", rs.getString("email"));
        cps.put("name", rs.getString("name"));
        contactPersonsDB.add(cps);
      }
    } catch (SQLException e) {
      LOG.error("There was an error getting the contact persons related to an activity. \n{}", query, e);
    }

    return contactPersonsDB;
  }

}
