package org.cgiar.ccafs.ap.data.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class CasesStudy {

  private int id;
  private String title;
  private String author;
  private Date date;
  private File photo;
  private String photoContentType;
  private String photoFileName;
  private String objectives;
  private String description;
  private String results;
  private String partners;
  private String links;
  private String keywords;
  private List<Country> countries;

  private Logframe logframe;


  private Leader leader;

  public CasesStudy() {

  }

  public CasesStudy(int id) {
    this.id = id;
  }


  public String getAuthor() {
    return author;
  }

  public List<Country> getCountries() {
    return countries;
  }

  public List<String> getCountriesIds() {
    ArrayList<String> ids = new ArrayList<>();
    for (int c = 0; c < getCountries().size(); c++) {
      ids.add(getCountries().get(c).getId() + "");
    }
    return ids;
  }

  public Date getDate() {
    return date;
  }

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public String getKeywords() {
    return keywords;
  }

  public Leader getLeader() {
    return leader;
  }

  public String getLinks() {
    return links;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getObjectives() {
    return objectives;
  }

  public String getPartners() {
    return partners;
  }

  public File getPhoto() {
    return photo;
  }

  public String getPhotoContentType() {
    return photoContentType;
  }


  public String getPhotoFileName() {
    return photoFileName;
  }


  public String getResults() {
    return results;
  }

  public String getTitle() {
    return title;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public void setCountries(List<Country> countries) {
    this.countries = countries;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setKeywords(String keywords) {
    this.keywords = keywords;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLinks(String links) {
    this.links = links;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }


  public void setObjectives(String objectives) {
    this.objectives = objectives;
  }


  public void setPartners(String partners) {
    this.partners = partners;
  }


  public void setPhoto(File photo) {
    this.photo = photo;
  }


  public void setPhotoContentType(String photoContentType) {
    this.photoContentType = photoContentType;
  }


  public void setPhotoFileName(String photoFileName) {
    this.photoFileName = photoFileName;
  }

  public void setResults(String results) {
    this.results = results;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
