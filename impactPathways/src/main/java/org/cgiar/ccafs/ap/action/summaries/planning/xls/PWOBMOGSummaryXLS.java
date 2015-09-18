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

import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.google.inject.Inject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Jorge Leonardo Solis B.
 */
public class PWOBMOGSummaryXLS {

  private BaseXLS xls;
  private BudgetManager budgetManager;

  @Inject
  public PWOBMOGSummaryXLS(BaseXLS xls, BudgetManager budgetManager) {
    this.xls = xls;
    this.budgetManager = budgetManager;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Project> projectsList, Workbook workbook) {

    // {"Outcome 2019", "MOG", "Total W1/W2(USD)", "Total W3/Bilateral(USD)", "Total W1/W2 (USD)",
    // "Total W3/Bilateral(USD)"

    double W1W2, W3Bilateral;
    StringBuilder stringBuilder;
    int counter;
    Sheet sheet = workbook.getSheetAt(0);
    Project project;
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    currencyFormatter.setMaximumFractionDigits(0);

    // Iterating all the projects
    for (int a = 0; a < 2; a++) {
      project = projectsList.get(a);

      // Iterating all the partners
      for (ProjectPartner projectPartnerPPA : project.getPPAPartners()) {
        xls.writeValue(sheet, project.getId());
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

        // Title
        xls.writeValue(sheet, project.getTitle());
        xls.nextColumn();

        // Summary
        xls.writeValue(sheet, project.getSummary());
        xls.nextColumn();

        if (project.getLeader() != null && project.getLeader().getInstitution() != null) {

          // Acronym Leader
          xls.writeValue(sheet, project.getLeader().getInstitution().getAcronym());
          xls.nextColumn();

          // Leader name
          xls.writeValue(sheet, project.getLeader().getInstitution().getName());
        } else {
          xls.nextColumn();
        }
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

        // W1/W2 Budget
        W1W2 =
          budgetManager.calculateTotalCCAFSBudgetByInstitutionAndType(project.getId(), projectPartnerPPA
            .getInstitution().getId(), BudgetType.W1_W2.getValue());

        xls.writeValue(sheet, currencyFormatter.format(W1W2));
        xls.nextColumn();

        // W3/Bilateral Budget
        W3Bilateral =
          budgetManager.calculateTotalCCAFSBudgetByInstitutionAndType(project.getId(), projectPartnerPPA
            .getInstitution().getId(), BudgetType.W3_BILATERAL.getValue());

        xls.writeValue(sheet, W3Bilateral);
        xls.nextColumn();

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
        xls.writeValue(sheet, stringBuilder.toString());

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
  public byte[] generateXLS(List<Project> projectsList, int startYear, int endYear) {

    try {


      // Writting headers

      StringBuilder headers = new StringBuilder();

      headers.append("Outcome 2019, ");
      headers.append("MOG , ");
      headers.append("Total W1/W2(USD) , ");
      headers.append("Total W3/Bilateral(USD) , ");
      headers.append("Total W1/W2(USD) , ");
      headers.append("Total W3/Bilateral(USD)");

      int[] headersType =
      {BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};


      Workbook workbook = xls.initializeXLS(true, headersType);

      // renaming sheet
      workbook.setSheetName(0, "PWOB Report");
      Sheet sheet = workbook.getSheetAt(0);
      this.addContent(projectsList, workbook);

      xls.writeHeaders(sheet, headers.toString().split(","));

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