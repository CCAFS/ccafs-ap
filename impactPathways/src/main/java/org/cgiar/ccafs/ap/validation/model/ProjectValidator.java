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
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPOtherContribution;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectValidator extends BaseValidator {

  private static final long serialVersionUID = -4351021637951625199L;

  public boolean validateActivities(List<Activity> activities) {
    return false;
  }

  public boolean validateBudget(List<Budget> budgets) {
    return false;
  }

  public boolean validateCoordinator(ProjectPartner coordinator) {
    return false;
  }

  public boolean validateEndDate(Date endDate) {
    return (endDate != null) ? true : false;
  }

  public boolean validateFlagships(List<IPProgram> flagships) {
    return false;
  }

  public boolean validateIndicators(List<IPIndicator> indicators) {
    return false;
  }

  public boolean validateIPOtherContributions(IPOtherContribution ipOtherContribution) {
    return false;
  }

  public boolean validateLeader(ProjectPartner leader) {
    return false;
  }

  public boolean validateLeaderResponsabilities(String leaderResponsabilities) {
    return (this.isValidString(leaderResponsabilities)) ? true : false;
  }

  public boolean validateLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    return false;
  }

  public boolean validateOutcomes(Map<String, ProjectOutcome> outcomes) {
    return false;
  }

  public boolean validateOutputs(List<IPElement> outputs) {
    return false;
  }

  public boolean validateOwner(User owner) {
    return false;
  }

  public boolean validatePPAPartners(List<ProjectPartner> ppaPartners) {
    return false;
  }

  public boolean validateProjectPartners(List<ProjectPartner> projectPartners) {
    return false;
  }

  public boolean validateRegions(List<IPProgram> regions) {
    return false;
  }

  public boolean validateStartDate(Date startDate) {
    return (startDate != null) ? true : false;
  }

  public boolean validateSummary(String summary) {
    return (this.isValidString(summary)) ? true : false;
  }

  public boolean validateTitle(String title) {
    return (this.isValidString(title)) ? true : false;
  }

  public boolean validateType() {
    return false;
  }

}