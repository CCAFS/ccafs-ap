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

import org.cgiar.ccafs.ap.data.dao.ProjectPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cgiar.ccafs.ap.data.manager.InstitutionManager;

import com.google.inject.Inject;


/**
 * @author Héctor Fabio Tobón R.
 */
public class ProjectPartnerManagerImpl implements ProjectPartnerManager {

  // DAO's
  private ProjectPartnerDAO projecPartnerDAO;

  // Managers
  private InstitutionManager institutionManager;

  @Inject
  public ProjectPartnerManagerImpl(ProjectPartnerDAO projectPartnerDAO, InstitutionManager institutionManager) {
    this.projecPartnerDAO = projectPartnerDAO;
    this.institutionManager = institutionManager;
  }

  @Override
  public boolean deleteProjectPartner(Project projectId, Institution partnerId) {
    return projecPartnerDAO.deleteProjectPartner(projectId.getId(), partnerId.getId());
  }

  @Override
  public List<ProjectPartner> getProjectPartners(int projectId) {
    List<ProjectPartner> projectPartners = new ArrayList<>();
    List<Map<String, String>> projectPartnerDataList = projecPartnerDAO.getProjectPartners(projectId);
    for (Map<String, String> pData : projectPartnerDataList) {
      ProjectPartner projectPartner = new ProjectPartner();
      projectPartner.setId(Integer.parseInt(pData.get("id")));
      projectPartner.setContactName(pData.get("contact_name"));
      projectPartner.setContactEmail(pData.get("contact_email"));
      projectPartner.setResponsabilities(pData.get("responsabilities"));

      // Institution as partner_id
      projectPartner.setPartner(institutionManager.getInstitution(Integer.parseInt(pData.get("partner_id"))));

      // adding information of the object to the array
      projectPartners.add(projectPartner);
    }
    return projectPartners;
  }

  @Override
  public boolean saveProjectPartner(List<ProjectPartner> partners) {
    Map<String, Object> projectPartnerData;
    boolean allSaved = true;
    for (ProjectPartner partner : partners) {
      projectPartnerData = new HashMap<String, Object>();

      projectPartnerData.put("id", partner.getId());
      projectPartnerData.put("contact_name", partner.getContactName());
      projectPartnerData.put("contact_email", partner.getContactEmail());
      projectPartnerData.put("responsabilities ", partner.getResponsabilities());


    }
    return allSaved;
  }

}
