package org.cgiar.ccafs.ap.action.planning;

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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverablesPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(DeliverablesPlanningAction.class);
  private static final long serialVersionUID = -8353280034478989495L;

  // Managers
  private ActivityManager activityManager;
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private DeliverableStatusManager deliverableStatusManager;
  private FileFormatManager fileFormatManager;

  // Model
  private int activityID;
  private Activity activity;
  private DeliverableType[] deliverableTypesList;
  private int[] deliverableTypeIdsNeeded;
  private int[] deliverableTypeIdsPublications;
  private DeliverableStatus[] deliverableStatusList;
  private FileFormat[] fileFormatsList;

  @Inject
  public DeliverablesPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    DeliverableManager deliverableManager, DeliverableTypeManager deliverableTypeManager,
    DeliverableStatusManager deliverableStatusManager, FileFormatManager fileFormatManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.deliverableStatusManager = deliverableStatusManager;
    this.fileFormatManager = fileFormatManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
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

  public FileFormat[] getFileFormatsList() {
    return fileFormatsList;
  }

  public List<String> getYearList() {
    List<String> years = new ArrayList<>();
    for (int c = config.getPlanningCurrentYear(); c <= config.getEndYear(); c++) {
      years.add(String.valueOf(c));
    }
    return years;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }
    LOG.info("-- prepare() > User {} load the deliverables for activity {} in planing section", getCurrentUser()
      .getEmail(), activityID);

    deliverableTypesList = deliverableTypeManager.getDeliverableTypes();
    deliverableStatusList = deliverableStatusManager.getDeliverableStatus();
    fileFormatsList = fileFormatManager.getFileFormats();

    // Deliverables types that need a file format specification:
    // ID = 1 - Data
    // ID = 4 - Models tools and software
    deliverableTypeIdsNeeded = new int[2];
    deliverableTypeIdsNeeded[0] = deliverableTypesList[0].getId();
    deliverableTypeIdsNeeded[1] = deliverableTypesList[3].getId();

    // Deliverables types that need to be reported in the publications section:
    // ID = 5
    deliverableTypeIdsPublications = new int[] {5};

    // Get the basic information about the activity
    activity = activityManager.getSimpleActivity(activityID);

    // Get the deliverables related to the activity
    activity.setDeliverables(deliverableManager.getDeliverables(activityID));

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (activity.getDeliverables() != null) {
        activity.getDeliverables().clear();
      }
    }
  }

  @Override
  public String save() {
    boolean problem = false;

    // Remove all those not expected deliverables since we don't know exactly what
    // deliverables have been changed.
    boolean deleted = deliverableManager.removeExpected(activityID);
    if (!deleted) {
      LOG.warn("There was a problem deleting the expected deliverables for activity {}.", activityID);
      problem = true;
    } else {
      LOG.info("The expected deliverables for activity {} were saved successfully.", activityID);

      if (activity.getDeliverables() != null) {
        for (int c = 0; c < activity.getDeliverables().size(); c++) {
          Deliverable deliverable = activity.getDeliverables().get(c);

          // As it is planning stage, there are some defaults parameters:

          // Set the is expected attribute to true
          deliverable.setExpected(true);

          // Set the status attribute to incomplete
          // ID = 3 - Incomplete
          deliverable.setStatus(deliverableStatusList[2]);

          boolean deliverableAdded = deliverableManager.addDeliverable(deliverable, activityID);
          // if the deliverable type need a file format specification.
          Arrays.sort(deliverableTypeIdsNeeded);
          if (Arrays.binarySearch(deliverableTypeIdsNeeded, deliverable.getType().getId()) >= 0) {
            // If it is a saved deliverable set the file formats
            if (deliverable.getId() != -1) {
              boolean fileFormatsUpdated =
                fileFormatManager.setFileFormats(deliverable.getId(), deliverable.getFileFormats());
              if (!fileFormatsUpdated) {
                LOG.warn("There was a problem saving the file formats for deliverable {}.", deliverable.getId());
                problem = true;
              } else {
                LOG.info("File formats for deliverable {} was saved succesfully.", deliverable.getId());
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
      addActionMessage(getText("saving.success", new String[] {getText("planning.activityDeliverables")}));
      LOG.info("-- save() > User '{}' save the deliverables corresponding to the activity {}", this.getCurrentUser()
        .getEmail(), activityID);

      if (save) {
        return SUCCESS;
      } else {
        return SAVE_NEXT;
      }
    } else {
      LOG.warn("-- save () > User '{}' had problems to save the deliverables corresponding to the activity {}", this
        .getCurrentUser().getEmail(), activityID);
      addActionError(getText("saving.problem"));

      return INPUT;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void validate() {
    boolean anyError = false;
    boolean fileFormatNeeded = false;
    Deliverable deliverable;

    // Validate only if exists deliverables
    if (save && activity.getDeliverables() != null) {
      for (int c = 0; c < activity.getDeliverables().size(); c++) {
        deliverable = activity.getDeliverables().get(c);
        if (deliverable.getDescription().isEmpty()) {
          anyError = true;
          addFieldError("activity.deliverables[" + c + "].description", getText("validation.field.required"));
        }
      }
      if (anyError) {
        LOG.info("-- validate() > User {} try to save the deliverables for activity {} but don't fill all fields.",
          this.getCurrentUser().getEmail(), activityID);
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
