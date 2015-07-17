package org.cgiar.ccafs.ap.action.json.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPIndicatorsByParentAction extends BaseAction {

  private static final long serialVersionUID = 1120428709995993003L;
  private Logger LOG = LoggerFactory.getLogger(IPIndicatorsByParentAction.class);

  // Managers
  private IPIndicatorManager indicatorManager;

  // Model
  int indicatorID;
  List<IPIndicator> indicators;

  @Inject
  public IPIndicatorsByParentAction(APConfig config, IPIndicatorManager indicatorManager) {
    super(config);
    this.indicatorManager = indicatorManager;
  }

  @Override
  public String execute() throws Exception {
    indicators = new ArrayList<>();

    if (indicatorID == -1) {
      return SUCCESS;
    }

    IPIndicator indicator = new IPIndicator(indicatorID);
    indicators = indicatorManager.getIndicatorsByParent(indicator);

    return SUCCESS;
  }

  public List<IPIndicator> getIndicators() {
    return indicators;
  }

  @Override
  public void prepare() throws Exception {
    String _indicatorID = "";

    // Verify if there is a indicatorID parameter
    _indicatorID = this.getRequest().getParameter(APConstants.INDICATOR_ID);
    if (_indicatorID == null) {
      indicatorID = -1;
      return;
    }

    try {
      indicatorID = Integer.parseInt(_indicatorID);
    } catch (NumberFormatException e) {
      LOG.warn("Exception rised trying to parse the indicator id {}.", _indicatorID);
      indicatorID = -1;
    }
  }
}
