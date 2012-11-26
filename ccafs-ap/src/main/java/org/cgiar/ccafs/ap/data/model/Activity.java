package org.cgiar.ccafs.ap.data.model;

import java.util.Date;

public class Activity {

  private int id;
  private String title;
  private Date startDate;
  private Date endDate;
  private String description;
  private Milestone milestone;
  private Leader leader;
  private boolean isPlanning;
  private boolean isGlobal;

  public Activity() {
  }

  public Activity(int id, String title, Date startDate, Date endDate, String description, Milestone milestone,
    Leader leader, boolean isPlanning, boolean isGlobal) {
    this.id = id;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.description = description;
    this.milestone = milestone;
    this.leader = leader;
    this.isPlanning = isPlanning;
    this.isGlobal = isGlobal;
  }

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public Milestone getMilestone() {
    return milestone;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getTitle() {
    return title;
  }

  public boolean isGlobal() {
    return isGlobal;
  }

  public boolean isPlanning() {
    return isPlanning;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setGlobal(boolean isGlobal) {
    this.isGlobal = isGlobal;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setMilestone(Milestone milestone) {
    this.milestone = milestone;
  }

  public void setPlanning(boolean isPlanning) {
    this.isPlanning = isPlanning;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setTitle(String title) {
    this.title = title;
  }


}
