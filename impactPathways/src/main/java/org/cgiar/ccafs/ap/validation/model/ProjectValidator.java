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

package org.cgiar.ccafs.ap.validation.model;

import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectValidator extends BaseValidator {

  private static final long serialVersionUID = -4351021637951625199L;

  @Inject
  public ProjectValidator() {
  }

  public boolean isValidActivities(List<Activity> activities) {
    return false;
  }

  public boolean isValidBilateralContractProposalName(String proposalName) {
    return (this.isValidString(proposalName)) ? true : false;
  }

  public boolean isValidBudget(List<Budget> budgets) {
    return false;
  }

  public boolean isValidCoordinator(ProjectPartner coordinator) {
    return false;
  }

  public boolean isValidCrpContributions(List<CRP> crpContributions) {
    return (crpContributions != null && !crpContributions.isEmpty());
  }

  public boolean isValidEndDate(Date endDate) {
    return (endDate != null) ? true : false;
  }

  public boolean isValidFlagships(List<IPProgram> flagships) {
    return (flagships != null && !flagships.isEmpty());
  }

  public boolean isValidIndicators(List<IPIndicator> indicators) {
    return false;
  }

  public boolean isValidLeader(ProjectPartner leader) {
    return false;
  }

  public boolean isValidLeaderResponsabilities(String leaderResponsabilities) {
    return (this.isValidString(leaderResponsabilities)) ? true : false;
  }

  public boolean isValidLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    return (liaisonInstitution != null) ? true : false;
  }

  public boolean isValidLinkedCoreProjects(List<Project> linkedCoreProjects) {
    return (linkedCoreProjects != null && !linkedCoreProjects.isEmpty());
  }

  public boolean isValidOutcomeGenderDimension(Map<String, ProjectOutcome> outcomes, int year) {
    if (outcomes != null && !outcomes.isEmpty()) {
      ProjectOutcome outcome = outcomes.get(String.valueOf(year));
      if (outcome != null) {
        if (outcome.getGenderDimension() != null && !outcome.getGenderDimension().isEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isValidOutcomeStatement(Map<String, ProjectOutcome> outcomes, int year) {
    if (outcomes != null && !outcomes.isEmpty()) {
      ProjectOutcome outcome = outcomes.get(String.valueOf(year));
      if (outcome != null) {
        if (outcome.getStatement() != null && !outcome.getStatement().isEmpty()) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isValidOutputs(List<IPElement> outputs) {
    return false;
  }

  public boolean isValidOwner(User owner) {
    if (owner != null) {
      return true;
    }
    return false;
  }

  public boolean isValidPPAPartners(List<ProjectPartner> ppaPartners) {
    return false;
  }

  public boolean isValidProjectPartners(List<ProjectPartner> projectPartners) {
    return false;
  }

  public boolean isValidProjectWorkplanName(String workplanName) {
    return (this.isValidString(workplanName)) ? true : false;
  }

  public boolean isValidRegions(List<IPProgram> regions) {
    if (regions != null && !regions.isEmpty()) {
      return true;
    }
    return false;
  }

  public boolean isValidStartDate(Date startDate) {
    return (startDate != null) ? true : false;
  }

  public boolean isValidSummary(String summary) {
    return (this.isValidString(summary)) ? true : false;
  }

  public boolean isValidTitle(String title) {
    return (this.isValidString(title)) ? true : false;
  }

  public boolean isValidType() {
    return false;
  }

}
