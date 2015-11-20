package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverablesSubTypeByTypeAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(DeliverablesSubTypeByTypeAction.class);
  private static final long serialVersionUID = -2109295995390998773L;

  // Model
  private String deliverableTypeID;
  private List<DeliverableType> subTypes;

  // Managers
  private DeliverableTypeManager deliverableTypeManager;

  @Inject
  public DeliverablesSubTypeByTypeAction(APConfig config, DeliverableTypeManager deliverableTypeManager) {
    super(config);
    this.deliverableTypeManager = deliverableTypeManager;
  }

  @Override
  public String execute() throws Exception {
    int typeID = 0;
    try {
      typeID = Integer.parseInt(deliverableTypeID);
    } catch (NumberFormatException e) {
      LOG.error("There was an exception trying to parse the parent id = {} ", deliverableTypeID);
    }

    subTypes = deliverableTypeManager.getDeliverableTypes(typeID);

    return SUCCESS;
  }

  public List<DeliverableType> getSubTypes() {
    return subTypes;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a programID parameter
    if (this.getRequest().getParameter(APConstants.DELIVERABLE_TYPE_REQUEST_ID) == null) {
      deliverableTypeID = "";
      return;
    }


    // If there is a parameter take its values
    deliverableTypeID = StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_TYPE_REQUEST_ID));
  }
}
