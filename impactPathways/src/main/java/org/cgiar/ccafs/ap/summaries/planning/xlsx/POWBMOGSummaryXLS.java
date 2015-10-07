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


import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


/**
 * @author Jorge Leonardo Solis B.
 */
public class POWBMOGSummaryXLS {

  private BaseXLS xls;
  private APConfig config;

  @Inject
  public POWBMOGSummaryXLS(BaseXLS xls, APConfig config) {
    this.xls = xls;
    this.config = config;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Map<String, Object>> informationListMapPOWB, Sheet sheet, int indexSheet,
    XSSFWorkbook workbook) {

    CreationHelper createHelper = workbook.getCreationHelper();
    XSSFHyperlink link;
    Map<String, Object> mapObject;
    StringBuilder stringBuilder;
    String valueOne, valueTwo;
    int projectID;
    // Iterating all the projects

    if (indexSheet == 0) {
      for (int a = 0; a < informationListMapPOWB.size(); a++) {
        mapObject = informationListMapPOWB.get(a);

        // Iterating all the partners

        // Outcome
        stringBuilder = new StringBuilder();

        valueOne = (String) mapObject.get("outcome_flagship");
        valueTwo = (String) mapObject.get("outcome_description");

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

        valueOne = (String) mapObject.get("mog_flagship");
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
        xls.nextRow();

      }
    } else if (indexSheet == 1) {
      for (int a = 0; a < informationListMapPOWB.size(); a++) {
        mapObject = informationListMapPOWB.get(a);

        // Iterating all the partners

        projectID = (int) mapObject.get("project_id");
        link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectID);

        // Project id
        xls.writeHyperlink(sheet, "P" + String.valueOf(projectID), link);
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

      XSSFWorkbook workbook = xls.initializeWorkbook(true);

      /***************** POWB MOG Report ******************/
      // Writting headers
      String[] headersPOWB =
        new String[] {"Outcome 2019", "MOG", "Total Budget W1/W2 (USD)", "Gender W1/W2 (USD)",
        "Total Budget W3/Bilateral (USD)", "Gender W3/Bilateral (USD)"};

      // defining header types.
      int[] headerTypesPOWB =
        new int[] {BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};

      // creating sheet
      Sheet[] sheets = new Sheet[2];
      sheets[0] = workbook.getSheetAt(0);
      sheets[1] = workbook.cloneSheet(0);


      workbook.setSheetName(0, "Level - 1 ");

      xls.initializeSheet(sheets[0], headerTypesPOWB);

      xls.writeHeaders(sheets[0], headersPOWB);
      this.addContent(informationPOWB, sheets[0], 0, workbook);

      // Set description
      xls.writeDescription(sheets[0], xls.getText("summaries.powb.mog.sheetone.description"));

      // write text box
      xls.writeTitleBox(sheets[0], "POWB Summary ");

      // write text box
      xls.createLogo(workbook, sheets[0]);

      /***************** POWB MOG Report Detail ******************/
      // Sheet cleanSheet =
      // Writting headers

      String[] headersPOWBDetail =
        new String[] {"Project Id", "Project title", "MOG", "Expected annual contribution",
        "Expected plan of the gender and social inclusion", "Total Budget W1/W2 (USD)", " Gender W1/W2 (USD)",
          "Total Budget W3/Bilateral (USD)", "Gender W3/Bilateral (USD)"};

      // defining header types.
      int[] headerTypesPOWBDetail =
        new int[] {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};


      workbook.setSheetName(1, "Level - 2");

      xls.initializeSheet(sheets[1], headerTypesPOWBDetail);

      xls.writeHeaders(sheets[1], headersPOWBDetail);
      this.addContent(informationDetailPOWB, sheets[1], 1, workbook);

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
