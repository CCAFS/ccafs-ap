package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableTypeDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

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
  public DeliverableType[] getActiveDeliverableTypes() {
    List<Map<String, String>> deliverableTypesList = deliverableTypeDAO.getActiveDeliverableTypes();
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

    LOG.warn("Deliverable type list loaded id empty.");
    return null;
  }

  @Override
  public DeliverableType[] getAllDeliverableTypes() {
    List<Map<String, String>> deliverableTypesList = deliverableTypeDAO.getAllDeliverableTypes();
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

    LOG.warn("Deliverable type list loaded id empty.");
    return null;
  }

  @Override
  public Object getDeliverableType(String id) {
    for (DeliverableType dType : getAllDeliverableTypes()) {
      if (Integer.parseInt(id) == dType.getId()) {
        return dType;
      }
    }
    return null;
  }
}
