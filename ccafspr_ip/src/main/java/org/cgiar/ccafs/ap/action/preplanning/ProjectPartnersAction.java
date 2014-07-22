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

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.Project;
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

  // Managers
  private ProjectPartnerManager projectPartnerManager;
  private InstitutionManager institutionManager;

  // Model for the backend
  private int projectId;
  private Project project;

  // Model for the view
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;
  private List<Institution> allPartners; // allPartners will be used to list all the partners that have the system.

  @Inject
  public ProjectPartnersAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager) {
    super(config);
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
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

  public List<InstitutionType> getPartnerTypes() {
    return partnerTypes;
  }


  public Project getProject() {
    return project;
  }

  public int getProjectId() {
    return projectId;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Creating a project
    project = new Project();
    project.setId(123);

    // if there are not partners, please return an empty List.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(projectId));

    // ***********FAKE OBJECTS JUST TO TEST!******************
    Random rand = new Random();


    // All Countries
    countries = this.getAllCountries();

    // All Partner Types
    partnerTypes = this.getAllPartnerTypes();

    // All Partners.
    allPartners = institutionManager.getAllInstitutions();
    // this.getAllPartnersTemporal(rand, countries, partnerTypes);

    // Project leader.
    ProjectPartner pp = new ProjectPartner();
    pp.setId(123);
    pp.setPartner(allPartners.get(rand.nextInt(5)));
    pp.setContactEmail("pp_email@email.com");
    pp.setContactName("Contact Name PP");
    pp.setResponsabilities(RandomStringUtils.randomAlphabetic(50));
    // project.setLeader(pp);

    // Saved Project Partners.
    ArrayList<ProjectPartner> projectPartners = new ArrayList<ProjectPartner>();

// for (int c = 0; c < 3; c++) {
// ProjectPartner ppTemp = new ProjectPartner();
// ppTemp.setId(c + 1000);
// ppTemp.setContactEmail("projectPartner" + c + "@email.com");
// ppTemp.setContactName("Project Partner Name " + c);
// ppTemp.setResponsabilities(RandomStringUtils.randomAlphabetic(50));
// if (c == 0) {
// ppTemp.setPartner(allPartners.get(0));
// } else {
// ppTemp.setPartner(allPartners.get(rand.nextInt(100)));
// }
// projectPartners.add(ppTemp);
// }
    project.setProjectPartners(projectPartners);
    // **************************************
  }

  @Override
  public String save() {

    // The following is the project leader.
    System.out.println(project.getLeader());

    List<ProjectPartner> previousProjectPartners = projectPartnerManager.getProjectPartners(projectId);

    // The following are the project partners to ADD
    for (ProjectPartner projectPartner : previousProjectPartners) {

    }


    return INPUT;
  }
}
