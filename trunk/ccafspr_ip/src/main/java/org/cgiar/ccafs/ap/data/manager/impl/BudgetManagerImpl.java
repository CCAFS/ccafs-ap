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
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;

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
  private IPProgramManager ipProgramManager;
  private LocationManager locationManger;

  @Inject
  public BudgetManagerImpl(BudgetDAO budgetDAO, InstitutionManager institutionManager,
    IPProgramManager ipProgramManager, LocationManager locationManger) {
    this.budgetDAO = budgetDAO;
    this.institutionManager = institutionManager;
    this.locationManger = locationManger;
    this.ipProgramManager = ipProgramManager;
  }


  @Override
  public double calculateTotalActivityBudget(int activityID) {
    return budgetDAO.calculateTotalActivityBudget(activityID);
  }

  @Override
  public double calculateTotalActivityBudgetByYear(int activityID, int year) {
    return budgetDAO.calculateTotalActivityBudgetByYear(activityID, year);
  }

  @Override
  public double calculateTotalCCAFSBudget(int projectID) {
    return budgetDAO.calculateTotalCCAFSBudget(projectID);
  }

  @Override
  public double calculateTotalCCAFSBudgetByYear(int projectID, int year) {
    return budgetDAO.calculateTotalCCAFSBudgetByYear(projectID, year);
  }

  @Override
  public double calculateTotalOverallBudget(int projectID) {
    return budgetDAO.calculateTotalOverallBudget(projectID);
  }

  @Override
  public double calculateTotalOverallBudgetByYear(int projectID, int year) {
    return budgetDAO.calculateTotalOverallBudgetByYear(projectID, year);
  }

  @Override
  public boolean deleteActivityBudgetByYear(int activityID, int year) {
    return budgetDAO.deleteActivityBudgetByYear(activityID, year);
  }

  @Override
  public boolean deleteActivityBudgetsByActivityID(int activityID) {
    return budgetDAO.deleteActivityBudgetsByActivityID(activityID);
  }

  @Override
  public boolean deleteActivityBudgetsByInstitution(int activityID, int institutionID) {
    return budgetDAO.deleteActivityBudgetsByInstitution(activityID, institutionID);
  }

  @Override
  public boolean deleteBudget(int budgetId) {
    return budgetDAO.deleteBudget(budgetId);
  }

  @Override
  public boolean deleteBudgetsByInstitution(int projectID, int institutionID) {
    return budgetDAO.deleteBudgetsByInstitution(projectID, institutionID);
  }

  @Override
  public boolean deleteBudgetsByYear(int projectID, int year) {
    return budgetDAO.deleteBudgetsByYear(projectID, year);
  }

  @Override
  public List<Budget> getActivityBudgetsByType(int activityID, int budgetType) {
    List<Budget> budgets = new ArrayList<>();
    List<Map<String, String>> budgetDataList = budgetDAO.getActivityBudgetsByType(activityID, budgetType);
    for (Map<String, String> budgetData : budgetDataList) {
      Budget budget = new Budget();
      budget.setId(Integer.parseInt(budgetData.get("id")));
      budget.setYear(Integer.parseInt(budgetData.get("year")));
      budget.setType(BudgetType.ACTIVITY);
      budget.setAmount(Double.parseDouble(budgetData.get("amount")));
      // Institution as institution_id
      budget.setInstitution(institutionManager.getInstitution(Integer.parseInt(budgetData.get("institution_id"))));

      // adding information of the object to the array
      budgets.add(budget);
    }
    return budgets;
  }

  @Override
  public List<Budget> getActivityBudgetsByYear(int activityID, int year) {
    List<Budget> budgets = new ArrayList<>();
    List<Map<String, String>> budgetDataList = budgetDAO.getActivityBudgetsByYear(activityID, year);
    for (Map<String, String> budgetData : budgetDataList) {
      Budget budget = new Budget();
      budget.setId(Integer.parseInt(budgetData.get("id")));
      budget.setYear(Integer.parseInt(budgetData.get("year")));
      budget.setType(BudgetType.ACTIVITY);
      budget.setAmount(Double.parseDouble(budgetData.get("amount")));
      // Institution as institution_id
      budget.setInstitution(institutionManager.getInstitution(Integer.parseInt(budgetData.get("institution_id"))));
      // adding information of the object to the array
      budgets.add(budget);
    }
    return budgets;
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
  public List<Budget> getBudgetsByType(int projectID, int budgetType) {
    List<Budget> budgets = new ArrayList<>();
    List<Map<String, String>> budgetDataList = budgetDAO.getBudgetsByType(projectID, budgetType);
    for (Map<String, String> budgetData : budgetDataList) {
      Budget budget = new Budget();
      budget.setId(Integer.parseInt(budgetData.get("id")));
      budget.setYear(Integer.parseInt(budgetData.get("year")));
      switch (Integer.parseInt(budgetData.get("budget_type"))) {
        case 1:
          budget.setType(BudgetType.W1);
          break;
        case 2:
          budget.setType(BudgetType.W2);
          break;
        case 3:
          budget.setType(BudgetType.W3);
          break;
        case 4:
          budget.setType(BudgetType.BILATERAL);
          break;
        case 5:
          budget.setType(BudgetType.LEVERAGED);
          break;
      }
      budget.setAmount(Double.parseDouble(budgetData.get("amount")));

      // Institution as institution_id
      budget.setInstitution(institutionManager.getInstitution(Integer.parseInt(budgetData.get("institution_id"))));

      // adding information of the object to the array
      budgets.add(budget);
    }
    return budgets;
  }

  @Override
  public List<Budget> getBudgetsByYear(int projectID, int year) {
    List<Budget> budgets = new ArrayList<>();
    List<Map<String, String>> budgetDataList = budgetDAO.getBudgetsByYear(projectID, year);
    for (Map<String, String> budgetData : budgetDataList) {
      Budget budget = new Budget();
      budget.setId(Integer.parseInt(budgetData.get("id")));
      budget.setYear(Integer.parseInt(budgetData.get("year")));
      switch (Integer.parseInt(budgetData.get("budget_type"))) {
        case 1:
          budget.setType(BudgetType.W1);
          break;
        case 2:
          budget.setType(BudgetType.W2);
          break;
        case 3:
          budget.setType(BudgetType.W3);
          break;
        case 4:
          budget.setType(BudgetType.BILATERAL);
          break;
        case 5:
          budget.setType(BudgetType.LEVERAGED);
          break;
      }
      budget.setAmount(Double.parseDouble(budgetData.get("amount")));

      // Institution as institution_id
      budget.setInstitution(institutionManager.getInstitution(Integer.parseInt(budgetData.get("institution_id"))));

      // adding information of the object to the array
      budgets.add(budget);
    }
    return budgets;
  }

  @Override
  public List<Budget> getCCAFSBudgets(int projectID) {
    List<Budget> budgets = new ArrayList<>();
    List<Map<String, String>> budgetDataList = budgetDAO.getCCAFSBudgets(projectID);
    for (Map<String, String> budgetData : budgetDataList) {
      Budget budget = new Budget();
      budget.setId(Integer.parseInt(budgetData.get("id")));
      budget.setYear(Integer.parseInt(budgetData.get("year")));
      switch (Integer.parseInt(budgetData.get("budget_type"))) {
        case 1:
          budget.setType(BudgetType.W1);
          break;
        case 2:
          budget.setType(BudgetType.W2);
          break;
        case 3:
          budget.setType(BudgetType.W3);
          break;
        case 4:
          budget.setType(BudgetType.BILATERAL);
          break;
        case 5:
          budget.setType(BudgetType.LEVERAGED);
          break;
      }
      budget.setAmount(Double.parseDouble(budgetData.get("amount")));

      // Institution as institution_id
      budget.setInstitution(institutionManager.getInstitution(Integer.parseInt(budgetData.get("institution_id"))));

      // adding information of the object to the array
      budgets.add(budget);
    }
    return budgets;
  }

  @Override
  public List<Institution> getLeveragedInstitutions(int projectID) {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList = budgetDAO.getLeveragedInstitutions(projectID);
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setContactPersonName(iData.get("contactPersonName"));
      institution.setContactPersonEmail(iData.get("contactPersonEmail"));

      // InstitutionType Object
      if (iData.get("institution_type_id") != null) {
        institution.setType(institutionManager.getInstitutionType(Integer.parseInt(iData.get("institution_type_id"))));
      }
      // Program Object
      if (iData.get("program_id") != null) {
        institution.setProgram(ipProgramManager.getIPProgramById(Integer.parseInt(iData.get("program_id"))));
      }
      // Location Object
      if (iData.get("loc_elements_id") != null) {
        institution.setCountry(locationManger.getCountry(Integer.parseInt(iData.get("loc_elements_id"))));
      }

      // Adding object to the array.
      institutions.add(institution);
    }
    return institutions;
  }

  @Override
  public List<Institution> getLeveragedInstitutions(int projectID, int year) {
    List<Institution> institutions = new ArrayList<>();
    List<Map<String, String>> institutionDataList = budgetDAO.getLeveragedInstitutions(projectID, year);
    for (Map<String, String> iData : institutionDataList) {
      Institution institution = new Institution();
      institution.setId(Integer.parseInt(iData.get("id")));
      institution.setName(iData.get("name"));
      institution.setAcronym(iData.get("acronym"));
      institution.setContactPersonName(iData.get("contactPersonName"));
      institution.setContactPersonEmail(iData.get("contactPersonEmail"));

      // InstitutionType Object
      if (iData.get("institution_type_id") != null) {
        institution.setType(institutionManager.getInstitutionType(Integer.parseInt(iData.get("institution_type_id"))));
      }
      // Program Object
      if (iData.get("program_id") != null) {
        institution.setProgram(ipProgramManager.getIPProgramById(Integer.parseInt(iData.get("program_id"))));
      }
      // Location Object
      if (iData.get("loc_elements_id") != null) {
        institution.setCountry(locationManger.getCountry(Integer.parseInt(iData.get("loc_elements_id"))));
      }

      // Adding object to the array.
      institutions.add(institution);
    }
    return institutions;
  }

  @Override
  public boolean saveActivityBudget(int activityID, Budget activityBudget) {
    boolean allSaved = true;
    Map<String, Object> budgetData = new HashMap<>();
    if (activityBudget.getId() > 0) {
      budgetData.put("id", activityBudget.getId());
    }
    budgetData.put("year", activityBudget.getYear());
    budgetData.put("budget_type", activityBudget.getType().getValue());
    budgetData.put("institution_id", activityBudget.getInstitution().getId());
    budgetData.put("amount", activityBudget.getAmount());

    int result = budgetDAO.saveActivityBudget(activityID, budgetData);

    if (result > 0) {
      LOG.debug("saveActivityBudget > New Budget and Activity Budget added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveActivityBudget > Budget with id={} was updated", activityBudget.getId());
    } else {
      LOG
        .error("saveActivityBudget > There was an error trying to save/update a Budget from ActivityId={}", activityID);
      allSaved = false;
    }
    return allSaved;
  }

  @Override
  public boolean saveBudget(int projectID, Budget budget) {
    boolean allSaved = true;
    Map<String, Object> budgetData = new HashMap<>();
    if (budget.getId() > 0) {
      budgetData.put("id", budget.getId());
    }
    budgetData.put("year", budget.getYear());
    budgetData.put("budget_type", budget.getType().getValue());
    budgetData.put("institution_id", budget.getInstitution().getId());
    budgetData.put("amount", budget.getAmount());

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
