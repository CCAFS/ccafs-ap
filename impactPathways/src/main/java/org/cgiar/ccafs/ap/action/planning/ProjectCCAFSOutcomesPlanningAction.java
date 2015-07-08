/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectCCAFSOutcomesPlanningAction extends BaseAction {

  private static final long serialVersionUID = -8936156831650257748L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectCCAFSOutcomesPlanningAction.class);


  // Manager
  private IPProgramManager programManager;
  private IPElementManager ipElementManager;
  private ProjectManager projectManager;
  private HistoryManager historyManager;

  // Model
  private List<IPElement> midOutcomes;
  private List<IPProgram> projectFocusList;
  private Activity activity;

  private List<IPElement> midOutcomesSelected;
  private List<IPElement> previousOutputs;
  private List<IPIndicator> previousIndicators;

  private int activityID;
  private int projectID;
  private Project project;

  @Inject
  public ProjectCCAFSOutcomesPlanningAction(APConfig config, IPProgramManager programManager,
    IPElementManager ipElementManager, ProjectManager projectManager, HistoryManager historyManager) {
    super(config);
    this.programManager = programManager;
    this.ipElementManager = ipElementManager;
    this.projectManager = projectManager;
    this.historyManager = historyManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  /**
   * This method return a string with the acronym of the impact pathways to which the project can contribute.
   * The flagship programs appears only when the global region is selected.
   * 
   * @return
   */
  public String getContributingPrograms() {
    boolean isGlobalRegionSelected = projectFocusList.contains(new IPProgram(APConstants.GLOBAL_PROGRAM));
    StringBuilder programs = new StringBuilder();
    boolean hasPrevious = false;

    for (int c = 0; c < projectFocusList.size(); c++) {
      IPProgram ipProgram = projectFocusList.get(c);

      if (ipProgram.isFlagshipProgram() && !isGlobalRegionSelected) {
        continue;
      }

      if (ipProgram.getId() == APConstants.GLOBAL_PROGRAM) {
        continue;
      }

      programs.append((c > 0 && hasPrevious) ? ", " + ipProgram.getAcronym() : ipProgram.getAcronym());
      hasPrevious = true;
    }

    return programs.toString();
  }

  public int getCurrentPlanningYear() {
    return config.getPlanningCurrentYear();
  }

  public List<IPElement> getMidOutcomeOutputs(int midOutcomeID) {
    List<IPElement> outputs = new ArrayList<>();
    IPElement midOutcome = ipElementManager.getIPElement(midOutcomeID);

    if (this.isRegionalOutcome(midOutcome)) {
      List<IPElement> mogs = new ArrayList<>();
      for (IPElement fsOutcome : midOutcome.getTranslatedOf()) {
        mogs.addAll(ipElementManager.getIPElementsByParent(fsOutcome, APConstants.ELEMENT_RELATION_CONTRIBUTION));
        for (IPElement mog : mogs) {
          if (!outputs.contains(mog)) {
            outputs.add(mog);
          }
        }
      }
    } else {
      outputs = ipElementManager.getIPElementsByParent(midOutcome, APConstants.ELEMENT_RELATION_CONTRIBUTION);
    }
    return outputs;
  }

  /**
   * In order to get the indicators and the mogs associated to
   * each midOutcome, we need the midOutcome identifier and the
   * identifier of the program corresponding to the midOutcome.
   * To send both values, we are going to send a composed key in
   * the map:
   * < "midOutcome.id-midoutcome.program.id", midoutcome.description >
   * 
   * @return
   */
  public Map<String, String> getMidOutcomes() {
    Map<String, String> midOutcomesMap = new TreeMap<>();

    for (IPElement midOutcome : midOutcomes) {

      // The first value of the list is the placeHolder,
      // therefore we should be omit the key concatenation
      if (midOutcome.getId() == -1) {
        midOutcomesMap.put(String.valueOf(midOutcome.getId()), midOutcome.getDescription());
        continue;
      }

      String id = String.valueOf(midOutcome.getId()) + "-" + String.valueOf(midOutcome.getProgram().getId());
      midOutcomesMap.put(id, midOutcome.getDescription());
    }

    return midOutcomesMap;
  }

  private void getMidOutcomesByIndicators() {
    for (IPIndicator indicator : project.getIndicators()) {
      IPElement midoutcome = ipElementManager.getIPElement(indicator.getOutcome().getId());
      if (!midOutcomesSelected.contains(midoutcome)) {
        String description =
          midoutcome.getProgram().getAcronym() + " - " + this.getText("planning.activityImpactPathways.outcome2019")
            + ": " + midoutcome.getDescription();
        midoutcome.setDescription(description);

        midOutcomesSelected.add(midoutcome);
      }
    }
  }

  private void getMidOutcomesByOutputs() {
    for (IPElement output : project.getOutputs()) {
      for (IPElement parent : output.getContributesTo()) {
        IPElement midoutcome = ipElementManager.getIPElement(parent.getId());
        if (!midOutcomesSelected.contains(midoutcome)) {
          String description =
            midoutcome.getProgram().getAcronym() + " - " + this.getText("planning.activityImpactPathways.outcome2019")
              + ": " + midoutcome.getDescription();
          midoutcome.setDescription(description);

          midOutcomesSelected.add(midoutcome);
        }
      }
    }
  }

  /**
   * This method is in charge of add the outcomes 2019 which correspond with the
   * project focuses chose previously.
   */
  private void getMidOutcomesByProjectFocuses() {
    boolean isGlobalProject;
    isGlobalProject = projectFocusList.contains(new IPProgram(APConstants.GLOBAL_PROGRAM));

    IPElementType midOutcomeType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);
    for (IPProgram program : projectFocusList) {

      if (!isGlobalProject && program.isFlagshipProgram()) {
        continue;
      }

      List<IPElement> elements = ipElementManager.getIPElements(program, midOutcomeType);

      for (int i = 0; i < elements.size(); i++) {
        IPElement midOutcome = elements.get(i);
        if (this.isValidMidoutcome(midOutcome)) {
          midOutcome.setDescription(program.getAcronym() + " - "
            + this.getText("planning.activityImpactPathways.outcome2019") + " #" + (i + 1) + ": "
            + midOutcome.getDescription());
          midOutcomes.add(midOutcome);
        }

      }
    }
  }

  public List<IPElement> getMidOutcomesSelected() {
    return midOutcomesSelected;
  }

  public int getMidOutcomeYear() {
    return config.getMidOutcomeYear();
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

  public Project getProject() {
    return project;
  }

  public List<IPProgram> getProjectFocusList() {
    return projectFocusList;
  }

  public int getProjectID() {
    return projectID;
  }

  public boolean isRegionalOutcome(IPElement outcome) {
    return !outcome.getTranslatedOf().isEmpty();
  }

  /**
   * The regional midOutcomes only can be selected if they are translation of
   * an outcome that belongs to the project focuses.
   * 
   * @param midOutcome - The element to evaluate
   * @return True if the midOutcome belongs to a flagship.
   *         True if the midOutcome is regional but is translated from an
   *         outcome that belongs to some of the project focuses.
   *         False otherwise.
   */
  private boolean isValidMidoutcome(IPElement midOutcome) {
    //
    if (!midOutcome.getTranslatedOf().isEmpty()) {
      for (IPElement parentElement : midOutcome.getTranslatedOf()) {
        if (projectFocusList.contains(parentElement.getProgram())) {
          return true;
        }
      }
    } else {
      // Is a flagship midOutcome
      return true;
    }

    return false;
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    midOutcomes = new ArrayList<>();
    midOutcomesSelected = new ArrayList<>();
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);


    // Load the project impact pathway

    // Get the programs to which the project contributes
    projectFocusList = new ArrayList<>();
    projectFocusList.addAll(programManager.getProjectFocuses(projectID, APConstants.FLAGSHIP_PROGRAM_TYPE));
    projectFocusList.addAll(programManager.getProjectFocuses(projectID, APConstants.REGION_PROGRAM_TYPE));

    // Get the project outputs from database
    project.setOutputs(projectManager.getProjectOutputs(projectID));

    // Get the project indicators from database
    project.setIndicators(projectManager.getProjectIndicators(projectID));

    // Then, we have to get all the midOutcomes that belongs to the project focuses
    this.getMidOutcomesByProjectFocuses();

    // Get all the midOutcomes selected
    this.getMidOutcomesByOutputs();

    // Get all the midOutcomes selected through the indicators
    this.getMidOutcomesByIndicators();

    this.removeOutcomesAlreadySelected();

    // Keep the activity outputs brought from the database
    previousOutputs = new ArrayList<>();
    previousOutputs.addAll(project.getOutputs());

    // Save the activity indicators brought from the database
    previousIndicators = new ArrayList<>();
    previousIndicators.addAll(project.getIndicators());

    super.setHistory(historyManager.getCCAFSOutcomesHistory(projectID));

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (project.getIndicators() != null) {
        project.getIndicators().clear();
      }

      if (project.getOutputs() != null) {
        project.getOutputs().clear();
      }
    }
  }

  private void removeOutcomesAlreadySelected() {
    for (int i = 0; i < midOutcomes.size(); i++) {
      if (midOutcomesSelected.contains(midOutcomes.get(i))) {
        midOutcomes.remove(i);
        i--;
      }
    }
  }

  @Override
  public String save() {
    if (this.isSaveable()) {
      boolean success = true;

      // Delete the outputs removed
      for (IPElement output : previousOutputs) {
        if (!project.containsOutput(output)) {
          boolean deleted =
            projectManager.deleteProjectOutput(projectID, output.getId(), output.getContributesToIDs()[0], this
              .getCurrentUser().getId(), this.getJustification());
          if (!deleted) {
            success = false;
          }
        }
      }

      success =
        success
          && projectManager.saveProjectOutputs(project.getOutputs(), projectID, this.getCurrentUser(),
            this.getJustification());

      // Delete the indicators removed
      for (IPIndicator indicator : previousIndicators) {
        if (!project.getIndicators().contains(indicator)) {
          boolean deleted =
            projectManager
              .deleteIndicator(projectID, indicator.getId(), this.getCurrentUser(), this.getJustification());
          if (!deleted) {
            success = false;
          }
        }
      }

      success =
        success
          && projectManager.saveProjectIndicators(project.getIndicators(), projectID, this.getCurrentUser(),
            this.getJustification());
      if (success) {
        this.addActionMessage(this.getText("saving.success",
          new String[] {this.getText("planning.projectOutcome.title")}));
        return SUCCESS;
      } else {
        this.addActionError(this.getText("saving.problem"));
        return INPUT;
      }
    } else {
      return BaseAction.ERROR;
    }
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

}