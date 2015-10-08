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
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.planning.ProjectOutputsPlanningValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectOutputsPlanningAction extends BaseAction {

  private static final long serialVersionUID = 5246876705706611499L;
  private static Logger LOG = LoggerFactory.getLogger(ProjectOutputsPlanningAction.class);

  // Managers
  private ProjectManager projectManager;
  private ProjectContributionOverviewManager overviewManager;
  private IPElementManager ipElementManager;
  private HistoryManager historyManager;

  // Model
  private Project project;
  private int projectID;
  private List<OutputOverview> previousOverviews;

  // Validator
  private ProjectOutputsPlanningValidator validator;

  @Inject
  public ProjectOutputsPlanningAction(APConfig config, ProjectManager projectManager,
    ProjectContributionOverviewManager overviewManager, IPElementManager ipElementManager,
    HistoryManager historyManager, ProjectOutputsPlanningValidator validator) {
    super(config);
    this.projectManager = projectManager;
    this.overviewManager = overviewManager;
    this.ipElementManager = ipElementManager;
    this.historyManager = historyManager;
    this.validator = validator;
  }

  @Override
  public int getCurrentPlanningYear() {
    return config.getPlanningCurrentYear();
  }

  public int getMidOutcomeYear() {
    return config.getMidOutcomeYear();
  }

  public int getMOGIndex(IPElement mog) {
    return ipElementManager.getMOGIndex(mog);
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }

  @Override
  public void prepare() throws Exception {
    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the activity information
    project = projectManager.getProject(projectID);
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));

    // Remove the outputs duplicated
    Set<IPElement> outputsTemp = new HashSet<>(project.getOutputs());
    project.getOutputs().clear();
    project.getOutputs().addAll(outputsTemp);

    // Get the project outputs from database
    project.setOutputsOverview(overviewManager.getProjectContributionOverviews(project));

    // save previous output overviews
    previousOverviews = new ArrayList<>();
    for (OutputOverview output : project.getOutputsOverview()) {
      previousOverviews.add(new OutputOverview(output.getId()));
    }

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), this.getCurrentPlanningYear()));

    this.setHistory(historyManager.getProjectOutputsHistory(projectID));
  }

  @Override
  public String save() {
    boolean success = true;
    if (securityContext.canUpdateProjectOverviewMOGs(projectID)) {

      if (!this.isNewProject()) {
        super.saveProjectLessons(projectID);
      }

      // Check if there are output overviews to delete
      for (OutputOverview overview : previousOverviews) {
        if (!project.getOutputsOverview().contains(overview)) {
          success = overviewManager.deleteProjectContributionOverview(overview.getId(), this.getCurrentUser(),
            this.getJustification());
        }
      }

      success =
        success && overviewManager.saveProjectContribution(project, this.getCurrentUser(), this.getJustification());

      if (success) {
        // Get the validation messages and append them to the save message
        Collection<String> messages = this.getActionMessages();
        if (!messages.isEmpty()) {
          String validationMessage = messages.iterator().next();
          this.setActionMessages(null);
          this.addActionWarning(this.getText("saving.saved") + validationMessage);
        } else {
          this.addActionMessage(this.getText("saving.saved"));
        }
        return SUCCESS;
      } else {
        this.addActionError(this.getText("saving.problem"));
        LOG.warn("There was a problem saving the project outputs planning.");
        return BaseAction.INPUT;
      }
    }

    return NOT_AUTHORIZED;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project, "Planning");
    }
  }
}
