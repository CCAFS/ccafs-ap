package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
import org.cgiar.ccafs.ap.validation.planning.ProjectDescriptionValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectLocationsValidator;
import org.cgiar.ccafs.ap.validation.planning.ProjectPartnersValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

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

  // Validators
  @Inject
  private ProjectDescriptionValidator descriptionValidator;
  @Inject
  private ProjectPartnersValidator projectPartnersValidator;
  @Inject
  private ProjectLocationsValidator locationValidator;

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
    sections.add("deliverable");
    sections.add("activities");
    sections.add("impactPathway");
    sections.add("budget");
    sections.add("budgetByMog");

    validSection = sections.contains(sectionName);

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
    Project project = projectManager.getProject(projectID);
    project.setLocations(locationManager.getProjectLocations(projectID));

    locationValidator.validate(this, project, "Planning");
  }

  private void validateProjectPartners() {
    // Getting information.
    Project project = projectManager.getProject(projectID);
    project.setProjectPartners(projectPartnerManager.getProjectPartners(project));

    // Validating.
    projectPartnersValidator.validate(this, project, "Planning");
  }
}
