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

package org.cgiar.ccafs.ap.summaries.projects.xlsx;

import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;


/**
 * @author Jorge Leonardo Solis B.
 */
public class BudgetPerPartnersSummaryXLS {

  private BaseXLS xls;
  private APConfig config;

  @Inject
  public BudgetPerPartnersSummaryXLS(BaseXLS xls, APConfig config) {
    this.xls = xls;
    this.config = config;

  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Map<String, Object>> informationBudgetByPartnerList, Sheet sheet) {

    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    XSSFHyperlink link;
    Map<String, Object> mapObject;
    int projectID;
    // Iterating all the projects


    for (int a = 0; a < informationBudgetByPartnerList.size(); a++) {
      mapObject = informationBudgetByPartnerList.get(a);

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

      // Partner
      xls.writeString(sheet, (String) mapObject.get("partner"));
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
  public byte[] generateXLS(List<Map<String, Object>> informationBudgetByPartnersReport, int year) {

    try {

      // Writting headers
      String[] headers =
        new String[] {"Project Id", "Project title", "Partner", "Total Budget W1/W2 (USD)", "Gender W1/W2 (USD)",
          "Total Budget W3/Bilateral (USD)", "Gender W3/Bilateral (USD)"};

      // Writting style content
      int[] headersType =
        new int[] {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
          BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET};

      Workbook workbook = xls.initializeWorkbook(true);

      // renaming sheet
      workbook.setSheetName(0, "Budget Summary Per Partners");
      Sheet sheet = workbook.getSheetAt(0);

      xls.initializeSheet(sheet, headersType);

      // Writing the sheet in the yellow box
      xls.writeTitleBox(sheet, xls.getText("summaries.budget.partners.summary.name").concat(" " + year));

      // Writing the sheet in the yellow box
      xls.writeDescription(sheet, xls.getText("summaries.budget.partners.summary.description"));

      // write text box
      xls.createLogo(workbook, sheet);

      xls.writeHeaders(sheet, headers);

      this.addContent(informationBudgetByPartnersReport, sheet);

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
