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
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectNextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectNextUser;
import org.cgiar.ccafs.ap.validation.projects.ProjectNextUserValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author
 */
public class ProjectNextUsersAction extends BaseAction {

  private static final long serialVersionUID = -5425536924161465111L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectNextUsersAction.class);

  // Managers
  private ProjectManager projectManager;
  private ProjectNextUserManager projectNextUserManager;

  private HistoryManager historyManager;
  private List<ProjectNextUser> nextUserPreview;

  // Model for the front-end
  private Project project;
  private int projectID;

  // validator
  private ProjectNextUserValidator validator;


  @Inject
  public ProjectNextUsersAction(APConfig config, ActivityManager activityManager, ProjectManager projectManager,
    ProjectPartnerManager projectPartnerManager, ProjectNextUserValidator validator, HistoryManager historyManager,
    ProjectNextUserManager projectNextUserManager) {
    super(config);
    this.projectManager = projectManager;
    this.validator = validator;
    this.historyManager = historyManager;
    this.projectNextUserManager = projectNextUserManager;
  }


  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
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

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getNextUsers() != null) {
        project.getNextUsers().clear();
      }
    }

    project.setNextUsers(projectNextUserManager.getProjectNextUserProject(projectID));
    nextUserPreview = project.getNextUsers();
    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, this.getActionName(),
      this.getCurrentPlanningYear(), this.getCycleName()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());

    // Getting the history for this section.
    super.setHistory(historyManager.getProjectNextUser(project.getId()));
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getNextUsers() != null) {
        project.getNextUsers().clear();
      }

    }
  }

  @Override
  public String save() {
    if (this.hasProjectPermission("update", project.getId())) {
      projectNextUserManager.deleteProjectNextUserList(project.getNextUsers(), project, this.getCurrentUser(),
        this.getJustification());

      for (ProjectNextUser projectNextUser : project.getNextUsers()) {
        projectNextUser.setProjectId(projectID);
        projectNextUserManager.saveProjectNextUser(projectID, projectNextUser, this.getCurrentUser(),
          this.getJustification());
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
      validator.validate(this, project, this.getCycleName());
    }
  }

}
