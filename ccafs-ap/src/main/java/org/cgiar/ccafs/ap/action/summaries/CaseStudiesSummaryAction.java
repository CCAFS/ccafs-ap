package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdf.CaseStudySummaryPdf;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.CaseStudyManager;
import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.manager.CountryManager;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Leader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CaseStudiesSummaryAction extends BaseAction {

  private static final long serialVersionUID = -5536266078525243680L;

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(CaseStudiesSummaryAction.class);

  // Manager
  private CaseStudyManager caseStudyManager;
  private LeaderManager leaderManager;
  private CountryManager countryManager;
  private CaseStudyTypeManager caseStudyTypeManager;

  // Model
  private CaseStudySummaryPdf caseStudyPdf;
  private List<Leader> leaders;
  private List<Country> countries;
  private CaseStudyType[] caseStudyTypeList;

  private String leadersSelected;
  private String countriesSelected;
  private String typesSelected;
  private int yearSelected;

  @Inject
  public CaseStudiesSummaryAction(APConfig config, LogframeManager logframeManager, CaseStudyManager caseStudyManager,
    LeaderManager leaderManager, CountryManager countryManager, CaseStudyTypeManager caseStudyTypeManager,
    CaseStudySummaryPdf caseStudyPdf) {
    super(config, logframeManager);
    this.caseStudyManager = caseStudyManager;
    this.leaderManager = leaderManager;
    this.countryManager = countryManager;
    this.caseStudyTypeManager = caseStudyTypeManager;
    this.caseStudyPdf = caseStudyPdf;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public CaseStudyType[] getCaseStudyTypeList() {
    return caseStudyTypeList;
  }

  public int getContentLength() {
    return caseStudyPdf.getContentLength();
  }

  public List<Country> getCountries() {
    return countries;
  }

  public String getCountriesSelected() {
    return countriesSelected;
  }

  public String getFileName() {
    return caseStudyPdf.getFileName();
  }

  public InputStream getInputStream() {
    return caseStudyPdf.getInputStream();
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

    leaders = new ArrayList<>();

    // Filling leader list
    Leader leader = new Leader(-1, getText("summaries.caseStudies.selectLeader"), "");
    leaders.add(leader);
    leaders.addAll(Arrays.asList(leaderManager.getAllLeaders()));

    // Filling country list
    countries = Arrays.asList(countryManager.getCountryList());

    caseStudyTypeList = caseStudyTypeManager.getCaseStudyTypes();
  }

  @Override
  public String save() {

    int leader = (leadersSelected == null) ? -1 : Integer.parseInt(leadersSelected);
    // Add quotes to array elements
    String countries = countriesSelected.replaceAll("(\\w+)", "\"$1\"");
    String types = typesSelected.replaceAll("(\\w+)", "\"$1\"");

    List<CaseStudy> caseStudies = caseStudyManager.getCaseStudyListForSummary(leader, yearSelected, countries, types);

    if (caseStudies.isEmpty()) {
      addActionError(getText("summaries.general.noResults"));
      return INPUT;
    }

    caseStudyPdf.setSummaryTitle(getText("summaries.caseStudies.pdf.title"));
    caseStudyPdf.generatePdf(caseStudies);
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
