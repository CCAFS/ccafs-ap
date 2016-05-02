package org.cgiar.ccafs.ap.data.model;
// Generated Apr 26, 2016 12:00:16 PM by Hibernate Tools 4.3.1.Final

import java.text.DecimalFormat;
import java.util.Date;

/**
 * ProjectEvaluation generated by hbm2java
 */
public class ProjectEvaluation implements java.io.Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = 117258751413894335L;

  private Long id;

  private long projectId;
  private String typeEvaluation;
  private Long userId;
  private Long programId;
  private boolean submited;
  private int year;
  private double rankingOutputs;
  private double rankingOutcomes;
  private double rankingParternshipComunnication;
  private double rankingResponseTeam;
  private double rankingQuality;
  private String communicationProducts;
  private String projectHighlights;
  private String outcomeCaseStudies;
  private String generalComments;
  private String recommendations;
  private String anyActionRequeried;
  private double totalScore;
  private boolean active;
  private Date activeSince;
  private Date submittedDate;

  private long createdBy;


  private long modifiedBy;


  private String modificationJustification;

  public ProjectEvaluation() {
  }

  public ProjectEvaluation(long projectId, String typeEvaluation, Long userId, boolean isSubmited, int year,
    double rankingOutputs, double rankingOutcomes, double rankingParternshipComunnication, double rankingResponseTeam,
    double rankingQuality, String communicationProducts, String projectHighlights, String outcomeCaseStudies,
    String generalComments, String recommendations, String anyActionRequeried, double totalScore, boolean isActive,
    Date activeSince, long createdBy, long modifiedBy, String modificationJustification) {
    this.projectId = projectId;
    this.typeEvaluation = typeEvaluation;
    this.userId = userId;
    this.submited = isSubmited;
    this.year = year;
    this.rankingOutputs = rankingOutputs;
    this.rankingOutcomes = rankingOutcomes;
    this.rankingParternshipComunnication = rankingParternshipComunnication;
    this.rankingResponseTeam = rankingResponseTeam;
    this.rankingQuality = rankingQuality;
    this.communicationProducts = communicationProducts;
    this.projectHighlights = projectHighlights;
    this.outcomeCaseStudies = outcomeCaseStudies;
    this.generalComments = generalComments;
    this.recommendations = recommendations;
    this.anyActionRequeried = anyActionRequeried;
    this.totalScore = totalScore;
    this.active = isActive;
    this.activeSince = activeSince;
    this.createdBy = createdBy;
    this.modifiedBy = modifiedBy;
    this.modificationJustification = modificationJustification;
  }


  /**
   * calculate the weighted average of the questions in the evaluation.
   * 
   * @return the evaluation total score
   */
  public double calculateTotalScore() {
    double totalScore = ((this.rankingOutcomes * EvaluationValueQuestions.RANKING_OUTCOMES.getValue())
      + (this.rankingOutputs * EvaluationValueQuestions.RANKING_OUTPUTS.getValue())
      + (this.rankingParternshipComunnication * EvaluationValueQuestions.RANKING_PARTERNSHIP.getValue())
      + (this.rankingResponseTeam * EvaluationValueQuestions.RANKING_RESPONSE_TEAM.getValue())
      + (this.rankingQuality * EvaluationValueQuestions.RANKING_QUALITY.getValue()));
    DecimalFormat df = new DecimalFormat("#.00");
    return Double.parseDouble(df.format(totalScore));

  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    ProjectEvaluation other = (ProjectEvaluation) obj;
    if (programId == null) {
      if (other.programId != null) {
        return false;
      }
    } else if (!programId.equals(other.programId)) {
      return false;
    }
    if (typeEvaluation == null) {
      if (other.typeEvaluation != null) {
        return false;
      }
    } else if (!typeEvaluation.equals(other.typeEvaluation)) {
      return false;
    }
    return true;
  }

  public Date getActiveSince() {
    return this.activeSince;
  }

  public String getAnyActionRequeried() {
    return this.anyActionRequeried;
  }

  public String getCommunicationProducts() {
    return this.communicationProducts;
  }

  public long getCreatedBy() {
    return this.createdBy;
  }

  public String getGeneralComments() {
    return this.generalComments;
  }

  public Long getId() {
    return this.id;
  }

  public String getModificationJustification() {
    return this.modificationJustification;
  }

  public long getModifiedBy() {
    return this.modifiedBy;
  }

  public String getOutcomeCaseStudies() {
    return this.outcomeCaseStudies;
  }

  public Long getProgramId() {
    return programId;
  }

  public String getProjectHighlights() {
    return this.projectHighlights;
  }

  public long getProjectId() {
    return this.projectId;
  }

  public double getRankingOutcomes() {
    return this.rankingOutcomes;
  }

  public double getRankingOutputs() {
    return this.rankingOutputs;
  }

  public double getRankingParternshipComunnication() {
    return this.rankingParternshipComunnication;
  }

  public double getRankingQuality() {
    return this.rankingQuality;
  }

  public double getRankingResponseTeam() {
    return this.rankingResponseTeam;
  }

  public String getRecommendations() {
    return this.recommendations;
  }

  public String getStatus() {
    if (submited) {
      return "Submitted";
    }
    return "Evaluating";
  }

  public Date getSubmittedDate() {
    return submittedDate;
  }

  public double getTotalScore() {
    return this.totalScore;
  }

  public String getTypeEvaluation() {
    return this.typeEvaluation;
  }

  public Long getUserId() {
    return this.userId;
  }


  public int getYear() {
    return this.year;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((programId == null) ? 0 : programId.hashCode());
    result = prime * result + ((typeEvaluation == null) ? 0 : typeEvaluation.hashCode());
    return result;
  }

  public boolean isActive() {
    return this.active;
  }

  public boolean isSubmited() {
    return this.submited;
  }

  public void setActive(boolean isActive) {
    this.active = isActive;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setAnyActionRequeried(String anyActionRequeried) {
    this.anyActionRequeried = anyActionRequeried;
  }

  public void setCommunicationProducts(String communicationProducts) {
    this.communicationProducts = communicationProducts;
  }

  public void setCreatedBy(long createdBy) {
    this.createdBy = createdBy;
  }

  public void setGeneralComments(String generalComments) {
    this.generalComments = generalComments;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(long modifiedBy) {
    this.modifiedBy = modifiedBy;
  }

  public void setOutcomeCaseStudies(String outcomeCaseStudies) {
    this.outcomeCaseStudies = outcomeCaseStudies;
  }

  public void setProgramId(Long programId) {
    this.programId = programId;
  }

  public void setProjectHighlights(String projectHighlights) {
    this.projectHighlights = projectHighlights;
  }

  public void setProjectId(long projectId) {
    this.projectId = projectId;
  }

  public void setRankingOutcomes(double rankingOutcomes) {
    this.rankingOutcomes = rankingOutcomes;
  }

  public void setRankingOutputs(double rankingOutputs) {
    this.rankingOutputs = rankingOutputs;
  }

  public void setRankingParternshipComunnication(double rankingParternshipComunnication) {
    this.rankingParternshipComunnication = rankingParternshipComunnication;
  }

  public void setRankingQuality(double rankingQuality) {
    this.rankingQuality = rankingQuality;
  }

  public void setRankingResponseTeam(double rankingResponseTeam) {
    this.rankingResponseTeam = rankingResponseTeam;
  }

  public void setRecommendations(String recommendations) {
    this.recommendations = recommendations;
  }

  public void setSubmited(boolean isSubmited) {
    this.submited = isSubmited;
  }

  public void setSubmittedDate(Date submitedDate) {
    this.submittedDate = submitedDate;
  }


  public void setTotalScore(double totalScore) {
    this.totalScore = totalScore;
  }


  public void setTypeEvaluation(String typeEvaluation) {
    this.typeEvaluation = typeEvaluation;
  }


  public void setUserId(Long userId) {
    this.userId = userId;
  }


  public void setYear(int year) {
    this.year = year;
  }


}

