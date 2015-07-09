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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 * @author Héctor fabio Tobón R.
 */
public class ActivityIPOtherContributionAction extends BaseAction {

  private static final long serialVersionUID = -3787446132042857817L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityIPOtherContributionAction.class);

  // Manager
  private ProjectOtherContributionManager ipOtherContributionManager;
  private ActivityManager activityManager;
  private ProjectManager projectManager;

  // Model for the back-end
  private OtherContribution ipOtherContribution;
  private Activity activity;

  // Model for the front-end
  private int activityID;
  private Project project;

  @Inject
  public ActivityIPOtherContributionAction(APConfig config, ProjectOtherContributionManager ipOtherContributionManager,
    ActivityManager activityManager, ProjectManager projectManager) {
    super(config);
    this.ipOtherContributionManager = ipOtherContributionManager;
    this.activityManager = activityManager;
    this.projectManager = projectManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public OtherContribution getIpOtherContribution() {
    return ipOtherContribution;
  }

  public Project getProject() {
    return project;
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));

    // Getting the activity information
    activity = activityManager.getActivityById(activityID);
    // Getting the project information.
    project = projectManager.getProjectFromActivityId(activityID);

    // Getting the information for the IP Other Contribution
    // ipOtherContribution = ipOtherContributionManager.getIPOtherContributionByActivityId(activityID);

    activity.setIpOtherContribution(ipOtherContribution);
  }

  @Override
  public String save() {
    if (this.isSaveable()) {
      // Saving Activity IP Other Contribution
      boolean saved =
        ipOtherContributionManager.saveIPOtherContribution(activityID, activity.getIpOtherContribution(),
          this.getCurrentUser(), this.getJustification());

      if (!saved) {
        this.addActionError(this.getText("saving.problem"));
        return BaseAction.INPUT;
      } else {
        this.addActionMessage(this.getText("saving.success",
          new String[] {this.getText("planning.impactPathways.otherContributions.title")}));
        return BaseAction.SUCCESS;
      }
    }
    return BaseAction.ERROR;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setIpOtherContribution(OtherContribution ipOtherContribution) {
    this.ipOtherContribution = ipOtherContribution;
  }


}