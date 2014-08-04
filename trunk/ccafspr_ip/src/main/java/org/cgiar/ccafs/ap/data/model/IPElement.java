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

import java.util.ArrayList;
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
  private List<IPElement> translatedOf;

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

  public int[] getContributesToIDs() {

    if (contributesTo != null) {
      int[] idsList = new int[contributesTo.size()];
      for (int c = 0; c < contributesTo.size(); c++) {
        idsList[c] = contributesTo.get(c).getId();
      }
      return idsList;
    }

    return null;
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

  /**
   * This method returns all the indicators which fill the condition
   * of has parents.
   * If the parameter received is false, returns a list with all the
   * indicators which have not parents.
   * If the parameter received is true returns a list with all the
   * indicators that have parents.
   * 
   * @param hasParents
   * @return
   */
  public List<IPIndicator> getIndicators(boolean hasParents) {
    List<IPIndicator> _indicators = new ArrayList<>();
    for (int i = 0; i < indicators.size(); i++) {
      if ((indicators.get(i).getParent() != null) == hasParents) {
        _indicators.add(indicators.get(i));
      }
    }
    return _indicators;
  }

  public int[] getParentIndicatorsIDs() {

    if (indicators != null) {
      int[] indicatorsIDs = new int[indicators.size()];
      for (int c = 0; c < indicators.size(); c++) {
        if (indicators.get(c).getParent() != null) {
          indicatorsIDs[c] = indicators.get(c).getParent().getId();
        }
      }
      return indicatorsIDs;
    }

    return null;
  }

  public IPProgram getProgram() {
    return program;
  }

  public List<IPElement> getTranslatedOf() {
    return translatedOf;
  }

  public int[] getTranslatedOfIDs() {

    if (translatedOf != null) {
      int[] idsList = new int[translatedOf.size()];
      for (int c = 0; c < translatedOf.size(); c++) {
        idsList[c] = translatedOf.get(c).getId();
      }
      return idsList;
    }

    return null;
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

  public void setTranslatedOf(List<IPElement> translatedOf) {
    this.translatedOf = translatedOf;
  }

  public void setType(IPElementType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}