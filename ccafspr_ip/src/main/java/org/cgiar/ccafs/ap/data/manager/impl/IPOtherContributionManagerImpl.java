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

import org.cgiar.ccafs.ap.data.dao.IPOtherContributionDAO;
import org.cgiar.ccafs.ap.data.manager.IPOtherContributionManager;
import org.cgiar.ccafs.ap.data.model.IPOtherContribution;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
public class IPOtherContributionManagerImpl implements IPOtherContributionManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(IPOtherContributionManagerImpl.class);

  // DAO's
  private IPOtherContributionDAO ipOtherContributionDAO;

  // Managers


  @Inject
  public IPOtherContributionManagerImpl(IPOtherContributionDAO ipOtherContributionDAO) {
    this.ipOtherContributionDAO = ipOtherContributionDAO;
  }

  @Override
  public boolean deleteIPOtherContribution(int contributionId) {
    return ipOtherContributionDAO.deleteIPOtherContribution(contributionId);
  }

  @Override
  public boolean deleteIPOtherContributionsByProjectId(int projectID) {
    return ipOtherContributionDAO.deleteIPOtherContributionsByProjectId(projectID);
  }

  @Override
  public IPOtherContribution getIPOtherContributionById(int ipOtherContributionID) {
    Map<String, String> ipOtherContributionData =
      ipOtherContributionDAO.getIPOtherContributionById(ipOtherContributionID);
    if (!ipOtherContributionData.isEmpty()) {
      IPOtherContribution ipOtherContribution = new IPOtherContribution();
      ipOtherContribution.setId(Integer.parseInt(ipOtherContributionData.get("id")));
      ipOtherContribution.setContribution(ipOtherContributionData.get("contribution"));
      ipOtherContribution.setAdditionalContribution(ipOtherContributionData.get("additional_contribution"));
      return ipOtherContribution;
    }
    return null;
  }

  @Override
  public IPOtherContribution getIPOtherContributionByProjectId(int projectID) {
    Map<String, String> ipOtherContributionData = ipOtherContributionDAO.getIPOtherContributionByProjectId(projectID);
    if (!ipOtherContributionData.isEmpty()) {
      IPOtherContribution ipOtherContribution = new IPOtherContribution();
      ipOtherContribution.setId(Integer.parseInt(ipOtherContributionData.get("id")));
      ipOtherContribution.setContribution(ipOtherContributionData.get("contribution"));
      ipOtherContribution.setAdditionalContribution(ipOtherContributionData.get("additional_contribution"));

      return ipOtherContribution;
    }
    return null;
  }

  @Override
  public boolean saveIPOtherContribution(int projectID, IPOtherContribution ipOtherContribution) {
    boolean allSaved = true;
    Map<String, Object> activityData = new HashMap<>();
    if (ipOtherContribution.getId() > 0) {
      activityData.put("id", ipOtherContribution.getId());
    }
    activityData.put("contribution", ipOtherContribution.getContribution());
    activityData.put("additional_contribution", ipOtherContribution.getAdditionalContribution());

    int result = ipOtherContributionDAO.saveIPOtherContribution(projectID, activityData);

    if (result > 0) {
      LOG.debug("saveIPOtherContribution > New IP Other Contribution added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveIPOtherContribution > IP Other Contribution with id={} was updated", ipOtherContribution.getId());
    } else {
      LOG
        .error(
          "saveIPOtherContribution > There was an error trying to save/update an IP Other Contribution from projectID={}",
          projectID);
      allSaved = false;
    }

    return allSaved;

  }
}
