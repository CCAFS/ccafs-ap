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
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFTextBox;


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
  private void addContent(List<Institution> projectLeadingInstitutions, String[] projectList, Workbook workBook) {
    int i = 12;
    int count = 0;
    XSSFDrawing draw = (XSSFDrawing) workBook.getSheetAt(0).createDrawingPatriarch();
    XSSFTextBox textbox = draw.createTextbox(new XSSFClientAnchor(0, 0, 1, 1, 1, 1, 2, 2));
    textbox.setText("foooooooooooooooooooooo");


    for (Institution institution : projectLeadingInstitutions) {
      Sheet sheet = workBook.getSheetAt(0);
      Row row = sheet.createRow((short) i);
      CellStyle style = workBook.createCellStyle();
      style.setAlignment(CellStyle.ALIGN_CENTER);
      row.createCell(1).setCellValue(String.valueOf(institution.getId()));
      sheet.autoSizeColumn(1);
      row.getCell(1).setCellStyle(style);
      row.createCell(2).setCellValue(institution.getName());
      sheet.autoSizeColumn(2);
      row.createCell(3).setCellValue(institution.getAcronym());
      sheet.autoSizeColumn(3);
      row.getCell(3).setCellStyle(style);
      row.createCell(4).setCellValue(institution.getWebsiteLink());
      sheet.autoSizeColumn(4);
      row.createCell(5).setCellValue(institution.getCountry().getName());
      sheet.autoSizeColumn(5);
      row.createCell(6).setCellValue(projectList[count]);
      sheet.autoSizeColumn(6);
      i++;
      count++;
    }
  }

  /**
   * This method is used to generate the csv file for the ProjectLeading institutions.
   * 
   * @param projectPartnerInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  public byte[] generateXLS(String templateFile, List<Institution> projectLeadingInstitutions, String[] projectList) {

    try {
      Workbook workbook = xls.initializeXLS("xlsx", templateFile);

      String[] headers =
        new String[] {"Institution ID", "Institution name", "Institution acronym", "Web site", "Location", "Projects"};

      workbook.setSheetName(0, "LeadInstitutions");
      Sheet sheet = workbook.getSheetAt(0);

      Row row = sheet.createRow((short) 11);
      Font font = workbook.createFont();
      font.setBold(true);
      CellStyle style = workbook.createCellStyle();
      style.setAlignment(CellStyle.ALIGN_CENTER);
      style.setFont(font);
      for (int c = 1; c <= headers.length; c++) {
        row.createCell(c).setCellValue(headers[c - 1]);
        row.getCell(c).setCellStyle(style);
        sheet.autoSizeColumn(c);
      }

      // this.addHeaders(headers);
      this.addContent(projectLeadingInstitutions, projectList, workbook);
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
