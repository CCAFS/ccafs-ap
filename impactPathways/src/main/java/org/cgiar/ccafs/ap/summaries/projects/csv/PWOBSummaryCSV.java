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

package org.cgiar.ccafs.ap.summaries.projects.csv;

import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;


/**
 * @author Jorge Leonardo Solis B. CIAT-CCAFS
 */
public class PWOBSummaryCSV extends BaseCSV {


  /**
   * Method constructor.
   */
  BudgetManager budgetManager;

  @Inject
  public PWOBSummaryCSV(APConfig config, BudgetManager budgetManager) {

    this.budgetManager = budgetManager;
  }

  /**
   * Method is used for to add the information in CSV file
   * 
   * @param projectList it is a list that contain the projects
   */
  private void addContent(List<Project> projectList) {


    StringBuilder stringBuilder;
    int counter = 0;
    Project project;
    List<ProjectPartner> listProjectPartnersPPA;
    // for (int a = 0; a < projectList.size(); a++) {
    double W1W2, W3Bilateral;

    try {
      for (int a = 0; a < 3; a++) {
        project = projectList.get(a);
        listProjectPartnersPPA = project.getPPAPartners();

        for (ProjectPartner projectPartnerPPA : listProjectPartnersPPA) {
          if (projectPartnerPPA != null) {

            stringBuilder = new StringBuilder();
            // Project Id
            this.writeString(String.valueOf(project.getId()), true, false);
            this.writeSeparator();

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

            this.writeString(stringBuilder.toString(), true, false);
            this.writeSeparator();

            // Title
            this.writeString(project.getTitle(), true, false);
            this.writeSeparator();

            // Summary
            this.writeString(project.getSummary(), true, false);
            this.writeSeparator();

            // Lead institution Acronym
            this.writeString(project.getLeader().getInstitution().getAcronym(), true, false);
            this.writeSeparator();

            // Lead institution
            this.writeString(project.getLeader().getInstitution().getName(), true, false);
            this.writeSeparator();

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
            this.writeString(stringBuilder.toString(), true, false);
            this.writeSeparator();

            // W1/W2 Budget
            W1W2 =
              budgetManager.calculateTotalCCAFSBudgetByInstitutionAndType(project.getId(), projectPartnerPPA
                .getInstitution().getId(), BudgetType.W1_W2.getValue());

            this.writeString(String.valueOf(W1W2), true, false);
            this.writeSeparator();

            // W3/Bilateral Budget
            W3Bilateral =
              budgetManager.calculateTotalCCAFSBudgetByInstitutionAndType(project.getId(), projectPartnerPPA
                .getInstitution().getId(), BudgetType.W3_BILATERAL.getValue());

            this.writeString(String.valueOf(W3Bilateral), true, false);
            this.writeSeparator();

            // Location
            counter = 0;
            project.getLocations();
            stringBuilder = new StringBuilder();
            for (Location location : project.getLocations()) {
              if (counter != 0) {
                stringBuilder.append(", ");
              }
              stringBuilder.append(location.getName());
              counter++;
            }
            this.writeString(stringBuilder.toString(), true, false);
            this.writeSeparator();

            this.writeNewLine();
          }

        }
      }

    } catch (IOException e) {
      e.printStackTrace();

    }
  }

  /**
   * Method is used to generate the csv for the deliverable.
   * 
   * @param projectList it is a list that contain the project with the nessesary information
   */

  public byte[] generateCSV(List<Project> projectList) {

    try {
      this.initializeCSV();
      String[] headers =
        new String[] {"Project Id", "Flagship(s)", "Project title", "Project summary", "Lead institution acronym",
          "Lead institution", "Region(s) covered", "W1/W2 Budget", "W3/Bilateral Budget", "locations"};

      this.addHeaders(headers);
      this.addContent(projectList);
      this.flush();
      this.closeStreams();

      // returning the bytes that are in the output stream.
      return this.getBytes();

    } catch (IOException e) {
      e.printStackTrace();
    }

    return null;
  }


}
