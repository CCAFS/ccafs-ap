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

import org.cgiar.ccafs.ap.config.APConstants;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IPProgram {

  private int id;
  private String name;
  private String acronym;
  private Region region;
  private IPProgramType type;
  private long created;

  private String error = "";

  public IPProgram() {
  }

  public IPProgram(int id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof IPProgram) {
      IPProgram ip = (IPProgram) obj;
      return ip.getId() == this.id;
    }
    return false;
  }


  public String getAcronym() {
    return acronym;
  }


  /**
   * This method returns a composed IPProgram name with the acronym on it.
   * 
   * @return a composed program name in the format "Acronym: Name":
   *         e.g. RPL LAM: Latin America
   */
  public String getComposedName() {
    StringBuilder builder = new StringBuilder();
    if (this.acronym != null) {
      builder.append(acronym);
      builder.append(": ");
    }
    if (this.name != null) {
      builder.append(name);
    }
    return builder.toString();
  }

  public long getCreated() {
    return created;
  }

  public Date getCreationDate() {
    return new Date(created);
  }

  public String getError() {
    return error;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Region getRegion() {
    return region;
  }

  public IPProgramType getType() {
    return type;
  }

  @Override
  public int hashCode() {
    return id;
  }

  public boolean isFlagshipProgram() {
    if (type != null) {
      return (this.type.getId() == APConstants.FLAGSHIP_PROGRAM_TYPE);
    } else {
      return false;
    }
  }

  public boolean isRegionalProgram() {
    if (type != null) {
      return (this.type.getId() == APConstants.REGION_PROGRAM_TYPE);
    } else {
      return false;
    }
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public void setType(IPProgramType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
