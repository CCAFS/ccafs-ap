package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.CaseStudyTypeDAO;
import org.cgiar.ccafs.ap.data.manager.CaseStudyTypeManager;
import org.cgiar.ccafs.ap.data.model.CaseStudy;
import org.cgiar.ccafs.ap.data.model.CaseStudyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CaseStudyTypeManagerImpl implements CaseStudyTypeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(CaseStudyTypeManagerImpl.class);
  private CaseStudyTypeDAO caseStudyTypeDAO;

  @Inject
  public CaseStudyTypeManagerImpl(CaseStudyTypeDAO caseStudyTypeDAO) {
    this.caseStudyTypeDAO = caseStudyTypeDAO;
  }

  @Override
  public List<CaseStudyType> getCaseStudyTypeList(String[] ids) {
    List<CaseStudyType> caseStudyTypes = new ArrayList<>();
    for (CaseStudyType cst : getCaseStudyTypes()) {
      for (String id : ids) {
        if (cst.getId() == Integer.valueOf(id)) {
          caseStudyTypes.add(cst);
        }
      }
    }

    return caseStudyTypes;
  }

  @Override
  public CaseStudyType[] getCaseStudyTypes() {
    List<Map<String, String>> caseStudyTypeList = caseStudyTypeDAO.getCaseStudyTypes();
    CaseStudyType[] caseStudyTypes = new CaseStudyType[caseStudyTypeList.size()];
    int c = 0;
    for (Map<String, String> caseStudyType : caseStudyTypeList) {
      caseStudyTypes[c] = new CaseStudyType(Integer.parseInt(caseStudyType.get("id")));
      caseStudyTypes[c].setName(caseStudyType.get("name"));
      c++;
    }

    if (caseStudyTypeList.size() > 0) {
      return caseStudyTypes;
    }
    return null;
  }

  @Override
  public List<CaseStudyType> getCaseStudyTypes(CaseStudy caseStudy) {
    List<Map<String, String>> caseStudyTypeList = caseStudyTypeDAO.getCaseStudyTypes(caseStudy.getId());
    List<CaseStudyType> caseStudyTypes = new ArrayList<>();

    for (Map<String, String> caseStudyType : caseStudyTypeList) {
      CaseStudyType caseStudyTypeTemp = new CaseStudyType(Integer.parseInt(caseStudyType.get("id")));
      caseStudyTypeTemp.setName(caseStudyType.get("name"));
      caseStudyTypes.add(caseStudyTypeTemp);
    }

    return caseStudyTypes;
  }

  @Override
  public boolean saveCaseStudyTypes(CaseStudy caseStudy) {
    List<CaseStudyType> caseStudyTypes = caseStudy.getTypes();
    int[] caseStudyTypeIds = new int[caseStudyTypes.size()];

    for (int c = 0; c < caseStudyTypes.size(); c++) {
      caseStudyTypeIds[c] = caseStudyTypes.get(c).getId();
    }
    return caseStudyTypeDAO.saveCaseStudyTypes(caseStudy.getId(), caseStudyTypeIds);
  }

}