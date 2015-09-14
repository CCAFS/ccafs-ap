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

import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.utils.APConfig;

import java.io.IOException;
import java.util.List;

import com.google.inject.Inject;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 * @author Carlos Alberto Martínez M.
 */
public class LeadInstitutionPartnersSummaryXLS {

  private APConfig config;
  private BaseXLS xls;

  @Inject
  public LeadInstitutionPartnersSummaryXLS(APConfig config, BaseXLS xls) {
    this.config = config;
    this.xls = xls;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(List<Institution> projectLeadingInstitutions, String[] projectList) {
    int i = 0;
    for (Institution institution : projectLeadingInstitutions) {
      // try {
      //
      // this.writeString(String.valueOf(institution.getId()), false, true);
      //
      // this.writeString(institution.getName(), false, true);
      //
      // this.writeString(institution.getAcronym(), false, true);
      //
      // this.writeString(institution.getWebsiteLink(), false, true);
      //
      // this.writeString(institution.getCountry().getName(), false, true);
      //
      // // Getting the project ids
      // this.writeString(projectList[i], false, false);
      // i++;
      //
      // this.writeNewLine();
      // } catch (IOException e) {
      // // TODO Auto-generated catch block
      // e.printStackTrace();
      // }

    }
  }

  /**
   * This method is used to generate the csv file for the ProjectLeading institutions.
   * 
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  public byte[] generateXLS(List<Institution> projectLeadingInstitutions, String[] projectList) {

    try {
      Workbook workbook = xls.initializeXLS("xls");

      String[] headers =
        new String[] {"Institution ID", "Institution name", "Institution acronym", "Web site", "Location", "Projects"};

      Sheet sheet = workbook.createSheet("LeadInstitutions");
      xls.createTemplate(sheet);

      // for (Row row : sheet1) {
      // for (Cell cell : row) {\
      Row row = sheet.createRow((short) 0);
      Font font = workbook.createFont();
      font.setBold(true);
      CellStyle style = workbook.createCellStyle();
      style.setFont(font);
      for (int c = 0; c < headers.length; c++) {
        row.createCell(c).setCellValue(headers[c]);
        row.getCell(c).setCellStyle(style);
      }
      // }
      // }

      // this.addHeaders(headers);
      // this.addContent(projectLeadingInstitutions, projectList);
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
