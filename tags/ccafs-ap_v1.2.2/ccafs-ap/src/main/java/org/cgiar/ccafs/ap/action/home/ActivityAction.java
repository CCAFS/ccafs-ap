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

package org.cgiar.ccafs.ap.action.home;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityKeywordManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.ContactPersonManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.ResourceManager;
import org.cgiar.ccafs.ap.data.model.Activity;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityAction.class);
  private static final long serialVersionUID = -7910041519418474107L;

  // Managers
  private ActivityManager activityManager;
  private DeliverableManager deliverableManager;
  private ActivityPartnerManager activityPartnerManager;
  private ContactPersonManager contactPersonManager;
  private BudgetManager budgetManager;
  private ActivityObjectiveManager activityObjectiveManager;
  private ResourceManager resourceManager;
  private ActivityKeywordManager keywordManager;

  // Model
  private Activity activity;
  int activityID;

  @Inject
  public ActivityAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    DeliverableManager deliverableManager, ActivityPartnerManager activityPartnerManager,
    ContactPersonManager contactPersonManager, BudgetManager budgetManager, ActivityKeywordManager keywordManager,
    ActivityObjectiveManager activityObjectiveManager, ResourceManager resourceManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.deliverableManager = deliverableManager;
    this.activityPartnerManager = activityPartnerManager;
    this.contactPersonManager = contactPersonManager;
    this.budgetManager = budgetManager;
    this.activityObjectiveManager = activityObjectiveManager;
    this.resourceManager = resourceManager;
    this.keywordManager = keywordManager;
  }

  @Override
  public String execute() throws Exception {
    super.execute();
    // If there is not a activityID parameter or it is invalid, return input
    if (activityID == -1 || !activityManager.isValidId(activityID)) {
      activityID = -1;
      return INPUT;
    }
    LOG.info("Activity information page for activity {} is being loaded.", activityID);

    // Get the basic information about the activity
    activity = activityManager.getActivityStatusInfo(activityID);
    // Set activity deliverables
    activity.setDeliverables(deliverableManager.getDeliverables(activityID));
    // Set activity partners
    activity.setActivityPartners(activityPartnerManager.getActivityPartners(activityID));
    // Set contact persons
    activity.setContactPersons(contactPersonManager.getContactPersons(activityID));
    // Set the budget
    activity.setBudget(budgetManager.getBudget(activityID));
    // Set the activity objectives
    activity.setObjectives(activityObjectiveManager.getActivityObjectives(activityID));
    // Set the activity resources
    activity.setResources(resourceManager.getResources(activityID));
    // Set the activity keywords
    activity.setKeywords(keywordManager.getKeywordList(activityID));
    return SUCCESS;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public String getMilestoneRequestParameter() {
    return APConstants.MILESTONE_REQUEST_ID;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Verify if there is a activityID parameter
    if (this.getRequest().getParameter(APConstants.PUBLIC_ACTIVITY_ID) == null) {
      activityID = -1;
      return;
    }

    try {
      // If there is a parameter take its values
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PUBLIC_ACTIVITY_ID)));
    } catch (NumberFormatException e) {
      // If there was an error trying to parse the URL parameter
      LOG.error("There was an error trying to parse the activityId parameter");
      // Set an invalid value to the activityId to prevent the page load in execute function
      activityID = -1;
      return;
    }
  }


  public void setActivity(Activity activity) {
    this.activity = activity;
  }
}
