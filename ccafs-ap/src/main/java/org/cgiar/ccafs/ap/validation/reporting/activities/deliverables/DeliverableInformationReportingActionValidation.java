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

package org.cgiar.ccafs.ap.validation.reporting.activities.deliverables;

import org.cgiar.ccafs.ap.data.manager.MetadataManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Metadata;
import org.cgiar.ccafs.ap.data.model.MetadataRequirement;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableInformationReportingActionValidation {

  private boolean isValid;
  private String validationMessage;

  // Managers
  private MetadataManager metadataManager;

  // Model
  private Map<Metadata, MetadataRequirement> metadataRequired;

  @Inject
  public DeliverableInformationReportingActionValidation(MetadataManager metadataManager) {
    this.metadataManager = metadataManager;
  }

  public String getValidationMessage() {
    return validationMessage;
  }

  private boolean isMetadataFilled(Deliverable deliverable, String metadataName) {
    return (deliverable.getMetadataIndex(metadataName) != -1 && !deliverable.getMetadataValue(metadataName).isEmpty());
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public void setValidationMessage(String validationMessage) {
    this.validationMessage = validationMessage;
  }

  public void validate(Deliverable deliverable) {
    isValid = true;
    metadataRequired = metadataManager.getRequiredMetadata(deliverable.getType().getId());

    for (Entry<Metadata, MetadataRequirement> entry : metadataRequired.entrySet()) {
      // If the metadata is mandatory and it is not present in the deliverable
      if (entry.getValue().isMandatory() && isMetadataFilled(deliverable, entry.getKey().getName())) {
        System.out.println(entry.getKey().getName());
        isValid = false;
      }
    }

    if (metadataRequired.get(deliverable.getMetadata().get(0)) != null) {
      System.out.println("Found");
    }
  }
}
