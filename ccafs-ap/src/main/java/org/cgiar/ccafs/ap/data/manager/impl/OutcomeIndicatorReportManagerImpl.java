package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutcomeIndicatorReportDAO;
import org.cgiar.ccafs.ap.data.manager.OutcomeIndicatorReportManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicator;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicatorReport;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class OutcomeIndicatorReportManagerImpl implements OutcomeIndicatorReportManager {

  private OutcomeIndicatorReportDAO oirDAO;

  @Inject
  public OutcomeIndicatorReportManagerImpl(OutcomeIndicatorReportDAO oirDAO) {
    this.oirDAO = oirDAO;
  }

  @Override
  public List<OutcomeIndicatorReport> getOutcomeIndicatorReports(Logframe logframe) {
    List<OutcomeIndicatorReport> outcomeIndicatorReports = new ArrayList<>();
    List<Map<String, String>> oirDataList = oirDAO.getOutcomeIndicatorReports(logframe.getYear());

    for (Map<String, String> oirData : oirDataList) {
      OutcomeIndicatorReport oir = new OutcomeIndicatorReport();

      if (oirData.get("id") != null) {
        oir.setId(Integer.parseInt(oirData.get("id")));
      } else {
        oir.setId(-1);
      }
      oir.setAchievements(oirData.get("achievements"));
      oir.setEvidence(oirData.get("evidence"));

      // temporal OutcomeIndicatorReport object
      OutcomeIndicator outcomeIndicator = new OutcomeIndicator();
      outcomeIndicator.setId(Integer.parseInt(oirData.get("outcome_indicator_id")));
      outcomeIndicator.setCode(Integer.parseInt(oirData.get("outcome_indicator_code")));
      outcomeIndicator.setDescription(oirData.get("outcome_indicator_description"));

      // Theme object
      Theme theme = new Theme();
      theme.setId(Integer.parseInt(oirData.get("theme_id")));
      theme.setCode(oirData.get("theme_code"));
      outcomeIndicator.setTheme(theme);

      // Leader object
      Leader leader = new Leader();
      if (oirData.get("leader_id") != null) {
        leader.setId(Integer.parseInt(oirData.get("leader_id")));
        leader.setAcronym(oirData.get("leader_acronym"));
      }

      oir.setOutcomeIndicator(outcomeIndicator);
      oir.setLeader(leader);

      outcomeIndicatorReports.add(oir);
    }

    return outcomeIndicatorReports;
  }

  @Override
  public boolean saveOutcomeIndicatorReports(List<OutcomeIndicatorReport> outcomeIndicatorReports, Leader leader,
    Logframe logframe) {
    List<Map<String, String>> oirDataList = new ArrayList<>();

    for (OutcomeIndicatorReport oir : outcomeIndicatorReports) {
      Map<String, String> oirData = new HashMap<String, String>();
      if (oir.getId() != -1) {
        oirData.put("id", String.valueOf(oir.getId()));
      } else {
        oirData.put("id", null);
      }

      if ((oir.getAchievements() == null || oir.getAchievements().isEmpty())) {
        oirData.put("achievements", null);
      } else {
        oirData.put("achievements", oir.getAchievements());
      }

      if ((oir.getEvidence() == null || oir.getEvidence().isEmpty())) {
        oirData.put("evidence", null);
      } else {
        oirData.put("evidence", oir.getEvidence());
      }

      oirData.put("leader_id", String.valueOf(leader.getId()));
      oirData.put("outcome_indicator_id", String.valueOf(oir.getOutcomeIndicator().getId()));

      oirDataList.add(oirData);
    }

    return oirDAO.saveOutcomeIndicators(oirDataList);
    // return false;
  }
}
