package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IndicatorReport {

  private int id;
  private int year;
  private double target;
  private double nextYearTarget;
  private double actual;
  private String supportLinks;
  private String deviation;
  private Leader leader;
  private Indicator indicator;

  public double getActual() {
    return actual;
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

  public double getNextYearTarget() {
    return nextYearTarget;
  }

  public String getSupportLinks() {
    return supportLinks;
  }

  public double getTarget() {
    return target;
  }

  public int getYear() {
    return year;
  }

  public void setActual(double actual) {
    this.actual = actual;
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

  public void setNextYearTarget(double nextYearTarget) {
    this.nextYearTarget = nextYearTarget;
  }

  public void setSupportLinks(String supportLinks) {
    this.supportLinks = supportLinks;
  }

  public void setTarget(double target) {
    this.target = target;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}