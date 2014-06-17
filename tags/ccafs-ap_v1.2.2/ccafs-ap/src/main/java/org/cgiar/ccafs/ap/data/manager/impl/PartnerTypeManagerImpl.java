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

import org.cgiar.ccafs.ap.data.dao.PartnerTypeDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerTypeManager;
import org.cgiar.ccafs.ap.data.model.PartnerType;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnerTypeManagerImpl implements PartnerTypeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnerTypeManagerImpl.class);
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
      partnerTypeList[c].setAcronym(partnerTypeData.get("description"));
    }
    if (partnerTypeDataList.size() > 0) {
      return partnerTypeList;
    }
    LOG.warn("Partner type list loaded is empty");
    return null;
  }

}
