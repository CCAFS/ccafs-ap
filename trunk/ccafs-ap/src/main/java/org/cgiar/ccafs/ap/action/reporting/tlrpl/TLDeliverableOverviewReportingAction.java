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

package org.cgiar.ccafs.ap.action.reporting.tlrpl;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableMetadataManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Hernán David Carvajal
 */

public class TLDeliverableOverviewReportingAction extends BaseAction {

  private static final long serialVersionUID = -7048066940093818514L;

  // Managers
  private ActivityManager activityManager;
  private DeliverableManager deliverableManager;
  private DeliverableMetadataManager deliverableMetadataManager;

  // Data
  private Deliverable deliverable;
  private int deliverableID;
  private Activity activity;

  @Inject
  public TLDeliverableOverviewReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, ActivityManager activityManager,
    DeliverableMetadataManager deliverableMetadataManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.activityManager = activityManager;
    this.deliverableMetadataManager = deliverableMetadataManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }

  @Override
  public void prepare() {
    deliverableID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));

    deliverable = deliverableManager.getDeliverable(deliverableID);
    activity = activityManager.getActivityByDeliverable(deliverableID);

    deliverable.setMetadata(deliverableMetadataManager.getDeliverableMetadata(deliverableID));
  }
}