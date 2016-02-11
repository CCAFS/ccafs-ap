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
 * @author Jorge leonardo Solis B.
 */
public class ImpactPathwayContributionsSummaryXLS {

  private APConfig config;
  private BaseXLS xls;


  @Inject
  public ImpactPathwayContributionsSummaryXLS(APConfig config, BaseXLS xls) {
    this.config = config;
    this.xls = xls;
  }

  /**
   * This method is used to add a project with its corresponding gender contribution
   * 
   * @param sheet is the workbook sheet where the information is going to be presented
   * @param projectMapInformation is the list with the projects related to each institution
   */
  private void addContent(List<Map<String, Object>> projectMapInformation, Sheet sheet) {

    Map<String, Object> projectMap;

    int projectID;
    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    XSSFHyperlink link;

    // ************************* Project Level Gender Contribution ***********************
    for (int i = 0; i < projectMapInformation.size(); i++) {
      projectMap = projectMapInformation.get(i);

      projectID = (int) projectMap.get("project_id");
      link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
      link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectID);

      // Project id
      xls.writeHyperlink(sheet, "P" + String.valueOf(projectID), link);
      xls.nextColumn();

      // Project title
      xls.writeString(sheet, (String) projectMap.get("project_title"));
      xls.nextColumn();

      // Flagship
      xls.writeString(sheet, (String) projectMap.get("flagship"));
      xls.nextColumn();

      // Outcome 2019
      xls.writeString(sheet, (String) projectMap.get("outcome_2019"));
      xls.nextColumn();

      // indicator
      xls.writeString(sheet, (String) projectMap.get("indicator"));
      xls.nextColumn();

      // target
      xls.writeString(sheet, (String) projectMap.get("target"));
      xls.nextColumn();

      // target narrative
      xls.writeString(sheet, (String) projectMap.get("target_narrative"));
      xls.nextColumn();

      // target gender
      xls.writeString(sheet, (String) projectMap.get("target_gender"));

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


    Workbook workbook = xls.initializeWorkbook(true);

    /***************** Indicator Contribution Report Project Level ******************/
    // Defining headers
    String[] headersProject =
      new String[] {"Project Id", "Title", "Flagship", "Outcome statement", "Indicator", "Target", "Target narrative",
    "Target gender "};

    // Defining header types
    int[] headerTypesProject =
    {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG};

    // creating sheet
    Sheet sheet = workbook.getSheetAt(0);
    workbook.setSheetName(0, "Impact Pathway Contributions");


    try {
      xls.initializeSheet(sheet, headerTypesProject);

      xls.writeHeaders(sheet, headersProject);
      this.addContent(projectList, sheet);

      // Set description
      xls.writeDescription(sheet, "");

      // write text box
      xls.writeTitleBox(sheet, "Impact Pathway Contributions Summary");

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

}
