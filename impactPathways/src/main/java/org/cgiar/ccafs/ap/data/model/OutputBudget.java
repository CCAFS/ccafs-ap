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
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class OutputBudget {

  private int id;
  private int year;
  private double totalContribution;
  private double genderContribution;
  private IPElement output;
  private BudgetType type;

  public double getGenderContribution() {
    return genderContribution;
  }

  public int getId() {
    return id;
  }

  public IPElement getOutput() {
    return output;
  }

  public double getTotalContribution() {
    return totalContribution;
  }

  public BudgetType getType() {
    return type;
  }

  public int getYear() {
    return year;
  }

  public void setGenderContribution(double genderContribution) {
    this.genderContribution = genderContribution;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOutput(IPElement output) {
    this.output = output;
  }

  public void setTotalContribution(double totalContribution) {
    this.totalContribution = totalContribution;
  }

  public void setType(BudgetType type) {
    this.type = type;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}