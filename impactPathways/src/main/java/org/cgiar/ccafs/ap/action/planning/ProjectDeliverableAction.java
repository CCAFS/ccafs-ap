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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectDeliverableAction extends BaseAction {

  private static final long serialVersionUID = 6921586701429004011L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectDeliverableAction.class);

  // Manager
  private ProjectManager projectManager;
  private DeliverableManager deliverableManager;
  private DeliverableTypeManager deliverableTypeManager;
  private DeliverablePartnerManager deliverablePartnerManager;
  private NextUserManager nextUserManager;
  private ProjectPartnerManager projectPartnerManager;

  // Model for the back-end
  private Deliverable deliverable;
  private Project project;

  // Model for the front-end
  private int deliverableID;
  private List<DeliverableType> deliverableTypes;
  private List<DeliverableType> deliverableSubTypes;
  private List<Integer> allYears;
  private List<IPElement> outputs;
  private List<Institution> institutions;


  @Inject
  public ProjectDeliverableAction(APConfig config, ProjectManager projectManager, DeliverableManager deliverableManager,
    DeliverableTypeManager deliverableTypeManager, NextUserManager nextUserManager,
    DeliverablePartnerManager deliverablePartnerManager, ProjectPartnerManager projectPartnerManager) {
    super(config);
    this.projectManager = projectManager;
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.projectPartnerManager = projectPartnerManager;
  }


  public List<Integer> getAllYears() {
    return allYears;
  }


  public Deliverable getDeliverable() {
    return deliverable;
  }


  public List<DeliverableType> getDeliverableSubTypes() {
    return deliverableSubTypes;
  }

  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
  }

  public List<Institution> getInstitutions() {
    return institutions;
  }

  public List<IPElement> getOutputs() {
    return outputs;
  }

  public Project getProject() {
    return project;
  }

  @Override
  public String next() {
    return SUCCESS;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    deliverableID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));
    project = projectManager.getProjectFromDeliverableId(deliverableID);

    // Getting the lists for the front-end.
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();
    deliverableSubTypes = deliverableTypeManager.getDeliverableSubTypes();
    allYears = project.getAllYears();
    outputs = projectManager.getProjectOutputs(project.getId());

    // Getting the list of institutions that will be showed in the lists.
    institutions = new ArrayList<>();
    for (ProjectPartner projectPartner : projectPartnerManager.getProjectPartners(project.getId())) {
      if (!institutions.contains(projectPartner.getInstitution())) {
        institutions.add(projectPartner.getInstitution());
      }
    }

    // Getting the deliverable information.
    deliverable = deliverableManager.getDeliverableById(deliverableID);

    // Getting next users.
    deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));

    // Getting the responsible partner.
    List<DeliverablePartner> partners =
      deliverablePartnerManager.getDeliverablePartners(deliverableID, APConstants.DELIVERABLE_PARTNER_RESP);
    if (partners.size() > 0) {
      deliverable.setResponsiblePartner(partners.get(0));
    } else {
      DeliverablePartner responsiblePartner = new DeliverablePartner(-1);
      responsiblePartner.setInstitution(new Institution(-1));
      responsiblePartner.setUser(new User(-1));
      responsiblePartner.setType(APConstants.DELIVERABLE_PARTNER_RESP);
      deliverable.setResponsiblePartner(responsiblePartner);
    }

    // Getting the other partners that are contributing to this deliverable.
    deliverable.setOtherPartners(
      deliverablePartnerManager.getDeliverablePartners(deliverableID, APConstants.DELIVERABLE_PARTNER_OTHER));

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (deliverable.getNextUsers() != null) {
        deliverable.getNextUsers().clear();
      }
      if (deliverable.getOtherPartners() != null) {
        deliverable.getOtherPartners().clear();
      }
    }

  }


  @Override
  public String save() {
    boolean success = true;

    // Getting the project
    project = projectManager.getProjectFromDeliverableId(deliverableID);

    // -------- Saving main information
    int result =
      deliverableManager.saveDeliverable(project.getId(), deliverable, this.getCurrentUser(), this.getJustification());
    if (result != 0) {
      success = false;
    }

    // -------- Saving next users.

    // Getting previous next users in order to identify those that need to be deleted.
    List<NextUser> previousNextUsers = nextUserManager.getNextUsersByDeliverableId(deliverableID);

    // Deleting the next users coming from the front-end that are not part of the list.
    for (NextUser previousNextUser : previousNextUsers) {
      if (!deliverable.getNextUsers().contains(previousNextUser)) {
        boolean deleted =
          nextUserManager.deleteNextUserById(previousNextUser.getId(), this.getCurrentUser(), this.getJustification());
        if (!deleted) {
          success = false;
        }
      }
    }

    // Saving new and old Next Users
    boolean saved = nextUserManager.saveNextUsers(deliverableID, deliverable.getNextUsers(), this.getCurrentUser(),
      this.getJustification());

    if (!saved) {
      success = false;
    }

    // ---------- Saving deliverable partners contribution

    // Saving responsible deliverable partner
    if (deliverable.getResponsiblePartner() != null && deliverable.getResponsiblePartner().getInstitution() != null) {
      result = deliverablePartnerManager.saveDeliverablePartner(deliverableID, deliverable.getResponsiblePartner(),
        this.getCurrentUser(), this.getJustification());
      if (result < 0) {
        success = false;
      }
    } else
      if (deliverable.getResponsiblePartner().getInstitution() == null
      && deliverable.getResponsiblePartner().getUser() == null) {
        saved = deliverablePartnerManager.deleteDeliverablePartner(deliverable.getResponsiblePartner().getId(),
          this.getCurrentUser(), this.getJustification());
        if (!saved) {
          success = false;
        }
      }


    // ----------------- other partner contributions ----------------------
    // Getting previous to identify those that need to be deleted.
    // List<ProjectPartner> previousPartners = projectPartnerManager.getProjectPartners(projectID, partnerType);
    //
    // // Deleting project partners
    // for (ProjectPartner previousPartner : previousPartners) {
    // if (!partners.contains(previousPartner)) {
    // boolean deleted =
    // projectPartnerManager.deleteProjectPartner(previousPartner.getId(), this.getCurrentUser(),
    // this.getJustification());
    // if (!deleted) {
    // success = false;
    // }
    // }
    // }
    //
    // // Saving new and old PPA Partners
    // boolean saved =
    // projectPartnerManager.saveProjectPartners(projectID, partners, this.getCurrentUser(), this.getJustification());
    // if (!saved) {
    // saved = false;
    // }
    //
    // // Saving project partner contributions
    // if (partnerType.equals(APConstants.PROJECT_PARTNER_PP)) {
    // // iterating each project partner
    // for (ProjectPartner projectPartner : partners) {
    // // Getting previous partner contributions to identify those that need to be deleted.
    // List<Institution> previousPartnerContributions =
    // institutionManager.getProjectPartnerContributeInstitutions(projectPartner);
    // // Deleting project partner contributions
    // for (Institution previousPartnerContribution : previousPartnerContributions) {
    // if (projectPartner.getContributeInstitutions() == null
    // || !projectPartner.getContributeInstitutions().contains(previousPartnerContribution)) {
    // boolean deleted =
    // institutionManager.deleteProjectPartnerContributeInstitution(projectPartner.getId(),
    // previousPartnerContribution.getId());
    // if (!deleted) {
    // success = false;
    // }
    // }
    // }
    //
    // // if the project partner has contribute institutions.
    // if (projectPartner.getContributeInstitutions() != null) {
    // // Saving new and old Project Partner Contributions
    // saved =
    // institutionManager.saveProjectPartnerContributeInstitutions(projectPartner.getId(),
    // projectPartner.getContributeInstitutions());
    // if (!saved) {
    // saved = false;
    // }
    // }
    // } // End loop
    // }
    //
    if (success) {
      this.addActionMessage(this.getText("saving.saved"));
      return SUCCESS;
    }
    return INPUT;
  }

  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }


  public void setInstitutions(List<Institution> institutions) {
    this.institutions = institutions;
  }

  @Override
  public void validate() {
    super.validate();
  }
}
