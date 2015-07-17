package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class OutcomeIndicator {

  private int id;
  private int code;
  private String description;
  private Theme theme;

  public int getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
