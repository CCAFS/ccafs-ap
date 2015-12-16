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


import org.cgiar.ccafs.ap.data.dao.CrossCuttingContributionDAO;
import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.CrossCuttingContributionMySQLDAO;
import org.cgiar.ccafs.ap.data.manager.CrossCuttingContributionManager;
import org.cgiar.ccafs.ap.data.model.CrossCuttingContribution;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Garcia
 */
public class CrossCuttingContributionManagerImpl implements CrossCuttingContributionManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(CrossCuttingContributionManagerImpl.class);

  // DAO's
  private CrossCuttingContributionDAO crossCuttingContributionDAO;
  // Managers


  @Inject
  public CrossCuttingContributionManagerImpl() {
    crossCuttingContributionDAO = new CrossCuttingContributionMySQLDAO();


  }

  @Override
  public boolean deleteCrossCuttingContribution(int crossCuttingContributionID, User user, String justification) {
    boolean problem = false;
    // Deleting crossCuttingContribution.
    boolean deleted = crossCuttingContributionDAO.deleteCrossCuttingContribution(crossCuttingContributionID,
      user.getId(), justification);
    if (!deleted) {
      problem = true;
    }


    return !problem;
  }


  @Override
  public boolean existCrossCuttingContribution(int crossCuttingContributionID) {
    return crossCuttingContributionDAO.existCrossCuttingContribution(crossCuttingContributionID);
  }

  @Override
  public CrossCuttingContribution getCrossCuttingContributionById(int crossCuttingContributionID) {

    CrossCuttingContribution crossCuttingContribution = crossCuttingContributionDAO.find(crossCuttingContributionID);


    return crossCuttingContribution;


  }


  @Override
  public List<CrossCuttingContribution> getCrossCuttingContributionsByProject(int projectID) {
    List<CrossCuttingContribution> crossCuttingContributionList =
      crossCuttingContributionDAO.getCrossCuttingContributionByProject(projectID);


    return crossCuttingContributionList;
  }


  @Override
  public int saveCrossCuttingContribution(int projectID, CrossCuttingContribution crossCuttingContribution, User user,
    String justification) {
    if (crossCuttingContribution.getId() == null) {
      crossCuttingContribution.setCreatedBy(Long.parseLong(user.getId() + ""));
    }
    crossCuttingContribution.setModifiedBy(Long.parseLong(user.getId() + ""));
    crossCuttingContribution.setModificationJustification(justification);
    crossCuttingContribution.setProjectId(projectID);
    crossCuttingContribution.setActiveSince(new Date());
    crossCuttingContribution.setIsActive(true);
    int result = crossCuttingContributionDAO.save(crossCuttingContribution);

    if (result > 0) {
      LOG.debug("saveCrossCuttingContribution > New CrossCuttingContribution added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveCrossCuttingContribution > CrossCuttingContribution with id={} was updated",
        crossCuttingContribution.getId());
    } else {
      LOG.error(
        "saveCrossCuttingContribution > There was an error trying to save/update a CrossCuttingContribution from projectId={}",
        projectID);
    }
    return result;
  }


}
