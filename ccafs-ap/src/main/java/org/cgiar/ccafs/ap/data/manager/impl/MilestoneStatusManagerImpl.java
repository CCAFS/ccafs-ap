package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.MilestoneStatusDAO;
import org.cgiar.ccafs.ap.data.manager.MilestoneStatusManager;
import org.cgiar.ccafs.ap.data.model.MilestoneStatus;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MilestoneStatusManagerImpl implements MilestoneStatusManager {

  MilestoneStatusDAO milestoneStatusDAO;

  @Inject
  public MilestoneStatusManagerImpl(MilestoneStatusDAO milestoneStatusDAO) {
    this.milestoneStatusDAO = milestoneStatusDAO;
  }

  @Override
  public MilestoneStatus[] getMilestoneStatusList() {
    List<Map<String, String>> milestoneStatusDataList = milestoneStatusDAO.getMilestoneStatus();
    MilestoneStatus[] milestoneStatus = new MilestoneStatus[milestoneStatusDataList.size()];

    for (int c = 0; c < milestoneStatusDataList.size(); c++) {
      milestoneStatus[c] = new MilestoneStatus();
      milestoneStatus[c].setId(Integer.parseInt(milestoneStatusDataList.get(c).get("id")));
      milestoneStatus[c].setName(milestoneStatusDataList.get(c).get("status"));
    }
    return milestoneStatus;
  }
}
