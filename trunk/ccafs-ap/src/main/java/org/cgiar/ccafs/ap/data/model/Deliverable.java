package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Deliverable {

  private int id;
  private String description;
  private boolean isExpected;
  private int year;
  private DeliverableStatus status;
  private DeliverableType type;
  private List<FileFormat> fileFormats;

  public Deliverable() {
  }

  public String getDescription() {
    return description;
  }

  public List<FileFormat> getFileFormats() {
    if (fileFormats != null) {
      return fileFormats;
    } else {
      return new ArrayList<>();
    }
  }

  public int[] getFileFormatsIds() {
    int[] ids = new int[this.getFileFormats().size()];
    for (int c = 0; c < ids.length; c++) {
      ids[c] = getFileFormats().get(c).getId();
    }
    return ids;
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

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  public void setFileFormats(List<FileFormat> fileFormats) {
    this.fileFormats = fileFormats;
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
