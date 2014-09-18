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
 * This class is a representation of a specific location, that's why it extends from Location.class.
 * It will be used for the other Location Elements Types different from countries and regions.
 * 
 * @author Javier Andrés Gallego
 */
public class OtherLocation extends Location {

  private LocationType type;
  private LocationGeoposition geoPosition;

  private Country country;

  public OtherLocation() {
  }

  public Country getCountry() {
    return country;
  }

  public LocationGeoposition getGeoPosition() {
    return geoPosition;
  }

  public LocationType getType() {
    return type;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setGeoPosition(LocationGeoposition geoPosition) {
    this.geoPosition = geoPosition;
  }

  public void setType(LocationType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
