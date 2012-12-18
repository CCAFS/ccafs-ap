package org.cgiar.ccafs.ap.data.model;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

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
  private ContactPerson[] contactPersons;
  private String genderIntegrationsDescription;
  private Status status;
  private Budget budget;
  private String statusDescription;
  private List<Deliverable> deliverables;
  private List<Partner> partners;

  public Activity() {
  }

  public Budget getBudget() {
    return budget;
  }

  public ContactPerson[] getContactPersons() {
    return contactPersons;
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }

  public String getDescription() {
    return description;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getGenderIntegrationsDescription() {
    return genderIntegrationsDescription;
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

  public List<Partner> getPartners() {
    return partners;
  }

  public Date getStartDate() {
    return startDate;
  }

  public Status getStatus() {
    return status;
  }

  public String getStatusDescription() {
    return statusDescription;
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

  public void setBudget(Budget budget) {
    this.budget = budget;
  }

  public void setContactPersons(ContactPerson[] contactPersons) {
    this.contactPersons = contactPersons;
  }

  public void setDeliverables(List<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setGenderIntegrationsDescription(String genderIntegrationsDescription) {
    this.genderIntegrationsDescription = genderIntegrationsDescription;
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

  public void setPartners(List<Partner> partners) {
    this.partners = partners;
  }

  public void setPlanning(boolean isPlanning) {
    this.isPlanning = isPlanning;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public void setStatusDescription(String statusDescription) {
    this.statusDescription = statusDescription;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


}
