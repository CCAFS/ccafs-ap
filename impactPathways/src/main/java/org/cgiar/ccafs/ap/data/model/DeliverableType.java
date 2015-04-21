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
 * This class represents a CCAFS Product type.
 * Each deliverable will have to be relate to one deliverable type.
 * The timeline is used in months and depends on the Open Access Policy implemented by the CGIAR.
 *
 * @author Héctor Fabio Tobón R.
 */
public class DeliverableType {

  private int id;
  private String name;
  private int timeline; // in months.
  private DeliverableType category;

  public DeliverableType() {

  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DeliverableType) {
      DeliverableType d = (DeliverableType) obj;
      return d.id == this.id;
    }
    return false;
  }

  public DeliverableType getCategory() {
    return category;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getTimeline() {
    return timeline;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setCategory(DeliverableType category) {
    this.category = category;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setTimeline(int timeline) {
    this.timeline = timeline;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
