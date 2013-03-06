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
import java.util.Iterator;

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
  private int[] deliverableTypeIdsPublications;
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


  public int[] getDeliverableTypeIdsPublications() {
    return deliverableTypeIdsPublications;
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

    // Deliverables types that need to be reported in the publications section:
    // ID = 5
    deliverableTypeIdsPublications = new int[] {5};

    // Remove all expected deliverables in case user clicked on submit button
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      if (activity.getDeliverables() != null) {
        Iterator<Deliverable> iter = activity.getDeliverables().iterator();
        while (iter.hasNext()) {
          if (!iter.next().isExpected()) {
            iter.remove();
          }
        }
      }
    }
  }

  @Override
  public String save() {
    boolean problem = false;

    // Remove all those not expected deliverables since we don't know exactly what
    // deliverables have been changed.
    boolean deleted = deliverableManager.removeNotExpected(activityID);
    if (!deleted) {
      problem = true;
    } else {
      if (activity.getDeliverables() != null) {
        for (int c = 0; c < activity.getDeliverables().size(); c++) {
          Deliverable deliverable = activity.getDeliverables().get(c);
          boolean deliverableAdded = deliverableManager.addDeliverable(deliverable, activityID);
          // if the deliverable type need a file format specification.
          Arrays.sort(deliverableTypeIdsNeeded);
          if (Arrays.binarySearch(deliverableTypeIdsNeeded, deliverable.getType().getId()) >= 0) {
            // If it is a saved deliverable set the file formats
            if (deliverable.getId() != -1) {
              boolean fileFormatsUpdated =
                fileFormatManager.setFileFormats(deliverable.getId(), deliverable.getFileFormats());
              if (!fileFormatsUpdated) {
                problem = true;
              }
            }
          }
          if (!deliverableAdded) {
            problem = true;
          }
        }
      }
    }

    if (!problem) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.activityDeliverables")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
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
    boolean fileFormatNeeded;
    boolean anyError = false;

    // Check if the user is saving and if exists any deliverable to validate
    if (save && activity.getDeliverables() != null) {
      for (int c = 0; c < activity.getDeliverables().size(); c++) {
        deliverable = activity.getDeliverables().get(c);
        fileFormatNeeded = false;

        // Check if the deliverable type selected needs a file format
        for (int deliverableTypeId : deliverableTypeIdsNeeded) {
          if (deliverable.getType().getId() == deliverableTypeId) {
            fileFormatNeeded = true;
            break;
          }
        }

        // If the deliverable needs a file format check if the user select at least one
        if (fileFormatNeeded) {
          if (deliverable.getFileFormats().isEmpty()) {
            anyError = true;
            addFieldError("activity.deliverables[" + c + "].fileFormats",
              getText("reporting.activityDeliverables.fileFormatValidate"));
          }
        }

        if (!deliverable.isExpected()) {
          if (deliverable.getDescription().isEmpty()) {
            anyError = true;
            addFieldError("activity.deliverables[" + c + "].description",
              getText("reporting.activityDeliverables.descriptionValidate"));
          }

          // Check if the deliverable year is valid. When the user set a invalid value (empty or NaN)
          // the deliverable converter set the year whit 0
          if (deliverable.getYear() == 0) {
            anyError = true;
            addFieldError("activity.deliverables[" + c + "].year",
              getText("reporting.activityDeliverables.yearInvalidValidate"));
          }

          // TODO - Check if always the application will show the activities for
          // current year only, in another way the validation of deliverable year must
          // be check

          // Check if the deliverable year is not from past years
          else if (deliverable.getYear() < getCurrentLogframe().getYear()) {
            anyError = true;
            addFieldError("activity.deliverables[" + c + "].year",
              getText("reporting.activityDeliverables.smallYearValidate") + getCurrentLogframe().getYear());
          }

          // Check if the deliverable year is not bigger than ccafs end
          else if (deliverable.getYear() > config.getEndYear()) {
            anyError = true;
            addFieldError("activity.deliverables[" + c + "].year",
              getText("reporting.activityDeliverables.bigYearValidate") + config.getEndYear());
          }
        }
      }

      if (anyError) {
        addActionError(getText("saving.fields.required"));
      }
    }
    super.validate();
  }

}
