/*
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
 */

package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Budget {

  private int id;
  private double usd;
  private BudgetPercentage cgFund;
  private BudgetPercentage bilateral;

  public Budget() {
  }

  public Budget(int id, int usd) {
    this.id = id;
    this.usd = usd;
  }

  public BudgetPercentage getBilateral() {
    return bilateral;
  }

  public BudgetPercentage getCgFund() {
    return cgFund;
  }

  public int getId() {
    return id;
  }

  public double getUsd() {
    return usd;
  }

  public void setBilateral(BudgetPercentage bilateral) {
    this.bilateral = bilateral;
  }

  public void setCgFund(BudgetPercentage cgFund) {
    this.cgFund = cgFund;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsd(double usd) {
    this.usd = usd;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
