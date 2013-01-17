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
        "SELECT p.id, p.identifier, p.citation, pt.id as 'publication_type_id', pt.name as 'publication_type_name' "
          + "FROM publications p INNER JOIN publication_types pt ON pt.id = p.publication_type_id "
          + "WHERE activity_leader_id = " + leaderId + " AND logframe_id = " + logframeId;
      ResultSet rs = dbManager.makeQuery(query, connection);
      while (rs.next()) {
        Map<String, String> publicationData = new HashMap<>();
        publicationData.put("id", rs.getString("id"));
        publicationData.put("identifier", rs.getString("identifier"));
        publicationData.put("citation", rs.getString("citation"));
        publicationData.put("publication_type_id", rs.getString("publication_type_id"));
        publicationData.put("publication_type_name", rs.getString("publication_type_name"));
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
  public boolean savePublications(List<Map<String, String>> publications) {
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String addQueryPrepared = null;
      Object[] values;
      for (Map<String, String> publicationData : publications) {
        addQueryPrepared =
          "INSERT INTO publications (publication_type_id, identifier, citation, logframe_id, activity_leader_id) VALUES (?, ?, ?, ?, ?)";
        values = new Object[5];
        values[0] = publicationData.get("publication_type_id");
        values[1] = publicationData.get("identifier");
        values[2] = publicationData.get("citation");
        values[3] = publicationData.get("logframe_id");
        values[4] = publicationData.get("activity_leader_id");

        int rows = dbManager.makeChangeSecure(connection, addQueryPrepared, values);
        if (rows < 0) {
          // TODO Make a log error message.
          problem = true;
        }
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    return !problem;
  }
}
