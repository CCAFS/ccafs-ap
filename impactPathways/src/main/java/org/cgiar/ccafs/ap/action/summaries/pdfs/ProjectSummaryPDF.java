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
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Deliverable;
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
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

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
  private int contentLength;
  int currentPlanningYear;
  private Document document;


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
    DeliverableManager deliverableManager, NextUserManager nextUserManager) {
    this.initialize(config.getBaseUrl());
    this.budgetManager = budgetManager;
    this.elementManager = elementManager;
  }

  /**
   * 
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

      for (int year = startYear; year <= endYear; year++)
      {  		      
        // if it is CCAFS_CORE
        if(project.isCoreProject())
        {
          cell = new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W1_W2.name() , String.valueOf(year)}  ), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
          cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year))));

        }else 
          // if it is BILATERAL
          if (project.isBilateralProject()){
            cell = new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W3_BILATERAL.name() , String.valueOf(year)}  ), TABLE_BODY_BOLD_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL.getValue(), year))));

          }else 
            // if it is COFOUNDED
            if(project.isCoFundedProject()){

              cell = new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W1_W2.name() , String.valueOf(year)}  ), TABLE_BODY_BOLD_FONT);
              this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

              cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W1_W2.getValue(), year))));
              this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

              cell = new Paragraph(this.getText("summaries.project.budget.overall", new String[] {BudgetType.W3_BILATERAL.name() , String.valueOf(year)}  ), TABLE_BODY_BOLD_FONT);
              this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

              cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateProjectBudgetByTypeAndYear(project.getId(), BudgetType.W3_BILATERAL.getValue(), year))));
            }

        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

      } 

      document.add(table);
      document.add(Chunk.NEWLINE);

      //********************** Totals *************
      table = new PdfPTable(2);
      //table.setLockedWidth(true);
      table.setTotalWidth(400);
      table.setHeaderRows(1);
      table.setKeepTogether(true);

      cell = new Paragraph(this.getText("summaries.project.budget.overall.total"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);

      cell = new Paragraph(this.getText("summaries.project.budget.overall.value"), TABLE_HEADER_FONT);
      this.addTableHeaderCell(table, cell);
      currencyFormatter = NumberFormat.getCurrencyInstance(locale);

      // if it is CCAFS_CORE
      if(project.isCoreProject())
      {
        cell = new Paragraph(this.getText("summaries.project.budget.overall.cumulative", new String[] {BudgetType.W1_W2.name() }  ), TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1); 

        cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(project.getId(),BudgetType.W1_W2.getValue()))));
      }else 
        // if it is BILATERAL
        if (project.isBilateralProject()){
          cell = new Paragraph(this.getText("summaries.project.budget.overall.cumulative", new String[] {BudgetType.W3_BILATERAL.name() }  ), TABLE_BODY_BOLD_FONT);
          this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

          cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(project.getId(),BudgetType.W3_BILATERAL.getValue()))));

        }else 
          // if it is COFOUNDED
          if(project.isCoFundedProject()){

            cell = new Paragraph(this.getText("summaries.project.budget.overall.cumulative", new String[] {BudgetType.W1_W2.name() }  ), TABLE_BODY_BOLD_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(project.getId(),BudgetType.W1_W2.getValue()))));
            this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);  

            cell = new Paragraph(this.getText("summaries.project.budget.overall.cumulative", new String[] {BudgetType.W3_BILATERAL.name() }  ), TABLE_BODY_BOLD_FONT);
            this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);

            cell = new Paragraph(String.valueOf(currencyFormatter.format(this.budgetManager.calculateTotalProjectBudgetByType(project.getId(),BudgetType.W3_BILATERAL.getValue()))));
          }

      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1); 

      document.add(table);
      document.add(Chunk.NEWLINE);

    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }


  }

  private void addActivities() {


    Paragraph paragraph = new Paragraph("6. "+this.getText("summaries.project.activity"), HEADING2_FONT);
    Paragraph activityBlock = new Paragraph(); 
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.add(Chunk.NEWLINE);

    int counter=0;
    for (Activity activity : project.getActivities()) {

      counter++;

      // Activity title
      activityBlock.setAlignment(Element.ALIGN_CENTER);
      activityBlock.setFont(BODY_TEXT_BOLD_FONT);
      activityBlock.add("Activity # "+counter);
      activityBlock.setAlignment(Element.ALIGN_JUSTIFIED);
      activityBlock.add(Chunk.NEWLINE);
      activityBlock.add(this.getText("summaries.project.activities.title"));

      activityBlock.setFont(BODY_TEXT_FONT);
      activityBlock.add(activity.getTitle());
      activityBlock.add(Chunk.NEWLINE);

      // Activity description
      activityBlock.setFont(BODY_TEXT_BOLD_FONT);
      activityBlock.add(this.getText("summaries.project.activities.description"));

      activityBlock.setFont(BODY_TEXT_FONT);
      activityBlock.add(activity.getDescription());
      activityBlock.add(Chunk.NEWLINE);

      // Activity Start Date
      activityBlock.setFont(BODY_TEXT_BOLD_FONT);
      activityBlock.add(this.getText("summaries.project.startDate") + ": ");

      activityBlock.setFont(BODY_TEXT_FONT);
      activityBlock.add(activity.getStartDate().toString());
      activityBlock.add(Chunk.NEWLINE);

      // Activity End Date
      activityBlock.setFont(BODY_TEXT_BOLD_FONT);
      activityBlock.add(this.getText("summaries.project.endDate")+ ": ");

      activityBlock.setFont(BODY_TEXT_FONT);
      activityBlock.add(activity.getEndDate().toString());
      activityBlock.add(Chunk.NEWLINE);

      // Activity Leader
      activityBlock.setFont(BODY_TEXT_BOLD_FONT);
      activityBlock.add(this.getText("summaries.project.activities.activityLeader")+ ": ");

      activityBlock.setFont(BODY_TEXT_FONT);
      activityBlock.add(activity.getLeader().getComposedName());
      activityBlock.add(Chunk.NEWLINE);
      activityBlock.add(Chunk.NEWLINE);  

    }

    try
    {
      document.add(paragraph);
      document.add(activityBlock);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project activities to the project summary pdf of project {} ", e,
        project.getId());
    }

  }




  /**
   * 
   * @param cell
   * @param institution PPA to calculate your budget  
   * @param startYear 
   * @param endYear
   * @param table
   * @param budgetType
   * @return
   */
  private double addBudgetsByPartners(Paragraph cell , Institution institution , int startYear, int endYear ,
    PdfPTable table , BudgetType budgetType)
  { 
    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);

    double budgetSum ,amount;
    // Anual budget           
    cell = new Paragraph("Annual Budget " + budgetType.name().toString(), TABLE_BODY_BOLD_FONT);
    this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
    budgetSum =0.0;
    for (int year = startYear; year <= endYear; year++) {
      Budget b = project.getBudget(institution.getId(), budgetType.getValue() , year);
      amount = b == null ? 0 : b.getAmount();
      budgetSum = budgetSum + amount;
      cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1); 
    }

    // Gender percentaje          
    currencyFormatter = NumberFormat.getPercentInstance(locale);
    cell = new Paragraph("Gender percentage "+budgetType.name().toString(), TABLE_BODY_BOLD_FONT);
    this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1);
    for (int year = startYear; year <= endYear; year++) {

      Budget b = project.getBudget(institution.getId(), budgetType.getValue() , year);
      amount = b == null ? 0 : b.getGenderPercentage();
      cell = new Paragraph(currencyFormatter.format(amount), TABLE_BODY_FONT);
      this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1); 
    }

    return budgetSum;      
  }


  private void addDelivable(Paragraph partnersBlock, Deliverable deliverable , int counter) {

    if (deliverable != null) {
      Paragraph paragraph = new Paragraph();
      paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
      paragraph.setKeepTogether(true);
      StringBuilder stringBuilder = new StringBuilder();

      // **** Deliverable Information *********
      paragraph = new Paragraph(this.getText("summaries.project.deliverable.information")+" # " +counter, HEADING4_FONT);
      paragraph.add(Chunk.NEWLINE);;

      //Title   
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.information.title")+" : " );

      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(deliverable.getTitle());
      paragraph.add(stringBuilder.toString());
      paragraph.add(Chunk.NEWLINE);;

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
      paragraph.add(Chunk.NEWLINE);; 

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
      paragraph.add(Chunk.NEWLINE);

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
      paragraph.add(Chunk.NEWLINE);

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
      paragraph.add(Chunk.NEWLINE);;

      // ********** Next Users**************************************
      //Next user   
      for(NextUser nextUser : deliverable.getNextUsers()){
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add(this.getText("summaries.project.deliverable.next.user")+" : " );      
        paragraph.setFont(BODY_TEXT_FONT);
        stringBuilder.append(nextUser.getUser());
        paragraph.add(stringBuilder.toString());
        paragraph.add(Chunk.NEWLINE);;

        //Expected Changes
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add(this.getText("summaries.project.deliverable.next.user.strategies")+" : " );      
        paragraph.setFont(BODY_TEXT_FONT);
        stringBuilder.append(nextUser.getExpectedChanges());
        paragraph.add(stringBuilder.toString());
        paragraph.add(Chunk.NEWLINE);;

        //Strategies  
        paragraph.setFont(BODY_TEXT_BOLD_FONT);
        paragraph.add(this.getText("summaries.project.deliverable.next.user.expected.change")+" : " );      
        paragraph.setFont(BODY_TEXT_FONT);
        stringBuilder.append(nextUser.getStrategies());
        paragraph.add(stringBuilder.toString());
        paragraph.add(Chunk.NEWLINE);; 


      }

      // ********** Deliverable partnership**************************************


      //Organization
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.partnership.organization")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(deliverable.getResponsiblePartner().getInstitution().getName());
      paragraph.add(stringBuilder.toString());
      paragraph.add(Chunk.NEWLINE);;

      //Contact Email  
      stringBuilder = new StringBuilder();
      paragraph.setFont(BODY_TEXT_BOLD_FONT);
      paragraph.add(this.getText("summaries.project.deliverable.partnership.email")+" : " );      
      paragraph.setFont(BODY_TEXT_FONT);
      stringBuilder.append(deliverable.getResponsiblePartner().getUser().getEmail());
      paragraph.add(stringBuilder.toString());
      paragraph.add(Chunk.NEWLINE);;


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
      document.add(Chunk.NEWLINE);;
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
      paragraph.add(Chunk.NEWLINE);;
      paragraph.add(Chunk.NEWLINE);;

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
        paragraph.add(Chunk.NEWLINE);;
        paragraph.add(Chunk.NEWLINE);;
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

  private void addProjectBudgets() {

    int startYear, endYear;

    Paragraph cell = new Paragraph();

    Calendar startDate = Calendar.getInstance();
    startDate.setTime(project.getStartDate());
    startYear = startDate.get(Calendar.YEAR);

    Calendar endDate = Calendar.getInstance();
    endDate.setTime(project.getEndDate());
    endYear = endDate.get(Calendar.YEAR);

    PdfPTable table = new PdfPTable(endYear - startYear + 2);

    cell.setFont(HEADING3_FONT);
    cell.add("3. " + this.getText("summaries.project.budget"));

    // ************************Budget By Partners*************************************
    cell.setFont(HEADING4_FONT);
    cell.add(Chunk.NEWLINE);
    cell.add("3.1 " + this.getText("summaries.project.budget.partners"));


    try {
      document.add(Chunk.NEWLINE);
      document.add(cell);

      // Summary table
      this.add_Budgets_Summary_By_Partners(startYear, endYear);

      List<Institution> institutions = new ArrayList<>();
      institutions.add(project.getLeader().getInstitution());

      List<ProjectPartner> listProjectPartner = this.removePartnersRepeat(project.getPPAPartners());

      for (ProjectPartner partner : listProjectPartner) {
        institutions.add(partner.getInstitution());
      }


      double budgetSum=0.0;
      double budgetSum_1=0.0;
      for (int i = 0; i < institutions.size(); i++) {

        Institution institution = institutions.get(i);
        table.setLockedWidth(true);
        table.setTotalWidth(80 * (endYear - startYear + 2));
        table.setWidths(this.getBudgetTableColumnWidths(startYear, endYear));
        table.setHeaderRows(1);
        table.setKeepTogether(true);

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

        budgetSum=0.0;
        budgetSum_1=0.0;
        //*********************** When project is CCAFS_CORE ***********************************
        if(project.isCoreProject()) {
          budgetSum = this.addBudgetsByPartners( cell ,  institution ,  startYear,  endYear ,   table ,  BudgetType.W1_W2);    	
        }	

        //*********************** When project is BILATERAL ***********************************      
        else if(project.isBilateralProject())   {
          budgetSum = this.addBudgetsByPartners( cell ,  institution ,  startYear,  endYear ,   table ,  BudgetType.W3_BILATERAL);    	
        }
        //*********************** When project is COFOUNDED ***********************************      
        else if(project.isCoFundedProject())   {
          budgetSum = this.addBudgetsByPartners( cell ,  institution ,  startYear,  endYear ,   table ,  BudgetType.W1_W2);    
          budgetSum = this.addBudgetsByPartners( cell ,  institution ,  startYear,  endYear ,   table ,  BudgetType.W3_BILATERAL);    	
        }


        if(budgetSum != 0.0) {
          document.add(Chunk.NEWLINE);;
          document.add(table);

        }
        // if the project is confounded.
        if(budgetSum_1 != 0.0) {
          document.add(Chunk.NEWLINE);;
          document.add(table);
        }

      }
    } catch (DocumentException e) {
      LOG.error("-- generatePdf() > There was an error adding the table with content for case study summary. ", e);
    }


    // ************************Budget By Mogs*************************************
    cell.setFont(HEADING4_FONT);
    cell.add(Chunk.NEWLINE);
    cell.add("3.2 " + this.getText("summaries.project.budget.mog"));

    Locale locale = new Locale("en", "US");
    NumberFormat currencyFormatter;

    this.addTableHeaderCell(table, new Paragraph());

    try{
      cell.add(Chunk.NEWLINE);
      cell.add(Chunk.NEWLINE);
      document.add(cell);

      for (int year = startYear; year <= endYear; year++) {
        table = new PdfPTable(2);

        // Title Year 
        cell = new Paragraph(String.valueOf(year), BODY_TEXT_BOLD_FONT);
        this.addCustomTableCell(table, cell, Element.ALIGN_CENTER, BODY_TEXT_BOLD_FONT, Color.WHITE,
          table.getNumberOfColumns(), 0, false);

        //First row
        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        cell = new Paragraph(this.getText("summaries.project.budget.mog.anual" , new String[] {String.valueOf(year)}) , TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1); 

        cell = new Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalCCAFSBudgetByYear(project.getId(), year))) , BODY_TEXT_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);  

        //Second row
        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        cell = new Paragraph(this.getText("summaries.project.budget.mog.gender" , new String[] {String.valueOf(year)}) , TABLE_BODY_BOLD_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_LEFT, 1); 

        cell = new Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalGenderBudgetByYear(project.getId(), year))) , BODY_TEXT_FONT);
        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);

        document.add(table);
        document.add(Chunk.NEWLINE);


        //    for(OutputBudget project.getOutputsBudgets())

        // Title MOG
        table = new PdfPTable(2);

        // Title Mog 
        //        cell = new Paragraph(String.valueOf(year), BODY_TEXT_BOLD_FONT);
        //        this.addCustomTableCell(table, cell, Element.ALIGN_LEFT, BODY_TEXT_FONT, Color.WHITE,
        //          table.getNumberOfColumns(), 0, false);
        //
        //        //Third row
        //        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        //        cell = new Paragraph(this.getText("summaries.project.budget.mog.anual" , new String[] {String.valueOf(year)}) , BODY_TEXT_BOLD_FONT);
        //        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1); 
        //
        //        cell = new Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalCCAFSBudgetByYear(project.getId(), year))) , BODY_TEXT_FONT);
        //        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);  
        //
        //        //Fourth row
        //        currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        //        cell = new Paragraph(this.getText("summaries.project.budget.mog.gender" , new String[] {String.valueOf(year)}) , BODY_TEXT_BOLD_FONT);
        //        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1); 
        //
        //        cell = new Paragraph(String.valueOf(currencyFormatter.format(budgetManager.calculateTotalGenderBudgetByYear(project.getId(), year))) , BODY_TEXT_FONT);
        //        this.addTableBodyCell(table, cell, Element.ALIGN_CENTER, 1);
        //

        document.add(table);
        cell.add(Chunk.NEWLINE);



      }

    }catch (DocumentException e) {
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
      document.add(Chunk.NEWLINE);;
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

    Paragraph title = new Paragraph("5.2 " + this.getText("summaries.project.indicatorsContribution"), HEADING3_FONT);
    indicatorsBlock.add(title);
    indicatorsBlock.add(Chunk.NEWLINE);;

    indicatorsBlock.setFont(BODY_TEXT_BOLD_FONT);
    indicatorsBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.contributiton.impact"));
    indicatorsBlock.setFont(BODY_TEXT_FONT);
    indicatorsBlock.add(Chunk.NEWLINE);;

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
        outcomeBlock.add(Chunk.NEWLINE);;
        outcomeBlock.add(Chunk.NEWLINE);;

        outcomeBlock.setFont(BODY_TEXT_BOLD_FONT);
        outcomeBlock.add(this.getText("summaries.project.indicators"));
        document.add(outcomeBlock);
        document.add(Chunk.NEWLINE);;

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
          document.add(Chunk.NEWLINE);;
        }

      }
    } catch (DocumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  private void addProjectLocations() {

    Paragraph title = new Paragraph("3. " + this.getText("summaries.project.projectLocations"), HEADING3_FONT);
    title.add(Chunk.NEWLINE);;

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
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project locations to the project summary pdf", e);
    }

  }
  private void addProjectOutcomes() {



    String outcomeProgress;
    Paragraph outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);
    Paragraph title = new Paragraph("5. " + this.getText("summaries.project.outcome"), HEADING2_FONT);
    outcomesBlock.add(title);
    outcomesBlock.add(Chunk.NEWLINE);;
    title = new Paragraph(); 
    title.setFont(HEADING3_FONT);
    title.add("5.1 " + this.getText("summaries.project.outcomeNarrative"));
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
      outcomesBlock.add(Chunk.NEWLINE);;

      if (project.getOutcomes().get(String.valueOf(year)) == null) {
        outcomeProgress = this.getText("summaries.project.empty");
      } else {
        outcomeProgress = project.getOutcomes().get(String.valueOf(year)).getStatement();
      }

      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(outcomeProgress);
      outcomesBlock.add(Chunk.NEWLINE);;
      try {
        document.add(outcomesBlock);
        document.add(Chunk.NEWLINE);;
      } catch (DocumentException e) {
        LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
      }
    }

    //******************* Gender contribution ***************/

    outcomesBlock = new Paragraph();
    outcomesBlock.setFont(HEADING3_FONT);
    outcomesBlock.add(this.getText("summaries.project.outcome.gender"));
    outcomesBlock.add(Chunk.NEWLINE);;


    for(int year = currentPlanningYear; year < midOutcomeYear; year++)
    {
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.gender.contributiton", new String[] {String.valueOf(year)}));
      new Chunk();
      outcomesBlock.add(Chunk.NEWLINE);    	



      if (project.getOutcomes().get(String.valueOf(year)) == null) {
        outcomeProgress = this.getText("summaries.project.empty");
      } else {
        outcomeProgress = project.getOutcomes().get(String.valueOf(year)).getGenderDimension();
      }
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(outcomeProgress);
      new Chunk();
      outcomesBlock.add(Chunk.NEWLINE);

    }	
    // Add paragraphs to document
    try {
      document.add(outcomesBlock);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

    //******************* CCAFS Outcomes***************/
    this.addProjectIndicators();

    //******************* Other contributions***************/
    OtherContribution otherContribution = project.getIpOtherContribution();


    outcomesBlock = new Paragraph();
    outcomesBlock.setAlignment(Element.ALIGN_JUSTIFIED);

    title = new Paragraph(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions"), HEADING3_FONT);
    outcomesBlock.add(title);
    outcomesBlock.add(Chunk.NEWLINE);

    if(otherContribution != null)
    {
      //Contribution to other Impact Pathways:
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.pathways"));
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(Chunk.NEWLINE);
      if (otherContribution.getContribution() == null) {
        outcomesBlock.add(this.getText("summaries.project.empty"));
      } else {
        outcomesBlock.add(otherContribution.getContribution());
      }

      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.add(Chunk.NEWLINE);

      //Contribution to that has not been covered:
      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.covered"));
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(Chunk.NEWLINE);
      if (otherContribution.getAdditionalContribution() == null) {
        outcomesBlock.add(this.getText("summaries.project.empty"));
      } else {
        outcomesBlock.add(otherContribution.getAdditionalContribution());
      }
      outcomesBlock.add(Chunk.NEWLINE);
      outcomesBlock.add(Chunk.NEWLINE);
      //CNature of the collaboration:

      outcomesBlock.setFont(BODY_TEXT_BOLD_FONT);
      outcomesBlock.add(this.getText("summaries.project.outcome.ccafs.outcomes.other.contributions.nature"));
      outcomesBlock.setFont(BODY_TEXT_FONT);
      outcomesBlock.add(Chunk.NEWLINE);
      if (otherContribution.getCrpCollaborationNature() == null) {
        outcomesBlock.add(this.getText("summaries.project.empty"));
      } else {
        outcomesBlock.add(otherContribution.getCrpCollaborationNature());
      }
      outcomesBlock.add(Chunk.NEWLINE);
    }
    // Add paragraphs to document
    try {
      document.add(outcomesBlock);
      document.add(Chunk.NEWLINE);
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
    }

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
          document.add(new Paragraph(this.getText("summaries.project.overviewbymogs.text") + years[counter],
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
    counter=1;
    List<Paragraph> listParagraphDeliverables = new ArrayList<Paragraph>();
    for (Deliverable deliverable : project.getDeliverables()) {
      paragraph = new Paragraph();
      this.addDelivable(paragraph, deliverable, counter);
      listParagraphDeliverables.add(paragraph);
      counter++;
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
    partnersBlock.add(Chunk.NEWLINE);;


    int c = 1;
    // Get project leader PL
    title = new Paragraph(28, "2.1 " + this.getText("summaries.project.projectPartners.section.one"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;
    this.auxiliarGetPartners(project.getLeader(), c, partnersBlock);
    c++;

    // Get project coordinator PC
    title = new Paragraph(28, "2.2 " + this.getText("summaries.project.projectPartners.section.two"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;
    if (!(project.getCoordinator() == null)) {
      this.auxiliarGetPartners(project.getCoordinator(), c, partnersBlock);
      c++;
    }

    // Get CCAFS partners PPA
    title = new Paragraph(28, "2.3 " + this.getText("summaries.project.projectPartners.section.three"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;
    for (ProjectPartner partner : project.getPPAPartners()) {
      this.auxiliarGetPartners(partner, c, partnersBlock);
      c++;
    }

    // Get Project Partners PP
    title = new Paragraph(28, "2.4 " + this.getText("summaries.project.projectPartners.section.four"), HEADING4_FONT);
    partnersBlock.add(title);
    partnersBlock.add(Chunk.NEWLINE);;

    for (ProjectPartner partner : project.getProjectPartners()) {
      this.auxiliarGetPartners(partner, c, partnersBlock);
      c++;
    }

    try {
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

    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);
    paragraph.setFont(HEADING3_FONT);

    paragraph.add(this.getProjectID());
    paragraph.add(Chunk.NEWLINE);;

    paragraph.setFont(BODY_TEXT_FONT);
    paragraph.add(project.getTitle());
    paragraph.add(line);



    try {
      document.add(paragraph);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
    }
  }

  private void addSummary() {
    Paragraph paragraph = new Paragraph();
    paragraph.setAlignment(Element.ALIGN_JUSTIFIED);

    Phrase title = new Phrase("1. " + this.getText("summaries.project.summary"), HEADING3_FONT);
    paragraph.add(title);

    paragraph.add(Chunk.NEWLINE);;
    paragraph.add(Chunk.NEWLINE);;

    Phrase body = new Phrase(project.getSummary(), BODY_TEXT_FONT);
    paragraph.add(body);

    try {
      document.add(paragraph);
      document.add(Chunk.NEWLINE);;
      document.add(Chunk.NEWLINE);;
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

      paragraph.add(Chunk.NEWLINE);;

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
      paragraph.add(Chunk.NEWLINE);;
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

    // Summary content
    this.addProjectTitle();
    this.addMainInformationTable();
    this.addProjectContributions();
    this.addSummary();
    this.addProjectPartners();
    this.addProjectLocations();
    this.addProjectOutputs();
    this.addProjectOutcomes();
    this.addActivities();
    this.addProjectBudgets();
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

  /**
   * 
   * @param ppaPartners
   * @param pp
   * @return
   */
  private boolean isRepeatProjectPartner( List<ProjectPartner> ppaPartners, ProjectPartner pp , int index )
  {
    for(int a=index; a < ppaPartners.size() ; a++){
      if(ppaPartners.get(a).getInstitution().getId() == pp.getInstitution().getId())
      {
        return true;
      }	 
    }
    return false;
  }

  /**
   * 
   * @param ppaPartners
   * @return
   */
  private List<ProjectPartner> removePartnersRepeat(
    List<ProjectPartner> ppaPartners) {

    List<ProjectPartner> ppaPartners_aux = new ArrayList<ProjectPartner>();
    int size = ppaPartners.size() - 1;
    for(int a=0; a < size; a++)
    {
      if(!this.isRepeatProjectPartner(ppaPartners , ppaPartners.get(a) , a + 1))
      {
        ppaPartners_aux.add(ppaPartners.get(a));
      }
    }

    //ppaPartners_aux.add(ppaPartners.get(size));
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