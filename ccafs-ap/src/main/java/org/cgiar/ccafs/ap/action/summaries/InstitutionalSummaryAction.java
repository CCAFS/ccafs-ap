package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdf.InstitutionalSummaryPdf;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.manager.CommunicationManager;
import org.cgiar.ccafs.ap.data.manager.LeverageManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeIndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.manager.PublicationManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;
import org.cgiar.ccafs.ap.data.model.Communication;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Outcome;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;
import org.cgiar.ccafs.ap.data.model.OutputSummary;
import org.cgiar.ccafs.ap.data.model.Publication;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class InstitutionalSummaryAction extends BaseAction {

  private static final long serialVersionUID = 4584243860407572942L;

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(InstitutionalSummaryAction.class);

  // Manager
  private ActivityManager activityManager;
  private OutputSummaryManager outputSummaryManager;
  private PublicationManager publicationManager;
  private CommunicationManager communicationManager;
  private CaseStudyManager caseStudyManager;
  private OutcomeManager outcomeManager;
  private OutcomeIndicatorReportManager outcomeIndicatorReportManager;
  private LeverageManager leverageManager;

  // Model
  private InstitutionalSummaryPdf institutionalPdf;
  private List<Leader> leaders;
  private List<Country> countries;

  private CaseStudyType[] caseStudyTypeList;

  private String leadersSelected;
  private String countriesSelected;
  private String typesSelected;
  private int yearSelected;

  @Inject
  public InstitutionalSummaryAction(APConfig config, LogframeManager logframeManager, ActivityManager activityManager,
    OutputSummaryManager outputSummaryManager, PublicationManager publicationManager,
    CommunicationManager communicationManager, CaseStudyManager caseStudyManager, OutcomeManager outcomeManager,
    OutcomeIndicatorReportManager outcomeIndicatorReportManager, LeverageManager leverageManager,
    InstitutionalSummaryPdf institutionalPdf) {
    super(config, logframeManager);
    this.activityManager = activityManager;
    this.outputSummaryManager = outputSummaryManager;
    this.publicationManager = publicationManager;
    this.communicationManager = communicationManager;
    this.caseStudyManager = caseStudyManager;
    this.outcomeManager = outcomeManager;
    this.outcomeIndicatorReportManager = outcomeIndicatorReportManager;
    this.leverageManager = leverageManager;
    this.institutionalPdf = institutionalPdf;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public CaseStudyType[] getCaseStudyTypeList() {
    return caseStudyTypeList;
  }

  public int getContentLength() {
    return institutionalPdf.getContentLength();
  }

  public List<Country> getCountries() {
    return countries;
  }

  public String getCountriesSelected() {
    return countriesSelected;
  }

  public String getFileName() {
    return institutionalPdf.getFileName();
  }

  public InputStream getInputStream() {
    return institutionalPdf.getInputStream();
  }

  public List<Leader> getLeaders() {
    return leaders;
  }

  public String getLeadersSelected() {
    return leadersSelected;
  }

  public String getTypesSelected() {
    return typesSelected;
  }

  public Map<Integer, String> getYearList() {
    Map<Integer, String> years = new TreeMap<>();
    years.put(-1, getText("summaries.caseStudies.selectYear"));
    for (int c = config.getStartYear(); c <= config.getReportingCurrentYear(); c++) {
      years.put(c, String.valueOf(c));
    }
    return years;
  }

  public int getYearSelected() {
    return yearSelected;
  }

  @Override
  public void prepare() throws Exception {

    Activity[] activities = activityManager.getActivities(getCurrentReportingLogframe().getYear(), getCurrentUser());
    OutputSummary[] outputSummaries =
      outputSummaryManager.getOutputSummaries(getCurrentUser().getLeader(), getCurrentReportingLogframe());
    List<Publication> publications =
      publicationManager.getPublications(getCurrentUser().getLeader(), getCurrentReportingLogframe());
    Communication communications =
      communicationManager.getCommunicationReport(getCurrentUser().getLeader(), getCurrentReportingLogframe());
    List<CaseStudy> caseStudies =
      caseStudyManager.getCaseStudyList(getCurrentUser().getLeader(), getCurrentReportingLogframe());
    List<Outcome> outcomes = outcomeManager.getOutcomes(getCurrentUser().getLeader(), getCurrentReportingLogframe());
    List<OutcomeIndicatorReport> outcomeIndicatorReport =
      outcomeIndicatorReportManager.getOutcomeIndicatorReports(getCurrentReportingLogframe(), getCurrentUser()
        .getLeader());
    List<Leverage> leverages =
      leverageManager.getLeverages(getCurrentUser().getLeader(), getCurrentReportingLogframe());

    institutionalPdf.setSummaryTitle(getCurrentUser().getLeader().getAcronym() + " "
      + getCurrentReportingLogframe().getYear() + " Activities");

    institutionalPdf.generatePdf(activities, outputSummaries, publications, communications, caseStudies, outcomes,
      outcomeIndicatorReport, leverages);
  }

  @Override
  public String save() {
    return SUCCESS;
  }

  public void setCountriesSelected(String countriesSelected) {
    this.countriesSelected = countriesSelected;
  }

  public void setLeadersSelected(String leadersSelected) {
    this.leadersSelected = leadersSelected;
  }

  public void setTypesSelected(String typesSelected) {
    this.typesSelected = typesSelected;
  }

  public void setYearSelected(int yearSelected) {
    this.yearSelected = yearSelected;
  }

  @Override
  public void validate() {
    // TODO Auto-generated method stub
    super.validate();
  }
}
