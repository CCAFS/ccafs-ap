package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class IPIndicatorsByProjectAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(IPIndicatorsByProjectAction.class);
  private static final long serialVersionUID = -1617732411048284931L;

  // Model
  private String projectID;
  private List<IPIndicator> ipIndicators;

  // Managers
  private IPIndicatorManager ipIndicatorManager;

  @Inject
  public IPIndicatorsByProjectAction(APConfig config, IPIndicatorManager ipIndicatorManager) {
    super(config);
    this.ipIndicatorManager = ipIndicatorManager;
  }

  @Override
  public String execute() throws Exception {
    int project_id = Integer.parseInt(projectID);

    if (project_id == -1) {
      return NOT_FOUND;
    }

    Project project = new Project(project_id);

    ipIndicators = ipIndicatorManager.getIndicatorsByProject(project);

    LOG.info("-- execute() > The indicators related to the project {} were loaded.", project_id);
    return SUCCESS;
  }

  public List<IPIndicator> getIPElementsList() {
    return ipIndicators;
  }

  @Override
  public void prepare() throws Exception {

    // Verify if there is a elementID parameter
    if (this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID) == null) {
      projectID = "-1";
      return;
    }

    // If there is a parameter take its values
    projectID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID));
  }
}
