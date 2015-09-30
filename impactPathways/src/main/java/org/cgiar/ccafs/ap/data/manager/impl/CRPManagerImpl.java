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

import org.cgiar.ccafs.ap.data.dao.CrpDAO;
import org.cgiar.ccafs.ap.data.manager.CRPManager;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.CRPContribution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class CRPManagerImpl implements CRPManager {

  private CrpDAO crpDAO;

  @Inject
  public CRPManagerImpl(CrpDAO crpDAO) {
    this.crpDAO = crpDAO;
  }

  @Override
  public CRP getCRPById(int crpID) {
    Map<String, String> crpData = crpDAO.getCRPById(crpID);
    if (!crpData.isEmpty()) {
      CRP crpinfo = new CRP();
      crpinfo.setId(Integer.parseInt(crpData.get("id")));
      crpinfo.setName(crpData.get("name"));
      crpinfo.setAcronym(crpData.get("acronym"));
      return crpinfo;
    }

    return null;
  }

  @Override
  public List<CRP> getCrpContributions(int projectID) {
    List<CRP> crps = new ArrayList<>();
    List<Map<String, String>> crpsData = crpDAO.getCrpContributions(projectID);

    for (Map<String, String> crpData : crpsData) {
      CRP crp = new CRP();
      crp.setId(Integer.parseInt(crpData.get("id")));
      crp.setName(crpData.get("name"));
      crp.setAcronym(crpData.get("acronym"));
      crps.add(crp);
    }
    return crps;
  }

  @Override
  public List<CRPContribution> getCrpContributionsNature(int projectID) {
    List<CRPContribution> crpContributions = new ArrayList<>();
    List<Map<String, String>> crpsData = crpDAO.getCrpContributionsNature(projectID);

    for (Map<String, String> crpData : crpsData) {
      CRPContribution crp = new CRPContribution();
      crp.setId(Integer.parseInt(crpData.get("id")));
      crp.setCrp(getCRPById(Integer.parseInt(crpData.get("crp_id"))));
      crp.setNatureCollaboration(crpData.get("collaboration_nature"));
      crpContributions.add(crp);
    }
    return crpContributions;
  }

  @Override
  public List<CRP> getCRPs(String[] crpIDs) {
    List<CRP> crps = new ArrayList<>();
    List<String> ids = new ArrayList<String>(Arrays.asList(crpIDs));

    for (CRP crp : this.getCRPsList()) {
      if (ids.contains(String.valueOf(crp.getId()))) {
        crps.add(crp);
      }
    }

    return crps;
  }

  @Override
  public List<CRP> getCRPsList() {
    List<CRP> crps = new ArrayList<>();
    List<Map<String, String>> crpsData = crpDAO.getCRPsList();

    for (Map<String, String> crpData : crpsData) {
      CRP crp = new CRP();
      crp.setId(Integer.parseInt(crpData.get("id")));
      crp.setName(crpData.get("name"));
      crp.setAcronym(crpData.get("acronym"));
      crps.add(crp);
    }
    return crps;
  }

  @Override
  public boolean removeCrpContributionNature(int projectID, int crpID, int userID, String justification) {
    return crpDAO.removeCrpContributionNature(projectID, crpID, userID, justification);
  }

  @Override
  public boolean saveCrpContributionsNature(Project project, User user, String justification) {
    boolean saved = true;

    for (CRPContribution crp : project.getIpOtherContribution().getCrpContributions()) {
      Map<String, Object> data = new HashMap<>();
      data.put("projectID", project.getId());
      data.put("crp_id", crp.getCrp().getId());
      data.put("collaboration_nature", crp.getNatureCollaboration());
      data.put("user_id", user.getId());
      data.put("justification", justification);

      saved = saved && crpDAO.saveCrpContributionsNature(project.getId(), data);
    }

    return saved;
  }
}
