package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Submission {

  private int id;
  private Logframe logframe;
  private Leader leader;
  private String section;

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getSection() {
    return section;
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

  public void setSection(String section) {
    this.section = section;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}