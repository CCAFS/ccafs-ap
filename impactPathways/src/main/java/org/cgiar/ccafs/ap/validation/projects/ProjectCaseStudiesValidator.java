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
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class ProjectCaseStudiesValidator extends BaseValidator {

  private ProjectValidator projectValidator;
  private boolean fields = false;

  private int c = 0;


  @Inject
  public ProjectCaseStudiesValidator(ProjectValidator projectValidator) {
    super();
    this.projectValidator = projectValidator;
  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // If project is CORE or CO-FUNDED
      if (project.isCoreProject() || project.isCoFundedProject()) {
        this.validateProjectJustification(action, project);

        for (CasesStudies caseStudy : project.getCaseStudies()) {
          c++;
          this.validateTitle(action, caseStudy.getTitle());
          this.validateActivities(action, caseStudy.getActivities());
          this.validateEvidenceOutcome(action, caseStudy.getEvidenceOutcome());
          this.validateNonResearchPartneres(action, caseStudy.getNonResearchPartneres());
          this.validateOutcomeStatement(action, caseStudy.getOutcomeStatement());
          this.validateOutputUsers(action, caseStudy.getOutputUsers());
          this.validatereRerencesCase(action, caseStudy.getReferencesCase());
          this.validateResearchOutputs(action, caseStudy.getResearchOutputs());
          this.validateResearchPatern(action, caseStudy.getResearchPartners());

        }
      }
      if (fields) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "caseStudies");
    }
  }


  private void validateActivities(BaseAction action, String activities) {
    if (!(this.isValidString(activities) && this.wordCount(activities) <= 150)) {
      this.addMessage("Case Study #" + c + " Activities");
      this.addMissingField("project.caseStudies.activities");
    }
  }


  private void validateEvidenceOutcome(BaseAction action, String evidenceOutcome) {
    if (!(this.isValidString(evidenceOutcome) && this.wordCount(evidenceOutcome) <= 50)) {
      this.addMessage("Case Study #" + c + " Outcome");
      this.addMissingField("project.caseStudies.evidenceOutcome");
    }
  }

  private void validateNonResearchPartneres(BaseAction action, String nonResearchPartneres) {
    if (!(this.isValidString(nonResearchPartneres) && this.wordCount(nonResearchPartneres) <= 150)) {
      this.addMessage("Case Study #" + c + " Non Research Partneres");
      this.addMissingField("project.caseStudies.nonResearchPartneres");
    }
  }


  private void validateOutcomeStatement(BaseAction action, String outcomeStatement) {
    if (!(this.isValidString(outcomeStatement) && this.wordCount(outcomeStatement) <= 80)) {
      this.addMessage("Case Study #" + c + " Outcome Statement");
      this.addMissingField("project.caseStudies.outcomeStatement");
    }
  }


  private void validateOutputUsers(BaseAction action, String outputUsers) {
    if (!(this.isValidString(outputUsers) && this.wordCount(outputUsers) <= 50)) {
      this.addMessage("Case Study #" + c + " Output Users");
      this.addMissingField("project.caseStudies.outputUsers");
    }
  }

  private void validatereRerencesCase(BaseAction action, String referencesCase) {
    if (!(this.isValidString(referencesCase) && this.wordCount(referencesCase) <= 50)) {
      this.addMessage("Case Study #" + c + " References Case");
      this.addMissingField("project.caseStudies.referencesCase");
    }
  }

  private void validateResearchOutputs(BaseAction action, String researchOutputs) {
    if (!(this.isValidString(researchOutputs) && this.wordCount(researchOutputs) <= 150)) {
      this.addMessage("Case Study #" + c + " Reserach Outputs");
      this.addMissingField("project.caseStudies.researchOutputs");
    }
  }


  private void validateResearchPatern(BaseAction action, String researchPatern) {
    if (!(this.isValidString(researchPatern) && this.wordCount(researchPatern) <= 150)) {
      this.addMessage("Case Study #" + c + " Research Patern");
      this.addMissingField("project.caseStudies.researchPatern");
    }
  }

  private void validateTitle(BaseAction action, String title) {
    if (!(this.isValidString(title) && this.wordCount(title) <= 15)) {
      this.addMessage("Case Study #" + c + " Title");
      this.addMissingField("project.caseStudies.title");
    }
  }
}
