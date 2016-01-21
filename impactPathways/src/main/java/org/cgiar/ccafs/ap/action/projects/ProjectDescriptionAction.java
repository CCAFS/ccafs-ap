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
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.validation.projects.ProjectDescriptionValidator;
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


public class ProjectDescriptionAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(ProjectDescriptionAction.class);
  private static final long serialVersionUID = 2845669913596494699L;

  // Manager
  private ProjectManager projectManager;
  private IPProgramManager ipProgramManager;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private UserManager userManager;
  private BudgetManager budgetManager;
  private HistoryManager historyManager;
  private ProjectCofinancingLinkageManager linkedProjectManager;

  // Model for the front-end
  private List<IPProgram> ipProgramRegions;
  private List<IPProgram> ipProgramFlagships;
  private List<LiaisonInstitution> liaisonInstitutions;
  private Map<String, String> projectStauses;
  private List<User> allOwners;
  private Map<String, String> projectTypes;

  // Model for the back-end
  private Project previousProject;
  private Project project;
  private int projectID;

  // These variables can contain either the information of the project workplan or the bilateral contract proposal
  private File file;
  private File fileReporting;
  private String fileContentType;
  private String fileFileName;
  private String fileReportingFileName;

  private ProjectDescriptionValidator validator;


  @Inject
  public ProjectDescriptionAction(APConfig config, ProjectManager projectManager, IPProgramManager ipProgramManager,
    UserManager userManager, BudgetManager budgetManager, LiaisonInstitutionManager liaisonInstitutionManager,
    ProjectCofinancingLinkageManager linkedCoreProjectManager, HistoryManager historyManager,
    ProjectDescriptionValidator validator) {
    super(config);
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.userManager = userManager;
    this.budgetManager = budgetManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.linkedProjectManager = linkedCoreProjectManager;
    this.historyManager = historyManager;
    this.validator = validator;
  }


  public List<User> getAllOwners() {
    return allOwners;
  }


  /**
   * Return the absolute path where the bilateral contract is or should be located.
   * 
   * @return complete path where the image is stored
   */
  private String getAnnualReportAbsolutePath() {
    return config.getUploadsBaseFolder() + File.separator + this.getAnualReportRelativePath() + File.separator;
  }

  private String getAnualReportRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getAnualReportFolder() + File.separator;
  }

  public String getAnualReportURL() {
    return config.getDownloadURL() + "/" + this.getAnualReportRelativePath().replace('\\', '/');
  }

  /**
   * Return the absolute path where the bilateral contract is or should be located.
   * 
   * @return complete path where the image is stored
   */
  private String getBilateralContractAbsolutePath() {
    return config.getUploadsBaseFolder() + File.separator + this.getBilateralProposalRelativePath() + File.separator;
  }

  public String getBilateralContractURL() {
    return config.getDownloadURL() + "/" + this.getBilateralProposalRelativePath().replace('\\', '/');
  }


  private String getBilateralProposalRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getBilateralProjectContractProposalFolder() + File.separator;
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

  public File getFile() {
    return file;
  }

  public String getFileContentType() {
    return fileContentType;
  }

  public String getFileFileName() {
    return fileFileName;
  }

  public File getFileReporting() {
    return fileReporting;
  }

  public String getFileReportingFileName() {
    return fileReportingFileName;
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

  public Map<String, String> getProjectStauses() {
    return projectStauses;
  }

  public Map<String, String> getProjectTypes() {
    return projectTypes;
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

  private String getWorkplanRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getProjectWorkplanFolder() + File.separator;
  }

  public String getWorkplanURL() {
    return config.getDownloadURL() + "/" + this.getWorkplanRelativePath().replace('\\', '/');
  }

  /**
   * Return the absolute path where the work plan is or should be located.
   * 
   * @param workplan name
   * @return complete path where the image is stored
   */
  private String getWorplansAbsolutePath() {
    return config.getUploadsBaseFolder() + File.separator + this.getWorkplanRelativePath() + File.separator;
  }

  public boolean isNewProject() {
    return project.isNew(config.getCurrentPlanningStartDate());
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

    projectStauses = new HashMap<>();
    List<ProjectStatusEnum> list = Arrays.asList(ProjectStatusEnum.values());
    for (ProjectStatusEnum projectStatusEnum : list) {
      projectStauses.put(projectStatusEnum.getStatusId(), projectStatusEnum.getStatus());
    }
    // Getting project
    project = projectManager.getProject(projectID);
    if (project != null) {
      // Getting the information of the Flagships Program associated with the project
      project.setRegions(ipProgramManager.getProjectFocuses(projectID, APConstants.REGION_PROGRAM_TYPE));
      // Getting the information of the Regions Program associated with the project
      project.setFlagships(ipProgramManager.getProjectFocuses(projectID, APConstants.FLAGSHIP_PROGRAM_TYPE));
    }

    // If project is CCAFS cofounded, we should load the core projects linked to it.
    if (!project.isBilateralProject()) {
      project.setLinkedProjects(linkedProjectManager.getLinkedBilateralProjects(projectID));
    } else {
      project.setLinkedProjects(linkedProjectManager.getLinkedCoreProjects(projectID));
    }

    projectTypes = new HashMap<>();
    projectTypes.put(APConstants.PROJECT_CORE, this.getText("planning.projectDescription.projectType.core"));
    projectTypes.put(APConstants.PROJECT_BILATERAL, this.getText("planning.projectDescription.projectType.bilateral"));
    projectTypes.put(APConstants.PROJECT_CCAFS_COFUNDED,
      this.getText("planning.projectDescription.projectType.cofounded"));
    projectTypes.put(APConstants.PROJECT_BILATERAL, this.getText("planning.projectDescription.projectType.bilateral"));

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
    previousProject.setBilateralContractRequired(project.isBilateralContractRequired());
    previousProject.setWorkplanName(project.getWorkplanName());
    previousProject.setBilateralContractProposalName(project.getBilateralContractProposalName());
    previousProject.setAnnualReportDonor(project.getAnnualReportDonor());
    previousProject.setStatus(project.getStatus());
    previousProject.setStatusDescription(project.getStatusDescription());

    if (project.getLinkedProjects() != null) {
      List<Project> linkedProjects = new ArrayList<>();
      for (Project p : project.getLinkedProjects()) {
        linkedProjects.add(new Project(p.getId()));
      }

      previousProject.setLinkedProjects(linkedProjects);
    }

    super.setHistory(historyManager.getProjectDescriptionHistory(project.getId()));

    if (this.isHttpPost()) {
      if (project.getLinkedProjects() != null) {
        project.getLinkedProjects().clear();
      }

    }
    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
  }


  @Override
  public String save() {
    if (this.hasProjectPermission("update", projectID)) {
      // If the user can edit the dates, delete the budgets that correspond to years that are not linked to the
      // project anymore to prevent errors in the project budget section.
      if (this.hasProjectPermission("startDate", projectID) || this.hasProjectPermission("endDate", projectID)) {
        List<Integer> currentYears = project.getAllYears();
        List<Integer> previousYears = previousProject.getAllYears();
        for (Integer previousYear : previousYears) {
          if (!currentYears.contains(previousYear)) {
            budgetManager.deleteBudgetsByYear(projectID, previousYear.intValue(), this.getCurrentUser(),
              this.getJustification());
          }
        }
        budgetManager.deleteBudgetsFromUnexistentYears(projectID);
      }

      // Update only the values to which the user is authorized to modify
      if (this.hasProjectPermission("title", projectID)) {
        previousProject.setTitle(project.getTitle());
      }

      if (this.hasProjectPermission("managementLiaison", projectID)) {
        previousProject.setLiaisonInstitution(project.getLiaisonInstitution());
        previousProject.setOwner(project.getOwner());
      }

      if (this.hasProjectPermission("startDate", projectID)) {
        previousProject.setStartDate(project.getStartDate());
      }

      if (this.hasProjectPermission("endDate", projectID)) {
        previousProject.setEndDate(project.getEndDate());
      }

      if (this.hasProjectPermission("workplan", projectID)) {
        previousProject.setWorkplanRequired(project.isWorkplanRequired());
        // previousProject.setBilateralContractRequired(project.isBilateralContractRequired());
      }


      if (!project.isBilateralProject() && previousProject.isWorkplanRequired()) {
        if (file != null) {
          if (previousProject.getWorkplanName() != null) {
            FileManager.deleteFile(this.getWorplansAbsolutePath() + previousProject.getWorkplanName());
          }

          previousProject.setWorkplanName(fileFileName);
          FileManager.copyFile(file, this.getWorplansAbsolutePath() + previousProject.getWorkplanName());
        } else {

          previousProject.setWorkplanName(project.getWorkplanName());
          if (project.getWorkplanName() != null && project.getWorkplanName().isEmpty()) {
            FileManager.deleteFile(this.getWorplansAbsolutePath() + previousProject.getWorkplanName());
            previousProject.setWorkplanName("");
          }
        }
      } else if (previousProject.isBilateralProject()) {
        if (file != null) {
          if (previousProject.getBilateralContractProposalName() != null) {
            FileManager
              .deleteFile(this.getBilateralContractAbsolutePath() + previousProject.getBilateralContractProposalName());
          }

          previousProject.setBilateralContractProposalName(fileFileName);
          FileManager.copyFile(file,
            this.getBilateralContractAbsolutePath() + previousProject.getBilateralContractProposalName());
        } else {

          if (previousProject.getBilateralContractProposalName() != null
            && previousProject.getBilateralContractProposalName().isEmpty()) {
            FileManager
              .deleteFile(this.getBilateralContractAbsolutePath() + previousProject.getBilateralContractProposalName());
            previousProject.setBilateralContractProposalName("");
          }
        }
      }

      // TODO - Update the type of projects and all the implications.
      // previousProject.setType(project.getType());

      if (project.isBilateralProject()) {
        if (this.hasProjectPermission("bilateralContract", projectID)) {
          if (file != null) {
            FileManager
              .deleteFile(this.getBilateralContractAbsolutePath() + previousProject.getBilateralContractProposalName());
            FileManager.copyFile(file,
              this.getBilateralContractAbsolutePath() + previousProject.getBilateralContractProposalName());
            previousProject.setBilateralContractProposalName(fileFileName);
          } else {
            previousProject.setBilateralContractProposalName(project.getBilateralContractProposalName());
            if (project.getBilateralContractProposalName() != null
              && project.getBilateralContractProposalName().isEmpty()) {
              previousProject.setBilateralContractProposalName("");
              FileManager
                .deleteFile(this.getWorplansAbsolutePath() + previousProject.getBilateralContractProposalName());
            }
          }
        }
        if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
          if (fileReporting != null) {
            FileManager.deleteFile(this.getAnnualReportAbsolutePath() + previousProject.getAnnualReportDonor());
            FileManager.copyFile(fileReporting, this.getAnnualReportAbsolutePath() + fileReportingFileName);
            previousProject.setAnnualReportDonor(fileReportingFileName);
          } else {
            previousProject.setAnnualReportDonor(project.getAnnualReportDonor());
            if (project.getAnnualReportDonor() != null && !project.getAnnualReportDonor().isEmpty()) {

              FileManager.deleteFile(this.getAnnualReportAbsolutePath() + previousProject.getAnnualReportDonor());
              previousProject.setAnnualReportDonor("");
            }
          }
        }
      }

      if (this.hasProjectPermission("summary", projectID)) {
        previousProject.setSummary(project.getSummary());
      }
      if (this.hasProjectPermission("status", projectID)) {
        previousProject.setStatus(project.getStatus());
      }
      if (this.hasProjectPermission("statusDescription", projectID)) {
        previousProject.setStatusDescription(project.getStatusDescription());
      }

      // Save the information
      int result =
        projectManager.saveProjectDescription(previousProject, this.getCurrentUser(), this.getJustification());
      if (project.getLinkedProjects().isEmpty()) {
        project.setCofinancing(false);
      } else {
        project.setCofinancing(true);
      }
      projectManager.updateProjectCofinancing(project);
      if (result < 0) {
        this.addActionError(this.getText("saving.problem"));
        LOG.warn("There was a problem saving the project description.");
        return BaseAction.INPUT;
      }

      // Save the regions and flagships

      if (this.hasProjectPermission("flagships", projectID)) {
        List<IPProgram> previousFlagships = previousProject.getFlagships();
        List<IPProgram> flagships = project.getFlagships();
        boolean saved = true;

        // TODO - To allow de-select flagships and regions we need to make
        // validations in the project outcomes

        // Save only the new flagships
        for (IPProgram flagship : flagships) {
          if (!previousFlagships.contains(flagship)) {
            saved = ipProgramManager.saveProjectFocus(project.getId(), flagship.getId(), this.getCurrentUser(),
              this.getJustification());
          }
        }

        if (!saved) {
          this.addActionError(this.getText("saving.problem"));
          LOG.warn("There was a problem saving the project flagships.");
          return BaseAction.INPUT;
        }
      }

      if (this.hasProjectPermission("regions", projectID)) {
        List<IPProgram> previousRegions = previousProject.getRegions();
        boolean saved = true;

        // Save only the new regions
        for (IPProgram region : project.getRegions()) {
          if (!previousRegions.contains(region)) {
            saved = saved && ipProgramManager.saveProjectFocus(project.getId(), region.getId(), this.getCurrentUser(),
              this.getJustification());
          }
        }

        if (!saved) {
          this.addActionError(this.getText("saving.problem"));
          LOG.warn("There was a problem saving the project regions.");
          return BaseAction.INPUT;
        }
      }

      // Save the contributing core projects if any
      List<Integer> linkedProjectsToDelete = new ArrayList<>();
      for (Project p : previousProject.getLinkedProjects()) {
        if (!project.getLinkedProjects().contains(p)) {
          linkedProjectsToDelete.add(p.getId());
        }
      }

      if (!linkedProjectsToDelete.isEmpty()) {
        if (project.isBilateralProject()) {
          linkedProjectManager.deletedLinkedCoreProjects(project, linkedProjectsToDelete, this.getCurrentUser(),
            this.getJustification());
        } else {
          linkedProjectManager.deletedLinkedBilateralProjects(project, linkedProjectsToDelete, this.getCurrentUser(),
            this.getJustification());
        }
      }

      // Then save the new core projects linked
      if (!project.getLinkedProjects().isEmpty()) {
        if (project.isBilateralProject()) {
          linkedProjectManager.saveLinkedCoreProjects(project, this.getCurrentUser(), this.getJustification());
        } else {
          linkedProjectManager.saveLinkedBilateralProjects(project, this.getCurrentUser(), this.getJustification());
        }
      }


      // Adjust the type of all projects according to their links with other projects.
      projectManager.updateProjectTypes();

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

  public void setFile(File file) {
    this.file = file;
  }

  public void setFileContentType(String fileContentType) {
    this.fileContentType = fileContentType;
  }

  public void setFileFileName(String fileFileName) {
    this.fileFileName = fileFileName;
  }

  public void setFileReporting(File fileReporting) {
    this.fileReporting = fileReporting;
  }

  public void setFileReportingFileName(String fileReportingFileName) {
    this.fileReportingFileName = fileReportingFileName;
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

  public void setStatusProject(Map<String, String> projectStauses) {
    this.projectStauses = projectStauses;
  }

  @Override
  public void validate() {
    if (save) {
      if (!project.isBilateralProject() && project.isWorkplanRequired()) {
        if (file != null) {
          project.setWorkplanName(fileFileName);
        }
      }
      if (project.isBilateralProject()) {

        if (file != null) {
          project.setBilateralContractProposalName(fileFileName);
        }
      }
      validator.validate(this, project, this.getCycleName());
    }
  }
}
