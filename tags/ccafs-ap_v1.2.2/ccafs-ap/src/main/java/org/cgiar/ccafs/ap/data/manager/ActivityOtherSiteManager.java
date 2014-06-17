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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ActivityOtherSiteManagerImpl;
import org.cgiar.ccafs.ap.data.model.OtherSite;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityOtherSiteManagerImpl.class)
public interface ActivityOtherSiteManager {

  /**
   * Delete the other sites related with the activity given.
   * 
   * @param activityID - The activity identifier
   * @return true if it was successfully deleted. False otherwise.
   */
  public boolean deleteActivityOtherSites(int activityID);

  /**
   * Get all the other sites related to the activity given
   * 
   * @param activityID - the activity identifier
   * @return a list of OtherSite objects with the information
   */
  public List<OtherSite> getActivityOtherSites(int activityID);

  /**
   * Save the list of other sites into the DAO
   * 
   * @param otherSites - The data to be saved
   * @param activityID - The activity identifier
   * @return true if ALL the otherSites were successfully saved. False otherwise.
   */
  public boolean saveActivityOtherSites(List<OtherSite> otherSites, int activityID);
}
