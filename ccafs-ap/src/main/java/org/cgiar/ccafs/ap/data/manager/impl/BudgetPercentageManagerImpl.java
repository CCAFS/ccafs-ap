package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.BudgetPercentageDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetPercentageManager;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BudgetPercentageManagerImpl implements BudgetPercentageManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(BudgetPercentageManagerImpl.class);
  private BudgetPercentageDAO budgetPercentageDAO;

  @Inject
  public BudgetPercentageManagerImpl(BudgetPercentageDAO budgetPercentageDAO) {
    this.budgetPercentageDAO = budgetPercentageDAO;
  }

  @Override
  public BudgetPercentage getBudgetPercentage(String id) {
    Map<String, String> bpData = budgetPercentageDAO.getBudgetPercentage(id);
    BudgetPercentage budgetPercentage = new BudgetPercentage();

    if (bpData.isEmpty()) {
      LOG.warn("It was not found any budget percentage identified by {}", id);
    } else {
      budgetPercentage.setId(Integer.parseInt(bpData.get("id")));
      budgetPercentage.setPercentage(bpData.get("percentage"));
    }

    return budgetPercentage;
  }

  @Override
  public List<BudgetPercentage> getBudgetPercentageList() {
    List<BudgetPercentage> budgetPercentages = new ArrayList<>();
    List<Map<String, String>> budgetPercentagesDataList = budgetPercentageDAO.getBudgetPercentages();

    // Create 'No funds' option in budget percentages list with a
    // fake object
    BudgetPercentage fakeBp = new BudgetPercentage();
    fakeBp.setId(-1);
    // i18n text set on page load, because managers can't call getText()
    fakeBp.setPercentage("");
    budgetPercentages.add(fakeBp);


    for (Map<String, String> bpData : budgetPercentagesDataList) {
      BudgetPercentage bp = new BudgetPercentage();
      bp.setId(Integer.parseInt(bpData.get("id")));
      bp.setPercentage(bpData.get("percentage"));
      budgetPercentages.add(bp);
    }

    return budgetPercentages;
  }

}
