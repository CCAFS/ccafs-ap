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

import org.cgiar.ccafs.ap.data.manager.impl.IPCrossCuttingManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * This class represents the Manager of IP Cross Cutting Theme
 * 
 * @author Javier Andrés Gallego B.
 */

@ImplementedBy(IPCrossCuttingManagerImpl.class)
public interface IPCrossCuttingManager {


  /**
   * This method removes a cross cutting element from a specific activity.
   * 
   * @param activityID - is the activity identifier.
   * @param crossCuttingID - is the cross cutting theme identifier.
   * @return true if the cross cutting record was successfully removed, false otherwise.
   */
  public boolean deleteCrossCuttingByActivity(int activityID, int crossCuttingID);

  /**
   * This method remove all the Cross Cutting records related by a given activity ID
   * 
   * @param activityID - is the activity identifier.
   * @return true if the cross cutting records was successfully removed, false otherwise.
   */
  public boolean deleteCrossCuttingsByActivity(int activityID);

  /**
   * This method gets the information of a IP Cross Cutting Theme by a given ID
   * 
   * @param ipCrossCuttingID - is the ID of a IP Cross Cutting Theme
   * @return an object with the information of a IP Cross Cutting Theme
   */
  public IPCrossCutting getIPCrossCutting(int ipCrossCuttingID);

  /**
   * This method gets all the information of IP Cross Cutting Themes related with a given activity ID
   * 
   * @param activityID - is the activity identifier.
   * @return a List with the information of IP Cross Cutting Themes related with the activity
   */
  public List<IPCrossCutting> getIPCrossCuttingByActivityID(int activityID);

  /**
   * This method gets all the IP Cross Cutting Themes
   * 
   * @return a List with the information of IP Cross Cutting Themes
   */
  public List<IPCrossCutting> getIPCrossCuttings();

  /**
   * This method saves a new Cross Cutting Element to a given project.
   * 
   * @param activityID is the activity identifier.
   * @param crossCuttingID is the cross cutting element identifier.
   * @return true if the cross cutting could be successfully saved, false otherwise.
   */
  public boolean saveCrossCutting(int activityID, int crossCuttingID);
}
