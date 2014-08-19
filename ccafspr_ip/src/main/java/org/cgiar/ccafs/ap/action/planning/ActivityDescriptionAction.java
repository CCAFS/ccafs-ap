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
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
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


  private static final long serialVersionUID = 2845677913596494699L;

  // Manager
  private ActivityManager activityManager;
  private InstitutionManager institutionManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityDescriptionAction.class);

  // Model for the back-end
  private Activity activity;


  // Model for the front-end
  private int activityID;
  private List<Institution> allPartners;
  private boolean hasExpectedLeader;


  @Inject
  public ActivityDescriptionAction(APConfig config, ActivityManager activityManager,
    InstitutionManager institutionManager) {
    super(config);
    this.activityManager = activityManager;
    this.institutionManager = institutionManager;
  }


  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  /**
   * This method returns an array of cross cutting ids depending on the project.crossCuttings attribute.
   *
   * @return an array of integers.
   */
// public int[] getCrossCuttingIds() {
// if (this.project.getCrossCuttings() != null) {
// int[] ids = new int[this.project.getCrossCuttings().size()];
// for (int c = 0; c < ids.length; c++) {
// ids[c] = this.project.getCrossCuttings().get(c).getId();
// }
// return ids;
// }
// return null;
// }

  public List<Institution> getAllPartners() {
    return allPartners;
  }


  public boolean isHasExpectedLeader() {
    return hasExpectedLeader;
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
// ipCrossCuttings = ipCrossCuttingManager.getIPCrossCuttings();

    // Getting the information for the activity
    activity = activityManager.getActivityById(activityID);
    // Getting the List of Institutions
    allPartners = institutionManager.getAllInstitutions();
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


  public void setHasExpectedLeader(boolean hasExpectedLeader) {
    this.hasExpectedLeader = hasExpectedLeader;
  }
}