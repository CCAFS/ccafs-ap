package org.cgiar.ccafs.ap.util;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PdfManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PdfManager.class);

  // Page orientation
  public final static int PORTRAIT = 1;
  public final static int LANDSCAPE = 2;

  // Fonts
  private final static Font TITLE_FONT = new Font(FontFactory.getFont("Arial", 26, Font.BOLD, new Color(153, 102, 51)));
  private final static Font NORMAL_FONT = new Font(FontFactory.getFont("Arial", 12, Color.BLACK));
  private final static Font HEADER_FONT = new Font(FontFactory.getFont("Arial", 12, Color.LIGHT_GRAY));
  private final static Font FOOTER_FONT = new Font(FontFactory.getFont("Arial", 12, Color.LIGHT_GRAY));
  private final static Font TABLE_HEADER_FONT = new Font(FontFactory.getFont("Arial", 10, Font.BOLD, Color.WHITE));
  private final static Font TABLE_BODY_FONT = new Font(FontFactory.getFont("Arial", 10, Color.BLACK));

  // Backgrounds colors
  private final static Color TABLE_HEADER_BACKGROUND = new Color(155, 187, 89);
  private final static Color TABLE_BODY_EVEN_ROW_BACKGROUND = new Color(234, 241, 221);
  private final static Color TABLE_BODY_ODD_ROW_BACKGROUND = Color.WHITE;


  public static void addCover(Document document, String title) {

    Image headerImage, ccafsLogo, ciatLogo, footerImage;
    try {
      headerImage = Image.getInstance(new URL("http://davinci.ciat.cgiar.org/images/global/header-background.png"));
      ccafsLogo = Image.getInstance(new URL("http://localhost/logo_ccafs.png"));
      ciatLogo = Image.getInstance(new URL("http://localhost/logo_ciat.png"));
      footerImage = Image.getInstance(new URL("http://davinci.ciat.cgiar.org/images/global/footer-background.png"));

      // Scale the image to the page size
      headerImage.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
      footerImage.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());

      // Put the image on top of page
      headerImage.setAbsolutePosition(0f, document.getPageSize().getHeight() - headerImage.getScaledHeight());
      ccafsLogo.setAbsolutePosition(75f, 250f);
      ciatLogo.setAbsolutePosition(380f, 250f);
      footerImage.setAbsolutePosition(0f, 0f);


      try {
        document.add(headerImage);
        document.add(ccafsLogo);
        document.add(ciatLogo);
        document.add(footerImage);

        Phrase phrase = new Phrase();
        for (int c = 0; c < 20; c++) {
          phrase.add(new Chunk().NEWLINE);
        }
        document.add(phrase);
        addTitle(document, title);
      } catch (DocumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      document.newPage();
    } catch (BadElementException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void addHeader(Document document, String text) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setFont(HEADER_FONT);
    paragraph.setAlignment(Paragraph.ALIGN_RIGHT);

    paragraph.add(text);
    document.add(paragraph);
  }

  public static void addParagraph(Document document, String text) throws DocumentException {

    Paragraph paragraph = new Paragraph();
    paragraph.setFont(NORMAL_FONT);
    paragraph.add(text);
    paragraph.setAlignment(Paragraph.ALIGN_JUSTIFIED);
    try {
      document.add(paragraph);
    } catch (DocumentException e) {
      LOG.error("There was an error adding a paragraph to the document", e);
    }
  }

  public static void addTable(Document doc, List<List<String>> data) throws DocumentException {
    int rowIndex = 0;
    PdfPCell tempCell;

    // Initialize and set the number of rows
    PdfPTable table = new PdfPTable(data.get(0).size());

    for (List<String> row : data) {
      for (String cellText : row) {
        if (rowIndex == 0) {
          tempCell = getTableHeaderCell(cellText);
          tempCell.setBackgroundColor(TABLE_HEADER_BACKGROUND);
          table.addCell(tempCell);
        } else {
          tempCell = getTableBodyCell(cellText);
          if (rowIndex % 2 == 0) {
            tempCell.setBackgroundColor(TABLE_BODY_EVEN_ROW_BACKGROUND);
          } else {
            tempCell.setBackgroundColor(TABLE_BODY_ODD_ROW_BACKGROUND);
          }
          table.addCell(tempCell);
        }
      }
      rowIndex++;
    }

    doc.add(table);
  }

  public static void addTitle(Document document, String text) {
    Paragraph paragraph = new Paragraph();
    paragraph.setFont(TITLE_FONT);
    paragraph.add(text);
    paragraph.setAlignment(Paragraph.ALIGN_CENTER);
    try {
      document.add(paragraph);
    } catch (DocumentException e) {
      LOG.error("There was an error adding title to the document", e);
    }
  }

  private static PdfPCell getTableBodyCell(String text) {
    Chunk chunk = new Chunk(text, TABLE_BODY_FONT);

    PdfPCell cell = new PdfPCell();
    cell.addElement(chunk);
    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
    return cell;
  }

  private static PdfPCell getTableHeaderCell(String text) {
    Paragraph paragraph = new Paragraph(text, TABLE_HEADER_FONT);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    PdfPCell cell = new PdfPCell();
    cell.addElement(paragraph);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setBackgroundColor(Color.LIGHT_GRAY);
    cell.setPadding(5f);
    return cell;
  }

  public static void initializePdf(Document document, ByteArrayOutputStream outputStream, int pageOrientation) {

    // Set the page orientation
    if (pageOrientation == LANDSCAPE) {
      document.setPageSize(PageSize.LETTER.rotate());
    } else {
      document.setPageSize(PageSize.LETTER);
    }

    try {
      PdfWriter.getInstance(document, outputStream);
    } catch (DocumentException e) {
      LOG.error("There was an error getting an instance of PdfWriter.", e);
    }
  }
}
