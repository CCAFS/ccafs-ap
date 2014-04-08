package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdf.ActivityDetailsPdf;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Leader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivitiesSummariesAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivitiesSummariesAction.class);
  private static final long serialVersionUID = -4206775818815244625L;

  // Managers
  ActivityManager activityManager;
  LeaderManager leaderManager;

  // Pdf generator
  ActivityDetailsPdf activitiesPdf;

  // Model
  List<Activity> activities;
  List<Leader> leaders;
  Map<String, String> reportTypes;
  String reportTypeSelected;
  String reportOptionSelected;
  int reportYear;
  int activityID;
  int activityLeader;

  @Inject
  public ActivitiesSummariesAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    LeaderManager leaderManager, ActivityManager activityManager, ActivityDetailsPdf activitiesPdf) {
    super(config, logframeManager, securityManager);
    this.leaderManager = leaderManager;
    this.activityManager = activityManager;
    this.activitiesPdf = activitiesPdf;
  }

  /**
   * This function is in charge of create the summary with the activity
   * detail for the activity(ies) selected.
   * 
   * @param year - the year selected or 0 to indicate none.
   * @param activityID - the activity selected or 0 to indicate none.
   */
  public void createActivityDetailSummary(int year, int activityID, int leaderID) {
    Activity[] activities = activityManager.getActivitiesForDetailedSummary(year, activityID, leaderID);

    activitiesPdf.setSummaryTitle("Activities summary");
    activitiesPdf.generatePdf(activities);
  }

  /**
   * This function is in charge of create the summary with the activity
   * status for the activity(ies) selected.
   * 
   * @param year - the year selected or 0 to indicate none.
   * @param activityID - the activity selected or 0 to indicate none.
   */
  public void createActivityStatusSummary(int year, int activityID, int leaderID) {

  }

  public int getActivityID() {
    return activityID;
  }

  public int getActivityLeader() {
    return activityLeader;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public int getContentLength() {
    return activitiesPdf.getContentLength();
  }

  public String getFileName() {
    return activitiesPdf.getFileName();
  }

  public InputStream getInputStream() {
    return activitiesPdf.getInputStream();
  }

  public List<Leader> getLeaders() {
    return leaders;
  }

  public String getReportOptionSelected() {
    return reportOptionSelected;
  }

  public Map<String, String> getReportTypeOptions() {
    return reportTypes;
  }

  public Map<String, String> getReportTypes() {
    return reportTypes;
  }

  public String getReportTypeSelected() {
    return reportTypeSelected;
  }

  public int getReportYear() {
    return reportYear;
  }

  public Map<Integer, String> getYearList() {
    Map<Integer, String> years = new HashMap<>();
    years.put(0, getText("summaries.activities.selectYear"));
    for (int c = config.getStartYear(); c <= config.getPlanningCurrentYear(); c++) {
      years.put(c, String.valueOf(c));
    }
    return years;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    leaders = new ArrayList<>();
    // Fake leader to include option ALL in the list
    Leader leader =
      new Leader(0, getText("summaries.activities.selectLeader"), getText("summaries.activities.selectLeader"));
    leaders.add(leader);
    leaders.addAll(Arrays.asList(leaderManager.getAllLeaders()));

    reportTypes = new HashMap<String, String>();

    reportTypes.put("activityStatus", getText("summaries.activities.activityStatus"));
    reportTypes.put("activityDetail", getText("summaries.activities.activityDetail"));
  }

  @Override
  public String save() {
    boolean saved = false;

    if (reportTypeSelected.equals("activityStatus")) {
      createActivityStatusSummary(reportYear, activityID, activityLeader);

      return SUCCESS;
    } else if (reportTypeSelected.equals("activityDetail")) {
      createActivityDetailSummary(reportYear, activityID, activityLeader);

      return SUCCESS;
    }

    return INPUT;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setActivityLeader(int activityLeader) {
    this.activityLeader = activityLeader;
  }

  public void setReportOptionSelected(String reportOptionSelected) {
    this.reportOptionSelected = reportOptionSelected;
  }

  public void setReportTypeSelected(String reportTypeSelected) {
    this.reportTypeSelected = reportTypeSelected;
  }

  public void setReportYear(int reportYear) {
    this.reportYear = reportYear;
  }

  @Override
  public void validate() {
    boolean problem = false;

    if (save) {
      if (reportTypeSelected == null) {
        addFieldError("reportTypeSelected", getText("validation.field.required"));
        problem = true;
      } else {
        if (reportOptionSelected == null) {
          addFieldError("reportOptions", getText("validation.field.required"));
          problem = true;
        } else {
          if (reportOptionSelected.equals("allActivitiesByYear")) {
            if (reportYear == 0) {
              addFieldError("reportYear", getText("validation.field.required"));
              problem = true;
            }
          }
          if (reportOptionSelected.equals("activityIdentifier")) {
            if (activityID == 0) {
              addFieldError("activityID", getText("validation.field.required"));
              problem = true;
            } else if (activityManager.isValidId(activityID)) {
              // The activity identifier must be valid

              if (!getCurrentUser().isAdmin()) {
                // If the user is not an admin, we must check that user is the owner of the activity
                Leader activityLeaderTemp = leaderManager.getActivityLeader(activityID);

                if (getCurrentUser().getLeader().getId() != activityLeaderTemp.getId()) {
                  addFieldError("activityID", getText("validation.field.required"));
                  addActionError(getText("summaries.activities.unauthorizedActivity",
                    new String[] {String.valueOf(activityID)}));
                  return;
                }
              } else {
                addFieldError("activityID", getText("validation.field.required"));
                addActionError(getText("summaries.activities.invalidActivity",
                  new String[] {String.valueOf(activityID)}));
                return;
              }
            }
          }
        }
      }

      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}
