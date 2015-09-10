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


package org.cgiar.ccafs.ap.action.summaries.planning.csv;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class manage everything related to the creation of a CSV and the output streams needed to write the information.
 * 
 * @author Jorge Leonardo Solis Banguera
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class BaseCSV {

  private static Logger LOG = LoggerFactory.getLogger(BaseCSV.class);

  // CSV configuration
  public static String SEPARATOR = ",";
  public static String NEW_LINE = "\n";

  // Internationalization file.
  private TextProvider textProvider;

  // Streams
  private ByteArrayOutputStream outputStream; // byte stream.
  private BufferedWriter writer; // writer.

  /**
   * This method is used to add the headers in the CSV stream.
   * 
   * @param headers String with the headers for the CSV
   * @param writer is the stream to the "file" where the headers will be placed.
   * @throws IOException If an I/O error occurs
   */
  protected void addHeaders(String[] headers) throws IOException {
    for (int a = 0; a < headers.length; a++) {
      writer.write(headers[a]);
      writer.write(SEPARATOR);
    }
    writer.write(NEW_LINE);
  }

  protected void closeStreams() throws IOException {
    outputStream.close();
    writer.close();
  }


  /**
   * This method flush the remaining information that is in the output buffer into the output stream.
   * 
   * @throws IOException If an I/O error occurs
   */
  protected void flush() throws IOException {
    writer.flush();
  }

  /**
   * This method return the information that is in the outputStream as an array of bytes.
   * 
   * @return an array of bytes with the information located in the output stream.
   */
  public byte[] getBytes() {
    return outputStream.toByteArray();
  }

  /**
   * Method used to get the internationalized key that is in the properties file.
   * 
   * @param key to search
   * @return international key
   */
  public String getText(String key) {
    return textProvider.getText(key);
  }

  /**
   * Method used for to initialize the CSV Stream.
   * It creates a byte output stream and returns a more complex stream that will help us to work with.
   * 
   * @return a BufferedWriter Stream representing the stream where is going to be written all the information in CSV
   *         format.
   */
  protected void initializeCSV() {
    textProvider = new DefaultTextProvider();
    outputStream = new ByteArrayOutputStream();
    writer = new BufferedWriter(new OutputStreamWriter(outputStream));
  }

  /**
   * This method writes a new line into the output stream.
   * 
   * @throws IOException if an I/O error occurs.
   */
  public void writeNewLine() throws IOException {
    writer.write(NEW_LINE);
  }

  /**
   * This method writes a separator into the output stream.
   * 
   * @throws IOException if an I/O error occurs.
   */
  public void writeSeparator() throws IOException {
    writer.write(SEPARATOR);
  }

  /**
   * This method is used for writing a text in the file, if the String is empty, the method will write a predefined
   * string. (e.g. <Not defined>)
   * 
   * @param text is the string to be added.
   * @param useDefault if true, a default value will be written in case the string is empty or null. Otherwise
   * @throws IOException If an I/O error occurs
   */
  public void writeString(String text, boolean useDefault, boolean endWithSeparator) throws IOException {
    if (useDefault && (text == null || text.equals(""))) {
      writer.write(this.getText("summaries.project.empty"));
    } else {
      writer.write(StringEscapeUtils.escapeCsv(String.valueOf(text)));
    }
    if (endWithSeparator) {
      writer.write(SEPARATOR);
    }
  }


}
