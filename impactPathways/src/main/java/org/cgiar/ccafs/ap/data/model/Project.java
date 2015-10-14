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

  public static final int STANDAR_IDENTIFIER = 1;
  public static final int PDF_IDENTIFIER_REPORT = 2;
  public static final int EXCEL_IDENTIFIER_REPORT = 3;
  public static final int EMAIL_SUBJECT_IDENTIFIER = 4;
  private List<Activity> activities;
  private String bilateralContractProposalName;
  private List<Budget> budgets;
  private List<ComponentLesson> componentLessons;
  private ProjectPartner coordinator; // Project Coordinator.
  private long created; // Timestamp number when the project was created
  private List<Deliverable> deliverables; // Project research outputs - deliverables.
  private Date endDate;
  private List<IPProgram> flagships; // The list of flagships in which this project works with.
  private int id;
  private List<IPIndicator> indicators;
  private OtherContribution ipOtherContribution;
  private boolean isCofinancing;
  private boolean isGlobal;
  private String leaderResponsabilities;
  private LiaisonInstitution liaisonInstitution; // Creator program. e.g. LAM, FP4, CU, etc.
  private List<Project> linkedProjects;
  private List<Location> locations; // Project locations.
  private Map<String, ProjectOutcome> outcomes;
  private List<IPElement> outputs;
  private List<OutputBudget> outputsBudgets;
  private List<OutputOverview> outputsOverview;
  private BudgetOverhead overhead;
  private User owner;
  private List<ProjectPartner> projectPartners; // Project partners.
  private List<IPProgram> regions; // The list of regions in which this project works with.
  private Date startDate;
  private String summary;
  private String title;

  private String type; // Type of project see APConstants. e.g. CCAFS Core, CCAFS Co-founded or Bilateral
  private String workplanName;
  private boolean workplanRequired;

  public Project() {
  }

  public Project(int id) {
    this.id = id;
  }


  /**
   * This method validates if the current project contributes to a specific output (MOG).
   * 
   * @param outputID is an output (MOG) identifier.
   * @return true if the project contributes to the given MOG, false otherwise.
   */
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

  /**
   * This method validates if the current project contributes to a specific output and if the given output contributes
   * to a specific outcome.
   * 
   * @param outputID is an output (MOG) identifier.
   * @param outcomeID is an outcome (2019) identifier.
   * @return tru if the project actualle contributes to the MOG, false otherwise.
   */
  public boolean containsOutput(int outputID, int outcomeID) {
    if (this.outputs != null) {
      for (IPElement output : this.outputs) {
        if (output != null && output.getId() == outputID) {
          if (output.getContributesTo().contains(new IPElement(outcomeID))) {
            return true;
          }
        }
      }
    }
    return false;
  }

  /**
   * this method validates if the current project contributes to a given output (MOG).
   * 
   * @param output is some output to be compared.
   * @return true if the project contributes to the specified output (MOG), or false otherwise.
   */
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

  /**
   * This method returns the name of the file that was uploaded with the bilateral contract.
   * 
   * @return an String with the name of the bilateral contract file.
   */
  public String getBilateralContractProposalName() {
    if (bilateralContractProposalName == null) {
      return "";
    }
    return bilateralContractProposalName;
  }

  /**
   * This method returns a Budget object that contains the given parameters.
   * 
   * @param institutionID is an institution identifier.
   * @param budgetType is a budget type (W1/W2, W3/Bilateral, etc).
   * @param year is a specific year.
   * @return a Budget object that belongs to the specific project.
   */
  public Budget getBudget(int institutionID, int budgetType, int year) {
    if (budgets != null) {
      for (Budget budget : budgets) {
        if (budget.getInstitution().getId() == institutionID && budget.getType().getValue() == budgetType
          && budget.getYear() == year) {
          return budget;
        }
      }
    }
    return null;
  }

  public List<Budget> getBudgets() {
    return budgets;
  }

  /**
   * This method gets the budget of a specific project that is co-financing the current one in a specific year.
   * 
   * @param confinancingProjectID is a project identifier.
   * @param year is a year.
   * @return a Budget object with the information.
   */
  public Budget getCofinancingBudget(int confinancingProjectID, int year) {
    if (this.getBudgets() != null) {
      for (Budget budget : this.getBudgets()) {
        if (budget.getCofinancingProject() != null) {
          if (budget.getCofinancingProject().getId() == confinancingProjectID && budget.getYear() == year) {
            return budget;
          }
        }
      }
    }
    return null;
  }

  /**
   * This method gets all the budgets from the projects that are co-financing the curren project.
   * 
   * @return a List of Budget object with the information requested.
   */
  public List<Budget> getCofinancingBudgets() {
    List<Budget> budgets = new ArrayList<>();
    if (this.getBudgets() != null) {
      for (Budget budget : this.getBudgets()) {
        if (budget.getCofinancingProject() != null) {
          budgets.add(budget);
        }
      }
    }
    return budgets;
  }

  /**
   * @param componentName is the name of the lesson to search
   * @return the founded ComponentLesson, null if the lesson doesn't exist
   */
  public ComponentLesson getComponentLesson(String componentName) {
    for (ComponentLesson componentLesson : this.getComponentLessons()) {
      if (componentLesson.getComponentName().equals(componentName)) {
        return componentLesson;
      }
    }
    return null;
  }

  /**
   * @return the componentLessons
   */
  public List<ComponentLesson> getComponentLessons() {
    return componentLessons;
  }

  /**
   * This method returns a composed Identifier that is going to be used in the front-end.
   * The convention is going to be used depending on the creation date of the project.
   * yyyy-project.id => e.g. 2014-46
   * 
   * @return the composed identifier or null if the created date is null.
   */
  public String getComposedId() {
    if (created != 0) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTimeInMillis(created);
      return calendar.get(Calendar.YEAR) + "-" + this.id;
    }
    return null;
  }

  public String getComposedName() {
    return "P" + this.id + " - " + this.title;
  }

  public ProjectPartner getCoordinator() {
    return coordinator;
  }

  /**
   * This method gets all the coordinators working for this project.
   * 
   * @return a list of PartnerPerson with the information requested.
   */
  public List<PartnerPerson> getCoordinatorPersons() {
    List<PartnerPerson> projectCoordinators = new ArrayList<>();
    if (projectPartners != null) {
      for (ProjectPartner partner : projectPartners) {
        for (PartnerPerson person : partner.getPartnerPersons()) {
          if (person.isCoordinator()) {
            projectCoordinators.add(person);
          }
        }
      }
    }
    return projectCoordinators;
  }

  public long getCreated() {
    return created;
  }

  public Date getCreationDate() {
    return new Date(created);
  }

  public List<Deliverable> getDeliverables() {
    return deliverables;
  }


  public Date getEndDate() {
    return endDate;
  }

  public List<IPProgram> getFlagships() {
    return flagships;
  }

  /**
   * This method gets the list of Flagships acronyms separated by comma (, ).
   * 
   * @return a String with the list of flagships which are contributing to this project.
   */
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

  /**
   * This method gets a specific indicator for the currentp toject taking into account the given the parameters.
   * 
   * @param parentIndicatorID
   * @param outcomeID is some outcome (2019) identifier.
   * @param year is a year.
   * @return and IPIndicator object with the information requested.
   */
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

  /**
   * This method returns a list of project Indicators where its parent is the the indicator identified with the given
   * parameter.
   * 
   * @param parentIndicatorID is the parent indicator identifier.
   * @return a List of IPIndicator objects with the information requested.
   */
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

  public OtherContribution getIpOtherContribution() {
    return ipOtherContribution;
  }


  /**
   * This method returns the project partner institution that is leading the project.
   * 
   * @return a ProjectPartner object with the information requested. Or null if the project doesn't have a leader.
   */

  public ProjectPartner getLeader() {
    if (projectPartners != null) {
      for (ProjectPartner partner : projectPartners) {
        for (PartnerPerson person : partner.getPartnerPersons()) {
          if (person.isLeader()) {
            return partner;
          }
        }
      }
    }
    return null;
  }

  /**
   * This method returns the project partner person who is leading the project.
   * 
   * @return a PartnerPerson object with the information requested. Or null if the project doesn't have a leader.
   */
  public PartnerPerson getLeaderPerson() {
    if (projectPartners != null) {
      for (ProjectPartner partner : projectPartners) {
        for (PartnerPerson person : partner.getPartnerPersons()) {
          if (person.isLeader()) {
            return person;
          }
        }
      }
    }
    return null;
  }

  public String getLeaderResponsabilities() {
    return leaderResponsabilities;
  }

  public LiaisonInstitution getLiaisonInstitution() {
    return liaisonInstitution;
  }

  public List<Project> getLinkedProjects() {
    return linkedProjects;
  }

  public List<Location> getLocations() {
    return locations;
  }

  public Map<String, ProjectOutcome> getOutcomes() {
    return outcomes;
  }

  /**
   * This method returns the output (MOG) represented with the given identifier.
   * 
   * @param outputID is some IPElement (MOG) identifier.
   * @return an IPElement object representing an output (MOG) identified with the given id.
   */
  public IPElement getOutput(int outputID) {
    if (outputs != null) {
      for (IPElement output : outputs) {
        if (output.getId() == outputID) {
          return output;
        }
      }
    }
    return null;
  }

  /**
   * this method gets a specific Overview by MOG taking into account a given year and a given output (MOG).
   * 
   * @param outputID is an output (MOG) identifier.
   * @param year is a year.
   * @return an OutputOverview object with the information requested.
   */
  public OutputOverview getOutputOverview(int outputID, int year) {
    for (OutputOverview overview : outputsOverview) {
      if (overview.getOutput().getId() == outputID && overview.getYear() == year) {
        return overview;
      }
    }
    return null;
  }

  public List<IPElement> getOutputs() {
    return outputs;
  }

  public List<OutputBudget> getOutputsBudgets() {
    return outputsBudgets;
  }

  public List<OutputOverview> getOutputsOverview() {
    return outputsOverview;
  }

  public BudgetOverhead getOverhead() {
    return overhead;
  }

  public User getOwner() {
    return owner;
  }

  /**
   * This method returns the list of partners that have a PPA institution associated.
   * 
   * @return a list of ProjectPartner objects.
   */
  public List<ProjectPartner> getPPAPartners() {
    List<ProjectPartner> ppaPartners = new ArrayList<>();
    if (this.getProjectPartners() != null) {
      for (ProjectPartner pp : this.getProjectPartners()) {
        if (pp.getInstitution().isPPA()) {
          ppaPartners.add(pp);
        }
      }
      return ppaPartners;
    }
    return null;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public List<IPProgram> getRegions() {
    return regions;
  }

  /**
   * This method gets the list of Region acronyms separated by comma (, ).
   * 
   * @return a String with the list of regions which are contributing to this project.
   */
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

  /**
   * This method returns the project identifier whether using composed codification (that is with the organization IATI
   * standard id) or a simple id.
   * 
   * @param project , the project to get the standard identifier from.
   * @param useComposedCodification , true if you want to get the full IATI standard codification or false for simple
   *        form.
   * @return a String with the standard identifier.
   */
  public String getStandardIdentifier(int typeCodification) {
    StringBuilder result = new StringBuilder();

    switch (typeCodification) {
        // Standar identifier
      case Project.STANDAR_IDENTIFIER:
        result.append(APConstants.CCAFS_ORGANIZATION_IDENTIFIER);
        result.append("-P");
        result.append(this.getId());
        break;

      // PDF Identifier
      case Project.PDF_IDENTIFIER_REPORT:
        // -- flagships
        for (IPProgram flagship : this.getFlagships()) {
          if (flagship != null) {
            result.append(flagship.getAcronym().replaceAll(" ", "") + "-");
          }
        }
        int counter = 0;
        // -- regions
        for (IPProgram region : this.getRegions()) {
          if (region != null) {
            if (counter != 0) {
              result.append("-");
            }
            result.append(region.getAcronym().replaceAll("RP", "").replaceAll(" ", ""));
          }
          counter++;
        }
        result.append("_P" + this.getId());
        break;

      // Excel Identifier
      case Project.EXCEL_IDENTIFIER_REPORT:
        result.append("P" + this.getId());
        break;

        // Email Subject Identifier
      case Project.EMAIL_SUBJECT_IDENTIFIER:
        result.append("P" + this.getId());
        break;

      default:
        // Do nothing
        break;

    }


    return result.toString();
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

  /**
   * This method gets the total bilateral budget for the current project.
   * 
   * @return a double representing the amount of the total bilateral budget.
   */
  public double getTotalBilateralBudget() {
    double totalBudget = 0.0;
    if (budgets != null) {
      for (Budget budget : this.getBudgets()) {
        totalBudget += (budget.getType().isBilateral()) ? budget.getAmount() : 0;
      }
    }
    return totalBudget;
  }

  /**
   * this method gets the total budget for this project.
   * 
   * @return a double representing the total amount of budget for this project.
   */
  public double getTotalBudget() {
    double totalBudget = 0.0;
    if (budgets != null) {
      for (Budget budget : this.getBudgets()) {
        totalBudget += budget.getAmount();
      }
    }
    return totalBudget;
  }

  /**
   * This method gets the total W1/W2 budget for this project.
   * 
   * @return a double representing the amount.
   */
  public double getTotalCcafsBudget() {
    double totalBudget = 0.0;
    if (budgets != null) {
      for (Budget budget : this.getBudgets()) {
        totalBudget += (budget.getType().isCCAFSBudget()) ? budget.getAmount() : 0;
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

  public boolean isBilateralProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_BILATERAL) : false;
  }

  /**
   * A project is bilateral stand alone if it is bilateral and it is NOT contributing to any Core project.
   * 
   * @return true if the project is bilateral stand alone, false if is bilateral and is contributing to some core
   *         project.
   */
  public boolean isBilateralStandAlone() {
    return (type != null) ? (this.isBilateralProject() && !this.isCofinancing) : false;
  }

  public boolean isCofinancing() {
    return isCofinancing;
  }

  public boolean isCoFundedProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_CCAFS_COFUNDED) : false;
  }

  public boolean isCoordinator(User user) {
    List<PartnerPerson> coordinators = this.getCoordinatorPersons();
    for (PartnerPerson person : coordinators) {
      if (person.getUser().getId() == user.getId()) {
        return true;
      }
    }
    return false;
  }

  public boolean isCoreProject() {
    return (type != null) ? type.equals(APConstants.PROJECT_CORE) : false;
  }

  public boolean isGlobal() {
    return isGlobal;
  }

  public boolean isLeader(User user) {
    PartnerPerson leader = this.getLeaderPerson();

    if (leader != null) {
      return leader.getUser().getId() == user.getId();
    }

    return false;
  }

  /**
   * Return if the project is new.
   * A project is new when it was created in the planning phase for the current year
   * 
   * @param currentPlanningYear
   * @return true if the project is recent, false otherwise
   */
  public boolean isNew(Date planningStartDate) {
    return this.getCreationDate().after(planningStartDate);
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

  public void setCofinancing(boolean isCofinancing) {
    this.isCofinancing = isCofinancing;
  }

  /**
   * @param componentLessons the componentLessons to set
   */
  public void setComponentLessons(List<ComponentLesson> componentLessons) {
    this.componentLessons = componentLessons;
  }

  public void setCoordinator(ProjectPartner coordinator) {
    this.coordinator = coordinator;
  }

  public void setCreated(long created) {
    this.created = created;
  }

  public void setDeliverables(List<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setFlagships(List<IPProgram> flagships) {
    this.flagships = flagships;
  }

  public void setGlobal(boolean isGlobal) {
    this.isGlobal = isGlobal;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicators(List<IPIndicator> indicators) {
    this.indicators = indicators;
  }

  public void setIpOtherContribution(OtherContribution ipOtherContribution) {
    this.ipOtherContribution = ipOtherContribution;
  }

  public void setLeaderResponsabilities(String leaderResponsabilities) {
    this.leaderResponsabilities = leaderResponsabilities;
  }

  public void setLiaisonInstitution(LiaisonInstitution liaisonInstitution) {
    this.liaisonInstitution = liaisonInstitution;
  }

  public void setLinkedProjects(List<Project> linkedProjects) {
    this.linkedProjects = linkedProjects;
  }

  public void setLocations(List<Location> locations) {
    this.locations = locations;
  }

  public void setOutcomes(Map<String, ProjectOutcome> outcomes) {
    this.outcomes = outcomes;
  }

  public void setOutputs(List<IPElement> outputs) {
    this.outputs = outputs;
  }

  public void setOutputsBudgets(List<OutputBudget> outputsBudgets) {
    this.outputsBudgets = outputsBudgets;
  }

  public void setOutputsOverview(List<OutputOverview> outpusOverview) {
    this.outputsOverview = outpusOverview;
  }

  public void setOverhead(BudgetOverhead overhead) {
    this.overhead = overhead;
  }

  public void setOwner(User owner) {
    this.owner = owner;
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
