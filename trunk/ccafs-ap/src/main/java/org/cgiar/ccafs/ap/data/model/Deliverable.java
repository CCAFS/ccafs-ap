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

import org.cgiar.ccafs.ap.config.APConstants;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.builder.ToStringBuilder;


public abstract class Deliverable {

  protected int id;
  protected int year;
  protected boolean isExpected;
  protected String description;
  protected String descriptionUpdate;
  protected String disseminationDescription;
  protected DeliverableStatus status;
  protected DeliverableType type;
  protected List<DeliverableFile> files;
  protected List<FileFormat> fileFormats;
  protected List<DeliverableMetadata> metadata;
  protected DeliverableTrafficLight trafficLight;
  protected DeliverableAccess accessDetails;
  protected Map<Integer, Double> scores;

  public DeliverableAccess getAccessDetails() {
    return accessDetails;
  }

  public double getAverageScore() {
    double sum = 0;
    int records = 0;
    for (Entry<Integer, Double> entry : scores.entrySet()) {
      sum += entry.getValue();
      records++;
    }
    return (records > 0) ? sum / records : 0;
  }

  public String getDescription() {
    return description;
  }

  public String getDescriptionUpdate() {
    return descriptionUpdate;
  }

  public String getDisseminationDescription() {
    return disseminationDescription;
  }

  public List<FileFormat> getFileFormats() {
    return fileFormats;
  }

  public List<DeliverableFile> getFiles() {
    return files;
  }

  public int getId() {
    return id;
  }

  public List<DeliverableMetadata> getMetadata() {
    return metadata;
  }

  public int getMetadataID(String metadataName) {
    for (DeliverableMetadata mData : metadata) {
      if (mData.getMetadata().getName().equals(metadataName)) {
        return mData.getMetadata().getId();
      }
    }
    return -1;
  }


  public int getMetadataIndex(String metadataName) {
    int c = 1;
    for (DeliverableMetadata mData : metadata) {
      if (mData.getMetadata().getName().equals(metadataName)) {
        return c;
      }
      c++;
    }
    return -1;
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

  public String getMetadataValue(String metadataName) {
    int c = 0;
    for (DeliverableMetadata mData : metadata) {
      if (mData.getMetadata().getName().equals(metadataName)) {
        return mData.getValue();
      }
      c++;
    }
    return "";
  }

  public double getScoreByLeader(int activityLeaderID) {
    if (scores != null && !scores.isEmpty()) {
      if (scores.containsKey(activityLeaderID)) {
        return scores.get(activityLeaderID);
      }
    }
    return -1;
  }

  public Map<Integer, Double> getScores() {
    return scores;
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

  public boolean isData() {
    if (type == null) {
      return false;
    } else if (type.getParent() == null) {
      return type.getId() == APConstants.DELIVERABLE_TYPE_DATA;
    } else {
      return type.getParent().getId() == APConstants.DELIVERABLE_TYPE_DATA;
    }
  }

  public boolean isExpected() {
    return isExpected;
  }

  public boolean isJournalArticle() {
    if (type == null) {
      return false;
    }
    return type.getId() == APConstants.DELIVERABLE_SUBTYPE_JOURNAL;
  }

  public boolean isPublication() {
    if (type == null) {
      return false;
    }
    if (type.getParent() == null) {
      return type.getId() == APConstants.DELIVERABLE_TYPE_PUBLICATION;
    } else {
      return type.getParent().getId() == APConstants.DELIVERABLE_TYPE_PUBLICATION;
    }
  }

  public void setAccessDetails(DeliverableAccess accessDetails) {
    this.accessDetails = accessDetails;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDescriptionUpdate(String descriptionUpdate) {
    this.descriptionUpdate = descriptionUpdate;
  }

  public void setDisseminationDescription(String disseminationDescription) {
    this.disseminationDescription = disseminationDescription;
  }

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  public void setFileFormats(List<FileFormat> fileFormats) {
    this.fileFormats = fileFormats;
  }

  public void setFiles(List<DeliverableFile> files) {
    this.files = files;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setMetadata(List<DeliverableMetadata> metadata) {
    this.metadata = metadata;
  }

  public void setScores(Map<Integer, Double> scores) {
    this.scores = scores;
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