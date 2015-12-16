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


public enum CategoryCrossCutingEnum {


  A("A", "Promotion of a product, launch of a book, big global event"), B("B", "Production process of an output "),
  C("C", "Workshop/ training");

  public static CategoryCrossCutingEnum getEnum(String value) {
    for (CategoryCrossCutingEnum v : values()) {
      if (v.getId().equalsIgnoreCase(value)) {
        return v;
      }
    }
    throw new IllegalArgumentException();
  }

  public static CategoryCrossCutingEnum value(String id) {

    CategoryCrossCutingEnum[] types = CategoryCrossCutingEnum.values();
    for (CategoryCrossCutingEnum projectHighlightsType : types) {
      if (projectHighlightsType.getId().equals(id)) {
        return projectHighlightsType;
      }

    }
    return null;

  }

  private String description;

  private String id;

  private CategoryCrossCutingEnum(String id, String description) {
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
