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

package org.cgiar.ccafs.ap.action.reporting.activities.deliverables;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableFileManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableFile;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.FileManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableDataReportingAction extends BaseAction {

  // Managers
  private DeliverableManager deliverableManager;
  private DeliverableFileManager deliverableFileManager;
  private SubmissionManager submissionManager;

  // Model
  private Deliverable deliverable;
  private int deliverableID;
  private int activityID;
  private boolean canSubmit;
  private List<File> filesUploaded = new ArrayList<File>();
  private List<String> filesUploadedContentType = new ArrayList<String>();
  private List<String> filesUploadedFileName = new ArrayList<String>();

  @Inject
  public DeliverableDataReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, DeliverableFileManager deliverableFileManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.deliverableFileManager = deliverableFileManager;
    this.submissionManager = submissionManager;
  }

  public int getActivityID() {
    return activityID;
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }

  public int getDeliverableID() {
    return deliverableID;
  }

  public List<File> getFilesUploaded() {
    return filesUploaded;
  }

  public List<String> getFilesUploadedContentType() {
    return filesUploadedContentType;
  }

  public List<String> getFilesUploadedFileName() {
    return filesUploadedFileName;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  @Override
  public void prepare() throws Exception {
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    deliverableID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));

    deliverable = deliverableManager.getDeliverable(deliverableID);
    deliverable.setFiles(deliverableFileManager.getDeliverableFiles(deliverable.getId()));

    filesUploaded = new ArrayList<>();
    filesUploadedContentType = new ArrayList<>();
    filesUploadedFileName = new ArrayList<>();

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (deliverable.getFiles() != null) {
        deliverable.getFiles().clear();
      }
    }
  }

  @Override
  public String save() {
    boolean success = true;

    List<DeliverableFile> previousFiles = deliverableFileManager.getDeliverableFiles(deliverable.getId());

    // Remove the deliverables that were delete in the user interface
    for (DeliverableFile previousFile : previousFiles) {
      if (!deliverable.getFiles().contains(previousFile) || deliverable.getFiles().isEmpty()) {
        boolean deleted = deliverableFileManager.removeDeliverableFile(previousFile.getId());
        if (previousFile.getHosted().equals(APConstants.DELIVERABLE_FILE_LOCALLY_HOSTED)) {
          deleted = deleted && FileManager.deleteFile(previousFile.getName());
        }

        success = success && deleted;
      }
    }

    // Save the information of the deliverable files
    for (DeliverableFile file : deliverable.getFiles()) {
      deliverableFileManager.saveDeliverableFile(file, deliverableID);
    }

    // For Internet Explorer we should copy the files to the repository and
    // add them to the database
    int c = 0;
    for (File file : filesUploaded) {
      StringBuilder finalPath = new StringBuilder();
      finalPath.append(config.getDeliverablesFilesPath());
      finalPath.append("/");
      finalPath.append(getCurrentUser().getLeader().getAcronym());
      finalPath.append("/");
      finalPath.append(config.getReportingCurrentYear());
      finalPath.append("/");
      finalPath.append(deliverable.getType().getName());
      finalPath.append("/");
      finalPath.append(deliverableID);
      finalPath.append("/");

      FileManager.copyFile(file, finalPath.toString() + filesUploadedFileName.get(c));
      DeliverableFile deliverableFile = new DeliverableFile();
      deliverableFile.setHosted(APConstants.DELIVERABLE_FILE_LOCALLY_HOSTED);
      deliverableFile.setSize(filesUploaded.get(c).length());
      deliverableFile.setId(-1);
      deliverableFile.setName(filesUploadedFileName.get(c));

      deliverableFileManager.saveDeliverableFile(deliverableFile, deliverableID);
      c++;
    }

    return super.save();
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }

  public void setFilesUploaded(List<File> filesUploaded) {
    this.filesUploaded = filesUploaded;
  }

  public void setFilesUploadedContentType(List<String> filesUploadedContentType) {
    this.filesUploadedContentType = filesUploadedContentType;
  }

  public void setFilesUploadedFileName(List<String> filesUploadedFileName) {
    this.filesUploadedFileName = filesUploadedFileName;
  }
}
