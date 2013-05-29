package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.util.EmailValidator;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersPlanningAction.class);

  // Managers
  private ActivityManager activityManager;
  private ActivityPartnerManager activityPartnerManager;
  private PartnerManager partnerManager;

  // Model
  private int activityID;
  private Activity activity;
  private Partner[] partners;

  @Inject
  public PartnersPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ActivityPartnerManager activityPartnerManager, PartnerManager partnerManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.activityPartnerManager = activityPartnerManager;
    this.partnerManager = partnerManager;
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


  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("User {} load the activity partners for leader {} in planing section", getCurrentUser().getEmail(),
      getCurrentUser().getLeader().getId());

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    // Get the basic information about the activity
    activity = activityManager.getSimpleActivity(activityID);
    // Get activity partners
    activity.setActivityPartners(activityPartnerManager.getActivityPartners(activityID));

    // Get the list of partners
    partners = partnerManager.getAllPartners();

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (activity.getActivityPartners() != null) {
        activity.getActivityPartners().clear();
      }
    }
  }

  @Override
  public String save() {
    // Remove all activity partners from the database.
    boolean removed = activityPartnerManager.removeActivityPartners(activityID);
    if (removed) {
      boolean added = activityPartnerManager.saveActivityPartners(activity.getActivityPartners(), activityID);
      if (added) {
        addActionMessage(getText("saving.success", new String[] {getText("reporting.activityPartners.partners")}));
        LOG.info("The user {} save the partners of activity {} successfully", getCurrentUser().getEmail(), activityID);
        if (save) {
          return SUCCESS;
        } else {
          return SAVE_NEXT;
        }
      }
    }
    LOG.info("The user {} had a problem saving the partners of activity {}", getCurrentUser().getEmail(), activityID);
    addActionError(getText("saving.problem"));
    return INPUT;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void validate() {
    boolean anyError = false;


    if (save) {
      if (activity.getActivityPartners() != null) {
        for (int c = 0; c < activity.getActivityPartners().size(); c++) {
          if (!activity.getActivityPartners().get(c).getContactEmail().isEmpty()) {
            if (!EmailValidator.isValidEmail(activity.getActivityPartners().get(c).getContactEmail())) {
              anyError = true;
              addFieldError("activity.activityPartners[" + c + "].contactEmail",
                getText("validation.invalid", new String[] {getText("planning.activityPartners.contactPersonEmail")}));
            }
          }
        }
      }
    }

    if (anyError) {
      addActionError(getText("saving.fields.required"));
    }

  }
}
