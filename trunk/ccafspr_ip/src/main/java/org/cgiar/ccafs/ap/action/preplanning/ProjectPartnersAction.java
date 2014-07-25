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

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
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
  private LocationManager locationManager;
  private ProjectManager projectManager;
  private UserManager userManager;

  // Model for the back-end
  private int projectID;
  private Project project;

  // Model for the view
  private List<InstitutionType> partnerTypes;
  private List<Location> countries;
  private List<Institution> allPartners; // will be used to list all the partners that have the system.
  private List<User> allProjectLeaders; // will be used to list all the project leaders that have the system.

  @Inject
  public ProjectPartnersAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager, LocationManager locationManager, ProjectManager projectManager,
    UserManager userManager) {
    super(config);
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.locationManager = locationManager;
    this.projectManager = projectManager;
    this.userManager = userManager;
  }

  @Override
  public String execute() throws Exception {
    /*
     * If there project Id is not in the parameter or if there is not a project with that id, we must redirect to a
     * NOT_FOUND page.
     */
    if (projectID == -1) {
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

  public List<Location> getCountries() {
    return countries;
  }

  public List<InstitutionType> getPartnerTypes() {
    return partnerTypes;
  }

  public Project getProject() {
    return project;
  }


  public int getProjectID() {
    return projectID;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Getting the project id from the URL parameter
    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID);
      projectID = -1;
      return; // Stop here and go to execute method.
    }
    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);
    // if there is not a project identified with the given id
    if (project == null) {
      return; // Stop here and go to execute method.
    }

    // if there are not partners, please return an empty List.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(projectID));

    // Getting all partners.
    allPartners = institutionManager.getAllInstitutions();

    // Getting all the countries
    countries = locationManager.getLocationsByType(APConstants.LOCATION_ELEMENT_TYPE_COUNTRY);

    // Getting all partner types
    partnerTypes = institutionManager.getAllInstitutionTypes();

    // Getting all Project Leaders
    allProjectLeaders = userManager.getAllUsers();

    // Getting the project partner leader.
    project.setLeader(userManager.getProjectLeader(projectID));

    // In case there is not a partner leader defined, an empty partner will be used for the view.
    if (project.getLeader() == null) {
      User projectLeader = new User();
      projectLeader.setId(-1);
      project.setLeader(projectLeader);
    }

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getProjectPartners() != null) {
        project.getProjectPartners().clear();
      }
    }

  }

  @Override
  public String save() {
    boolean success = true;

    // Getting previous Project Partners.
    List<ProjectPartner> previousProjectPartners = projectPartnerManager.getProjectPartners(projectID);


    for (ProjectPartner projectPartner : previousProjectPartners) {
      if (!project.getProjectPartners().contains(projectPartner)) {
        // TODO HT - Test if the delete method is properly working.
        if (projectPartnerManager.deleteProjectPartner(projectPartner.getId())) {
          success = false;
        }
      }
    }

    // TODO HT - Test if the save method is properly working.
// if (!projectPartnerManager.saveProjectPartner(project.getProjectPartners())) {
// success = false;
// }

    if (success) {
      return SUCCESS;
    } else {
      return INPUT;
    }

  }

  public void setAllProjectLeaders(List<User> allProjectLeaders) {
    this.allProjectLeaders = allProjectLeaders;
  }

  public void setProject(Project project) {
    this.project = project;
  }

// public void setProjectID(int projectId) {
// System.out.println("setProjectId(" + projectId + ")");
// this.projectID = projectId;
// }


}
