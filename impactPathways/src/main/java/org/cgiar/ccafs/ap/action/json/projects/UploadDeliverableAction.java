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
  private String filename;
  private int deliverableID;
  private int projectID;
  private Deliverable deliverable;
  private boolean saved;
  private int fileID;

  @Inject
  public UploadDeliverableAction(APConfig config, DeliverableManager deliverableManager,
    DeliverableDataSharingFileDAO deliverableFileManager) {
    super(config);
    this.deliverableManager = deliverableManager;
    this.deliverableFileManager = deliverableFileManager;

  }

  @Override
  public String execute() throws Exception {
    boolean fileCopied = false;

    // Validate if project parameter exists in the URL.

    deliverable = deliverableManager.getDeliverableById(deliverableID);


    StringBuilder finalPath = new StringBuilder();
    // finalPath.append(config.getDeliverablesFilesPath());
    finalPath.append("/");
    finalPath.append(this.getCurrentUser());
    finalPath.append("/");
    finalPath.append(config.getReportingCurrentYear());
    finalPath.append("/");
    finalPath.append(deliverable.getType().getName());
    finalPath.append("/");
    finalPath.append(deliverableID);
    finalPath.append("/");

    fileCopied = FileManager.copyFile(file, finalPath.toString() + filename);

    DeliverableFile delFile = new DeliverableFile();
    delFile.setHosted(APConstants.DELIVERABLE_FILE_LOCALLY_HOSTED);
    delFile.setSize(this.file.length());
    delFile.setName(filename);

    // int deliverableFileID = deliverableFileManager.existsDeliverableFile(file.getName(), deliverableID);
    // file.setId(deliverableFileID);

    // fileID = deliverableFileManager.saveDeliverableFile(file, deliverableID);
    // saved = (fileID != -1) && fileCopied ? true : false;

    DeliverableDataSharingFile file = new DeliverableDataSharingFile();
    file.setDeliverableId(deliverableID);
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


  public int getDeliverableID() {
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


  public int getFileID() {
    return fileID;
  }


  public String getFilename() {
    return filename;
  }


  public int getProjectID() {
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


  public void setDeliverableID(int deliverableID) {
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


  public void setFileID(int fileID) {
    this.fileID = fileID;
  }


  public void setFilename(String filename) {
    this.filename = filename;
  }


  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setSaved(boolean saved) {
    this.saved = saved;
  }


}

