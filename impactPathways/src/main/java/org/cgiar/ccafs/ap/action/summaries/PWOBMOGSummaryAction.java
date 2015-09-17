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
import org.cgiar.ccafs.ap.action.summaries.planning.xls.PWOBMOGSummaryXLS;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jorge Leonardo Solis B. CCAFS
 */
public class PWOBMOGSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(DeliverablePlanningSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;

  private PWOBMOGSummaryXLS pwobSummaryXLS;
  private ProjectManager projectManager;
  private BudgetManager budgetManager;
  private ProjectOutcomeManager projectOutcomeManager;
  private int startYear, endYear;

  // XLS bytes
  private byte[] bytesXLS;

  // Streams
  InputStream inputStream;

  // Model
  List<Project> projectsList;

  @Inject
  public PWOBMOGSummaryAction(APConfig config, ProjectManager projectManager, BudgetManager budgetManager,
    PWOBMOGSummaryXLS pwobSummaryXLS, ProjectOutcomeManager projectOutcomeManager) {
    super(config);

    this.projectManager = projectManager;
    this.budgetManager = budgetManager;
    this.projectOutcomeManager = projectOutcomeManager;
    this.pwobSummaryXLS = pwobSummaryXLS;
    this.startYear = 9999;
    this.endYear = 0;
  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    bytesXLS = pwobSummaryXLS.generateXLS(projectsList, startYear, endYear);

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return bytesXLS.length;
  }

  @Override
  public String getContentType() {
    return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  }


  @Override
  public String getFileName() {
    StringBuffer fileName = new StringBuffer();
    fileName.append("POWB-MOGs-");
    fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
    fileName.append(".xlsx");

    return fileName.toString();

  }

  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesXLS);
    }
    return inputStream;
  }

  @Override
  public void prepare() {
    projectsList = this.projectManager.getAllProjectsBasicInfo();

    for (Project project : projectsList) {

      // *************************Budgets ******************************
      project.setBudgets(this.budgetManager.getBudgetsByProject(project));

      // *************************Outcomes*****************************
      project.setOutcomes(projectOutcomeManager.getProjectOutcomesByProject(project.getId()));

      if (project.getStartDate().getYear() < this.startYear) {
        startYear = project.getStartDate().getYear();
      }
    }

  }
}
