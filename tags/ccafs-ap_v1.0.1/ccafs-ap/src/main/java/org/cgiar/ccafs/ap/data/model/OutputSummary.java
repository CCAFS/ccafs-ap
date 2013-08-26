package org.cgiar.ccafs.ap.data.model;


public class OutputSummary {

  private int id;
  private String description;
  private Output output;
  private Leader leader;


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

  public Leader getLeader() {
    return leader;
  }

  public Output getOutput() {
    return output;
  }


  public void setDescription(String description) {
    this.description = description;
  }


  public void setId(int id) {
    this.id = id;
  }


  public void setLeader(Leader leader) {
    this.leader = leader;
  }


  public void setOutput(Output output) {
    this.output = output;
  }
}
