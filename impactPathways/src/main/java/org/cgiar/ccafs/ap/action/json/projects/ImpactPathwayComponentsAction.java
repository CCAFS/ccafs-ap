package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.utils.APConfig;

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
  private static final long serialVersionUID = 7009052475028216564L;

  // Model
  private List<IPElement> ipElements;
  private List<Map<String, Object>> nodes;
  private List<Map<String, String>> relations;
  private int programID;

  // Managers
  private IPElementManager ipElementManager;

  @Inject
  public ImpactPathwayComponentsAction(APConfig config, IPElementManager ipElementManager) {
    super(config);
    this.ipElementManager = ipElementManager;
  }

  private void addImpactPathwayElements() {
    Map<String, Object> node = new HashMap<>();
    Map<String, String> contributesTo;
    Map<String, String> translatedOf;
    Map<String, String> program;
    Map<String, String> type;

    for (IPElement element : ipElements) {
      node = new HashMap<>();

      List<Map<String, String>> contributionList = new ArrayList<>();
      for (IPElement contribution : element.getContributesTo()) {
        contributesTo = new HashMap<>();
        contributesTo.put("description", contribution.getDescription());
        contributesTo.put("id", contribution.getId() + "");

        contributionList.add(contributesTo);
      }
      node.put("contributesTo", contributionList);

      node.put("description", element.getDescription());
      node.put("id", element.getId());

      program = new HashMap<>();
      program.put("id", element.getProgram().getId() + "");
      program.put("acronym", element.getProgram().getAcronym());
      program.put("composedName", element.getProgram().getComposedName());
      node.put("program", program);

      List<Map<String, String>> translationList = new ArrayList<>();
      for (IPElement translation : element.getTranslatedOf()) {
        translatedOf = new HashMap<>();
        translatedOf.put("id", translation.getId() + "");
        translatedOf.put("name", translation.getDescription());
        translationList.add(translatedOf);
      }
      node.put("translatedOf", translationList);

      type = new HashMap<>();
      type.put("id", element.getType().getId() + "");
      type.put("name", element.getType().getName());
      node.put("type", type);

    }

    nodes.add(node);
  }

  private void addProjectsAndActivities() {

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

  public List<Map<String, Object>> getNodes() {
    addImpactPathwayElements();
    return nodes;
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