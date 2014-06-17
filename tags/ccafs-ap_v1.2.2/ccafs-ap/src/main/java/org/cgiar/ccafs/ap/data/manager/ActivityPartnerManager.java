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

import org.cgiar.ccafs.ap.data.manager.impl.ActivityPartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ActivityPartnerManagerImpl.class)
public interface ActivityPartnerManager {


  /**
   * Get a list of activity partners that belong to a specified activity.
   * 
   * @param activityID - activity identifier.
   * @return a list of ActivityPartner objects.
   */
  public List<ActivityPartner> getActivityPartners(int activityID);

  /**
   * Remove all activity parters that belong to a specified activity.
   * 
   * @param activityID - Activity identifier
   * @return true if the remove process was successfully made, false otherwise.
   */
  public boolean removeActivityPartners(int activityID);

  /**
   * Save into the DAO the list of activity partners.
   * 
   * @param activityPartners - List of ActivityPartner objects.
   * @param activityID - Activity identifier.
   * @return true if the list was successfully saved, false otherwise.
   */
  public boolean saveActivityPartners(List<ActivityPartner> activityPartners, int activityID);

}
