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
    List<Map<String, Object>> outputsData = tlOutputDAO.getTLOutputSummaries(leader.getId(), logframe.getId());
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
