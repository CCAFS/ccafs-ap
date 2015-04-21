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
 * This class represents the IP Cross Cutting Theme
 *
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 */
public class IPCrossCutting {

  private int id;
  private String name;

  public IPCrossCutting() {
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IPCrossCutting) {
      IPCrossCutting c = (IPCrossCutting) obj;
      return c.getId() == this.id;
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  @Override
  public int hashCode() {
    return this.id;
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