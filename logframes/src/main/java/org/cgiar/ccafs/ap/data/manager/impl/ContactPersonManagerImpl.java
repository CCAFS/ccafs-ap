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
