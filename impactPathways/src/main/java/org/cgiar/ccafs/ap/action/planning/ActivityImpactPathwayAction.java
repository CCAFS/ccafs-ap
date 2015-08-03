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
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
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
 * @author Javier Andrés Galllego B.
 * @author Hernán David Carvajal B.
 */
public class ActivityImpactPathwayAction extends BaseAction {

  private static final long serialVersionUID = -5073068363009363496L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityImpactPathwayAction.class);

  // Manager
  private ActivityManager activityManager;
  private IPProgramManager programManager;
  private IPElementManager ipElementManager;
  private ProjectManager projectManager;

  // Model
  private List<IPElement> midOutcomes;
  private List<IPProgram> projectFocusList;
  private Activity activity;

  private List<IPElement> midOutcomesSelected;
  private List<IPElement> previousOutputs;
  private List<IPIndicator> previousIndicators;

  private int activityID;
  private Project project;

  @Inject
  public ActivityImpactPathwayAction(APConfig config, IPProgramManager programManager,
    IPElementManager ipElementManager, ProjectManager projectManager, ActivityManager activityManager) {
    super(config);
    this.programManager = programManager;
    this.ipElementManager = ipElementManager;
    this.projectManager = projectManager;
    this.activityManager = activityManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public List<IPElement> getMidOutcomeOutputs(int midOutcomeID) {
    IPElement midOutcome = new IPElement(midOutcomeID);
    return ipElementManager.getIPElementsByParent(midOutcome, APConstants.ELEMENT_RELATION_CONTRIBUTION);
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

  private void getMidOutcomesByProjectFocuses() {
    midOutcomes = new ArrayList<>();
    IPElement placeHolder = new IPElement(-1);
    placeHolder.setDescription(this.getText("planning.activityImpactPathways.outcome.placeholder"));
    midOutcomes.add(placeHolder);

    IPElementType midOutcomeType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);
    for (IPProgram program : projectFocusList) {

      // if (!isGlobalProject && program.isFlagshipProgram()) {
      // continue;
      // }

      List<IPElement> elements = ipElementManager.getIPElements(program, midOutcomeType);

      for (int i = 0; i < elements.size(); i++) {
        IPElement element = elements.get(i);
        element
        .setDescription(program.getAcronym() + " - " + this.getText("planning.activityImpactPathways.outcome2019")
        + " #" + (i + 1) + ": " + element.getDescription());
        midOutcomes.add(element);
      }
    }
  }

  public List<IPElement> getMidOutcomesSelected() {
    return midOutcomesSelected;
  }

  public Project getProject() {
    return project;
  }

  public List<IPProgram> getProjectFocusList() {
    return projectFocusList;
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
    boolean isGlobalProject;

    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityById(activityID);
    project = projectManager.getProjectFromActivityId(activityID);

    // First, get the project to which the activity belongs to
    Project project = projectManager.getProjectFromActivityId(activityID);

    // Get the programs to which the project contributes
    projectFocusList = new ArrayList<>();
    projectFocusList.addAll(programManager.getProjectFocuses(project.getId(), APConstants.FLAGSHIP_PROGRAM_TYPE));
    projectFocusList.addAll(programManager.getProjectFocuses(project.getId(), APConstants.REGION_PROGRAM_TYPE));

    isGlobalProject = projectFocusList.contains(new IPProgram(APConstants.GLOBAL_PROGRAM));

    // Then, we have to get all the midOutcomes that belongs to the project focuses
    this.getMidOutcomesByProjectFocuses();

    // Get activity the outputs from database
    activity.setOutputs(activityManager.getActivityOutputs(activityID));

    // Get the activity indicators from database
    activity.setIndicators(activityManager.getActivityIndicators(activityID));

    midOutcomesSelected = new ArrayList<>();

    // First check the midOutcomes selected according to the indicators
    for (int i = 0; i < midOutcomes.size(); i++) {
      IPElement midOutcome = midOutcomes.get(i);
      if (midOutcome.getIndicators() != null) {
        for (IPIndicator indicator : activity.getIndicators()) {
          if (midOutcome.getIndicators().contains(indicator.getParent())) {

            // Check if the midOutcome is not already present
            if (!midOutcomesSelected.contains(midOutcome)) {
              midOutcomesSelected.add(midOutcome);
              midOutcomes.remove(i);
              i--;
            }
          }
        }
      }
    }

    // First check the midOutcomes selected according to the outputs
    for (IPElement output : activity.getOutputs()) {
      int index = midOutcomes.indexOf(output.getContributesTo().get(0));
      if (index != -1) {
        IPElement element = midOutcomes.get(index);
        if (!midOutcomesSelected.contains(element)) {
          midOutcomesSelected.add(element);
          midOutcomes.remove(index);
        }
      }
    }

    // Save the activity outputs brought from the database
    previousOutputs = new ArrayList<>();
    previousOutputs.addAll(activity.getOutputs());

    // Save the activity indicators brought from the database
    previousIndicators = new ArrayList<>();
    previousIndicators.addAll(activity.getIndicators());

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (activity.getIndicators() != null) {
        activity.getIndicators().clear();
      }

      if (activity.getOutputs() != null) {
        activity.getOutputs().clear();
      }
    }
  }

  @Override
  public String save() {
    if (this.isSaveable()) {
      boolean success = true;

      // Delete the outputs removed
      for (IPElement output : previousOutputs) {
        if (!activity.getOutputs().contains(output)) {
          // boolean deleted = activityManager.deleteActivityOutput(activityID, output.getId());
          // if (!deleted) {
          success = false;
          // }
        }
      }

      success = success && activityManager.saveActivityOutputs(activity.getOutputs(), activityID);

      // Delete the indicators removed
      for (IPIndicator indicator : previousIndicators) {
        if (!activity.getOutputs().contains(indicator)) {
          boolean deleted = activityManager.deleteIndicator(activityID, indicator.getId());
          if (!deleted) {
            success = false;
          }
        }
      }

      success = success && activityManager.saveActivityIndicators(activity.getIndicators(), activityID);
      if (success) {
        this.addActionMessage(
          this.getText("saving.success", new String[] {this.getText("planning.activityImpactPathways.title")}));
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

}
