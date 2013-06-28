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
    LOG.debug(">> getKeywordList(activityID={})", activityID);
    List<Map<String, String>> keywordDataList = new ArrayList<>();
    String query =
      "SELECT ak.id, ak.other, ke.id as keyword_id, ke.name as keyword_name FROM activity_keywords ak "
        + "LEFT JOIN keywords ke ON ak.keyword_id = ke.id " + "WHERE ak.activity_id = " + activityID
        + " ORDER BY ke.name";
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
      LOG.error("-- getKeywordList() > There was an error getting activity keywords for activity {}", activityID, e);
    }
    LOG.debug("<< getKeywordList():{}", keywordDataList);
    return keywordDataList;
  }

  @Override
  public boolean removeActivityKeywords(int activityID) {
    LOG.debug(">> removeActivityKeywords(activityID={})", activityID);

    boolean problem = false;
    String removeQuery = "DELETE FROM activity_keywords WHERE activity_id = " + activityID;
    try (Connection connection = databaseManager.getConnection()) {
      int rows = databaseManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        LOG
          .warn(
            "-- removeActivityKeywords() > It was tried delete activity keywords from database for activity {} but failed.",
            activityID);
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error(
        "-- removeActivityKeywords() > There was an error deleting the activity keywords related to the activity {}.",
        activityID, e);
    }

    LOG.debug("<< removeActivityKeywords():{}", !problem);
    return !problem;
  }

  @Override
  public boolean saveKeyword(Map<String, String> keywordData) {
    LOG.debug(">> saveKeyword(keywordData={})", keywordData);
    boolean saved = false;
    String query = "INSERT INTO activity_keywords (id, keyword_id, other, activity_id) VALUES (?, ?, ?, ?)  ";
    Object[] values = new Object[4];
    values[0] = keywordData.get("id");
    values[1] = keywordData.get("keyword_id");
    values[2] = keywordData.get("other");
    values[3] = keywordData.get("activity_id");

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG
          .warn("-- saveKeyword() > There was an error saving the keyword. \n Query: {}. \n Values: {}", query, values);
      } else {
        saved = true;
      }
    } catch (SQLException e) {
      LOG.error("-- saveKeyword() > There was an error saving the keyword.", e);
    }

    LOG.debug("<< saveKeyword():{}", saved);
    return saved;
  }

}
