package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Indicator {

  private int id;
  private String serial;
  private String name;
  private String description;
  private boolean active;
  private IndicatorType type;

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSerial() {
    return serial;
  }

  public IndicatorType getType() {
    return type;
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean is_active) {
    this.active = is_active;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSerial(String serial) {
    this.serial = serial;
  }

  public void setType(IndicatorType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}