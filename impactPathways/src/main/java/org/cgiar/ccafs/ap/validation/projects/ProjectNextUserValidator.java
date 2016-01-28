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
import org.cgiar.ccafs.ap.validation.BaseValidator;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class ProjectNextUserValidator extends BaseValidator {


  @Inject
  public ProjectNextUserValidator() {
    super();

  }

  public void validate(BaseAction action, Project project, String cycle) {
    if (project != null) {
      // Does the project have any nextUser?
      if (project.getNextUsers() != null && !project.getNextUsers().isEmpty() && project.getNextUsers().size() >= 3) {
        // Validate project justification.
        this.validateProjectJustification(action, project);


        // Loop all the nexte users.
        for (int c = 0; c < project.getNextUsers().size(); c++) {
          // Required fields are required for all type of projects.

          this.validateKeyNextUser(action, project.getNextUsers().get(c).getKeyNextUser(), c);
          this.validateLessonsImplications(action, project.getNextUsers().get(c).getLessonsImplications(), c);
          this.validateReportedDeliverables(action, project.getNextUsers().get(c).getReportedDeliverables(), c);
          this.validateStrategies(action, project.getNextUsers().get(c).getStrategies(), c);

        }
      } else {
        // Show problem only for Core projects and Co-funded projects
        if (project.isCoreProject() || project.isCoFundedProject()) {
          this.addMessage("At least Three next users. ");
          this.addMissingField("project.nextUsers.empty");
        }
      }

      if (!action.getFieldErrors().isEmpty()) {
        action.addActionError(action.getText("saving.fields.required"));
      } else if (validationMessage.length() > 0) {
        action
          .addActionMessage(" " + action.getText("saving.missingFields", new String[] {validationMessage.toString()}));
      }

      // Saving missing fields.
      this.saveMissingFields(project, cycle, "nextUsers");
    }
  }


  public void validateKeyNextUser(BaseAction action, String keyNextUser, int c) {
    if (!(this.isValidString(keyNextUser) && this.wordCount(keyNextUser) <= 200)) {
      this.addMessage("Next User #" + (c + 1) + ": Key Next User");
      this.addMissingField("project.nextUsers[" + c + "].keyNextUser");
    }
  }

  public void validateLessonsImplications(BaseAction action, String lessons, int c) {
    if (!(this.isValidString(lessons) && this.wordCount(lessons) <= 100)) {
      this.addMessage("Next User #" + (c + 1) + ": Lesson Implications");
      this.addMissingField("project.nextUsers[" + c + "].lessonsImplications");
    }
  }

  public void validateReportedDeliverables(BaseAction action, String reportedDeliverables, int c) {
    if (!(this.isValidString(reportedDeliverables) && this.wordCount(reportedDeliverables) <= 100)) {
      this.addMessage("Next User #" + (c + 1) + ": Reported Deliverables");
      this.addMissingField("project.nextUsers[" + c + "].reportedDeliverables");
    }
  }

  public void validateStrategies(BaseAction action, String strategies, int c) {
    if (!(this.isValidString(strategies) && this.wordCount(strategies) <= 100)) {
      this.addMessage("Next User #" + (c + 1) + ": Strategies");
      this.addMissingField("project.nextUsers[" + c + "].strategies");
    }
  }
}
