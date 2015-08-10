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
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Deliverable;
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

  private List<Activity> activitiesList;
  private int projectPartnerID;

  private String message = "", warning;
  private List<Activity> linkedActivities;
  private List<Deliverable> linkedDeliverables;
  private List<ProjectPartner> linkedProjectPartners;

  @Inject
  public ProjectPartnersDeleteAction(APConfig config, ActivityManager activityManager,
    DeliverableManager deliverableManager, ProjectPartnerManager projectPartnerManager) {
    super(config);
    this.activityManager = activityManager;
    this.deliverableManager = deliverableManager;
    this.projectPartnerManager = projectPartnerManager;
  }


  /**
   * This method gets all the activities where the activity leaders are linked to this partner.
   */
  private void checkActivityLeaders() {
    activitiesList = activityManager.getActivitiesByProjectPartner(projectPartnerID);
  }

  /**
   * This method gets all the deliverables that are being contributed by this project partner
   */
  private void checkDeliverableContributions() {
    linkedDeliverables = deliverableManager.getDeliverablesByProjectPartnerID(projectPartnerID);
  }

  /**
   * This method gets all the Project Partners that are being contributed by this project partner.
   */
  private void checkProjectPartnerContributions() {
    // TODO Auto-generated method stub
  }


  @Override
  public String execute() {

    // checking activity leaders.
    this.checkActivityLeaders();

    // Checking contribution to the deliverables.
    this.checkDeliverableContributions();

    // Checking project partner contributions.
    this.checkProjectPartnerContributions();

    // ------------- TODO: TEST -------------
    // linkedActivities = new ArrayList<>();
    // linkedActivities.add(activityManager.getActivityById(3));
    // linkedActivities.add(activityManager.getActivityById(5));
    // linkedActivities.add(activityManager.getActivityById(6));

    // linkedDeliverables = new ArrayList<>();
    // linkedDeliverables.add(deliverableManager.getDeliverableById(5));
    // linkedDeliverables.add(deliverableManager.getDeliverableById(6));
    // linkedDeliverables.add(deliverableManager.getDeliverableById(7));

    linkedProjectPartners = new ArrayList<>();
    linkedProjectPartners.add(projectPartnerManager.getProjectPartnerById(678));
    linkedProjectPartners.add(projectPartnerManager.getProjectPartnerById(679));
    linkedProjectPartners.add(projectPartnerManager.getProjectPartnerById(680));
    // ----------------------------------

    LOG.info("They were loaded {} activities", activitiesList.size());

    for (int i = 0; i < activitiesList.size(); i++) {
      message += "\n activity ID = " + activitiesList.get(i).getId() + ", " + "activity title = "
        + activitiesList.get(i).getTitle();
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
    message = this.getText("planning.projectPartners.activities");

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
