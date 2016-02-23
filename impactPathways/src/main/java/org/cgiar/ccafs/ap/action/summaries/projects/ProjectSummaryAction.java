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

package org.cgiar.ccafs.ap.action.summaries.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.BudgetOverheadManager;
import org.cgiar.ccafs.ap.data.manager.CRPManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudiesManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.HighLightManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.PartnerPersonManager;
import org.cgiar.ccafs.ap.data.manager.ProjectCofinancingLinkageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.manager.ProjectLessonsManager;
import org.cgiar.ccafs.ap.data.manager.ProjectLeverageManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectNextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.SubmissionManager;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.CaseStudieIndicators;
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.ProjecteOtherContributions;
import org.cgiar.ccafs.ap.summaries.projects.pdf.ProjectSummaryPDF;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.summaries.Summary;

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
 * @author Jorge Leonardo Solis B.
 * @author Hernán David Carvajal
 */

public class ProjectSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(ProjectSummaryAction.class);
  private static final long serialVersionUID = 5140987672008315842L;

  // Managers
  private ActivityManager activityManager;
  private BudgetManager budgetManager;
  private DeliverableManager deliverableManager;
  private DeliverablePartnerManager deliverablePartnerManager;
  private IPElementManager ipElementManager;
  private ProjectOtherContributionManager ipOtherContributionManager;
  private IPProgramManager ipProgramManager;
  private ProjectCofinancingLinkageManager linkedCoreProjectManager;
  private LocationManager locationManager;
  private NextUserManager nextUserManager;
  private ProjectContributionOverviewManager overviewManager;
  private ProjectPartnerManager partnerManager;
  private ProjectManager projectManager;
  private PartnerPersonManager partnerPersonManager;
  private ProjectOutcomeManager projectOutcomeManager;
  private IPIndicatorManager indicatorManager;
  private ProjectLessonsManager projectLessonsManager;
  private SubmissionManager submisssionManager;
  private BudgetOverheadManager budgetOverheadManager;
  private CRPManager crpManager;
  private CaseStudiesManager caseStudiesManager;
  private ProjectNextUserManager projectNextUserManager;
  private ProjectLeverageManager projectLeverageManager;
  private HighLightManager highlightManager;
  private InstitutionManager institutionManager;
  // Model
  private Project project;
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
    CRPManager crpManager, PartnerPersonManager partnerPersonManager, IPIndicatorManager indicatorManager,
    ProjectLessonsManager projectLessonsManager, SubmissionManager submisssionManager,
    BudgetOverheadManager budgetOverheadManager, CaseStudiesManager caseStudiesManager,
    ProjectNextUserManager projectNextUserManager, ProjectLeverageManager projectLeverageManager,
    HighLightManager highlightManager) {
    super(config);
    this.caseStudiesManager = caseStudiesManager;
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
    this.deliverableManager = deliverableManager;
    this.nextUserManager = nextUserManager;
    this.deliverablePartnerManager = deliverablePartnerManager;
    this.ipOtherContributionManager = ipOtherContributionManager;
    this.partnerPersonManager = partnerPersonManager;
    this.indicatorManager = indicatorManager;
    this.projectLessonsManager = projectLessonsManager;
    this.submisssionManager = submisssionManager;
    this.budgetOverheadManager = budgetOverheadManager;
    this.crpManager = crpManager;
    this.projectNextUserManager = projectNextUserManager;
    this.projectLeverageManager = projectLeverageManager;
    this.highlightManager = highlightManager;
    this.institutionManager = institutionManager;
  }


  @Override
  public String execute() throws Exception {
    int currentPlanningYear = this.config.getPlanningCurrentYear();
    int currentReportingYear = this.config.getReportingCurrentYear();
    int midOutcomeYear = this.config.getMidOutcomeYear();
    // Generate the pdf file
    projectPDF.generatePdf(project, currentPlanningYear, currentReportingYear, midOutcomeYear);

    streams = new ArrayList<>();
    streams.add(projectPDF.getInputStream());

    return SUCCESS;
  }

  private String getBilateralContractRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getBilateralProjectContractProposalFolder() + File.separator;
  }

  public String getBilateralContractURL() {
    return config.getDownloadURL() + "/" + this.getBilateralContractRelativePath().replace('\\', '/');
  }


  public Budget getCofinancingBudget(int projectID, int cofinanceProjectID, int year) {

    List<Budget> budgets = this.project.getBudgets();
    for (Budget budget : budgets) {
      if (budget != null) {
        if (budget.getProjectId() == projectID && budget.getCofinancingProject() != null
          && budget.getCofinancingProject().getId() == cofinanceProjectID && budget.getYear() == year) {
          return budget;
        }
      }
    }
    return null;
  }

  @Override
  public int getContentLength() {
    return projectPDF.getContentLength();
  }


  @Override
  public String getContentType() {
    return "application/pdf";
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

  @Override
  public void prepare() throws Exception {
    int projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    String cycle = StringUtils.trim(this.getRequest().getParameter(APConstants.CYCLE));


    // Get all the information to add in the pdf file
    project = projectManager.getProject(projectID);
    project.setReporting(cycle);

    // Get a route for the workplan name
    if (project.isWorkplanRequired()) {
      String workPlanURL = this.getWorkplanURL();
      if (workPlanURL != null || project.getWorkplanName() != null) {
        project.setWorkplanURL(workPlanURL + project.getWorkplanName());
      }
    }
    // Get a route for the bilateral contract
    if (project.isBilateralProject()) {
      String bilateralContractURL = this.getBilateralContractURL();
      if (bilateralContractURL != null || project.getBilateralContractProposalName() != null) {
        project.setWorkplanURL(bilateralContractURL + project.getBilateralContractProposalName());
      }
    }

    // Getting the information of the Regions program
    project.setRegions(ipProgramManager.getProjectFocuses(project.getId(), APConstants.REGION_PROGRAM_TYPE));

    // Getting the information of the Flagships program
    project.setFlagships(ipProgramManager.getProjectFocuses(project.getId(), APConstants.FLAGSHIP_PROGRAM_TYPE));

    // Set submissions
    project.setSubmissions(submisssionManager.getProjectSubmissions(project));

    List<ProjectPartner> projectPartnerList = this.partnerManager.getProjectPartners(project);

    for (ProjectPartner projectPartner : projectPartnerList) {
      projectPartner.setPartnerPersons(this.partnerPersonManager.getPartnerPersons(projectPartner));
      projectPartner.setPartnerContributors(partnerManager.getProjectPartnerContributors(projectPartner));
    }
    project.setProjectPartners(projectPartnerList);

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
      if (listaOver.get(a).getOutput() != null) {
        listaOver.get(a).setOutput(ipElementManager.getIPElement(listaOver.get(a).getOutput().getId()));
      }
    }
    project.setOutputsOverview(listaOver);

    // *************************Deliverables*****************************/
    project.setDeliverables(deliverableManager.getDeliverablesByProject(projectID));

    // *************************Next users*****************************/
    project.setNextUsers(this.projectNextUserManager.getProjectNextUserProject(projectID));

    // *************************Outcomes*****************************/
    project.setOutcomes(projectOutcomeManager.getProjectOutcomesByProject(project.getId()));

    project.setIpOtherContribution(ipOtherContributionManager.getIPOtherContributionByProjectId(projectID));

    project.setIndicators(indicatorManager.getProjectIndicators(project.getId()));

    project.setActivities(activityManager.getActivitiesByProject(project.getId()));


    List<ProjecteOtherContributions> projectOtherList =
      ipOtherContributionManager.getOtherContributionsByProjectId(project.getId());
    for (ProjecteOtherContributions projectOther : projectOtherList) {
      // Changing indicator_id for indicator_description
      projectOther.setIndicators(
        this.indicatorManager.getIndicator(Integer.parseInt(projectOther.getIndicators())).getDescription());
      // Changing Id region By the composed name
      projectOther
        .setRegion(ipProgramManager.getIPProgramById(Integer.parseInt(projectOther.getRegion())).getComposedName());
    }
    project.setOtherContributions(projectOtherList);

    project.setListCRPContributions(crpManager.getCrpContributionsNature(project.getId()));

    List<CasesStudies> caseStudiesList = caseStudiesManager.getCaseStudysByProject(project.getId());

    List<IPIndicator> ipIndicatorList;
    IPIndicator ipIndicator;

    for (CasesStudies caseStudie : caseStudiesList) {
      ipIndicatorList = new ArrayList<IPIndicator>();
      for (CaseStudieIndicators indicatorCaseStudie : caseStudie.getCaseStudieIndicatorses()) {
        ipIndicator = indicatorManager.getIndicatorFlgship(indicatorCaseStudie.getIdIndicator());
        ipIndicatorList.add(ipIndicator);
      }
      caseStudie.setCaseStudyIndicators(ipIndicatorList);
    }
    project.setCaseStudies(caseStudiesList);

    // *************************Budgets******************************/
    project.setBudgets(this.budgetManager.getBudgetsByProject(project));

    // Set Leasson regarding
    project.setComponentLessons(this.projectLessonsManager.getComponentLessonsByProjectAndCycle(projectID, cycle));

    // Set project overhead
    if (project.isBilateralProject()) {
      project.setOverhead(this.budgetOverheadManager.getProjectBudgetOverhead(project.getId()));
    }


    // *************************Annual contribution*******************/
    for (Project projectContributor : project.getLinkedProjects()) {
      if (project.isBilateralProject()) {
        projectContributor.setAnualContribution(
          this.getCofinancingBudget(projectContributor.getId(), projectID, config.getPlanningCurrentYear()));
      } else if (project.isCoFundedProject()) {
        projectContributor.setAnualContribution(
          this.getCofinancingBudget(projectID, projectContributor.getId(), config.getPlanningCurrentYear()));
      }
    }

    // ************************Project Leverage *******************

    List<ProjectLeverage> projectLeverageList = projectLeverageManager.getProjectLeverageProject(projectID);
    for (ProjectLeverage projectLeverage : projectLeverageList) {
      if (projectLeverage.getInstitution() != null) {
        projectLeverage.setMyInstitution(institutionManager.getInstitution(projectLeverage.getInstitution()));
      }

      if (projectLeverage.getFlagship() != null) {
        projectLeverage.setMyFlagship(this.ipProgramManager.getIPProgramById(projectLeverage.getFlagship()));
      }
    }

    project.setLeverages(projectLeverageList);

    // ************************Project HighLigth *******************

    List<ProjectHighligths> projectHighLightList = highlightManager.getHighLightsByProject(projectID);
    List<Country> countryList;
    for (ProjectHighligths projectHighligth : projectHighLightList) {
      countryList = new ArrayList<Country>();
      if (projectHighligth.getCountriesIds() != null) {
        for (Integer countryId : projectHighligth.getCountriesIds()) {
          countryList.add(this.locationManager.getCountry(countryId.intValue()));
        }
      }
      projectHighligth.setCountries(countryList);
    }
    project.setHighlights(projectHighLightList);

    //
    // for (ProjectHighligths projectHighLight : projectHighLightList) {
    // Set<ProjectHighligthsCountry> countriesHighLight = projectHighLight.getProjectHighligthsCountries();
    //
    //
    // Set<ProjectHighligthsTypes> highLightTypes = projectHighLight.getProjectHighligthsTypeses();
    // }
    //

  }
}
