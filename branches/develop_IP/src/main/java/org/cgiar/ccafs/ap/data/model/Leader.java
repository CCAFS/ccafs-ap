package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Leader {

  private int id;
  private String name;
  private String acronym;
  private LeaderType leaderType;
  private Theme theme;
  private Region region;

  public Leader() {
  }

  public Leader(int id) {
    this.id = id;
  }

  public Leader(int id, String name, LeaderType leaderType) {
    this.id = id;
    this.name = name;
    this.leaderType = leaderType;
  }

  public Leader(int id, String acronym, String name) {
    this.id = id;
    this.name = name;
    this.acronym = acronym;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Leader) {
      Leader leader = (Leader) obj;
      return leader.getId() == this.getId();
    }
    return false;
  }

  public String getAcronym() {
    return acronym;
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

  public Region getRegion() {
    return region;
  }

  public Theme getTheme() {
    return theme;
  }

  public void setAcronym(String acronym) {
    this.acronym = acronym;
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

  public void setRegion(Region region) {
    this.region = region;
  }

  public void setTheme(Theme theme) {
    this.theme = theme;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}