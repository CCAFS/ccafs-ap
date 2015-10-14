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

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a submission made by some user in a specific P&R cycle.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class Submission {

  private int id;
  private String cycle;
  private short year;
  private User user;
  private Date dateTime;

  public Submission() {
  }

  public String getCycle() {
    return cycle;
  }

  public Date getDateTime() {
    return dateTime;
  }

  public int getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public short getYear() {
    return year;
  }

  public void setCycle(String cycle) {
    this.cycle = cycle;
  }

  public void setDateTime(Date dateTime) {
    this.dateTime = dateTime;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public void setYear(short year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
