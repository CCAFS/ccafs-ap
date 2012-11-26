package org.cgiar.ccafs.ap.data.model;


public class LeaderType {

  private int code;
  private String name;

  public LeaderType() {

  }

  public LeaderType(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public void setName(String name) {
    this.name = name;
  }

}
