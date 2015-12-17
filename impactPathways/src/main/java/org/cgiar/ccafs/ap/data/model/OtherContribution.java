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
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * This class represents Other Contributions for a specific activity.
 * 
 * @author Javier Andrés Gallego B.
 * @author Hernán David Carvajal B.
 */
public class OtherContribution {

  private String additionalContribution;
  private String contribution;
  private List<CRPContribution> crpContributions;
  private int id;


  public OtherContribution() {
    crpContributions = new ArrayList<CRPContribution>();
  }


  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OtherContribution) {
      OtherContribution n = (OtherContribution) obj;
      return n.id == this.id;
    }
    return false;
  }


  public String getAdditionalContribution() {
    return additionalContribution;
  }


  public String getContribution() {
    return contribution;
  }


  public List<CRPContribution> getCrpContributions() {
    return crpContributions;
  }


  public int getId() {
    return id;
  }


  @Override
  public int hashCode() {
    return this.id;
  }


  public void setAdditionalContribution(String additionalContribution) {
    this.additionalContribution = additionalContribution;
  }


  public void setContribution(String contribution) {
    this.contribution = contribution;
  }


  public void setCrpCollaborationNature(List<CRPContribution> crpContributions) {
    this.crpContributions = crpContributions;
  }


  public void setCrpContributions(List<CRPContribution> crpContributions) {
    this.crpContributions = crpContributions;
  }


  public void setId(int id) {
    this.id = id;
  }


  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
