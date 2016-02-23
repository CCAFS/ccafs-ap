package org.cgiar.ccafs.ap.summaries.projects.pdf;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasePDF {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(BasePDF.class);

  // Page orientation
  public final static int PORTRAIT = 1;
  public final static int LANDSCAPE = 2;

  // Row index
  public final static int ROW_EVEN = 0;
  public final static int ROW_ODD = 1;

  // Table width
  public final static int TABLE_WIDTH_PORTRAIT = 500;
  public final static int TABLE_WIDTH_LANDSCAPE = 700;

  // Colors
  public final static Color titleColor = new Color(102, 55, 0);
  public final static Color bodyColor = new Color(34, 34, 34);

  // Fonts
  public final static Font HEADING1_FONT = new Font(FontFactory.getFont("openSans", 24, Font.BOLD, titleColor));
  public final static Font HEADING2_FONT = new Font(FontFactory.getFont("openSans", 16, Font.BOLD, titleColor));
  public final static Font HEADING3_FONT = new Font(FontFactory.getFont("openSans", 14, Font.BOLD, titleColor));
  public final static Font HEADING4_FONT = new Font(FontFactory.getFont("openSans", 12, Font.BOLD, titleColor));

  public final static Font BODY_TEXT_FONT = new Font(FontFactory.getFont("openSans", 12, bodyColor));
  public final static Font BODY_TEXT_FONT_LINK = new Font(FontFactory.getFont("openSans", 12, Color.BLUE));
  public final static Font BODY_TEXT_BOLD_FONT = new Font(FontFactory.getFont("openSans", 12, Font.BOLD, bodyColor));

  public final static Font TABLE_BODY_FONT_LINK =
    new Font(Font.getFamilyIndex("openSans"), 10, Font.UNDERLINE, Color.BLUE);
  public final static Font TABLE_HEADER_FONT = new Font(FontFactory.getFont("openSans", 10, Font.BOLD, Color.WHITE));
  public final static Font TABLE_BODY_FONT = new Font(FontFactory.getFont("openSans", 10, bodyColor));
  public final static Font TABLE_BODY_BOLD_FONT = new Font(FontFactory.getFont("openSans", 10, Font.BOLD, bodyColor));

  // Backgrounds colors
  public final static Color TABLE_HEADER_BACKGROUND = new Color(48, 140, 175);
  public final static Color TABLE_BODY_EVEN_ROW_BACKGROUND = new Color(238, 242, 248);
  public final static Color TABLE_BODY_ODD_ROW_BACKGROUND = Color.WHITE;

  // Table cell border color
  public final static Color TABLE_CELL_BORDER_COLOR = new Color(225, 225, 225);

  // Images path
  private static String HEADER_IMAGE_PATH;
  private static String FOOTER_IMAGE_PATH;
  private static String CIAT_LOGO_PATH;
  private static String CCAFS_LOGO_PATH;


  // Text provider to read the internationalization file
  TextProvider textProvider;

  public BasePDF() {
  }

  /**
   * Create the cover page and add it to the document;
   * 
   * @param document - Document where the cover must be inserted.
   * @param title - Cover title.
   */
  public void addCover(Document document, String title) {
    Image headerImage = null, footerImage = null, ccafsLogo = null, ciatLogo = null;
    int orientation = (document.getPageSize().getHeight() > document.getPageSize().getWidth()) ? PORTRAIT : LANDSCAPE;

    try {
      headerImage = Image.getInstance(new URL(HEADER_IMAGE_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the header image: '{}'", HEADER_IMAGE_PATH, e);
    }

    try {
      footerImage = Image.getInstance(new URL(FOOTER_IMAGE_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the footer image: '{}'", FOOTER_IMAGE_PATH, e);
    }

    try {
      ccafsLogo = Image.getInstance(new URL(CCAFS_LOGO_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the logo of CCAFS: '{}'", CCAFS_LOGO_PATH, e);
    }

    try {
      ciatLogo = Image.getInstance(new URL(CIAT_LOGO_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the logo of CIAT: '{}'", CIAT_LOGO_PATH, e);
    }

    // Scale the image to the page size
    headerImage.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
    footerImage.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());

    // Adjust the size and positions of images in according to the page orientation

    // Portrait orientation
    if (document.getPageSize().getHeight() > document.getPageSize().getWidth()) {
      // Put the image on top of page
      ccafsLogo.setAbsolutePosition(75f, 250f);
      ciatLogo.setAbsolutePosition(380f, 250f);
    }

    // Landscape orientation
    if (document.getPageSize().getHeight() < document.getPageSize().getWidth()) {
      // Adjust the images height
      headerImage.scaleAbsoluteHeight(150);
      footerImage.scaleAbsoluteHeight(150);

      // Put the image on top of page
      ccafsLogo.setAbsolutePosition(150f, 175f);
      ciatLogo.setAbsolutePosition(480f, 175f);
    }

    headerImage.setAbsolutePosition(0f, document.getPageSize().getHeight() - headerImage.getScaledHeight());
    footerImage.setAbsolutePosition(0f, 0f);

    try {
      document.add(headerImage);
      document.add(ccafsLogo);
      document.add(ciatLogo);
      document.add(footerImage);

      Phrase phrase = new Phrase();
      phrase.setFont(HEADING1_FONT);
      int blankLines = (orientation == PORTRAIT) ? 20 : 17;
      for (int c = 0; c < blankLines; c++) {
        phrase.add(Chunk.NEWLINE);
      }
      document.add(phrase);

      Paragraph p = new Paragraph();
      p.setFont(HEADING1_FONT);
      p.setAlignment(Paragraph.ALIGN_CENTER);
      p.add(title);

      document.add(p);
    } catch (DocumentException e) {
      LOG.error("-- addCover() > There was an error adding images and title to the cover page", e);
    }
    document.newPage();
  }

  /**
   * Creates a PdfCell object add the text passed and
   * give it the standard format for body cells.
   * 
   * @param text - Text to insert into the cell.
   * @param alignment - Alignment to apply in the cell.
   * @return a PdfCell object with the text formatted.
   */
  public void addCustomTableCell(PdfPTable table, Paragraph paragraph, int alignment, Font cellFont, Color cellColor,
    int colspan, int rowspan, boolean showBorder) {
    PdfPCell cell = new PdfPCell(paragraph);

    // Set alignment
    cell.setHorizontalAlignment(alignment);
    cell.setVerticalAlignment(Element.ALIGN_LEFT);
    cell.setBackgroundColor(cellColor);

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPaddingBottom(5);


    // Set border color
    if (showBorder) {
      cell.setBorderColor(TABLE_CELL_BORDER_COLOR);
    } else {
      cell.setBorder(Rectangle.NO_BORDER);
    }

    // Set leading
    cell.setLeading(2, 1);

    if (colspan != 0) {
      cell.setColspan(colspan);
    }

    if (rowspan != 0) {
      cell.setRowspan(rowspan);
    }

    table.addCell(cell);
  }

  /**
   * Creates a PdfCell object add the text passed and
   * give it the standard format for body cells.
   * 
   * @param text - Text to insert into the cell.
   * @param alignment - Alignment to apply in the cell.
   * @return a PdfCell object with the text formatted.
   */
  public void addTableBodyCell(PdfPTable table, Paragraph paragraph, int alignment, int rowIndex) {
    PdfPCell cell = new PdfPCell(paragraph);

    // Set alignment
    cell.setHorizontalAlignment(alignment);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    if (rowIndex == ROW_EVEN) {
      cell.setBackgroundColor(TABLE_BODY_EVEN_ROW_BACKGROUND);
    } else {
      cell.setBackgroundColor(TABLE_BODY_ODD_ROW_BACKGROUND);
    }

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPadding(5);

    // Set border color
    cell.setBorderColor(TABLE_CELL_BORDER_COLOR);

    // Set leading
    cell.setLeading(2, 1);

    table.addCell(cell);
  }

  /**
   * Creates a PdfCell object add the text passed and
   * give it the standard format for body cells.
   * 
   * @param text - Text to insert into the cell.
   * @param alignment - Alignment to apply in the cell.
   * @return a PdfCell object with the text formatted.
   */
  public void addTableColSpanCell(PdfPTable table, Paragraph paragraph, int alignment, int rowIndex, int colspan) {
    PdfPCell cell = new PdfPCell(paragraph);

    // Set alignment
    cell.setHorizontalAlignment(alignment);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    if (rowIndex == ROW_EVEN) {
      cell.setBackgroundColor(TABLE_BODY_EVEN_ROW_BACKGROUND);
    } else {
      cell.setBackgroundColor(TABLE_BODY_ODD_ROW_BACKGROUND);
    }

    // add setColspan
    cell.setColspan(colspan);

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPadding(5);

    // Set border color
    cell.setBorderColor(TABLE_CELL_BORDER_COLOR);

    // Set leading
    cell.setLeading(2, 1);

    table.addCell(cell);
  }


  /**
   * Creates a PdfCell object add the text passed and
   * give it the standard format for header cells.
   * 
   * @param text - Text to insert into the cell.
   * @return a PdfCell object with the text formatted.
   */
  public void addTableHeaderCell(PdfPTable table, Paragraph paragraph) {
    PdfPCell cell = new PdfPCell(paragraph);

    // Set alignment
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBackgroundColor(TABLE_HEADER_BACKGROUND);

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPadding(3);

    // Set border color
    cell.setBorderColor(TABLE_CELL_BORDER_COLOR);

    table.addCell(cell);
  }


  /**
   * Creates a PdfCell object add the text passed and
   * give it the standard format for header cells.
   * 
   * @param cell - Cell for insert
   * @return a PdfCell object with the text formatted.
   */
  public void addTableHeaderCell(PdfPTable table, PdfPCell cell) {
    // Set alignment
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBackgroundColor(TABLE_HEADER_BACKGROUND);

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPadding(3);

    // Set border color
    cell.setBorderColor(TABLE_CELL_BORDER_COLOR);

    table.addCell(cell);
  }


  /**
   * Creates a PdfCell object add the text passed and
   * give it the standard format for header cell with colspan
   * 
   * @param text - Text to insert into the cell.
   * @return a PdfCell object with the text formatted.
   */
  public void addTableHeaderCellColspan(PdfPTable table, Paragraph paragraph, int colspan) {
    PdfPCell cell = new PdfPCell(paragraph);

    // Set alignment
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBackgroundColor(TABLE_HEADER_BACKGROUND);

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPadding(3);

    // Set border color
    cell.setBorderColor(TABLE_CELL_BORDER_COLOR);


    // colspan
    cell.setColspan(colspan);
    table.addCell(cell);
  }

  /**
   * Add a title in the current page of document.
   * 
   * @param document - Document where title must be inserted
   * @param text - Title to insert
   */
  public void addTitle(Document document, String text) {
    Paragraph paragraph = new Paragraph();
    paragraph.setFont(HEADING1_FONT);
    paragraph.add(text);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    try {
      document.add(paragraph);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error adding title to the document", e);
    }
  }

  /**
   * Create a box where should be placed the header and footer
   * according to the page orientation.
   * 
   * @param pageOrientation - Landscape or portrait
   * @return A Rectangle object.
   */
  public Rectangle getBoxSize(int pageOrientation) {
    switch (pageOrientation) {
      case PORTRAIT:
        return new Rectangle(36, 20, 590, 770);
      case LANDSCAPE:
        return new Rectangle(36, 20, 770, 590);
      default:
        // By default the Portrait rectangle
        return new Rectangle(36, 20, 590, 770);
    }
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
   * @param baseUrl
   */
  public void initialize(String baseUrl) {
    textProvider = new DefaultTextProvider();

    // Use this space to register and initialize the default font
    FontFactory.register(baseUrl + "/resources/pdfFonts/open_sans/OpenSans-Regular.ttf", "openSans");
    FontFactory.register(baseUrl + "/resources/pdfFonts/open_sans/OpenSans-Bold.ttf", "openSansBold");
    FontFactory.register(baseUrl + "/resources/pdfFonts/open_sans/OpenSans-Italic.ttf", "openSansItalic");
    FontFactory.register(baseUrl + "/resources/pdfFonts/calibri.ttf", "calibri");

    HEADER_IMAGE_PATH = baseUrl + "/images/global/header-background.png";
    FOOTER_IMAGE_PATH = baseUrl + "/images/global/footer-background.png";
    CIAT_LOGO_PATH = baseUrl + "/images/summaries/logo_ciat.png";
    CCAFS_LOGO_PATH = baseUrl + "/images/summaries/logo_ccafs.png";
  }

  /**
   * Initialize the document object with the right size according to
   * pageOrientation and get an instance of PdfWriter.
   * 
   * @param document - Document to initialize.
   * @param outputStream - Stream to store the data
   * @param pageOrientation - Landscape or portrait
   * @return the PdfWriter object.
   */
  public PdfWriter initializePdf(Document document, ByteArrayOutputStream outputStream, int pageOrientation) {

    // Set the page orientation
    if (pageOrientation == LANDSCAPE) {
      document.setPageSize(PageSize.LETTER.rotate());
    } else {
      document.setPageSize(PageSize.LETTER);
    }

    PdfWriter writer = null;
    try {
      writer = PdfWriter.getInstance(document, outputStream);
      writer.setBoxSize("art", this.getBoxSize(pageOrientation));
    } catch (DocumentException e) {
      LOG.error("-- initializePdf() > There was an error initializing the pdf file.", e);
    }

    return writer;
  }

}
