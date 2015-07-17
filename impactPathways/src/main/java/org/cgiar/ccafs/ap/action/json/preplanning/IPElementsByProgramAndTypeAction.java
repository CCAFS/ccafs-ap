package org.cgiar.ccafs.ap.action.json.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.List;

import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.utils.APConfig;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPElementsByProgramAndTypeAction extends BaseAction {

  private static final long serialVersionUID = -6424773640004100983L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(IPElementsByProgramAndTypeAction.class);

  // Model
  private String programID;
  private String ipElementTypeID;
  private List<IPElement> ipElements;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public IPElementsByProgramAndTypeAction(APConfig config, IPElementManager ipElementManager) {
    super(config);
    this.ipElementManager = ipElementManager;
  }

  @Override
  public String execute() throws Exception {
    // Program object
    IPProgram program = new IPProgram();
    program.setId(Integer.parseInt(programID));

    // IP ELement type object
    IPElementType type = new IPElementType();
    type.setId(Integer.parseInt(ipElementTypeID));

    ipElements = ipElementManager.getIPElements(program, type);

    return SUCCESS;
  }

  public List getIPElementsList() {
    return ipElements;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a programID parameter
    if (this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID) == null) {
      programID = "";
      return;
    }

    // Verify if there is a ipElmentTypeID parameter
    if (this.getRequest().getParameter(APConstants.IP_ELEMENT_TYPE_REQUEST_ID) == null) {
      ipElementTypeID = "";
      return;
    }

    // If there is a parameter take its values
    programID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID));
    ipElementTypeID = StringUtils.trim(this.getRequest().getParameter(APConstants.IP_ELEMENT_TYPE_REQUEST_ID));
  }

}
