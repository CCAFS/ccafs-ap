package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableTypeDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableTypeManager;
import org.cgiar.ccafs.ap.data.model.DeliverableType;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class DeliverableTypeManagerImpl implements DeliverableTypeManager {

  private DeliverableTypeDAO deliverableTypeDAO;

  @Inject
  public DeliverableTypeManagerImpl(DeliverableTypeDAO deliverableTypeDAO) {
    this.deliverableTypeDAO = deliverableTypeDAO;
  }

  @Override
  public Object getDeliverableType(String id) {
    for (DeliverableType dType : getDeliverableTypes()) {
      if (Integer.parseInt(id) == dType.getId()) {
        return dType;
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

    return null;
  }
}
