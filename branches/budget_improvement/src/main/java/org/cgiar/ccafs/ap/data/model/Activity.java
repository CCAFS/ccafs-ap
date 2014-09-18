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
 * This class represents a CCAFS Activity, which belongs to a specific Project.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class Activity {

  private int id;
  private String title;
  private String description;
  private Date startDate;
  private Date endDate;
  private User leader;
  private User expectedLeader;
  private boolean isGlobal;
  // private ExpectedActivityLeader expectedLeader;
  private List<IPCrossCutting> crossCuttings;// The list of Cross Cutting themes in which this project works with.
  private List<Location> locations;
  private long created;
  private List<ActivityPartner> activityPartners;
  private List<IPElement> outputs;
  private List<IPIndicator> indicators;
  private List<Budget> budgets;
  private List<Deliverable> deliverables;
  private IPOtherContribution ipOtherContribution;


  public Activity() {
  }

  public Activity(int id) {
    this.id = id;
  }

  public boolean containsOutput(int outputID) {
    if (this.outputs != null) {
      for (IPElement output : this.outputs) {
        if (output.getId() == outputID) {
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Activity) {
      Activity a = (Activity) obj;
      return a.getId() == this.id;
    }
    return false;
  }

  public List<ActivityPartner> getActivityPartners() {
    return activityPartners;
  }

  /**
   * This method calculates all the years between the start date and the end date.
   * 
   * @return a List of numbers representing all the years, or an empty list if nothing found.
   */
  public List<Integer> getAllYears() {
    List<Integer> allYears = new ArrayList<>();
    if (startDate != null && endDate != null) {
      Calendar calendarStart = Calendar.getInstance();
      calendarStart.setTime(startDate);
      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(endDate);

      while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
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
   * The convention is going to be used depending on the creation date of the activity.
   * yyyy-activityID => e.g. 2014-12
   * 
   * @return the composed identifier or null if the created date is null.
   */
  public String getComposedId() {
    if (created != 0) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(this.created);
      return calendar.get(Calendar.YEAR) + "-" + this.id;
    }
    return null;
  }

  public long getCreated() {
    return created;
  }

  public List<IPCrossCutting> getCrossCuttings() {
    return crossCuttings;
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

  public User getExpectedLeader() {
    return expectedLeader;
  }

  public int getId() {
    return id;
  }

  /**
   * This method search if the list of indicators contains an indicator
   * which parent is identified by the value passed as parameter.
   * 
   * @param indicatorID - indicator identifier
   * @return If the indicator is found, the method returns it. Otherwise, return null
   */
  public IPIndicator getIndicatorByParent(int parentIndicatorID) {
    if (indicators != null) {
      for (IPIndicator indicator : this.indicators) {
        if (indicator.getParent() != null) {
          if (indicator.getParent().getId() == parentIndicatorID) {
            return indicator;
          }
        }
      }
    }
    return null;
  }

  public List<IPIndicator> getIndicators() {
    return indicators;
  }

  public IPOtherContribution getIpOtherContribution() {
    return ipOtherContribution;
  }

  public User getLeader() {
    return leader;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public List<IPElement> getOutputs() {
    return outputs;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getTitle() {
    return title;
  }

  public double getTotalActivitiesBudget() {
    double totalBudget = 0.0;
    for (Budget budget : this.getBudgets()) {
      totalBudget += budget.getAmount();
    }
    return totalBudget;
  }

  @Override
  public int hashCode() {
    return id;
  }

  public boolean isGlobal() {
    return isGlobal;
  }

  public void setActivityPartners(List<ActivityPartner> partners) {
    this.activityPartners = partners;
  }

// public void setExpectedLeader(ExpectedActivityLeader expectedLeader) {
// this.expectedLeader = expectedLeader;
// }

  public void setBudgets(List<Budget> budgets) {
    this.budgets = budgets;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setCrossCuttings(List<IPCrossCutting> crossCuttings) {
    this.crossCuttings = crossCuttings;
  }

  public void setDeliverables(List<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setExpectedLeader(User expectedLeader) {
    this.expectedLeader = expectedLeader;
  }

  public void setGlobal(boolean isGlobal) {
    this.isGlobal = isGlobal;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicators(List<IPIndicator> indicators) {
    this.indicators = indicators;
  }

  public void setIpOtherContribution(IPOtherContribution ipOtherContribution) {
    this.ipOtherContribution = ipOtherContribution;
  }


  public void setLeader(User leader) {
    this.leader = leader;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public void setOutputs(List<IPElement> outputs) {
    this.outputs = outputs;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}