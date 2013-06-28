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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLPublicationThemeDAO implements PublicationThemeDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLPublicationThemeDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLPublicationThemeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getThemes(int publicationId) {
    LOG.debug(">> getThemes(publicationId={})", publicationId);
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
      LOG.error("-- getThemes() > There was an error getting the theme of publication {}", publicationId, e);
    }
    LOG.debug("<< getThemes():themesDataList.size={}", themesDataList.size());
    return themesDataList;
  }

  @Override
  public boolean saveThemes(int publicationId, ArrayList<String> themeIds) {
    LOG.debug(">> saveThemes(publicationId, themeIds)", publicationId, themeIds);
    boolean added = false;
    String addQuery = "INSERT INTO themes_publications (publication_id, theme_id) VALUES ";
    try (Connection con = databaseManager.getConnection()) {
      for (int c = 0; c < themeIds.size(); c++) {
        if (c != 0) {
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
      LOG.error("-- saveThemes() > There was not posible save the data into 'themes' table. \n{}", e);
    }
    LOG.debug("<< saveThemes():{}", added);
    return added;
  }

}
