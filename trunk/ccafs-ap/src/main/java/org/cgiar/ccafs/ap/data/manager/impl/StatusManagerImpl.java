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
      LOG.debug("Activity status list loaded succesfully");
      return statusList;
    }
    LOG.warn("Activity status list loaded is empty");
    return null;
  }
}
