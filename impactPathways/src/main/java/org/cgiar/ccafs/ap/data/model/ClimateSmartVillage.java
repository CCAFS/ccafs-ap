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


/**
 * @author Hernán David Carvajal
 */

public class ClimateSmartVillage extends Location {

  private OtherLocation ccafsSite;
  private LocationGeoposition geoPosition;

  public OtherLocation getCcafsSite() {
    return ccafsSite;
  }

  public LocationGeoposition getGeoPosition() {
    return geoPosition;
  }

  public void setCcafsSite(OtherLocation ccafsSite) {
    this.ccafsSite = ccafsSite;
  }

  public void setGeoPosition(LocationGeoposition geoPosition) {
    this.geoPosition = geoPosition;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
