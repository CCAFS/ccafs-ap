package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class MilestoneReport {

  private int id;
  private String themeLeaderDescription;
  private String regionalLeaderDescription;
  Milestone milestone;
  MilestoneStatus status;


  public MilestoneReport() {
  }

  public MilestoneReport(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }


  public Milestone getMilestone() {
    return milestone;
  }


  public String getRegionalLeaderDescription() {
    return regionalLeaderDescription;
  }


  public MilestoneStatus getStatus() {
    return status;
  }


  public String getThemeLeaderDescription() {
    return themeLeaderDescription;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setMilestone(Milestone milestone) {
    this.milestone = milestone;
  }


  public void setRegionalLeaderDescription(String regionalLeaderDescription) {
    this.regionalLeaderDescription = regionalLeaderDescription;
  }


  public void setStatus(MilestoneStatus status) {
    this.status = status;
  }


  public void setThemeLeaderDescription(String themeLeaderDescription) {
    this.themeLeaderDescription = themeLeaderDescription;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
