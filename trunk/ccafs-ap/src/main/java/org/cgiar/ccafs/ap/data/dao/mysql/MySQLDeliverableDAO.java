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
    LOG.debug(">> addDeliverable(deliverableData={})", deliverableData);
    int generatedId = -1;
    try (Connection connection = databaseManager.getConnection()) {

      String addDeliveryQuery =
        "INSERT INTO deliverables (id, description, year, activity_id, deliverable_type_id, is_expected, deliverable_status_id, filename, description_update) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) "
          + "ON DUPLICATE KEY UPDATE description = VALUES(description), year = VALUES(year), activity_id = VALUES(activity_id), "
          + "deliverable_type_id = VALUES(deliverable_type_id), is_expected = VALUES(is_expected), deliverable_status_id = VALUES(deliverable_status_id), filename = VALUES(filename), description_update = VALUES(description_update)";
      Object[] values = new Object[9];
      values[0] = deliverableData.get("id");
      values[1] = deliverableData.get("description");
      values[2] = deliverableData.get("year");
      values[3] = deliverableData.get("activity_id");
      values[4] = deliverableData.get("deliverable_type_id");
      values[5] = deliverableData.get("is_expected");
      values[6] = deliverableData.get("deliverable_status_id");
      values[7] = deliverableData.get("filename");
      values[8] = deliverableData.get("description_update");
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
      LOG.error("-- addDeliverable() > There was a problem saving new deliverables into the database. \n{}", e);
    }

    LOG.debug("<< addDeliverable():{}", generatedId);
    return generatedId;
  }

  @Override
  public Map<String, String> getDeliverable(int deliverableID) {
    LOG.debug(">> getDeliverable(deliverableID={})", deliverableID);
    Map<String, String> deliverable = new HashMap<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT d.*, ds.name as 'deliverable_status_name' FROM deliverables d ");
    query.append("INNER JOIN deliverable_status ds ON d.deliverable_status_id = ds.id ");
    query.append("WHERE d.id =" + deliverableID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        deliverable.put("id", rs.getString("id"));
        deliverable.put("description", rs.getString("description"));
        deliverable.put("year", rs.getString("year"));
        deliverable.put("is_expected", rs.getString("is_expected"));
        deliverable.put("description_update", rs.getString("description_update"));
        deliverable.put("deliverable_status_id", rs.getString("deliverable_status_id"));
        deliverable.put("deliverable_status_name", rs.getString("deliverable_status_name"));
        deliverable.put("deliverable_type_id", rs.getString("deliverable_type_id"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was a problem getting deliverables for activity {}.", deliverableID, e);
      LOG.debug("<< getDeliverables():null");
      return null;
    }

    LOG.debug("<< getDeliverable():{}", deliverable);
    return deliverable;
  }

  @Override
  public Map<String, String> getDeliverableLeader(int deliverableID) {
    LOG.debug(">> getDeliverableLeader(deliverableID={})", deliverableID);

    Map<String, String> leader = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT l.id, l.name, l.acronym ");
    query.append("FROM deliverables d ");
    query.append("INNER JOIN activities a ON d.activity_id = a.id ");
    query.append("INNER JOIN activity_leaders l ON a.activity_leader_id = l.id ");
    query.append("WHERE d.id = ");
    query.append(deliverableID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        leader.put("id", rs.getString("id"));
        leader.put("name", rs.getString("name"));
        leader.put("acronym", rs.getString("acronym"));
      }
    } catch (SQLException e) {
      String message =
        "-- getDeliverableLeader(): Exception occurred trying to get the leader to which the deliverable {} contributes to.";
      LOG.debug(message, deliverableID, e);
    }

    LOG.debug("<< getDeliverableLeader():", leader);
    return leader;
  }

  @Override
  public List<Map<String, String>> getDeliverableMetadata(int deliverableID) {
    LOG.debug(">> getDeliverableMetadata(deliverableID={})", deliverableID);
    List<Map<String, String>> deliverableMetadata = new ArrayList<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT m.id, m.description, m.name, dm.value ");
    query.append("FROM deliverable_metadata dm ");
    query.append("INNER JOIN metadata_questions m ON dm.metadata_id = m.id ");
    query.append("WHERE dm.deliverable_id = ");
    query.append(deliverableID);

    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      if (rs.next()) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("id", rs.getString("id"));
        metadata.put("description", rs.getString("description"));
        metadata.put("name", rs.getString("name"));
        metadata.put("value", rs.getString("value"));

        deliverableMetadata.add(metadata);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableMetadata() > There was a problem getting the metadata for deliverable {}.",
        deliverableID, e);
    }

    LOG.debug("<< getDeliverableMetadata():deliverableMetadata.size={}", deliverableMetadata.size());
    return deliverableMetadata;
  }

  @Override
  public List<Map<String, String>> getDeliverablesByActivityID(int activityID) {
    LOG.debug(">> getDeliverables(activityID={})", activityID);
    List<Map<String, String>> deliverables = new ArrayList<>();
    String query =
      "SELECT de.id, de.description, de.year, de.is_expected, de.filename, de.description_update, ds.id as 'deliverable_status_id', "
        + "ds.name as 'deliverable_status_name', dt.id as 'deliverable_type_id', dt.name as 'deliverable_type_name' "
        + "FROM deliverables de "
        + "INNER JOIN deliverable_types dt ON de.deliverable_type_id = dt.id "
        + "INNER JOIN deliverable_status ds ON de.deliverable_status_id = ds.id "
        + "WHERE de.activity_id="
        + activityID + " ORDER BY de.id";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> deliverable = new HashMap<>();
        deliverable.put("id", rs.getString("id"));
        deliverable.put("description", rs.getString("description"));
        deliverable.put("year", rs.getString("year"));
        deliverable.put("is_expected", rs.getString("is_expected"));
        deliverable.put("filename", rs.getString("filename"));
        deliverable.put("description_update", rs.getString("description_update"));
        deliverable.put("deliverable_status_id", rs.getString("deliverable_status_id"));
        deliverable.put("deliverable_status_name", rs.getString("deliverable_status_name"));
        deliverable.put("deliverable_type_id", rs.getString("deliverable_type_id"));
        deliverable.put("deliverable_type_name", rs.getString("deliverable_type_name"));
        deliverables.add(deliverable);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was a problem getting deliverables for activity {}.", activityID, e);
      LOG.debug("<< getDeliverables():null");
      return null;
    }

    if (deliverables.isEmpty()) {
      LOG.debug("<< getDeliverables():null");
      return null;
    }

    LOG.debug("<< getDeliverables():deliverables.size={}", deliverables.size());
    return deliverables;
  }

  @Override
  public int getDeliverablesCount(int activityID) {
    LOG.debug(">> getDeliverablesCount(activityID={})", activityID);

    int deliverableCount = 0;
    String query = "SELECT COUNT(id) FROM deliverables WHERE activity_id = " + activityID;
    try (Connection connection = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, connection);
      if (rs.next()) {
        deliverableCount = rs.getInt(1);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverablesCount() > There was a problem counting the deliverables for activity {}.",
        activityID, e);
    }

    LOG.debug("<< getDeliverablesCount():{}", deliverableCount);
    return deliverableCount;
  }

  @Override
  public List<Map<String, String>> getDeliverablesListByLeader(int activityLeaderID) {
    List<Map<String, String>> deliverables = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT de.id, de.description, de.year, de.is_expected, ");
    query.append("ds.id as 'deliverable_status_id', ds.name as 'deliverable_status_name', ");
    query.append("dt.id as 'deliverable_type_id', dt.name as 'deliverable_type_name' ");
    query.append("FROM deliverables de ");
    query.append("INNER JOIN activities a ON de.activity_id = a.id ");
    query.append("INNER JOIN deliverable_types dt ON de.deliverable_type_id = dt.id ");
    query.append("INNER JOIN deliverable_status ds ON de.deliverable_status_id = ds.id ");
    query.append("WHERE a.activity_leader_id = ");
    query.append(activityLeaderID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
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
      LOG.error("There was a problem getting deliverables for activity {}.", activityLeaderID, e);
    }

    return deliverables;
  }

  @Override
  public List<Map<String, String>> getDeliverablesListByTheme(int themeCode) {
    LOG.debug(">> getDeliverablesListByTheme(themeCode = {})", themeCode);

    List<Map<String, String>> deliverables = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT de.id, de.description, de.year, de.is_expected, ");
    query.append("ds.id as 'deliverable_status_id', ds.name as 'deliverable_status_name', ");
    query.append("dt.id as 'deliverable_type_id', dt.name as 'deliverable_type_name' ");
    query.append("FROM deliverables de ");
    query.append("INNER JOIN deliverable_types dt ON de.deliverable_type_id = dt.id ");
    query.append("INNER JOIN deliverable_status ds ON de.deliverable_status_id = ds.id ");
    query.append("INNER JOIN activities a ON de.activity_id = a.id ");
    query.append("INNER JOIN milestones m ON a.milestone_id = m.id ");
    query.append("INNER JOIN outputs ou ON m.output_id = ou.id ");
    query.append("INNER JOIN objectives ob ON ou.objective_id = ob.id ");
    query.append("INNER JOIN themes th ON ob.theme_id = th.id ");
    query.append("WHERE th.code = ");
    query.append(themeCode);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
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
      LOG.error("-- getDeliverablesListByTheme() > Exception getting deliverables of theme {}.", themeCode, e);
    }

    LOG.debug("<< getDeliverablesListByTheme(): {}.", deliverables);
    return deliverables;
  }

  @Override
  public Map<String, String> getDeliverableTheme(int deliverableID) {
    LOG.debug(">> getDeliverableTheme(deliverableID={})", deliverableID);

    Map<String, String> theme = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT al.id, al.acronym, al.name ");
    query.append("FROM activity_leaders al ");
    query.append("INNER JOIN themes t ON al.theme_id = t.code ");
    query.append("INNER JOIN objectives ob ON t.id = ob.theme_id ");
    query.append("INNER JOIN outputs op ON ob.id = op.objective_id ");
    query.append("INNER JOIN milestones m ON op.id = m.output_id  ");
    query.append("INNER JOIN activities a ON m.id = a.milestone_id ");
    query.append("INNER JOIN deliverables d ON a.id = d.activity_id ");
    query.append("WHERE d.id = ");
    query.append(deliverableID);

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        theme.put("id", rs.getString("id"));
        theme.put("acronym", rs.getString("acronym"));
        theme.put("name", rs.getString("name"));
      }
    } catch (SQLException e) {
      String message =
        "-- getDeliverableTheme(): Exception occurred trying to get the theme to which the deliverable {} contributes to.";
      LOG.debug(message, deliverableID, e);
    }

    LOG.debug("<< getDeliverableTheme():", theme);
    return theme;
  }

  @Override
  public boolean removeDeliverable(int deliverableID) {
    LOG.debug(">> removeDeliverable(deliverableID={})", deliverableID);
    boolean success = true;
    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM deliverables WHERE id = ?");

    try (Connection con = databaseManager.getConnection()) {
      int rowsDeleted = databaseManager.makeChangeSecure(con, query.toString(), new String[] {deliverableID + ""});
      if (rowsDeleted >= 0) {
        LOG.debug("<< removeDeliverable():", true);
        return true;
      }
    } catch (SQLException e) {
      LOG.error("-- removeDeliverable(): Exception trying to delete the deliverable {}", deliverableID, e);
    }

    LOG.debug("<< removeDeliverable():", success);
    return success;
  }

  @Override
  public boolean removeExpected(int activityID) {
    LOG.debug(">> removeExpected(activityID={})", activityID);

    String deleteDeliverableQuery = "DELETE FROM deliverables WHERE is_expected = 1 AND activity_id = ?";
    try (Connection connection = databaseManager.getConnection()) {
      int rowsDeleted = databaseManager.makeChangeSecure(connection, deleteDeliverableQuery, new Object[] {activityID});
      if (rowsDeleted >= 0) {
        LOG.debug("<< removeExpected():{}", true);
        return true;
      }
    } catch (SQLException e) {
      LOG.error("-- removeExpected() > There was a problem deleting the planned deliverables for activity {}.",
        activityID, e);
    }

    LOG.debug("<< getDeliverablesCount():{}", false);
    return false;
  }

  @Override
  public boolean removeNotExpected(int activityID) {
    LOG.debug(">> removeNotExpected(activityID={})", activityID);

    String deleteDeliverableQuery = "DELETE FROM deliverables WHERE is_expected = 0 AND activity_id = ?";
    try (Connection connection = databaseManager.getConnection()) {
      int rowsDeleted = databaseManager.makeChangeSecure(connection, deleteDeliverableQuery, new Object[] {activityID});
      if (rowsDeleted >= 0) {
        LOG.debug("<< removeNotExpected():{}", true);
        return true;
      }
    } catch (SQLException e) {
      LOG.error("-- removeNotExpected() > There was a problem deleting the new deliverables for activity {}.",
        activityID, e);
    }

    LOG.debug("<< removeNotExpected():{}", false);
    return false;
  }
}
