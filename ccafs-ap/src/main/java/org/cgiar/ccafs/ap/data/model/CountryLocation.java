package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class CountryLocation {

  private Country country;
  private String details;

  public Country getCountry() {
    return country;
  }

  public String getDetails() {
    return details;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
