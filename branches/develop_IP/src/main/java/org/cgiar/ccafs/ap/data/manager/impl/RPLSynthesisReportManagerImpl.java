package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.RPLSynthesisReportDAO;
import org.cgiar.ccafs.ap.data.manager.RPLSynthesisReportManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.RPLSynthesisReport;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RPLSynthesisReportManagerImpl implements RPLSynthesisReportManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(RPLSynthesisReportManagerImpl.class);
  private RPLSynthesisReportDAO synthesisReportDAO;

  @Inject
  public RPLSynthesisReportManagerImpl(RPLSynthesisReportDAO synthesisReportDAO) {
    this.synthesisReportDAO = synthesisReportDAO;
  }

  @Override
  public RPLSynthesisReport getRPLSynthesisReport(Leader leader, Logframe logframe) {
    Map<String, Object> synthesisData = synthesisReportDAO.getRPLSynthesisReport(leader.getId(), logframe.getId());
    RPLSynthesisReport synthesisReport = new RPLSynthesisReport();
    if (synthesisData == null) {
      synthesisReport.setId(-1);
    } else {
      synthesisReport.setId((int) synthesisData.get("id"));
      synthesisReport.setCcafsSites(synthesisData.get("ccafs_sites").toString());
      synthesisReport.setCrossCenter(synthesisData.get("cross_center").toString());
      synthesisReport.setRegional(synthesisData.get("regional").toString());
      synthesisReport.setDecisionSupport(synthesisData.get("decision_support").toString());
      synthesisReport.setLeader(leader);
      synthesisReport.setLogframe(logframe);
    }
    return synthesisReport;
  }

  @Override
  public boolean saveRPLSynthesisReport(RPLSynthesisReport synthesisReport, Leader leader, Logframe logframe) {
    Map<String, Object> synthesisData = new HashMap<String, Object>();
    synthesisData.put("id", synthesisReport.getId());
    synthesisData.put("ccafs_sites", synthesisReport.getCcafsSites());
    synthesisData.put("cross_center", synthesisReport.getCrossCenter());
    synthesisData.put("regional", synthesisReport.getRegional());
    synthesisData.put("decision_support", synthesisReport.getDecisionSupport());
    synthesisData.put("activity_leader_id", leader.getId());
    synthesisData.put("logframe_id", logframe.getId());
    return synthesisReportDAO.saveRPLSynthesisReport(synthesisData);
  }
}
