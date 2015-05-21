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

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public class IPIndicator {

  private int id;
  private String description;
  private String target;
  private IPIndicator parent;
  private IPElement outcome;
  private int year;

  public IPIndicator() {
  }

  public IPIndicator(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IPIndicator)) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    IPIndicator element = (IPIndicator) obj;
    if (this.id == element.id) {
      return true;
    }

    return false;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public IPElement getOutcome() {
    return outcome;
  }

  public IPIndicator getParent() {
    return parent;
  }

  public String getTarget() {
    return target;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31).append(id).toHashCode();
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOutcome(IPElement outcome) {
    this.outcome = outcome;
  }

  public void setParent(IPIndicator parent) {
    this.parent = parent;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}