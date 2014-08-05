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
 * This class represents the expected next user of a specific deliverable.
 * This is part of the theory of change that is being implemented for CCAFS.
 *
 * @author Héctor Fabio Tobón R.
 */
public class NextUser {

  private int id;
  private String user;
  private String expectedChanges;
  private String strategies;

  public NextUser() {

  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof NextUser) {
      NextUser n = (NextUser) obj;
      return n.id == this.id;
    }
    return false;
  }

  public String getExpectedChanges() {
    return expectedChanges;
  }

  public int getId() {
    return id;
  }

  public String getStrategies() {
    return strategies;
  }

  public String getUser() {
    return user;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setExpectedChanges(String expectedChanges) {
    this.expectedChanges = expectedChanges;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setStrategies(String strategies) {
    this.strategies = strategies;
  }

  public void setUser(String user) {
    this.user = user;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
