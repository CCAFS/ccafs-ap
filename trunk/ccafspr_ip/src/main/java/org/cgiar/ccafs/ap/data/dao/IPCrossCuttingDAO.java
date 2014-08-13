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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPCrossCuttingDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego.
 */
@ImplementedBy(MySQLIPCrossCuttingDAO.class)
public interface IPCrossCuttingDAO {

  /**
   * This method removes a record from the table IP cross cutting by a given activity ID and a IP Cross Cutting.
   * 
   * @param activityID is the activity ID.
   * @param crossCuttingID is the theme_id
   * @return true if the record could be successfully removed, false otherwise.
   */
  public boolean deleteCrossCuttingsByActivityId(int activityID, int crossCuttingID);

  /**
   * This Method return the information of a IP Cross Cutting Theme by a given ID of a Theme
   * 
   * @param ipCrossCuttingID - is the Id of a IP Cross Cutting Theme
   * @return a Map with the information of a IP Cross Cutting Theme
   */
  public Map<String, String> getIPCrossCutting(int ipCrossCuttingID);

  /**
   * This method return the information of a IP Cross Cutting Theme by a given activity ID
   * 
   * @param activityID
   * @return a List of Map with the information of IP Cross Cutting Themes
   */
  public List<Map<String, String>> getIPCrossCuttingByActivityId(int activityID);

  /**
   * This method return a all the IP Cross Cutting Themes
   * 
   * @return a list of maps with the information of all IP Cross Cutting Themes.
   */
  public List<Map<String, String>> getIPCrossCuttings();

  /**
   * This method saves a new record in the project_cross_cutting_themes table.
   * 
   * @param elementData is a Map with the project_id and theme_id.
   * @return true if the record could successfully inserted, false otherwise.
   */
  public boolean saveCrossCutting(Map<String, Object> elementData);

}
