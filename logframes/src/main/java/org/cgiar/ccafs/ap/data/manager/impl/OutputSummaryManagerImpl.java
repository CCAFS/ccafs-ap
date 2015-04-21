package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutputSummaryDAO;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.OutputSummary;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutputSummaryManagerImpl implements OutputSummaryManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OutputSummaryManagerImpl.class);
  private OutputSummaryDAO outputSummaryDAO;

  @Inject
  public OutputSummaryManagerImpl(OutputSummaryDAO outputSumaryDAO) {
    this.outputSummaryDAO = outputSumaryDAO;
  }

  @Override
  public OutputSummary[] getOutputSummaries(Leader activityLeader, Logframe logframe) {
    List<Map<String, String>> outputSummaryDataList =
      outputSummaryDAO.getOutputSummariesList(activityLeader.getId(), logframe.getId());
    OutputSummary[] outputSummaries = new OutputSummary[outputSummaryDataList.size()];

    for (int c = 0; c < outputSummaryDataList.size(); c++) {
      outputSummaries[c] = new OutputSummary();
      if (outputSummaryDataList.get(c).get("id") != null) {
        outputSummaries[c].setId(Integer.parseInt(outputSummaryDataList.get(c).get("id")));
      }
      if (outputSummaryDataList.get(c).get("description") != null) {
        outputSummaries[c].setDescription(outputSummaryDataList.get(c).get("description"));
      }

      // Temporal output object
      Output op = new Output();
      op.setId(Integer.parseInt(outputSummaryDataList.get(c).get("output_id")));
      op.setCode(outputSummaryDataList.get(c).get("output_code"));
      op.setDescription(outputSummaryDataList.get(c).get("output_description"));

      // Temporal objective object
      Objective obj = new Objective();
      obj.setId(Integer.parseInt(outputSummaryDataList.get(c).get("objective_id")));
      obj.setCode(outputSummaryDataList.get(c).get("objective_code"));

      // Temporal theme object
      Theme th = new Theme();
      th.setId(Integer.parseInt(outputSummaryDataList.get(c).get("theme_id")));
      th.setCode(outputSummaryDataList.get(c).get("theme_code"));

      // Assign objects
      obj.setTheme(th);
      op.setObjective(obj);
      outputSummaries[c].setOutput(op);
      outputSummaries[c].setLeader(activityLeader);
    }
    return outputSummaries;
  }

  @Override
  public boolean saveOutputSummary(List<OutputSummary> outputSummaries) {
    boolean problem = false;
    List<Map<String, Object>> outputSummaryData = new ArrayList<>();
    for (OutputSummary outputSummary : outputSummaries) {
      Map<String, Object> osData = new HashMap<>();
      if (outputSummary.getDescription().isEmpty()) {
        osData.put("description", null);
      } else {
        osData.put("description", outputSummary.getDescription());
      }
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
      osData.put("output_id", outputSummary.getOutput().getId());
      osData.put("activity_leader_id", outputSummary.getLeader().getId());
      outputSummaryData.add(osData);
    }
    problem = !outputSummaryDAO.updateOutputsSummaryList(outputSummaryData);
    return !problem;
  }
}
