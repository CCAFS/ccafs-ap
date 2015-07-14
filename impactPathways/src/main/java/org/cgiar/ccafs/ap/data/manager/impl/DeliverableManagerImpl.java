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
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
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
  public boolean deleteDeliverableOutput(int deliverableID) {
    return deliverableDAO.deleteDeliverableOutput(deliverableID);
  }


  @Override
  public boolean deleteDeliverablesByProject(int projectID) {
    return deliverableDAO.deleteDeliverablesByProject(projectID);
  }

  @Override
  public boolean existDeliverable(int deliverableID) {
    return deliverableDAO.existDeliverable(deliverableID);
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
      deliverable.setTypeOther(deliverableData.get("type_other"));
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(deliverableID));
      deliverable.setOutput(this.getDeliverableOutput(deliverableID));
      deliverable.setCreated(Long.parseLong(deliverableData.get("active_since")));
      return deliverable;
    }
    return null;
  }

  @Override
  public IPElement getDeliverableOutput(int deliverableID) {
    IPElement deliverableOutput = new IPElement();
    Map<String, String> deliverableOutputData = deliverableDAO.getDeliverableOutput(deliverableID);
    if (!deliverableOutputData.isEmpty()) {
      deliverableOutput.setId(Integer.parseInt(deliverableOutputData.get("id")));
      deliverableOutput.setDescription(deliverableOutputData.get("description"));
      return deliverableOutput;
    }
    return null;
  }

  @Override
  public List<Deliverable> getDeliverablesByProject(int projectID) {
    List<Deliverable> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableDataList = deliverableDAO.getDeliverablesByProject(projectID);
    for (Map<String, String> deliverableData : deliverableDataList) {
      Deliverable deliverable = new Deliverable();
      deliverable.setId(Integer.parseInt(deliverableData.get("id")));
      deliverable.setTitle(deliverableData.get("title"));
      deliverable.setYear(Integer.parseInt(deliverableData.get("year")));
      deliverable
      .setType(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableData.get("type_id"))));
      deliverable.setTypeOther(deliverableData.get("type_other"));
      deliverable.setNextUsers(nextUserManager.getNextUsersByDeliverableId(projectID));
      deliverable.setOutput(this.getDeliverableOutput(Integer.parseInt(deliverableData.get("id"))));
      deliverable.setCreated(Long.parseLong(deliverableData.get("active_since")));
      // adding information of the object to the array
      deliverableList.add(deliverable);
    }
    return deliverableList;
  }

  @Override
  public int saveDeliverable(int projectID, Deliverable deliverable, User user, String justification) {
    Map<String, Object> deliverableData = new HashMap<>();
    if (deliverable.getId() != -1) {
      deliverableData.put("id", deliverable.getId());
    } else {
      deliverableData.put("id", null);
      deliverableData.put("created_by", user.getId());
    }
    deliverableData.put("project_id", projectID);
    deliverableData.put("title", deliverable.getTitle());
    deliverableData.put("type_id", deliverable.getType().getId());
    deliverableData.put("type_other", deliverable.getTypeOther());
    deliverableData.put("year", deliverable.getYear());
    // Logs
    deliverableData.put("modified_by", user.getId());
    deliverableData.put("modification_justification", justification);


    int result = deliverableDAO.saveDeliverable(deliverableData);

    if (result > 0) {
      LOG.debug("saveDeliverable > New Deliverable added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveDeliverable > Deliverable with id={} was updated", deliverable.getId());
    } else {
      LOG.error("saveDeliverable > There was an error trying to save/update a Deliverable from projectId={}",
        projectID);
    }

    return result;

  }

  @Override
  public boolean saveDeliverableOutput(int deliverableID, int projectID, User user, String justification) {
    return deliverableDAO.saveDeliverableOutput(deliverableID, projectID, user.getId(), justification);
  }
}
