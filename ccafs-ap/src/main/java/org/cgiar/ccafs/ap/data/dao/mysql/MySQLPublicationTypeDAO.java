package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PublicationTypeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLPublicationTypeDAO implements PublicationTypeDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLPublicationTypeDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public Map<String, String> getPublicationType(String id) {
    Map<String, String> typeData = new HashMap<>();
    String query = "SELECT * FROM publication_types WHERE id = " + id;
    try (Connection connection = dbManager.getConnection()) {
      ResultSet rs = dbManager.makeQuery(query, connection);
      if (rs.next()) {
        typeData.put("id", rs.getString("id"));
        typeData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting publication types information. '{}'", query, e);
    }
    return typeData;
  }

  @Override
  public Map<String, String> getPublicationTypes() {
    Map<String, String> typesData = new LinkedHashMap<>();
    String query = "SELECT * FROM publication_types";
    try (Connection connection = dbManager.getConnection()) {
      ResultSet rs = dbManager.makeQuery(query, connection);
      while (rs.next()) {
        typesData.put(rs.getString("id"), rs.getString("name"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting publication types information. '{}'", query, e);
    }
    return typesData;
  }

}
