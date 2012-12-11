package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Partner {

  private int id;
  private String name;
  private String acronym;
  private Country country;
  private String city;
  private PartnerType type;
  private ArrayList<ActivityPartner> contactPoints;


  public Partner() {
  }


  public String getAcronym() {
    return acronym;
  }


  public String getCity() {
    return city;
  }


  public ArrayList<ActivityPartner> getContactPoints() {
    return contactPoints;
  }


  public Country getCountry() {
    return country;
  }


  public int getId() {
    return id;
  }


  public String getName() {
    return name;
  }


  public PartnerType getType() {
    return type;
  }


  public void setAcronym(String acronym) {
    this.acronym = acronym;
  }


  public void setCity(String city) {
    this.city = city;
  }


  public void setContactPoints(ArrayList<ActivityPartner> contactPoints) {
    this.contactPoints = contactPoints;
  }


  public void setCountry(Country country) {
    this.country = country;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setName(String name) {
    this.name = name;
  }

  public void setType(PartnerType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
