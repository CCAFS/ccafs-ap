package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class DeliverableFormat {

  private int Code;
  private String name;

  public DeliverableFormat() {
  }

  public DeliverableFormat(int Code, String name) {
    this.Code = Code;
    this.name = name;
  }

  public int getCode() {
    return Code;
  }

  public String getName() {
    return name;
  }

  public void setCode(int Code) {
    this.Code = Code;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
