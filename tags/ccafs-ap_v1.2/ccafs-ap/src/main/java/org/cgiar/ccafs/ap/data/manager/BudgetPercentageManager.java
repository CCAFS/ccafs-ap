package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.BudgetPercentageManagerImpl;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(BudgetPercentageManagerImpl.class)
public interface BudgetPercentageManager {

  /**
   * Get the budget percentage identified by id
   * 
   * @return a BudgetPercentage object with the information
   */
  public BudgetPercentage getBudgetPercentage(String id);

  /**
   * Get the list of budget percentages
   * 
   * @return a list of BudgetPercentage objects with the information
   */
  public List<BudgetPercentage> getBudgetPercentageList();
}
