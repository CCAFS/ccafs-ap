package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.ThemeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLThemeDAO implements ThemeDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLThemeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getThemes() {
    List<Map<String, String>> themes = new ArrayList<>();
    String query = "SELECT * FROM themes";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> theme = new HashMap<String, String>();
        theme.put("id", rs.getString("id"));
        theme.put("code", rs.getString("code"));
        theme.put("description", rs.getString("description"));
        themes.add(theme);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return themes;
  }

  @Override
  public List<Map<String, String>> getThemes(int logframeId) {
    List<Map<String, String>> themes = new ArrayList<>();
    String query = "SELECT * FROM themes WHERE logframe_id = " + logframeId;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> theme = new HashMap<String, String>();
        theme.put("id", rs.getString("id"));
        theme.put("code", rs.getString("code"));
        theme.put("description", rs.getString("description"));
        themes.add(theme);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return themes;
  }
}