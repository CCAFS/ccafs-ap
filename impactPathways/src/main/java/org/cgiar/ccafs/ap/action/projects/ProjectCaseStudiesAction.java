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
package org.cgiar.ccafs.ap.action.projects;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.CaseStudiesManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.CaseStudieIndicators;
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.validation.projects.ProjectCrossCuttingValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Christian David Garcia Oviedo- CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectCaseStudiesAction extends BaseAction {

  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private CaseStudiesManager caseStudieManager;
  private ProjectManager projectManager;
  private IPIndicatorManager ipIndicatorMamager;

  private File file;
  private String fileFileName;
  private ProjectCrossCuttingValidator validator;

  private List<IPIndicator> caseStudyIndicators;
  private int projectID;
  private List<Integer> allYears;


  private Project project;


  @Inject
  public ProjectCaseStudiesAction(APConfig config, ProjectManager projectManager, IPIndicatorManager ipIndicatorMamager,
    CaseStudiesManager crossManager, HistoryManager historyManager, ProjectCrossCuttingValidator validator) {
    super(config);
    this.validator = validator;
    this.caseStudieManager = crossManager;
    this.projectManager = projectManager;
    this.ipIndicatorMamager = ipIndicatorMamager;

  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public List<IPIndicator> getCaseStudyIndicators() {
    return caseStudyIndicators;
  }


  private String getCaseStudyPath() {
    return config.getUploadsBaseFolder() + File.separator + this.getCaseStudyRelativePath() + File.separator;
  }


  private String getCaseStudyRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator + "caseStudy"
      + File.separator;
  }


  public String getCaseStudyURL() {
    return config.getDownloadURL() + "/" + this.getCaseStudyRelativePath().replace('\\', '/');
  }


  public File getFile() {
    return file;
  }


  public String getFileFileName() {
    return fileFileName;
  }

  public Project getProject() {
    return project;
  }

  public int getProjectID() {
    return projectID;
  }

  public ProjectManager getProjectManager() {
    return projectManager;
  }


  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();


    projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));
    project = projectManager.getProject(projectID);

    // Getting all years from project
    allYears = project.getAllYears();

    // Getting the Project lessons for this section.
    int evaluatingYear = 0;
    if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
      evaluatingYear = this.getCurrentReportingYear();
    } else {
      evaluatingYear = this.getCurrentPlanningYear();
    }

    project.setCaseStudies(caseStudieManager.getCaseStudysByProject(projectID));
    List<String> idsIndicators;
    Iterator<CaseStudieIndicators> iteratorIndicators;
    CaseStudieIndicators caseStudyIndicator;
    for (CasesStudies caseStudy : project.getCaseStudies()) {
      idsIndicators = new ArrayList<>();
      iteratorIndicators = caseStudy.getCaseStudieIndicatorses().iterator();
      while (iteratorIndicators.hasNext()) {
        caseStudyIndicator = iteratorIndicators.next();
        idsIndicators.add(String.valueOf(caseStudyIndicator.getId()));
      }
      caseStudy.setCaseStudyIndicatorsIds(idsIndicators);
    }

    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), evaluatingYear, this.getCycleName()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
    caseStudyIndicators = ipIndicatorMamager.getProjectIndicators(projectID);

    // Getting the last history

  }


  @Override
  public String save() {

    List<CaseStudieIndicators> indicators;
    CaseStudieIndicators caseStudieIndicator;
    for (CasesStudies caseStudie : project.getCaseStudies()) {

      if (caseStudie.getMyFile() != null) {

        FileManager.deleteFile(this.getCaseStudyPath() + caseStudie.getFile());
        FileManager.copyFile(caseStudie.getMyFile(), this.getCaseStudyPath() + caseStudie.getMyFileFileName());
        caseStudie.setFile(caseStudie.getMyFileFileName());
      }
      indicators = new ArrayList<>();

      for (String indicator : caseStudie.getCaseStudyIndicatorsIds()) {
        caseStudieIndicator = new CaseStudieIndicators();
        caseStudieIndicator.setCasesStudies(caseStudie);
        caseStudieIndicator.setIdIndicator(Integer.parseInt(indicator));
        indicators.add(caseStudieIndicator);
      }
      caseStudie.setIsActive(true);
      caseStudie.setCaseStudieIndicatorses(new HashSet<>(indicators));
      caseStudieManager.saveCaseStudy(projectID, caseStudie, this.getCurrentUser(), this.getJustification());
    }

    return SUCCESS;
  }


  public void setCaseStudyIndicators(List<IPIndicator> caseStudyIndicators) {
    this.caseStudyIndicators = caseStudyIndicators;
  }


  public void setFile(File file) {
    this.file = file;
  }


  public void setFileFileName(String fileFileName) {
    this.fileFileName = fileFileName;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(int projectID) {
    this.projectID = projectID;
  }


  public void setProjectManager(ProjectManager projectManager) {
    this.projectManager = projectManager;
  }

  @Override
  public void validate() {
    if (save) {
      // validator.validate(this, project, this.getCycleName());
    }
  }
}