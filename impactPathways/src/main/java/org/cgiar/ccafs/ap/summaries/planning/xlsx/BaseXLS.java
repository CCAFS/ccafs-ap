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


package org.cgiar.ccafs.ap.summaries.planning.xlsx;


import org.cgiar.ccafs.utils.APConfig;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.inject.Inject;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFTextBox;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder.BorderSide;
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

  public static final int COLUMN_TYPE_BUDGET = 1;
  public static final int COLUMN_TYPE_DECIMAL = 2;
  public static final int COLUMN_TYPE_TEXT_LONG = 3;
  public static final int COLUMN_TYPE_TEXT_SHORT = 4;
  public static final int COLUMN_TYPE_BOOLEAN = 5;
  public static final int COLUMN_TYPE_NUMERIC = 6;
  public static final int COLUMN_TYPE_DATE = 7;
  public static final int COLUMN_TYPE_HYPERLINK = 8;
  public static final int COLUMN_TYPE_DATE_TIME = 9;

  // Constants for write description
  public static final int REPORT_DESCRIPTION_ROW = 7;
  public static final int REPORT_DESCRIPTION_COLUMN = 1;

  // Constants for logo position
  public static final int LOGO_POSITION_ROW = 1;
  public static final int LOGO_POSITION_COLUMN = 4;

  private static Logger LOG = LoggerFactory.getLogger(BaseXLS.class);

  // Header Style
  private static final String HEADER_FONT_NAME = "Tahoma";

  private static final short HEADER_FONT_SIZE = 10;
  private static final String HEADER_FONT_COLOR_HEX = "#404040";
  private static final String HEADER_BG_COLOR_HEX = "#f5e8d8";
  private static final int HEADER_ROW_HEIGHT = 31;
  private static final String HEADER_BORDER_COLOR_HEX = "#fbbf77";
  // Textbox Style
  private static final Color TEXTBOX_BACKGROUND_COLOR_RGB = new Color(255, 204, 41);

  private static final short TEXTBOX_FONT_COLOR_INDEX = HSSFColor.WHITE.index;
  // Cell Style
  private static final String CELL_DATE_FORMAT = "yyyy-MM-dd";
  private static final String CELL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm";


  private static final String CELL_TRUE_BOOLEAN = "Yes";
  private static final String CELL_FALSE_BOOLEAN = "No";
  private static final String CELL_BORDER_COLOR_HEX = "#c2a5a5";
  private static final short CELL_BORDER_TYPE_BOTTOM = XSSFCellStyle.BORDER_THIN;
  private static final short CELL_BORDER_TYPE_LEFT = XSSFCellStyle.BORDER_THIN;
  private static final short CELL_BORDER_TYPE_RIGHT = XSSFCellStyle.BORDER_THIN;


  private APConfig config;
  private File excelTemplateFile; // Excel template file.
  private TextProvider textProvider; // Internationalization file.
  private ByteArrayOutputStream outputStream; // byte stream.
  private Workbook workbook; // Excel high level model.
  private boolean usingTemplate;
  private int rowStart, columnStart, rowCounter, columnCounter;

  // styleHeader;
  private XSSFCellStyle styleHeader;
  private XSSFCellStyle[] columnStyles;

  // Font to search words
  private Font richTextFont;

  // cell
  private Cell cell;

  @Inject
  public BaseXLS(APConfig config) {
    this.config = config;
    this.excelTemplateFile = new File(config.getResourcePath(), "templates" + File.separator + "template.xlsx");
  }

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
   * @throws IOException
   */
  public void createLogo(Workbook workbook, Sheet sheet) throws IOException {
    // FileInputStream obtains input bytes from the image file
    InputStream inputStream =


    new FileInputStream(new File(config.getResourcePath(), "templates" + File.separator + "logo-ccafs.png"));
    // Get the contents of an InputStream as a byte[].
    byte[] bytes = IOUtils.toByteArray(inputStream);
    // Adds a picture to the workbook
    int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
    // close the input stream
    inputStream.close();

    // Creates the top-level drawing patriarch.
    XSSFDrawing drawing = (XSSFDrawing) sheet.createDrawingPatriarch();

    // Set top-left corner for the image
    XSSFClientAnchor anchor = new XSSFClientAnchor();
    anchor.setAnchorType(2);
    anchor.setCol1(LOGO_POSITION_COLUMN);
    anchor.setRow1(LOGO_POSITION_ROW);

    // Creates a picture
    XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);

    // Reset the image to the original size
    pict.resize();

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
   * Method used to get the internationalized key that is in the properties file.
   * 
   * @param key to search
   * @param args values to be visualized
   * @return international key
   */
  protected String getText(String key, String[] args) {
    return textProvider.getText(key, args);
  }

  /**
   * Method used for to initialize an Excel Workbook object.
   * It creates a Workbook object using a predefined template.
   * 
   * @param excelFormat is the format that you want to create (i.e. 'xls' or 'xlsx').
   * @param useTemplate is true if you want to use a templa, false if you want to create the Workbook empty.
   * @return a Workbook Object representing the Workbook instance where is going to be written all the information in
   *         XLS format.
   * @throws IOException
   */
  public void initializeSheet(Sheet sheet, int[] columnTypes) throws IOException {

    // initializing values
    rowStart = 12;
    columnStart = 1;
    rowCounter = rowStart;
    columnCounter = columnStart;

    // Initializing styles depending on the cell type.
    this.initializeStyles(columnTypes);

    // applying header.
    this.addHeader(sheet);

    StringBuilder rangeString = new StringBuilder();
    char initialColumn = 'B';
    char endColumn = (char) (initialColumn + (columnTypes.length - 1));

    // Set filter in cell
    rangeString = new StringBuilder();
    rangeString.append(initialColumn);
    rangeString.append("12:");
    rangeString.append(endColumn);
    rangeString.append("12");


    sheet.setAutoFilter(CellRangeAddress.valueOf(rangeString.toString()));

  }

  /**
   * Method used to initialize the different styles according to the type of value
   */
  private void initializeStyles(int[] columnTypes) {

    // Style header
    styleHeader = (XSSFCellStyle) workbook.createCellStyle();
    styleHeader.setAlignment(CellStyle.ALIGN_CENTER);
    styleHeader.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
    styleHeader.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
    styleHeader.setFillForegroundColor(new XSSFColor(Color.decode(HEADER_BG_COLOR_HEX)));
    styleHeader.setWrapText(true);


    // Font
    XSSFFont font = (XSSFFont) workbook.createFont();
    font.setBold(true);
    font.setFontName(HEADER_FONT_NAME);
    font.setColor(new XSSFColor(Color.decode(HEADER_FONT_COLOR_HEX)));
    font.setFontHeightInPoints(HEADER_FONT_SIZE);
    styleHeader.setFont(font);


    richTextFont = workbook.createFont();
    richTextFont.setFontName("Tahoma");
    richTextFont.setBold(true);
    richTextFont.setColor(HSSFColor.RED.index);

    // border
    this.setBottomBorderCell(styleHeader, Color.decode(HEADER_BORDER_COLOR_HEX));

    CreationHelper createHelper = workbook.getCreationHelper();

    columnStyles = new XSSFCellStyle[columnTypes.length];
    for (int c = 0; c < columnTypes.length; c++) {

      columnStyles[c] = (XSSFCellStyle) workbook.createCellStyle();
      switch (columnTypes[c]) {

      // Style numeric
        case COLUMN_TYPE_NUMERIC:
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          break;

          // Style date
        case COLUMN_TYPE_DATE:
          columnStyles[c].setDataFormat(createHelper.createDataFormat().getFormat(CELL_DATE_FORMAT));
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          break;

          // styleBoleean
        case COLUMN_TYPE_BOOLEAN:
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          columnStyles[c].setDataFormat(workbook.createDataFormat().getFormat("#.##"));
          break;

          // styleBudget
        case COLUMN_TYPE_BUDGET:
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          columnStyles[c].setDataFormat(workbook.createDataFormat().getFormat("$#,##0.00"));
          // "_($* #,##0.00_);_($* (#,##0.00);_($* \"-\"??_);_(@_)"
          break;

          // Style decimal
        case COLUMN_TYPE_DECIMAL:
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          columnStyles[c].setDataFormat(workbook.createDataFormat().getFormat("#.##"));
          break;

          // Style long string
        case COLUMN_TYPE_TEXT_LONG:
          columnStyles[c].setAlignment(HorizontalAlignment.LEFT);
          columnStyles[c].setWrapText(true);
          break;

          // Style short string
        case COLUMN_TYPE_TEXT_SHORT:
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          break;

          // Style hyperlink
        case COLUMN_TYPE_HYPERLINK:
          XSSFFont hlinkfont = (XSSFFont) workbook.createFont();
          hlinkfont.setUnderline(XSSFFont.U_SINGLE);
          hlinkfont.setColor(HSSFColor.BLUE.index);
          columnStyles[c].setFont(hlinkfont);
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          break;

        // Style hyperlink
        case COLUMN_TYPE_DATE_TIME:
          columnStyles[c].setDataFormat(createHelper.createDataFormat().getFormat(CELL_DATE_TIME_FORMAT));
          columnStyles[c].setAlignment(CellStyle.ALIGN_CENTER);
          break;


      }
      this.setBottomBorderCell(columnStyles[c], Color.decode(CELL_BORDER_COLOR_HEX));
      if (c == 0) {
        columnStyles[c].setBorderLeft(CELL_BORDER_TYPE_LEFT);
        columnStyles[c].setBorderColor(BorderSide.LEFT, new XSSFColor(Color.decode(CELL_BORDER_COLOR_HEX)));
      } else if (c == columnTypes.length - 1) {
        columnStyles[c].setBorderRight(CELL_BORDER_TYPE_RIGHT);
        columnStyles[c].setBorderColor(BorderSide.RIGHT, new XSSFColor(Color.decode(CELL_BORDER_COLOR_HEX)));
      }

    }


  }

  /**
   * Method used for to initialize an Excel Workbook object.
   * It creates a Workbook object using a predefined template.
   * 
   * @param useTemplate is true if you want to use a templa, false if you want to create the Workbook empty.
   * @return a Workbook Object representing the Workbook instance where is going to be written all the information in
   *         XLS format.
   */
  public XSSFWorkbook initializeWorkbook(boolean useTemplate) {
    textProvider = new DefaultTextProvider();
    outputStream = new ByteArrayOutputStream();
    usingTemplate = useTemplate;

    try {
      // validating the type of format.
      if (useTemplate) {
        LOG.info("Loading template from: " + this.excelTemplateFile);
        InputStream templateStream = new FileInputStream(this.excelTemplateFile);
        // creating workbook based on the template.
        workbook = new XSSFWorkbook(templateStream);

        // closing input stream.
        templateStream.close();

      } else {
        workbook = new XSSFWorkbook();
      }
      return (XSSFWorkbook) workbook;

    } catch (IOException e) {
      LOG.error("There was a problem trying to create the Excel Workbook: " + excelTemplateFile, e.getMessage());
    }
    return null;
  }

  /**
   * This method move the cursor to the next column.
   */
  public void nextColumn() {
    columnCounter++;
  }

  /**
   * This method move the cursor to the beginning of the next row.
   */
  public void nextRow() {
    rowCounter++;
    columnCounter = columnStart;
  }


  /**
   * This method writes any value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void prepareCell(Sheet sheet) {

    Row row = sheet.getRow(rowCounter);
    // if there is no row index, it should create it
    if (row == null) {
      row = sheet.createRow(rowCounter);
    }
    row.setHeightInPoints((5 * sheet.getDefaultRowHeightInPoints()));
    cell = row.createCell(columnCounter);
    cell.setCellStyle(columnStyles[columnCounter - 1]);
  }


  private void setBottomBorderCell(XSSFCellStyle cellStyle, Color color) {
    // Create the border
    cellStyle.setBorderBottom(CELL_BORDER_TYPE_BOTTOM);

    // Set color border
    cellStyle.setBorderColor(BorderSide.BOTTOM, new XSSFColor(color));

    cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
  }

  /**
   * This method writes boolean value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeBoolean(Sheet sheet, boolean value) {
    this.prepareCell(sheet);
    if (value == true) {
      cell.setCellValue(CELL_TRUE_BOOLEAN);
    } else {
      cell.setCellValue(CELL_FALSE_BOOLEAN);
    }
  }


  /**
   * This method writes double value with format budget into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeBudget(Sheet sheet, double value) {
    this.prepareCell(sheet);
    cell.setCellValue(value);
  }

  /**
   * This method writes date value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeDate(Sheet sheet, Date value) {
    this.prepareCell(sheet);
    cell.setCellValue(value);
  }

  /**
   * This method writes double value with format budget into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeDescription(Sheet sheet, String description) {
    // Set description
    Row row = sheet.getRow(REPORT_DESCRIPTION_ROW);
    Cell cell = row.getCell(REPORT_DESCRIPTION_COLUMN);
    cell.getCellStyle().setAlignment(CellStyle.ALIGN_LEFT);
    cell.getCellStyle().setWrapText(true);
    cell.setCellValue(description);

  }


  /**
   * This method writes integer value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeDouble(Sheet sheet, double value) {
    this.prepareCell(sheet);
    cell.setCellValue(value);
  }

  /**
   * This method writes the headers into the given sheet.
   * 
   * @param sheet is the sheet where you want to write the header.
   * @param headers is the array of headers to write.
   */
  public void writeHeaders(Sheet sheet, String[] headers) {
    if (usingTemplate) {
      // Row
      Row row = sheet.createRow(rowStart - 1);
      row.setHeightInPoints(HEADER_ROW_HEIGHT);

      // Writing headers.
      Cell cell;
      int counter;
      for (counter = 1; counter <= headers.length; counter++) {
        cell = row.createCell(counter);
        cell.setCellStyle(styleHeader);
        cell.setCellValue(headers[counter - 1]);
        sheet.autoSizeColumn(counter);
      }
    } else {
      // TODO To develop the same algorithm but without style starting in the first row of the sheet.
    }
  }


  /**
   * This method writes string value with hyperlink url into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   * @param link is the specific link with the to reference
   */
  public void writeHyperlink(Sheet sheet, String value, XSSFHyperlink link) {
    // Set description
    this.prepareCell(sheet);
    cell.setCellValue(value);
    cell.setHyperlink(link);
  }


  /**
   * This method writes integer value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeInteger(Sheet sheet, int value) {
    this.prepareCell(sheet);
    cell.setCellValue(value);
    // sheet.autoSizeColumn(columnCounter);
  }


  /**
   * This method writes string value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   */
  public void writeString(Sheet sheet, String value) {
    this.prepareCell(sheet);

    if (value == null) {
      cell.setCellValue("");
    } else {
      if (value.toString().length() > 30) {
        sheet.setColumnWidth(columnCounter, 12000);
      } else {
        sheet.setColumnWidth(columnCounter, 8000);
      }
      cell.setCellValue(value);
    }

  }


  /**
   * This method writes string value into a specific cell.
   * 
   * @param sheet is the sheet where you want to add information into.
   * @param value is the specific information to be written.
   * @param terms
   */
  public void writeString(Sheet sheet, String text, String[] terms) {
    this.prepareCell(sheet);
    StringTokenizer tokens;
    String token;
    Pattern pat;
    Matcher mat;
    XSSFRichTextString richText = new XSSFRichTextString();
    boolean found;
    if (text == null) {
      cell.setCellValue("");
    } else {
      tokens = new StringTokenizer(text);
      while (tokens.hasMoreTokens()) {
        found = false;
        token = tokens.nextToken();
        richText.append(token);

        // searching terms in text
        for (String term : terms) {

          pat = Pattern.compile("^\\p{Punct}?+" + term.toLowerCase() + "\\p{Punct}?");
          mat = pat.matcher(token.toLowerCase());
          if (mat.matches()) {
            found = true;
            break;
          }
        }
        if (found) {
          richText.applyFont(richText.length() - token.length(), richText.length(), richTextFont);
        }
        richText.append(" ");
      }

      if (text.toString().length() > 30) {
        sheet.setColumnWidth(columnCounter, 12000);
      }
      cell.setCellValue(richText);
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
    XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1, 1, 1, 1, 3, 6);
    anchor.setAnchorType(2);
    XSSFTextBox textbox = draw.createTextbox(anchor);

    textbox.setFillColor(TEXTBOX_BACKGROUND_COLOR_RGB.getRed(), TEXTBOX_BACKGROUND_COLOR_RGB.getGreen(),
      TEXTBOX_BACKGROUND_COLOR_RGB.getBlue());
    textbox.setVerticalAlignment(VerticalAlignment.CENTER);

    XSSFRichTextString stringX = new XSSFRichTextString();
    Font font = workbook.createFont();
    font.setFontHeightInPoints((short) 20);
    font.setFontName("Tahoma");
    font.setColor(TEXTBOX_FONT_COLOR_INDEX);
    stringX.append(text);


    stringX.applyFont(font);
    textbox.setText(stringX);
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
