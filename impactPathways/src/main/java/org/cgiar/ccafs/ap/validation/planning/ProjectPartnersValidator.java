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

package org.cgiar.ccafs.ap.validation.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Carlos Alberto Martínez M. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectPartnersValidator extends BaseValidator {

  private static final long serialVersionUID = -4087794336343347402L;

  @Inject
  public ProjectPartnersValidator(ProjectValidator projectValidator) {
    super();
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      // All projects must specify the project leader
      if (project.getLeader() == null) {
        String msg = this.getText("validation.required", new String[] {this.getText("home.glossary.projectLeader")});
        action.addActionError(msg);
      }

      if (project.isBilateralProject()) {
        this.validateBilateralProject(action, project);
      } else {
        this.validateCCAFSProject(action, project);
      }

    }
  }

  private void validateBilateralProject(BaseAction action, Project project) {

  }

  private void validateCCAFSProject(BaseAction action, Project project) {

  }

  private boolean validateLeaderAndCoordinator(BaseAction action, Project project) {
    if (project.getLeader() != null && project.getCoordinator() != null && project.getLeader().getId() != -1
      && project.getCoordinator().getId() != -1 && project.getLeader().getId() == project.getCoordinator().getId()) {
      action.addActionError(this.getText("planning.projectPartners.duplicated.PLPC"));
      action.addFieldError("contact-person-leader", this.getText("validation.duplicated"));
      action.addFieldError("contact-person-coordinator", this.getText("validation.duplicated"));
      return true;
    }
    return false;
  }


}
