package org.cgiar.ccafs.ap.data.model;


public class Publication {

  private int id;
  private PublicationType type;
  private String identifier;
  private String citation;
  private String fileUrl;
  private Logframe logframe;
  private Leader leader;
  private Theme[] relatedThemes;
  private OpenAccess access;

  public Publication() {
  }

  public OpenAccess getAccess() {
    return access;
  }

  public String getCitation() {
    return citation;
  }

  public String getFileUrl() {
    return fileUrl;
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

  public Theme[] getRelatedThemes() {
    return relatedThemes;
  }

  public PublicationType getType() {
    return type;
  }

  public void setAccess(OpenAccess access) {
    this.access = access;
  }

  public void setCitation(String citation) {
    this.citation = citation;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
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

  public void setRelatedThemes(Theme[] relatedThemes) {
    this.relatedThemes = relatedThemes;
  }

  public void setType(PublicationType type) {
    this.type = type;
  }
}