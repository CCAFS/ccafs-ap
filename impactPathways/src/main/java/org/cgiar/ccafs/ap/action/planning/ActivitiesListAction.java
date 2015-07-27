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
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.validation.planning.ActivitiesListValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
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

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivitiesListAction.class);
  // Manager
  private ActivityManager activityManager;
  private ProjectManager projectManager;
  private ProjectPartnerManager projectPartnerManager;
  private HistoryManager historyManager;

  // Model for the back-end
  private List<Activity> activities;
  private Project project;
  private List<ProjectPartner> projectPartners;
  // Model for the front-end
  private int projectID;
  private int activityID;

  // validator
  private ActivitiesListValidator validator;


  @Inject
  public ActivitiesListAction(APConfig config, ActivityManager activityManager, ProjectManager projectManager,
    ProjectPartnerManager projectPartnerManager, ActivitiesListValidator validator) {
    super(config);
    this.activityManager = activityManager;
    this.projectManager = projectManager;
    this.projectPartnerManager = projectPartnerManager;
    this.validator = validator;
  }

  @Override
  public String add() {
    // Create new activity and redirect to activity description using the new activityID assigned by the database.
    activityID = activityManager.saveActivity(projectID, new Activity(-1));
    if (activityID > 0) {
      this.addActionMessage(this.getText("saving.add.new", new String[] {this.getText("planning.activity")}));
      // Let's redirect the user to the Activity Description section.
      return BaseAction.SUCCESS;
    }
    // Let's redirect the user to the error page.
    return BaseAction.ERROR;
  }

  public boolean canBeDeleted(int activityID) {
    for (Activity activity : activities) {
      if (activity.getId() == activityID) {
        return activity.getCreated() >= this.config.getCurrentPlanningStartDate().getTime();
      }
    }
    return false;
  }

  public List<Activity> getActivities() {
    return activities;
  }

  public int getActivityID() {
    return activityID;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);
    activities = activityManager.getActivitiesByProject(projectID);
    projectPartners = projectPartnerManager.getProjectPartners(projectID);

  }

  @Override
  public String save() {
    if (securityContext.canUpdateProjectActivities()) {
      // Update only the values to which the user is authorized to modify
      List<Activity> activityArray = new ArrayList<Activity>();
      Activity previousActivity = new Activity();
      for (Activity activity : activities) {

        previousActivity.setId(activity.getId());

        previousActivity.setTitle(activity.getTitle());

        previousActivity.setDescription(activity.getDescription());

        previousActivity.setStartDate(activity.getStartDate());

        previousActivity.setEndDate(activity.getEndDate());

        previousActivity.setProjectPartners(projectPartners);

        previousActivity.setLeader(activity.getLeader());

        activityArray.add(previousActivity);
      }

      // Save the information
      int result = activityManager.saveActivityList(projectID, activityArray);
      if (result < 0) {
        this.addActionError(this.getText("saving.problem"));
        LOG.warn("There was a problem saving the activity list.");
        return BaseAction.INPUT;
      }

      // Get the validation messages and append them to the save message
      Collection<String> messages = this.getActionMessages();
      if (!messages.isEmpty()) {
        String validationMessage = messages.iterator().next();
        this.setActionMessages(null);
        this.addActionWarning(this.getText("saving.saved") + validationMessage);
      } else {
        this.addActionMessage(this.getText("saving.saved"));
      }
      return SUCCESS;
    }
    return NOT_AUTHORIZED;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project);
    }
  }

}
