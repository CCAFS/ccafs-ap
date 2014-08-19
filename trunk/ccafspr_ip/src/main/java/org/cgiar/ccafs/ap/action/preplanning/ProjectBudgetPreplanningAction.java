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
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project Budget Action.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class ProjectBudgetPreplanningAction extends BaseAction {

  private static final long serialVersionUID = 6828801151634526985L;

  // LOG
  public static Logger LOG = LoggerFactory.getLogger(ProjectBudgetPreplanningAction.class);

  // Model for the back-end
  private int projectID;
  private int year;
  private Project project;

  // Model for the front-end
  private List<Integer> allYears;
  private List<Institution> allInstitutions;
  private List<Institution> leveragedInstitutions;
  private List<ProjectPartner> projectPartners;
  private User projectLeader;
  private boolean hasLeader;
  private boolean invalidYear;
  private Map<String, Budget> mapBudgets;
  private double totalBudget;
  private double totalBudgetByYear;
  private double totalCCAFSBudget;
  private double totalCCAFSBudgetByYear;

  // Managers
  private ProjectManager projectManager;
  private BudgetManager budgetManager;
  private InstitutionManager institutionManager;
  private ProjectPartnerManager partnerManager;

  @Inject
  public ProjectBudgetPreplanningAction(APConfig config, ProjectManager projectManager, BudgetManager budgetManager,
    InstitutionManager institutionManager, ProjectPartnerManager partnerManager) {
    super(config);
    this.projectManager = projectManager;
    this.budgetManager = budgetManager;
    this.institutionManager = institutionManager;
    this.partnerManager = partnerManager;
    this.hasLeader = true;
    this.invalidYear = false;
  }

  /**
   * This method returns a Map of Budgets. The key of this map is a composition of the year, the institution id and the
   * type.
   * e.g. 2014-9-W1
   * Where 2014 is the year, 9 is the institution identifier and W1 is the budget type.
   * If the budget is not in the database, this method will create a new one with an id=-1 and amount=0.
   * 
   * @return a Map of budgets as was described above.
   */
  private Map<String, Budget> generateMapBudgets(int year) {
    Map<String, Budget> budgetsMap = new HashMap<String, Budget>();

    // project partners
    for (ProjectPartner projectPartner : projectPartners) {
      boolean w1 = false;
      boolean w2 = false;
      boolean w3 = false;
      boolean bilateral = false;
      for (Budget budget : project.getBudgets()) {
        if (budget.getInstitution().getId() == projectPartner.getPartner().getId() && budget.getYear() == year) {
          if (budget.getType().getValue() == BudgetType.W1.getValue()) {
            w1 = true;
            budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.W1.name(), budget);
          } else if (budget.getType().getValue() == BudgetType.W2.getValue()) {
            w2 = true;
            budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.W2.name(), budget);
          } else if (budget.getType().getValue() == BudgetType.W3.getValue()) {
            w3 = true;
            budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.W3.name(), budget);
          } else if (budget.getType().getValue() == BudgetType.BILATERAL.getValue()) {
            bilateral = true;
            budgetsMap
              .put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.BILATERAL.name(), budget);
          }
        }
      }
      if (w1 == false) {
        Budget newBudget = new Budget();
        newBudget.setId(-1);
        newBudget.setInstitution(projectPartner.getPartner());
        newBudget.setType(BudgetType.W1);
        newBudget.setAmount(0);
        newBudget.setYear(year);
        budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.W1.name(), newBudget);
      }
      if (w2 == false) {
        Budget newBudget = new Budget();
        newBudget.setId(-1);
        newBudget.setInstitution(projectPartner.getPartner());
        newBudget.setType(BudgetType.W2);
        newBudget.setAmount(0);
        newBudget.setYear(year);
        budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.W2.name(), newBudget);
      }
      if (w3 == false) {
        Budget newBudget = new Budget();
        newBudget.setId(-1);
        newBudget.setInstitution(projectPartner.getPartner());
        newBudget.setType(BudgetType.W3);
        newBudget.setAmount(0);
        newBudget.setYear(year);
        budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.W3.name(), newBudget);
      }
      if (bilateral == false) {
        Budget newBudget = new Budget();
        newBudget.setId(-1);
        newBudget.setInstitution(projectPartner.getPartner());
        newBudget.setType(BudgetType.BILATERAL);
        newBudget.setAmount(0);
        newBudget.setYear(year);
        budgetsMap.put(year + "-" + projectPartner.getPartner().getId() + "-" + BudgetType.BILATERAL.name(), newBudget);
      }
    }
    // Project leader
    boolean w1 = false;
    boolean w2 = false;
    boolean w3 = false;
    boolean bilateral = false;
    for (Budget budget : project.getBudgets()) {
      if (budget.getInstitution().getId() == project.getLeader().getCurrentInstitution().getId()
        && budget.getYear() == year) {
        if (budget.getType().getValue() == BudgetType.W1.getValue()) {
          w1 = true;
          budgetsMap.put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.W1.name(),
            budget);
        } else if (budget.getType().getValue() == BudgetType.W2.getValue()) {
          w2 = true;
          budgetsMap.put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.W2.name(),
            budget);
        } else if (budget.getType().getValue() == BudgetType.W3.getValue()) {
          w3 = true;
          budgetsMap.put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.W3.name(),
            budget);
        } else if (budget.getType().getValue() == BudgetType.BILATERAL.getValue()) {
          bilateral = true;
          budgetsMap.put(
            year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.BILATERAL.name(),
            budget);
        }
      }
    }
    if (w1 == false) {
      Budget newBudget = new Budget();
      newBudget.setId(-1);
      newBudget.setInstitution(project.getLeader().getCurrentInstitution());
      newBudget.setType(BudgetType.W1);
      newBudget.setAmount(0);
      newBudget.setYear(year);
      budgetsMap.put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.W1.name(),
        newBudget);
    }
    if (w2 == false) {
      Budget newBudget = new Budget();
      newBudget.setId(-1);
      newBudget.setInstitution(project.getLeader().getCurrentInstitution());
      newBudget.setType(BudgetType.W2);
      newBudget.setAmount(0);
      newBudget.setYear(year);
      budgetsMap.put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.W2.name(),
        newBudget);
    }
    if (w3 == false) {
      Budget newBudget = new Budget();
      newBudget.setId(-1);
      newBudget.setInstitution(project.getLeader().getCurrentInstitution());
      newBudget.setType(BudgetType.W3);
      newBudget.setAmount(0);
      newBudget.setYear(year);
      budgetsMap.put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.W3.name(),
        newBudget);
    }
    if (bilateral == false) {
      Budget newBudget = new Budget();
      newBudget.setId(-1);
      newBudget.setInstitution(project.getLeader().getCurrentInstitution());
      newBudget.setType(BudgetType.BILATERAL);
      newBudget.setAmount(0);
      newBudget.setYear(year);
      budgetsMap
        .put(year + "-" + project.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.BILATERAL.name(),
          newBudget);
    }

    // Leveraged
    for (Institution leveraged : leveragedInstitutions) {
      boolean isLeveraged = false;
      for (Budget budget : project.getBudgets()) {
        if (budget.getInstitution().getId() == leveraged.getId() && budget.getYear() == year) {
          if (budget.getType().getValue() == BudgetType.LEVERAGED.getValue()) {
            isLeveraged = true;
            budgetsMap.put(year + "-" + leveraged.getId() + "-" + BudgetType.LEVERAGED.name(), budget);
          }
        }
      }
      if (isLeveraged == false) {
        Budget newBudget = new Budget();
        newBudget.setId(-1);
        newBudget.setInstitution(leveraged);
        newBudget.setType(BudgetType.W1);
        newBudget.setAmount(0);
        newBudget.setYear(year);
        budgetsMap.put(year + "-" + leveraged.getId() + "-" + BudgetType.LEVERAGED.name(), newBudget);
      }
    }

    return budgetsMap;
  }

  public List<Institution> getAllInstitutions() {
    return allInstitutions;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public List<Institution> getLeveragedInstitutions() {
    return leveragedInstitutions;
  }

  public Map<String, Budget> getMapBudgets() {
    return mapBudgets;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public User getProjectLeader() {
    return projectLeader;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  public double getTotalBudget() {
    return totalBudget;
  }

  public double getTotalBudgetByYear() {
    return totalBudgetByYear;
  }

  public double getTotalCCAFSBudget() {
    return totalCCAFSBudget;
  }

  public double getTotalCCAFSBudgetByYear() {
    return totalCCAFSBudgetByYear;
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

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Getting the project id from the URL parameters.
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    String parameter;
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);

    // Getting all the years of the project.
    allYears = project.getAllYears();
    // If there are not years, we stop here.
    if (allYears.size() > 0) {

      // Getting the year from the URL parameters.
      try {
        parameter = this.getRequest().getParameter(APConstants.YEAR_REQUEST);
        if (parameter != null) {
          year = Integer.parseInt(StringUtils.trim(parameter));
        } else {
          year = allYears.get(0);
        }
      } catch (NumberFormatException e) {
        LOG.error("-- prepare() > There was an error parsing the year '{}'.", year);
        return; // Stop here and go to the execute method.
      }

      if (allYears.contains(new Integer(year))) {


        // We validate if the partner leader is already in the employees table. If so, we get this
        // information. If not, we load the information from expected project leader.
        User projectLeader = projectManager.getProjectLeader(project.getId());
        // if the official leader is defined.
        if (projectLeader != null) {
          project.setLeader(projectLeader);
        } else {
          project.setLeader(projectManager.getExpectedProjectLeader(projectID));
        }
        // if the project leader is not defined, stop here.
        if (project.getLeader() != null) {

          // Getting the Total Overall Project Budget
          totalBudget = budgetManager.calculateTotalOverallBudget(projectID);
          totalBudgetByYear = budgetManager.calculateTotalOverallBudgetByYear(projectID, year);
          totalCCAFSBudget = budgetManager.calculateTotalCCAFSBudget(projectID);
          totalCCAFSBudgetByYear = budgetManager.calculateTotalCCAFSBudgetByYear(projectID, year);

          // Getting the list of institutions that are funding the project as leveraged.
          leveragedInstitutions = budgetManager.getLeveragedInstitutions(projectID);

          // Getting all the project partners.
          projectPartners = partnerManager.getProjectPartners(projectID);

          // Getting all the institutions.
          allInstitutions = institutionManager.getAllInstitutions();
          // Adding header - place holder.
          Institution headerInstitution = new Institution();
          headerInstitution.setId(-1);
          headerInstitution.setName(getText("preplanning.projectBudget.institutionList.header"));
          allInstitutions.add(0, headerInstitution);

          // Removing the institution that is already added as project partner:
          allInstitutions.remove(project.getLeader().getCurrentInstitution());
          // Removing those institutions that were added in project partners.
          for (ProjectPartner projectParner : projectPartners) {
            allInstitutions.remove(projectParner.getPartner());
          }
          // Removing those institutions that are leveraged.
          for (Institution leverage : leveragedInstitutions) {
            allInstitutions.remove(leverage);
          }

          // Getting the list of budgets.
          project.setBudgets(budgetManager.getBudgetsByYear(project.getId(), year));
          // Creating budgets that do not exist.
          mapBudgets = generateMapBudgets(year);

          if (getRequest().getMethod().equalsIgnoreCase("post")) {
            // Clear out the list if it has some element
            if (project.getBudgets() != null) {
              project.getBudgets().clear();
            }
          }

        } else {
          hasLeader = false;
        }
      } else {
        invalidYear = true;
      }
    }
  }

  @Override
  public String save() {
    boolean success = true;
    boolean deleted = true;

    // ---- Identify those deleted budgets from leverages.
    // Getting previous leverage budgets.
    List<Budget> previousLeveragedBudgets =
      budgetManager.getBudgetsByType(project.getId(), BudgetType.LEVERAGED.getValue());
    // Deleting budgets removed
    for (Budget budget : previousLeveragedBudgets) {
      if (!project.getBudgets().contains(budget)) {
        deleted = budgetManager.deleteBudget(budget.getId());
        if (!deleted) {
          success = false;
        }
      }
    }

    // Saving project budgets
    for (Budget budget : project.getBudgets()) {
      boolean saved = budgetManager.saveBudget(projectID, budget);
      if (budget.getType() == BudgetType.LEVERAGED) {
      }
      if (!saved) {
        success = false;
      }
    }

    if (!success) {
      addActionError(getText("saving.problem"));
      return BaseAction.INPUT;
    } else {
      addActionMessage(getText("saving.success", new String[] {getText("preplanning.projectBudget.title")}));
      return BaseAction.SUCCESS;
    }
  }

  public void setAllInstitutions(List<Institution> allInstitutions) {
    this.allInstitutions = allInstitutions;
  }

  public void setInvalidYear(boolean invalidYear) {
    this.invalidYear = invalidYear;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setTotalBudget(double totalBudget) {
    this.totalBudget = totalBudget;
  }

  public void setTotalBudgetByYear(double totalBudgetByYear) {
    this.totalBudgetByYear = totalBudgetByYear;
  }

  public void setTotalCCAFSBudget(double totalCCAFSBudget) {
    this.totalCCAFSBudget = totalCCAFSBudget;
  }

  public void setTotalCCAFSBudgetByYear(double totalCCAFSBudgetByYear) {
    this.totalCCAFSBudgetByYear = totalCCAFSBudgetByYear;
  }

  public void setYear(int year) {
    this.year = year;
  }


}
