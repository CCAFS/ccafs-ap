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
  private void addContent(List<Map<String, Object>> informationDetailPOWB, Sheet sheet) {

    Map<String, Object> mapObject;
    StringBuilder stringBuilder;
    String valueOne, valueTwo;
    // Iterating all the projects
    for (int a = 0; a < informationDetailPOWB.size(); a++) {
      mapObject = informationDetailPOWB.get(a);

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

  /**
   * This method is used to generate the csv file for the ProjectLeading institutions.
   * 
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  public byte[] generateXLS(List<Map<String, Object>> informationDetailPOWB) {

    try {

      Workbook workbook = xls.initializeWorkbook(true);

      /***************** POWB MOG Report Detail ******************/
      // Writting headers
      String[] _headersPOWBDetail =
        new String[] {"Project Id", "Project title", "MOG", "Expected annual contribution",
        "Expected plan of the gender and social inclusion", " Budget Total W1/W2 (USD)",
        "Budget Total W3/Bilateral (USD)", " Budget Total W1/W2 (USD)", "Budget Total W3/Bilateral (USD)"};

      // defining header types.
      int[] headerTypesPOWBDetail =
        new int[] {BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};


      // renaming sheet
      workbook.setSheetName(0, "P&R - POWB Detail");
      Sheet sheet = workbook.getSheetAt(0);
      xls.initializeSheet(sheet, headerTypesPOWBDetail);

      xls.writeTitleBox(sheet, "POWBMOG Summary Detail");

      xls.writeHeaders(sheet, _headersPOWBDetail);
      this.addContent(informationDetailPOWB, sheet);

      xls.setDescription(sheet, "Invenire praesent moderatius ut sit, autem nonumy ei nec. Diceret tibique eu sea."
        + " In altera contentiones est, pro noster fuisset dissentias eu. Pro nonumes detracto ne. "
        + "t dicam iisque ocurreret ius, eum an liber tritani. Has vocibus ceteros definiebas ex.");


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
