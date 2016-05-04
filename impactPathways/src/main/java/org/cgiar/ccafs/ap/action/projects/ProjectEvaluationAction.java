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
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectEvalutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.RoleManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectEvaluation;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.Role;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.projects.ProjectEvaluationValidator;
import org.cgiar.ccafs.security.data.manager.UserRoleManagerImpl;
import org.cgiar.ccafs.security.data.model.UserRole;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.SendMail;
import org.cgiar.ccafs.utils.URLFileDownloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectEvaluationAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(ProjectEvaluationAction.class);
  private static final long serialVersionUID = 2845669913596494699L;

  // Manager
  private ProjectManager projectManager;
  private ProjectPartnerManager projectPartnerManager;
  private ProjectEvalutionManager projectEvaluationManager;
  private BudgetManager budgetManager;
  private UserRoleManagerImpl userRoleManager;
  private UserManager userManager;
  private IPProgramManager ipProgramManager;
  private RoleManager roleManager;

  private final int STAR_DIV = 2;
  // Model for the back-end
  private Project project;


  private int projectID;
  private ProjectPartner projectLeader;
  private double totalCCAFSBudget;
  private double totalBilateralBudget;
  private PartnerPerson partnerPerson;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private ProjectEvaluationValidator validator;
  private SendMail sendMail;
  private LiaisonInstitution currentLiaisonInstitution;

  @Inject
  public ProjectEvaluationAction(APConfig config, ProjectManager projectManager,
    ProjectPartnerManager projectPartnerManager, BudgetManager budgetManager,
    ProjectEvalutionManager projectEvaluationManager, IPProgramManager ipProgramManager,
    ProjectEvaluationValidator validator, UserRoleManagerImpl userRoleManager, UserManager userManager,
    LiaisonInstitutionManager liaisonInstitutionManager, SendMail sendMail, RoleManager roleManager) {
    super(config);
    this.projectManager = projectManager;
    this.projectPartnerManager = projectPartnerManager;
    this.projectEvaluationManager = projectEvaluationManager;
    this.userRoleManager = userRoleManager;
    this.budgetManager = budgetManager;
    this.userManager = userManager;
    this.ipProgramManager = ipProgramManager;
    this.validator = validator;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.sendMail = sendMail;
    this.roleManager = roleManager;
  }

  /**
   * this method check if the user can rank this evaluation by his program
   * 
   * @param projectEvaluation - The evaluation to check
   * @return true if the user belong to the program or false if not belong.
   */
  public boolean checkEditByProgram(ProjectEvaluation projectEvaluation) {
    boolean bCheckProgram = false;
    if (projectEvaluation.getProgramId().intValue() == Integer.parseInt(currentLiaisonInstitution.getIpProgram())) {
      bCheckProgram = true;
    }
    return bCheckProgram;
  }

  /**
   * this method check if the user can rank this evaluation by his role
   * 
   * @param projectEvaluation - The evaluation to check
   * @return true if the user have the role of false if not have.
   */
  public boolean checkEditByRole(ProjectEvaluation projectEvaluation) {

    boolean bCheckRole = false;
    Role role = roleManager.getRoleByAcronym(projectEvaluation.getTypeEvaluation());
    List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(this.getCurrentUser().getId()));
    if (projectEvaluation.isSubmited()) {
      return false;
    }

    UserRole adminRole = new UserRole(APConstants.ROLE_ADMIN);
    if (roles.contains(adminRole)) {
      return true;
    }
    for (UserRole userRole : roles) {
      if (userRole.getId().intValue() == role.getId()) {
        bCheckRole = true;
        if (projectEvaluation.getProgramId() != null) {
          bCheckRole = bCheckRole && this.checkEditByProgram(projectEvaluation);
        }
        break;
      }
    }
    return bCheckRole;
  }


  public boolean existEvaluation(List<ProjectEvaluation> projectEvualtions, ProjectEvaluation projectEvaluation) {
    for (ProjectEvaluation projectEv : projectEvualtions) {
      if (projectEv.getTypeEvaluation().equals(projectEvaluation.getTypeEvaluation())) {
        if (projectEvaluation.getProgramId() != null) {
          if (projectEvaluation.getProgramId().equals(projectEv.getProgramId())) {
            return true;
          }

        } else {
          return true;
        }
      }
    }

    return false;
  }


  public BudgetManager getBudgetManager() {
    return budgetManager;
  }


  public IPProgramManager getIpProgramManager() {
    return ipProgramManager;
  }


  public PartnerPerson getPartnerPerson() {
    return partnerPerson;
  }


  /**
   * get the program acronyms of the project evaluation.
   * 
   * @param projectEvaluation - The project evaluation
   * @return string whit the program acronyms or "" string if the project evaluation haven't programs
   */
  public String getProgramEvaluation(ProjectEvaluation projectEvaluation) {
    if (projectEvaluation.getProgramId() != null) {
      IPProgram program = ipProgramManager.getIPProgramById(projectEvaluation.getProgramId().intValue());
      if (program != null) {
        return program.getAcronym();
      } else {
        return "";
      }
    } else {
      return "";
    }

  }


  public Project getProject() {
    return project;
  }


  public int getProjectID() {
    return projectID;
  }


  public ProjectPartner getProjectLeader() {
    return projectLeader;
  }


  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }


  public int getStartsDiv() {
    return STAR_DIV;
  }

  public double getTotalBilateralBudget() {
    return totalBilateralBudget;
  }


  public double getTotalCCAFSBudget() {
    return totalCCAFSBudget;
  }

  public String getUserName(int userId) {
    User user = userManager.getUser(userId);
    return user.getComposedName();
  }

  @Override
  public void prepare() throws Exception {

    super.prepare();

    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID, e);
      projectID = -1;
      return; // Stop here and go to execute method.
    }
    // Getting project
    project = projectManager.getProject(projectID);

    // Getting all the project partners.
    project.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    // Getting the information of the Regions Program associated with the project
    project.setRegions(ipProgramManager.getProjectFocuses(projectID, APConstants.REGION_PROGRAM_TYPE));
    // Getting the information of the Flagships Program associated with the project
    project.setFlagships(ipProgramManager.getProjectFocuses(projectID, APConstants.FLAGSHIP_PROGRAM_TYPE));

    // get the Project Leader information
    projectLeader = project.getLeader();

    // get the Project Leader contact information
    partnerPerson = project.getLeaderPerson();

    // calculate the cumulative total budget
    totalCCAFSBudget = budgetManager.calculateTotalProjectBudgetByType(projectID, BudgetType.W1_W2.getValue());
    totalBilateralBudget =
      budgetManager.calculateTotalProjectBudgetByType(projectID, BudgetType.W3_BILATERAL.getValue());

    List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(this.getCurrentUser().getId()));
    List<ProjectEvaluation> lstEvaluations = new ArrayList<ProjectEvaluation>();

    // evaluationUser.setId(new Long(-1));
    int liaisonInstitutionID = 0;
    try {
      liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().get(0).getId();
    } catch (Exception e) {
      liaisonInstitutionID = 2;
    }
    currentLiaisonInstitution = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);
    if (currentLiaisonInstitution.getIpProgram() == null) {
      currentLiaisonInstitution.setIpProgram("1");
    }


    lstEvaluations.addAll(projectEvaluationManager.getEvaluationsProject(projectID));
    for (UserRole userRole : roles) {
      ProjectEvaluation evaluationUser = null;

      switch (userRole.getId()) {

        case APConstants.ROLE_FLAGSHIP_PROGRAM_LEADER:
        case APConstants.ROLE_REGIONAL_PROGRAM_LEADER:
          evaluationUser = new ProjectEvaluation();
          evaluationUser.setProjectId(new Long(projectID));
          evaluationUser.setYear(this.getCurrentReportingYear());
          evaluationUser.setActive(true);
          evaluationUser.setActiveSince(new Date());
          evaluationUser.setProgramId(new Long(currentLiaisonInstitution.getIpProgram()));
          evaluationUser.setModifiedBy(new Long(this.getCurrentUser().getId()));
          evaluationUser.setTypeEvaluation(userRole.getAcronym());

          if (!this.existEvaluation(lstEvaluations, evaluationUser)) {
            lstEvaluations.add(evaluationUser);
          }


          break;

        case APConstants.ROLE_PROJECT_LEADER:

          Project p = projectManager.getProjectBasicInfo(projectID);
          if (p.getLeaderUserId() == this.getCurrentUser().getId()) {

            evaluationUser = new ProjectEvaluation();
            evaluationUser.setProjectId(new Long(projectID));
            evaluationUser.setYear(this.getCurrentReportingYear());
            evaluationUser.setActive(true);
            evaluationUser.setActiveSince(new Date());
            evaluationUser.setModifiedBy(new Long(this.getCurrentUser().getId()));

            evaluationUser.setTypeEvaluation(userRole.getAcronym());

            if (!this.existEvaluation(lstEvaluations, evaluationUser)) {
              lstEvaluations.add(evaluationUser);
            }


          }


          break;

        case APConstants.ROLE_EXTERNAL_EVALUATOR:

        case APConstants.ROLE_COORDINATING_UNIT:


          evaluationUser = new ProjectEvaluation();
          evaluationUser.setProjectId(new Long(projectID));
          evaluationUser.setYear(this.getCurrentReportingYear());
          evaluationUser.setActive(true);
          evaluationUser.setActiveSince(new Date());
          evaluationUser.setModifiedBy(new Long(this.getCurrentUser().getId()));
          evaluationUser.setTypeEvaluation(userRole.getAcronym());


          if (!this.existEvaluation(lstEvaluations, evaluationUser)) {
            lstEvaluations.add(evaluationUser);
          }


          break;

      }


    }


    for (ProjectEvaluation projectEvaluation : lstEvaluations) {
      projectEvaluation.setRankingOutcomes(projectEvaluation.getRankingOutcomes() * STAR_DIV);
      projectEvaluation.setRankingOutputs(projectEvaluation.getRankingOutputs() * STAR_DIV);
      projectEvaluation
        .setRankingParternshipComunnication(projectEvaluation.getRankingParternshipComunnication() * STAR_DIV);
      projectEvaluation.setRankingQuality(projectEvaluation.getRankingQuality() * STAR_DIV);
      projectEvaluation.setRankingResponseTeam(projectEvaluation.getRankingResponseTeam() * STAR_DIV);
    }

    Collections.sort(lstEvaluations, new Comparator<ProjectEvaluation>() {

      @Override
      public int compare(ProjectEvaluation s1, ProjectEvaluation s2) {
        Boolean p1 = s1.isSubmited();
        Boolean p2 = s2.isSubmited();
        return p1.compareTo(p2) * -1;
      }
    });

    project.setEvaluations(lstEvaluations);


  }

  @Override
  public String save() {

    int index = Integer.parseInt(this.getParameterValue("evaluationIndex"));

    ProjectEvaluation projectEvaluation = project.getEvaluations().get(index);

    projectEvaluation.setRankingOutcomes(projectEvaluation.getRankingOutcomes() / STAR_DIV);
    projectEvaluation.setRankingOutputs(projectEvaluation.getRankingOutputs() / STAR_DIV);
    projectEvaluation
      .setRankingParternshipComunnication(projectEvaluation.getRankingParternshipComunnication() / STAR_DIV);
    projectEvaluation.setRankingResponseTeam(projectEvaluation.getRankingResponseTeam() / STAR_DIV);
    projectEvaluation.setRankingQuality(projectEvaluation.getRankingQuality() / STAR_DIV);
    projectEvaluation.setTotalScore(projectEvaluation.calculateTotalScore());
    projectEvaluation.setModifiedBy(new Long(this.getCurrentUser().getId()));
    projectEvaluationManager.saveProjectEvalution(projectEvaluation, this.getCurrentUser(), "");


    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty())

    {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("saving.saved") + validationMessage);
    } else

    {
      this.addActionMessage(this.getText("saving.saved"));
    }
    return SUCCESS;

  }


  /**
   * Send Email notification when the user submit the evaluation.
   * 
   * @param submitedEvaluation - The user submit evaluation
   */
  private void sendNotificationEmail(ProjectEvaluation submittedEvaluation) {
    // Building the email message
    StringBuilder message = new StringBuilder();


    String toEmail = null;
    String ccEmail = null;
    /*
     * put in array the information that contains the email.
     * [0] = User name that has been submitted the evaluation.
     * [1] = The evaluated project name.
     * [2] = the evaluated project id.
     */
    String[] values = new String[4];
    values[0] = this.getCurrentUser().getComposedCompleteName();
    values[1] = project.getTitle();
    values[2] = project.getStandardIdentifier(Project.EMAIL_SUBJECT_IDENTIFIER);

    if (submittedEvaluation.getProgramId() != null) {
      values[3] = this.getProgramEvaluation(submittedEvaluation);
    } else {
      values[3] = submittedEvaluation.getTypeEvaluation();
    }

    String subject = this.getText("evaluation.submit.email.subject", values);
    message.append(this.getText("evaluation.submit.email.message", values));
    message.append(this.getText("\n\n"));
    message.append(this.getText("planning.manageUsers.email.support"));
    message.append(this.getText("\n"));
    message.append(this.getText("planning.manageUsers.email.bye"));
    ByteBuffer buffer = null;
    String fileName = null;
    String contentType = null;

    switch (roleManager.getRoleByAcronym(submittedEvaluation.getTypeEvaluation()).getId()) {
      case APConstants.ROLE_FLAGSHIP_PROGRAM_LEADER:
      case APConstants.ROLE_PROGRAM_DIRECTOR_EVALUATOR:

        try {
          // Making the URL to get the report.
          URL pdfURL = new URL(config.getBaseUrl() + "/summaries/projectEvaluation.do?" + APConstants.PROJECT_REQUEST_ID
            + "=" + projectID);
          // Getting the file data.
          Map<String, Object> fileProperties = URLFileDownloader.getAsByteArray(pdfURL);
          buffer = fileProperties.get("byte_array") != null ? (ByteBuffer) fileProperties.get("byte_array") : null;
          fileName = fileProperties.get("filename") != null ? (String) fileProperties.get("filename") : null;
          contentType = fileProperties.get("mime_type") != null ? (String) fileProperties.get("mime_type") : null;
        } catch (MalformedURLException e) {
          // Do nothing.
          LOG.error("There was an error trying to get the URL to download the PDF file: " + e.getMessage());
        } catch (IOException e) {
          // Do nothing
          LOG.error("There was a problem trying to download the PDF file for the projectID=" + projectID + " : "
            + e.getMessage());
        }

        break;

    }

    sendMail = new SendMail(this.config);

    /*
     * ask if the application is in production to add the users
     * that send the notification else the email will send to developer team
     */
    if (this.config.isProduction()) {
      /*
       * TODO
       * should ask how will users send the email.
       * for now only send the email to the user that submitted the project evaluation.
       */
      toEmail = this.getCurrentUser().getEmail();
    } else {
      toEmail = this.config.getEmailNotification();
    }

    if (buffer != null && fileName != null && contentType != null) {
      sendMail.send(toEmail, ccEmail, null, subject, message.toString(), buffer.array(), contentType, fileName);
    } else {
      sendMail.send(toEmail, ccEmail, null, subject, message.toString(), null, null, null);
    }
  }


  public void setPartnerPerson(PartnerPerson partnerPerson) {
    this.partnerPerson = partnerPerson;
  }


  public void setProject(Project project) {
    this.project = project;
  }


  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setProjectLeader(ProjectPartner projectLeader) {
    this.projectLeader = projectLeader;
  }

  public void setTotalBilateralBudget(double totalBilateralBudget) {
    this.totalBilateralBudget = totalBilateralBudget;
  }

  public void setTotalCCAFSBudget(double totalCCAFSBudget) {
    this.totalCCAFSBudget = totalCCAFSBudget;
  }

  @Override
  public String submit() {

    int index = Integer.parseInt(this.getParameterValue("evaluationIndex"));

    ProjectEvaluation projectEvaluation = project.getEvaluations().get(index);
    validator.validate(this, project, project.getEvaluations().get(0), this.getCycleName());

    if (!validator.hasErrors) {
      projectEvaluation.setSubmited(true);
      projectEvaluation.setSubmittedDate(new Date());

    }
    projectEvaluation.setRankingOutcomes(projectEvaluation.getRankingOutcomes() / STAR_DIV);
    projectEvaluation.setRankingOutputs(projectEvaluation.getRankingOutputs() / STAR_DIV);
    projectEvaluation
      .setRankingParternshipComunnication(projectEvaluation.getRankingParternshipComunnication() / STAR_DIV);
    projectEvaluation.setRankingResponseTeam(projectEvaluation.getRankingResponseTeam() / STAR_DIV);
    projectEvaluation.setRankingQuality(projectEvaluation.getRankingQuality() / STAR_DIV);
    projectEvaluation.setTotalScore(projectEvaluation.calculateTotalScore());
    projectEvaluation.setModifiedBy(new Long(this.getCurrentUser().getId()));

    int iReturn = projectEvaluationManager.saveProjectEvalution(projectEvaluation, this.getCurrentUser(), "");

    // if the evaluation has submitted, send the email notification
    if (iReturn > 0 && !validator.hasErrors) {
      this.sendNotificationEmail(projectEvaluation);
    }
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("saving.savednotSubmit") + "" + validationMessage);
    } else {
      this.addActionMessage(this.getText("saving.saved"));
    }
    return SUCCESS;

  }

  @Override
  public void validate() {
    // validate only the request user evaluation.
    if (save) {
      validator.validate(this, project, project.getEvaluations().get(0), this.getCycleName());
    }
  }
}

