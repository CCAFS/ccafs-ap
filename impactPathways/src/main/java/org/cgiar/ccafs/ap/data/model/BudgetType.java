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
package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This enum represents the different type of Budget the system will manage.
 * The value for each enum corresponds with the ids that are assigned in the database.
 * 
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal.
 */
public enum BudgetType {

  W1_W2(7), W3_BILATERAL(8), LEVERAGED(9), W1_W2_PARTNERS(10), W1_W2_OTHER(11), W3_BILATERAL_PARTNERS(12),
  W3_BILATERAL_OTHERS(13), W1_W2_GENDER(14), W3_BILATERAL_GENDER(15), ACTIVITY_W1_W2(16), ACTIVITY_W3_BILATERAL(17);

  private int value;

  private BudgetType(int value) {
    this.value = value;
  }

  static public BudgetType getBudgetType(int id) {
    switch (id) {
      case 7:
        return BudgetType.W1_W2;
      case 8:
        return BudgetType.W3_BILATERAL;
      case 9:
        return BudgetType.LEVERAGED;
      case 10:
        return BudgetType.W1_W2_PARTNERS;
      case 11:
        return BudgetType.W1_W2_OTHER;
      case 12:
        return BudgetType.W3_BILATERAL_PARTNERS;
      case 13:
        return BudgetType.W3_BILATERAL_OTHERS;
      case 14:
        return BudgetType.W1_W2_GENDER;
      case 15:
        return BudgetType.W3_BILATERAL_GENDER;
      case 16:
        return BudgetType.ACTIVITY_W1_W2;
      case 17:
        return BudgetType.ACTIVITY_W3_BILATERAL;
    }
    return BudgetType.W1_W2;
  }

  public static BudgetType[] getProjectBudgetTypes() {
    List<BudgetType> budgetTypes = new ArrayList<>();
    for (BudgetType bt : BudgetType.values()) {
      if (!bt.name().toLowerCase().contains("activity")) {
        budgetTypes.add(bt);
      }
    }

    return budgetTypes.toArray(new BudgetType[] {});
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}