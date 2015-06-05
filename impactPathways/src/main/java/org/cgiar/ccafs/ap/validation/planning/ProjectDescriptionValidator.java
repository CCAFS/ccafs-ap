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

import java.util.Date;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectDescriptionValidator extends BaseValidator {

  public ProjectDescriptionValidator() {
    super();
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      this.validateTitle(action, project.getTitle());
    }
  }

  public void validateEndDate(BaseAction action, Date endDate) {

  }

  public void validateLiaisonContactPerson(BaseAction action, String title) {

  }

  public void validateManagementLiaison(BaseAction action, Date startDate) {

  }

  public void validateProjectSummary(BaseAction action, Date startDate) {
  }

  public void validateStartDate(BaseAction action, Date startDate) {

  }

  public void validateTitle(BaseAction action, String title) {
    if (!this.isValidString(title)) {
      this.addMessage(this.getText("validation.required", this.getText("planning.projectDescription.projectTitle")));
    }
  }

}
