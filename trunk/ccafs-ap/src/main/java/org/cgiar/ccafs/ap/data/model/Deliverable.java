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

/**
 * @author Hernán David Carvajal
 */

package org.cgiar.ccafs.ap.data.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


public abstract class Deliverable {

  protected int id;
  protected int year;
  protected boolean isExpected;
  protected String description;
  protected String descriptionUpdate;
  protected DeliverableStatus status;
  protected DeliverableType type;
  protected List<String> filesNames;
  protected List<FileFormat> fileFormats;
  protected List<DeliverableMetadata> metadata;
  protected DeliverableTrafficLight trafficLight;

  public String getDescription() {
    return description;
  }

  public String getDescriptionUpdate() {
    return descriptionUpdate;
  }

  public List<FileFormat> getFileFormats() {
    return fileFormats;
  }

  public List<String> getFilesNames() {
    return filesNames;
  }

  public int getId() {
    return id;
  }

  public List<DeliverableMetadata> getMetadata() {
    return metadata;
  }

  public String getMetadataValue(int metadataID) {
    String value = "";
    for (DeliverableMetadata dmetadata : metadata) {
      if (dmetadata.getMetadata().getId() == metadataID) {
        value = dmetadata.getValue();
      }
    }

    return value;
  }

  public DeliverableStatus getStatus() {
    return status;
  }

  public DeliverableTrafficLight getTrafficLight() {
    return trafficLight;
  }

  public DeliverableType getType() {
    return type;
  }

  public int getYear() {
    return year;
  }

  public boolean isExpected() {
    return isExpected;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDescriptionUpdate(String descriptionUpdate) {
    this.descriptionUpdate = descriptionUpdate;
  }

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  public void setFileFormats(List<FileFormat> fileFormats) {
    this.fileFormats = fileFormats;
  }

  public void setFilesNames(List<String> filesNames) {
    this.filesNames = filesNames;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMetadata(List<DeliverableMetadata> metadata) {
    this.metadata = metadata;
  }

  public void setStatus(DeliverableStatus status) {
    this.status = status;
  }

  public void setTrafficLight(DeliverableTrafficLight trafficLight) {
    this.trafficLight = trafficLight;
  }

  public void setType(DeliverableType type) {
    this.type = type;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}