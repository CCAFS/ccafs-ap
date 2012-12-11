package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Country {

  private String id;
  private String name;
  private Region region;

  public Country(String id, String name) {
    super();
    this.id = id;
    this.name = name;
  }


  public String getId() {
    return id;
  }


  public String getName() {
    return name;
  }


  public Region getRegion() {
    return region;
  }


  public void setId(String id) {
    this.id = id;
  }


  public void setName(String name) {
    this.name = name;
  }


  public void setRegion(Region region) {
    this.region = region;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
