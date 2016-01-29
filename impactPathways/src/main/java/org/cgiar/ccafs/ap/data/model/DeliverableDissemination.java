package org.cgiar.ccafs.ap.data.model;
// Generated Jan 14, 2016 3:04:11 PM by Hibernate Tools 4.3.1.Final


import java.util.Date;

/**
 * DeliverableDissemination generated by hbm2java
 */
public class DeliverableDissemination implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 5059497581082773630L;
  private Integer id;
  private int deliverableId;
  private String type;

  private Boolean isOpenAccess;


  private Boolean intellectualProperty;

  private Boolean limitedExclusivity;
  private Boolean restrictedUseAgreement;
  private Date restrictedAccessUntil;
  private String restrictedAccessUntilText;

  private Boolean effectiveDateRestriction;


  private Date restrictedEmbargoed;


  private String restrictedEmbargoedText;


  private Boolean alreadyDisseminated;

  private String disseminationChannel;
  private String disseminationUrl;
  private String disseminationChannelName;


  public DeliverableDissemination() {
  }

  public DeliverableDissemination(int deliverableId) {
    this.deliverableId = deliverableId;
  }

  public DeliverableDissemination(int deliverableId, Boolean isOpenAccess, Boolean intellectualProperty,
    Boolean limitedExclusivity, Boolean restrictedUseAgreement, Date restrictedAccessUntil,
    Boolean effectiveDateRestriction, Date restrictedEmbargoed, Boolean alreadyDisseminated,
    String disseminationChannel, String disseminationUrl, String disseminationChannelName, String descriptionMetadata,
    String authorsMetadata, String identifierMetadata, String publishierMetadata, String relationMetadata,
    String contributorMetadata, String subjectMetadata, String sourceMetadata, String publicationMetada,
    String languageMetadata, String coverageMetadata, String formatMetadata, String rigthsMetadata) {
    this.deliverableId = deliverableId;
    this.isOpenAccess = isOpenAccess;
    this.intellectualProperty = intellectualProperty;
    this.limitedExclusivity = limitedExclusivity;
    this.restrictedUseAgreement = restrictedUseAgreement;
    this.restrictedAccessUntil = restrictedAccessUntil;
    this.effectiveDateRestriction = effectiveDateRestriction;
    this.restrictedEmbargoed = restrictedEmbargoed;
    this.alreadyDisseminated = alreadyDisseminated;
    this.disseminationChannel = disseminationChannel;
    this.disseminationUrl = disseminationUrl;
    this.disseminationChannelName = disseminationChannelName;

  }

  public Boolean getAlreadyDisseminated() {
    return this.alreadyDisseminated;
  }


  public int getDeliverableId() {
    return this.deliverableId;
  }


  public String getDisseminationChannel() {
    return this.disseminationChannel;
  }

  public String getDisseminationChannelName() {
    return this.disseminationChannelName;
  }

  public String getDisseminationUrl() {
    return this.disseminationUrl;
  }

  public Boolean getEffectiveDateRestriction() {
    return this.effectiveDateRestriction;
  }


  public Integer getId() {
    return this.id;
  }


  public Boolean getIntellectualProperty() {
    return this.intellectualProperty;
  }

  public Boolean getIsOpenAccess() {
    return this.isOpenAccess;
  }


  public Boolean getLimitedExclusivity() {
    return this.limitedExclusivity;
  }


  public Date getRestrictedAccessUntil() {
    return this.restrictedAccessUntil;
  }

  public String getRestrictedAccessUntilText() {
    return restrictedAccessUntilText;
  }

  public Date getRestrictedEmbargoed() {
    return this.restrictedEmbargoed;
  }

  public String getRestrictedEmbargoedText() {
    return restrictedEmbargoedText;
  }

  public Boolean getRestrictedUseAgreement() {
    return this.restrictedUseAgreement;
  }


  public String getType() {
    return type;
  }

  public void setAlreadyDisseminated(Boolean alreadyDisseminated) {
    this.alreadyDisseminated = alreadyDisseminated;
  }


  public void setDeliverableId(int deliverableId) {
    this.deliverableId = deliverableId;
  }


  public void setDisseminationChannel(String disseminationChannel) {
    this.disseminationChannel = disseminationChannel;
  }

  public void setDisseminationChannelName(String disseminationChannelName) {
    this.disseminationChannelName = disseminationChannelName;
  }

  public void setDisseminationUrl(String disseminationUrl) {
    this.disseminationUrl = disseminationUrl;
  }

  public void setEffectiveDateRestriction(Boolean effectiveDateRestriction) {
    this.effectiveDateRestriction = effectiveDateRestriction;
  }


  public void setId(Integer id) {
    this.id = id;
  }


  public void setIntellectualProperty(Boolean intellectualProperty) {
    this.intellectualProperty = intellectualProperty;
  }

  public void setIsOpenAccess(Boolean isOpenAccess) {
    this.isOpenAccess = isOpenAccess;
  }


  public void setLimitedExclusivity(Boolean limitedExclusivity) {
    this.limitedExclusivity = limitedExclusivity;
  }


  public void setRestrictedAccessUntil(Date restrictedAccessUntil) {
    this.restrictedAccessUntil = restrictedAccessUntil;
  }

  public void setRestrictedAccessUntilText(String restrictedAccessUntilText) {
    this.restrictedAccessUntilText = restrictedAccessUntilText;
  }

  public void setRestrictedEmbargoed(Date restrictedEmbargoed) {
    this.restrictedEmbargoed = restrictedEmbargoed;
  }

  public void setRestrictedEmbargoedText(String restrictedEmbargoedText) {
    this.restrictedEmbargoedText = restrictedEmbargoedText;
  }

  public void setRestrictedUseAgreement(Boolean restrictedUseAgreement) {
    this.restrictedUseAgreement = restrictedUseAgreement;
  }


  public void setType(String type) {
    this.type = type;
  }


}
