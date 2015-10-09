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
public class DeliverablePlanningSummaryXLS {

  private BaseXLS xls;
  private APConfig config;

  @Inject
  public DeliverablePlanningSummaryXLS(BaseXLS xls, APConfig config) {
    this.xls = xls;
    this.config = config;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */


  private void addContent(List<Map<String, Object>> deliverableList, XSSFWorkbook workbook) {

    XSSFHyperlink link;
    Sheet sheet = workbook.getSheetAt(0);
    CreationHelper createHelper = workbook.getCreationHelper();
    Map<String, Object> deliverableMap;
    int projectID;

    // Main Type
    StringBuilder stringBuilder;


    // Iterating all the projects
    for (int a = 0; a < deliverableList.size(); a++) {
      deliverableMap = deliverableList.get(a);

      projectID = (int) deliverableMap.get("project_id");
      link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
      link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectID);

      // Project id
      xls.writeHyperlink(sheet, "P" + String.valueOf(projectID), link);
      xls.nextColumn();

      // Title
      xls.writeString(sheet, (String) deliverableMap.get("project_title"));
      xls.nextColumn();

      // Flashig
      xls.writeString(sheet, (String) deliverableMap.get("flagships"));
      xls.nextColumn();

      // Region
      xls.writeString(sheet, (String) deliverableMap.get("regions"));
      xls.nextColumn();

      // Title
      xls.writeString(sheet, this.messageReturn((String) deliverableMap.get("deliverable_title")));
      xls.nextColumn();

      // MOG
      xls.writeString(sheet, this.messageReturn((String) deliverableMap.get("mog")));
      xls.nextColumn();

      // Year
      xls.writeInteger(sheet, (int) deliverableMap.get("year"));
      xls.nextColumn();

      // Main type
      xls.writeString(sheet, this.messageReturn((String) deliverableMap.get("deliverable_type")));
      xls.nextColumn();


      // Sub Type
      int deliverableTypeId = (int) deliverableMap.get("deliverable_type_id");
      stringBuilder = new StringBuilder();
      if (deliverableTypeId == 38) {
        stringBuilder.append("Other: (");
        stringBuilder.append(this.messageReturn((String) deliverableMap.get("other_type")));
        stringBuilder.append(")");
      } else {
        stringBuilder.append(this.messageReturn((String) deliverableMap.get("deliverable_sub_type")));
      }
      xls.writeString(sheet, stringBuilder.toString());
      xls.nextColumn();

      // Partner Responsible
      xls.writeString(sheet, (String) deliverableMap.get("partner_responsible"));
      xls.nextColumn();

      // Other Partner
      xls.writeString(sheet, (String) deliverableMap.get("other_responsibles"));
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
  public byte[] generateXLS(List<Map<String, Object>> deliverableList) {

    try {
      // Writting headers
      String[] headers =
        new String[] {"Project id", "Project title", "Flagship(s)", "Region(s)", "Deliverable title", "MOG",
          "Year of expected completion", "Main type", "Sub type", "Partner responsible", "Other responsibles"};

      // defining header types.
      int[] headerTypes =
        new int[] {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
          BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG};

      XSSFWorkbook workbook = xls.initializeWorkbook(true);

      this.xls.initializeSheet(workbook.getSheetAt(0), headerTypes);

      // renaming sheet
      workbook.setSheetName(0, "Deliverable Report");
      Sheet sheet = workbook.getSheetAt(0);

      xls.writeHeaders(sheet, headers);
      this.addContent(deliverableList, workbook);

      // Set description
      xls.writeDescription(sheet, xls.getText("summaries.expected.deliverable.summary.sheetone.description"));

      // write text box
      xls.writeTitleBox(sheet, xls.getText("summaries.expected.deliverable.summary.name"));

      // write text box
      xls.createLogo(workbook, sheet);

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

  /**
   * This method converts the string in return message of summary
   * 
   * @param enter String of entering
   * @returnnull default message when the string is null or empty, otherwise the string
   */
  private String messageReturn(String enter) {

    if (enter == null || enter.equals("")) {
      return xls.getText("summaries.project.empty");
    } else {
      return enter;
    }

  }
}
