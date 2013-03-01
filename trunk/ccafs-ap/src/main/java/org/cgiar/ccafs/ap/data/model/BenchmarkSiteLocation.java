package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class BenchmarkSiteLocation {

  private BenchmarkSite benchmarkSite;
  private String details;

  public BenchmarkSite getBenchmarkSite() {
    return benchmarkSite;
  }

  public String getDetails() {
    return details;
  }

  public void setBenchmarkSite(BenchmarkSite benchmarkSite) {
    this.benchmarkSite = benchmarkSite;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
