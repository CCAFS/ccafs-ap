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

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.dao.PartnerDAO;
import org.cgiar.ccafs.ap.data.manager.PartnerManager;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Partner;
import org.cgiar.ccafs.ap.data.model.PartnerType;
import org.cgiar.ccafs.ap.data.model.Region;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PartnerManagerImpl implements PartnerManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(PartnerManagerImpl.class);
  private PartnerDAO partnerDAO;

  @Inject
  public PartnerManagerImpl(PartnerDAO partnerDAO, ActivityPartnerDAO activityPartnerDAO) {
    this.partnerDAO = partnerDAO;
  }

  @Override
  public Partner[] getAllPartners() {
    List<Map<String, String>> partnerList = partnerDAO.getAllPartners();
    Partner[] partners = new Partner[partnerList.size()];
    for (int c = 0; c < partners.length; c++) {
      Map<String, String> partnerData = partnerList.get(c);
      partners[c] = new Partner();
      partners[c].setId(Integer.parseInt(partnerData.get("id")));
      partners[c].setAcronym(partnerData.get("acronym"));
      partners[c].setName(partnerData.get("name"));
      // Partner Type
      PartnerType partnerType = new PartnerType();
      partnerType.setId(Integer.parseInt(partnerData.get("partner_type_id")));
      partnerType.setName(partnerData.get("partner_type_acronym"));
      partners[c].setType(partnerType);

    }
    if (partnerList.size() == 0) {
      LOG.warn("-- getAllPartners() > Partner list loaded is empty");
      return null;
    }
    return partners;
  }


  @Override
  public Partner getPartner(int id) {
    Map<String, String> partnerData = partnerDAO.getPartner(id);
    if (partnerData != null) {
      Partner partner = new Partner();
      partner.setId(id);
      partner.setAcronym(partnerData.get("acronym"));
      partner.setName(partnerData.get("name"));
      return partner;
    }
    LOG.warn("-- getPartner() > Partner identified by {} wasn't found", id);
    return null;
  }

  @Override
  public Partner[] getPartnerForXML() {
    List<Map<String, String>> partnerList = partnerDAO.getPartnersForXML();
    Partner[] partners = new Partner[partnerList.size()];
    for (int c = 0; c < partners.length; c++) {
      Map<String, String> partnerData = partnerList.get(c);
      partners[c] = new Partner();
      partners[c].setId(Integer.parseInt(partnerData.get("id")));
      partners[c].setAcronym(partnerData.get("acronym"));
      partners[c].setName(partnerData.get("name"));
      partners[c].setCity(partnerData.get("city"));
      partners[c].setWebsite(partnerData.get("website"));
      // Partner Type
      PartnerType partnerType = new PartnerType();
      partnerType.setId(Integer.parseInt(partnerData.get("partner_type_id")));
      partnerType.setAcronym(partnerData.get("partner_type_acronym"));
      partnerType.setName(partnerData.get("partner_type_name"));
      partners[c].setType(partnerType);

      // region
      Region region = new Region();
      region.setId(Integer.parseInt(partnerData.get("region_id")));
      region.setName(partnerData.get("region_name"));

      // Country
      Country country = new Country();
      country.setId(partnerData.get("country_iso2"));
      country.setName(partnerData.get("country_name"));
      country.setRegion(region);

      partners[c].setCountry(country);
    }
    if (partnerList.size() == 0) {
      LOG.warn("-- getPartnerForXML() > Partner list loaded is empty");
      return null;
    }
    return partners;
  }

  @Override
  public Partner[] getPartnersByFilter(String countryID, String partnerTypeID) {

    // Check that at least exists one filter
    if (countryID.isEmpty() && partnerTypeID.isEmpty()) {
      // If there aren't conditions return all partners
      return getAllPartners();
    }

    List<Map<String, String>> partnerList = partnerDAO.getPartnersByFilter(countryID, partnerTypeID);
    Partner[] partners = new Partner[partnerList.size()];
    for (int c = 0; c < partners.length; c++) {
      Map<String, String> partnerData = partnerList.get(c);
      partners[c] = new Partner();
      partners[c].setId(Integer.parseInt(partnerData.get("id")));
      partners[c].setAcronym(partnerData.get("acronym"));
      partners[c].setName(partnerData.get("name"));
      // Partner Type
      PartnerType partnerType = new PartnerType();
      partnerType.setId(Integer.parseInt(partnerData.get("partner_type_id")));
      partnerType.setName(partnerData.get("partner_type_acronym"));
      partners[c].setType(partnerType);

    }
    if (partnerList.size() == 0) {
      LOG.warn("-- getPartnersByFilter() > Partner list loaded is empty");
      return null;
    }
    return partners;
  }
}
