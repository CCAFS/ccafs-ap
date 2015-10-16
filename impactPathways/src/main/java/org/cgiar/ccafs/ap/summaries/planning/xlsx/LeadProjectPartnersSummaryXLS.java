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
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;


/**
 * @author Carlos Alberto Martínez M.
 */
public class LeadProjectPartnersSummaryXLS {

  private BaseXLS xls;
  private APConfig config;

  @Inject
  public LeadProjectPartnersSummaryXLS(APConfig config, BaseXLS xls) {
    this.xls = xls;
    this.config = config;
  }

  /**
   * This method is used to add an institution being a project leader
   * 
   * @param projectLeadingInstitutions is the list of institutions to be added
   * @param projectList is the list with the projects related to each institution
   */
  private void addContent(Sheet sheet, List<Map<String, Object>> projectList) {
    XSSFHyperlink link;
    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    Map<String, Object> projectPartnerLeader;
    String projectId;
    for (int i = 0; i < projectList.size(); i++) {
      projectPartnerLeader = projectList.get(i);
      projectId = projectPartnerLeader.get("project_id").toString();
      link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
      link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectId);
      xls.writeHyperlink(sheet, "P" + projectId, link);
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("project_title"));
      xls.nextColumn();
      xls.writeString(sheet, projectPartnerLeader.get("project_type").toString().replace("_", " "));
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("project_summary"));
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("flagships"));
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("regions"));
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("lead_institution"));
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("project_leader"));
      xls.nextColumn();
      xls.writeString(sheet, (String) projectPartnerLeader.get("project_coordinator"));
      xls.nextRow();
    }
  }

  /**
   * This method is used to generate the xls file for the ProjectLeading institutions.
   * 
   * @param projectList is the list with the projects partner leaders
   * @return a byte array with the information provided for the xls file.
   */
  public byte[] generateXLS(List<Map<String, Object>> projectList) {

    try {

      // Defining headers
      String[] headers =
        new String[] {"Project Id", "Title", "Type", "Summary", "Flagship(s)", "Region(s)", "Lead institution",
          "Leader", "Coordinator"};

      // Defining header types
      int[] headerTypes =
      {BaseXLS.COLUMN_TYPE_NUMERIC, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG};

      Workbook workbook = xls.initializeWorkbook(true);

      workbook.setSheetName(0, "Project leaders");
      Sheet sheet = workbook.getSheetAt(0);
      xls.initializeSheet(sheet, headerTypes);
      xls.writeTitleBox(sheet, "      CCAFS Project leaders");
      xls.writeHeaders(sheet, headers);

      this.addContent(sheet, projectList);
      sheet.autoSizeColumn(3);
      // Adding CCAFS logo
      xls.createLogo(workbook, sheet);
      // Set description
      xls.writeDescription(sheet, xls.getText("summaries.leadProjectPartners.summary.description"));
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
