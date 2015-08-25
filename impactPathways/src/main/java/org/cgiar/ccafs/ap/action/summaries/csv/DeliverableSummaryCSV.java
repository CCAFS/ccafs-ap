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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author Jorge Leonardo Solis B.
 */
public class DeliverableSummaryCSV extends BaseCSV {

  private InputStream inputStream;
  int contentLength;
  FileWriter fileWriter;

  public void generateCSV() {

    // File file = new File(baseURL + "temporal.txt");
    File file = new File("temporal.txt");
    fileWriter = null;
    this.initializeCsv(file);

    try {
      fileWriter = new FileWriter(file, true);

      // Write here.
      String COMMA_DELIMITER = ",";
      String NEW_LINE_SEPARATOR = "\n";
      fileWriter.append("55");
      fileWriter.append(COMMA_DELIMITER);
      fileWriter.append("555");
      fileWriter.append(COMMA_DELIMITER);
      fileWriter.append("lu");
      fileWriter.append(NEW_LINE_SEPARATOR);
      fileWriter.append("fa");
      fileWriter.append(COMMA_DELIMITER);
      fileWriter.append("fo");
      fileWriter.append(COMMA_DELIMITER);
      fileWriter.append("fu");
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
   * @return
   */
  public String getFileName() {
    String fileName;

    fileName = "publication2";
    fileName += ".csv";

    return fileName;
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
