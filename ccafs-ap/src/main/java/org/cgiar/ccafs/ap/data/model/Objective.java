package org.cgiar.ccafs.ap.data.model;


public class Objective {

  private int id;
  private String code;
  private String description;
  private String outcomeDescription;
  private Theme theme;

  public Objective(int id) {
    this.id = id;
  }

  public Objective(int id, String code, String description, String outcomeDescription, Theme theme) {
    super();
    this.id = id;
    this.code = code;
    this.description = description;
    this.outcomeDescription = outcomeDescription;
    this.theme = theme;
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

  public void setOutcomeDescription(String outcomeDescription) {
    this.outcomeDescription = outcomeDescription;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

}
