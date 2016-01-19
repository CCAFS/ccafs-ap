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
 * @author Christian David Garcia Oviedo
 */

public class DeliverableFile {

  private int id;
  private String name;
  private String link;
  private long size;
  private String hosted;

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof DeliverableFile) {
      DeliverableFile v = (DeliverableFile) obj;
      return v.id == this.id;
    }
    return false;
  }

  public String getHosted() {
    return hosted;
  }

  public int getId() {
    return id;
  }

  public String getLink() {
    return link;
  }

  public String getName() {
    return name;
  }

  public long getSize() {
    return size;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setHosted(String hosted) {
    this.hosted = hosted;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSize(long size) {
    this.size = size;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}