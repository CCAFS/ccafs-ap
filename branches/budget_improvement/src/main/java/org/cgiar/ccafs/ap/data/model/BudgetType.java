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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This enum represents the different type of Budget the system will manage.
 * The value for each enum corresponds with the ids that are assigned in the database.
 * 
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal.
 */
public enum BudgetType {

  W1_W2(1), W3_BILATERAL(2), LEVERAGED(3), W1_W2_PARTNERS(4), W1_W2_OTHER(5), W3_BILATERAL_PARTNERS(6),
  W3_BILATERAL_OTHERS(7), W1_W2_GENDER(8), W3_BILATERAL_GENDER(9), ACTIVITY(10);

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
      case 3:
        return BudgetType.LEVERAGED;
      case 4:
        return BudgetType.W1_W2_PARTNERS;
      case 5:
        return BudgetType.W1_W2_OTHER;
      case 6:
        return BudgetType.W3_BILATERAL_PARTNERS;
      case 7:
        return BudgetType.W3_BILATERAL_OTHERS;
      case 8:
        return BudgetType.W1_W2_GENDER;
      case 9:
        return BudgetType.W3_BILATERAL_GENDER;
      case 10:
        return BudgetType.ACTIVITY;
    }
    return BudgetType.W1_W2;
  }

  public int getValue() {
    return value;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}