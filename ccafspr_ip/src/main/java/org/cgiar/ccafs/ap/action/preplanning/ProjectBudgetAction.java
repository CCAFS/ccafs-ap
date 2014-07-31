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
package org.cgiar.ccafs.ap.action.preplanning;

import java.util.List;

import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.model.Project;
import com.google.inject.Inject;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.action.BaseAction;

/**
 * Project Budget Action.
 *
 * @author Héctor Fabio Tobón R.
 */
public class ProjectBudgetAction extends BaseAction {

  private static final long serialVersionUID = 6828801151634526985L;

  // LOG
  public static Logger LOG = LoggerFactory.getLogger(ProjectBudgetAction.class);

  // Model for the back-end
  private int projectID;
  private int year;
  private Project project;

  // Model for the front-end
  private List<Integer> allYears;
  private List<Institution> allInstitutions;
  private List<Institution> leveragedInstitutions;
  private List<ProjectPartner> projectPartners;
  private User projectLeader;

  // Managers
  private ProjectManager projectManager;
  private BudgetManager budgetManager;
  private InstitutionManager institutionManager;
  private ProjectPartnerManager partnerManager;

  @Inject
  public ProjectBudgetAction(APConfig config, ProjectManager projectManager, BudgetManager budgetManager,
    InstitutionManager institutionManager, ProjectPartnerManager partnerManager) {
    super(config);
    this.projectManager = projectManager;
    this.budgetManager = budgetManager;
    this.institutionManager = institutionManager;
    this.partnerManager = partnerManager;
  }

  @Override
  public String execute() throws Exception {
    /*
     * If projectID is not in the parameter or if there is not a project with that id, we must redirect to a
     * NOT_FOUND page.
     */
    if (projectID == -1) {
      return NOT_FOUND;
    }
    return super.execute();
  }

  public List<Institution> getAllInstitutions() {
    return allInstitutions;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public List<Institution> getLeveragedInstitutions() {
    return leveragedInstitutions;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public User getProjectLeader() {
    return projectLeader;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public int getYear() {
    return year;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Getting the project id from the URL parameter
    String parameter;
    try {
      parameter = this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID);
      if (parameter != null) {
        projectID = Integer.parseInt(StringUtils.trim(parameter));
      }
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the project identifier '{}'.", projectID);
      projectID = -1;
      return; // Stop here and go to execute method.
    }

    // Getting the project identified with the id parameter.
    project = projectManager.getProject(projectID);
    // if there is not a project identified with the given id
    if (project == null) {
      return; // Stop here and go to execute method.
    }

    // Getting all the institutions.
    allInstitutions = institutionManager.getAllInstitutions();

    // Getting the list of institutions that are funding the project as leveraged. TODO HT - validate if there are not
// institutions.
    leveragedInstitutions = budgetManager.getLeveragedInstitutions(projectID);

    // Getting all the project partners. TODO HT - Validate if there are not partners.
    projectPartners = partnerManager.getProjectPartners(projectID);

    // Getting all the years dof the project. TODO HT - Validate if there are not dates configured.
    allYears = project.getAllYears();

    // Getting project leader. TODO HT - Validate if project leader doesn't exist.
    projectLeader = projectManager.getProjectLeader(projectID);

    try {
      parameter = this.getRequest().getParameter(APConstants.YEAR_REQUEST);
      if (parameter != null) {
        year = Integer.parseInt(StringUtils.trim(parameter));
      } else {
        year = allYears.get(0);
      }
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the year '{}'.", parameter);
      projectID = -1;
      return; // Stop here and go to execute method.
    }

  }

  public void setAllInstitutions(List<Institution> allInstitutions) {
    this.allInstitutions = allInstitutions;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }

  public void setYear(int year) {
    this.year = year;
  }


}
