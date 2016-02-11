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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;


/**
 * @author Carlos Alberto Martínez M.
 */
public class SearchTermsSummaryXLS {

  private APConfig config;
  private BaseXLS xls;


  @Inject
  public SearchTermsSummaryXLS(APConfig config, BaseXLS xls) {
    this.config = config;
    this.xls = xls;
  }

  /**
   * This method is used to add a project with its corresponding gender contribution
   * 
   * @param sheet is the workbook sheet where the information is going to be presented
   * @param informationList is the list with the projects related to each institution
   */
  private void addContent(List<Map<String, Object>> informationList, Sheet sheet, int sheetIndex, String[] terms) {

    Map<String, Object> projectContribution, activityContribution, deliverableContribution;

    for (int counter = 0; counter < terms.length; counter++) {
      terms[counter] = terms[counter].toLowerCase();
    }


    int projectID, deliverableID;
    CreationHelper createHelper = sheet.getWorkbook().getCreationHelper();
    XSSFHyperlink link; // = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
    if (sheetIndex == 0) {
      // ************************* Project Level Gender Contribution ***********************
      for (int i = 0; i < informationList.size(); i++) {
        projectContribution = informationList.get(i);

        projectID = (int) projectContribution.get("project_id");
        link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectID);

        // Project id
        xls.writeHyperlink(sheet, "P" + String.valueOf(projectID), link);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) projectContribution.get("project_title"), terms);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) projectContribution.get("project_summary"), terms);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) projectContribution.get("outcome_statement"), terms);
        xls.nextColumn();

        xls.writeString(sheet, (String) projectContribution.get("start_date"));
        xls.nextColumn();
        xls.writeString(sheet, (String) projectContribution.get("end_date"));
        xls.nextColumn();
        xls.writeString(sheet, (String) projectContribution.get("flagships"));
        xls.nextColumn();
        xls.writeString(sheet, (String) projectContribution.get("regions"));
        xls.nextColumn();
        xls.writeString(sheet, (String) projectContribution.get("lead_institution"));
        xls.nextColumn();
        xls.writeString(sheet, (String) projectContribution.get("project_leader"));
        xls.nextColumn();
        xls.writeString(sheet, (String) projectContribution.get("project_coordinator"));
        xls.nextColumn();
        xls.writeBudget(sheet, (double) projectContribution.get("budget_w1w2"));
        xls.nextColumn();
        xls.writeBudget(sheet, (double) projectContribution.get("budget_w3bilateral"));
        xls.nextColumn();
        xls.writeBudget(sheet, (double) projectContribution.get("gender_w1w2"));
        xls.nextColumn();
        xls.writeBudget(sheet, (double) projectContribution.get("gender_w3bilateral"));
        xls.nextRow();
      }
    } else if (sheetIndex == 1) {

      // ************************* Activity Level Gender Contribution ***********************
      for (int i = 0; i < informationList.size(); i++) {
        activityContribution = informationList.get(i);

        projectID = (int) activityContribution.get("project_id");
        link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectID);

        // Project id
        xls.writeHyperlink(sheet, "P" + String.valueOf(projectID), link);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) activityContribution.get("project_title"), terms);
        xls.nextColumn();

        // Activity id
        link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(config.getBaseUrl() + "/planning/projects/activities.do?projectID=" + projectID);
        xls.writeHyperlink(sheet,
          "P" + String.valueOf(projectID) + "-" + "A" + (int) activityContribution.get("activity_id"), link);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) activityContribution.get("activity_title"), terms);
        xls.nextColumn();
        xls.writeSearchString(sheet, (String) activityContribution.get("activity_description"), terms);
        xls.nextColumn();
        xls.writeString(sheet, (String) activityContribution.get("activity_startDate"));
        xls.nextColumn();
        xls.writeString(sheet, (String) activityContribution.get("activity_endDate"));
        xls.nextColumn();
        xls.writeString(sheet, (String) activityContribution.get("institution"));
        xls.nextColumn();
        xls.writeString(sheet, (String) activityContribution.get("activity_leader"));

        xls.nextRow();
      }
    } else if (sheetIndex == 2) {
      // ************************* Deliverable Level Gender Contribution ***********************
      for (int i = 0; i < informationList.size(); i++) {
        deliverableContribution = informationList.get(i);

        projectID = (int) deliverableContribution.get("project_id");
        link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(config.getBaseUrl() + "/planning/projects/description.do?projectID=" + projectID);

        // Project id
        xls.writeHyperlink(sheet, "P" + String.valueOf(projectID), link);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) deliverableContribution.get("project_title"), terms);
        xls.nextColumn();

        // Deliverable id
        deliverableID = (int) deliverableContribution.get("deliverable_id");
        link = (XSSFHyperlink) createHelper.createHyperlink(Hyperlink.LINK_URL);
        link.setAddress(config.getBaseUrl() + "/planning/projects/deliverable.do?deliverableID=" + deliverableID);
        xls.writeHyperlink(sheet, "P" + String.valueOf(projectID) + "-" + "D" + String.valueOf(deliverableID), link);
        xls.nextColumn();

        xls.writeSearchString(sheet, (String) deliverableContribution.get("deliverable_title"), terms);
        xls.nextColumn();
        xls.writeString(sheet, (String) deliverableContribution.get("deliverable_type"));
        xls.nextColumn();
        xls.writeString(sheet, (String) deliverableContribution.get("deliverable_subtype"));
        xls.nextColumn();
        xls.writeSearchString(sheet, (String) deliverableContribution.get("next_user"), terms);
        xls.nextColumn();
        xls.writeSearchString(sheet, (String) deliverableContribution.get("expected_changes"), terms);
        xls.nextColumn();
        xls.writeSearchString(sheet, (String) deliverableContribution.get("strategies"), terms);
        xls.nextColumn();
        xls.writeString(sheet, (String) deliverableContribution.get("institution"));
        xls.nextColumn();
        xls.writeString(sheet, (String) deliverableContribution.get("deliverable_responsible"));

        xls.nextRow();
      }
    }

  }

  /**
   * This method is used to generate the xls file for the ProjectLeading institutions.
   * 
   * @param projectList is the list with the projects partner leaders
   * @return a byte array with the information provided for the xls file.
   */
  public byte[] generateXLS(List<Map<String, Object>> projectList, List<Map<String, Object>> activityList,
    List<Map<String, Object>> deliverableList, String[] termsToSearch) {


    Workbook workbook = xls.initializeWorkbook(true);

    /***************** Gender Contribution Report Project Level ******************/
    // Defining headers
    String[] headersProject =
      new String[] {"Project Id", "Title", "Summary", "Outcome statement", "Start date", "End date", "Flagship(s)",
        "Region(s)", "Lead institution", "Leader", "Coordinator", "Total budget W1/W2", "Total budget W3/Bilateral",
        "Total gender W1/W2", "Total gender W3/Bilateral"};

    // Defining header types
    int[] headerTypesProject =
      {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_DATE, BaseXLS.COLUMN_TYPE_DATE,
        BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_LONG,
        BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_BUDGET,
        BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET, BaseXLS.COLUMN_TYPE_BUDGET};

    // creating sheet
    Sheet[] sheets = new Sheet[3];
    sheets[0] = workbook.getSheetAt(0);
    sheets[1] = workbook.cloneSheet(0);
    sheets[2] = workbook.cloneSheet(0);

    workbook.setSheetName(0, "Projects");
    workbook.setSheetName(1, "Activities ");
    workbook.setSheetName(2, "Deliverables ");

    try {
      xls.initializeSheet(sheets[0], headerTypesProject);

      xls.writeHeaders(sheets[0], headersProject);
      this.addContent(projectList, sheets[0], 0, termsToSearch);

      // Set description
      xls.writeDescription(
        sheets[0],
        xls.getText("summaries.gender.summary.sheetone.description",
          new String[] {StringUtils.join(termsToSearch, ", ")}));

      // write text box
      xls.writeTitleBox(sheets[0], "Search Terms Summary Project Level Summary");

      // write text box
      xls.createLogo(workbook, sheets[0]);


      /***************** Gender Contribution Report Activity Level ******************/

      // Defining headers
      String[] headersActivity =
        new String[] {"Project Id", "Project Title", "Activity Id", "Activity Title", "Description", "Start date",
          "End date", "Leader Institution", "Leader Person"};

      // Defining header types
      int[] headerTypesActivity =
        {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_HYPERLINK,
          BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_DATE,
          BaseXLS.COLUMN_TYPE_DATE, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG};


      xls.initializeSheet(sheets[1], headerTypesActivity);

      xls.writeHeaders(sheets[1], headersActivity);
      this.addContent(activityList, sheets[1], 1, termsToSearch);

      // Set description
      xls.writeDescription(
        sheets[1],
        xls.getText("summaries.gender.summary.sheettwo.description",
          new String[] {StringUtils.join(termsToSearch, ", ")}));

      // write text box
      xls.writeTitleBox(sheets[1], "Search Terms Summary Project Level Summary");

      // write text box
      xls.createLogo(workbook, sheets[1]);


      /***************** Gender Contribution Report Deliverable Level ******************/

      // Defining headers
      String[] headersDeliverable =
        new String[] {"Project Id", "Project Title", "Deliverable Id", "Deliverable Title", "Deliverable Type",
          "Deliverable Sub-Type", "Next User", "Knowledge, attitude, skills and practice changes ", " Strategies",
          "Leader Institution", "Responsible Person"};

      // Defining header types
      int[] headerTypesDeliverable =
        {BaseXLS.COLUMN_TYPE_HYPERLINK, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_HYPERLINK,
          BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_SHORT, BaseXLS.COLUMN_TYPE_TEXT_SHORT,
          BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG,
          BaseXLS.COLUMN_TYPE_TEXT_LONG, BaseXLS.COLUMN_TYPE_TEXT_LONG};


      xls.initializeSheet(sheets[2], headerTypesDeliverable);

      xls.writeHeaders(sheets[2], headersDeliverable);
      this.addContent(deliverableList, sheets[2], 2, termsToSearch);

      // Set description
      xls.writeDescription(
        sheets[2],
        xls.getText("summaries.gender.summary.sheetthree.description",
          new String[] {StringUtils.join(termsToSearch, ", ")}));

      // write text box
      xls.writeTitleBox(sheets[2], "Search Terms Summary Project Level Summary");

      // write text box
      xls.createLogo(workbook, sheets[2]);

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
