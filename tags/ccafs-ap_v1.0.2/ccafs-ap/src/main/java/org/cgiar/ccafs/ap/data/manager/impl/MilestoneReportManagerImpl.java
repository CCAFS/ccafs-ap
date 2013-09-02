package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.MilestoneReportDAO;
import org.cgiar.ccafs.ap.data.manager.MilestoneReportManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.MilestoneReport;
import org.cgiar.ccafs.ap.data.model.MilestoneStatus;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.Theme;
import org.cgiar.ccafs.ap.data.model.User.UserRole;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MilestoneReportManagerImpl implements MilestoneReportManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MilestoneReportManagerImpl.class);
  private MilestoneReportDAO milestoneReportDAO;

  @Inject
  public MilestoneReportManagerImpl(MilestoneReportDAO milestoneReportDAO) {
    this.milestoneReportDAO = milestoneReportDAO;
  }

  @Override
  public MilestoneReport[] getMilestoneReports(Leader activityLeader, Logframe logframe, UserRole role) {
    List<Map<String, String>> milestoneReportsDataList = new ArrayList<Map<String, String>>();

    if (role == UserRole.TL) {
      milestoneReportsDataList =
        milestoneReportDAO.getTLMilestoneReportList(activityLeader.getId(), logframe.getId(), logframe.getYear());
    } else if (role == UserRole.RPL) {
      milestoneReportsDataList =
        milestoneReportDAO.getRPLMilestoneReportList(activityLeader.getId(), logframe.getId(), logframe.getYear());
    }

    MilestoneReport[] milestoneReports = new MilestoneReport[milestoneReportsDataList.size()];

    for (int c = 0; c < milestoneReportsDataList.size(); c++) {
      milestoneReports[c] = new MilestoneReport();
      // If there is no a report for the milestone, set the milestone report identifier to -1
      if (milestoneReportsDataList.get(c).get("id") == null) {
        milestoneReports[c].setId(-1);
      } else {
        milestoneReports[c].setId(Integer.parseInt(milestoneReportsDataList.get(c).get("id")));
      }

      if (milestoneReportsDataList.get(c).get("tl_description") != null) {
        milestoneReports[c].setThemeLeaderDescription(milestoneReportsDataList.get(c).get("tl_description"));
      } else {
        milestoneReports[c].setThemeLeaderDescription("");
      }

      if (milestoneReportsDataList.get(c).get("rpl_description") != null) {
        milestoneReports[c].setRegionalLeaderDescription(milestoneReportsDataList.get(c).get("rpl_description"));
      } else {
        milestoneReports[c].setRegionalLeaderDescription("");
      }

      // Temporal milestone status object
      MilestoneStatus status = new MilestoneStatus();
      if (milestoneReportsDataList.get(c).get("milestone_status_id") != null) {
        status.setId(Integer.parseInt(milestoneReportsDataList.get(c).get("milestone_status_id")));
      } else {
        status.setId(-1);
      }
      status.setName(milestoneReportsDataList.get(c).get("milestone_status_name"));

      // Temporal milestone object
      Milestone milestone = new Milestone();
      milestone.setId(Integer.parseInt(milestoneReportsDataList.get(c).get("milestone_id")));
      milestone.setCode(milestoneReportsDataList.get(c).get("milestone_code"));
      milestone.setDescription(milestoneReportsDataList.get(c).get("milestone_description"));

      // Temporal Output object
      Output output = new Output(Integer.parseInt(milestoneReportsDataList.get(c).get("output_id")));
      output.setCode(milestoneReportsDataList.get(c).get("output_code"));

      // Temporal Objective code
      Objective objective = new Objective(Integer.parseInt(milestoneReportsDataList.get(c).get("objective_id")));
      objective.setCode(milestoneReportsDataList.get(c).get("output_id"));

      // Temporal Theme object
      Theme theme = new Theme(Integer.parseInt(milestoneReportsDataList.get(c).get("theme_id")));
      theme.setCode(milestoneReportsDataList.get(c).get("theme_code"));

      // Assign objects
      objective.setTheme(theme);
      output.setObjective(objective);
      milestone.setOutput(output);

      // Add all objects to milestone reports
      milestoneReports[c].setMilestone(milestone);
      milestoneReports[c].setStatus(status);
    }
    return milestoneReports;
  }

  @Override
  public MilestoneReport[] getMilestoneReportsForSumamry(Logframe logframe, Theme theme, Milestone milestone) {
    // Creating the milestone Reports Data List (mrDataList)
    List<Map<String, String>> mrDataList;

    mrDataList =
      milestoneReportDAO.getMilestoneReportListForSummary(logframe.getId(), theme.getId(), milestone.getId());

    MilestoneReport[] milestoneReports = new MilestoneReport[mrDataList.size()];

    for (int c = 0; c < mrDataList.size(); c++) {
      milestoneReports[c] = new MilestoneReport();
      // If there is no a report for the milestone, set the milestone report identifier to -1
      if (mrDataList.get(c).get("id") == null) {
        milestoneReports[c].setId(-1);
      } else {
        milestoneReports[c].setId(Integer.parseInt(mrDataList.get(c).get("id")));
      }

      if (mrDataList.get(c).get("tl_description") != null) {
        milestoneReports[c].setThemeLeaderDescription(mrDataList.get(c).get("tl_description"));
      } else {
        milestoneReports[c].setThemeLeaderDescription("");
      }

      if (mrDataList.get(c).get("rpl_description") != null) {
        milestoneReports[c].setRegionalLeaderDescription(mrDataList.get(c).get("rpl_description"));
      } else {
        milestoneReports[c].setRegionalLeaderDescription("");
      }

      // Temporal milestone status object
      MilestoneStatus status = new MilestoneStatus();
      if (mrDataList.get(c).get("milestone_status_id") != null) {
        status.setId(Integer.parseInt(mrDataList.get(c).get("milestone_status_id")));
      } else {
        status.setId(-1);
      }
      status.setName(mrDataList.get(c).get("milestone_status_name"));

      // Temporal milestone object
      Milestone milestoneObj = new Milestone();
      milestoneObj.setId(Integer.parseInt(mrDataList.get(c).get("milestone_id")));
      milestoneObj.setCode(mrDataList.get(c).get("milestone_code"));
      milestoneObj.setDescription(mrDataList.get(c).get("milestone_description"));

      // Temporal Output object
      Output output = new Output(Integer.parseInt(mrDataList.get(c).get("output_id")));
      output.setCode(mrDataList.get(c).get("output_code"));
      output.setDescription(mrDataList.get(c).get("output_description"));

      // Temporal Objective code
      Objective objective = new Objective(Integer.parseInt(mrDataList.get(c).get("objective_id")));
      objective.setCode(mrDataList.get(c).get("objective_code"));
      objective.setDescription(mrDataList.get(c).get("objective_description"));
      objective.setOutcomeDescription(mrDataList.get(c).get("outcome_description"));

      // Temporal Theme object
      Theme themeObj = new Theme(Integer.parseInt(mrDataList.get(c).get("theme_id")));
      themeObj.setCode(mrDataList.get(c).get("theme_code"));
      themeObj.setDescription(mrDataList.get(c).get("theme_description"));

      // Assign objects
      objective.setTheme(themeObj);
      output.setObjective(objective);
      milestoneObj.setOutput(output);

      // Add all objects to milestone reports
      milestoneReports[c].setMilestone(milestoneObj);
      milestoneReports[c].setStatus(status);
    }
    return milestoneReports;
  }

  @Override
  public boolean saveMilestoneReports(MilestoneReport[] milestoneReports) {
    List<Map<String, Object>> milestoneReportDataList = new ArrayList<>();

    for (MilestoneReport milestoneReport : milestoneReports) {
      Map<String, Object> milestoneReportData = new HashMap<String, Object>();
      milestoneReportData.put("id", milestoneReport.getId());
      milestoneReportData.put("milestone_id", milestoneReport.getMilestone().getId());
      if (milestoneReport.getStatus().getId() == -1) {
        milestoneReportData.put("milestone_status_id", null);
      } else {
        milestoneReportData.put("milestone_status_id", milestoneReport.getStatus().getId());
      }
      if (milestoneReport.getThemeLeaderDescription().isEmpty()) {
        milestoneReportData.put("tl_description", null);
      } else {
        milestoneReportData.put("tl_description", milestoneReport.getThemeLeaderDescription());
      }
      if (milestoneReport.getRegionalLeaderDescription().isEmpty()) {
        milestoneReportData.put("rpl_description", null);
      } else {
        milestoneReportData.put("rpl_description", milestoneReport.getRegionalLeaderDescription());
      }

      milestoneReportDataList.add(milestoneReportData);
    }
    return !milestoneReportDAO.saveMilestoneReportList(milestoneReportDataList);
  }
}