package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PublicationThemeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLPublicationThemeDAO implements PublicationThemeDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLPublicationThemeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getThemes(int publicationId) {
    List<Map<String, String>> themesDataList = new ArrayList<>();
    String query =
      "SELECT th.id, th.code, th.description FROM themes th "
        + "INNER JOIN themes_publications tp ON th.id = tp.theme_id " + "WHERE tp.publication_id = " + publicationId;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> themeData = new HashMap<>();
        themeData.put("id", rs.getString("id"));
        themeData.put("code", rs.getString("code"));
        themeData.put("description", rs.getString("description"));
        themesDataList.add(themeData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return themesDataList;
  }

  @Override
  public boolean saveThemes(int publicationId, ArrayList<String> themeIds) {
    boolean added = false;
    try (Connection con = databaseManager.getConnection()) {
      String addQuery = "INSERT INTO themes_publications (publication_id, theme_id) VALUES ";
      boolean isFirst = true;
      for (String themeId : themeIds) {
        if (isFirst) {
          isFirst = false;
        } else {
          addQuery += ", ";
        }
        addQuery += "(" + publicationId + ", ?)";
      }
      Object[] values = new Object[themeIds.size()];
      for (int c = 0; c < values.length; c++) {
        values[c] = themeIds.get(c);
      }
      int rows = databaseManager.makeChangeSecure(con, addQuery, values);
      if (rows > 0) {
        added = true;
      }
    } catch (SQLException e) {
      // TODO: handle exception
      e.printStackTrace();
    }
    return added;
  }

}
