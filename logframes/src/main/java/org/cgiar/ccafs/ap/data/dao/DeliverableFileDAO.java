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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableFileDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(MySQLDeliverableFileDAO.class)
public interface DeliverableFileDAO {

  /**
   * This method verifies if exists some deliverable file in the database with the
   * name received as parameter.
   * 
   * @param fileName
   * @return the identifier of the record in the database if exists. -1 Otherwise.
   */
  public int existsDeliverableFile(String fileName, int deliverableID);

  /**
   * Get all the deliverable files related to the deliverable
   * identified by the value received as parameter.
   * 
   * @param deliverableID - deliverable identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getDeliverableFiles(int deliverableID);

  /**
   * This method remove from the deliverable file information from the database.
   * 
   * @param deliverableFileID - deliverable file identifier
   * @return true if the record was deleted successfully. False otherwise.
   */
  public boolean removeDeliverableFile(int deliverableFileID);

  /**
   * This method save in the database the information of the file
   * received.
   * 
   * @param fileData - Map with the information
   * @return the id of the record inserted, 0 if the record was
   *         updated or -1 if any error occurred.
   */
  public int saveDeliverableFile(Map<String, Object> fileData);

}
