package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class OtherSite {

  private int id;
  private double latitude;
  private double longitude;
  private String details;
  private Country country;

  public Country getCountry() {
    return country;
  }

  public String getDetails() {
    return details;
  }

  public int getId() {
    return id;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
