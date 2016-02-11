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
import org.cgiar.ccafs.ap.data.manager.CrossCuttingContributionManager;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.CategoryCrossCutingEnum;
import org.cgiar.ccafs.ap.data.model.CrossCuttingContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.util.FileManager;
import org.cgiar.ccafs.ap.validation.projects.ProjectCrossCuttingValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Christian David Garcia Oviedo- CIAT/CCAFS
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class ProjectCrossCuttingAction extends BaseAction {

  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private CrossCuttingContributionManager crossManager;
  private ProjectManager projectManager;

  private Map<String, String> commEngageCategories;
  private File file;
  private String fileFileName;
  private ProjectCrossCuttingValidator validator;


  private int projectID;


  private CrossCuttingContribution contribution;


  private Project project;


  @Inject
  public ProjectCrossCuttingAction(APConfig config, ProjectManager projectManager,
    CrossCuttingContributionManager crossManager, HistoryManager historyManager,
    ProjectCrossCuttingValidator validator) {
    super(config);
    this.validator = validator;
    this.crossManager = crossManager;
    this.projectManager = projectManager;

  }

  public Map<String, String> getCommEngageCategories() {
    return commEngageCategories;
  }

  public CrossCuttingContribution getContribution() {
    return contribution;
  }

  private String getCrossCuttingAbsolutePath() {
    return config.getUploadsBaseFolder() + File.separator + this.getCrossCuttingRelativePath() + File.separator;
  }


  private String getCrossCuttingRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator + "crosscutting"
      + File.separator;
  }


  public String getCrossCuttingURL() {
    return config.getDownloadURL() + "/" + this.getCrossCuttingRelativePath().replace('\\', '/');
  }


  public CrossCuttingContributionManager getCrossManager() {
    return crossManager;
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
    List<CrossCuttingContribution> listCross = crossManager.getCrossCuttingContributionsByProject(projectID);
    if (listCross.size() > 0) {
      contribution = listCross.get(0);
    }


    commEngageCategories = new HashMap<>();
    List<CategoryCrossCutingEnum> list = Arrays.asList(CategoryCrossCutingEnum.values());
    for (CategoryCrossCutingEnum category : list) {
      commEngageCategories.put(category.getId(), category.getDescription());
    }

    // Getting the Project lessons for this section.
    int evaluatingYear = 0;
    if (this.getCycleName().equals(APConstants.REPORTING_SECTION)) {
      evaluatingYear = this.getCurrentReportingYear();
    } else {
      evaluatingYear = this.getCurrentPlanningYear();
    }
    // Getting the Project lessons for this section.
    this.setProjectLessons(
      lessonManager.getProjectComponentLesson(projectID, this.getActionName(), evaluatingYear, this.getCycleName()));

    // Initializing Section Statuses:
    this.initializeProjectSectionStatuses(project, this.getCycleName());
    if (contribution == null) {
      project.setCrossCutting(new CrossCuttingContribution());
    } else {
      project.setCrossCutting(contribution);
    }

    // Getting the last history

  }


  @Override
  public String save() {
    if (this.hasProjectPermission("update", project.getId())) {
      if (file != null) {
        FileManager.deleteFile(this.getCrossCuttingAbsolutePath() + contribution.getFile());
        FileManager.copyFile(file, this.getCrossCuttingAbsolutePath() + fileFileName);
        project.getCrossCutting().setFile(fileFileName);
      }
      this.saveProjectLessons(project.getId());
      crossManager.saveCrossCuttingContribution(projectID, project.getCrossCutting(), this.getCurrentUser(),
        this.getJustification());
      return SUCCESS;
    }
    return NOT_AUTHORIZED;
  }


  public void setCommEngageCategories(Map<String, String> commEngageCategories) {
    this.commEngageCategories = commEngageCategories;
  }


  public void setContribution(CrossCuttingContribution contribution) {
    this.contribution = contribution;
  }


  public void setCrossManager(CrossCuttingContributionManager crossManager) {
    this.crossManager = crossManager;
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
      validator.validate(this, project, this.getCycleName());
    }
  }
}