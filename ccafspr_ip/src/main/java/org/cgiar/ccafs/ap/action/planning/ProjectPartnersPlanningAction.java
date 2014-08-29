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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the Project Partners section in the planning step.
 *
 * @author Hernán Carvajal
 */
public class ProjectPartnersPlanningAction extends BaseAction {

  private static final long serialVersionUID = 5839536146328620421L;
  public static Logger LOG = LoggerFactory.getLogger(ProjectPartnersPlanningAction.class);

  // Managers
  private ProjectPartnerManager projectPartnerManager;
  private InstitutionManager institutionManager;
  private LocationManager locationManager;
  private ProjectManager projectManager;
  private UserManager userManager;
  private BudgetManager budgetManager;

  // Model for the back-end
  private int projectID;
  private Project project;
  private boolean isExpected;
  private Project previousProject;

  // Model for the view
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;
  private List<Institution> allPartners; // will be used to list all the partners that have the system.
  private List<User> allProjectLeaders; // will be used to list all the project leaders that have the system.

  @Inject
  public ProjectPartnersPlanningAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager, LocationManager locationManager, ProjectManager projectManager,
    UserManager userManager, BudgetManager budgetManager) {
    super(config);
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.locationManager = locationManager;
    this.projectManager = projectManager;
    this.userManager = userManager;
    this.budgetManager = budgetManager;
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

  public int getProjectID() {
    return projectID;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  public boolean isExpected() {
    return isExpected;
  }

  @Override
  public String next() {
    String result = save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Getting the project id from the URL parameter
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);

    // if there are not partners, please return an empty List.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(projectID));

    // Getting all partners.
    allPartners = institutionManager.getAllInstitutions();

    // Getting all the countries
    countries = locationManager.getInstitutionCountries();

    // Getting all partner types
    partnerTypes = institutionManager.getAllInstitutionTypes();

    // Getting all Project Leaders
    allProjectLeaders = userManager.getAllUsers();

    // Getting the project partner leader.
    // We validate if the partner leader is already in the employees table. If so, we need to get this
    // information and show it as label in the front-end.
    // If not, we just load the form for the expected project leader.
    User projectLeader = projectManager.getProjectLeader(project.getId());
    // if the official leader is defined.
    if (projectLeader != null) {
      isExpected = false;
      project.setLeader(projectLeader);
    } else {
      isExpected = true;
      project.setExpectedLeader(projectManager.getExpectedProjectLeader(projectID));
      // In case there is not a partner leader defined, an empty partner will be used for the view.
      if (project.getExpectedLeader() == null) {
        User exptectedProjectLeader = new User();
        exptectedProjectLeader.setId(-1);
        project.setExpectedLeader(exptectedProjectLeader);
      }
    }

    // If the user is not admin or the project owner, we should keep some information
    // unmutable
    previousProject = new Project();
    previousProject.setId(project.getId());
    previousProject.setProjectPartners(project.getProjectPartners());


    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getProjectPartners() != null) {
        project.getProjectPartners().clear();
      }
    }

  }

  @Override
  public String save() {
    if (this.isSaveable()) {
      if (this.isFullEditable()) {
        // If user is an Admin, FPL, RPL or PO, he has privileges to update anything.
        boolean success = true;
        boolean saved = true;

        // Getting previous Project Partners.
        List<ProjectPartner> previousProjectPartners = projectPartnerManager.getProjectPartners(projectID);

        // Deleting project partners
        for (ProjectPartner projectPartner : previousProjectPartners) {
          if (!project.getProjectPartners().contains(projectPartner)) {
            boolean deleted = projectPartnerManager.deleteProjectPartner(projectPartner.getId());
            if (!deleted) {
              success = false;
            }
          }
        }

        // --- Getting previous Partner Institutions
        List<Institution> previousInstitutions = new ArrayList<>();
        // - From project leader
        if (isExpected) {
          previousInstitutions.add(projectManager.getExpectedProjectLeader(project.getId()).getCurrentInstitution());
        } else {
          previousInstitutions.add(projectManager.getProjectLeader(project.getId()).getCurrentInstitution());
        }
        // - From project partners
        for (ProjectPartner projectPartner : previousProjectPartners) {
          previousInstitutions.add(projectPartner.getPartner());
        }
        // --- Getting current Partner Institutions
        List<Institution> currentInstitutions = new ArrayList<>();
        // - From project leader
        if (isExpected) {
          currentInstitutions.add(project.getExpectedLeader().getCurrentInstitution());
        } else {
          currentInstitutions.add(project.getLeader().getCurrentInstitution());
        }
        // - From project partners
        for (ProjectPartner projectPartner : project.getProjectPartners()) {
          currentInstitutions.add(projectPartner.getPartner());
        }
        // Deleting Partner Institutions from budget section
        for (Institution previousInstitution : previousInstitutions) {
          if (!currentInstitutions.contains(previousInstitution)) {
            budgetManager.deleteBudgetsByInstitution(project.getId(), previousInstitution.getId());
          }
        }

        // Saving Project leader
        if (isExpected) {
          saved = projectManager.saveExpectedProjectLeader(project.getId(), project.getExpectedLeader());
          if (!saved) {
            success = false;
          }
        }

        // Saving new and old project partners
        saved = projectPartnerManager.saveProjectPartner(project.getId(), project.getProjectPartners());
        if (!saved) {
          success = false;
        }

        if (success) {
          addActionMessage(getText("saving.saved"));
          return SUCCESS;
        } else {
          addActionError(getText("saving.problem"));
          return INPUT;
        }
      } else {
        // User is PL, thus, only partner's responsabilities.

        // We set the values that changed to the previous project
        // in order to prevent unauthorized changes.
        previousProject.setProjectPartners(projectPartnerManager.getProjectPartners(project.getId()));
        for (int c = 0; c < previousProject.getProjectPartners().size(); c++) {
          // Copying responsibilities.
          previousProject.getProjectPartners().get(c)
            .setResponsabilities(project.getProjectPartners().get(c).getResponsabilities());
        }
        boolean result =
          projectPartnerManager.saveProjectPartner(previousProject.getId(), previousProject.getProjectPartners());
        if (result) {
          addActionMessage(getText("saving.saved"));
          return SUCCESS;
        } else {
          addActionError(getText("saving.problem"));
          return BaseAction.INPUT;
        }
      }
    } else {
      LOG
        .warn(
          "User (employee_id={}, email={}) tried to save information in Project Partners without having enough privileges!",
          new Object[] {this.getCurrentUser().getEmployeeId(), this.getCurrentUser().getEmail()});
    }
    return BaseAction.ERROR;

  }

  public void setAllProjectLeaders(List<User> allProjectLeaders) {
    this.allProjectLeaders = allProjectLeaders;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    // Validate the email of all project partners
// for (int c = 0; c < project.getProjectPartners().size(); c++) {
// if (!EmailValidator.isValidEmail(project.getProjectPartners().get(c).getContactEmail())) {
// addFieldError("project.projectPartners[" + c + "].contactEmail", getText("validation.incorrect.format"));
// addActionError(getText("preplanning.projectPartners.invalid.contactEmail", new String[] {c + ""}));
// }
// }

    // Validate fields are empty

    super.validate();
  }

}
