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
package org.cgiar.ccafs.ap.action.preplanning;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectDescriptionAction extends BaseAction {


  private static final long serialVersionUID = 2845669913596494699L;

  // Manager
  private ProjectManager projectManager;
  private IPProgramManager ipProgramManager;
  private IPCrossCuttingManager ipCrossCuttingManager;
  private UserManager userManager;

  private static Logger LOG = LoggerFactory.getLogger(ProjectDescriptionAction.class);

  // Model for the front-end
  private List<IPProgram> ipProgramRegions;
  private List<IPProgram> ipProgramFlagships;
  private List<IPCrossCutting> ipCrossCuttings;
  private List<User> allOwners;

  // Model for the back-end
  private Project project;
  private int projectID;

  @Inject
  public ProjectDescriptionAction(APConfig config, ProjectManager projectManager, IPProgramManager ipProgramManager,
    IPCrossCuttingManager ipCrossCuttingManager, UserManager userManager) {
    super(config);
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.ipCrossCuttingManager = ipCrossCuttingManager;
    this.userManager = userManager;
  }

  @Override
  public String execute() throws Exception {
    /*
     * If there project Id is not in the parameter or if the is not a project with that id, we must redirect to a
     * NOT_FOUND page.
     */
    if (projectID == -1) {
      return NOT_FOUND;
    }
    return super.execute();
  }

  public List<User> getAllOwners() {
    return allOwners;
  }

  /**
   * TODO HT - To Document
   *
   * @param ipProgramId
   * @return
   */
  public String getComposedName(int ipProgramId) {
    IPProgram program = this.ipProgramFlagships.get(ipProgramId);
    return program.getAcronym() + ": " + program.getName();
  }


  /**
   * This method returns an array of cross cutting ids depenging on the project.crossCuttings attribute.
   *
   * @return an array of integers.
   */
  public int[] getCrossCuttingIds() {
    if (this.project.getCrossCuttings() != null) {
      int[] ids = new int[this.project.getCrossCuttings().size()];
      for (int c = 0; c < ids.length; c++) {
        ids[c] = this.project.getCrossCuttings().get(c).getId();
      }
      return ids;
    }
    return null;
  }

  public int getEndYear() {
    return config.getEndYear();
  }

  /**
   * This method returns an array of flagship ids depenging on the project.flagships attribute.
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

  public List<IPCrossCutting> getIpCrossCuttings() {
    return ipCrossCuttings;
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

  /**
   * This method returns an array of region ids depenging on the project.regions attribute.
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
    // System.out.println(userContacts);

    // Getting the information of the Regions program for the View
    ipProgramRegions = ipProgramManager.getProgramsByType(APConstants.REGION_PROGRAM_TYPE);

    // Getting the information of the Flagships program for the View
    ipProgramFlagships = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);

    // Getting the information of the Cross Cutting Theme for the View
    ipCrossCuttings = ipCrossCuttingManager.getIPCrossCuttings();

    // Getting project
    project = projectManager.getProject(projectID);

    // Getting the information of the Flagships Program associated with the project
    project.setRegions(ipProgramManager.getProjectFocuses(projectID, APConstants.REGION_PROGRAM_TYPE));
    // Getting the information of the Regions Program associated with the project
    project.setFlagships(ipProgramManager.getProjectFocuses(projectID, APConstants.FLAGSHIP_PROGRAM_TYPE));
    // Getting the information of the Cross Cutting Theme associated with the project
    project.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByProject(projectID));


  }

  @Override
  public String save() {
    // Saving project description
    userManager.getEmployeeID(project.getOwner());
    int result = projectManager.saveProjectDescription(project);
    if (result < 0) {
      addActionError(getText("saving.problem"));
      return BaseAction.INPUT;
    }

    // Saving IPPrograms (Flagships and Regions)


    addActionMessage(getText("saving.success", new String[] {getText("preplanning.projectDescription.title")}));
    return BaseAction.SUCCESS;
  }

  public void setIpCrossCuttings(List<IPCrossCutting> ipCrossCuttings) {
    this.ipCrossCuttings = ipCrossCuttings;
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
