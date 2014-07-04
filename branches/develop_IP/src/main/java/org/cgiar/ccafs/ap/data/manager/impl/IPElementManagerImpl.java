package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPElementDAO;
import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.ArrayList;
import java.util.HashMap;
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
  public boolean deleteIPElements(IPProgram program, IPElementType type) {
    return ipElementDAO.deleteIpElements(program.getId(), type.getId());
  }

  @Override
  public List<IPElement> getIPElements(IPProgram program) {
    List<Map<String, String>> ipElementDataList = ipElementDAO.getIPElement(program.getId());
    return setDataToIPElementObjects(ipElementDataList);
  }

  @Override
  public List<IPElement> getIPElements(IPProgram program, IPElementType type) {
    List<Map<String, String>> ipElementDataList = ipElementDAO.getIPElement(program.getId(), type.getId());
    return setDataToIPElementObjects(ipElementDataList);
  }

  @Override
  public boolean saveIPElements(List<IPElement> elements) {
    Map<String, Object> ipElementData;
    boolean allSaved = true;
    int elementId;

    for (IPElement element : elements) {
      ipElementData = new HashMap<String, Object>();

      if (element.getId() == -1) {
        ipElementData.put("id", null);
      } else {
        ipElementData.put("id", element.getId());
      }

      ipElementData.put("description", element.getDescription());
      ipElementData.put("program_id", element.getProgram().getId());
      ipElementData.put("element_type_id", element.getType().getId());

      elementId = ipElementDAO.saveIPElements(ipElementData);

      // If the result is 0 the element was updated and keep the same id
      elementId = (elementId == 0) ? element.getId() : elementId;

      if (elementId != -1) {
        // Save the indicators of the element if any
        if (element.getIndicators() != null) {
          for (IPIndicator indicator : element.getIndicators()) {
            Map<String, Object> indicatorData = new HashMap<>();
            if (indicator.getId() == -1) {
              indicatorData.put("id", null);
            } else {
              indicatorData.put("id", indicator.getId());
            }

            indicatorData.put("description", indicator.getDescription());
            indicatorData.put("target", indicator.getTarget());
            indicatorData.put("element_id", elementId);
            ipIndicatorDAO.saveIndicator(indicatorData);

          }
        }

      } else {
        // All elements should be saved correctly
        allSaved = (elementId != -1) && allSaved;
      }
    }

    return allSaved;
  }

  /**
   * This function takes the information of IPElements stored in a list of maps
   * to organize it in a list of IPElement objects
   * 
   * @param ipElementDataList
   * @return
   */
  private List<IPElement> setDataToIPElementObjects(List<Map<String, String>> ipElementDataList) {
    List<IPElement> elementsList = new ArrayList<>();

    for (Map<String, String> elementData : ipElementDataList) {
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
      element.setIndicators(indicators);

      // Set element parents if exists
      List<IPElement> parents = new ArrayList<>();
      List<Map<String, String>> parentsData = ipElementDAO.getParentsOfIPElement(element.getId());
      for (Map<String, String> parentData : parentsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));
        parents.add(parentElement);
      }
      element.setParents(parents);

      elementsList.add(element);
    }

    return elementsList;
  }
}
