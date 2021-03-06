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


public enum ChannelEnum {


  CGSPACE("cgspace", "CGSpace"), OTHER("other", "Other");


  private String id;
  private String desc;

  private ChannelEnum(String id, String desc) {
    this.desc = desc;
    this.id = id;
  }


  public String getDesc() {
    return desc;
  }


  public String getId() {
    return id;
  }


  public void setDesc(String desc) {
    this.desc = desc;
  }


  public void setId(String id) {
    this.id = id;
  }


}
