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
  private int activityStatus;
  private String activityProgress;

  private Date startDate;


  private Date endDate;


  private PartnerPerson leader;


  private long created;

  public Activity() {
    super();
  }

  public Activity(int id) {
    super();
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Activity) {
      Activity a = (Activity) obj;
      return a.getId() == this.id;
    }
    return false;
  }

  public String getActivityProgress() {
    return activityProgress;
  }

  public int getActivityStatus() {
    return activityStatus;
  }

  /**
   * This method calculates all the years between the start date and the end date.
   * 
   * @return a List of numbers representing all the years, or an empty list if nothing found.
   **/
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

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

  public int getId() {
    return id;
  }

  public PartnerPerson getLeader() {
    return leader;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getTitle() {
    return title;
  }

  @Override
  public int hashCode() {
    return id;
  }

  /**
   * Return if the activity is new.
   * An activity is new when it was created in the planning phase for the current year
   * 
   * @param currentPlanningYear
   * @return true if the activity is recent, false otherwise
   */
  public boolean isNew(Date planningStartDate) {
    return this.created >= planningStartDate.getTime();
  }


  /**
   * Check if the activity status is "Cancelled"
   * 
   * @return true if activity status is "Cancelled" else false
   */
  public boolean isStatusCancelled() {
    try {
      // 5- Cancelled - The activity has been cancelled.
      return activityStatus == 5;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isStatusComplete() {
    try {
      // 3- Complete
      return activityStatus == 3;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isStatusExtended() {
    try {
      // 4 - Extended
      return activityStatus == 4;
    } catch (Exception e) {
      return false;
    }
  }

  public boolean isStatusOnGoing() {
    try {
      // 2 - On Going
      return activityStatus == 2;
    } catch (Exception e) {
      return false;
    }
  }

  public void setActivityProgress(String activityProgress) {
    this.activityProgress = activityProgress;
  }

  public void setActivityStatus(int activityStatus) {
    this.activityStatus = activityStatus;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(PartnerPerson leader) {
    this.leader = leader;
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