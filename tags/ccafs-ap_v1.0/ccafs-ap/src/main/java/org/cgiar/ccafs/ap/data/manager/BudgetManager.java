package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.BudgetManagerImpl;
import org.cgiar.ccafs.ap.data.model.Budget;

import com.google.inject.ImplementedBy;

@ImplementedBy(BudgetManagerImpl.class)
public interface BudgetManager {

  /**
   * Get the budget of a given activity.
   * 
   * @param activityID - activity identifier.
   * @return a Budget object.
   */
  public Budget getBudget(int activityID);

  /**
   * Save into the DAO the budget data corresponding to the given activity
   * 
   * @param budget Budget object with the information to save
   * @param activityID - Activity identifier
   * @return true if was successfully saved, false otherwise
   */
  public boolean saveBudget(Budget budget, int activityID);

}
