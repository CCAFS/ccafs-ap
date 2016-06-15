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
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.summaries.projects.csv.BudgetSummaryCSV;
import org.cgiar.ccafs.utils.APConfig;
import org.cgiar.ccafs.utils.summaries.Summary;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Carlos Alberto Martínez M.
 */
public class BudgetSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(BudgetSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;
  private BudgetSummaryCSV budgetCSV;
  private InstitutionManager institutionManager;
  private ProjectPartnerManager projectPartnerManager;
  private ProjectManager projectManager;
  private BudgetManager budgetManager;
  List<Project> projectList;
  // CSV bytes
  private byte[] bytesCSV;

  // Streams
  InputStream inputStream;

  @Inject
  public BudgetSummaryAction(APConfig config, BudgetSummaryCSV budgetCSV, InstitutionManager institutionManager,
    ProjectManager projectManager, ProjectPartnerManager projectPartnerManager, BudgetManager budgetManager) {
    super(config);
    this.budgetCSV = budgetCSV;
    this.institutionManager = institutionManager;
    this.projectManager = projectManager;
    this.budgetManager = budgetManager;
    this.projectPartnerManager = projectPartnerManager;

  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    // bytesCSV = budgetCSV.generateCSV(projectPartnerInstitutions, projectList);

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return bytesCSV.length;
  }

  @Override
  public String getContentType() {
    // TODO Auto-generated method stub
    return null;
  }


  @Override
  public String getFileName() {
    String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
    StringBuffer fileName = new StringBuffer();
    fileName.append("Budget_");
    fileName.append(date);
    fileName.append(".csv");
    return fileName.toString();
  }


  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesCSV);
    }
    return inputStream;
  }

  @Override
  public void prepare() {

    projectList = projectManager.getAllProjectsBasicInfo(this.getCycleName());
    List<ProjectPartner> partnersList;

    for (Project project : projectList) {

      // ***************** Partners ******************************
      partnersList = projectPartnerManager.getProjectPartners(project, 0);
      project.setProjectPartners(partnersList);

      // *************************Budgets ******************************
      project.setBudgets(this.budgetManager.getBudgetsByProject(project));
    }

  }
}
