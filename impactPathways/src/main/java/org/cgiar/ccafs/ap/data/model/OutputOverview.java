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

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class OutputOverview {

  private int id;
  private int year;
  private IPElement output;
  private String expectedAnnualContribution;
  private String socialInclusionDimmension;
  private String briefSummary;
  private String summaryGender;


  public OutputOverview() {
  }


  public OutputOverview(int id) {
    this.id = id;
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OutputOverview) {
      OutputOverview v = (OutputOverview) obj;
      return v.id == this.id;
    }
    return false;
  }


  public String getBriefSummary() {
    return briefSummary;
  }

  public String getExpectedAnnualContribution() {
    return expectedAnnualContribution;
  }

  public int getId() {
    return id;
  }

  public IPElement getOutput() {
    return output;
  }

  public String getSocialInclusionDimmension() {
    return socialInclusionDimmension;
  }

  public String getSummaryGender() {
    return summaryGender;
  }

  public int getYear() {
    return year;
  }

  @Override
  public int hashCode() {
    return this.id;
  }

  public void setBriefSummary(String briefSummary) {
    this.briefSummary = briefSummary;
  }

  public void setExpectedAnnualContribution(String expectedAnnualContribution) {
    this.expectedAnnualContribution = expectedAnnualContribution;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setOutput(IPElement output) {
    this.output = output;
  }

  public void setSocialInclusionDimmension(String socialInclusionDimmension) {
    this.socialInclusionDimmension = socialInclusionDimmension;
  }

  public void setSummaryGender(String summaryGender) {
    this.summaryGender = summaryGender;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}