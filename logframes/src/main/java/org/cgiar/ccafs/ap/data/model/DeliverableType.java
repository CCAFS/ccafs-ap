package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class DeliverableType {

  private int id;
  private String name;
  private DeliverableType parent;

  public DeliverableType() {
  }

  public DeliverableType(int id) {
    this.id = id;
  }

  public DeliverableType(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public DeliverableType getParent() {
    return parent;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setParent(DeliverableType parent) {
    this.parent = parent;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}