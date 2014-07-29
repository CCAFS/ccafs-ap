package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.IPCrossCuttingDAO;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;

import java.util.ArrayList;
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
  public IPCrossCutting getIPCrossCutting(int iD) {
    Map<String, String> ipCrossCuttingData = ipCrossCuttingDAO.getIPCrossCutting(iD);
    if (!ipCrossCuttingData.isEmpty()) {
      IPCrossCutting ipCrossCutting = new IPCrossCutting();
      ipCrossCutting.setId(Integer.parseInt(ipCrossCuttingData.get("id")));
      ipCrossCutting.setName(ipCrossCuttingData.get("name"));

      return ipCrossCutting;
    }
    return null;
  }

  @Override
  public List<IPCrossCutting> getIPCrossCuttingByProject(int projectID) {
    List<IPCrossCutting> ipCrossCuttings = new ArrayList<>();
    List<Map<String, String>> ipCrossCuttingList = ipCrossCuttingDAO.getIPCrossCuttingByProject(projectID);
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
}
