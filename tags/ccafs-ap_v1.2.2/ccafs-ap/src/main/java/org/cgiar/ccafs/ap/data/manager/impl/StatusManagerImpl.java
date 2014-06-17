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

import org.cgiar.ccafs.ap.data.dao.StatusDAO;
import org.cgiar.ccafs.ap.data.manager.StatusManager;
import org.cgiar.ccafs.ap.data.model.Status;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StatusManagerImpl implements StatusManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(StatusManagerImpl.class);
  private StatusDAO statusDAO;

  @Inject
  public StatusManagerImpl(StatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @Override
  public Status getStatus(String id) {
    for (Status st : getStatusList()) {
      if (st.getId() == Integer.parseInt(id)) {
        return st;
      }
    }
    return null;
  }

  @Override
  public Status[] getStatusList() {
    List<Map<String, String>> statusDataList = statusDAO.getStatusList();
    Map<String, String> statusData;
    Status[] statusList = new Status[statusDataList.size()];
    for (int c = 0; c < statusDataList.size(); c++) {
      statusData = statusDataList.get(c);
      statusList[c] = new Status(Integer.parseInt(statusData.get("id")), statusData.get("name"));
    }
    if (statusDataList.size() > 0) {
      return statusList;
    }
    LOG.warn("Activity status list loaded is empty");
    return null;
  }
}
