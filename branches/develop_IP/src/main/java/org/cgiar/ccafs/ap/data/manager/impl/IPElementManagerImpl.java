package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPElementDAO;
import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class IPElementManagerImpl implements IPElementManager {

  // DAOs
  private IPElementDAO ipElementDAO;
  private IPIndicatorDAO ipIndicatorDAO;

  @Inject
  public IPElementManagerImpl(IPElementDAO ipElementDAO, IPIndicatorDAO ipIndicatorDAO) {
    this.ipElementDAO = ipElementDAO;
    this.ipIndicatorDAO = ipIndicatorDAO;
  }

  @Override
  public List<IPElement> getIPElementsByProgram(IPProgram program) {
    List<IPElement> IPElements = new ArrayList<>();
    List<Map<String, String>> IPElementDataList = ipElementDAO.getIPElementByProgram(program.getId());

    for (Map<String, String> elementData : IPElementDataList) {
      IPElement element = new IPElement();
      element.setId(Integer.parseInt(elementData.get("id")));
      element.setDescription(elementData.get("description"));

      // Create an object program
      IPProgram programTemp = new IPProgram();
      programTemp.setId(Integer.parseInt(elementData.get("program_id")));
      programTemp.setAcronym(elementData.get("program_acronym"));
      element.setProgram(programTemp);

      // Create an object IPElementType
      IPElementType type = new IPElementType();
      type.setId(Integer.parseInt(elementData.get("element_type_id")));
      type.setName(elementData.get("element_type_name"));
      element.setType(type);

      // Set element indicators if exists
      List<IPIndicator> indicators = new ArrayList<>();
      List<Map<String, String>> indicatorsData = ipIndicatorDAO.getIndicatorsByIpElementID(element.getId());
      for (Map<String, String> indicatorData : indicatorsData) {
        IPIndicator indicator = new IPIndicator();
        indicator.setId(Integer.parseInt(indicatorData.get("id")));
        indicator.setDescription(indicatorData.get("description"));
        indicator.setTarget(indicatorData.get("target"));
        indicators.add(indicator);
      }

    }

    return IPElements;
  }
}
