package org.cgiar.ccafs.ap.data.model;


public class Publication {

  private int id;
  private PublicationType type;
  private String identifier;
  private String citation;
  private Logframe logframe;
  private Leader leader;

  public Publication() {
  }

  public String getCitation() {
    return citation;
  }

  public int getId() {
    return id;
  }

  public String getIdentifier() {
    return identifier;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public PublicationType getType() {
    return type;
  }

  public void setCitation(String citation) {
    this.citation = citation;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setType(PublicationType type) {
    this.type = type;
  }

}
