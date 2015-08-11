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
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.BaseValidator;
import org.cgiar.ccafs.ap.validation.model.ProjectValidator;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;


/**
 * @author Carlos Alberto Martínez M. - CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class ProjectPartnersValidator extends BaseValidator {

  private static final long serialVersionUID = -4871185832403702671L;

  @Inject
  public ProjectPartnersValidator(ProjectValidator projectValidator) {
    super();
  }

  /**
   * This method initialize empty objects where there is not information.
   * 
   * @param project is the object with the information coming from the interface.
   * @deprecated It seems it is not used any more.
   */
  @Deprecated
  private void initializeEmptyFields(Project project) {
    // validating depending on the section where the user is.
    if (ActionContext.getContext().getName().equals("partnerLead")) {
      // If project leader is null, we just initialize it as empty object.
      if (project.getLeader() == null) {
        project.setLeader(new ProjectPartner(-1));
      }
      // If leader organization is null, we initialize it empty.
      if (project.getLeader().getInstitution() == null) {
        project.getLeader().setInstitution(new Institution(-1));
      }
      // If leader user is null, we initialize it empty.
      if (project.getLeader().getUser() == null) {
        project.getLeader().setUser(new User(-1));
      }
      // If coordinator is null, we initialize it empty.
      if (project.getCoordinator() == null) {
        project.setCoordinator(new ProjectPartner(-1));
      }
      // If coordinator organization is null, we initialize it empty.
      if (project.getCoordinator().getInstitution() == null) {
        project.getCoordinator().setInstitution(new Institution(-1));
      }
      // If coordinator user is null, we initialize it empty.
      if (project.getCoordinator().getUser() == null) {
        project.getCoordinator().setUser(new User(-1));
      }
    } else if (ActionContext.getContext().getName().equals("ppaPartners")) {
      for (ProjectPartner ppaPartner : project.getPPAPartners()) {
        if (ppaPartner.getInstitution() == null) {
          ppaPartner.setInstitution(new Institution(-1));
        }
        if (ppaPartner.getUser() == null) {
          ppaPartner.setUser(new User(-1));
        }
      }
    } else if (ActionContext.getContext().getName().equals("partners")) {
      for (ProjectPartner partner : project.getProjectPartners()) {
        if (partner.getInstitution() == null) {
          partner.setInstitution(new Institution(-1));
        }
        if (partner.getUser() == null) {
          partner.setUser(new User(-1));
        }
      }
    }
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      boolean problem = false;
      boolean result;
      System.out.println();
      // Validating empty fields and show red messages when something required is missing.
      result = this.validateEmptyFields(action, project);
      if (result) {
        // letting know that there is a problem.
        problem = true;
      }

      // Validate that the project leader and project coordinator are not the same person.
      result = this.validateLeaderAndCoordinator(action, project);
      if (result) {
        problem = true;
      }

      // Validating repeated partners if there is not empty fields.
      if (!problem) {
        // Do not validate repeated partners until the empty fields are filled.
        result = this.validateRepeatedPartners(action, project);
      }

      // Validating that the partners can be deleted.


      // problem = this.validatePartners(project);

      // if (problem) {
      // action.addActionError(this.getText(this.getFieldErrors().values().toString()).substring(2,
      // this.getFieldErrors().values().toString().length() - 2));
      // }
    }
  }


  /**
   * This method validates all the empty fields.
   * 
   * @param action is the action that is being executed.
   * @param project is an object with the information coming from the interface.
   * @return true if the required fields are filled, false otherwise.
   */
  private boolean validateEmptyFields(BaseAction action, Project project) {
    boolean problem = false;
    if (ActionContext.getContext().getName().equals("partnerLead")) {
      if (project.getLeader().getInstitution() == null || project.getLeader().getInstitution().getId() == -1) {
        action.addFieldError("project.leader.institution", this.getText("planning.projectPartners.selectInstitution"));
        problem = true;
      }
    } else if (ActionContext.getContext().getName().equals("ppaPartners")) {
      // validating null fields on institution
      for (int c = 0; c < project.getPPAPartners().size(); c++) {
        if (project.getPPAPartners().get(c).getInstitution() == null
          || project.getPPAPartners().get(c).getInstitution().getId() == -1) {
          // Indicate problem in the missing field.
          action.addFieldError("project.PPAPartners[" + c + "].institution",
            this.getText("planning.projectPartners.selectInstitution"));
          problem = true;
        }
      }
    } else if (ActionContext.getContext().getName().equals("partners")) {
      for (int c = 0; c < project.getProjectPartners().size(); c++) {
        if (project.getProjectPartners().get(c).getInstitution() == null
          || project.getProjectPartners().get(c).getInstitution().getId() == -1) {
          // Indicate problem in the missing field.
          action.addFieldError("project.projectPartners[" + c + "].institution",
            this.getText("planning.projectPartners.selectInstitution"));
          problem = true;
        }
      }
    }

    // Validate justification always.
    if (action.getJustification().trim().isEmpty()) {
      action.addFieldError("justification",
        this.getText("validation.required", new String[] {this.getText("saving.justification")}));
      problem = true;
    }
    return problem;
  }

  private boolean validateLeaderAndCoordinator(BaseAction action, Project project) {
    if (project.getLeader() != null && project.getCoordinator() != null
      && project.getLeader().getId() == project.getCoordinator().getId()) {
      action.addActionError(this.getText("planning.projectPartners.duplicated.PLPC"));
      action.addFieldError("contact-person-leader", this.getText("validation.duplicated"));
      action.addFieldError("contact-person-coordinator", this.getText("validation.duplicated"));
      return true;
    }
    return false;
  }

  private boolean validatePartners(BaseAction action, Project project) {
    boolean problem = false;
    boolean contributionEmpty = false;
    List<ProjectPartner> allProjectPartners = new ArrayList<ProjectPartner>();
    allProjectPartners.add(project.getLeader());
    allProjectPartners.addAll(project.getPPAPartners());
    allProjectPartners.addAll(project.getProjectPartners());


    // validate contribution partners
    for (int j = 0; j < allProjectPartners.size(); j++) {
      if (project.getPPAPartners().size() > 0) {
        if (allProjectPartners.get(j).getContributeInstitutions() == null) {
          contributionEmpty = true;
          problem = true;
        }
      }
    }


    if (contributionEmpty) {
      this.addFieldError("Project Partners, contribute Institutions empty",
        this.getText("planning.projectPartners.contributingInstitutions"));
    }

    return problem;
  }

  /**
   * This method validates that a partner is not repeated.
   * 
   * @param action is the action where the user is located.
   * @param project is the project object that contains the information coming from the interface.
   * @return true if at least one partner is repeated. False otherwise.
   */
  private boolean validateRepeatedPartners(BaseAction action, Project project) {
    List<ProjectPartner> allPartners = new ArrayList<ProjectPartner>();
    allPartners.add(project.getLeader());
    allPartners.addAll(project.getPPAPartners());
    allPartners.addAll(project.getProjectPartners());

    boolean problem = false;

    // Validating repeated partners for each section.
    if (ActionContext.getContext().getName().equals("partnerLead")) {
      // Validating that the project leader is not repeated.
      for (int c = 0; c < allPartners.size(); c++) {
        if (allPartners.get(c).getId() != project.getLeader().getId()) {
          if (allPartners.get(c).getUser() != null
            && allPartners.get(c).getInstitution().getId() == project.getLeader().getInstitution().getId()
            && allPartners.get(c).getUser().getId() == project.getLeader().getUser().getId()) {
            problem = true;
            action.addActionError(this.getText("planning.projectPartners.duplicated"));
            action.addFieldError("project.leader.institution", this.getText("validation.duplicated"));
            action.addFieldError("contact-person-leader", this.getText("validation.duplicated"));
          }
        }
      }
    } else if (ActionContext.getContext().getName().equals("ppaPartners")) {
      // Validating that the CCAFS Partners are not repeated.
      for (int i = 0; i < project.getPPAPartners().size(); i++) {
        // Validating CCAFS Partners against the project leader
        if (project.getPPAPartners().get(i).getUser() != null
          && project.getLeader() != null & project.getLeader().getUser() != null
          && project.getPPAPartners().get(i).getUser().getId() == project.getLeader().getUser().getId()
          && project.getPPAPartners().get(i).getInstitution().getId() == project.getLeader().getInstitution().getId()) {
          problem = true;
          action.addActionError(this.getText("planning.projectPartners.duplicated.leader"));
          action.addFieldError("project.PPAPartners[" + i + "].institution", this.getText("validation.duplicated"));
          action.addFieldError("contact-person-" + i, this.getText("validation.duplicated"));
        }
        // Validating CCAFS Partners against the other CCAFS Partners.
        for (int j = i + 1; j < project.getPPAPartners().size(); j++) {
          if (project.getPPAPartners().get(i).getUser() != null && project.getPPAPartners().get(j).getUser() != null) {
            if (project.getPPAPartners().get(i).getInstitution().getId() == project.getPPAPartners().get(j)
              .getInstitution().getId()
              && project.getPPAPartners().get(i).getUser().getId() == project.getPPAPartners().get(j).getUser()
                .getId()) {
              problem = true;
              action.addActionError(this.getText("planning.projectPartners.duplicated"));
              action.addFieldError("project.PPAPartners[" + i + "].institution", this.getText("validation.duplicated"));
              action.addFieldError("contact-person-" + i, this.getText("validation.duplicated"));

              action.addFieldError("project.PPAPartners[" + j + "].institution", this.getText("validation.duplicated"));
              action.addFieldError("contact-person-" + j, this.getText("validation.duplicated"));
            }
          }
        }
      }

    } else if (ActionContext.getContext().getName().equals("partners")) {
      // Validating that the Project Partners are not repeated.
      for (int i = 0; i < project.getProjectPartners().size(); i++) {
        // Validating Project Partners against the project leader
        if (project.getProjectPartners().get(i).getUser() != null
          && project.getLeader() != null & project.getLeader().getUser() != null
          && project.getProjectPartners().get(i).getUser().getId() == project.getLeader().getUser().getId() && project
          .getProjectPartners().get(i).getInstitution().getId() == project.getLeader().getInstitution().getId()) {
          problem = true;
          action.addActionError(this.getText("planning.projectPartners.duplicated.leader"));
          action.addFieldError("project.projectPartners[" + i + "].institution", this.getText("validation.duplicated"));
          action.addFieldError("contact-person-" + i, this.getText("validation.duplicated"));
        }
        // Validating Project Partners against the other Project Partners.
        for (int j = i + 1; j < project.getProjectPartners().size(); j++) {
          if (project.getProjectPartners().get(i).getUser() != null
            && project.getProjectPartners().get(j).getUser() != null) {
            if (project.getProjectPartners().get(i).getInstitution().getId() == project.getProjectPartners().get(j)
              .getInstitution().getId()
              && project.getProjectPartners().get(i).getUser().getId() == project.getProjectPartners().get(j).getUser()
                .getId()) {
              problem = true;
              action.addActionError(this.getText("planning.projectPartners.duplicated"));
              action.addFieldError("project.projectPartners[" + i + "].institution",
                this.getText("validation.duplicated"));
              action.addFieldError("contact-person-" + i, this.getText("validation.duplicated"));

              action.addFieldError("project.projectPartners[" + j + "].institution",
                this.getText("validation.duplicated"));
              action.addFieldError("contact-person-" + j, this.getText("validation.duplicated"));
            }
          }
        }
      }
    }
    return problem;
  }

}
