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
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LinkedCoreProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.planning.ProjectDescriptionValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectDescriptionPlanningAction extends BaseAction {


  private static final long serialVersionUID = 2845669913596494699L;

  private static Logger LOG = LoggerFactory.getLogger(ProjectDescriptionPlanningAction.class);
  // Manager
  private ProjectManager projectManager;
  private IPProgramManager ipProgramManager;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private UserManager userManager;
  private BudgetManager budgetManager;
  private HistoryManager historyManager;

  private LinkedCoreProjectManager linkedCoreProjectManager;

  // Model for the front-end
  private List<IPProgram> ipProgramRegions;
  private List<IPProgram> ipProgramFlagships;
  private List<LiaisonInstitution> liaisonInstitutions;
  // private List<IPCrossCutting> ipCrossCuttings;
  private List<User> allOwners;

  // Model for the back-end
  private Project previousProject;
  private Project project;
  private int projectID;

  private ProjectDescriptionValidator validator;

  @Inject
  public ProjectDescriptionPlanningAction(APConfig config, ProjectManager projectManager,
    IPProgramManager ipProgramManager, UserManager userManager, BudgetManager budgetManager,
    LiaisonInstitutionManager liaisonInstitutionManager, LinkedCoreProjectManager linkedCoreProjectManager,
    HistoryManager historyManager, ProjectDescriptionValidator validator) {
    super(config);
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.userManager = userManager;
    this.budgetManager = budgetManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.linkedCoreProjectManager = linkedCoreProjectManager;
    this.historyManager = historyManager;
    this.validator = validator;
  }

  public List<User> getAllOwners() {
    return allOwners;
  }

  /**
   * This method returns a composed name with the Acronym and Name.
   * e.g. FP4: Policies and Institutions for Climate-Resilient Food Systems
   * 
   * @param ipProgramId is the program identifier.
   * @return the composed name described above.
   */
  public String getComposedName(int ipProgramId) {
    for (IPProgram p : ipProgramFlagships) {
      if (p.getId() == ipProgramId) {
        return p.getAcronym() + ": " + p.getName();
      }
    }
    return null;
  }

  public int getEndYear() {
    return config.getEndYear();
  }


  /**
   * This method returns an array of flagship ids depending on the project.flagships attribute.
   * 
   * @return an array of integers.
   */
  public int[] getFlagshipIds() {
    if (this.project.getFlagships() != null) {
      int[] ids = new int[this.project.getFlagships().size()];
      for (int c = 0; c < ids.length; c++) {
        ids[c] = this.project.getFlagships().get(c).getId();
      }
      return ids;
    }
    return null;
  }

  public List<IPProgram> getIpProgramFlagships() {
    return ipProgramFlagships;
  }


  public List<IPProgram> getIpProgramRegions() {
    return ipProgramRegions;
  }


  public List<LiaisonInstitution> getLiaisonInstitutions() {
    return liaisonInstitutions;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public String getProjectRequest() {
    return APConstants.PROJECT_REQUEST_ID;
  }

  /**
   * This method returns an array of region ids depending on the project.regions attribute.
   * 
   * @return an array of integers.
   */
  public int[] getRegionIds() {
    if (this.project.getRegions() != null) {
      int[] ids = new int[this.project.getRegions().size()];
      for (int c = 0; c < ids.length; c++) {
        ids[c] = this.project.getRegions().get(c).getId();
      }
      return ids;
    }
    return null;
  }

  public int getStartYear() {
    return config.getStartYear();
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
    super.prepare();

    try {
      projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID, e);
      projectID = -1;
      return; // Stop here and go to execute method.
    }

    // Getting the information for the Project Owner Contact Persons for the View
    allOwners = userManager.getAllOwners();

    // Getting the information of the Regions program for the View
    ipProgramRegions = ipProgramManager.getProgramsByType(APConstants.REGION_PROGRAM_TYPE);

    // Getting the information of the Flagships program for the View
    ipProgramFlagships = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);

    // Get the list of institutions that can be management liaison of a project.
    liaisonInstitutions = liaisonInstitutionManager.getLiaisonInstitutions();

    // Getting project
    project = projectManager.getProject(projectID);
    if (project != null) {
      // Getting the information of the Flagships Program associated with the project
      project.setRegions(ipProgramManager.getProjectFocuses(projectID, APConstants.REGION_PROGRAM_TYPE));
      // Getting the information of the Regions Program associated with the project
      project.setFlagships(ipProgramManager.getProjectFocuses(projectID, APConstants.FLAGSHIP_PROGRAM_TYPE));
      // Getting the information of the Cross Cutting Theme associated with the project
      // project.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByProject(projectID));
    }

    // If project is bilateral cofounded, we should load the core projects linked to it.
    if (!project.isCoreProject()) {
      project.setLinkedCoreProjects(linkedCoreProjectManager.getLinkedCoreProjects(projectID));
    }
    // If the user is not admin or the project owner, we should keep some information
    // unmutable
    previousProject = new Project();
    previousProject.setId(project.getId());
    previousProject.setTitle(project.getTitle());
    previousProject.setLiaisonInstitution(project.getLiaisonInstitution());
    previousProject.setOwner(project.getOwner());
    previousProject.setStartDate(project.getStartDate());
    previousProject.setEndDate(project.getEndDate());
    previousProject.setSummary(project.getSummary());
    previousProject.setFlagships(project.getFlagships());
    previousProject.setRegions(project.getRegions());
    previousProject.setType(project.getType());
    previousProject.setWorkplanRequired(project.isWorkplanRequired());
    previousProject.setLinkedCoreProjects(project.getLinkedCoreProjects());

    super.setHistory(historyManager.getLogHistory("projects", project.getId()));
  }

  public String previousSave() {
    if (this.isSaveable()) {
      if (1 == 1) {
        return SUCCESS;
      }
      // ----- SAVING Project description -----
      int result = 0;
      // if user is project owner or FPL/RPL, he is able to fully edit.
      if (this.isFullEditable()) {

        // Reviewing some change in the year range from start date to end date in order to reflect those changes in the
        // project budget section.
        List<Integer> currentYears = project.getAllYears();
        List<Integer> previousYears = projectManager.getProject(project.getId()).getAllYears();
        // Deleting unused years from project budget.
        for (Integer previousYear : previousYears) {
          if (!currentYears.contains(previousYear)) {
            budgetManager.deleteBudgetsByYear(projectID, previousYear.intValue());
          }
        }

        result = projectManager.saveProjectDescription(project, this.getCurrentUser(), this.getJustification());

        if (result < 0) {
          this.addActionError(this.getText("saving.problem"));
          return BaseAction.INPUT;
        }

        // ----- SAVING IPPrograms (Flagships and Regions) -----
        boolean success = true;
        boolean saved = true;
        boolean deleted;

        // Adding the program that was disabled in the interface, and validate that at least one item was selected.
        IPProgram programDisabled = ipProgramManager.getIPProgramByProjectId(project.getId());
        if (programDisabled.getType().getId() == APConstants.FLAGSHIP_PROGRAM_TYPE) {
          project.getFlagships().add(programDisabled);
        } else if (programDisabled.getType().getId() == APConstants.REGION_PROGRAM_TYPE) {
          project.getRegions().add(programDisabled);
        } // else if (programDisabled.getType().getId() == APConstants.COORDINATION_PROGRAM_TYPE) {
        // project.getFlagships().add(programDisabled); // Which should be Global.
        // }

        if (project.getRegions().isEmpty()) {
          this.addActionWarning(this.getText("preplanning.projectDescription.noRegions"));
        }
        if (project.getFlagships().isEmpty()) {
          this.addActionWarning(this.getText("preplanning.projectDescription.noFlagships"));
        }

        // Identifying regions that were unchecked in the front-end
        if (project.getRegions() != null) {
          List<IPProgram> previousRegions =
            ipProgramManager.getProjectFocuses(project.getId(), APConstants.REGION_PROGRAM_TYPE);
          for (IPProgram programRegion : previousRegions) {
            if (!project.getRegions().contains(programRegion)) {
              deleted = ipProgramManager.deleteProjectFocus(project.getId(), programRegion.getId());
              if (!deleted) {
                success = false;
              }
            }
          }

          // Identifying existing regions in the database, so we don't have to insert them again.
          Iterator<IPProgram> iterator = project.getRegions().iterator();
          while (iterator.hasNext()) {
            if (previousRegions.contains(iterator.next())) {
              iterator.remove();
            }
          }
          // Adding new Regional Project Focuses.
          for (IPProgram programToAdd : project.getRegions()) {
            saved =
              ipProgramManager.saveProjectFocus(project.getId(), programToAdd.getId(), this.getCurrentUser(),
                this.getJustification());
            if (!saved) {
              success = false;
            }
          }
          // Stop here if a something bad happened.
          if (!success) {
            this.addActionError(this.getText("saving.problem"));
            return BaseAction.INPUT;
          }
        }

        // Identifying flagships that were unchecked in the front-end
        if (project.getFlagships() != null) {
          // Identifying deleted flagships
          List<IPProgram> previousFlagships =
            ipProgramManager.getProjectFocuses(project.getId(), APConstants.FLAGSHIP_PROGRAM_TYPE);
          for (IPProgram programFlagship : previousFlagships) {
            if (!project.getFlagships().contains(programFlagship)) {
              deleted = ipProgramManager.deleteProjectFocus(project.getId(), programFlagship.getId());
              if (!deleted) {
                success = false;
              }
            }
          }
          // Identifying existing flagships in the database, so we don't have to insert them again.
          Iterator<IPProgram> iterator = project.getFlagships().iterator();
          while (iterator.hasNext()) {
            if (previousFlagships.contains(iterator.next())) {
              iterator.remove();
            }
          }
          // Adding new Flagship Project Focuses.
          for (IPProgram programToAdd : project.getFlagships()) {
            saved =
              ipProgramManager.saveProjectFocus(project.getId(), programToAdd.getId(), this.getCurrentUser(),
                this.getJustification());
            if (!saved) {
              success = false;
            }
          }
          // Stop here if a something bad happened.
          if (!success) {
            this.addActionError(this.getText("saving.problem"));
            return BaseAction.INPUT;
          }
        }
        // ----- END SAVING IPPrograms (Flagships and Regions) -----

      } else {
        // User is PL, thus, only save title and summary.

        // We set the values that changed to the previous project
        // in order to prevent unauthorized changes.
        previousProject.setTitle(project.getTitle()); // setting the possible new title.
        previousProject.setSummary(project.getSummary()); // setting the possible new summary.
        result = projectManager.saveProjectDescription(previousProject, this.getCurrentUser(), this.getJustification());
        if (result < 0) {
          this.addActionError(this.getText("saving.problem"));
          return BaseAction.INPUT;
        }
      }

      // If there are some warnings, show a different message: Saving with problems
      if (this.getActionMessages().size() > 0) {
        this.addActionMessage(this.getText("saving.saved.problem"));
        return BaseAction.INPUT;
      } else {
        this.addActionMessage(this.getText("saving.success",
          new String[] {this.getText("preplanning.projectDescription.title")}));
        return BaseAction.SUCCESS;
      }
    } else {
      LOG.warn("User {} tried to save information in Project Description without having enough privileges!", this
        .getCurrentUser().getId());
    }
    return BaseAction.ERROR;

  }

  @Override
  public String save() {
    if (this.isEditable()) {

      // If the user can edit the dates, delete the budgets that correspond to years that are not linked to the
      // project anymore to prevent errors in the project budget section.
      if (securityContext.canEditStartDate() || securityContext.canEditEndDate()) {
        List<Integer> currentYears = project.getAllYears();
        List<Integer> previousYears = previousProject.getAllYears();
        for (Integer previousYear : previousYears) {
          if (!currentYears.contains(previousYear)) {
            budgetManager.deleteBudgetsByYear(projectID, previousYear.intValue());
          }
        }
      }

      // Update only the values to which the user is authorized to modify

      previousProject.setTitle(project.getTitle());

      if (securityContext.canEditManagementLiaison()) {
        previousProject.setLiaisonInstitution(project.getLiaisonInstitution());
      }

      if (securityContext.canEditManagementLiaison()) {
        previousProject.setOwner(project.getOwner());
      }

      if (securityContext.canEditStartDate()) {
        previousProject.setStartDate(project.getStartDate());
      }

      if (securityContext.canEditEndDate()) {
        previousProject.setEndDate(project.getEndDate());
      }

      if (securityContext.canAllowProjectWorkplanUpload()) {
        // TODO - Check if this permission changes when the checkbox is disabled.
        previousProject.setWorkplanRequired(project.isWorkplanRequired());

        if (previousProject.isCoreProject() && previousProject.isWorkplanRequired()) {
          // TODO - Check if user attached a file, upload it and save the file name.
          // uploadFile();
        }
      }

      if (!project.isCoreProject()) {
        if (securityContext.canUploadBilateralContract()) {
          // TODO - Check if user attached a file, upload it and save the file name.
          // uploadFile();
        }
      }

      previousProject.setSummary(project.getSummary());


      if (!project.isCoreProject()) {
        previousProject.setLinkedCoreProjects(project.getLinkedCoreProjects());
      }

      // Save the information
      int result =
        projectManager.saveProjectDescription(previousProject, this.getCurrentUser(), this.getJustification());

      if (result < 0) {
        this.addActionError(this.getText("saving.problem"));
        LOG.warn("There was a problem saving the project description.");
        return BaseAction.INPUT;
      }

      // Save the regions and flagships

      if (securityContext.canEditProjectFlagships()) {
        List<IPProgram> previousFlagships = previousProject.getFlagships();
        List<IPProgram> flagships = project.getFlagships();
        boolean saved = true;

        // TODO - To allow de-select flagships and regions we need to make
        // validations in the project outcomes

        // Save only the new flagships
        for (IPProgram flagship : flagships) {
          if (!previousFlagships.contains(flagship)) {
            saved =
              true && ipProgramManager.saveProjectFocus(project.getId(), flagship.getId(), this.getCurrentUser(),
                this.getJustification());
          }
        }

        if (!saved) {
          this.addActionError(this.getText("saving.problem"));
          LOG.warn("There was a problem saving the project flagships.");
          return BaseAction.INPUT;
        }
      }

      if (securityContext.canEditProjectRegions()) {
        List<IPProgram> previousRegions = previousProject.getRegions();
        List<IPProgram> regions = project.getRegions();
        boolean saved = true;

        // Save only the new regions
        for (IPProgram region : project.getRegions()) {
          if (!previousRegions.contains(region)) {
            saved =
              saved
                && ipProgramManager.saveProjectFocus(project.getId(), region.getId(), this.getCurrentUser(),
                  this.getJustification());
          }
        }

        if (!saved) {
          this.addActionError(this.getText("saving.problem"));
          LOG.warn("There was a problem saving the project regions.");
          return BaseAction.INPUT;
        }
      }

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

  public void setIpProgramFlagships(List<IPProgram> ipProgramFlagships) {
    this.ipProgramFlagships = ipProgramFlagships;
  }

  public void setIpProgramRegions(List<IPProgram> ipProgramRegions) {
    this.ipProgramRegions = ipProgramRegions;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  @Override
  public void validate() {
    if (this.isHttpPost()) {
      validator.validate(this, project);
    }
  }
}
