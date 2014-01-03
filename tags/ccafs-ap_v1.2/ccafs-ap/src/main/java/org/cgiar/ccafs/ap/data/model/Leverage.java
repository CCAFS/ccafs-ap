package org.cgiar.ccafs.ap.data.model;


import org.apache.commons.lang3.builder.ToStringBuilder;


public class Leverage {

  private int id;
  private String title;
  private double budget;
  private int startYear;
  private int endYear;
  private Theme theme;
  private Leader leader;
  private String partnerName;

  public double getBudget() {
    return budget;
  }

  public int getEndYear() {
    return endYear;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public String getPartnerName() {
    return partnerName;
  }

  public int getStartYear() {
    return startYear;
  }

  public Theme getTheme() {
    return theme;
  }

  public String getTitle() {
    return title;
  }

  public void setBudget(double budget) {
    this.budget = budget;
  }

  public void setEndYear(int endYear) {
    this.endYear = endYear;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setPartnerName(String partnerName) {
    this.partnerName = partnerName;
  }

  public void setStartYear(int startYear) {
    this.startYear = startYear;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}