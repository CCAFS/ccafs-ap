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
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Iterator;
import java.util.List;

import org.cgiar.ccafs.ap.data.manager.BudgetManager;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectDescriptionPlanningAction extends BaseAction {


  private static final long serialVersionUID = 2845669913596494699L;

  // Manager
  private ProjectManager projectManager;
  private IPProgramManager ipProgramManager;
  private UserManager userManager;
  private BudgetManager budgetManager;

  private static Logger LOG = LoggerFactory.getLogger(ProjectDescriptionPlanningAction.class);

  // Model for the front-end
  private List<IPProgram> ipProgramRegions;
  private List<IPProgram> ipProgramFlagships;
// private List<IPCrossCutting> ipCrossCuttings;
  private List<User> allOwners;

  // Model for the back-end
  private Project previousProject;
  private Project project;
  private int projectID;


  @Inject
  public ProjectDescriptionPlanningAction(APConfig config, ProjectManager projectManager,
    IPProgramManager ipProgramManager, UserManager userManager, BudgetManager budgetManager) {
    super(config);
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.userManager = userManager;
    this.budgetManager = budgetManager;
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

    // If the user is not admin or the project owner, we should keep some information
    // unmutable
    previousProject = new Project();
    previousProject.setId(project.getId());
    previousProject.setOwner(project.getOwner());
    previousProject.setTitle(project.getTitle());
    previousProject.setStartDate(project.getStartDate());
    previousProject.setEndDate(project.getEndDate());
  }

  @Override
  public String save() {

    if (this.isSaveable()) {
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

      // ----- SAVING Project description -----
      int result = 0;
      // if user is project owner or FPL/RPL, he is able to fully edit.
      if (this.isFullEditable()) {
        result = projectManager.saveProjectDescription(project);

        if (result < 0) {
          addActionError(getText("saving.problem"));
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
          addActionWarning(getText("preplanning.projectDescription.noRegions"));
        }
        if (project.getFlagships().isEmpty()) {
          addActionWarning(getText("preplanning.projectDescription.noFlagships"));
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
            saved = ipProgramManager.saveProjectFocus(project.getId(), programToAdd.getId());
            if (!saved) {
              success = false;
            }
          }
          // Stop here if a something bad happened.
          if (!success) {
            addActionError(getText("saving.problem"));
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
            saved = ipProgramManager.saveProjectFocus(project.getId(), programToAdd.getId());
            if (!saved) {
              success = false;
            }
          }
          // Stop here if a something bad happened.
          if (!success) {
            addActionError(getText("saving.problem"));
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
        result = projectManager.saveProjectDescription(previousProject);
        if (result < 0) {
          addActionError(getText("saving.problem"));
          return BaseAction.INPUT;
        }
      }

      // If there are some warnings, show a different message: Saving with problems
      if (getActionMessages().size() > 0) {
        addActionMessage(getText("saving.saved.problem"));
        return BaseAction.INPUT;
      } else {
        addActionMessage(getText("saving.success", new String[] {getText("preplanning.projectDescription.title")}));
        return BaseAction.SUCCESS;
      }
    } else {
      LOG.warn("User (employee_id={}, email={}) tried to save information without having enough privileges!",
        new Object[] {this.getCurrentUser().getEmployeeId(), this.getCurrentUser().getEmail()});
    }
    return BaseAction.ERROR;

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
}