package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.RegionDAO;

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

public class MySQLRegionDAO implements RegionDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLRegionDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLRegionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getRegionsList() {
    List<Map<String, String>> regionDataList = new ArrayList<>();
    String query = "SELECT * FROM regions";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> regionData = new HashMap<>();
        regionData.put("id", rs.getString("id"));
        regionData.put("name", rs.getString("name"));
        regionData.put("description", rs.getString("description"));
        regionDataList.add(regionData);
      }
    } catch (SQLException e) {
      LOG.error("There was an error getting the region list", e);
    }
    return regionDataList;
  }
}
