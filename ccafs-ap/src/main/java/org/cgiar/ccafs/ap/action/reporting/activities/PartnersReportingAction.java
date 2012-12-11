package org.cgiar.ccafs.ap.action.reporting.activities;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnersReportingAction extends BaseAction {

  private static final long serialVersionUID = 1380416261959252826L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(PartnersReportingAction.class);

  // Managers
  private LogframeManager logframeManager;
  private PartnerManager partnerManager;
  private ActivityManager activityManager;

  // Model
  private List<Partner> partners;

  // This list contains the deliverables types which need specify
  // a format file

  private int[] typesFileFormatNeeded;

  private Activity activity;


  private int activityID;

  @Inject
  public PartnersReportingAction(APConfig config, LogframeManager logframeManager, PartnerManager partnerManager,
    ActivityManager activityManager) {

    super(config, logframeManager);
    this.activityManager = activityManager;
    this.partnerManager = partnerManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }


  public List<Partner> getPartners() {
    return partners;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityPartnersInfo(activityID);

    partners = partnerManager.getPartners(activityID);
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


  public void setPartners(List<Partner> partners) {
    this.partners = partners;
  }

}
