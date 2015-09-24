package org.cgiar.ccafs.ap.action.json.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.SectionStatusManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.SectionStatus;
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
  public ValidateProjectPlanningSectionAction(APConfig config) {
    super(config);
  }

  @Override
  public String execute() throws Exception {
    if (existProject && validSection) {
      // getting the current section status.
      sectionStatus = sectionStatusManager.getSectionStatus(new Project(projectID), "Planning", sectionName);

      switch (sectionName) {
        case "description":
          this.validateProjectDescription();
          break;
        default:
          // Do nothing.
      }
    }
    // try {
    //
    // typeID = Integer.parseInt(deliverableTypeID);
    // } catch (NumberFormatException e) {
    // LOG.error("There was an exception trying to parse the project id = {} ", projectID);
    // }
    //
    // subTypes = deliverableTypeManager.getDeliverableTypes(typeID);

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
    validSection = sections.contains(sectionName);

  }

  /**
   * This method will validate the Project description section.
   */
  private void validateProjectDescription() {
    // Getting information.
    Project project = projectManager.getProjectBasicInfo(projectID);
    // TODO
  }
}
