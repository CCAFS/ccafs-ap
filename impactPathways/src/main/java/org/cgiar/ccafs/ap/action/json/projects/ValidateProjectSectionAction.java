package org.cgiar.ccafs.ap.action.json.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetByMogManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.BudgetOverheadManager;
import org.cgiar.ccafs.ap.data.manager.CrossCuttingContributionManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.CrossCuttingContribution;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.OutputBudget;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
import org.cgiar.ccafs.ap.data.model.SectionStatusEnum;
import org.cgiar.ccafs.ap.validation.projects.ActivitiesListValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectBudgetByMOGValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectBudgetValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectCCAFSOutcomesValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectCrossCuttingValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectDeliverableValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectDescriptionValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectIPOtherContributionValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectLocationsValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectOutcomeValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectOutputsValidator;
import org.cgiar.ccafs.ap.validation.projects.ProjectPartnersValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class validates that all the fields are filled in a specific section of a project.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ValidateProjectSectionAction extends BaseAction {

  private static final long serialVersionUID = -9075503855862433753L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ValidateProjectSectionAction.class);

  // Model
  private SectionStatus sectionStatus;
  private boolean existProject;
  private boolean validSection;
  private String sectionName;
  private String currentCycle;
  private int projectID;

  // Managers
  @Inject
  private SectionStatusManager sectionStatusManager;
  @Inject
  private ProjectManager projectManager;
  @Inject
  private IPProgramManager ipProgramManager;
  @Inject
  private ProjectCofinancingLinkageManager linkedProjectManager;
  @Inject
  private ProjectPartnerManager projectPartnerManager;
  @Inject
  private LocationManager locationManager;
  @Inject
  private ProjectOutcomeManager projectOutcomeManager;
  @Inject
  private IPElementManager ipElementManager;
  @Inject
  private ProjectContributionOverviewManager overviewManager;
  @Inject
  private IPIndicatorManager indicatorManager;
  @Inject
  private ActivityManager activityManager;
  @Inject
  private ProjectOtherContributionManager ipOtherContributionManager;
  @Inject
  private DeliverableManager deliverableManager;
  @Inject
  private BudgetOverheadManager overheadManager;
  @Inject
  private BudgetManager budgetManager;
  @Inject
  private BudgetByMogManager budgetByMogManager;

  @Inject
  private CrossCuttingContributionManager crossCutingManager;


  // Validators
  @Inject
  private ProjectDescriptionValidator descriptionValidator;
  @Inject
  private ProjectPartnersValidator projectPartnersValidator;
  @Inject
  private ProjectLocationsValidator locationValidator;
  @Inject
  private ProjectOutcomeValidator projectOutcomeValidator;
  @Inject
  private ProjectOutputsValidator projectOutputValidator;
  @Inject
  private ProjectCCAFSOutcomesValidator projectCCAFSOutcomesValidator;
  @Inject
  private ActivitiesListValidator activityListValidator;
  @Inject
  private ProjectIPOtherContributionValidator projectOtherContributionValidator;
  @Inject
  private ProjectDeliverableValidator deliverableValidator;
  @Inject
  private ProjectBudgetValidator budgetValidator;

  @Inject
  private ProjectCrossCuttingValidator crossValidator;

  @Inject
  private ProjectBudgetByMOGValidator budgetbyMOGValidator;


  @Inject
  public ValidateProjectSectionAction(APConfig config) {
    super(config);
  }

  @Override
  public String execute() throws Exception {
    if (existProject && validSection) {
      // getting the current section status.
      switch (SectionStatusEnum.valueOf(sectionName.toUpperCase())) {
        case DESCRIPTION:
          this.validateProjectDescription();
          break;
        case PARTNERS:
          this.validateProjectPartners();
          break;
        case LOCATIONS:
          this.validateProjectLocations();
          break;
        case OUTCOMES:
          this.validateProjectOutcomes();
          break;
        case CCAFSOUTCOMES:
          this.validateCCAFSOutcomes();
          break;
        case OTHERCONTRIBUTIONS:
          this.validateProjectOtherContributions();
          break;
        case OUTPUTS:
          this.validateOverviewByMOGS();
          break;
        case CROSSCUTTING:
          this.validateCrossCutting();
          break;
        case DELIVERABLESLIST:
          this.validateDeliverables();
          break;
        case ACTIVITIES:
          this.validateActivities();
          break;
        case BUDGET:
          this.validateBudgetByPartner();
          break;
        case BUDGETBYMOG:
          this.validateBudgetByMOG();
          break;
        default:
          // Do nothing.
          break;
      }
      // for deliverables, we going to create a fake section status with all the missing fields for all the deliverables
      // on it. Please refer to the method validateDeliverables().
      if (!sectionName.equals("deliverablesList")) {
        sectionStatus = sectionStatusManager.getSectionStatus(new Project(projectID), currentCycle, sectionName);
      }
    }
    return SUCCESS;
  }

  public Budget getBilateralCofinancingBudget(int projectID, int cofinanceProjectID, int year) {
    List<Budget> budgets = budgetManager.getBudgetsByYear(cofinanceProjectID, year);

    for (Budget budget : budgets) {
      if (budget.getCofinancingProject() != null) {
        if (budget.getProjectId() == projectID) {
          return budget;
        }
      }
    }


    return null;
  }

  public Budget getCofinancingBudget(int projectID, int cofinanceProjectID, int year) {
    Budget budged;
    Project cofinancingProject = projectManager.getProject(cofinanceProjectID);
    cofinancingProject
      .setBudgets(budgetManager.getBudgetsByYear(cofinancingProject.getId(), config.getPlanningCurrentYear()));
    if (cofinancingProject.isBilateralProject()) {

      budged = this.getBilateralCofinancingBudget(projectID, cofinanceProjectID, year);
    } else {
      budged = cofinancingProject.getCofinancingBudget(projectID, year);
    }
    // project.getBudgets().add(budged);
    return budged;
  }


  public SectionStatus getSectionStatus() {
    return sectionStatus;
  }

  @Override
  public void prepare() throws Exception {
    // Validating parameters.
    sectionName = this.getRequest().getParameter(APConstants.SECTION_NAME);
    currentCycle = this.getRequest().getParameter(APConstants.CYCLE);
    projectID = -1;
    if (this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID) != null) {
      try {
        projectID = Integer.parseInt(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID));
      } catch (NumberFormatException e) {
        LOG.error("There was an exception trying to parse the project id = {} ",
          this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID));
      }
    }

    // Validate if project exists.
    existProject = projectManager.existProject(projectID);

    // Validate if the section exists.
    List<String> sections = new ArrayList<>();
    sections.add("description");
    sections.add("partners");
    sections.add("locations");
    sections.add("outcomes");
    sections.add("ccafsOutcomes");
    sections.add("otherContributions");
    sections.add("outputs");
    sections.add("deliverablesList");
    sections.add("activities");
    sections.add("budget");
    sections.add("budgetByMog");
    if (currentCycle.equals(APConstants.REPORTING_SECTION)) {
      sections.add("crossCutting");
    }

    validSection = sections.contains(sectionName);

  }

  private void validateActivities() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // Getting the activities from the database.
    project.setActivities(activityManager.getActivitiesByProject(projectID));

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "activities",
      this.getCurrentPlanningYear(), this.getCycleName()));

    activityListValidator.validate(this, project, currentCycle);
  }

  private void validateBudgetByMOG() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));
    // Remove the outputs duplicated
    Set<IPElement> outputsTemp = new HashSet<>(project.getOutputs());
    project.getOutputs().clear();
    project.getOutputs().addAll(outputsTemp);
    // Getting the current Planning Year
    int year = config.getPlanningCurrentYear();
    int bilateralBudgetType = BudgetType.W3_BILATERAL.getValue();
    int ccafsBudgetType = BudgetType.W1_W2.getValue();

    List<OutputBudget> budgets = new ArrayList<>();
    budgets.addAll(budgetByMogManager.getProjectOutputsBudgetByTypeAndYear(projectID, ccafsBudgetType, year));
    budgets.addAll(budgetByMogManager.getProjectOutputsBudgetByTypeAndYear(projectID, bilateralBudgetType, year));
    project.setOutputsBudgets(budgets);

    budgetbyMOGValidator.validate(this, project, currentCycle);

  }


  private void validateBudgetByPartner() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // Getting budget information
    if (!project.isBilateralProject()) {
      project.setLinkedProjects(linkedProjectManager.getLinkedBilateralProjects(projectID));
    } else {
      project.setLinkedProjects(linkedProjectManager.getLinkedCoreProjects(projectID));
      project.setOverhead(overheadManager.getProjectBudgetOverhead(projectID));
    }
    // Getting project partners
    project.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    if (project.getLeader() != null) {
      // Getting the list of budgets for the current planning year.
      project.setBudgets(budgetManager.getBudgetsByYear(project.getId(), config.getPlanningCurrentYear()));
    }

    for (Project contribution : project.getLinkedProjects()) {
      contribution.setAnualContribution(
        this.getCofinancingBudget(projectID, contribution.getId(), this.getConfig().getPlanningCurrentYear()));
    }
    // TODO
    budgetValidator.validate(this, project, currentCycle);

  }

  private void validateCCAFSOutcomes() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // Get the project outputs from database
    project.setOutputs(ipElementManager.getProjectOutputsCcafs(projectID));
    // Get the project indicators from database
    project.setIndicators(indicatorManager.getProjectIndicators(projectID));
    // Getting the outcomes for each indicator
    for (IPIndicator indicator : project.getIndicators()) {
      indicator.setOutcome(ipElementManager.getIPElement(indicator.getOutcome().getId()));
    }

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "ccafsOutcomes",
      this.getCurrentPlanningYear(), this.getCycleName()));

    // Validating
    projectCCAFSOutcomesValidator.validate(this, project, currentCycle);
  }


  private void validateCrossCutting() {
    if (currentCycle.equals(APConstants.REPORTING_SECTION)) {
      // Getting basic project information.
      Project project = projectManager.getProject(projectID);
      List<CrossCuttingContribution> list = crossCutingManager.getCrossCuttingContributionsByProject(projectID);
      if (list.size() > 0) {
        project.setCrossCutting(list.get(0));

        // Getting the Project lessons for this section.
        this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "crossCutting",
          this.getCurrentPlanningYear(), this.getCycleName()));

        // Validate
        crossValidator.validate(this, project, currentCycle);
      }
    }


  }

  private void validateDeliverables() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // Getting the list of deliverables.
    project.setDeliverables(deliverableManager.getDeliverablesByProject(projectID));
    // we need to make two validations here. One at project level, and the other one for each deliverable.
    deliverableValidator.validate(this, project, currentCycle);

    sectionStatus = new SectionStatus();
    sectionStatus.setId(-1);
    sectionStatus.setSection("deliverablesList");
    StringBuilder missingFieldsAllDeliverables = new StringBuilder();
    // Getting the status made before.
    SectionStatus tempStatus = sectionStatusManager.getSectionStatus(project, currentCycle, "deliverablesList");
    // Adding the missing fields to the concatenated deliverablesStatus.
    missingFieldsAllDeliverables.append(tempStatus.getMissingFieldsWithPrefix());

    if (project.getDeliverables() != null && !project.getDeliverables().isEmpty()) {
      for (Deliverable deliverable : project.getDeliverables()) {
        deliverableValidator.validate(this, project, deliverable, currentCycle);
        // Appending all the missing fields for the current deliverable.
        tempStatus = sectionStatusManager.getSectionStatus(deliverable, currentCycle, "deliverable");
        missingFieldsAllDeliverables.append(tempStatus.getMissingFieldsWithPrefix());
      }
    }
    sectionStatus.setMissingFields(missingFieldsAllDeliverables.toString());
  }


  private void validateOverviewByMOGS() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // getting all the outputs (MOGs) related to this project.
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));
    // Get the project MOG overviews for this project
    project.setOutputsOverview(overviewManager.getProjectContributionOverviews(project));

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "outputs", this.getCurrentPlanningYear(),
      this.getCycleName()));

    // Validate
    projectOutputValidator.validate(this, project, currentCycle);
  }

  /**
   * This method will validate the Project description section.
   */
  private void validateProjectDescription() {
    // Getting information.
    Project project = projectManager.getProject(projectID);
    if (project != null) {
      // Getting the information of the Flagships Program associated with the project
      project.setRegions(ipProgramManager.getProjectFocuses(projectID, APConstants.REGION_PROGRAM_TYPE));
      // Getting the information of the Regions Program associated with the project
      project.setFlagships(ipProgramManager.getProjectFocuses(projectID, APConstants.FLAGSHIP_PROGRAM_TYPE));
    }

    // If project is CCAFS co-funded, we should load the core projects linked to it.
    if (!project.isBilateralProject()) {
      project.setLinkedProjects(linkedProjectManager.getLinkedBilateralProjects(projectID));
    } else {
      project.setLinkedProjects(linkedProjectManager.getLinkedCoreProjects(projectID));
    }
    // Validate.
    descriptionValidator.validate(this, project, currentCycle);

  }

  private void validateProjectLocations() {
    // Getting the project information.
    Project project = projectManager.getProject(projectID);
    project.setLocations(locationManager.getProjectLocations(projectID));

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "locations",
      this.getCurrentPlanningYear(), this.getCycleName()));

    locationValidator.validate(this, project, currentCycle);
  }

  private void validateProjectOtherContributions() {
    // Getting the Project information.
    Project project = projectManager.getProject(projectID);
    project.setIpOtherContribution(ipOtherContributionManager.getIPOtherContributionByProjectId(projectID));
    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "otherContributions",
      this.getCurrentPlanningYear(), this.getCycleName()));
    // Validating.
    projectOtherContributionValidator.validate(this, project);
  }

  private void validateProjectOutcomes() {
    // Getting information.
    Project project = projectManager.getProject(projectID);
    int currentPlanningYear = this.config.getPlanningCurrentYear();
    int midOutcomeYear = this.config.getMidOutcomeYear();

    // Loading the project outcomes
    Map<String, ProjectOutcome> projectOutcomes = new HashMap<>();

    int evaluatingYear = 0;
    if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
      evaluatingYear = this.getCurrentReportingYear();
    } else {
      evaluatingYear = currentPlanningYear;
    }

    for (int year = evaluatingYear; year <= midOutcomeYear; year++) {
      ProjectOutcome projectOutcome = projectOutcomeManager.getProjectOutcomeByYear(projectID, year);
      if (projectOutcome == null) {
        projectOutcome = new ProjectOutcome(-1);
        projectOutcome.setYear(year);
      }
      projectOutcomes.put(String.valueOf(year), projectOutcome);
    }
    project.setOutcomes(projectOutcomes);

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "outcomes", this.getCurrentPlanningYear(),
      this.getCycleName()));

    projectOutcomeValidator.validate(this, project, midOutcomeYear, currentPlanningYear, currentCycle);

  }

  private void validateProjectPartners() {
    // Getting the Project information.
    Project project = projectManager.getProject(projectID);
    project.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    // Getting the Project lessons for this section.
    this.setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "partners", this.getCurrentPlanningYear(),
      this.getCycleName()));

    // Validating.
    projectPartnersValidator.validate(this, project, currentCycle);
  }
}
