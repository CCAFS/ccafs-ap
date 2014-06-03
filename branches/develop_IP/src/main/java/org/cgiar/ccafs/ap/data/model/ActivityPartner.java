package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class ActivityPartner {

  private int id;
  private String contactName;
  private String contactEmail;
  private Partner partner;
  private PartnerRole role;

  public ActivityPartner() {
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public String getContactName() {
    return contactName;
  }

  public int getId() {
    return id;
  }

  public Partner getPartner() {
    return partner;
  }

  public PartnerRole getRole() {
    return role;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public void setContactName(String contactName) {
    this.contactName = contactName;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setPartner(Partner partner) {
    this.partner = partner;
  }

  public void setRole(PartnerRole role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
