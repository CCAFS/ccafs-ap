package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutputSummaryDAO;
import org.cgiar.ccafs.ap.data.manager.OutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.OutputSummary;

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
  public OutputSummary[] getOutputSumariesList(int activityLeaderID) {
    List<Map<String, String>> outputSummariesDataList = outputSummaryDAO.getOutputSummariesList(activityLeaderID);
    Map<String, String> outputSummaryData;
    OutputSummary[] outputSummariesList = new OutputSummary[outputSummariesDataList.size()];

    for (int c = 0; c < outputSummariesDataList.size(); c++) {
      outputSummaryData = outputSummariesDataList.get(c);
      outputSummariesList[c] =
        new OutputSummary(Integer.parseInt(outputSummaryData.get("id")), outputSummaryData.get("descritpion"));
    }

    if (!outputSummariesDataList.isEmpty()) {
      return outputSummariesList;
    }
    return null;
  }
}
