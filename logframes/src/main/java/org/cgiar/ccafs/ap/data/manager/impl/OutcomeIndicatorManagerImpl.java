package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutcomeIndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.OutcomeIndicatorManager;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.OutcomeIndicator;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class OutcomeIndicatorManagerImpl implements OutcomeIndicatorManager {

  private OutcomeIndicatorDAO outcomeIndicatorDAO;

  @Inject
  public OutcomeIndicatorManagerImpl(OutcomeIndicatorDAO outcomeIndicatorDAO) {
    this.outcomeIndicatorDAO = outcomeIndicatorDAO;
  }

  @Override
  public OutcomeIndicator[] getOutcomeIndicators(Logframe logframe) {
    OutcomeIndicator[] outcomeIndicators;
    List<Map<String, String>> outcomeIndicatorsDataList = outcomeIndicatorDAO.getOutcomeIndicators(logframe.getYear());

    outcomeIndicators = new OutcomeIndicator[outcomeIndicatorsDataList.size()];
    for (int c = 0; c < outcomeIndicatorsDataList.size(); c++) {
      OutcomeIndicator oi = new OutcomeIndicator();
      oi.setId(Integer.parseInt(outcomeIndicatorsDataList.get(c).get("id")));
      oi.setCode(Integer.parseInt(outcomeIndicatorsDataList.get(c).get("code")));
      oi.setDescription(outcomeIndicatorsDataList.get(c).get("description"));

      // fake theme
      Theme theme = new Theme();
      theme.setId(Integer.parseInt(outcomeIndicatorsDataList.get(c).get("theme_id")));
      theme.setCode(outcomeIndicatorsDataList.get(c).get("theme_code"));
      oi.setTheme(theme);

      outcomeIndicators[c] = oi;
    }

    return outcomeIndicators;
  }
}
