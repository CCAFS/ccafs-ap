package org.cgiar.ccafs.ap.data.model;


import org.apache.commons.lang3.builder.ToStringBuilder;


public class Leverage {

  private int id;
  private String title;
  private double budget;
  private int start_year;
  private int end_year;
  private Theme theme;
  private Leader leader;
  private String partnerName;

  public double getBudget() {
    return budget;
  }

  public int getEnd_year() {
    return end_year;
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

  public int getStart_year() {
    return start_year;
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

  public void setEnd_year(int end_year) {
    this.end_year = end_year;
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

  public void setStart_year(int start_year) {
    this.start_year = start_year;
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