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

import org.cgiar.ccafs.ap.data.dao.MilestoneStatusDAO;
import org.cgiar.ccafs.ap.data.manager.MilestoneStatusManager;
import org.cgiar.ccafs.ap.data.model.MilestoneStatus;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MilestoneStatusManagerImpl implements MilestoneStatusManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MilestoneStatusManagerImpl.class);
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
