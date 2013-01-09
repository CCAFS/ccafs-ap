package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;

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


public class MySQLDeliverableDAO implements DeliverableDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLDeliverableDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public int addDeliverable(Map<String, Object> deliverableData) {
    int generatedId = -1;
    try (Connection connection = databaseManager.getConnection()) {
      String addDeliveryQuery =
        "INSERT INTO deliverables (description, year, activity_id, deliverable_type_id, is_expected, deliverable_status_id) VALUES (?, ?, ?, ?, ?, ?)";
      Object[] values = new Object[6];
      values[0] = deliverableData.get("description");
      values[1] = deliverableData.get("year");
      values[2] = deliverableData.get("activity_id");
      values[3] = deliverableData.get("deliverable_type_id");
      values[4] = deliverableData.get("is_expected");
      values[5] = deliverableData.get("deliverable_status_id");
      int deliverableAdded = databaseManager.makeChangeSecure(connection, addDeliveryQuery, values);
      if (deliverableAdded > 0) {
        // get the id assigned to this new record.
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", connection);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return generatedId;
  }

  @Override
  public List<Map<String, String>> getDeliverables(int activityID) {
    List<Map<String, String>> deliverables = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT de.id, de.description, de.year, de.is_expected, ds.id as 'deliverable_status_id', "
          + "ds.name as 'deliverable_status_name', dt.id as 'deliverable_type_id', dt.name as 'deliverable_type_name' "
          + "FROM deliverables de " + "INNER JOIN deliverable_types dt ON de.deliverable_type_id = dt.id "
          + "INNER JOIN deliverable_status ds ON de.deliverable_status_id = ds.id " + "WHERE de.activity_id="
          + activityID + " ORDER BY de.id";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> deliverable = new HashMap<>();
        deliverable.put("id", rs.getString("id"));
        deliverable.put("description", rs.getString("description"));
        deliverable.put("year", rs.getString("year"));
        deliverable.put("is_expected", rs.getString("is_expected"));
        deliverable.put("deliverable_status_id", rs.getString("deliverable_status_id"));
        deliverable.put("deliverable_status_name", rs.getString("deliverable_status_name"));
        deliverable.put("deliverable_type_id", rs.getString("deliverable_type_id"));
        deliverable.put("deliverable_type_name", rs.getString("deliverable_type_name"));
        deliverables.add(deliverable);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

    if (deliverables.isEmpty()) {
      return null;
    } else {
      return deliverables;
    }

  }

  @Override
  public boolean removeNotExpected(int activityID) {
    try (Connection connection = databaseManager.getConnection()) {
      String deleteDeliverableQuery = "DELETE FROM deliverables WHERE is_expected = 0 AND activity_id = ?";
      int rowsDeleted = databaseManager.makeChangeSecure(connection, deleteDeliverableQuery, new Object[] {activityID});
      if (rowsDeleted >= 0) {
        return true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
}
