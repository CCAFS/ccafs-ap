package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityKeywordDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

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


public class MySQLActivityKeywordDAO implements ActivityKeywordDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLActivityKeywordDAO.class);
  DAOManager databaseManager;

  @Inject
  public MySQLActivityKeywordDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getKeywordList(int activityID) {
    List<Map<String, String>> keywordDataList = new ArrayList<>();
    String query =
      "SELECT ak.id, ak.other, ke.id as keyword_id, ke.name as keyword_name FROM activity_keywords ak "
        + "INNER JOIN keywords ke ON ak.keyword_id = ke.id " + "WHERE ak.activity_id = " + activityID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> keywordData = new HashMap<String, String>();
        keywordData.put("id", rs.getString("id"));
        keywordData.put("other", rs.getString("other"));
        keywordData.put("keyword_id", rs.getString("keyword_id"));
        keywordData.put("keyword_name", rs.getString("keyword_name"));
        keywordDataList.add(keywordData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the data from 'activity_keywords' table \n{}", query, e);
    }
    return keywordDataList;
  }

}
