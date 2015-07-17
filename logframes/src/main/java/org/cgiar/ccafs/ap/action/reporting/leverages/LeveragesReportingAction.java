package org.cgiar.ccafs.ap.action.reporting.leverages;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.LeverageManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.data.model.Theme;
import org.cgiar.ccafs.ap.util.Capitalize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LeveragesReportingAction extends BaseAction {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(LeveragesReportingAction.class);
  private static final long serialVersionUID = 5600071396191142596L;

  // Manager
  private LeverageManager leverageManager;
  private ThemeManager themeManager;
  private SubmissionManager submissionManager;

  // Model
  private List<Leverage> leverages;
  private Map<Integer, String> themeList;
  private StringBuilder validationMessage;
  private boolean canSubmit;


  @Inject
  public LeveragesReportingAction(APConfig config, LogframeManager logframeManager, LeverageManager leverageManager,
    ThemeManager themeManager, SubmissionManager submissionManager) {
    super(config, logframeManager);
    this.leverageManager = leverageManager;
    this.themeManager = themeManager;
    this.submissionManager = submissionManager;

    validationMessage = new StringBuilder();
  }

  public List<Leverage> getLeverages() {
    return leverages;
  }

  public Map<Integer, String> getThemeList() {
    return themeList;
  }

  public List<String> getYearList() {
    List<String> years = new ArrayList<>();
    for (int c = config.getStartYear(); c <= config.getEndYear(); c++) {
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
    leverages = leverageManager.getLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    Theme[] themes = themeManager.getThemes(this.getCurrentReportingLogframe());
    themeList = new HashMap<>();
    for (Theme theme : themes) {
      themeList.put(theme.getId(), getText("reporting.publications.Theme") + " " + theme.getCode());
    }

    // Remove all indicators so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      LOG.debug("The leverages for leader {} have been deleted from the model to save them later", getCurrentUser()
        .getLeader().getId());
      leverages.clear();
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {
    String finalMessage;
    boolean saved = false;

    // First, remove all the leverages
    if (leverageManager.removeLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe())) {
      saved = leverageManager.saveLeverages(leverages, getCurrentUser().getLeader());
    } else {
      LOG.warn("There was an error removing the leverages from the database.");
    }

    if (saved) {
      if (validationMessage.toString().isEmpty()) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.leverages")}));
      } else {
        // If there were validation messages show them in a warning message.
        finalMessage = getText("saving.success", new String[] {getText("reporting.leverages")});
        finalMessage += getText("saving.missingFields", new String[] {validationMessage.toString()});

        addActionWarning(Capitalize.capitalizeString(finalMessage));
      }
      LOG.info("The user {} saved the leverages for the leader {}.", getCurrentUser().getEmail(), getCurrentUser()
        .getLeader().getId());
      return SUCCESS;
    } else {
      LOG.warn("There was an error saving the leverages for the leader {}", getCurrentUser().getLeader());
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setLeverages(List<Leverage> leverages) {
    this.leverages = leverages;
  }

  @Override
  public void validate() {
    boolean missingTitle = false, missingPartnerName = false, missingBudget = false;

    if (save) {
      for (Leverage l : leverages) {
        if (l.getTitle() == null || l.getTitle().isEmpty()) {
          missingTitle = true;
        }

        if (l.getPartnerName() == null || l.getPartnerName().isEmpty()) {
          missingPartnerName = true;
        }

        if (l.getBudget() == 0) {
          missingBudget = true;
        }
      }

      if (missingTitle) {
        validationMessage.append(getText("reporting.leverages.validation.title") + ", ");
      }

      if (missingPartnerName) {
        validationMessage.append(getText("reporting.leverages.validation.partnerName") + ", ");
      }

      if (missingBudget) {
        validationMessage.append(getText("reporting.leverages.validation.budget") + ", ");
      }
    }
  }
}