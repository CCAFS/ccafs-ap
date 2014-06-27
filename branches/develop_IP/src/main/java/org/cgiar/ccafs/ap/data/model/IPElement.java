package org.cgiar.ccafs.ap.data.model;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class IPElement {

  private int id;
  private String description;
  private IPElementType type;
  private IPProgram program;
  private List<IPIndicator> indicators;
  private List<IPElement> parents;

  public String getDescription() {
    return description;
  }

  public int getId() {
    return id;
  }

  public List<IPIndicator> getIndicators() {
    return indicators;
  }

  public List<IPElement> getParents() {
    return parents;
  }

  public IPProgram getProgram() {
    return program;
  }

  public IPElementType getType() {
    return type;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicators(List<IPIndicator> indicator) {
    this.indicators = indicator;
  }

  public void setParents(List<IPElement> parents) {
    this.parents = parents;
  }

  public void setProgram(IPProgram program) {
    this.program = program;
  }

  public void setType(IPElementType type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}