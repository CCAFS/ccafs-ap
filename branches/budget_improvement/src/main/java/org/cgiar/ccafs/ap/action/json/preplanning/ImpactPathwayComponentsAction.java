package org.cgiar.ccafs.ap.action.json.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImpactPathwayComponentsAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ImpactPathwayComponentsAction.class);
  private static final long serialVersionUID = -518901501302323594L;

  // Model
  private List<IPElement> ipElements;
  private List<Map<String, String>> relations;
  private int programID;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public ImpactPathwayComponentsAction(APConfig config, IPElementManager ipElementManager) {
    super(config);
    this.ipElementManager = ipElementManager;
  }

  @Override
  public String execute() throws Exception {
    relations = new ArrayList<>();

    IPProgram program = new IPProgram(programID);
    ipElements = ipElementManager.getIPElementListForGraph(program);

    for (IPElement element : ipElements) {

      // Translation relations
      for (int parentID : element.getTranslatedOfIDs()) {
        Map<String, String> relation = new HashMap<>();
        relation.put("id", String.valueOf(parentID) + "-" + String.valueOf(element.getId()));
        relation.put("parentID", String.valueOf(parentID));
        relation.put("childID", String.valueOf(element.getId()));

        relations.add(relation);
      }

      // Contribution relations
      for (int parentID : element.getContributesToIDs()) {
        Map<String, String> relation = new HashMap<>();
        relation.put("id", String.valueOf(parentID) + "-" + String.valueOf(element.getId()));
        relation.put("parentID", String.valueOf(parentID));
        relation.put("childID", String.valueOf(element.getId()));

        relations.add(relation);
      }
    }

    return SUCCESS;
  }

  public List<IPElement> getIpElements() {
    return ipElements;
  }

  public List<Map<String, String>> getRelations() {
    return relations;
  }

  @Override
  public void prepare() throws Exception {
    String _programID;

    // If there is a country ID take its values
    _programID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID));
    try {
      programID = (_programID != null) ? Integer.parseInt(_programID) : -1;
    } catch (NumberFormatException e) {
      LOG.warn("There was an exception trying to convert to int the parameter {}", _programID);
      programID = -1;
    }
  }

}