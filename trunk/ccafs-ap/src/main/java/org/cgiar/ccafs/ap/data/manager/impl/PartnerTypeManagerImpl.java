package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.cgiar.ccafs.ap.data.dao.PartnerTypeDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.PartnerType;


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
      partnerTypeList[c] = new PartnerType(Integer.parseInt(partnerTypeData.get("id")), partnerTypeData.get("acronym"));
    }
    if (partnerTypeDataList.size() > 0) {
      return partnerTypeList;
    }
    return null;
  }

}
