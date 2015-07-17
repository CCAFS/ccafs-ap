package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class MilestoneStatus {

  private int id;
  private String name;


  public MilestoneStatus() {
  }

  public MilestoneStatus(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }


  public String getName() {
    return name;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setName(String name) {
    this.name = name;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
