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

package org.cgiar.ccafs.ap.action.reporting.communications;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.CommunicationManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CommunicationsReportingAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(CommunicationsReportingAction.class);

  // Manager
  private CommunicationManager communicationManager;
  private SubmissionManager submissionManager;

  // Model
  private Communication communicationReport;
  private StringBuilder validationMessage;
  private boolean canSubmit;

  @Inject
  public CommunicationsReportingAction(APConfig config, LogframeManager logframeManager,
    CommunicationManager communicationManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.communicationManager = communicationManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  public Communication getCommunicationReport() {
    return communicationReport;
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
    communicationReport =
      communicationManager.getCommunicationReport(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean saved;

    saved =
      communicationManager.saveCommunicationReport(communicationReport, getCurrentUser().getLeader(),
        getCurrentReportingLogframe());

    if (validationMessage.toString().isEmpty()) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.communications")}));
    } else {
      // If there were validation messages show them in a warning message.
      finalMessage = getText("saving.success", new String[] {getText("reporting.communications")});
      finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

      addActionWarning(Capitalize.capitalizeString(finalMessage));
    }

    return (saved) ? SUCCESS : INPUT;
  }

  public void setCommunicationReport(Communication communicationReport) {
    this.communicationReport = communicationReport;
  }

  @Override
  public void validate() {
    if (save) {
      if (communicationReport.getMediaCampaings().isEmpty() || communicationReport.getBlogs().isEmpty()
        || communicationReport.getWebsites().isEmpty() || communicationReport.getSociaMediaCampaigns().isEmpty()
        || communicationReport.getNewsletters().isEmpty() || communicationReport.getEvents().isEmpty()
        || communicationReport.getVideosMultimedia().isEmpty()
        || communicationReport.getOtherCommunications().isEmpty()) {

        validationMessage.append(getText("reporting.communications.validation"));
      }
    }
  }
}
