/*****************************************************************
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
 *****************************************************************/


package org.cgiar.ccafs.ap.util;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author Christian David Garcia Oviedo
 */
public class Metadata {

  private String title;

  private String description;


  private String authors;

  private String identifier;
  private String publisher;
  private String relation;
  private String contributor;
  private String subject;
  private String source;
  private String publication;
  private String language;
  private String coverage;
  private String format;
  private String rigths;

  public String getAuthors() {
    return authors;
  }

  public String getContributor() {
    return contributor;
  }

  public String getCoverage() {
    return coverage;
  }

  public String getDescription() {
    return description;
  }

  public String getFormat() {
    return format;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getLanguage() {
    return language;
  }

  public String getPublication() {
    return publication;
  }

  public String getPublisher() {
    return publisher;
  }

  public String getRelation() {
    return relation;
  }

  public String getRigths() {
    return rigths;
  }

  public String getSource() {
    return source;
  }

  public String getSubject() {
    return subject;
  }

  public String getTitle() {
    return title;
  }

  public void setAuthors(String authors) {
    this.authors = authors;
  }

  public void setContributor(String contributor) {
    this.contributor = contributor;
  }

  public void setCoverage(String coverage) {
    this.coverage = coverage;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setFormat(String format) {
    this.format = format;
  }

  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public void setPublication(String publication) {
    this.publication = publication;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setRelation(String relation) {
    this.relation = relation;
  }

  public void setRigths(String rigths) {
    this.rigths = rigths;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


}
