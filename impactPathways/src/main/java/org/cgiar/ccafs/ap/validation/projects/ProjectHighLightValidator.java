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

package org.cgiar.ccafs.ap.validation.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class ProjectHighLightValidator extends BaseValidator {

  private ProjectValidator projectValidator;
  private boolean fields = false;

  @Inject
  public ProjectHighLightValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }


  public void validate(BaseAction action, Project project, ProjectHighligths highLigths, String cycle) {
    if (project != null) {
      // If project is CORE or CO-FUNDED
      if (project.isCoreProject() || project.isCoFundedProject()) {
        // this.validateProjectJustification(action, project);
        // this.validateLessonsLearn(action, project, "highlights");
        this.ValidateHightLigth(action, highLigths);
        this.ValidateHightAuthor(action, highLigths);
        this.ValidateHightTitle(action, highLigths);
        this.ValidateYear(action, highLigths);

      }
      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "highlights");
    }
  }

  private void ValidateHightAuthor(BaseAction action, ProjectHighligths higligth) {

    if (!this.isValidString(higligth.getAuthor())) {
      this.addMessage("Author");
      this.addMissingField("reporting.projectHighligth.author");
    }
  }

  private void ValidateHightLigth(BaseAction action, ProjectHighligths higligth) {

    if (higligth.getTypesids().size() == 0) {
      this.addMessage(action.getText("reporting.projectHighligth.types").toLowerCase());
      this.addMissingField("reporting.projectHighligth.types");
    }


  }

  private void ValidateHightTitle(BaseAction action, ProjectHighligths higligth) {

    if (!this.isValidString(higligth.getTitle())) {
      this.addMessage(action.getText("Title"));
      this.addMissingField("reporting.projectHighligth.title");
    }
  }


  private void ValidateYear(BaseAction action, ProjectHighligths higligth) {

    if (!(higligth.getYear() > 0)) {
      this.addMessage("Year");
      this.addMissingField("reporting.projectHighligth.year");

    }
  }
}
