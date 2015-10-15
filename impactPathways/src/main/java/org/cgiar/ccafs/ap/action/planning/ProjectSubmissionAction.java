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
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.util.SendMail;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.URLFileDownloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
  private ProjectPartnerManager partnerManager;
  private UserManager userManager;
  private SendMail sendMail;

  // Model for the back-end
  private Project project;
  private int projectID;

  // Model for the front-end
  private Submission submission;
  private boolean alreadySubmitted;

  @Inject
  public ProjectSubmissionAction(APConfig config, SubmissionManager submissionManager, ProjectManager projectManager,
    UserManager userManager, ProjectPartnerManager partnerManager, SendMail sendMail) {
    super(config);
    this.submissionManager = submissionManager;
    this.projectManager = projectManager;
    this.userManager = userManager;
    this.partnerManager = partnerManager;
    this.sendMail = sendMail;
  }


  @Override
  public String execute() throws Exception {
    // Check if user has permissions to submit the project.
    if (securityContext.canSubmitProject(projectID)) {
      // isComplete method comes from BaseAction.
      if (this.isComplete()) {
        // Getting all the submissions made for this project.
        List<Submission> submissions = submissionManager.getProjectSubmissions(project);
        for (Submission theSubmission : submissions) {
          // Get the submission we need.
          if (theSubmission.getYear() == config.getPlanningCurrentYear()
            && theSubmission.getCycle().equals("Planning")) {
            submission = theSubmission;
            alreadySubmitted = true;
          }
        }

        if (!alreadySubmitted) {
          // Let's submit the project. <:)
          this.submitProject();
        } else {
          LOG.info("User " + this.getCurrentUser().getComposedCompleteName() + " tried to submit the ProjectID="
            + projectID + " which is already submitted.");
        }
      } else {
        LOG.info("User " + this.getCurrentUser().getComposedCompleteName() + " tried to submit the ProjectID="
          + projectID + " which is not complete yet.");
      }
      return INPUT;
    }
    return NOT_AUTHORIZED;
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

  public boolean isAlreadySubmitted() {
    return alreadySubmitted;
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
    project.setProjectPartners(partnerManager.getProjectPartners(project));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, "Planning");
  }

  private void sendNotficationEmail() {
    // Building the email message
    StringBuilder message = new StringBuilder();
    String[] values = new String[3];
    values[0] = this.getCurrentUser().getComposedCompleteName();
    values[1] = project.getTitle();
    values[2] = String.valueOf(config.getPlanningCurrentYear());
    message.append(this.getText("planning.submit.email.message", values));
    message.append(this.getText("planning.manageUsers.email.support"));
    message.append(this.getText("planning.manageUsers.email.bye"));
    String subject = this.getText("planning.submit.email.subject",
      new String[] {String.valueOf(project.getStandardIdentifier(Project.EMAIL_SUBJECT_IDENTIFIER))});

    String toEmail = null;
    String ccEmail = null;
    if (config.isProduction()) {
      // Send email to the user that is submitting the project.
      // TO
      toEmail = this.getCurrentUser().getEmail();

      // Getting all the MLs associated to the Project Liaison institution
      List<User> owners = userManager.getAllOwners(project.getLiaisonInstitution());
      StringBuilder ccEmails = new StringBuilder();
      for (User user : owners) {
        if (user.getId() != this.getCurrentUser().getId()) {
          ccEmails.append(user.getEmail());
          ccEmails.append(" ");
        }
      }
      // Also the Project Leader
      System.out.println();
      if (project.getLeaderPerson() != null
        && project.getLeaderPerson().getUser().getId() != this.getCurrentUser().getId()) {
        ccEmails.append(project.getLeaderPerson().getUser().getEmail());
        ccEmails.append(" ");
      }
      // and the Project Coordinator(s).
      for (PartnerPerson partnerPerson : project.getCoordinatorPersons()) {
        if (partnerPerson.getUser().getId() != this.getCurrentUser().getId()) {
          ccEmails.append(partnerPerson.getUser().getEmail());
          ccEmails.append(" ");
        }
      }

      // CC will be the other MLs.
      ccEmail = ccEmails.toString().isEmpty() ? null : ccEmails.toString();

    }
    // BBC will be our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();

    // Get the PDF from the Project report url.
    ByteBuffer buffer = null;
    String fileName = null;
    String contentType = null;
    try {
      // Making the URL to get the report.
      URL pdfURL =
        new URL(config.getBaseUrl() + "/summaries/project.do?" + APConstants.PROJECT_REQUEST_ID + "=" + projectID);
      // Getting the file data.
      Map<String, Object> fileProperties = URLFileDownloader.getAsByteArray(pdfURL);
      buffer = fileProperties.get("byte_array") != null ? (ByteBuffer) fileProperties.get("byte_array") : null;
      fileName = fileProperties.get("filename") != null ? (String) fileProperties.get("filename") : null;
      contentType = fileProperties.get("mime_type") != null ? (String) fileProperties.get("mime_type") : null;
    } catch (MalformedURLException e) {
      // Do nothing.
      LOG.error("There was an error trying to get the URL to download the PDF file: " + e.getMessage());
    } catch (IOException e) {
      // Do nothing
      LOG.error(
        "There was a problem trying to download the PDF file for the projectID=" + projectID + " : " + e.getMessage());
    }

    if (buffer != null && fileName != null && contentType != null) {
      sendMail.send(toEmail, ccEmail, bbcEmails, subject, message.toString(), buffer.array(), contentType, fileName);
    } else {
      sendMail.send(toEmail, ccEmail, bbcEmails, subject, message.toString(), null, null, null);
    }
  }

  public void setAlreadySubmitted(boolean alreadySubmitted) {
    this.alreadySubmitted = alreadySubmitted;
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
      this.sendNotficationEmail();
    }
  }
}