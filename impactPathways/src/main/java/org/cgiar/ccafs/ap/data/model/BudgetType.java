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

  W1_W2(1), W3_BILATERAL(2);

  private int value;

  private BudgetType(int value) {
    this.value = value;
  }

  static public BudgetType getBudgetType(int id) {
    switch (id) {
      case 1:
        return BudgetType.W1_W2;
      case 2:
        return BudgetType.W3_BILATERAL;
    }
    return BudgetType.W1_W2;
  }

  public static BudgetType[] getProjectBudgetTypes() {
    List<BudgetType> budgetTypes = new ArrayList<>();
    for (BudgetType bt : BudgetType.values()) {
      budgetTypes.add(bt);
    }

    return budgetTypes.toArray(new BudgetType[] {});
  }

  public int getValue() {
    return value;
  }

  public boolean isBilateral() {
    return this.getValue() == BudgetType.W3_BILATERAL.getValue();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}