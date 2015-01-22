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


/**
 * @author Hernán David Carvajal
 */

public enum MetadataRequirement {
  Mandatory("Mandatory"), Optional("Optional"), NotRequired("Not required");

  private String name;

  private MetadataRequirement(String name) {
    this.name = name;
  }

  static public MetadataRequirement getMetadataRequirement(String name) {
    switch (name) {
      case "Mandatory":
        return MetadataRequirement.Mandatory;

      case "Optional":
        return MetadataRequirement.Optional;

      case "Not required":
        return MetadataRequirement.NotRequired;

      default:
        break;
    }
    return MetadataRequirement.NotRequired;
  }

  public String getMetadataRequirement() {
    return name;
  }

  public boolean isMandatory() {
    return this.name.equals(Mandatory.name());
  }

  public boolean isNotRequired() {
    return this.name.equals(NotRequired.name());
  }

  public boolean isOptional() {
    return this.name.equals(Optional.name());
  }
}
