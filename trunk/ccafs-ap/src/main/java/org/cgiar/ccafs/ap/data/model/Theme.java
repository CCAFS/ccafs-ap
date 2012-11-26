package org.cgiar.ccafs.ap.data.model;


public class Theme {

  private int id;
  private String code;
  private String description;
  private Logframe logframe;

  public Theme(int id) {
    this.id = id;
  }

  public Theme(int id, String code, String description, Logframe logframe) {
    super();
    this.id = id;
    this.code = code;
    this.description = description;
    this.logframe = logframe;
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

  public Logframe getLogframe() {
    return logframe;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

}
