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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Project.
 *
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R
 */
public class Project {

  private int id;
  private String title;
  private String summary;
  private Date startDate;
  private Date endDate;
  private List<IPProgram> regions; // The list of regions in which this project works with.
  private List<IPProgram> flagships; // The list of flagships in which this project works with.
  private List<IPCrossCutting> crossCuttings;// The list of Cross Cutting themes in which this project works with.
  private User leader; // Project leader will be a user too.
  private String leaderResponsabilities;
  private IPProgram programCreator; // Creator program. e.g. LAM, FP4, CU, etc.
  private User owner;
  private List<ProjectPartner> projectPartners; // Project partners.
  private User expectedLeader;
  private List<Budget> budgets;
  private ProjectOutcome outcome;
  private long created; // Timestamp number when the project was created

  public Project(int id) {
    this.id = id;
  }

  /**
   * Equals based on the the project id.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Project) {
      Project p = (Project) obj;
      return p.getId() == this.getId();
    }
    return super.equals(obj);
  }

  /**
   * This method calculates all the years between the start date and the end date.
   *
   * @return a List of numbers representing all the years.
   */
  public List<Integer> getAllYears() {
    List<Integer> allYears = new ArrayList<>();
    if (startDate != null && endDate != null) {
      Calendar calendarStart = Calendar.getInstance();
      calendarStart.setTime(startDate);
      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(endDate);

      while (calendarStart.getTimeInMillis() <= calendarEnd.getTimeInMillis()) {
        // Adding the year to the list.
        allYears.add(calendarStart.get(Calendar.YEAR));
        // Adding a year (365 days) to the start date.
        calendarStart.add(Calendar.YEAR, 1);
      }
    }

    return allYears;
  }

  public List<Budget> getBudgets() {
    return budgets;
  }

  /**
   * This method returns a composed Identifier that is going to be used in the front-end.
   * The convention is going to be used depending on the creationg date of the project.
   * yyyy-project.id => e.g. 2014-46
   *
   * @return the composed indentifier or null if the created date is null.
   */
  public String getComposedId() {
    if (created != 0) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(created);
      return calendar.get(Calendar.YEAR) + "-" + this.id;
    }
    return null;
  }

  public long getCreated() {
    return created;
  }

  public List<String> getCrossCuttingIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (IPCrossCutting ipCrossCutting : crossCuttings) {
      ids.add(String.valueOf(ipCrossCutting.getId()));
    }
    return ids;
  }

  public List<IPCrossCutting> getCrossCuttings() {
    return crossCuttings;
  }

  public Date getEndDate() {
    return endDate;
  }

  public User getExpectedLeader() {
    return expectedLeader;
  }

  public List<String> getFlagshipIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (IPProgram flagship : flagships) {
      ids.add(String.valueOf(flagship.getId()));
    }
    return ids;
  }

  public List<IPProgram> getFlagships() {
    return flagships;
  }

  public int[] getFlagshipsIds() {
    int[] flagshipsIds = new int[flagships.size()];
    for (int c = 0; c < flagships.size(); c++) {
      flagshipsIds[c] = flagships.get(c).getId();
    }
    return flagshipsIds;
  }

  public int getId() {
    return id;
  }

  public User getLeader() {
    return leader;
  }

  public String getLeaderResponsabilities() {
    return leaderResponsabilities;
  }

  public ProjectOutcome getOutcome() {
    return outcome;
  }

  public User getOwner() {
    return owner;
  }

  public IPProgram getProgramCreator() {
    return programCreator;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public List<String> getRegionIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (IPProgram region : regions) {
      ids.add(String.valueOf(region.getId()));
    }
    return ids;
  }

  public List<IPProgram> getRegions() {
    return regions;
  }

  public int[] getRegionsIds() {
    int[] regionsIds = new int[regions.size()];
    for (int c = 0; c < regions.size(); c++) {
      regionsIds[c] = regions.get(c).getId();
    }
    return regionsIds;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getSummary() {
    return summary;
  }

  public String getTitle() {
    return title;
  }

  public List<IPProgram> getTypes() {
    return regions;
  }

  @Override
  public int hashCode() {
    return this.getId();
  }

  public void setBudgets(List<Budget> budgets) {
    this.budgets = budgets;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setCrossCuttings(List<IPCrossCutting> crossCuttings) {
    this.crossCuttings = crossCuttings;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setExpectedLeader(User expectedLeader) {
    this.expectedLeader = expectedLeader;
  }

  public void setFlagships(List<IPProgram> flagships) {
    this.flagships = flagships;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(User leader) {
    this.leader = leader;
  }

  public void setLeaderResponsabilities(String leaderResponsabilities) {
    this.leaderResponsabilities = leaderResponsabilities;
  }

  public void setOutcome(ProjectOutcome outcome) {
    this.outcome = outcome;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setProgramCreator(IPProgram creator) {
    this.programCreator = creator;
  }

  public void setProjectPartners(List<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setRegions(List<IPProgram> regions) {
    this.regions = regions;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTypes(List<IPProgram> types) {
    this.regions = types;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}