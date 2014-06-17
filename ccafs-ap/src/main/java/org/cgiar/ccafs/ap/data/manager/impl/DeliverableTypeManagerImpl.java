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

    LOG.warn("Deliverable type list loaded id empty.");
    return null;
  }
}
