package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.util.PdfManager;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;


public class TestSummary extends BaseAction {

  private static final long serialVersionUID = 8458390479176682313L;
  public static final String RESULT = "test.pdf";
  private InputStream inputStream;
  private int contentLength;

  @Inject
  public TestSummary(APConfig config, LogframeManager logframeManager) {
    super(config, logframeManager);
  }

  public void createPdf(String filename) throws DocumentException, IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document();


    PdfManager.initializePdf(document, outputStream, PdfManager.PORTRAIT);

    document.open();

    PdfManager.addCover(document);
    PdfManager.addTitle(document, "Titulo de prueba");
    PdfManager.addParagraph(document, "Prueba texto");
    document.add(new Chunk(" Test "));
    document.add(new Chunk().NEWLINE);
    document.add(new Chunk(" Test 2"));
    document.add(new Chunk().NEWLINE);
    document.add(new Chunk(" Test 3").setBackground(new Color(231, 255, 0)));
    document.add(new Chunk().NEWLINE);
    Phrase phrase = new Phrase("Prueba frase ");
    phrase.add(new Chunk("Ademas agregamos chunk"));
    document.add(phrase);

    List<List<String>> table = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      List<String> row = new ArrayList<>();
      for (int j = 0; j < 6; j++) {
        row.add("string " + j);
      }
      table.add(row);
    }

    PdfManager.addTable(document, table);


    // step 5
    document.close();

    contentLength = outputStream.size();
    inputStream = (new ByteArrayInputStream(outputStream.toByteArray()));
  }

  @Override
  public String execute() throws Exception {
    createPdf(RESULT);
    return SUCCESS;
  }

  public int getContentLength() {
    return contentLength;
  }

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }
}
