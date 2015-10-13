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
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Carlos Alberto Martínez M.
 */
public class PartnersSummaryXLS {


  private BaseXLS xls;

  @Inject
  public PartnersSummaryXLS(APConfig config, BaseXLS xls) {
    this.xls = xls;
  }

  /**
   * This method is used to add an institution being a project partner
   *
   * @param projectPartnerInstitutions is the list of institutions to be added
   */
  private void addContent(Sheet sheet, List<Map<String, Object>> projectPartnerInstitutions) {
    for (Map<String, Object> institution : projectPartnerInstitutions) {
      xls.writeString(sheet, (String) institution.get("name"));
      xls.nextColumn();
      xls.writeString(sheet, (String) institution.get("acronym"));
      xls.nextColumn();
      if (institution.get("institution_type_id") != null) {
        xls.writeString(sheet, (String) institution.get("institution_type_name"));
        xls.nextColumn();
      } else {
        xls.nextColumn();
      }
      xls.writeString(sheet, (String) institution.get("website_link"));
      xls.nextColumn();
      xls.writeString(sheet, (String) institution.get("country_name"));
      xls.nextColumn();
      xls.writeString(sheet, (String) institution.get("projects"));
      xls.nextRow();
    }
  }

  /**
   * This method is used to generate the csv file for the ProjectPartner institutions.
   *
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   * @return a byte array with the information provided for the xls file.
   */
  public byte[] generateCSV(List<Map<String, Object>> projectPartnerInstitutions) {

    try {
      String[] headers =
        new String[] {"Institution name", "Institution acronym", "Partner type", "Web site", "Country location",
      "Projects"};
      int[] headersType =
      {BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_LONG};

      Workbook workbook = xls.initializeWorkbook(true);
      workbook.setSheetName(0, "Project Partners");
      Sheet sheet = workbook.getSheetAt(0);
      xls.initializeSheet(sheet, headersType);
      xls.writeTitleBox(sheet, "\t    Project Partners");
      xls.writeHeaders(sheet, headers);
      this.addContent(sheet, projectPartnerInstitutions);
      // Adding CCAFS logo
      xls.createLogo(workbook, sheet);
      // Set description
      xls.writeDescription(sheet, xls.getText("summaries.projectPartners.summary.description"));
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
