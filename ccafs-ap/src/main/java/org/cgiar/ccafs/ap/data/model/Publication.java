/*
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 */

package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;


public class Publication {

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

  public PublicationType getType() {
    return type;
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