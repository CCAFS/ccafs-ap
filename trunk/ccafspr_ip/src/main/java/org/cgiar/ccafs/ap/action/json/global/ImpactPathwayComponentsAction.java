package org.cgiar.ccafs.ap.action.json.global;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ImpactPathwayComponentsAction extends BaseAction {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ImpactPathwayComponentsAction.class);
  private static final long serialVersionUID = -518901501302323594L;

  // Model
  private List<IPElement> ipElements;
  private List<Map<String, String>> relations;

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
    ipElements = ipElementManager.getIPElementListForGraph();

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

}