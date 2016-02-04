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
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.CRPManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.CRPContribution;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjecteOtherContributions;
import org.cgiar.ccafs.ap.validation.projects.ProjectIPOtherContributionValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hernán David Carvajal B.
 * @author Christian David García - CIAT/CCAFS
 */
public class ProjectIPOtherContributionAction extends BaseAction {

  // LOG
  private static final long serialVersionUID = 5866456304533553208L;

  private List<CRPContribution> crpContributions;
  // private Map<String, String> regions;
  // private Map<String, String> flagships;
  private Map<String, String> otherIndicators;
  private Map<String, String> regions;

  private Map<String, String> flagships;


  private CRPManager crpManager;
  private IPIndicatorManager ipIndicatorManager;
  private List<CRP> crps;


  private HistoryManager historyManager;


  private IPProgramManager ipProgramManager;
  // Manager
  private ProjectOtherContributionManager ipOtherContributionManager;


  // Validator
  private ProjectIPOtherContributionValidator otherContributionValidator;


  // Model for the back-end
  private List<CRPContribution> previousCRPContributions;
  private Project project;

  // Model for the front-end
  private int projectID;


  private ProjectManager projectManager;


  @Inject
  public ProjectIPOtherContributionAction(APConfig config, ProjectOtherContributionManager ipOtherContributionManager,
    ProjectManager projectManager, CRPManager crpManager,
    ProjectIPOtherContributionValidator otherContributionValidator, HistoryManager historyManager,
    IPProgramManager ipProgramManager, IPIndicatorManager ipIndicatorManager) {
    super(config);
    this.ipOtherContributionManager = ipOtherContributionManager;
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.crpManager = crpManager;
    this.otherContributionValidator = otherContributionValidator;
    this.historyManager = historyManager;
    this.ipIndicatorManager = ipIndicatorManager;
  }

  public List<CRPContribution> getCrpContributions() {
    return crpContributions;
  }

  public List<CRP> getCrps() {
    return crps;
  }

  public Map<String, String> getFlagships() {
    return flagships;
  }

  public Map<String, String> getOtherIndicators() {
    return otherIndicators;
  }

  public List<CRPContribution> getPreviousCRPContributions() {
    return previousCRPContributions;
  }

  public Project getProject() {
    return project;
  }


  public int getProjectID() {
    return projectID;
  }

  public Map<String, String> getRegions() {
    return regions;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
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

    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Getting the project information
    project = projectManager.getProject(projectID);
    crps = crpManager.getCRPsList();

    // Getting the information for the IP Other Contribution
    project.setIpOtherContribution(ipOtherContributionManager.getIPOtherContributionByProjectId(projectID));
    List<ProjecteOtherContributions> others = ipOtherContributionManager.getOtherContributionsByProjectId(projectID);
    project.setOtherContributions(others);
    if (project.getIpOtherContribution() == null) {

      project.setIpOtherContribution(new OtherContribution());
    }

    // Getting the previous contributions.
    previousCRPContributions = new ArrayList<>();
    previousCRPContributions.addAll(project.getIpOtherContribution().getCrpContributions());

    this.regions = new HashMap<String, String>();
    // Getting the information of the Regions program for the View
    List<IPProgram> regions = ipProgramManager.getProgramsByType(APConstants.REGION_PROGRAM_TYPE);
    for (IPProgram ipProgram : regions) {
      this.regions.put(String.valueOf(ipProgram.getId()), ipProgram.getComposedName());

    }
    this.flagships = new HashMap<String, String>();
    // Getting the information of the Flagships program for the View
    List<IPProgram> flagships = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);
    for (IPProgram ipProgram : flagships) {
      this.flagships.put(String.valueOf(ipProgram.getId()), ipProgram.getComposedName());

    }
    this.otherIndicators = new HashMap<>();
    if (project.getOtherContributions() != null) {
      for (ProjecteOtherContributions otherContributions : project.getOtherContributions()) {
        int flagship = 0;
        int region = 0;

        try {
          flagship = Integer.parseInt(otherContributions.getFlagship());
          region = Integer.parseInt(otherContributions.getRegion());
        } catch (Exception e) {
          flagship = 0;
          region = 0;
        }

        if (flagship != 0 && region != 0) {
          List<IPIndicator> otherIndicators =
            ipIndicatorManager.getIndicatorsOtherContribution(projectID, flagship, region);
          for (IPIndicator ipIndicator : otherIndicators) {
            this.otherIndicators.put(String.valueOf(ipIndicator.getId()), ipIndicator.getDescription());
          }
        }

      }
    }

    // Getting the Project lessons for this section.
    int evaluatingYear = 0;
    if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
      evaluatingYear = this.getCurrentReportingYear();
    } else {
      evaluatingYear = this.getCurrentPlanningYear();
    }
    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), evaluatingYear, this.getCycleName()));
    if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
      this.setProjectLessonsPreview(lessonManager.getProjectComponentLesson(projectID, this.getActionName(),
        this.getCurrentReportingYear(), APConstants.PLANNING_SECTION));
    }


    super.setHistory(historyManager.getProjectIPOtherContributionHistory(project.getId()));

    if (this.isHttpPost()) {
      project.getIpOtherContribution().getCrpContributions().clear();
    }

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
  }

  @Override
  public String save() {
    if (this.hasProjectPermission("update", projectID)) {

      if (!this.isNewProject()) {
        super.saveProjectLessons(projectID);
      }

      // Saving Activity IP Other Contribution
      ipOtherContributionManager.saveIPOtherContribution(projectID, project.getIpOtherContribution(),
        this.getCurrentUser(), this.getJustification());
      ipOtherContributionManager.saveOtherContributionsList(projectID, project.getOtherContributions(),
        this.getCurrentUser(), this.getJustification());
      // Delete the CRPs that were un-selected
      for (CRPContribution crp : previousCRPContributions) {
        if (!project.getIpOtherContribution().getCrpContributions().contains(crp)) {
          crpManager.removeCrpContribution(project.getId(), crp.getCrp(), this.getCurrentUser().getId(),
            this.getJustification());
        }
      }

      crpManager.saveCrpContributions(project.getId(), project.getIpOtherContribution().getCrpContributions(),
        this.getCurrentUser(), this.getJustification());


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
    }
    return BaseAction.NOT_AUTHORIZED;
  }

  public void setCrpContributions(List<CRPContribution> crpContributions) {
    this.crpContributions = crpContributions;
  }

  public void setFlagships(Map<String, String> flagships) {
    this.flagships = flagships;
  }

  public void setOtherIndicators(Map<String, String> otherIndicators) {
    this.otherIndicators = otherIndicators;
  }


  public void setPreviousCRPContributions(ArrayList<CRPContribution> previousCRPContributions) {
    this.previousCRPContributions = previousCRPContributions;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setRegions(Map<String, String> regions) {
    this.regions = regions;
  }


  @Override
  public void validate() {
    if (save) {
      otherContributionValidator.validate(this, project, this.getCycleName());
    }
  }
}