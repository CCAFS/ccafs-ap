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

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.planning.csv.BaseCSV;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.utils.APConfig;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * @author Jorge Leonardo Solis Banguera
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class DeliverablePlanningSummaryAction extends BaseAction implements Summary {

  private static final long serialVersionUID = 365962206662709857L;

  // Managers
  private ProjectManager projectManager;
  private BaseCSV csv;

  // CSV bytes
  private byte[] bytesCSV;

  // Streams
  InputStream inputStream;

  // CSV list
  List<Map<String, Object>> csvDeliverableList;

  @Inject
  public DeliverablePlanningSummaryAction(APConfig config, ProjectManager projectManager, BaseCSV csv) {
    super(config);
    this.projectManager = projectManager;
    this.csv = csv;

  }

  @Override
  public String execute() throws Exception {
    // Generate the csv file

    try {
      csv.initializeCSV();
      String[] headers = new String[] {"Project Id", "Project title", " Flagship(s) ", "Region(s)", "Deliverable ID",
        "Deliverable title", "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible",
      "Others Partners"};

      csv.addHeaders(headers);
      for (Map<String, Object> csvRow : csvDeliverableList) {
        csv.writeString(csvRow.get("project_id"), false, true);
        csv.writeString(csvRow.get("project_title"), true, true);
        csv.writeString(csvRow.get("flagships"), true, true);
        csv.writeString(csvRow.get("regions"), true, true);
        csv.writeString(csvRow.get("deliverable_id"), false, true);
        csv.writeString(csvRow.get("deliverable_title"), true, true);
        csv.writeString(csvRow.get("mog"), true, true);
        csv.writeString(csvRow.get("deliverable_year"), true, true);
        csv.writeString(csvRow.get("deliverable_type"), true, true);
        csv.writeString(csvRow.get("deliverable_sub_type"), false, true);
        csv.writeString(csvRow.get("other_type"), false, true);
        csv.writeString(csvRow.get("partner_responsible"), true, true);
        csv.writeString(csvRow.get("other_responsibles"), true, false);
        csv.writeNewLine();
      }
      csv.flush();

      // getting the bytes that are in the output stream.
      bytesCSV = csv.getBytes();
      csv.closeStreams();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return SUCCESS;
  }

  @Override
  public int getContentLength() {
    return bytesCSV.length;
  }


  @Override
  public String getFileName() {
    // e.g. Expected-deliverables-20150914-0835.csv
    String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
    StringBuffer fileName = new StringBuffer();
    fileName.append("Expected-deliverables-");
    fileName.append(date);
    fileName.append(".csv");
    return fileName.toString();
  }

  @Override
  public InputStream getInputStream() {
    if (inputStream == null) {
      inputStream = new ByteArrayInputStream(bytesCSV);
    }
    return inputStream;
  }

  @Override
  public void prepare() {

    csvDeliverableList = projectManager.summaryGetAllProjectsWithDeliverables();

  }

}
