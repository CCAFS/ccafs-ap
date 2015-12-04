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
import org.cgiar.ccafs.ap.data.manager.HighLightManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.hibernate.model.ProjectHighligths;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */
public class ProjectHighListAction extends BaseAction {

  private static final long serialVersionUID = -6143944536558245482L;

  // Manager
  private HighLightManager deliverableManager;

  private ProjectManager projectManager;

  // Model for the back-end
  private Project project;

  // Model for the front-end
  private int projectID;
  private int deliverableID;

  private List<Integer> allYears;

  @Inject
  public ProjectHighListAction(APConfig config, HighLightManager deliverableManager, ProjectManager projectManager) {
    super(config);
    this.deliverableManager = deliverableManager;

    this.projectManager = projectManager;
  }

  @Override
  public String add() {
    ProjectHighligths newDeliverable = new ProjectHighligths(-1);
    // newDeliverable.setType(deliverableTypeManager.getDeliverableSubTypes().get(0));
    newDeliverable.setYear(new Long(project.getAllYears().get(0)));

    newDeliverable.setActiveSince(new Date());
    newDeliverable.setAuthor("");
    newDeliverable.setContributor("");
    newDeliverable.setCoverage("");
    newDeliverable.setCreatedBy(this.getCurrentUser().getId());
    newDeliverable.setDescription("");
    newDeliverable.setEndDate(new Date());

    newDeliverable.setIsActive(true);
    newDeliverable.setIsGlobal(false);
    newDeliverable.setKeywords("");
    newDeliverable.setLeader(0);
    newDeliverable.setLinks("");
    newDeliverable.setObjectives("");
    newDeliverable.setPartners("");
    newDeliverable.setProjectId(project.getId());
    newDeliverable.setPublisher("");
    newDeliverable.setRelation("");
    newDeliverable.setResults("");
    newDeliverable.setRights("");
    newDeliverable.setStartDate(new Date());
    newDeliverable.setStatus(new Long(1));
    newDeliverable.setSubject("");
    newDeliverable.setTitle("");

    deliverableID = deliverableManager.saveHighLight(project.getId(), newDeliverable, this.getCurrentUser(),
      "New expected deliverable created");

    if (deliverableID > 0) {
      return SUCCESS;
    }

    return INPUT;
  }

  @Override
  public String delete() {
    // Deleting deliverable.
    for (ProjectHighligths deliverable : project.getHighlights()) {
      if (deliverable.getId() == deliverableID) {
        boolean deleted = deliverableManager.deleteHighLight(deliverableID, this.getCurrentUser(),
          this.getJustification() == null ? "Deleting deliverable" : this.getJustification());
        if (deleted) {
          this.addActionMessage(
            this.getText("deleting.success", new String[] {this.getText("planning.projectDeliverable").toLowerCase()}));
        } else {
          this.addActionError(
            this.getText("deleting.problem", new String[] {this.getText("planning.projectDeliverable").toLowerCase()}));
        }
      }
    }
    return SUCCESS;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public Date getCurrentPlanningStartDate() {
    return config.getCurrentPlanningStartDate();
  }

  public int getDeliverableID() {
    return deliverableID;
  }


  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public String getProjectRequestID() {
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

    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);

    // Getting the Deliverables Main Types.


    allYears = project.getAllYears();

    // Getting the List of Expected Deliverables

    List<ProjectHighligths> deliverables = deliverableManager.getHighLightsByProject(projectID);
    project.setHighlights(deliverables);

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());

  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }


  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

}
