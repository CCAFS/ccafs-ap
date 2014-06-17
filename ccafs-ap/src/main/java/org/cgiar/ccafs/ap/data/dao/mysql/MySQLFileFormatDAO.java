/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.FileFormatDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    LOG.debug(">> addFileFormats(deliverableId={}, fileFormatsIds={})", deliverableId, fileFormatsIds);
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
      LOG.error("-- addFileFormats() > There was an error saving the formats asociated with the deliverable {}.",
        deliverableId, e);
    }
    LOG.debug("<< addFileFormats():{}", problem);
    return !problem;
  }

  @Override
  public List<Map<String, String>> getFileFormats() {
    LOG.debug(">> getFileFormats()");
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
      LOG.error("-- getFileFormats() > There was an error getting the file formats list. \n{}", query, e);
    }
    LOG.debug("<< getFileFormats():fileFormatsList.size={}", fileFormatsList.size());
    return fileFormatsList;
  }

  @Override
  public List<Map<String, String>> getFileFormats(int deliverableID) {
    LOG.debug(">> getFileFormats(deliverableID={})", deliverableID);
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
      LOG.error("-- getFileFormats() > There was an error getting the file formats related to deliverable {}.",
        deliverableID, e);

      LOG.debug("<< getFileFormats():null");
      return null;
    }

    if (fileFormatsList.isEmpty()) {
      LOG.debug("<< getFileFormats():null");
      return null;
    }

    LOG.debug("<< getFileFormats():fileFormatsList.size={}", fileFormatsList.size());
    return fileFormatsList;
  }

  @Override
  public boolean setFileFormats(int deliverableId, int[] fileFormatIds) {
    LOG.debug(">> setFileFormats(deliverableId={}, fileFormatIds={})", deliverableId, Arrays.toString(fileFormatIds));
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
            LOG
              .warn(
                "-- setFileFormats() > There wasn't posible save the file formats list for deliverable {} into the database",
                deliverableId);
            problem = true;
          }
        }
      } else {
        LOG
          .warn(
            "-- setFileFormats() > There was a problem removing the deliverables formats that belongs to deliverable {} from the database",
            deliverableId);
        problem = true;
      }
    } catch (SQLException e) {
      LOG.error("-- setFileFormats() > There was an error saving the file formats related to deliverable {}.",
        deliverableId, e);
    }
    LOG.debug("<< setFileFormats():{}", !problem);
    return !problem;
  }
}
