package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.KeywordDAO;

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


public class MySQLKeywordDAO implements KeywordDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLKeywordDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLKeywordDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getKeywordInformation(String id) {
    LOG.debug(">> getKeywordInformation(id={})", id);

    Map<String, String> keywordData = new HashMap<>();
    String query = "SELECT * FROM keywords WHERE id='" + id + "';";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        keywordData = new HashMap<>();
        keywordData.put("id", rs.getString("id"));
        keywordData.put("name", rs.getString("name"));
      }
      rs.close();
    } catch (Exception e) {
      LOG.error("-- getKeywordInformation() > There was an error getting a keyword. \n{}", query, e);
    }

    LOG.debug("<< getKeywordInformation():{}", keywordData.toString());
    return keywordData;
  }

  @Override
  public List<Map<String, String>> getKeywordList() {
    LOG.debug(">> getKeywordList()");
    List<Map<String, String>> keywordDataList = new ArrayList<>();
    String query = "SELECT * FROM keywords ORDER BY name";

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> keywordData = new HashMap<String, String>();
        keywordData.put("id", rs.getString("id"));
        keywordData.put("name", rs.getString("name"));
        keywordDataList.add(keywordData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getKeywordList() > There was an error getting the keyword list from the database.", e);
    }

    LOG.debug("<< getKeywordList():keywordDataList.size={}", keywordDataList.size());
    return keywordDataList;
  }
}
