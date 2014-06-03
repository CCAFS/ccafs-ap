package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Objective {

  private int id;
  private String code;
  private String description;
  private String outcomeDescription;
  private Theme theme;

  public Objective() {
  }

  public Objective(int id) {
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public String getOutcomeDescription() {
    return outcomeDescription;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOutcomeDescription(String outcomeDescription) {
    this.outcomeDescription = outcomeDescription;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
