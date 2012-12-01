package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;

import java.util.Map;

import com.google.inject.Inject;


public class BudgetManagerImpl implements BudgetManager {

  private BudgetDAO budgetDAO;

  @Inject
  public BudgetManagerImpl(BudgetDAO budgetDAO) {
    this.budgetDAO = budgetDAO;
  }

  @Override
  public Budget getBudget(int activityID) {
    Map<String, String> budgetDB = budgetDAO.getBudget(activityID);
    Budget budget = new Budget();
    budget.setId(Integer.parseInt(budgetDB.get("id")));
    budget.setUsd(Double.parseDouble(budgetDB.get("usd")));
    if (budgetDB.get("cg_funds_id") != null) {
      BudgetPercentage cgFunds = new BudgetPercentage();
      cgFunds.setId(Integer.parseInt(budgetDB.get("cg_funds_id")));
      cgFunds.setPercentage(budgetDB.get("cg_funds_percentage"));
      budget.setCgFund(cgFunds);
    }
    if (budgetDB.get("bilateral_id") != null) {
      BudgetPercentage bilateral = new BudgetPercentage();
      bilateral.setId(Integer.parseInt(budgetDB.get("bilateral_id")));
      bilateral.setPercentage(budgetDB.get("bilateral_percentage"));
      budget.setBilateral(bilateral);
    }
    if (budgetDB.size() == 0) {
      return null;
    }
    return budget;
  }

}
