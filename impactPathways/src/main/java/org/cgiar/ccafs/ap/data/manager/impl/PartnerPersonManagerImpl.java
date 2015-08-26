/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.PartnerPersonDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerPersonManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Héctor Fabio Tobón R.
 */
public class PartnerPersonManagerImpl implements PartnerPersonManager {

  // LOG
  public static Logger LOG = LoggerFactory.getLogger(PartnerPersonManagerImpl.class);

  // DAO's
  private PartnerPersonDAO partnerPersonDAO;

  // Managers
  private UserManager userManager;

  @Inject
  public PartnerPersonManagerImpl(UserManager userManager, PartnerPersonDAO partnerPersonDAO) {
    this.userManager = userManager;
    this.partnerPersonDAO = partnerPersonDAO;
  }

  @Override
  public PartnerPerson getPartnerPerson(int partnerPersonID) {
    Map<String, String> personsData = partnerPersonDAO.getPartnerPerson(partnerPersonID);
    if (personsData.size() > 0) {
      PartnerPerson person = new PartnerPerson();
      person.setId(Integer.parseInt(personsData.get("id")));
      person.setType(personsData.get("contact_type"));
      person.setUser(userManager.getUser(Integer.parseInt(personsData.get("user_id"))));
      person.setResponsibilities(personsData.get("responsibilities"));
      return person;
    }
    return null;
  }

  @Override
  public List<PartnerPerson> getPartnerPersons(ProjectPartner projectPartner) {
    List<PartnerPerson> persons = new ArrayList<>();
    List<Map<String, String>> personsDataList = partnerPersonDAO.getPartnerPersonsByPartnerID(projectPartner.getId());
    for (Map<String, String> personsData : personsDataList) {
      PartnerPerson person = new PartnerPerson();
      person.setId(Integer.parseInt(personsData.get("id")));
      person.setType(personsData.get("contact_type"));
      person.setUser(userManager.getUser(Integer.parseInt(personsData.get("user_id"))));
      person.setResponsibilities(personsData.get("responsibilities"));
      // adding information of the object to the array
      persons.add(person);
    }
    return persons;
  }


}
