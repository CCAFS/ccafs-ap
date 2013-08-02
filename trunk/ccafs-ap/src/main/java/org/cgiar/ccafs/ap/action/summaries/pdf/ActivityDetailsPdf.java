package org.cgiar.ccafs.ap.action.summaries.pdf;

import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.util.BasePdf;
import org.cgiar.ccafs.ap.util.HeaderFooterPdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;

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
  private String fileName;
  private String fileTitle;
  private InputStream inputStream;
  private int contentLength;

  public ActivityDetailsPdf(String fileName, String fileTitle) {
    this.fileName = fileName;
    this.fileTitle = fileTitle;
  }

  public void generatePdf(Activity[] activities) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);
    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");

    PdfWriter writer = BasePdf.initializePdf(document, outputStream, BasePdf.LANDSCAPE);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(fileTitle, BasePdf.LANDSCAPE);
    writer.setPageEvent(event);

    document.open();

    // Cover page
    BasePdf.addCover(document, "Test Report");

    // Summary title
    BasePdf.addTitle(document, "Titulo de prueba");

    // Create table
    try {
      PdfPTable table = new PdfPTable(11);
      table.setLockedWidth(true);
      table.setTotalWidth(700);
      table.setWidths(new int[] {1, 3, 1, 1, 1, 1, 1, 2, 1, 2, 2});

      // Add table headers
      BasePdf.addTableHeaderCell(table, "Activity ID");
      BasePdf.addTableHeaderCell(table, "Activity title");
      BasePdf.addTableHeaderCell(table, "Leader");
      BasePdf.addTableHeaderCell(table, "Contact person(s)");
      BasePdf.addTableHeaderCell(table, "Start date");
      BasePdf.addTableHeaderCell(table, "End date");
      BasePdf.addTableHeaderCell(table, "Status");
      BasePdf.addTableHeaderCell(table, "Regions");
      BasePdf.addTableHeaderCell(table, "CCAFS Sites");
      BasePdf.addTableHeaderCell(table, "Key partners");
      BasePdf.addTableHeaderCell(table, "Key deliverables");

      Activity a;
      // Add table contents
      for (int c = 0; c < activities.length; c++) {
        a = activities[c];
        BasePdf.addTableBodyCell(table, String.valueOf(a.getId()), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, a.getTitle(), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, a.getLeader().getAcronym(), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, dateFormat.format(a.getStartDate()), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, dateFormat.format(a.getEndDate()), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, a.getStatus().getName(), Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);
        BasePdf.addTableBodyCell(table, "Element test kdfjd aksdfj ", Element.ALIGN_CENTER, c % 2);

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
}
