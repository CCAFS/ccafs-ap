package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class OutcomeIndicatorReport {

  private int id;
  private String achievements;
  private String evidence;
  private OutcomeIndicator outcomeIndicator;
  private Leader leader;

  public String getAchievements() {
    return achievements;
  }

  public String getEvidence() {
    return evidence;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public OutcomeIndicator getOutcomeIndicator() {
    return outcomeIndicator;
  }

  public void setAchievements(String achievements) {
    this.achievements = achievements;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setOutcomeIndicator(OutcomeIndicator outcomeIndicator) {
    this.outcomeIndicator = outcomeIndicator;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}