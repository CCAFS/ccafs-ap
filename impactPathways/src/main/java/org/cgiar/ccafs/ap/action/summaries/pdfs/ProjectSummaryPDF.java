/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform.
 * CCAFS P&R is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * CCAFS P&R is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.ap.action.summaries.pdfs;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.ClimateSmartVillage;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;


/**
 * @author Hernán David Carvajal
 */

public class ProjectSummaryPDF extends BasePDF {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ProjectSummaryPDF.class);

  private BudgetManager budgetManager;
  private IPElementManager elementManager;
  private ActivityManager activityManager;
  private IPCrossCuttingManager ipCrossCuttingManager;
  private ActivityPartnerManager activityPartnerManager;
  private LocationManager locationManager;
  private DeliverableManager deliverableManager;
  private NextUserManager nextUserManager;


  // Attributes
  private String summaryTitle;
  private InputStream inputStream;
  private int contentLength;

  private Document document;
  private Project project;
  int currentPlanningYear;
  int midOutcomeYear;

  // Budget
  @Inject
  public ProjectSummaryPDF(APConfig config, BudgetManager budgetManager, IPElementManager elementManager,
    ActivityManager activityManager, IPCrossCuttingManager ipCrossCuttingManager,
    ActivityPartnerManager activityPartnerManager, LocationManager locationManager,
    DeliverableManager deliverableManager, NextUserManager nextUserManager) {
    this.initialize(config.getBaseUrl());
    this.budgetManager = budgetManager;
    this.elementManager = elementManager;
    this.activityManager = activityManager;
    this.ipCrossCuttingManager = ipCrossCuttingManager;
    this.activityPartnerManager = activityPartnerManager;
    this.locationManager = locationManager;
    this.deliverableManager = deliverableManager;
    this.nextUserManager = nextUserManager;
  }

  private void addActivities() {
    Paragraph paragraph = new Paragraph(this.getText("summaries.project.activities"), HEADING2_FONT);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    try {

      document.newPage();
      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project activities to the project summary pdf of project {} ", e,
        project.getId());
    }

    for (Activity activity : project.getActivities()) {
      activity = activityManager.getActivityById(activity.getId());

      // Getting the activity outcome information
      activity.setOutcome(activityManager.getActivityOutcome(activity.getId()));
      // Getting the information of the Cross Cutting Themes associated with the project
      activity.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByActivityID(activity.getId()));
      activity.setActivityPartners(activityPartnerManager.getActivityPartnersByActivity(activity.getId()));
      activity.setLocations(locationManager.getProjectLocations(activity.getId()));
      activity.setDeliverables(deliverableManager.getDeliverablesByProject(activity.getId()));

      // this.addActivityMainInformation(activity);
      this.addActivityOutcome(activity);
      this.addActivityGender(activity);
      this.addActivityCrossCuttingThemes(activity);
      this.addActivityPartners(activity);
      this.addActivityLocations(activity);
      this.addActivityDeliverables(activity);

      document.newPage();
    }
  }

  private void addActivityCrossCuttingThemes(Activity activity) {
    Paragraph crossCutting = new Paragraph();
    crossCutting.setAlignment(Element.ALIGN_JUSTIFIED);
    String text = "";

    crossCutting.setFont(HEADING4_FONT);
    crossCutting.add(this.getText("summaries.project.activities.crossCutting"));

    if (!activity.getCrossCuttings().isEmpty()) {
      for (int i = 0; i < activity.getCrossCuttings().size(); i++) {
        text += activity.getCrossCuttings().get(i).getName();
        if (i != activity.getCrossCuttings().size() - 1) {
          text += ", ";
        }
      }
    } else {
      text = this.getText("summaries.project.empty");
    }

    crossCutting.setFont(BODY_TEXT_FONT);
    crossCutting.add(text);

    try {
      document.add(crossCutting);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity cross cutting themes to the summary pdf of project {}",
        e, project.getId());
    }

  }

  private void addActivityDeliverables(Activity activity) {
    Paragraph deliverableBlock = new Paragraph();
    deliverableBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    deliverableBlock.setFont(HEADING4_FONT);

    try {
      deliverableBlock.add(this.getText("summaries.project.activities.deliverables"));
      document.add(deliverableBlock);
      document.add(new Chunk().NEWLINE);

      if (activity.getDeliverables().isEmpty()) {
        deliverableBlock.setFont(BODY_TEXT_FONT);
        deliverableBlock.add(this.getText("summaries.project.empty"));

        document.add(deliverableBlock);
        document.add(new Chunk().NEWLINE);
        return;
      }

      int i = 1;
      for (Deliverable deliverable : activity.getDeliverables()) {
        deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));
        deliverableBlock = new Paragraph();

        deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.activities.deliverable"));
        deliverableBlock.add(i + ": ");

        deliverableBlock.setFont(BODY_TEXT_FONT);
        deliverableBlock.add(deliverable.getTitle());
        deliverableBlock.add(new Chunk().NEWLINE);
        document.add(deliverableBlock);
        document.add(new Chunk().NEWLINE);

        // Add content
        PdfPTable table = new PdfPTable(3);
        Paragraph cellContent = new Paragraph();

        // Set table widths
        table.setLockedWidth(true);
        table.setTotalWidth(480);
        table.setWidths(new int[] {6, 2, 4});
        table.setKeepTogether(true);

        // First row
        cellContent = new Paragraph(this.getText("summaries.project.activities.MOG"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cellContent);

        cellContent = new Paragraph(this.getText("summaries.project.activities.year"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cellContent);

        cellContent = new Paragraph(this.getText("summaries.project.activities.type"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cellContent);

        if (deliverable.getOutput() != null) {
          cellContent = new Paragraph(deliverable.getOutput().getDescription(), TABLE_BODY_FONT);
        } else {
          cellContent = new Paragraph(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
        }
        this.addTableBodyCell(table, cellContent, Element.ALIGN_JUSTIFIED, 1);

        cellContent = new Paragraph(deliverable.getYear() + "", TABLE_BODY_FONT);
        this.addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, 1);

        cellContent = new Paragraph(deliverable.getType().getName(), TABLE_BODY_FONT);
        this.addTableBodyCell(table, cellContent, Element.ALIGN_JUSTIFIED, 1);

        document.add(table);
        document.add(new Chunk().NEWLINE);

        Paragraph nextUserBlock = new Paragraph();
        nextUserBlock.setFirstLineIndent(0);
        nextUserBlock.setIndentationLeft(22);
        nextUserBlock.setIndentationRight(22);
        nextUserBlock.setFont(BODY_TEXT_BOLD_FONT);

        int z = 1;
        nextUserBlock.setAlignment(Element.ALIGN_JUSTIFIED);
        for (NextUser nextUser : deliverable.getNextUsers()) {

          nextUserBlock.add(this.getText("summaries.project.activities.nextUser"));
          nextUserBlock.add(" #" + z + ": ");

          nextUserBlock.setFont(BODY_TEXT_FONT);
          nextUserBlock.add(nextUser.getUser());
          nextUserBlock.add(new Chunk().NEWLINE);
          nextUserBlock.add(new Chunk().NEWLINE);

          nextUserBlock.setFont(BODY_TEXT_BOLD_FONT);
          nextUserBlock.add(this.getText("summaries.project.activities.knowledge"));

          nextUserBlock.setFont(BODY_TEXT_FONT);
          nextUserBlock.add(nextUser.getExpectedChanges());
          nextUserBlock.add(new Chunk().NEWLINE);
          nextUserBlock.add(new Chunk().NEWLINE);

          nextUserBlock.setFont(BODY_TEXT_BOLD_FONT);
          nextUserBlock.add(this.getText("summaries.project.activities.strategies"));

          nextUserBlock.setFont(BODY_TEXT_FONT);
          nextUserBlock.add(nextUser.getStrategies());
          nextUserBlock.add(new Chunk().NEWLINE);
          nextUserBlock.add(new Chunk().NEWLINE);

          z++;
        }

        document.add(nextUserBlock);
        i++;
      }
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity deliverables summary pdf of project {}", e,
        project.getId());
    }
  }

  private void addActivityGender(Activity activity) {
    Paragraph researchOutputs = new Paragraph();
    researchOutputs.setAlignment(Element.ALIGN_JUSTIFIED);

    researchOutputs.setFont(HEADING4_FONT);
    researchOutputs.add(this.getText("summaries.project.activities.genderNarrative"));

    String text;
    if (activity.getExpectedResearchOutputs() != null && activity.getExpectedResearchOutputs().isEmpty()) {
      text = this.getText("summaries.project.empty");
    } else {
      text = activity.getExpectedResearchOutputs();
    }

    researchOutputs.setFont(BODY_TEXT_FONT);
    researchOutputs.add(text);

    Paragraph genderOutcomes = new Paragraph();
    genderOutcomes.setAlignment(Element.ALIGN_JUSTIFIED);
    genderOutcomes.add(new Chunk().NEWLINE);
    genderOutcomes.setFont(HEADING4_FONT);
    genderOutcomes.add(this.getText("summaries.project.activities.genderOutcome"));

    if (activity.getExpectedGenderContribution() != null && activity.getExpectedGenderContribution().isEmpty()) {
      text = this.getText("summaries.project.empty");
    } else {
      text = activity.getExpectedGenderContribution();
    }
    genderOutcomes.setFont(BODY_TEXT_FONT);
    genderOutcomes.add(text);
    genderOutcomes.add(new Chunk().NEWLINE);

    try {
      document.add(researchOutputs);
      document.add(genderOutcomes);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity gender to the summary pdf of project {}", e,
        project.getId());
    }
  }

  private void addActivityLocations(Activity activity) {
    Paragraph activityLocation = new Paragraph();
    double latitude, longitude;
    String text;

    activityLocation.setAlignment(Element.ALIGN_JUSTIFIED);
    activityLocation.setFont(HEADING4_FONT);

    activityLocation.add(this.getText("summaries.project.activities.locations"));

    if (activity.getLocations().isEmpty()) {
      activityLocation.add(new Chunk().NEWLINE);
      activityLocation.setFont(BODY_TEXT_FONT);

      if (activity.isGlobal()) {
        activityLocation.add(this.getText("summaries.project.activities.global"));
      } else {
        activityLocation.add(this.getText("summaries.project.empty"));
      }

      try {
        document.add(activityLocation);
        document.add(new Chunk().NEWLINE);
      } catch (DocumentException e) {
        LOG.error("There was an error trying to add the activity locations to the summary pdf of project {}", e,
          project.getId());
      }
      return;
    }

    PdfPTable table = new PdfPTable(4);
    Paragraph cellContent;

    try {
      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(480);
      table.setWidths(new int[] {5, 3, 3, 5});
      table.setKeepTogether(true);
      table.setHeaderRows(1);

      // Table header
      cellContent = new Paragraph(this.getText("summaries.project.activities.locationType"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cellContent);

      cellContent = new Paragraph(this.getText("summaries.project.activities.location.latitude"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cellContent);

      cellContent = new Paragraph(this.getText("summaries.project.activities.location.longitude"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cellContent);

      cellContent = new Paragraph(this.getText("summaries.project.activities.location.name"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cellContent);

      int c = 0;
      for (Location location : activity.getLocations()) {
        String locationType;

        if (location.isRegion()) {
          locationType = this.getText("summaries.project.activities.region");
          latitude = longitude = -999;
        } else if (location.isCountry()) {
          locationType = this.getText("summaries.project.activities.country");
          latitude = longitude = -999;
        } else if (location.isClimateSmartVillage()) {
          locationType = this.getText("summaries.project.activities.csv");
          ClimateSmartVillage csv = (ClimateSmartVillage) location;
          latitude = csv.getGeoPosition().getLatitude();
          longitude = csv.getGeoPosition().getLongitude();
        } else {
          OtherLocation otherLocation = (OtherLocation) location;
          locationType = otherLocation.getType().getName();
          latitude = Math.round(otherLocation.getGeoPosition().getLatitude() * 1000) / 1000;
          longitude = Math.round(otherLocation.getGeoPosition().getLongitude() * 1000) / 1000;
        }

        // Table row
        cellContent = new Paragraph(locationType, TABLE_BODY_FONT);
        this.addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, c % 2);

        text =
          (latitude == -999) ? this.getText("summaries.project.activities.location.notApplicable") : String
            .valueOf(latitude);
          cellContent = new Paragraph(text, TABLE_BODY_FONT);
          this.addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, c % 2);

          text =
            (longitude == -999) ? this.getText("summaries.project.activities.location.notApplicable") : String
              .valueOf(longitude);
            cellContent = new Paragraph(text, TABLE_BODY_FONT);
            this.addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, c % 2);

            cellContent = new Paragraph(location.getName(), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, c % 2);

            c++;
      }

      document.add(activityLocation);
      document.add(new Chunk().NEWLINE);

      document.add(table);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity locations to the summary pdf of project {}", e,
        project.getId());
    }
  }

  private void addActivityMainInformation(Activity activity) {
    Paragraph activityTitle = new Paragraph();
    activityTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    activityTitle.setFont(HEADING4_FONT);

    activityTitle.add(this.getActivityID(activity) + ": ");

    activityTitle.setFont(BODY_TEXT_FONT);
    activityTitle.add(activity.getTitle());

    LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -7);
    activityTitle.add(line);

    Paragraph activityDescription = new Paragraph();
    activityDescription.setAlignment(Element.ALIGN_JUSTIFIED);
    activityDescription.setFont(HEADING4_FONT);

    activityDescription.add(this.getText("summaries.project.activities.description"));

    activityDescription.setFont(BODY_TEXT_FONT);
    activityDescription.add(activity.getDescription());

    try {
      document.add(activityTitle);
      document.add(new Chunk().NEWLINE);

      this.addActivityMainInformationTable(activity);
      document.add(new Chunk().NEWLINE);

      document.add(activityDescription);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity main information to the project summary pdf", e);
    }
  }

  private void addActivityMainInformationTable(Activity activity) {
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    String startDate, endDate;
    double amount = 0;
    Paragraph cellContent;


    if (activity.getStartDate() != null) {
      startDate = new SimpleDateFormat("yyyy-MM-dd").format(activity.getStartDate());
    } else {
      startDate = this.getText("summaries.project.empty");
    }

    if (activity.getEndDate() != null) {
      endDate = new SimpleDateFormat("yyyy-MM-dd").format(activity.getEndDate());
    } else {
      endDate = this.getText("summaries.project.empty");
    }

    // Add content
    try {
      PdfPTable table = new PdfPTable(4);

      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(480);
      table.setWidths(new int[] {3, 5, 3, 5});
      table.setKeepTogether(true);


      // First row
      cellContent = new Paragraph(this.getText("summaries.project.startDate"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(startDate, TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(this.getText("summaries.project.endDate"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(endDate, TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Second row
      cellContent = new Paragraph(this.getText("summaries.project.activities.activityLeader"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      if (activity.getLeader() != null) {
        cellContent = new Paragraph(activity.getLeader().getComposedName(), TABLE_BODY_FONT);
      } else if (activity.getExpectedLeader() != null) {
        cellContent = new Paragraph(activity.getExpectedLeader().getComposedName(), TABLE_BODY_FONT);
      } else {
        cellContent = new Paragraph(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
      }
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(this.getText("summaries.project.activities.leadOrganization"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      if (activity.getLeader() != null) {
        cellContent = new Paragraph(activity.getLeader().getCurrentInstitution().getName(), TABLE_BODY_FONT);
      } else if (activity.getExpectedLeader() != null) {
        cellContent = new Paragraph(activity.getExpectedLeader().getCurrentInstitution().getName(), TABLE_BODY_FONT);
      } else {
        cellContent = new Paragraph(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
      }

      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Third row
      cellContent = new Paragraph(this.getText("summaries.project.activities.budgetw1w2"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      // amount = budgetManager.calculateActivityBudgetByType(activity.getId(), BudgetType.ACTIVITY_W1_W2.getValue());
      // cellContent = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      // this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(this.getText("summaries.project.activities.budgetw3bil"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      // amount =
      // budgetManager.calculateActivityBudgetByType(activity.getId(), BudgetType.ACTIVITY_W3_BILATERAL.getValue());
      // cellContent = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      // this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      document.add(table);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }

  }

  private void addActivityOutcome(Activity activity) {
    Paragraph activityOutcome = new Paragraph();
    activityOutcome.setAlignment(Element.ALIGN_JUSTIFIED);
    activityOutcome.setFont(HEADING4_FONT);

    activityOutcome.add(this.getText("summaries.project.activities.outcome"));

    activityOutcome.setFont(BODY_TEXT_FONT);

    String outcome = (activity.getOutcome() == null) ? this.getText("summaries.project.empty") : activity.getOutcome();
    activityOutcome.add(outcome);

    try {
      document.add(activityOutcome);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity outcome to the project summary pdf", e);
    }
  }

  private void addActivityPartners(Activity activity) {
    Paragraph partnersBlock = new Paragraph();
    partnersBlock.setKeepTogether(true);

    Paragraph title = new Paragraph(this.getText("summaries.project.activities.activityPartners"), HEADING4_FONT);
    title.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);

    int c = 1;
    for (ActivityPartner partner : activity.getActivityPartners()) {
      Paragraph partnerInfo = new Paragraph();
      partnerInfo.setAlignment(Element.ALIGN_JUSTIFIED);

      String partnerLabel = this.getText("summaries.project.partner") + c + ": ";

      // Contact information
      partnerInfo.setFont(BODY_TEXT_BOLD_FONT);
      partnerInfo.add(partnerLabel);

      partnerInfo.setFont(BODY_TEXT_FONT);
      partnerInfo.add(partner.getComposedName());
      partnerInfo.add(new Chunk().NEWLINE);

      // Organization
      partnerInfo.setFont(BODY_TEXT_BOLD_FONT);
      partnerInfo.add(this.getText("summaries.project.organization"));

      partnerInfo.setFont(BODY_TEXT_FONT);
      partnerInfo.add(partner.getPartner().getName());
      partnerInfo.add(new Chunk().NEWLINE);

      // Responsibilities
      partnerInfo.setFont(BODY_TEXT_BOLD_FONT);
      partnerInfo.add(this.getText("summaries.project.activities.activityPartners.contribution"));

      partnerInfo.setFont(BODY_TEXT_FONT);
      if (partner.getContribution() != null && !partner.getContribution().isEmpty()) {
        partnerInfo.add(partner.getContribution());
      } else {
        partnerInfo.add(this.getText("summaries.project.empty"));
      }

      partnerInfo.add(new Chunk().NEWLINE);
      partnersBlock.add(partnerInfo);

      c++;
    }
    try {
      document.add(partnersBlock);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  private void addDelivable(Paragraph partnersBlock, Deliverable deliverable) {

    if (deliverable != null) {
      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      paragraph.setKeepTogether(true);
      StringBuilder stringBuilder = new StringBuilder();

     
      // **** Deliverable Information *********
      paragraph = new Paragraph(this.getText("summaries.project.deliverable.information"), HEADING4_FONT);
      paragraph.add(new Chunk().NEWLINE);
      paragraph.add(new Chunk().NEWLINE);
      
      //Title   
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.information.title")+" : " );
      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(deliverable.getTitle());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);
 
      
      // MOG
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      stringBuilder.append( this.getText("summaries.project.deliverable.information.mog"));
      stringBuilder.append(" : ");
      paragraph.add(stringBuilder.toString());
      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder = new StringBuilder();
      stringBuilder.append(deliverable.getOutput().getDescription());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);

      
      
      // Year
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      stringBuilder.append( this.getText("summaries.project.deliverable.information.year"));
      stringBuilder.append(" : ");
      paragraph.add(stringBuilder.toString());
      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder = new StringBuilder();
      stringBuilder.append(deliverable.getYear());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);

      
      // Main Type
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      stringBuilder.append( this.getText("summaries.project.deliverable.information.main"));
      stringBuilder.append(" : ");
      paragraph.add(stringBuilder.toString());
      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder = new StringBuilder();
      stringBuilder.append(deliverable.getType().getCategory().getName());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);

      
      // Sub Type
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      stringBuilder.append( this.getText("summaries.project.deliverable.information.sub"));
      stringBuilder.append(" : ");
      paragraph.add(stringBuilder.toString());
      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder = new StringBuilder();
      
      if(deliverable.getType().getName()!= null){
      stringBuilder.append(deliverable.getType().getName());}
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);
      paragraph.add(new Chunk().NEWLINE);

      
      // ********** Next Users**************************************
      paragraph.setFont(HEADING4_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.next.user")+ "s");
      paragraph.add(new Chunk().NEWLINE);
      paragraph.add(new Chunk().NEWLINE);
      
      //Next user   
      for(NextUser nextUser : deliverable.getNextUsers()){
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.next.user")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(nextUser.getUser());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);

      
      //Expected Changes
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.next.user.strategies")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(nextUser.getExpectedChanges());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);
  
      
      //Strategies  
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.next.user.expected.change")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(nextUser.getStrategies());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE); 
      paragraph.add(new Chunk().NEWLINE); 
      
      }
      
      // ********** Deliverable partnership**************************************
     
      paragraph.setFont(HEADING4_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.partnership")+" : ");
      paragraph.add(new Chunk().NEWLINE);
      paragraph.add(new Chunk().NEWLINE);
      
      //Organization
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.partnership.organization")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(deliverable.getResponsiblePartner().getInstitution().getName());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);
      
      //Contact Email  
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.partnership.email")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(deliverable.getResponsiblePartner().getUser().getEmail());
      paragraph.add(stringBuilder.toString());
      paragraph.add(new Chunk().NEWLINE);
      paragraph.add(new Chunk().NEWLINE);

      partnersBlock.add(paragraph);
      
      
    }
  }


  private void addMainInformationTable() {
    String startDate = new SimpleDateFormat("yyyy-MM-dd").format(project.getStartDate());
    String endDate = new SimpleDateFormat("yyyy-MM-dd").format(project.getEndDate());
    Paragraph cellContent;

    // Add content
    try {
      PdfPTable table = new PdfPTable(4);

      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(480);
      table.setWidths(new int[] {3, 5, 3, 5});

      // Add table

      // First row
      cellContent = new Paragraph(this.getText("summaries.project.startDate"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(startDate, TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(this.getText("summaries.project.endDate"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(endDate, TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Second row
      cellContent = new Paragraph(this.getText("summaries.project.managementLiaison"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(project.getLiaisonInstitution().getAcronym(), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(this.getText("summaries.project.contactPerson"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(project.getOwner().getComposedName(), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Third row
      cellContent = new Paragraph(this.getText("summaries.project.leadOrganization"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(project.getLeader().getInstitution().getName(), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(this.getText("summaries.project.projectLeader"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(project.getLeader().getUser().getComposedName(), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Fourth row
      cellContent = new Paragraph(this.getText("summaries.project.projectType"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(project.getType(), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(this.getText("summaries.project.projectBilateral"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.getNameProjectLinkages(project.getLinkedProjects()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      document.add(table);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }


  private void addOverview(Paragraph partnersBlock, OutputOverview outputOverview) {

    if (outputOverview != null) {
      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      paragraph.setKeepTogether(true);

      // Mog contribution
      paragraph.setFont(BODY_TEXT_FONT);
      if (outputOverview.getOutput() != null) {
        paragraph.add(outputOverview.getOutput().getProgram().getAcronym() + " - MOG # "
          + this.getMOGIndex(outputOverview.getOutput()) + " : " + outputOverview.getOutput().getDescription());

      } else {
        paragraph.add("Not defined");
      }
      paragraph.add(new Chunk().NEWLINE);
      paragraph.add(new Chunk().NEWLINE);

      String expectedAnnualContribution =
        this.getText("summaries.project.expected.contribution.first") + outputOverview.getYear() + " "
          + this.getText("summaries.project.expected.contribution.last") + " : ";

      // Expected Annual Contribution title
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(expectedAnnualContribution);

      // Expected Annual Contribution text
      paragraph.setFont(BODY_TEXT_FONT);
      if (outputOverview.getExpectedAnnualContribution() != null) {
        paragraph.add(outputOverview.getExpectedAnnualContribution());
        paragraph.add(new Chunk().NEWLINE);
        paragraph.add(new Chunk().NEWLINE);
      }

      String socialInclusionDimenssion = this.getText("summaries.project.social.contribution") + ": ";

      // Social inclusion dimension title
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(socialInclusionDimenssion);

      // Social inclusion dimension text
      paragraph.setFont(BODY_TEXT_FONT);
      if (outputOverview.getSocialInclusionDimmension() != null) {
        paragraph.add(outputOverview.getSocialInclusionDimmension());
        paragraph.add(Chunk.NEWLINE);
      }
      partnersBlock.add(paragraph);
    }
  }


  private void addProjectBudget() {
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    BudgetType[] budgetTypes = BudgetType.getProjectBudgetTypes();
    int startYear, endYear;
    double amount;
    Paragraph cell = new Paragraph();

    // currencyFormatter.format(budget);

    Calendar startDate = Calendar.getInstance();
    startDate.setTime(project.getStartDate());
    startYear = startDate.get(Calendar.YEAR);

    Calendar endDate = Calendar.getInstance();
    endDate.setTime(project.getEndDate());
    endYear = endDate.get(Calendar.YEAR);

    cell.setFont(HEADING3_FONT);
    cell.add("3. " + this.getText("summaries.project.budget"));


    // Institutions
    try {
      document.add(cell);

      // Summary table
      this.addProjectBudgetSummary(startYear, endYear, currencyFormatter);

      List<Institution> institutions = new ArrayList<>();
      institutions.add(project.getLeader().getInstitution());

      for (ProjectPartner partner : project.getProjectPartners()) {
        institutions.add(partner.getInstitution());
      }

      for (int i = 0; i < institutions.size(); i++) {
        Institution institution = institutions.get(i);

        PdfPTable table = new PdfPTable(endYear - startYear + 2);
        table.setLockedWidth(true);
        table.setTotalWidth(80 * (endYear - startYear + 2));
        table.setWidths(this.getBudgetTableColumnWidths(startYear, endYear));
        table.setHeaderRows(1);
        table.setKeepTogether(true);

        String name;
        if (i == 0) {
          name = institution.getName() + " " + this.getText("summaries.project.organization.lead");
        } else {
          name = institution.getName();
        }

        // Add cell with the institution name
        cell = new Paragraph(institution.getName(), BODY_TEXT_BOLD_FONT);
        this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        // Headers
        this.addTableHeaderCell(table, new Paragraph());
        for (int year = startYear; year <= endYear; year++) {
          cell = new Paragraph(String.valueOf(year), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);
        }

        for (BudgetType bt : budgetTypes) {
          cell = new Paragraph(String.valueOf(bt.name().toLowerCase().replace("_", " + ")), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          for (int year = startYear; year <= endYear; year++) {
            Budget b = project.getBudget(institution.getId(), bt.getValue(), year);
            amount = (b == null) ? 0 : b.getAmount();
            cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);

            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
          }
        }

        document.add(new Chunk().NEWLINE);
        document.add(table);
        document.add(new Chunk().NEWLINE);
        document.add(new Chunk().NEWLINE);
      }


    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }

  }


  private void addProjectBudgetSummary(int startYear, int endYear, NumberFormat currencyFormatter) {
    Paragraph cell = new Paragraph();
    double amount;

    try {
      PdfPTable table = new PdfPTable(endYear - startYear + 2);
      table.setLockedWidth(true);
      table.setTotalWidth(80 * (endYear - startYear + 2));
      table.setWidths(this.getBudgetTableColumnWidths(startYear, endYear));
      table.setKeepTogether(true);
      table.setHeaderRows(2);

      // Add cell with the institution name
      cell = new Paragraph(this.getText("summaries.project.budget.summary"), BODY_TEXT_BOLD_FONT);
      this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
        table.getNumberOfColumns(), 0, false);

      // Headers
      this.addTableHeaderCell(table, new Paragraph());
      for (int year = startYear; year <= endYear; year++) {
        cell = new Paragraph(String.valueOf(year), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);
      }

      // Window 1 and 2
      cell =
        new Paragraph(String.valueOf(BudgetType.W1_W2.name().toLowerCase().replace("_", " + ")), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      for (int year = startYear; year <= endYear; year++) {
        amount = budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
        cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);

        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      }

      // Window 1 and 2 + Window 3 + bilateral
      String text = String.valueOf(BudgetType.W1_W2.name().toLowerCase().replace("_", " + "));
      text += " + " + String.valueOf(BudgetType.W3_BILATERAL.name().toLowerCase().replace("_", " + "));

      cell = new Paragraph(text, TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      for (int year = startYear; year <= endYear; year++) {
        amount = budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
        amount +=
          budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL.getValue(), year);

        cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      }

      // Leveraged
      // cell =
      // new Paragraph(String.valueOf(BudgetType.LEVERAGED.name().toLowerCase().replace("_", " + ")),
      // TABLE_BODY_BOLD_FONT);
      // this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      // for (int year = startYear; year <= endYear; year++) {
      // amount =
      // budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.LEVERAGED.getValue(), year);
      // cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      //
      // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      // }

      // Gender
      // text = String.valueOf(BudgetType.W1_W2_GENDER.name().toLowerCase().replace("_", " + "));
      // text += " + " + String.valueOf(BudgetType.W3_BILATERAL_GENDER.name().toLowerCase().replace("_", " + "));

      cell = new Paragraph(text, TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      // for (int year = startYear; year <= endYear; year++) {
      // amount =
      // budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2_GENDER.getValue(), year);
      // amount +=
      // budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL_GENDER.getValue(),
      // year);
      //
      // cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      // }

      document.add(table);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }


  private void addProjectContributions() {
    StringBuilder projectFocuses = new StringBuilder();
    Paragraph paragraph = new Paragraph();

    for (IPProgram program : project.getFlagships()) {
      projectFocuses.append(program.getAcronym());
      projectFocuses.append(", ");
    }

    for (IPProgram program : project.getRegions()) {
      projectFocuses.append(program.getAcronym());
      projectFocuses.append(", ");
    }

    paragraph.setFont(BODY_TEXT_BOLD_FONT);
    paragraph.add(this.getText("summaries.project.ipContributions"));

    paragraph.setFont(BODY_TEXT_FONT);
    if (projectFocuses.length() != 0) {
      paragraph.add(projectFocuses.substring(0, projectFocuses.length() - 2));
    }

    try {
      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  private void addProjectIndicators() {
    PdfPTable table = new PdfPTable(3);
    Paragraph cell = new Paragraph();
    Paragraph indicatorsBlock = new Paragraph();
    indicatorsBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    indicatorsBlock.setKeepTogether(true);

    Paragraph title = new Paragraph("5. " + this.getText("summaries.project.indicatorsContribution"), HEADING3_FONT);
    indicatorsBlock.add(title);
    indicatorsBlock.add(new Chunk().NEWLINE);

    try {
      document.add(indicatorsBlock);

      for (IPElement outcome : this.getMidOutcomesPerIndicators()) {
        Paragraph outcomeBlock = new Paragraph();
        int indicatorIndex = 1;

        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(outcome.getProgram().getAcronym());
        outcomeBlock.add(" - " + this.getText("summaries.project.midoutcome"));

        outcomeBlock.setFont(BODY_TEXT_FONT);
        outcomeBlock.add(outcome.getDescription());
        outcomeBlock.add(new Chunk().NEWLINE);
        outcomeBlock.add(new Chunk().NEWLINE);

        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(this.getText("summaries.project.indicators"));
        document.add(outcomeBlock);
        document.add(new Chunk().NEWLINE);

        for (IPIndicator outcomeIndicator : outcome.getIndicators()) {
          outcomeIndicator = (outcomeIndicator.getParent() != null) ? outcomeIndicator.getParent() : outcomeIndicator;
          List<IPIndicator> indicators = project.getIndicatorsByParent(outcomeIndicator.getId());
          if (indicators.isEmpty()) {
            continue;
          }

          Paragraph indicatorDescription =
            new Paragraph(indicatorIndex + ". " + outcomeIndicator.getDescription(), BODY_TEXT_FONT);
          document.add(indicatorDescription);
          document.add(new Chunk().NEWLINE);

          table = new PdfPTable(3);
          table.setLockedWidth(true);
          table.setTotalWidth(480);
          table.setWidths(new int[] {2, 3, 6});
          table.setHeaderRows(1);

          // Headers
          cell = new Paragraph(this.getText("summaries.project.indicator.year"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);
          cell = new Paragraph(this.getText("summaries.project.indicator.targetValue"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);
          cell = new Paragraph(this.getText("summaries.project.indicator.targetNarrative"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          for (IPIndicator indicator : indicators) {

            if (indicator.getOutcome().getId() != outcome.getId()) {
              continue;
            }
            cell = new Paragraph(String.valueOf(indicator.getYear()), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

            cell = new Paragraph(String.valueOf(indicator.getTarget()), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            cell = new Paragraph(String.valueOf(indicator.getDescription()), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          }

          indicatorIndex++;
          document.add(table);
          document.add(new Chunk().NEWLINE);
        }

      }
    } catch (DocumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private void addProjectLocations() {

    Paragraph title = new Paragraph("3. " + this.getText("summaries.project.projectLocations"), HEADING3_FONT);
    title.add(new Chunk().NEWLINE);

    StringBuilder projectLocations = new StringBuilder();
    Paragraph paragraph = new Paragraph();


    List<Location> locationList = this.removeRepeatedLocations(project.getLocations());

    for (Location location : locationList) {
      if (location.isCountry()) {
        projectLocations.append("Country");
      } else if (location.isRegion()) {
        projectLocations.append("Region");
      } else if (location.isClimateSmartVillage()) {
        projectLocations.append("CSV");
      } else {
        OtherLocation otherLocation = (OtherLocation) location;
        projectLocations.append(otherLocation.getType().getName());
      }

      projectLocations.append(" - " + location.getName() + " ");
      projectLocations.append("\n");
    }
    try {
      paragraph.add(title);
      paragraph.setFont(BODY_TEXT_FONT);
      if (projectLocations.length() != 0) {
        paragraph.add(projectLocations.substring(0, projectLocations.length() - 2));
      }

      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project locations to the project summary pdf", e);
    }

  }
  private void addProjectOutcome() {
    String outcomeProgress;
    Paragraph outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph title = new Paragraph("5. " + this.getText("summaries.project.outcome"), HEADING2_FONT);
    outcomesBlock.add(title);
    outcomesBlock.add(new Chunk().NEWLINE);
    title = new Paragraph(); 
    title.setFont(HEADING3_FONT);
    title.add("5.1 " + this.getText("summaries.project.outcomeNarrative"));
    outcomesBlock.add(title);
    outcomesBlock.add(new Chunk().NEWLINE);

    // Project outcome statement
    Paragraph body = new Paragraph();
    body.setFont(BODY_TEXT_BOLD_FONT);
    body.add(this.getText("summaries.project.outcomeStatement"));
    body.add(new Chunk().NEWLINE);

    body.setFont(BODY_TEXT_FONT);
    if (project.getOutcomes() != null && project.getOutcomes().get(String.valueOf(midOutcomeYear)) != null) {
      body.add(project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement());
    } else {
      body.add(this.getText("summaries.project.empty"));
    }
    body.add(new Chunk().NEWLINE);
    outcomesBlock.add(body);

    try {
      document.add(outcomesBlock);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

    for (int year = currentPlanningYear; year < midOutcomeYear; year++) {
      // Project outcome statement
      outcomesBlock = new Paragraph();
      outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);

      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcomeAnnualProgress", new String[] {String.valueOf(year)}));
      outcomesBlock.add(new Chunk().NEWLINE);

      if (project.getOutcomes().get(String.valueOf(year)) == null) {
        outcomeProgress = this.getText("summaries.project.empty");
      } else {
        outcomeProgress = project.getOutcomes().get(String.valueOf(year)).getStatement();
      }
      
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(outcomeProgress);
      outcomesBlock.add(new Chunk().NEWLINE);
      try {
          document.add(outcomesBlock);
          document.add(new Chunk().NEWLINE);
        } catch (DocumentException e) {
          LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
        }
      }
    
    //******************* Gender contribution ***************/

    outcomesBlock = new Paragraph();
    outcomesBlock.setFont(HEADING3_FONT);
    outcomesBlock.add(this.getText("summaries.project.outcome.gender"));
    outcomesBlock.add(new Chunk().NEWLINE);
    
    
    for(int year = currentPlanningYear; year < midOutcomeYear; year++)
    {
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.gender.contributiton", new String[] {String.valueOf(year)}));
      outcomesBlock.add(new Chunk().NEWLINE);    	
      
      if (project.getOutcomes().get(String.valueOf(year)) == null) {
          outcomeProgress = this.getText("summaries.project.empty");
        } else {
          outcomeProgress = project.getOutcomes().get(String.valueOf(year)).getGenderDimension();
        }
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(outcomeProgress);
      outcomesBlock.add(new Chunk().NEWLINE);
      
    }	
    // Add paragraphs to document
    try {
      document.add(outcomesBlock);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }
  
    //******************* Gender contribution ***************/
  
    
  }

  // Entering the project outputs in the summary
  private void addProjectOutputs() {

	//**********************************************************************************
    //***************************  Overview By Mog *************************************
	//**********************************************************************************
     
    Paragraph overview_title = new Paragraph("4. " + this.getText("summaries.project.projectOutput"), HEADING2_FONT);
    Paragraph overview_title_1 =
      new Paragraph("4.1 " + this.getText("summaries.project.overviewbymogs"), HEADING3_FONT);
    try {
      document.add(overview_title);
      document.add(overview_title_1);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }

    int[] years = {2015, 2016, 2019};

    // 0 = 2015 , 1= 2016 , 2 = 2019
    Paragraph[] paragraphs = new Paragraph[years.length];
    for (int a = 0; a < paragraphs.length; a++) {
      paragraphs[a] = new Paragraph();
    }
    int counter = 0;
    for (OutputOverview outputOverview : project.getOutputsOverview()) {
      for (counter = 0; counter < years.length; counter++) {
        if (outputOverview.getYear() == years[counter]) {
          this.addOverview(paragraphs[counter], outputOverview);
        }
      }
    }
    counter = 0;
    for (Paragraph paragraph : paragraphs) {
      try {
        if (paragraph.size() != 0) {
          document.add(new Paragraph((this.getText("summaries.project.overviewbymogs.text") + years[counter]),
            HEADING4_FONT));
          document.add(paragraph);
          document.add(Chunk.NEWLINE);
        }

      } catch (DocumentException e) {
        LOG.error("There was an error trying to add the project title to the project summary pdf", e);
      }
      counter++;
    }
    
    //**********************************************************************************
    // *************************** Deliverables.****************************************
    //**********************************************************************************


    Paragraph paragraph;
    try {
        paragraph =  new Paragraph("4.2 " + this.getText("summaries.project.deliverable.title"), HEADING3_FONT);
      document.add(paragraph);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
    
    List<Paragraph> listParagraphDeliverables = new ArrayList<Paragraph>();
    for (Deliverable deliverable : project.getDeliverables()) {
    	paragraph = new Paragraph();
    	this.addDelivable(paragraph, deliverable);
    	listParagraphDeliverables.add(paragraph);
    }

    for (Paragraph paragraph_1 : listParagraphDeliverables) {
      try {
        if (paragraph_1.size() != 0) {
          paragraph_1.setAlignment(Element.ALIGN_JUSTIFIED);
          document.add(paragraph_1);
          document.add(Chunk.NEWLINE);
        }

      } catch (DocumentException e) {
        LOG.error("There was an error trying to add the project title to the project summary pdf", e);
      }
      
    }
   
    
  }
  


  private void addProjectPartners() {
    Paragraph partnersBlock = new Paragraph();
    partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.setKeepTogether(true);

    Paragraph title = new Paragraph(32, "2. " + this.getText("summaries.project.projectPartners"), HEADING3_FONT);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);


    int c = 1;
    // Get project leader PL
    title = new Paragraph(28, "2.1 " + this.getText("summaries.project.projectPartners.section.one"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);
    this.auxiliarGetPartners(project.getLeader(), c, partnersBlock);
    c++;

    // Get project coordinator PC
    title = new Paragraph(28, "2.2 " + this.getText("summaries.project.projectPartners.section.two"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);
    if (!(project.getCoordinator() == null)) {
      this.auxiliarGetPartners(project.getCoordinator(), c, partnersBlock);
      c++;
    }

    // Get CCAFS partners PPA
    title = new Paragraph(28, "2.3 " + this.getText("summaries.project.projectPartners.section.three"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);
    for (ProjectPartner partner : project.getPPAPartners()) {
      this.auxiliarGetPartners(partner, c, partnersBlock);
      c++;
    }

    // Get Project Partners PP
    title = new Paragraph(28, "2.4 " + this.getText("summaries.project.projectPartners.section.four"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);

    for (ProjectPartner partner : project.getProjectPartners()) {
      this.auxiliarGetPartners(partner, c, partnersBlock);
      c++;
    }

    try {
      document.add(partnersBlock);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  private void addProjectTitle() {
    LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -7);
    Paragraph paragraph = new Paragraph();
    line.setLineColor(titleColor);

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.setFont(HEADING3_FONT);

    paragraph.add(this.getProjectID());
    paragraph.add(new Chunk().NEWLINE);

    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(project.getTitle());
    paragraph.add(line);

    try {
      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addSummary() {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

    Phrase title = new Phrase("1. " + this.getText("summaries.project.summary"), HEADING3_FONT);
    paragraph.add(title);

    paragraph.add(new Chunk().NEWLINE);
    paragraph.add(new Chunk().NEWLINE);

    Phrase body = new Phrase(project.getSummary(), BODY_TEXT_FONT);
    paragraph.add(body);

    try {
      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project summary to the project summary pdf", e);
    }
  }


  private void auxiliarGetPartners(ProjectPartner partner, int c, Paragraph partnersBlock) {

    if (!(partner == null)) {
      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      // paragraph.setLeading(125);
      paragraph.setKeepTogether(true);
      String partnerLabel = this.getText("summaries.project.partner") + c + ": ";

      // Contact information
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(partnerLabel);

      // User
      paragraph.setFont(BODY_TEXT_FONT);
      if (partner.getUser() != null) {
        paragraph.add(partner.getUser().getComposedName());

      } else {
        paragraph.add("Not defined");
      }

      paragraph.add(new Chunk().NEWLINE);

      // Organization
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.organization"));

      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(partner.getInstitution().getName());
      paragraph.add(Chunk.NEWLINE);

      // Responsibilities
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.responsibilities"));

      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(partner.getResponsabilities());
      paragraph.add(new Chunk().NEWLINE);
      partnersBlock.add(paragraph);
    }
  }
/**
 *  This method is used for generate the file pdf. 
 * @param project
 * @param currentPlanningYear
 * @param midOutcomeYear
 */
  public void generatePdf(Project project, int currentPlanningYear, int midOutcomeYear) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    this.document = new Document(PageSize.A4, 57, 57, 60, 57);
    this.project = project;
    this.midOutcomeYear = midOutcomeYear;
    this.currentPlanningYear = currentPlanningYear;
    this.setSummaryTitle(this.getProjectID());

    PdfWriter writer = this.initializePdf(document, outputStream, PORTRAIT);

    // Adding the event to include header and footer on each page
    HeaderFooterPDF event = new HeaderFooterPDF(summaryTitle, PORTRAIT);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Project content
    this.addProjectTitle();
    this.addMainInformationTable();
    this.addProjectContributions();
    this.addSummary();
    this.addProjectPartners();
    this.addProjectLocations();
    this.addProjectOutputs();

    // More o less
    this.addProjectIndicators();
    this.addActivities();

    
    // this.addProjectBudget();
    // this.addProjectOutcome();
    
    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();
    inputStream = (new ByteArrayInputStream(outputStream.toByteArray()));
  }

  private String getActivityID(Activity activity) {
    StringBuilder activityID = new StringBuilder();
    activityID.append(this.getText("summaries.project.activity"));
    activityID.append(" ");
    activityID.append("P" + project.getId() + "A" + activity.getId());

    return activityID.toString();
  }

  private int[] getBudgetTableColumnWidths(int startYear, int endYear) {
    int[] columnWidths = new int[endYear - startYear + 2];
    for (int i = 0; i < columnWidths.length; i++) {
      if (i == 0) {
        columnWidths[i] = 10;
      } else {
        columnWidths[i] = 8;
      }
    }

    return columnWidths;
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

  private List<IPElement> getMidOutcomesPerIndicators() {
    List<IPElement> midOutcomes = new ArrayList<>();

    for (IPIndicator indicator : project.getIndicators()) {
      if (!midOutcomes.contains(indicator.getOutcome())) {
        midOutcomes.add(elementManager.getIPElement(indicator.getOutcome().getId()));
      }
    }
    return midOutcomes;
  }

 /**
 * Auxiliary method used for numbering the mogs in the summary  
 * @param mog
 * @return
 */
  public int getMOGIndex(IPElement mog) {
    int index = 0;
    List<IPElement> allMOGs = elementManager.getIPElements(mog.getProgram(), mog.getType());

    for (int i = 0; i < allMOGs.size(); i++) {
      if (allMOGs.get(i).getId() == mog.getId()) {
        return (i + 1);
      }
    }

    return index;
  }


  private String getNameProjectLinkages(List<Project> projectList) {
    String answer = "";
    if (!(projectList == null)) {
      for (int a = 0; a < projectList.size(); a++) {
        answer += projectList.get(a).getTitle() + " " + "\n";
      }
    }
    return answer;

  }

  private String getProjectID() {
    StringBuilder projectID = new StringBuilder();
    projectID.append(this.getText("summaries.project.project"));
    projectID.append(" ");
    projectID.append("P" + project.getId());

    for (IPProgram flagship : project.getFlagships()) {
      projectID.append("-");
      projectID.append(flagship.getAcronym().replace(" ", ""));
    }

    for (IPProgram region : project.getRegions()) {
      if (region.getRegion().getCode() != null) {
        projectID.append("-");
        projectID.append(region.getRegion().getCode());
      }
    }

    projectID.append("-");
    projectID.append(project.getLeader().getInstitution().getAcronym());

    return projectID.toString();
  }

  public boolean isRepeatedLocation(Location location, List<Location> listLocation, int index) {
    for (int a = index; a < listLocation.size(); a++) {
      if (listLocation.get(a).getName().trim().equals(location.getName().trim())) {
        return true;
      }
    }
    return false;
  }

  public List<Location> removeRepeatedLocations(List<Location> listLocation) {
    List<Location> listLocationAnswer = new ArrayList();
    //
    for (int a = 0; a < listLocation.size() - 1; a++) {
      if (!this.isRepeatedLocation(listLocation.get(a), listLocation, a + 1)) {
        listLocationAnswer.add(listLocation.get(a));
      }
    }
    listLocationAnswer.add(listLocation.get(listLocation.size() - 1));
    return listLocationAnswer;
  }

  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }

}
