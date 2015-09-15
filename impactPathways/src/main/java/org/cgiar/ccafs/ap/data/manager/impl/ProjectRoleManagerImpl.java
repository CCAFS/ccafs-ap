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

import org.cgiar.ccafs.ap.data.dao.ProjectRoleDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectRoleManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectRoleManagerImpl implements ProjectRoleManager {

  private ProjectRoleDAO projectRoleDAO;

  @Inject
  public ProjectRoleManagerImpl(ProjectRoleDAO projectRoleDAO) {
    this.projectRoleDAO = projectRoleDAO;
  }

  private boolean deleteProjectRoles(Project project) {
    return projectRoleDAO.removeProjectRoles(project.getId());
  }

  @Override
  public boolean saveProjectRoles(Project project, User user, String justification) {
    boolean success = true;
    // First remove the project roles saved previously if any
    success = success && this.deleteProjectRoles(project);

    // Save the leaders and coordinators
    success = success && projectRoleDAO.addProjectRoles(project.getId());
    return success;
  }
}
