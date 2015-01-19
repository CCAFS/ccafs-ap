/*****************************************************************
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
 *****************************************************************/

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableFileDAO;

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


/**
 * @author Hernán David Carvajal
 */

public class MySQLDeliverableFileDAO implements DeliverableFileDAO {

  private DAOManager daoManager;
  public static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableFileDAO.class);

  @Inject
  public MySQLDeliverableFileDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getDeliverableFiles(int deliverableID) {
    LOG.debug(">> getDeliverableFiles(deliverableID={})", deliverableID);
    List<Map<String, String>> deliverableFiles = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM deliverable_files ");
    query.append("WHERE deliverable_id = ");
    query.append(deliverableID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> fileData = new HashMap<>();
        fileData.put("id", rs.getString("id"));
        fileData.put("filename", rs.getString("filename"));
        fileData.put("link", rs.getString("link"));
        fileData.put("hosted", rs.getString("hosted"));

        deliverableFiles.add(fileData);
      }

    } catch (SQLException e) {
      LOG.debug("-- getDeliverableFiles() : Exception rised trying to get the files of the deliverable {}.", e);
    }

    return deliverableFiles;
  }

  @Override
  public boolean removeDeliverableFile(int deliverableFileID) {
    LOG.debug(">> removeDeliverableFile(deliverableFileID={})", deliverableFileID);
    boolean removed = false;
    StringBuilder query = new StringBuilder();
    query.append("DELETE FROM deliverable_files WHERE id = ");
    query.append(deliverableFileID);

    try (Connection con = daoManager.getConnection()) {
      int rowsDeleted = daoManager.makeChange(query.toString(), con);
      if (rowsDeleted < 0) {
        LOG.debug("-- removeDeliverableFile():There was an error trying to remove the deliverable file.");
      } else {
        removed = true;
      }
    } catch (SQLException e) {
      LOG.debug("-- removeDeliverableFile():There was an exception trying to remove the deliverable file.", e);
    }

    LOG.debug("<< removeDeliverableFile():{}", removed);
    return removed;
  }

  @Override
  public int saveDeliverableFile(Map<String, Object> fileData) {
    LOG.debug(">> saveDeliverableFile(fileData={})", fileData);
    int rowID = -1;
    StringBuilder query = new StringBuilder();

    Object[] values = new Object[fileData.size()];
    values[0] = fileData.get("filename");
    values[1] = fileData.get("hosted");
    values[2] = fileData.get("link");
    values[3] = fileData.get("filesize");
    values[4] = fileData.get("deliverable_id");
    values[5] = fileData.get("id");

    int deliverableID = Integer.parseInt(fileData.get("deliverable_id") + "");
    query.append("SELECT * FROM deliverable_files WHERE id = ");
    query.append(deliverableID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        // If record exists update
        query.setLength(0);
        query.append("UPDATE deliverableFile SET filename = ?, hosted = ?, ");
        query.append("link=?, filesize=? deliverable_id = ? WHERE id = ? ");

        int updatedRows = daoManager.makeChangeSecure(con, query.toString(), values);
        rowID = Integer.parseInt(fileData.get("id") + "");
      } else {
        // If record not exists insert it
        query.setLength(0);
        query.append("INSERT INTO deliverable_files (filename, hosted, link, filesize, deliverable_id, id) ");
        query.append("VALUES (?, ?, ?, ?, ?, ?) ");

        int insertedRows = daoManager.makeChangeSecure(con, query.toString(), values);
        query.setLength(0);
        query.append("SELECT LAST_INSERT_ID() as 'ID' FROM deliverable_files ");
        rs = daoManager.makeQuery(query.toString(), con);

        rs.next();
        rowID = rs.getInt("id");
      }
    } catch (SQLException e) {
      LOG.error("-- saveDeliverableFile() : There was an exception saving the information of the deliverable file.", e);
    }


    LOG.debug("<< saveDeliverableFile():{}", rowID);
    return rowID;
  }
}
