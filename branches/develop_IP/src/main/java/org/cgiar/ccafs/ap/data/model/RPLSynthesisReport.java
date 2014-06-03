package org.cgiar.ccafs.ap.data.model;


public class RPLSynthesisReport {

  private int id;
  private String ccafsSites;
  private String crossCenter;
  private String regional;
  private String decisionSupport;
  private Leader leader;
  private Logframe logframe;

  public RPLSynthesisReport() {
  }

  public String getCcafsSites() {
    return ccafsSites;
  }

  public String getCrossCenter() {
    return crossCenter;
  }

  public String getDecisionSupport() {
    return decisionSupport;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getRegional() {
    return regional;
  }

  public void setCcafsSites(String ccafsSites) {
    this.ccafsSites = ccafsSites;
  }

  public void setCrossCenter(String crossCenter) {
    this.crossCenter = crossCenter;
  }

  public void setDecisionSupport(String decisionSupport) {
    this.decisionSupport = decisionSupport;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setRegional(String regional) {
    this.regional = regional;
  }
}
