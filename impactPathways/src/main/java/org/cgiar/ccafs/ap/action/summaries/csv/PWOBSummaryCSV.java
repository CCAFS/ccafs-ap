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

package org.cgiar.ccafs.ap.action.summaries.csv;

import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;


/**
 * @author Your name
 */
public class PWOBSummaryCSV extends BaseCSV {


  /**
   * Method constructor.
   */
  APConfig config;
  BudgetManager budgetManager;

  @Inject
  public PWOBSummaryCSV(APConfig config, BudgetManager budgetManager) {

    COMMA_DELIMITER = ",";
    NEW_LINE_SEPARATOR = "\n";
    headers =
      new String[] {"Project Id", "Flagship(s)", "Project title", "Project summary", "Lead institution",
      "Lead institution acronym", "Region(s) covered", "W1/W2 Budget", "W3/Bilateral Budget", "Activity ID",
      "Activity title", "Activity description", "activity leader", "locations"};

    this.budgetManager = budgetManager;
    // Project ID Flagship(s) Project title Project summary Lead institution Lead institution acronym Regions covered
    // W1/W2 Budget W3/Bilateral Budget Activity ID Activity title Activity description activity leader deliverable
    // project MOG to which the deliverable contributes Locations
    this.config = config;
  }

  /**
   * Method is used for to add the deliverable
   * 
   * @param projectList it is a list that contain the projects
   */
  private void addContent(List<Project> projectList) {

    List<Activity> activities;
    StringBuilder stringBuilder;
    int counter = 0;
    Project project;
    // for (int a = 0; a < projectList.size(); a++) {
    for (int a = 0; a < 3; a++) {
      project = projectList.get(a);
      activities = project.getActivities();

      for (Activity activity : activities) {

        try {
          // Activity
          if (activity != null) {
            stringBuilder = new StringBuilder();

            // Project Id
            this.addRegister(project.getId(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Flashig
            counter = 0;
            stringBuilder = new StringBuilder();
            for (IPProgram flashig : project.getFlagships()) {
              if (counter != 0) {
                stringBuilder.append(", ");
              }
              stringBuilder.append(flashig.getAcronym());
              counter++;
            }

            this.addRegister(stringBuilder.toString(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Title
            this.addRegister(project.getTitle(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Summary
            this.addRegister(project.getSummary(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            if (project.getLeader() != null) {
              // Lead institution Acronym
              this.addRegister(project.getLeader().getInstitution().getAcronym(), fileWriter);
              fileWriter.append(COMMA_DELIMITER);

              // Lead institution
              this.addRegister(project.getLeader().getInstitution().getName(), fileWriter);
              fileWriter.append(COMMA_DELIMITER);
            } else {
              this.addRegister(null, fileWriter);
              fileWriter.append(COMMA_DELIMITER);

              this.addRegister(null, fileWriter);
              fileWriter.append(COMMA_DELIMITER);
            }

            // Region
            counter = 0;
            stringBuilder = new StringBuilder();
            for (IPProgram region : project.getRegions()) {
              if (counter != 0) {
                stringBuilder.append("-");
              }
              stringBuilder.append(region.getAcronym());
              counter++;
            }
            this.addRegister(stringBuilder.toString(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // W1/W2 Budget
            this.addRegister(
              budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), BudgetType.W1_W2.getValue()), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // W3/Bilateral Budget
            this.addRegister(
              budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), BudgetType.W3_BILATERAL.getValue()),
              fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Activity Id
            this.addRegister(activity.getId(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Activity title
            this.addRegister(activity.getTitle(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Activity description
            this.addRegister(activity.getDescription(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Activity leader
            if (activity.getLeader() != null) {
              this.addRegister(activity.getLeader().getComposedName(), fileWriter);
            } else {
              this.addRegister(null, fileWriter);
            }
            fileWriter.append(COMMA_DELIMITER);

            // Location
            counter = 0;
            stringBuilder = new StringBuilder();
            for (Location location : project.getLocations()) {
              if (counter != 0) {
                stringBuilder.append(",");
              }
              stringBuilder.append(location.getName());
              counter++;
            }
            this.addRegister(stringBuilder.toString(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // MOG
            // if (activity.getOutput() != null) {
            // this.addRegister(activity.getOutput().getDescription(), fileWriter);
            // } else {
            // this.addRegister(this.getText("summaries.project.empty"), fileWriter);
            // }
            // fileWriter.append(COMMA_DELIMITER);

            // // Year
            // this.addRegister(activity.getYear(), fileWriter);
            // fileWriter.append(COMMA_DELIMITER);
            //
            // // Main Type
            // this.addRegister(activity.getType().getCategory().getName(), fileWriter);
            // fileWriter.append(COMMA_DELIMITER);
            //
            // // Sub Type
            // this.addRegister(activity.getType().getName(), fileWriter);
            // fileWriter.append(COMMA_DELIMITER);
            //
            // // Other type
            // stringBuilder = new StringBuilder();
            // if (activity.getTypeOther() == null || activity.getTypeOther().equals("")) {
            // stringBuilder.append(this.getText("summaries.project.notapplicable"));
            // } else {
            // stringBuilder.append(activity.getTypeOther());
            // }
            // this.addRegister(stringBuilder.toString(), fileWriter);
            // fileWriter.append(COMMA_DELIMITER);
            //
            // // Partner Responsible
            // if (activity.getResponsiblePartner() != null && (activity.getResponsiblePartner().getPartner() != null))
            // {
            // this.addRegister(activity.getResponsiblePartner().getPartner().getComposedName(), fileWriter);
            // } else {
            // this.addRegister("", fileWriter);
            // }
            // fileWriter.append(COMMA_DELIMITER);
            //
            // // Others Partners
            // DeliverablePartner otherPartner;
            // stringBuilder = new StringBuilder();
            // if (activity.getOtherPartners() != null && !activity.getOtherPartners().isEmpty()) {
            // for (int b = 0; b < activity.getOtherPartners().size(); b++) {
            // otherPartner = activity.getOtherPartners().get(b);
            // if (otherPartner != null && otherPartner.getPartner() != null) {
            // if (b != 0) {
            // stringBuilder.append("; ");
            // }
            // stringBuilder.append(otherPartner.getPartner().getComposedName());
            // }
            // }
            // } else {
            // stringBuilder.append(this.getText("summaries.project.empty"));
            // }
            // this.addRegister(stringBuilder, fileWriter);
            // fileWriter.append(COMMA_DELIMITER);


            fileWriter.append(this.NEW_LINE_SEPARATOR);

          }

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Method is used to generate the csv for the deliverable.
   * 
   * @param projectList it is a list that contain the project with the nessesary information
   */

  public void generateCSV(List<Project> projectList) {

    File file = new File(config.getUploadsBaseFolder() + "temporal.txt");
    fileWriter = null;
    this.initializeCsv(file);

    try {
      fileWriter = new FileWriter(file, true);
      fileWriter.write('\ufeff');

      this.addHeaders(headers, fileWriter);
      this.addContent(projectList);
      fileWriter.close();

      // *********************Created the fileName****************************
      // Expected-deliverables-fecha(yyyyMMdd)


      StringBuffer fileName = new StringBuffer();
      fileName.append("PWOB");
      fileName.append("-");
      fileName.append(new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date()));
      fileName.append(".csv");

      this.fileName = fileName.toString();
      // *****************************************************************

      inputStream = new FileInputStream(file);
      contentLength = (int) file.length();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
