/*****************************************************************
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
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.IPElementDAO;
import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;
import org.cgiar.ccafs.ap.data.dao.IPRelationshipDAO;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class IPElementManagerImpl implements IPElementManager {

  // DAOs
  private IPElementDAO ipElementDAO;
  private IPIndicatorDAO ipIndicatorDAO;
  private IPRelationshipDAO ipRelationshipDAO;
  private IPIndicatorManager ipIndicatorManager;
  private IPProgramManager ipProgamManager;

  @Inject
  public IPElementManagerImpl(IPElementDAO ipElementDAO, IPIndicatorDAO ipIndicatorDAO,
    IPRelationshipDAO ipRelationshipDAO, IPIndicatorManager ipIndicatorManager, IPProgramManager ipProgamManager) {
    this.ipElementDAO = ipElementDAO;
    this.ipIndicatorDAO = ipIndicatorDAO;
    this.ipRelationshipDAO = ipRelationshipDAO;
    this.ipIndicatorManager = ipIndicatorManager;
    this.ipProgamManager = ipProgamManager;
  }

  @Override
  public boolean deleteChildIPElements(IPElement parentElement) {
    return ipElementDAO.deleteChildIPElements(parentElement.getId());
  }

  @Override
  public boolean deleteIPElement(IPElement element) {
    return ipElementDAO.deleteIPElement(element.getId());
  }

  @Override
  // TODO - This method deletes ip_program_elements, we need to check where is it called in order to know if it is
  // really
  // required.
  public boolean deleteIPElements(IPProgram program, IPElementType type) {
    return ipElementDAO.deleteIpElements(program.getId(), type.getId());
  }

  @Override
  public IPElement getIPElement(int elementID) {
    List<Map<String, String>> ipElementsDataList = ipElementDAO.getIPElements(new String[] {String.valueOf(elementID)});
    List<IPElement> elements = this.setDataToIPElementObjects(ipElementsDataList);
    if (elements.size() == 1) {
      return elements.get(0);
    }
    return null;
  }

  @Override
  public List<IPElement> getIPElementList() {
    List<Map<String, String>> elementDataList = ipElementDAO.getAllIPElements();
    return this.setDataToIPElementObjects(elementDataList);
  }

  @Override
  public List<IPElement> getIPElementList(String[] ids) {
    List<Map<String, String>> ipElementDataList = ipElementDAO.getIPElements(ids);
    return this.setDataToIPElementObjects(ipElementDataList);
  }

  @Override
  public List<IPElement> getIPElementListForGraph(IPProgram _program) {
    List<Map<String, String>> ipElementDataList;
    List<IPElement> elementsList = new ArrayList<>();

    if (_program.getId() == -1) {
      ipElementDataList = ipElementDAO.getAllIPElements();
    } else {
      ipElementDataList = ipElementDAO.getIPElementByProgramID(_program.getId());
    }

    for (Map<String, String> elementData : ipElementDataList) {
      IPElement element = new IPElement();
      element.setId(Integer.parseInt(elementData.get("id")));
      element.setDescription(elementData.get("description"));

      // Set the program
      IPProgram program = new IPProgram();
      program.setId(Integer.parseInt(elementData.get("program_id")));
      program.setAcronym(elementData.get("program_acronym"));
      element.setProgram(program);

      // Set elements 'contributesTo' if exists
      List<IPElement> elementsRelated = new ArrayList<>();
      List<Map<String, String>> elementsData =
        ipElementDAO.getIPElementsRelated(element.getId(), APConstants.ELEMENT_RELATION_CONTRIBUTION);
      for (Map<String, String> parentData : elementsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));
        IPProgram parentProgram = new IPProgram();
        parentProgram.setId(Integer.parseInt(parentData.get("program_id")));
        parentProgram.setAcronym(parentData.get("program_acronym"));
        parentElement.setProgram(parentProgram);
        IPElementType parentType = new IPElementType();
        parentType.setId(Integer.parseInt(parentData.get("element_type_id")));
        parentType.setName(parentData.get("element_type_name"));
        parentElement.setType(parentType);
        elementsRelated.add(parentElement);
      }
      element.setContributesTo(elementsRelated);

      // Set elements 'translatedOf' if exists
      elementsRelated = new ArrayList<>();
      elementsData = ipElementDAO.getIPElementsRelated(element.getId(), APConstants.ELEMENT_RELATION_TRANSLATION);
      for (Map<String, String> parentData : elementsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));
        IPProgram parentProgram = new IPProgram();
        parentProgram.setId(Integer.parseInt(parentData.get("program_id")));
        parentProgram.setAcronym(parentData.get("program_acronym"));
        parentElement.setProgram(parentProgram);
        IPElementType parentType = new IPElementType();
        parentType.setId(Integer.parseInt(parentData.get("element_type_id")));
        parentType.setName(parentData.get("element_type_name"));
        parentElement.setType(parentType);
        elementsRelated.add(parentElement);
      }
      element.setTranslatedOf(elementsRelated);

      // Set element types
      IPElementType type = new IPElementType();
      type.setId(Integer.parseInt(elementData.get("element_type_id")));
      type.setName(elementData.get("element_type_name"));
      element.setType(type);

      elementsList.add(element);
    }

    return elementsList;

  }


  @Override
  public List<IPElement> getIPElementListForSynthesis(IPProgram _program) {
    List<Map<String, String>> ipElementDataList;
    List<IPElement> elementsList = new ArrayList<>();

    switch (_program.getId()) {
      case 1:
      case 2:
      case 3:
      case 4:
        ipElementDataList = ipElementDAO.getIPElementByProgramIDSynthesis(_program.getId());
        break;

      default:
        /**
         * TODO
         */
        ipElementDataList = ipElementDAO.getIPElementListForSynthesisRegion(_program.getId());
        break;
    }


    for (Map<String, String> elementData : ipElementDataList) {
      IPElement element = new IPElement();
      element.setId(Integer.parseInt(elementData.get("id")));
      element.setDescription(elementData.get("description"));

      // Set the program
      IPProgram program = new IPProgram();
      program.setId(Integer.parseInt(elementData.get("program_id")));
      program.setAcronym(elementData.get("program_acronym"));
      element.setProgram(program);

      // Set elements 'contributesTo' if exists
      List<IPElement> elementsRelated = new ArrayList<>();
      List<Map<String, String>> elementsData =
        ipElementDAO.getIPElementsRelated(element.getId(), APConstants.ELEMENT_RELATION_CONTRIBUTION);
      for (Map<String, String> parentData : elementsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));
        IPProgram parentProgram = new IPProgram();
        parentProgram.setId(Integer.parseInt(parentData.get("program_id")));
        parentProgram.setAcronym(parentData.get("program_acronym"));
        parentElement.setProgram(parentProgram);
        IPElementType parentType = new IPElementType();
        parentType.setId(Integer.parseInt(parentData.get("element_type_id")));
        parentType.setName(parentData.get("element_type_name"));
        parentElement.setType(parentType);
        elementsRelated.add(parentElement);
      }
      element.setContributesTo(elementsRelated);

      // Set elements 'translatedOf' if exists
      elementsRelated = new ArrayList<>();
      elementsData = ipElementDAO.getIPElementsRelated(element.getId(), APConstants.ELEMENT_RELATION_TRANSLATION);
      for (Map<String, String> parentData : elementsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));
        IPProgram parentProgram = new IPProgram();
        parentProgram.setId(Integer.parseInt(parentData.get("program_id")));
        parentProgram.setAcronym(parentData.get("program_acronym"));
        parentElement.setProgram(parentProgram);
        IPElementType parentType = new IPElementType();
        parentType.setId(Integer.parseInt(parentData.get("element_type_id")));
        parentType.setName(parentData.get("element_type_name"));
        parentElement.setType(parentType);
        elementsRelated.add(parentElement);
      }
      element.setTranslatedOf(elementsRelated);

      // Set element types
      IPElementType type = new IPElementType();
      type.setId(Integer.parseInt(elementData.get("element_type_id")));
      type.setName(elementData.get("element_type_name"));
      element.setType(type);

      elementsList.add(element);
    }

    return elementsList;

  }

  @Override
  public List<IPElement> getIPElements(IPProgram program) {
    List<Map<String, String>> ipElementDataList = ipElementDAO.getIPElementByProgramID(program.getId());
    return this.setDataToIPElementObjects(ipElementDataList);
  }

  @Override
  public List<IPElement> getIPElements(IPProgram program, IPElementType type) {
    List<Map<String, String>> ipElementDataList = ipElementDAO.getIPElement(program.getId(), type.getId());
    return this.setDataToIPElementObjects(ipElementDataList);
  }

  @Override
  public List<IPElement> getIPElementsByParent(IPElement parent, int relationTypeID) {
    List<Map<String, String>> ipElementDataList = ipElementDAO.getIPElementsByParent(parent.getId(), relationTypeID);
    return this.setDataToIPElementObjects(ipElementDataList);
  }

  @Override
  public int getMOGIndex(IPElement mog) {
    int index = 0;
    // Getting all the MOGs
    List<IPElement> allMOGs = this.getIPElements(mog.getProgram(), mog.getType());
    // Getting the mog position.
    for (int i = 0; i < allMOGs.size(); i++) {
      if (allMOGs.get(i).getId() == mog.getId()) {
        return (i + 1);
      }
    }
    return index;
  }

  @Override
  public List<IPElement> getProjectOutputs(int projectID) {
    List<Map<String, String>> elementDataList = ipElementDAO.getProjectOutputs(projectID);
    List<IPElement> elementsList = new ArrayList<>();

    for (Map<String, String> elementData : elementDataList) {
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

      // Set the parent element
      List<IPElement> elementsRelated = new ArrayList<>();
      IPElement parentElement = new IPElement();
      parentElement.setId(Integer.parseInt(elementData.get("parent_id")));
      parentElement.setDescription(elementData.get("parent_description"));
      elementsRelated.add(parentElement);
      element.setContributesTo(elementsRelated);

      elementsList.add(element);
    }

    return elementsList;
  }


  @Override
  public List<IPElement> getProjectOutputsCcafs(int projectID) {
    List<Map<String, String>> elementDataList = ipElementDAO.getProjectOutputsCcafs(projectID);
    List<IPElement> elementsList = new ArrayList<>();

    for (Map<String, String> elementData : elementDataList) {
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

      // Set the parent element
      List<IPElement> elementsRelated = new ArrayList<>();
      IPElement parentElement = new IPElement();
      parentElement.setId(Integer.parseInt(elementData.get("parent_id")));
      parentElement.setDescription(elementData.get("parent_description"));
      elementsRelated.add(parentElement);
      element.setContributesTo(elementsRelated);

      elementsList.add(element);
    }

    return elementsList;
  }

  @Override
  public boolean saveIPElements(List<IPElement> elements, User user, String justification) {
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
      ipElementData.put("creator_id", element.getProgram().getId());
      ipElementData.put("element_type_id", element.getType().getId());
      ipElementData.put("created_by", user.getId());
      ipElementData.put("modified_by", user.getId());
      ipElementData.put("modification_justification", justification);
      elementId = ipElementDAO.createIPElement(ipElementData);

      // If the ip_element was updated, createIPElement method returns 0, update the value
      elementId = (elementId == 0) ? element.getId() : elementId;


      // If the result is 0 the element was updated and keep the same id
      elementId = (elementId == 0) ? element.getId() : elementId;

      //
      // VAMOS POR ACA
      //
      if (elementId != -1) {
        // Save the indicators of the element if any
        if (element.getIndicators() != null) {
          for (IPIndicator indicator : element.getIndicators()) {
            if (indicator == null) {
              continue;
            }
            Map<String, Object> indicatorData = new HashMap<>();
            if (indicator.getId() == -1) {
              indicatorData.put("id", null);
            } else {
              indicatorData.put("id", indicator.getId());
            }

            indicatorData.put("description", indicator.getDescription());
            indicatorData.put("target", indicator.getTarget());
            indicatorData.put("ip_element_id", elementId);
            indicatorData.put("user_id", user.getId());
            indicatorData.put("justification", justification);

            if (indicator.getParent() != null) {
              indicatorData.put("parent_id", indicator.getParent().getId());
            } else {
              indicatorData.put("parent_id", null);
            }
            ipIndicatorDAO.saveIndicator(indicatorData);
          }
        }

        // Save the relations of type contribution added if any
        if (element.getContributesTo() != null) {
          for (IPElement parentElement : element.getContributesTo()) {
            ipRelationshipDAO.saveIPRelation(parentElement.getId(), elementId,
              APConstants.ELEMENT_RELATION_CONTRIBUTION);
          }
        }

        // Save the relations of type translation if any
        if (element.getTranslatedOf() != null) {
          for (IPElement parentElement : element.getTranslatedOf()) {
            ipRelationshipDAO.saveIPRelation(parentElement.getId(), elementId,
              APConstants.ELEMENT_RELATION_TRANSLATION);
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
      programTemp = ipProgamManager.getIPProgramById(programTemp.getId());


      element.setProgram(programTemp);

      // Create an object IPElementType
      IPElementType type = new IPElementType();
      type.setId(Integer.parseInt(elementData.get("element_type_id")));
      type.setName(elementData.get("element_type_name"));
      element.setType(type);

      // Set element indicators if exists
      List<IPIndicator> indicators = new ArrayList<>();
      List<Map<String, String>> indicatorsData = ipIndicatorDAO.getIndicatorsByElementID(element.getId());
      IPIndicator previewFlagship = null;
      for (Map<String, String> indicatorData : indicatorsData) {
        IPIndicator indicator = new IPIndicator();
        indicator.setId(Integer.parseInt(indicatorData.get("id")));
        indicator.setDescription(indicatorData.get("description"));
        indicator.setTarget(indicatorData.get("target"));
        previewFlagship = ipIndicatorManager.getIndicatorFlgship(indicator.getId());
        if (previewFlagship != null) {
          indicator.setDescription(previewFlagship.getDescription());
        }
        // Indicator parent
        if (indicatorData.get("parent_id") != null) {
          IPIndicator indicatorParent = new IPIndicator();


          indicatorParent.setId(Integer.parseInt(indicatorData.get("parent_id")));
          previewFlagship = ipIndicatorManager.getIndicatorFlgship(indicatorParent.getId());
          if (previewFlagship != null) {
            indicatorParent.setDescription(previewFlagship.getDescription());
          } else {
            indicatorParent.setDescription(indicatorData.get("parent_description"));
          }

          indicator.setParent(indicatorParent);
        }

        indicators.add(indicator);
      }
      element.setIndicators(indicators);

      // Set elements 'contributesTo' if exists
      List<IPElement> elementsRelated = new ArrayList<>();
      List<Map<String, String>> elementsData =
        ipElementDAO.getIPElementsRelated(element.getId(), APConstants.ELEMENT_RELATION_CONTRIBUTION);
      for (Map<String, String> parentData : elementsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));
        elementsRelated.add(parentElement);
      }
      element.setContributesTo(elementsRelated);

      // Set elements 'translatedOf' if exists
      elementsRelated = new ArrayList<>();
      elementsData = ipElementDAO.getIPElementsRelated(element.getId(), APConstants.ELEMENT_RELATION_TRANSLATION);
      for (Map<String, String> parentData : elementsData) {
        IPElement parentElement = new IPElement();
        parentElement.setId(Integer.parseInt(parentData.get("id")));
        parentElement.setDescription(parentData.get("description"));

        // To get the indicators of the parent we need to know which program created it
        Map<String, String> programCreatorData = ipElementDAO.getElementCreator(parentElement.getId());
        IPProgram parentProgram = new IPProgram();
        parentProgram.setId(Integer.parseInt(programCreatorData.get("id")));
        parentProgram.setName(programCreatorData.get("name"));
        parentElement.setProgram(parentProgram);

        parentElement.setIndicators(ipIndicatorManager.getElementIndicators(parentElement));

        elementsRelated.add(parentElement);
      }
      element.setTranslatedOf(elementsRelated);


      elementsList.add(element);
    }

    return elementsList;
  }
}
