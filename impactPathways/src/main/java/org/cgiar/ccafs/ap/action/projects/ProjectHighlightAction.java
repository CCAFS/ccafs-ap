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
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.projects.ProjectDeliverableValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectHighlightAction extends BaseAction {

  private static final long serialVersionUID = 6921586701429004011L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectHighlightAction.class);

  // Manager
  private ProjectManager projectManager;
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;

  private ProjectDeliverableValidator validator;

  // Model for the back-end
  private Deliverable highlight;
  private Project project;

  // Model for the front-end
  private int highlightID;
  private List<DeliverableType> highlightTypes;
  private List<Integer> allYears;


  @Inject
  public ProjectHighlightAction(APConfig config, ProjectManager projectManager, DeliverableManager deliverableManager,
    DeliverableTypeManager deliverableTypeManager, NextUserManager nextUserManager,
    ProjectDeliverableValidator validator) {
    super(config);
    this.projectManager = projectManager;
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.validator = validator;
  }


  /**
   * This method validates if this highlight can be deleted or not.
   * Keep in mind that a highlight can be deleted if it was created in the current planning cycle.
   * 
   * @param highlightID is the highlight identifier.
   * @return true if the highlight can be deleted, false otherwise.
   */
  public boolean canDelete() {
    // Loop all the deliverables that are in the interface.
    return highlight.getCreated() >= this.config.getCurrentPlanningStartDate().getTime();
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public Deliverable getHighlight() {
    return highlight;
  }


  public List<DeliverableType> getHighlightTypes() {
    return highlightTypes;
  }


  public Project getProject() {
    return project;
  }


  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
  }

  @Override
  public String next() {
    return SUCCESS;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    highlightID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));
    project = projectManager.getProjectFromDeliverableId(highlightID);

    // Getting the lists for the front-end.
    highlightTypes = deliverableTypeManager.getDeliverableTypes();
    allYears = project.getAllYears();

    // Getting the highlight information.
    highlight = deliverableManager.getDeliverableById(highlightID);


    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
  }


  @Override
  public String save() {
    return SUCCESS;
  }


  public void setDeliverable(Deliverable deliverable) {
    this.highlight = deliverable;
  }

  @Override
  public void validate() {
    super.validate();
    if (save) {
      validator.validate(this, project, highlight, this.getCycleName());
    }
  }
}