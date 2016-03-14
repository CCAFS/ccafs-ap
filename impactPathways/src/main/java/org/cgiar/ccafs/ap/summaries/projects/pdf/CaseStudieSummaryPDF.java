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
import org.cgiar.ccafs.ap.data.manager.CaseStudiesManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.PartnerPersonManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.CaseStudieIndicators;
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.utils.APConfig;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hernán David Carvajal
 * @author Jorge Leonardo Solis Banguera
 */

public class CaseStudieSummaryPDF extends BasePDF {


  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CaseStudieSummaryPDF.class);

  // Managers

  private InputStream inputStream;

  // Model
  private APConfig config;
  private Document document;
  private int contentLength;
  private int midOutcomeYear;
  private Project project;
  private ProjectPartnerManager partnerManager;
  private PartnerPersonManager partnerPersonManager;
  private String summaryTitle;
  private IPProgramManager ipProgramManager;
  private Map<String, String> statuses;
  private CaseStudiesManager caseStudiesManager;
  private ProjectManager projectManager;
  private IPIndicatorManager indicatorManager;


  @Inject
  public CaseStudieSummaryPDF(APConfig config, IPIndicatorManager indicatorManager,
    CaseStudiesManager caseStudiesManager, ProjectManager projectManager, ProjectPartnerManager partnerManager,
    PartnerPersonManager partnerPersonManager, IPProgramManager ipProgramManager) {
    this.config = config;

    this.partnerManager = partnerManager;
    this.partnerPersonManager = partnerPersonManager;
    this.caseStudiesManager = caseStudiesManager;
    this.indicatorManager = indicatorManager;
    this.projectManager = projectManager;
    this.initialize(config.getBaseUrl());
    this.ipProgramManager = ipProgramManager;
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
   * TODO METHOD
   */
  /**
   * This method is used for add Outcomes in the project summary
   */
  private void addProjectOutcomes(String number) {
    try {
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
        // document.add(outcomesBlock);
      } catch (Exception e) {
        LOG.error("There was an error trying to add the project focuses to the project summary pdf", e);
      }

      Anchor anchor;

      ////////////////// Reporting
      PdfPTable table;

      // **********************************************************************************
      // *************************** Outcome Case Studies *************************************
      // **********************************************************************************

      if (project.isReporting()) {
        document.newPage();
        Paragraph cell;

        if (project.getCaseStudies().isEmpty()) {
          document.add(new Paragraph(this.getText("summaries.project.reporting.outcome.not.case.studies")));
        } else {
          title = new Paragraph();
          title.add(Chunk.NEWLINE);
          // document.add(title);
        }
        List<Project> lst = projectManager.getAllProjectsBasicInfo(APConstants.REPORTING_SECTION);
        Collections.sort(lst, new Comparator<Project>() {

          @Override
          public int compare(Project s1, Project s2) {
            Integer p1 = s1.getId();
            Integer p2 = s2.getId();
            return p1.compareTo(p2);
          }
        });
        for (Project project : lst) {
          project = projectManager.getProject(project.getId());
          if (project.isWorkplanRequired()) {
            String workPlanURL = this.getWorkplanURL();
            if (workPlanURL != null || project.getWorkplanName() != null) {
              project.setWorkplanURL(workPlanURL + project.getWorkplanName());
            }
          }
          // Get a route for the bilateral contract
          if (project.isBilateralProject()) {
            String bilateralContractURL = this.getBilateralContractURL();
            if (bilateralContractURL != null || project.getBilateralContractProposalName() != null) {
              project.setWorkplanURL(bilateralContractURL + project.getBilateralContractProposalName());
            }
          }


          // Getting the information of the Regions program
          project.setRegions(ipProgramManager.getProjectFocuses(project.getId(), APConstants.REGION_PROGRAM_TYPE));

          // Getting the information of the Flagships program
          project.setFlagships(ipProgramManager.getProjectFocuses(project.getId(), APConstants.FLAGSHIP_PROGRAM_TYPE));


          List<ProjectPartner> projectPartnerList = this.partnerManager.getProjectPartners(project);

          for (ProjectPartner projectPartner : projectPartnerList) {
            projectPartner.setPartnerPersons(this.partnerPersonManager.getPartnerPersons(projectPartner));
            projectPartner.setPartnerContributors(partnerManager.getProjectPartnerContributors(projectPartner));
          }
          project.setProjectPartners(projectPartnerList);
          this.project = project;


          List<CasesStudies> caseStudiesList = caseStudiesManager.getCaseStudysByProject(project.getId());

          List<IPIndicator> ipIndicatorList;
          IPIndicator ipIndicator;
          if (caseStudiesList != null) {
            if (!caseStudiesList.isEmpty()) {
              try {
                this.addProjectTitle();
                this.addMainInformationTable();
              } catch (Exception e) {
                System.out.println(project.getId());
                e.printStackTrace();
              }
            }

            for (CasesStudies caseStudie : caseStudiesList) {
              ipIndicatorList = new ArrayList<IPIndicator>();
              for (CaseStudieIndicators indicatorCaseStudie : caseStudie.getCaseStudieIndicatorses()) {
                ipIndicator = indicatorManager.getIndicatorFlgship(indicatorCaseStudie.getIdIndicator());
                ipIndicatorList.add(ipIndicator);
              }
              caseStudie.setCaseStudyIndicators(ipIndicatorList);
            }
            project.setCaseStudies(caseStudiesList);

            int counter = 0;
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
              cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.title"),
                TABLE_BODY_BOLD_FONT);
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
              for (IPIndicator _ipIndicator : caseStudy.getCaseStudyIndicators()) {
                if (_ipIndicator.getOutcome() != null) {
                  cell.add(_ipIndicator.getOutcome().getDescription());
                }
                cell.add(" " + _ipIndicator.getDescription() + "\n");
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
              cell = new Paragraph(this.getText("summaries.project.reporting.outcome.casestudy.upload"),
                TABLE_BODY_BOLD_FONT);
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


          document.newPage();
        }
      }


    } catch (DocumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
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
      paragraph.add("Project: ");
      paragraph.setFont(BODY_TEXT_FONT);
      paragraph.add(this.messageReturn("P" + project.getId() + " - "));
      paragraph.add(this.messageReturn(project.getTitle()));
      paragraph.add(line);
      document.add(paragraph);
      document.add(Chunk.NEWLINE);;
    } catch (DocumentException e) {
      LOG.error("There was an error trying to add the project title to the project summary pdf", e);
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


    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    this.document = new Document(PageSize.A4, 57, 57, 60, 57);
    this.project = project;
    this.midOutcomeYear = midOutcomeYear;

    this.setSummaryTitle(project.getStandardIdentifier(Project.PDF_IDENTIFIER_REPORT));
    PdfWriter writer = this.initializePdf(document, outputStream, PORTRAIT);


    // Open document
    document.open();

    // Summary content

    this.addProjectOutcomes("4");


    // Close document
    document.close();

    // Setting result file attributes
    contentLength = outputStream.size();

    inputStream = new ByteArrayInputStream(outputStream.toByteArray());
    /*
     * InputStream inputStream_loca = new ByteArrayInputStream(outputStream.toByteArray());
     * try {
     * FileUtils.copyInputStreamToFile(inputStream_loca, new File("C:test.pdf"));
     * } catch (IOException e) {
     * // TODO Auto-generated catch block
     * e.printStackTrace();
     * }
     */
  }

  private String getBilateralContractRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getBilateralProjectContractProposalFolder() + File.separator;
  }

  public String getBilateralContractURL() {
    return config.getDownloadURL() + "/" + this.getBilateralContractRelativePath().replace('\\', '/');
  }

  /**
   * Method used for to get the size of document
   * 
   * @return size of document
   */
  public int getContentLength() {
    return contentLength;
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

  private String getWorkplanRelativePath() {
    return config.getProjectsBaseFolder() + File.separator + project.getId() + File.separator
      + config.getProjectWorkplanFolder() + File.separator;
  }


  public String getWorkplanURL() {
    return config.getDownloadURL() + "/" + this.getWorkplanRelativePath().replace('\\', '/');
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


  /**
   * this method is used for set the title
   * 
   * @param title
   */
  public void setSummaryTitle(String title) {
    this.summaryTitle = title;
  }
}
