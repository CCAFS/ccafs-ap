package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.PartnerTypeDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.PartnerType;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class PartnerTypeManagerImpl implements PartnerTypeManager {

  private PartnerTypeDAO partnerTypeDAO;

  @Inject
  public PartnerTypeManagerImpl(PartnerTypeDAO partnerTypeDAO) {
    this.partnerTypeDAO = partnerTypeDAO;
  }

  @Override
  public PartnerType[] getPartnerTypeList() {
    List<Map<String, String>> partnerTypeDataList = partnerTypeDAO.getPartnerTypeList();

    if (partnerTypeDataList == null) {
      return null;
    }

    Map<String, String> partnerTypeData;
    PartnerType[] partnerTypeList = new PartnerType[partnerTypeDataList.size()];
    for (int c = 0; c < partnerTypeDataList.size(); c++) {
      partnerTypeData = partnerTypeDataList.get(c);
      partnerTypeList[c] = new PartnerType();
      partnerTypeList[c].setId(Integer.parseInt(partnerTypeData.get("id")));
      partnerTypeList[c].setName(partnerTypeData.get("name"));
      partnerTypeList[c].setAcronym(partnerTypeData.get("acronym"));
    }
    if (partnerTypeDataList.size() > 0) {
      return partnerTypeList;
    }
    return null;
  }

}
