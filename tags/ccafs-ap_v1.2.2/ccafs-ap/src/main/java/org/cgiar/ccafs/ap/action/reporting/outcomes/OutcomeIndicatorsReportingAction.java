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

package org.cgiar.ccafs.ap.action.reporting.outcomes;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeIndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomeIndicatorsReportingAction extends BaseAction {

  private static final long serialVersionUID = 8463505732461732629L;
  private static Logger LOG = LoggerFactory.getLogger(OutcomeIndicatorsReportingAction.class);

  // Managers
  private OutcomeIndicatorReportManager outcomeIndicatorReportManager;
  private ThemeManager themeManager;
  private SubmissionManager submissionManager;

  // Model
  private List<OutcomeIndicatorReport> outcomeIndicatorReports;
  private Theme[] themes;
  private int currentIndicatorsTheme;
  private boolean canSubmit;

  @Inject
  public OutcomeIndicatorsReportingAction(APConfig config, LogframeManager logframeManager,
    OutcomeIndicatorReportManager outcomeIndicatorReportManager, ThemeManager themeManager,
    SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.outcomeIndicatorReportManager = outcomeIndicatorReportManager;
    this.themeManager = themeManager;
    this.submissionManager = submissionManager;
  }

  public int getCurrentIndicatorsTheme() {
    return currentIndicatorsTheme;
  }

  public List<OutcomeIndicatorReport> getOutcomeIndicatorReports() {
    return outcomeIndicatorReports;
  }

  public Theme[] getThemes() {
    return themes;
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
    outcomeIndicatorReports =
      outcomeIndicatorReportManager.getOutcomeIndicatorReports(getCurrentReportingLogframe(), getCurrentUser()
        .getLeader());
    themes = themeManager.getThemes(getCurrentReportingLogframe());

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    boolean save =
      outcomeIndicatorReportManager.saveOutcomeIndicatorReports(outcomeIndicatorReports, getCurrentUser().getLeader(),
        getCurrentReportingLogframe());

    if (save) {
      addActionMessage(getText("saving.success", new String[] {getText("reporting.outcomeIndicators")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem", new String[] {getText("reporting.outcomeIndicators")}));
      return INPUT;
    }
  }

  public void setCurrentIndicatorsTheme(int currentIndicatorsTheme) {
    this.currentIndicatorsTheme = currentIndicatorsTheme;
  }

  public void setOutcomeIndicatorReports(List<OutcomeIndicatorReport> outcomeIndicatorReports) {
    this.outcomeIndicatorReports = outcomeIndicatorReports;
  }
}