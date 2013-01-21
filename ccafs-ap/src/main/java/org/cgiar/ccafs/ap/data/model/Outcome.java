package org.cgiar.ccafs.ap.data.model;


public class Outcome {

  private int id;
  private String outcome;
  private String outputs;
  private String partners;
  private String outputUser;
  private String howUsed;
  private String evidence;
  private Logframe logframe;
  private Leader leader;

  public Outcome() {
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

}
