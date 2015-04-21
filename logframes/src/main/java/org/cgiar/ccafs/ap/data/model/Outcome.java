package org.cgiar.ccafs.ap.data.model;


public class Outcome {

  private int id;
  private String title;
  private String outcome;
  private String outputs;
  private String activities;
  private String nonResearchPartners;
  private String partners;
  private String outputUser;
  private String howUsed;
  private String evidence;
  private Logframe logframe;
  private Leader leader;

  public Outcome() {
  }

  public String getActivities() {
    return activities;
  }

  public String getEvidence() {
    return evidence;
  }

  public String getHowUsed() {
    return howUsed;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getNonResearchPartners() {
    return nonResearchPartners;
  }

  public String getOutcome() {
    return outcome;
  }

  public String getOutputs() {
    return outputs;
  }

  public String getOutputUser() {
    return outputUser;
  }

  public String getPartners() {
    return partners;
  }

  public String getTitle() {
    return title;
  }

  public void setActivities(String activities) {
    this.activities = activities;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public void setHowUsed(String howUsed) {
    this.howUsed = howUsed;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setNonResearchPartners(String nonResearchPartners) {
    this.nonResearchPartners = nonResearchPartners;
  }

  public void setOutcome(String outcome) {
    this.outcome = outcome;
  }

  public void setOutputs(String outputs) {
    this.outputs = outputs;
  }

  public void setOutputUser(String outputUser) {
    this.outputUser = outputUser;
  }

  public void setPartners(String partners) {
    this.partners = partners;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}