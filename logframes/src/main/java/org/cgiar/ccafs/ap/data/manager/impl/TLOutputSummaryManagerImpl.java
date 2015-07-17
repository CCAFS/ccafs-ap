package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.TLOutputSummaryDAO;
import org.cgiar.ccafs.ap.data.manager.TLOutputSummaryManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.TLOutputSummary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class TLOutputSummaryManagerImpl implements TLOutputSummaryManager {

  private TLOutputSummaryDAO tlOutputDAO;

  @Inject
  public TLOutputSummaryManagerImpl(TLOutputSummaryDAO tlOutputDAO) {
    this.tlOutputDAO = tlOutputDAO;
  }

  @Override
  public List<TLOutputSummary> getTLOutputSummaries(Leader leader, Logframe logframe) {
    List<TLOutputSummary> outputs = new ArrayList<>();
    int themeCode = Integer.parseInt(leader.getTheme().getCode());
    List<Map<String, Object>> outputsData = tlOutputDAO.getTLOutputSummaries(themeCode, logframe.getId());
    for (Map<String, Object> outputData : outputsData) {
      TLOutputSummary output = new TLOutputSummary();
      // if there is not an id, just assign -1 as default.
      output.setId(outputData.get("id") == null ? -1 : Integer.parseInt(outputData.get("id").toString()));
      output.setDescription(outputData.get("description") != null ? outputData.get("description").toString() : null);
      Output newOutput = new Output();
      newOutput.setId(Integer.parseInt(outputData.get("output_id").toString()));
      newOutput.setDescription(outputData.get("output_description").toString());
      // e.g. 1.1.1 2012 (1)
      String code = outputData.get("milestone_code").toString();
      code = code.split(" ")[0]; // 1.1.1
      newOutput.setCode(code);
      output.setOutput(newOutput);

      outputs.add(output);
    }
    return outputs;
  }

  @Override
  public boolean saveTLOutputSummaries(List<TLOutputSummary> outputs, Leader leader) {
    List<Map<String, Object>> outputsData = new ArrayList<>();
    for (TLOutputSummary outputSummary : outputs) {
      Map<String, Object> outputData = new HashMap<>();
      outputData.put("id", outputSummary.getId());
      outputData.put("description", outputSummary.getDescription());
      outputData.put("output_id", outputSummary.getOutput().getId());
      outputData.put("activity_leader_id", leader.getId());
      outputsData.add(outputData);
    }
    return !tlOutputDAO.saveTLOutputSummaries(outputsData);
  }

}
