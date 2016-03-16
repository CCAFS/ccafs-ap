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

package org.cgiar.ccafs.ap.summaries.projects.pdf;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.BudgetByMogManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.CRPContribution;
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.DeliverableDataSharingFile;
import org.cgiar.ccafs.ap.data.model.DeliverableDissemination;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.DeliverablePublicationMetadata;
import org.cgiar.ccafs.ap.data.model.DeliverablesRanking;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Location;
import org.cgiar.ccafs.ap.data.model.NextUser;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.OtherLocation;
import org.cgiar.ccafs.ap.data.model.OutputBudget;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectHighlightsType;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsCountry;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsTypes;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;
import org.cgiar.ccafs.ap.data.model.ProjectNextUser;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.ProjectStatusEnum;
import org.cgiar.ccafs.ap.data.model.ProjecteOtherContributions;
import org.cgiar.ccafs.utils.APConfig;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import com.lowagie.text.Anchor;
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
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal
 * @author Jorge Leonardo Solis Banguera
 */

public class ProjectSummaryPDF extends BasePDF {


  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ProjectSummaryPDF.class);

  // Managers
  private IPElementManager elementManager;
  private InputStream inputStream;
  private BudgetManager budgetManager;
  private BudgetByMogManager budgetByMogManager;
  private ProjectPartnerManager projectPartnerManager;
  private LocationManager locationManager;
  // Model
  private APConfig config;
  private Document document;
  private int contentLength;
  private int currentPlanningYear;
  private int currentReportingYear;
  private int midOutcomeYear;
  private Project project;
  private DecimalFormat budgetFormatter, genderFormatter;
  private String summaryTitle;
  private List<IPElement> allMOGs;
  private Map<String, String> mapPartnerPersons;
  private Map<String, String> statuses;

  @Inject
  public ProjectSummaryPDF(APConfig config, BudgetManager budgetManager, IPElementManager elementManager,
    IPCrossCuttingManager ipCrossCuttingManager, LocationManager locationManager,
    ProjectPartnerManager projectPartnerManager, BudgetByMogManager budgetByMogManager) {
    this.config = config;
    this.budgetManager = budgetManager;
    this.elementManager = elementManager;
    this.budgetByMogManager = budgetByMogManager;
    this.projectPartnerManager = projectPartnerManager;
    this.locationManager = locationManager;
    this.initialize(config.getBaseUrl());
  }

  /**
   * This method add Activities for the summary project
   */
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
            activityBlock.setFont(TABLE_HEADER_FONT);
            activityBlock.add("Activity #" + counter);

            PdfPCell cell_new = new PdfPCell(activityBlock);
            cell_new.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell_new.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell_new.setBackgroundColor(TABLE_HEADER_BACKGROUND);
            cell_new.setUseBorderPadding(true);
            cell_new.setPadding(3);
            cell_new.setBorderColor(TABLE_CELL_BORDER_COLOR);
            cell_new.setColspan(2);

            this.addTableHeaderCell(table, cell_new);

            // Activity title
            activityBlock = new Paragraph();
            activityBlock.setFont(TABLE_BODY_BOLD_FONT);
            activityBlock.add(this.getText("summaries.project.activities.title"));

            activityBlock.setFont(TABLE_BODY_FONT);
            activityBlock.add(this.messageReturn(activity.getTitle()));
            activityBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);

            // Activity description
            activityBlock = new Paragraph();
            activityBlock.setFont(TABLE_BODY_BOLD_FONT);
            activityBlock.add(this.getText("summaries.project.activities.description"));

            activityBlock.setFont(TABLE_BODY_FONT);
            activityBlock.add(this.messageReturn(activity.getDescription()));
            activityBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);

            String startDate = null;
            String endDate = null;
            try {
              startDate = new SimpleDateFormat("dd-MM-yyyy").format(activity.getStartDate());
            } catch (Exception e) {

            }
            try {

              endDate = new SimpleDateFormat("dd-MM-yyyy").format(activity.getEndDate());
            } catch (Exception e) {

            }


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

            PartnerPerson activityPartnerPerson = activity.getLeader();

            if (activityPartnerPerson != null) {
              activityBlock.add(activityPartnerPerson.getComposedName());
              String partnerInstitution = this.mapPartnerPersons.get(String.valueOf(activityPartnerPerson.getId()));
              if (partnerInstitution != null) {
                activityBlock.add(", " + partnerInstitution);
              }
            } else {
              activityBlock.add(this.getText("summaries.project.empty"));
            }
            activityBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);

            if (project.isReporting()) {
              // status
              activityBlock = new Paragraph();
              activityBlock.setFont(TABLE_BODY_BOLD_FONT);
              activityBlock.add(this.getText("summaries.project.activities.status"));

              activityBlock.setFont(TABLE_BODY_FONT);
              if (activity.getActivityStatus() > 0) {
                activityBlock.add(statuses.get(String.valueOf(activity.getActivityStatus())));
              } else {
                activityBlock.add(" " + this.getText("summaries.project.empty"));
              }
              activityBlock.add(Chunk.NEWLINE);

              if (activity.isStatusCancelled() || activity.isStatusExtended() || activity.isStatusOnGoing()) {
                this.addTableBodyCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1);

                activityBlock = new Paragraph();
                activityBlock.setFont(TABLE_BODY_BOLD_FONT);
                activityBlock.add(this.getText("summaries.project.activities.justification"));

                activityBlock.setFont(TABLE_BODY_FONT);
                activityBlock.add(this.messageReturn(activity.getActivityProgress()));
                activityBlock.add(Chunk.NEWLINE);

                this.addTableBodyCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1);
              } else {
                this.addTableColSpanCell(table, activityBlock, Element.ALIGN_JUSTIFIED, 1, 2);
              }
            }

            // document.add(Chunk.NEWLINE);
            document.add(table);
            activityBlock = new Paragraph();
            activityBlock.add(Chunk.NEWLINE);
            document.add(activityBlock);
            counter++;


          }
        }

        // Leason regardins
        activityBlock = new Paragraph();
        activityBlock.setAlignment(Element.ALIGN_JUSTIFIED);
        activityBlock.setFont(BODY_TEXT_BOLD_FONT);
        if (!project.isReporting()) {
          activityBlock.add(this.getText("summaries.project.activities.lessonsRegarding"));
        } else {
          activityBlock.add(this.getText("summaries.project.activities.reporting.lessonsRegarding"));
        }
        activityBlock.setFont(BODY_TEXT_FONT);

        if (project.getComponentLesson("activities") != null) {
          activityBlock.add(this.messageReturn(project.getComponentLesson("activities").getLessons()));
        } else {
          activityBlock.add(this.messageReturn(null));
        }
        document.add(activityBlock);

      }


    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project activities to the project summary pdf of project {} ", e,
        project.getId());
    }

  }

  private void addBudgetByMogOne(Paragraph paragraph, PdfPTable table, StringBuffer budgetLabel, IPElement mog,
    int startYear, int endYear, BudgetType budgetType) {
    paragraph = new Paragraph();
    budgetLabel = new StringBuffer();
    paragraph.setFont(TABLE_BODY_FONT);
    budgetLabel.append(mog.getProgram().getAcronym());
    budgetLabel.append(" - MOG # ");
    budgetLabel.append(this.getMOGIndex(mog));
    budgetLabel.append(": ");
    budgetLabel.append(mog.getDescription());
    budgetLabel.append(" - " + budgetType.name().replace("_", "/"));
    paragraph.add(budgetLabel.toString());
    this.addCustomTableCell(table, paragraph, Element.ALIGN_JUSTIFIED, BODY_TEXT_FONT, Color.WHITE,
      table.getNumberOfColumns(), 0, false);

    PdfPCell cell;
    // year
    paragraph = new Paragraph(this.getText("summaries.project.budget.overall.type"), TABLE_HEADER_FONT);
    cell = new PdfPCell(paragraph);
    cell.setRowspan(2);
    this.addTableHeaderCell(table, cell);

    // % de amount
    paragraph = new Paragraph(
      this.getText("summaries.project.budget.mog.anual.percentaje", new String[] {budgetType.name().replace("_", "/")}),
      TABLE_HEADER_FONT);
    cell = new PdfPCell(paragraph);
    cell.setColspan(2);
    this.addTableHeaderCell(table, cell);

    // gender
    paragraph = new Paragraph(
      this.getText("summaries.project.budget.mog.anual.gender", new String[] {budgetType.name().replace("_", "/")}),
      TABLE_HEADER_FONT);
    cell = new PdfPCell(paragraph);
    cell.setColspan(2);
    this.addTableHeaderCell(table, cell);

    // amount (%)
    paragraph = new Paragraph("(%)", TABLE_HEADER_FONT);
    this.addTableHeaderCell(table, paragraph);

    // amount (USD)
    paragraph = new Paragraph("(USD)", TABLE_HEADER_FONT);
    this.addTableHeaderCell(table, paragraph);

    // gender (%)
    paragraph = new Paragraph("(%)", TABLE_HEADER_FONT);
    this.addTableHeaderCell(table, paragraph);

    // gender (USD)
    paragraph = new Paragraph("(USD)", TABLE_HEADER_FONT);
    this.addTableHeaderCell(table, paragraph);

    double[] totalsByYear = {0, 0};

    for (int year = startYear; year <= endYear; year++) {
      paragraph = new Paragraph(String.valueOf(year), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, paragraph, Element.ALIGN_CENTER, 0);
      this.addBudgetMogByYear(year, paragraph, table, mog, budgetType, totalsByYear);
    }

    // Totals
    paragraph = new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_BODY_BOLD_FONT);
    this.addTableBodyCell(table, paragraph, Element.ALIGN_CENTER, 0);

    // total amount $
    paragraph = new Paragraph(this.budgetFormatter.format(totalsByYear[0]), TABLE_BODY_BOLD_FONT);
    this.addTableColSpanCell(table, paragraph, Element.ALIGN_RIGHT, 1, 2);

    // total gender $
    paragraph = new Paragraph(this.budgetFormatter.format(totalsByYear[1]), TABLE_BODY_BOLD_FONT);
    this.addTableColSpanCell(table, paragraph, Element.ALIGN_RIGHT, 1, 2);


  }

  /**
   * @param year year of Budget
   * @param cell cell of tge table
   * @param table table for to represent the budget MOG
   * @param mog MOG to calculate the budget
   * @param budgetType type budget
   */
  private void addBudgetMogByYear(int year, Paragraph cell, PdfPTable table, IPElement mog, BudgetType budgetType,
    double[] totalsByYear) {

    List<OutputBudget> listOutputBudgets = new ArrayList<>();


    listOutputBudgets =
      budgetByMogManager.getProjectOutputsBudgetByTypeAndYear(project.getId(), budgetType.getValue(), year);
    OutputBudget budget_temp = this.getOutputBudgetByMog(listOutputBudgets, mog);

    cell = new Paragraph();
    cell.setFont(TABLE_BODY_FONT);

    if (budget_temp == null) {

      // amount
      cell.add(this.genderFormatter.format(0));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(budgetFormatter.format(0));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

      // gender
      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(genderFormatter.format(0));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(budgetFormatter.format(0));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);


    } else {
      double value = budget_temp.getTotalContribution() * 0.01;
      // amount %
      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(genderFormatter.format(value));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

      // amoun $
      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      value = budget_temp.getTotalContribution() * 0.01
        * budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), budgetType.getValue(), year);
      cell.add(budgetFormatter.format(value));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
      totalsByYear[0] += value;

      // Gender %
      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);
      cell.add(this.genderFormatter.format(budget_temp.getGenderContribution() * 0.01));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

      cell = new Paragraph();
      cell.setFont(TABLE_BODY_FONT);

      cell = new Paragraph();
      // Gender $
      cell.setFont(TABLE_BODY_FONT);
      value = budget_temp.getGenderContribution() * 0.01
        * budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(), budgetType.getValue(), year);
      cell.add(budgetFormatter.format(value));
      this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
      totalsByYear[1] += value;
    }


  }

  private void addBudgetPartner(ProjectPartner projectPartner, Paragraph paragraph, PdfPTable table,
    BudgetType budgetType, int startYear, int endYear) {

    PdfPCell cell;

    paragraph =
      new Paragraph(projectPartner.getInstitution().getComposedName() + " " + budgetType.name().replace("_", "/"),
        BODY_TEXT_BOLD_FONT);
    this.addCustomTableCell(table, paragraph, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
      table.getNumberOfColumns(), 0, false);

    // year
    paragraph = new Paragraph(this.getText("summaries.project.budget.overall.type"), TABLE_HEADER_FONT);
    cell = new PdfPCell(paragraph);
    cell.setRowspan(2);
    this.addTableHeaderCell(table, cell);

    // amount
    paragraph = new Paragraph(this.getText("summaries.project.budget.overall.amount", new String[] {""})
      + budgetType.name().toString().replace("_", "/") + " (USD)", TABLE_HEADER_FONT);
    cell = new PdfPCell(paragraph);
    cell.setRowspan(2);
    this.addTableHeaderCell(table, cell);

    // gender
    paragraph = new Paragraph(this.getText("summaries.project.budget.overall.gender", new String[] {""})
      + budgetType.name().toString().replace("_", "/"), TABLE_HEADER_FONT);
    cell = new PdfPCell(paragraph);
    cell.setColspan(2);
    this.addTableHeaderCell(table, cell);

    // gender (%)
    paragraph = new Paragraph("(%)", TABLE_HEADER_FONT);
    this.addTableHeaderCell(table, paragraph);

    // gender (USD)
    paragraph = new Paragraph("(USD)", TABLE_HEADER_FONT);
    this.addTableHeaderCell(table, paragraph);


    for (int year = startYear; year <= endYear; year++) {
      paragraph = new Paragraph(String.valueOf(year), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, paragraph, Element.ALIGN_CENTER, 0);
      this.addRowBudgetByPartners(paragraph, projectPartner.getInstitution(), year, table, budgetType);
    }

    // ************** Totals
    paragraph = new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_BODY_BOLD_FONT);
    this.addTableBodyCell(table, paragraph, Element.ALIGN_CENTER, 0);

    // amount
    double value = this.budgetManager.calculateTotalCCAFSBudgetByInstitutionAndType(project.getId(),
      projectPartner.getInstitution().getId(), budgetType.getValue());
    paragraph = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
    this.addTableBodyCell(table, paragraph, Element.ALIGN_RIGHT, 1);

    // gender
    value = this.budgetManager.calculateTotalGenderBudgetByInstitutionAndType(project.getId(),
      projectPartner.getInstitution().getId(), budgetType.getValue());
    paragraph = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
    this.addTableColSpanCell(table, paragraph, Element.ALIGN_RIGHT, 1, 2);
  }


  private void addBudgetReporting(String number) {

    try {
      document.newPage();
      Paragraph leverageBlock =
        new Paragraph(number + ". " + this.getText("summaries.project.leverage") + "s", HEADING2_FONT);
      leverageBlock.setAlignment(Element.ALIGN_JUSTIFIED);
      leverageBlock.add(Chunk.NEWLINE);

      PdfPTable table;
      List<ProjectLeverage> listLeverage = project.getLeverages();

      if (listLeverage.isEmpty()) {
        leverageBlock.setFont(BODY_TEXT_FONT);
        leverageBlock.add(this.getText("summaries.project.empty"));
        document.add(leverageBlock);
      } else {
        leverageBlock.add(Chunk.NEWLINE);
        document.add(leverageBlock);
        int counter = 1;
        for (ProjectLeverage leverage : listLeverage) {
          if (leverage != null) {
            table = new PdfPTable(2);
            table.setTotalWidth(480);
            table.setLockedWidth(true);

            // Header table
            leverageBlock = new Paragraph();
            leverageBlock.setAlignment(Element.ALIGN_CENTER);
            leverageBlock.setFont(TABLE_HEADER_FONT);
            leverageBlock.add(this.getText("summaries.project.leverage") + " #" + counter);

            this.addTableHeaderCellColspan(table, leverageBlock, 2);

            // leverage title
            leverageBlock = new Paragraph();
            leverageBlock.setFont(TABLE_BODY_BOLD_FONT);
            leverageBlock.add(this.getText("summaries.project.activities.title"));

            leverageBlock.setFont(TABLE_BODY_FONT);
            leverageBlock.add(this.messageReturn(leverage.getTitle()));
            leverageBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, leverageBlock, Element.ALIGN_JUSTIFIED, 1, 2);

            // Leverage institution
            leverageBlock = new Paragraph();
            leverageBlock.setFont(TABLE_BODY_BOLD_FONT);
            leverageBlock.add(this.getText("summaries.project.leverage.partnerName"));

            leverageBlock.setFont(TABLE_BODY_FONT);
            if (leverage.getMyInstitution() != null) {
              leverageBlock.add(this.messageReturn(leverage.getMyInstitution().getComposedName()));
            } else {
              leverageBlock.add(this.messageReturn(null));
            }

            leverageBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, leverageBlock, Element.ALIGN_JUSTIFIED, 1, 2);

            // Leverage Year
            leverageBlock = new Paragraph();
            leverageBlock.setFont(TABLE_BODY_BOLD_FONT);
            leverageBlock.add(this.getText("summaries.project.leverage.year"));

            leverageBlock.setFont(TABLE_BODY_FONT);
            leverageBlock.add(String.valueOf(this.currentReportingYear));
            leverageBlock.add(Chunk.NEWLINE);
            this.addTableColSpanCell(table, leverageBlock, Element.ALIGN_JUSTIFIED, 1, 2);

            // Leverage Flagship
            leverageBlock = new Paragraph();
            leverageBlock.setFont(TABLE_BODY_BOLD_FONT);
            leverageBlock.add(this.getText("summaries.project.leverage.flagship"));

            leverageBlock.setFont(TABLE_BODY_FONT);
            leverageBlock.add(this.messageReturn(String.valueOf(leverage.getMyFlagship().getComposedName())));
            leverageBlock.add(Chunk.NEWLINE);
            this.addTableBodyCell(table, leverageBlock, Element.ALIGN_JUSTIFIED, 1);

            // Leverage Budget
            leverageBlock = new Paragraph();
            leverageBlock.setFont(TABLE_BODY_BOLD_FONT);
            leverageBlock.add(this.getText("summaries.project.leverage.budget"));

            leverageBlock.setFont(TABLE_BODY_FONT);
            if (leverage.getBudget() != null) {
              leverageBlock.add("US $");
              leverageBlock.add(this.budgetFormatter.format(leverage.getBudget().doubleValue()));
            } else {
              leverageBlock.add(this.messageReturn(null));
            }
            leverageBlock.add(Chunk.NEWLINE);
            this.addTableBodyCell(table, leverageBlock, Element.ALIGN_JUSTIFIED, 1);

            document.add(table);
            leverageBlock = new Paragraph();
            leverageBlock.add(Chunk.NEWLINE);
            document.add(leverageBlock);
            counter++;
          }
        }
        document.add(leverageBlock);
      }
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project activities to the project summary pdf of project {} ", e,
        project.getId());
    }

  }

  /**
   * This Method is for to calculate the overall or gender summary
   * 
   * @param startYear start year to calculate the summary
   * @param endYear end year to calculate the summary
   * @param type this is used for to determinate the type the report to create
   */
  private void addBudgetsSummaryByPartners(int startYear, int endYear, int typeSummary) {

    try {
      Paragraph cell;
      if (typeSummary == 0) {
        cell = new Paragraph(this.getText("summaries.project.budget.summary", new String[] {"Overall"}),
          BODY_TEXT_BOLD_FONT);
      } else {
        cell =
          new Paragraph(this.getText("summaries.project.budget.summary", new String[] {"Gender"}), BODY_TEXT_BOLD_FONT);
      }
      PdfPTable table;
      if (project.isCoFundedProject()) {
        table = new PdfPTable(4);
        table.setWidths(new int[] {2, 3, 3, 3});
      } else {
        table = new PdfPTable(2);
        table.setWidths(new int[] {3, 3});
      }

      table.setLockedWidth(true);
      table.setTotalWidth(400);

      table.setHeaderRows(1);


      // Add cell with the name summary
      this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
        table.getNumberOfColumns(), 0, false);


      cell = new Paragraph(this.getText("summaries.project.budget.overall.type"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);


      if (project.isCoFundedProject()) {

        if (typeSummary == 0) {
          cell = new Paragraph(this.getText("summaries.project.budget.overall.text",
            new String[] {BudgetType.W1_W2.name().replace("_", "/")}) + "(USD)", TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          cell = new Paragraph(this.getText("summaries.project.budget.overall.text",
            new String[] {BudgetType.W3_BILATERAL.name().replace("_", "/")}) + "(USD)", TABLE_HEADER_FONT);
        } else {
          cell = new Paragraph(this.getText("summaries.project.budget.overall.gender",
            new String[] {BudgetType.W1_W2.name().replace("_", "/")}) + "(USD)", TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          cell = new Paragraph(this.getText("summaries.project.budget.overall.gender",
            new String[] {BudgetType.W3_BILATERAL.name().replace("_", "/")}) + "(USD)", TABLE_HEADER_FONT);
        }
        this.addTableHeaderCell(table, cell);

        // Total column
        cell = new Paragraph(this.getText("summaries.project.budget.overall.total") + " (USD)", TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, cell);
      }

      else {

        if (typeSummary == 0) {
          cell = new Paragraph(this.getText("summaries.project.budget.overall.text",
            new String[] {this.getBudgetType().name().replace("_", "/")}) + "(USD)", TABLE_HEADER_FONT);
        } else {
          cell = new Paragraph(this.getText("summaries.project.budget.overall.gender",
            new String[] {this.getBudgetType().name().replace("_", "/")}) + "(USD)", TABLE_HEADER_FONT);
        }
        this.addTableHeaderCell(table, cell);
      }


      double value, valueSum;
      value = 0.0;

      for (int year = startYear; year <= endYear; year++) {
        cell = new Paragraph(String.valueOf(year), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 0);

        valueSum = 0.0;

        if (project.isCoFundedProject()) {
          if (typeSummary == 0) {
            // amount w1/w2
            value = this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(),
              year);
            cell = new Paragraph(this.budgetFormatter.format(value), TABLE_BODY_FONT);;
            this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
            valueSum = value;

            // amount w3/Bilateral
            value = this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(),
              BudgetType.W3_BILATERAL.getValue(), year);

          } else {

            // gender w1/w2
            value =
              this.budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year);
            cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_FONT);;
            this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
            valueSum = value;

            // gender w3/Bilateral
            value = this.budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(),
              BudgetType.W3_BILATERAL.getValue(), year);
          }

          cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_FONT);;
          this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

          // Total
          valueSum += value;
          cell = new Paragraph(budgetFormatter.format(valueSum), TABLE_BODY_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);


        } else {
          if (typeSummary == 0) {
            // amount w1/w2
            value = this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(),
              this.getBudgetType().getValue(), year);
            cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_FONT);;
            this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

          } else {

            // gender w1/w2
            value = this.budgetManager.calculateGenderBudgetByTypeAndYear(project.getId(),
              this.getBudgetType().getValue(), year);
            cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_FONT);;
            this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);

          }
        }
      }

      // ********************** Totals *************

      cell = new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_BODY_BOLD_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 0);

      if (project.isCoFundedProject()) {

        if (typeSummary == 0) {
          value = budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), BudgetType.W1_W2.getValue());
          cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
          valueSum = value;

          value = budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), BudgetType.W3_BILATERAL.getValue());
          cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
          valueSum += value;
        } else {

          value = budgetManager.calculateTotalGenderPercentageByType(project.getId(), BudgetType.W1_W2.getValue());
          cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
          valueSum = value;

          value =
            budgetManager.calculateTotalGenderPercentageByType(project.getId(), BudgetType.W3_BILATERAL.getValue());
          cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
          valueSum += value;
        }

        // Total
        cell = new Paragraph(budgetFormatter.format(valueSum), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
      }

      else {
        if (typeSummary == 0) {
          value = budgetManager.calculateTotalCCAFSBudgetByType(project.getId(), this.getBudgetType().getValue());
        } else {
          value = budgetManager.calculateTotalGenderPercentageByType(project.getId(), this.getBudgetType().getValue());
        }

        cell = new Paragraph(budgetFormatter.format(value), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_RIGHT, 1);
        valueSum = value;
      }
      document.add(table);
      cell = new Paragraph(Chunk.NEWLINE);
      document.add(cell);

    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  /**
   * @param deliverable deliverable to add in the summary
   * @param counter number of deliverable
   **/
  private void addDelivable(Deliverable deliverable, int counter) {
    try {
      if (deliverable != null) {

        PdfPTable table = new PdfPTable(2);
        table.setTotalWidth(480);
        table.setLockedWidth(true);

        StringBuilder stringBuilder = new StringBuilder();
        PdfPCell cell_new;

        // **** Expected Deliverable #*********
        Paragraph deliverableBlock = new Paragraph();
        deliverableBlock.setFont(HEADING4_FONT);
        if (project.isReporting()) {
          deliverableBlock.add(this.getText("summaries.project.deliverable") + " #" + counter);
        } else {
          deliverableBlock.add(this.getText("summaries.project.deliverable.expected") + " #" + counter);
        }

        deliverableBlock.add(Chunk.NEWLINE);
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

        // **** Deliverable Information *********
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(BODY_TEXT_BOLD_FONT);
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.information"));

        cell_new = new PdfPCell(deliverableBlock);
        cell_new.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_new.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell_new.setBackgroundColor(TABLE_HEADER_BACKGROUND);
        cell_new.setUseBorderPadding(true);
        cell_new.setPadding(3);
        cell_new.setBorderColor(TABLE_CELL_BORDER_COLOR);
        cell_new.setColspan(2);

        this.addTableHeaderCell(table, cell_new);

        // Title
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.information.title") + ": ");

        deliverableBlock.setFont(TABLE_BODY_FONT);
        deliverableBlock.add(this.messageReturn(deliverable.getTitle()));
        deliverableBlock.add(Chunk.NEWLINE);;
        // document.add(deliverableBlock);
        this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 2);

        // MOG

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        if (deliverable.getOutput() != null) {
          stringBuilder = new StringBuilder();
          if (deliverable.getOutput().getProgram() != null
            && deliverable.getOutput().getProgram().getAcronym() != null) {
            stringBuilder.append(deliverable.getOutput().getProgram().getAcronym());
            stringBuilder.append(" - MOG # ");
          } else {
            stringBuilder.append("MOG # ");
          }
          stringBuilder.append(this.getMOGIndex(deliverable.getOutput()));
          stringBuilder.append(": ");
          deliverableBlock.add(stringBuilder.toString());
          deliverableBlock.setFont(TABLE_BODY_FONT);
          stringBuilder = new StringBuilder();
          stringBuilder.append(deliverable.getOutput().getDescription());
        } else {
          deliverableBlock.add("MOG :");
          stringBuilder.append(this.getText("summaries.project.empty"));
        }
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
        if (deliverable.getType() != null && deliverable.getType().getCategory() != null) {
          stringBuilder.append(this.messageReturn(deliverable.getType().getCategory().getName()));
        } else {
          stringBuilder.append(this.messageReturn(""));
        }
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);
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
        if (deliverable.getType() == null) {
          stringBuilder.append(this.messageReturn(this.getText("summaries.project.empty")));
        } else if (deliverable.getType().getId() == 38) {
          stringBuilder.append(this.getText("summaries.project.deliverable.other.expected"));
          stringBuilder.append("(");
          stringBuilder.append(this.messageReturn(deliverable.getTypeOther()));
          stringBuilder.append(")");
        } else {
          stringBuilder.append(this.messageReturn(deliverable.getType().getName()));
        }

        deliverableBlock.add(this.messageReturn(stringBuilder.toString()));
        deliverableBlock.add(Chunk.NEWLINE);;
        // document.add(deliverableBlock);
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

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

        // Status
        deliverableBlock = new Paragraph();
        stringBuilder = new StringBuilder();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        stringBuilder.append(this.getText("summaries.project.deliverable.information.statuts"));
        deliverableBlock.add(stringBuilder.toString());

        deliverableBlock.setFont(TABLE_BODY_FONT);
        stringBuilder = new StringBuilder();
        if (deliverable.getStatus() != 0) {
          stringBuilder.append(this.statuses.get(String.valueOf(deliverable.getStatus())));
        } else {
          stringBuilder.append(this.messageReturn(null));
        }
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);
        // document.add(deliverableBlock);

        if (deliverable.isStatusCancelled() || deliverable.isStatusExtended() || deliverable.isStatusOnGoing()) {
          this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 1);

          // Justification
          deliverableBlock = new Paragraph();
          stringBuilder = new StringBuilder();
          deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
          stringBuilder.append(this.getText("summaries.project.deliverable.information.justification"));
          deliverableBlock.add(stringBuilder.toString());

          deliverableBlock.setFont(TABLE_BODY_FONT);
          deliverableBlock.add(this.messageReturn(deliverable.getStatusDescription()));
          deliverableBlock.add(Chunk.NEWLINE);

          this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 1);

        } else {
          this.addTableColSpanCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1, 2);
        }

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
            deliverableBlock.setFont(TABLE_HEADER_FONT);
            if (nextUsers.size() == 1) {
              deliverableBlock.add(this.getText("summaries.project.deliverable.next.user"));
            } else {
              deliverableBlock.add(this.getText("summaries.project.deliverable.next.user") + " #" + counter);
            }

            this.addTableHeaderCell(table, deliverableBlock);

            // Next user
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(nextUser.getUser());
            deliverableBlock.add(this.messageReturn(stringBuilder.toString()));
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Expected Changes
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user.expected.change"));
            deliverableBlock.setFont(TABLE_BODY_FONT);
            stringBuilder.append(this.messageReturn(nextUser.getExpectedChanges()));
            deliverableBlock.add(stringBuilder.toString());
            // document.add(deliverableBlock);
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);

            // Strategies
            stringBuilder = new StringBuilder();
            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.deliverable.next.user.strategies"));
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

        // ******************Partner contributing
        counter = 1;

        table = new PdfPTable(1);
        table.setLockedWidth(true);
        table.setTotalWidth(480);

        // Title partners contributing
        deliverableBlock = new Paragraph();
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.partnership"));
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        this.addTableHeaderCell(table, deliverableBlock);

        // Organization
        stringBuilder = new StringBuilder();
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.deliverable.partnership.organization") + " #" + counter
          + " (Responsible)" + ": ");
        deliverableBlock.setFont(TABLE_BODY_FONT);
        DeliverablePartner deliverableResponsiblePartner = deliverable.getResponsiblePartner();
        PartnerPerson partnerPersonResponsible = null;
        if (deliverableResponsiblePartner != null) {
          partnerPersonResponsible = deliverableResponsiblePartner.getPartner();
        }
        if (deliverableResponsiblePartner != null && partnerPersonResponsible != null) {
          stringBuilder.append(this.messageReturn(partnerPersonResponsible.getComposedName()));
          stringBuilder.append(", ");
          stringBuilder.append(this.mapPartnerPersons.get(String.valueOf(partnerPersonResponsible.getId())));
        } else {
          stringBuilder.append(this.getText("summaries.project.empty"));
        }
        deliverableBlock.add(stringBuilder.toString());
        deliverableBlock.add(Chunk.NEWLINE);;
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);
        counter = 1;
        // ************** Other Partners
        List<DeliverablePartner> listOtherPartner = deliverable.getOtherPartners();

        PartnerPerson otherResponsiblepartnerPerson = null;

        if (!listOtherPartner.isEmpty()) {
          for (DeliverablePartner deliverablePartner : listOtherPartner) {
            if (deliverablePartner != null) {
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

              otherResponsiblepartnerPerson = deliverablePartner.getPartner();
              if (otherResponsiblepartnerPerson != null) {
                stringBuilder.append(this.messageReturn(otherResponsiblepartnerPerson.getComposedName()));
                stringBuilder.append(", ");
                stringBuilder.append(this.mapPartnerPersons.get(String.valueOf(otherResponsiblepartnerPerson.getId())));
              } else {
                stringBuilder.append(this.getText("summaries.project.empty"));
              }

              deliverableBlock.add(stringBuilder.toString());
              deliverableBlock.add(Chunk.NEWLINE);;
              this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_JUSTIFIED, 1);
            }
          }
        }


        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

      }

      // ********** Ranking**************************************
      PdfPCell cell_new;
      if (project.isReporting()) {

        PdfPTable table = new PdfPTable(2);
        table.setLockedWidth(true);
        table.setTotalWidth(480);
        table.setWidths(new int[] {7, 3});
        DeliverablesRanking deliverableRanking = deliverable.getRanking();

        // summaries.project.reporting.deliverable.ranking
        Paragraph deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.ranking"));

        cell_new = new PdfPCell(deliverableBlock);
        cell_new.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_new.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell_new.setBackgroundColor(TABLE_HEADER_BACKGROUND);
        cell_new.setUseBorderPadding(true);
        cell_new.setPadding(3);
        cell_new.setBorderColor(TABLE_CELL_BORDER_COLOR);
        cell_new.setColspan(2);

        this.addTableHeaderCell(table, cell_new);

        // address gender
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.addres.gender"));
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_FONT);
        if (deliverableRanking != null && deliverableRanking.getAddress() != null) {
          deliverableBlock.add(this.messageReturn(deliverableRanking.getAddress().toString()));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_CENTER, 1);

        // Get Potential
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.contribution.outcome"));
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_FONT);
        if (deliverableRanking != null && deliverableRanking.getPotential() != null) {
          deliverableBlock.add(this.messageReturn(deliverableRanking.getPotential().toString()));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_CENTER, 1);

        // Level
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.shared.ownership"));
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_FONT);
        if (deliverableRanking != null && deliverableRanking.getLevel() != null) {
          deliverableBlock.add(this.messageReturn(deliverableRanking.getLevel().toString()));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_CENTER, 1);

        // Personal perspective
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.personal.prespective"));
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);


        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_FONT);
        if (deliverableRanking != null && deliverableRanking.getPersonalPerspective() != null) {
          deliverableBlock.add(this.messageReturn(String.valueOf(deliverableRanking.getPersonalPerspective())));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_CENTER, 1);

        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);


        // ********** Deliverable Dissemination**************************************
        DeliverableDissemination deliverableDissemination = deliverable.getDissemination();
        table = new PdfPTable(1);
        table.setLockedWidth(true);
        table.setTotalWidth(480);

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination"));
        this.addTableHeaderCell(table, deliverableBlock);

        // Open access
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.open"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableDissemination != null) {
          if (deliverableDissemination.getIsOpenAccess() != null
            && deliverableDissemination.getIsOpenAccess().booleanValue()) {
            deliverableBlock.add("Yes");
          } else if (deliverableDissemination.getIntellectualProperty() != null
            && deliverableDissemination.getIntellectualProperty().booleanValue()) {
            deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.intellectual"));
          } else if (deliverableDissemination.getLimitedExclusivity() != null
            && deliverableDissemination.getLimitedExclusivity().booleanValue()) {
            deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.limited"));
          } else if (deliverableDissemination.getRestrictedUseAgreement() != null
            && deliverableDissemination.getRestrictedUseAgreement().booleanValue()) {

            deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.restricted"));
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.access"));
            deliverableBlock.setFont(TABLE_BODY_FONT);
            deliverableBlock.add(deliverableDissemination.getRestrictedAccessUntilText());

          } else if (deliverableDissemination.getEffectiveDateRestriction() != null
            && deliverableDissemination.getEffectiveDateRestriction().booleanValue()) {
            deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.effective"));
            this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

            deliverableBlock = new Paragraph();
            deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
            deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.embargoed"));
            deliverableBlock.setFont(TABLE_BODY_FONT);
            deliverableBlock.add(deliverableDissemination.getRestrictedEmbargoedText());

          } else {
            deliverableBlock.add(this.messageReturn(null));
          }
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);


        // License adopted

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.license"));
        deliverableBlock.setFont(TABLE_BODY_FONT);


        if (deliverableDissemination != null) {
          if (deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_LICENSE) != null) {
            deliverableBlock.add(this
              .messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_LICENSE)));
          } else {
            deliverableBlock.add("No");
          }
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);


        // // Dissemination channel
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.channel"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableDissemination != null) {
          deliverableBlock.add(this.messageReturn(deliverableDissemination.getDisseminationChannel()));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        // Dissemination URL
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.dissemination.url"));
        deliverableBlock.setFont(TABLE_BODY_FONT_LINK);

        if (deliverableDissemination != null) {
          deliverableBlock.add(this.messageReturn(deliverableDissemination.getDisseminationUrl()));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

        // ********** Deliverable Metadata**************************************
        DeliverablePublicationMetadata deliverableMetadata = deliverable.getPublicationMetadata();
        table = new PdfPTable(1);
        table.setLockedWidth(true);
        table.setTotalWidth(480);

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata"));
        this.addTableHeaderCell(table, deliverableBlock);

        // Description
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata.description"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableMetadata != null) {
          deliverableBlock.add(this
            .messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_DESCRIPTION)));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        // creator
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata.creator"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableMetadata != null) {
          deliverableBlock.add(
            this.messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_CREATOR)));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);


        // authorID
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata.authorID"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableMetadata != null) {
          deliverableBlock.add(this
            .messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_CREATOR_ID)));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        // Creation
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata.creation"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableMetadata != null) {
          deliverableBlock.add(this
            .messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_PUBLICATION)));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        // Language
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata.language"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableMetadata != null) {
          deliverableBlock.add(this.messageReturn(this
            .messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_LANGUAGE))));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);

        // Coverage
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.metadata.coverage"));
        deliverableBlock.setFont(TABLE_BODY_FONT);

        if (deliverableMetadata != null) {
          deliverableBlock.add(
            this.messageReturn(deliverable.getMetadataValueByEncondedName(APConstants.DELIVERABLE_ENCONDING_COVERAGE)));
        } else {
          deliverableBlock.add(this.messageReturn(null));
        }
        this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);
        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);

        // ********** Deliverable Data Sharing**************************************
        table = new PdfPTable(1);
        table.setLockedWidth(true);
        table.setTotalWidth(480);

        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_HEADER_FONT);
        deliverableBlock.setAlignment(Element.ALIGN_LEFT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.datasharing"));
        this.addTableHeaderCell(table, deliverableBlock);

        // Files
        deliverableBlock = new Paragraph();
        deliverableBlock.setFont(TABLE_BODY_BOLD_FONT);
        deliverableBlock.add(this.getText("summaries.project.reporting.deliverable.datasharing.files"));
        deliverableBlock.add("\n");
        deliverableBlock.setFont(TABLE_BODY_FONT);

        List<DeliverableDataSharingFile> deliverableDataSharingFileList = deliverable.getDataSharingFile();

        cell_new = new PdfPCell(deliverableBlock);
        cell_new.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell_new.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell_new.setBackgroundColor(TABLE_BODY_ODD_ROW_BACKGROUND);
        cell_new.setUseBorderPadding(true);
        cell_new.setPadding(3);
        cell_new.setBorderColor(TABLE_CELL_BORDER_COLOR);
        cell_new.setColspan(2);
        Anchor anchor;
        Phrase myurl;
        counter = 0;
        if (deliverableDataSharingFileList != null) {
          for (DeliverableDataSharingFile deliverableDataSharingFile : deliverableDataSharingFileList) {
            if (deliverableDataSharingFile != null) {

              anchor = new Anchor(deliverableDataSharingFile.getFile(), TABLE_BODY_FONT_LINK);
              anchor.setReference(config.getDownloadURL() + "/projects/" + project.getId() + "/hightlightsImage/"
                + deliverableDataSharingFile.getFile());
              myurl = new Phrase();
              myurl.add(anchor);
              myurl.setFont(TABLE_BODY_FONT_LINK);

              cell_new.addElement(myurl);
              if (counter > 1) {
                cell_new.addElement(new Paragraph("\n"));
              }
              counter++;
            }
          }
          table.addCell(cell_new);
        } else {
          deliverableBlock.setFont(TABLE_BODY_FONT);
          deliverableBlock.add(this.messageReturn(null));
          this.addTableBodyCell(table, deliverableBlock, Element.ALIGN_LEFT, 1);
        }
        document.add(table);
        deliverableBlock = new Paragraph();
        deliverableBlock.add(Chunk.NEWLINE);
        deliverableBlock.add(Chunk.NEWLINE);
        document.add(deliverableBlock);
      }


    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }


  /**
   * This method is used for equalize the size Flashing list and Region list
   * 
   * @param year year of Budget
   * @param size size list more big
   */
  private void addEmptyFlashigAndRegion(List<IPProgram> listToAdding, int size) {
    for (int a = 0; a <= size; a++) {
      if (a > listToAdding.size()) {
        listToAdding.add(new IPProgram());
      }
    }
  }


  /**
   * This method is used for add the main information table of project summary
   */
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

      cellContent = new Paragraph(
        this.messageReturn(
          project.getLiaisonInstitution().getAcronym() + " - " + project.getLiaisonInstitution().getName()),
        TABLE_BODY_FONT);
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
      if (project.getLeaderPerson() == null || project.getLeaderPerson().getUser() == null) {
        cellContent = new Paragraph(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
      } else {
        cellContent =
          new Paragraph(this.messageReturn(project.getLeaderPerson().getUser().getComposedName()), TABLE_BODY_FONT);
      }

      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 0);

      // Fourth row
      cellContent = (new Paragraph(this.getText("summaries.project.projectType"), TABLE_BODY_BOLD_FONT));
      this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

      cellContent = new Paragraph(this.messageReturn(project.getType().replaceAll("_", " ")), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      // Fiveth row
      Chunk imdb = null;
      Font hyperLink = new Font(FontFactory.getFont("openSans", 10, Color.BLUE));
      hyperLink.setStyle(Font.UNDERLINE);

      // -- Not Bilateral
      if (!project.isBilateralProject()) {
        cellContent = (new Paragraph(this.getText("summaries.project.detailed"), TABLE_BODY_BOLD_FONT));
        this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);

        if (project.getWorkplanName() != null && !project.getWorkplanName().equals("")) {
          imdb = new Chunk(project.getWorkplanName(), hyperLink);
          try {
            imdb.setAction(new PdfAction(new URL(this.messageReturn(project.getWorkplanURL()))));
          } catch (MalformedURLException exp) {
            imdb = new Chunk(project.getWorkplanName(), TABLE_BODY_FONT);
            LOG.error("There is an Malformed exception in " + project.getWorkplanName());
          }
        } else {
          imdb = new Chunk(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
        }
      }

      // -- Bilateral
      else {
        cellContent =
          (new Paragraph(this.getText("summaries.project.ipContributions.proposal.space"), TABLE_BODY_BOLD_FONT));
        this.addTableBodyCell(table, cellContent, Element.ALIGN_RIGHT, 1);


        if (project.getBilateralContractProposalName() != null
          && !project.getBilateralContractProposalName().equals("")) {
          imdb = new Chunk(project.getBilateralContractProposalName(), hyperLink);
          try {
            imdb.setAction(new PdfAction(new URL(this.messageReturn(project.getWorkplanURL()))));
          } catch (MalformedURLException exp) {
            imdb = new Chunk(project.getBilateralContractProposalName(), TABLE_BODY_FONT);
            LOG.error(
              "There is an Malformed exception in bilateral contract: " + project.getBilateralContractProposalName());
          }
        } else {
          imdb = new Chunk(this.getText("summaries.project.empty"), TABLE_BODY_FONT);
        }
      }

      cellContent = new Paragraph();
      cellContent.add(imdb);
      this.addTableBodyCell(table, cellContent, Element.ALIGN_LEFT, 1);

      document.add(table);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  /**
   * This method is used for add Overview in the project summary
   * 
   * @param table table that contain information of overwiew
   * @param mog IPElement
   * @param year year of Budget
   */
  private void addOverview(PdfPTable table, IPElement mog, int year) {

    Paragraph overviewBlock = new Paragraph();
    overviewBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    // overviewBlock.setKeepTogether(true);
    StringBuffer overviewLabel = new StringBuffer();

    // Get OverviewByMog, Year and project
    OutputOverview overviewYear = project.getOverviewByMOGAndYear(mog.getId(), year);

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
    overviewLabel.append(" " + this.getText("summaries.project.expected.contribution.last"));
    overviewBlock.add(overviewLabel.toString());
    overviewBlock.add(Chunk.NEWLINE);


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

    //

    Calendar dateTime = new GregorianCalendar();
    if (project.isReporting() && year < dateTime.get(Calendar.YEAR)) {
      // Brief summary Annual Contribution title
      overviewLabel = new StringBuffer();
      overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
      overviewLabel
        .append(this.getText("summaries.project.reporting.outcome.briefSummary", new String[] {String.valueOf(year)}));
      overviewBlock.add(overviewLabel.toString());
      overviewBlock.add(Chunk.NEWLINE);

      // Brief Summary Social Annual Contribution text
      overviewBlock.setFont(TABLE_BODY_FONT);
      overviewLabel = new StringBuffer();
      if (overviewYear != null) {
        overviewLabel.append(this.messageReturn(overviewYear.getBriefSummary()));
      } else {
        overviewLabel.append(this.getText("summaries.project.empty"));
      }
      overviewBlock.add(overviewLabel.toString());
      overviewBlock.add(Chunk.NEWLINE);
      overviewBlock.add(Chunk.NEWLINE);
    }


    // Social Annual Contribution title
    overviewLabel = new StringBuffer();
    overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
    overviewLabel.append(this.getText("summaries.project.social.contribution", new String[] {String.valueOf(year)}));
    overviewBlock.add(overviewLabel.toString());
    overviewBlock.add(Chunk.NEWLINE);

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

    // summaries.project.reporting.outcome.summaryGender
    if (project.isReporting() && year < dateTime.get(Calendar.YEAR)) {
      // Summary gender Brief
      overviewLabel = new StringBuffer();
      overviewBlock.setFont(TABLE_BODY_BOLD_FONT);
      overviewLabel
        .append(this.getText("summaries.project.reporting.outcome.summaryGender", new String[] {String.valueOf(year)}));
      overviewBlock.add(overviewLabel.toString());
      overviewBlock.add(Chunk.NEWLINE);

      // Brief Summary Social Gender Contribution text
      overviewBlock.setFont(TABLE_BODY_FONT);
      overviewLabel = new StringBuffer();
      if (overviewYear != null) {
        overviewLabel.append(this.messageReturn(overviewYear.getSummaryGender()));
      } else {
        overviewLabel.append(this.getText("summaries.project.empty"));
      }
      overviewBlock.add(overviewLabel.toString());
      overviewBlock.add(Chunk.NEWLINE);
      overviewBlock.add(Chunk.NEWLINE);
    }
    this.addTableBodyCell(table, overviewBlock, Element.ALIGN_JUSTIFIED, 1);
  }

  /**
   * This is an auxiliar method for to get information project partners in the paragrapn block.
   * 
   * @param partner partner to get information
   * @param numberPP number of project partner
   * @param partnersBlock paragraph to add the information
   */
  private void addPartner(ProjectPartner partner, int numberPP) {

    if (!(partner == null)) {
      try {
        PdfPTable table = new PdfPTable(1);
        table.setTotalWidth(500);
        table.setLockedWidth(true);

        // /////////////////////////////////////////////////////////////////////
        Paragraph paragraph = new Paragraph();

        // Partner #
        paragraph.setFont(HEADING3_FONT);

        if (project.getLeader() != null && partner.getId() == project.getLeader().getId()) {
          paragraph.add(this.getText("summaries.project.partner") + numberPP + " "
            + this.getText("summaries.project.partner.leader"));
        } else {
          paragraph.add(this.getText("summaries.project.partner") + numberPP + " ");
        }

        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);


        // Organization
        paragraph = new Paragraph();
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        paragraph.add(this.getText("summaries.project.organization"));
        paragraph.setFont(BODY_TEXT_FONT);
        if (partner.getInstitution() != null) {
          if (!((partner.getInstitution().getAcronym() == null) || (partner.getInstitution().getAcronym().isEmpty()))) {
            paragraph.add(partner.getInstitution().getAcronym());
            paragraph.add(" - ");

          }
          paragraph.add(this.messageReturn(partner.getInstitution().getName()));
        } else {
          paragraph.add("Not defined");
        }

        document.add(paragraph);

        // CCAFS Partner(s) allocating budget:
        if (!partner.getInstitution().isPPA()) {
          table = new PdfPTable(1);
          table.setTotalWidth(500);
          table.setLockedWidth(true);

          paragraph = new Paragraph();
          paragraph.add(Chunk.NEWLINE);

          // title contributing
          paragraph.setFont(BODY_TEXT_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.projectPartners.ccafs.partner.allocating"));


          List<ProjectPartner> partnersContributings = partner.getPartnerContributors();
          if (partnersContributings.isEmpty()) {
            paragraph.add(": ");
            paragraph.setFont(BODY_TEXT_FONT);
            paragraph.add(this.getText("summaries.project.empty"));
            document.add(paragraph);
          } else {
            this.addCustomTableCell(table, paragraph, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
              table.getNumberOfColumns(), 0, false);

            for (ProjectPartner partnerContributing : partnersContributings) {
              paragraph = new Paragraph();
              this.addTableBodyCell(table,
                new Paragraph(partnerContributing.getInstitution().getComposedName(), TABLE_BODY_FONT),
                Element.ALIGN_JUSTIFIED, 1);
            }
          }
          document.add(table);

        }

        paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);

        // PartnerPersons
        table = new PdfPTable(3);
        table.setTotalWidth(500);
        table.setLockedWidth(true);
        table.setWidths(new int[] {1, 3, 6});

        paragraph = new Paragraph();
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add(this.getText("summaries.project.partner.partnerPerson"));
        this.addCustomTableCell(table, paragraph, Element.ALIGN_LEFT, BODY_TEXT_BOLD_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        // ***** Header Partner persons

        // Person type
        paragraph = new Paragraph();
        paragraph.setFont(TABLE_HEADER_FONT);
        paragraph.add(this.getText("summaries.project.partner.persontype"));
        this.addTableHeaderCell(table, paragraph);

        // contact person
        paragraph = new Paragraph();
        paragraph.setFont(TABLE_HEADER_FONT);
        paragraph.add(this.getText("summaries.project.partner.contactperson"));
        this.addTableHeaderCell(table, paragraph);

        // responsabities
        paragraph = new Paragraph();
        paragraph.setFont(TABLE_HEADER_FONT);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.add(this.getText("summaries.project.partner.responsibilities"));
        this.addTableHeaderCell(table, paragraph);

        paragraph.setFont(TABLE_BODY_FONT);

        for (PartnerPerson partnerPerson : partner.getPartnerPersons()) {

          // type
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_FONT);
          if (partnerPerson.getType() == null) {
            paragraph.add(this.getText("summary.project.empty"));
          } else if (partnerPerson.getType().equals("PL")) {
            paragraph.add(this.getText("planning.projectPartners.types.PL"));
          } else if (partnerPerson.getType().equals("PC")) {
            paragraph.add(this.getText("planning.projectPartners.types.PC"));
          } else {
            // is CP
            paragraph.add(this.getText("planning.projectPartners.types.CP"));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_CENTER, 1, 1);

          // contact person
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_FONT);
          paragraph.add(this.messageReturn(partnerPerson.getComposedName()));
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_CENTER, 1, 1);

          // Responsabilities
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_FONT);
          paragraph.add(this.messageReturn(partnerPerson.getResponsibilities()));
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_JUSTIFIED, 1, 1);

        }

        document.add(table);
        paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);

      } catch (DocumentException e) {
        LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
      }
    }
  }

  private void addProjectBudgets() {

    int startYear = 0;
    int endYear = 0;

    Paragraph paragraph = new Paragraph();

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
    paragraph.setFont(HEADING2_FONT);
    paragraph.add("7. " + this.getText("summaries.project.budget"));
    try {
      document.newPage();
      paragraph.add(Chunk.NEWLINE);
      paragraph.add(Chunk.NEWLINE);
      document.add(paragraph);


      // If project is Bilateral to ask the following;
      if (project.isBilateralProject() && project.getOverhead() != null) {
        paragraph = new Paragraph();
        paragraph.setFont(BODY_TEXT_BOLD_FONT);


        // Overhead
        if (!project.getOverhead().isBilateralCostRecovered()) {
          paragraph.add(this.getText("summaries.project.budget.overhead"));
          paragraph.setFont(BODY_TEXT_FONT);
          paragraph.add(this.genderFormatter.format(project.getOverhead().getContractedOverhead()));
        } else {
          paragraph.add(this.getText("summaries.project.budget.cost.covered"));
          paragraph.setFont(BODY_TEXT_FONT);
          paragraph.add(this.getText("summaries.options.yes"));
        }
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);
      }

      // Summary table Overall
      this.addBudgetsSummaryByPartners(startYear, endYear, 0);

      // Summary table Gender
      this.addBudgetsSummaryByPartners(startYear, endYear, 1);


      // ************************Budget By Partners*************************************
      paragraph = new Paragraph();
      paragraph.add(Chunk.NEWLINE);
      paragraph.setFont(HEADING3_FONT);
      paragraph.add("7.1 " + this.getText("summaries.project.budget.partners"));
      paragraph.add(Chunk.NEWLINE);


      paragraph.add(Chunk.NEWLINE);
      paragraph.add(Chunk.NEWLINE);
      document.add(paragraph);


      if (project.isBilateralProject()) {
        table = new PdfPTable(4);
        table.setWidths(new int[] {2, 3, 3, 3});
        table.setTotalWidth(480);
        this.addBudgetPartner(project.getLeader(), paragraph, table, BudgetType.W3_BILATERAL, startYear, endYear);

        document.add(table);
        paragraph = new Paragraph(Chunk.NEWLINE);
        paragraph = new Paragraph(Chunk.NEWLINE);

        document.add(paragraph);


      } else {

        List<ProjectPartner> listProjectPartner = project.getPPAPartners();
        listProjectPartner.add(project.getLeader());
        listProjectPartner = this.removePartnersRepeat(listProjectPartner);

        for (ProjectPartner projectPartner : listProjectPartner) {
          table = new PdfPTable(4);
          table.setWidths(new int[] {2, 3, 3, 3});
          table.setTotalWidth(480);

          if (project.isCoFundedProject()) {
            this.addBudgetPartner(projectPartner, paragraph, table, BudgetType.W1_W2, startYear, endYear);
            document.add(table);
            paragraph = new Paragraph(Chunk.NEWLINE);
            paragraph.add(Chunk.NEWLINE);
            document.add(paragraph);

            table = new PdfPTable(4);
            table.setWidths(new int[] {2, 3, 3, 3});
            table.setTotalWidth(480);
            this.addBudgetPartner(projectPartner, paragraph, table, BudgetType.W3_BILATERAL, startYear, endYear);
          } else {
            this.addBudgetPartner(projectPartner, paragraph, table, this.getBudgetType(), startYear, endYear);
          }

          document.add(table);
          paragraph = new Paragraph(Chunk.NEWLINE);
          paragraph = new Paragraph(Chunk.NEWLINE);

          document.add(paragraph);
        }
      }
      // }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }

    // ************************Budget By Contributions*************************************
    paragraph = new Paragraph();
    paragraph.setFont(HEADING3_FONT);
    paragraph.add(Chunk.NEWLINE);
    if (project.isBilateralProject()) {
      paragraph.add("7.2 " + this.getText("summaries.project.budget.contribution.bilateral"));
    } else {
      paragraph.add("7.2 " + this.getText("summaries.project.budget.contribution.cofunded"));
    }
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);

    try {
      document.add(paragraph);

      paragraph = new Paragraph();
      List<Project> projectLinkagesList = project.getLinkedProjects();
      if (projectLinkagesList.isEmpty()) {
        paragraph.add(this.getText("summaries.project.empty"));
        document.add(paragraph);
      } else {

        table = new PdfPTable(3);
        table.setTotalWidth(480);
        table.setWidths(new int[] {7, 1, 2});
        table.setLockedWidth(true);
        this.addTableHeaderCell(table,
          new Paragraph(this.getText("summaries.project.budget.overall.project"), TABLE_HEADER_FONT));

        this.addTableHeaderCell(table,
          new Paragraph(this.getText("summaries.project.budget.overall.year"), TABLE_HEADER_FONT));

        StringBuilder type = new StringBuilder();
        type.append((this.getBudgetType() == null) ? BudgetType.W3_BILATERAL.name().replace("_", "/")
          : BudgetType.W1_W2.name().replace("_", "/"));

        type.append("\n (USD)");

        this.addTableHeaderCell(table, new Paragraph(
          this.getText("summaries.project.budget.overall.amount", new String[] {type.toString()}), TABLE_HEADER_FONT));
        Budget annualContribution;
        double totalAnnualContribution = 0.0;
        for (Project linkageProject : projectLinkagesList) {

          // Budgets for projects

          annualContribution = linkageProject.getAnualContribution();
          if (annualContribution != null) {

            this.addTableBodyCell(table, new Paragraph(linkageProject.getComposedName(), TABLE_BODY_FONT),
              Element.ALIGN_JUSTIFIED, 1);

            this.addTableBodyCell(table, new Paragraph(String.valueOf(annualContribution.getYear()), TABLE_BODY_FONT),
              Element.ALIGN_CENTER, 1);

            totalAnnualContribution += annualContribution.getAmount();
            this.addTableBodyCell(table,
              new Paragraph(this.budgetFormatter.format(annualContribution.getAmount()), TABLE_BODY_FONT),
              Element.ALIGN_CENTER, 1);
          }

        }

        this.addTableColSpanCell(table,
          new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_BODY_BOLD_FONT),
          Element.ALIGN_CENTER, 1, 2);

        this.addTableBodyCell(table, new Paragraph(String.valueOf(totalAnnualContribution), TABLE_BODY_BOLD_FONT),
          Element.ALIGN_CENTER, 1);

        document.add(table);
      }
      document.add(Chunk.NEWLINE);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }


    // ************************Budget By Mogs*************************************
    paragraph = new Paragraph();
    paragraph.setFont(HEADING3_FONT);
    paragraph.add(Chunk.NEWLINE);
    paragraph.add("7.3 " + this.getText("summaries.project.budget.mog"));
    paragraph.add(Chunk.NEWLINE);
    paragraph.add(Chunk.NEWLINE);
    try {

      document.add(paragraph);

      paragraph = new Paragraph();
      List<IPElement> outputsList = project.getOutputs();
      if (outputsList.isEmpty()) {
        paragraph.setFont(BODY_TEXT_FONT);
        paragraph.add(this.getText("summaries.project.empty"));
        document.add(paragraph);
      }

      StringBuffer budgetLabel = new StringBuffer();;
      table = new PdfPTable(5);

      table.setTotalWidth(480);
      table.setWidths(new int[] {2, 3, 3, 3, 3});
      table.setLockedWidth(true);


      for (IPElement mog : outputsList) {
        table = new PdfPTable(5);

        table.setTotalWidth(480);
        table.setWidths(new int[] {2, 3, 3, 3, 3});
        table.setLockedWidth(true);


        if (project.isCoFundedProject()) {
          this.addBudgetByMogOne(paragraph, table, budgetLabel, mog, startYear, endYear, BudgetType.W1_W2);
          document.add(table);

          table = new PdfPTable(5);

          table.setTotalWidth(480);
          table.setWidths(new int[] {2, 3, 3, 3, 3});
          table.setLockedWidth(true);


          document.add(Chunk.NEWLINE);
          this.addBudgetByMogOne(paragraph, table, budgetLabel, mog, startYear, endYear, BudgetType.W3_BILATERAL);
        } else {
          this.addBudgetByMogOne(paragraph, table, budgetLabel, mog, startYear, endYear, this.getBudgetType());
        }

        document.add(table);
        document.add(Chunk.NEWLINE);
      }
      // }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }
  }

  private void addProjectCCAFSOutcomes(String number) {
    PdfPTable table = new PdfPTable(3);

    Paragraph cell = new Paragraph();
    Paragraph indicatorsBlock = new Paragraph();
    indicatorsBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    indicatorsBlock.setKeepTogether(true);

    Paragraph title =
      new Paragraph(number + ".2 " + this.getText("summaries.project.indicatorsContribution"), HEADING3_FONT);
    indicatorsBlock.add(Chunk.NEWLINE);
    indicatorsBlock.add(title);

    try {
      document.add(indicatorsBlock);
      List<IPElement> listIPElements = this.getMidOutcomesPerIndicators();
      if (!listIPElements.isEmpty()) {

        if (project.isReporting()) {

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

              PdfPCell cell_new;
              for (IPIndicator indicator : indicators) {

                table = new PdfPTable(3);
                table.setLockedWidth(true);
                table.setTotalWidth(480);
                table.setWidths(new int[] {3, 3, 3});
                table.setHeaderRows(1);

                if (indicator.getOutcome().getId() != outcome.getId()) {
                  continue;
                }

                cell = new Paragraph(this.messageReturn(String.valueOf(indicator.getYear())), TABLE_HEADER_FONT);

                cell_new = new PdfPCell(cell);
                // Set alignment
                cell_new.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell_new.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell_new.setBackgroundColor(TABLE_HEADER_BACKGROUND);

                // Set padding
                cell_new.setUseBorderPadding(true);
                cell_new.setPadding(3);

                // Set border color
                cell_new.setBorderColor(TABLE_CELL_BORDER_COLOR);
                cell_new.setColspan(3);

                this.addTableHeaderCell(table, cell_new);
                // Target value
                cell = new Paragraph(this.getText("summaries.project.indicator.targetValue"), TABLE_BODY_BOLD_FONT);
                cell.setFont(TABLE_BODY_FONT);
                cell.add(this.messageReturn(indicator.getTarget()));

                this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

                // Cumulative target to date
                // TODO
                cell = new Paragraph(this.getText("summaries.project.indicator.cumulative"), TABLE_BODY_BOLD_FONT);
                cell.setFont(TABLE_BODY_FONT);
                cell.add(this.messageReturn(project.calculateAcumulativeTarget(indicator.getYear(), indicator)));
                if (indicator.getYear() <= this.currentReportingYear) {
                  this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);
                  // achieved
                  cell = new Paragraph(this.getText("summaries.project.indicator.archieved"), TABLE_BODY_BOLD_FONT);
                  cell.setFont(TABLE_BODY_FONT);
                  if (indicator.getArchived() == null) {
                    cell.add(this.messageReturn(null));
                  } else {
                    cell.add(this.messageReturn(String.valueOf(indicator.getArchived())));
                  }
                  this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);
                } else {
                  this.addTableColSpanCell(table, cell, Element.ALIGN_JUSTIFIED, 1, 2);
                }
                // target narrative
                cell = new Paragraph(this.getText("summaries.project.indicator.targetNarrative"), TABLE_BODY_BOLD_FONT);
                cell.setFont(TABLE_BODY_FONT);
                cell.add(this.messageReturn(indicator.getDescription()));
                this.addTableColSpanCell(table, cell, Element.ALIGN_JUSTIFIED, 1, 3);

                // targets achieved
                if (indicator.getYear() <= this.currentReportingYear) {
                  cell =
                    new Paragraph(this.getText("summaries.project.indicator.targetsAchieved"), TABLE_BODY_BOLD_FONT);
                  cell.setFont(TABLE_BODY_FONT);
                  cell.add(this.messageReturn(indicator.getNarrativeTargets()));
                  this.addTableColSpanCell(table, cell, Element.ALIGN_JUSTIFIED, 1, 3);
                }

                // Target gender
                cell = new Paragraph(this.getText("summaries.project.indicator.targetGender"), TABLE_BODY_BOLD_FONT);
                cell.setFont(TABLE_BODY_FONT);
                cell.add(this.messageReturn(indicator.getGender()));
                this.addTableColSpanCell(table, cell, Element.ALIGN_JUSTIFIED, 1, 3);

                // Target achieved gender
                if (indicator.getYear() <= this.currentReportingYear) {
                  cell =
                    new Paragraph(this.getText("summaries.project.indicator.genderAchieved"), TABLE_BODY_BOLD_FONT);
                  cell.setFont(TABLE_BODY_FONT);
                  cell.add(this.messageReturn(indicator.getNarrativeGender()));
                  this.addTableColSpanCell(table, cell, Element.ALIGN_JUSTIFIED, 1, 3);
                }

                document.add(table);
                document.add(Chunk.NEWLINE);

              }
              indicatorIndex++;


            }
          }

          //////////// Planning
        } else {


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

              table = new PdfPTable(4);
              table.setLockedWidth(true);
              table.setTotalWidth(480);
              table.setWidths(new int[] {1, 3, 3, 3});
              table.setHeaderRows(1);

              // Headers
              cell = new Paragraph(this.getText("summaries.project.indicator.year"), TABLE_HEADER_FONT);
              this.addTableHeaderCell(table, cell);
              cell = new Paragraph(this.getText("summaries.project.indicator.targetValue"), TABLE_HEADER_FONT);
              this.addTableHeaderCell(table, cell);
              cell = new Paragraph(this.getText("summaries.project.indicator.targetNarrative"), TABLE_HEADER_FONT);
              this.addTableHeaderCell(table, cell);
              cell = new Paragraph(this.getText("summaries.project.indicator.targetGender"), TABLE_HEADER_FONT);
              this.addTableHeaderCell(table, cell);

              for (IPIndicator indicator : indicators) {

                if (indicator.getOutcome().getId() != outcome.getId()) {
                  continue;
                }
                cell = new Paragraph(this.messageReturn(String.valueOf(indicator.getYear())), TABLE_BODY_FONT);
                this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

                cell = new Paragraph(this.messageReturn(indicator.getTarget()), TABLE_BODY_FONT);
                this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

                cell = new Paragraph(this.messageReturn(indicator.getDescription()), TABLE_BODY_FONT);
                this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

                cell = new Paragraph(this.messageReturn(indicator.getGender()), TABLE_BODY_FONT);
                this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

              }
              indicatorIndex++;
              document.add(table);
              document.add(Chunk.NEWLINE);
            }
          }
        }

        // When there isn't elements in indicators
      } else {
        cell = new Paragraph(this.getText("summaries.project.empty"));
        document.add(cell);
      }
    } catch (

    DocumentException e)

    {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
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
        this.addCustomTableCell(table, cell, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
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

        for (Project projectContribution : project.getLinkedProjects()) {
          projectFocuses = new StringBuffer();
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


      /*
       * if (project.isBilateralProject()) {
       * projectFocuses = new StringBuffer();
       * cell = new Paragraph();
       * cell.setFont(BODY_TEXT_BOLD_FONT);
       * projectFocuses.append(this.getText("summaries.project.ipContributions.proposal"));
       * cell.add(projectFocuses.toString());
       * cell.setFont(BODY_TEXT_FONT);
       * cell.add(this.messageReturn(project.getBilateralContractProposalName()));
       * document.add(cell);
       * document.add(Chunk.NEWLINE);
       * }
       */


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

        if (!project.isReporting()) {
          // Lesson regarding locations
          Paragraph locationsBlock = new Paragraph();
          locationsBlock.setAlignment(Element.ALIGN_JUSTIFIED);
          locationsBlock.add(Chunk.NEWLINE);
          locationsBlock.add(Chunk.NEWLINE);
          locationsBlock.setFont(BODY_TEXT_BOLD_FONT);
          locationsBlock.add(this.getText("summaries.project.location.lessonRegarding"));
          locationsBlock.setFont(BODY_TEXT_FONT);
          if (project.getComponentLesson("locations") != null) {
            locationsBlock.add(this.messageReturn(project.getComponentLesson("locations").getLessons()));
          } else {
            locationsBlock.add(this.messageReturn(null));
          }
          document.add(locationsBlock);
        }
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

  /**
   * This method is used for add Outcomes in the project summary
   */
  private void addProjectOutcomes(String number) {
    Paragraph outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph title = new Paragraph(number + ". " + this.getText("summaries.project.outcome"), HEADING2_FONT);
    outcomesBlock.add(title);
    outcomesBlock.add(Chunk.NEWLINE);;
    title = new Paragraph();
    title.setFont(HEADING3_FONT);
    title.add(number + ".1 " + this.getText("summaries.project.outcomeNarrative"));
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

    Anchor anchor;
    currentPlanningYear--;
    ////////////////// Reporting
    PdfPTable table;
    if (project.isReporting()) {
      for (int year = currentPlanningYear; year < midOutcomeYear; year++) {

        // Annual progress towards
        outcomesBlock = new Paragraph();
        outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
        outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomesBlock.add(this.getText("summaries.project.outcomeAnnualProgress", new String[] {String.valueOf(year)}));
        outcomesBlock.setFont(BODY_TEXT_FONT);

        if (project.getOutcomes() == null || project.getOutcomes().get(String.valueOf(year)) == null) {
          outcomesBlock.add(this.getText("summaries.project.empty"));
        } else {
          outcomesBlock.add(this.messageReturn(project.getOutcomes().get(String.valueOf(year)).getStatement()));
        }
        outcomesBlock.add(Chunk.NEWLINE);
        outcomesBlock.add(Chunk.NEWLINE);

        try {
          document.add(outcomesBlock);
        } catch (DocumentException e) {
          LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
        }


        if (year == this.currentPlanningYear) {
          table = new PdfPTable(1);
          table.setLockedWidth(true);
          table.setTotalWidth(500);

          ///// outcome toward in reporting cycle
          outcomesBlock = new Paragraph();
          outcomesBlock.setFont(TABLE_BODY_BOLD_FONT);
          outcomesBlock
            .add(this.getText("summaries.project.outcomeAnnualTowards", new String[] {String.valueOf(year)}));

          outcomesBlock.setFont(TABLE_BODY_FONT);

          if (project.getOutcomes() == null || project.getOutcomes().get(String.valueOf(year)) == null) {
            outcomesBlock.add(this.getText("summaries.project.empty"));
          } else {
            outcomesBlock.add(this.messageReturn(project.getOutcomes().get(String.valueOf(year)).getAnualProgress()));
          }

          this.addTableBodyCell(table, outcomesBlock, Element.ALIGN_JUSTIFIED, 1);

          ///// outcome communication
          outcomesBlock = new Paragraph();
          outcomesBlock.setFont(TABLE_BODY_BOLD_FONT);
          outcomesBlock.add(this.getText("summaries.project.outcomeAnnualCommunication"));
          outcomesBlock.setFont(TABLE_BODY_FONT);

          if (project.getOutcomes() == null || project.getOutcomes().get(String.valueOf(year)) == null) {
            outcomesBlock.add(this.getText("summaries.project.empty"));
          } else {
            outcomesBlock.add(this.messageReturn(project.getOutcomes().get(String.valueOf(year)).getComunication()));
          }
          this.addTableBodyCell(table, outcomesBlock, Element.ALIGN_JUSTIFIED, 1);

          // Any evendence
          outcomesBlock = new Paragraph();
          outcomesBlock.setFont(TABLE_BODY_BOLD_FONT);
          outcomesBlock.add(this.getText("summaries.project.outcomeAnnualEvidence"));
          outcomesBlock.setFont(TABLE_BODY_FONT);

          if (project.getOutcomes() == null || project.getOutcomes().get(String.valueOf(year)) == null
            || project.getOutcomes().get(String.valueOf(year)).getFile() == null
            || project.getOutcomes().get(String.valueOf(year)).getFile().equals("")) {
            outcomesBlock.add(this.getText("summaries.project.empty"));
          } else {

            anchor = new Anchor(project.getOutcomes().get(String.valueOf(year)).getFile(), TABLE_BODY_FONT_LINK);
            anchor.setReference(config.getDownloadURL() + "/projects/" + project.getId() + "/project_outcome/"
              + project.getOutcomes().get(String.valueOf(year)).getFile());
            outcomesBlock.add(anchor);
          }
          this.addTableBodyCell(table, outcomesBlock, Element.ALIGN_LEFT, 1);
          try {
            document.add(table);
            outcomesBlock = new Paragraph();
            outcomesBlock.add(Chunk.NEWLINE);
            document.add(outcomesBlock);
          } catch (DocumentException e) {
            LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
          }
        }
      }


    }
    ////////////////// Planning
    else {
      String outcomeProgress = new String();
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
        outcomesBlock.add(Chunk.NEWLINE);;


        try {
          document.add(outcomesBlock);
        } catch (DocumentException e) {
          LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
        }
      }

    }


    // Lesson regardins
    outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
    if (project.isReporting()) {
      outcomesBlock.add(this.getText("summaries.project.outcome.reporting.lessonRegarding"));
    } else {
      outcomesBlock.add(this.getText("summaries.project.outcome.lessonRegarding"));
    }
    outcomesBlock.setFont(BODY_TEXT_FONT);
    if (project.getComponentLesson("outcomes") != null) {
      outcomesBlock.add(this.messageReturn(project.getComponentLesson("outcomes").getLessons()));
    } else {
      outcomesBlock.add(this.messageReturn(null));
    }
    try {
      document.add(outcomesBlock);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }
    // ******************* CCAFS Outcomes***************/
    this.addProjectCCAFSOutcomes(number);


    // **********************************************************************************
    // ******************* Other contributions***************/
    // **********************************************************************************

    OtherContribution otherContribution = project.getIpOtherContribution();

    try {
      outcomesBlock = new Paragraph();
      outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);

      title = new Paragraph(
        number + ".3 " + this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions"), HEADING3_FONT);
      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.add(title);
      outcomesBlock.add(Chunk.NEWLINE);

      // Contribution to other Impact Pathways:
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      if (project.isReporting()) {
        outcomesBlock
          .add(this.getText("summaries.project.outcome.ccafs.outcomes.reporting.other.contributions.pathways"));
      } else {

        outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.pathways"));
      }
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

      document.add(outcomesBlock);
      document.add(Chunk.NEWLINE);

      Paragraph cell;

      if (project.isReporting()) {

        if (project.getOtherContributions().isEmpty()) {

        } else {
          table = new PdfPTable(4);
          table.setWidths(new int[] {2, 4, 4, 4});
          table.setLockedWidth(true);
          table.setTotalWidth(500);

          // adding headers
          cell = new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.region"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          cell = new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.indicator"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          cell = new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.describe",
            new String[] {String.valueOf(this.currentReportingYear)}), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          cell =
            new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.ablequantily"), TABLE_HEADER_FONT);
          this.addTableHeaderCell(table, cell);

          // Adding contain
          for (ProjecteOtherContributions projectOther : project.getOtherContributions()) {
            cell = new Paragraph(projectOther.getRegion(), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

            cell = new Paragraph(projectOther.getIndicators(), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

            cell = new Paragraph(projectOther.getDescription(), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

            cell = new Paragraph(String.valueOf(projectOther.getTarget()), TABLE_BODY_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);

          }
          document.add(table);
          document.add(Chunk.NEWLINE);
        }
        // // Collaboration with other CRPs
        cell =
          new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.collaborating"), BODY_TEXT_BOLD_FONT);

        if (project.getListCRPContributions().isEmpty()) {
          cell.add(": ");
          cell.setFont(BODY_TEXT_FONT);
          cell.add(this.messageReturn(null));
          cell.add(Chunk.NEWLINE);
          document.add(cell);
        } else {
          document.add(cell);
          document.add(Chunk.NEWLINE);

          for (CRPContribution crpContribution : project.getListCRPContributions()) {
            table = new PdfPTable(1);
            table.setLockedWidth(true);
            table.setTotalWidth(500);

            cell = new Paragraph(this.messageReturn(crpContribution.getCrp().getName()), TABLE_BODY_BOLD_FONT);
            cell.setAlignment(Element.ALIGN_LEFT);
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);


            cell = new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.natureCollaboration"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(this.messageReturn(crpContribution.getNatureCollaboration()));
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            cell = new Paragraph(this.getText("summaries.project.reporting.ccafs.outcomes.achievedOutcome"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(this.messageReturn(crpContribution.getExplainAchieved()));
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            document.add(table);
            document.add(Chunk.NEWLINE);
          }

        }
      }

      else {
        outcomesBlock = new Paragraph();
        // Contribution to another Center activity:
        outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.center"));
        outcomesBlock.setFont(BODY_TEXT_FONT);
        if (otherContribution == null || otherContribution.getAdditionalContribution() == null
          || otherContribution.getAdditionalContribution().equals("")) {
          outcomesBlock.add(": " + this.getText("summaries.project.empty"));
        } else {
          outcomesBlock.add(otherContribution.getAdditionalContribution());
          outcomesBlock.add(Chunk.NEWLINE);
          outcomesBlock.add(Chunk.NEWLINE);

        }

        boolean addParagraph = false;
        cell = new Paragraph();;
        cell.setFont(BODY_TEXT_BOLD_FONT);

        table = new PdfPTable(2);

        cell.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.covered"));
        if (otherContribution != null)

        {
          List<CRPContribution> listCRP = otherContribution.getCrpContributions();
          if (listCRP.isEmpty()) {
            cell.setFont(BODY_TEXT_FONT);
            cell.add(this.getText("summaries.project.empty"));
            addParagraph = true;
          } else {
            table.setWidths(new int[] {4, 6});
            table.setLockedWidth(true);
            table.setTotalWidth(500);

            this.addCustomTableCell(table, cell, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
              table.getNumberOfColumns(), 0, false);

            // adding headers
            this.addTableHeaderCell(table, new Paragraph(
              this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.crp"), TABLE_HEADER_FONT));

            this.addTableHeaderCell(table, new Paragraph(
              this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.nature"), TABLE_HEADER_FONT));

            for (CRPContribution CRPContribution : listCRP) {
              if (CRPContribution != null && CRPContribution.getCrp() != null) {
                cell = new Paragraph();
                cell.setFont(TABLE_BODY_FONT);
                cell.add(CRPContribution.getCrp().getName());
                this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

                cell = new Paragraph();
                cell.setFont(TABLE_BODY_FONT);
                cell.add(CRPContribution.getNatureCollaboration());
                this.addTableBodyCell(table, cell, Element.ALIGN_JUSTIFIED, 1);
              }
            }
          }
        } else

        {
          cell.add(": ");
          cell.setFont(BODY_TEXT_FONT);
          cell.add(this.getText("summaries.project.empty"));
          addParagraph = true;
        }
        document.add(outcomesBlock);
        // Add paragraphs to document
        if (addParagraph) {
          document.add(cell);
        } else

        {
          document.add(table);
        }

        document.add(Chunk.NEWLINE);

        // Lesson regardins Other contributions
        outcomesBlock = new Paragraph();
        outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
        outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.lessonRegarding"));
        outcomesBlock.setFont(BODY_TEXT_FONT);
        if (project.getComponentLesson("otherContributions") != null)

        {
          outcomesBlock.add(this.messageReturn(project.getComponentLesson("otherContributions").getLessons()));
        } else

        {
          outcomesBlock.add(this.messageReturn(null));
        }
        document.add(outcomesBlock);
      }
      // **********************************************************************************
      // *************************** Outcome Case Studies *************************************
      // **********************************************************************************
      int counter = 0;
      if (project.isReporting()) {
        document.newPage();
        title = new Paragraph(number + ".4 " + this.getText("summaries.project.reporting.outcome.case.studies"),
          HEADING3_FONT);
        document.add(title);

        if (project.getCaseStudies().isEmpty()) {
          document.add(new Paragraph(this.getText("summaries.project.reporting.outcome.not.case.studies")));
        } else {
          title = new Paragraph();
          title.add(Chunk.NEWLINE);
          document.add(title);

          for (CasesStudies caseStudy : project.getCaseStudies()) {

            counter++;
            table = new PdfPTable(1);
            table.setLockedWidth(true);
            table.setTotalWidth(500);

            // case study
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.case.study") + " #" + counter,
              TABLE_HEADER_FONT);
            this.addTableHeaderCell(table, cell);
            // this.addCustomTableCell(table, cell, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
            // table.getNumberOfColumns(), 0, false);

            // Title
            cell =
              new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.title"), TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getTitle());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Outcome statement
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.outcomestatement"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getOutcomeStatement());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Research outputs
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.researchoutputs"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getResearchOutputs());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Research partners
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.researchPartners"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getResearchPartners());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // activities Contributed
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.activitiesContributed"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getActivities());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Non Research Partners
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.nonResearchPartners"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getNonResearchPartneres());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Output Users
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.outputUsers"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getOutputUsers());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Output Used
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.outputWasUsed"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getOutputUsed());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Evidence
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.evidenceOutcome"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getEvidenceOutcome());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // References
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.references"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getReferencesCase());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Outcome indicators
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.primaryOutcome",
              new String[] {String.valueOf(this.midOutcomeYear)}), TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add("\n");
            for (IPIndicator ipIndicator : caseStudy.getCaseStudyIndicators()) {
              if (ipIndicator.getOutcome() != null) {
                cell.add(ipIndicator.getOutcome().getDescription());
              }
              cell.add(" " + ipIndicator.getDescription() + "\n");
            }
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // Explain link
            cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.explainLink"),
              TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(caseStudy.getExplainIndicatorRelation());
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // year
            cell =
              new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.year"), TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            cell.add(String.valueOf(caseStudy.getYear()));
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            // upload
            cell =
              new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.upload"), TABLE_BODY_BOLD_FONT);
            cell.setFont(TABLE_BODY_FONT);
            if (caseStudy.getFile() == null || caseStudy.getFile().equals("")) {
              cell.add(this.messageReturn(null));
            } else {
              anchor = new Anchor(caseStudy.getFile(), TABLE_BODY_FONT_LINK);
              anchor.setReference(
                config.getDownloadURL() + "/projects/" + project.getId() + "/caseStudy/" + caseStudy.getFile());
              cell.add(anchor);
            }
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            document.add(table);
            document.add(new Paragraph(Chunk.NEWLINE));
          }
        }
      }


    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }


  /**
   * Entering the project outputs in the summary
   */
  private void addProjectOutputs(String number) {

    // **********************************************************************************
    // *************************** Overview By Mog *************************************
    // **********************************************************************************
    try {
      Paragraph overview_title = new Paragraph();
      overview_title.setFont(HEADING2_FONT);
      overview_title.add(number + ". " + this.getText("summaries.project.projectOutput"));
      overview_title.add(Chunk.NEWLINE);
      overview_title.add(Chunk.NEWLINE);

      document.newPage();
      document.add(overview_title);

      overview_title = new Paragraph();
      overview_title.setFont(HEADING3_FONT);
      overview_title.add(number + ".1 " + this.getText("summaries.project.overviewbymogs"));
      document.add(overview_title);

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }

    int[] years =
      {midOutcomeYear, project.isReporting() ? this.currentReportingYear - 1 : this.currentReportingYear - 1,
        project.isReporting() ? this.currentReportingYear : this.currentPlanningYear,
        project.isReporting() ? this.currentReportingYear + 1 : this.currentReportingYear + 1};

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
          paragraph.setFont(TABLE_HEADER_FONT);
          paragraph.add(this.getText("summaries.project.overviewbymogs.text") + "- " + years[a]);
          table = new PdfPTable(1);
          table.setLockedWidth(true);
          table.setTotalWidth(480);
          this.addTableHeaderCell(table, paragraph);
          // Mog
          for (int b = 0; b < mogs.size(); b++) {
            this.addOverview(table, mogs.get(b), years[a]);
          }
          paragraph = new Paragraph();
          paragraph.add(Chunk.NEWLINE);
          document.add(paragraph);
          document.add(table);
        }

        // Leason regardins
        paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        if (!project.isReporting()) {
          paragraph.add(this.getText("summaries.project.output.lessonRegarding"));
        } else {
          paragraph.add(this.getText("summaries.project.overviewbymogs.reporting.lesson"));
        }
        paragraph.setFont(BODY_TEXT_FONT);
        if (project.getComponentLesson("outputs") != null) {
          paragraph.add(this.messageReturn(project.getComponentLesson("outputs").getLessons()));
        } else {
          paragraph.add(this.messageReturn(null));
        }
        paragraph.add(Chunk.NEWLINE);
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);
      }

    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }

    // **********************************************************************************
    // *************************** Deliverables.****************************************
    // **********************************************************************************

    int counter = 1;
    try {
      document.newPage();
      paragraph = new Paragraph();
      paragraph.setFont(HEADING3_FONT);
      paragraph.add(number + ".2 " + this.getText("summaries.project.deliverable.title"));
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


      // **********************************************************************************
      // *************************** Next users.****************************************
      // **********************************************************************************

      if (project.isReporting()) {

        counter = 1;
        document.newPage();
        paragraph = new Paragraph();
        paragraph.setFont(HEADING3_FONT);
        paragraph.add(number + ".3 " + this.getText("summaries.project.reporting.nextuser.title"));
        document.add(paragraph);


        PdfPTable table;

        if (project.getNextUsers().isEmpty()) {

          paragraph = new Paragraph();
          paragraph.add(this.messageReturn(null));
          paragraph.add(Chunk.NEWLINE);
          document.add(paragraph);

        } else {
          paragraph = new Paragraph();
          paragraph.add(Chunk.NEWLINE);
          document.add(paragraph);

          for (ProjectNextUser projectNextUser : project.getNextUsers()) {

            paragraph = new Paragraph();
            paragraph.setFont(TABLE_HEADER_FONT);
            paragraph.setAlignment(Element.ALIGN_LEFT);

            table = new PdfPTable(1);
            table.setLockedWidth(true);
            table.setTotalWidth(480);

            paragraph.add(this.getText("summaries.project.reporting.nextuser", new String[] {String.valueOf(counter)}));
            this.addTableHeaderCell(table, paragraph);

            // Next users key
            paragraph = new Paragraph();
            paragraph.setFont(TABLE_BODY_BOLD_FONT);
            paragraph.add(this.getText("summaries.project.reporting.nextuser.currentPeriod"));
            paragraph.setFont(TABLE_BODY_FONT);

            if (projectNextUser != null) {
              paragraph.add(this.messageReturn(projectNextUser.getKeyNextUser()));
            } else {
              paragraph.add(this.messageReturn(null));
            }
            this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

            // Strategies
            paragraph = new Paragraph();
            paragraph.setFont(TABLE_BODY_BOLD_FONT);
            paragraph.add(this.getText("summaries.project.reporting.nextuser.strategies"));
            paragraph.setFont(TABLE_BODY_FONT);

            if (projectNextUser != null) {
              paragraph.add(this.messageReturn(projectNextUser.getStrategies()));
            } else {
              paragraph.add(this.messageReturn(null));
            }
            this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);


            // reported
            paragraph = new Paragraph();
            paragraph.setFont(TABLE_BODY_BOLD_FONT);
            paragraph.add(this.getText("summaries.project.reporting.nextuser.reported"));
            paragraph.setFont(TABLE_BODY_FONT);

            if (projectNextUser != null) {
              paragraph.add(this.messageReturn(projectNextUser.getReportedDeliverables()));
            } else {
              paragraph.add(this.messageReturn(null));
            }
            this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

            // lessons
            paragraph = new Paragraph();
            paragraph.setFont(TABLE_BODY_BOLD_FONT);
            paragraph.add(this.getText("summaries.project.reporting.nextuser.lessons"));
            paragraph.setFont(TABLE_BODY_FONT);

            if (projectNextUser != null) {
              paragraph.add(this.messageReturn(projectNextUser.getLessonsImplications()));
            } else {
              paragraph.add(this.messageReturn(null));
            }
            this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

            counter++;
            document.add(table);
            paragraph = new Paragraph();
            paragraph.add(Chunk.NEWLINE);
            document.add(paragraph);
          }
        }


        // **********************************************************************************
        // *************************** Project HighLights****************************************
        // **********************************************************************************
        counter = 1;
        document.newPage();
        paragraph = new Paragraph();
        paragraph.setFont(HEADING3_FONT);
        paragraph.add(number + ".4 " + this.getText("summaries.project.reporting.highlight.principal"));
        document.add(paragraph);

        paragraph = new Paragraph();
        paragraph.add(Chunk.NEWLINE);
        document.add(paragraph);

        for (ProjectHighligths projectHighLigth : project.getHighlights()) {
          table = new PdfPTable(2);
          table.setLockedWidth(true);
          table.setTotalWidth(480);


          paragraph = new Paragraph();
          paragraph.setFont(TABLE_HEADER_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight", new String[] {String.valueOf(counter)}));
          this.addTableHeaderCellColspan(table, paragraph, 2);

          // title
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.title"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getTitle()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          // author
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.author"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getAuthor()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // subject
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.subject"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getSubject()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // publisher
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.publisher"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getPublisher()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // year
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.year"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(String.valueOf(projectHighLigth.getYear())));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // project highlight types
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.types"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            for (ProjectHighligthsTypes projectHighLigthTypes : projectHighLigth.getProjectHighligthsTypeses()) {
              paragraph.add(this.messageReturn("\n"
                + ProjectHighlightsType.getEnum(String.valueOf(projectHighLigthTypes.getIdType())).getDescription()));
            }
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // project images
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add("Image");

          if (projectHighLigth != null) {
            Image global;
            try {
              if (projectHighLigth.getPhoto() != null) {
                String urlImage =
                  config.getDownloadURL() + "/" + this.getHighlightsImagesUrlPath() + projectHighLigth.getPhoto();
                urlImage = urlImage.replace(" ", "%20");
                global = Image.getInstance(urlImage);

              } else {
                global = null;
              }

              if (global != null) {

                float documentWidth =
                  document.getPageSize().getWidth() - document.leftMargin() - document.rightMargin();
                float documentHeight =
                  document.getPageSize().getHeight() - document.topMargin() - document.bottomMargin();
                global.scaleToFit((float) (documentWidth * 0.4), global.getHeight());
                global.setAlignment(Element.ALIGN_CENTER);

                this.addTableBodyCell(table, global, Element.ALIGN_LEFT, 1);

              }

            } catch (MalformedURLException e) {

              e.printStackTrace();
            } catch (IOException e) {
              e.printStackTrace();

            }


          } else {
            paragraph.add(this.messageReturn(null));
          }


          // project start date
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.startDate"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(String.valueOf(projectHighLigth.getStartDate())));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // project end date
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.endDate"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(String.valueOf(projectHighLigth.getEndDate())));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);

          // Its Global
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.itsglobal"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            if (projectHighLigth.isIsGlobal()) {
              paragraph.add("Yes");
            } else {
              paragraph.add("No");
            };
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          // Country
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.country"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null && projectHighLigth.getProjectHighligthsCountries() != null) {
            for (ProjectHighligthsCountry country : projectHighLigth.getProjectHighligthsCountries()) {
              paragraph.add(locationManager.getCountry(country.getIdCountry()).getName() + "\n");
            }
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableBodyCell(table, paragraph, Element.ALIGN_LEFT, 1);
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.keywords"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getKeywords()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          // description
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.description"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getDescription()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);


          // Introduction / Objectives
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.introduction"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getObjectives()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          // Results
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.results"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getResults()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          // Partners
          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.partners"));
          paragraph.setFont(TABLE_BODY_FONT);
          if (projectHighLigth != null) {
            paragraph.add(this.messageReturn(projectHighLigth.getPartners()));
          } else {
            paragraph.add(this.messageReturn(null));
          }
          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          // Links


          paragraph = new Paragraph();
          paragraph.setFont(TABLE_BODY_BOLD_FONT);
          paragraph.add(this.getText("summaries.project.reporting.highlight.links"));
          paragraph.setFont(TABLE_BODY_FONT);
          paragraph.add(this.messageReturn(projectHighLigth.getLinks()));

          this.addTableColSpanCell(table, paragraph, Element.ALIGN_LEFT, 1, 2);

          document.add(table);
          document.newPage();
        }


      }
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  /**
   * Entering the project partners in the summary
   */
  private void addProjectPartners() {


    Paragraph partnersBlock = new Paragraph();
    partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    partnersBlock.setKeepTogether(true);

    Paragraph title = new Paragraph(32, "2. " + this.getText("summaries.project.projectPartners"), HEADING2_FONT);
    partnersBlock.add(title);
    try {
      document.newPage();

      if (project.getLeader() == null && project.getProjectPartners().isEmpty()) {
        title = new Paragraph(this.getText("summaries.project.empty"), BODY_TEXT_FONT);
        partnersBlock.add(title);
        document.add(partnersBlock);
        document.add(Chunk.NEWLINE);
      } else {
        document.add(partnersBlock);
        document.add(Chunk.NEWLINE);

        int c = 1;

        List<ProjectPartner> partnersList = project.getProjectPartners();
        if (!partnersList.isEmpty()) {
          for (ProjectPartner partner : partnersList) {
            this.addPartner(partner, c);
            c++;
          }
        }
      }

      if (project.isReporting()) {
        // Please describe how your partnerships overall ha
        List<ProjectPartner> partnersList = project.getProjectPartners();

        partnersBlock = new Paragraph();
        partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
        partnersBlock.setFont(BODY_TEXT_BOLD_FONT);
        partnersBlock.add(this.getText("summaries.project.partner.reporting.overall"));
        partnersBlock.setFont(BODY_TEXT_FONT);

        if (!partnersList.isEmpty()) {
          partnersBlock.add(this.messageReturn(partnersList.get(0).getOverall()));
        } else {
          partnersBlock.add(this.messageReturn(null));
        }
        partnersBlock.add(Chunk.NEWLINE);
        partnersBlock.add(Chunk.NEWLINE);
        document.add(partnersBlock);
      }
      // Leason regardins
      partnersBlock = new Paragraph();
      partnersBlock.setAlignment(Element.ALIGN_JUSTIFIED);
      partnersBlock.setFont(BODY_TEXT_BOLD_FONT);
      partnersBlock.add(this.getText("summaries.project.partner.planning.lessonRegarding"));
      partnersBlock.setFont(BODY_TEXT_FONT);
      if (project.getComponentLesson("partners") != null) {
        partnersBlock.add(this.messageReturn(project.getComponentLesson("partners").getLessons()));
      } else {
        partnersBlock.add(this.messageReturn(null));
      }
      document.add(partnersBlock);


    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

  }

  /**
   * Entering the project title in the summary
   */
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


  /**
   * @param paragraph paragraph for write
   * @param institution PPA to calculate your budget
   * @param startYear start year
   * @param endYear end year to calculate
   * @param table table to represent the budget
   * @param budgetType budget type
   * @return
   */
  private void addRowBudgetByPartners(Paragraph paragraph, Institution institution, int year, PdfPTable table,
    BudgetType budgetType) {

    Budget budget;

    budget = project.getBudget(institution.getId(), budgetType.getValue(), year);
    if (budget == null) {
      // Annual budget
      paragraph = new Paragraph(this.budgetFormatter.format(0.0), TABLE_BODY_FONT);
      this.addTableBodyCell(table, paragraph, Element.ALIGN_RIGHT, 1);

      // gender percentage (%)
      paragraph = new Paragraph(this.genderFormatter.format(0), TABLE_BODY_FONT);
      this.addTableBodyCell(table, paragraph, Element.ALIGN_RIGHT, 1);

      // gender percentage (USD)
      paragraph = new Paragraph(budgetFormatter.format(0.0), TABLE_BODY_FONT);
      budget = null;
    } else {

      // Annual budget
      paragraph = new Paragraph(budgetFormatter.format(budget.getAmount()), TABLE_BODY_FONT);
      this.addTableBodyCell(table, paragraph, Element.ALIGN_RIGHT, 1);

      // gender percentage
      paragraph = new Paragraph(genderFormatter.format(budget.getGenderPercentage() * 0.01), TABLE_BODY_FONT);
      this.addTableBodyCell(table, paragraph, Element.ALIGN_RIGHT, 1);


      paragraph = new Paragraph(budgetFormatter.format(budget.getAmount() * budget.getGenderPercentage() * 0.01),
        TABLE_BODY_FONT);
      budget = null;
    }
    this.addTableBodyCell(table, paragraph, Element.ALIGN_RIGHT, 1);
  }

  /**
   * Method used for to add the project summary
   */
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

  /**
   * This method is used for generating the PDF File.
   * 
   * @param project project prepared before with the information
   * @param currentPlanningYear current year of planning
   * @param midOutcomeYear year 2019
   */
  public void generatePdf(Project project, int currentPlanningYear, int currentReportingYear, int midOutcomeYear) {

    this.prepareFormatter();
    this.allMOGs = elementManager.getIPElementList();
    statuses = new HashMap<>();
    List<ProjectStatusEnum> list = Arrays.asList(ProjectStatusEnum.values());
    for (ProjectStatusEnum projectStatusEnum : list) {
      statuses.put(projectStatusEnum.getStatusId(), projectStatusEnum.getStatus());
    }


    this.mapPartnerPersons = projectPartnerManager.getAllProjectPartnersPersonsWithTheirInstitution();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    this.document = new Document(PageSize.A4, 57, 57, 60, 57);
    this.project = project;
    this.midOutcomeYear = midOutcomeYear;
    this.currentPlanningYear = currentPlanningYear;
    this.currentReportingYear = currentReportingYear;
    this.setSummaryTitle(project.getStandardIdentifier(Project.PDF_IDENTIFIER_REPORT));

    PdfWriter writer = this.initializePdf(document, outputStream, PORTRAIT);

    // Adding the event to include header and footer on each page

    HeaderFooterPDF event;


    if (project.isReporting()) {
      event = new HeaderFooterPDF(summaryTitle, PORTRAIT,
        project.isSubmitted(currentReportingYear, APConstants.REPORTING_SECTION), APConstants.REPORTING_SECTION,
        String.valueOf(currentReportingYear));
    } else {
      event = new HeaderFooterPDF(summaryTitle, PORTRAIT,
        project.isSubmitted(currentPlanningYear, APConstants.PLANNING_SECTION), APConstants.PLANNING_SECTION,
        String.valueOf(this.currentPlanningYear));
    }

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
    this.addProjectOutcomes("4");
    this.addProjectOutputs("5");
    this.addActivities();
    if (project.isReporting()) {
      this.addBudgetReporting("7");
    } else {
      this.addProjectBudgets();
    }


    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();
    inputStream = new ByteArrayInputStream(outputStream.toByteArray());
  }


  /**
   * Method used for to get the etiquette of BudgetType depending of project type
   * 
   * @return etiquette
   */
  private BudgetType getBudgetType() {
    if (project.isBilateralProject()) {
      return BudgetType.W3_BILATERAL;
    } else if (project.isCoreProject()) {
      return BudgetType.W1_W2;
    }
    return null;
  }

  /**
   * Method used for to get the size of document
   * 
   * @return size of document
   */
  public int getContentLength() {
    return contentLength;
  }

  private List<IPElement> getElementMOGsByType(IPElement ipeElement, IPElementType elementType) {

    List<IPElement> listIPElement = new ArrayList<>();
    for (IPElement IPElementIterator : this.allMOGs) {
      if (IPElementIterator.getType().getId() == elementType.getId()
        && ipeElement.getProgram().getId() == IPElementIterator.getProgram().getId()) {
        listIPElement.add(IPElementIterator);
      }
    }
    return listIPElement;
  }

  /**
   * Method used for to get the name of document
   * 
   * @return name of document
   */
  public String getFileName() {
    DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
    String fileName;

    fileName = summaryTitle.replace(' ', '_');
    fileName += "_" + dateFormat.format(new Date());
    fileName += ".pdf";

    return fileName;
  }

  public String getHighlightsImagesUrlPath() {
    return config.getProjectsBaseFolder() + "/" + project.getId() + "/" + "hightlightsImage" + "/";
  }


  /**
   * Method used for to get the inputStream of document
   * 
   * @return inputStream of document
   */
  public InputStream getInputStream() {
    return inputStream;
  }

  /**
   * Method used for to get outcomes by indicator
   * 
   * @return midOutcomes that is the list of outcomes found
   */
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
   * @param mog MOG to number
   * @return number mog
   */
  private int getMOGIndex(IPElement mog) {
    int index = 0;

    List<IPElement> IPElements = this.getElementMOGsByType(mog, mog.getType());
    for (int i = 0; i < IPElements.size(); i++) {
      if (IPElements.get(i).getId() == mog.getId()) {
        return i + 1;
      }
    }

    return index;
  }


  /**
   * Method used for to search if the MOG in an OutputBudget's ArrayList is related.
   * 
   * @param listOutputBudget list of OutpuBudget
   * @param mog MOG to search in the list
   * @return outputBudget founded, null when the output doesn't exists
   */
  private OutputBudget getOutputBudgetByMog(List<OutputBudget> listOutputBudget, IPElement mog) {

    for (OutputBudget outputBudget : listOutputBudget) {
      if (outputBudget.getOutput().getId() == mog.getId()) {
        return outputBudget;
      }
    }
    return null;
  }

  /**
   * This method is for search the project partner for the index in a list
   * 
   * @param ppaPartners list of project partners
   * @param pp project partner to search
   * @param index index of the list
   * @return true if project partner exist in the list otherwise false
   */
  private boolean isRepeatProjectPartner(List<ProjectPartner> ppaPartners, ProjectPartner pp, int index) {
    ProjectPartner ppaPartner;
    for (int a = index; a < ppaPartners.size(); a++) {
      ppaPartner = ppaPartners.get(a);
      if (ppaPartner != null && ppaPartner.getInstitution().getId() == pp.getInstitution().getId()) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method converts the string in return message of summary
   * 
   * @param enter String of entering
   * @returnnull default message when the string is null or empty, otherwise the string
   */
  private String messageReturn(String enter) {

    if (enter == null || enter.equals("")) {
      return this.getText("summaries.project.empty");
    } else {
      return enter;
    }

  }

  private void prepareFormatter() {
    // Locale locale = new Locale("en", "US");

    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    simbolo.setDecimalSeparator('.');
    simbolo.setGroupingSeparator(',');
    budgetFormatter = new DecimalFormat("###,###.##", simbolo);
    budgetFormatter.setMinimumFractionDigits(2);
    budgetFormatter.setDecimalSeparatorAlwaysShown(true);

    // gender formatter
    this.genderFormatter = (DecimalFormat) NumberFormat.getPercentInstance();
  }

  /**
   * This method is used for removed partner repeat in a list
   * 
   * @param ppaPartners list to depure
   * @return list of project partners refined
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

  /**
   * this method is used for set the title
   * 
   * @param title
   */
  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
