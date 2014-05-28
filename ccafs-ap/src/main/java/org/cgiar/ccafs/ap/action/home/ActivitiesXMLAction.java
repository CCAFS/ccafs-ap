package org.cgiar.ccafs.ap.action.home;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityKeywordManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityOtherSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivitiesXMLAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivitiesXMLAction.class);
  private static final long serialVersionUID = 4983286741588568418L;

  // Managers
  private ActivityManager activityManager;
  private LeaderManager leaderManager;
  private ContactPersonManager contactPersonManager;
  private ActivityCountryManager activityCountryManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityOtherSiteManager activityOtherSiteManager;
  private ActivityPartnerManager activityPartnerManager;
  private ActivityKeywordManager activityKeywordManager;
  private BudgetManager budgetManager;

  // Models
  private Activity[] activities;
  private int year;

  @Inject
  public ActivitiesXMLAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    LeaderManager leaderManager, ContactPersonManager contactPersonManager,
    ActivityCountryManager activityCountryManager, ActivityBenchmarkSiteManager activityBenchmarkSiteManager,
    ActivityOtherSiteManager activityOtherSiteManager, ActivityPartnerManager activityPartnerManager,
    ActivityKeywordManager activityKeywordManager, BudgetManager budgetManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.leaderManager = leaderManager;
    this.contactPersonManager = contactPersonManager;
    this.activityCountryManager = activityCountryManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityOtherSiteManager = activityOtherSiteManager;
    this.activityPartnerManager = activityPartnerManager;
    this.activityKeywordManager = activityKeywordManager;
    this.budgetManager = budgetManager;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public Activity[] getActivities() {
    return activities;
  }

  public String getLimitRequestParameter() {
    return APConstants.ACTIVITY_LIMIT_REQUEST;
  }

  public int getYear() {
    return year;
  }

  public String getYearRequestParameter() {
    return APConstants.ACTIVITY_YEAR_REQUEST;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    String yearRequested = this.getRequest().getParameter(this.getYearRequestParameter());
    String limitRequested = this.getRequest().getParameter(this.getLimitRequestParameter());
    if (yearRequested != null) {
      try {
        year = Integer.parseInt(yearRequested);
      } catch (NumberFormatException e) {
        year = 0;
      }
    } else {
      year = 0;
    }
    int limit = -1;
    if (limitRequested != null) {
      try {
        limit = Integer.parseInt(limitRequested);
      } catch (NumberFormatException e) {
        // Nothing here
      }
    }

    LOG.info("The XML file with activity list for year '{}' is being generated with limit '{}'", year, limit);

    activities = activityManager.getActivitiesForXML(year, limit);
    if (activities == null) {
      activities = new Activity[0];
    } else {
      // Set the other information to activities
      for (Activity activity : activities) {
        int activityID = activity.getId();

        // Leader
        activity.setLeader(leaderManager.getActivityLeader(activityID));
        // Contact Person
        activity.setContactPersons(contactPersonManager.getContactPersons(activityID));
        // Countries
        activity.setCountries(activityCountryManager.getActvitiyCountries(activityID));
        // Benchmark Sites
        activity.setBsLocations(activityBenchmarkSiteManager.getActivityBenchmarkSites(activityID));
        // Other sites
        activity.setOtherLocations(activityOtherSiteManager.getActivityOtherSites(activityID));
        // Partners
        activity.setActivityPartners(activityPartnerManager.getActivityPartners(activityID));
        // Keywords
        activity.setKeywords(activityKeywordManager.getKeywordList(activityID));

        activity.setBudget(budgetManager.getBudget(activityID));
      }
    }
  }


}
