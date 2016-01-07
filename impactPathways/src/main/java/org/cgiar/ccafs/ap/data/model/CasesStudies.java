package org.cgiar.ccafs.ap.data.model;
// Generated Jan 5, 2016 2:07:57 PM by Hibernate Tools 3.5.0.Final


import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CasesStudies generated by hbm2java
 */
public class CasesStudies implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 6386030099091356371L;
  private Integer id;
  private int projectId;
  private String comment;
  private String title;
  private String outcomeStatement;
  private String researchOutputs;
  private String researchPatern;
  private String activities;
  private String nonResearchPartneres;
  private String outputUsers;
  private String evidenceOutcome;
  private String referencesCase;
  private String outputUsed;
  private String researchPartners;
  private String explainIndicatorRelation;
  private Integer year;
  private String file;
  private boolean isActive;
  private Date activeSince;
  private Long createdBy;
  private Long modifiedBy;
  private String modificationJustification;
  private File myFile;
  private List<String> caseStudyIndicatorsIds;
  private List<IPIndicator> caseStudyIndicators;


  private String myFileFileName;


  private Set<CaseStudieIndicators> caseStudieIndicatorses = new HashSet<CaseStudieIndicators>(0);

  public CasesStudies() {
  }


  public CasesStudies(int projectId, boolean isActive) {
    this.projectId = projectId;
    this.isActive = isActive;
  }

  public CasesStudies(int projectId, String comment, String title, String outcomeStatement, String researchOutputs,
    String researchPatern, String activities, String nonResearchPartneres, String outputUsers, String evidenceOutcome,
    String referencesCase, String outputUsed, String researchPartners, String explainIndicatorRelation, Integer year,
    String file, boolean isActive, Date activeSince, Long createdBy, Long modifiedBy, String modificationJustification,
    Set<CaseStudieIndicators> caseStudieIndicatorses) {
    this.projectId = projectId;
    this.comment = comment;
    this.title = title;
    this.outcomeStatement = outcomeStatement;
    this.researchOutputs = researchOutputs;
    this.researchPatern = researchPatern;
    this.activities = activities;
    this.nonResearchPartneres = nonResearchPartneres;
    this.outputUsers = outputUsers;
    this.evidenceOutcome = evidenceOutcome;
    this.referencesCase = referencesCase;
    this.outputUsed = outputUsed;
    this.researchPartners = researchPartners;
    this.explainIndicatorRelation = explainIndicatorRelation;
    this.year = year;
    this.file = file;
    this.isActive = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
    this.caseStudieIndicatorses = caseStudieIndicatorses;
  }


  public Date getActiveSince() {
    return this.activeSince;
  }


  public String getActivities() {
    return this.activities;
  }


  public Set<CaseStudieIndicators> getCaseStudieIndicatorses() {
    return this.caseStudieIndicatorses;
  }

  public List<IPIndicator> getCaseStudyIndicators() {
    return caseStudyIndicators;
  }

  public List<String> getCaseStudyIndicatorsIds() {
    return caseStudyIndicatorsIds;
  }

  public String getComment() {
    return this.comment;
  }


  public Long getCreatedBy() {
    return this.createdBy;
  }

  public String getEvidenceOutcome() {
    return this.evidenceOutcome;
  }

  public String getExplainIndicatorRelation() {
    return this.explainIndicatorRelation;
  }

  public String getFile() {
    return this.file;
  }

  public Integer getId() {
    return this.id;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public Long getModifiedBy() {
    return this.modifiedBy;
  }

  public File getMyFile() {
    return myFile;
  }

  public String getMyFileFileName() {
    return myFileFileName;
  }

  public String getNonResearchPartneres() {
    return this.nonResearchPartneres;
  }

  public String getOutcomeStatement() {
    return this.outcomeStatement;
  }

  public String getOutputUsed() {
    return this.outputUsed;
  }

  public String getOutputUsers() {
    return this.outputUsers;
  }

  public int getProjectId() {
    return this.projectId;
  }

  public String getReferencesCase() {
    return this.referencesCase;
  }

  public String getResearchOutputs() {
    return this.researchOutputs;
  }

  public String getResearchPartners() {
    return this.researchPartners;
  }

  public String getResearchPatern() {
    return this.researchPatern;
  }

  public String getTitle() {
    return this.title;
  }

  public Integer getYear() {
    return this.year;
  }

  public boolean isIsActive() {
    return this.isActive;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setActivities(String activities) {
    this.activities = activities;
  }

  public void setCaseStudieIndicatorses(Set<CaseStudieIndicators> caseStudieIndicatorses) {
    this.caseStudieIndicatorses = caseStudieIndicatorses;
  }

  public void setCaseStudyIndicators(List<IPIndicator> caseStudyIndicators) {
    this.caseStudyIndicators = caseStudyIndicators;
  }

  public void setCaseStudyIndicatorsIds(List<String> caseStudyIndicatorsIds) {
    this.caseStudyIndicatorsIds = caseStudyIndicatorsIds;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  public void setEvidenceOutcome(String evidenceOutcome) {
    this.evidenceOutcome = evidenceOutcome;
  }

  public void setExplainIndicatorRelation(String explainIndicatorRelation) {
    this.explainIndicatorRelation = explainIndicatorRelation;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(Long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setMyFile(File myFile) {
    this.myFile = myFile;
  }

  public void setMyFileFileName(String myFileFileName) {
    this.myFileFileName = myFileFileName;
  }

  public void setNonResearchPartneres(String nonResearchPartneres) {
    this.nonResearchPartneres = nonResearchPartneres;
  }

  public void setOutcomeStatement(String outcomeStatement) {
    this.outcomeStatement = outcomeStatement;
  }

  public void setOutputUsed(String outputUsed) {
    this.outputUsed = outputUsed;
  }

  public void setOutputUsers(String outputUsers) {
    this.outputUsers = outputUsers;
  }

  public void setProjectId(int projectId) {
    this.projectId = projectId;
  }

  public void setReferencesCase(String referencesCase) {
    this.referencesCase = referencesCase;
  }

  public void setResearchOutputs(String researchOutputs) {
    this.researchOutputs = researchOutputs;
  }

  public void setResearchPartners(String researchPartners) {
    this.researchPartners = researchPartners;
  }

  public void setResearchPatern(String researchPatern) {
    this.researchPatern = researchPatern;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setYear(Integer year) {
    this.year = year;
  }


}

