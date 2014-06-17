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


public class MilestoneReport {

  private int id;
  private String themeLeaderDescription;
  private String regionalLeaderDescription;
  Milestone milestone;
  MilestoneStatus status;


  public MilestoneReport() {
  }

  public MilestoneReport(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }


  public Milestone getMilestone() {
    return milestone;
  }


  public String getRegionalLeaderDescription() {
    return regionalLeaderDescription;
  }


  public MilestoneStatus getStatus() {
    return status;
  }


  public String getThemeLeaderDescription() {
    return themeLeaderDescription;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setMilestone(Milestone milestone) {
    this.milestone = milestone;
  }


  public void setRegionalLeaderDescription(String regionalLeaderDescription) {
    this.regionalLeaderDescription = regionalLeaderDescription;
  }


  public void setStatus(MilestoneStatus status) {
    this.status = status;
  }


  public void setThemeLeaderDescription(String themeLeaderDescription) {
    this.themeLeaderDescription = themeLeaderDescription;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
