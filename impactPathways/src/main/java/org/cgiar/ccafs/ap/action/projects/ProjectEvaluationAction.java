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
import org.cgiar.ccafs.ap.data.manager.ProjectEvalutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectEvaluation;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.projects.ProjectEvaluationValidator;
import org.cgiar.ccafs.security.data.manager.UserRoleManagerImpl;
import org.cgiar.ccafs.security.data.model.UserRole;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

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
  private ProjectEvaluationValidator validator;


  // Model for the back-end
  private Project project;


  private int projectID;
  private ProjectPartner projectLeader;
  private double totalCCAFSBudget;
  private double totalBilateralBudget;
  private PartnerPerson partnerPerson;

  @Inject
  public ProjectEvaluationAction(APConfig config, ProjectManager projectManager,
    ProjectPartnerManager projectPartnerManager, BudgetManager budgetManager,
    ProjectEvalutionManager projectEvaluationManager, IPProgramManager ipProgramManager,
    ProjectEvaluationValidator validator, UserRoleManagerImpl userRoleManager, UserManager userManager) {
    super(config);
    this.projectManager = projectManager;
    this.projectPartnerManager = projectPartnerManager;
    this.projectEvaluationManager = projectEvaluationManager;
    this.userRoleManager = userRoleManager;
    this.budgetManager = budgetManager;
    this.userManager = userManager;
    this.ipProgramManager = ipProgramManager;
    this.validator = validator;

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


  public double getTotalBilateralBudget() {
    return totalBilateralBudget;
  }


  public double getTotalCCAFSBudget() {
    return totalCCAFSBudget;
  }


  public String getUserName(int userId) {
    final User user = userManager.getUser(userId);
    return user.getComposedName();
  }


  @Override
  public String next() {
    final String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {

    super.prepare();

    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (final NumberFormatException e) {
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


    final List<ProjectEvaluation> lstEvaluations = new ArrayList<ProjectEvaluation>();
    ProjectEvaluation evaluationUser =
      projectEvaluationManager.getEvaluationProjectByUser(projectID, this.getCurrentUser().getId());
    if (evaluationUser == null) {
      evaluationUser = new ProjectEvaluation();
      evaluationUser.setProjectId(new Long(projectID));
      evaluationUser.setYear(this.getCurrentReportingYear());
      evaluationUser.setIsActive(true);
      evaluationUser.setActiveSince(new Date());
      evaluationUser.setUserId(new Long(this.getCurrentUser().getId()));
      // evaluationUser.setId(new Long(-1));
      final List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(this.getCurrentUser().getId()));
      for (final UserRole userRole : roles) {

        switch (userRole.getId()) {
          case APConstants.ROLE_ADMIN:
          case APConstants.ROLE_EXTERNAL_EVALUATOR:
          case APConstants.ROLE_FLAGSHIP_PROGRAM_LEADER:
          case APConstants.ROLE_PROJECT_LEADER:
          case APConstants.ROLE_COORDINATING_UNIT:
          case APConstants.ROLE_REGIONAL_PROGRAM_LEADER:
            evaluationUser.setTypeEvaluation(userRole.getAcronym());
            break;
        }
      }


    }
    lstEvaluations.add(evaluationUser);
    lstEvaluations
      .addAll(projectEvaluationManager.getEvaluationsProjectExceptUserId(projectID, this.getCurrentUser().getId()));


    project.setEvaluations(lstEvaluations);


  }


  @Override
  public String save() {


    for (final ProjectEvaluation projectEvaluation : project.getEvaluations()) {

      projectEvaluation.setTotalScore(projectEvaluation.calculateTotalScore());

      projectEvaluationManager.saveProjectEvalution(projectEvaluation, this.getCurrentUser(), "");
    }


    final Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      final String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("saving.saved") + validationMessage);
    } else {
      this.addActionMessage(this.getText("saving.saved"));
    }
    return SUCCESS;

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
  public void validate() {
    // validate only the request user evaluation.
    if (save) {
      validator.validate(this, project, project.getEvaluations().get(0), this.getCycleName());
    }
  }
}

