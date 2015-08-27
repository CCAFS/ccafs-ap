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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * @author Jorge Leonardo Solis B.
 */
public class DeliverableSummaryCSV extends BaseCSV {


  private InputStream inputStream;
  String COMMA_DELIMITER = ",";
  String NEW_LINE_SEPARATOR = "\n";

  int contentLength;
  FileWriter fileWriter;

  public DeliverableSummaryCSV() {
  }

  private void addContent(List<Deliverable> deliverables) {
    for (Deliverable deliverable : deliverables) {

      try {

        // fileWriter.append(deliverable.getId());
        fileWriter.append(String.valueOf(deliverable.getId()));
        fileWriter.append(COMMA_DELIMITER);

        fileWriter.append(deliverable.getTitle());
        fileWriter.append(COMMA_DELIMITER);

        fileWriter.append(String.valueOf(deliverable.getType().getName()));
        fileWriter.append(COMMA_DELIMITER);

        fileWriter.append(String.valueOf(deliverable.getOutput().getDescription()));
        fileWriter.append(COMMA_DELIMITER);

        fileWriter.append(String.valueOf(deliverable.getTitle()));
        fileWriter.append(COMMA_DELIMITER);

        fileWriter.append(String.valueOf(deliverable.getId()));
        fileWriter.append(COMMA_DELIMITER);


        fileWriter.append(this.NEW_LINE_SEPARATOR);
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
      new String[] {"Author", "Title", "Publication type", "Publication status", "Description", "Identifier"};

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
   * method for to set the inputStream
   * 
   * @param inputStream the inputStream to set
   */
  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }
}
