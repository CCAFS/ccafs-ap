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
  public List<Map<String, String>> getDeliverableSubTypes() {
    LOG.debug(">> getDeliverableSubTypes()");
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    String query = "SELECT * from deliverable_types WHERE parent_id IS NOT NULL";

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        typesData.put("parent_id", rs.getString("parent_id"));
        deliverableTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableSubTypes() > There was an error getting the deliverable types list. \n{}", query, e);
    }

    LOG.debug("<< getDeliverableSubTypes():deliverableTypesList.size={}", deliverableTypesList.size());
    return deliverableTypesList;
  }

  @Override
  public List<Map<String, String>> getDeliverableTypes() {
    LOG.debug(">> getDeliverableTypes()");
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    String query = "SELECT * from deliverable_types WHERE parent_id IS NULL";
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
  public List<Map<String, String>> getDeliverableTypesAndSubTypes() {
    LOG.debug(">> getDeliverableAndSubTypes()");
    List<Map<String, String>> deliverableTypesList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT dt.id, dt.name, dtp.id as 'parent_id', dtp.name as 'parent_name' ");
    query.append("FROM deliverable_types dt ");
    query.append("INNER JOIN deliverable_types dtp ON dt.parent_id = dtp.id ");


    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> typesData = new HashMap<>();
        typesData.put("id", rs.getString("id"));
        typesData.put("name", rs.getString("name"));
        typesData.put("parent_id", rs.getString("parent_id"));
        typesData.put("parent_name", rs.getString("parent_name"));
        deliverableTypesList.add(typesData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableAndSubTypes() > There was an error getting the deliverable types list. \n{}", query,
        e);
    }

    LOG.debug("<< getDeliverableAndSubTypes():deliverableTypesList.size={}", deliverableTypesList.size());
    return deliverableTypesList;
  }
}
