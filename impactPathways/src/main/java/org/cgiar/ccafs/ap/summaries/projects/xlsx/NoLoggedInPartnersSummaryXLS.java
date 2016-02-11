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
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;


/**
 * @author Carlos Alberto Martínez M. CCAFS-CIAT
 */
public class NoLoggedInPartnersSummaryXLS {

  private BaseXLS xls;
  private APConfig config;

  @Inject
  public NoLoggedInPartnersSummaryXLS(BaseXLS xls, APConfig config) {
    this.xls = xls;
    this.config = config;

  }

  /**
   * This method is used to add each partner not having logged in P&R
   * 
   * @param noLoggedInPartnersList is the list of partners to be added
   * @param sheet is the workbook sheet in which the information is going to be added
   */
  private void addContent(List<Map<String, Object>> noLoggedInPartnersList, Sheet sheet) {

    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    XSSFHyperlink link;
    Map<String, Object> mapObject;
    int projectID;


    for (int a = 0; a < noLoggedInPartnersList.size(); a++) {
      mapObject = noLoggedInPartnersList.get(a);

      // User Id
      xls.writeInteger(sheet, (int) (mapObject.get("user_id")));
      xls.nextColumn();

      // Name
      xls.writeString(sheet, (String) mapObject.get("name"));
      xls.nextColumn();

      // Email
      xls.writeString(sheet, (String) mapObject.get("email"));
      xls.nextColumn();

      // Contact type
      xls.writeString(sheet, (String) mapObject.get("contact_type"));
      xls.nextColumn();

      // Project id
      xls.writeString(sheet, (String) mapObject.get("project_id"));
      xls.nextRow();
    }
  }

  /**
   * This method is used to generate the xls file for the partners not having logged in P&R.
   * 
   * @param PartnersInformation is the list of partners to be added
   */
  public byte[] generateXLS(List<Map<String, Object>> PartnersInformation) {

    try {

      // Writting headers
      String[] headers = new String[] {"ID", "Name", "Email", "Contact Type", "Related Project Ids",};

      // Writting style content
      int[] headersType =
        new int[] {BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
        BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_LONG};

      Workbook workbook = xls.initializeWorkbook(true);

      // renaming sheet
      workbook.setSheetName(0, "Partners not logged in");
      Sheet sheet = workbook.getSheetAt(0);

      xls.initializeSheet(sheet, headersType);

      // Writing the sheet in the yellow box
      xls.writeTitleBox(sheet, xls.getText("summaries.partners.notlogged.summary.name"));

      // Writing the sheet in the yellow box
      xls.writeDescription(sheet, xls.getText("summaries.partners.notlogged.summary.description"));

      // write text box
      xls.createLogo(workbook, sheet);

      xls.writeHeaders(sheet, headers);

      this.addContent(PartnersInformation, sheet);
      sheet.autoSizeColumn(1);

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
