/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * 
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * 
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R.  If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Leader {

  private int id;
  private String name;
  private String acronym;
  private LeaderType leaderType;
  private Region region;

  public Leader() {
  }

  public Leader(int id) {
    this.id = id;
  }

  public Leader(int id, String name, LeaderType leaderType) {
    this.id = id;
    this.name = name;
    this.leaderType = leaderType;
  }

  public Leader(int id, String acronym, String name) {
    this.id = id;
    this.name = name;
    this.acronym = acronym;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Leader) {
      Leader leader = (Leader) obj;
      return leader.getId() == this.getId();
    }
    return false;
  }

  public String getAcronym() {
    return acronym;
  }

  public int getId() {
    return id;
  }

  public LeaderType getLeaderType() {
    return leaderType;
  }

  public String getName() {
    return name;
  }

  public Region getRegion() {
    return region;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeaderType(LeaderType leaderType) {
    this.leaderType = leaderType;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}