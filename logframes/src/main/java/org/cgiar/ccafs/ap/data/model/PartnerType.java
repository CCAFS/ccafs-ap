package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class PartnerType {

  private int id;
  private String name;
  private String acronym;
  private String description;


  public PartnerType() {
  }

  public String getAcronym() {
    return acronym;
  }

  public String getDescription() {
    return description;
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

  public void setDescription(String description) {
    this.description = description;
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
