package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.ResourceDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLResourceDAO implements ResourceDAO {

  DAOManager databaseManager;

  @Inject
  public MySQLResourceDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getResources(int activityID) {
    List<Map<String, String>> resorcesDataList = new ArrayList<>();
    String query = "SELECT id, name FROM resources WHERE activity_id=" + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> resourcesData = new HashMap<String, String>();
        resourcesData.put("id", rs.getString("id"));
        resourcesData.put("name", rs.getString("name"));
        resorcesDataList.add(resourcesData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return resorcesDataList;
  }

}
