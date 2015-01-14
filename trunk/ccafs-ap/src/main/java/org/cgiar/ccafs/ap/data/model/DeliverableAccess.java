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

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableAccess {

  private boolean dataDictionary;
  private String qualityProcedures;
  private String accessRestrictions;
  private String accessLimits;
  private Date accessLimitStartDate;
  private Date accessLimitEndDate;
  private boolean harvestingProtocols;
  private String harvestingProtocolDetails;

  public Date getAccessLimitEndDate() {
    return accessLimitEndDate;
  }

  public String getAccessLimits() {
    return accessLimits;
  }

  public Date getAccessLimitStartDate() {
    return accessLimitStartDate;
  }

  public String getAccessRestrictions() {
    return accessRestrictions;
  }

  public String getHarvestingProtocolDetails() {
    return harvestingProtocolDetails;
  }

  public String getQualityProcedures() {
    return qualityProcedures;
  }

  public boolean isDataDictionary() {
    return dataDictionary;
  }

  public boolean isHarvestingProtocols() {
    return harvestingProtocols;
  }

  public void setAccessLimitEndDate(Date accessLimitEndDate) {
    this.accessLimitEndDate = accessLimitEndDate;
  }

  public void setAccessLimits(String accessLimits) {
    this.accessLimits = accessLimits;
  }

  public void setAccessLimitStartDate(Date accessLimitStartDate) {
    this.accessLimitStartDate = accessLimitStartDate;
  }

  public void setAccessRestrictions(String accessRestrictions) {
    this.accessRestrictions = accessRestrictions;
  }

  public void setDataDictionary(boolean dataDictionary) {
    this.dataDictionary = dataDictionary;
  }

  public void setHarvestingProtocolDetails(String harvestingProtocolDetails) {
    this.harvestingProtocolDetails = harvestingProtocolDetails;
  }

  public void setHarvestingProtocols(boolean harvestingProtocols) {
    this.harvestingProtocols = harvestingProtocols;
  }

  public void setQualityProcedures(String qualityProcedures) {
    this.qualityProcedures = qualityProcedures;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
