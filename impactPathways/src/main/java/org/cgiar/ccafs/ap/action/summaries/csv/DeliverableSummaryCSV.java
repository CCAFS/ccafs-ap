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
 * @author Your name
 */
public class DeliverableSummaryCSV {

  private InputStream inputStream;
  int contentLength;

  public void generateCSV() {

    File file = new File("temporal.txt");
    FileWriter fileWriter = null;

    try {
      fileWriter = new FileWriter(file, true);

      // Write here.

      fileWriter.flush();
      fileWriter.close();
      inputStream = new FileInputStream(file);
      contentLength = (int) file.length();
      file.delete();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public int getContentLength() {
    return contentLength;
  }

  public String getFileName() {
    String fileName;

    fileName = "publication";
    fileName += ".csv";

    return fileName;
  }

  /**
   * @return the inputStream
   */
  public InputStream getInputStream() {
    return inputStream;
  }

  /**
   * @param inputStream the inputStream to set
   */
  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }
}
