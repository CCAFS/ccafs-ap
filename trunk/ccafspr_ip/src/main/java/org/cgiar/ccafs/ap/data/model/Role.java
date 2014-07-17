package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Role {

  public enum UserRole {
    Admin, FPL, RPL, CP, PI, CU, PO, PL
  }

  private int id;
  private String name;
  private String acronym;

  public String getAcronym() {
    return acronym;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
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
