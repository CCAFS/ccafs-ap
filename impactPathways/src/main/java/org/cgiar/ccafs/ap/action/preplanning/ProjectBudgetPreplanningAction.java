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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.APConfig;

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
  // year contains the year in which the budget should be saved
  private int year;
  // targetYear contains the year to which the users should
  // be redirected once them press a tab
  private int targetYear;
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
  private double totalW1W2W3BilateralBudget;
  private double totalW1W2W3BilateralBudgetByYear;
  private double totalW1W2Budget;
  private double totalW1W2BudgetByYear;
  private double totalLeveragedBudget;
  private double leveragedBudgetByYear;

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
  @Deprecated
  private Map<String, Budget> generateMapBudgets(int year) {
    Map<String, Budget> budgetsMap = new HashMap<String, Budget>();

    // project partners
    // for (ProjectPartner projectPartner : projectPartners) {
    // boolean w1_w2 = false;
    // boolean w3_bilateral = false;
    // boolean leveraged = false;
    // boolean w1_w2_partners = false;
    // boolean w1_w2_others = false;
    // boolean w3_bilateral_partners = false;
    // boolean w3_bilateral_others = false;
    // boolean w1_w2_gender = false;
    // boolean w3_bilateral_gender = false;
    //
    // for (Budget budget : project.getBudgets()) {
    // if (budget.getInstitution().getId() == projectPartner.getInstitution().getId() && budget.getYear() == year) {
    // if (budget.getType().getValue() == BudgetType.W1_W2.getValue()) {
    // w1_w2 = true;
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL.getValue()) {
    // w3_bilateral = true;
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.LEVERAGED.getValue()) {
    // leveraged = true;
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.LEVERAGED.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W1_W2_PARTNERS.getValue()) {
    // w1_w2_partners = true;
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2_PARTNERS.name(), budget);
    // } else if (budget.getType().getValue() == BudgetType.W1_W2_OTHER.getValue()) {
    // w1_w2_others = true;
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2_OTHER.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL_PARTNERS.getValue()) {
    // w3_bilateral_partners = true;
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_PARTNERS.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL_OTHERS.getValue()) {
    // w3_bilateral_others = true;
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_OTHERS.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W1_W2_GENDER.getValue()) {
    // w1_w2_gender = true;
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2_GENDER.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL_GENDER.getValue()) {
    // w3_bilateral_gender = true;
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_GENDER.name(),
    // budget);
    // }
    // }
    // }
    // if (w1_w2 == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W1_W2);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2.name(), newBudget);
    // }
    // if (w3_bilateral == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL.name(),
    // newBudget);
    // }
    // if (leveraged == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.LEVERAGED);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.LEVERAGED.name(),
    // newBudget);
    // }
    // if (w1_w2_partners == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W1_W2_PARTNERS);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2_PARTNERS.name(),
    // newBudget);
    // }
    // if (w1_w2_others == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W1_W2_OTHER);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2_OTHER.name(),
    // newBudget);
    // }
    // if (w3_bilateral_partners == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL_PARTNERS);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_PARTNERS.name(),
    // newBudget);
    // }
    // if (w3_bilateral_others == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL_OTHERS);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_OTHERS.name(),
    // newBudget);
    // }
    // if (w1_w2_gender == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W1_W2_GENDER);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W1_W2_GENDER.name(),
    // newBudget);
    // }
    // if (w3_bilateral_gender == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(projectPartner.getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL_GENDER);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(
    // year + "-" + projectPartner.getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_GENDER.name(),
    // newBudget);
    // }
    // }
    // // Project leader
    // boolean w1_w2 = false;
    // boolean w3_bilateral = false;
    // boolean leveraged = false;
    // boolean w1_w2_partners = false;
    // boolean w1_w2_others = false;
    // boolean w3_bilateral_partners = false;
    // boolean w3_bilateral_others = false;
    // boolean w1_w2_gender = false;
    // boolean w3_bilateral_gender = false;
    //
    // for (Budget budget : project.getBudgets()) {
    // if (budget.getInstitution().getId() == project.getLeader().getInstitution().getId() && budget.getYear() == year)
    // {
    // if (budget.getType().getValue() == BudgetType.W1_W2.getValue()) {
    // w1_w2 = true;
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL.getValue()) {
    // w3_bilateral = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL.name(), budget);
    // } else if (budget.getType().getValue() == BudgetType.LEVERAGED.getValue()) {
    // leveraged = true;
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.LEVERAGED.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W1_W2_PARTNERS.getValue()) {
    // w1_w2_partners = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2_PARTNERS.name(), budget);
    // } else if (budget.getType().getValue() == BudgetType.W1_W2_OTHER.getValue()) {
    // w1_w2_others = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2_OTHER.name(), budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL_PARTNERS.getValue()) {
    // w3_bilateral_partners = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_PARTNERS.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL_OTHERS.getValue()) {
    // w3_bilateral_others = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_OTHERS.name(),
    // budget);
    // } else if (budget.getType().getValue() == BudgetType.W1_W2_GENDER.getValue()) {
    // w1_w2_gender = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2_GENDER.name(), budget);
    // } else if (budget.getType().getValue() == BudgetType.W3_BILATERAL_GENDER.getValue()) {
    // w3_bilateral_gender = true;
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_GENDER.name(),
    // budget);
    // }
    // }
    // }
    // if (w1_w2 == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W1_W2);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2.name(),
    // newBudget);
    // }
    // if (w3_bilateral == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL.name(),
    // newBudget);
    // }
    // if (leveraged == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.LEVERAGED);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.LEVERAGED.name(),
    // newBudget);
    // }
    // if (w1_w2_partners == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W1_W2_PARTNERS);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" +
    // BudgetType.W1_W2_PARTNERS.name(),
    // newBudget);
    // }
    // if (w1_w2_others == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W1_W2_OTHER);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2_OTHER.name(),
    // newBudget);
    // }
    // if (w3_bilateral_partners == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL_PARTNERS);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_PARTNERS.name(),
    // newBudget);
    // }
    // if (w3_bilateral_others == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL_OTHERS);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_OTHERS.name(),
    // newBudget);
    // }
    // if (w1_w2_gender == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W1_W2_GENDER);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W1_W2_GENDER.name(),
    // newBudget);
    // }
    // if (w3_bilateral_gender == false) {
    // Budget newBudget = new Budget();
    // newBudget.setId(-1);
    // newBudget.setInstitution(project.getLeader().getInstitution());
    // newBudget.setType(BudgetType.W3_BILATERAL_GENDER);
    // newBudget.setAmount(0);
    // newBudget.setYear(year);
    // budgetsMap.put(
    // year + "-" + project.getLeader().getInstitution().getId() + "-" + BudgetType.W3_BILATERAL_GENDER.name(),
    // newBudget);
    // }

    return budgetsMap;
  }

  public List<Institution> getAllInstitutions() {
    return allInstitutions;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public double getLeveragedBudgetByYear() {
    return leveragedBudgetByYear;
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

  public int getTargetYear() {
    return targetYear;
  }

  public double getTotalBudget() {
    return totalBudget;
  }

  public double getTotalBudgetByYear() {
    return totalBudgetByYear;
  }

  public double getTotalLeveragedBudget() {
    return totalLeveragedBudget;
  }

  public double getTotalW1W2Budget() {
    return totalW1W2Budget;
  }

  public double getTotalW1W2BudgetByYear() {
    return totalW1W2BudgetByYear;
  }

  public double getTotalW1W2W3BilateralBudget() {
    return totalW1W2W3BilateralBudget;
  }

  public double getTotalW1W2W3BilateralBudgetByYear() {
    return totalW1W2W3BilateralBudgetByYear;
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

    // Getting the project id from the URL parameters.
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    String parameter;
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);
    project.setBudgets(budgetManager.getCCAFSBudgets(projectID));


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


        // This code needs to be updated.
        // We validate if the partner leader is already in the employees table. If so, we get this
        // information. If not, we load the information from expected project leader.
        // User projectLeader = projectManager.getProjectLeader(project.getId());
        // if the official leader is defined.
        // if (projectLeader != null) {
        // project.setLeader(projectLeader);
        // } else {
        // project.setLeader(projectManager.getExpectedProjectLeader(projectID));
        // }
        // if the project leader is not defined, stop here.
        if (project.getLeader() != null) {

          // Getting the Total Overall Project Budget
          totalBudget = budgetManager.calculateTotalOverallBudget(projectID);
          totalBudgetByYear = budgetManager.calculateTotalOverallBudgetByYear(projectID, year);
          totalW1W2W3BilateralBudget = budgetManager.calculateProjectW1W2W3BilateralBudget(projectID);
          totalW1W2W3BilateralBudgetByYear = budgetManager.calculateProjectW1W2W3BilateralBudgetByYear(projectID, year);
          totalW1W2Budget = budgetManager.calculateTotalProjectW1W2(projectID);
          totalW1W2BudgetByYear = budgetManager.calculateTotalProjectW1W2ByYear(projectID, year);
          leveragedBudgetByYear = budgetManager.calculateProjectLeveragedBudgetByYear(projectID, year);
          totalLeveragedBudget = budgetManager.calculateProjectTotalLeveragedBudget(projectID);

          // Getting all the project partners.
          projectPartners = partnerManager.getProjectPartners(projectID);

          // Getting the list of budgets.
          project.setBudgets(budgetManager.getBudgetsByYear(project.getId(), year));
          // Creating budgets that do not exist.
          mapBudgets = this.generateMapBudgets(year);

          if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
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

    // Saving project budgets
    for (Budget budget : project.getBudgets()) {
      boolean saved = budgetManager.saveBudget(projectID, budget);

      if (!saved) {
        success = false;
      }
    }

    if (!success) {
      this.addActionError(this.getText("saving.problem"));
      return BaseAction.INPUT;
    } else {
      this.addActionMessage(this.getText("saving.success",
        new String[] {this.getText("preplanning.projectBudget.title")}));
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

  public void setTargetYear(int targetYear) {
    this.targetYear = targetYear;
  }

  public void setTotalBudget(double totalBudget) {
    this.totalBudget = totalBudget;
  }

  public void setTotalBudgetByYear(double totalBudgetByYear) {
    this.totalBudgetByYear = totalBudgetByYear;
  }

  public void setTotalW1W2W3BilateralBudget(double totalW1W2W3BilateralBudget) {
    this.totalW1W2W3BilateralBudget = totalW1W2W3BilateralBudget;
  }

  public void setTotalW1W2W3BilateralBudgetByYear(double totalW1W2W3BilateralBudgetByYear) {
    this.totalW1W2W3BilateralBudgetByYear = totalW1W2W3BilateralBudgetByYear;
  }

  public void setYear(int year) {
    this.year = year;
  }


}
