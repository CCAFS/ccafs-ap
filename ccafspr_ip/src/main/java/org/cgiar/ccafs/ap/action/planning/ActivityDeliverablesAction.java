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

import org.cgiar.ccafs.ap.data.manager.ActivityManager;

import org.cgiar.ccafs.ap.data.model.Activity;
import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 */
public class ActivityDeliverablesAction extends BaseAction {

  private static final long serialVersionUID = -6143944536558245482L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityDeliverablesAction.class);

  // Manager
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private NextUserManager nextUserManager;
  private ProjectManager projectManager;
  private ActivityManager activityManager;

  // Model for the back-end
  private Activity activity;

  // Model for the front-end
  private Project project;
  private int activityID;
  private List<DeliverableType> deliverableTypes;
  private List<Integer> allYears;

  @Inject
  public ActivityDeliverablesAction(APConfig config, DeliverableManager deliverableManager,
    NextUserManager nextUserManager, DeliverableTypeManager deliverableTypeManager, ProjectManager projectManager,
    ActivityManager activityManager) {
    super(config);
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
    this.projectManager = projectManager;
    this.activityManager = activityManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
  }

  public Project getProject() {
    return project;
  }

  @Override
  public void prepare() throws Exception {

    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityById(activityID);

    // Getting the project where this activity belongs to.
    project = projectManager.getProjectFromActivityId(activityID);

    // Getting the Type of Deliverables
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();

    allYears = activity.getAllYears();

    // Getting the List of Expected Deliverables
    List<Deliverable> deliverables = deliverableManager.getDeliverablesByActivity(activityID);
    activity.setDeliverables(deliverables);

    // Getting the List of Next Users related to the expected Deliverable
    for (Deliverable deliverable : activity.getDeliverables()) {
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));
    }

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (activity.getDeliverables() != null) {
        activity.getDeliverables().clear();
      }
    }
  }

  @Override
  public String save() {
    boolean success = true;
    // Saving Project Outcome
    for (Deliverable deliverable : activity.getDeliverables()) {
      boolean saved = deliverableManager.saveDeliverable(activityID, deliverable);
      if (!saved) {
        success = false;
      }
    }
    return BaseAction.SUCCESS;
  }


  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setDeliverableTypes(List<DeliverableType> deliverableTypes) {
    this.deliverableTypes = deliverableTypes;
  }

}
