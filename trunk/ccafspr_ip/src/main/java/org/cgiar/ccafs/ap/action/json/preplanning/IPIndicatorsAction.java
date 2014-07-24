package org.cgiar.ccafs.ap.action.json.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPIndicatorsAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(IPElementsAction.class);
  private static final long serialVersionUID = -420783282085859338L;

  // Model
  private String ipElementID;
  private String ipProgramID;
  private List<IPIndicator> ipIndicators;

  // Managers
  private IPIndicatorManager ipIndicatorManager;

  @Inject
  public IPIndicatorsAction(APConfig config, IPIndicatorManager ipIndicatorManager) {
    super(config);
    this.ipIndicatorManager = ipIndicatorManager;
  }

  @Override
  public String execute() throws Exception {
    int elementID = Integer.parseInt(ipElementID);
    int programID = Integer.parseInt(ipProgramID);

    if (elementID == -1 || programID == -1) {
      return NOT_FOUND;
    }

    // IPElement object
    IPElement element = new IPElement();
    element.setId(elementID);

    IPProgram program = new IPProgram();
    program.setId(programID);
    element.setProgram(program);

    ipIndicators = ipIndicatorManager.getElementIndicators(element);

    LOG.info("-- execute() > The indicators related to the ipProgramElement {} were loaded.", ipElementID);
    return SUCCESS;
  }

  public List<IPIndicator> getIPElementsList() {
    return ipIndicators;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a elementID parameter
    if (this.getRequest().getParameter(APConstants.IP_ELEMENT_REQUEST_ID) == null) {
      ipElementID = "-1";
      return;
    }

    // Verify if there is a programID parameter
    if (this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID) == null) {
      ipProgramID = "-1";
      return;
    }

    // If there is a parameter take its values
    ipElementID = StringUtils.trim(this.getRequest().getParameter(APConstants.IP_ELEMENT_REQUEST_ID));
    ipProgramID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID));
  }
}
