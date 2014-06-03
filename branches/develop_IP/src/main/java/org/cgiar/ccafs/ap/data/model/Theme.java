package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Theme {

  private int id;
  private String code;
  private String description;
  private Logframe logframe;

  public Theme() {
  }

  public Theme(int id) {
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

  public Logframe getLogframe() {
    return logframe;
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

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
