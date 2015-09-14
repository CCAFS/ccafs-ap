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


package org.cgiar.ccafs.ap.action.summaries.planning.xls;


import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class manages everything related to the creation of a XLS (Microsoft Excel) and the output streams needed to
 * write the
 * information.
 * 
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
public class BaseXLS {

  private static Logger LOG = LoggerFactory.getLogger(BaseXLS.class);

  private static String EXCEL_TEMPLATE_FILE;

  // Internationalization file.
  private TextProvider textProvider;
  // Streams
  private ByteArrayOutputStream outputStream; // byte stream.

  private Workbook workbook; // Excel high level model.

  /**
   * This method creates a template in a specific sheet.
   * 
   * @param sheet where the template will be placed.
   */
  private void addHeader(Sheet sheet) {
    Header header = sheet.getHeader();
    String date = new SimpleDateFormat("yyyy-MM-dd-HHmm").format(new Date());
    header.setLeft("Planning and Reporting Platform");
    header.setRight("Report generated on " + date);
  }

  public void closeStreams() throws IOException {
    outputStream.close();
    workbook.close();
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
  protected String getText(String key) {
    return textProvider.getText(key);
  }

  /**
   * Method used for to initialize the XLS Workbook object.
   * It creates a byte output stream and returns a more complex stream that will help us to work with.
   * 
   * @return a Workbook Object representing the Workbook instance where is going to be written all the information in
   *         XLS
   *         format.
   */
  public Workbook initializeXLS(String excelFormat, String templateFile) {
    textProvider = new DefaultTextProvider();
    outputStream = new ByteArrayOutputStream();

    if (excelFormat.toLowerCase().equals("xls")) {
      workbook = new HSSFWorkbook();
    } else if (excelFormat.toLowerCase().equals("xlsx")) {
      try {
        InputStream templateStream = new FileInputStream(templateFile);
        workbook = new XSSFWorkbook(templateStream);
        templateStream.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    return workbook;
  }


  /**
   * This Method is used for to write the Workbook instance into the output stream
   * 
   * @throws IOException if an I/O error occurs.
   */
  public void writeWorkbook() throws IOException {
    workbook.write(outputStream);
  }


}
