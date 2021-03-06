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


package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.DeliverableDataSharingFileDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableDataSharingFile;
import org.cgiar.ccafs.ap.data.model.DeliverableFile;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UploadDeliverableAction extends BaseAction {

  /**
   * @author Christian David Garcia Oviedo
   */
  private static final long serialVersionUID = -242932821656538581L;


  public static Logger LOG = LoggerFactory.getLogger(UploadDeliverableAction.class);


  // Manager
  private DeliverableManager deliverableManager;
  private DeliverableDataSharingFileDAO deliverableFileManager;


  // Model
  private File file;
  private String fileContentType;
  private String fileFileName;
  private String deliverableID;
  private String projectID;
  private Deliverable deliverable;
  private boolean saved;
  private int fileID;

  @Inject
  public UploadDeliverableAction(APConfig config, DeliverableManager deliverableManager,
    DeliverableDataSharingFileDAO deliverableFileManager) {
    super(config);
    this.deliverableManager = deliverableManager;
    this.deliverableFileManager = deliverableFileManager;
    this.config = config;

  }

  @Override
  public String execute() throws Exception {
    boolean fileCopied = false;

    // Validate if project parameter exists in the URL.

    deliverable = deliverableManager.getDeliverableById(Integer.parseInt(deliverableID));


    // finalPath.append(config.getDeliverablesFilesPath());


    fileCopied = FileManager.copyFile(file, this.getDeliverableFilePath() + fileFileName);

    DeliverableFile delFile = new DeliverableFile();
    delFile.setHosted(APConstants.DELIVERABLE_FILE_LOCALLY_HOSTED);
    delFile.setSize(this.file.length());
    delFile.setName(fileFileName);

    // int deliverableFileID = deliverableFileManager.existsDeliverableFile(file.getName(), deliverableID);
    // file.setId(deliverableFileID);

    // fileID = deliverableFileManager.saveDeliverableFile(file, deliverableID);
    // saved = (fileID != -1) && fileCopied ? true : false;

    DeliverableDataSharingFile file = new DeliverableDataSharingFile();
    file.setDeliverableId(Integer.parseInt(deliverableID));
    file.setFile(delFile.getName());
    file.setType(delFile.getHosted());
    fileID = deliverableFileManager.save(file);
    saved = (fileID != -1) && fileCopied ? true : false;
    return SUCCESS;
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }


  public DeliverableDataSharingFileDAO getDeliverableFileManager() {
    return deliverableFileManager;
  }

  private String getDeliverableFilePath() {
    String upload = config.getUploadsBaseFolder();
    return upload + File.separator + this.getDeliverableFileRelativePath() + File.separator;
  }


  private String getDeliverableFileRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + projectID + File.separator + "deliverableDataSharing"
      + File.separator;
  }


  public String getDeliverableFileURL() {
    return config.getDownloadURL() + "/" + this.getDeliverableFilePath().replace('\\', '/');
  }


  public String getDeliverableID() {
    return deliverableID;
  }


  public DeliverableManager getDeliverableManager() {
    return deliverableManager;
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


  public String getProjectID() {
    return projectID;
  }


  public boolean isSaved() {
    return saved;
  }


  @Override
  public void prepare() throws Exception {

  }


  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }


  public void setDeliverableFileManager(DeliverableDataSharingFileDAO deliverableFileManager) {
    this.deliverableFileManager = deliverableFileManager;
  }


  public void setDeliverableID(String deliverableID) {
    this.deliverableID = deliverableID;
  }


  public void setDeliverableManager(DeliverableManager deliverableManager) {
    this.deliverableManager = deliverableManager;
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


  public void setFileID(int fileID) {
    this.fileID = fileID;
  }

  public void setProjectID(String projectID) {
    this.projectID = projectID;
  }

  public void setSaved(boolean saved) {
    this.saved = saved;
  }


}

