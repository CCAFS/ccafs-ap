package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Budget {

  private int id;
  private double usd;
  private BudgetPercentage cgFund;
  private BudgetPercentage bilateral;

  public Budget() {
  }

  public BudgetPercentage getBilateral() {
    return bilateral;
  }

  public BudgetPercentage getCgFund() {
    return cgFund;
  }

  public int getId() {
    return id;
  }

  public double getUsd() {
    return usd;
  }

  public void setBilateral(BudgetPercentage bilateral) {
    this.bilateral = bilateral;
  }

  public void setCgFund(BudgetPercentage cgFund) {
    this.cgFund = cgFund;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setUsd(double usd) {
    this.usd = usd;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
