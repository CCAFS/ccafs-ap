/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverablePartnerDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverablePartnerManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.DeliverablePartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Héctor Fabio Tobón R.
 */
public class DeliverablePartnerManagerImpl implements DeliverablePartnerManager {

  // LOG
  public static Logger LOG = LoggerFactory.getLogger(DeliverablePartnerManagerImpl.class);

  // DAO's
  private DeliverablePartnerDAO deliverablePartnerDAO;

  // Managers
  private InstitutionManager institutionManager;
  private UserManager userManager;

  @Inject
  public DeliverablePartnerManagerImpl(DeliverablePartnerDAO deliverablePartnerDAO,
    InstitutionManager institutionManager, UserManager userManager) {
    this.deliverablePartnerDAO = deliverablePartnerDAO;
    this.institutionManager = institutionManager;
    this.userManager = userManager;
  }

  @Override
  public boolean deleteDeliverablePartner(int id, User user, String justification) {
    return deliverablePartnerDAO.deleteDeliverablePartner(id, user.getId(), justification);
  }

  @Override
  public boolean deleteDeliverablePartnerByDeliverable(int deliverableID, User user, String justification) {
    boolean problem = false;
    boolean deleted;
    for (DeliverablePartner partner : this.getDeliverablePartners(deliverableID)) {
      deleted = deliverablePartnerDAO.deleteDeliverablePartner(partner.getId(), user.getId(), justification);
      if (!deleted) {
        problem = true;
      }
    }
    return !problem;
  }

  @Override
  public List<DeliverablePartner> getDeliverablePartners(int deliverableID) {
    List<DeliverablePartner> deliverablePartners = new ArrayList<>();
    List<Map<String, String>> deliverablePartnerDataList = deliverablePartnerDAO.getDeliverablePartners(deliverableID);
    for (Map<String, String> dData : deliverablePartnerDataList) {
      DeliverablePartner deliverablePartner = new DeliverablePartner();
      deliverablePartner.setId(Integer.parseInt(dData.get("id")));
      // Partner type (PPA, PL, PP, etc.)
      deliverablePartner.setType(dData.get("partner_type"));
      // User as user_id
      deliverablePartner.setUser(userManager.getUser(Integer.parseInt(dData.get("user_id"))));
      // Institution as partner_id
      deliverablePartner
      .setInstitution(institutionManager.getInstitution(Integer.parseInt(dData.get("institution_id"))));
      // adding information of the object to the array
      deliverablePartners.add(deliverablePartner);
    }
    return deliverablePartners;
  }

  @Override
  public List<DeliverablePartner> getDeliverablePartners(int deliverableID, String deliverablePartnerType) {
    List<DeliverablePartner> deliverablePartners = new ArrayList<>();
    List<Map<String, String>> deliverablePartnerDataList =
      deliverablePartnerDAO.getDeliverablePartners(deliverableID, deliverablePartnerType);
    for (Map<String, String> dData : deliverablePartnerDataList) {
      DeliverablePartner deliverablePartner = new DeliverablePartner();
      deliverablePartner.setId(Integer.parseInt(dData.get("id")));
      // Partner type (Resp, Other)
      deliverablePartner.setType(dData.get("partner_type"));
      // User as user_id
      if (dData.get("user_id") != null) {
        deliverablePartner.setUser(userManager.getUser(Integer.parseInt(dData.get("user_id"))));
      } else {
        User user = new User();
        user.setId(-1);
        deliverablePartner.setUser(user);
      }

      // Institution as partner_id
      deliverablePartner
        .setInstitution(institutionManager.getInstitution(Integer.parseInt(dData.get("institution_id"))));

      // adding information of the object to the array
      deliverablePartners.add(deliverablePartner);
    }
    return deliverablePartners;
  }

  @Override
  public int saveDeliverablePartner(int deliverableID, DeliverablePartner deliverablePartner, User user,
    String justification) {
    Map<String, Object> deliverablePartnerData = new HashMap<>();

    // Deliverable partners must have an institution associated.
    if (deliverablePartner.getInstitution() == null || deliverablePartner.getInstitution().getId() == -1) {
      return -1;
    }

    // if this is a new deliverable partner, do not assign an id.
    if (deliverablePartner.getId() > 0) {
      deliverablePartnerData.put("id", deliverablePartner.getId());
    } else {
      // otherwise will be a new record so we need to include the creator.
      deliverablePartnerData.put("created_by", user.getId());
    }
    deliverablePartnerData.put("deliverable_id", deliverableID);
    deliverablePartnerData.put("institution_id", deliverablePartner.getInstitution().getId());
    deliverablePartnerData.put("user_id",
      deliverablePartner.getUser().getId() == -1 ? null : deliverablePartner.getUser().getId());
    deliverablePartnerData.put("partner_type", deliverablePartner.getType());
    // Logs data
    deliverablePartnerData.put("modified_by", user.getId());
    deliverablePartnerData.put("modification_justification", justification);

    int result = deliverablePartnerDAO.saveDeliverablePartner(deliverablePartnerData);
    if (result > 0) {
      LOG.debug("saveDeliverablePartner > New Deliverable Partner added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveDeliverablePartner > Deliverable partner with id={} was updated", deliverablePartner.getId());
    } else {
      LOG.error(
        "saveDeliverablePartner > There was an error trying to save/update a deliverable partner from deliverableID={}",
        deliverableID);
    }

    return result;
  }

  @Override
  public boolean saveDeliverablePartners(int deliverableID, List<DeliverablePartner> deliverablePartners, User user,
    String justification) {
    boolean allSaved = true;
    int result;
    for (DeliverablePartner deliverablePartner : deliverablePartners) {
      result = this.saveDeliverablePartner(deliverableID, deliverablePartner, user, justification);
      if (result == -1) {
        allSaved = false;
      }
    }
    return allSaved;
  }

}
