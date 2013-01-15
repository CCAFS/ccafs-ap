package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutputSummaryDAO;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class OutputSummaryManagerImpl implements OutputSummaryManager {

  private OutputSummaryDAO outputSummaryDAO;

  @Inject
  public OutputSummaryManagerImpl(OutputSummaryDAO outputSumaryDAO) {
    this.outputSummaryDAO = outputSumaryDAO;
  }

  @Override
  public OutputSummary getOutputSummary(int outputID, int activityLeaderId) {

    Map<String, String> outputSummaryData = outputSummaryDAO.getOutputSummary(outputID, activityLeaderId);

    if (outputSummaryData == null) {
      return null;
    }

    return new OutputSummary(Integer.parseInt(outputSummaryData.get("id")), outputSummaryData.get("description"));
  }

  @Override
  public boolean saveOutputSummary(List<OutputSummary> outputSummaries) {
    boolean problem = false;
    List<Map<String, Object>> outputSummaryData = new ArrayList<>();
    for (OutputSummary outputSummary : outputSummaries) {
      Map<String, Object> osData = new HashMap<>();
      osData.put("description", outputSummary.getDescription());
      osData.put("output_id", outputSummary.getOutput().getId());
      osData.put("activity_leader_id", outputSummary.getLeader().getId());
      outputSummaryData.add(osData);
    }

    problem = !outputSummaryDAO.saveOutputsSummaryList(outputSummaryData);
    return !problem;
  }

  @Override
  public boolean updateOutputSummary(List<OutputSummary> outputSummaries) {
    boolean problem = false;
    List<Map<String, Object>> outputSummaryData = new ArrayList<>();
    for (OutputSummary outputSummary : outputSummaries) {
      Map<String, Object> osData = new HashMap<>();
      osData.put("description", outputSummary.getDescription());
      osData.put("id", outputSummary.getId());
      outputSummaryData.add(osData);
    }
    problem = !outputSummaryDAO.updateOutputsSummaryList(outputSummaryData);
    return !problem;
  }
}
