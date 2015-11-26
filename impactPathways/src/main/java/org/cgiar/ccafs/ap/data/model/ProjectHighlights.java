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


package org.cgiar.ccafs.ap.data.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProjectHighlights


{

  private int id;
  private int project_id;


  private String title;


  private String author;

  private Date startDate;
  private Date endDate;
  private String image;
  private String imageContentType;
  private String imageFileName;
  private String objectives;
  private String description;
  private String results;
  private String partners;
  private String links;
  private String country;

  private String keywords;


  private String subject;

  private String contributor;
  private String publisher;
  private String relation;
  private String coverage;
  private String rights;
  private ProjectHighlightsType type;
  private List<Country> countries;
  private String leader;
  private boolean isGlobal;
  private List<ProjectHighlightsType> types;

  public String getAuthor() {
    return author;
  }

  public String getContributor() {
    return contributor;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<String> getCountriesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < this.getCountries().size(); c++) {
      ids.add(this.getCountries().get(c).getId() + "");
    }
    return ids;
  }

  public String getCountry() {
    return country;
  }

  public String getCoverage() {
    return coverage;
  }


  public String getDescription() {
    return description;
  }


  public Date getEndDate() {
    return endDate;
  }

  public int getId() {
    return id;
  }

  public String getImage() {
    return image;
  }

  public String getImageContentType() {
    return imageContentType;
  }


  public String getImageFileName() {
    return imageFileName;
  }

  public String getKeywords() {
    return keywords;
  }

  public String getLeader() {
    return leader;
  }

  public String getLinks() {
    return links;
  }

  public String getObjectives() {
    return objectives;
  }

  public String getPartners() {
    return partners;
  }

  public int getProject_id() {
    return project_id;
  }

  public String getPublisher() {
    return publisher;
  }

  public String getRelation() {
    return relation;
  }

  public String getResults() {
    return results;
  }

  public String getRights() {
    return rights;
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getSubject() {
    return subject;
  }

  public String getTitle() {
    return title;
  }

  public ProjectHighlightsType getType() {
    return type;
  }


  public List<ProjectHighlightsType> getTypes() {
    return types;
  }


  public List<String> getTypesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < this.getTypes().size(); c++) {
      ids.add(this.getTypes().get(c).getId() + "");
    }
    return ids;
  }

  public boolean isGlobal() {
    return isGlobal;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setContributor(String contributor) {
    this.contributor = contributor;
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setCoverage(String coverage) {
    this.coverage = coverage;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setGlobal(boolean isGlobal) {
    this.isGlobal = isGlobal;
  }

  public void setId(int id) {
    this.id = id;
  }


  public void setImage(String image) {
    this.image = image;
  }

  public void setImageContentType(String imageContentType) {
    this.imageContentType = imageContentType;
  }

  public void setImageFileName(String imageFileName) {
    this.imageFileName = imageFileName;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setLeader(String leader) {
    this.leader = leader;
  }

  public void setLinks(String links) {
    this.links = links;
  }

  public void setObjectives(String objectives) {
    this.objectives = objectives;
  }

  public void setPartners(String partners) {
    this.partners = partners;
  }

  public void setProject_id(int project_id) {
    this.project_id = project_id;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public void setRelation(String relation) {
    this.relation = relation;
  }

  public void setResults(String results) {
    this.results = results;
  }

  public void setRights(String rights) {
    this.rights = rights;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }


  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(ProjectHighlightsType type) {
    this.type = type;
  }

  public void setTypes(List<ProjectHighlightsType> types) {
    this.types = types;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
