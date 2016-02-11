package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
  private Map<Integer, IPElement> ipElements;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public MogsByOutcomeAction(APConfig config, IPElementManager ipElementManager) {
    super(config);
    this.ipElementManager = ipElementManager;
  }

  @Override
  public String execute() throws Exception {
    ipElements = new HashMap<>();
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
        for (IPElement mog : mogs) {
          if (!ipElements.containsValue(mog)) {
            ipElements.put(getMOGIndex(mog), mog);
          }
        }
      }
    } else {
      for (IPElement mog : ipElementManager.getIPElementsByParent(parent, APConstants.ELEMENT_RELATION_CONTRIBUTION)) {
        ipElements.put(getMOGIndex(mog), mog);
      }
    }

    return SUCCESS;
  }

  public Map<Integer, IPElement> getIPElementsList() {
    return ipElements;
  }

  public int getMOGIndex(IPElement mog) {
    int index = 0;
    List<IPElement> allMOGs = ipElementManager.getIPElements(mog.getProgram(), mog.getType());

    for (int i = 0; i < allMOGs.size(); i++) {
      if (allMOGs.get(i).getId() == mog.getId()) {
        return (i + 1);
      }
    }

    return index;
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
