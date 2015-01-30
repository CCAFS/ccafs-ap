package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableTypeDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverableTypeManagerImpl implements DeliverableTypeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(DeliverableTypeManagerImpl.class);
  private DeliverableTypeDAO deliverableTypeDAO;

  @Inject
  public DeliverableTypeManagerImpl(DeliverableTypeDAO deliverableTypeDAO) {
    this.deliverableTypeDAO = deliverableTypeDAO;
  }

  @Override
  public DeliverableType[] getDeliverableSubTypes() {
    List<Map<String, String>> deliverableTypesList = deliverableTypeDAO.getDeliverableSubTypes();
    Map<String, String> deliverableTypeData;

    DeliverableType[] deliverableTypes = new DeliverableType[deliverableTypesList.size()];
    for (int c = 0; c < deliverableTypesList.size(); c++) {
      deliverableTypeData = deliverableTypesList.get(c);

      DeliverableType type = new DeliverableType();
      type.setId(Integer.parseInt(deliverableTypeData.get("id")));
      type.setName(deliverableTypeData.get("name"));

      DeliverableType parent = new DeliverableType();
      parent.setId(Integer.parseInt(deliverableTypeData.get("parent_id")));
      type.setParent(parent);

      deliverableTypes[c] = type;
    }

    return deliverableTypes;
  }

  @Override
  public DeliverableType[] getDeliverableSubTypesByType(int deliverableTypeID) {
    List<DeliverableType> types = new ArrayList<>();

    for (DeliverableType dType : getDeliverableTypesAndSubtypes()) {
      if (dType.getParent().getId() == deliverableTypeID) {
        types.add(dType);
      }
    }

    return types.toArray(new DeliverableType[types.size()]);
  }

  @Override
  public DeliverableType getDeliverableType(String id) {
    for (DeliverableType dType : getDeliverableTypesAndSubtypes()) {
      if (Integer.parseInt(id) == dType.getId()) {
        return dType;
      }
    }
    return null;
  }

  @Override
  public DeliverableType getDeliverableTypeBySubType(int deliverableSubTypeID) {
    for (DeliverableType dType : getDeliverableTypesAndSubtypes()) {
      if (deliverableSubTypeID == dType.getId()) {
        if (dType.getParent() != null) {
          return dType.getParent();
        }
      }
    }
    return null;
  }

  @Override
  public DeliverableType[] getDeliverableTypes() {
    List<Map<String, String>> deliverableTypesList = deliverableTypeDAO.getDeliverableTypes();
    Map<String, String> deliverableTypeData;

    DeliverableType[] deliverableTypes = new DeliverableType[deliverableTypesList.size()];
    for (int c = 0; c < deliverableTypesList.size(); c++) {
      deliverableTypeData = deliverableTypesList.get(c);
      deliverableTypes[c] =
        new DeliverableType(Integer.parseInt(deliverableTypeData.get("id")), deliverableTypeData.get("name"));
    }

    if (deliverableTypesList.size() > 0) {
      return deliverableTypes;
    }

    LOG.warn("Product type list loaded id empty.");
    return null;
  }

  @Override
  public DeliverableType[] getDeliverableTypesAndSubtypes() {
    List<Map<String, String>> deliverableTypesList = deliverableTypeDAO.getDeliverableTypesAndSubTypes();
    Map<String, String> deliverableTypeData;

    DeliverableType[] deliverableTypes = new DeliverableType[deliverableTypesList.size()];
    for (int c = 0; c < deliverableTypesList.size(); c++) {
      deliverableTypeData = deliverableTypesList.get(c);

      DeliverableType type = new DeliverableType();
      type.setId(Integer.parseInt(deliverableTypeData.get("id")));
      type.setName(deliverableTypeData.get("name"));

      DeliverableType parent = new DeliverableType();
      parent.setId(Integer.parseInt(deliverableTypeData.get("parent_id")));
      parent.setName(deliverableTypeData.get("parent_name"));
      type.setParent(parent);

      deliverableTypes[c] = type;
    }

    return deliverableTypes;
  }
}
