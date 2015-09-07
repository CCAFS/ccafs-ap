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

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.csv.DeliverableSummaryCSV;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jorge Leonardo Solis Banguera
 */
public class DeliverableProjectSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(DeliverableProjectSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;
  private NextUserManager nextUserManager;
  private DeliverablePartnerManager deliverablePartnerManager;
  private DeliverableSummaryCSV deliverableCSV;
  private DeliverableManager deliverableManager;
  private ProjectManager projectManager;
  private List<InputStream> streams;

  List<Project> projectList;
  int projectID;

  @Inject
  public DeliverableProjectSummaryAction(APConfig config, DeliverableManager deliverableManager,
    NextUserManager nextUserManager, DeliverablePartnerManager deliverablePartnerManager,
    DeliverableSummaryCSV deliverableCSV, ProjectManager projectManager) {
    super(config);
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.deliverableCSV = deliverableCSV;
    this.deliverableManager = deliverableManager;
    this.projectManager = projectManager;
  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    deliverableCSV.generateCSV(projectList);
    streams = new ArrayList<>();
    streams.add(deliverableCSV.getInputStream());

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return deliverableCSV.getContentLength();
  }

  @Override
  public String getFileName() {
    return deliverableCSV.getFileName();
  }


  @Override
  public InputStream getInputStream() {
    return deliverableCSV.getInputStream();
  }


  @Override
  public void prepare() {

    projectList = this.projectManager.getAllProjectsBasicInfo();
    // projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    List<Deliverable> deliverables;

    for (Project project : projectList) {

      deliverables = deliverableManager.getDeliverablesByProject(project.getId());

      for (Deliverable deliverable : deliverables) {
        // Getting next users.
        deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));

        // Getting the responsible partner.
        List<DeliverablePartner> partners =
          deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_RESP);
        if (partners.size() > 0) {
          deliverable.setResponsiblePartner(partners.get(0));
        } else {
          DeliverablePartner responsiblePartner = new DeliverablePartner(-1);
          deliverable.setResponsiblePartner(responsiblePartner);
        }

        // Getting the other partners that are contributing to this deliverable.
        deliverable.setOtherPartners(deliverablePartnerManager.getDeliverablePartners(deliverable.getId(),
          APConstants.DELIVERABLE_PARTNER_OTHER));
      }

      project.setDeliverables(deliverables);

    }
  }
}
