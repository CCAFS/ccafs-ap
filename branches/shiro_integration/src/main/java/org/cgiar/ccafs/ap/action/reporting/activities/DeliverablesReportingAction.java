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
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.FileFormat;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.mgt.SecurityManager;
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
  private SubmissionManager submissionManager;

  // Model
  private DeliverableType[] deliverableTypesList;
  private DeliverableStatus[] deliverableStatusList;
  private FileFormat[] fileFormatsList;
  private int[] deliverableTypeIdsNeeded;
  private int[] fileFormatIds;
  private int[] deliverableTypeIdsPublications;
  private Activity activity;
  private int activityID;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public DeliverablesReportingAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    DeliverableManager deliverableManager, ActivityManager activityManager,
    DeliverableTypeManager deliverableTypeManager, DeliverableStatusManager deliverableStatusManager,
    FileFormatManager fileFormatManager, SubmissionManager submissionManager) {

    super(config, logframeManager, securityManager);
    this.deliverableManager = deliverableManager;
    this.activityManager = activityManager;
    this.deliverableStatusManager = deliverableStatusManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.fileFormatManager = fileFormatManager;
    this.submissionManager = submissionManager;
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

  public String getIntranetIndications() {
    StringBuilder path = new StringBuilder();
    path.append("<b> * </b> Intranet Home ");

    if (getCurrentUser().isCP() || getCurrentUser().isPI()) {
      path.append("<b> >> </b> Institutional Contact Points library ");
      path.append("<b> >> </b> Reviewing and reporting ");
      path.append("<b> >> </b> Center Technical Reports ");
      path.append("<b> >> </b> " + getCurrentReportingLogframe().getYear() + " ");
      path.append("<b> >> </b> " + getCurrentUser().getLeader().getAcronym() + " ");
    }

    if (getCurrentUser().isRPL()) {
      path.append("<b> >> </b> Program Management Library ");
      path.append("<b> >> </b> Reviewing and reporting ");
      path.append("<b> >> </b> Annual Reporting ");
      path.append("<b> >> </b> TL and RPL Technical reporting ");
      path.append("<b> >> </b> " + getCurrentReportingLogframe().getYear() + " ");
      path.append("<b> >> </b> RPLs ");

      switch (getCurrentUser().getLeader().getRegion().getName()) {
        case "East Africa (EA)":
          path.append("<b> >> </b> EA ");
          break;

        case "Latin America (LAM)":
          path.append("<b> >> </b> LAM ");
          break;

        case "South East Asia (SEA) ":
          path.append("<b> >> </b> SAs ");
          break;

        case "South Asia (SAs)":
          path.append("<b> >> </b> SEA ");
          break;

        case "West Africa (WA)":
          path.append("<b> >> </b> WA ");
          break;
      }
    }

    if (getCurrentUser().isTL()) {
      path.append("<b> >> </b> Program Management Library ");
      path.append("<b> >> </b> Reviewing and reporting ");
      path.append("<b> >> </b> Annual Reporting ");
      path.append("<b> >> </b> TL and RPL Technical reporting ");
      path.append("<b> >> </b> " + getCurrentReportingLogframe().getYear() + " ");
      path.append("THEMES/");

      switch (getCurrentUser().getLeader().getTheme().getCode()) {
        case "1":
          path.append("<b> >> </b> T1 ");
          break;

        case "2":
          path.append("<b> >> </b> T2 ");
          break;

        case "3":
          path.append("<b> >> </b> T3 ");
          break;

        case "4":
          path.append("<b> >> </b> T4 ");
          break;

      }
    }
    return path.toString();
  }

  public String getIntranetPath() {
    StringBuilder path = new StringBuilder();
    path.append("http://intranet.ccafs.cgiar.org/");

    if (getCurrentUser().isCP() || getCurrentUser().isPI()) {
      path.append("Institutional%20Contact%20Points%20Library/Forms/");
      path.append("AllItems.aspx?RootFolder=/Institutional%20Contact%20Points%20Library/");
      path.append("Reviewing%20and%20Reporting/Center%20Technical%20Reports/");
      path.append(getCurrentReportingLogframe().getYear() + "/");
      path.append(getCurrentUser().getLeader().getAcronym() + "/");
    }

    if (getCurrentUser().isRPL()) {
      path.append("CRP%207%20Management/Forms/");
      path.append("AllItems.aspx?RootFolder=/CRP%207%20Management/");
      path.append("Reviewing%20and%20Reporting/Annual%20Reporting/");
      path.append("TL%20and%20RPL%20Technical%20Reporting/");
      path.append(getCurrentReportingLogframe().getYear() + "/");
      path.append("RPLs/");

      switch (getCurrentUser().getLeader().getRegion().getName()) {
        case "East Africa (EA)":
          path.append("EA/");
          break;

        case "Latin America (LAM)":
          path.append("LAM/");
          break;

        case "South East Asia (SEA) ":
          path.append("SAs/");
          break;

        case "South Asia (SAs)":
          path.append("SEA/");
          break;

        case "West Africa (WA)":
          path.append("WA/");
          break;
      }
    }

    if (getCurrentUser().isTL()) {
      path.append("CRP%207%20Management/Forms/");
      path.append("AllItems.aspx?RootFolder=/CRP%207%20Management/");
      path.append("Reviewing%20and%20Reporting/Annual%20Reporting/");
      path.append("TL%20and%20RPL%20Technical%20Reporting/");
      path.append(getCurrentReportingLogframe().getYear() + "/");
      path.append("THEMES/");

      switch (getCurrentUser().getLeader().getTheme().getCode()) {
        case "1":
          path.append("T1/");
          break;

        case "2":
          path.append("T2/");
          break;

        case "3":
          path.append("T3/");
          break;

        case "4":
          path.append("T4/");
          break;

      }
    }
    return path.toString();
  }

  public List<String> getYearList() {
    List<String> years = new ArrayList<>();
    for (int c = activity.getYear(); c <= config.getEndYear(); c++) {
      years.add(String.valueOf(c));
    }
    return years;
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }


  @Override
  public String next() {
    save();
    return super.next();
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    validationMessage = new StringBuilder();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    LOG.info("There user {} is loading the deliverables information for the activity {}", getCurrentUser().getEmail(),
      String.valueOf(activityID));
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

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;

    getIntranetPath();
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
      if (validationMessage.toString().isEmpty()) {
        LOG.info("The deliverables for the activity {} was saved successfully", String.valueOf(activityID));
        addActionMessage(getText("saving.success", new String[] {getText("reporting.activityDeliverables")}));
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("reporting.activityDeliverables")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      return SUCCESS;
    } else {
      LOG.warn("There was a problem saving the deliverables for the activity {}", String.valueOf(activityID));
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
            validationMessage.append(getText("reporting.activityDeliverables.fileFormatValidate") + ", ");
          }
        }

        if (!deliverable.isExpected()) {
          if (deliverable.getDescription().isEmpty()) {
            validationMessage.append(getText("reporting.activityDeliverables.descriptionValidate") + ", ");
          }

          // Check if the deliverable year is valid. When the user set a invalid value (empty or NaN)
          // the deliverable converter set the year whit 0
          if (deliverable.getYear() == 0) {
            validationMessage.append(getText("reporting.activityDeliverables.yearInvalidValidate") + ", ");
          }

          // TODO - Check if always the application will show the activities for
          // current year only, in another way the validation of deliverable year must
          // be check

          // Check if the deliverable year is not from past years
          else if (deliverable.getYear() < getCurrentReportingLogframe().getYear()) {
            validationMessage.append(getText("reporting.activityDeliverables.smallYearValidate") + ", ");
          }

          // Check if the deliverable year is not bigger than ccafs end
          else if (deliverable.getYear() > config.getEndYear()) {
            validationMessage.append(getText("reporting.activityDeliverables.bigYearValidate") + ", ");
          }
        }
      }

    }
    super.validate();
  }

}
