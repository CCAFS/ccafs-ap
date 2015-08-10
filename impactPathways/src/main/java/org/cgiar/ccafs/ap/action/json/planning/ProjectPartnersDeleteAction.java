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


  @Override
  public String execute() {
    activitiesList = activityManager.getActivitiesByProjectPartner(projectPartnerID);

    // ------------- TODO: TEST -------------
    linkedActivities.add(activityManager.getActivityById(3));
    linkedActivities.add(activityManager.getActivityById(5));
    linkedActivities.add(activityManager.getActivityById(6));

    linkedDeliverables.add(deliverableManager.getDeliverableById(5));
    linkedDeliverables.add(deliverableManager.getDeliverableById(6));
    linkedDeliverables.add(deliverableManager.getDeliverableById(7));

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
    if (activitiesList.size() <= 0) {
      message = null;
    } else {
      message = warning + message;
    }
    return message;
  }

  @Override
  public void prepare() throws Exception {

    // setting a general message
    warning = this.getText("planning.projectPartners.activities");

    // Getting the project partner ID
    String _partnerID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_PARTNER_REQUEST_ID));
    try {
      projectPartnerID = (_partnerID != null) ? Integer.parseInt(_partnerID) : -1;
    } catch (NumberFormatException e) {
      LOG.warn("There was an exception trying to convert to int the parameter {}", _partnerID);
      projectPartnerID = -1;
    }


  }
}
