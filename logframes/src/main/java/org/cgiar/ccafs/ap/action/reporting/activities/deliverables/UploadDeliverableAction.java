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

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class UploadDeliverableAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(UploadDeliverableAction.class);
  private static final long serialVersionUID = 6236254742437672459L;

  // Manager
  private DeliverableManager deliverableManager;
  private DeliverableFileManager deliverableFileManager;
  private SubmissionManager submissionManager;

  // Model
  private File file;
  private String fileContentType;
  private String fileFileName;
  private int activityID;
  private int deliverableID;
  private Deliverable deliverable;
  private boolean saved;
  private boolean canSubmit;
  private int fileID;

  @Inject
  public UploadDeliverableAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, DeliverableFileManager deliverableFileManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.deliverableFileManager = deliverableFileManager;
    this.submissionManager = submissionManager;
  }

  @Override
  public String execute() throws Exception {
    boolean fileCopied = false;
    deliverable = deliverableManager.getDeliverable(deliverableID);
    if (!canSubmit || deliverable.getType() == null) {
      saved = false;
      fileID = -1;
      return INPUT;
    }

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

    fileCopied = FileManager.copyFile(file, finalPath.toString() + fileFileName);

    DeliverableFile file = new DeliverableFile();
    file.setHosted(APConstants.DELIVERABLE_FILE_LOCALLY_HOSTED);
    file.setSize(this.file.length());
    file.setName(fileFileName);

    int deliverableFileID = deliverableFileManager.existsDeliverableFile(file.getName(), deliverableID);
    file.setId(deliverableFileID);

    fileID = deliverableFileManager.saveDeliverableFile(file, deliverableID);
    saved = (fileID != -1) && fileCopied ? true : false;

    return SUCCESS;
  }

  public int getActivityID() {
    return activityID;
  }

  public int getDeliverableID() {
    return deliverableID;
  }

  public File getFile() {
    return file;
  }

  public String getFileContentType() {
    return fileContentType;
  }

  public String getFileFileName() {
    return fileFileName;
  }

  public int getFileID() {
    return fileID;
  }

  public boolean isSaved() {
    return saved;
  }

  @Override
  public void prepare() throws Exception {
    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }

  public void setFile(File file) {
    this.file = file;
  }

  public void setFileContentType(String fileContentType) {
    this.fileContentType = fileContentType;
  }

  public void setFileFileName(String fileFileName) {
    this.fileFileName = fileFileName;
  }

}
