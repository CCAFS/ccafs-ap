package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPElementDAO;
import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class IPIndicatorManagerImpl implements IPIndicatorManager {

  private IPIndicatorDAO indicatorDAO;
  private IPElementDAO elementDAO;

  @Inject
  public IPIndicatorManagerImpl(IPIndicatorDAO indicatorDAO, IPElementDAO elementDAO) {
    this.indicatorDAO = indicatorDAO;
    this.elementDAO = elementDAO;
  }

  @Override
  public List<IPIndicator> getElementIndicators(IPElement element) {
    List<IPIndicator> indicators = new ArrayList<>();
    int programElementID = elementDAO.getProgramElementID(element.getId(), element.getProgram().getId());
    List<Map<String, String>> indicatorsDataList = indicatorDAO.getIndicatorsByIpProgramElementID(programElementID);

    for (Map<String, String> iData : indicatorsDataList) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));

      // Parent indicator
      if (iData.get("parent_id") != null) {
        IPIndicator parent = new IPIndicator();
        parent.setId(Integer.parseInt(iData.get("parent_id")));
        parent.setDescription(iData.get("parent_description"));
        indicator.setParent(parent);
      }

      indicators.add(indicator);
    }

    return indicators;
  }

  @Override
  public boolean removeElementIndicators(IPElement element, IPProgram program) {
    return indicatorDAO.removeIpElementIndicators(element.getId(), program.getId());
  }


}
