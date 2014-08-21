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
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;
import org.cgiar.ccafs.ap.data.model.Institution;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 */
public class ActivityDescriptionAction extends BaseAction {

  private static final long serialVersionUID = -2523743477705227748L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityDescriptionAction.class);

  // Manager
  private ActivityManager activityManager;
  private InstitutionManager institutionManager;
  private IPCrossCuttingManager ipCrossCuttingManager;

  // Model for the back-end
  private Activity activity;

  // Model for the front-end
  private int activityID;
  private List<IPCrossCutting> ipCrossCuttings;
  private List<Institution> allPartners;

  @Inject
  public ActivityDescriptionAction(APConfig config, ActivityManager activityManager,
    InstitutionManager institutionManager, IPCrossCuttingManager ipCrossCuttingManager) {
    super(config);
    this.activityManager = activityManager;
    this.institutionManager = institutionManager;
    this.ipCrossCuttingManager = ipCrossCuttingManager;
  }

  public Activity getActivity() {
    return activity;
  }


  public int getActivityID() {
    return activityID;
  }


  public List<Institution> getAllPartners() {
    return allPartners;
  }

  /**
   * This method returns an array of cross cutting ids depending on the project.crossCuttings attribute.
   * 
   * @return an array of integers.
   */
  public int[] getCrossCuttingIds() {
    if (this.activity.getCrossCuttings() != null) {
      int[] ids = new int[this.activity.getCrossCuttings().size()];
      for (int c = 0; c < ids.length; c++) {
        ids[c] = this.activity.getCrossCuttings().get(c).getId();
      }
      return ids;
    }
    return null;
  }

  public List<IPCrossCutting> getIpCrossCuttings() {
    return ipCrossCuttings;
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

    // Getting the information of the Cross Cutting Theme for the View
    ipCrossCuttings = ipCrossCuttingManager.getIPCrossCuttings();

    // Getting the information for the activity
    activity = activityManager.getActivityById(activityID);
    // Getting the List of Institutions
    allPartners = institutionManager.getAllInstitutions();

    // Getting the information of the Cross Cutting Theme associated with the project
    activity.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByActivityID(activityID));
  }


  @Override
  public String save() {
    boolean success = true;
    // Saving Project Outcome

    boolean saved = activityManager.saveActivity(1, activity);
    if (!saved) {
      success = false;
    }

    return INPUT;

  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }


  public void setAllPartners(List<Institution> allPartners) {
    this.allPartners = allPartners;
  }

  public void setIpCrossCuttings(List<IPCrossCutting> ipCrossCuttings) {
    this.ipCrossCuttings = ipCrossCuttings;
  }
}
