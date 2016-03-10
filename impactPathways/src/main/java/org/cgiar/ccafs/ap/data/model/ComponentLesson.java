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
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ComponentLesson {

  private int id;
  private int year;
  private String lessons;
  private String componentName;
  private int ipProgamId;


  public String getComponentName() {
    return componentName;
  }


  public int getId() {
    return id;
  }

  public int getIpProgamId() {
    return ipProgamId;
  }

  public String getLessons() {
    return lessons;
  }

  public int getYear() {
    return year;
  }

  public void setComponentName(String componentName) {
    this.componentName = componentName;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIpProgamId(int ipProgamId) {
    this.ipProgamId = ipProgamId;
  }

  public void setLessons(String lessons) {
    this.lessons = lessons;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
