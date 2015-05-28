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
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal B.
 */
public class ProjectOutcomeAction extends BaseAction {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutcomeAction.class);
  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private IPProgramManager programManager;
  private IPElementManager ipElementManager;
  private ProjectManager projectManager;
  private ProjectOutcomeManager projectOutcomeManager;

  // Model
  private List<IPElement> midOutcomes;
  private List<IPProgram> projectFocusList;
  private Activity activity;

  private List<IPElement> midOutcomesSelected;
  private List<IPElement> previousOutputs;
  private List<IPIndicator> previousIndicators;

  private int currentPlanningYear;
  private int midOutcomeYear;

  private int activityID;
  private int projectID;
  private Project project;

  @Inject
  public ProjectOutcomeAction(APConfig config, IPProgramManager programManager, IPElementManager ipElementManager,
    ProjectManager projectManager, ProjectOutcomeManager projectOutcomeManager) {
    super(config);
    this.programManager = programManager;
    this.ipElementManager = ipElementManager;
    this.projectManager = projectManager;
    this.projectOutcomeManager = projectOutcomeManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public int getCurrentPlanningYear() {
    return currentPlanningYear;
  }

  public List<IPElement> getMidOutcomeOutputs(int midOutcomeID) {
    List<IPElement> outputs = new ArrayList<>();
    IPElement midOutcome = ipElementManager.getIPElement(midOutcomeID);

    if (isRegionalOutcome(midOutcome)) {
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
          midoutcome.getProgram().getAcronym() + " - " + getText("planning.activityImpactPathways.outcome2019") + ": "
            + midoutcome.getDescription();
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
            midoutcome.getProgram().getAcronym() + " - " + getText("planning.activityImpactPathways.outcome2019")
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

    IPElement placeHolder = new IPElement(-1);
    placeHolder.setDescription(getText("planning.activityImpactPathways.outcome.placeholder"));

    midOutcomes.add(placeHolder);

    IPElementType midOutcomeType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);
    for (IPProgram program : projectFocusList) {

      if (!isGlobalProject && program.isFlagshipProgram()) {
        continue;
      }

      List<IPElement> elements = ipElementManager.getIPElements(program, midOutcomeType);

      for (int i = 0; i < elements.size(); i++) {
        IPElement midOutcome = elements.get(i);
        if (isValidMidoutcome(midOutcome)) {
          midOutcome.setDescription(program.getAcronym() + " - "
            + getText("planning.activityImpactPathways.outcome2019") + " #" + (i + 1) + ": "
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
    return midOutcomeYear;
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
    String result = save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();
    currentPlanningYear = this.config.getPlanningCurrentYear();
    midOutcomeYear = this.config.getMidOutcomeYear();

    midOutcomes = new ArrayList<>();
    midOutcomesSelected = new ArrayList<>();
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);

    // Load the project outcomes
    Map<String, ProjectOutcome> projectOutcomes = new HashMap<>();
    for (int year = currentPlanningYear; year <= midOutcomeYear; year++) {
      ProjectOutcome projectOutcome = projectOutcomeManager.getProjectOutcomeByYear(projectID, year);
      if (projectOutcome == null) {
        projectOutcome = new ProjectOutcome(-1);
        projectOutcome.setYear(year);
      }

      projectOutcomes.put(String.valueOf(year), projectOutcome);
    }
    project.setOutcomes(projectOutcomes);

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
    getMidOutcomesByProjectFocuses();

    // Get all the midOutcomes selected
    getMidOutcomesByOutputs();

    // Get all the midOutcomes selected through the indicators
    getMidOutcomesByIndicators();

    removeOutcomesAlreadySelected();

    // Keep the activity outputs brought from the database
    previousOutputs = new ArrayList<>();
    previousOutputs.addAll(project.getOutputs());

    // Save the activity indicators brought from the database
    previousIndicators = new ArrayList<>();
    previousIndicators.addAll(project.getIndicators());

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
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

      // Saving Project Outcome
      for (int year = currentPlanningYear; year <= midOutcomeYear; year++) {
        success =
          success
            && projectOutcomeManager.saveProjectOutcome(projectID, project.getOutcomes().get(String.valueOf(year)));
      }

      // Delete the outputs removed
      for (IPElement output : previousOutputs) {
        if (!project.containsOutput(output)) {
          boolean deleted = projectManager.deleteProjectOutput(projectID, output.getId());
          if (!deleted) {
            success = false;
          }
        }
      }

      success = success && projectManager.saveProjectOutputs(project.getOutputs(), projectID);

      // Delete the indicators removed
      for (IPIndicator indicator : previousIndicators) {
        if (!project.getOutputs().contains(indicator)) {
          boolean deleted = projectManager.deleteIndicator(projectID, indicator.getId());
          if (!deleted) {
            success = false;
          }
        }
      }

      success = success && projectManager.saveProjectIndicators(project.getIndicators(), projectID);
      if (success) {
        addActionMessage(getText("saving.success", new String[] {getText("planning.projectOutcome.title")}));
        return SUCCESS;
      } else {
        addActionError(getText("saving.problem"));
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

  public void setCurrentPlanningYear(int currentPlanningYear) {
    this.currentPlanningYear = currentPlanningYear;
  }

  public void setMidOutcomeYear(int midOutcomeYear) {
    this.midOutcomeYear = midOutcomeYear;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

}
