package org.cgiar.ccafs.ap.summaries.planning.pdf;

import org.cgiar.ccafs.ap.data.model.Submission;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;


public class HeaderFooterPDF extends PdfPageEventHelper {

  private final static Font HEADER_FONT = new Font(FontFactory.getFont("Arial", 12, Color.WHITE));
  private final static Font SUB_HEADER_FONT = new Font(FontFactory.getFont("Arial", 10, Color.GRAY));
  private final static Font FOOTER_FONT = new Font(FontFactory.getFont("Arial", 12, Color.BLACK));

  private String headerText;
  private int pagenumber;
  private Submission submissionCurrentPlanningYear;

  public HeaderFooterPDF(String headerText, int pageOrientation, Submission submissionCurrentPlanningYear) {
    this.submissionCurrentPlanningYear = submissionCurrentPlanningYear;
    this.headerText = headerText;
  }

  /**
   * Adds the header and the footer.
   */
  @Override
  public void onEndPage(PdfWriter writer, Document document) {
    PdfContentByte cb = writer.getDirectContent();
    Color headerColor = new Color(14, 125, 167);
    float pageWidth = cb.getPdfDocument().getPageSize().getWidth();

    // Background rectangle
    cb.saveState();
    cb.setColorStroke(headerColor);
    cb.setColorFill(headerColor);
    cb.rectangle(0, 744, pageWidth, 50);
    cb.fillStroke();
    cb.stroke();
    cb.restoreState();

    Rectangle rect = writer.getBoxSize("art");

    // Header
    Phrase phrase = new Phrase();
    phrase.setFont(HEADER_FONT);
    phrase.add(headerText);
    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, phrase, rect.getRight(), rect.getTop(),
      0);

    // Submit Pending
    phrase = new Phrase();
    phrase.setFont(HEADER_FONT);
    phrase.setFont(new Font(FontFactory.getFont("Arial", 12, Font.ITALIC, Color.WHITE)));
    DateFormat date = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm z");
    if (this.submissionCurrentPlanningYear != null) {
      phrase.add("Submitted on ");
      phrase.add(date.format(submissionCurrentPlanningYear.getDateTime()));
    } else {
      phrase.add("Submission: ");
      phrase.add("<pending>");
    }

    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase, rect.getLeft(),
      rect.getTop() - 20, 0);

    // Date
    phrase = new Phrase();
    phrase.setFont(SUB_HEADER_FONT);

    phrase.add("This report was generated on " + date.format(new Date()));
    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, phrase, rect.getLeft(), rect.getBottom(),
      0);

    // Footer Number
    phrase = new Phrase();
    phrase.setFont(FOOTER_FONT);
    phrase.add(String.valueOf(pagenumber));
    ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, phrase, rect.getRight(),
      rect.getBottom(), 0);
  }

  /**
   * Increase the page number.
   */
  @Override
  public void onStartPage(PdfWriter writer, Document document) {
    pagenumber++;
  }
}
