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
 * This class represents a CCAFS Activity, which belongs to a specific Project.
 * 
 * @author Héctor Fabio Tobón R.
 */
public class BoardMessage {

  private int id;
  private String message;
  private long created;


  public BoardMessage() {

  }


  public BoardMessage(int id) {
    this.id = id;
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof BoardMessage) {
      BoardMessage a = (BoardMessage) obj;
      return a.getId() == this.id;
    }
    return false;
  }


  public long getCreated() {
    return created;
  }


  public int getId() {
    return id;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public int hashCode() {
    return id;
  }

  public void setCreated(long created) {
    this.created = created;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setMessage(String message) {
    this.message = message;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
