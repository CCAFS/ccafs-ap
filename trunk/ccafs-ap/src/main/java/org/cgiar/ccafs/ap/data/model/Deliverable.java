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
