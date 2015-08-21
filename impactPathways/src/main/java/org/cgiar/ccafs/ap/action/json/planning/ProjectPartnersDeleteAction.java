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

package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Carlos Alberto Martínez M - CIAT/CCAFS
 */

public class ProjectPartnersDeleteAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(ProjectPartnersDeleteAction.class);
  private static final long serialVersionUID = 2947868791705570456L;

  // Managers
  private ActivityManager activityManager;
  private DeliverableManager deliverableManager;
  private ProjectPartnerManager projectPartnerManager;
  private ProjectManager projectManager;
  private InstitutionManager institutionManager;

  private int projectPartnerID;

  private String message = "";
  private List<Activity> linkedActivities;
  private List<Deliverable> linkedDeliverables;
  private List<ProjectPartner> linkedProjectPartners;

  @Inject
  public ProjectPartnersDeleteAction(APConfig config, ActivityManager activityManager,
    DeliverableManager deliverableManager, ProjectPartnerManager projectPartnerManager, ProjectManager projectManager,
    InstitutionManager institutionManager) {
    super(config);
    this.activityManager = activityManager;
    this.deliverableManager = deliverableManager;
    this.projectPartnerManager = projectPartnerManager;
    this.projectManager = projectManager;
    this.institutionManager = institutionManager;
  }


  /**
   * This method gets all the activities where the activity leaders are linked to this partner.
   */
  private void checkActivityLeaders() {
    linkedActivities = activityManager.getActivitiesByProjectPartner(projectPartnerID);
  }

  /**
   * This method gets all the deliverables that are being contributed by this project partner
   */
  private void checkDeliverableContributions() {
    linkedDeliverables = deliverableManager.getDeliverablesByProjectPartnerID(projectPartnerID);
  }

  /**
   * This method gets all the Project Partners where some institution will stop contributing if this partner got
   * deleted.
   * What this method does is to get the list of all the PPA institutions, and validate if the institution of the
   * current project partner is repeated. If so, nothing happen.
   * However, if the institution of this partner is not repeated on another partner, we need know which other Project
   * Partners are being contributed by this institution.
   */
  public void checkProjectPartnerContributions() {
    // First, we need to get the list of all the CCAFS Partners of the project.
    // To do that, we need to know the project where this project partner belongs to.
    linkedProjectPartners = new ArrayList<>();
    Project project = projectManager.getProjectFromProjectPartnerID(projectPartnerID);
    if (project != null) {
      // Getting the partner information.
      ProjectPartner partnerToDelete = projectPartnerManager.getProjectPartnerById(projectPartnerID);

      // Getting the list of partners that are contributing to this project.
      List<ProjectPartner> partners = projectPartnerManager.getProjectPartners(project.getId());
      boolean lastInstitution = institutionManager.validateLastOneInstitution(projectPartnerID);

      // If the institution is the last one, we need to get all the project partners that will be affected.
      if (lastInstitution) {
        // Looping the list of partners.
        for (ProjectPartner partner : partners) {
          // Looping the list of "contribute institutions".
          // for (Institution institution : partner.getContributeInstitutions()) {
          // if (institution.equals(partnerToDelete.getInstitution())) {
          // // Populate the array.
          // linkedProjectPartners.add(partner);
          // break; // stop the loop.
          // }
          // }
        }
      }
    }
  }


  @Override
  public String execute() {

    // checking activity leaders.
    this.checkActivityLeaders();

    // Checking contribution to the deliverables.
    this.checkDeliverableContributions();

    // Checking project partner contributions.
    this.checkProjectPartnerContributions();

    if (linkedActivities.size() == 0) {
      if (linkedDeliverables.size() > 0 || linkedProjectPartners.size() > 0) {
        message = this.getText("planning.projectPartners.delete.relationships");
      } else {
        message = "";
      }
    } else {
      message = this.getText("planning.projectPartners.delete.activities");
    }
    return SUCCESS;
  }


  public List<Activity> getLinkedActivities() {
    return linkedActivities;
  }

  public List<Deliverable> getLinkedDeliverables() {
    return linkedDeliverables;
  }

  public List<ProjectPartner> getLinkedProjectPartners() {
    return linkedProjectPartners;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public void prepare() throws Exception {

    // setting a general message
    message = "";

    // Getting the project partner ID from the URL parameter.
    String partnerIDParameter =
      StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_PARTNER_REQUEST_ID));
    try {
      projectPartnerID = (partnerIDParameter != null) ? Integer.parseInt(partnerIDParameter) : -1;
    } catch (NumberFormatException e) {
      LOG.warn("There was an exception trying to convert to int the parameter {}", partnerIDParameter);
      projectPartnerID = -1;
    }


  }
}
