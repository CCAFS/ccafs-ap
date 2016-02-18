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


  public IPElement() {
  }

  public IPElement(int id) {
    this.id = id;
  }

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

  /**
   * TODO HT
   * 
   * @return
   */
  public String getComposedId() {
    StringBuilder composedID = new StringBuilder();
    // composedID.append("O");
    if (this.program != null && this.program.getId() >= 5) {
      composedID.append(this.program != null ? this.program.getAcronym().substring(3) : "p_null");
    } else {
      composedID.append(this.program != null ? "F" + this.program.getAcronym().substring(3) : "p_null");
    }
    composedID.append("-");
    composedID.append(this.type != null ? this.type.getName() : "t_null");
    composedID.append(" #");
    composedID.append(this.id);
    return composedID.toString();
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

  public IPIndicator getIndicatorByParentID(int indicatorID) {
    for (IPIndicator _indicator : indicators) {
      if (_indicator.getParent() != null) {
        if (_indicator.getParent().getId() == indicatorID) {
          return _indicator;
        }
      }
    }
    return null;
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
   * @param hasParents - true if you want to get the indicators which have parents.
   *        false otherwise.
   * @return a list of IPIndicator objects with the information.
   */
  public List<IPIndicator> getIndicators(boolean hasParents) {
    List<IPIndicator> _indicators = new ArrayList<>();

    if (indicators != null) {
      for (int i = 0; i < indicators.size(); i++) {
        if ((indicators.get(i).getParent() != null) == hasParents) {
          _indicators.add(indicators.get(i));
        }
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

  /**
   * Check the IPElement program type if is Flagship Type
   * 
   * @return true if IPElement program type is Flagship else false
   */
  public boolean isFlagshipProgramType() {
    try {
      // 4 -Flagship program from table ip_program_types
      return program.getType().getId() == 4;
    } catch (Exception e) {
      return false;
    }
  }

  /**
   * Check the IPElement program type if is Regional Type
   * 
   * @return true if IPElement program type is Regional else false
   */
  public boolean isRegionalProgramType() {
    try {
      // 5 - Regional program from table ip_program_types
      return program.getType().getId() == 5;
    } catch (Exception e) {
      return false;
    }
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