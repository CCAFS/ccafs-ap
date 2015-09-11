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
import org.cgiar.ccafs.ap.action.summaries.planning.csv.PWOBSummaryCSV;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
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
public class PWOBSummaryAction extends BaseAction implements Summary {

  public static Logger LOG = LoggerFactory.getLogger(DeliverablePlanningSummaryAction.class);
  private static final long serialVersionUID = 5110987672008315842L;

  // Managers
  private LocationManager locationManager;
  private PWOBSummaryCSV pwobSummaryCSV;
  private ProjectManager projectManager;
  private BudgetManager budgetManager;
  private ProjectPartnerManager projectPartnerManager;

  // CSV bytes
  private byte[] bytesCSV;

  // Streams
  InputStream inputStream;

  // Model
  List<Project> projectsList;

  @Inject
  public PWOBSummaryAction(APConfig config, PWOBSummaryCSV pwobSummaryCSV, ProjectManager projectManager,
    BudgetManager budgetManager, ProjectPartnerManager projectPartnerManager, LocationManager locationManager) {
    super(config);
    this.locationManager = locationManager;
    this.pwobSummaryCSV = pwobSummaryCSV;
    this.projectManager = projectManager;
    this.budgetManager = budgetManager;
    this.projectPartnerManager = projectPartnerManager;
  }

  @Override
  public String execute() throws Exception {

    // Generate the csv file
    bytesCSV = pwobSummaryCSV.generateCSV(projectsList);

    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return pwobSummaryCSV.getBytes().length;
  }

  @Override
  public String getFileName() {
    StringBuffer fileName = new StringBuffer();
    fileName.append("PWOB");
    fileName.append("-");
    fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
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


    projectsList = this.projectManager.getAllProjectsBasicInfo();
    List<ProjectPartner> partnersList;
    List<Location> locationsList;

    for (Project project : projectsList) {

      // ***************** Partners ******************************
      partnersList = projectPartnerManager.getProjectPartners(project);
      project.setProjectPartners(partnersList);

      // ***************** Locations ******************************
      locationsList = this.locationManager.getProjectLocations(project.getId());
      project.setLocations(locationsList);

      // *************************Budgets ******************************
      project.setBudgets(this.budgetManager.getBudgetsByProject(project));
    }

  }
}
