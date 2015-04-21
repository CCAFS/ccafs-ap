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

import org.cgiar.ccafs.ap.data.dao.IPCrossCuttingDAO;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;

/**
 * This class represents the Manager of IP Cross Cutting Theme
 * 
 * @author Javier Andrés Gallego B.
 */
public class IPCrossCuttingManagerImpl implements IPCrossCuttingManager {

  // DAOs
  private IPCrossCuttingDAO ipCrossCuttingDAO;

  @Inject
  public IPCrossCuttingManagerImpl(IPCrossCuttingDAO ipCrossCuttingDAO) {
    this.ipCrossCuttingDAO = ipCrossCuttingDAO;
  }

  @Override
  public boolean deleteCrossCuttingByActivity(int activityID, int crossCuttingID) {
    return ipCrossCuttingDAO.deleteCrossCuttingByActivityId(activityID, crossCuttingID);
  }

  @Override
  public boolean deleteCrossCuttingsByActivity(int activityID) {
    return ipCrossCuttingDAO.deleteCrossCuttingsByActivityId(activityID);
  }

  @Override
  public IPCrossCutting getIPCrossCutting(int ipCrossCuttingID) {
    Map<String, String> ipCrossCuttingData = ipCrossCuttingDAO.getIPCrossCutting(ipCrossCuttingID);
    if (!ipCrossCuttingData.isEmpty()) {
      IPCrossCutting ipCrossCutting = new IPCrossCutting();
      ipCrossCutting.setId(Integer.parseInt(ipCrossCuttingData.get("id")));
      ipCrossCutting.setName(ipCrossCuttingData.get("name"));

      return ipCrossCutting;
    }
    return null;
  }

  @Override
  public List<IPCrossCutting> getIPCrossCuttingByActivityID(int activityID) {
    List<IPCrossCutting> ipCrossCuttings = new ArrayList<>();
    List<Map<String, String>> ipCrossCuttingList = ipCrossCuttingDAO.getIPCrossCuttingByActivityId(activityID);
    for (Map<String, String> ipCrossCuttingData : ipCrossCuttingList) {
      IPCrossCutting ipCrossCutting = new IPCrossCutting();
      ipCrossCutting.setId(Integer.parseInt(ipCrossCuttingData.get("id")));
      ipCrossCutting.setName(ipCrossCuttingData.get("name"));

      ipCrossCuttings.add(ipCrossCutting);

    }
    return ipCrossCuttings;
  }

  @Override
  public List<IPCrossCutting> getIPCrossCuttings() {
    List<IPCrossCutting> ipCrossCuttings = new ArrayList<>();
    List<Map<String, String>> ipCrossCuttingList = ipCrossCuttingDAO.getIPCrossCuttings();
    for (Map<String, String> ipCrossCuttingData : ipCrossCuttingList) {
      IPCrossCutting ipCrossCutting = new IPCrossCutting();
      ipCrossCutting.setId(Integer.parseInt(ipCrossCuttingData.get("id")));
      ipCrossCutting.setName(ipCrossCuttingData.get("name"));

      ipCrossCuttings.add(ipCrossCutting);

    }
    return ipCrossCuttings;
  }

  @Override
  public boolean saveCrossCutting(int activityID, int crossCuttingID) {
    Map<String, Object> elementData = new HashMap<>();
    elementData.put("activity_id", activityID);
    elementData.put("theme_id", crossCuttingID);
    return ipCrossCuttingDAO.saveCrossCutting(elementData);
  }
}
