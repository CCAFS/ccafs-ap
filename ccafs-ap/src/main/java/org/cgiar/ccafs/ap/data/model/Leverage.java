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


public class Leverage {

  private int id;
  private String title;
  private double budget;
  private int startYear;
  private int endYear;
  private Theme theme;
  private Leader leader;
  private String partnerName;

  public double getBudget() {
    return budget;
  }

  public int getEndYear() {
    return endYear;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public int getStartYear() {
    return startYear;
  }

  public Theme getTheme() {
    return theme;
  }

  public String getTitle() {
    return title;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public void setEndYear(int endYear) {
    this.endYear = endYear;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public void setStartYear(int startYear) {
    this.startYear = startYear;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}