package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutputDAO;
import org.cgiar.ccafs.ap.data.manager.OutputManager;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class OutputManagerImpl implements OutputManager {

  private OutputDAO outputDAO;

  @Inject
  public OutputManagerImpl(OutputDAO outputDAO) {
    this.outputDAO = outputDAO;
  }

  @Override
  public Output[] getOutputList(int activityLeaderID) {
    List<Map<String, String>> outputDataList = outputDAO.getOutputsList(activityLeaderID);
    Map<String, String> outputData;
    Output[] outputsList = new Output[outputDataList.size()];

    for (int c = 0; c < outputDataList.size(); c++) {
      outputData = outputDataList.get(c);

      // Create a temporal theme object
      Theme theme = new Theme(Integer.parseInt(outputData.get("theme_id")));
      theme.setCode(outputData.get("theme_code"));

      // Create a temporal objective object
      Objective obj = new Objective(Integer.parseInt(outputData.get("objective_id")));
      obj.setCode(outputData.get("objective_code"));
      obj.setTheme(theme);

      outputsList[c] = new Output(Integer.parseInt(outputData.get("id")));
      outputsList[c].setDescription(outputData.get("description"));
      outputsList[c].setCode(outputData.get("code"));
      outputsList[c].setObjective(obj);
    }

    if (outputDataList.isEmpty()) {
      return null;
    }

    return outputsList;
  }


}
