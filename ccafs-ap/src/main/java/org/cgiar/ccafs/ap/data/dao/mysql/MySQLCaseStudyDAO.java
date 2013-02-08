package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.CaseStudyDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLCaseStudyDAO implements CaseStudyDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLCaseStudyDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getCaseStudyList(int activityLeaderId, int logframeId) {
    List<Map<String, String>> caseStudyDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT cs.id, cs.title, cs.author, cs.start_date, cs.end_date, cs.photo, cs.objectives, cs.description, cs.results, cs.partners, "
          + "cs.links, cs.keywords, cs.logframe_id " + "FROM case_studies cs " + "WHERE cs.activity_leader_id="
          + activityLeaderId + " AND logframe_id=" + logframeId;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> caseStudyData = new HashMap<String, String>();
        caseStudyData.put("id", rs.getString("id"));
        caseStudyData.put("title", rs.getString("title"));
        caseStudyData.put("author", rs.getString("author"));
        caseStudyData.put("start_date", rs.getString("start_date"));
        caseStudyData.put("end_date", rs.getString("end_date"));
        caseStudyData.put("photo", rs.getString("photo"));
        caseStudyData.put("objectives", rs.getString("objectives"));
        caseStudyData.put("description", rs.getString("description"));
        caseStudyData.put("results", rs.getString("results"));
        caseStudyData.put("partners", rs.getString("partners"));
        caseStudyData.put("links", rs.getString("links"));
        caseStudyData.put("keywords", rs.getString("keywords"));
        caseStudyData.put("logframe_id", rs.getString("logframe_id"));
        caseStudyDataList.add(caseStudyData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO auto generated try catch block
      e.printStackTrace();
    }
    return caseStudyDataList;
  }

  @Override
  public boolean removeAllCaseStudies(int activityLeaderId, int logframeId) {
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
  public int saveCaseStudy(Map<String, Object> caseStudyData) {
    int generatedId = -1;
    try (Connection con = databaseManager.getConnection()) {
      String preparedQuery =
        "INSERT INTO case_studies (id, title, author, start_date, end_date, photo, objectives, description, "
          + "results, partners, links, keywords, logframe_id, activity_leader_id) "
          + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

      Object[] data = new Object[14];
      data[0] = caseStudyData.get("id");
      data[1] = caseStudyData.get("title");
      data[2] = caseStudyData.get("author");
      data[3] = caseStudyData.get("start_date");
      data[4] = caseStudyData.get("end_date");
      data[5] = caseStudyData.get("photo");
      data[6] = caseStudyData.get("objectives");
      data[7] = caseStudyData.get("description");
      data[8] = caseStudyData.get("results");
      data[9] = caseStudyData.get("partners");
      data[10] = caseStudyData.get("links");
      data[11] = caseStudyData.get("keywords");
      data[12] = caseStudyData.get("logframe_id");
      data[13] = caseStudyData.get("activity_leader_id");
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
      return -1;
    }
    return generatedId;
  }
}
