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
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.SendMail;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectSubmissionAction extends BaseAction {

  private static final long serialVersionUID = -6518242684588260210L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectSubmissionAction.class);

  // Manager
  private SubmissionManager submissionManager;
  private ProjectManager projectManager;
  private SendMail sendMail;

  // Model for the back-end
  private Project project;
  private int projectID;

  // Model for the front-end
  private Submission submission;
  private boolean alreadySubmitted;

  @Inject
  public ProjectSubmissionAction(APConfig config, SubmissionManager submissionManager, ProjectManager projectManager,
    SendMail sendMail) {
    super(config);
    this.submissionManager = submissionManager;
    this.projectManager = projectManager;
    this.sendMail = sendMail;
  }

  @Override
  public String execute() throws Exception {

    if (this.isComplete()) {
      // Getting all the submissions made for this project.
      List<Submission> submissions = submissionManager.getProjectSubmissions(project);
      for (Submission theSubmission : submissions) {
        // Get the submission we need.
        if (theSubmission.getYear() == config.getPlanningCurrentYear() && theSubmission.getCycle().equals("Planning")) {
          submission = theSubmission;
          alreadySubmitted = true;
        }
      }

      if (alreadySubmitted) {
        System.out.println("The project already was submitted");
      } else {
        // Let's submit the project.
        this.submitProject();
      }
    } else {
      System.out.println("Project is not complete");
    }
    return INPUT;
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

  @Override
  public void prepare() throws Exception {
    super.prepare();

    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID, e);
      projectID = -1;
      return; // Stop here and go to execute method.
    }

    // Getting the project information.
    project = projectManager.getProject(projectID);

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  private void submitProject() {
    submission = new Submission();
    submission.setId(-1);
    submission.setCycle("Planning");
    submission.setUser(this.getCurrentUser());
    submission.setYear((short) config.getPlanningCurrentYear());
    submission.setDateTime(new Date());

    int result = submissionManager.saveProjectSubmission(project, submission);

    if (result > 0) {
      System.out.println("The project has been successfully submitted!");
      // TODO Send emails.
    }
  }
}