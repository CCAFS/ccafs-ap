package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableStatusDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableStatusManager;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class DeliverableStatusManagerImpl implements DeliverableStatusManager {

  private DeliverableStatusDAO deliverableStatusDAO;

  @Inject
  public DeliverableStatusManagerImpl(DeliverableStatusDAO deliverableStatusDAO) {
    this.deliverableStatusDAO = deliverableStatusDAO;
  }

  @Override
  public DeliverableStatus[] getDeliverableStatus() {
    List<Map<String, String>> deliverableStatusList = deliverableStatusDAO.getDeliverableStatus();
    Map<String, String> deliverableStatusData;

    DeliverableStatus[] deliverableStatus = new DeliverableStatus[deliverableStatusList.size()];
    for (int c = 0; c < deliverableStatusList.size(); c++) {
      deliverableStatusData = deliverableStatusList.get(c);
      deliverableStatus[c] =
        new DeliverableStatus(Integer.parseInt(deliverableStatusData.get("id")), deliverableStatusData.get("name"));
    }

    if (deliverableStatusList.size() > 0) {
      return deliverableStatus;
    }

    return null;
  }

  @Override
  public DeliverableStatus getDeliverableStatus(String id) {
    for (DeliverableStatus deliverableStatus : getDeliverableStatus()) {
      if (deliverableStatus.getId() == Integer.parseInt(id)) {
        return deliverableStatus;
      }
    }
    return null;
  }

  @Override
  public boolean setDeliverableStatus(int id, DeliverableStatus status) {
    return deliverableStatusDAO.setDeliverableStatus(id, status.getId());
  }
}
