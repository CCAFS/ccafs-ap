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
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.validation.BaseValidation;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableInformationReportingActionValidation extends BaseValidation {

  private boolean isValid;
  private StringBuilder validationMessage;

  // Managers
  private MetadataManager metadataManager;

  // Model
  private Map<Metadata, MetadataRequirement> metadataRequired;

  @Inject
  public DeliverableInformationReportingActionValidation(MetadataManager metadataManager) {
    this.metadataManager = metadataManager;
  }

  private void addMessage(String fieldName) {
    validationMessage.append(getText("validation.isMissing", new String[] {fieldName}));
    validationMessage.append(", ");

    isValid = false;
  }

  public String getValidationMessage() {
    if (validationMessage != null) {
      int index = validationMessage.lastIndexOf(",");
      if (index != -1) {
        validationMessage.setCharAt(index, '.');
      }
      return validationMessage.toString();
    }
    return "";
  }

  private boolean isMetadataFilled(Deliverable deliverable, String metadataName) {
    int metadataIndex = deliverable.getMetadataIndex(metadataName);
    String metadataValue = deliverable.getMetadataValue(metadataName);

    return (metadataIndex != -1 && metadataValue != null && !metadataValue.isEmpty());
  }

  public boolean isValid() {
    return isValid;
  }

  public void setValid(boolean isValid) {
    this.isValid = isValid;
  }

  public void validate(Deliverable deliverable, Publication publication) {
    isValid = true;
    validationMessage = new StringBuilder();

    // TODO - Remove the 2014 and put the current reporting logframe
    if (deliverable.getYear() != 2014) {
      return;
    }
    validateMetadata(deliverable);

    if (deliverable.isData()) {
      if (!isValidString(deliverable.getAccessDetails().getQualityProcedures())) {
        addMessage(getText("reporting.deliverables.dataAccess.dataQualityProcedures"));
      }
    }

    if (deliverable.isPublication()) {
      if (!isValidString(publication.getCitation())) {
        addMessage(getText(""));
      }
    }
  }

  private void validateMetadata(Deliverable deliverable) {
    metadataRequired = metadataManager.getRequiredMetadata(deliverable.getType().getId());

    for (Entry<Metadata, MetadataRequirement> entry : metadataRequired.entrySet()) {
      if (entry.getKey().getName().equals("Description") || entry.getKey().getName().equals("Date")
        || entry.getKey().getName().equals("Source")) {
        continue;
      }
      if (entry.getKey().getName().equals("Rights") && !deliverable.isData()) {
        continue;
      }

      // If the metadata is mandatory and it is not present in the deliverable
      if (entry.getValue().isMandatory() && !isMetadataFilled(deliverable, entry.getKey().getName())) {
        addMessage(entry.getKey().getName());
      }
    }
  }
}
