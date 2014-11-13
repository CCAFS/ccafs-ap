package org.cgiar.ccafs.ap.action.summaries.pdfs;

import org.cgiar.ccafs.ap.config.APConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.inject.Inject;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestSummaryPdf {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(TestSummaryPdf.class);

  // Attributes
  private String summaryTitle;
  private InputStream inputStream;
  private int contentLength;
  private BasePdf basePdf;

  @Inject
  // public TestSummaryPdf(IPProgramManager programManager) {
  public TestSummaryPdf(APConfig config, BasePdf basePdf) {
    basePdf.initialize(config.getBaseUrl());
    this.basePdf = basePdf;
  }


  public void generatePdf() {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);

    PdfWriter writer = basePdf.initializePdf(document, outputStream, basePdf.PORTRAIT);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(summaryTitle, basePdf.PORTRAIT);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Cover page
    basePdf.addCover(document, summaryTitle);

    // Summary title
    basePdf.addTitle(document, summaryTitle);


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

  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
