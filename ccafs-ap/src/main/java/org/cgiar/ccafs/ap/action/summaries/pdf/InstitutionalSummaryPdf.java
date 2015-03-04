package org.cgiar.ccafs.ap.action.summaries.pdf;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.ActivityRegionManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableMetadataManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityObjective;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.BenchmarkSite;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Outcome;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;
import org.cgiar.ccafs.ap.data.model.OutputSummary;
import org.cgiar.ccafs.ap.data.model.Publication;
import org.cgiar.ccafs.ap.data.model.PublicationTheme;
import org.cgiar.ccafs.ap.data.model.Region;
import org.cgiar.ccafs.ap.util.BasePdf;
import org.cgiar.ccafs.ap.util.HeaderFooterPdf;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import com.lowagie.text.Anchor;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.ListItem;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.opensymphony.xwork2.DefaultTextProvider;
import com.opensymphony.xwork2.TextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstitutionalSummaryPdf {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(InstitutionalSummaryPdf.class);

  // Attributes
  private BasePdf basePdf;
  private String summaryTitle;
  private InputStream inputStream;
  private int contentLength;
  private Document document;

  // Config
  private APConfig config;

  // Manager for specific sections - to avoid this, it will have to do some new report function on the manager
  private ActivityObjectiveManager activityObjectiveManager;
  private CaseStudyTypeManager caseStudyTypeManager;
  private DeliverableManager deliverableManager;
  private DeliverableMetadataManager deliverableMetadataManager;
  private ActivityCountryManager activityCountryManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityRegionManager activityRegionManager;

  // Text provider to read the internationalization file
  private TextProvider textProvider;

  // Margins
  private final float LEFT_MARGIN = (float) 56.7;
  private final float RIGHT_MARGIN = (float) 56.7;
  private final float TOP_MARGIN = (float) 56.7;
  private final float BOTTOM_MARGIN = (float) 56.7;

  // Colors
  public final static Color titleColor = new Color(102, 55, 0);
  public final static Color subTitleColor = new Color(118, 146, 60);
  public final static Color bodyColor = new Color(34, 34, 34);

  // Fonts
  public final static Font HEADING1_FONT = new Font(FontFactory.getFont("openSans", 24, Font.BOLD, titleColor));
  public final static Font HEADING2_FONT = new Font(FontFactory.getFont("openSans", 16, Font.BOLD, titleColor));
  public final static Font HEADING3_FONT = new Font(FontFactory.getFont("openSans", 13, Font.BOLD, subTitleColor));
  public final static Font HEADING4_FONT = new Font(FontFactory.getFont("openSans", 11, Font.NORMAL, subTitleColor));

  public final static Font LINK_TEXT_FONT = new Font(FontFactory.getFont("openSans", 11, Font.UNDERLINE, Color.BLUE));
  public final static Font BODY_TEXT_FONT = new Font(FontFactory.getFont("openSans", 11, bodyColor));
  public final static Font BODY_TEXT_BOLD_FONT = new Font(FontFactory.getFont("openSans", 11, Font.BOLD, bodyColor));

  @Inject
  public InstitutionalSummaryPdf(APConfig config, BasePdf basePdf, ActivityObjectiveManager activityObjectiveManager,
    CaseStudyTypeManager caseStudyTypeManager, DeliverableManager deliverableManager,
    ActivityCountryManager activityCountryManager, ActivityBenchmarkSiteManager activityBenchmarkSiteManager,
    ActivityRegionManager activityRegionManager, DeliverableMetadataManager deliverableMetadataManager) {
    this.basePdf = basePdf;
    this.config = config;
    textProvider = new DefaultTextProvider();
    this.activityObjectiveManager = activityObjectiveManager;
    this.caseStudyTypeManager = caseStudyTypeManager;
    this.deliverableManager = deliverableManager;
    this.activityCountryManager = activityCountryManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityRegionManager = activityRegionManager;
    this.deliverableMetadataManager = deliverableMetadataManager;
  }

  private void addActivities(Activity[] activities) throws DocumentException {
    if (activities.length != 0) {
      int c = 0;
      for (Activity activity : activities) {

        addActivityMainInformation(activity, (c == 0));
        addActivityObjectives(activity);
        addActivityDeliverables(activity);
        addActivityPartners(activity);
        addActivityLocations(activity);

        c++;
      }
    } else {
      try {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add("There is no activities \n");

        document.add(paragraph);
        document.add(Chunk.NEWLINE);
      } catch (DocumentException e) {
        LOG.error("-- generatePdf() > There was an error adding the activities content. ", e);
      }
    }
  }

  private void addActivityDeliverables(Activity activity) throws DocumentException {
    PdfPTable table = new PdfPTable(1);
    table.setWidthPercentage(100);
    PdfPCell cell = new PdfPCell();
    cell.setBorder(0);

    Paragraph paragraph = new Paragraph();
    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Deliverables: \n");
    paragraph.add(Chunk.NEWLINE);

    cell.addElement(Chunk.NEWLINE);
    cell.addElement(paragraph);


    PdfPTable deliverablesTable = new PdfPTable(5);
    deliverablesTable.setWidthPercentage(99);
    deliverablesTable.setWidths(new int[] {10, 4, 3, 4, 10});
    deliverablesTable.setKeepTogether(true);
    deliverablesTable.setHeaderRows(1);

    basePdf.addCustomTableCell(deliverablesTable, "Description", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
    basePdf.addCustomTableCell(deliverablesTable, "Type", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
    basePdf.addCustomTableCell(deliverablesTable, "Year", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
    basePdf.addCustomTableCell(deliverablesTable, "Status", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
    basePdf.addCustomTableCell(deliverablesTable, "Justification", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);

    for (Deliverable deliverable : activity.getDeliverables()) {
      basePdf.addTableBodyCell(deliverablesTable, deliverable.getDescription(), Element.ALIGN_LEFT, 1);
      basePdf.addTableBodyCell(deliverablesTable, deliverable.getType().getName(), Element.ALIGN_LEFT, 1);
      basePdf.addTableBodyCell(deliverablesTable, deliverable.getYear() + "", Element.ALIGN_CENTER, 1);
      basePdf.addTableBodyCell(deliverablesTable, deliverable.getStatus().getName(), Element.ALIGN_CENTER, 1);
      basePdf.addTableBodyCell(deliverablesTable, deliverable.getDescriptionUpdate(), Element.ALIGN_LEFT, 1);
    }

    cell.addElement(deliverablesTable);
    table.addCell(cell);
    document.add(table);
    document.add(Chunk.NEWLINE);
  }

  private void addActivityLocations(Activity activity) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    boolean hasLocations = false;

    paragraph.add(" \n");
    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Location(s): "); // Lista de Locations
    paragraph.add(Chunk.NEWLINE);
    paragraph.setFont(BODY_TEXT_FONT);

    if (activity.isGlobal()) {
      paragraph.add("\b Global \b");
      hasLocations = true;
    } else {
      activity.setCountries(activityCountryManager.getActvitiyCountries(activity.getId()));
      if (activity.getCountries() != null && activity.getCountries().size() != 0) {
        hasLocations = true;
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add("Countries: ");
        paragraph.setFont(BODY_TEXT_FONT);
        for (Country country : activity.getCountries()) {
          paragraph.add(country.getName() + ", ");
        }
        paragraph.add(Chunk.NEWLINE);
      }

      activity.setBsLocations(activityBenchmarkSiteManager.getActivityBenchmarkSites(activity.getId()));
      if (activity.getBsLocations() != null && !activity.getBsLocations().isEmpty()) {
        hasLocations = true;
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add("Benchmark Site: ");
        paragraph.setFont(BODY_TEXT_FONT);
        for (BenchmarkSite bMarkSite : activity.getBsLocations()) {
          paragraph.add(bMarkSite.getName() + ", ");
        }
        paragraph.add(Chunk.NEWLINE);
      }

      activity.setRegions(activityRegionManager.getActvitiyRegions(activity.getId()));
      if (activity.getRegions() != null && !activity.getRegions().isEmpty()) {
        hasLocations = true;
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add("\n Regions: ");
        paragraph.setFont(BODY_TEXT_FONT);
        for (Region region : activity.getRegions()) {
          paragraph.add(region.getName() + ", ");
        }
        paragraph.add(Chunk.NEWLINE);
      }
    }

    if (!hasLocations) {
      paragraph.add("Not defined");
    }

    paragraph.add("\n \n");

    document.add(paragraph);
  }

  private void addActivityMainInformation(Activity activity, boolean isFirstActivity) throws DocumentException {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM");
    // Will create a table cell to keep all the main information together.
    PdfPTable mainInformationTable = new PdfPTable(1);
    mainInformationTable.setWidthPercentage(100);
    mainInformationTable.setKeepTogether(true);

    PdfPCell cell = new PdfPCell();
    cell.setBorder(0);

    Paragraph paragraph = new Paragraph();

    if (isFirstActivity) {
      paragraph.setAlignment(Element.ALIGN_CENTER);
      paragraph.setFont(HEADING2_FONT);
      paragraph.add(getText("summaries.institutional.titleActivity"));
      paragraph.add(Chunk.NEWLINE);
      paragraph.add(Chunk.NEWLINE);
      paragraph.add(Chunk.NEWLINE);
    }

    // Title
    paragraph.setFont(HEADING3_FONT);
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.add("Activity " + activity.getActivityId());
    LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -7);
    line.setLineColor(Color.LIGHT_GRAY);
    paragraph.add(line);

    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(activity.getTitle());

    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    // document.add(paragraph);
    cell.addElement(paragraph);

    // Table
    PdfPTable table = new PdfPTable(4);

    // Set table widths
    table.setWidthPercentage(95);
    table.setWidths(new int[] {2, 5, 2, 5});

    String status = (activity.getStatus() != null) ? activity.getStatus().getName() : "Not defined";
    basePdf.addCustomTableCell(table, "Status", Element.ALIGN_LEFT, HEADING4_FONT, Color.white, 0);
    basePdf.addTableBodyCell(table, status, Element.ALIGN_LEFT, 1);
    basePdf.addCustomTableCell(table, "Milestone", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
    basePdf.addTableBodyCell(table, activity.getMilestone().getCode(), Element.ALIGN_LEFT, 1);

    String startDate = (activity.getStartDate() != null) ? dateFormat.format(activity.getStartDate()) : "Not defined";
    String endDate = (activity.getEndDate() != null) ? dateFormat.format(activity.getEndDate()) : "Not defined";
    basePdf.addCustomTableCell(table, "Start date", Element.ALIGN_LEFT, HEADING4_FONT, Color.white, 0);
    basePdf.addTableBodyCell(table, startDate, Element.ALIGN_LEFT, 1);
    basePdf.addCustomTableCell(table, "End date", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
    basePdf.addTableBodyCell(table, endDate, Element.ALIGN_LEFT, 1);

    cell.addElement(table);
    // document.add(table);

    // Description
    paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.add(Chunk.NEWLINE);
    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Description: ");
    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(activity.getDescription());
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    // Status description
    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Status: ");
    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(status + ". ");
    paragraph.add(activity.getStatusDescription());
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    // Gender component
    String genderDescription = activity.getGenderIntegrationsDescription();
    genderDescription = (genderDescription != null && !genderDescription.isEmpty()) ? genderDescription : "Not defined";
    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Gender Component: ");
    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(genderDescription);
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    // document.add(paragraph);
    cell.addElement(paragraph);
    mainInformationTable.addCell(cell);

    document.add(mainInformationTable);
  }

  private void addActivityObjectives(Activity activity) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.setKeepTogether(true);

    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Objectives: \n");
    document.add(paragraph);


    activity.setObjectives(activityObjectiveManager.getActivityObjectives(activity.getId()));

    if (activity.getObjectives() != null && !activity.getObjectives().isEmpty()) {
      com.lowagie.text.List list = new com.lowagie.text.List(true, 20);

      for (ActivityObjective activityObjective : activity.getObjectives()) {
        list.add(new ListItem(activityObjective.getDescription(), BODY_TEXT_FONT));
      }
      document.add(list);
    } else {
      paragraph = new Paragraph();
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add("Objectives not defined");

      document.add(paragraph);

    }

  }

  private void addActivityPartners(Activity activity) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

    paragraph.setFont(HEADING4_FONT);
    paragraph.add("Partners: ");
    paragraph.add(Chunk.NEWLINE);
    paragraph.setFont(BODY_TEXT_FONT);

    int c = 1;
    for (ActivityPartner activityPartner : activity.getActivityPartners()) {
      paragraph.add(c + "- ");
      paragraph.add(activityPartner.getPartner().getName());

      if (activityPartner.getPartner().getAcronym() != null && !activityPartner.getPartner().getAcronym().isEmpty()) {
        paragraph.add(" (");
        paragraph.add(activityPartner.getPartner().getAcronym());
        paragraph.add("): ");
      } else {
        paragraph.add(": ");
      }

      paragraph.add(Chunk.NEWLINE);
      paragraph.add("    ");
      paragraph.add(activityPartner.getContactName());
      if (activityPartner.getContactEmail() != null && !activityPartner.getContactEmail().isEmpty()) {
        paragraph.add(" <");
        paragraph.add(activityPartner.getContactEmail().toLowerCase());
        paragraph.add("> ");
      }

      paragraph.add(Chunk.NEWLINE);
      paragraph.add(Chunk.NEWLINE);
      c++;
    }

    if (c == 1) {
      paragraph.add("Partners not defined");
    }

    document.add(paragraph);
  }

  private void addCaseStudies(List<CaseStudy> caseStudies) throws DocumentException {
    Paragraph paragraph = new Paragraph();

    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("4. Case studies.");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    document.add(paragraph);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

    if (caseStudies.size() != 0) {
      int i = 1;
      for (CaseStudy caseStudy : caseStudies) {
        paragraph.clear();

        if (i > 1) {
          document.newPage();
        }
        Image casestudyImage = null;
        paragraph.setFont(HEADING3_FONT);
        paragraph.add("Case Study #" + i + "\n");

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Title: ");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(caseStudy.getTitle() + "\n");

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Author: ");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(caseStudy.getAuthor() + "\n");

        if (caseStudyTypeManager.getCaseStudyTypes(caseStudy).size() != 0) {
          paragraph.setFont(HEADING4_FONT);
          paragraph.add("Type: ");
          paragraph.setFont(BODY_TEXT_FONT);

          List<CaseStudyType> caseStudyTypes = caseStudyTypeManager.getCaseStudyTypes(caseStudy);
          for (CaseStudyType caseStudyType : caseStudyTypes) {
            paragraph.add(caseStudyType.getName() + "; ");
          }
        }
        paragraph.add("\n");
        paragraph.add(Chunk.NEWLINE);

        if (caseStudy.getImageFileName() != null) {
          try {
            String url =
              config.getCaseStudiesImagesUrl() + "/" + config.getReportingCurrentYear() + "/"
                + caseStudy.getLeader().getAcronym() + "/" + caseStudy.getImageFileName();

            casestudyImage = Image.getInstance(new URL(url.replace(" ", "%20")));

            casestudyImage.scaleToFit(400, 400);
            casestudyImage.setAlignment(Image.MIDDLE);

            paragraph.add(new Chunk(casestudyImage, 50, 0, true));
            paragraph.add(Chunk.NEWLINE);
            paragraph.add(Chunk.NEWLINE);
          } catch (BadElementException | IOException e) {
            LOG.error("-- CaseStudies -> There was an error loading the image '{}'", config.getCaseStudiesImagesUrl()
              + "/" + caseStudy.getImageFileName(), e);
          }
        }


        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Project Description: \n");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(caseStudy.getDescription() + "\n \n");

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Introduction / objectives: \n");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(caseStudy.getObjectives() + "\n \n");

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Project Results: \n");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(caseStudy.getResults() + "\n \n");

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Partners: \n");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(caseStudy.getPartners() + "\n \n");

        if (caseStudy.getLinks() != null) {
          paragraph.setFont(HEADING4_FONT);
          paragraph.add("Links / sources for further information: \n");
          paragraph.setFont(BODY_TEXT_FONT);
          paragraph.add(caseStudy.getLinks() + "\n \n");
        } else {
          paragraph.add("\n \n");
        }
        document.add(paragraph);
        i++;
      }
    } else {
      paragraph.add("There is no Case Studies \n");
      document.add(paragraph);
    }

  }

  private void addCommunications(Communication communications) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("3. Communications. ");
// paragraph.add("4. Communications. ");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    if (communications.getId() != -1) {
      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Media Campaigns: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getMediaCampaings()));
      paragraph.add(Chunk.NEWLINE);


      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Blogs: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getBlogs()));
      paragraph.add(Chunk.NEWLINE);

      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Websites: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getWebsites()));
      paragraph.add(Chunk.NEWLINE);

      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Social Media Campaigns: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getMediaCampaings()));
      paragraph.add(Chunk.NEWLINE);

      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Newsletters: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getNewsletters()));
      paragraph.add(Chunk.NEWLINE);

      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Events: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getEvents()));
      paragraph.add(Chunk.NEWLINE);

      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Videos and other Multimedia: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getVideosMultimedia()));
      paragraph.add(Chunk.NEWLINE);

      paragraph.setFont(HEADING4_FONT);
      paragraph.add("Other Communications and Outreach: \n");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(addLinksToText(communications.getOtherCommunications()));
      paragraph.add(Chunk.NEWLINE);
    } else {
      paragraph.add("There is no Communications \n");
    }
    document.add(paragraph);
  }

  private void addLeverages(List<Leverage> leverages) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setKeepTogether(true);

    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("8. Leveraged funds.");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    if (leverages.size() != 0) {
      int i = 1;
      for (Leverage leverage : leverages) {
        NumberFormat budgetformat = NumberFormat.getCurrencyInstance();
        String budget = budgetformat.format(leverage.getBudget());

        paragraph.setFont(HEADING3_FONT);
        paragraph.add("Leveraged funds #" + i + "\n");
        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Title: \n");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(leverage.getTitle() + "\n");
        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Partner Name: ");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(leverage.getPartnerName() + "\n");
        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Budget: ");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(budget + "\n");
        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Theme :");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(leverage.getTheme().getCode() + "\n \n");
        i++;
      }
    } else {
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add("There is no Leverage funds\n");
    }
    document.add(paragraph);

  }

  private Paragraph addLinksToText(String string) {
    Paragraph paragraph = new Paragraph();
    paragraph.setFont(BODY_TEXT_FONT);
    int startIndex = 0, spaceIndex = 0;

    if (string.contains("http")) {
      StringBuilder result = new StringBuilder(string);
      while (result.toString().contains("http")) {
        startIndex = result.indexOf("http");

        // Add the text before the link
        paragraph.add(result.substring(0, startIndex));
        result.delete(0, startIndex);

        // Look for the link end
        spaceIndex = result.indexOf(" ");
        // Check if the link is the last element in the string
        spaceIndex = (spaceIndex == -1) ? result.length() : spaceIndex;
        Anchor anchor = new Anchor(result.substring(0, spaceIndex), LINK_TEXT_FONT);
        anchor.setReference(result.substring(0, spaceIndex));
        paragraph.add(anchor);
        result.delete(0, spaceIndex);
      }

      // Add the remaining text after the last link
      paragraph.add(result.toString());
    } else {
      paragraph.add(string);
    }

    return paragraph;
  }

  private void addOutcomeIndicators(List<OutcomeIndicatorReport> outcomeIndicatorReport) throws DocumentException {
    Paragraph paragraph = new Paragraph();

    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("7. Outcome indicators.");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

    if (outcomeIndicatorReport.size() != 0) {
      String achievements, evidence;
      for (OutcomeIndicatorReport outcomeIndicator : outcomeIndicatorReport) {
        achievements = outcomeIndicator.getAchievements();
        achievements = (achievements != null && !achievements.isEmpty()) ? achievements : null;

        evidence = outcomeIndicator.getEvidence();
        evidence = (evidence != null && !evidence.isEmpty()) ? evidence : null;

        if (achievements != null || evidence != null) {
          paragraph.setFont(HEADING3_FONT);
          paragraph.add("Outcome Indicator: \n");
          paragraph.setFont(HEADING4_FONT);
          paragraph.add(outcomeIndicator.getOutcomeIndicator().getDescription() + "\n \n");

          paragraph.setFont(HEADING4_FONT);
          paragraph.add("Achievements:\n");
          paragraph.setFont(BODY_TEXT_FONT);
          if (achievements != null) {
            paragraph.add(outcomeIndicator.getAchievements() + "\n");
          } else {
            paragraph.add("Achievements not defined\n");
          }

          paragraph.setFont(HEADING4_FONT);
          paragraph.add("Evidence:\n");
          paragraph.setFont(BODY_TEXT_FONT);
          if (evidence != null) {
            paragraph.add(outcomeIndicator.getEvidence() + "\n \n");
          } else {
            paragraph.add("Evidence not defined \n \n");
          }
        }
      }
    } else {
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add("There is no Outcome Indicators \n");
    }

    document.add(paragraph);
  }

  private void addOutcomes(List<Outcome> outcomes) throws DocumentException {
    Paragraph paragraph = new Paragraph();

    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("5. Outcomes. ");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    document.add(paragraph);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

    paragraph.clear();
    if (outcomes.size() != 0) {
      int c = 1;
      for (Outcome outcome : outcomes) {
        paragraph.setFont(HEADING3_FONT);
        paragraph.add("Outcome #");
        paragraph.add(c + ":");
        paragraph.add(Chunk.NEWLINE);
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(outcome.getTitle() + " \n \n");
        paragraph.setFont(HEADING4_FONT);

        paragraph
          .add("What is the outcome of the research (i.e. use of research results by non-research partners)? \n");
        paragraph.setFont(BODY_TEXT_FONT);
        if (outcome.getOutcome() != null && !outcome.getOutcome().isEmpty()) {
          paragraph.add(outcome.getOutcome() + " \n \n");
        } else {
          paragraph.add(" \n");
        }

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("What outputs produced in the three preceding years resulted in this outcome? \n");
        paragraph.setFont(BODY_TEXT_FONT);
        if (outcome.getOutputs() != null && !outcome.getOutputs().isEmpty()) {
          paragraph.add(outcome.getOutputs() + " \n \n");
        } else {
          paragraph.add(" \n");
        }

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("What partners helped in producing the outcome? \n ");
        paragraph.setFont(BODY_TEXT_FONT);
        if (outcome.getPartners() != null && !outcome.getPartners().isEmpty()) {
          paragraph.add(outcome.getPartners() + " \n \n");
        } else {
          paragraph.add(" \n");
        }

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Who used the output? \n");
        paragraph.setFont(BODY_TEXT_FONT);
        if (outcome.getOutputUser() != null && !outcome.getOutputUser().isEmpty()) {
          paragraph.add(outcome.getOutputUser() + " \n\n");
        } else {
          paragraph.add(" \n");
        }

        paragraph.setFont(HEADING4_FONT);
        paragraph.add("How was the output used? \n");
        paragraph.setFont(BODY_TEXT_FONT);
        if (outcome.getHowUsed() != null && !outcome.getHowUsed().isEmpty()) {
          paragraph.add(outcome.getHowUsed() + " \n\n");
        } else {
          paragraph.add(" \n");
        }

        paragraph.setFont(HEADING4_FONT);
        paragraph
          .add("What is the evidence for this outcome? Specifically, what kind of study was conducted to show the connection between the research and the outcome? Who conducted it? \n");
        paragraph.setFont(BODY_TEXT_FONT);
        if (outcome.getEvidence() != null && !outcome.getEvidence().isEmpty()) {
          paragraph.add(outcome.getEvidence() + " \n\n");
        } else {
          paragraph.add(" \n");
        }

        document.add(paragraph);
        document.add(Chunk.NEWLINE);
        document.add(Chunk.NEWLINE);
        document.newPage();
        paragraph.clear();
        c++;
      }
    } else {
      paragraph.add("There is no Outcomes \n");
    }

  }

  private void addPublications(List<Publication> publications) throws DocumentException {
    Paragraph paragraph = new Paragraph();

    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("9. Publications.");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    document.add(paragraph);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.clear();
    if (publications.size() != 0) {
      int i = 1;
      Deliverable deliverable;
      for (Publication publication : publications) {
        if (deliverableManager.getDeliverableLeader(publication.getDeliverableID()).getId() != 0) {
          continue;
        }

        deliverable = deliverableManager.getDeliverable(publication.getDeliverableID());
        deliverable.setMetadata(deliverableMetadataManager.getDeliverableMetadata(deliverable.getId()));

        paragraph.setFont(HEADING3_FONT);
        paragraph.add("Publication #" + i + ": \n");

        // Insert title
        String title = deliverable.getMetadataValue("Title");
        paragraph.setFont(BODY_TEXT_FONT);
        if (!title.isEmpty()) {
          paragraph.add(title + "\n");
        } else {
          paragraph.add("This publication has not a title defined.\n");
        }
        paragraph.add(Chunk.NEWLINE);

        // Citation
        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Citation:");
        paragraph.add(Chunk.NEWLINE);
        paragraph.setFont(BODY_TEXT_FONT);
        if (!publication.getCitation().isEmpty()) {
          paragraph.add(publication.getCitation() + "");
        } else {
          paragraph.add("The citation is not defined yet.");
        }

        // Get the table content
        String themesRelated = "";
        if (publication.getRelatedThemes().length > 0) {
          for (PublicationTheme theme : publication.getRelatedThemes()) {
            themesRelated = "Theme " + theme.getCode() + ", ";
          }
        } else {
          themesRelated = "Not defined";
        }

        document.add(paragraph);
        document.add(Chunk.NEWLINE);

        PdfPTable deliverablesTable = new PdfPTable(4);
        deliverablesTable.setWidthPercentage(95);
        deliverablesTable.setWidths(new int[] {10, 7, 12, 5});
        deliverablesTable.setKeepTogether(true);

        // Table headers
        basePdf
          .addCustomTableCell(deliverablesTable, "Identifier", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
        basePdf.addCustomTableCell(deliverablesTable, "CCAFS Themes", Element.ALIGN_CENTER, HEADING4_FONT, Color.white,
          0);
        basePdf.addCustomTableCell(deliverablesTable, "Type", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);
        basePdf.addCustomTableCell(deliverablesTable, "Access", Element.ALIGN_CENTER, HEADING4_FONT, Color.white, 0);

        // Table values
        basePdf.addTableBodyCell(deliverablesTable, deliverable.getMetadataValue("Identifier"), Element.ALIGN_LEFT, 1);
        basePdf.addTableBodyCell(deliverablesTable, themesRelated, Element.ALIGN_LEFT, 1);
        basePdf.addTableBodyCell(deliverablesTable, deliverable.getType().getName(), Element.ALIGN_CENTER, 1);
        basePdf.addTableBodyCell(deliverablesTable, publication.getAccess().getName(), Element.ALIGN_CENTER, 1);
        document.add(deliverablesTable);

        paragraph.clear();
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);
        paragraph.clear();

// if (publication.getAccess().getName() != null) {
// paragraph.setFont(HEADING4_FONT);
// paragraph.add("Access:");
// paragraph.setFont(BODY_TEXT_FONT);
// paragraph.add(publication.getAccess().getName() + "\n");
// }
//
// if (publication.getPublicationType() != null && publication.getDeliverableID() != 0) {
// paragraph.setFont(HEADING4_FONT);
// paragraph.add("Type: ");
//
// PublicationType publicationType = publicationTypeManager.getPublicationType(publication.getIdentifier());
// paragraph.setFont(BODY_TEXT_FONT);
// paragraph.add(deliverable.getType().getName() + "\n");
// }
//
//
// // identifier
// paragraph.setFont(HEADING4_FONT);
// paragraph.add("Identifier: ");
// paragraph.setFont(BODY_TEXT_FONT);
// String identifier = deliverable.getMetadataValue("Identifier");
// if (!identifier.isEmpty()) {
// paragraph.add(identifier + "\n");
// } else {
// paragraph.add("\n");
// }
//
// // status
// // publicationTitle.setFont(HEADING4_FONT);
// // publicationTitle.add("Description for dissemination: ");
// // publicationTitle.setFont(BODY_TEXT_FONT);
// // if (publication.getDisseminationDescription() != null) {
// // publicationTitle.add(publication.getDisseminationDescription() + "\n");
// // } else {
// // publicationTitle.add("\n");
// // }
//
//
// paragraph.setFont(HEADING4_FONT);
// paragraph.add("CCAFS Themes: ");
// paragraph.setFont(BODY_TEXT_FONT);
// paragraph.add(themesRelated + "\n");

// document.add(paragraph);
        paragraph.clear();
        i++;
      }
    } else {
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add("There is no publication. \n");
      document.add(paragraph);
    }

  }

  private void addSummaryByOutputs(OutputSummary[] outputSummaries) throws DocumentException {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("2. Succinct summary of activities and deliverables by Output level.");
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    if (outputSummaries.length != 0) {
      for (OutputSummary outputSummary : outputSummaries) {
        paragraph.setFont(HEADING3_FONT);
        paragraph.add("Output: " + outputSummary.getOutput().getObjective().getTheme().getCode() + "."
          + outputSummary.getOutput().getObjective().getCode() + "." + outputSummary.getOutput().getCode() + " \n");
        paragraph.setFont(HEADING4_FONT);
        paragraph.add("Summary: ");
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(outputSummary.getDescription() + "\n \n");
      }
    } else {
      paragraph.add("There is no Output Summaries \n");
    }

    document.add(paragraph);
    document.add(Chunk.NEWLINE);
  }


  public void generatePdf(Activity[] activities, OutputSummary[] outputSummaries, List<Publication> publications,
    Communication communications, List<CaseStudy> caseStudies, List<Outcome> outcomes,
    List<OutcomeIndicatorReport> outcomeIndicatorReport, List<Leverage> leverages) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    document = new Document(PageSize.A4, LEFT_MARGIN, RIGHT_MARGIN, TOP_MARGIN, BOTTOM_MARGIN);

    //
    Paragraph titles = new Paragraph();
    titles.setAlignment(Element.ALIGN_CENTER);
    titles.setFont(HEADING2_FONT);

    Paragraph outputSummaryTitle = new Paragraph();
    outputSummaryTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph publicationTitle = new Paragraph();
    publicationTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph communicationTitle = new Paragraph();
    communicationTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph caseStudyTitle = new Paragraph();
    caseStudyTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph outcomeTitle = new Paragraph();
    outcomeTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph outcomeIndicatorReportTitle = new Paragraph();
    outcomeIndicatorReportTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph leverageTitle = new Paragraph();
    leverageTitle.setAlignment(Element.ALIGN_JUSTIFIED);

    PdfWriter writer = basePdf.initializePdf(document, outputStream, BasePdf.PORTRAIT);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(summaryTitle, BasePdf.PORTRAIT);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Cover page
    // basePdf.addCover(document, summaryTitle);

    try {
      addActivities(activities);
// document.newPage();
// addSummaryByOutputs(outputSummaries);
// document.newPage();
// addCommunications(communications);
// document.newPage();
// addCaseStudies(caseStudies);
// document.newPage();
// addOutcomes(outcomes);
// document.newPage();
// addOutcomeIndicators(outcomeIndicatorReport);
// document.newPage();
// addLeverages(leverages);
// document.newPage();
// addPublications(publications);
// document.newPage();

    } catch (DocumentException e2) {
      // TODO Auto-generated catch block
      e2.printStackTrace();
    }
    titles.clear();


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

  protected String getText(String key) {
    return textProvider.getText(key);
  }


  protected String getText(String key, String[] args) {
    return textProvider.getText(key, args);
  }

  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
