package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.PublicationDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLPublicationDAO implements PublicationDAO {

  private DAOManager dbManager;

  @Inject
  public MySQLPublicationDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public List<Map<String, String>> getPublications(int leaderId, int logframeId) {
    List<Map<String, String>> publications = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT p.id, p.identifier, p.citation, p.file_url, pt.id as 'publication_type_id', "
          + "pt.name as 'publication_type_name', oa.id as 'publication_access_id', oa.name as 'publication_access_name'"
          + "FROM publications p INNER JOIN publication_types pt ON pt.id = p.publication_type_id "
          + "LEFT JOIN open_access oa ON p.open_access_id = oa.id " + "WHERE activity_leader_id = " + leaderId
          + " AND logframe_id = " + logframeId;
      ResultSet rs = dbManager.makeQuery(query, connection);
      while (rs.next()) {
        Map<String, String> publicationData = new HashMap<>();
        publicationData.put("id", rs.getString("id"));
        publicationData.put("identifier", rs.getString("identifier"));
        publicationData.put("citation", rs.getString("citation"));
        publicationData.put("file_url", rs.getString("file_url"));
        publicationData.put("publication_type_id", rs.getString("publication_type_id"));
        publicationData.put("publication_type_name", rs.getString("publication_type_name"));
        publicationData.put("publication_access_id", rs.getString("publication_access_id"));
        publicationData.put("publication_access_name", rs.getString("publication_access_name"));
        publications.add(publicationData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return publications;
  }

  @Override
  public boolean removeAllPublications(int leaderId, int logframeId) {
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String removeQuery =
        "DELETE FROM publications WHERE activity_leader_id = " + leaderId + " AND logframe_id = " + logframeId;
      int rows = dbManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        // TODO Define some log error message.
        problem = true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return !problem;
  }

  @Override
  public int savePublication(Map<String, String> publication) {
    int generatedId = -1;
    try (Connection connection = dbManager.getConnection()) {
      String addQueryPrepared = null;
      Object[] values;
      addQueryPrepared =
        "INSERT INTO publications (id, publication_type_id, identifier, citation, file_url, logframe_id, "
          + "activity_leader_id, open_access_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
      values = new Object[8];
      values[0] = publication.get("id");
      values[1] = publication.get("publication_type_id");
      values[2] = publication.get("identifier");
      values[3] = publication.get("citation");
      values[4] = publication.get("file_url");
      values[5] = publication.get("logframe_id");
      values[6] = publication.get("activity_leader_id");
      values[7] = publication.get("open_access_id");

      int rows = dbManager.makeChangeSecure(connection, addQueryPrepared, values);
      if (rows > 0) {
        // get the id assigned to this new record.
        ResultSet rs = dbManager.makeQuery("SELECT LAST_INSERT_ID()", connection);
        if (rs.next()) {
          generatedId = rs.getInt(1);
        }
        rs.close();
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return generatedId;
  }
}
