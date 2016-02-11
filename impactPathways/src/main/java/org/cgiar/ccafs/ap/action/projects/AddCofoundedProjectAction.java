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
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class AddCofoundedProjectAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(AddCofoundedProjectAction.class);
  private static final long serialVersionUID = -4979730931322079960L;

  // Managers
  private ProjectManager projectManager;
  private ProjectCofinancingLinkageManager linkedCoreProjectManager;

  // Model
  private int coreProjectID;
  private int bilateralProjectID;
  private List<Project> coreProjects;
  private List<Project> bilateralProjects;

  @Inject
  public AddCofoundedProjectAction(APConfig config, ProjectManager projectManager,
    ProjectCofinancingLinkageManager linkedCoreProjectManager) {
    super(config);
    this.projectManager = projectManager;
    this.linkedCoreProjectManager = linkedCoreProjectManager;
  }

  public int getBilateralProjectID() {
    return bilateralProjectID;
  }

  public List<Project> getBilateralProjects() {
    return bilateralProjects;
  }

  public int getCoreProjectID() {
    return coreProjectID;
  }

  public List<Project> getCoreProjects() {
    return coreProjects;
  }

  @Override
  public void prepare() throws Exception {
    coreProjects = projectManager.getCoreProjects(-1, -1);
    bilateralProjects = projectManager.getBilateralProjects();
  }

  @Override
  public String save() {
    boolean success = false;

    if (!securityContext.canAddCofoundedProject()) {
      return NOT_AUTHORIZED;
    }

    if (coreProjectID == -1 || bilateralProjectID == -1) {
      return NOT_FOUND;
    }

    Project bilateralProject = projectManager.getProject(bilateralProjectID);
    Project coreProject = projectManager.getProject(coreProjectID);

    List<Project> linkedProjects = new ArrayList<>();
    linkedProjects.add(coreProject);
    bilateralProject.setLinkedProjects(linkedProjects);

    // Save into the database the core linked projects
    success =
      linkedCoreProjectManager.saveLinkedCoreProjects(bilateralProject, this.getCurrentUser(), this.getJustification());
    if (!success) {
      LOG.warn("There was an error creating a cofounded project with coreProjectID: {} and bilateralProjectID: {}");
      this.addActionError("The co-founded project could not be created.");
    }

    // Change the type of the core project to cofounded
    coreProject.setType(APConstants.PROJECT_CCAFS_COFUNDED);
    String justification = (this.getJustification() == null) ? "" : this.getJustification();
    int result = projectManager.saveProjectDescription(coreProject, this.getCurrentUser(), justification);
    success = success && (result != -1);

    // Mark the bilateral project as co-financing
    bilateralProject.setCofinancing(true);
    justification = (this.getJustification() == null) ? "" : this.getJustification();
    result = projectManager.saveProjectDescription(bilateralProject, this.getCurrentUser(), justification);
    success = success && (result != -1);

    this.addActionMessage(this.getText("planning.projectDescription.createdCofoundedProject"));
    return (success) ? SUCCESS : ERROR;
  }

  public void setBilateralProjectID(int bilateralProjectID) {
    this.bilateralProjectID = bilateralProjectID;
  }

  public void setCoreProjectID(int coreProjectID) {
    this.coreProjectID = coreProjectID;
  }
}