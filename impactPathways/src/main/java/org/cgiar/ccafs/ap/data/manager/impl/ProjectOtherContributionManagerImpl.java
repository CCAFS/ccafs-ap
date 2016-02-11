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

import org.cgiar.ccafs.ap.data.dao.OtherContributionsDAO;
import org.cgiar.ccafs.ap.data.dao.ProjectOtherContributionDAO;
import org.cgiar.ccafs.ap.data.manager.CRPManager;
import org.cgiar.ccafs.ap.data.manager.ProjectOtherContributionManager;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.ProjecteOtherContributions;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
public class ProjectOtherContributionManagerImpl implements ProjectOtherContributionManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectOtherContributionManagerImpl.class);
  private OtherContributionsDAO otherContributionsDAO;
  // DAO's
  private ProjectOtherContributionDAO ipOtherContributionDAO;

  // Managers
  private CRPManager crpManager;

  @Inject
  public ProjectOtherContributionManagerImpl(ProjectOtherContributionDAO ipOtherContributionDAO, CRPManager crpManager,
    OtherContributionsDAO otherContributionsDAO) {
    this.ipOtherContributionDAO = ipOtherContributionDAO;
    this.crpManager = crpManager;
    this.otherContributionsDAO = otherContributionsDAO;
  }

  @Override
  public OtherContribution getIPOtherContributionById(int ipOtherContributionID) {
    Map<String, String> ipOtherContributionData =
      ipOtherContributionDAO.getIPOtherContributionById(ipOtherContributionID);
    if (!ipOtherContributionData.isEmpty()) {
      OtherContribution ipOtherContribution = new OtherContribution();
      ipOtherContribution.setId(Integer.parseInt(ipOtherContributionData.get("id")));
      ipOtherContribution.setContribution(ipOtherContributionData.get("contribution"));
      ipOtherContribution.setAdditionalContribution(ipOtherContributionData.get("additional_contribution"));
      // ipOtherContribution.setCrpCollaborationNature(crpManager.getCrpContributionsNature()); // TODO JG modify method
      // to bring project ID
      return ipOtherContribution;
    }
    return null;
  }

  @Override
  public OtherContribution getIPOtherContributionByProjectId(int projectID) {
    Map<String, String> ipOtherContributionData = ipOtherContributionDAO.getIPOtherContributionByProjectId(projectID);
    if (!ipOtherContributionData.isEmpty()) {
      OtherContribution ipOtherContribution = new OtherContribution();
      ipOtherContribution.setId(Integer.parseInt(ipOtherContributionData.get("id")));
      ipOtherContribution.setContribution(ipOtherContributionData.get("contribution"));
      ipOtherContribution.setAdditionalContribution(ipOtherContributionData.get("additional_contribution"));


      ipOtherContribution.setCrpCollaborationNature(crpManager.getCrpContributionsNature(projectID));
      return ipOtherContribution;
    }
    return null;
  }

  @Override
  public List<ProjecteOtherContributions> getOtherContributionsByProjectId(int projectID) {
    List<ProjecteOtherContributions> otherContributionsList =
      otherContributionsDAO.getOtherContributionsByProject(projectID);


    return otherContributionsList;

  }


  @Override
  public boolean saveIPOtherContribution(int projectID, OtherContribution ipOtherContribution, User user,
    String justification) {
    boolean allSaved = true;
    Map<String, Object> contributionData = new HashMap<>();
    if (ipOtherContribution.getId() > 0) {
      contributionData.put("id", ipOtherContribution.getId());
    }
    contributionData.put("contribution", ipOtherContribution.getContribution());
    contributionData.put("additional_contribution", ipOtherContribution.getAdditionalContribution());
    // contributionData.put("crp_contributions_nature", ipOtherContribution.getCrpCollaborationNature());
    contributionData.put("user_id", user.getId());
    contributionData.put("justification", justification);


    int result = ipOtherContributionDAO.saveIPOtherContribution(projectID, contributionData);

    if (result > 0) {
      LOG.debug("saveIPOtherContribution > New IP Other Contribution added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveIPOtherContribution > IP Other Contribution with id={} was updated", ipOtherContribution.getId());
    } else {
      LOG.error(
        "saveIPOtherContribution > There was an error trying to save/update an IP Other Contribution from projectID={}",
        projectID);
      allSaved = false;
    }

    return allSaved;

  }

  @Override
  public int saveOtherContributionsList(int projectID, List<ProjecteOtherContributions> OtherContributionsList,
    User user, String justification) {

    List<ProjecteOtherContributions> preview = this.getOtherContributionsByProjectId(projectID);


    for (ProjecteOtherContributions otherContributions : OtherContributionsList) {
      if (otherContributions.getId() == null || otherContributions.getId() == -1) {
        otherContributions.setCreatedBy(Long.parseLong(user.getId() + ""));
        otherContributions.setId(null);
      }
      otherContributions.setModifiedBy(Long.parseLong(user.getId() + ""));
      otherContributions.setModificationJustification(justification);
      otherContributions.setProjectId(projectID);
      otherContributions.setActiveSince(new Date());
      otherContributions.setIsActive(true);
      int result = otherContributionsDAO.save(otherContributions);

      for (ProjecteOtherContributions projecteOtherContributions : preview) {
        if (!OtherContributionsList.contains(projecteOtherContributions)) {
          otherContributionsDAO.deleteOtherContributions(projecteOtherContributions.getId(), user.getId(),
            justification);
        }
      }

      if (result > 0) {
        LOG.debug("saveCrossCuttingContribution > New CrossCuttingContribution added with id {}", result);
      } else if (result == 0) {
        LOG.debug("saveCrossCuttingContribution > CrossCuttingContribution with id={} was updated",
          otherContributions.getId());
      } else {
        LOG.error(
          "saveCrossCuttingContribution > There was an error trying to save/update a CrossCuttingContribution from projectId={}",
          projectID);
      }
    }

    return 1;
  }
}
