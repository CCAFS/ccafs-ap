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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ContactPersonDAO;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.model.ContactPerson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ContactPersonManagerImpl implements ContactPersonManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ContactPersonManagerImpl.class);
  private ContactPersonDAO contactPersonDAO;

  @Inject
  public ContactPersonManagerImpl(ContactPersonDAO contactPersonDAO) {
    this.contactPersonDAO = contactPersonDAO;
  }

  @Override
  public List<ContactPerson> getContactPersons(int activityID) {
    List<Map<String, String>> contactPersonsDB = contactPersonDAO.getContactPersons(activityID);
    List<ContactPerson> contactPersons = new ArrayList<>();

    for (int c = 0; c < contactPersonsDB.size(); c++) {
      ContactPerson cp = new ContactPerson();
      cp.setId(Integer.parseInt(contactPersonsDB.get(c).get("id")));
      cp.setEmail(contactPersonsDB.get(c).get("email"));
      cp.setName(contactPersonsDB.get(c).get("name"));
      contactPersons.add(cp);
    }

    return contactPersons;
  }

  @Override
  public boolean saveContactPersons(List<ContactPerson> contactPersons, int activityID) {
    boolean saved = true;
    // First delete all the contact persons related to the activity
    contactPersonDAO.deleteContactPersons(activityID);

    // Then save the contact persons one by one
    for (ContactPerson contactPerson : contactPersons) {
      Map<String, String> cpData = new HashMap<>();
      if (contactPerson.getId() == -1) {
        cpData.put("id", null);
      } else {
        cpData.put("id", String.valueOf(contactPerson.getId()));
      }
      cpData.put("name", contactPerson.getName());
      cpData.put("email", contactPerson.getEmail());
      if (!contactPersonDAO.saveContactPersons(cpData, activityID)) {
        saved = false;
        LOG.warn("There was a problem saving the contact person ({}, {}) for activity {}",
          new Object[] {contactPerson.getName(), contactPerson.getEmail(), activityID});
      } else {
        LOG.debug("The contact person ({}, {}) for activity {} was successfully saved.",
          new Object[] {contactPerson.getName(), contactPerson.getEmail(), activityID});
      }
    }
    return saved;
  }
}
