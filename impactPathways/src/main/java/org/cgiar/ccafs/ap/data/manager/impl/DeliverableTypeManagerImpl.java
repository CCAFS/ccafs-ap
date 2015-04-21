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

import org.cgiar.ccafs.ap.data.dao.DeliverableTypeDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

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
public class DeliverableTypeManagerImpl implements DeliverableTypeManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(DeliverableTypeManagerImpl.class);

  // DAO's
  private DeliverableTypeDAO deliverableTypeDAO;

  // Managers
  private DeliverableTypeManager deliverableTypeManager;


  @Inject
  public DeliverableTypeManagerImpl(DeliverableTypeDAO deliverableTypeDAO, DeliverableTypeManager deliverableTypeManager) {
    this.deliverableTypeDAO = deliverableTypeDAO;
    this.deliverableTypeManager = deliverableTypeManager;
  }

  @Override
  public boolean deleteDeliverableType(int deliverableId) {
    return deliverableTypeDAO.deleteDeliverableType(deliverableId);
  }

  @Override
  public List<DeliverableType> getDeliverableSubTypes() {
    List<DeliverableType> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableTypeDataList = deliverableTypeDAO.getDeliverableSubTypes();
    for (Map<String, String> deliverableTypeData : deliverableTypeDataList) {
      DeliverableType deliverableType = new DeliverableType();
      deliverableType.setId(Integer.parseInt(deliverableTypeData.get("id")));
      deliverableType.setName(deliverableTypeData.get("name"));
      if (deliverableTypeData.get("parent_id") != null) {
        deliverableType.setCategory(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableTypeData
          .get("parent_id"))));
      }
      if (deliverableTypeData.get("timeline") != null) {
        deliverableType.setTimeline(Integer.parseInt(deliverableTypeData.get("timeline")));
      }
      // adding information of the object to the array
      deliverableList.add(deliverableType);
    }
    return deliverableList;
  }

  @Override
  public DeliverableType getDeliverableTypeById(int deliverableTypeID) {
    Map<String, String> deliverableTypeData = deliverableTypeDAO.getDeliverableTypeById(deliverableTypeID);
    if (!deliverableTypeData.isEmpty()) {
      DeliverableType deliverableType = new DeliverableType();
      deliverableType.setId(deliverableTypeID);
      deliverableType.setName(deliverableTypeData.get("name"));
      if (deliverableTypeData.get("parent_id") != null) {
        deliverableType.setCategory(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableTypeData
          .get("parent_id"))));
      }
      if (deliverableTypeData.get("timeline") != null) {
        deliverableType.setTimeline(Integer.parseInt(deliverableTypeData.get("timeline")));
      }

      return deliverableType;
    }
    return null;
  }

  @Override
  public List<DeliverableType> getDeliverableTypes() {
    List<DeliverableType> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableTypeDataList = deliverableTypeDAO.getDeliverableTypes();
    for (Map<String, String> deliverableTypeData : deliverableTypeDataList) {
      DeliverableType deliverableType = new DeliverableType();
      deliverableType.setId(Integer.parseInt(deliverableTypeData.get("id")));
      deliverableType.setName(deliverableTypeData.get("name"));
      if (deliverableTypeData.get("timeline") != null) {
        deliverableType.setTimeline(Integer.parseInt(deliverableTypeData.get("timeline")));
      }
      // adding information of the object to the array
      deliverableList.add(deliverableType);
    }
    return deliverableList;
  }

  @Override
  public List<DeliverableType> getDeliverableTypes(int typeID) {
    List<DeliverableType> deliverableList = new ArrayList<>();
    List<Map<String, String>> deliverableTypeDataList = deliverableTypeDAO.getDeliverableTypes(typeID);
    for (Map<String, String> deliverableTypeData : deliverableTypeDataList) {
      DeliverableType deliverableType = new DeliverableType();
      deliverableType.setId(Integer.parseInt(deliverableTypeData.get("id")));
      deliverableType.setName(deliverableTypeData.get("name"));
      if (deliverableTypeData.get("parent_id") != null) {
        deliverableType.setCategory(deliverableTypeManager.getDeliverableTypeById(Integer.parseInt(deliverableTypeData
          .get("parent_id"))));
      }
      if (deliverableTypeData.get("timeline") != null) {
        deliverableType.setTimeline(Integer.parseInt(deliverableTypeData.get("timeline")));
      }
      // adding information of the object to the array
      deliverableList.add(deliverableType);
    }
    return deliverableList;
  }

  @Override
  public boolean saveDeliverableType(DeliverableType deliverableType) {
    boolean allSaved = true;
    Map<String, Object> deliverableTypeData = new HashMap<>();
    if (deliverableType.getId() > 0) {
      deliverableTypeData.put("id", deliverableType.getId());
    }
    deliverableTypeData.put("name", deliverableType.getName());
    deliverableTypeData.put("parent_id", deliverableType.getCategory().getId());
    deliverableTypeData.put("timeline", deliverableType.getTimeline());

    int result = deliverableTypeDAO.saveDeliverableType(deliverableTypeData);

    if (result > 0) {
      LOG.debug("saveDeliverableType > New Deliverable Typeadded with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveDeliverableType > Deliverable Type with id={} was updated", deliverableType.getId());
    } else {
      LOG.error("saveDeliverableType > There was an error trying to save/update a Deliverable Type ");
      allSaved = false;
    }

    return allSaved;

  }
}
