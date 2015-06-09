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
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.Date;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectDescriptionValidator extends BaseValidator {

  private static final long serialVersionUID = -4871185832403702671L;
  private ProjectValidator projectValidator;

  @Inject
  public ProjectDescriptionValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      this.validateTitle(action, project.getTitle());
      this.validateManagementLiaison(action, project.getLiaisonInstitution());
      this.validateLiaisonContactPerson(action, project.getOwner());
      this.validateStartDate(action, project.getStartDate());
      this.validateEndDate(action, project.getEndDate());

      if (validationMessage.length() > 0) {
        action.addActionWarning(this.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }
    }
  }

  public void validateEndDate(BaseAction action, Date endDate) {
    if (!projectValidator.isValidEndDate(endDate)) {
      this.addMessage(this.getText("preplanning.projectDescription.endDate").toLowerCase());
    }
  }

  public void validateLiaisonContactPerson(BaseAction action, User user) {
    if (!projectValidator.isValidOwner(user)) {
      this.addMessage(this.getText("preplanning.projectDescription.projectownercontactperson").toLowerCase());
    }
  }

  public void validateManagementLiaison(BaseAction action, LiaisonInstitution institution) {
    if (!projectValidator.isValidLiaisonInstitution(institution)) {
      this.addMessage(this.getText("planning.projectDescription.programCreator").toLowerCase());
    }
  }

  public void validateProjectSummary(BaseAction action, String summary) {
    if (!projectValidator.isValidSummary(summary)) {
      this.addMessage(this.getText("preplanning.projectDescription.projectSummary").toLowerCase());
    }
  }

  public void validateStartDate(BaseAction action, Date startDate) {
    if (!projectValidator.isValidStartDate(startDate)) {
      this.addMessage(this.getText("preplanning.projectDescription.startDate").toLowerCase());
    }
  }

  public void validateTitle(BaseAction action, String title) {
    if (!projectValidator.isValidTitle(title)) {
      this.addMessage(this.getText("planning.projectDescription.projectTitle").toLowerCase());
    }
  }

}
