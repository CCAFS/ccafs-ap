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
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
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
import org.cgiar.ccafs.ap.util.SendMail;
import org.cgiar.ccafs.ap.validation.planning.ProjectPartnersValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to manage the Project Partners section in the planning step.
 * 
 * @author Hernán Carvajal
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Carlos Alberto Martínez M.
 * @author Christian David Garcia
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
  private HistoryManager historyManager;
  // private BudgetManager budgetManager;
  // private DeliverablePartnerManager deliverablePartnerManager;
  // private DeliverableManager deliverableManager;

  // Validator
  private ProjectPartnersValidator projectPartnersValidator;

  // Model for the back-end
  private int projectID;
  private Project previousProject;
  private Project project;

  // Model for the view
  private List<InstitutionType> intitutionTypes;
  private Map<String, String> partnerPersonTypes; // List of partner person types (CP, PL, PC).
  private List<Country> countries;
  private List<Institution> allInstitutions; // Is used to list all the partner institutions that have the system.
  private List<Institution> allPPAInstitutions; // Is used to list all the PPA partners institutions
  private List<ProjectPartner> projectPPAPartners; // Is used to list all the PPA partners that belongs to the project.
  private List<User> allUsers; // will be used to list all the project leaders that have the system.

  // Util
  private SendMail sendMail;

  // private List<Institution> contributionPartners; // this would get the partners contributing to others

  @Inject
  public ProjectPartnersPlanningAction(APConfig config, ProjectPartnerManager projectPartnerManager,
    InstitutionManager institutionManager, LocationManager locationManager, ProjectManager projectManager,
    UserManager userManager, BudgetManager budgetManager, ProjectPartnersValidator projectPartnersValidator,
    DeliverablePartnerManager deliverablePartnerManager, DeliverableManager deliverableManager,
    ActivityManager activityManager, ProjectRoleManager projectRoleManager, RoleManager roleManager, SendMail sendMail,
    HistoryManager historyManager) {
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
    this.sendMail = sendMail;
    this.historyManager = historyManager;
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

  /**
   * This method will validate if the user is deactivated. If so, it will send an email indicating the credentials to
   * access.
   * 
   * @param leader is a PartnerPerson object that could be the leader or the coordinator.
   */
  private void notifyNewUserCreated(User user) {

    if (!user.isActive()) {

      user.setActive(true);
      // Building the Email message:
      StringBuilder message = new StringBuilder();
      message.append(this.getText("planning.manageUsers.email.dear", new String[] {user.getFirstName()}));
      message.append(this.getText("planning.manageUsers.email.newUser.part1"));
      message.append(this.getText("planning.manageUsers.email.newUser.part2"));

      String password = this.getText("planning.manageUsers.email.outlookPassword");
      if (!user.isCcafsUser()) {
        // Generating a random password.
        password = RandomStringUtils.randomNumeric(6);
        // Applying the password to the user.
        user.setMD5Password(password);
      }
      message.append(this.getText("planning.manageUsers.email.newUser.part3",
        new String[] {config.getBaseUrl(), user.getEmail(), password}));
      message.append(this.getText("planning.manageUsers.email.support"));
      message.append(this.getText("planning.manageUsers.email.bye"));

      // Saving the new user configuration.
      userManager.saveUser(user, this.getCurrentUser());

      String toEmail = null;
      if (config.isProduction()) {
        // Send email to the new user and the P&R notification email.
        // TO
        toEmail = user.getEmail();
      }
      // BBC
      String bbcEmails = this.config.getEmailNotification();
      sendMail.send(toEmail, null, bbcEmails,
        this.getText("planning.manageUsers.email.newUser.subject", new String[] {user.getComposedName()}),
        message.toString());
    }
  }

  /**
   * This method notify the user that is been assigned as Project Leader/Coordinator for a specific project.
   * 
   * @param userAssigned is the user that is being assigned.
   * @param role is the role (Project Leader or Project Coordinator).
   */
  private void notifyRoleAssigned(User userAssigned, Role role) {
    String projectRole = null;
    if (role.getId() == APConstants.ROLE_PROJECT_LEADER) {
      projectRole = this.getText("planning.projectPartners.types.PL");
    } else {
      projectRole = this.getText("planning.projectPartners.types.PC");
    }
    StringBuilder message = new StringBuilder();
    // Building the Email message:
    message.append(this.getText("planning.manageUsers.email.dear", new String[] {userAssigned.getFirstName()}));
    message.append(
      this.getText("planning.manageUsers.email.project.assigned", new String[] {projectRole, project.getTitle()}));
    message.append(this.getText("planning.manageUsers.email.support"));
    message.append(this.getText("planning.manageUsers.email.bye"));

    String toEmail = null;
    String ccEmail = null;
    if (config.isProduction()) {
      // Send email to the new user and the P&R notification email.
      // TO
      toEmail = userAssigned.getEmail();
      // CC will be the user who is making the modification.
      if (this.getCurrentUser() != null) {
        ccEmail = this.getCurrentUser().getEmail();
      }
    }
    // BBC will be our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();
    sendMail.send(toEmail, ccEmail, bbcEmails, this.getText("planning.manageUsers.email.project.assigned.subject",
      new String[] {projectRole, project.getStandardIdentifier(false)}), message.toString());
  }

  /**
   * This method notify the the user that he/she stopped contributing to a specific project.
   * 
   * @param userUnassigned is the user that stopped contribution.
   * @param role is the user role that stopped contributing (Project Leader or Project Coordinator).
   */
  private void notifyRoleUnassigned(User userUnassigned, Role role) {
    String projectRole = null;
    if (role.getId() == APConstants.ROLE_PROJECT_LEADER) {
      projectRole = this.getText("planning.projectPartners.types.PL");
    } else {
      projectRole = this.getText("planning.projectPartners.types.PC");
    }
    StringBuilder message = new StringBuilder();
    // Building the Email message:
    message.append(this.getText("planning.manageUsers.email.dear", new String[] {userUnassigned.getFirstName()}));
    message.append(
      this.getText("planning.manageUsers.email.project.unAssigned", new String[] {projectRole, project.getTitle()}));
    message.append(this.getText("planning.manageUsers.email.support"));
    message.append(this.getText("planning.manageUsers.email.bye"));

    String toEmail = null;
    String ccEmail = null;
    if (config.isProduction()) {
      // Send email to the new user and the P&R notification email.
      // TO
      toEmail = userUnassigned.getEmail();
      // CC will be the user who is making the modification.
      if (this.getCurrentUser() != null) {
        ccEmail = this.getCurrentUser().getEmail();
      }
    }
    // BBC will be our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();
    sendMail.send(toEmail, ccEmail, bbcEmails, this.getText("planning.manageUsers.email.project.unAssigned.subject",
      new String[] {projectRole, project.getStandardIdentifier(false)}), message.toString());
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
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

    // Set History.
    super.setHistory(historyManager.getProjectPartnersHistory(project.getId()));

  }

  public List<ProjectPartner> projectPPAPartners() {
    return this.projectPPAPartners;
  }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectPartners(project.getId())) {

      // Saving Lessons
      if (!this.isNewProject()) {
        this.saveProjectLessons(project.getId());
      }

      // First, delete the partners that are not active anymore
      for (ProjectPartner previousPartner : previousProject.getProjectPartners()) {
        if (!project.getProjectPartners().contains(previousPartner)) {
          projectPartnerManager.deleteProjectPartner(previousPartner, this.getCurrentUser(), this.getJustification());
        }
      }

      projectPartnerManager.saveProjectPartners(project, project.getProjectPartners(), this.getCurrentUser(),
        this.getJustification());

      // Check if the project leader has changed and send the corresponding emails
      PartnerPerson previousLeader = previousProject.getLeaderPerson();
      PartnerPerson leader = project.getLeaderPerson();
      // Notify user if the project leader was created.
      if (leader != null) {
        this.notifyNewUserCreated(leader.getUser());
      }
      Role plRole = new Role(APConstants.ROLE_PROJECT_LEADER);
      // Update roles into the database and notify project assignment.
      this.updateRoles(previousLeader, leader, plRole);

      // Check if the project coordinator has changed and send the corresponding emails
      PartnerPerson previousCoordinator = null;
      if (previousProject.getCoordinatorPersons().size() > 0) {
        previousCoordinator = project.getCoordinatorPersons().get(0);
      }
      PartnerPerson coordinator = null;
      if (project.getCoordinatorPersons().size() > 0) {
        coordinator = project.getCoordinatorPersons().get(0);
      }
      // Notify user if the project coordinator was created.
      if (coordinator != null) {
        this.notifyNewUserCreated(coordinator.getUser());
      }
      Role pcRole = new Role(APConstants.ROLE_PROJECT_COORDINATOR);
      // Update roles into the database and notify project assignment.
      this.updateRoles(previousCoordinator, coordinator, pcRole);

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

  /**
   * This method updates the role for each user (Leader/Coordinator) into the database, and notifies by email what has
   * been done.
   * 
   * @param previousPartnerPerson is the previous leader/coordinator that has assigned the project before.
   * @param partnerPerson the current leader/coordinator associated to the project.
   * @param role is the new role assginated (leader/coordinator).
   */
  private void updateRoles(PartnerPerson previousPartnerPerson, PartnerPerson partnerPerson, Role role) {
    if (previousPartnerPerson == null && partnerPerson != null) {
      roleManager.saveRole(partnerPerson.getUser(), role);
      // Notifying user is assigned as Project Leader/Coordinator.
      this.notifyRoleAssigned(partnerPerson.getUser(), role);
    } else if (previousPartnerPerson != null && partnerPerson == null) {
      roleManager.deleteRole(previousPartnerPerson.getUser(), role);
      // Notifying user that is not the project leader anymore
      this.notifyRoleUnassigned(previousPartnerPerson.getUser(), role);
    } else if (previousPartnerPerson != null && partnerPerson != null) {
      if (!partnerPerson.equals(previousPartnerPerson)) {
        roleManager.saveRole(partnerPerson.getUser(), role);
        // Notifying user is assigned as Project Leader/Coordinator.
        this.notifyRoleAssigned(partnerPerson.getUser(), role);
        // Deleting role.
        roleManager.deleteRole(previousPartnerPerson.getUser(), role);
        // Notifying user that is not the project leader anymore
        this.notifyRoleUnassigned(previousPartnerPerson.getUser(), role);
      }
    }
  }

  @Override
  public void validate() {
    if (save) {
      projectPartnersValidator.validate(this, project, "Planning");
    }
  }

}
