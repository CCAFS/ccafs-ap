package org.cgiar.ccafs.ap.action.json.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPElementsByParentElementAction extends BaseAction {

  private static final long serialVersionUID = -6424773640004100983L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(IPElementsByParentElementAction.class);

  // Model
  private String parentID;
  private List<IPElement> ipElements;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public IPElementsByParentElementAction(APConfig config, IPElementManager ipElementManager) {
    super(config);
    this.ipElementManager = ipElementManager;
  }

  @Override
  public String execute() throws Exception {
    // Program object
    IPElement parent = new IPElement();
    try {
      parent.setId(Integer.parseInt(parentID));
    } catch (NumberFormatException e) {
      LOG.error("There was an exception trying to parse the parent id = {} ", parentID);
    }

    ipElements = ipElementManager.getIPElementsByParent(parent, APConstants.ELEMENT_RELATION_CONTRIBUTION);

    return SUCCESS;
  }

  public List getIPElementsList() {
    return ipElements;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a programID parameter
    if (this.getRequest().getParameter(APConstants.IP_ELEMENT_REQUEST_ID) == null) {
      parentID = "";
      return;
    }


    // If there is a parameter take its values
    parentID = StringUtils.trim(this.getRequest().getParameter(APConstants.IP_ELEMENT_REQUEST_ID));
  }

}
