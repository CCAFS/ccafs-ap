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

package org.cgiar.ccafs.ap.summaries.planning.xlsx;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Jorge Leonardo Solis B.
 */
public class POWBMOGSummaryXLS {

  private BaseXLS xls;


  @Inject
  public POWBMOGSummaryXLS(BaseXLS xls) {
    this.xls = xls;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Map<String, Object>> informationListMapPOWB, Sheet sheet, int indexSheet) {

    Map<String, Object> mapObject;
    StringBuilder stringBuilder;
    String valueOne, valueTwo;
    // Iterating all the projects

    if (indexSheet == 0) {
      for (int a = 0; a < informationListMapPOWB.size(); a++) {
        mapObject = informationListMapPOWB.get(a);

        // Iterating all the partners

        // Outcome
        stringBuilder = new StringBuilder();

        valueOne = (String) mapObject.get("flagship_outcome");
        valueTwo = (String) mapObject.get("outcome_2019");

        if (valueOne != null && valueTwo != null) {
          stringBuilder.append(valueOne);
          stringBuilder.append(" - ");
          stringBuilder.append(valueTwo);
        } else {
          stringBuilder.append("");
        }

        xls.writeString(sheet, stringBuilder.toString());
        xls.nextColumn();

        // MOG description
        stringBuilder = new StringBuilder();

        valueOne = (String) mapObject.get("flagship_mog");
        valueTwo = (String) mapObject.get("mog_description");

        if (valueOne != null && valueTwo != null) {
          stringBuilder.append(valueOne);
          stringBuilder.append(" - ");
          stringBuilder.append(valueTwo);
        } else {
          stringBuilder.append("");
        }

        xls.writeString(sheet, stringBuilder.toString());
        xls.nextColumn();

        // budget_W1_W2
        xls.writeBudget(sheet, (double) mapObject.get("budget_W1_W2"));
        xls.nextColumn();

        // gender_W1_W2
        xls.writeBudget(sheet, (double) mapObject.get("gender_W1_W2"));
        xls.nextColumn();

        // budget_W3_Bilateral
        xls.writeBudget(sheet, (double) mapObject.get("budget_W3_Bilateral"));
        xls.nextColumn();

        // gender_W3_Bilateral
        xls.writeBudget(sheet, (double) mapObject.get("gender_W3_Bilateral"));
        xls.nextColumn();

        xls.nextRow();

      }
    } else if (indexSheet == 1) {
      for (int a = 0; a < informationListMapPOWB.size(); a++) {
        mapObject = informationListMapPOWB.get(a);

        // Iterating all the partners

        // Project id
        xls.writeInteger(sheet, (int) mapObject.get("project_id"));
        xls.nextColumn();

        // Title
        xls.writeString(sheet, (String) mapObject.get("project_title"));
        xls.nextColumn();

        // MOG description
        stringBuilder = new StringBuilder();


        valueOne = (String) mapObject.get("flagship");
        valueTwo = (String) mapObject.get("mog_description");

        if (valueOne != null && valueTwo != null) {
          stringBuilder.append(valueOne);
          stringBuilder.append(" - ");
          stringBuilder.append(valueTwo);
        } else {
          stringBuilder.append("");
        }


        xls.writeString(sheet, stringBuilder.toString());
        xls.nextColumn();

        // Annual description
        xls.writeString(sheet, (String) mapObject.get("anual_contribution"));
        xls.nextColumn();

        // Gender description
        xls.writeString(sheet, (String) mapObject.get("gender_contribution"));
        xls.nextColumn();

        // budget_W1_W2
        xls.writeBudget(sheet, (double) mapObject.get("budget_W1_W2"));
        xls.nextColumn();

        // gender_W1_W2
        xls.writeBudget(sheet, (double) mapObject.get("gender_W1_W2"));
        xls.nextColumn();

        // budget_W3_Bilateral
        xls.writeBudget(sheet, (double) mapObject.get("budget_W3_Bilateral"));
        xls.nextColumn();

        // gender_W3_Bilateral
        xls.writeBudget(sheet, (double) mapObject.get("gender_W3_Bilateral"));
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
  public byte[] generateXLS(List<Map<String, Object>> informationDetailPOWB, List<Map<String, Object>> informationPOWB) {

    try {

      Workbook workbook = xls.initializeWorkbook(true);

      /***************** POWB MOG Report ******************/
      // Writting headers
      String[] _headersPOWB =
        new String[] {"Outcome 2019", "MOG", " Budget Total W1/W2 (USD)", " Budget Total W1/W2 (USD)",
          "Budget Total W3/Bilateral (USD) ", "Budget Total W3/Bilateral (USD)"};

      // defining header types.
      int[] headerTypesPOWB =
        new int[] {BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};

      // creating sheet
      Sheet[] sheets = new Sheet[2];
      sheets[0] = workbook.getSheetAt(0);
      sheets[1] = workbook.cloneSheet(0);


      workbook.setSheetName(0, "P&R - POWB Summary ");

      xls.initializeSheet(sheets[0], headerTypesPOWB);

      xls.writeHeaders(sheets[0], _headersPOWB);
      this.addContent(informationPOWB, sheets[0], 0);

      // Set description
      xls.writeDescription(sheets[0], xls.getText("summaries.powb.mog.sheetone.description"));

      // write text box
      xls.writeTitleBox(sheets[0], "POWB Summary ");

      // write text box
      xls.createLogo(workbook, sheets[0]);

      /***************** POWB MOG Report Detail ******************/
      // Sheet cleanSheet =
      // Writting headers

      String[] _headersPOWBDetail =
        new String[] {"Project Id", "Project title", "MOG", "Expected annual contribution",
        "Expected plan of the gender and social inclusion", " Budget Total W1/W2 (USD)",
          " Budget Gender W1/W2 (USD)", "Budget Total W3/Bilateral (USD)", "Budget Gender W3/Bilateral (USD)"};

      // defining header types.
      int[] headerTypesPOWBDetail =
        new int[] {BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};


      workbook.setSheetName(1, "P&R - POWB Summary Detail");

      xls.initializeSheet(sheets[1], headerTypesPOWBDetail);

      xls.writeHeaders(sheets[1], _headersPOWBDetail);
      this.addContent(informationDetailPOWB, sheets[1], 1);

      // Set description
      xls.writeDescription(sheets[1], xls.getText("summaries.powb.mog.sheetone.description"));

      // write text box
      xls.writeTitleBox(sheets[1], "POWB Summary Detail");

      // write text box
      xls.createLogo(workbook, sheets[1]);

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
