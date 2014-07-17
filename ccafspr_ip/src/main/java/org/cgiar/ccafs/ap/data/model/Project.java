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

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Project {

  public int id;
  public String title;
  private ArrayList<IPProgram> types;

  public Project() {

  }


  public Project(int id, String title) {
    this.id = id;
    this.title = title;
  }


  public int getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<IPProgram> getTypes() {
    return types;
  }

  public void setId(int id) {
    this.id = id;
  }


  public void setTitle(String title) {
    this.title = title;
  }


  public void setTypes(ArrayList<IPProgram> types) {
    this.types = types;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}