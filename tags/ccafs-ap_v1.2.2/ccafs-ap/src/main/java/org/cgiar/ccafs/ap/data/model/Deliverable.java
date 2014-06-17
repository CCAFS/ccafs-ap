/*
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
 */

package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Deliverable {

  private int id;
  private int year;
  private boolean isExpected;
  private String description;
  private String fileName;
  private String descriptionUpdate;
  private DeliverableStatus status;
  private DeliverableType type;
  private List<FileFormat> fileFormats;

  public Deliverable() {
  }

  public String getDescription() {
    return description;
  }

  public String getDescriptionUpdate() {
    return descriptionUpdate;
  }

  public List<FileFormat> getFileFormats() {
    if (fileFormats != null) {
      return fileFormats;
    } else {
      return new ArrayList<>();
    }
  }

  public ArrayList<String> getFileFormatsIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getFileFormats().size(); c++) {
      ids.add(getFileFormats().get(c).getId() + "");
    }
    return ids;
  }

  public String getFileName() {
    return fileName;
  }

  public int getId() {
    return id;
  }

  public DeliverableStatus getStatus() {
    return status;
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

  public void setDescriptionUpdate(String desriptionUpdate) {
    this.descriptionUpdate = desriptionUpdate;
  }

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  public void setFileFormats(List<FileFormat> fileFormats) {
    this.fileFormats = fileFormats;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setStatus(DeliverableStatus status) {
    this.status = status;
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
