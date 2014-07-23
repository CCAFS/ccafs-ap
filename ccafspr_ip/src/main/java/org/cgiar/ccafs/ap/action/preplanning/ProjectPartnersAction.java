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

import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.config.APConstants;
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
  private ProjectManager projectManager;
  private UserManager userManager;

  // Model for the backend
  private int projectId;
  private Project project;

  // Model for the view
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;
  private List<Institution> allPartners; // will be used to list all the partners that have the system.
  private List<User> allProjectLeaders; // will be used to list all the project leaders that have the system.

  @Inject
  public ProjectPartnersAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager, ProjectManager projectManager, UserManager userManager) {
    super(config);
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.projectManager = projectManager;
    this.userManager = userManager;
  }


  @Override
  public String execute() throws Exception {
    if (projectId == -1) {
      return NOT_FOUND;
    }
    return super.execute();
  }

  public List<Institution> getAllPartners() {
    return allPartners;
  }

  public List<User> getAllProjectLeaders() {
    return allProjectLeaders;
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

    // Getting the project id from the URL parameter
    try {
      projectId = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectId, e);
      projectId = -1;
      return; // Stopping here!
    }

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectId);
    // if there is not a project identified with the given id
    if (project == null) {
      return;
    }

    // if there are not partners, please return an empty List.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(projectId));

    // Getting all partners.
    allPartners = institutionManager.getAllInstitutions();

    // ***********FAKE OBJECTS JUST TO TEST!******************
    // Getting all countries
    countries = this.temporalGetAllCountries();

    // Getting all partner types
    partnerTypes = this.temporalGetAllPartnerTypes();

    // Getting all Project Leaders
    allProjectLeaders = this.temporalGetAllProjectLeaders();

    // allPartners = this.getAllPartnersTemporal(rand, countries, partnerTypes);

    // Getting the project partner leader.
    // TODO When the method is developed we will be able to use it -
    project.setLeader(allProjectLeaders.get(10));
    project.setLeaderResponsabilities("My responsabilities are.....");

    // **************************************

    // project.setLeader(userManager.getProjectLeader(projectId));
    // In case there is not a partner leader defined, an empty partner will be used for the view.
    if (project.getLeader() == null) {
      User projectLeader = new User();
      projectLeader.setId(-1);
      project.setLeader(projectLeader);
    }

    // Saved Project Partners.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(projectId));

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

  public void setAllProjectLeaders(List<User> allProjectLeaders) {
    this.allProjectLeaders = allProjectLeaders;
  }

  // TODO - Temporal - To be removed!
  private List<Country> temporalGetAllCountries() {
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

  // TODO - Temporal - To be removed!
  private List<InstitutionType> temporalGetAllPartnerTypes() {
    ArrayList<InstitutionType> types = new ArrayList<InstitutionType>();
    for (int c = 1; c <= 20; c++) {
      types.add(new InstitutionType(c, "Type Name " + c, "Acronym "));
    }
    return types;
  }

  private List<User> temporalGetAllProjectLeaders() {
    List<User> users = new ArrayList<User>();
    for (int c = 1; c <= 30; c++) {
      User fakeUser = new User();
      fakeUser.setId(c);
      fakeUser.setFirstName("Héctor " + c);
      fakeUser.setLastName("Tobón " + c);
      fakeUser.setEmail("h.f.tobon" + c + "@cgiar.org");
      fakeUser.setCcafsUser(true);
      List<Institution> fakeUserInstitutions = new ArrayList<>();
      Institution inst = institutionManager.getInstitution(3);
      fakeUserInstitutions.add(inst);
      fakeUser.setInstitutions(fakeUserInstitutions);
      users.add(fakeUser);
    }
    return users;
  }
}
