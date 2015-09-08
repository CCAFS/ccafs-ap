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
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.apache.commons.lang3.StringEscapeUtils;

/**
 * @author Jorge Leonardo Solis Banguera
 */
public class BaseCSV {


  public String COMMA_DELIMITER = ",";
  public String NEW_LINE_SEPARATOR = "\n";
  public FileWriter fileWriter;
  public TextProvider textProvider;
  public String[] headers;
  public String fileName;
  public InputStream inputStream;
  public int contentLength;

  /**
   * This method is used for to add the headers for the file
   * 
   * @param headers String with the headers of the csv
   * @param fileWriter file for write the headers
   */
  public void addHeaders(String[] headers, FileWriter fileWriter) {

    try {
      for (int a = 0; a < headers.length; a++) {
        fileWriter.append(headers[a]);
        fileWriter.append(COMMA_DELIMITER);
      }
      fileWriter.append(this.NEW_LINE_SEPARATOR);
    } catch (IOException e) {
      //
      e.printStackTrace();
    }
  }

  /**
   * Method used for to add a register in the file
   * 
   * @param register this is the register for add
   * @param fileWriter file to write
   */
  public void addRegister(Object register, FileWriter fileWriter) {
    try {
      String text = "";
      if (register == null || register.equals("")) {
        fileWriter.append(this.getText("summaries.project.empty"));
      } else {
        text = StringEscapeUtils.escapeCsv(String.valueOf(register)); // I said "Hey, I am 5'10"."
      }
      fileWriter.append(text);
    } catch (IOException e) {
      // TODO Auto-generated catch block
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
   * Method used for to get the name of document
   * 
   * @return name of document
   */
  public String getFileName() {
    return fileName;
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
   * Method used for to get the key internationalized that is in the properties file.
   * 
   * @param key key to search
   * @return internazionale key
   */
  public String getText(String key) {
    return textProvider.getText(key);
  }


  /**
   * Method used for to inicialize the csv file
   * 
   * @param file file to initialize
   */
  public void initializeCsv(File file) {
    try {
      textProvider = new DefaultTextProvider();
      file.delete();
      file.createNewFile();
    } catch (IOException ioe) {
      ioe.printStackTrace();
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
