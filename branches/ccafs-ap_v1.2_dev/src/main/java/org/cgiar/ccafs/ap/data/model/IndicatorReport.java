package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IndicatorReport {

  private int id;
  private double target;
  private double actual;
  private String description;
  private String supportLinks;
  private String deviation;
  private Leader leader;
  private Indicator indicator;
  private Logframe logframe;

  public double getActual() {
    return actual;
  }

  public String getDescription() {
    return description;
  }

  public String getDeviation() {
    return deviation;
  }

  public int getId() {
    return id;
  }

  public Indicator getIndicator() {
    return indicator;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getSupportLinks() {
    return supportLinks;
  }

  public double getTarget() {
    return target;
  }

  public void setActual(double actual) {
    this.actual = actual;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDeviation(String deviation) {
    this.deviation = deviation;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicator(Indicator indicator) {
    this.indicator = indicator;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setSupportLinks(String supportLinks) {
    this.supportLinks = supportLinks;
  }

  public void setTarget(double target) {
    this.target = target;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}