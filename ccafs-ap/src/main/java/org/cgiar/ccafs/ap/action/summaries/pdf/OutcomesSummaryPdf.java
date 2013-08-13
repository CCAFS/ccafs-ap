package org.cgiar.ccafs.ap.action.summaries.pdf;

import org.cgiar.ccafs.ap.data.model.Outcome;
import org.cgiar.ccafs.ap.util.BasePdf;
import org.cgiar.ccafs.ap.util.HeaderFooterPdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomesSummaryPdf {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OutcomesSummaryPdf.class);

  // Attributes
  private String summaryTitle;
  private InputStream inputStream;
  private int contentLength;

  public OutcomesSummaryPdf(String summaryTitle) {
    this.summaryTitle = summaryTitle;
  }

  public void generatePdf(List<Outcome> outcomes) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);

    PdfWriter writer = BasePdf.initializePdf(document, outputStream, BasePdf.LANDSCAPE);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(summaryTitle, BasePdf.LANDSCAPE);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Cover page
    BasePdf.addCover(document, summaryTitle);

    // Summary title
    BasePdf.addTitle(document, summaryTitle);

    // Add content
    try {
      PdfPTable table = new PdfPTable(3);

      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(BasePdf.TABLE_WIDTH_LANDSCAPE);
      table.setWidths(new int[] {1, 2, 10});

      // Repeat header in every page
      table.setHeaderRows(1);

      // Add table headers
      BasePdf.addTableHeaderCell(table, "ID");
      BasePdf.addTableHeaderCell(table, "Leader");
      BasePdf.addTableHeaderCell(table, "Title");

      // Add table body
      Outcome outcomeTemp;
      for (int c = 0; c < outcomes.size(); c++) {
        outcomeTemp = outcomes.get(c);
        BasePdf.addTableBodyCell(table, String.valueOf(outcomeTemp.getId()), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, outcomeTemp.getLeader().getAcronym(), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, outcomeTemp.getOutcome(), Element.ALIGN_LEFT, c % 2);
      }

      document.add(table);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }

    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();
    inputStream = (new ByteArrayInputStream(outputStream.toByteArray()));
  }

  public int getContentLength() {
    return contentLength;
  }

  public String getFileName() {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    String fileName;

    fileName = summaryTitle.replace(' ', '_');
    fileName += "_" + dateFormat.format(new Date());
    fileName += ".pdf";

    return fileName;
  }

  public String getFileTitle() {
    return summaryTitle;
  }

  public InputStream getInputStream() {
    return inputStream;
  }
}
