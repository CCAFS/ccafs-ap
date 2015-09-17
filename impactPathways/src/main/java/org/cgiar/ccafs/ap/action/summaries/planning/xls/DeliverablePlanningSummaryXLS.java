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

package org.cgiar.ccafs.ap.action.summaries.planning.xls;


import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Jorge Leonardo Solis B.
 */
public class DeliverablePlanningSummaryXLS {

  private BaseXLS xls;


  @Inject
  public DeliverablePlanningSummaryXLS(BaseXLS xls) {
    this.xls = xls;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Project> projectsList, Workbook workbook) {


    StringBuilder stringBuilder;
    int counter;
    Sheet sheet = workbook.getSheetAt(0);
    Project project;

    // Iterating all the projects
    for (int a = 0; a < 2; a++) {
      project = projectsList.get(a);

      // Iterating all the partners
      for (Deliverable deliverable : project.getDeliverables()) {

        // Project id
        xls.writeValue(sheet, project.getId());
        xls.nextColumn();

        // Title
        xls.writeValue(sheet, project.getTitle());
        xls.nextColumn();

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
        xls.writeValue(sheet, stringBuilder.toString());

        xls.nextColumn();

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
        xls.writeValue(sheet, stringBuilder.toString());

        xls.nextColumn();

        // deliverable id
        xls.writeValue(sheet, deliverable.getId());
        xls.nextColumn();

        // Title
        xls.writeValue(sheet, deliverable.getTitle());
        xls.nextColumn();


        // MOG
        stringBuilder = new StringBuilder();
        if (deliverable.getOutput() != null) {
          stringBuilder.append(deliverable.getOutput().getDescription());
        }
        xls.writeValue(sheet, stringBuilder.toString());
        xls.nextColumn();

        // Year
        xls.writeValue(sheet, deliverable.getYear());
        xls.nextColumn();

        // Main type
        if (deliverable.getType() != null && deliverable.getType().getCategory() != null) {
          xls.writeValue(sheet, deliverable.getType().getCategory().getName());
        }
        xls.nextColumn();

        // Sub Type
        if (deliverable.getType() != null) {
          xls.writeValue(sheet, deliverable.getType().getName());
        }
        xls.nextColumn();

        // Other Type
        if (deliverable.getTypeOther() != null) {
          xls.writeValue(sheet, deliverable.getTypeOther());
        } else {
          xls.writeValue(sheet, "Not applicable");
        }
        xls.nextColumn();

        // Partner Responsible
        if (deliverable.getResponsiblePartner() != null && deliverable.getResponsiblePartner().getPartner() != null) {
          xls.writeValue(sheet, deliverable.getResponsiblePartner().getPartner().getComposedName());
        } else {
          xls.writeValue(sheet, "Not defined");
        }
        xls.nextColumn();

        // Other Partner
        counter = 0;

        stringBuilder = new StringBuilder();
        for (DeliverablePartner deliverablePartner : deliverable.getOtherPartners()) {
          if (deliverablePartner != null && deliverablePartner.getPartner() != null) {
            if (counter != 0) {
              stringBuilder.append(", ");
            }
            stringBuilder.append(deliverablePartner.getPartner().getComposedName());
          }
        }
        xls.writeValue(sheet, stringBuilder.toString());
        xls.nextColumn();


        xls.nextRow();
      }


    }
  }

  /**
   * This method is used to generate the csv file for the ProjectLeading institutions.
   * 
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  public byte[] generateXLS(List<Project> projectsList) {

    try {
      Workbook workbook = xls.initializeXLS(true);

      // renaming sheet
      workbook.setSheetName(0, "PWOB Report");
      Sheet sheet = workbook.getSheetAt(0);

      // Writting headers
      String[] headers =
        new String[] {"Project Id", "Project title", "Flagship(s)", "Region(s) covered", "Deliverable Id",
        "Deliverable title", "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible",
      "Others Partners"};

      xls.writeHeaders(sheet, headers);
      this.addContent(projectsList, workbook);

      // this.flush();
      xls.writeWorkbook();

      byte[] byteArray = xls.getBytes();

      // Closing streams.
      xls.closeStreams();

      return byteArray;

    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
