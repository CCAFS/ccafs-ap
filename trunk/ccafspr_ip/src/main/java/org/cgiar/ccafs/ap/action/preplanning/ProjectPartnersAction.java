/*****************************************************************
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
 *****************************************************************/
package org.cgiar.ccafs.ap.action.preplanning;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.action.BaseAction;
import com.google.inject.Inject;
import org.cgiar.ccafs.ap.config.APConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the Project Partners section in the pre-planning step.
 *
 * @author Héctor Tobón
 */
public class ProjectPartnersAction extends BaseAction {

  private static final long serialVersionUID = -2678924292464949934L;

  public static Logger LOG = LoggerFactory.getLogger(ProjectPartnersAction.class);

  // TODO - Create Managers and assign them to the constructor below.

  // Model
  private int projectId;
  private List<Institution> partners;
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;
  private Institution leader;
  // allPartners will be used to list all the partners that have the system.
  private List<Institution> allPartners;

  @Inject
  public ProjectPartnersAction(APConfig config) {
    super(config);
    // TODO Managers.
  }

  // TODO - Temporal - To be removed!
  private List<Country> getAllCountries() {
    ArrayList<Country> countries = new ArrayList<Country>();
    for (int c = 1; c <= 30; c++) {
      Country co = new Country(RandomStringUtils.randomAlphabetic(2), RandomStringUtils.randomAlphabetic(10));
      // Country co = new Country(new Random().nextInt(1000) + "", RandomStringUtils.randomAlphabetic(10));
      if (!countries.contains(co)) {
        countries.add(co);
      } else {
        System.out.println("ya existe " + co);
      }
    }
    countries.add(new Country("coo", "Colombia"));
    return countries;
  }

  public List<Institution> getAllPartners() {
    return allPartners;
  }

  // TODO - Temporal - To be removed!
  private List<Institution> getAllPartnersTemporal(Random rand, List<Country> countries, List<InstitutionType> types) {
    ArrayList<Institution> partners = new ArrayList<Institution>();
    for (int c = 1; c <= 100; c++) {
      Institution inst1 = new Institution();
      inst1.setId(c);
      inst1.setName("Partner" + c);
      inst1.setContactPersonEmail("email" + c + "@email.com");
      inst1.setContactPersonName("Jhon Doe " + c);
      if (c == 1) {
        inst1.setCountry(countries.get(countries.size() - 1));
      } else {
        inst1.setCountry(countries.get(rand.nextInt(countries.size())));
      }
      inst1.setType(types.get(rand.nextInt(types.size())));
      partners.add(inst1);
    }

    return partners;
  }

  // TODO - Temporal - To be removed!
  private List<InstitutionType> getAllPartnerTypes() {
    ArrayList<InstitutionType> types = new ArrayList<InstitutionType>();
    for (int c = 1; c <= 20; c++) {
      types.add(new InstitutionType(c, "Type Name " + c, "Acronym "));
    }
    return types;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public Institution getLeader() {
    return leader;
  }

  public List<Institution> getPartners() {
    return partners;
  }

  public List<InstitutionType> getPartnerTypes() {
    return partnerTypes;
  }

  public int getProjectId() {
    return projectId;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // partners = projectPartnerManager.getPartners(projectId);

    // ***********FAKE OBJECTS JUST TO TEST!******************
    Random rand = new Random();

    // All Countries
    countries = this.getAllCountries();

    // All Partner Types
    partnerTypes = this.getAllPartnerTypes();

    // All Partners.
    allPartners = this.getAllPartnersTemporal(rand, countries, partnerTypes);

    // Project leader.
    leader = allPartners.get(rand.nextInt(100));

    // Saved Partners.
    partners = new ArrayList<Institution>();

    for (int c = 0; c < 10; c++) {
      if (c == 0) {
        partners.add(allPartners.get(0));
      } else {
        Institution inst = allPartners.get(rand.nextInt(100));
        if (!partners.contains(inst)) {
          partners.add(inst);
        } else {
          c--;
        }
      }
    }
    // **************************************


  }


}
