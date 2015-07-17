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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableFileManagerImpl;
import org.cgiar.ccafs.ap.data.model.DeliverableFile;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(DeliverableFileManagerImpl.class)
public interface DeliverableFileManager {

  /**
   * This method verifies if exists some deliverable file in the database with the
   * name received as parameter.
   * 
   * @param fileName
   * @return the identifier of the record in the database if exists. -1 Otherwise.
   */
  public int existsDeliverableFile(String fileName, int deliverableID);

  /**
   * This method get all the files related with the
   * deliverable identified with the value received a
   * 
   * @param deliverableID
   * @return
   */
  public List<DeliverableFile> getDeliverableFiles(int deliverableID);

  /**
   * This method remove from the deliverable file information from the database.
   * 
   * @param deliverableFileID - deliverable file identifier
   * @return true if the record was deleted successfully. False otherwise.
   */
  public boolean removeDeliverableFile(int deliverableFileID);

  /**
   * This method save the information of the deliverable files received
   * into the database.
   * 
   * @param file - Deliverable file object with the information.
   * @param deliverableID - deliverable identifier
   * @return the id of the record inserted in the database. -1 if any error occurred.
   */
  public int saveDeliverableFile(DeliverableFile file, int deliverableID);

  /**
   * This method save the information of the deliverable files received
   * into the database.
   * 
   * @param files - List of Deliverable file objects with the information.
   * @return true if the information was saved. False otherwise.
   */
  public boolean saveDeliverableFiles(List<DeliverableFile> files, int deliverableID);
}
