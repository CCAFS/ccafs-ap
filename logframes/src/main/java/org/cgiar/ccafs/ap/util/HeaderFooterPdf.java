package org.cgiar.ccafs.ap.util;

import java.awt.Color;
import java.io.IOException;
import java.net.URL;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;


public class HeaderFooterPdf extends PdfPageEventHelper {

  private final static Font HEADER_FONT = new Font(FontFactory.getFont("openSans", 10, Color.BLACK));
  private final static Font FOOTER_FONT = new Font(FontFactory.getFont("openSans", 10, Color.BLACK));

  private String headerText;
  private int pagenumber;
  private int pageOrientation;
  private Image ccafsLogo;

  public HeaderFooterPdf(String headerText, int pageOrientation) {
    this.headerText = headerText;
    this.pageOrientation = pageOrientation;

    try {
      ccafsLogo = Image.getInstance(new URL(BasePdf.CCAFS_LOGO_PATH));
      ccafsLogo.scaleToFit(90, 90);
      ccafsLogo.setAbsolutePosition(470, 750);
    } catch (BadElementException | IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds the header and the footer.
   */
  @Override
  public void onEndPage(PdfWriter writer, Document document) {
    Rectangle rect = writer.getBoxSize("art");
    int xPos, yPos;

    // Header
    Phrase phrase = new Phrase();
    phrase.setFont(HEADER_FONT);
    phrase.add(headerText);
    try {
      document.add(ccafsLogo);
    } catch (DocumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase, rect.getLeft() + 20,
      rect.getTop() - 10, 0);

    // Footer
    phrase = new Phrase();
    phrase.setFont(FOOTER_FONT);
    phrase.add(String.valueOf(pagenumber));
    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase, rect.getRight() - 40,
      rect.getBottom() + 10, 0);
  }

  /**
   * Increase the page number.
   */
  @Override
  public void onStartPage(PdfWriter writer, Document document) {
    pagenumber++;
  }
}
