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

import org.cgiar.ccafs.ap.data.dao.DeliverableDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableManager;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.manager.NextUserManager;
import org.cgiar.ccafs.ap.data.model.Deliverable;
import org.cgiar.ccafs.ap.data.model.IPElement;

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
public class DeliverableManagerImpl implements DeliverableManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(DeliverableManagerImpl.class);

  // DAO's
  private DeliverableDAO deliverableDAO;

  // Managers
  private DeliverableTypeManager deliverableTypeManager;
  private NextUserManager nextUserManager;


  @Inject
  public DeliverableManagerImpl(DeliverableDAO deliverableDAO, DeliverableTypeManager deliverableTypeManager,
    NextUserManager nextUserManager) {
    this.deliverableDAO = deliverableDAO;
    this.deliverableTypeManager = deliverableTypeManager;
    this.nextUserManager = nextUserManager;
  }

  @Override
  public boolean deleteDeliverable(int deliverableId) {
    return deliverableDAO.deleteDeliverable(deliverableId);
  }

  @Override
  public boolean deleteDeliverablesByActivity(int activityID) {
    return deliverableDAO.deleteDeliverablesByActivity(activityID);
  }


  @Override
  public Deliverable getDeliverableById(int deliverableID) {
    Map<String, String> deliverableData = deliverableDAO.getDeliverableById(deliverableID);
    if (!deliverableData.isEmpty()) {
      Deliverable deliverable = new Deliverable();
      deliverable.setId(deliverableID);
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable
        .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverableID));
      return deliverable;
    }
    return null;
  }

  @Override
  public List<IPElement> getDeliverableContributions(int deliverableID) {
    List<IPElement> deliverableContributionsList = new ArrayList<>();
    List<Map<String, String>> deliverableContributionDataList = deliverableDAO.getDeliverablesByActivity(deliverableID);
    for (Map<String, String> deliverableData : deliverableContributionDataList) {
      IPElement deliverableContributions = new IPElement();
      deliverableContributions.setId(Integer.parseInt(deliverableData.get("id")));
      deliverableContributions.setDescription(deliverableData.get("descritpion"));

      // adding information of the object to the array
      deliverableContributionsList.add(deliverableContributions);
    }
    return deliverableContributionsList;
  }

  @Override
  public List<Deliverable> getDeliverablesByActivity(int activityID) {
    List<Deliverable> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableDataList = deliverableDAO.getDeliverablesByActivity(activityID);
    for (Map<String, String> deliverableData : deliverableDataList) {
      Deliverable deliverable = new Deliverable();
      deliverable.setId(Integer.parseInt(deliverableData.get("id")));
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable
        .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(activityID));

      // adding information of the object to the array
      deliverableList.add(deliverable);
    }
    return deliverableList;
  }

  @Override
  public int saveDeliverable(int activityID, Deliverable deliverable) {
    Map<String, Object> deliverableData = new HashMap<>();
    if (deliverable.getId() > 0) {
      deliverableData.put("id", deliverable.getId());
    }
    deliverableData.put("activity_id", activityID);
    deliverableData.put("title", deliverable.getTitle());
    deliverableData.put("type_id", deliverable.getType().getId());
    deliverableData.put("year", deliverable.getYear());

    int result = deliverableDAO.saveDeliverable(activityID, deliverableData);

    if (result > 0) {
      LOG.debug("saveDeliverable > New Deliverable added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveDeliverable > Deliverable with id={} was updated", deliverable.getId());
    } else {
      LOG.error("saveDeliverable > There was an error trying to save/update a Deliverable from projectId={}",
        activityID);
    }

    return result;

  }
}
