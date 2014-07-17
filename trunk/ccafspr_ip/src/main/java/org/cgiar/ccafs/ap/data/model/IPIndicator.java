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


public class IPIndicator {

  private int id;
  private String description;
  private String target;

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public String getTarget() {
    return target;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTarget(String target) {
    this.target = target;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
