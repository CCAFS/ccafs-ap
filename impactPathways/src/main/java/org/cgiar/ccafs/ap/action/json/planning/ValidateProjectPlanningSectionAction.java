package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
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
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
import org.cgiar.ccafs.ap.validation.planning.ActivitiesListValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectCCAFSOutcomesValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectDescriptionValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectIPOtherContributionValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectLocationsValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectOutcomeValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectOutputsPlanningValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectPartnersValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class validates that all the fields are filled in a specific section of a project.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ValidateProjectPlanningSectionAction extends BaseAction {

  private static final long serialVersionUID = -9075503855862433753L;

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ValidateProjectPlanningSectionAction.class);

  // Model
  private SectionStatus sectionStatus;
  private boolean existProject;
  private boolean validSection;
  private String sectionName;
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
  private ProjectOutputsPlanningValidator projectOutputValidator;
  @Inject
  private ProjectCCAFSOutcomesValidator projectCCAFSOutcomesValidator;
  @Inject
  private ActivitiesListValidator activityListValidator;

  @Inject
  private ProjectIPOtherContributionValidator projectOtherContributionValidator;

  @Inject
  public ValidateProjectPlanningSectionAction(APConfig config) {
    super(config);
  }

  @Override
  public String execute() throws Exception {
    if (existProject && validSection) {
      // getting the current section status.
      switch (sectionName) {
        case "description":
          this.validateProjectDescription();
          break;
        case "partners":
          this.validateProjectPartners();
          break;
        case "locations":
          this.validateProjectLocations();
          break;
        case "outcomes":
          this.validateProjectOutcomes();
          break;
        case "ccafsOutcomes":
          this.validateCCAFSOutcomes();
          break;
        case "otherContributions":
          this.validateProjectOtherContributions();
          break;
        case "outputs":
          this.validateOverviewByMOGS();
          break;
        case "deliverableList":
          // TODO
          break;
        case "activities":
          this.validateActivities();
          break;
        case "budget":
          // TODO
          break;
        case "budgetByMog":
          // TODO
          break;
        default:
          // Do nothing.
          break;
      }
      sectionStatus = sectionStatusManager.getSectionStatus(new Project(projectID), "Planning", sectionName);

    }
    return SUCCESS;
  }

  public SectionStatus getSectionStatus() {
    return sectionStatus;
  }

  @Override
  public void prepare() throws Exception {
    // Validating parameters.
    sectionName = this.getRequest().getParameter(APConstants.SECTION_NAME);
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
    sections.add("deliverableList");
    sections.add("activities");
    sections.add("budget");
    sections.add("budgetByMog");
    validSection = sections.contains(sectionName);

  }

  private void validateActivities() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // Getting the activities from the database.
    project.setActivities(activityManager.getActivitiesByProject(projectID));

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, "activities", this.getCurrentPlanningYear()));

    activityListValidator.validate(this, project, "Planning");
  }


  private void validateCCAFSOutcomes() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // Get the project outputs from database
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));
    // Get the project indicators from database
    project.setIndicators(indicatorManager.getProjectIndicators(projectID));
    // Getting the outcomes for each indicator
    for (IPIndicator indicator : project.getIndicators()) {
      indicator.setOutcome(ipElementManager.getIPElement(indicator.getOutcome().getId()));
    }

    // Getting the Project lessons for this section.
    this
    .setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "outcomes", this.getCurrentPlanningYear()));

    // Validating
    projectCCAFSOutcomesValidator.validate(this, project, "Planning");
  }

  private void validateOverviewByMOGS() {
    // Getting basic project information.
    Project project = projectManager.getProject(projectID);
    // getting all the outputs (MOGs) related to this project.
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));
    // Get the project MOG overviews for this project
    project.setOutputsOverview(overviewManager.getProjectContributionOverviews(project));

    // Getting the Project lessons for this section.
    this
    .setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "outputs", this.getCurrentPlanningYear()));

    // Validate
    projectOutputValidator.validate(this, project, "Planning");
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
    descriptionValidator.validate(this, project, "Planning");

  }

  private void validateProjectLocations() {
    // Getting the project information.
    Project project = projectManager.getProject(projectID);
    project.setLocations(locationManager.getProjectLocations(projectID));

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, "locations", this.getCurrentPlanningYear()));

    locationValidator.validate(this, project, "Planning");
  }

  private void validateProjectOtherContributions() {
    // Getting the Project information.
    Project project = projectManager.getProject(projectID);
    project.setIpOtherContribution(ipOtherContributionManager.getIPOtherContributionByProjectId(projectID));
    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, "otherContributions", this.getCurrentPlanningYear()));
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
    for (int year = currentPlanningYear; year <= midOutcomeYear; year++) {
      ProjectOutcome projectOutcome = projectOutcomeManager.getProjectOutcomeByYear(projectID, year);
      if (projectOutcome == null) {
        projectOutcome = new ProjectOutcome(-1);
        projectOutcome.setYear(year);
      }
      projectOutcomes.put(String.valueOf(year), projectOutcome);
    }
    project.setOutcomes(projectOutcomes);

    // Getting the Project lessons for this section.
    this
    .setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "outcomes", this.getCurrentPlanningYear()));

    projectOutcomeValidator.validate(this, project, midOutcomeYear, currentPlanningYear, "Planning");

  }

  private void validateProjectPartners() {
    // Getting the Project information.
    Project project = projectManager.getProject(projectID);
    project.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    // Getting the Project lessons for this section.
    this
      .setProjectLessons(lessonManager.getProjectComponentLesson(projectID, "partners", this.getCurrentPlanningYear()));

    // Validating.
    projectPartnersValidator.validate(this, project, "Planning");
  }
}
