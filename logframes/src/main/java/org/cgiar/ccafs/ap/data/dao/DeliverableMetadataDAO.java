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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableMetadataDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(MySQLDeliverableMetadataDAO.class)
public interface DeliverableMetadataDAO {

  /**
   * This method get from the database all the metadata related to the
   * deliverable identified by the value received as parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return a map that contains the metadata of the deliverable
   */
  public List<Map<String, String>> getDeliverableMetadata(int deliverableID);

  /**
   * This method save in the database the deliverable metadata information.
   * 
   * @param deliverableMetadata - Map containing the information
   * @return true if all the information was saved successfully.
   */
  public boolean saveDeliverableMetadata(Map<String, Object> deliverableMetadata);
}
