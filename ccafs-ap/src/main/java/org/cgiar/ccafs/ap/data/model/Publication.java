package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;


public class Publication extends Deliverable {

  private int id;
  private PublicationType type;
  private String identifier;
  private String citation;
  private String fileUrl;
  private Logframe logframe;
  private Leader leader;
  private PublicationTheme[] relatedThemes;
  private OpenAccess access;
  private boolean ccafsAcknowledge;
  private boolean isiPublication;
  private boolean narsCoauthor;
  private boolean earthSystemCoauthor;

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

  public PublicationType getPublicationType() {
    return type;
  }

  public PublicationTheme[] getRelatedThemes() {
    return relatedThemes;
  }

  public ArrayList<String> getRelatedThemesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getRelatedThemes().length; c++) {
      ids.add(getRelatedThemes()[c].getId() + "");
    }
    return ids;
  }

  public boolean isCcafsAcknowledge() {
    return ccafsAcknowledge;
  }

  public boolean isEarthSystemCoauthor() {
    return earthSystemCoauthor;
  }

  public boolean isIsiPublication() {
    return isiPublication;
  }

  public boolean isNarsCoauthor() {
    return narsCoauthor;
  }

  public void setAccess(OpenAccess access) {
    this.access = access;
  }

  public void setCcafsAcknowledge(boolean ccafsAcknowledge) {
    this.ccafsAcknowledge = ccafsAcknowledge;
  }

  public void setCitation(String citation) {
    this.citation = citation;
  }

  public void setEarthSystemCoauthor(boolean earthSystemCoauthor) {
    this.earthSystemCoauthor = earthSystemCoauthor;
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

  public void setIsiPublication(boolean isiPublication) {
    this.isiPublication = isiPublication;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setNarsCoauthor(boolean narsCoauthor) {
    this.narsCoauthor = narsCoauthor;
  }

  public void setRelatedThemes(PublicationTheme[] relatedThemes) {
    this.relatedThemes = relatedThemes;
  }

  public void setType(PublicationType type) {
    this.type = type;
  }
}