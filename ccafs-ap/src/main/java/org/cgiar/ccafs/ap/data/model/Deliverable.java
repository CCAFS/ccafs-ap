package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Deliverable {

  private int code;
  private String description;
  private boolean isExpected;
  private int year;
  private DeliverableStatus status;
  private DeliverableType type;
  private DeliverableFormat deliverableFormat;


  public Deliverable() {
  }


  public Deliverable(int code) {
    this.code = code;
  }


  public int getCode() {
    return code;
  }


  public DeliverableFormat getDeliverableFormat() {
    return deliverableFormat;
  }


  public String getDescription() {
    return description;
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


  public void setCode(int code) {
    this.code = code;
  }


  public void setDeliverableFormat(DeliverableFormat deliverableFormat) {
    this.deliverableFormat = deliverableFormat;
  }


  public void setDescription(String description) {
    this.description = description;
  }

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
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
