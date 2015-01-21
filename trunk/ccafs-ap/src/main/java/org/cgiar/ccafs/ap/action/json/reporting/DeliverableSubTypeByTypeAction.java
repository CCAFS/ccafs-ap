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
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableSubTypeByTypeAction extends BaseAction {

  // Managers
  private DeliverableTypeManager deliverableTypeManager;

  // Model
  private String deliverableTypeID;
  private List<DeliverableType> subTypes;

  @Inject
  public DeliverableSubTypeByTypeAction(APConfig config, LogframeManager logframeManager,
    DeliverableTypeManager deliverableTypeManager) {
    super(config, logframeManager);
    this.deliverableTypeManager = deliverableTypeManager;
  }

  @Override
  public String execute() throws Exception {
    int typeID = Integer.parseInt(deliverableTypeID);
    subTypes = new ArrayList<>();
    DeliverableType[] allSubtypes = deliverableTypeManager.getDeliverableSubTypes();

    for (DeliverableType type : allSubtypes) {
      if (type.getParent().getId() == typeID) {
        subTypes.add(type);
      }
    }

    return SUCCESS;
  }

  public List<DeliverableType> getSubTypes() {
    return subTypes;
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

  public void setSubTypes(List<DeliverableType> subTypes) {
    this.subTypes = subTypes;
  }

}
