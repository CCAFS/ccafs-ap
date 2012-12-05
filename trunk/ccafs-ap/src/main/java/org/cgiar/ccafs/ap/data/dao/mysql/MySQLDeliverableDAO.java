package org.cgiar.ccafs.ap.data.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;


public class MySQLDeliverableDAO implements DeliverableDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLDeliverableDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getDeliverables(int activityID) {
    List<Map<String, String>> deliverables = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT de.id, de.description, de.year, de.is_expected, ff.name as 'file_format_name', "
          + "ds.name as 'deliverable_status_name', dt.name as 'deliverable_type_name' "
          + "FROM activities ac, deliverables de, deliverable_formats df, file_formats ff, deliverable_types dt, "
          + "deliverable_status ds "
          + "WHERE ac.id = de.activity_id AND de.deliverable_type_id = dt.id AND de.id = df.deliverable_id "
          + "AND df.file_format_id = ff.id AND de.deliverable_status_id = ds.id AND ac.id=" + activityID;
      ResultSet rs = databaseManager.makeQuery(query, con);

      while (rs.next()) {
        Map<String, String> deliverable = new HashMap<>();
        deliverable.put("id", rs.getString("id"));
        deliverable.put("description", rs.getString("description"));
        deliverable.put("year", rs.getString("year"));
        deliverable.put("is_expected", rs.getString("is_expected"));
        deliverable.put("file_format_name", rs.getString("file_format_name"));
        deliverable.put("deliverable_status_name", rs.getString("deliverable_status_name"));
        deliverable.put("deliverable_type_name", rs.getString("deliverable_type_name"));
        deliverables.add(deliverable);
      }
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
}
