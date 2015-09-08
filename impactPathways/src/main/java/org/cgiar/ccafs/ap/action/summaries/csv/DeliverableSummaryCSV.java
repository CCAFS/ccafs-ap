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

package org.cgiar.ccafs.ap.action.summaries.csv;

import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;


/**
 * @author Jorge Leonardo Solis B.
 */
public class DeliverableSummaryCSV extends BaseCSV {


  private InputStream inputStream;
  private String COMMA_DELIMITER;
  private String NEW_LINE_SEPARATOR;

  private int contentLength;
  private APConfig config;


  /**
   * Method constructor.
   */
  @Inject
  public DeliverableSummaryCSV(APConfig config) {

    COMMA_DELIMITER = ",";
    NEW_LINE_SEPARATOR = "\n";
    headers =
      new String[] {"Project Id", "Project title", " Flagship(s) ", "Region(s)", "Deliverable ID", "Deliverable title",
      "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible", "Others Partners"};
    this.config = config;
  }

  /**
   * Method is used for to add the deliverable
   * 
   * @param deliverables it is a list that contain the deliverables
   */
  private void addContent(List<Project> projectList) {

    List<Deliverable> deliverables;
    StringBuilder stringBuilder;
    int counter = 0;
    Project project;
    // for (Project project : projectList) {
    for (int a = 0; a < 10; a++) {
      project = projectList.get(a);
      deliverables = project.getDeliverables();

      for (Deliverable deliverable : deliverables) {

        try {
          // if (deliverable != null && deliverable.getYear() > 2014)
          if (deliverable != null) {
            stringBuilder = new StringBuilder();

            // Project Id
            this.addRegister(project.getId(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Title
            this.addRegister(project.getTitle(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Flashig
            counter = 0;
            stringBuilder = new StringBuilder();
            for (IPProgram flashig : project.getFlagships()) {
              if (counter != 0) {
                stringBuilder.append(", ");
              }
              stringBuilder.append(flashig.getAcronym());
              counter++;
            }

            this.addRegister(stringBuilder.toString(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);


            // Region
            counter = 0;
            stringBuilder = new StringBuilder();
            for (IPProgram region : project.getRegions()) {
              if (counter != 0) {
                stringBuilder.append("-");
              }
              stringBuilder.append(region.getAcronym());
              counter++;
            }

            this.addRegister(stringBuilder.toString(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // deliverable Id
            this.addRegister(deliverable.getId(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // deliverable Title
            this.addRegister(deliverable.getTitle(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // MOG
            if (deliverable.getOutput() != null) {
              this.addRegister(deliverable.getOutput().getDescription(), fileWriter);
            } else {
              this.addRegister(this.getText("summaries.project.empty"), fileWriter);
            }
            fileWriter.append(COMMA_DELIMITER);

            // Year
            this.addRegister(deliverable.getYear(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Main Type
            this.addRegister(deliverable.getType().getCategory().getName(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Sub Type
            this.addRegister(deliverable.getType().getName(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Other type
            stringBuilder = new StringBuilder();
            if (deliverable.getTypeOther() == null || deliverable.getTypeOther().equals("")) {
              stringBuilder.append(this.getText("summaries.project.notapplicable"));
            } else {
              stringBuilder.append(deliverable.getTypeOther());
            }
            this.addRegister(stringBuilder.toString(), fileWriter);
            fileWriter.append(COMMA_DELIMITER);

            // Partner Responsible
            if (deliverable.getResponsiblePartner() != null
              && (deliverable.getResponsiblePartner().getPartner() != null)) {
              this.addRegister(deliverable.getResponsiblePartner().getPartner().getComposedName(), fileWriter);
            } else {
              this.addRegister("", fileWriter);
            }
            fileWriter.append(COMMA_DELIMITER);

            // Others Partners
            DeliverablePartner otherPartner;
            stringBuilder = new StringBuilder();
            if (deliverable.getOtherPartners() != null && !deliverable.getOtherPartners().isEmpty()) {
              for (int b = 0; b < deliverable.getOtherPartners().size(); b++) {
                otherPartner = deliverable.getOtherPartners().get(b);
                if (otherPartner != null && otherPartner.getPartner() != null) {
                  if (b != 0) {
                    stringBuilder.append("; ");
                  }
                  stringBuilder.append(otherPartner.getPartner().getComposedName());
                }
              }
            } else {
              stringBuilder.append(this.getText("summaries.project.empty"));
            }
            this.addRegister(stringBuilder, fileWriter);
            fileWriter.append(COMMA_DELIMITER);


            fileWriter.append(this.NEW_LINE_SEPARATOR);

          }

        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  /**
   * Method is used to generate the csv for the deliverable.
   * 
   * @param deliverables it is a list that contain the deliverables
   */

  public void generateCSV(List<Project> projectList) {

    File file = new File(config.getUploadsBaseFolder() + "temporal.txt");
    fileWriter = null;
    this.initializeCsv(file);

    try {
      fileWriter = new FileWriter(file, true);
      fileWriter.write('\ufeff');

      this.addHeaders(headers, fileWriter);
      this.addContent(projectList);
      fileWriter.close();

      // *********************Created the fileName****************************
      // Expected-deliverables-fecha(yyyyMMdd)

      String date = new SimpleDateFormat("yyyyMMdd-HHmm").format(new Date());
      StringBuffer fileName = new StringBuffer();

      fileName.append("Expected");
      fileName.append("-");
      fileName.append("deliverables");
      fileName.append("-");
      fileName.append(date);
      fileName.append(".csv");

      this.fileName = fileName.toString();
      // *****************************************************************

      inputStream = new FileInputStream(file);
      contentLength = (int) file.length();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * @return
   */
  public int getContentLength() {
    return contentLength;
  }


  /**
   * Method for to get the inputStream
   * 
   * @return the inputStream
   */
  public InputStream getInputStream() {
    return inputStream;
  }


  /**
   * method for to set the inputStream
   * 
   * @param inputStream the inputStream to set
   */
  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

}
