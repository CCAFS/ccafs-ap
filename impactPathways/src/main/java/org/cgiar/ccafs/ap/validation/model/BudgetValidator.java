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

package org.cgiar.ccafs.ap.validation.model;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class BudgetValidator extends BaseAction {

  private static final long serialVersionUID = -6700461905676525206L;

  @Inject
  public BudgetValidator(APConfig config) {
    super(config);
  }

  public boolean isValidAmount(double amount) {
    return (amount >= 0);
  }


  public boolean isValidAmountNoZero(double amount) {
    return (amount > 0);
  }

  public boolean isValidGenderPercentage(int genderPercentage) {
    return (genderPercentage >= 0 && genderPercentage <= 100);
  }

  public boolean isValidInstitution(Institution institution) {
    return institution != null && institution.getId() > 0;
  }

  public boolean isValidType(BudgetType type) {
    return type != null;
  }

  public boolean isValidYear(int year) {
    return (year >= 1900 && year <= 2500);
  }

}
