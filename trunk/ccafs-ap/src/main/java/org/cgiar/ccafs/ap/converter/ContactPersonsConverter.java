package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.model.ContactPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class ContactPersonsConverter extends StrutsTypeConverter {

  private ContactPersonManager contactPersonManager;

  @Inject
  public ContactPersonsConverter(ContactPersonManager contactPersonManager) {
    this.contactPersonManager = contactPersonManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    ContactPerson[] cps = new ContactPerson[values.length];
    return cps;
  }

  @Override
  public String convertToString(Map context, Object o) {
    List<ContactPerson> contactPersonArray = (List<ContactPerson>) o;
    ArrayList<String> temp = new ArrayList<>();
    for (ContactPerson cp : contactPersonArray) {
      temp.add(String.valueOf(cp.getId()));
    }
    // TODO
    return temp.toString();
  }
}
