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

import org.cgiar.ccafs.ap.data.manager.impl.ContactPersonManagerImpl;
import org.cgiar.ccafs.ap.data.model.ContactPerson;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ContactPersonManagerImpl.class)
public interface ContactPersonManager {

  /**
   * Get all the contact persons of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return a set of ContactPerson objects.
   */
  public List<ContactPerson> getContactPersons(int activityID);

  /**
   * Save the contact persons for the given activity
   * 
   * @param contactPersons
   * @param activityID
   * @return true if ALL contact persons was saved successfully, false otherwise.
   */
  public boolean saveContactPersons(List<ContactPerson> contactPersons, int activityID);
}
