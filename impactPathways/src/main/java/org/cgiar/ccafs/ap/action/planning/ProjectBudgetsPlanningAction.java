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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.BudgetOverheadManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.validation.planning.ProjectBudgetPlanningValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectBudgetsPlanningAction extends BaseAction {

  private static final long serialVersionUID = -6482057269111538580L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectBudgetsPlanningAction.class);

  // Managers
  private BudgetManager budgetManager;
  private ProjectPartnerManager projectPartnerManager;
  private ProjectManager projectManager;
  private ProjectCofinancingLinkageManager linkedProjectManager;
  private BudgetOverheadManager overheadManager;
  private HistoryManager historyManager;

  private ProjectBudgetPlanningValidator validator;

  // Model for the back-end
  private int projectID;
  private Project project;
  private Set<Institution> projectPPAPartners;
  private List<Integer> allYears;
  private int year;
  private boolean hasLeader;
  private boolean invalidYear;
  private double totalCCAFSBudget;
  private double totalBilateralBudget;
  private Project previousProject;

  @Inject
  public ProjectBudgetsPlanningAction(APConfig config, BudgetManager budgetManager,
    BudgetOverheadManager overheadManager, ProjectBudgetPlanningValidator validator,
    ProjectPartnerManager projectPartnerManager, ProjectCofinancingLinkageManager linkedProjectManager,
    ProjectManager projectManager, HistoryManager historyManager) {
    super(config);
    this.budgetManager = budgetManager;
    this.projectPartnerManager = projectPartnerManager;
    this.projectManager = projectManager;
    this.linkedProjectManager = linkedProjectManager;
    this.historyManager = historyManager;
    this.overheadManager = overheadManager;
    this.validator = validator;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public Budget getBilateralCofinancingBudget(int projectID, int cofinanceProjectID, int year) {
    List<Budget> budgets = budgetManager.getBudgetsByYear(projectID, year);

    for (Budget budget : budgets) {
      if (budget.getCofinancingProject() != null) {
        if (budget.getCofinancingProject().getId() == cofinanceProjectID) {
          return budget;
        }
      }
    }
    return null;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public Set<Institution> getProjectPPAPartners() {
    return projectPPAPartners;
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

  public String getW1W2BudgetLabel() {
    return this.getText("planning.projectBudget.W1W2");
  }

  public String getW1W2BudgetType() {
    return BudgetType.W1_W2.toString();
  }

  public String getW3BilateralBudgetLabel() {
    return this.getText("planning.projectBudget.W3Bilateral");
  }

  public String getW3BilateralBudgetType() {
    return BudgetType.W3_BILATERAL.toString();
  }

  public int getYear() {
    return year;
  }

  public boolean isHasLeader() {
    return hasLeader;
  }


  public boolean isInvalidYear() {
    return invalidYear;
  }


  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }


  @Override
  public void prepare() throws Exception {
    previousProject = new Project();
    // Getting the project id from the URL parameter
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);

    // If project is CCAFS cofounded, we should load the core projects linked to it.
    if (!project.isBilateralProject()) {
      project.setLinkedProjects(linkedProjectManager.getLinkedBilateralProjects(projectID));
    } else {
      project.setLinkedProjects(linkedProjectManager.getLinkedCoreProjects(projectID));

      project.setOverhead(overheadManager.getProjectBudgetOverhead(projectID));
    }

    if (project.getLinkedProjects() != null) {
      List<Project> linkedProjects = new ArrayList<>();
      for (Project p : project.getLinkedProjects()) {
        linkedProjects.add(new Project(p.getId()));
      }
      previousProject.setLinkedProjects(linkedProjects);
    }

    // Getting the Project Leader.
    List<ProjectPartner> ppArray =
      projectPartnerManager.getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PL);
    if (!ppArray.isEmpty()) {
      project.setLeader(ppArray.get(0));
      hasLeader = true;
    } else {
      hasLeader = false;
    }

    totalCCAFSBudget = budgetManager.calculateTotalProjectBudgetByType(projectID, BudgetType.W1_W2.getValue());
    totalBilateralBudget =
      budgetManager.calculateTotalProjectBudgetByType(projectID, BudgetType.W3_BILATERAL.getValue());

    // Getting PPA Partners
    project.setPPAPartners(projectPartnerManager.getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PPA));

    // Getting the list of PPA Partner institutions
    projectPPAPartners = new HashSet<Institution>();
    for (ProjectPartner ppaPartner : project.getPPAPartners()) {
      projectPPAPartners.add(ppaPartner.getInstitution());
    }

    // Remove the project leader from the list of PPA partner in case it is present.
    if (project.getLeader() != null) {
      projectPPAPartners.remove(project.getLeader().getInstitution());
    }

    allYears = project.getAllYears();
    invalidYear = allYears.isEmpty();
    if (!allYears.isEmpty()) {

      // Getting the year from the URL parameters.
      try {
        String parameter = this.getRequest().getParameter(APConstants.YEAR_REQUEST);
        year = (parameter != null) ? Integer.parseInt(StringUtils.trim(parameter)) : config.getPlanningCurrentYear();
      } catch (NumberFormatException e) {
        LOG.warn("-- prepare() > There was an error parsing the year '{}'.", year);
        // Set the current year as default
        year = config.getPlanningCurrentYear();
      }

      if (!allYears.contains(new Integer(year))) {
        year = config.getPlanningCurrentYear();
      }

      if (project.getLeader() != null) {
        // Getting the list of budgets.
        project.setBudgets(budgetManager.getBudgetsByYear(project.getId(), year));
      } else {
        hasLeader = false;
      }


      super.setHistory(historyManager.getProjectBudgetHistory(projectID));

    } else {
      invalidYear = true;
    }

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getBudgets() != null) {
        project.getBudgets().clear();
      }

      if (project.getLinkedProjects() != null) {
        project.getLinkedProjects().clear();
      }
    }
  }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectBudget()) {
      boolean success = true, saved = false;

      for (Budget budget : project.getBudgets()) {
        // Only can save the budgets to which the user is authorized
        if (budget.getType().isCCAFSBudget() && !securityContext.canUpdateAnnualW1W2Budget()) {
          continue;
        }

        if (budget.getType().isBilateral() && !securityContext.canUpdateAnnualBilateralBudget()) {
          continue;
        }

        if (budget.getCofinancingProject() == null) {
          saved = budgetManager.saveBudget(projectID, budget, this.getCurrentUser(), this.getJustification());
        } else {
          Project cofinancingProject = budget.getCofinancingProject();
          // Getting the Project Leader.
          List<ProjectPartner> ppArray =
            projectPartnerManager.getProjectPartners(cofinancingProject.getId(), APConstants.PROJECT_PARTNER_PL);
          if (!ppArray.isEmpty()) {
            cofinancingProject.setLeader(ppArray.get(0));

            // The co-financing budget belongs to the project which receive it.
            budget.setCofinancingProject(project);
            budget.setInstitution(cofinancingProject.getLeader().getInstitution());
            saved =
              budgetManager.saveBudget(cofinancingProject.getId(), budget, this.getCurrentUser(),
                this.getJustification());
          } else {
            String projectID = "2014-" + cofinancingProject.getId();
            this
              .addActionWarning(this.getText("planning.projectBudget.invalidCoreComponent", new String[] {projectID}));
          }
        }

        if (!saved) {
          success = false;
        }
      }

      if (project.isBilateralProject()) {
        // Save the budget overhead
        overheadManager.saveProjectBudgetOverhead(project, this.getCurrentUser(), this.getJustification());
      }

      // Save the linked projects
      List<Integer> linkedProjectsToDelete = new ArrayList<>();
      for (Project p : previousProject.getLinkedProjects()) {
        if (!project.getLinkedProjects().contains(p)) {
          linkedProjectsToDelete.add(p.getId());
        }
      }

      if (!linkedProjectsToDelete.isEmpty()) {
        if (project.isBilateralProject()) {
          linkedProjectManager.deletedLinkedCoreProjects(project, linkedProjectsToDelete, this.getCurrentUser(),
            this.getJustification());
        } else {
          linkedProjectManager.deletedLinkedBilateralProjects(project, linkedProjectsToDelete, this.getCurrentUser(),
            this.getJustification());
        }
      }

      // Then save the new core projects linked
      if (!project.getLinkedProjects().isEmpty()) {
        if (project.isBilateralProject()) {
          linkedProjectManager.saveLinkedCoreProjects(project, this.getCurrentUser(), this.getJustification());
        } else {
          linkedProjectManager.saveLinkedBilateralProjects(project, this.getCurrentUser(), this.getJustification());
        }
      }

      // Adjust the type of all projects according to their links with other projects.
      projectManager.updateProjectTypes();
      budgetManager.deleteBudgetsWithNoLinkToInstitutions(projectID);

      if (!success) {
        this.addActionError(this.getText("saving.problem"));
        return BaseAction.INPUT;
      } else {
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
    }
    return NOT_AUTHORIZED;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setTotalBilateralBudget(double totalBilateralBudget) {
    this.totalBilateralBudget = totalBilateralBudget;
  }

  public void setTotalCCAFSBudget(double totalCCAFSBudget) {
    this.totalCCAFSBudget = totalCCAFSBudget;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project);
    }
  }
}
