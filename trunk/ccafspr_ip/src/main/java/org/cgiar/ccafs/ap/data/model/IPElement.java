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

import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class IPElement {

  private int id;
  private String description;
  private IPElementType type;
  private IPProgram program;
  private List<IPIndicator> indicators;
  private List<IPElement> contributesTo;

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IPElement)) {
      return false;
    }

    if (obj == this) {
      return true;
    }

    IPElement element = (IPElement) obj;
    if (this.id == element.id) {
      return true;
    }

    return false;
  }

  public List<IPElement> getContributesTo() {
    return contributesTo;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public List<IPIndicator> getIndicators() {
    return indicators;
  }

  public IPProgram getProgram() {
    return program;
  }

  public IPElementType getType() {
    return type;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31).append(id).toHashCode();
  }

  public void setContributesTo(List<IPElement> contributesTo) {
    this.contributesTo = contributesTo;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicators(List<IPIndicator> indicator) {
    this.indicators = indicator;
  }

  public void setProgram(IPProgram program) {
    this.program = program;
  }

  public void setType(IPElementType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}