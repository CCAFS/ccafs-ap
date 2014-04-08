package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdf.MilestonesSummaryPdf;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.manager.MilestoneReportManager;
import org.cgiar.ccafs.ap.data.manager.ThemeManager;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.apache.shiro.mgt.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MilestonesSummaryAction extends BaseAction {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MilestonesSummaryAction.class);

  // Manager
  private MilestoneManager milestoneManager;
  private MilestoneReportManager milestoneReportManager;
  private ThemeManager themeManager;

  // Model
  private MilestonesSummaryPdf milestonesPdf;

  private List<Logframe> logframes;
  private List<Theme> themes;
  private List<Milestone> milestones;
  private Map<Integer, String> summaryTypes;
  private int summaryTypeSelected;
  private int themeSelected;
  private String logframeSelected;
  private String milestoneSelected;

  @Inject
  public MilestonesSummaryAction(APConfig config, LogframeManager logframeManager, SecurityManager securityManager,
    ThemeManager themeManager, MilestoneManager milestoneManager, MilestoneReportManager milestoneReportManager,
    MilestonesSummaryPdf milestonesSummaryPdf) {
    super(config, logframeManager, securityManager);
    this.themeManager = themeManager;
    this.milestoneManager = milestoneManager;
    this.milestoneReportManager = milestoneReportManager;
    this.milestonesPdf = milestonesSummaryPdf;
  }

  public int getContentLength() {
    return milestonesPdf.getContentLength();
  }

  public String getFileName() {
    return milestonesPdf.getFileName();
  }

  public InputStream getInputStream() {
    return milestonesPdf.getInputStream();
  }

  public List<Logframe> getLogframes() {
    return logframes;
  }

  public String getLogframeSelected() {
    return logframeSelected;
  }

  public List<Milestone> getMilestones() {
    return milestones;
  }

  public String getMilestoneSelected() {
    return milestoneSelected;
  }

  public Map<Integer, String> getSummaryTypes() {
    return summaryTypes;
  }

  public int getSummaryTypeSelected() {
    return summaryTypeSelected;
  }

  public List<Theme> getThemes() {
    return themes;
  }

  public int getThemeSelected() {
    return themeSelected;
  }

  @Override
  public void prepare() throws Exception {

    // Setting the list of logframes
    logframes = new ArrayList<>();
    Logframe tempLogframe = new Logframe(-1, -1, getText("summaries.milestones.selectLogframe"));
    logframes.add(tempLogframe);
    for (int c = config.getStartYear(); c <= config.getReportingCurrentYear(); c++) {
      if ((tempLogframe = logframeManager.getLogframeByYear(c)) != null) {
        logframes.add(tempLogframe);
      }
    }

    // Setting the list of themes
    themes = new ArrayList<>();
    // fake Theme object
    Theme temp = new Theme(-1);
    temp.setCode(getText("summaries.milestones.allThemes"));
    themes.add(temp);

    themes.addAll(Arrays.asList(themeManager.getThemes(getCurrentReportingLogframe())));

    // Setting the list of milestones
    milestones = new ArrayList<>();
    Milestone milestone = new Milestone(-1);
    milestone.setCode(getText("summaries.milestones.allMilestones"));
    milestones.add(milestone);
    milestones.addAll(Arrays.asList(milestoneManager.getMilestoneList(getCurrentReportingLogframe())));

    // Set the map with the different types of reports
    summaryTypes = new TreeMap<>();
    summaryTypes.put(1, getText("summaries.milestones.trafficLight"));
    summaryTypes.put(2, getText("summaries.milestones.milestoneReport"));
  }

  @Override
  public String save() {
    Logframe tempLogframe = new Logframe(Integer.parseInt(logframeSelected), -1, "");
    Theme tempTheme = new Theme(themeSelected);
    Milestone tempMilestone = new Milestone(Integer.parseInt(milestoneSelected));

    MilestoneReport[] milestoneReports =
      milestoneReportManager.getMilestoneReportsForSumamry(tempLogframe, tempTheme, tempMilestone);

    if (milestoneReports.length == 0) {
      addActionError(getText("summaries.general.noResults"));
      return INPUT;
    }

    if (summaryTypeSelected == 1) {
      milestonesPdf.setSummaryTitle(getText("summaries.milestones.trafficLightReport"));
      milestonesPdf.generateTrafficLightReportPdf(milestoneReports);
    } else if (summaryTypeSelected == 2) {
      milestonesPdf.generateMilestoneReportPdf(milestoneReports);
    }
    return SUCCESS;
  }

  public void setLogframeSelected(String logframeSelected) {
    this.logframeSelected = logframeSelected;
  }

  public void setMilestoneSelected(String milestoneSelected) {
    this.milestoneSelected = milestoneSelected;
  }

  public void setSummaryTypeSelected(int summaryTypeSelected) {
    this.summaryTypeSelected = summaryTypeSelected;
  }

  public void setThemeSelected(int themeSelected) {
    this.themeSelected = themeSelected;
  }

  @Override
  public void validate() {
    boolean problem = false;

    if (save) {
      if (summaryTypeSelected == 0) {
        problem = true;
        addFieldError("summaryTypeSelected", "Escoge ps mk");
      } else if (logframeSelected.equals("-1")) {
        problem = true;
        addFieldError("logframeSelected", "Escoge ps mk");
      }
      if (problem) {
        addActionError(getText("saving.fields.required"));
      }
    }
  }
}