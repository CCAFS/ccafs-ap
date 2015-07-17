package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.BudgetPercentageManager;
import org.cgiar.ccafs.ap.data.model.BudgetPercentage;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class BudgetPercentageConverter extends StrutsTypeConverter {

  private BudgetPercentageManager budgetPercentageManager;

  @Inject
  public BudgetPercentageConverter(BudgetPercentageManager budgetPercentageManager) {
    this.budgetPercentageManager = budgetPercentageManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == BudgetPercentage.class) {
      if (values[0].equals("-1")) {
        BudgetPercentage budgetPercentage = new BudgetPercentage();
        budgetPercentage.setId(-1);
        budgetPercentage.setPercentage("");
        return budgetPercentage;
      }
      return budgetPercentageManager.getBudgetPercentage(values[0]);
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    BudgetPercentage budgetPercentage = (BudgetPercentage) o;
    return String.valueOf(budgetPercentage.getId());
  }


}
