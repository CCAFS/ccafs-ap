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

package org.cgiar.ccafs.ap.action.reporting.deliverables;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableScoreManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.List;
import java.util.Map.Entry;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class OverallDeliverablesReportingAction extends BaseAction {

  // Managers
  private ActivityManager activityManager;
  private DeliverableManager deliverableManager;
  private DeliverableScoreManager deliverableScoreManager;
  private SubmissionManager submissionManager;

  // Model
  private List<Deliverable> deliverables;
  private boolean canSubmit;
  private StringBuilder validationMessage;

  private static final long serialVersionUID = 2492835373748627301L;

  @Inject
  public OverallDeliverablesReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, DeliverableScoreManager deliverableScoreManager,
    ActivityManager activityManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.deliverableScoreManager = deliverableScoreManager;
    this.submissionManager = submissionManager;
    this.activityManager = activityManager;
  }

  public int getActivityLeaderID() {
    return getCurrentUser().getLeader().getId();
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public int getDeliverableActivity(int deliverableID) {
    Activity activity = activityManager.getActivityByDeliverable(deliverableID);
    return activity.getId();
  }

  public String getDeliverableLeader(int deliverableID) {
    Leader leader = deliverableManager.getDeliverableLeader(deliverableID);
    return leader.getAcronym();
  }

  public String getDeliverableRequestParameter() {
    return APConstants.DELIVERABLE_REQUEST_ID;
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }

  public int getDeliverableThemeLeader(int deliverableID) {
    if (getCurrentUser().isTL()) {
      return getCurrentUser().getLeader().getId();
    } else {
      Leader leader = deliverableManager.getDeliverableThemeLeader(deliverableID);
      return leader.getId();
    }
  }

  private boolean hasValidType(Deliverable deliverable) {
    int typeID = deliverable.getType().getParent().getId();
    return (typeID == APConstants.DELIVERABLE_TYPE_DATA || typeID == APConstants.DELIVERABLE_TYPE_TOOLS);
  }

  private boolean hasValidYear(Deliverable deliverable) {
    int year = deliverable.getYear();
    return (year >= 2012 && year <= 2014);
  }

  public boolean isCanSubmit() {
    return canSubmit;
  }

  private boolean isComplete(Deliverable deliverable) {
    return deliverable.getStatus().getName().equals("Complete");
  }

  @Override
  public String next() {
    save();
    return super.next();
  }

  @Override
  public void prepare() throws Exception {

    if (getCurrentUser().isTL()) {
      String themeCode = getCurrentUser().getLeader().getTheme().getCode();
      deliverables = deliverableManager.getDeliverablesListByTheme(Integer.parseInt(themeCode));

      for (int c = 0; c < deliverables.size(); c++) {
        if (hasValidType(deliverables.get(c)) && hasValidYear(deliverables.get(c)) && isComplete(deliverables.get(c))) {
          deliverables.get(c).setScores(deliverableScoreManager.getDeliverableScores(deliverables.get(c).getId()));
        } else {
          deliverables.remove(c);
          c--;
        }
      }
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean success = true;
    System.out.println(deliverables);

    for (Deliverable deliverable : deliverables) {
      if (!deliverable.getScores().isEmpty()) {
        for (Entry<Integer, Double> score : deliverable.getScores().entrySet()) {
          int leaderID = score.getKey();
          double scoreValue = score.getValue();

          if (getCurrentUser().getLeader().getId() == leaderID) {
            deliverableScoreManager.saveDeliverableScore(deliverable.getId(), leaderID, scoreValue);
          }
        }
      }
    }

    if (success) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.overallDeliverables")}));
      } else {
        String finalMessage = getText("saving.success", new String[] {getText("reporting.overallDeliverables")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setDeliverables(List<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  @Override
  public void validate() {
    validationMessage = new StringBuilder();
  }
}
