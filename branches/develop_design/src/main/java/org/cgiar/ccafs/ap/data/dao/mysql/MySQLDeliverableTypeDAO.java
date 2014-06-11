package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableTypeDAO;

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


public class MySQLDeliverableTypeDAO implements DeliverableTypeDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLDeliverableTypeDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLDeliverableTypeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getActiveDeliverableTypes() {
    LOG.debug(">> getDeliverableTypes()");
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    String query = "SELECT * FROM deliverable_types WHERE is_active = 1";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        deliverableTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableTypes() > There was an error getting the deliverable types list. \n{}", query, e);
    }

    LOG.debug("<< getDeliverableTypes():deliverableTypesList.size={}", deliverableTypesList.size());
    return deliverableTypesList;
  }

  @Override
  public List<Map<String, String>> getAllDeliverableTypes() {
    LOG.debug(">> getDeliverableTypes()");
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    String query = "SELECT * FROM deliverable_types";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        deliverableTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableTypes() > There was an error getting the deliverable types list. \n{}", query, e);
    }

    LOG.debug("<< getDeliverableTypes():deliverableTypesList.size={}", deliverableTypesList.size());
    return deliverableTypesList;
  }
}
