package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IPProgramTypes {

  private int id;
  private String acronym;
  private int type_id;

  public String getAcronym() {
    return acronym;
  }

  public int getId() {
    return id;
  }


  public int getTypeId() {
    return type_id;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setTypeId(int type_id) {
    this.type_id = type_id;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
