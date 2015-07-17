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
import org.cgiar.ccafs.ap.action.planning.ProjectOutputsPlanningAction;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.OutputOverviewValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectOutputsPlanningValidator extends BaseValidator {

  private static final long serialVersionUID = -1204775953613372275L;
  private ProjectValidator projectValidator;
  private OutputOverviewValidator overviewValidator;

  @Inject
  public ProjectOutputsPlanningValidator(ProjectValidator projectValidator, OutputOverviewValidator overviewValidator) {
    this.projectValidator = projectValidator;
    this.overviewValidator = overviewValidator;
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      this.validateProjectJustification(action, project);

      if (!project.isBilateralProject()) {
        this.validateOutputOverviews(action, project);
      }

      if (validationMessage.length() > 0) {
        String msg = " " + this.getText("saving.missingFields", new String[] {validationMessage.toString()});
        action.addActionMessage(msg);
      }
    }
  }

  public void validateAnnualContribution(Project project, OutputOverview overview, ProjectOutputsPlanningAction action) {
    StringBuilder msg = new StringBuilder();

    if (!overviewValidator.isValidExpectedAnnualContribution(overview.getExpectedAnnualContribution())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = action.getMOGIndex(output);
      msg.setLength(0);
      msg.append(this.getText("planning.projectOutputs.expectedBulletPoints.readText",
        new String[] {String.valueOf(overview.getYear())}).toLowerCase());
      msg.append("( ");
      msg.append(output.getProgram().getAcronym());
      msg.append(" - MOG #");
      msg.append(index);
      msg.append(") ");

      this.addMessage(msg.toString());
    }
  }

  public void validateOutputOverviews(BaseAction action, Project project) {
    ProjectOutputsPlanningAction outputsAction = (ProjectOutputsPlanningAction) action;


    if (projectValidator.isValidOutputOverviews(project.getOutputsOverview())) {
      for (OutputOverview overview : project.getOutputsOverview()) {
        this.validateAnnualContribution(project, overview, outputsAction);
        this.validateSocialDimmension(project, overview, outputsAction);
      }
    } else {
      // TODO - Add a custom message to say that there is no MOG overviews
    }
  }

  public void validateSocialDimmension(Project project, OutputOverview overview, ProjectOutputsPlanningAction action) {
    StringBuilder msg = new StringBuilder();

    if (!overviewValidator.isValidExpectedAnnualContribution(overview.getSocialInclusionDimmension())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = action.getMOGIndex(output);
      msg.setLength(0);
      msg.append(this.getText("planning.projectOutputs.expectedSocialAndGenderPlan.readText",
        new String[] {String.valueOf(overview.getYear())}).toLowerCase());
      msg.append("( ");
      msg.append(output.getProgram().getAcronym());
      msg.append(" - MOG #");
      msg.append(index);
      msg.append(") ");

      this.addMessage(msg.toString());
    }
  }
}
