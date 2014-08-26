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
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 */
public class ActivityDeliverablesAction extends BaseAction {

  private static final long serialVersionUID = -2433705513583937702L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityDeliverablesAction.class);

  // Manager
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private NextUserManager nextUserManager;
  private ProjectManager projectManager;

  // Model for the back-end
  private List<Deliverable> deliverables;
  private List<NextUser> nextUsers;

  // Model for the front-end
  private Project project;
  private int activityID;
  private List<DeliverableType> deliverableTypes;

  @Inject
  public ActivityDeliverablesAction(APConfig config, DeliverableManager deliverableManager,
    NextUserManager nextUserManager, DeliverableTypeManager deliverableTypeManager, ProjectManager projectManager) {
    super(config);
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
    this.projectManager = projectManager;
  }


  public int getActivityID() {
    return activityID;
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }

  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
  }

  public List<NextUser> getNextUsers() {
    return nextUsers;
  }

  public Project getProject() {
    return project;
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
    // Getting the project activity
    project = projectManager.getProjectFromActivityId(activityID);

    // Getting the Type of Deliverables
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();

    // Getting the List of Expected Deliverables
    deliverables = deliverableManager.getDeliverablesByActivity(activityID);

    // Getting the List of Next Users related to the expected Deliverable
    for (Deliverable deliverable : deliverables) {
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));
    }
  }


// @Override
// public String save() {
// boolean success = true;
// // Saving Project Outcome
// boolean saved = deliverableManager.saveDeliverable(activityID, deliverable);
// if (!saved) {
// success = false;
// }
// return INPUT;
// }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setDeliverables(List<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setDeliverableTypes(List<DeliverableType> deliverableTypes) {
    this.deliverableTypes = deliverableTypes;
  }

  public void setNextUsers(List<NextUser> nextUsers) {
    this.nextUsers = nextUsers;
  }
}
