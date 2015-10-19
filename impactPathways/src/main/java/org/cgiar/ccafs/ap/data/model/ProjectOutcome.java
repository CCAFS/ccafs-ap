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
 * This class represents a Project Outcome.
 * Is mostly used for the Planning section.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class ProjectOutcome {

  private int id;
  private int year;
  private String statement;
  // private String stories;
  // private String genderDimension;

  public ProjectOutcome() {
  }

  public ProjectOutcome(int id) {
    this.id = id;
  }

  // public String getGenderDimension() {
  // return genderDimension;
  // }

  public int getId() {
    return id;
  }

  public String getStatement() {
    return statement;
  }

  // public String getStories() {
  // return stories;
  // }

  public int getYear() {
    return year;
  }

  // public void setGenderDimension(String genderDimension) {
  // this.genderDimension = genderDimension;
  // }

  public void setId(int id) {
    this.id = id;
  }

  public void setStatement(String statement) {
    this.statement = statement;
  }

  // public void setStories(String stories) {
  // this.stories = stories;
  // }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}