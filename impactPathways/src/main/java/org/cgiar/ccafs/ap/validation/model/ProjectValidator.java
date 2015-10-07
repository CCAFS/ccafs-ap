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

import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
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

  public boolean isValidBilateralContractProposalName(String proposalName) {
    return (this.isValidString(proposalName)) ? true : false;
  }

  public boolean isValidBudget(List<Budget> budgets) {
    return (budgets != null && !budgets.isEmpty());
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
    return (indicators != null && !indicators.isEmpty());
  }

  public boolean isValidLeader(ProjectPartner leader, boolean isBilateral) {
    if (leader == null) {
      return false;
    }

    // For bilateral projects the leader must be a PPA institution
    if (isBilateral && !leader.getInstitution().isPPA()) {
      return false;
    }
    return true;
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

  // This method validates if the list of locations given as parameter is not empty and different from null
  // If so, it returns true
  public boolean isValidListLocations(List<Location> locations) {
    return (!locations.isEmpty() && (locations != null));
  }

  // This method validates if the Gender dimension outcomes given as parameter are not empty and different from null
  // If so, it returns true
  public boolean isValidOutcomeGenderDimension(Map<String, ProjectOutcome> outcomes, int year) {
    if (outcomes != null && !outcomes.isEmpty()) {
      ProjectOutcome outcome = outcomes.get(String.valueOf(year));
      if (outcome != null) {
        if (this.isValidString(outcome.getGenderDimension())) {
          if (year == this.config.getMidOutcomeYear()) {
            // Number of words for outcome 2019
            return this.wordCount(outcome.getGenderDimension()) <= 150;
          } else {
            // Number of words for outcomes between 2014-2018.
            return this.wordCount(outcome.getGenderDimension()) <= 80;
          }
        }
        if (this.isValidString(outcome.getGenderDimension())) {
          if (year == this.config.getMidOutcomeYear()) {
            // Number of words for gender dimension 2019.
            return this.wordCount(outcome.getGenderDimension()) <= 150;
          } else {
            // Number of words for gender dimension between 2014-2018
            return this.wordCount(outcome.getGenderDimension()) <= 100;
          }
        }
      }

    }
    return false;
  }

  // This method validates if the outcome statement given as parameter is not empty and different from null
  // If so, it returns true
  public boolean isValidOutcomeStatement(Map<String, ProjectOutcome> outcomes, int year) {
    if (outcomes != null && !outcomes.isEmpty()) {
      ProjectOutcome outcome = outcomes.get(String.valueOf(year));
      if (outcome != null) {
        if (this.isValidString(outcome.getStatement())) {
          return true;
        }
      }
    }
    return false;
  }

  // This method validates if the output overview given as parameter is not empty and different from null
  // If so, it returns true
  public boolean isValidOutputOverviews(List<OutputOverview> outputOverviews) {
    return (outputOverviews != null && !outputOverviews.isEmpty());
  }

  public boolean isValidOutputs(List<IPElement> outputs) {
    return (outputs != null && !outputs.isEmpty());
  }

  public boolean isValidOwner(User owner) {
    if (owner != null) {
      return true;
    }
    return false;
  }

  public boolean isValidPersonResponsibilities(String responsibilities) {
    return (this.isValidString(responsibilities) && this.wordCount(responsibilities) <= 100);
  }

  public boolean isValidPPAPartners(List<ProjectPartner> ppaPartners) {
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
    return (this.isValidString(summary) && this.wordCount(summary) <= 150) ? true : false;
  }

  public boolean isValidTargetNarrative(String targetNarrative) {
    return (this.isValidString(targetNarrative) && this.wordCount(targetNarrative) <= 100);
  }

  public boolean isValidTargetValue(String targetValue) {
    return (this.isValidString(targetValue) && this.isValidNumber(targetValue));
  }

  public boolean isValidTitle(String title) {
    return (this.isValidString(title) && this.wordCount(title) <= 20) ? true : false;
  }


}
