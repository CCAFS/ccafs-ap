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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.OutputOverviewValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectOutputsValidator extends BaseValidator {

  // Validators
  private ProjectValidator projectValidator;
  private OutputOverviewValidator overviewValidator;

  // Managers
  private IPElementManager ipElementManager;

  public String cycle;

  @Inject
  public ProjectOutputsValidator(ProjectValidator projectValidator, OutputOverviewValidator overviewValidator,
    IPElementManager ipElementManager) {
    this.projectValidator = projectValidator;
    this.overviewValidator = overviewValidator;
    this.ipElementManager = ipElementManager;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // this.validateProjectJustification(action, project);
      this.cycle = cycle;
      // This section is required for Core, co-funded and bilateral projects that are not contributing to any core
      // project.
      if (project.isCoreProject() || project.isCoFundedProject() || project.isBilateralStandAlone()) {
        if (action.isPlanningCycle()) {
          this.validateLessonsLearn(action, project, "outputs");
        }
        this.validateOutputOverviews(action, project);
      } else {
        // If project is bilateral but is co-financing some core project, thus this section is not needed to be filled.
      }
      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        String msg = " " + action.getText("saving.missingFields", new String[] {validationMessage.toString()});
        action.addActionMessage(msg);
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "outputs");
    }
  }

  public void validateAnnualContribution(BaseAction action, Project project, OutputOverview overview, int counter) {
    StringBuilder msg = new StringBuilder();
    StringBuilder msgMOG = new StringBuilder();
    if (!overviewValidator.isValidExpectedAnnualContribution(overview.getExpectedAnnualContribution())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = ipElementManager.getMOGIndex(output);
      msg.setLength(0);
      msgMOG.setLength(0);

      msgMOG.append("( ");
      msgMOG.append(output.getProgram().getAcronym());
      msgMOG.append(" - MOG #");
      msgMOG.append(index);
      msgMOG.append(")");
      msg.append(action.getText("planning.projectOutputs.expectedBulletPoints.readText.short",
        new String[] {String.valueOf(overview.getYear()), msgMOG.toString()}));


      this.addMessage(msg.toString());
      this.addMissingField("project.outputsOverview[" + counter + "].expectedAnnualContribution");
    }
  }


  public void validateBriefSummary(BaseAction action, Project project, OutputOverview overview, int counter) {
    StringBuilder msg = new StringBuilder();
    StringBuilder msgMOG = new StringBuilder();
    if (!overviewValidator.isValidBriefSummary(overview.getBriefSummary())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = ipElementManager.getMOGIndex(output);
      msg.setLength(0);
      msgMOG.setLength(0);

      msgMOG.append("( ");
      msgMOG.append(output.getProgram().getAcronym());
      msgMOG.append(" - MOG #");
      msgMOG.append(index);
      msgMOG.append(")");
      msg.append(action.getText("planning.projectOutputs.summaryAnnualContribution",
        new String[] {String.valueOf(overview.getYear()), msgMOG.toString()}));


      this.addMessage(msg.toString());
      this.addMissingField("project.outputsOverview[" + counter + "].summaryAnnualContribution");
    }
  }

  public void validateGenderSummary(BaseAction action, Project project, OutputOverview overview, int counter) {
    StringBuilder msg = new StringBuilder();
    StringBuilder msgMOG = new StringBuilder();
    if (!overviewValidator.isValidGenderSummary(overview.getSummaryGender())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = ipElementManager.getMOGIndex(output);
      msg.setLength(0);
      msgMOG.setLength(0);
      msgMOG.append("( ");
      msgMOG.append(output.getProgram().getAcronym());
      msgMOG.append(" - MOG #");
      msgMOG.append(index);
      msgMOG.append(")");
      msg.append(action.getText("planning.projectOutputs.summarySocialInclusionDimmension",
        new String[] {String.valueOf(overview.getYear()), msgMOG.toString()}));


      this.addMessage(msg.toString());
      this.addMissingField("project.outputsOverview[" + counter + "].summarySocialInclusionDimmension");
    }
  }

  public void validateOutputOverviews(BaseAction action, Project project) {
    if (projectValidator.hasOutputOverviews(project.getOutputsOverview())) {
      int c = 0;
      if (cycle.equals(APConstants.REPORTING_SECTION)) {
        for (OutputOverview overview : project.getOutputsOverview()) {
          // Validate only current planning year which is 2016 at this moment.
          if (overview.getYear() == this.config.getReportingCurrentYear()) {
            this.validateBriefSummary(action, project, overview, c);
            this.validateGenderSummary(action, project, overview, c);
            c++;
          }
        }
      }
      if (cycle.equals(APConstants.PLANNING_SECTION)) {
        for (OutputOverview overview : project.getOutputsOverview()) {
          // Validate only current planning year which is 2016 at this moment.
          if (overview.getYear() == this.config.getPlanningCurrentYear()) {
            this.validateAnnualContribution(action, project, overview, c);
            this.validateSocialDimmension(action, project, overview, c);
            c++;
          }
        }
        for (IPElement ipElement : project.getOutputs()) {
          boolean isContain = false;
          for (OutputOverview overview : project.getOutputsOverview()) {
            if (overview.getYear() == this.config.getPlanningCurrentYear()) {
              if (overview.getOutput().equals(ipElement)) {
                isContain = true;
                break;
              }
            }
          }
          if (!isContain) {
            this.addMessage("For" + ipElement.getProgram().getAcronym() + "- MOG #"
              + ipElementManager.getMOGIndex(ipElement) + " Incomplete Information");
            this.addMissingField("project.outputsOverview.nocomplete");
          }
        }
      }
    } else {
      action.addActionMessage(action.getText("planning.projectOutputs.validation.empty"));
      this.addMissingField("project.outputsOverview.empty");
    }
  }

  public void validateSocialDimmension(BaseAction action, Project project, OutputOverview overview, int counter) {
    StringBuilder msg = new StringBuilder();
    StringBuilder msgMOG = new StringBuilder();
    if (!overviewValidator.isValidExpectedAnnualContribution(overview.getSocialInclusionDimmension())) {
      IPElement output = project.getOutput(overview.getOutput().getId());
      int index = ipElementManager.getMOGIndex(output);
      msg.setLength(0);
      msgMOG.setLength(0);

      msgMOG.append("( ");
      msgMOG.append(output.getProgram().getAcronym());
      msgMOG.append(" - MOG #");
      msgMOG.append(index);
      msgMOG.append(")");

      msg.append(action.getText("planning.projectOutputs.expectedSocialAndGenderPlan.readText.short",
        new String[] {String.valueOf(overview.getYear()), msgMOG.toString()}));

      this.addMessage(msg.toString());
      this.addMissingField("project.outputsOverview[" + counter + "].socialInclusionDimmension");
    }
  }
}
