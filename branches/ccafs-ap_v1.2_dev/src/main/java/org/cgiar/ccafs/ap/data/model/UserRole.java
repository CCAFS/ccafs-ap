package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class UserRole {

  private int id;
  private String name;

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isAdmin() {
    return getName().equals("Admin");
  }

  public boolean isCP() {
    return getName().equals("CP");
  }

  public boolean isPI() {
    return getName().equals("PI");
  }

  public boolean isRPL() {
    return getName().equals("RPL");
  }

  public boolean isTL() {
    return getName().equals("TL");
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
