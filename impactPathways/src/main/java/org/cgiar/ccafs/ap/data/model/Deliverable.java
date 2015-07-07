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

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * This class represents a CCAFS Product.
 * Each deliverable will have to belong a specific activity, and will be related to a specific MOG in the Impact
 * Pathway.
 *
 * @author Héctor Fabio Tobón R.
 */
public class Deliverable {

  private int id;
  private String title;
  private int year;
  private DeliverableType type;
  private String typeOther; // Other specific type defined by the user.
  private List<NextUser> nextUsers;
  private IPElement output; // Now it is called MOG.
  private List<DeliverablePartner> partnerships;

  public Deliverable() {
    super();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Deliverable) {
      Deliverable v = (Deliverable) obj;
      return v.id == this.id;
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public List<NextUser> getNextUsers() {
    return nextUsers;
  }

  public IPElement getOutput() {
    return output;
  }

  public List<DeliverablePartner> getPartnerships() {
    return partnerships;
  }

  public String getTitle() {
    return title;
  }

  public DeliverableType getType() {
    return type;
  }

  public String getTypeOther() {
    return typeOther;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNextUsers(List<NextUser> nextUsers) {
    this.nextUsers = nextUsers;
  }

  public void setOutput(IPElement output) {
    this.output = output;
  }

  public void setPartnerships(List<DeliverablePartner> partnerships) {
    this.partnerships = partnerships;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(DeliverableType type) {
    this.type = type;
  }

  public void setTypeOther(String typeOther) {
    this.typeOther = typeOther;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
