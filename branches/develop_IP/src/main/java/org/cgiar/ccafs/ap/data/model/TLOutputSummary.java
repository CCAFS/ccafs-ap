package org.cgiar.ccafs.ap.data.model;


public class TLOutputSummary {

  private int id;
  private Output output;
  private Leader leader;
  private String description;

  public TLOutputSummary() {
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
