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

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdfs.ProjectSummaryPDF;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.CRPManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class ProjectSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(ProjectSummaryAction.class);
  private static final long serialVersionUID = 5140987672008315842L;

  ActivityManager activityManager;
  BudgetManager budgetManager;
  CRPManager crpManager;
  DeliverableManager deliverableManager;
  DeliverablePartnerManager deliverablePartnerManager;
  InstitutionManager institutionManager;
  IPElementManager ipElementManager;
  ProjectOtherContributionManager ipOtherContributionManager;
  IPProgramManager ipProgramManager;
  ProjectCofinancingLinkageManager linkedCoreProjectManager;
  LocationManager locationManager;
  NextUserManager nextUserManager;
  ProjectContributionOverviewManager overviewManager;
  ProjectPartnerManager partnerManager;
  Project project;
  // Managers
  ProjectManager projectManager;

  ProjectOutcomeManager projectOutcomeManager;
  // Model
  ProjectSummaryPDF projectPDF;
  List<InputStream> streams;

  @Inject
  public ProjectSummaryAction(APConfig config, ProjectSummaryPDF projectPDF, ProjectManager projectManager,
    IPProgramManager ipProgramManager, ProjectPartnerManager partnerManager, BudgetManager budgetManager,
    ProjectOutcomeManager projectOutcomeManager, ActivityManager activityManager,
    ProjectCofinancingLinkageManager linkedCoreProjectManager, LocationManager locationManager,
    IPElementManager ipElementManager, ProjectContributionOverviewManager overviewManager,
    InstitutionManager institutionManager, DeliverableManager deliverableManager, NextUserManager nextUserManager,
    DeliverablePartnerManager deliverablePartnerManager, ProjectOtherContributionManager ipOtherContributionManager,
    CRPManager crpManager) {
    super(config);
    this.projectPDF = projectPDF;
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.partnerManager = partnerManager;
    this.budgetManager = budgetManager;
    this.projectOutcomeManager = projectOutcomeManager;
    this.activityManager = activityManager;
    this.linkedCoreProjectManager = linkedCoreProjectManager;
    this.locationManager = locationManager;
    this.ipElementManager = ipElementManager;
    this.overviewManager = overviewManager;
    this.institutionManager = institutionManager;
    this.deliverableManager = deliverableManager;
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.ipOtherContributionManager = ipOtherContributionManager;
    this.crpManager = crpManager;
  }


  @Override
  public String execute() throws Exception {
    int currentPlanningYear = this.config.getPlanningCurrentYear();
    int midOutcomeYear = this.config.getMidOutcomeYear();
    // Generate the pdf file
    projectPDF.generatePdf(project, currentPlanningYear, midOutcomeYear);

    streams = new ArrayList<>();
    streams.add(projectPDF.getInputStream());

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return projectPDF.getContentLength();
  }

  @Override
  public String getFileName() {
    return projectPDF.getFileName();
  }

  @Override
  public InputStream getInputStream() {
    return projectPDF.getInputStream();
  }


  private String getWorkplanRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getProjectWorkplanFolder() + File.separator;
  }

  public String getWorkplanURL() {
    return config.getDownloadURL() + "/" + this.getWorkplanRelativePath().replace('\\', '/');
  }


  /**
   * Return the absolute path where the work plan is or should be located.
   * 
   * @param workplan name
   * @return complete path where the image is stored
   */
  // private String getWorplansAbsolutePath() {
  // return config.getUploadsBaseFolder() + File.separator + this.getWorkplanRelativePath() + File.separator;
  // }
  //

  @Override
  public void prepare() throws Exception {
    int projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Get all the information to add in the pdf file
    project = projectManager.getProject(projectID);

    // Get a route for the workplan name
    if (project.isWorkplanRequired()) {

      if (this.getWorkplanURL() != null || project.getWorkplanName() != null) {
        project.setWorkplanName(this.getWorkplanURL() + project.getWorkplanName());
      }
    }

    // Getting the information of the Regions program
    project.setRegions(ipProgramManager.getProjectFocuses(project.getId(), APConstants.REGION_PROGRAM_TYPE));

    // Getting the information of the Flagships program
    project.setFlagships(ipProgramManager.getProjectFocuses(project.getId(), APConstants.FLAGSHIP_PROGRAM_TYPE));


    // Getting the Project Leader.
    List<ProjectPartner> ppArray = new ArrayList<>();
    // partnerManager.z_old_getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PL);
    if (ppArray.size() != 0) {
      project.setLeader(ppArray.get(0));
    }

    // Getting Project Coordinator
    // ppArray = partnerManager.z_old_getProjectPartners(project.getId(), APConstants.PROJECT_PARTNER_PC);
    ppArray = new ArrayList(); // TODO To review.
    if (ppArray.size() != 0) {
      project.setCoordinator(ppArray.get(0));
    }

    // Getting PPA Partners
    // project.setPPAPartners(partnerManager.z_old_getProjectPartners(project.getId(), "PPA"));


    // Getting 2-level Project Partners
    // project.setProjectPartners(partnerManager.z_old_getProjectPartners(project.getId(), "PP"));
    project.setProjectPartners(new ArrayList());
    // Getting the 2-level Project Partner contributions
    for (ProjectPartner partner : project.getProjectPartners()) {
      // partner.setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(partner));
    }

    // Add Linked project
    // If project is CCAFS co_founded, we should load the core projects linked to it.
    if (!project.isBilateralProject()) {
      project.setLinkedProjects(linkedCoreProjectManager.getLinkedBilateralProjects(projectID));
    } else {
      project.setLinkedProjects(linkedCoreProjectManager.getLinkedCoreProjects(projectID));
    }
    // Add project locations
    project.setLocations(locationManager.getProjectLocations(projectID));

    // Get project outputs
    project.setOutputs(ipElementManager.getProjectOutputs(projectID));

    // Remove the outputs duplicated
    Set<IPElement> outputsTemp = new HashSet<>(project.getOutputs());
    project.getOutputs().clear();
    project.getOutputs().addAll(outputsTemp);

    // Get the project outputs from database
    List<OutputOverview> listaOver = overviewManager.getProjectContributionOverviews(project);
    for (int a = 0; a < listaOver.size(); a++) {
      listaOver.get(a).setOutput(ipElementManager.getIPElement(listaOver.get(a).getOutput().getId()));
    }
    project.setOutputsOverview(listaOver);


    // *************************Deliverables*****************************/
    List<Deliverable> deliverables = deliverableManager.getDeliverablesByProject(projectID);
    for (Deliverable deliverable : deliverables) {
      // Getting next users.
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));

      // Getting the responsible partner.
      List<DeliverablePartner> partners =
        deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_RESP);
      if (partners.size() > 0) {
        deliverable.setResponsiblePartner(partners.get(0));
      } else {
        DeliverablePartner responsiblePartner = new DeliverablePartner(-1);
        // responsiblePartner.setInstitution(new Institution(-1));
        // responsiblePartner.setUser(new User(-1));
        // responsiblePartner.setType(APConstants.DELIVERABLE_PARTNER_RESP);
        deliverable.setResponsiblePartner(responsiblePartner);
      }

      // Getting the other partners that are contributing to this deliverable.
      deliverable.setOtherPartners(
        deliverablePartnerManager.getDeliverablePartners(deliverable.getId(), APConstants.DELIVERABLE_PARTNER_OTHER));
    }

    // Add Deliverables
    project.setDeliverables(deliverables);


    // *************************Outcomes*****************************
    project.setOutcomes(projectOutcomeManager.getProjectOutcomesByProject(project.getId()));

    // Getting the information for the IP Other Contribution
    project.setCrpContributions(crpManager.getCrpContributions(projectID));
    project.setIpOtherContribution(ipOtherContributionManager.getIPOtherContributionByProjectId(projectID));

    project.setIndicators(projectManager.getProjectIndicators(project.getId()));

    project.setActivities(activityManager.getActivitiesByProject(project.getId()));

    // *************************Budgets ******************************

    project.setBudgets(this.budgetManager.getBudgetsByProject(project));
  }
}
