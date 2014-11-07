package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MogsByOutcomeAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MogsByOutcomeAction.class);
  private static final long serialVersionUID = -1383961556879257674L;

  // Model
  private String outcomeID;
  private List<IPElement> ipElements;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public MogsByOutcomeAction(APConfig config, IPElementManager ipElementManager) {
    super(config);
    this.ipElementManager = ipElementManager;
  }

  @Override
  public String execute() throws Exception {
    ipElements = new ArrayList<>();
    IPElement parent = new IPElement();

    try {
      parent = ipElementManager.getIPElement(Integer.parseInt(outcomeID));
    } catch (NumberFormatException e) {
      LOG.error("There was an exception trying to parse the parent id = {} ", outcomeID);
    }

    if (isRegionalOutcome(parent)) {
      List<IPElement> mogs = new ArrayList<>();
      for (IPElement fsOutcome : parent.getTranslatedOf()) {
        mogs.addAll(ipElementManager.getIPElementsByParent(fsOutcome, APConstants.ELEMENT_RELATION_CONTRIBUTION));
        ipElements.addAll(mogs);
      }
    } else {
      ipElements = ipElementManager.getIPElementsByParent(parent, APConstants.ELEMENT_RELATION_CONTRIBUTION);
    }

    return SUCCESS;
  }

  public List<IPElement> getIPElementsList() {
    return ipElements;
  }

  public boolean isRegionalOutcome(IPElement outcome) {
    outcome = ipElementManager.getIPElement(outcome.getId());
    return !outcome.getTranslatedOf().isEmpty();
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a programID parameter
    if (this.getRequest().getParameter(APConstants.IP_ELEMENT_REQUEST_ID) == null) {
      outcomeID = "";
      return;
    }

    // If there is a parameter take its values
    outcomeID = StringUtils.trim(this.getRequest().getParameter(APConstants.IP_ELEMENT_REQUEST_ID));
  }

}
