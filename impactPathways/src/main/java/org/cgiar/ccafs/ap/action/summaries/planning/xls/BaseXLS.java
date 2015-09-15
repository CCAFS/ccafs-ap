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


import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFTextBox;
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

  // Header Style
  private static final String HEADER_FONT_NAME = "Arial";
  private static final short HEADER_FONT_SIZE = 11;
  private static final String HEADER_FONT_COLOR_HEX = "#404040";
  private static final String HEADER_BG_COLOR_HEX = "#f5e8d8";
  private static final int HEADER_ROW_HEIGHT = 31;

  // Box Style
  // TODO

  private TextProvider textProvider; // Internationalization file.
  private ByteArrayOutputStream outputStream; // byte stream.
  private Workbook workbook; // Excel high level model.
  private boolean usingTemplate;
  private int rowStart, columnStart, rowCounter, columnCounter;


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
  public Workbook initializeXLS(boolean useTemplate) {
    textProvider = new DefaultTextProvider();
    outputStream = new ByteArrayOutputStream();
    usingTemplate = useTemplate;
    try {
      // validating the type of format.
      if (useTemplate) {
        rowStart = 12;
        columnStart = 1;
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
      return workbook;
    } catch (IOException e) {
      LOG.error("There was a problem trying to create the Excel Workbook: ", e.getMessage());
    }
    return null;
  }

  public void newLine() {
    rowCounter++;
    columnCounter = columnStart;
  }

  /**
   * This method writes the headers into the given sheet.
   * 
   * @param sheet is the sheet where you want to write the header.
   * @param headers is the array of headers to write.
   */
  public void writeHeaders(Sheet sheet, String[] headers) {
    if (usingTemplate) {
      // ------ Preparing the style.
      // Font
      XSSFFont font = (XSSFFont) workbook.createFont();
      font.setBold(true);
      font.setFontName(HEADER_FONT_NAME);
      font.setColor(new XSSFColor(Color.decode(HEADER_FONT_COLOR_HEX)));
      font.setFontHeightInPoints(HEADER_FONT_SIZE);
      // Style
      XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
      style.setAlignment(CellStyle.ALIGN_CENTER);
      style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
      style.setFillBackgroundColor(new XSSFColor(Color.decode(HEADER_BG_COLOR_HEX)));
      style.setFont(font);
      // Row
      Row row = sheet.createRow(rowStart - 1);
      row.setHeightInPoints(HEADER_ROW_HEIGHT);

      // Writing headers.
      Cell cell;
      for (int c = 0, columnCounter = columnStart; c < headers.length; c++, columnCounter++) {
        cell = row.createCell(columnCounter);
        cell.setCellStyle(style);
        cell.setCellValue(headers[c]);
        sheet.autoSizeColumn(columnCounter);
      }
    } else {
      // TODO To develop the same algorithm but without style starting in the first row of the sheet.
    }
  }

  /**
   * This method writes the title box into the given sheet.
   * 
   * @param sheet is the sheet where you want to write the title box.
   * @param text is the title of the report.
   */
  public void writeTitleBox(Sheet sheet, String text) {
    XSSFDrawing draw = (XSSFDrawing) sheet.createDrawingPatriarch();
    XSSFTextBox textbox = draw.createTextbox(new XSSFClientAnchor(0, 0, 1, 1, 1, 1, 4, 6));
    textbox.setText(text);
    textbox.setFillColor(255, 204, 41);
    textbox.setVerticalAlignment(VerticalAlignment.CENTER);
  }

  /**
   * This method writes any value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeValue(Sheet sheet, Object value) {
    // TODO CM

    columnCounter++;
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
