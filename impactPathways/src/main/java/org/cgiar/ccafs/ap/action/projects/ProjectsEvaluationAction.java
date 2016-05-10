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
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.security.data.manager.UserRoleManagerImpl;
import org.cgiar.ccafs.security.data.model.UserRole;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;


public class ProjectsEvaluationAction extends BaseAction {


  private static final long serialVersionUID = 2845677913596494699L;


  // Manager
  private final ProjectManager projectManager;
  private final LiaisonInstitutionManager liaisonInstitutionManager;
  private final UserRoleManagerImpl userRoleManager;
  // Model for the back-end
  private List<Project> projects;
  private List<Project> allProjects;


  @Inject
  public ProjectsEvaluationAction(APConfig config, ProjectManager projectManager, UserRoleManagerImpl userRoleManager,
    LiaisonInstitutionManager liaisonInstitutionManager) {
    super(config);
    this.projectManager = projectManager;
    this.userRoleManager = userRoleManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
  }


  public List<Project> getAllProjects() {
    return allProjects;
  }


  public List<Project> getProjects() {
    return projects;
  }


  @Override
  public void prepare() {
    projects = new ArrayList<>();

    final Set<Project> myProjects = new HashSet<Project>();

    final List<UserRole> roles = userRoleManager.getUserRolesByUserID(String.valueOf(this.getCurrentUser().getId()));
    for (final UserRole userRole : roles) {

      switch (userRole.getId()) {
        case APConstants.ROLE_ADMIN:
        case APConstants.ROLE_EXTERNAL_EVALUATOR:
        case APConstants.ROLE_FLAGSHIP_PROGRAM_LEADER:
        case APConstants.ROLE_PROJECT_LEADER:
        case APConstants.ROLE_COORDINATING_UNIT:
        case APConstants.ROLE_PROGRAM_DIRECTOR_EVALUATOR:

        case APConstants.ROLE_REGIONAL_PROGRAM_LEADER:
          int liaisonInstitutionID = 0;

          try {
            liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().get(0).getId();
          } catch (final Exception e) {
            liaisonInstitutionID = 2;
          }
          final LiaisonInstitution currentLiaisonInstitution =
            liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);
          if (currentLiaisonInstitution.getIpProgram() == null) {
            currentLiaisonInstitution.setIpProgram("1");
          }
          final List<Project> projectsBd =
            projectManager.getProjectEvaluationInfo(this.getCurrentReportingYear(), userRole.getId(),
              this.getCurrentUser().getId(), Integer.parseInt(currentLiaisonInstitution.getIpProgram()));

          myProjects.addAll(projectsBd);

          break;

        default:
          break;
      }


    }
    projects = new ArrayList<>();

    projects.addAll(myProjects);


  }

  public void setAllProjects(List<Project> allProjects) {
    this.allProjects = allProjects;
  }


}
