package org.cgiar.ccafs.ap.action.summaries.pdf;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.util.BasePdf;
import org.cgiar.ccafs.ap.util.HeaderFooterPdf;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.inject.Inject;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MilestonesSummaryPdf {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MilestonesSummaryPdf.class);

  // Attributes
  private APConfig config;
  private BasePdf basePdf;
  private String summaryTitle;
  private InputStream inputStream;
  private int contentLength;

  // Traffic light report custom header backgrounds
  private final Color THEME_ROW_BACKGROUND = new Color(73, 69, 41);
  private final Color OBJECTIVE_ROW_BACKGROUND = new Color(148, 138, 84);
  private final Color OUTCOME_ROW_BACKGROUND = new Color(196, 189, 151);
  private final Color OUTPUT_ROW_BACKGROUND = new Color(221, 217, 196);
  private final Color HEADER_ROW_BACKGROUND = new Color(242, 242, 242);
  private final Color BODY_ROW_BACKGROUND = new Color(234, 241, 221);
  private final Color STATUS_COMPLETE_BACKGROUND = new Color(146, 208, 80);
  private final Color STATUS_PARTIALLY_COMPLETE_BACKGROUND = new Color(255, 255, 0);
  private final Color STATUS_INCOMPLETE_BACKGROUND = new Color(255, 0, 0);

  // Traffic light report custom header backgrounds
  private final Font THEME_HEADER_FONT = new Font(FontFactory.getFont("calibri", 12, Font.BOLD, Color.WHITE));
  private final Font OBJECTIVE_HEADER_FONT = new Font(FontFactory.getFont("calibri", 10, Color.WHITE));
  private final Font OUTCOME_HEADER_FONT = new Font(FontFactory.getFont("calibri", 10, Color.BLACK));
  private final Font OUTPUT_HEADER_FONT = new Font(FontFactory.getFont("calibri", 10, Color.BLACK));

  @Inject
  public MilestonesSummaryPdf(APConfig config, BasePdf basePdf) {
    this.config = config;
    this.basePdf = basePdf;
  }

  /**
   * This function add the headers of the table to the traffic light report.
   * This header is composed by several rows, to know which row must be included
   * it is needed verify what change between the current milestoneReport and the
   * previous
   * 
   * @param table - table to add the headers
   * @param currentMR - Current milestone report
   * @param previousMR - Previous milestone report
   */
  public void addTrafficLightTableHeader(PdfPTable table, MilestoneReport currentMR, MilestoneReport previousMR) {
    boolean showOutput, showObjective, showTheme;
    String currentCode, previousCode;

    // If the previous milestone report is null we show all the headers
    if (previousMR == null) {
      showTheme = showObjective = showOutput = true;
    } else {
      // Table should show the output header ?
      currentCode = currentMR.getMilestone().getOutput().getCode();
      previousCode = previousMR.getMilestone().getOutput().getCode();
      showOutput = (!currentCode.equals(previousCode));

      // Table should show the objective header ?
      currentCode = currentMR.getMilestone().getOutput().getObjective().getCode();
      previousCode = previousMR.getMilestone().getOutput().getObjective().getCode();
      showObjective = (!currentCode.equals(previousCode));

      // Table should show the theme header ?
      currentCode = currentMR.getMilestone().getOutput().getObjective().getTheme().getCode();
      previousCode = previousMR.getMilestone().getOutput().getObjective().getTheme().getCode();
      showTheme = (!currentCode.equals(previousCode));
    }

    // Theme
    if (showTheme) {
      String themeTitle = "Theme " + currentMR.getMilestone().getOutput().getObjective().getTheme().getCode();
      themeTitle += ". ";
      themeTitle += currentMR.getMilestone().getOutput().getObjective().getTheme().getDescription();
      themeTitle += ".";
      basePdf.addCustomTableCell(table, themeTitle, Element.ALIGN_CENTER, THEME_HEADER_FONT, THEME_ROW_BACKGROUND, 3);
    }

    // Objective and outcome
    if (showObjective) {
      String objectiveTitle = "Objective ";
      objectiveTitle += currentMR.getMilestone().getOutput().getObjective().getTheme().getId() + ".";
      objectiveTitle += currentMR.getMilestone().getOutput().getObjective().getCode() + " ";
      objectiveTitle += currentMR.getMilestone().getOutput().getObjective().getDescription() + ".";
      basePdf.addCustomTableCell(table, objectiveTitle, Element.ALIGN_LEFT, OBJECTIVE_HEADER_FONT,
        OBJECTIVE_ROW_BACKGROUND, 3);

      String outcomeTitle = "Outcome ";
      outcomeTitle += currentMR.getMilestone().getOutput().getObjective().getTheme().getId() + ".";
      outcomeTitle += currentMR.getMilestone().getOutput().getObjective().getCode() + " ";
      outcomeTitle += currentMR.getMilestone().getOutput().getObjective().getOutcomeDescription() + ".";
      basePdf.addCustomTableCell(table, outcomeTitle, Element.ALIGN_LEFT, OUTCOME_HEADER_FONT, OUTCOME_ROW_BACKGROUND,
        3);
    }

    // Output
    if (showOutput) {
      String outputTitle = "Output ";
      outputTitle += currentMR.getMilestone().getOutput().getObjective().getTheme().getId() + ".";
      outputTitle += currentMR.getMilestone().getOutput().getObjective().getCode() + ".";
      outputTitle += currentMR.getMilestone().getOutput().getCode() + " ";
      outputTitle += currentMR.getMilestone().getOutput().getDescription() + ".";
      basePdf.addCustomTableCell(table, outputTitle, Element.ALIGN_LEFT, OUTPUT_HEADER_FONT, OUTPUT_ROW_BACKGROUND, 3);

      // If the theme also changed, then show the columns headers
      if (showTheme) {
        basePdf.addCustomTableCell(table, "Output target", Element.ALIGN_CENTER, OUTPUT_HEADER_FONT,
          HEADER_ROW_BACKGROUND, 0);
        basePdf.addCustomTableCell(table, "Status", Element.ALIGN_CENTER, OUTPUT_HEADER_FONT, HEADER_ROW_BACKGROUND, 0);
        basePdf.addCustomTableCell(table, "Explanation and evidence", Element.ALIGN_CENTER, OUTPUT_HEADER_FONT,
          HEADER_ROW_BACKGROUND, 0);
      }
    }
  }

  public void generateMilestoneReportPdf(MilestoneReport[] milestoneReports) {

  }

  public void generateTrafficLightReportPdf(MilestoneReport[] milestoneReports) {

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);

    PdfWriter writer = basePdf.initializePdf(document, outputStream, basePdf.LANDSCAPE);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(summaryTitle, basePdf.LANDSCAPE);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Cover page
    basePdf.addCover(document, summaryTitle);

    // Summary title
    basePdf.addTitle(document, summaryTitle);


    // Add content
    try {
      PdfPTable table = new PdfPTable(3);
      String milestoneTitle, milestoneStatus, milestoneReportText;
      Color statusColor = null;

      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(basePdf.TABLE_WIDTH_LANDSCAPE);
      table.setWidths(new int[] {7, 2, 10});

      // Add first table headers
      addTrafficLightTableHeader(table, milestoneReports[0], null);


      for (int c = 0; c < milestoneReports.length; c++) {
        statusColor = BODY_ROW_BACKGROUND;
        if (c != 0) {
          addTrafficLightTableHeader(table, milestoneReports[c], milestoneReports[c - 1]);
        }

        milestoneTitle = milestoneReports[c].getMilestone().getCode() + ". ";
        milestoneTitle += milestoneReports[c].getMilestone().getDescription();

        if (milestoneReports[c].getStatus().getId() != -1) {
          milestoneStatus = milestoneReports[c].getStatus().getName();
        } else {
          milestoneStatus = "No reported yet";
        }

        if (!milestoneReports[c].getThemeLeaderDescription().isEmpty()) {
          milestoneReportText = "TL: \n" + milestoneReports[c].getThemeLeaderDescription() + "\n";
          milestoneReportText += milestoneReports[c].getRegionalLeaderDescription();
        } else {
          milestoneReportText = milestoneReports[c].getRegionalLeaderDescription();
        }

        // Setting the color of status cell according to the value
        if (milestoneReports[c].getStatus().getId() == 1) {
          statusColor = STATUS_COMPLETE_BACKGROUND;
        } else if (milestoneReports[c].getStatus().getId() == 2) {
          statusColor = STATUS_PARTIALLY_COMPLETE_BACKGROUND;
        } else if (milestoneReports[c].getStatus().getId() == 3) {
          statusColor = STATUS_INCOMPLETE_BACKGROUND;
        }

        basePdf.addCustomTableCell(table, milestoneTitle, Element.ALIGN_LEFT, OUTPUT_HEADER_FONT, BODY_ROW_BACKGROUND,
          0);
        basePdf.addCustomTableCell(table, milestoneStatus, Element.ALIGN_CENTER, OUTPUT_HEADER_FONT, statusColor, 0);
        basePdf.addCustomTableCell(table, milestoneReportText, Element.ALIGN_LEFT, OUTPUT_HEADER_FONT,
          BODY_ROW_BACKGROUND, 0);
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

  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
