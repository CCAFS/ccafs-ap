package org.cgiar.ccafs.ap.action.reporting.activities;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableStatusManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.FileFormatManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.FileFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverablesReportingAction extends BaseAction {

  private static final long serialVersionUID = -5156289630539056822L;

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(DeliverablesReportingAction.class);

  // Managers
  private DeliverableManager deliverableManager;
  private DeliverableStatusManager deliverableStatusManager;
  private DeliverableTypeManager deliverableTypeManager;
  private FileFormatManager fileFormatManager;
  private ActivityManager activityManager;

  // Model
  private List<Deliverable> deliverables;
  private DeliverableType[] deliverableTypesList;
  private DeliverableStatus[] deliverableStatusList;
  private FileFormat[] fileFormatsList;

  // This list contains the deliverables types which need specify
  // a format file

  private int[] typesFileFormatNeeded;

  private Activity activity;


  private int activityID;

  @Inject
  public DeliverablesReportingAction(APConfig config, LogframeManager logframeManager,
    DeliverableManager deliverableManager, ActivityManager activityManager,
    DeliverableTypeManager deliverableTypeManager, DeliverableStatusManager deliverableStatusManager,
    FileFormatManager fileFormatManager) {

    super(config, logframeManager);
    this.deliverableManager = deliverableManager;
    this.activityManager = activityManager;
    this.deliverableStatusManager = deliverableStatusManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.fileFormatManager = fileFormatManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public String getActivityRequestParameter() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }

  public DeliverableStatus[] getDeliverableStatusList() {
    return deliverableStatusList;
  }


  public DeliverableType[] getDeliverableTypesList() {
    return deliverableTypesList;
  }


  public FileFormat[] getFileFormatsList() {
    return fileFormatsList;
  }

  public int[] getTypesFileFormatNeeded() {
    return typesFileFormatNeeded;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    // get information of deliverables that belong to the activity whit activityID
    deliverables = deliverableManager.getDeliverables(activityID);
    deliverableTypesList = deliverableTypeManager.getDeliverableTypes();
    deliverableStatusList = deliverableStatusManager.getDeliverableStatus();
    // get information of files format
    fileFormatsList = fileFormatManager.getFileFormats();
    activity = activityManager.getActivityDeliverableInfo(activityID);

    // The deliverables types that need a file format specification are:
    // 1 - Data
    // 4 - Models tools and software

    typesFileFormatNeeded = new int[2];
    typesFileFormatNeeded[0] = deliverableTypesList[0].getId();
    typesFileFormatNeeded[1] = deliverableTypesList[3].getId();
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

  public void setDeliverables(List<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setDeliverableStatusList(DeliverableStatus[] deliverableStatusList) {
    this.deliverableStatusList = deliverableStatusList;
  }

  public void setFileFormatsList(FileFormat[] fileFormatsList) {
    this.fileFormatsList = fileFormatsList;
  }


}
