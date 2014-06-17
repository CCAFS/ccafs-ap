/*
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
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.BudgetDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BudgetManagerImpl implements BudgetManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(BudgetManagerImpl.class);
  private BudgetDAO budgetDAO;

  @Inject
  public BudgetManagerImpl(BudgetDAO budgetDAO) {
    this.budgetDAO = budgetDAO;
  }

  @Override
  public Budget getBudget(int activityID) {
    Map<String, String> budgetDB = budgetDAO.getBudget(activityID);

    /*
     * If there is no budget stored in the DB
     * creates a budget object whit identifier -1
     * and usd 0
     */
    if (budgetDB == null) {
      Budget budget = new Budget(-1, 0);
      return budget;
    }

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

    LOG.debug("The budget for the activity {} was loaded successfully.", activityID);
    return budget;
  }

  @Override
  public boolean saveBudget(Budget budget, int activityID) {
    Map<String, String> budgetData = new HashMap<>();

    if (budget.getId() != -1) {
      budgetData.put("id", String.valueOf(budget.getId()));
    } else {
      budgetData.put("id", null);
    }

    budgetData.put("usd", String.valueOf(budget.getUsd()));

    if (budget.getCgFund().getId() != -1) {
      budgetData.put("cgFund", String.valueOf(budget.getCgFund().getId()));
    } else {
      budgetData.put("cgFund", null);
    }

    if (budget.getBilateral().getId() != -1) {
      budgetData.put("bilateral", String.valueOf(budget.getBilateral().getId()));
    } else {
      budgetData.put("bilateral", null);
    }

    budgetData.put("activityID", String.valueOf(activityID));

    return budgetDAO.saveBudget(budgetData);
  }
}
