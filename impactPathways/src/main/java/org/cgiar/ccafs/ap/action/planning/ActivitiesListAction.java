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
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.validation.planning.ActivitiesListValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 * @author Carlos Alberto Martínez M.
 */
public class ActivitiesListAction extends BaseAction {

  private static final long serialVersionUID = -5425536924161465111L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivitiesListAction.class);

  // Managers
  private ActivityManager activityManager;
  private ProjectManager projectManager;
  private ProjectPartnerManager projectPartnerManager;
  private Map<Integer, String> projectPartnerPersons;
  private HistoryManager historyManager;

  // Model for the back-end
  private List<ProjectPartner> projectPartners;

  // Model for the front-end
  private Project project;
  private int projectID;
  private int activityID;

  // validator
  private ActivitiesListValidator validator;


  @Inject
  public ActivitiesListAction(APConfig config, ActivityManager activityManager, ProjectManager projectManager,
    ProjectPartnerManager projectPartnerManager, ActivitiesListValidator validator, HistoryManager historyManager) {
    super(config);
    this.activityManager = activityManager;
    this.projectManager = projectManager;
    this.projectPartnerManager = projectPartnerManager;
    this.validator = validator;
    this.historyManager = historyManager;
  }

  @Override
  public String add() {
    // Create new activity and redirect to activity description using the new activityID assigned by the database.
    activityID =
      activityManager.saveActivity(projectID, new Activity(-1), this.getCurrentUser(), this.getJustification());
    if (activityID > 0) {
      this.addActionMessage(this.getText("saving.add.new", new String[] {this.getText("planning.activity")}));
      // Let's redirect the user to the Activity Description section.
      return BaseAction.SUCCESS;
    }
    // Let's redirect the user to the error page.
    return BaseAction.ERROR;
  }

  /**
   * This method validates if an activity can be deleted or not.
   * Keep in mind that an activity can be deleted if it was created in the current planning cycle.
   * 
   * @param activityID is the activity identifier.
   * @return true if the activity can be deleted, false otherwise.
   */
  public boolean canDelete(int activityID) {
    // First, loop all projects that the user is able to edit.
    for (Activity activity : project.getActivities()) {
      if (activity.getId() == activityID) {
        if (activity.isNew(this.config.getCurrentPlanningStartDate())) {
          return true;
        }
      }
    }
    // If nothing found, return false.
    return false;
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

  public Map<Integer, String> getProjectPartnerPersons() {
    return projectPartnerPersons;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);
    project.setActivities(activityManager.getActivitiesByProject(projectID));

    projectPartners = projectPartnerManager.getProjectPartners(project);

    // Creating Map of partner persons to be displayed in the view.
    projectPartnerPersons = new HashMap<>();
    for (ProjectPartner partner : projectPartners) {
      for (PartnerPerson person : partner.getPartnerPersons()) {
        projectPartnerPersons.put(person.getId(), partner.getPersonComposedName(person.getId()));
      }
    }

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getActivities() != null) {
        project.getActivities().clear();
      }
    }

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), this.getCurrentPlanningYear()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");

    // Getting the history for this section.
    super.setHistory(historyManager.getActivitiesHistory(project.getId()));
  }


  @Override
  public String save() {
    if (securityContext.canUpdateProjectActivities(projectID)) {

      super.saveProjectLessons(projectID);

      // Update only the values to which the user is authorized to modify
      boolean success = true;

      // Getting previous activities to identify those that need to be deleted.
      List<Activity> previousActivities = activityManager.getActivitiesByProject(projectID);

      // Deleting activities
      boolean deleted;
      for (Activity previousActivity : previousActivities) {
        if (!project.getActivities().contains(previousActivity)) {
          deleted =
            activityManager.deleteActivity(previousActivity.getId(), this.getCurrentUser(), this.getJustification());
          if (!deleted) {
            success = false;
          }
        }
      }
      // Saving new and old Activities
      boolean saved = activityManager.saveActivityList(projectID, project.getActivities(), this.getCurrentUser(),
        this.getJustification());

      if (!saved) {
        success = false;
      }
      if (!success) {
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

  public void setProjectPartnerPersons(Map<Integer, String> projectPartnerPersons) {
    this.projectPartnerPersons = projectPartnerPersons;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project, "Planning");
    }
  }

}
