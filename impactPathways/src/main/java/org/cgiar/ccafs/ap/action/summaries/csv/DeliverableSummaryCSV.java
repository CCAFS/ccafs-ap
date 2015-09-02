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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.opensymphony.xwork2.TextProvider;


/**
 * @author Jorge Leonardo Solis B.
 */
public class DeliverableSummaryCSV extends BaseCSV {


  private InputStream inputStream;
  String COMMA_DELIMITER = ",";
  String NEW_LINE_SEPARATOR = "\n";
  TextProvider textProvider;
  int contentLength;
  FileWriter fileWriter;

  public DeliverableSummaryCSV() {
  }

  private void addContent(List<Deliverable> deliverables) {

    StringBuilder stringBuilder;
    for (Deliverable deliverable : deliverables) {

      try {
        if (deliverable != null) {
          // Id
          stringBuilder = new StringBuilder();
          stringBuilder.append(deliverable.getId());
          fileWriter.append(stringBuilder.toString());
          fileWriter.append(COMMA_DELIMITER);

          // Title
          stringBuilder = new StringBuilder();
          fileWriter.append(this.messageReturn(deliverable.getTitle()));
          fileWriter.append(COMMA_DELIMITER);

          // MOG
          fileWriter.append(this.messageReturn(deliverable.getOutput().getDescription()));
          fileWriter.append(COMMA_DELIMITER);

          // Year
          fileWriter.append(String.valueOf(deliverable.getYear()));
          fileWriter.append(COMMA_DELIMITER);

          // Main Type
          stringBuilder = new StringBuilder();
          stringBuilder.append(this.messageReturn(deliverable.getType().getCategory().getName()));
          fileWriter.append(stringBuilder.toString());
          fileWriter.append(COMMA_DELIMITER);

          // Sub Type
          stringBuilder = new StringBuilder();
          stringBuilder.append(this.messageReturn(deliverable.getType().getName()));
          fileWriter.append(stringBuilder.toString());
          fileWriter.append(COMMA_DELIMITER);

          // Other type
          stringBuilder = new StringBuilder();
          stringBuilder.append(this.messageReturn(deliverable.getTypeOther()));
          fileWriter.append(stringBuilder.toString());
          fileWriter.append(COMMA_DELIMITER);

          // Partner Responsible
          stringBuilder = new StringBuilder();
          if (deliverable.getResponsiblePartner() != null && (deliverable.getResponsiblePartner().getPartner() != null)) {
            stringBuilder
            .append(this.messageReturn(deliverable.getResponsiblePartner().getPartner().getComposedName()));
          } else {
            stringBuilder.append(this.getText("summaries.project.empty"));
          }
          fileWriter.append(stringBuilder.toString());
          fileWriter.append(COMMA_DELIMITER);

          // Others Partners
          DeliverablePartner otherPartner;
          stringBuilder = new StringBuilder();
          if (deliverable.getOtherPartners() != null) {
            for (int a = 0; a < deliverable.getOtherPartners().size(); a++) {
              otherPartner = deliverable.getOtherPartners().get(a);
              if (otherPartner != null && otherPartner.getPartner() != null) {
                if (a != 0) {
                  stringBuilder.append("--");
                }
                stringBuilder.append(this.messageReturn(otherPartner.getPartner().getComposedName()));
              }
            }
          } else {
            stringBuilder.append(this.getText("summaries.project.empty"));
          }
          fileWriter.append(stringBuilder.toString());
          fileWriter.append(COMMA_DELIMITER);


          fileWriter.append(this.NEW_LINE_SEPARATOR);

        }
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }

    }
  }

  /**
   * This method is used for to add the headers for the file
   */
  private void addHeaders() {

    String[] headers =
      new String[] {"Identifier", "Title", "MOG", "Year", "Main Type", "Sub Type", "Other Type", "Partner Responsible",
        "Others Partners"};

    try {

      for (int a = 0; a < headers.length; a++) {
        fileWriter.append(headers[a]);
        fileWriter.append(COMMA_DELIMITER);
      }
      fileWriter.append(this.NEW_LINE_SEPARATOR);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public void generateCSV(List<Deliverable> deliverables) {

    // File file = new File(baseURL + "temporal.txt");
    File file = new File("temporal.txt");
    fileWriter = null;
    this.initializeCsv(file);

    try {
      fileWriter = new FileWriter(file, true);
      this.addHeaders();
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
   * method for to get the inputStream
   * 
   * @return the inputStream
   */
  public InputStream getInputStream() {
    return inputStream;
  }


  /**
   * This method converts the string in return message of summary
   * 
   * @param enter String of entering
   * @returnnull default message when the string is null or empty, otherwise the string
   */
  private String messageReturn(String enter) {

    if (enter == null || enter.equals("")) {
      return this.getText("summaries.project.empty");
    } else {
      return enter.replace(",", ";");
    }

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
