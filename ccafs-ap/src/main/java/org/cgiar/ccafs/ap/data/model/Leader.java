package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Leader {

  private int code;
  private String name;
  private LeaderType leaderType;

  public Leader() {
  }

  public Leader(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public Leader(int code, String name, LeaderType leaderType) {
    this.code = code;
    this.name = name;
    this.leaderType = leaderType;
  }

  public int getCode() {
    return code;
  }

  public LeaderType getLeaderType() {
    return leaderType;
  }

  public String getName() {
    return name;
  }

  public void setCode(int code) {
    this.code = code;
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
