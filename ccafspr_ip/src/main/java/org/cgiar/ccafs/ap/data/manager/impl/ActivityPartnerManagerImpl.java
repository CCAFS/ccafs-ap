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

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 */
public class ActivityPartnerManagerImpl implements ActivityPartnerManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityPartnerManagerImpl.class);

  // DAO's
  private ActivityPartnerDAO activityPartnerDAO;

  // Managers
  private InstitutionManager institutionManager;


  @Inject
  public ActivityPartnerManagerImpl(ActivityPartnerDAO activityPartnerDAO, InstitutionManager institutionManager) {
    this.activityPartnerDAO = activityPartnerDAO;
    this.institutionManager = institutionManager;
  }

  @Override
  public boolean deleteActivityPartner(int activityPartnerId) {
    return activityPartnerDAO.deleteActivityPartner(activityPartnerId);
  }

  @Override
  public boolean deleteActivityPartnersByActivityId(int activityID) {
    return activityPartnerDAO.deleteActivityPartnerByActivityId(activityID);
  }

  @Override
  public ActivityPartner getActivityPartnerById(int activityPartnerID) {
    Map<String, String> activityPartnerData = activityPartnerDAO.getActivityPartnerById(activityPartnerID);
    if (!activityPartnerData.isEmpty()) {
      ActivityPartner activityPartner = new ActivityPartner();
      activityPartner.setId(Integer.parseInt(activityPartnerData.get("id")));
      activityPartner.setPartner(institutionManager.getInstitution(Integer.parseInt(activityPartnerData
        .get("institution_id"))));
      activityPartner.setContactName(activityPartnerData.get("contact_name"));
      activityPartner.setContactEmail(activityPartnerData.get("contact_email"));
      activityPartner.setContribution(activityPartnerData.get("contribution"));
      return activityPartner;
    }
    return null;
  }

  @Override
  public List<ActivityPartner> getActivityPartnersByActivity(int activityID) {
    List<ActivityPartner> activityPartnerList = new ArrayList<>();
    List<Map<String, String>> activityPartnerDataList = activityPartnerDAO.getActivityPartnersByActivity(activityID);
    for (Map<String, String> activityPartnerData : activityPartnerDataList) {
      ActivityPartner activityPartner = new ActivityPartner();
      activityPartner.setId(Integer.parseInt(activityPartnerData.get("id")));
      activityPartner.setPartner(institutionManager.getInstitution(Integer.parseInt(activityPartnerData
        .get("institution_id"))));
      activityPartner.setContactName(activityPartnerData.get("contact_name"));
      activityPartner.setContactEmail(activityPartnerData.get("contact_email"));
      activityPartner.setContribution(activityPartnerData.get("contribution"));

      // adding information of the object to the array
      activityPartnerList.add(activityPartner);
    }
    return activityPartnerList;
  }

  @Override
  public boolean saveActivityPartner(int activityID, ActivityPartner activityPartner) {
    boolean allSaved = true;
    Map<String, Object> activityPartnerData = new HashMap<>();
    if (activityPartner.getId() > 0) {
      activityPartnerData.put("id", activityPartner.getId());
    }
    activityPartnerData.put("institution_id", activityPartner.getPartner().getId());
    activityPartnerData.put("activity_id", activityID);
    activityPartnerData.put("contact_name", activityPartner.getContactName());
    activityPartnerData.put("contact_email", activityPartner.getContactEmail());
    activityPartnerData.put("contribution", activityPartner.getContribution());

    int result = activityPartnerDAO.saveActivityPartner(activityID, activityPartnerData);

    if (result > 0) {
      LOG.debug("saveActivity > New Activity Partner added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveActivity > Activity Partner with id={} was updated", activityPartner.getId());
    } else {
      LOG.error("saveActivity > There was an error trying to save/update an Activity Partner from activityId={}",
        activityID);
      allSaved = false;
    }

    return allSaved;

  }
}
