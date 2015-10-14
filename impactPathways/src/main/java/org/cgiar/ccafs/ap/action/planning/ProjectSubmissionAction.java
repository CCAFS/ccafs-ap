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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
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

  private ByteBuffer getAsByteArray(URL url) throws IOException {
    URLConnection connection = url.openConnection();
    // Since you get a URLConnection, use it to get the InputStream
    InputStream in = connection.getInputStream();
    // Now that the InputStream is open, get the content length
    int contentLength = connection.getContentLength();

    // To avoid having to resize the array over and over and over as
    // bytes are written to the array, provide an accurate estimate of
    // the ultimate size of the byte array
    ByteArrayOutputStream tmpOut;
    if (contentLength != -1) {
      tmpOut = new ByteArrayOutputStream(contentLength);
    } else {
      tmpOut = new ByteArrayOutputStream(16384); // Pick some appropriate size
    }
    byte[] buf = new byte[512];
    while (true) {
      int len = in.read(buf);
      if (len == -1) {
        break;
      }
      tmpOut.write(buf, 0, len);
    }
    in.close();
    tmpOut.close(); // No effect, but good to do anyway to keep the metaphor alive

    byte[] array = tmpOut.toByteArray();

    // Lines below used to test if file is corrupt
    // FileOutputStream fos = new FileOutputStream("C:\\abc.pdf");
    // fos.write(array);
    // fos.close();

    return ByteBuffer.wrap(array);
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

  private void sendNotficationEmail() {
    StringBuilder message = new StringBuilder();

    message.append(this.getText("planning.submit.email.message",
      new String[] {this.getCurrentUser().getComposedCompleteName(), String.valueOf(config.getPlanningCurrentYear())}));

    message.append(this.getText("planning.manageUsers.email.bye"));

    String subject = this.getText("planning.submit.email.subject",
      new String[] {String.valueOf(project.getStandardIdentifier(Project.EMAIL_SUBJECT_IDENTIFIER))});
    // StringBuilder message = new StringBuilder();
    // // Building the Email message:
    // message.append(this.getText("planning.manageUsers.email.dear", new String[] {userUnassigned.getFirstName()}));
    // message.append(
    // this.getText("planning.manageUsers.email.project.unAssigned", new String[] {projectRole, project.getTitle()}));
    // message.append(this.getText("planning.manageUsers.email.support"));
    // message.append(this.getText("planning.manageUsers.email.bye"));
    //
    String toEmail = null;
    String ccEmail = null;
    if (config.isProduction()) {
      // Send email to the new user and the P&R notification email.
      // TO
      // toEmail = userUnassigned.getEmail();
      // // CC will be the user who is making the modification.
      // if (this.getCurrentUser() != null) {
      ccEmail = this.getCurrentUser().getEmail();
      // }
    }
    // BBC will be our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();

    // Get the PDF.
    ByteBuffer buffer = null;
    try {
      URL pdfURL =
        new URL(config.getBaseUrl() + "/summaries/project.do?" + APConstants.PROJECT_REQUEST_ID + "=" + projectID);
      buffer = this.getAsByteArray(pdfURL);
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (buffer != null) {
      sendMail.send(toEmail, ccEmail, bbcEmails, subject, message.toString(), buffer.array(), "application/pdf",
        "project.pdf");
    } else {
      sendMail.send(toEmail, ccEmail, bbcEmails, subject, message.toString(), null, null, null);
    }
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