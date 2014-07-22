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

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Project;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ProjectDescriptionAction extends BaseAction {


  private static final long serialVersionUID = 2845669913596494699L;

  // Manager
  private ProjectManager projectManager;

  private static Logger LOG = LoggerFactory.getLogger(ProjectDescriptionAction.class);

  // Model
  private Project projects;
  private int projectID;

  @Inject
  public ProjectDescriptionAction(APConfig config, ProjectManager projectManager) {
    super(config);
    this.projectManager = projectManager;
  }


  public Project getProject() {
    return projects;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    String projectStringID = StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID));
    try {
      projectID = Integer.parseInt(projectStringID);
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectStringID, e);
    }

    // Depending on the user that is logged-in, the list of projects will be displayed.
    /*
     * Project fakeProject = new Project();
     * fakeProject.setId(projectID);
     */
    // fakeProject.setStartDate(startDate);

    // fakeProject.setTitle("titulo de prueba");
    // fakeProject.setSummary("-------------------------");


    // Getting project list.
    // projects = projectManager.getAllProjects();
    projects = projectManager.getProject(projectID);

    System.out.println(projects);
  }
}
