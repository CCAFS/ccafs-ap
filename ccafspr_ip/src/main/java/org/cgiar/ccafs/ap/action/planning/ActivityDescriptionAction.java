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

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
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
  private ProjectManager projectManager;

  // Model for the back-end
  private Activity activity;

  // Model for the front-end
  private Project project;
  private int activityID;
  private boolean isExpected;
  private List<IPCrossCutting> ipCrossCuttings;
  private List<Institution> allPartners;

  @Inject
  public ActivityDescriptionAction(APConfig config, ActivityManager activityManager,
    InstitutionManager institutionManager, IPCrossCuttingManager ipCrossCuttingManager, ProjectManager projectManager) {
    super(config);
    this.activityManager = activityManager;
    this.institutionManager = institutionManager;
    this.ipCrossCuttingManager = ipCrossCuttingManager;
    this.projectManager = projectManager;
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

  public int getEndYear() {
    return config.getEndYear();
  }

  public List<IPCrossCutting> getIpCrossCuttings() {
    return ipCrossCuttings;
  }

  public Project getProject() {
    return project;
  }


  public int getStartYear() {
    return config.getStartYear();
  }


  public boolean isExpected() {
    return isExpected;
  }

  @Override
  public String next() {
    String result = save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    }
    return result;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));

    activity = activityManager.getActivityById(activityID);
    project = projectManager.getProjectFromActivityId(activityID);

    if (activity.getLeader() != null) {
      isExpected = false;
    } else {
      isExpected = true;
      // Let's create an empty user.
      if (activity.getExpectedLeader() == null) {
        User expectedLeader = new User();
        expectedLeader.setId(-1);
        activity.setExpectedLeader(expectedLeader);
      }
    }

    // Getting the list of Cross Cutting Themes
    ipCrossCuttings = ipCrossCuttingManager.getIPCrossCuttings();

    // Getting the List of all Institutions
    allPartners = institutionManager.getAllInstitutions();

    // Getting the information of the Cross Cutting Themes associated with the project
    activity.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByActivityID(activityID));
  }

  @Override
  public String save() {
    boolean success = true;
    if (activity.getExpectedLeader() != null) {
      // Saving the information of the expected leader.
      int result = activityManager.saveExpectedActivityLeader(activityID, activity.getExpectedLeader());
      if (result > 0) {
        // if new record was added, we need to assign this new id to the activity.
        activity.getExpectedLeader().setId(result);
      }
    }
    // then, save the full information of the activity description, included the expected activity leader, if applies.
    int result = activityManager.saveActivity(project.getId(), activity);
    if (result < 0) {
      success = false;
    }

    // Saving Cross Cutting elements.
    ipCrossCuttingManager.deleteCrossCuttingsByActivity(activity.getId());
    for (IPCrossCutting ipCrossTheme : activity.getCrossCuttings()) {
      ipCrossCuttingManager.saveCrossCutting(activityID, ipCrossTheme.getId());
    }

    if (success == false) {
      addActionError(getText("saving.problem"));
      return BaseAction.INPUT;
    }
    addActionMessage(getText("saving.success", new String[] {getText("planning.projectDescription.title")}));
    return BaseAction.SUCCESS;


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

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  public void setIpCrossCuttings(List<IPCrossCutting> ipCrossCuttings) {
    this.ipCrossCuttings = ipCrossCuttings;
  }
}
