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


public enum ProjectHighlightsType {

  Gender_and_social_inclusion("1", "Gender and social inclusion"),
  Innovative_non_research_partnerships("2", "Innovative non-research partnerships"),
  Participatory_action_research("3", "Participatory action research"),
  Successful_communications("4", "Successful communications"), Capacity_enhancement("5", "Capacity enhancement"),
  Breakthrough_science("6", "Breakthrough science"), Inter_center_collaboration("7", "Inter-center collaboration"),
  Policy_engagement("8", "Policy engagement"), Food_security("9", "Food security");

  public static ProjectHighlightsType getEnum(String value) {
    for (ProjectHighlightsType v : values()) {
      if (v.getId().equalsIgnoreCase(value)) {
        return v;
      }
    }
    throw new IllegalArgumentException();
  }

  private String description;

  private String id;

  private ProjectHighlightsType(String id, String description) {
    this.description = description;
    this.id = id;
  }


  public String getDescription() {
    return description;
  }

  public String getId() {
    return id;
  }


}
