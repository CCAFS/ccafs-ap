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
import org.cgiar.ccafs.utils.APConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
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
      new String[] {"Identifier", "Title", "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible",
    "Others Partners"};
    this.config = config;
  }

  /**
   * Method is used for to add the deliverable
   * 
   * @param deliverables
   */
  private void addContent(List<Deliverable> deliverables) {

    for (Deliverable deliverable : deliverables) {

      try {
        if (deliverable != null) {
          StringBuilder stringBuilder = new StringBuilder();

          // Id
          this.addRegister(deliverable.getId(), fileWriter);
          fileWriter.append(COMMA_DELIMITER);

          // Title
          this.addRegister(deliverable.getTitle(), fileWriter);
          fileWriter.append(COMMA_DELIMITER);

          // MOG
          this.addRegister(deliverable.getOutput().getDescription(), fileWriter);
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
          if (deliverable.getResponsiblePartner() != null && (deliverable.getResponsiblePartner().getPartner() != null)) {
            this.addRegister(deliverable.getResponsiblePartner().getPartner().getComposedName(), fileWriter);
          } else {
            this.addRegister("", fileWriter);
          }
          fileWriter.append(COMMA_DELIMITER);

          // Others Partners
          DeliverablePartner otherPartner;
          stringBuilder = new StringBuilder();
          if (deliverable.getOtherPartners() != null) {
            for (int a = 0; a < deliverable.getOtherPartners().size(); a++) {
              otherPartner = deliverable.getOtherPartners().get(a);
              if (otherPartner != null && otherPartner.getPartner() != null) {
                if (a != 0) {
                  stringBuilder.append("; ");
                }
                stringBuilder.append(otherPartner.getPartner().getComposedName());
              }
            }
          } else {
            stringBuilder.append(this.getText("summaries.project.empty"));
          }
          this.addRegister(stringBuilder, fileWriter);
          fileWriter.append(this.NEW_LINE_SEPARATOR);

        }
      } catch (IOException e) {
        e.printStackTrace();
      }

    }
  }

  /**
   * Method is used to generate the csv for the deliverable.
   * 
   * @param deliverables
   */

  public void generateCSV(List<Deliverable> deliverables) {

    File file = new File(config.getUploadsBaseFolder() + "temporal.txt");
    fileWriter = null;
    this.initializeCsv(file);

    try {
      fileWriter = new FileWriter(file, true);
      fileWriter.write('\ufeff');

      this.addHeaders(headers, fileWriter);
      this.addContent(deliverables);
      fileWriter.close();

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
