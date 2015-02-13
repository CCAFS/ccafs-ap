package org.cgiar.ccafs.ap.action.summaries.pdf;

import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityBenchmarkSiteManager;
import org.cgiar.ccafs.ap.data.manager.ActivityCountryManager;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityObjectiveManager;
import org.cgiar.ccafs.ap.data.manager.ActivityRegionManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.PublicationTypeManager;
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
import org.cgiar.ccafs.ap.data.model.PublicationType;
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
import com.lowagie.text.pdf.PdfWriter;
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

  // Config
  private APConfig config;

  // Manager for specific sections - to avoid this, it will have to do some new report function on the manager
  private ActivityObjectiveManager activityObjectiveManager;
  private CaseStudyTypeManager caseStudyTypeManager;
  private PublicationTypeManager publicationTypeManager;
  private DeliverableManager deliverableManager;
  private ActivityCountryManager activityCountryManager;
  private ActivityBenchmarkSiteManager activityBenchmarkSiteManager;
  private ActivityRegionManager activityRegionManager;
  private ActivityManager activityManager;

  // Text provider to read the internationalization file
  private TextProvider textProvider;

  // Colors
  public final static Color titleColor = new Color(102, 55, 0);
  public final static Color subTitleColor = new Color(118, 146, 60);
  public final static Color bodyColor = new Color(34, 34, 34);

  // Fonts
  public final static Font HEADING1_FONT = new Font(FontFactory.getFont("openSans", 24, Font.BOLD, titleColor));
  public final static Font HEADING2_FONT = new Font(FontFactory.getFont("openSans", 16, Font.BOLD, titleColor));
  public final static Font HEADING3_FONT = new Font(FontFactory.getFont("openSans", 14, Font.BOLD, subTitleColor));
  public final static Font HEADING4_FONT = new Font(FontFactory.getFont("openSans", 12, Font.NORMAL, subTitleColor));

  public final static Font BODY_TEXT_FONT = new Font(FontFactory.getFont("openSans", 12, bodyColor));
  public final static Font BODY_TEXT_BOLD_FONT = new Font(FontFactory.getFont("openSans", 12, Font.BOLD, bodyColor));

  @Inject
  public InstitutionalSummaryPdf(APConfig config, BasePdf basePdf, ActivityObjectiveManager activityObjectiveManager,
    CaseStudyTypeManager caseStudyTypeManager, PublicationTypeManager publicationTypeManager,
    DeliverableManager deliverableManager, ActivityCountryManager activityCountryManager,
    ActivityBenchmarkSiteManager activityBenchmarkSiteManager, ActivityRegionManager activityRegionManager,
    ActivityManager activityManager) {
    this.basePdf = basePdf;
    this.config = config;
    textProvider = new DefaultTextProvider();
    this.activityObjectiveManager = activityObjectiveManager;
    this.caseStudyTypeManager = caseStudyTypeManager;
    this.publicationTypeManager = publicationTypeManager;
    this.deliverableManager = deliverableManager;
    this.activityCountryManager = activityCountryManager;
    this.activityBenchmarkSiteManager = activityBenchmarkSiteManager;
    this.activityRegionManager = activityRegionManager;
    this.activityManager = activityManager;
  }

  public void generatePdf(Activity[] activities, OutputSummary[] outputSummaries, List<Publication> publications,
    Communication communications, List<CaseStudy> caseStudies, List<Outcome> outcomes,
    List<OutcomeIndicatorReport> outcomeIndicatorReport, List<Leverage> leverages) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    Document document = new Document(PageSize.A4);
    //
    Paragraph titles = new Paragraph();
    titles.setAlignment(Element.ALIGN_CENTER);
    titles.setFont(HEADING2_FONT);
    Paragraph activityTitle = new Paragraph();
    activityTitle.setAlignment(Element.ALIGN_JUSTIFIED);
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
    //

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy MMM");

    PdfWriter writer = basePdf.initializePdf(document, outputStream, basePdf.PORTRAIT);

    // Adding the event to include header and footer on each page
    HeaderFooterPdf event = new HeaderFooterPdf(summaryTitle, basePdf.PORTRAIT);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Cover page
    basePdf.addCover(document, summaryTitle);

    // Summary title
    // basePdf.addTitle(document, summaryTitle);

    // Add content
    // //////////////////////////////////Activity Reporting
    titles.add(getText("summaries.institutional.titleActivity") + "  \n \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (activities.length != 0) {
      for (Activity activity : activities) {
        activityTitle.setFont(HEADING3_FONT);
        activityTitle.add("Activity " + activity.getActivityId() + " (Milestone " + activity.getMilestone().getCode()
          + ")\n");

        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Title: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        activityTitle.add(activity.getTitle() + " \n");
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Start Date: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        if (activity.getStartDate() != null) {
          activityTitle.add(dateFormat.format(activity.getStartDate()) + ". \n");
        } else {
          activityTitle.add(" \n");
        }
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("End Date: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        if (activity.getEndDate() != null) {
          activityTitle.add(dateFormat.format(activity.getEndDate()) + ". \n");
        } else {
          activityTitle.add(" \n");
        }
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Description: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        activityTitle.add(activity.getDescription() + " \n \n");
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Objectives: \n");
        activityTitle.setFont(BODY_TEXT_FONT);
        activity.setObjectives(activityObjectiveManager.getActivityObjectives(activity.getId()));
        if (activity.getObjectives() != null && activity.getObjectives().size() != 0) {
          for (ActivityObjective activityObjective : activity.getObjectives()) {
            activityTitle.add(" - " + activityObjective.getDescription() + " \n");
          }
        }
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Status: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        activityTitle.add(activity.getStatus().getName() + " - " + activity.getStatusDescription() + " \n");
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Theme: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        activityTitle.add(activity.getMilestone().getOutput().getObjective().getTheme().getCode() + " \n");
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Gender Component: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        if (activity.getGenderIntegrationsDescription() != null
          && !activity.getGenderIntegrationsDescription().isEmpty()) {
          activityTitle.add(activity.getGenderIntegrationsDescription() + " \n");
        } else {
          activityTitle.add(" \n");
        }
        int i = 1;
        for (Deliverable deliverable : activity.getDeliverables()) {
          activityTitle.setFont(HEADING4_FONT);
          activityTitle.add("Deliverable " + i + ": ");
          activityTitle.setFont(BODY_TEXT_FONT);
          activityTitle.add(deliverable.getDescription() + " \n");
          activityTitle.setFont(HEADING4_FONT);
          activityTitle.add("Type: ");
          activityTitle.setFont(BODY_TEXT_FONT);
          activityTitle.add(deliverable.getType().getName() + " \n");
          activityTitle.setFont(HEADING4_FONT);
          activityTitle.add("Year: ");
          activityTitle.setFont(BODY_TEXT_FONT);
          activityTitle.add(deliverable.getYear() + " \n \n");
          i++;
        }
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Partners: ");
        activityTitle.setFont(BODY_TEXT_FONT);
        for (ActivityPartner activityPartner : activity.getActivityPartners()) {
          activityTitle.add(activityPartner.getPartner().getAcronym() + " - " + activityPartner.getContactName() + ", "
            + activityPartner.getContactEmail() + "; ");
        }
        activityTitle.add(" \n");
        activityTitle.setFont(HEADING4_FONT);
        activityTitle.add("Location: "); // Lista de Locations
        activityTitle.setFont(BODY_TEXT_FONT);
        // Activity activ = activityManager.getActivity(activity.getId()); //TODO evaluate this function throws null in
// haspartners
        if (activity.isGlobal() == true) {
          activityTitle.add("\b Global \b");
        } else {
          List<Country> activityCountries = activityCountryManager.getActvitiyCountries(activity.getId());
          if (activity.getCountries() != null && activity.getCountries().size() != 0) {
            activityTitle.add("Countries: ");
            for (Country country : activityCountries) {
              activityTitle.add(country.getName() + ", ");
            }
            activityTitle.add("\n ");
          }
          List<BenchmarkSite> activityBenchMark =
            activityBenchmarkSiteManager.getActivityBenchmarkSites(activity.getId());

          if (activityBenchMark != null && activityBenchMark.size() != 0) {
            activityTitle.add("\n Benchmark Site: ");
            for (BenchmarkSite bMarkSite : activityBenchMark) {
              activityTitle.add(bMarkSite.getName() + ", ");
            }
            activityTitle.add("\n ");
          }
          List<Region> activityRegions = activityRegionManager.getActvitiyRegions(activity.getId());
          if (activityRegions != null && activityRegions.size() != 0) {
            activityTitle.add("\n Regions: ");
            for (Region region : activityRegions) {
              activityTitle.add(region.getName() + ", ");
            }
            activityTitle.add("\n ");
          }
        }
        activityTitle.add("\n \n");
      }
    } else {
      activityTitle.add("There is no activities \n");
    }
    try {
      document.add(activityTitle);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the activities content. ", e);
    }


    // ////////////////////////////////// Summary by Output
    document.newPage();
    titles.add("\n 2. Succinct summary of activities and deliverables by Output level. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (outputSummaries.length != 0) {
      for (OutputSummary outputSummary : outputSummaries) {
        outputSummaryTitle.setFont(HEADING3_FONT);
        outputSummaryTitle.add("Output: " + outputSummary.getOutput().getObjective().getTheme().getCode() + "."
          + outputSummary.getOutput().getObjective().getCode() + "." + outputSummary.getOutput().getCode() + " \n");
        outputSummaryTitle.setFont(HEADING4_FONT);
        outputSummaryTitle.add("Summary: ");
        outputSummaryTitle.setFont(BODY_TEXT_FONT);
        outputSummaryTitle.add(outputSummary.getDescription() + "\n \n");
      }
    } else {
      outputSummaryTitle.add("There is no Output Summaries \n");
    }
    try {
      document.add(outputSummaryTitle);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the Output Summary Content. ", e);
    }


    // ////////////////////////////////// Publications
    document.newPage();
    titles.add("\n 3. Publications. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (publications.size() != 0) {
      int i = 1;
      for (Publication publication : publications) {

        publicationTitle.setFont(HEADING3_FONT);
        publicationTitle.add("Publication #" + i + ": \n");
        if (publication.getPublicationType() != null && publication.getDeliverableID() != 0) {
          publicationTitle.setFont(HEADING4_FONT);
          publicationTitle.add("Type: ");

          if (publication.getPublicationType() != null) {
            PublicationType publicationType = publicationTypeManager.getPublicationType(publication.getIdentifier());
            publicationTitle.setFont(BODY_TEXT_FONT);
            publicationTitle.add(publicationType + "\n");
          } else if (publication.getDeliverableID() != 0) {
            Deliverable deliverable = deliverableManager.getDeliverable(publication.getDeliverableID());
            publicationTitle.setFont(BODY_TEXT_FONT);
            publicationTitle.add(deliverable.getType().getName() + "\n");
          }
        }// TODO Doesn't work .getName()validar si viene null consultar del deliverabletype
         // description
// publicationTitle.setFont(HEADING4_FONT);
// publicationTitle.add("Description: \n");
// publicationTitle.setFont(BODY_TEXT_FONT);
// if (publication.getDescription() != null) {
// publicationTitle.add(publication.getDescription() + "\n");
// }
        // identifier
        publicationTitle.setFont(HEADING4_FONT);
        publicationTitle.add("Identifier: ");
        publicationTitle.setFont(BODY_TEXT_FONT);
        if (publication.getIdentifier() != null) {
          publicationTitle.add(publication.getIdentifier() + "\n");
        } else {
          publicationTitle.add("\n");
        }
        // status
// publicationTitle.setFont(HEADING4_FONT);
// publicationTitle.add("Description for dissemination: ");
// publicationTitle.setFont(BODY_TEXT_FONT);
// if (publication.getDisseminationDescription() != null) {
// publicationTitle.add(publication.getDisseminationDescription() + "\n");
// } else {
// publicationTitle.add("\n");
// }
        // CCAFS Themes
        publicationTitle.setFont(HEADING4_FONT);
        publicationTitle.add("CCAFS Themes:");
        publicationTitle.setFont(BODY_TEXT_FONT);
        for (PublicationTheme theme : publication.getRelatedThemes()) {
          publicationTitle.add(theme.getCode() + "; ");
        }
        publicationTitle.add("\n");
        publicationTitle.setFont(HEADING4_FONT);
        publicationTitle.add("Citation:");
        publicationTitle.setFont(BODY_TEXT_FONT);
        publicationTitle.add(publication.getCitation() + "\n");
        if (publication.getAccess().getName() != null) {
          publicationTitle.setFont(HEADING4_FONT);
          publicationTitle.add("Access:");
          publicationTitle.setFont(BODY_TEXT_FONT);
          publicationTitle.add(publication.getAccess().getName() + "\n");
        }
        publicationTitle.add(" \n");
        publicationTitle.setFont(HEADING4_FONT);
        if (publication.getAccessDetails() != null) {
          publicationTitle.add("Access Restriction:");
          publicationTitle.setFont(BODY_TEXT_FONT);
          publicationTitle.add(publication.getAccessDetails().getAccessRestrictions() + "\n");
          publicationTitle.setFont(HEADING4_FONT);
          publicationTitle.add("Access Limits:");
          publicationTitle.setFont(BODY_TEXT_FONT);
          publicationTitle.add(publication.getAccessDetails().getAccessLimits() + "\n");
          publicationTitle.setFont(HEADING4_FONT);
          publicationTitle.add("Access Limits:");
          publicationTitle.setFont(BODY_TEXT_FONT);
          publicationTitle.add(publication.getAccessDetails().getAccessLimits() + "\n");
        }
        i++;
      }
    } else {
      publicationTitle.add("There is no publication \n");
    }
    try {
      document.add(publicationTitle);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the publications Content. ", e);
    }


    // ////////////////////////////////// Communications
    document.newPage();
    titles.add("\n 4.  Communications. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (communications.getId() != -1) {
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Media Campaigns: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getMediaCampaings() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Blogs: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getBlogs() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Websites: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getWebsites() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Social Media Campaigns: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getSociaMediaCampaigns() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Newsletters: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getNewsletters() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Events: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getEvents() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Videos and other Multimedia: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getVideosMultimedia() + "\n \n");
      communicationTitle.setFont(HEADING4_FONT);
      communicationTitle.add("Other Communications and Outreach: \n");
      communicationTitle.setFont(BODY_TEXT_FONT);
      communicationTitle.add(communications.getOtherCommunications() + "\n \n");
    } else {
      communicationTitle.add("There is no Communications \n");
    }
    try {
      document.add(communicationTitle);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the communication Content. ", e);
    }
    // ////////////////////////////////// Case Studies
    document.newPage();
    titles.add("\n 5. Case studies. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (caseStudies.size() != 0) {
      int i = 1;
      for (CaseStudy caseStudy : caseStudies) {
        Image casestudyImage = null;
        caseStudyTitle.setFont(HEADING3_FONT);
        caseStudyTitle.add("Case Study #" + i + "\n");
        caseStudyTitle.setFont(HEADING4_FONT);
        caseStudyTitle.add("Title: ");
        caseStudyTitle.setFont(BODY_TEXT_FONT);
        caseStudyTitle.add(caseStudy.getTitle() + "\n");
        // TODO to resolve the image print
        System.out.println(caseStudy.getId() + " - " + config.getCaseStudiesImagesPath() + "/"
          + caseStudy.getImageFileName());
        if (caseStudy.getImageFileName() != null) {

          try {
            casestudyImage =
              Image.getInstance(new URL(config.getCaseStudiesImagesPath() + "/" + caseStudy.getImageFileName()));
          } catch (BadElementException | IOException e) {
            LOG.error("-- CaseStudies -> There was an error loading the image '{}'", config.getCaseStudiesImagesPath()
              + "/" + caseStudy.getImageFileName(), e);
          }
          caseStudyTitle.add(casestudyImage + "\n \n");
        }
        caseStudyTitle.setFont(HEADING4_FONT);
        caseStudyTitle.add("Author: ");
        caseStudyTitle.setFont(BODY_TEXT_FONT);
        caseStudyTitle.add(caseStudy.getAuthor() + "\n");
        if (caseStudyTypeManager.getCaseStudyTypes(caseStudy).size() != 0) {
          caseStudyTitle.setFont(HEADING4_FONT);
          caseStudyTitle.add("Type: ");
          caseStudyTitle.setFont(BODY_TEXT_FONT);
          // TODO validar por que no está trayendo info
          List<CaseStudyType> caseStudyTypes = caseStudyTypeManager.getCaseStudyTypes(caseStudy);
          for (CaseStudyType caseStudyType : caseStudyTypes) {
            caseStudyTitle.add(caseStudyType.getName() + "; ");
          }
        }
        caseStudyTitle.add("\n");
        caseStudyTitle.setFont(HEADING4_FONT);
        caseStudyTitle.add("Project Description: \n");
        caseStudyTitle.setFont(BODY_TEXT_FONT);
        caseStudyTitle.add(caseStudy.getDescription() + "\n \n");
        caseStudyTitle.setFont(HEADING4_FONT);
        caseStudyTitle.add("Introduction / objectives: \n");
        caseStudyTitle.setFont(BODY_TEXT_FONT);
        caseStudyTitle.add(caseStudy.getObjectives() + "\n \n");
        caseStudyTitle.setFont(HEADING4_FONT);
        caseStudyTitle.add("Project Results: \n");
        caseStudyTitle.setFont(BODY_TEXT_FONT);
        caseStudyTitle.add(caseStudy.getResults() + "\n \n");
        caseStudyTitle.setFont(HEADING4_FONT);
        caseStudyTitle.add("Partners: \n");
        caseStudyTitle.setFont(BODY_TEXT_FONT);
        caseStudyTitle.add(caseStudy.getPartners() + "\n \n");
        if (caseStudy.getLinks() != null) {
          caseStudyTitle.setFont(HEADING4_FONT);
          caseStudyTitle.add("Links / sources for further information: \n");
          caseStudyTitle.setFont(BODY_TEXT_FONT);
          caseStudyTitle.add(caseStudy.getLinks() + "\n \n");
        } else {
          caseStudyTitle.add("\n \n");
        }
        i++;
      }
    } else {
      caseStudyTitle.add("There is no Case Studies \n");
    }
    try {
      document.add(caseStudyTitle);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the Case Studies  Content. ", e);
    }


    // ////////////////////////////////// Outcomes
    document.newPage();
    titles.add("\n 6. Outcomes. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (outcomes.size() != 0) {
      for (Outcome outcome : outcomes) {
        outcomeTitle.setFont(HEADING3_FONT);
        outcomeTitle.add("Title: \n");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        outcomeTitle.add(outcome.getTitle() + " \n \n");
        outcomeTitle.setFont(HEADING4_FONT);
        outcomeTitle
          .add("What is the outcome of the research (i.e. use of research results by non-research partners)? \n");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        if (outcome.getOutcome() != null && !outcome.getOutcome().isEmpty()) {
          outcomeTitle.add(outcome.getOutcome() + " \n \n");
        } else {
          outcomeTitle.add(" \n \n");
        }
        outcomeTitle.setFont(HEADING4_FONT);
        outcomeTitle.add("\n What outputs produced in the three preceding years resulted in this outcome? \n");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        if (outcome.getOutputs() != null && !outcome.getOutputs().isEmpty()) {
          outcomeTitle.add(outcome.getOutputs() + " \n \n");
        } else {
          outcomeTitle.add(" \n \n");
        }
        outcomeTitle.setFont(HEADING4_FONT);
        outcomeTitle.add("\n What partners helped in producing the outcome? \n ");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        if (outcome.getPartners() != null && !outcome.getPartners().isEmpty()) {
          outcomeTitle.add(outcome.getPartners() + " \n \n");
        } else {
          outcomeTitle.add(" \n \n");
        }
        outcomeTitle.setFont(HEADING4_FONT);
        outcomeTitle.add("\n Who used the output? \n");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        if (outcome.getOutputUser() != null && !outcome.getOutputUser().isEmpty()) {
          outcomeTitle.add(outcome.getOutputUser() + " \n");
        } else {
          outcomeTitle.add(" \n \n");
        }
        outcomeTitle.setFont(HEADING4_FONT);
        outcomeTitle.add("\n How was the output used? \n");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        if (outcome.getHowUsed() != null && !outcome.getHowUsed().isEmpty()) {
          outcomeTitle.add(outcome.getHowUsed() + " \n");
        } else {
          outcomeTitle.add(" \n \n");
        }
        outcomeTitle.setFont(HEADING4_FONT);
        outcomeTitle
          .add("\n What is the evidence for this outcome? Specifically, what kind of study was conducted to show the connection between the research and the outcome? Who conducted it? \n");
        outcomeTitle.setFont(BODY_TEXT_FONT);
        if (outcome.getEvidence() != null && !outcome.getEvidence().isEmpty()) {
          outcomeTitle.add(outcome.getEvidence() + " \n");
        } else {
          outcomeTitle.add(" \n \n");
        }
      }
    } else {
      outcomeTitle.add("There is no Outcomes \n");
    }
    try {
      document.add(outcomeTitle);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the Outcome  Content. ", e);
    }


    // ////////////////////////////////// Outcome Indicators
    document.newPage();
    titles.add("\n 7. Outcome indicators. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (outcomeIndicatorReport.size() != 0) {
      for (OutcomeIndicatorReport outcomeIndicator : outcomeIndicatorReport) {
        if ((outcomeIndicator.getAchievements() != null && !outcomeIndicator.getAchievements().isEmpty())
          && (outcomeIndicator.getEvidence() != null && !outcomeIndicator.getEvidence().isEmpty())) {
          outcomeIndicatorReportTitle.setFont(HEADING3_FONT);
          outcomeIndicatorReportTitle.add("Outcome Indicator: \n");
          outcomeIndicatorReportTitle.setFont(HEADING4_FONT);
          outcomeIndicatorReportTitle.add(outcomeIndicator.getOutcomeIndicator().getDescription() + "\n \n");
          outcomeIndicatorReportTitle.setFont(HEADING4_FONT);
          outcomeIndicatorReportTitle.add("Achievements:\n");
          outcomeIndicatorReportTitle.setFont(BODY_TEXT_FONT);

          outcomeIndicatorReportTitle.add(outcomeIndicator.getAchievements() + "\n");

          outcomeIndicatorReportTitle.setFont(HEADING4_FONT);
          outcomeIndicatorReportTitle.add("Evidence:\n");
          outcomeIndicatorReportTitle.setFont(BODY_TEXT_FONT);
          outcomeIndicatorReportTitle.add(outcomeIndicator.getEvidence() + "\n \n");
        }

      }
    } else {
      outcomeIndicatorReportTitle.add("There is no Outcome Indicators \n");
    }

    try {
      document.add(outcomeIndicatorReportTitle);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the Outcome Indicator Content. ", e);
    }


    // ////////////////////////////////// Leveraged Funds
    document.newPage();
    titles.add("\n 8. Leveraged funds. \n \n");
    try {
      document.add(titles);
    } catch (DocumentException e1) {
      e1.printStackTrace();
    }
    titles.clear();
    if (leverages.size() != 0) {
      int i = 1;
      for (Leverage leverage : leverages) {
        NumberFormat budgetformat = NumberFormat.getCurrencyInstance();
        String budget = budgetformat.format(leverage.getBudget());
        leverageTitle.setFont(HEADING3_FONT);
        leverageTitle.add("Leveraged funds #" + i + "\n");
        leverageTitle.setFont(HEADING4_FONT);
        leverageTitle.add("Title: \n");
        leverageTitle.setFont(BODY_TEXT_FONT);
        leverageTitle.add(leverage.getTitle() + "\n");
        leverageTitle.setFont(HEADING4_FONT);
        leverageTitle.add("Partner Name: ");
        leverageTitle.setFont(BODY_TEXT_FONT);
        leverageTitle.add(leverage.getPartnerName() + "\n");
        leverageTitle.setFont(HEADING4_FONT);
        leverageTitle.add("Budget: ");
        leverageTitle.setFont(BODY_TEXT_FONT);
        leverageTitle.add(budget + "\n");
        leverageTitle.setFont(HEADING4_FONT);
        leverageTitle.add("Theme :");
        leverageTitle.setFont(BODY_TEXT_FONT);
        leverageTitle.add(leverage.getTheme().getCode() + "\n \n");
        i++;
      }
    } else {
      leverageTitle.add("There is no Leverage \n");
    }
    try {
      document.add(leverageTitle);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the Leverages Content. ", e);
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
