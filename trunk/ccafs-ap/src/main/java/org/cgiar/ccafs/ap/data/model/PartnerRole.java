package org.cgiar.ccafs.ap.data.model;


public class PartnerRole {

  private int id;
  private String name;

  public PartnerRole(int id, String name) {
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
}
