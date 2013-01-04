package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableStatusManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.FileFormatManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.FileFormat;

import java.util.Arrays;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverablesReportingAction extends BaseAction {

  private static final long serialVersionUID = -5156289630539056822L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(DeliverablesReportingAction.class);

  // Managers
  private DeliverableManager deliverableManager;
  private DeliverableStatusManager deliverableStatusManager;
  private DeliverableTypeManager deliverableTypeManager;
  private FileFormatManager fileFormatManager;
  private ActivityManager activityManager;

  // Model
  private DeliverableType[] deliverableTypesList;
  private DeliverableStatus[] deliverableStatusList;
  private FileFormat[] fileFormatsList;
  private int[] deliverableTypeIdsNeeded;
  private int[] fileFormatIds;
  private Activity activity;
  private int activityID;

  @Inject
  public DeliverablesReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, ActivityManager activityManager,
    DeliverableTypeManager deliverableTypeManager, DeliverableStatusManager deliverableStatusManager,
    FileFormatManager fileFormatManager) {

    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.activityManager = activityManager;
    this.deliverableStatusManager = deliverableStatusManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.fileFormatManager = fileFormatManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public DeliverableStatus[] getDeliverableStatusList() {
    return deliverableStatusList;
  }

  public int[] getDeliverableTypeIdsNeeded() {
    return deliverableTypeIdsNeeded;
  }


  public DeliverableType[] getDeliverableTypesList() {
    return deliverableTypesList;
  }


  public int[] getFileFormatIds() {
    return fileFormatIds;
  }

  public FileFormat[] getFileFormatsList() {
    return fileFormatsList;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));

    deliverableTypesList = deliverableTypeManager.getDeliverableTypes();
    deliverableStatusList = deliverableStatusManager.getDeliverableStatus();
    // get information of files format
    fileFormatsList = fileFormatManager.getFileFormats();
    // getting file format list of ids to chech in the interface all those already checked file formats.
    fileFormatIds = new int[fileFormatsList.length];
    for (int c = 0; c < fileFormatsList.length; c++) {
      fileFormatIds[c] = fileFormatsList[c].getId();
    }
    activity = activityManager.getSimpleActivity(activityID);

    // get information of deliverables that belong to the activity whit activityID
    activity.setDeliverables(deliverableManager.getDeliverables(activityID));

    // Deliverables types that need a file format specification:
    // ID = 1 - Data
    // ID = 4 - Models tools and software
    deliverableTypeIdsNeeded = new int[2];
    deliverableTypeIdsNeeded[0] = deliverableTypesList[0].getId();
    deliverableTypeIdsNeeded[1] = deliverableTypesList[3].getId();
  }

  @Override
  public String save() {
    boolean problem = false;
    boolean notExpectedDeleted = false;
    for (int c = 0; c < activity.getDeliverables().size(); c++) {
      Deliverable deliverable = activity.getDeliverables().get(c);
      // If is an expected deliverable, we must only save its status and its file formats.
      if (deliverable.isExpected()) {
        boolean statusUpdated =
          deliverableStatusManager.setDeliverableStatus(deliverable.getId(), deliverable.getStatus());
        // Any problem?
        if (!statusUpdated) {
          problem = true;
        }
        // if the deliverable type need a file format specification.
        Arrays.sort(deliverableTypeIdsNeeded);
        if (Arrays.binarySearch(deliverableTypeIdsNeeded, deliverable.getType().getId()) >= 0) {
          boolean fileFormatsUpdated =
            fileFormatManager.setFileFormats(deliverable.getId(), deliverable.getFileFormats());
          if (!fileFormatsUpdated) {
            problem = true;
          }
        }
      } else {
        // Saving here those not expected deliverables.
        // But first, we have to remove all those not expected deliverables since we don't know exactly what
        // deliverables have been changed.
        if (!notExpectedDeleted) {
          notExpectedDeleted = true;
          boolean deleted = deliverableManager.removeNotExpected(activityID);
          if (!deleted) {
            problem = true;
          }
        }
        // Second, add again all the not expected deliverables.
        boolean deliverableAdded = deliverableManager.addDeliverable(deliverable, activityID, false);
        if (!deliverableAdded) {
          problem = true;
        }
      }
    }
    if (!problem) {
      addActionMessage(getText("reporting.activityDeliverables.saved"));
      return SUCCESS;
    } else {
      addActionError(getText("reporting.activityDeliverables.problem"));
      return INPUT;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setDeliverableStatusList(DeliverableStatus[] deliverableStatusList) {
    this.deliverableStatusList = deliverableStatusList;
  }

  public void setFileFormatsList(FileFormat[] fileFormatsList) {
    this.fileFormatsList = fileFormatsList;
  }

  @Override
  public void validate() {
    Deliverable deliverable = null;
    for (int c = 0; c < activity.getDeliverables().size(); c++) {
      deliverable = activity.getDeliverables().get(c);
      if (deliverable.isExpected()) {
        // TODO

      }
    }
    super.validate();
  }

}
