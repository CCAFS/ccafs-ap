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

import org.cgiar.ccafs.ap.config.APConfig;
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
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


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
    initialize(config.getBaseUrl());
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
    Paragraph paragraph = new Paragraph(getText("summaries.project.activities"), HEADING2_FONT);
    paragraph.setAlignment(Element.ALIGN_CENTER);

    try {

      document.newPage();
      document.add(paragraph);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project summary to the project summary pdf", e);
    }

    for (Activity activity : project.getActivities()) {
      activity = activityManager.getActivityById(activity.getId());

      // Getting the activity outcome information
      activity.setOutcome(activityManager.getActivityOutcome(activity.getId()));
      // Getting the information of the Cross Cutting Themes associated with the project
      activity.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByActivityID(activity.getId()));
      activity.setActivityPartners(activityPartnerManager.getActivityPartnersByActivity(activity.getId()));
      activity.setLocations(locationManager.getActivityLocations(activity.getId()));
      activity.setDeliverables(deliverableManager.getDeliverablesByActivity(activity.getId()));

      addActivityMainInformation(activity);
      addActivityOutcome(activity);
      addActivityGender(activity);
      addActivityCrossCuttingThemes(activity);
      addActivityPartners(activity);
      addActivityLocations(activity);
      addActivityDeliverables(activity);

      document.newPage();
    }
  }

  private void addActivityCrossCuttingThemes(Activity activity) {
    Paragraph crossCutting = new Paragraph();
    crossCutting.setAlignment(Element.ALIGN_JUSTIFIED);
    String text = "";

    crossCutting.setFont(HEADING4_FONT);
    crossCutting.add(getText("summaries.project.activities.crossCutting"));

    if (!activity.getCrossCuttings().isEmpty()) {
      for (int i = 0; i < activity.getCrossCuttings().size(); i++) {
        text += activity.getCrossCuttings().get(i).getName();
        if (i != activity.getCrossCuttings().size() - 1) {
          text += ", ";
        }
      }
    } else {
      text = getText("summaries.project.empty");
    }

    crossCutting.setFont(BODY_TEXT_FONT);
    crossCutting.add(text);

    try {
      document.add(crossCutting);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity outcome to the project summary pdf", e);
    }

  }

  private void addActivityDeliverables(Activity activity) {
    Paragraph deliverableBlock = new Paragraph();
    deliverableBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    deliverableBlock.setFont(HEADING4_FONT);

    try {
      deliverableBlock.add(getText("summaries.project.activities.deliverables"));
      document.add(deliverableBlock);
      document.add(new Chunk().NEWLINE);

      if (activity.getDeliverables().isEmpty()) {
        deliverableBlock.setFont(BODY_TEXT_FONT);
        deliverableBlock.add(getText("summaries.project.empty"));

        document.add(deliverableBlock);
        document.add(new Chunk().NEWLINE);
        return;
      }

      int i = 1;
      for (Deliverable deliverable : activity.getDeliverables()) {
        deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverable.getId()));
        deliverableBlock = new Paragraph();

        deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
        deliverableBlock.add(getText("summaries.project.activities.deliverable"));
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
        cellContent = new Paragraph(getText("summaries.project.activities.MOG"), TABLE_HEADER_FONT);
        addTableHeaderCell(table, cellContent);

        cellContent = new Paragraph(getText("summaries.project.activities.year"), TABLE_HEADER_FONT);
        addTableHeaderCell(table, cellContent);

        cellContent = new Paragraph(getText("summaries.project.activities.type"), TABLE_HEADER_FONT);
        addTableHeaderCell(table, cellContent);

        if (deliverable.getOutput() != null) {
          cellContent = new Paragraph(deliverable.getOutput().getDescription(), TABLE_BODY_FONT);
        } else {
          cellContent = new Paragraph(getText("summaries.project.empty"), TABLE_BODY_FONT);
        }
        addTableBodyCell(table, cellContent, Element.ALIGN_JUSTIFIED, 1);

        cellContent = new Paragraph(deliverable.getYear() + "", TABLE_BODY_FONT);
        addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, 1);

        cellContent = new Paragraph(deliverable.getType().getName(), TABLE_BODY_FONT);
        addTableBodyCell(table, cellContent, Element.ALIGN_JUSTIFIED, 1);

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

          nextUserBlock.add(getText("summaries.project.activities.nextUser"));
          nextUserBlock.add(" #" + z + ": ");

          nextUserBlock.setFont(BODY_TEXT_FONT);
          nextUserBlock.add(nextUser.getUser());
          nextUserBlock.add(new Chunk().NEWLINE);
          nextUserBlock.add(new Chunk().NEWLINE);

          nextUserBlock.setFont(BODY_TEXT_BOLD_FONT);
          nextUserBlock.add(getText("summaries.project.activities.knowledge"));

          nextUserBlock.setFont(BODY_TEXT_FONT);
          nextUserBlock.add(nextUser.getExpectedChanges());
          nextUserBlock.add(new Chunk().NEWLINE);
          nextUserBlock.add(new Chunk().NEWLINE);

          nextUserBlock.setFont(BODY_TEXT_BOLD_FONT);
          nextUserBlock.add(getText("summaries.project.activities.strategies"));

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
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addActivityGender(Activity activity) {
    Paragraph researchOutputs = new Paragraph();
    researchOutputs.setAlignment(Element.ALIGN_JUSTIFIED);

    researchOutputs.setFont(HEADING4_FONT);
    researchOutputs.add(getText("summaries.project.activities.genderNarrative"));

    String text;
    if (activity.getExpectedResearchOutputs() != null && activity.getExpectedResearchOutputs().isEmpty()) {
      text = getText("summaries.project.empty");
    } else {
      text = activity.getExpectedResearchOutputs();
    }

    researchOutputs.setFont(BODY_TEXT_FONT);
    researchOutputs.add(text);

    Paragraph genderOutcomes = new Paragraph();
    genderOutcomes.setAlignment(Element.ALIGN_JUSTIFIED);
    genderOutcomes.add(new Chunk().NEWLINE);
    genderOutcomes.setFont(HEADING4_FONT);
    genderOutcomes.add(getText("summaries.project.activities.genderOutcome"));

    text =
      (activity.getExpectedGenderContribution().isEmpty()) ? getText("summaries.project.empty") : activity
        .getExpectedGenderContribution();
    genderOutcomes.setFont(BODY_TEXT_FONT);
    genderOutcomes.add(text);
    genderOutcomes.add(new Chunk().NEWLINE);

    try {
      document.add(researchOutputs);
      document.add(genderOutcomes);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the activity outcome to the project summary pdf", e);
    }
  }

  private void addActivityLocations(Activity activity) {
    Paragraph activityLocation = new Paragraph();
    double latitude, longitude;
    String text;

    activityLocation.setAlignment(Element.ALIGN_JUSTIFIED);
    activityLocation.setFont(HEADING4_FONT);

    activityLocation.add(getText("summaries.project.activities.locations"));

    if (activity.getLocations().isEmpty()) {
      activityLocation.add(new Chunk().NEWLINE);
      activityLocation.setFont(BODY_TEXT_FONT);

      if (activity.isGlobal()) {
        activityLocation.add(getText("summaries.project.activities.global"));
      } else {
        activityLocation.add(getText("summaries.project.empty"));
      }

      try {
        document.add(activityLocation);
        document.add(new Chunk().NEWLINE);
      } catch (DocumentException e) {
        LOG.error("There was an error trying to add the project title to the project summary pdf", e);
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

      // Table header
      cellContent = new Paragraph(getText("summaries.project.activities.locationType"), TABLE_HEADER_FONT);
      addTableHeaderCell(table, cellContent);

      cellContent = new Paragraph(getText("summaries.project.activities.location.latitude"), TABLE_HEADER_FONT);
      addTableHeaderCell(table, cellContent);

      cellContent = new Paragraph(getText("summaries.project.activities.location.longitude"), TABLE_HEADER_FONT);
      addTableHeaderCell(table, cellContent);

      cellContent = new Paragraph(getText("summaries.project.activities.location.name"), TABLE_HEADER_FONT);
      addTableHeaderCell(table, cellContent);

      int c = 0;
      for (Location location : activity.getLocations()) {
        String locationType;

        if (location.isRegion()) {
          locationType = getText("summaries.project.activities.region");
          latitude = longitude = -999;
        } else if (location.isCountry()) {
          locationType = getText("summaries.project.activities.country");
          latitude = longitude = -999;
        } else if (location.isClimateSmartVillage()) {
          locationType = getText("summaries.project.activities.csv");
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
        addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, c % 2);

        text =
          (latitude == -999) ? getText("summaries.project.activities.location.notApplicable") : String
            .valueOf(latitude);
        cellContent = new Paragraph(text, TABLE_BODY_FONT);
        addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, c % 2);

        text =
          (longitude == -999) ? getText("summaries.project.activities.location.notApplicable") : String
            .valueOf(longitude);
        cellContent = new Paragraph(text, TABLE_BODY_FONT);
        addTableBodyCell(table, cellContent, Element.ALIGN_CENTER, c % 2);

        cellContent = new Paragraph(location.getName(), TABLE_BODY_FONT);
        addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, c % 2);

        c++;
      }

      document.add(activityLocation);
      document.add(new Chunk().NEWLINE);

      document.add(table);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addActivityMainInformation(Activity activity) {
    Paragraph activityTitle = new Paragraph();
    activityTitle.setAlignment(Element.ALIGN_JUSTIFIED);
    activityTitle.setFont(HEADING4_FONT);

    activityTitle.add(getActivityID(activity) + ": ");

    activityTitle.setFont(BODY_TEXT_FONT);
    activityTitle.add(activity.getTitle());

    LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -7);
    activityTitle.add(line);


    Paragraph activityDescription = new Paragraph();
    activityDescription.setAlignment(Element.ALIGN_JUSTIFIED);
    activityDescription.setFont(HEADING4_FONT);

    activityDescription.add(getText("summaries.project.activities.description"));

    activityDescription.setFont(BODY_TEXT_FONT);
    activityDescription.add(activity.getDescription());

    try {
      document.add(activityTitle);
      document.add(new Chunk().NEWLINE);

      addActivityMainInformationTable(activity);
      document.add(new Chunk().NEWLINE);

      document.add(activityDescription);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
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
      startDate = getText("summaries.project.empty");
    }

    if (activity.getEndDate() != null) {
      endDate = new SimpleDateFormat("yyyy-MM-dd").format(activity.getEndDate());
    } else {
      endDate = getText("summaries.project.empty");
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
      cellContent = new Paragraph(getText("summaries.project.startDate"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(startDate, TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(getText("summaries.project.endDate"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(endDate, TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Second row
      cellContent = new Paragraph(getText("summaries.project.activities.activityLeader"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      if (activity.getLeader() != null) {
        cellContent = new Paragraph(activity.getLeader().getComposedName(), TABLE_BODY_FONT);
      } else {
        cellContent = new Paragraph(activity.getExpectedLeader().getComposedName(), TABLE_BODY_FONT);
      }
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(getText("summaries.project.activities.leadOrganization"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      if (activity.getLeader() != null) {
        cellContent = new Paragraph(activity.getLeader().getCurrentInstitution().getName(), TABLE_BODY_FONT);
      } else {
        cellContent = new Paragraph(activity.getExpectedLeader().getCurrentInstitution().getName(), TABLE_BODY_FONT);
      }

      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Third row
      cellContent = new Paragraph(getText("summaries.project.activities.budgetw1w2"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      amount = budgetManager.calculateActivityBudgetByType(activity.getId(), BudgetType.ACTIVITY_W1_W2.getValue());
      cellContent = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(getText("summaries.project.activities.budgetw3bil"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      amount =
        budgetManager.calculateActivityBudgetByType(activity.getId(), BudgetType.ACTIVITY_W3_BILATERAL.getValue());
      cellContent = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

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

    activityOutcome.add(getText("summaries.project.activities.outcome"));

    activityOutcome.setFont(BODY_TEXT_FONT);

    String outcome = (activity.getOutcome() == null) ? getText("summaries.project.empty") : activity.getOutcome();
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

    Paragraph title = new Paragraph(getText("summaries.project.activities.activityPartners"), HEADING4_FONT);
    title.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);

    int c = 1;
    for (ActivityPartner partner : activity.getActivityPartners()) {
      Paragraph partnerInfo = new Paragraph();
      partnerInfo.setAlignment(Element.ALIGN_JUSTIFIED);

      String partnerLabel = getText("summaries.project.partner") + c + ": ";

      // Contact information
      partnerInfo.setFont(BODY_TEXT_BOLD_FONT);
      partnerInfo.add(partnerLabel);

      partnerInfo.setFont(BODY_TEXT_FONT);
      partnerInfo.add(partner.getComposedName());
      partnerInfo.add(new Chunk().NEWLINE);

      // Organization
      partnerInfo.setFont(BODY_TEXT_BOLD_FONT);
      partnerInfo.add(getText("summaries.project.organization"));

      partnerInfo.setFont(BODY_TEXT_FONT);
      partnerInfo.add(partner.getPartner().getName());
      partnerInfo.add(new Chunk().NEWLINE);

      // Responsibilities
      partnerInfo.setFont(BODY_TEXT_BOLD_FONT);
      partnerInfo.add(getText("summaries.project.activities.activityPartners.contribution"));

      partnerInfo.setFont(BODY_TEXT_FONT);
      if (partner.getContribution() != null && !partner.getContribution().isEmpty()) {
        partnerInfo.add(partner.getContribution());
      } else {
        partnerInfo.add(getText("summaries.project.empty"));
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
      cellContent = new Paragraph(getText("summaries.project.startDate"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(startDate, TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(getText("summaries.project.endDate"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(endDate, TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Second row
      cellContent = new Paragraph(getText("summaries.project.managementLiaison"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent =
        new Paragraph(project.getOwner().getCurrentInstitution().getProgram().getAcronym(), TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(getText("summaries.project.contactPerson"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(project.getOwner().getComposedName(), TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Third row
      cellContent = new Paragraph(getText("summaries.project.leadOrganization"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(project.getLeader().getCurrentInstitution().getName(), TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(getText("summaries.project.projectLeader"), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(project.getLeader().getComposedName(), TABLE_BODY_FONT);
      addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      document.add(table);
      document.add(new Chunk().NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
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
    cell.add("3. " + getText("summaries.project.budget"));


    // Institutions
    try {
      document.add(cell);

      // Summary table
      addProjectBudgetSummary(startYear, endYear, currencyFormatter);

      List<Institution> institutions = new ArrayList<>();
      institutions.add(project.getLeader().getCurrentInstitution());

      for (ProjectPartner partner : project.getProjectPartners()) {
        institutions.add(partner.getPartner());
      }

      for (int i = 0; i < institutions.size(); i++) {
        Institution institution = institutions.get(i);

        PdfPTable table = new PdfPTable(endYear - startYear + 2);
        table.setLockedWidth(true);
        table.setTotalWidth(80 * (endYear - startYear + 2));
        table.setWidths(getBudgetTableColumnWidths(startYear, endYear));
        table.setHeaderRows(1);
        table.setKeepTogether(true);

        String name;
        if (i == 0) {
          name = institution.getName() + " " + getText("summaries.project.organization.lead");
        } else {
          name = institution.getName();
        }

        // Add cell with the institution name
        cell = new Paragraph(institution.getName(), BODY_TEXT_BOLD_FONT);
        addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        // Headers
        addTableHeaderCell(table, new Paragraph());
        for (int year = startYear; year <= endYear; year++) {
          cell = new Paragraph(String.valueOf(year), TABLE_HEADER_FONT);
          addTableHeaderCell(table, cell);
        }

        for (BudgetType bt : budgetTypes) {
          cell = new Paragraph(String.valueOf(bt.name().toLowerCase().replace("_", " + ")), TABLE_BODY_BOLD_FONT);
          addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          for (int year = startYear; year <= endYear; year++) {
            Budget b = project.getBudget(institution.getId(), bt.getValue(), year);
            amount = (b == null) ? 0 : b.getAmount();
            cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);

            addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
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
      table.setWidths(getBudgetTableColumnWidths(startYear, endYear));
      table.setKeepTogether(true);
      table.setHeaderRows(2);

      // Add cell with the institution name
      cell = new Paragraph(getText("summaries.project.budget.summary"), BODY_TEXT_BOLD_FONT);
      addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
        table.getNumberOfColumns(), 0, false);

      // Headers
      addTableHeaderCell(table, new Paragraph());
      for (int year = startYear; year <= endYear; year++) {
        cell = new Paragraph(String.valueOf(year), TABLE_HEADER_FONT);
        addTableHeaderCell(table, cell);
      }

      // Window 1 and 2
      cell =
        new Paragraph(String.valueOf(BudgetType.W1_W2.name().toLowerCase().replace("_", " + ")), TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      for (int year = startYear; year <= endYear; year++) {
        amount = budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
        cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);

        addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      }

      // Window 1 and 2 + Window 3 + bilateral
      String text = String.valueOf(BudgetType.W1_W2.name().toLowerCase().replace("_", " + "));
      text += " + " + String.valueOf(BudgetType.W3_BILATERAL.name().toLowerCase().replace("_", " + "));

      cell = new Paragraph(text, TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      for (int year = startYear; year <= endYear; year++) {
        amount = budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
        amount +=
          budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL.getValue(), year);

        cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
        addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      }

      // Leveraged
      cell =
        new Paragraph(String.valueOf(BudgetType.LEVERAGED.name().toLowerCase().replace("_", " + ")),
          TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      for (int year = startYear; year <= endYear; year++) {
        amount =
          budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.LEVERAGED.getValue(), year);
        cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);

        addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      }

      // Gender
      text = String.valueOf(BudgetType.W1_W2_GENDER.name().toLowerCase().replace("_", " + "));
      text += " + " + String.valueOf(BudgetType.W3_BILATERAL_GENDER.name().toLowerCase().replace("_", " + "));

      cell = new Paragraph(text, TABLE_BODY_BOLD_FONT);
      addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      for (int year = startYear; year <= endYear; year++) {
        amount =
          budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2_GENDER.getValue(), year);
        amount +=
          budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL_GENDER.getValue(),
            year);

        cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
        addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
      }

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
    paragraph.add(getText("summaries.project.ipContributions"));

    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(projectFocuses.substring(0, projectFocuses.length() - 2));

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

    Paragraph title = new Paragraph("5. " + getText("summaries.project.indicatorsContribution"), HEADING3_FONT);
    indicatorsBlock.add(title);
    indicatorsBlock.add(new Chunk().NEWLINE);

    try {
      document.add(indicatorsBlock);

      for (IPElement outcome : getMidOutcomesPerIndicators()) {
        Paragraph outcomeBlock = new Paragraph();
        int indicatorIndex = 1;

        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(outcome.getProgram().getAcronym());
        outcomeBlock.add(" - " + getText("summaries.project.midoutcome"));

        outcomeBlock.setFont(BODY_TEXT_FONT);
        outcomeBlock.add(outcome.getDescription());
        outcomeBlock.add(new Chunk().NEWLINE);
        outcomeBlock.add(new Chunk().NEWLINE);

        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(getText("summaries.project.indicators"));
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
          cell = new Paragraph(getText("summaries.project.indicator.year"), TABLE_HEADER_FONT);
          addTableHeaderCell(table, cell);
          cell = new Paragraph(getText("summaries.project.indicator.targetValue"), TABLE_HEADER_FONT);
          addTableHeaderCell(table, cell);
          cell = new Paragraph(getText("summaries.project.indicator.targetNarrative"), TABLE_HEADER_FONT);
          addTableHeaderCell(table, cell);

          for (IPIndicator indicator : indicators) {

            if (indicator.getOutcome().getId() != outcome.getId()) {
              continue;
            }
            cell = new Paragraph(String.valueOf(indicator.getYear()), TABLE_BODY_FONT);
            addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

            cell = new Paragraph(String.valueOf(indicator.getTarget()), TABLE_BODY_FONT);
            addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            cell = new Paragraph(String.valueOf(indicator.getDescription()), TABLE_BODY_FONT);
            addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

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

  private void addProjectOutcome() {
    String outcomeProgress;
    Paragraph outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);

    Paragraph title = new Paragraph("4. " + getText("summaries.project.outcomeNarrative"), HEADING3_FONT);
    outcomesBlock.add(title);
    outcomesBlock.add(new Chunk().NEWLINE);

    // Project outcome statement
    Paragraph body = new Paragraph();
    body.setFont(BODY_TEXT_BOLD_FONT);
    body.add(getText("summaries.project.outcomeStatement"));
    body.add(new Chunk().NEWLINE);

    body.setFont(BODY_TEXT_FONT);
    if (project.getOutcomes() != null) {
      body.add(project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement());
    } else {
      body.add(getText("summaries.project.empty"));
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
      outcomesBlock.add(getText("summaries.project.outcomeAnnualProgress", new String[] {String.valueOf(year)}));
      outcomesBlock.add(new Chunk().NEWLINE);

      if (project.getOutcomes().get(year) == null) {
        outcomeProgress = getText("summaries.project.empty");
      } else {
        outcomeProgress = project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement();
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
  }

  private void addProjectPartners() {
    Paragraph partnersBlock = new Paragraph();
    partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.setKeepTogether(true);

    Paragraph title = new Paragraph(32, "2. " + getText("summaries.project.projectPartners"), HEADING3_FONT);
    partnersBlock.add(title);
    partnersBlock.add(new Chunk().NEWLINE);

    int c = 1;
    for (ProjectPartner partner : project.getProjectPartners()) {
      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      // paragraph.setLeading(125);
      paragraph.setKeepTogether(true);
      String partnerLabel = getText("summaries.project.partner") + c + ": ";

      // Contact information
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(partnerLabel);

      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(partner.getComposedName());
      paragraph.add(new Chunk().NEWLINE);

      // Organization
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(getText("summaries.project.organization"));

      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(partner.getPartner().getName());
      paragraph.add(new Chunk().NEWLINE);

      // Responsibilities
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(getText("summaries.project.responsibilities"));

      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(partner.getResponsabilities());
      paragraph.add(new Chunk().NEWLINE);

      partnersBlock.add(paragraph);

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

    paragraph.add(getProjectID());
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

    Phrase title = new Phrase("1. " + getText("summaries.project.summary"), HEADING3_FONT);
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

  public void generatePdf(Project project, int currentPlanningYear, int midOutcomeYear) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    this.document = new Document(PageSize.A4, 57, 57, 60, 57);
    this.project = project;
    this.midOutcomeYear = midOutcomeYear;
    this.currentPlanningYear = currentPlanningYear;
    this.setSummaryTitle(getProjectID());

    PdfWriter writer = initializePdf(document, outputStream, PORTRAIT);

    // Adding the event to include header and footer on each page
    HeaderFooterPDF event = new HeaderFooterPDF(summaryTitle, PORTRAIT);
    writer.setPageEvent(event);

    // Open document
    document.open();

    // Project content
    addProjectTitle();
    addMainInformationTable();
    addProjectContributions();
    addSummary();
    addProjectPartners();
    addProjectBudget();
    addProjectOutcome();
    addProjectIndicators();
    addActivities();

    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();
    inputStream = (new ByteArrayInputStream(outputStream.toByteArray()));
  }

  private String getActivityID(Activity activity) {
    StringBuilder activityID = new StringBuilder();
    activityID.append(getText("summaries.project.activity"));
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

  private String getProjectID() {
    StringBuilder projectID = new StringBuilder();
    projectID.append(getText("summaries.project.project"));
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
    projectID.append(project.getLeader().getCurrentInstitution().getAcronym());

    return projectID.toString();
  }

  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
