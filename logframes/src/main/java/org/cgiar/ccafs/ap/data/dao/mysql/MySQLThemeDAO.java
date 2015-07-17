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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLThemeDAO implements ThemeDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLThemeDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLThemeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getThemes() {
    LOG.debug(">> getThemes()");
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
      LOG.error("-- getThemes() > There was an error getting the themes list.", e);
    }

    LOG.debug("<< getThemes():themes.size={}", themes.size());
    return themes;
  }

  @Override
  public List<Map<String, String>> getThemes(int logframeId) {
    LOG.debug(">> getThemes(logframeId={})", logframeId);
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
      LOG.error("-- getThemes() > There was an error getting the themes list related to the logframe {}.", logframeId,
        e);
    }

    LOG.debug("<< getThemes():themes.size={}", themes.size());
    return themes;
  }

  @Override
  public List<Map<String, String>> getThemesByPartner(int partnerId) {
    LOG.debug(">> getThemesByPartner(partnerId = {})", partnerId);
    List<Map<String, String>> themes = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT th.* FROM themes th ");
    query.append("INNER JOIN objectives ob ON th.id = ob.theme_id ");
    query.append("INNER JOIN outputs op ON ob.id = op.objective_id ");
    query.append("INNER JOIN milestones m ON op.id = m.output_id ");
    query.append("INNER JOIN activities a ON m.id = a.milestone_id ");
    query.append("INNER JOIN activity_partners ap ON a.id = ap.activity_id ");
    query.append("INNER JOIN partners p ON ap.partner_id = p.id ");
    query.append("WHERE p.id = " + partnerId + " ");
    query.append("GROUP BY th.code ");
    query.append("ORDER BY th.code ");
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> theme = new HashMap<String, String>();
        theme.put("id", rs.getString("id"));
        theme.put("code", rs.getString("code"));
        theme.put("description", rs.getString("description"));
        themes.add(theme);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getThemesByPartner() > There was an error getting the themes list.", e);
    }

    LOG.debug("<< getThemesByPartner():themes.size={}", themes.size());
    return themes;
  }
}