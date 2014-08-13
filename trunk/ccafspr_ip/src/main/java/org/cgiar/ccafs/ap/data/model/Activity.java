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
  private String customId;
  private String title;
  private String description;
  private Date start;
  private Date end;
  private User leader;
  private ExpectedActivityLeader expectedLeader;


  private List<Location> locations;

  private long created;
  private List<IPElement> outputs;

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Activity) {
      Activity a = (Activity) obj;
      return a.getId() == this.id;
    }
    return false;
  }

  public long getCreated() {
    return created;
  }

  public String getCustomId() {
    return customId;
  }

  public String getDescription() {
    return description;
  }

  public Date getEnd() {
    return end;
  }

  public ExpectedActivityLeader getExpectedLeader() {
    return expectedLeader;
  }

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

  public Date getStart() {
    return start;
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

  public void setCustomId(String customId) {
    this.customId = customId;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  public void setExpectedLeader(ExpectedActivityLeader expectedLeader) {
    this.expectedLeader = expectedLeader;
  }

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

  public void setStart(Date start) {
    this.start = start;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}