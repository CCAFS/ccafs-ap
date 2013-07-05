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
  public boolean deleteContactPersons(int activityID) {
    LOG.debug(">> deleteContactPersons(activityID={})", activityID);
    String query = "DELETE FROM contact_person WHERE activity_id = ?";
    try (Connection con = databaseManager.getConnection()) {
      int rowsDeleted = databaseManager.makeChangeSecure(con, query, new Object[] {activityID});
      if (rowsDeleted >= 0) {
        LOG.debug("<< deleteContactPersons():{}", true);
        return true;
      }
    } catch (SQLException e) {
      LOG.error(
        "-- deleteContactPersons() > There was an error deleting the contact persons related to the activity {}",
        activityID, e);
    }

    LOG.debug("<< deleteContactPersons():{}", false);
    return false;
  }

  @Override
  public List<Map<String, String>> getContactPersons(int activityID) {
    LOG.debug(">> getContactPersons(activityID={})", activityID);

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
      LOG.error("-- getContactPersons() > There was an error getting the contact persons related to the activity {}.",
        activityID, e);
    }

    LOG.debug("<< getContactPersons():contactPersonsDB.size={}", contactPersonsDB.size());
    return contactPersonsDB;
  }

  @Override
  public boolean saveContactPersons(Map<String, String> contactPerson, int activityID) {
    LOG.debug(">> saveContactPersons(contactPerson={}, activityID={})", contactPerson, activityID);
    boolean saved = false;
    String query =
      "INSERT INTO contact_person (id, name, email, activity_id) VALUES (?, ?, ?, " + activityID
        + ") ON DUPLICATE KEY UPDATE name = VALUES(name), email = VALUES(email), activity_id = VALUES(activity_id);";
    Object[] values = new Object[3];
    values[0] = contactPerson.get("id");
    values[1] = contactPerson.get("name");
    values[2] = contactPerson.get("email");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows > 0) {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveContactPersons() > There was an error saving the contact person for activity {} into the DAO.",
        activityID, e);
    }

    LOG.debug("<< saveContactPersons():{}", saved);
    return saved;
  }
}
