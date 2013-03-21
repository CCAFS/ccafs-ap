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
}
