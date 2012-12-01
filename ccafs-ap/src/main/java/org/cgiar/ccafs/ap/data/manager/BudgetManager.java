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

}
