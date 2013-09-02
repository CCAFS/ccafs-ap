package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;
import org.cgiar.ccafs.ap.util.EmailValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
  private CountryManager countryManager;
  private PartnerTypeManager partnerTypeManager;

  // Model
  private int activityID;
  private Activity activity;
  private Partner[] partners;
  private List<PartnerType> partnerTypes;
  private List<Country> countries;

  @Inject
  public PartnersPlanningAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    ActivityPartnerManager activityPartnerManager, PartnerManager partnerManager, CountryManager countryManager,
    PartnerTypeManager partnerTypeManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.activityPartnerManager = activityPartnerManager;
    this.partnerManager = partnerManager;
    this.countryManager = countryManager;
    this.partnerTypeManager = partnerTypeManager;
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


  public List<Country> getCountries() {
    return countries;
  }


  public Partner[] getPartners() {
    return partners;
  }

  public List<PartnerType> getPartnerTypes() {
    return partnerTypes;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    LOG.info("-- prepare() > User {} load the partners for activity {} in planing section",
      getCurrentUser().getEmail(), activityID);

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    // Get the basic information about the activity
    activity = activityManager.getSimpleActivity(activityID);
    // Get activity partners
    activity.setActivityPartners(activityPartnerManager.getActivityPartners(activityID));

    // Get the list of partners
    partners = partnerManager.getAllPartners();

    // Get the list of countries
    Country co = new Country("-1", "");
    countries = new ArrayList<>();
    countries.add(co);
    countries.addAll(Arrays.asList(countryManager.getCountryList()));

    // Get the list of partner types
    PartnerType pt = new PartnerType();
    pt.setId(-1);
    pt.setName("");
    partnerTypes = new ArrayList<>();
    partnerTypes.add(pt);
    partnerTypes.addAll(Arrays.asList(partnerTypeManager.getPartnerTypeList()));

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
        LOG.info("-- save() > The user {} save the partners of activity {} successfully", getCurrentUser().getEmail(),
          activityID);
        if (save) {
          return SUCCESS;
        } else {
          return SAVE_NEXT;
        }
      }
    }
    LOG.info("-- save() > The user {} had a problem saving the partners of activity {}", getCurrentUser().getEmail(),
      activityID);
    addActionError(getText("saving.problem"));
    return INPUT;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  @Override
  public void validate() {
    boolean anyError = false;

    if (save || saveNext) {
      if (activity.getActivityPartners() != null) {
        for (int c = 0; c < activity.getActivityPartners().size(); c++) {
          if (!activity.getActivityPartners().get(c).getContactEmail().isEmpty()) {
            if (!EmailValidator.isValidEmail(activity.getActivityPartners().get(c).getContactEmail())) {
              anyError = true;
              addFieldError("activity.activityPartners[" + c + "].contactEmail",
                getText("validation.invalid", new String[] {getText("planning.activityPartners.contactPersonEmail")}));
            }
          }
          if (activity.getActivityPartners().get(c).getPartner() == null) {
            // If User save the option of no result for filter this element should be deleted from the list.
            // activity.getActivityPartners().remove(c);
            // As we removed an element from the list and the list reorganize its indexes
            // we need check again the c position
            // c--;
          }
        }
      }
    }

    if (anyError) {
      LOG.info("User {} try to save the partners for activity {} but don't fill all required fields.", this
        .getCurrentUser().getEmail(), activityID);
      addActionError(getText("saving.fields.required"));
    }

  }
}
