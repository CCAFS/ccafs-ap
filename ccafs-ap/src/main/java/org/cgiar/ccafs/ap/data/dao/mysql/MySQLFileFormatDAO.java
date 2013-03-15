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
  public boolean addFileFormats(int deliverableId, ArrayList<String> fileFormatsIds) {
    boolean problem = false;
    try (Connection connection = databaseManager.getConnection()) {
      String addQuery = "INSERT INTO deliverable_formats (deliverable_id, file_format_id) VALUES ";
      for (int c = 0; c < fileFormatsIds.size(); c++) {
        if (c != 0) {
          addQuery += ", ";
        }
        addQuery += "(" + deliverableId + ", ?)";
      }
      Object[] values = new Object[fileFormatsIds.size()];
      for (int c = 0; c < values.length; c++) {
        values[c] = fileFormatsIds.get(c);
      }
      int rows = databaseManager.makeChangeSecure(connection, addQuery, values);
      if (rows <= 0) {
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("There was an error saving data into 'deliverable_formats' table.", e);
    }
    return !problem;
  }

  @Override
  public List<Map<String, String>> getFileFormats() {
    List<Map<String, String>> fileFormatsList = new ArrayList<>();
    String query = "SELECT * from file_formats";
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> fileFormatsData = new HashMap<>();
        fileFormatsData.put("id", rs.getString("id"));
        fileFormatsData.put("name", rs.getString("name"));
        fileFormatsList.add(fileFormatsData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the file formats list. \n{}", query, e);
    }
    return fileFormatsList;
  }

  @Override
  public List<Map<String, String>> getFileFormats(int deliverableID) {
    List<Map<String, String>> fileFormatsList = new ArrayList<>();
    String query =
      "SELECT ff.id, ff.name FROM file_formats ff " + "INNER JOIN deliverable_formats df ON df.file_format_id = ff.id "
        + "WHERE df.deliverable_id=" + deliverableID;
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> fileFormatsData = new HashMap<>();
        fileFormatsData.put("id", rs.getString("id"));
        fileFormatsData.put("name", rs.getString("name"));
        fileFormatsList.add(fileFormatsData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an error getting the file formats related to a deliverable. \n{}", query, e);
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
        for (int fileFormatId : fileFormatIds) {
          String preparedInsertQuery = "INSERT INTO deliverable_formats (deliverable_id, file_format_id) VALUES (?, ?)";
          int rowsInserted =
            databaseManager.makeChangeSecure(connection, preparedInsertQuery,
              new Object[] {deliverableId, fileFormatId});
          if (rowsInserted < 0) {
            LOG.warn("There wasn't posible save the file formats list into the database");
            problem = true;
          }
        }
      } else {
        LOG.warn(
          "There was a problem removing the deliverables formats that belongs to deliverable {} from the database",
          deliverableId);
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("There was an error saving the file formats related to a deliverable.", e);
    }
    return !problem;
  }
}
