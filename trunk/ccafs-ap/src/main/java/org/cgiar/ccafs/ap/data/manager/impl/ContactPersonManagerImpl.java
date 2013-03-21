package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ContactPersonDAO;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.model.ContactPerson;

import java.util.ArrayList;
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

    if (contactPersonsDB.size() == 0) {
      return null;
    }
    return contactPersons;
  }

}
