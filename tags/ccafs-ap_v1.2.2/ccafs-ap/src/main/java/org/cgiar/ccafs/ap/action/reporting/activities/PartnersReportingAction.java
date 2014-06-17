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

package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;
import org.cgiar.ccafs.ap.data.model.Submission;
import org.cgiar.ccafs.ap.util.Capitalize;
import org.cgiar.ccafs.ap.util.EmailValidator;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersReportingAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersReportingAction.class);
  private static final long serialVersionUID = 1380416261959252826L;

  // Managers
  private ActivityManager activityManager;
  private ActivityPartnerManager activityPartnerManager;
  private PartnerManager partnerManager;
  private PartnerTypeManager partnerTypeManager;
  private SubmissionManager submissionManager;

  // Model
  private PartnerType[] partnerTypes;
  private Partner[] partners;
  private Activity activity;
  private StringBuilder validationMessage;
  private Map<Boolean, String> partnersOptions;
  private boolean canSubmit;

  private int activityID;

  @Inject
  public PartnersReportingAction(APConfig config, LogframeManager logframeManager,
    ActivityPartnerManager activityPartnerManager, ActivityManager activityManager, PartnerManager partnerManager,
    PartnerTypeManager partnerTypeManager, SubmissionManager submissionManager) {

    super(config, logframeManager);
    this.activityManager = activityManager;
    this.activityPartnerManager = activityPartnerManager;
    this.partnerManager = partnerManager;
    this.partnerTypeManager = partnerTypeManager;
    this.submissionManager = submissionManager;

    this.partnersOptions = new LinkedHashMap<>();

    validationMessage = new StringBuilder();
    partnersOptions.put(true, getText("form.options.yes"));
    partnersOptions.put(false, getText("form.options.no"));
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


  public Partner[] getPartners() {
    return partners;
  }


  public Map<Boolean, String> getPartnersOptions() {
    return partnersOptions;
  }

  public PartnerType[] getPartnerTypes() {
    return partnerTypes;
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
    LOG.info("The user {} loads the partners for the activity {}", getCurrentUser().getEmail(), activityID);
    activity = activityManager.getSimpleActivity(activityID);
    activity.setActivityPartners(activityPartnerManager.getActivityPartners(activityID));
    partnerTypes = partnerTypeManager.getPartnerTypeList();
    partners = partnerManager.getAllPartners();
    // Remove all partners so they can be added again in the save method.
    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      activity.getActivityPartners().clear();
    }

    /* --------- Checking if the user can submit ------------- */
    Submission submission =
      submissionManager.getSubmission(getCurrentUser().getLeader(), getCurrentReportingLogframe(),
        APConstants.REPORTING_SECTION);

    canSubmit = (submission == null) ? true : false;
  }

  @Override
  public String save() {

    // Remove all activity partners from the database.
    boolean removed = activityPartnerManager.removeActivityPartners(activityID);
    if (removed) {
      boolean added = activityPartnerManager.saveActivityPartners(activity.getActivityPartners(), activityID);
      if (added) {

        if (validationMessage.toString().isEmpty()) {
          addActionMessage(getText("saving.success", new String[] {getText("reporting.activityPartners.partners")}));
          LOG
            .info("The user {} save the partners of activity {} successfully", getCurrentUser().getEmail(), activityID);
        } else {
          String finalMessage =
            getText("saving.success", new String[] {getText("reporting.activityPartners.partners")});
          finalMessage += getText("saving.keepInMind", new String[] {validationMessage.toString()});
          addActionWarning(Capitalize.capitalizeString(finalMessage));
        }

        return SUCCESS;
      }
    }
    LOG.info("The user {} had a problem saving the partners of activity {}", getCurrentUser().getEmail(), activityID);
    addActionError(getText("saving.problem"));
    return INPUT;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setPartnerTypes(PartnerType[] partnerTypes) {
    this.partnerTypes = partnerTypes;
  }

  @Override
  public void validate() {
    boolean missingContactName = false, missingContactEmail = false;

    if (save) {
      if (activity.getActivityPartners() != null) {
        for (int c = 0; c < activity.getActivityPartners().size(); c++) {

          if (activity.getActivityPartners().get(c).getContactName().isEmpty()) {
            missingContactName = true;
          }

          if (!activity.getActivityPartners().get(c).getContactEmail().isEmpty()) {
            if (!activity.getActivityPartners().get(c).getContactEmail().isEmpty()) {
              if (!EmailValidator.isValidEmail(activity.getActivityPartners().get(c).getContactEmail())) {
                addFieldError("activity.activityPartners[" + c + "].contactEmail",
                  getText("validation.invalid", new String[] {getText("planning.activityPartners.contactPersonEmail")}));
              }
            }
          } else {
            missingContactEmail = true;
          }

          if (activity.getActivityPartners().get(c).getPartner() == null) {
            // If User save the option of no result for filter this element should be deleted from the list.
            activity.getActivityPartners().remove(c);
            // As we removed an element from the list and the list reorganize its indexes
            // we need check again the c position
            c--;
          }
        }
      }

      // If the user said the activity will have partner but don't select anyone
      if (activity.isHasPartners() && activity.getActivityPartners().isEmpty()) {
        validationMessage.append(getText("planning.activityPartners.atLeastOne") + ".");
      } else {
        if (missingContactEmail) {
          validationMessage.append(getText("reporting.activityPartners.contactName.validation") + ", ");
        }

        if (missingContactName) {
          validationMessage.append(getText("reporting.activityPartners.contactEmail.validation") + ", ");
        }
      }

    }
  }
}
