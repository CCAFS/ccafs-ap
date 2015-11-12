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
 * This class represents a specific Budget value within the system.
 * 
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */
public class Budget {

  private int id;
  private int projectId;
  private int year;
  private Institution institution;
  private double amount;
  private BudgetType type;
  private double genderPercentage;
  private Project cofinancingProject;


  public Budget() {
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Budget) {
      Budget b = (Budget) obj;
      return b.getId() == this.id;
    }
    return false;
  }

  public double getAmount() {
    return amount;
  }

  public Project getCofinancingProject() {
    return cofinancingProject;
  }

  public double getGenderPercentage() {
    return genderPercentage;
  }

  public int getId() {
    return id;
  }

  public Institution getInstitution() {
    return institution;
  }

  public int getProjectId() {
    return projectId;
  }

  public BudgetType getType() {
    return type;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public void setCofinancingProject(Project cofinancingProject) {
    this.cofinancingProject = cofinancingProject;
  }

  public void setGenderPercentage(double genderPercentage) {
    this.genderPercentage = genderPercentage;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setInstitution(Institution institution) {
    this.institution = institution;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
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