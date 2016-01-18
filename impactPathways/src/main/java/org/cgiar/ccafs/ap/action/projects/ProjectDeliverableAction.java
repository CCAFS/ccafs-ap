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
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.DeliverableType;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.validation.projects.ProjectDeliverableValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 * @author Christian David Garcia O. - CIAT/CCAFS
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
  private IPElementManager ipElementManager;
  private HistoryManager historyManager;
  private ProjectDeliverableValidator validator;
  private IPProgramManager ipProgramManager;
  private File file;
  private String fileFileName;
  // Model for the back-end
  private Deliverable deliverable;


  private Project project;

  // Model for the front-end
  private int deliverableID;
  private List<DeliverableType> deliverableTypes;

  private List<DeliverableType> deliverableSubTypes;
  private List<Integer> allYears;
  private List<IPElement> outputs;
  private List<ProjectPartner> projectPartners;
  private List<DeliverablePartner> deliverablePartners;
  private List<IPProgram> ipProgramFlagships;
  private Map<Integer, String> projectPartnerPersons;
  private Map<String, String> openAccessStatuses;
  private Map<String, String> disseminationChannels;
  private Map<String, String> statuses;

  @Inject
  public ProjectDeliverableAction(APConfig config, ProjectManager projectManager, DeliverableManager deliverableManager,
    DeliverableTypeManager deliverableTypeManager, NextUserManager nextUserManager,
    DeliverablePartnerManager deliverablePartnerManager, ProjectPartnerManager projectPartnerManager,
    IPElementManager ipElementManager, HistoryManager historyManager, ProjectDeliverableValidator validator,
    IPProgramManager ipProgramManager) {
    super(config);
    this.projectManager = projectManager;
    this.deliverableManager = deliverableManager;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.projectPartnerManager = projectPartnerManager;
    this.ipElementManager = ipElementManager;
    this.historyManager = historyManager;
    this.validator = validator;
    this.ipProgramManager = ipProgramManager;
  }

  /**
   * This method validates if this deliverable can be deleted or not.
   * Keep in mind that a deliverable can be deleted if it was created in the current planning cycle.
   * 
   * @param deliverableID is the deliverable identifier.
   * @return true if the deliverable can be deleted, false otherwise.
   */
  public boolean canDelete() {
    // Loop all the deliverables that are in the interface.
    return deliverable.getCreated() >= this.config.getCurrentPlanningStartDate().getTime();
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public Deliverable getDeliverable() {
    return deliverable;
  }


  public List<DeliverablePartner> getDeliverablePartners() {
    return deliverablePartners;
  }


  public List<DeliverableType> getDeliverableSubTypes() {
    return deliverableSubTypes;
  }


  /**
   * This method returns a list of DeliverableSubTypes depending on the deliverableMainTypeID received as parameter.
   * 
   * @param deliverableMainTypeID is the deliverable main category identifier.
   * @return a list with the sub-types related to the main-type identifier received, an empty list if there is no
   *         coincidence or null if an error happens.
   */
  public List<DeliverableType> getDeliverableSubTypes(int deliverableMainTypeID) {
    List<DeliverableType> listSubTypes = new ArrayList<DeliverableType>();
    for (int i = 0; i < deliverableSubTypes.size(); i++) {
      if (deliverableSubTypes.get(i).getCategory().getId() == deliverableMainTypeID) {
        listSubTypes.add(deliverableSubTypes.get(i));
      }
    }
    return listSubTypes;
  }


  public List<DeliverableType> getDeliverableTypes() {
    return deliverableTypes;
  }


  public Map<String, String> getDisseminationChannels() {
    return disseminationChannels;
  }


  public File getFile() {
    return file;
  }

  public List<IPProgram> getIpProgramFlagships() {
    return ipProgramFlagships;
  }

  public Map<String, String> getOpenAccessStatuses() {
    return openAccessStatuses;
  }

  public List<IPElement> getOutputs() {
    return outputs;
  }

  public Project getProject() {
    return project;
  }

  public Map<Integer, String> getProjectPartnerPersons() {
    return projectPartnerPersons;
  }

  public List<ProjectPartner> getProjectPartners() {
    return this.projectPartners;
  }

  private String getRankingAbsoloutUrlPath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator + "hightlihts"
      + File.separator;
  }

  private String getRankingPath() {
    return config.getUploadsBaseFolder() + File.separator + this.getRankingAbsoloutUrlPath() + File.separator;
  }


  public String getRankingUrl() {
    return config.getDownloadURL() + "/" + this.getRankingUrlPath().replace('\\', '/');
  }


  public String getRankingUrlPath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator + "rankingsImage"
      + File.separator;
  }

  public Map<String, String> getStatuses() {
    return statuses;
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

    deliverableID =
      Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.DELIVERABLE_REQUEST_ID)));
    project = projectManager.getProjectFromDeliverableId(deliverableID);

    // Getting the lists for the front-end.
    deliverableTypes = deliverableTypeManager.getDeliverableTypes();
    deliverableSubTypes = deliverableTypeManager.getDeliverableSubTypes();
    allYears = project.getAllYears();
    outputs = ipElementManager.getProjectOutputs(project.getId());
    ipProgramFlagships = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);

    openAccessStatuses = new HashMap<>();
    openAccessStatuses.put(APConstants.OA_OPEN, this.getText("reporting.projectDeliverable.openAccess.open"));
    openAccessStatuses.put(APConstants.OA_LIMITED, this.getText("reporting.projectDeliverable.openAccess.limited"));

    disseminationChannels = new HashMap<>();
    disseminationChannels.put("amkn", "AMKN");
    disseminationChannels.put("cgspace", "CGSpace");
    disseminationChannels.put("agtrials", "AgTrials");
    disseminationChannels.put("other", "Other");
    statuses = new HashMap<>();
    List<ProjectStatusEnum> list = Arrays.asList(ProjectStatusEnum.values());
    for (ProjectStatusEnum projectStatusEnum : list) {
      statuses.put(projectStatusEnum.getStatusId(), projectStatusEnum.getStatus());
    }
    projectPartners = projectPartnerManager.getProjectPartners(project);

    // Getting the partner persons in a single HashMap to be displayed in the view.
    projectPartnerPersons = new HashMap<>();
    for (ProjectPartner partner : projectPartners) {
      for (PartnerPerson person : partner.getPartnerPersons()) {
        projectPartnerPersons.put(person.getId(), partner.getPersonComposedName(person.getId()));
      }
    }

    // Getting the deliverable information.
    deliverable = deliverableManager.getDeliverableById(deliverableID);

    // Getting next users.
    deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));

    // Getting the responsible partner.
    List<DeliverablePartner> deliverablePartners =
      deliverablePartnerManager.getDeliverablePartners(deliverableID, APConstants.DELIVERABLE_PARTNER_RESP);
    if (deliverablePartners.size() > 0) {
      deliverable.setResponsiblePartner(deliverablePartners.get(0));
    }

    // Getting the other partners that are contributing to this deliverable.
    deliverable.setOtherPartners(
      deliverablePartnerManager.getDeliverablePartners(deliverableID, APConstants.DELIVERABLE_PARTNER_OTHER));

    super.setHistory(historyManager.getProjectDeliverablesHistory(deliverableID));

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (deliverable.getNextUsers() != null) {
        deliverable.getNextUsers().clear();
      }
      if (deliverable.getOtherPartners() != null) {
        deliverable.getOtherPartners().clear();
      }
      if (deliverable.getTypeOther() != null) {
        deliverable.setTypeOther(null);
      }
    }

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
  }


  @Override
  public String save() {


    // Getting the project
    project = projectManager.getProjectFromDeliverableId(deliverableID);
    if (securityContext.canUpdateProjectDeliverables(project.getId())) {
      if (file != null) {
        FileManager.deleteFile(this.getRankingPath() + deliverable.getRanking().getProcessDataFile());
        FileManager.copyFile(file, this.getRankingPath() + fileFileName);

        deliverable.getRanking().setProcessDataFile(fileFileName);
      }
      // -------- Saving main information
      deliverableManager.saveDeliverable(project.getId(), deliverable, this.getCurrentUser(), this.getJustification());


      if (deliverable.getOutput() != null) {
        deliverableManager.saveDeliverableOutput(deliverable.getId(), deliverable.getOutput().getId(),
          this.getCurrentUser(), this.getJustification());

      }
      // Saving MOG

      // -------- Saving next users.

      // Getting previous next users in order to identify those that need to be deleted.
      List<NextUser> previousNextUsers = nextUserManager.getNextUsersByDeliverableId(deliverableID);

      // Deleting the next users coming from the front-end that are not part of the list.
      for (NextUser previousNextUser : previousNextUsers) {
        if (!deliverable.getNextUsers().contains(previousNextUser)) {
          nextUserManager.deleteNextUserById(previousNextUser.getId(), this.getCurrentUser(), this.getJustification());
        }
      }

      // Saving new and old Next Users
      nextUserManager.saveNextUsers(deliverableID, deliverable.getNextUsers(), this.getCurrentUser(),
        this.getJustification());


      // ---------- Saving deliverable partners contribution

      // Saving responsible deliverable partner
      if (deliverable.getResponsiblePartner() != null && deliverable.getResponsiblePartner().getPartner() != null) {
        deliverablePartnerManager.saveDeliverablePartner(deliverableID, deliverable.getResponsiblePartner(),
          this.getCurrentUser(), this.getJustification());
      }

      // Saving other contributions

      // Getting previous other contributions in order to know those that need to be deleted.
      List<DeliverablePartner> previousOtherPartners =
        deliverablePartnerManager.getDeliverablePartners(deliverableID, APConstants.DELIVERABLE_PARTNER_OTHER);

      // Deleting other contributions
      for (DeliverablePartner previousOtherPartner : previousOtherPartners) {
        if (!deliverable.getOtherPartners().contains(previousOtherPartner)) {
          deliverablePartnerManager.deleteDeliverablePartner(previousOtherPartner.getId(), this.getCurrentUser(),
            this.getJustification());
        }
      }

      // Saving new and old Other Deliverable Partners
      deliverablePartnerManager.saveDeliverablePartners(deliverableID, deliverable.getOtherPartners(),
        this.getCurrentUser(), this.getJustification());

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

  public void setDeliverable(Deliverable deliverable) {
    this.deliverable = deliverable;
  }

  public void setFile(File file) {
    this.file = file;
  }


  public void setIpProgramFlagships(List<IPProgram> ipProgramFlagships) {
    this.ipProgramFlagships = ipProgramFlagships;
  }


  public void setProjectPartnerPersons(Map<Integer, String> projectPartnerPersons) {
    this.projectPartnerPersons = projectPartnerPersons;
  }

  public void setStatuses(Map<String, String> statuses) {
    this.statuses = statuses;
  }

  @Override
  public void validate() {
    super.validate();
    if (save) {
      validator.validate(this, project, deliverable, this.getCycleName());
    }
  }
}
