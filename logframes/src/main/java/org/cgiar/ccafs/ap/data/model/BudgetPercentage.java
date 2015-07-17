package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class BudgetPercentage {

  private int id;
  private String percentage;

  public BudgetPercentage() {
  }

  public int getId() {
    return id;
  }

  public String getPercentage() {
    return percentage;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setPercentage(String percentage) {
    this.percentage = percentage;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
