package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityKeywordManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.KeywordManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.ResourceManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Keyword;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdditionalInformationPlanningAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(AdditionalInformationPlanningAction.class);
  private static final long serialVersionUID = -3696416634818796763L;

  // Managers
  private ActivityManager activityManager;
  private ActivityKeywordManager activityKeywordManager;
  private KeywordManager keywordManager;
  private ResourceManager resourceManager;

  // Model
  private int activityID;
  private Activity activity;
  private Keyword[] keywords;

  @Inject
  public AdditionalInformationPlanningAction(APConfig config, LogframeManager logframeManager,
    ActivityManager activityManager, KeywordManager keywordManager, ActivityKeywordManager activityKeywordManager,
    ResourceManager resourceManager) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.keywordManager = keywordManager;
    this.activityKeywordManager = activityKeywordManager;
    this.resourceManager = resourceManager;
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

  public Keyword[] getKeywords() {
    return keywords;
  }


  @Override
  public void prepare() throws Exception {
    LOG.info("User {} load the activity additional information for leader {} in planing section", getCurrentUser()
      .getEmail(), getCurrentUser().getLeader().getId());

    String activityStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID));
    try {
      activityID = Integer.parseInt(activityStringID);
    } catch (NumberFormatException e) {
      LOG.error("There was an error parsing the activity identifier '{}'.", activityStringID, e);
    }

    // Get keyword list
    keywords = keywordManager.getKeywordList();

    // Get the basic information about the activity
    activity = activityManager.getSimpleActivity(activityID);

    // Set the activity keywords
    activity.setKeywords(activityKeywordManager.getKeywordList(activityID));

    // Set the activity resources
    activity.setResources(resourceManager.getResources(activityID));

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      activity.getKeywords().clear();
      activity.getResources().clear();
    }
  }

  @Override
  public String save() {
    boolean deleted;
    boolean keywordsSaved = true, resourcesSaved = true;
    // First delete all the records from the database.
    deleted = activityKeywordManager.removeActivityKeywords(activityID);
    if (!deleted) {
      LOG.warn("There was a problem deleting the keywords for the activity {} from the database.", activityID);
    }

    deleted = resourceManager.removeResources(activityID);
    if (!deleted) {
      LOG.warn("There was a problem deleting the resources for the activity {} from the database.", activityID);
    }

    // After remove the records, insert the values received if there is any.
    if (activity.getKeywords().size() > 0) {
      keywordsSaved = activityKeywordManager.saveKeywordList(activity.getKeywords(), activityID);
      if (!keywordsSaved) {
        LOG.warn("There was a problem saving the keywords for the activity {} into the database.", activityID);
      }
    }

    if (activity.getResources().size() > 0) {
      resourcesSaved = resourceManager.saveResources(activity.getResources(), activityID);
      if (!resourcesSaved) {
        LOG.warn("There was a problem saving the resources for the activity {} from the database.", activityID);
      }
    }

    if (keywordsSaved && resourcesSaved) {
      addActionMessage(getText("saving.success", new String[] {getText("planning.additionalInformation")}));
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  @Override
  public void validate() {
    boolean anyError = false;

    if (save) {
      // If there is keywords
      if (activity.getKeywords() != null) {
        for (int c = 0; c < activity.getKeywords().size(); c++) {
          if (activity.getKeywords().get(c).getKeyword() == null) {
            // The other keywords can't be empty
            if (activity.getKeywords().get(c).getOther().isEmpty()) {
              addFieldError("activity.keywords[" + c + "].other", getText("validation.field.required"));
              anyError = true;
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