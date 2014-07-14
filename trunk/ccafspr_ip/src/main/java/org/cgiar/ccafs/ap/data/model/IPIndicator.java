package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IPIndicator {

  private int id;
  private String description;
  private String target;

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public String getTarget() {
    return target;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTarget(String target) {
    this.target = target;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
