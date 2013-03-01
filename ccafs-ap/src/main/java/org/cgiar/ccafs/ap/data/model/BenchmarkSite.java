package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class BenchmarkSite {

  private String id;
  private String name;
  private double longitud;
  private double latitude;
  private Country country;

  public Country getCountry() {
    return country;
  }

  public String getId() {
    return id;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitud() {
    return longitud;
  }

  public String getName() {
    return name;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public void setLongitud(double longitud) {
    this.longitud = longitud;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}