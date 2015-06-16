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
package org.cgiar.ccafs.ap.data.model;

import org.cgiar.ccafs.ap.config.APConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * This class represents a Project.
 * 
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */
public class Project {

  private int id;
  private String title;
  private String type; // Type of project see APConstants. e.g. CCAFS Core, Bilateral Stand-alone or Bilateral
  // Co-Funded.
  private String summary;
  private Date startDate;
  private Date endDate;
  private List<IPProgram> regions; // The list of regions in which this project works with.
  private List<IPProgram> flagships; // The list of flagships in which this project works with.
  private ProjectPartner leader; // Project Leader.
  private ProjectPartner coordinator; // Project Coordinator.
  private String leaderResponsabilities;
  private LiaisonInstitution liaisonInstitution; // Creator program. e.g. LAM, FP4, CU, etc.
  private User owner;
  private List<ProjectPartner> projectPartners; // Project partners or 2-level partners.
  private List<ProjectPartner> ppaPartners; // PPA Partners or CCAFS Program Partners.
  private List<Budget> budgets;
  private Map<String, ProjectOutcome> outcomes;
  private List<Activity> activities;
  private List<IPElement> outputs;
  private List<IPIndicator> indicators;
  private IPOtherContribution ipOtherContribution;
  private boolean workplanRequired;
  private String workplanName;
  private String bilateralContractProposalName;
  private List<Project> linkedCoreProjects;
  private long created; // Timestamp number when the project was created

  public Project() {
  }

  public Project(int id) {
    this.id = id;
  }

  /**
   * Return if the project can be deleted.
   * A project only can be deleted if it was created in the planning
   * phase for the current year
   * 
   * @param currentPlanningYear
   * @return
   */
  public boolean canDelete(Date planningStartDate) {
    return this.getCreationDate().after(planningStartDate);
  }

  public boolean containsOutput(int outputID) {
    if (this.outputs != null) {
      for (IPElement output : this.outputs) {
        if (output.getId() == outputID) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean containsOutput(int outputID, int outcomeID) {
    if (this.outputs != null) {
      for (IPElement output : this.outputs) {
        if (output.getId() == outputID) {
          if (output.getContributesTo().contains(new IPElement(outcomeID))) {
            return true;
          }
        }
      }
    }
    return false;
  }

  public boolean containsOutput(IPElement output) {
    if (this.outputs != null) {
      for (IPElement projectOutput : this.outputs) {
        if (projectOutput == null) {
          continue;
        }
        if (projectOutput.getId() == output.getId()) {
          int projectOutcome = projectOutput.getContributesToIDs()[0];
          int outcome = output.getContributesToIDs()[0];

          if (outcome == projectOutcome) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * Equals based on the the project id.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Project) {
      Project p = (Project) obj;
      return p.getId() == this.getId();
    }
    return super.equals(obj);
  }

  public List<Activity> getActivities() {
    return activities;
  }

  /**
   * This method calculates all the years between the start date and the end date.
   * 
   * @return a List of numbers representing all the years, or an empty list if nothing found.
   */
  public List<Integer> getAllYears() {
    List<Integer> allYears = new ArrayList<>();
    if (startDate != null && endDate != null) {
      Calendar calendarStart = Calendar.getInstance();
      calendarStart.setTime(startDate);
      Calendar calendarEnd = Calendar.getInstance();
      calendarEnd.setTime(endDate);

      while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
        // Adding the year to the list.
        allYears.add(calendarStart.get(Calendar.YEAR));
        // Adding a year (365 days) to the start date.
        calendarStart.add(Calendar.YEAR, 1);
      }
    }

    return allYears;
  }

  public String getBilateralContractProposalName() {
    return bilateralContractProposalName;
  }

  public Budget getBudget(int institutionID, int budgetType, int year) {
    for (Budget budget : budgets) {
      if (budget.getInstitution().getId() == institutionID && budget.getType().getValue() == budgetType
        && budget.getYear() == year) {
        return budget;
      }
    }
    return null;
  }

  public List<Budget> getBudgets() {
    return budgets;
  }

  /**
   * This method returns a composed Identifier that is going to be used in the front-end.
   * The convention is going to be used depending on the creationg date of the project.
   * yyyy-project.id => e.g. 2014-46
   * 
   * @return the composed indentifier or null if the created date is null.
   */
  public String getComposedId() {
    if (created != 0) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(created);
      return calendar.get(Calendar.YEAR) + "-" + this.id;
    }
    return null;
  }

  public ProjectPartner getCoordinator() {
    return coordinator;
  }

  public long getCreated() {
    return created;
  }

  public Date getCreationDate() {
    return new Date(created);
  }

  public Date getEndDate() {
    return endDate;
  }

  public List<IPProgram> getFlagships() {
    return flagships;
  }

  public String getFlagshipsAcronym() {
    StringBuilder flagshipAcronym = new StringBuilder();

    if (flagships != null) {
      for (int i = 0; i < flagships.size(); i++) {
        flagshipAcronym.append(flagships.get(i).getAcronym());
        if (i != (flagships.size() - 1)) {
          flagshipAcronym.append(", ");
        }
      }
    }

    return flagshipAcronym.toString();
  }

  public int getId() {
    return id;
  }

  public IPIndicator getIndicator(int parentIndicatorID, int outcomeID, int year) {
    IPIndicator emptyIndicator = new IPIndicator(-1);

    if (indicators != null) {
      for (IPIndicator indicator : this.indicators) {
        if (indicator.getParent() != null) {
          if (indicator.getParent().getId() == parentIndicatorID && indicator.getYear() == year
            && indicator.getOutcome().getId() == outcomeID) {
            return indicator;
          }
        }
      }
    }
    return emptyIndicator;
  }

  public List<IPIndicator> getIndicators() {
    return indicators;
  }

  public List<IPIndicator> getIndicatorsByParent(int parentIndicatorID) {
    List<IPIndicator> indicators = new ArrayList<>();
    if (indicators != null) {
      this.getIndicatorsByParentAndYear(parentIndicatorID, 2019);
      for (IPIndicator indicator : this.indicators) {
        if (indicator.getParent() != null) {
          if (indicator.getParent().getId() == parentIndicatorID) {
            indicators.add(indicator);
          }
        }
      }
    }
    return indicators;
  }

  /**
   * This method search if the list of indicators contains an indicator
   * which parent is identified by the value passed as parameter.
   * 
   * @param indicatorID - indicator identifier
   * @return If the indicator is found, the method returns it. Otherwise, return null
   */
  public IPIndicator getIndicatorsByParentAndYear(int parentIndicatorID, int year) {
    IPIndicator emptyIndicator = new IPIndicator(-1);

    if (indicators != null) {
      for (IPIndicator indicator : this.indicators) {
        if (indicator.getParent() != null) {
          if (indicator.getParent().getId() == parentIndicatorID && indicator.getYear() == year) {
            return indicator;
          }
        }
      }
    }
    return emptyIndicator;
  }

  public IPOtherContribution getIpOtherContribution() {
    return ipOtherContribution;
  }

  public ProjectPartner getLeader() {
    return leader;
  }

  public String getLeaderResponsabilities() {
    return leaderResponsabilities;
  }

  public LiaisonInstitution getLiaisonInstitution() {
    return liaisonInstitution;
  }

  public List<Project> getLinkedCoreProjects() {
    return linkedCoreProjects;
  }

  public Map<String, ProjectOutcome> getOutcomes() {
    return outcomes;
  }


  public List<IPElement> getOutputs() {
    return outputs;
  }

  public User getOwner() {
    return owner;
  }

  public List<ProjectPartner> getPpaPartners() {
    return ppaPartners;
  }

  public List<ProjectPartner> getPPAPartners() {
    return ppaPartners;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public List<IPProgram> getRegions() {
    return regions;
  }

  public String getRegionsAcronym() {
    StringBuilder regionAcronym = new StringBuilder();

    if (regions != null) {
      for (int i = 0; i < regions.size(); i++) {
        regionAcronym.append(regions.get(i).getAcronym());
        if (i != (regions.size() - 1)) {
          regionAcronym.append(", ");
        }
      }
    }
    return regionAcronym.toString();
  }

  public Date getStartDate() {
    return startDate;
  }

  public String getSummary() {
    return summary;
  }

  public String getTitle() {
    return title;
  }

  public double getTotalCcafsBudget() {
    double totalBudget = 0.0;
    if (budgets != null) {
      for (Budget budget : this.getBudgets()) {
        totalBudget += budget.getAmount();
      }
    }
    return totalBudget;
  }

  public String getType() {
    return type;
  }

  public String getWorkplanName() {
    return workplanName;
  }

  @Override
  public int hashCode() {
    return this.getId();
  }

  public boolean isCoreProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_CORE) : false;
  }

  public boolean isWorkplanRequired() {
    return workplanRequired;
  }

  public void setActivities(List<Activity> activities) {
    this.activities = activities;
  }

  public void setBilateralContractProposalName(String bilateralContractProposalName) {
    this.bilateralContractProposalName = bilateralContractProposalName;
  }

  public void setBudgets(List<Budget> budgets) {
    this.budgets = budgets;
  }

  public void setCoordinator(ProjectPartner coordinator) {
    this.coordinator = coordinator;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setFlagships(List<IPProgram> flagships) {
    this.flagships = flagships;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicators(List<IPIndicator> indicators) {
    this.indicators = indicators;
  }

  public void setIpOtherContribution(IPOtherContribution ipOtherContribution) {
    this.ipOtherContribution = ipOtherContribution;
  }

  public void setLeader(ProjectPartner leader) {
    this.leader = leader;
  }

  public void setLeaderResponsabilities(String leaderResponsabilities) {
    this.leaderResponsabilities = leaderResponsabilities;
  }

  public void setLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
  }

  public void setLinkedCoreProjects(List<Project> linkedCoreProjects) {
    this.linkedCoreProjects = linkedCoreProjects;
  }

  public void setOutcomes(Map<String, ProjectOutcome> outcomes) {
    this.outcomes = outcomes;
  }

  public void setOutputs(List<IPElement> outputs) {
    this.outputs = outputs;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public void setPpaPartners(List<ProjectPartner> ppaPartners) {
    this.ppaPartners = ppaPartners;
  }

  public void setPPAPartners(List<ProjectPartner> ppaPartners) {
    this.ppaPartners = ppaPartners;
  }

  public void setProjectPartners(List<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setRegions(List<IPProgram> regions) {
    this.regions = regions;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setType(String type) {
    this.type = type;
  }

  public void setWorkplanName(String projectWorkplanName) {
    this.workplanName = projectWorkplanName;
  }

  public void setWorkplanRequired(boolean workplanRequired) {
    this.workplanRequired = workplanRequired;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
