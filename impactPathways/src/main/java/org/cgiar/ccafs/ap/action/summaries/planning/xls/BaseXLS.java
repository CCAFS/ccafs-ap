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
import org.apache.struts2.ServletActionContext;
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

  // Excel template location.
  private static String EXCEL_TEMPLATE_FILE =
    ServletActionContext.getServletContext().getRealPath("resources/templates/template.xlsx");

  private TextProvider textProvider; // Internationalization file.
  private ByteArrayOutputStream outputStream; // byte stream.
  private Workbook workbook; // Excel high level model.
  private boolean usingTemplate;


  /**
   * This method set-ups a header to the sheet page.
   * 
   * @param sheet where the header will be placed.
   */
  private void addHeader(Sheet sheet) {
    Header header = sheet.getHeader();
    String date = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date());
    header.setLeft("CCAFS Planning and Reporting Platform");
    header.setRight("Report generated on " + date);
  }

  /**
   * This method closes all the streams opened in the process.
   * 
   * @throws IOException If some I/O error occurs.
   */
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
   * Method used for to initialize an Excel Workbook object.
   * It creates a Workbook object using a predefined template.
   * 
   * @param excelFormat is the format that you want to create (i.e. 'xls' or 'xlsx').
   * @param useTemplate is true if you want to use a templa, false if you want to create the Workbook empty.
   * @return a Workbook Object representing the Workbook instance where is going to be written all the information in
   *         XLS format.
   */
  public Workbook initializeXLS(String excelFormat, boolean useTemplate) {
    textProvider = new DefaultTextProvider();
    outputStream = new ByteArrayOutputStream();
    usingTemplate = useTemplate;
    try {
      // validating the type of format.
      if (excelFormat.toLowerCase().equals("xls")) {
        if (useTemplate) {
          // opening excel template.
          InputStream templateStream = new FileInputStream(EXCEL_TEMPLATE_FILE);
          // creating workbook based on the template.
          workbook = new HSSFWorkbook(templateStream);
          // closing input stream.
          templateStream.close();
          // applying header.
          this.addHeader(workbook.getSheetAt(0));
        } else {
          workbook = new HSSFWorkbook();
        }
      } else if (excelFormat.toLowerCase().equals("xlsx")) {
        if (useTemplate) {
          // opening excel template.
          InputStream templateStream = new FileInputStream(EXCEL_TEMPLATE_FILE);
          // creating workbook based on the template.
          workbook = new XSSFWorkbook(templateStream);
          // closing input stream.
          templateStream.close();
          // applying header.
          this.addHeader(workbook.getSheetAt(0));
        } else {
          workbook = new XSSFWorkbook();
        }
      }
      return workbook;
    } catch (IOException e) {
      LOG.error("There was a problem trying to create the Excel Workbook: ", e.getMessage());
    }
    return null;
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
