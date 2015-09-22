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
  private void addContent(List<Map<String, Object>> deliverableList, Workbook workbook) {


    Sheet sheet = workbook.getSheetAt(0);

    Map<String, Object> deliverableMap;
    // Iterating all the projects
    for (int a = 0; a < 100; a++) {
      deliverableMap = deliverableList.get(a);

      // Iterating all the partners

      // Project id
      xls.writeInteger(sheet, (int) deliverableMap.get("project_id"));
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

      // deliverable id
      xls.writeInteger(sheet, (int) deliverableMap.get("deliverable_id"));
      xls.nextColumn();

      // Title
      xls.writeString(sheet, (String) deliverableMap.get("deliverable_title"));
      xls.nextColumn();

      // MOG
      xls.writeString(sheet, (String) deliverableMap.get("mog"));
      xls.nextColumn();

      // Year
      xls.writeInteger(sheet, (int) deliverableMap.get("year"));
      xls.nextColumn();

      // Main type
      xls.writeString(sheet, (String) deliverableMap.get("deliverable_type"));
      xls.nextColumn();

      // Sub Type
      xls.writeString(sheet, (String) deliverableMap.get("deliverable_sub_type"));
      xls.nextColumn();

      // Other Type
      xls.writeString(sheet, (String) deliverableMap.get("other_type"));
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
        new String[] {"Project Id", "Project title", "Flagship(s)", "Region(s)", "Deliverable Id", "Deliverable title",
          "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible", "Others Partners"};

      // defining header types.
      int[] headerTypes =
        new int[] {BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
        BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG};

      Workbook workbook = xls.initializeXLS(true, headerTypes);

      // renaming sheet
      workbook.setSheetName(0, "Deliverable Report");
      Sheet sheet = workbook.getSheetAt(0);


      xls.writeHeaders(sheet, headers);
      this.addContent(deliverableList, workbook);


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
