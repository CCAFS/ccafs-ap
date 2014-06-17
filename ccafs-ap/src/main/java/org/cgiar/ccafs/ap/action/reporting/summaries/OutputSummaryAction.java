/*
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
 */

package org.cgiar.ccafs.ap.action.reporting.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.OutputSummary;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.ArrayList;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutputSummaryAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OutputSummaryAction.class);
  private static final long serialVersionUID = 6422944336602787958L;

  // Managers
  private OutputSummaryManager outputSummaryManager;
  private SubmissionManager submissionManager;

  // Model
  private OutputSummary[] outputSummaries;
  StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public OutputSummaryAction(APConfig config, LogframeManager logframeManager,
    OutputSummaryManager outputSummaryManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.outputSummaryManager = outputSummaryManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  public OutputSummary[] getOutputSummaries() {
    return outputSummaries;
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
    LOG.info("User {} loads the outputs for the leader {}.", getCurrentUser().getEmail(), getCurrentUser().getLeader()
      .getId());
    // Get all the summary outputs objects corresponding to the activity leader and current logframe
    outputSummaries =
      outputSummaryManager.getOutputSummaries(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean added = false;

    // Create lists of objects to make only one call to the manager
    ArrayList<OutputSummary> outputSummariesToSave = new ArrayList<>();
    ArrayList<OutputSummary> outputSummariesToUpdate = new ArrayList<>();

    for (OutputSummary outputSummary : outputSummaries) {
      // If the id of the field is equal to zero the summary is
      // not in the database and have to be inserted
      if (outputSummary.getId() == 0) {
        outputSummariesToSave.add(outputSummary);
      } else {
        // Another way, the summary is updated
        outputSummariesToUpdate.add(outputSummary);
      }
    }

    if (!outputSummariesToSave.isEmpty()) {
      added = outputSummaryManager.saveOutputSummary(outputSummariesToSave);
      if (!added) {
        addActionError(getText("reporting.outputSummary.error"));
        LOG.error("There was a problem storing the output summaries to save");
        return INPUT;
      }
    }

    if (!outputSummariesToUpdate.isEmpty()) {
      added = outputSummaryManager.updateOutputSummary(outputSummariesToUpdate);
      if (!added) {
        addActionError(getText("reporting.outputSummary.error"));
        LOG.error("There was a problem storing the output summaries to update");
        return INPUT;
      }
    }

    if (validationMessage.toString().isEmpty()) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.outputSummary.outputSummary")}));
    } else {
      // If there were validation messages show them in a warning message.
      finalMessage = getText("saving.success", new String[] {getText("planning.mainInformation")});
      finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

      addActionWarning(Capitalize.capitalizeString(finalMessage));
    }
    LOG.info("The user {} saved the outputs for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
      .getLeader().getId());
    return SUCCESS;
  }

  public void setOutputSummaries(OutputSummary[] outputSummaries) {
    this.outputSummaries = outputSummaries;
  }

  @Override
  public void validate() {
    boolean missingDescription = false;

    for (OutputSummary os : outputSummaries) {
      if (os.getDescription() == null || os.getDescription().isEmpty()) {
        missingDescription = true;
      }
    }

    if (missingDescription) {
      validationMessage.append(getText("reporting.tlOutputSummaries.validation"));
    }
  }
}
