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
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectLeverageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;
import org.cgiar.ccafs.ap.validation.projects.ProjectLeverageValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Christian David Garcia Oviedo- CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectLeveragesAction extends BaseAction {

  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private ProjectManager projectManager;
  private InstitutionManager institutionManager;
  private IPProgramManager ipProgramManager;
  private ProjectLeverageManager projectLeverageManager;
  private int projectID;
  private Project project;
  private Map<String, String> allInstitutions;
  private Map<String, String> ipProgramFlagships;
  private List<ProjectLeverage> leveragesPreview;
  private ProjectLeverageValidator validator;
  private HistoryManager historyManager;

  @Inject
  public ProjectLeveragesAction(APConfig config, ProjectManager projectManager, HistoryManager historyManager,
    InstitutionManager institutionManager, IPProgramManager ipProgramManager,
    ProjectLeverageManager projectLeverageManager, ProjectLeverageValidator validator) {
    super(config);
    this.projectManager = projectManager;
    this.institutionManager = institutionManager;
    this.ipProgramManager = ipProgramManager;
    this.projectLeverageManager = projectLeverageManager;
    this.validator = validator;
    this.historyManager = historyManager;
  }

  public Map<String, String> getAllInstitutions() {
    return allInstitutions;
  }

  /*
   * /**
   * This method returns a composed name with the Acronym and Name.
   * e.g. FP4: Policies and Institutions for Climate-Resilient Food Systems
   * @param ipProgramId is the program identifier.
   * @return the composed name described above.
   */
  /*
   * public String getComposedName(int ipProgramId) {
   * for (IPProgram p : ipProgramFlagships) {
   * if (p.getId() == ipProgramId) {
   * return p.getAcronym() + ": " + p.getName();
   * }
   * }
   * return null;
   * }
   */
  public Map<String, String> getIpProgramFlagships() {
    return ipProgramFlagships;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public ProjectManager getProjectManager() {
    return projectManager;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
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
    project = projectManager.getProject(projectID);

    // Getting the list of all institutions
    this.allInstitutions = new HashMap<>();
    List<Institution> allInstitutions = institutionManager.getAllInstitutions();
    for (Institution institution : allInstitutions) {
      this.allInstitutions.put(String.valueOf(institution.getId()), institution.getComposedName());
    }
    this.ipProgramFlagships = new HashMap<>();
    // Getting the information of the Flagships program for the View
    List<IPProgram> ipProgramFlagships = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);
    for (IPProgram ipProgram : ipProgramFlagships) {
      this.ipProgramFlagships.put(String.valueOf(ipProgram.getId()), ipProgram.getComposedName());
    }
    project.setLeverages(projectLeverageManager.getProjectLeverageProject(projectID));
    if (project.getLeverages() != null) {
      leveragesPreview = projectLeverageManager.getProjectLeverageProject(projectID);


      // leverage.setMyInstitution(institutionManager.getInstitution(leverage.getInstitution()));
      if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
        // Clear out the list if it has some element
        if (project.getLeverages() != null) {
          project.getLeverages().clear();
        }


      }

      super.setHistory(historyManager.getProjectLeverage(project.getId()));

    }
    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
  }


  @Override
  public String save() {

    if (this.hasProjectPermission("update", project.getId())) {
      if (leveragesPreview != null) {
        for (ProjectLeverage projectLeverage : leveragesPreview) {
          if (!project.getLeverages().contains(projectLeverage)) {
            projectLeverageManager.deleteProjectLeverage(projectLeverage.getId(), this.getCurrentUser(),
              this.getJustification());
          }
        }
      }


      for (ProjectLeverage projectLeverage : project.getLeverages()) {

        // projectLeverage.setInstitution(projectLeverage.getMyInstitution().getId());

        projectLeverage.setProjectId(projectID);
        projectLeverageManager.saveProjectLeverage(projectID, projectLeverage, this.getCurrentUser(),
          this.getJustification());
      }
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
    return NOT_AUTHORIZED;
  }


  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setProjectManager(ProjectManager projectManager) {
    this.projectManager = projectManager;
  }

  @Override
  public void validate() {
    if (save) {

      validator.validate(this, project, this.getCycleName());
    }
  }
}