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

package org.cgiar.ccafs.ap.action.json.reporting;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MetadataManager;
import org.cgiar.ccafs.ap.data.model.Metadata;
import org.cgiar.ccafs.ap.data.model.MetadataRequirement;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Hernán David Carvajal
 */

public class MetadataRequiredByDeliverableTypeAction extends BaseAction {

  private static final long serialVersionUID = -3708645626412029792L;

  // Manager
  private MetadataManager metadataManager;

// Model
  private String deliverableTypeID;

  private Map<Metadata, MetadataRequirement> metadataRequired;
  private Map<Map<String, String>, MetadataRequirement> result;

  @Inject
  public MetadataRequiredByDeliverableTypeAction(APConfig config, LogframeManager logframeManager,
    MetadataManager metadataManager) {
    super(config, logframeManager);
    this.metadataManager = metadataManager;
  }

  @Override
  public String execute() {
    metadataRequired = metadataManager.getRequiredMetadata(Integer.parseInt(deliverableTypeID));
    result = new HashMap<>();

    for (Entry<Metadata, MetadataRequirement> entry : metadataRequired.entrySet()) {
      Map<String, String> temp = new HashMap<>();
      temp.put(entry.getKey().getId() + "", entry.getKey().getName());

      result.put(temp, entry.getValue());
    }

    return SUCCESS;
  }

  public Map<Metadata, MetadataRequirement> getMetadataRequirements() {
    return metadataRequired;
  }

  public Map<Map<String, String>, MetadataRequirement> getResult() {
    return result;
  }


  @Override
  public void prepare() throws Exception {
    // Verify if there is a activityID parameter
    if (this.getRequest().getParameter(APConstants.DELIVERABLE_TYPE_REQUEST_ID) == null) {
      deliverableTypeID = "";
      return;
    }

    // If there is a parameter take its values
    deliverableTypeID = StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_TYPE_REQUEST_ID));
  }

  public void setMetadataRequired(Map<Metadata, MetadataRequirement> metadataRequired) {
    this.metadataRequired = metadataRequired;
  }

}
