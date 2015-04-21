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
 * This class represents a specific location. E.g. Country, Region, etc.
 * 
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal
 * @author Javier Andrés Gallego
 */
public abstract class Location {

  private int id;
  private String name;
  private String code;

  public Location() {
  }

  public Location(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getCode() {
    return code;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public OtherLocation getOtherLocationInstance() {
    if (this.isOtherLocation()) {
      return (OtherLocation) this;
    } else {
      return null;
    }
  }

  public boolean isClimateSmartVillage() {
    return (this instanceof ClimateSmartVillage);
  }

  public boolean isCountry() {
    return (this instanceof Country);
  }

  public boolean isOtherLocation() {
    return (this instanceof OtherLocation);
  }

  public boolean isRegion() {
    return (this instanceof Region);
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
