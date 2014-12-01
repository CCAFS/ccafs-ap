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
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Project;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal
 */

public class ProjectSummaryAction extends BaseAction implements Summary {

  private static final long serialVersionUID = 5140987672008315842L;
  public static Logger LOG = LoggerFactory.getLogger(ProjectSummaryAction.class);

  // Managers
  ProjectManager projectManager;
  IPProgramManager ipProgramManager;
  ProjectPartnerManager partnerManager;
  BudgetManager budgetManager;
  ProjectOutcomeManager projectOutcomeManager;
  ActivityManager activityManager;

  // Model
  ProjectSummaryPDF projectPDF;
  Project project;
  List<InputStream> streams;

  @Inject
  public ProjectSummaryAction(APConfig config, ProjectSummaryPDF projectPDF, ProjectManager projectManager,
    IPProgramManager ipProgramManager, ProjectPartnerManager partnerManager, BudgetManager budgetManager,
    ProjectOutcomeManager projectOutcomeManager, ActivityManager activityManager) {
    super(config);
    this.projectPDF = projectPDF;
    this.projectManager = projectManager;
    this.ipProgramManager = ipProgramManager;
    this.partnerManager = partnerManager;
    this.budgetManager = budgetManager;
    this.projectOutcomeManager = projectOutcomeManager;
    this.activityManager = activityManager;
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

  public int getContentLength() {
    return projectPDF.getContentLength();
  }

  public String getFileName() {
    return projectPDF.getFileName();
  }

  public InputStream getInputStream() {
    return projectPDF.getInputStream();
  }

  @Override
  public void prepare() throws Exception {
    int projectID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_REQUEST_ID)));

    // Get all the information to add in the pdf file
    project = projectManager.getProject(projectID);

    // Getting the information of the Regions program
    project.setRegions(ipProgramManager.getProjectFocuses(project.getId(), APConstants.REGION_PROGRAM_TYPE));

    // Getting the information of the Flagships program
    project.setFlagships(ipProgramManager.getProjectFocuses(project.getId(), APConstants.FLAGSHIP_PROGRAM_TYPE));

    // Getting the project leader
    project.setLeader(projectManager.getProjectLeader(project.getId()));

    project.setProjectPartners(partnerManager.getProjectPartners(project.getId()));

    project.setBudgets(budgetManager.getBudgetsByProject(project));

    project.setOutcomes(projectOutcomeManager.getProjectOutcomesByProject(project.getId()));

    project.setIndicators(projectManager.getProjectIndicators(project.getId()));

    project.setActivities(activityManager.getActivitiesByProject(project.getId()));
  }

  public InputStream testing(List<InputStream> streamOfPDFFiles) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    try {
      System.out.println("Entrando");
      Document PDFCombineUsingJava = new Document();
      PdfCopy copy = new PdfCopy(PDFCombineUsingJava, outputStream);
      PDFCombineUsingJava.open();
      PdfReader readInputPDF;

      int number_of_pages;
      for (int i = 0; i < streamOfPDFFiles.size(); i++) {
        System.out.println(i);
        readInputPDF = new PdfReader(streamOfPDFFiles.get(i));
        System.out.println(i);
        number_of_pages = readInputPDF.getNumberOfPages();
        for (int page = 0; page < number_of_pages;) {
          copy.addPage(copy.getImportedPage(readInputPDF, ++page));
        }
      }
      System.out.println("Fuera");
      PDFCombineUsingJava.close();

    } catch (Exception i) {
      System.out.println(i);
    }

    return (new ByteArrayInputStream(outputStream.toByteArray()));
  }
}
