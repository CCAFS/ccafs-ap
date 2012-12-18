package org.cgiar.ccafs.ap.action.reporting.activities;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.PartnerType;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersReportingAction extends BaseAction {

  private static final long serialVersionUID = 1380416261959252826L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnersReportingAction.class);

  // Managers
  private PartnerManager partnerManager;
  private ActivityManager activityManager;
  private PartnerTypeManager partnerTypeManager;

  // Model
  private PartnerType[] partnerTypes;
  private Activity activity;


  private int activityID;

  @Inject
  public PartnersReportingAction(APConfig config, LogframeManager logframeManager, PartnerManager partnerManager,
    ActivityManager activityManager, PartnerTypeManager partnerTypeManager) {

    super(config, logframeManager);
    this.activityManager = activityManager;
    this.partnerManager = partnerManager;
    this.partnerTypeManager = partnerTypeManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }


  public PartnerType[] getPartnerTypes() {
    return partnerTypes;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getSimpleActivity(activityID);

    activity.setPartners(partnerManager.getPartners(activityID));
    partnerTypes = partnerTypeManager.getPartnerTypeList();
  }


  @Override
  public String save() {
    // TODO Auto-generated method stub
    System.out.println("-------------SAVING-----------");
    return SUCCESS;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setPartnerTypes(PartnerType[] partnerTypes) {
    this.partnerTypes = partnerTypes;
  }

}
