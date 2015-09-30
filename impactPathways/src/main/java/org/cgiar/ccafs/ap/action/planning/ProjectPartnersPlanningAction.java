/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.ProjectRoleManager;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.planning.ProjectPartnersValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the Project Partners section in the planning step.
 * 
 * @author Hernán Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Carlos Alberto Martínez M.
 */
public class ProjectPartnersPlanningAction extends BaseAction {

  private static final long serialVersionUID = 5839536146328620421L;
  public static Logger LOG = LoggerFactory.getLogger(ProjectPartnersPlanningAction.class);

  // Managers
  private ProjectPartnerManager projectPartnerManager;
  private InstitutionManager institutionManager;
  private LocationManager locationManager;
  private ProjectManager projectManager;
  private ProjectRoleManager projectRoleManager;
  private UserManager userManager;
  private RoleManager roleManager;
  private ActivityManager activityManager;
  private DeliverableManager deliverableManager;
  // private BudgetManager budgetManager;
  // private DeliverablePartnerManager deliverablePartnerManager;
  // private DeliverableManager deliverableManager;

  // Validator
  private ProjectPartnersValidator projectPartnersValidator;

  // Model for the back-end
  private int projectID;
  private Project previousProject;
  private Project project;
  private String actionName;

  // Model for the view
  private List<InstitutionType> intitutionTypes;
  private Map<String, String> partnerPersonTypes; // List of partner person types (CP, PL, PC).
  private List<Country> countries;
  private List<Institution> allInstitutions; // Is used to list all the partner institutions that have the system.
  private List<Institution> allPPAInstitutions; // Is used to list all the PPA partners institutions
  private List<ProjectPartner> projectPPAPartners; // Is used to list all the PPA partners that belongs to the project.
  private List<User> allUsers; // will be used to list all the project leaders that have the system.

  // private List<Institution> contributionPartners; // this would get the partners contributing to others

  @Inject
  public ProjectPartnersPlanningAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager, LocationManager locationManager, ProjectManager projectManager,
    UserManager userManager, BudgetManager budgetManager, ProjectPartnersValidator projectPartnersValidator,
    DeliverablePartnerManager deliverablePartnerManager, DeliverableManager deliverableManager,
    ActivityManager activityManager, ProjectRoleManager projectRoleManager, RoleManager roleManager) {
    super(config);
    this.projectPartnerManager = projectPartnerManager;
    this.institutionManager = institutionManager;
    this.locationManager = locationManager;
    this.projectManager = projectManager;
    this.projectRoleManager = projectRoleManager;
    this.userManager = userManager;
    this.activityManager = activityManager;
    this.deliverableManager = deliverableManager;
    this.projectPartnersValidator = projectPartnersValidator;
    this.roleManager = roleManager;
    // this.budgetManager = budgetManager;
    // this.deliverablePartnerManager = deliverablePartnerManager;
  }

  // public static void main(String[] args) {
  // Injector in = Guice.createInjector(new APModule());
  // ProjectPartnerManager partnerManager = in.getInstance(ProjectPartnerManager.class);
  // InstitutionManager instManager = in.getInstance(InstitutionManager.class);
  // PartnerPersonManager personManager = in.getInstance(PartnerPersonManager.class);
  //
  // ProjectPartner partner = partnerManager.getProjectPartner(1);
  // partner.setInstitution(instManager.getInstitution(5));
  // PartnerPerson newPerson = new PartnerPerson(810);
  // newPerson.setResponsibilities("responsibilities OTHER....");
  // newPerson.setType(APConstants.PROJECT_PARTNER_CP);
  // newPerson.setUser(new User(5));
  //
  // System.out.println(personManager.savePartnerPerson(partner, newPerson, new User(2), "Justification test...."));
  // // partnerManager.saveProjectPartner(new Project(2), partner, new User(1), "testing update");
  //
  // }

  public List<Activity> getActivitiesLedByUser(int userID) {
    return activityManager.getProjectActivitiesLedByUser(projectID, userID);
  }

  public List<Institution> getAllInstitutions() {
    return allInstitutions;
  }

  public List<Institution> getAllPPAInstitutions() {
    return allPPAInstitutions;
  }

  // private boolean deletePartner(ProjectPartner partnerToDelete, List<ProjectPartner> partners) {
  //
  // // Before deleting the project partner, we have to delete the deliverable partner contributions.
  //
  // List<Deliverable> deliverables = deliverableManager.getDeliverablesByProjectPartnerID(partnerToDelete.getId());
  //
  // for (Deliverable deliverable : deliverables) {
  // // Deleting partner in case it is selected in the responsible.
  // if (deliverable.getResponsiblePartner() != null
  // && deliverable.getResponsiblePartner().getPartner().equals(partnerToDelete)) {
  // deliverablePartnerManager.deleteDeliverablePartner(deliverable.getResponsiblePartner().getId(),
  // this.getCurrentUser(), this.getJustification());
  // }
  // // Deleting partner in case it is selected in other parter contributions.
  // for (DeliverablePartner deliverablePartner : deliverable.getOtherPartners()) {
  // if (deliverablePartner.getPartner().equals(partnerToDelete)) {
  // deliverablePartnerManager.deleteDeliverablePartner(deliverablePartner.getId(), this.getCurrentUser(),
  // this.getJustification());
  // }
  // }
  // }
  //
  // // Deleting all the project partners contributions.
  //
  // // we need to validate that it is the only institution that is entered in the system.
  // boolean lastInstitution = institutionManager.validateLastOneInstitution(partnerToDelete.getId());
  // // If the institution is the last one, we need to get all the project partners that will be affected.
  // if (lastInstitution) {
  // for (ProjectPartner partner : partners) {
  // // Looping the list of "contribute institutions".
  // // if (partner.getContributeInstitutions() != null) {
  // // for (Institution institution : partner.getContributeInstitutions()) {
  // // if (institution.equals(partnerToDelete.getInstitution())) {
  // // // delete the project partner contribution
  // // institutionManager.deleteProjectPartnerContributeInstitution(partner, partnerToDelete.getInstitution());
  // // break; // stop the loop.
  // // }
  // // }
  // // }
  // }
  // }
  //
  // // Now it is ok to delete the current project partner.
  // boolean deleted = projectPartnerManager.z_old_deleteProjectPartner(partnerToDelete.getId(), this.getCurrentUser(),
  // this.getJustification());
  // return deleted;
  // }

  public List<Institution> getAllPPAPartners() {
    return allPPAInstitutions;
  }

  public List<User> getAllUsers() {
    return allUsers;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<Deliverable> getDeliverablesLedByUser(int userID) {
    return deliverableManager.getProjectDeliverablesLedByUser(projectID, userID);
  }

  public List<InstitutionType> getInstitutionTypes() {
    return intitutionTypes;
  }

  public Map<String, String> getPartnerPersonTypes() {
    return partnerPersonTypes;
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

  public String getTypeProjectContactPerson() {
    return APConstants.PROJECT_PARTNER_CP;
  }

  public String getTypeProjectCoordinator() {
    return APConstants.PROJECT_PARTNER_PC;
  }

  public String getTypeProjectLeader() {
    return APConstants.PROJECT_PARTNER_PL;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    actionName = ActionContext.getContext().getName();
    // Getting the project id from the URL parameter
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);

    // Getting the list of all institutions
    allInstitutions = institutionManager.getAllInstitutions();

    // Getting the list of all PPA institutions
    allPPAInstitutions = new ArrayList<>();
    allPPAInstitutions.addAll(institutionManager.getAllPPAInstitutions());

    // Getting all the countries
    countries = locationManager.getInstitutionCountries();

    // Getting all partner types
    intitutionTypes = institutionManager.getAllInstitutionTypes();

    // Getting all Project Leaders
    allUsers = userManager.getAllUsers();

    // Getting all the project partners.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    // Positioning project leader to be the first in the list.
    ProjectPartner leader = project.getLeader();
    if (leader != null) {
      // First we remove the element from the array.
      project.getProjectPartners().remove(leader);
      // then we add it to the first position.
      project.getProjectPartners().add(0, leader);
    }

    // Getting the list of PPA Partners for this project
    this.projectPPAPartners = new ArrayList<ProjectPartner>();
    for (ProjectPartner pp : project.getProjectPartners()) {
      if (pp.getInstitution().isPPA()) {
        this.projectPPAPartners.add(pp);
      }
    }

    // Populating the list of partner person types
    partnerPersonTypes = new HashMap<>();
    partnerPersonTypes.put(APConstants.PROJECT_PARTNER_CP, this.getText("planning.projectPartners.types.CP"));

    if (!project.isLeader(this.getCurrentUser()) && !project.isCoordinator(this.getCurrentUser())) {
      partnerPersonTypes.put(APConstants.PROJECT_PARTNER_PL, this.getText("planning.projectPartners.types.PL"));
    }

    if (!project.isCoordinator(this.getCurrentUser())) {
      partnerPersonTypes.put(APConstants.PROJECT_PARTNER_PC, this.getText("planning.projectPartners.types.PC"));
    }

    // If the user is not admin or the project owner, we should keep some information
    // immutable
    previousProject = new Project();
    previousProject.setId(project.getId());
    previousProject.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    // if (actionName.equals("partnerLead")) {
    // super.setHistory(historyManager.getProjectPartnersHistory(project.getId(),
    // new String[] {APConstants.PROJECT_PARTNER_PL, APConstants.PROJECT_PARTNER_PC}));
    // } else if (actionName.equals("ppaPartners")) {
    // super.setHistory(
    // historyManager.getProjectPartnersHistory(project.getId(), new String[] {APConstants.PROJECT_PARTNER_PPA}));
    // } else if (actionName.equals("partners")) {
    // super.setHistory(
    // historyManager.getProjectPartnersHistory(project.getId(), new String[] {APConstants.PROJECT_PARTNER_PP}));
    // }

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      // if (ActionContext.getContext().getName().equals("ppaPartners") && project.getPPAPartners() != null) {
      // project.getPPAPartners().clear();
      // }

      if (ActionContext.getContext().getName().equals("partners") && project.getProjectPartners() != null) {
        project.getProjectPartners().clear();
      }
    }

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), this.getCurrentPlanningYear()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");

  }

  public List<ProjectPartner> projectPPAPartners() {
    return this.projectPPAPartners;
  }

  // private String savePartnerLead() {
  // boolean success = true;
  //
  // // Saving Project leader
  // int id = projectPartnerManager.z_old_saveProjectPartner(projectID, project.getLeader(), this.getCurrentUser(),
  // this.getJustification());
  // if (id < 0) {
  // success = false;
  // }
  //
  // // Saving Project Coordinator
  // // Setting the same institution that was selected for the Project Leader.
  // project.getCoordinator().setInstitution(project.getLeader().getInstitution());
  // id = projectPartnerManager.z_old_saveProjectPartner(projectID, project.getCoordinator(), this.getCurrentUser(),
  // this.getJustification());
  // if (id < 0) {
  // success = false;
  // }
  //
  // budgetManager.deleteBudgetsWithNoLinkToInstitutions(projectID);
  // if (success) {
  // this.addActionMessage(this.getText("saving.saved"));
  // return SUCCESS;
  // }
  // return INPUT;
  // }

  // private String savePartners(String partnerType) {
  // boolean success = true;
  //
  // // Getting the partners coming from the view.
  // // List<ProjectPartner> partners;
  // // if (partnerType.equals(APConstants.PROJECT_PARTNER_PPA)) {
  // // partners = project.getPPAPartners();
  // // } else if (partnerType.equals(APConstants.PROJECT_PARTNER_PP)) {
  // // partners = project.getProjectPartners();
  // // } else {
  // // partners = new ArrayList<>();
  // // }
  //
  // // ----------------- PARTNERS ----------------------
  // // Getting previous partners to identify those that need to be deleted.
  // // List<ProjectPartner> previousPartners = projectPartnerManager.getProjectPartners(projectID, partnerType);
  // //
  // // // Deleting project partners
  // // for (ProjectPartner previousPartner : previousPartners) {
  // // if (!partners.contains(previousPartner)) {
  // // boolean deleted = this.deletePartner(previousPartner, partners);
  // // if (!deleted) {
  // // success = false;
  // // }
  // // }
  // // }
  // //
  // // // Saving new and old PPA Partners
  // // boolean saved =
  // // projectPartnerManager.saveProjectPartners(projectID, partners, this.getCurrentUser(), this.getJustification());
  // // if (!saved) {
  // // saved = false;
  // // }
  //
  // // Saving project partner contributions
  // // if (partnerType.equals(APConstants.PROJECT_PARTNER_PP)) {
  // // // iterating each project partner
  // // for (ProjectPartner projectPartner : partners) {
  // // // Getting previous partner contributions to identify those that need to be deleted.
  // // List<Institution> previousPartnerContributions =
  // // institutionManager.getProjectPartnerContributeInstitutions(projectPartner);
  // // // Deleting project partner contributions
  // // // for (Institution previousPartnerContribution : previousPartnerContributions) {
  // // // if (projectPartner.getContributeInstitutions() == null
  // // // || !projectPartner.getContributeInstitutions().contains(previousPartnerContribution)) {
  // // // boolean deleted = institutionManager.deleteProjectPartnerContributeInstitution(projectPartner.getId(),
  // // // previousPartnerContribution.getId());
  // // // if (!deleted) {
  // // // success = false;
  // // // }
  // // // }
  // // // }
  // //
  // // // if the project partner has contribute institutions.
  // // // if (projectPartner.getContributeInstitutions() != null) {
  // // // // Saving new and old Project Partner Contributions
  // // // saved = institutionManager.saveProjectPartnerContributeInstitutions(projectPartner.getId(),
  // // // projectPartner.getContributeInstitutions());
  // // // if (!saved) {
  // // // saved = false;
  // // // }
  // // // }
  // // } // End loop
  //
  // // }
  //
  // budgetManager.deleteBudgetsWithNoLinkToInstitutions(projectID);
  // if (success)
  //
  // {
  // this.addActionMessage(this.getText("saving.saved"));
  // return SUCCESS;
  // }
  // return INPUT;
  //
  // }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectPartners(project.getId())) {

      if (!this.isNewProject()) {
        super.saveProjectLessons(projectID);
      }

      // First, delete the partners that are not active anymore
      for (ProjectPartner previousPartner : previousProject.getProjectPartners()) {
        if (!project.getProjectPartners().contains(previousPartner)) {
          projectPartnerManager.deleteProjectPartner(previousPartner, this.getCurrentUser(), this.getJustification());
        }
      }

      projectPartnerManager.saveProjectPartners(project, project.getProjectPartners(), this.getCurrentUser(),
        this.getJustification());

      // TODO - The project role manager can be deleted now because we are not using the table project_roles to
      // verify if a user can edit a project.
      projectRoleManager.saveProjectRoles(project, this.getCurrentUser(), this.getJustification());

      // Check if the project leader has changed and send the corresponding emails
      PartnerPerson previousLeader = previousProject.getLeaderPerson();
      PartnerPerson leader = project.getLeaderPerson();

      Role plRole = new Role(APConstants.ROLE_PROJECT_LEADER);
      if (previousLeader == null && leader != null) {
        roleManager.saveRole(leader.getUser(), plRole);
        // TODO - Send message notifying to the new project leader
      } else if (previousLeader != null && leader == null) {
        roleManager.deleteRole(previousLeader.getUser(), plRole);
        // TODO - Send message notifying to the user that is not the project leader anymore
      } else if (previousLeader != null && leader != null) {
        if (!leader.equals(previousLeader)) {
          roleManager.saveRole(leader.getUser(), plRole);
          roleManager.deleteRole(previousLeader.getUser(), plRole);
          // TODO - Send message to leader notifying that he/she is the new project leader
          // TODO - Send message to previousLeader notifying that he/she is not the project leader anymore
        }
      }

      // Get the validation messages and append them to the save message
      Collection<String> messages = this.getActionMessages();
      if (!messages.isEmpty()) {
        String validationMessage = messages.iterator().next();
        this.setActionMessages(null);
        this.addActionWarning(this.getText("saving.saved") + validationMessage);
      } else {
        this.addActionMessage(this.getText("saving.saved"));
      }
      return SUCCESS;
    }
    return NOT_AUTHORIZED;

  }

  public void setAllProjectLeaders(List<User> allProjectLeaders) {
    this.allUsers = allProjectLeaders;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    if (save) {
      projectPartnersValidator.validate(this, project, "Planning");
    }
  }

}
