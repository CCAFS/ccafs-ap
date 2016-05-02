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
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.Project;
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
public class ProjectDeliverablesListAction extends BaseAction {

  private static final long serialVersionUID = -6143944536558245482L;

  // Manager
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private ProjectManager projectManager;

  // Model for the back-end
  private Project project;

  // Model for the front-end
  private int projectID;
  private int deliverableID;
  private List<DeliverableType> deliverableTypes;
  private List<DeliverableType> deliverableSubTypes;
  private List<Integer> allYears;

  @Inject
  public ProjectDeliverablesListAction(APConfig config, DeliverableManager deliverableManager,
    DeliverableTypeManager deliverableTypeManager, ProjectManager projectManager) {
    super(config);
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.projectManager = projectManager;
  }

  @Override
  public String add() {
    Deliverable newDeliverable = new Deliverable(-1);
    // newDeliverable.setType(deliverableTypeManager.getDeliverableSubTypes().get(0));
    if (this.isReportingCycle()) {
      newDeliverable.setYear(this.getCurrentReportingYear());
    } else {
      newDeliverable.setYear(this.getCurrentPlanningYear());
    }

    deliverableID = deliverableManager.saveDeliverable(project.getId(), newDeliverable, this.getCurrentUser(),
      "New expected deliverable created");

    if (deliverableID > 0) {
      return SUCCESS;
    }

    return INPUT;
  }

  @Override
  public String delete() {
    // Deleting deliverable.
    for (Deliverable deliverable : project.getDeliverables()) {
      if (deliverable.getId() == deliverableID) {
        boolean deleted = deliverableManager.deleteDeliverable(deliverableID, this.getCurrentUser(),
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

  public List<DeliverableType> getDeliverableSubTypes() {
    return deliverableSubTypes;
  }

  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
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
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();

    allYears = project.getAllYears();

    // Getting the List of Expected Deliverables

    List<Deliverable> deliverables = deliverableManager.getDeliverablesBasciByProject(projectID);
    project.setDeliverables(deliverables);

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());

  }

  public void setDeliverableID(int deliverableID) {
    this.deliverableID = deliverableID;
  }

  public void setDeliverableSubTypes(List<DeliverableType> deliverableSubTypes) {
    this.deliverableSubTypes = deliverableSubTypes;
  }

  public void setDeliverableTypes(List<DeliverableType> deliverableTypes) {
    this.deliverableTypes = deliverableTypes;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

}
