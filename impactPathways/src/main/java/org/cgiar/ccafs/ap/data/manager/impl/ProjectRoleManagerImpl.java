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

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.ProjectRoleDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectRoleManager;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
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
    // First remove the project roles saved previously if any
    this.deleteProjectRoles(project);

    // Save the leaders and coordinators
    for (ProjectPartner projectPartner : project.getProjectPartners()) {
      for (PartnerPerson partnerPerson : projectPartner.getPartnerPersons()) {
        if (partnerPerson.getType().equals(APConstants.PROJECT_PARTNER_PL)) {
          projectRoleDAO.addProjectRole(project.getId(), user.getId(), APConstants.PROJECT_PARTNER_PL);
        } else if (partnerPerson.getType().equals(APConstants.PROJECT_PARTNER_PC)) {
          // TODO - Create project coordinator role and assign it here
        }
      }
    }

    return false;
  }
}
