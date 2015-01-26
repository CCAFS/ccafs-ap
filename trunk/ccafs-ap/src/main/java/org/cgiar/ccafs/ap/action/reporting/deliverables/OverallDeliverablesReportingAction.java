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
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableScoreManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
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
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.deliverableScoreManager = deliverableScoreManager;
    this.submissionManager = submissionManager;
  }

  public int getActivityLeaderID() {
    return getCurrentUser().getLeader().getId();
  }

  public String getDeliverableLeader(int deliverableID) {
    Leader leader = deliverableManager.getDeliverableLeader(deliverableID);
    return leader.getAcronym();
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }

  public int getDeliverableThemeLeader(int deliverableID) {
    Leader leader = deliverableManager.getDeliverableThemeLeader(deliverableID);
    return leader.getId();
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

    if (getCurrentUser().isTL()) {
      String themeCode = getCurrentUser().getLeader().getTheme().getCode();
      deliverables = deliverableManager.getDeliverablesListByTheme(Integer.parseInt(themeCode));

      for (int c = 0; c < deliverables.size(); c++) {
        deliverables.get(c).setScores(deliverableScoreManager.getDeliverableScores(deliverables.get(c).getId()));
      }
    } else {

      deliverables = deliverableManager.getDeliverablesListByLeader(getCurrentUser().getLeader().getId());
      for (int c = 0; c < deliverables.size(); c++) {
        deliverables.get(c).setScores(deliverableScoreManager.getDeliverableScores(deliverables.get(c).getId()));
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
