package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CasesStudiesDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLCasesStudiesDAO implements CasesStudiesDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLCasesStudiesDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCasesStudiesList(int activityLeaderId, int logframeId) {
    List<Map<String, String>> casesStudiesDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT cs.id, cs.title, cs.author, cs.date, cs.photo, cs.objectives, cs.description, cs.results, cs.partners, "
          + "cs.links, cs.keywords, cs.logframe_id " + "FROM case_studies cs " + "WHERE cs.activity_leader_id="
          + activityLeaderId + " AND logframe_id=" + logframeId;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> casesStudiesData = new HashMap<String, String>();
        casesStudiesData.put("id", rs.getString("id"));
        casesStudiesData.put("title", rs.getString("title"));
        casesStudiesData.put("author", rs.getString("author"));
        casesStudiesData.put("date", rs.getString("date"));
        casesStudiesData.put("photo", rs.getString("photo"));
        casesStudiesData.put("objectives", rs.getString("objectives"));
        casesStudiesData.put("description", rs.getString("description"));
        casesStudiesData.put("results", rs.getString("results"));
        casesStudiesData.put("partners", rs.getString("partners"));
        casesStudiesData.put("links", rs.getString("links"));
        casesStudiesData.put("keywords", rs.getString("keywords"));
        casesStudiesData.put("logframe_id", rs.getString("logframe_id"));
        casesStudiesDataList.add(casesStudiesData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO auto generated try catch block
      e.printStackTrace();
    }
    if (casesStudiesDataList.isEmpty()) {
      return null;
    }

    return casesStudiesDataList;
  }

  @Override
  public boolean removeAllStudyCases(int activityLeaderId, int logframeId) {
    try (Connection connection = databaseManager.getConnection()) {
      String deleteDeliverableQuery = "DELETE FROM case_studies WHERE activity_leader_id = ? AND logframe_id = ?";
      int rowsDeleted =
        databaseManager.makeChangeSecure(connection, deleteDeliverableQuery,
          new Object[] {activityLeaderId, logframeId});
      if (rowsDeleted >= 0) {
        return true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return false;
  }

  @Override
  public int saveCaseStudies(Map<String, Object> casesStudiesData) {
    int generatedId = -1;
    boolean problem = false;
    try (Connection con = databaseManager.getConnection()) {

      String preparedQuery =
        "INSERT INTO `case_studies` (`title`, `author`, `date`, `photo`, `objectives`, `description`, "
          + "`results`, `partners`, `links`, `keywords`, `logframe_id`, `activity_leader_id`) "
          + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      Object[] data = new Object[12];
      data[0] = casesStudiesData.get("title");
      data[1] = casesStudiesData.get("author");
      data[2] = casesStudiesData.get("date");
      data[3] = casesStudiesData.get("photo");
      data[4] = casesStudiesData.get("objectives");
      data[5] = casesStudiesData.get("description");
      data[6] = casesStudiesData.get("results");
      data[7] = casesStudiesData.get("partners");
      data[8] = casesStudiesData.get("links");
      data[9] = casesStudiesData.get("keywords");
      data[10] = casesStudiesData.get("logframe_id");
      data[11] = casesStudiesData.get("activity_leader_id");
      int rows = databaseManager.makeChangeSecure(con, preparedQuery, data);
      if (rows > 0) {
        // get the id assigned to the new record
        ResultSet rs = databaseManager.makeQuery("SELECT LAST_INSERT_ID()", con);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();
      }

    } catch (SQLException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
    }
    return generatedId;
  }
}
