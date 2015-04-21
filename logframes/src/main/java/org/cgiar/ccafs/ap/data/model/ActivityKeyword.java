package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class ActivityKeyword {

  private int id;
  private String other;
  private Keyword keyword;

  public int getId() {
    return id;
  }

  public Keyword getKeyword() {
    return keyword;
  }

  public String getOther() {
    return other;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setKeyword(Keyword keyword) {
    this.keyword = keyword;
  }

  public void setOther(String other) {
    this.other = other;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}