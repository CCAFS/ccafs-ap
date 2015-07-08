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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectCofinancingLinkageDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectCofinancingLinkageManagerImpl implements ProjectCofinancingLinkageManager {

  private ProjectCofinancingLinkageDAO linkedCoreProjectsDAO;

  @Inject
  public ProjectCofinancingLinkageManagerImpl(ProjectCofinancingLinkageDAO linkedCoreProjectsDAO) {
    this.linkedCoreProjectsDAO = linkedCoreProjectsDAO;
  }

  @Override
  public boolean
    deletedLinkedProjects(Project project, List<Integer> linkedProjects, User user, String justification) {
    return linkedCoreProjectsDAO.removeLinkedProjects(project.getId(), linkedProjects, user.getId(), justification);
  }

  @Override
  public List<Project> getLinkedProjects(int projectID) {
    List<Project> projects = new ArrayList<>();
    List<Map<String, String>> projectsInfo = linkedCoreProjectsDAO.getLinkedProjects(projectID);
    for (Map<String, String> projectInfo : projectsInfo) {
      Project project = new Project();
      project.setId(Integer.parseInt(projectInfo.get("id")));
      project.setTitle(projectInfo.get("title"));

      projects.add(project);
    }
    return projects;
  }

  @Override
  public boolean saveLinkedProjects(Project project, User user, String justification) {
    List<Integer> bilateralProjectsIDs = new ArrayList<>();
    for (Project bilateralProject : project.getLinkedProjects()) {
      bilateralProjectsIDs.add(bilateralProject.getId());
    }

    return linkedCoreProjectsDAO.saveLinkedProjects(project.getId(), bilateralProjectsIDs, user.getId(), justification);
  }

}