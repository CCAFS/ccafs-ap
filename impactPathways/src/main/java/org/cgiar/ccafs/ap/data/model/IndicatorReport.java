package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class IndicatorReport {

  private int id;
  private int year;
  private String target;
  private String nextYearTarget;
  private String actual;
  private String supportLinks;
  private String deviation;
  private LiaisonInstitution liaisonInstitution;
  private Indicator indicator;

  public String getActual() {
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


  public LiaisonInstitution getLiaisonInstitution() {
    return liaisonInstitution;
  }

  public String getNextYearTarget() {
    return nextYearTarget;
  }

  public String getSupportLinks() {
    return supportLinks;
  }

  public String getTarget() {
    return target;
  }

  public int getYear() {
    return year;
  }

  public void setActual(String actual) {
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


  public void setLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
  }

  public void setNextYearTarget(String nextYearTarget) {
    this.nextYearTarget = nextYearTarget;
  }

  public void setSupportLinks(String supportLinks) {
    this.supportLinks = supportLinks;
  }

  public void setTarget(String target) {
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