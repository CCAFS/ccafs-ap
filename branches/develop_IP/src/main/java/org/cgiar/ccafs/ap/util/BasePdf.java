package org.cgiar.ccafs.ap.util;

import org.cgiar.ccafs.ap.config.APConfig;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import com.google.inject.Inject;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BasePdf {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(BasePdf.class);

  // Config
  private APConfig config;

  // Page orientation
  public final static int PORTRAIT = 1;
  public final static int LANDSCAPE = 2;

  // Row index
  public final static int ROW_EVEN = 0;
  public final static int ROW_ODD = 1;

  // Table width
  public final static int TABLE_WIDTH_PORTRAIT = 500;
  public final static int TABLE_WIDTH_LANDSCAPE = 700;

  // Fonts
  private final static Font TITLE_FONT = new Font(
    FontFactory.getFont("calibri", 24, Font.BOLD, new Color(153, 102, 51)));
  private final static Font BODY_TEXT_FONT = new Font(FontFactory.getFont("calibri", 12, Color.BLACK));
  private final static Font TABLE_HEADER_FONT = new Font(FontFactory.getFont("calibri", 11, Font.BOLD, Color.WHITE));
  private final static Font TABLE_BODY_FONT = new Font(FontFactory.getFont("calibri", 10, new Color(34, 34, 34)));

  // Backgrounds colors
  private final static Color TABLE_HEADER_BACKGROUND = new Color(155, 187, 89);
  private final static Color TABLE_BODY_EVEN_ROW_BACKGROUND = new Color(234, 241, 221);
  private final static Color TABLE_BODY_ODD_ROW_BACKGROUND = Color.WHITE;

  // Table cell border color
  private final static Color TABLE_CELL_BORDER_COLOR = new Color(225, 225, 225);

  // Images path
  private static String HEADER_IMAGE_PATH;
  private static String FOOTER_IMAGE_PATH;
  private static String CIAT_LOGO_PATH;
  private static String CCAFS_LOGO_PATH;

  @Inject
  public BasePdf(APConfig config) {
    this.config = config;

    // Use this space to register and initialize the default font
    FontFactory.register(config.getBaseUrl() + "/resources/pdfFonts/calibri.ttf", "calibri");

    HEADER_IMAGE_PATH = config.getBaseUrl() + "/images/global/header-background.png";
    FOOTER_IMAGE_PATH = config.getBaseUrl() + "/images/global/footer-background.png";
    CIAT_LOGO_PATH = config.getBaseUrl() + "/images/summaries/logo_ciat.png";
    CCAFS_LOGO_PATH = config.getBaseUrl() + "/images/summaries/logo_ccafs.png";
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
      LOG.error("-- addCover() > There was an error loading the image '{}'", HEADER_IMAGE_PATH, e);
    }

    try {
      footerImage = Image.getInstance(new URL(FOOTER_IMAGE_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the image '{}'", FOOTER_IMAGE_PATH, e);
    }

    try {
      ccafsLogo = Image.getInstance(new URL(CCAFS_LOGO_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the image '{}'", CCAFS_LOGO_PATH, e);
    }

    try {
      ciatLogo = Image.getInstance(new URL(CIAT_LOGO_PATH));
    } catch (BadElementException | IOException e) {
      LOG.error("-- addCover() > There was an error loading the image '{}'", CIAT_LOGO_PATH, e);
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
      phrase.setFont(TITLE_FONT);
      int blankLines = (orientation == PORTRAIT) ? 20 : 17;
      for (int c = 0; c < blankLines; c++) {
        phrase.add(new Chunk().NEWLINE);
      }
      document.add(phrase);

      Paragraph p = new Paragraph();
      p.setFont(TITLE_FONT);
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
  public void addCustomTableCell(PdfPTable table, String text, int alignment, Font cellFont, Color cellColor,
    int colspan) {
    Paragraph paragraph = new Paragraph(text, cellFont);
    paragraph.setAlignment(alignment);

    PdfPCell cell = new PdfPCell(paragraph);

    // Set alignment
    cell.setHorizontalAlignment(alignment);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBackgroundColor(cellColor);

    // Set padding
    cell.setUseBorderPadding(true);
    cell.setPadding(5);

    // Set border color
    cell.setBorderColor(TABLE_CELL_BORDER_COLOR);

    // Set leading
    cell.setLeading(2, 1);

    if (colspan != 0) {
      cell.setColspan(colspan);
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
  public void addTableBodyCell(PdfPTable table, String text, int alignment, int rowIndex) {
    Paragraph paragraph = new Paragraph(text, TABLE_BODY_FONT);
    paragraph.setAlignment(alignment);

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
   * give it the standard format for header cells.
   * 
   * @param text - Text to insert into the cell.
   * @return a PdfCell object with the text formatted.
   */
  public void addTableHeaderCell(PdfPTable table, String text) {
    Paragraph paragraph = new Paragraph(text, TABLE_HEADER_FONT);
    paragraph.setAlignment(Element.ALIGN_CENTER);

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
   * Add a title in the current page of document.
   * 
   * @param document - Document where title must be inserted
   * @param text - Title to insert
   */
  public void addTitle(Document document, String text) {
    Paragraph paragraph = new Paragraph();
    paragraph.setFont(TITLE_FONT);
    paragraph.add(text);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    try {
      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
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
   * Initialize the document object with the rigth size according to
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
      writer.setBoxSize("art", getBoxSize(pageOrientation));
    } catch (DocumentException e) {
      LOG.error("-- initializePdf() > There was an error initializing the pdf file.", e);
    }

    return writer;
  }
}
