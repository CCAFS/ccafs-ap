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
 */

public class ProjectPartnersValidator extends BaseValidator {

  private static final long serialVersionUID = -4871185832403702671L;

  @Inject
  public ProjectPartnersValidator(ProjectValidator projectValidator) {
    super();
  }

  public void validate(BaseAction action, Project project) {
    if (project != null) {
      // Sending empty objects to the FTL view.
      if (ActionContext.getContext().getName().equals("partnerLead")) {
        if (project.getLeader() == null) {
          project.setLeader(new ProjectPartner(-1));
        }
        if (project.getLeader().getInstitution() == null) {
          project.getLeader().setInstitution(new Institution(-1));
        }
        if (project.getLeader().getUser() == null) {
          project.getLeader().setUser(new User(-1));
        }
        if (project.getCoordinator() == null) {
          project.setCoordinator(new ProjectPartner(-1));
        }
        if (project.getCoordinator().getInstitution() == null) {
          project.getCoordinator().setInstitution(new Institution(-1));
        }
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

      boolean problem = false;
      problem = this.validatePartners(project);


      // Validate justification always.
      if (action.getJustification().trim().isEmpty()) {
        this.addFieldError("justification",
          this.getText("validation.required", new String[] {this.getText("saving.justification")}));
        problem = true;
      }

      if (problem) {
        action.addActionError(this.getText(this.getFieldErrors().values().toString()).substring(2,
          this.getFieldErrors().values().toString().length() - 2));
      }
    }
  }

  private boolean validatePartners(Project project) {
    boolean problem = false;
    boolean duplicated = false;
    boolean contributionEmpty = false;
    List<ProjectPartner> allProjectPartners = new ArrayList<ProjectPartner>();
    allProjectPartners.add(project.getLeader());
    allProjectPartners.addAll(project.getPPAPartners());
    allProjectPartners.addAll(project.getProjectPartners());

    if (project.getLeader().getInstitution() == null || project.getLeader().getInstitution().getId() == -1) {
      this.addFieldError("project.leader.institution", this.getText("planning.projectPartners.selectInstitution"));
      problem = true;
    }

    // validating null fields on institution
    for (int c = 0; c < project.getPPAPartners().size(); c++) {
      if (project.getPPAPartners().get(c).getInstitution() == null
        || project.getPPAPartners().get(c).getInstitution().getId() == -1) {
        // Indicate problem in the missing field.
        this.addFieldError("project.PPAPartners[" + c + "].institution",
          this.getText("planning.projectPartners.selectInstitution"));
        problem = true;
      }
    }

    for (int c = 0; c < project.getProjectPartners().size(); c++) {
      if (project.getProjectPartners().get(c).getInstitution() == null
        || project.getProjectPartners().get(c).getInstitution().getId() == -1) {
        // Indicate problem in the missing field.
        this.addFieldError("project.projectPartners[" + c + "].institution",
          this.getText("planning.projectPartners.selectInstitution"));
        problem = true;
      }
    }
    // Validating repeated partners
    for (int i = 0; i < project.getPPAPartners().size(); i++) {
      for (int j = i + 1; j < project.getPPAPartners().size(); j++) {
        if (project.getPPAPartners().get(i).getUser() != null && project.getPPAPartners().get(j).getUser() != null) {
          if (project.getPPAPartners().get(i).getInstitution().getId() == project.getPPAPartners().get(j)
            .getInstitution().getId()
            && project.getPPAPartners().get(i).getUser().getId() == project.getPPAPartners().get(j).getUser().getId()) {
            problem = true;
            duplicated = true;
          }
        }
      }
    }
    for (int i = 0; i < project.getProjectPartners().size(); i++) {
      for (int j = i + 1; j < project.getProjectPartners().size(); j++) {
        if (project.getProjectPartners().get(i).getUser() != null
          && project.getProjectPartners().get(j).getUser() != null) {
          if (project.getProjectPartners().get(i).getInstitution().getId() == project.getProjectPartners().get(j)
            .getInstitution().getId()
            && project.getProjectPartners().get(i).getUser().getId() == project.getProjectPartners().get(j).getUser()
              .getId()) {
            problem = true;
            duplicated = true;
          }
        }
      }
    }

    // Validating with all partners
    for (int i = 0; i < allProjectPartners.size(); i++) {
      for (int j = i + 1; j < allProjectPartners.size(); j++) {
        if (allProjectPartners.get(i).getUser() != null && allProjectPartners.get(j).getUser() != null) {
          if (allProjectPartners.get(i).getInstitution().getId() == allProjectPartners.get(j).getInstitution().getId()
            && allProjectPartners.get(i).getUser().getId() == allProjectPartners.get(j).getUser().getId()) {
            problem = true;
            duplicated = true;
          }
        }
      }
    }
    // validate contribution partners
    for (int j = 0; j < allProjectPartners.size(); j++) {
      if (project.getPPAPartners().size() > 0) {
        if (allProjectPartners.get(j).getContributeInstitutions() == null) {
          contributionEmpty = true;
          problem = true;
        }
      }
    }

    if (duplicated) {
      this.addFieldError("Project partners, duplicated records", this.getText("planning.projectPartners.duplicated"));
    }
    if (contributionEmpty) {
      this.addFieldError("Project Partners, contribute Institutions empty",
        this.getText("planning.projectPartners.contributingInstitutions"));
    }

    return problem;
  }

}
