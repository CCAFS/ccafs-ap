package org.cgiar.ccafs.ap.action.summaries.pdf;

import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.util.BasePdf;
import org.cgiar.ccafs.ap.util.HeaderFooterPdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import com.google.inject.Inject;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ActivityDetailsPdf {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ActivityDetailsPdf.class);

  // Attributes
  private BasePdf basePdf;
  private String fileName;
  private String summaryTitle;
  private InputStream inputStream;
  private int contentLength;

  @Inject
  public ActivityDetailsPdf(BasePdf basePdf) {
    this.basePdf = basePdf;
  }

  public void generatePdf(Activity[] activities) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");

    PdfWriter writer = basePdf.initializePdf(document, outputStream, basePdf.LANDSCAPE);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(summaryTitle, basePdf.LANDSCAPE);
    writer.setPageEvent(event);

    document.open();

    // Cover page
    basePdf.addCover(document, "Test Report");

    // Summary title
    basePdf.addTitle(document, "Titulo de prueba");

    // Create table
    try {
      PdfPTable table = new PdfPTable(11);
      table.setLockedWidth(true);
      table.setTotalWidth(700);
      table.setWidths(new int[] {1, 3, 1, 1, 1, 1, 1, 2, 1, 2, 2});

      // Add table headers
      basePdf.addTableHeaderCell(table, "Activity ID");
      basePdf.addTableHeaderCell(table, "Activity title");
      basePdf.addTableHeaderCell(table, "Leader");
      basePdf.addTableHeaderCell(table, "Contact person(s)");
      basePdf.addTableHeaderCell(table, "Start date");
      basePdf.addTableHeaderCell(table, "End date");
      basePdf.addTableHeaderCell(table, "Status");
      basePdf.addTableHeaderCell(table, "Regions");
      basePdf.addTableHeaderCell(table, "CCAFS Sites");
      basePdf.addTableHeaderCell(table, "Key partners");
      basePdf.addTableHeaderCell(table, "Key deliverables");

      Activity a;
      // Add table contents
      for (int c = 0; c < activities.length; c++) {
        a = activities[c];
        basePdf.addTableBodyCell(table, String.valueOf(a.getId()), Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, a.getTitle(), Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, a.getLeader().getAcronym(), Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, dateFormat.format(a.getStartDate()), Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, dateFormat.format(a.getEndDate()), Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, a.getStatus().getName(), Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        basePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);

      }

      document.add(table);
    } catch (DocumentException e) {
      LOG.error("There was an error adding the Activity details table to ActivityDetail.pdf document.", e);
    }

    document.close();

    contentLength = outputStream.size();
    inputStream = (new ByteArrayInputStream(outputStream.toByteArray()));
  }

  public int getContentLength() {
    return contentLength;
  }

  public String getFileName() {
    return fileName;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
