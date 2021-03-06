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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLMetadataDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(MySQLMetadataDAO.class)
public interface MetadataDAO {

  /**
   * This method return the information of all the metadata elements
   * present in the database.
   * 
   * @return a list of map objects with the information.
   */
  public List<Map<String, String>> getMetadataList();

  /**
   * This method gets all the metadata required according
   * to the deliverable type received as parameter.
   * 
   * @param deliverableTypeID - deliverable type identifier
   * @return a list of maps with the information of the metadata.
   */
  public List<Map<String, String>> getRequiredMetadata(int deliverableTypeID);
}
