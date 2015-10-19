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
import org.cgiar.ccafs.ap.data.manager.BudgetByMogManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputBudget;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.planning.ProjectBudgetByMOGValidator;
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

public class ProjectBudgetByMOGPlanningAction extends BaseAction {

  private static final long serialVersionUID = 4022245671803576328L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectBudgetByMOGPlanningAction.class);

  // Managers
  private ProjectManager projectManager;
  private IPElementManager ipElementManager;
  private BudgetByMogManager budgetByMogManager;
  private BudgetManager budgetManager;
  private HistoryManager historyManager;


  // Validator
  private ProjectBudgetByMOGValidator projectBudgetByMOGValidator;
  // Model
  private Project project;
  private int projectID;
  private List<Integer> allYears;
  private int year;
  private double ccafsBudgetByYear;
  private double bilateralBudgetByYear;
  private double ccafsGenderPercentage;
  private double bilateralGenderPercentage;
  private int ccafsBudgetType;
  private int bilateralBudgetType;

  @Inject
  public ProjectBudgetByMOGPlanningAction(APConfig config, ProjectManager projectManager, BudgetManager budgetManager,
    IPElementManager ipElementManager, BudgetByMogManager budgetByMogManager, HistoryManager historyManager,
    ProjectBudgetByMOGValidator projectBudgetByMOGValidator) {
    super(config);
    this.projectManager = projectManager;
    this.ipElementManager = ipElementManager;
    this.budgetByMogManager = budgetByMogManager;
    this.budgetManager = budgetManager;
    this.historyManager = historyManager;
    this.projectBudgetByMOGValidator = projectBudgetByMOGValidator;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public double getBilateralBudgetByYear() {
    return bilateralBudgetByYear;
  }

  public int getBilateralBudgetType() {
    return bilateralBudgetType;
  }

  public double getBilateralGenderPercentage() {
    return bilateralGenderPercentage;
  }

  public double getCcafsBudgetByYear() {
    return ccafsBudgetByYear;
  }

  public int getCcafsBudgetType() {
    return ccafsBudgetType;
  }

  public double getCcafsGenderPercentage() {
    return ccafsGenderPercentage;
  }

  @Override
  public int getCurrentPlanningYear() {
    return config.getPlanningCurrentYear();
  }

  public int getMidOutcomeYear() {
    return config.getMidOutcomeYear();
  }

  public int getMOGIndex(IPElement mog) {
    int index = 0;
    List<IPElement> allMOGs = ipElementManager.getIPElements(mog.getProgram(), mog.getType());

    for (int i = 0; i < allMOGs.size(); i++) {
      if (allMOGs.get(i).getId() == mog.getId()) {
        return (i + 1);
      }
    }

    return index;
  }


  public OutputBudget getOutputBudget(int outputID, int budgetType) {
    for (OutputBudget ob : project.getOutputsBudgets()) {
      if (ob.getOutput().getId() == outputID && ob.getType().getValue() == budgetType) {
        return ob;
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

  public double getTotalBilateralGenderBudgetByYear() {
    double a = (bilateralGenderPercentage / 100) * bilateralBudgetByYear;
    return a;
  }

  public double getTotalCCAFSGenderBudgetByYear() {
    double a = (ccafsGenderPercentage / 100) * ccafsBudgetByYear;
    return a;
  }

  public int getYear() {
    return year;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }


  @Override
  public void prepare() throws Exception {
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project information
    project = projectManager.getProject(projectID);
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));

    // Remove the outputs duplicated
    Set<IPElement> outputsTemp = new HashSet<>(project.getOutputs());
    project.getOutputs().clear();
    project.getOutputs().addAll(outputsTemp);

    allYears = project.getAllYears();
    if (!allYears.isEmpty()) {

      // Getting the year from the URL parameters.
      try {
        String parameter = this.getRequest().getParameter(APConstants.YEAR_REQUEST);
        year = (parameter != null) ? Integer.parseInt(StringUtils.trim(parameter)) : config.getPlanningCurrentYear();
      } catch (NumberFormatException e) {
        LOG.warn("-- prepare() > There was an error parsing the year '{}'.", year);
        year = config.getPlanningCurrentYear();
      }

      bilateralBudgetType = BudgetType.W3_BILATERAL.getValue();
      ccafsBudgetType = BudgetType.W1_W2.getValue();

      List<OutputBudget> budgets = new ArrayList<>();
      budgets.addAll(budgetByMogManager.getProjectOutputsBudgetByTypeAndYear(projectID, ccafsBudgetType, year));
      budgets.addAll(budgetByMogManager.getProjectOutputsBudgetByTypeAndYear(projectID, bilateralBudgetType, year));
      project.setOutputsBudgets(budgets);

      ccafsBudgetByYear = budgetManager.calculateProjectBudgetByTypeAndYear(projectID, ccafsBudgetType, year);
      bilateralBudgetByYear = budgetManager.calculateProjectBudgetByTypeAndYear(projectID, bilateralBudgetType, year);

      ccafsGenderPercentage =
        budgetManager.calculateTotalGenderPercentageByYearAndType(projectID, year, ccafsBudgetType);
      bilateralGenderPercentage =
        budgetManager.calculateTotalGenderPercentageByYearAndType(projectID, year, bilateralBudgetType);

      if (!allYears.contains(new Integer(year))) {
        year = allYears.get(0);
      }
    }

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");

    // Getting the history for this section
    this.setHistory(historyManager.getProjectBudgetByMogHistory(projectID));
  }

  @Override
  public String save() {
    boolean success = false;
    if (securityContext.canUpdateProjectBudgetByMOG(projectID)) {

      success = budgetByMogManager.saveProjectOutputsBudget(project, this.getCurrentUser(), this.getJustification());

      if (success) {
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
      } else {
        this.addActionError(this.getText("saving.problem"));
        LOG.warn("There was a problem saving the project outputs planning.");
        return BaseAction.INPUT;
      }
    } else {
      return NOT_AUTHORIZED;
    }
  }

  public void setAllYears(List<Integer> allYears) {
    this.allYears = allYears;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setYear(int year) {
    this.year = year;
  }


  @Override
  public void validate() {
    if (save) {
      projectBudgetByMOGValidator.validate(this, project, "Planning");
    }
  }
}