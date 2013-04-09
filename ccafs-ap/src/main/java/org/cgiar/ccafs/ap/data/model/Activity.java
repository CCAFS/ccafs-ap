package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
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
  private List<ContactPerson> contactPersons;
  private String genderIntegrationsDescription;
  private Status status;
  private Budget budget;
  private String statusDescription;
  private List<Deliverable> deliverables;
  private List<ActivityPartner> activityPartners;
  private Date dateAdded;
  private List<ActivityObjective> objectives;
  private List<Resource> resources;
  private List<ActivityKeyword> keywords;
  private List<Country> countries;
  private List<BenchmarkSite> bsLocations;
  private List<OtherSite> otherLocations;
  private boolean commissioned;
  private Activity continuousActivity;

  public Activity() {
  }

  public List<ActivityPartner> getActivityPartners() {
    return activityPartners;
  }

  public List<String> getBenchmarkSitesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getBsLocations().size(); c++) {
      ids.add(getBsLocations().get(c).getId() + "");
    }
    return ids;
  }

  public List<BenchmarkSite> getBsLocations() {
    return bsLocations;
  }

  public Budget getBudget() {
    return budget;
  }

  public List<ContactPerson> getContactPersons() {
    return contactPersons;
  }

  public Activity getContinuousActivity() {
    return continuousActivity;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<String> getCountriesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getCountries().size(); c++) {
      ids.add(getCountries().get(c).getId() + "");
    }
    return ids;
  }

  public Date getDateAdded() {
    return dateAdded;
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

  public List<ActivityKeyword> getKeywords() {
    return keywords;
  }

  public Leader getLeader() {
    return leader;
  }

  public Milestone getMilestone() {
    return milestone;
  }

  public List<ActivityObjective> getObjectives() {
    return objectives;
  }

  public List<OtherSite> getOtherLocations() {
    return otherLocations;
  }

  public List<Resource> getResources() {
    return resources;
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

  public boolean isCommissioned() {
    return commissioned;
  }

  public boolean isGlobal() {
    return isGlobal;
  }

  public boolean isPlanning() {
    return isPlanning;
  }

  public void setActivityPartners(List<ActivityPartner> activityPartners) {
    this.activityPartners = activityPartners;
  }

  public void setBsLocations(List<BenchmarkSite> bsLocations) {
    this.bsLocations = bsLocations;
  }

  public void setBudget(Budget budget) {
    this.budget = budget;
  }

  public void setCommissioned(boolean isCommissioned) {
    this.commissioned = isCommissioned;
  }

  public void setContactPersons(List<ContactPerson> contactPersons) {
    this.contactPersons = contactPersons;
  }

  public void setContinuousActivity(Activity continuousActivity) {
    this.continuousActivity = continuousActivity;
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setDateAdded(Date dateAdded) {
    this.dateAdded = dateAdded;
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

  public void setKeywords(List<ActivityKeyword> keywords) {
    this.keywords = keywords;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setMilestone(Milestone milestone) {
    this.milestone = milestone;
  }

  public void setObjectives(List<ActivityObjective> objectives) {
    this.objectives = objectives;
  }

  public void setOtherLocations(List<OtherSite> otherLocations) {
    this.otherLocations = otherLocations;
  }

  public void setPlanning(boolean isPlanning) {
    this.isPlanning = isPlanning;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
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
