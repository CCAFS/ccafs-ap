package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.FileFormatDAO;

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


public class MySQLFileFormatDAO implements FileFormatDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLFileFormatDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLFileFormatDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getFileFormats() {
    List<Map<String, String>> fileFormatsList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * from file_formats";
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> fileFormatsData = new HashMap();
        fileFormatsData.put("id", rs.getString("id"));
        fileFormatsData.put("name", rs.getString("name"));
        fileFormatsList.add(fileFormatsData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return fileFormatsList;
  }

  @Override
  public List<Map<String, String>> getFileFormats(int deliverableID) {
    List<Map<String, String>> fileFormatsList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT ff.id, ff.name FROM file_formats ff "
          + "INNER JOIN deliverable_formats df ON df.file_format_id = ff.id " + "WHERE df.deliverable_id="
          + deliverableID;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> fileFormatsData = new HashMap();
        fileFormatsData.put("id", rs.getString("id"));
        fileFormatsData.put("name", rs.getString("name"));
        fileFormatsList.add(fileFormatsData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

    if (fileFormatsList.isEmpty()) {
      return null;
    } else {
      return fileFormatsList;
    }
  }

  @Override
  public boolean setFileFormats(int deliverableId, int[] fileFormatIds) {
    boolean problem = false;
    try (Connection connection = databaseManager.getConnection()) {
      String preparedRemoveQuery = "DELETE FROM deliverable_formats WHERE deliverable_id = ?";
      int rowsDeleted = databaseManager.makeChangeSecure(connection, preparedRemoveQuery, new Object[] {deliverableId});
      if (rowsDeleted >= 0) {
        for (int c = 0; c < fileFormatIds.length; c++) {
          String preparedInsertQuery = "INSERT INTO deliverable_formats (deliverable_id, file_format_id) VALUES (?, ?)";
          int rowsInserted =
            databaseManager.makeChangeSecure(connection, preparedInsertQuery, new Object[] {deliverableId,
              fileFormatIds[c]});
          if (rowsInserted < 0) {
            problem = true;
          }
        }
      } else {
        problem = true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return !problem;
  }
}
