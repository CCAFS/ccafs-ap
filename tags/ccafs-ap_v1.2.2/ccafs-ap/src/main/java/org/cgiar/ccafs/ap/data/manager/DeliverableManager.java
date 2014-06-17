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

import org.cgiar.ccafs.ap.data.manager.impl.DeliverableManagerImpl;
import org.cgiar.ccafs.ap.data.model.Deliverable;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(DeliverableManagerImpl.class)
public interface DeliverableManager {


  /**
   * Add a deliverable to the database which its corresponding file formats.
   * 
   * @param deliverable - Deliverable object.
   * @param activityID - Activity identifier in which the deliverable belongs to.
   * @return true y the deliverable was successfully added, false otherwise.
   */
  public boolean addDeliverable(Deliverable deliverable, int activityID);

  /**
   * Get all the deliverables objects belongs to an activity identified
   * with the given id.
   * 
   * @param id
   * @return a Deliverables array or null if no deliverables was found.
   */
  public List<Deliverable> getDeliverables(int activityId);

  /**
   * Remove all those expected deliverables that belongs to the specified activity id.
   * 
   * @param activityID - activity identifier.
   * @return true if all the not expected deliverables were removed, or false if any problem occur.
   */
  public boolean removeExpected(int activityID);

  /**
   * Remove all those not expected deliverables that belongs to the specified activity id.
   * 
   * @param activityID - activity identifier.
   * @return true if all the not expected deliverables were removed, or false if any problem occur.
   */
  public boolean removeNotExpected(int activityID);

  /**
   * Save into the DAO the list of the deliverables and its file formats associated with a specific activity.
   * 
   * @param deliverables - A list of Deliverable objects representing the information that is going to be added into the
   *        DAO.
   * @param activityID - An integer representing the id of the activity that will contain those deliverable list.
   * @return true if the list of the deliverables was successfully added into the DAO, or false if any problem occur.
   */
  public boolean saveDeliverables(List<Deliverable> deliverables, int activityID);
}
