/*
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
 */

package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Partner {

  private int id;
  private String name;
  private String acronym;
  private Country country;
  private String city;
  private String website;
  private PartnerType type;

  public Partner() {
  }

  public String getAcronym() {
    return acronym;
  }

  public String getCity() {
    return city;
  }

  public Country getCountry() {
    return country;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public PartnerType getType() {
    return type;
  }

  public String getWebsite() {
    return website;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setType(PartnerType type) {
    this.type = type;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
