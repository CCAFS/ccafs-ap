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


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class BudgetOverhead {

  private int id;
  private boolean bilateralCostRecovered;
  private double contractedOverhead;

  public double getContractedOverhead() {
    return contractedOverhead;
  }

  public int getId() {
    return id;
  }

  public boolean isBilateralCostRecovered() {
    return bilateralCostRecovered;
  }

  public void setBilateralCostRecovered(boolean bilateralCostRecovered) {
    this.bilateralCostRecovered = bilateralCostRecovered;
  }

  public void setContractedOverhead(double contractedOverhead) {
    this.contractedOverhead = contractedOverhead;
  }

  public void setId(int id) {
    this.id = id;
  }
}
