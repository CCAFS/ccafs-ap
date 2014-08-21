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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 */
public class ActivityImpactPathwayAction extends BaseAction {

  private static final long serialVersionUID = -5073068363009363496L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityImpactPathwayAction.class);

  // Manager
  private ActivityManager activityManager;
  private IPElementManager ipElementManager;

  // Model for the back-end

  // Model for the front-end
  private int activityID;

  @Inject
  public ActivityImpactPathwayAction(APConfig config, ActivityManager activityManager, IPElementManager ipElementManager) {
    super(config);
    this.activityManager = activityManager;
    this.ipElementManager = ipElementManager;
  }


  public int getActivityID() {
    return activityID;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    try {
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityID, e);
      activityID = -1;
      return; // Stop here and go to execute method.
    }

    // Getting the information for the activity Impact Pathway
  }


// @Override
// public String save() {
// boolean success = true;
// // Saving Project Outcome
//
// boolean saved = activityManager.saveActivity(1, activity);
// if (!saved) {
// success = false;
// }
// return INPUT;
// }


  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

}
