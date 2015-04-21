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
import org.cgiar.ccafs.ap.data.model.Activity;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 */
public class ActivitiesListAction extends BaseAction {

  private static final long serialVersionUID = 6131146898077781801L;

  // Manager
  private ActivityManager activityManager;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivitiesListAction.class);

  // Model for the back-end
  private List<Activity> activities;


  // Model for the front-end
  private int projectID;
  private int activityID;


  @Inject
  public ActivitiesListAction(APConfig config, ActivityManager activityManager) {
    super(config);
    this.activityManager = activityManager;
  }


  @Override
  public String add() {
    // Create new activity and redirect to activity description using the new activityID assigned by the database.
    activityID = activityManager.saveActivity(projectID, new Activity(-1));
    if (activityID > 0) {
      addActionMessage(getText("saving.add.new", new String[] {getText("planning.activity")}));
      // Let's redirect the user to the Activity Description section.
      return BaseAction.SUCCESS;
    }
    // Let's redirect the user to the error page.
    return BaseAction.ERROR;
  }


  public List<Activity> getActivities() {
    return activities;
  }

  public int getActivityID() {
    return activityID;
  }

  public int getProjectID() {
    return projectID;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    activities = activityManager.getActivitiesByProject(projectID);
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }


}
