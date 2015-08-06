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

import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOutcomeManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
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
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
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
  private int contentLength;
  int currentPlanningYear;
  private Document document;
  private APConfig config;
  private ProjectContributionOverviewManager overviewManager;
  private ProjectOutcomeManager projectOutcomeManager;
  private IPElementManager elementManager;
  private InputStream inputStream;
  int midOutcomeYear;
  private Project project;

  // Attributes
  private String summaryTitle;


  // Budget
  @Inject
  public ProjectSummaryPDF(APConfig config, BudgetManager budgetManager, IPElementManager elementManager,
    ActivityManager activityManager, IPCrossCuttingManager ipCrossCuttingManager,
    ActivityPartnerManager activityPartnerManager, LocationManager locationManager,
    DeliverableManager deliverableManager, NextUserManager nextUserManager,
    ProjectContributionOverviewManager overviewManager, ProjectOutcomeManager projectOutcomeManager) {
    this.config = config;
    this.initialize(config.getBaseUrl());
    this.budgetManager = budgetManager;
    this.elementManager = elementManager;
    this.overviewManager = overviewManager;
    this.projectOutcomeManager = projectOutcomeManager;

  }

  /**
   * @param startYear
   * @param endYear
   * @param currencyFormatter
   */
  private void add_Budgets_Summary_By_Partners(int startYear, int endYear) {
    try {
      Paragraph cell = new Paragraph(this.getText("summaries.project.budget.summary"), BODY_TEXT_BOLD_FONT);


      PdfPTable table = new PdfPTable(2);
      table.setLockedWidth(true);
      table.setTotalWidth(400);
      table.setHeaderRows(1);
      table.setKeepTogether(true);

      Locale locale = new Locale("en", "US");
      NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

      // Add cell with the name summary
      this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
        table.getNumberOfColumns(), 0, false);


      cell = new Paragraph(this.getText("summaries.project.budget.overall.text"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);

      cell = new Paragraph(this.getText("summaries.project.budget.overall.value"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);

      for (int year = startYear; year <= endYear; year++) {
        // if it is CCAFS_CORE
        if (project.isCoreProject()) {
          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W1_W2.name(),
              String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
          cell =
            new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager
              .calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year))));

        } else
        // if it is BILATERAL
        if (project.isBilateralProject()) {
          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {
              BudgetType.W3_BILATERAL.name(), String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          cell =
            new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager
              .calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL.getValue(), year))));

        } else
        // if it is COFOUNDED
        if (project.isCoFundedProject()) {

          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W1_W2.name(),
              String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          cell =
            new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager
              .calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year))));
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {
              BudgetType.W3_BILATERAL.name(), String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          cell =
            new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager
              .calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL.getValue(), year))));
        }

        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

      }

      document.add(table);
      document.add(Chunk.NEWLINE);

      // ********************** Totals *************
      table = new PdfPTable(2);
      // table.setLockedWidth(true);
      table.setTotalWidth(400);
      table.setHeaderRows(1);
      table.setKeepTogether(true);

      cell = new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);

      cell = new Paragraph(this.getText("summaries.project.budget.overall.value"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);
      currencyFormatter = NumberFormat.getCurrencyInstance(locale);

      // if it is CCAFS_CORE
      if (project.isCoreProject()) {
        cell =
          new Paragraph(this.getText("summaries.project.budget.overall.cumulative",
            new String[] {BudgetType.W1_W2.name()}), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        cell =
          new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(
            project.getId(), BudgetType.W1_W2.getValue()))));
      } else
      // if it is BILATERAL
      if (project.isBilateralProject()) {
        cell =
          new Paragraph(this.getText("summaries.project.budget.overall.cumulative",
            new String[] {BudgetType.W3_BILATERAL.name()}), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        cell =
          new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(
            project.getId(), BudgetType.W3_BILATERAL.getValue()))));

      } else
      // if it is COFOUNDED
      if (project.isCoFundedProject()) {

        cell =
          new Paragraph(this.getText("summaries.project.budget.overall.cumulative",
            new String[] {BudgetType.W1_W2.name()}), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        cell =
          new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(
            project.getId(), BudgetType.W1_W2.getValue()))));
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

        cell =
          new Paragraph(this.getText("summaries.project.budget.overall.cumulative",
            new String[] {BudgetType.W3_BILATERAL.name()}), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        cell =
          new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(
            project.getId(), BudgetType.W3_BILATERAL.getValue()))));
      }

      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

      document.add(table);
      document.add(Chunk.NEWLINE);

    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }


  }


  private void addActivities() {

    try {
      document.newPage();
      Paragraph activityBlock = new Paragraph("6. " + this.getText("summaries.project.activity"), HEADING2_FONT);
      activityBlock.setAlignment(Element.ALIGN_JUSTIFIED);
      activityBlock.add(Chunk.NEWLINE);
      activityBlock.add(Chunk.NEWLINE);
      document.add(activityBlock);
      PdfPTable table;
      for (Activity activity : project.getActivities()) {

        if (activity != null) {

          table = new PdfPTable(2);
          table.setTotalWidth(480);
          table.setLockedWidth(true);


          // Header table
          activityBlock = new Paragraph();
          activityBlock.setAlignment(Element.ALIGN_CENTER);
          activityBlock.setFont(BODY_TEXT_BOLD_FONT);
          activityBlock.add("Activity #" + activity.getId());

          this.addCustomTableCell(table, activityBlock, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
            table.getNumberOfColumns(), 0, false);

          // Activity title
          activityBlock = new Paragraph();
          activityBlock.setFont(TABLE_BODY_BOLD_FONT);
          activityBlock.add(this.getText("summaries.project.activities.title"));

          activityBlock.setFont(TABLE_BODY_FONT);
          activityBlock.add(activity.getTitle());
          activityBlock.add(Chunk.NEWLINE);
          this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);

          // Activity description
          activityBlock = new Paragraph();
          activityBlock.setFont(TABLE_BODY_BOLD_FONT);
          activityBlock.add(this.getText("summaries.project.activities.description"));

          activityBlock.setFont(TABLE_BODY_FONT);
          activityBlock.add(activity.getDescription());
          activityBlock.add(Chunk.NEWLINE);
          this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);

          String startDate = new SimpleDateFormat("dd-MM-yyyy").format(activity.getStartDate());
          String endDate = new SimpleDateFormat("dd-MM-yyyy").format(activity.getEndDate());

          // Activity Start Date
          activityBlock = new Paragraph();
          activityBlock.setFont(TABLE_BODY_BOLD_FONT);
          activityBlock.add(this.getText("summaries.project.startDate") + ": ");

          activityBlock.setFont(TABLE_BODY_FONT);
          activityBlock.add(startDate);
          activityBlock.add(Chunk.NEWLINE);
          this.addTableBodyCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1);

          // Activity End Date
          activityBlock = new Paragraph();
          activityBlock.setFont(TABLE_BODY_BOLD_FONT);
          activityBlock.add(this.getText("summaries.project.endDate") + ": ");

          activityBlock.setFont(TABLE_BODY_FONT);
          activityBlock.add(endDate);
          activityBlock.add(Chunk.NEWLINE);
          this.addTableBodyCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1);

          // Activity Leader
          activityBlock = new Paragraph();
          activityBlock.setFont(TABLE_BODY_BOLD_FONT);
          activityBlock.add(this.getText("summaries.project.activities.activityLeader") + ": ");

          activityBlock.setFont(TABLE_BODY_FONT);
          activityBlock.add(activity.getLeader().getComposedName());
          activityBlock.add(Chunk.NEWLINE);
          this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);
          // document.add(Chunk.NEWLINE);
          document.add(table);
          activityBlock = new Paragraph();
          activityBlock.add(Chunk.NEWLINE);
          document.add(activityBlock);
        }


      }

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project activities to the project summary pdf of project {} ", e,
        project.getId());
    }

  }

  /**
   * @param cell
   * @param institution PPA to calculate your budget
   * @param startYear
   * @param endYear
   * @param table
   * @param budgetType
   * @return
   */
  private void addBudgetsByPartners(Paragraph cell, Institution institution, int year, PdfPTable table,
    BudgetType budgetType) {
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);


    Budget budget = project.getBudget(institution.getId(), budgetType.getValue(), year);
    if (budget != null) {
      // Anual budget
      cell = new Paragraph("Annual Budget " + budgetType.name().toString(), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      cell = new Paragraph(currencyFormatter.format(budget.getAmount()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

      // Gender percentaje
      currencyFormatter = NumberFormat.getPercentInstance(locale);
      cell = new Paragraph("Gender percentage " + budgetType.name().toString(), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      cell = new Paragraph(currencyFormatter.format(budget.getGenderPercentage()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
    }
  }

  private void addDelivable(Deliverable deliverable) {
    try {
      if (deliverable != null) {
        //
        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(480);
        table.setLockedWidth(true);


        StringBuilder stringBuilder = new StringBuilder();

        // **** Expected Deliverable #*********
        Paragraph deliverableBlock = new Paragraph();

        deliverableBlock.setFont(HEADING4_FONT);

        deliverableBlock.add(this.getText("summaries.project.deliverable.expected") + " #" + deliverable.getId());
        deliverableBlock.add(Chunk.NEWLINE);
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

        // **** Deliverable Information *********
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);

        deliverableBlock.add(this.getText("summaries.project.deliverable.information"));
        // document.add(deliverableBlock);
        this.addCustomTableCell(table, deliverableBlock, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        // Title
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.information.title") + " : ");

        deliverableBlock.setFont(TABLE_BODY_FONT);
        deliverableBlock.add(deliverable.getTitle());
        deliverableBlock.add(Chunk.NEWLINE);;
        // document.add(deliverableBlock);
        this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 2);

        // MOG
        deliverableBlock = new Paragraph();
        stringBuilder = new StringBuilder();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        stringBuilder.append(this.getText("summaries.project.deliverable.information.mog"));
        stringBuilder.append(" : ");
        deliverableBlock.add(stringBuilder.toString());

        deliverableBlock.setFont(TABLE_BODY_FONT);
        stringBuilder = new StringBuilder();
        if (deliverable.getOutput() != null) {
          stringBuilder.append(deliverable.getOutput().getDescription());
        } else {
          stringBuilder.append(this.getText("summaries.project.empty"));
        }
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);
        // document.add(deliverableBlock);
        this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 2);

        // Year
        deliverableBlock = new Paragraph();
        stringBuilder = new StringBuilder();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        stringBuilder.append(this.getText("summaries.project.deliverable.information.year"));
        stringBuilder.append(" : ");
        deliverableBlock.add(stringBuilder.toString());

        deliverableBlock.setFont(TABLE_BODY_FONT);
        stringBuilder = new StringBuilder();
        stringBuilder.append(deliverable.getYear());
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);
        // document.add(deliverableBlock);
        this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 2);

        // Main Type
        deliverableBlock = new Paragraph();
        stringBuilder = new StringBuilder();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        stringBuilder.append(this.getText("summaries.project.deliverable.information.main"));
        stringBuilder.append(" : ");
        deliverableBlock.add(stringBuilder.toString());

        deliverableBlock.setFont(TABLE_BODY_FONT);
        stringBuilder = new StringBuilder();
        stringBuilder.append(deliverable.getType().getCategory().getName());
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);
        // document.add(deliverableBlock);
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

        // Sub Type
        deliverableBlock = new Paragraph();
        stringBuilder = new StringBuilder();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        stringBuilder.append(this.getText("summaries.project.deliverable.information.sub"));
        stringBuilder.append(" : ");
        deliverableBlock.add(stringBuilder.toString());

        deliverableBlock.setFont(TABLE_BODY_FONT);
        stringBuilder = new StringBuilder();

        if (deliverable.getType().getName() != null) {
          stringBuilder.append(deliverable.getType().getName());
        }
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);;
        // document.add(deliverableBlock);
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

        // ********** Next Users**************************************


        int counter = 1;
        List<NextUser> nextUsers = deliverable.getNextUsers();
        for (NextUser nextUser : nextUsers) {
          if (nextUser != null) {

            table = new PdfPTable(1);
            table.setTotalWidth(480);
            table.setLockedWidth(true);

            // Next user title
            deliverableBlock = new Paragraph();
            deliverableBlock.setAlignment(Element.ALIGN_LEFT);
            deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
            if (nextUsers.size() == 1) {
              deliverableBlock.add(this.getText("summaries.project.deliverable.next.user"));
            } else {
              deliverableBlock.add(this.getText("summaries.project.deliverable.next.user") + " #" + counter);
            }

            this.addCustomTableCell(table, deliverableBlock, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
              table.getNumberOfColumns(), 0, false);

            // Next user
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user") + " : ");
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(nextUser.getUser());
            deliverableBlock.add(stringBuilder.toString());
            // document.add(deliverableBlock);
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Expected Changes
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user.strategies") + " : ");
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(nextUser.getExpectedChanges());
            deliverableBlock.add(stringBuilder.toString());
            // document.add(deliverableBlock);
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Strategies
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user.expected.change") + " : ");
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(nextUser.getStrategies());
            deliverableBlock.add(stringBuilder.toString());
            // document.add(deliverableBlock);
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            document.add(table);
            deliverableBlock = new Paragraph();
            deliverableBlock.add(Chunk.NEWLINE);
            document.add(deliverableBlock);

            counter++;
          }

        }

        // ********** Deliverable partnership**************************************

        // ******************Partnership responsible

        if (deliverable.getResponsiblePartner().getPartner() != null) {
          table = new PdfPTable(1);
          table.setLockedWidth(true);
          table.setTotalWidth(480);

          // Title partners contributing
          deliverableBlock = new Paragraph();
          deliverableBlock.setAlignment(Element.ALIGN_LEFT);
          deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
          deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.responsible"));
          this.addCustomTableCell(table, deliverableBlock, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
            table.getNumberOfColumns(), 0, false);

          // Organization
          stringBuilder = new StringBuilder();
          deliverableBlock = new Paragraph();
          deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
          deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.organization") + " : ");
          deliverableBlock.setFont(TABLE_BODY_FONT);

          stringBuilder.append(this.messageReturn(deliverable.getResponsiblePartner().getPartner().getUser()
            .getComposedName()));

          deliverableBlock.add(stringBuilder.toString());
          deliverableBlock.add(Chunk.NEWLINE);;
          this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

          // Contact Email
          stringBuilder = new StringBuilder();
          deliverableBlock = new Paragraph();
          deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
          deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.email") + " : ");
          deliverableBlock.setFont(TABLE_BODY_FONT);
          stringBuilder.append(this
            .messageReturn(deliverable.getResponsiblePartner().getPartner().getUser().getEmail()));


          deliverableBlock.add(stringBuilder.toString());
          deliverableBlock.add(Chunk.NEWLINE);
          this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

          document.add(table);
          deliverableBlock = new Paragraph();
          deliverableBlock.add(Chunk.NEWLINE);
          document.add(deliverableBlock);

        }
        counter = 0;
        // ************** Other Partners
        List<DeliverablePartner> listOtherPartner = deliverable.getOtherPartners();
        if (!listOtherPartner.isEmpty()) {
          for (DeliverablePartner deliverablePartner : listOtherPartner) {
            counter++;
            table = new PdfPTable(1);
            table.setLockedWidth(true);
            table.setTotalWidth(480);

            // Title partners contributing
            deliverableBlock = new Paragraph();
            deliverableBlock.setAlignment(Element.ALIGN_LEFT);
            deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
            if (listOtherPartner.size() == 1) {
              deliverableBlock.add(this.getText("summaries.project.deliverable.partnership"));
            } else {
              deliverableBlock.add(this.getText("summaries.project.deliverable.partnership") + " #" + counter);
            }

            this.addCustomTableCell(table, deliverableBlock, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
              table.getNumberOfColumns(), 0, false);


            // Organization
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.organization") + " : ");

            deliverableBlock.setFont(TABLE_BODY_FONT);

            if (deliverablePartner.getPartner() != null) {
              stringBuilder.append(deliverablePartner.getPartner().getInstitution().getComposedName());
            } else {
              stringBuilder.append(this.getText("summaries.project.empty"));
            }

            deliverableBlock.add(stringBuilder.toString());
            deliverableBlock.add(Chunk.NEWLINE);;
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Contact Email
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.email") + " : ");

            deliverableBlock.setFont(TABLE_BODY_FONT);
            if (deliverablePartner.getPartner() != null) {
              stringBuilder.append(deliverablePartner.getPartner().getUser().getEmail());
            } else {
              stringBuilder.append(this.getText("summaries.project.empty"));
            }

            deliverableBlock.add(stringBuilder.toString());
            deliverableBlock.add(Chunk.NEWLINE);
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            document.add(table);
            deliverableBlock = new Paragraph();
            deliverableBlock.add(Chunk.NEWLINE);
            document.add(deliverableBlock);
          }

        }
      }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  private void addMainInformationTable() {
    String startDate = new SimpleDateFormat("dd-MM-yyyy").format(project.getStartDate());
    String endDate = new SimpleDateFormat("dd-MM-yyyy").format(project.getEndDate());
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
      cellContent =
        new Paragraph(this.getText("summaries.project.startDate") + "\n" + " (dd-MM-yyyy)", TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(this.messageReturn(startDate), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent =
        new Paragraph(this.getText("summaries.project.endDate") + "\n" + " (dd-MM-yyyy)", TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(this.messageReturn(endDate), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Second row
      cellContent = new Paragraph(this.getText("summaries.project.managementLiaison"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn(project.getLiaisonInstitution().getName()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(this.getText("summaries.project.contactPerson"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn(project.getOwner().getComposedName()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Third row
      cellContent = new Paragraph(this.getText("summaries.project.leadOrganization"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent =
        new Paragraph(this.messageReturn(this.messageReturn(project.getLeader().getInstitution().getAcronym())),
          TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      cellContent = new Paragraph(this.getText("summaries.project.projectLeader"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      cellContent = new Paragraph(this.messageReturn(project.getLeader().getUser().getComposedName()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Fourth row
      cellContent = (new Paragraph(this.getText("summaries.project.projectType"), TABLE_BODY_BOLD_FONT));
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn(project.getType().replaceAll("_", " ")), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);


      cellContent = (new Paragraph(this.getText("summaries.project.detailed"), TABLE_BODY_BOLD_FONT));
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn("http://www.google.com"), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // this.addTableColSpanCell(table, new Paragraph(), Element.ALIGN_LEFT, 1, 2);

      document.add(table);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  private void addOverview(PdfPTable table, IPElement mog, int year) {

    // Paragraph paragraph = new Paragraph();

    Paragraph overviewBlock = new Paragraph();
    overviewBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    // overviewBlock.setKeepTogether(true);
    StringBuffer overviewLabel = new StringBuffer();

    // Get OverviewByMog, Year and project
    List<OutputOverview> listOver =
      overviewManager.getProjectContributionOverviewsByYearAndOutput(project, year, mog.getId());
    OutputOverview overviewYear = null;
    if (listOver.size() != 0) {
      overviewYear = overviewManager.getProjectContributionOverviewsByYearAndOutput(project, year, mog.getId()).get(0);
    }
    // Mog contribution
    overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
    overviewLabel.append(mog.getProgram().getAcronym());
    overviewLabel.append(" - MOG # ");
    overviewLabel.append(this.getMOGIndex(mog));
    overviewLabel.append(": ");
    overviewBlock.add(overviewLabel.toString());

    overviewLabel = new StringBuffer();
    overviewBlock.setFont(TABLE_BODY_FONT);
    overviewLabel.append(this.messageReturn(mog.getDescription()));
    overviewBlock.add(overviewLabel.toString());

    overviewLabel = new StringBuffer();
    overviewBlock.add(Chunk.NEWLINE);
    overviewBlock.add(Chunk.NEWLINE);

    // Expected Annual Contribution title
    overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
    overviewLabel.append(this.getText("summaries.project.expected.contribution.first"));
    overviewLabel.append(year);
    overviewLabel.append(" " + this.getText("summaries.project.expected.contribution.last") + " : ");
    overviewBlock.add(overviewLabel.toString());

    // Expected Annual Contribution text
    overviewBlock.setFont(TABLE_BODY_FONT);
    overviewLabel = new StringBuffer();

    if (overviewYear != null) {
      overviewLabel.append(this.messageReturn(overviewYear.getExpectedAnnualContribution()));
    } else {
      overviewLabel.append(this.getText("summaries.project.empty"));
    }

    overviewBlock.add(overviewLabel.toString());
    overviewBlock.add(Chunk.NEWLINE);
    overviewBlock.add(Chunk.NEWLINE);

    // Social Annual Contribution title
    overviewLabel = new StringBuffer();
    overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
    overviewLabel.append(this.getText("summaries.project.social.contribution") + " : ");
    overviewBlock.add(overviewLabel.toString());

    // Social Annual Contribution text
    overviewBlock.setFont(TABLE_BODY_FONT);
    overviewLabel = new StringBuffer();
    if (overviewYear != null) {
      overviewLabel.append(this.messageReturn(overviewYear.getSocialInclusionDimmension()));
    } else {
      overviewLabel.append(this.getText("summaries.project.empty"));
    }

    overviewBlock.add(overviewLabel.toString());
    overviewBlock.add(Chunk.NEWLINE);
    overviewBlock.add(Chunk.NEWLINE);

    this.addTableBodyCell(table, overviewBlock, Element.ALIGN_LEFT, 1);


  }

  private void addProjectBudgets() {

    int startYear, endYear;

    Paragraph cell = new Paragraph();

    Calendar startDate = Calendar.getInstance();
    startDate.setTime(project.getStartDate());
    startYear = startDate.get(Calendar.YEAR);

    Calendar endDate = Calendar.getInstance();
    endDate.setTime(project.getEndDate());
    endYear = endDate.get(Calendar.YEAR);

    PdfPTable table;
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    cell.setFont(HEADING3_FONT);
    cell.add("7. " + this.getText("summaries.project.budget"));

    // ************************Budget By Partners*************************************

    List<ProjectPartner> listProjectPartner = this.removePartnersRepeat(project.getPPAPartners());

    cell.setFont(HEADING4_FONT);
    cell.add(Chunk.NEWLINE);
    cell.add(Chunk.NEWLINE);
    cell.add("7.1 " + this.getText("summaries.project.budget.partners"));
    cell.add(Chunk.NEWLINE);

    try {
      cell.add(Chunk.NEWLINE);
      cell.add(Chunk.NEWLINE);

      document.add(cell);

      // Summary table
      // this.add_Budgets_Summary_By_Partners(startYear, endYear);
      int year;
      for (year = startYear; year <= endYear; year++) {

        table = new PdfPTable(2);
        table.setLockedWidth(true);
        table.setTotalWidth(400);
        table.setHeaderRows(1);
        table.setKeepTogether(true);


        cell = new Paragraph();
        cell.setFont(TABLE_HEADER_FONT);
        cell.add(String.valueOf(year));
        PdfPCell cell_table = new PdfPCell(cell);

        // Set alignment

        cell_table.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_table.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell_table.setBackgroundColor(TABLE_HEADER_BACKGROUND);

        // Set padding
        cell_table.setUseBorderPadding(true);
        cell_table.setPadding(3);
        cell_table.setColspan(2);

        // Set border color
        cell_table.setBorderColor(TABLE_CELL_BORDER_COLOR);

        table.addCell(cell_table);


        // if it is CCAFS_CORE
        if (project.isCoreProject()) {
          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W1_W2.name(),
              String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

          cell = new Paragraph();
          cell.setFont(TABLE_BODY_FONT);
          cell.add(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(
            project.getId(), BudgetType.W1_W2.getValue(), year))));
        } else
        // if it is BILATERAL
        if (project.isBilateralProject()) {
          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {
              BudgetType.W3_BILATERAL.name(), String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

          cell = new Paragraph();
          cell.setFont(TABLE_BODY_FONT);
          cell.add(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(
            project.getId(), BudgetType.W3_BILATERAL.getValue(), year))));

        } else
        // if it is COFOUNDED
        if (project.isCoFundedProject()) {
          cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W1_W2.name(),
              String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

              cell = new Paragraph();
          cell.setFont(TABLE_BODY_FONT);
          cell.add(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(
            project.getId(), BudgetType.W1_W2.getValue(), year))));

              cell =
            new Paragraph(this.getText("summaries.project.budget.overall", new String[] {
                  BudgetType.W3_BILATERAL.name(), String.valueOf(year)}), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

              cell = new Paragraph();
          cell.setFont(TABLE_BODY_FONT);
          cell.add(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(
            project.getId(), BudgetType.W3_BILATERAL.getValue(), year))));


            }
        //
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
        document.add(table);
        document.add(Chunk.NEWLINE);;

        Institution institution;

        for (int i = 0; i < listProjectPartner.size(); i++) {

          institution = listProjectPartner.get(i).getInstitution();
          table = new PdfPTable(2);
          table.setLockedWidth(true);
          table.setTotalWidth(80 * (endYear - startYear + 2));

          table.setHeaderRows(1);
          table.setKeepTogether(true);

          // // Add cell with the institution name
          cell = new Paragraph(institution.getName(), BODY_TEXT_BOLD_FONT);
          this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
            table.getNumberOfColumns(), 0, false);

          // // *********************** When project is CCAFS_CORE ***********************************
          if (project.isCoreProject()) {
            this.addBudgetsByPartners(cell, institution, year, table, BudgetType.W1_W2);
          }

          // *********************** When project is BILATERAL ***********************************
          else if (project.isBilateralProject()) {
            this.addBudgetsByPartners(cell, institution, year, table, BudgetType.W3_BILATERAL);
          }
          // *********************** When project is COFOUNDED ***********************************
          else if (project.isCoFundedProject()) {
            this.addBudgetsByPartners(cell, institution, year, table, BudgetType.W1_W2);
            this.addBudgetsByPartners(cell, institution, year, table, BudgetType.W3_BILATERAL);
          }
          document.add(table);
          document.add(Chunk.NEWLINE);;

        }
      }

    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }


    // ************************Budget By Mogs*************************************
    // cell.setFont(HEADING4_FONT);
    // cell.add(Chunk.NEWLINE);
    // cell.add("3.2 " + this.getText("summaries.project.budget.mog"));
    //
    // locale = new Locale("en", "US");


    // this.addTableHeaderCell(table, new Paragraph());

    // try {
    // cell.add(Chunk.NEWLINE);
    // cell.add(Chunk.NEWLINE);
    // document.add(cell);

    // for (int year = startYear; year <= endYear; year++) {
    // table = new PdfPTable(2);

    // Title Year
    // cell = new Paragraph(String.valueOf(year), BODY_TEXT_BOLD_FONT);
    // this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
    // table.getNumberOfColumns(), 0, false);

    // First row
    // currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    // cell =
    // new Paragraph(this.getText("summaries.project.budget.mog.anual", new String[] {String.valueOf(year)}),
    // TABLE_BODY_BOLD_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
    //
    // cell =
    // new Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalCCAFSBudgetByYear(
    // project.getId(), year))), BODY_TEXT_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

    // Second row
    // currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    // cell =
    // new Paragraph(this.getText("summaries.project.budget.mog.gender", new String[] {String.valueOf(year)}),
    // TABLE_BODY_BOLD_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
    //
    // cell =
    // new Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalGenderBudgetByYear(
    // project.getId(), year))), BODY_TEXT_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
    //
    // document.add(table);
    // document.add(Chunk.NEWLINE);


    // for(OutputBudget project.getOutputsBudgets())

    // Title MOG
    // table = new PdfPTable(2);

    // Title Mog
    // cell = new Paragraph(String.valueOf(year), BODY_TEXT_BOLD_FONT);
    // this.addCustomTableCell(table, cell, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
    // table.getNumberOfColumns(), 0, false);
    //
    // //Third row
    // currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    // cell = new Paragraph(this.getText("summaries.project.budget.mog.anual" , new String[] {String.valueOf(year)})
    // , BODY_TEXT_BOLD_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
    //
    // cell = new
    // Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalCCAFSBudgetByYear(project.getId(),
    // year))) , BODY_TEXT_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
    //
    // //Fourth row
    // currencyFormatter = NumberFormat.getCurrencyInstance(locale);
    // cell = new Paragraph(this.getText("summaries.project.budget.mog.gender" , new String[]
    // {String.valueOf(year)}) , BODY_TEXT_BOLD_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
    //
    // cell = new
    // Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalGenderBudgetByYear(project.getId(),
    // year))) , BODY_TEXT_FONT);
    // this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
    //

    // document.add(table);
    // cell.add(Chunk.NEWLINE);
    //
    //
    // } catch (DocumentException e) {
    // LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    // }
  }

  private void addProjectContributions() {
    try {
      PdfPTable table = new PdfPTable(1);

      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(480);
      table.setWidths(new int[] {5});

      Paragraph cell;
      StringBuffer projectFocuses = new StringBuffer();

      // ************************ Adding flagships and regions **********************************
      List<IPProgram> listIPFlagship = project.getFlagships();
      List<IPProgram> listIPRegions = project.getFlagships();

      if (!listIPFlagship.isEmpty() || !listIPRegions.isEmpty()) {
        cell = new Paragraph(this.getText("summaries.project.ipContributions"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        for (IPProgram program : listIPFlagship) {
          projectFocuses = new StringBuffer();
          if (program.getComposedName() == null || program.getComposedName().isEmpty()) {
          } else {
            cell = new Paragraph(program.getComposedName(), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
          }
        }

        // Regions
        for (IPProgram program : listIPRegions) {
          if (program.getComposedName() == null || program.getComposedName().isEmpty()) {
          } else {
            cell = new Paragraph(program.getComposedName(), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
          }
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
      } else {
        cell = new Paragraph(this.getText("summaries.project.ipContributions"), BODY_TEXT_BOLD_FONT);
        cell.setFont(BODY_TEXT_BOLD_FONT);
        cell.add(" : ");
        projectFocuses.append(this.getText("summaries.project.empty"));
        cell.setFont(BODY_TEXT_FONT);
        cell.add(projectFocuses.toString());
        document.add(cell);
        document.add(Chunk.NEWLINE);

      }


      // ******************** Adding Bilateral ********************************************
      List<Project> listLinkageProject = project.getLinkedProjects();
      cell = new Paragraph();
      projectFocuses = new StringBuffer();

      if (project.isBilateralProject()) {
        projectFocuses.append(this.getText("summaries.project.ipContributions.project", new String[] {"Core"}));
      } else {
        projectFocuses.append(this.getText("summaries.project.ipContributions.project", new String[] {"Biltareal"}));
      }
      // outcomesBlock.add(this.getText("summaries.project.outcome.gender.contributiton",
      // new String[] {String.valueOf(year)}));
      if (listLinkageProject != null && !listLinkageProject.isEmpty()) {
        table = new PdfPTable(1);

        // Set table widths
        table.setLockedWidth(true);
        table.setTotalWidth(480);

        cell.setFont(TABLE_HEADER_FONT);
        cell.add(projectFocuses.toString());
        table.setWidths(new int[] {5});

        // Adding Header
        this.addTableHeaderCell(table, cell);

        projectFocuses = new StringBuffer();
        for (Project projectContribution : project.getLinkedProjects()) {
          projectFocuses.append(projectContribution.getId());
          projectFocuses.append(" - ");
          projectFocuses.append(projectContribution.getTitle());
          cell = new Paragraph(projectFocuses.toString(), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
      } else {
        cell.setFont(BODY_TEXT_BOLD_FONT);
        cell.add(projectFocuses.toString());
        cell.add(": ");
        cell.setFont(BODY_TEXT_FONT);
        projectFocuses = new StringBuffer();

        // projectFocuses.append(this.getText("summaries.project.ipContributions.project", new String[] {"Core"}));
        // } else {
        // projectFocuses.append(this.getText("summaries.project.ipContributions.project", new String[] {"Biltareal"}));
        // }


        if (project.isBilateralProject()) {
          projectFocuses.append(this.getText("summaries.project.ipContributions.noproject", new String[] {"Core"}));
        } else {
          projectFocuses
            .append(this.getText("summaries.project.ipContributions.noproject", new String[] {"Bilateral"}));
        }
        cell.add(projectFocuses.toString());
        document.add(cell);
        document.add(Chunk.NEWLINE);
      }
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

    Paragraph title = new Paragraph("4.3 " + this.getText("summaries.project.indicatorsContribution"), HEADING3_FONT);
    indicatorsBlock.add(Chunk.NEWLINE);
    indicatorsBlock.add(title);
    indicatorsBlock.add(Chunk.NEWLINE);


    try {
      document.add(indicatorsBlock);

      for (IPElement outcome : this.getMidOutcomesPerIndicators()) {
        Paragraph outcomeBlock = new Paragraph();
        int indicatorIndex = 1;

        outcomeBlock.setAlignment(Element.ALIGN_JUSTIFIED);
        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(outcome.getProgram().getAcronym());
        outcomeBlock.add(" - " + this.getText("summaries.project.midoutcome"));

        outcomeBlock.setFont(BODY_TEXT_FONT);
        outcomeBlock.add(outcome.getDescription());
        outcomeBlock.add(Chunk.NEWLINE);
        outcomeBlock.add(Chunk.NEWLINE);

        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(this.getText("summaries.project.indicators"));
        document.add(outcomeBlock);
        document.add(Chunk.NEWLINE);

        for (IPIndicator outcomeIndicator : outcome.getIndicators()) {
          outcomeIndicator = outcomeIndicator.getParent() != null ? outcomeIndicator.getParent() : outcomeIndicator;
          List<IPIndicator> indicators = project.getIndicatorsByParent(outcomeIndicator.getId());
          if (indicators.isEmpty()) {
            continue;
          }

          Paragraph indicatorDescription =
            new Paragraph(indicatorIndex + ". " + outcomeIndicator.getDescription(), BODY_TEXT_FONT);
          document.add(indicatorDescription);
          document.add(Chunk.NEWLINE);;

          table = new PdfPTable(3);
          table.setLockedWidth(true);
          table.setTotalWidth(480);
          table.setWidths(new int[] {1, 3, 6});
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
            cell = new Paragraph(this.messageReturn(String.valueOf(indicator.getYear())), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

            cell = new Paragraph(this.messageReturn(String.valueOf(indicator.getTarget())), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

            cell = new Paragraph(this.messageReturn(String.valueOf(indicator.getDescription())), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

          }

          indicatorIndex++;
          document.add(table);
          document.add(Chunk.NEWLINE);;
        }

      }
    } catch (DocumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private void addProjectLocations() {

    Paragraph title = new Paragraph("3. " + this.getText("summaries.projectLocation.title"), HEADING3_FONT);
    title.add(Chunk.NEWLINE);
    title.add(Chunk.NEWLINE);


    StringBuffer projectLocations = new StringBuffer();
    List<Location> locationList = (project.getLocations());

    try {
      document.newPage();
      document.add(title);

      if (project.isGlobal()) {
        Image global = Image.getInstance(config.getBaseUrl() + "/images/summaries/global-map.png");
        global.scalePercent(60f);
        global.setAlignment(Element.ALIGN_CENTER);
        document.add(global);
      } else {

        PdfPTable table = new PdfPTable(4);

        // Set table widths
        table.setLockedWidth(true);
        table.setTotalWidth(480);
        table.setWidths(new int[] {4, 4, 4, 5});
        table.setHeaderRows(1);

        // Headers
        Paragraph cell = new Paragraph(this.getText("summaries.projectLocation.table.level"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        cell = new Paragraph(this.getText("summaries.projectLocation.table.latitude"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        cell = new Paragraph(this.getText("summaries.projectLocation.table.longitude"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        cell = new Paragraph(this.getText("summaries.projectLocation.table.name"), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);
        double answer = 0;
        OtherLocation otherLocation;

        for (Location location : locationList) {
          answer = 0;
          projectLocations = new StringBuffer();
          // Level
          if (location.isCountry()) {
            projectLocations.append("Country");
          } else if (location.isRegion()) {
            projectLocations.append("Region");
          } else if (location.isClimateSmartVillage()) {
            projectLocations.append("CSV");
          } else if (location.isOtherLocation()) {
            otherLocation = (OtherLocation) location;
            projectLocations.append(otherLocation.getType().getName());
          }

          cell = new Paragraph();
          cell.setFont(TABLE_BODY_FONT);
          cell.add(projectLocations.toString());
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

          // Latitude
          projectLocations = new StringBuffer();
          if (location.isOtherLocation()) {
            otherLocation = (OtherLocation) location;

            answer = otherLocation.getGeoPosition().getLatitude();

            cell = new Paragraph();
            cell.setFont(TABLE_BODY_FONT);
            if (answer != 0) {
              cell.add(String.valueOf(answer));
            } else {
              cell.add("Not applicable");
            }
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

            // Longitude
            answer = otherLocation.getGeoPosition().getLatitude();
            cell = new Paragraph();
            cell.setFont(TABLE_BODY_FONT);
            if (answer != 0) {
              cell.add(String.valueOf(answer));
            } else {
              cell.add("Not applicable");
            }
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

          } else {

            // Latitude
            cell = new Paragraph();
            cell.setFont(TABLE_BODY_FONT);
            cell.add("Not applicable");
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

            cell = new Paragraph();
            cell.setFont(TABLE_BODY_FONT);
            // Longitude
            cell.add("Not applicable");
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
          }

          // Name
          cell = new Paragraph();
          cell.setFont(TABLE_BODY_FONT);
          projectLocations = new StringBuffer();
          projectLocations.append(location.getName());
          cell.add(projectLocations.toString());
          this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
        }
        if (locationList.isEmpty()) {
          cell = new Paragraph();
          cell.setFont(BODY_TEXT_FONT);
          cell.add(this.getText("summaries.project.empty"));
          document.add(cell);
        } else {
          document.add(table);
        }
      }

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project locations to the project summary pdf", e);
    } catch (MalformedURLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private void addProjectOutcomes() {


    String outcomeProgress;
    Paragraph outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph title = new Paragraph("4. " + this.getText("summaries.project.outcome"), HEADING2_FONT);
    outcomesBlock.add(title);
    outcomesBlock.add(Chunk.NEWLINE);;
    title = new Paragraph();
    title.setFont(HEADING3_FONT);
    title.add("4.1 " + this.getText("summaries.project.outcomeNarrative"));
    outcomesBlock.add(title);
    outcomesBlock.add(Chunk.NEWLINE);;

    // Project outcome statement
    Paragraph body = new Paragraph();
    body.setFont(BODY_TEXT_BOLD_FONT);
    body.add(this.getText("summaries.project.outcomeStatement"));
    body.add(Chunk.NEWLINE);;

    body.setFont(BODY_TEXT_FONT);
    if (project.getOutcomes() != null && project.getOutcomes().get(String.valueOf(midOutcomeYear)) != null) {
      body.add(project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement());
    } else {
      body.add(this.getText("summaries.project.empty"));
    }
    body.add(Chunk.NEWLINE);;
    outcomesBlock.add(body);

    try {
      document.newPage();
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

      if (project.getOutcomes().get(String.valueOf(year)) == null) {
        outcomeProgress = this.getText("summaries.project.empty");
      } else {
        outcomeProgress = this.messageReturn(project.getOutcomes().get(String.valueOf(year)).getStatement());
      }

      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(outcomeProgress);
      outcomesBlock.add(Chunk.NEWLINE);;
      try {

        document.add(outcomesBlock);
        document.add(Chunk.NEWLINE);

      } catch (DocumentException e) {
        LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
      }
    }

    // ******************* Gender contribution ***************/

    outcomesBlock = new Paragraph();
    outcomesBlock.add(Chunk.NEWLINE);
    outcomesBlock.setFont(HEADING3_FONT);
    outcomesBlock.add("4.2 " + this.getText("summaries.project.outcome.gender"));
    outcomesBlock.add(Chunk.NEWLINE);;
    outcomesBlock.add(Chunk.NEWLINE);;

    outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
    outcomesBlock.add(this.getText("summaries.project.outcome.gender.contributiton.title"));

    ProjectOutcome narrative =
      this.projectOutcomeManager.getProjectOutcomeByYear(this.project.getId(), this.midOutcomeYear);
    if (narrative != null) {
      if (narrative.getGenderDimension() != null) {
        outcomeProgress = narrative.getGenderDimension();

      } else {
        outcomeProgress = this.getText("summaries.project.empty");
      }
    } else {
      outcomeProgress = this.getText("summaries.project.empty");
    }

    outcomesBlock.setFont(BODY_TEXT_FONT);
    outcomesBlock.add(outcomeProgress);
    outcomesBlock.add(Chunk.NEWLINE);;
    outcomesBlock.add(Chunk.NEWLINE);;

    for (int year = currentPlanningYear; year < midOutcomeYear; year++) {
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.gender.contributiton",
        new String[] {String.valueOf(year)}));

      if ((project.getOutcomes().get(String.valueOf(year)) == null)
        || (project.getOutcomes().get(String.valueOf(year)).getGenderDimension() == null)) {
        outcomeProgress = this.getText("summaries.project.empty");
      } else {
        outcomeProgress = project.getOutcomes().get(String.valueOf(year)).getGenderDimension();
      }
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(outcomeProgress);
      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.add(Chunk.NEWLINE);

    }
    // Add paragraphs to document
    try {
      document.add(outcomesBlock);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

    // ******************* CCAFS Outcomes***************/
    this.addProjectIndicators();

    // ******************* Other contributions***************/
    OtherContribution otherContribution = project.getIpOtherContribution();


    outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);

    title =
      new Paragraph("4.4 " + this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions"),
        HEADING3_FONT);
    outcomesBlock.add(Chunk.NEWLINE);
    outcomesBlock.add(title);
    outcomesBlock.add(Chunk.NEWLINE);

    // Contribution to other Impact Pathways:
    outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
    outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.pathways"));

    outcomesBlock.setFont(BODY_TEXT_FONT);

    if (otherContribution == null || otherContribution.getContribution() == null
      || otherContribution.getContribution().equals("")) {
      outcomesBlock.add(this.getText("summaries.project.empty"));
    } else {
      outcomesBlock.add(otherContribution.getContribution());
    }

    outcomesBlock.add(Chunk.NEWLINE);
    outcomesBlock.add(Chunk.NEWLINE);

    // Contribution to another Center activity:
    outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
    outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.center"));
    outcomesBlock.setFont(BODY_TEXT_FONT);
    if (otherContribution == null || otherContribution.getAdditionalContribution() == null
      || otherContribution.getAdditionalContribution().equals("")) {
      outcomesBlock.add(this.getText("summaries.project.empty"));
    } else {
      outcomesBlock.add(otherContribution.getAdditionalContribution());
    }
    outcomesBlock.add(Chunk.NEWLINE);
    outcomesBlock.add(Chunk.NEWLINE);

    // Collaboration with other CRPs
    boolean addParagraph = false;
    List<CRP> listCRP = project.getCrpContributions();
    Paragraph cell = new Paragraph();;
    cell.setFont(BODY_TEXT_BOLD_FONT);
    cell.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.covered"));
    PdfPTable table = new PdfPTable(1);
    if (listCRP.isEmpty()) {
      cell.add(" : ");
      cell.setFont(BODY_TEXT_FONT);
      cell.add(this.getText("summaries.project.empty"));
      addParagraph = true;
    } else {
      table.setLockedWidth(true);
      table.setTotalWidth(500);
      this.addCustomTableCell(table, cell, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE, table.getNumberOfColumns(),
        0, false);


      for (CRP crp : listCRP) {
        cell = new Paragraph();
        cell.setFont(TABLE_BODY_FONT);
        cell.add(crp.getName());
        this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);
      }


    }
    try {
      document.add(outcomesBlock);

      // CNature of the collaboration:

      outcomesBlock = new Paragraph();
      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.nature"));
      outcomesBlock.setFont(BODY_TEXT_FONT);
      if (otherContribution == null || otherContribution.getCrpCollaborationNature() == null
        || otherContribution.getCrpCollaborationNature().equals("")) {
        outcomesBlock.add(this.getText("summaries.project.empty"));
      } else {
        outcomesBlock.add(otherContribution.getCrpCollaborationNature());
      }
      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.add(Chunk.NEWLINE);
      // Add paragraphs to document

      if (addParagraph) {
        document.add(cell);
      } else {
        document.add(table);
      }
      document.add(outcomesBlock);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  // Entering the project outputs in the summary
  private void addProjectOutputs() {

    // **********************************************************************************
    // *************************** Overview By Mog *************************************
    // **********************************************************************************

    try {
      Paragraph overview_title = new Paragraph();
      overview_title.setFont(HEADING2_FONT);
      overview_title.add("5. " + this.getText("summaries.project.projectOutput"));
      overview_title.add(Chunk.NEWLINE);
      overview_title.add(Chunk.NEWLINE);
      document.newPage();

      document.add(overview_title);

      overview_title = new Paragraph();
      overview_title.setFont(HEADING3_FONT);
      overview_title.add("5.1 " + this.getText("summaries.project.overviewbymogs"));
      overview_title.add(Chunk.NEWLINE);
      overview_title.add(Chunk.NEWLINE);

      document.add(overview_title);

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }

    int[] years = {2019, 2016, 2017};

    // 0 = 2015 , 1= 2016 , 2 = 2019
    Paragraph paragraph = new Paragraph();

    List<IPElement> mogs = project.getOutputs();
    try {
      // year
      PdfPTable table;
      for (int a = 0; a < years.length; a++) {


        paragraph = new Paragraph();
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add(this.getText("summaries.project.overviewbymogs.text") + "- " + years[a]);


        table = new PdfPTable(1);
        table.setLockedWidth(true);
        table.setTotalWidth(480);
        this.addCustomTableCell(table, paragraph, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);
        // Mog
        for (int b = 0; b < mogs.size(); b++) {

          this.addOverview(table, mogs.get(b), years[a]);

        }
        document.add(table);
        paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);

      }
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }


    // **********************************************************************************
    // *************************** Deliverables.****************************************
    // **********************************************************************************


    try {
      paragraph = new Paragraph();
      paragraph.setFont(HEADING3_FONT);

      paragraph.add("5.2 " + this.getText("summaries.project.deliverable.title"));

      document.add(paragraph);
      paragraph = new Paragraph();
      paragraph.add(Chunk.NEWLINE);
      document.add(paragraph);

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }


    List<Deliverable> listDeliverables = project.getDeliverables();
    for (Deliverable deliverable : listDeliverables) {
      this.addDelivable(deliverable);

    }


  }

  private void addProjectPartners() {


    Paragraph partnersBlock = new Paragraph();
    partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.setKeepTogether(true);

    Paragraph title = new Paragraph(32, "2. " + this.getText("summaries.project.projectPartners"), HEADING3_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;


    int c = 1;
    // Get project leader PL
    title = new Paragraph(28, "2.1 " + this.getText("summaries.project.projectPartners.section.one"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;
    this.auxiliarGetPartners(project.getLeader(), c, partnersBlock);
    String partnerLabel = "Coordinator: ";

    // Coordinator
    partnersBlock.setFont(BODY_TEXT_BOLD_FONT);
    partnersBlock.add(partnerLabel);
    partnersBlock.setFont(BODY_TEXT_FONT);
    if (project.getCoordinator() != null) {
      partnersBlock.add(project.getCoordinator().getComposedName());
    } else {
      partnersBlock.add("Not defined");
    }

    partnersBlock.add(Chunk.NEWLINE);
    partnersBlock.add(Chunk.NEWLINE);;
    c++;

    // Get CCAFS partners PPA
    title = new Paragraph(28, "2.2 " + this.getText("summaries.project.projectPartners.section.three"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;
    c = 1;
    for (ProjectPartner partner : project.getPPAPartners()) {
      this.auxiliarGetPartners(partner, c, partnersBlock);
      partnersBlock.add(Chunk.NEWLINE);
      c++;
    }

    // Get Project Partners PP
    title = new Paragraph(28, "2.3 " + this.getText("summaries.project.projectPartners.section.four"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;
    c = 1;
    for (ProjectPartner partner : project.getProjectPartners()) {
      this.auxiliarGetPartners(partner, c, partnersBlock);
      partnersBlock.add(Chunk.NEWLINE);
      c++;
    }

    try {
      document.newPage();
      document.add(partnersBlock);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  private void addProjectTitle() {
    LineSeparator line = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -7);
    Paragraph paragraph = new Paragraph();
    line.setLineColor(titleColor);
    try {
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      paragraph.setFont(HEADING3_FONT);
      paragraph.add(this.getProjectID());
      paragraph.add(Chunk.NEWLINE);
      document.add(paragraph);

      paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(project.getTitle());
      paragraph.add(line);


      document.add(paragraph);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addSummary() {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_CENTER);
    try {
      Phrase title = new Phrase(this.getText("summaries.project.summary"), BODY_TEXT_BOLD_FONT);
      paragraph.add(title);
      document.add(paragraph);

      paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      Phrase body = new Phrase(this.messageReturn(project.getSummary()), BODY_TEXT_FONT);
      paragraph.add(body);

      document.add(paragraph);

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project summary to the project summary pdf", e);
    }
  }

  private void auxiliarGetPartners(ProjectPartner partner, int c, Paragraph partnersBlock) {

    if (!(partner == null)) {
      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      paragraph.setKeepTogether(true);

      // Partner #
      StringBuffer partnerLabel = new StringBuffer();
      partnerLabel.append(this.getText("summaries.project.partner") + c);
      paragraph.setFont(HEADING4_FONT);
      paragraph.add(partnerLabel.toString());
      paragraph.add(Chunk.NEWLINE);;


      // Contact information
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      partnerLabel = new StringBuffer();
      partnerLabel.append(this.getText("summaries.project.partner.contactperson"));
      paragraph.add(partnerLabel.toString());

      paragraph.setFont(BODY_TEXT_FONT);
      if (partner.getUser() != null) {
        paragraph.add(this.messageReturn(partner.getUser().getComposedName()));
      } else {
        paragraph.add("Not defined");
      }

      paragraph.add(Chunk.NEWLINE);;

      // Organization
      partnerLabel = new StringBuffer();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.organization"));
      paragraph.setFont(BODY_TEXT_FONT);
      if (partner.getInstitution() != null) {
        if (!((partner.getInstitution().getAcronym() == null) || (partner.getInstitution().getAcronym().isEmpty()))) {
          partnerLabel.append(partner.getInstitution().getAcronym());
          partnerLabel.append(" - ");

        }
        partnerLabel.append(this.messageReturn(partner.getInstitution().getName()));
      } else {
        partnerLabel.append("Not defined");
      }
      paragraph.add(partnerLabel.toString());
      paragraph.add(Chunk.NEWLINE);

      // Responsibilities
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.responsibilities"));
      paragraph.setFont(BODY_TEXT_FONT);

      paragraph.add(this.messageReturn(partner.getResponsabilities()));

      partnersBlock.add(paragraph);
    }
  }

  /**
   * This method is used for generate the file pdf.
   * 
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

    // Summary content
    this.addProjectTitle();
    this.addMainInformationTable();
    this.addProjectContributions();
    this.addSummary();
    this.addProjectPartners();
    this.addProjectLocations();
    this.addProjectOutcomes();
    this.addProjectOutputs();
    this.addActivities();
    // this.addProjectBudgets();

    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();
    inputStream = new ByteArrayInputStream(outputStream.toByteArray());
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
   * 
   * @param mog
   * @return
   */
  public int getMOGIndex(IPElement mog) {
    int index = 0;
    List<IPElement> allMOGs = elementManager.getIPElements(mog.getProgram(), mog.getType());

    for (int i = 0; i < allMOGs.size(); i++) {
      if (allMOGs.get(i).getId() == mog.getId()) {
        return i + 1;
      }
    }

    return index;
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

  /**
   * @param ppaPartners
   * @param pp
   * @return
   */
  private boolean isRepeatProjectPartner(List<ProjectPartner> ppaPartners, ProjectPartner pp, int index) {
    for (int a = index; a < ppaPartners.size(); a++) {
      if (ppaPartners.get(a).getInstitution().getId() == pp.getInstitution().getId()) {
        return true;
      }
    }
    return false;
  }


  private String messageReturn(String enter) {

    if (enter.equals(null) || enter.equals("")) {
      return this.getText("summaries.project.empty");
    } else {
      return enter;
    }

  }

  /**
   * @param ppaPartners
   * @return
   */
  private List<ProjectPartner> removePartnersRepeat(List<ProjectPartner> ppaPartners) {

    List<ProjectPartner> ppaPartners_aux = new ArrayList<ProjectPartner>();
    int size = ppaPartners.size() - 1;
    for (int a = 0; a < size; a++) {
      if (!this.isRepeatProjectPartner(ppaPartners, ppaPartners.get(a), a + 1)) {
        ppaPartners_aux.add(ppaPartners.get(a));
      }
    }

    ppaPartners_aux.add(ppaPartners.get(size));
    return ppaPartners_aux;
  }

  public List<Location> removeRepeatedLocations(List<Location> listLocation) {
    List<Location> listLocationAnswer = new ArrayList<Location>();
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