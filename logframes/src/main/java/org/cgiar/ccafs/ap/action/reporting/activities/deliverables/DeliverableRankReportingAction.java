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
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableScoreManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTrafficLightManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableRankReportingAction extends BaseAction {

  private static final long serialVersionUID = -8287300517736865700L;

  // Managers
  private DeliverableScoreManager deliverableScoreManager;
  private DeliverableManager deliverableManager;
  private DeliverableTrafficLightManager trafficLightManager;
  private SubmissionManager submissionManager;

  // Model
  private Deliverable deliverable;
  private int deliverableID;
  private int activityID;
  private boolean canSubmit;
  private Map<Boolean, String> yesNoRadio;
  private StringBuilder validationMessage;

  @Inject
  public DeliverableRankReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableScoreManager deliverableScoreManager, DeliverableManager deliverableManager,
    DeliverableTrafficLightManager trafficLightManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.deliverableScoreManager = deliverableScoreManager;
    this.deliverableManager = deliverableManager;
    this.trafficLightManager = trafficLightManager;
    this.submissionManager = submissionManager;
  }

  public int getActivityID() {
    return activityID;
  }

  public int getActivityLeaderID() {
    return getCurrentUser().getLeader().getId();
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }

  public int getDeliverableID() {
    return deliverableID;
  }

  public Map<Boolean, String> getYesNoRadio() {
    return yesNoRadio;
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
    deliverableID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));

    deliverable = deliverableManager.getDeliverable(deliverableID);
    deliverable.setTrafficLight(trafficLightManager.getDeliverableTrafficLight(deliverableID));
    deliverable.setScores(deliverableScoreManager.getDeliverableScores(deliverableID));

    // Create options for the yes/no radio buttons
    yesNoRadio = new LinkedHashMap<>();
    yesNoRadio.put(true, getText("reporting.activityDeliverables.yes"));
    yesNoRadio.put(false, getText("reporting.activityDeliverables.no"));

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean success = true;
    int activityLeaderID = getCurrentUser().getLeader().getId();

    // Save the traffic light questions
    trafficLightManager.saveDeliverableTrafficLight(deliverable.getTrafficLight(), deliverable.getId());

    if (deliverable.getScores() != null && !deliverable.getScores().isEmpty()) {
      double score = deliverable.getScores().get(getCurrentUser().getLeader().getId());
      deliverableScoreManager.saveDeliverableScore(deliverable.getId(), activityLeaderID, score);
    }

    if (success) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.activityDeliverables.ranking")}));
      } else {
        String finalMessage =
          getText("saving.success", new String[] {getText("reporting.activityDeliverables.ranking")});
        finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }

  public void setYesNoRadio(Map<Boolean, String> yesNoRadio) {
    this.yesNoRadio = yesNoRadio;
  }

  @Override
  public void validate() {
    validationMessage = new StringBuilder();
  }
}
