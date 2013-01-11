package org.cgiar.ccafs.ap.data.model;


public class OutputSummary {

  private int id;
  private String description;


  public OutputSummary() {
  }

  public OutputSummary(int id, String description) {
    super();
    this.id = id;
    this.description = description;
  }


  public String getDescription() {
    return description;
  }


  public int getId() {
    return id;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public void setId(int id) {
    this.id = id;
  }
}
