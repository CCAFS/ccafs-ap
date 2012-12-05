package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Leader {

  private int id;
  private String name;
  private LeaderType leaderType;

  public Leader() {
  }

  public Leader(int id, String name) {
    this.id = id;
    this.name = name;
  }

  public Leader(int id, String name, LeaderType leaderType) {
    this.id = id;
    this.name = name;
    this.leaderType = leaderType;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Leader) {
      Leader leader = (Leader) obj;
      return leader.getId() == this.getId();
    }
    return false;
  }

  public int getId() {
    return id;
  }

  public LeaderType getLeaderType() {
    return leaderType;
  }

  public String getName() {
    return name;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeaderType(LeaderType leaderType) {
    this.leaderType = leaderType;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
