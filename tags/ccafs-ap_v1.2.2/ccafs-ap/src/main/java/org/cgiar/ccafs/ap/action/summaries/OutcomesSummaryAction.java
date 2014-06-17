/*
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
 */

package org.cgiar.ccafs.ap.action.summaries;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.action.summaries.pdf.OutcomesSummaryPdf;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.manager.LogframeManager;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Outcome;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomesSummaryAction extends BaseAction {

  private static final long serialVersionUID = 3562157484973719128L;

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(OutcomesSummaryAction.class);

  // Manager
  private OutcomeManager outcomeManager;
  private LeaderManager leaderManager;

  // Model
  private OutcomesSummaryPdf outcomesPdf;
  private List<Leader> leaders;
  private List<Country> countries;

  private String leadersSelected;
  private int yearSelected;

  @Inject
  public OutcomesSummaryAction(APConfig config, LogframeManager logframeManager, OutcomeManager outcomeManager,
    LeaderManager leaderManager, OutcomesSummaryPdf outcomesPdf) {
    super(config, logframeManager);
    this.outcomeManager = outcomeManager;
    this.leaderManager = leaderManager;
    this.logframeManager = logframeManager;
    this.outcomesPdf = outcomesPdf;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public int getContentLength() {
    return outcomesPdf.getContentLength();
  }

  public List<Country> getCountries() {
    return countries;
  }

  public String getFileName() {
    return outcomesPdf.getFileName();
  }

  public InputStream getInputStream() {
    return outcomesPdf.getInputStream();
  }

  public List<Leader> getLeaders() {
    return leaders;
  }

  public String getLeadersSelected() {
    return leadersSelected;
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
  }

  @Override
  public String save() {

    int leader = (leadersSelected == null) ? -1 : Integer.parseInt(leadersSelected);
    int year = (yearSelected == 0) ? -1 : yearSelected;

    // Fake objects
    Leader fakeLeader = new Leader(leader);
    Logframe fakeLogframe;

    if (year != -1) {
      fakeLogframe = logframeManager.getLogframeByYear(year);
      if (fakeLogframe == null) {
        addActionError(getText("summaries.general.noResults"));
        return INPUT;
      }
    } else {
      // Create a fake logframe with id -1 to indicate all logframes
      fakeLogframe = new Logframe();
      fakeLogframe.setId(year);
    }

    List<Outcome> outcomes = outcomeManager.getOutcomesForSummary(fakeLeader, fakeLogframe);

    if (outcomes.isEmpty()) {
      addActionError(getText("summaries.general.noResults"));
      return INPUT;
    }

    outcomesPdf.setSummaryTitle(getText("summaries.outcomes.pdf.title"));
    outcomesPdf.generatePdf(outcomes);
    return SUCCESS;
  }

  public void setLeadersSelected(String leadersSelected) {
    this.leadersSelected = leadersSelected;
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