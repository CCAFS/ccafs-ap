package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableStatusDAO;

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


public class MySQLDeliverableStatusDAO implements DeliverableStatusDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLDeliverableStatusDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableStatusDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getDeliverableStatus() {
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * from deliverable_status";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        deliverableTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return deliverableTypesList;
  }

  @Override
  public boolean setDeliverableStatus(int deliverableId, int statusId) {
    try (Connection connection = databaseManager.getConnection()) {
      String preparedUpdateQuery = "UPDATE deliverables SET deliverable_status_id = ? WHERE id = ?";
      int rowsUpdated =
        databaseManager.makeChangeSecure(connection, preparedUpdateQuery, new Object[] {statusId, deliverableId});
      return (rowsUpdated > 0);

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }
}
