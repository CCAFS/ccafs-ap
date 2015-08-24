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
import org.cgiar.ccafs.ap.data.manager.BudgetByMogManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
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
import org.cgiar.ccafs.ap.data.model.OutputBudget;
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
import java.net.URL;
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
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfAction;
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
  private int contentLength;
  int currentPlanningYear;
  private Document document;
  private APConfig config;
  private ProjectContributionOverviewManager overviewManager;
  private ProjectOutcomeManager projectOutcomeManager;
  private IPElementManager elementManager;
  private InputStream inputStream;
  private BudgetManager budgetManager;
  private BudgetByMogManager budgetByMogManager;
  private ProjectManager projectManager;
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
    ProjectContributionOverviewManager overviewManager, ProjectOutcomeManager projectOutcomeManager,
    BudgetByMogManager budgetByMogManager, ProjectManager projectManager) {
    this.config = config;
    this.initialize(config.getBaseUrl());
    this.budgetManager = budgetManager;
    this.elementManager = elementManager;
    this.overviewManager = overviewManager;
    this.projectOutcomeManager = projectOutcomeManager;
    this.budgetByMogManager = budgetByMogManager;
    this.projectManager = projectManager;
  }

  private void addActivities() {

    try {
      document.newPage();
      Paragraph activityBlock = new Paragraph("6. " + this.getText("summaries.project.activity"), HEADING2_FONT);
      activityBlock.setAlignment(Element.ALIGN_JUSTIFIED);
      activityBlock.add(Chunk.NEWLINE);

      PdfPTable table;
      List<Activity> listActivities = project.getActivities();

      if (listActivities.isEmpty()) {
        activityBlock.setFont(BODY_TEXT_FONT);
        activityBlock.add(this.getText("summaries.project.empty"));
        document.add(activityBlock);
      } else {
        activityBlock.add(Chunk.NEWLINE);
        document.add(activityBlock);
        int counter = 1;
        for (Activity activity : listActivities) {

          if (activity != null) {

            table = new PdfPTable(2);
            table.setTotalWidth(480);
            table.setLockedWidth(true);

            // Header table
            activityBlock = new Paragraph();
            activityBlock.setAlignment(Element.ALIGN_CENTER);
            activityBlock.setFont(BODY_TEXT_BOLD_FONT);
            activityBlock.add("Activity #" + counter);

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
            activityBlock.add(this.getText("summaries.project.startDate") + " (dd-MM-yyyy)" + ": ");

            activityBlock.setFont(TABLE_BODY_FONT);
            activityBlock.add(startDate);
            activityBlock.add(Chunk.NEWLINE);
            this.addTableBodyCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1);

            // Activity End Date
            activityBlock = new Paragraph();
            activityBlock.setFont(TABLE_BODY_BOLD_FONT);
            activityBlock.add(this.getText("summaries.project.endDate") + " (dd-MM-yyyy)" + ": ");

            activityBlock.setFont(TABLE_BODY_FONT);
            activityBlock.add(endDate);
            activityBlock.add(Chunk.NEWLINE);
            this.addTableBodyCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1);

            // Activity Leader
            activityBlock = new Paragraph();
            activityBlock.setFont(TABLE_BODY_BOLD_FONT);
            activityBlock.add(this.getText("summaries.project.activities.activityLeader") + ": ");

            activityBlock.setFont(TABLE_BODY_FONT);
            // activityBlock.add(activity.getLeader().getComposedName());
            activityBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);
            // document.add(Chunk.NEWLINE);
            document.add(table);
            activityBlock = new Paragraph();
            activityBlock.add(Chunk.NEWLINE);
            document.add(activityBlock);
            counter++;
          }
        }
      }

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project activities to the project summary pdf of project {} ", e,
        project.getId());
    }

  }

  private void addBudgetByMog(int year, Paragraph cell, PdfPTable table, IPElement mog, BudgetType budgetType) {

    List<OutputBudget> listOutputBudgets = new ArrayList<>();
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter;


    currencyFormatter = NumberFormat.getPercentInstance(locale);
    currencyFormatter.setMaximumFractionDigits(0);

    listOutputBudgets =
      budgetByMogManager.getProjectOutputsBudgetByTypeAndYear(project.getId(), budgetType.getValue(), year);
    OutputBudget budget_temp = this.getOutputBudgetByMog(listOutputBudgets, mog);

    cell = new Paragraph();
    cell.setFont(TABLE_BODY_FONT);

    if (budget_temp == null) {

      cell.add(this.truncate(currencyFormatter.format(0)));
      currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);
      cell.add(" - USD " + this.truncate(currencyFormatter.format(0)));
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      currencyFormatter = NumberFormat.getPercentInstance(locale);
      cell.add(this.truncate(currencyFormatter.format(0)));
      currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);
      cell.add(" - USD " + this.truncate(currencyFormatter.format(0)));
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


    } else {

      // amount
      currencyFormatter = NumberFormat.getPercentInstance(locale);
      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(this.truncate(currencyFormatter.format(budget_temp.getTotalContribution() * 0.01)));
      currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);
      cell.add(" - USD " + this.truncate(currencyFormatter.format(budget_temp.getTotalContribution() * 0.01
        * budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), budgetType.getValue(), year))));
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      // Gender
      currencyFormatter = NumberFormat.getPercentInstance(locale);
      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(this.truncate(currencyFormatter.format(budget_temp.getGenderContribution() * 0.01)));
      currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);
      cell.add(" - USD " + this.truncate(currencyFormatter.format(budget_temp.getGenderContribution() * 0.01
        * budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(), budgetType.getValue(), year))));
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
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
    currencyFormatter.setMaximumFractionDigits(0);
    currencyFormatter.setParseIntegerOnly(true);
    Budget budget;

    budget = project.getBudget(institution.getId(), budgetType.getValue(), year);

    if (budget == null) {
      // Annual budget
      cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(0.0)), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      // gender percentage
      currencyFormatter = NumberFormat.getPercentInstance();
      currencyFormatter.setParseIntegerOnly(true);
      cell = new Paragraph(currencyFormatter.format(0), TABLE_BODY_FONT);

      currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);
      cell.add(" - USD " + this.truncate(currencyFormatter.format(0.0)));
      budget = null;
    } else {
      // Annual budget
      cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(budget.getAmount())), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

      // gender percentage
      currencyFormatter = NumberFormat.getPercentInstance();
      currencyFormatter.setParseIntegerOnly(true);
      cell = new Paragraph(currencyFormatter.format(budget.getGenderPercentage() * 0.01), TABLE_BODY_FONT);

      currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);
      cell.add(
        " - USD " + this.truncate(currencyFormatter.format(budget.getAmount() * budget.getGenderPercentage() * 0.01)));
      budget = null;
    }
    this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


  }

  /**
   * @param startYear
   * @param endYear
   * @param currencyFormatter
   */
  private void addBudgetsSummaryByPartners(int startYear, int endYear) {

    try {
      Paragraph cell = new Paragraph(this.getText("summaries.project.budget.summary"), BODY_TEXT_BOLD_FONT);

      // = new double[] {0.0, 0.0, 0.0, 0.0};
      PdfPTable table;
      if (project.isCoFundedProject()) {
        table = new PdfPTable(5);
        table.setWidths(new int[] {2, 3, 3, 3, 3});
      } else {
        table = new PdfPTable(3);
        table.setWidths(new int[] {2, 3, 3});
      }

      table.setLockedWidth(true);
      table.setTotalWidth(480);

      table.setHeaderRows(1);

      Locale locale = new Locale("en", "US");
      NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
      currencyFormatter.setMaximumFractionDigits(0);

      // Add cell with the name summary
      this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
        table.getNumberOfColumns(), 0, false);


      cell = new Paragraph(this.getText("summaries.project.budget.overall.type"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);


      if (project.isCoFundedProject()) {
        cell = new Paragraph(this.getText("summaries.project.budget.overall.text",
          new String[] {BudgetType.W1_W2.name().replace("_", "/")}), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        cell = new Paragraph(this.getText("summaries.project.budget.overall.gender",
          new String[] {BudgetType.W1_W2.name().replace("_", "/")}), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        cell = new Paragraph(this.getText("summaries.project.budget.overall.text",
          new String[] {BudgetType.W3_BILATERAL.name().replace("_", "/")}), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);


        cell = new Paragraph(this.getText("summaries.project.budget.overall.gender",
          new String[] {BudgetType.W3_BILATERAL.name().replace("_", "/")}), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

      }

      else {
        cell = new Paragraph(this.getText("summaries.project.budget.overall.text",
          new String[] {this.getBudgetType().name().replace("_", "/")}), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);

        cell = new Paragraph(this.getText("summaries.project.budget.overall.gender",
          new String[] {this.getBudgetType().name().replace("_", "/")}), TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);
      }

      double value, genderValue;

      for (int year = startYear; year <= endYear; year++) {


        cell = new Paragraph(String.valueOf(year), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 0);

        if (project.isCoFundedProject()) {

          // amount w1/w2
          value =
            this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);

          cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(value)), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          // gender w1/w2
          genderValue =
            this.budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
          cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(genderValue)), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


          // amount w3/Bilateral \n
          value = this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(),
            BudgetType.W3_BILATERAL.getValue(), year);
          cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(value)), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


          // gender w3/Bilateral

          genderValue = this.budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(),
            BudgetType.W3_BILATERAL.getValue(), year);

          cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(genderValue)), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


        } else {
          // amount w1/w2
          value =
            this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);

          cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(value)), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          // gender w1/w2
          genderValue =
            this.budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
          cell = new Paragraph("USD " + this.truncate(currencyFormatter.format(genderValue)), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        }
      }

      // ********************** Totals *************

      cell = new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 0);


      if (project.isCoFundedProject()) {
        cell = new Paragraph(
          "USD " + this.truncate(currencyFormatter
            .format(budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), BudgetType.W1_W2.getValue()))),
          TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        cell = new Paragraph(
          "USD " + this.truncate(currencyFormatter
            .format(budgetManager.calculateTotalGenderPercentageByType(project.getId(), BudgetType.W1_W2.getValue()))),
          TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


        cell = new Paragraph(
          "USD " + this.truncate(currencyFormatter.format(
            budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), BudgetType.W3_BILATERAL.getValue()))),
          TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);


        cell = new Paragraph(
          "USD " + this.truncate(currencyFormatter.format(
            budgetManager.calculateTotalGenderPercentageByType(project.getId(), BudgetType.W3_BILATERAL.getValue()))),
          TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
      }

      else {

        cell = new Paragraph(
          "USD " + this.truncate(currencyFormatter
            .format(budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), this.getBudgetType().getValue()))),
          TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        cell = new Paragraph(
          "USD " + this.truncate(currencyFormatter.format(
            budgetManager.calculateTotalGenderPercentageByType(project.getId(), this.getBudgetType().getValue()))),
          TABLE_BODY_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
      }


      document.add(table);
      document.add(Chunk.NEWLINE);

    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  // if value = 0 add to listIPFlagship else adding listIPRegion
  private void addDelivable(Deliverable deliverable, int counter) {
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

        deliverableBlock.add(this.getText("summaries.project.deliverable.expected") + " #" + counter);
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
        deliverableBlock.add(this.getText("summaries.project.deliverable.information.title") + ": ");

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
        stringBuilder.append(": ");
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
        stringBuilder.append(": ");
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
        stringBuilder.append(": ");
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
        stringBuilder.append(": ");
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


        counter = 1;
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
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(nextUser.getUser());
            deliverableBlock.add(this.messageReturn(stringBuilder.toString()));
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Expected Changes
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user.strategies") + ": ");
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(this.messageReturn(nextUser.getExpectedChanges()));
            deliverableBlock.add(stringBuilder.toString());
            // document.add(deliverableBlock);
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Strategies
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user.expected.change") + ": ");
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(this.messageReturn(nextUser.getStrategies()));
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
        // ********** Deliverable partnership****************************

        // ******************partner contributing
        counter = 1;

        table = new PdfPTable(1);
        table.setLockedWidth(true);
        table.setTotalWidth(480);

        // Title partners contributing
        deliverableBlock = new Paragraph();
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.partnership"));
        this.addCustomTableCell(table, deliverableBlock, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        // Organization
        stringBuilder = new StringBuilder();
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.organization") + " #" + counter
          + " (Responsible)" + ": ");
        deliverableBlock.setFont(TABLE_BODY_FONT);
        if (deliverable.getResponsiblePartner() != null && (deliverable.getResponsiblePartner().getPartner() != null)) {
          // stringBuilder.append(this.messageReturn(deliverable.getResponsiblePartner().getPartner().getComposedName()));
        } else {
          stringBuilder.append(this.getText("summaries.project.empty"));
        }
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);;
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

        counter = 1;
        // ************** Other Partners
        List<DeliverablePartner> listOtherPartner = deliverable.getOtherPartners();
        if (!listOtherPartner.isEmpty()) {
          for (DeliverablePartner deliverablePartner : listOtherPartner) {
            counter++;

            // Title partners contributing
            deliverableBlock = new Paragraph();
            deliverableBlock.setAlignment(Element.ALIGN_LEFT);
            deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
            if (listOtherPartner.size() == 1) {
              deliverableBlock.add(this.getText("summaries.project.deliverable.partnership"));
            } else {
              deliverableBlock.add(this.getText("summaries.project.deliverable.partnership") + " #" + counter);
            }


            // Organization
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);

            deliverableBlock
              .add(this.getText("summaries.project.deliverable.partnership.organization") + " #" + counter + ": ");
            deliverableBlock.add("");

            deliverableBlock.setFont(TABLE_BODY_FONT);

            if (deliverablePartner.getPartner() != null) {
              // stringBuilder.append(deliverablePartner.getPartner().getComposedName());
            } else {
              stringBuilder.append(this.getText("summaries.project.empty"));
            }

            deliverableBlock.add(stringBuilder.toString());
            deliverableBlock.add(Chunk.NEWLINE);;
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);
          }
        }


        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

      }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  private void addEmptyFlashigAndRegion(List<IPProgram> listToAdding, int size) {
    for (int a = 0; a <= size; a++) {
      if (a > listToAdding.size()) {
        listToAdding.add(new IPProgram());
      }
    }
  }


  private void addMainInformationTable() {


    String startDate, endDate;
    if (project.getStartDate() != null) {
      startDate = new SimpleDateFormat("dd-MM-yyyy").format(project.getStartDate());
    } else {
      startDate = this.messageReturn(null);
    }


    if (project.getEndDate() != null) {
      endDate = new SimpleDateFormat("dd-MM-yyyy").format(project.getEndDate());
    } else {
      endDate = this.messageReturn(null);
    }


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

      cellContent =
        new Paragraph(this.messageReturn(project.getLiaisonInstitution().getAcronym() + " - "
          + project.getLiaisonInstitution().getName()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      cellContent = new Paragraph(this.getText("summaries.project.contactPerson"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn(project.getOwner().getComposedName()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Third row
      cellContent = new Paragraph(this.getText("summaries.project.leadOrganization"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);
      if (project.getLeader() == null || project.getLeader().getInstitution() == null) {
        cellContent = new Paragraph(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
      } else {
        cellContent =
          new Paragraph(this.messageReturn(this.messageReturn(project.getLeader().getInstitution().getComposedName())),
            TABLE_BODY_FONT);

      }
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);


      cellContent = new Paragraph(this.getText("summaries.project.projectLeader"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 0);

      // if (project.getLeader() == null || project.getLeader().getUser() == null) {
      // cellContent = new Paragraph(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
      // } else {
      // cellContent =
      // new Paragraph(this.messageReturn(project.getLeader().getUser().getComposedName()), TABLE_BODY_FONT);
      // }
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Fourth row
      cellContent = (new Paragraph(this.getText("summaries.project.projectType"), TABLE_BODY_BOLD_FONT));
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn(project.getType().replaceAll("_", " ")), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);


      cellContent = (new Paragraph(this.getText("summaries.project.detailed"), TABLE_BODY_BOLD_FONT));
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);


      Font hyperLink = new Font(FontFactory.getFont("openSans", 10, Color.BLUE));
      hyperLink.setStyle(Font.UNDERLINE);

      Chunk imdb;
      if (project.getWorkplanName() == null) {
        imdb = new Chunk(this.getText("summaries.project.empty"), hyperLink);
      } else {
        imdb = new Chunk("Download", hyperLink);
        imdb.setAction(new PdfAction(new URL(this.messageReturn(project.getWorkplanName()))));
      }


      cellContent = new Paragraph();
      cellContent.add(imdb);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // this.addTableColSpanCell(table, new Paragraph(), Element.ALIGN_LEFT, 1, 2);

      document.add(table);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    } catch (MalformedURLException e) {
      e.printStackTrace();
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
    // overviewBlock.setAlignment(Element.ALIGN_JUSTIFIED);
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
    // overviewBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
    overviewLabel.append(this.getText("summaries.project.expected.contribution.first"));
    overviewLabel.append(year);
    overviewLabel.append(" " + this.getText("summaries.project.expected.contribution.last") + " : ");
    overviewBlock.add(overviewLabel.toString());

    // Expected Annual Contribution text

    overviewBlock.setFont(TABLE_BODY_FONT);

    overviewLabel = new StringBuffer();
    // overviewBlock.setAlignment(Element.ALIGN_JUSTIFIED);

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

    this.addTableBodyCell(table, overviewBlock, Element.ALIGN_JUSTIFIED, 1);
  }

  private void addProjectBudgets() {

    int startYear = 0;
    int endYear = 0;

    Paragraph cell = new Paragraph();

    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();
    if (project.getStartDate() != null) {
      startDate.setTime(project.getStartDate());
      startYear = startDate.get(Calendar.YEAR);

    }
    if (project.getEndDate() != null) {
      endDate.setTime(project.getEndDate());
      endYear = endDate.get(Calendar.YEAR);
    }

    PdfPTable table;
    cell.setFont(HEADING2_FONT);
    cell.add("7. " + this.getText("summaries.project.budget"));

    // ************************Budget By Partners*************************************

    cell.setFont(HEADING3_FONT);
    cell.add(Chunk.NEWLINE);
    cell.add(Chunk.NEWLINE);
    cell.add("7.1 " + this.getText("summaries.project.budget.partners"));

    try {
      document.newPage();
      if (project.getBudgets().isEmpty()) {
        cell.setFont(BODY_TEXT_FONT);
        cell.add(Chunk.NEWLINE);
        cell.add(this.getText("summaries.project.empty"));
        document.add(cell);
      } else {
        List<ProjectPartner> listProjectPartner = project.getPPAPartners();
        listProjectPartner.add(project.getLeader());
        listProjectPartner = this.removePartnersRepeat(listProjectPartner);

        cell.add(Chunk.NEWLINE);
        cell.add(Chunk.NEWLINE);
        document.add(cell);

        // Summary table
        this.addBudgetsSummaryByPartners(startYear, endYear);

        for (ProjectPartner projectPartner : listProjectPartner) {

          if (project.isCoFundedProject()) {
            table = new PdfPTable(5);
            table.setWidths(new int[] {2, 3, 3, 3, 3});
          } else {
            table = new PdfPTable(3);
            table.setWidths(new int[] {2, 3, 3});
          }

          table.setTotalWidth(480);

          table.setLockedWidth(true);

          cell = new Paragraph(projectPartner.getInstitution().getComposedName(), BODY_TEXT_BOLD_FONT);
          this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
            table.getNumberOfColumns(), 0, false);

          cell = new Paragraph(this.getText("summaries.project.budget.overall.type"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          if (project.isCoFundedProject()) {
            cell =
              new Paragraph("Annual Budget " + BudgetType.W1_W2.name().toString().replace("_", "/"), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph("Gender percentage " + BudgetType.W1_W2.name().toString().replace("_", "/"),
              TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph("Annual Budget " + BudgetType.W3_BILATERAL.name().toString().replace("_", "/"),
              TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph("Gender percentage " + BudgetType.W3_BILATERAL.name().toString().replace("_", "/"),
              TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

          } else {
            cell = new Paragraph("Annual Budget " + this.getBudgetType().name().toString().replace("_", "/"),
              TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph("Gender percentage " + this.getBudgetType().name().toString().replace("_", "/"),
              TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);
          }

          for (int year = startYear; year <= endYear; year++) {
            cell = new Paragraph(String.valueOf(year), TABLE_BODY_BOLD_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 0);

            if (project.isCoFundedProject()) {
              this.addBudgetsByPartners(cell, projectPartner.getInstitution(), year, table, BudgetType.W1_W2);
              this.addBudgetsByPartners(cell, projectPartner.getInstitution(), year, table, BudgetType.W3_BILATERAL);
            } else {
              this.addBudgetsByPartners(cell, projectPartner.getInstitution(), year, table, this.getBudgetType());
            }
          }


          document.add(table);
          cell = new Paragraph(Chunk.NEWLINE);
          document.add(cell);
        }
      }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }

    // ************************Budget By Mogs*************************************
    cell = new Paragraph();
    cell.setFont(HEADING3_FONT);
    cell.add(Chunk.NEWLINE);
    cell.add("7.2 " + this.getText("summaries.project.budget.mog"));
    try {
      if (project.getBudgets().isEmpty()) {
        cell.setFont(BODY_TEXT_FONT);
        cell.add(Chunk.NEWLINE);
        cell.add(this.getText("summaries.project.empty"));
        document.add(cell);
      } else {
        document.add(cell);

        cell = new Paragraph();
        cell.add(Chunk.NEWLINE);
        List<IPElement> outputsList = project.getOutputs();
        if (outputsList.isEmpty()) {
          cell.setFont(BODY_TEXT_FONT);
          cell.add(this.getText("summaries.project.empty"));
          document.add(cell);
        }
        cell.add(Chunk.NEWLINE);
        document.add(cell);

        int year;

        StringBuffer budgetLabel;
        int[] widths;
        for (IPElement mog : outputsList) {

          if (project.isCoFundedProject()) {

            table = new PdfPTable(5);
            widths = new int[] {2, 3, 3, 3, 3};
          } else {
            table = new PdfPTable(3);
            widths = new int[] {2, 3, 3};
          }

          table.setTotalWidth(480);
          table.setWidths(widths);
          table.setLockedWidth(true);

          cell = new Paragraph();
          budgetLabel = new StringBuffer();
          cell.setFont(TABLE_BODY_FONT);
          budgetLabel.append(mog.getProgram().getAcronym());
          budgetLabel.append(" - MOG # ");
          budgetLabel.append(this.getMOGIndex(mog));
          budgetLabel.append(": ");
          budgetLabel.append(mog.getDescription());
          cell.add(budgetLabel.toString());
          this.addCustomTableCell(table, cell, Element.ALIGN_JUSTIFIED, BODY_TEXT_FONT, Color.WHITE,
            table.getNumberOfColumns(), 0, false);

          cell = new Paragraph(this.getText("summaries.project.budget.overall.type"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);
          if (project.isCoFundedProject()) {

            cell = new Paragraph(this.getText("summaries.project.budget.mog.anual.percentaje",
              new String[] {BudgetType.W1_W2.name().replace("_", "/")}), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph(this.getText("summaries.project.budget.mog.anual.gender",
              new String[] {BudgetType.W1_W2.name().replace("_", "/")}), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);


            cell = new Paragraph(this.getText("summaries.project.budget.mog.anual.percentaje",
              new String[] {BudgetType.W3_BILATERAL.name().replace("_", "/")}), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph(this.getText("summaries.project.budget.mog.anual.gender",
              new String[] {BudgetType.W3_BILATERAL.name().replace("_", "/")}), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);


          } else {
            cell = new Paragraph(this.getText("summaries.project.budget.mog.anual.percentaje",
              new String[] {this.getBudgetType().name().replace("_", "/")}), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);

            cell = new Paragraph(this.getText("summaries.project.budget.mog.anual.gender",
              new String[] {this.getBudgetType().name().replace("_", "/")}), TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);
          }

          for (year = startYear; year <= endYear; year++) {
            cell = new Paragraph(String.valueOf(year), TABLE_BODY_BOLD_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 0);

            if (project.isCoFundedProject()) {
              this.addBudgetByMog(year, cell, table, mog, BudgetType.W1_W2);
              this.addBudgetByMog(year, cell, table, mog, BudgetType.W3_BILATERAL);
            } else {
              this.addBudgetByMog(year, cell, table, mog, this.getBudgetType());
            }
          }

          document.add(table);
          document.add(Chunk.NEWLINE);
        }
      }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  private void addProjectContributions() {
    try {
      PdfPTable table = new PdfPTable(2);

      // Set table widths
      table.setLockedWidth(true);
      table.setTotalWidth(480);
      table.setWidths(new int[] {7, 3});

      Paragraph cell;
      StringBuffer projectFocuses = new StringBuffer();

      // ************************ Adding flagships and regions **********************************
      List<IPProgram> listIPFlagship = project.getFlagships();
      List<IPProgram> listIPRegions = project.getRegions();

      if (listIPFlagship.isEmpty() && listIPRegions.isEmpty()) {

        cell = new Paragraph(this.getText("summaries.project.ipContributions"), BODY_TEXT_BOLD_FONT);
        cell.setFont(BODY_TEXT_BOLD_FONT);
        cell.add(": ");
        projectFocuses.append(this.getText("summaries.project.empty"));
        cell.setFont(BODY_TEXT_FONT);
        cell.add(projectFocuses.toString());
        document.add(cell);
        document.add(Chunk.NEWLINE);

      } else {

        cell = new Paragraph(this.getText("summaries.project.ipContributions"), BODY_TEXT_BOLD_FONT);
        this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        cell = new Paragraph("", TABLE_HEADER_FONT);
        cell.setAlignment(Element.ALIGN_LEFT);
        cell.add(this.getText("summaries.project.ipContributions.flagship"));

        this.addTableHeaderCell(table, cell);

        cell = new Paragraph("", TABLE_HEADER_FONT);
        cell.setAlignment(Element.ALIGN_LEFT);
        cell.add(this.getText("summaries.project.ipContributions.region"));
        this.addTableHeaderCell(table, cell);

        int sizeFlaships = listIPFlagship.size();
        int sizeRegions = listIPRegions.size();

        if (sizeFlaships < sizeRegions) {
          this.addEmptyFlashigAndRegion(listIPFlagship, sizeRegions);
        } else if (sizeFlaships > sizeRegions) {
          this.addEmptyFlashigAndRegion(listIPRegions, sizeFlaships);
        }


        IPProgram program;
        for (int a = 0; a < listIPRegions.size(); a++) {

          // Flashing
          program = listIPFlagship.get(a);
          if (program.getComposedName() == null || program.getComposedName().isEmpty()) {

            cell = new Paragraph("", TABLE_BODY_FONT);
          } else {
            cell = new Paragraph(program.getComposedName(), TABLE_BODY_FONT);
          }

          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          // Regions
          program = listIPRegions.get(a);

          if (program.getComposedName() == null || program.getComposedName().isEmpty()) {

            cell = new Paragraph("", TABLE_BODY_FONT);
          } else {
            cell = new Paragraph(program.getComposedName(), TABLE_BODY_FONT);
          }

          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

        }
        document.add(table);
        document.add(Chunk.NEWLINE);

      }

      // ******************** Adding Bilateral ********************************************
      List<Project> listLinkageProject = project.getLinkedProjects();
      cell = new Paragraph();
      projectFocuses = new StringBuffer();

      if (project.isBilateralProject()) {
        projectFocuses.append(this.getText("summaries.project.ipContributions.project", new String[] {"Core"}));
      } else {
        projectFocuses.append(this.getText("summaries.project.ipContributions.project", new String[] {"Bilateral"}));
      }

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
          this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);
        }
        document.add(table);
        document.add(Chunk.NEWLINE);
      } else {
        cell.setFont(BODY_TEXT_BOLD_FONT);
        cell.add(projectFocuses.toString());
        cell.add(Chunk.NEWLINE);
        cell.setFont(BODY_TEXT_FONT);
        projectFocuses = new StringBuffer();

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


      if (project.isBilateralProject()) {
        projectFocuses = new StringBuffer();
        cell = new Paragraph();
        cell.setFont(BODY_TEXT_BOLD_FONT);
        projectFocuses.append(this.getText("summaries.project.ipContributions.proposal"));
        cell.add(projectFocuses.toString());
        cell.setFont(BODY_TEXT_FONT);
        cell.add(this.messageReturn(project.getBilateralContractProposalName()));

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

    try {
      document.add(indicatorsBlock);
      List<IPElement> listIPElements = this.getMidOutcomesPerIndicators();
      if (!listIPElements.isEmpty()) {

        for (IPElement outcome : listIPElements) {
          Paragraph outcomeBlock = new Paragraph();
          int indicatorIndex = 1;

          outcomeBlock.add(Chunk.NEWLINE);
          outcomeBlock.setAlignment(Element.ALIGN_JUSTIFIED);
          outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
          outcomeBlock.add(outcome.getProgram().getAcronym());
          outcomeBlock.add(" - " + this.getText("summaries.project.midoutcome"));

          outcomeBlock.setFont(BODY_TEXT_FONT);
          outcomeBlock.add(outcome.getDescription());
          outcomeBlock.add(Chunk.NEWLINE);
          outcomeBlock.add(Chunk.NEWLINE);

          document.add(outcomeBlock);

          for (IPIndicator outcomeIndicator : outcome.getIndicators()) {
            outcomeIndicator = outcomeIndicator.getParent() != null ? outcomeIndicator.getParent() : outcomeIndicator;
            List<IPIndicator> indicators = project.getIndicatorsByParent(outcomeIndicator.getId());
            if (indicators.isEmpty()) {
              continue;
            }

            Paragraph indicatorDescription = new Paragraph();
            indicatorDescription.setFont(BODY_TEXT_BOLD_FONT);
            indicatorDescription.add(this.getText("summaries.project.indicators"));
            indicatorDescription.add(String.valueOf(indicatorIndex) + ": ");

            indicatorDescription.setFont(BODY_TEXT_FONT);
            indicatorDescription.setAlignment(Element.ALIGN_JUSTIFIED);
            indicatorDescription.add(outcomeIndicator.getDescription());
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
      } else {
        cell = new Paragraph(this.getText("summaries.project.empty"));
        document.add(cell);
      }


    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  private void addProjectLocations() {
    Paragraph title = new Paragraph("3. " + this.getText("summaries.projectLocation.title"), HEADING3_FONT);
    Paragraph cell;
    StringBuffer projectLocations = new StringBuffer();
    List<Location> locationList = (project.getLocations());
    PdfPTable table;
    try {
      document.newPage();
      document.add(title);

      if (project.isGlobal()) {
        Image global = Image.getInstance(config.getBaseUrl() + "/images/summaries/global-map.png");
        global.scalePercent(60f);
        global.setAlignment(Element.ALIGN_CENTER);
        document.add(global);
      } else if (locationList.isEmpty()) {
        cell = new Paragraph();
        cell.setFont(BODY_TEXT_FONT);
        cell.add(this.getText("summaries.project.empty"));
        document.add(cell);
      } else {
        table = new PdfPTable(4);

        // Set table widths
        table.setLockedWidth(true);
        table.setTotalWidth(480);
        table.setWidths(new int[] {4, 4, 4, 5});
        table.setHeaderRows(1);

        // Headers
        cell = new Paragraph(this.getText("summaries.projectLocation.table.level"), TABLE_HEADER_FONT);
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
        title = new Paragraph();


        title.add(Chunk.NEWLINE);
        title.add(Chunk.NEWLINE);
        document.add(title);
        document.add(table);
      }

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project locations to the project summary pdf", e);
    } catch (MalformedURLException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
      e.printStackTrace();
    } catch (IOException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
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

    // body.setFont(BODY_TEXT_FONT);
    if (project.getOutcomes() == null || project.getOutcomes().get(String.valueOf(midOutcomeYear)) == null
      || project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement() == null
      || project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement().equals("")) {
      body.add(": ");
      body.setFont(BODY_TEXT_FONT);
      body.add(this.getText("summaries.project.empty"));
    } else {
      body.setFont(BODY_TEXT_FONT);
      body.add(Chunk.NEWLINE);
      body.add(this.messageReturn(project.getOutcomes().get(String.valueOf(midOutcomeYear)).getStatement()));

    }
    body.add(Chunk.NEWLINE);;
    outcomesBlock.add(body);

    try {
      document.newPage();
      document.add(outcomesBlock);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

    currentPlanningYear--;
    for (int year = currentPlanningYear; year < midOutcomeYear; year++) {
      // Annual progress towards
      outcomesBlock = new Paragraph();
      outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcomeAnnualProgress", new String[] {String.valueOf(year)}));

      if (project.getOutcomes() == null || project.getOutcomes().get(String.valueOf(year)) == null
        || project.getOutcomes().get(String.valueOf(year)).getStatement() == null
        || project.getOutcomes().get(String.valueOf(year)).getStatement().equals("")) {
        outcomesBlock.add(": ");
        outcomesBlock.setFont(BODY_TEXT_FONT);
        outcomeProgress = this.getText("summaries.project.empty");
      } else {
        outcomesBlock.add(Chunk.NEWLINE);
        outcomeProgress = this.messageReturn(project.getOutcomes().get(String.valueOf(year)).getStatement());
        outcomesBlock.setFont(BODY_TEXT_FONT);
      }
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
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
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
      outcomesBlock
        .add(this.getText("summaries.project.outcome.gender.contributiton", new String[] {String.valueOf(year)}));

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

    title = new Paragraph("4.4 " + this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions"),
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
      outcomesBlock.add(": " + this.getText("summaries.project.empty"));
    } else {
      outcomesBlock.add(Chunk.NEWLINE);
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
      outcomesBlock.add(": " + this.getText("summaries.project.empty"));
    } else {
      outcomesBlock.add(Chunk.NEWLINE);
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
      cell.setFont(BODY_TEXT_FONT);
      cell.add(": " + this.getText("summaries.project.empty"));
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
        outcomesBlock.add(": " + this.getText("summaries.project.empty"));
      } else {
        outcomesBlock.add(Chunk.NEWLINE);
        outcomesBlock.add(otherContribution.getCrpCollaborationNature());
      }

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
      document.add(overview_title);

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }

    int[] years = {2019, 2016, 2017};

    // 0 = 2015 , 1= 2016 , 2 = 2019
    Paragraph paragraph = new Paragraph();

    List<IPElement> mogs = project.getOutputs();
    try {
      if (mogs.isEmpty()) {
        paragraph.add(this.getText("summaries.project.empty"));
        document.add(paragraph);

      } else {
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
          paragraph = new Paragraph();
          paragraph.add(Chunk.NEWLINE);
          document.add(paragraph);
          document.add(table);
        }
      }
      paragraph = new Paragraph();
      paragraph.add(Chunk.NEWLINE);
      document.add(paragraph);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }

    // **********************************************************************************
    // *************************** Deliverables.****************************************
    // **********************************************************************************

    int counter = 1;
    try {
      paragraph = new Paragraph();
      paragraph.setFont(HEADING3_FONT);

      paragraph.add("5.2 " + this.getText("summaries.project.deliverable.title"));
      document.add(paragraph);
      paragraph = new Paragraph();

      List<Deliverable> listDeliverables = project.getDeliverables();
      if (listDeliverables.isEmpty()) {
        paragraph.add(this.getText("summaries.project.empty"));
        document.add(paragraph);
      } else {
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);
        for (Deliverable deliverable : listDeliverables) {
          this.addDelivable(deliverable, counter);
          counter++;
        }
      }
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addProjectPartners() {


    Paragraph partnersBlock = new Paragraph();
    partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.setKeepTogether(true);

    Paragraph title = new Paragraph(32, "2. " + this.getText("summaries.project.projectPartners"), HEADING3_FONT);
    partnersBlock.add(title);

    if (project.getLeader() == null && project.getPPAPartners().isEmpty()) {
      title = new Paragraph(this.getText("summaries.project.empty"), BODY_TEXT_FONT);
      partnersBlock.add(title);
    } else {
      partnersBlock.add(Chunk.NEWLINE);;
      int c = 1;
      // Get project leader PL
      this.auxiliarGetPartners(project.getLeader(), c, partnersBlock);
      c++;
      partnersBlock.add(Chunk.NEWLINE);


      List<ProjectPartner> listPPA = project.getPPAPartners();

      if (!listPPA.isEmpty()) {

        for (ProjectPartner partner : listPPA) {
          this.auxiliarGetPartners(partner, c, partnersBlock);
          partnersBlock.add(Chunk.NEWLINE);
          c++;
        }
      }


      // Get Project partners
      List<ProjectPartner> listPP = project.getProjectPartners();
      if (!listPP.isEmpty()) {

        for (ProjectPartner partner : listPP) {
          this.auxiliarGetPartners(partner, c, partnersBlock);
          c++;

          // List<Institution> listInstitutionContributing = partner.getContributeInstitutions();
          partnersBlock.setFont(BODY_TEXT_BOLD_FONT);
          partnersBlock.add(this.getText("summaries.project.projectPartners.section.five"));


          // if (listInstitutionContributing.isEmpty()) {
          // partnersBlock.add(": ");
          // partnersBlock.setFont(BODY_TEXT_FONT);
          // partnersBlock.add(this.getText("summaries.project.empty"));
          // } else {
          // partnersBlock.setFont(BODY_TEXT_FONT);
          // for (Institution institution : listInstitutionContributing) {
          // partnersBlock.add(Chunk.NEWLINE);
          // partnersBlock.add(institution.getComposedName());
          // }
          // }
          partnersBlock.add(Chunk.NEWLINE);
          partnersBlock.add(Chunk.NEWLINE);
        }

      }
      partnersBlock.add(Chunk.NEWLINE);
      partnersBlock.add(Chunk.NEWLINE);
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
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add("Title: ");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(this.messageReturn(project.getTitle()));
      paragraph.add(line);


      document.add(paragraph);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addSummary() {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_LEFT);
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

      partnerLabel.append(this.getText("summaries.project.partner") + c + " ");
      if (c == 1) {
        partnerLabel.append(this.getText("summaries.project.partner.leader"));
      }
      paragraph.setFont(HEADING4_FONT);
      paragraph.add(partnerLabel.toString());
      paragraph.add(Chunk.NEWLINE);;


      // Contact information
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      partnerLabel = new StringBuffer();
      if (c == 1) {
        partnerLabel.append(this.getText("summaries.project.organization.lead"));
      } else {
        partnerLabel.append(this.getText("summaries.project.partner.contactperson"));

      }
      paragraph.add(partnerLabel.toString());

      paragraph.setFont(BODY_TEXT_FONT);
      // if (partner.getUser() != null) {
      // paragraph.add(this.messageReturn(partner.getUser().getComposedName()));
      // } else {
      // paragraph.add(this.getText("summaries.project.empty"));
      // }

      paragraph.add(Chunk.NEWLINE);;


      // Coordinator
      if (c == 1) {
        partnerLabel = new StringBuffer();
        partnerLabel.append("Coordinator: ");
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add(partnerLabel.toString());
        paragraph.setFont(BODY_TEXT_FONT);
        if (project.getCoordinator() != null) {
          // paragraph.add(project.getCoordinator().getUser().getComposedName());
        } else {
          paragraph.add(this.getText("summaries.project.empty"));
        }
        paragraph.add(Chunk.NEWLINE);;
      }

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
      paragraph.add(Chunk.NEWLINE);
      paragraph.setFont(BODY_TEXT_FONT);

      // paragraph.add(this.messageReturn(partner.getResponsabilities()));

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
    this.setSummaryTitle(this.projectManager.getStandardIdentifier(project, true));

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
    this.addProjectBudgets();

    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();
    inputStream = new ByteArrayInputStream(outputStream.toByteArray());
  }

  private BudgetType getBudgetType() {
    if (project.isBilateralProject()) {
      return BudgetType.W3_BILATERAL;
    } else if (project.isCoreProject()) {
      return BudgetType.W1_W2;
    }
    return null;
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


  public OutputBudget getOutputBudgetByMog(List<OutputBudget> listOutputBudget, IPElement mog) {

    for (OutputBudget outputBudget : listOutputBudget) {
      if (outputBudget.getOutput().getId() == mog.getId()) {
        return outputBudget;
      }
    }
    return null;
  }


  private boolean isRepeatedLocation(Location location, List<Location> listLocation, int index) {
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

    if (enter == null || enter.equals("")) {
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
    if (ppaPartners.get(size) != null) {
      ppaPartners_aux.add(ppaPartners.get(size));
    }

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

  public String truncate(String moneyString) {
    //
    // if (moneyString.endsWith(".00")) {
    // int centsIndex = moneyString.lastIndexOf(".00");
    // if (centsIndex != -1) {
    // moneyString = moneyString.substring(1, centsIndex);
    // }
    // }

    return moneyString.replace(",", ".");
  }
}