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
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
public class BudgetManagerImpl implements BudgetManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(BudgetManagerImpl.class);

  // DAO's
  private BudgetDAO budgetDAO;

  // Managers
  private InstitutionManager institutionManager;

  @Inject
  public BudgetManagerImpl(BudgetDAO budgetDAO, InstitutionManager institutionManager) {
    this.budgetDAO = budgetDAO;
    this.institutionManager = institutionManager;
  }

  @Override
  public double calculateGenderBudgetByTypeAndYear(int projectID, int budgetTypeID, int year) {
    return budgetDAO.calculateGenderBudgetByTypeAndYear(projectID, budgetTypeID, year);
  }

  @Override
  public double calculateProjectBudgetByTypeAndYear(int projectID, int budgetTypeID, int year) {
    return budgetDAO.calculateProjectBudgetByTypeAndYear(projectID, budgetTypeID, year);
  }

  @Override
  public double calculateTotalBudget(int projectID) {
    return budgetDAO.calculateTotalBudget(projectID);
  }

  @Override
  public double calculateTotalBudgetByYear(int projectID, int year) {
    return budgetDAO.calculateTotalBudgetByYear(projectID, year);
  }

  @Override
  public double calculateTotalCCAFSBudgetByInstitution(int projectID, int institutionID) {
    return budgetDAO.calculateTotalCCAFSBudgetByInstitution(projectID, institutionID);
  }

  @Override
  public double calculateTotalCCAFSBudgetByInstitutionAndType(int projectID, int institutionID, int budgetTypeID) {
    return budgetDAO.calculateTotalCCAFSBudgetByInstitutionAndType(projectID, institutionID, budgetTypeID);
  }

  @Override
  public double calculateTotalCCAFSBudgetByType(int projectID, int budgetTypeID) {
    return budgetDAO.calculateTotalCCAFSBudgetByType(projectID, budgetTypeID);
  }

  @Override
  public double calculateTotalGenderBudget(int projectID) {
    return budgetDAO.calculateTotalGenderBudget(projectID);
  }

  @Override
  public double calculateTotalGenderBudgetByInstitutionAndType(int projectID, int institutionID, int budgetTypeID) {
    return budgetDAO.calculateTotalGenderBudgetByInstitutionAndType(projectID, institutionID, budgetTypeID);
  }

  @Override
  public double calculateTotalGenderBudgetByYear(int projectID, int year) {
    return budgetDAO.calculateTotalGenderBudgetByYear(projectID, year);
  }

  @Override
  public double calculateTotalGenderPercentageByType(int projectID, int budgetTypeID) {
    return budgetDAO.calculateTotalGenderPercentageByType(projectID, budgetTypeID);
  }

  @Override
  public double calculateTotalGenderPercentageByYearAndType(int projectID, int year, int budgetTypeID) {
    return budgetDAO.calculateTotalGenderPercentageByYearAndType(projectID, year, budgetTypeID);
  }

  @Override
  public double calculateTotalProjectBudgetByType(int projectID, int budgetTypeID) {
    return budgetDAO.calculateTotalProjectBudgetByType(projectID, budgetTypeID);
  }

  @Override
  public double calculateTotalProjectBudgetByTypeYear(int projectID, int budgetTypeID, int year) {
    return budgetDAO.calculateTotalProjectBudgetByTypeYear(projectID, budgetTypeID, year);
  }

  @Override
  public boolean deleteBudget(int budgetId, User user, String justification) {
    return budgetDAO.deleteBudget(budgetId, user.getId(), justification);
  }

  @Override
  public boolean deleteBudgetsByInstitution(int projectID, Institution institution, User user, String justification) {
    return budgetDAO.deleteBudgetsByInstitution(projectID, institution.getId(), user.getId(), justification);
  }

  @Override
  public boolean deleteBudgetsByYear(int projectID, int year, User user, String justification) {
    return budgetDAO.deleteBudgetsByYear(projectID, year, user.getId(), justification);
  }

  @Override
  public boolean deleteBudgetsFromUnexistentYears(int projectID) {
    return budgetDAO.deleteBudgetsFromUnexistentYears(projectID);
  }

  @Override
  public boolean deleteBudgetsWithNoLinkToInstitutions(int projectID, int currentYear) {
    return budgetDAO.deleteBudgetsWithNoLinkToInstitutions(projectID, currentYear);
  }

  @Override
  public boolean deleteCofoundedBudgetsWithNoLink(int projectID) {
    return budgetDAO.deleteCofoundedBudgetsWithNoLink(projectID);
  }

  @Override
  public List<Budget> getBudgetsByProject(Project project) {
    List<Integer> allYears = project.getAllYears();

    List<Budget> budgets = new ArrayList<>();
    for (Integer year : allYears) {
      budgets.addAll(this.getBudgetsByYear(project.getId(), year));
    }
    return budgets;
  }

  @Override
  public List<Budget> getBudgetsByYear(int projectID, int year) {
    List<Budget> budgets = new ArrayList<>();
    List<Map<String, String>> budgetDataList = budgetDAO.getBudgetsByYear(projectID, year);
    for (Map<String, String> budgetData : budgetDataList) {
      Budget budget = new Budget();
      budget.setProjectId(Integer.parseInt(budgetData.get("project_id")));
      budget.setId(Integer.parseInt(budgetData.get("id")));
      budget.setYear(Integer.parseInt(budgetData.get("year")));
      budget.setType(BudgetType.getBudgetType(Integer.parseInt(budgetData.get("budget_type"))));
      budget.setAmount(Double.parseDouble(budgetData.get("amount")));
      if (budgetData.get("gender_percentage") != null) {
        budget.setGenderPercentage(Integer.parseInt(budgetData.get("gender_percentage")));
      }

      // Institution as institution_id
      budget.setInstitution(institutionManager.getInstitution(Integer.parseInt(budgetData.get("institution_id"))));

      if (budgetData.get("cofinancing_project_id") != null) {
        Project project = new Project();
        project.setId(Integer.parseInt(budgetData.get("cofinancing_project_id")));
        project.setTitle(budgetData.get("cofinancing_project_title"));
        budget.setCofinancingProject(project);
      }

      // adding information of the object to the array
      budgets.add(budget);
    }
    return budgets;
  }

  @Override
  public boolean saveBudget(int projectID, Budget budget, User user, String justification) {
    boolean allSaved = true;
    Map<String, Object> budgetData = new HashMap<>();
    if (budget.getId() > 0) {
      budgetData.put("id", budget.getId());
    }
    budgetData.put("year", budget.getYear());
    budgetData.put("budget_type", budget.getType().getValue());
    budgetData.put("institution_id", budget.getInstitution().getId());
    budgetData.put("amount", budget.getAmount());
    budgetData.put("gender_percentage", budget.getGenderPercentage());
    if (budget.getCofinancingProject() != null) {
      budgetData.put("cofinance_project_id", budget.getCofinancingProject().getId());
    }
    budgetData.put("user_id", user.getId());
    budgetData.put("justification", justification);

    int result = budgetDAO.saveBudget(projectID, budgetData);

    if (result > 0) {
      LOG.debug("saveBudget > New Budget and Project Budget added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveBudget > Budget with id={} was updated", budget.getId());
    } else {
      LOG.error("saveBudget > There was an error trying to save/update a Budget from projectId={}", projectID);
      allSaved = false;
    }
    return allSaved;
  }


}
