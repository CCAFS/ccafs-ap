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
  // private ExpectedActivityLeader expectedLeader;
  private List<IPCrossCutting> crossCuttings;// The list of Cross Cutting themes in which this project works with.
  private List<Location> locations;
  private long created;
  private List<IPElement> outputs;

  public Activity() {

  }

  public Activity(int id) {
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

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

// public ExpectedActivityLeader getExpectedLeader() {
// return expectedLeader;
// }

  public int getId() {
    return id;
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

  @Override
  public int hashCode() {
    return id;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setCrossCuttings(List<IPCrossCutting> crossCuttings) {
    this.crossCuttings = crossCuttings;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

// public void setExpectedLeader(ExpectedActivityLeader expectedLeader) {
// this.expectedLeader = expectedLeader;
// }

  public void setId(int id) {
    this.id = id;
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

  public User getExpectedLeader() {
    return expectedLeader;
  }

  public void setExpectedLeader(User expectedLeader) {
    this.expectedLeader = expectedLeader;
  }

}
