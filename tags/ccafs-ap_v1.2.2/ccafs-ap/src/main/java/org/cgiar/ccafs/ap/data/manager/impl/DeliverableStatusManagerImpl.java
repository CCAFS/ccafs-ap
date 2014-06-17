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

import org.cgiar.ccafs.ap.data.dao.DeliverableStatusDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableStatusManager;
import org.cgiar.ccafs.ap.data.model.DeliverableStatus;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DeliverableStatusManagerImpl implements DeliverableStatusManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(DeliverableStatusManagerImpl.class);
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

    LOG.warn("Deliverable status list loaded is empty.");
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
