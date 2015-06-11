/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.config.APModule;
import org.cgiar.ccafs.ap.data.dao.ProjectPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectPartnerManager;
import org.cgiar.ccafs.ap.data.manager.UserManager;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Héctor Fabio Tobón R.
 */
public class ProjectPartnerManagerImpl implements ProjectPartnerManager {

  // LOG
  public static Logger LOG = LoggerFactory.getLogger(ProjectPartnerManagerImpl.class);

  // DAO's
  private ProjectPartnerDAO projecPartnerDAO;

  // Managers
  private InstitutionManager institutionManager;
  private UserManager userManager;

  @Inject
  public ProjectPartnerManagerImpl(ProjectPartnerDAO projectPartnerDAO, InstitutionManager institutionManager,
    UserManager userManager) {
    this.projecPartnerDAO = projectPartnerDAO;
    this.institutionManager = institutionManager;
    this.userManager = userManager;
  }

  // TODO - TEST - If you see this, please REMOVE IT!
  public static void main(String[] args) {
    Injector in = Guice.createInjector(new APModule());
    ProjectPartnerManager partnerManager = in.getInstance(ProjectPartnerManager.class);
    UserManager userManager = in.getInstance(UserManager.class);

    User user = userManager.getUser(1);
    int projectID = 2;
    List<ProjectPartner> partners = partnerManager.getProjectPartners(projectID);
    ProjectPartner pp = partners.get(0);
    pp.setResponsabilities("TEST - " + pp.getResponsabilities());
    pp = partners.get(1);
    pp.setResponsabilities("TEST - " + pp.getResponsabilities());
    // TODO - created_by and modified_by are referencing the employees table.
    // TODO - Once it is fixed, test both methods (save and update)
    // System.out.println(partnerManager.saveProjectPartners(projectID, partners, "justificacion"));
    System.out.println(partnerManager.saveProjectPartner(projectID, pp, user, "La justificacion"));
  }

  @Override
  public boolean deleteProjectPartner(int id) {
    return projecPartnerDAO.deleteProjectPartner(id);
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
      projectPartner.setResponsabilities(pData.get("responsabilities"));
      // Partner type (PPA, PL, PP, etc.)
      projectPartner.setType(pData.get("partner_type"));
      // User as user_id
      projectPartner.setUser(userManager.getUser(Integer.parseInt(pData.get("user_id"))));
      // Institution as partner_id
      projectPartner.setInstitution(institutionManager.getInstitution(Integer.parseInt(pData.get("partner_id"))));
      // Getting the institutions which this partner is contributing to.
      projectPartner
      .setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(projectPartner));
      // adding information of the object to the array
      projectPartners.add(projectPartner);
    }
    return projectPartners;
  }

  @Override
  public List<ProjectPartner> getProjectPartners(int projectId, String projectPartnerType) {
    List<ProjectPartner> projectPartners = new ArrayList<>();
    List<Map<String, String>> projectPartnerDataList =
      projecPartnerDAO.getProjectPartners(projectId, projectPartnerType);
    for (Map<String, String> pData : projectPartnerDataList) {
      ProjectPartner projectPartner = new ProjectPartner();
      projectPartner.setId(Integer.parseInt(pData.get("id")));
      // Partner type (PPA, PL, PP, etc.)
      projectPartner.setType(pData.get("partner_type"));
      // User as user_id
      if (pData.get("user_id") != null) {
        projectPartner.setUser(userManager.getUser(Integer.parseInt(pData.get("user_id"))));
      } else {
        User user = new User();
        user.setId(-1);
        projectPartner.setUser(user);
      }
      projectPartner.setResponsabilities(pData.get("responsabilities"));

      // Institution as partner_id
      projectPartner.setInstitution(institutionManager.getInstitution(Integer.parseInt(pData.get("partner_id"))));

      // Getting the institutions which this partner is contributing to.
      projectPartner
      .setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(projectPartner));

      // adding information of the object to the array
      projectPartners.add(projectPartner);
    }
    return projectPartners;
  }

  @Override
  public int saveProjectPartner(int projectId, ProjectPartner projectPartner, User user, String justification) {
    Map<String, Object> projectPartnerData = new HashMap<>();

    // Validate if the Project Partner is invalid.
    if (projectPartner.getInstitution() == null) {
      return -1;
    } else if (projectPartner.getInstitution().getId() == -1) {
      return -1;
    }

    // if this is a new project partner, do not assign an id.
    if (projectPartner.getId() > 0) {
      projectPartnerData.put("id", projectPartner.getId());
    } else {
      // otherwise will be a new record so we need to include the creator.
      projectPartnerData.put("created_by", user.getId());
    }
    projectPartnerData.put("project_id", projectId);
    projectPartnerData.put("partner_id", projectPartner.getInstitution().getId());
    projectPartnerData.put("user_id", projectPartner.getUser().getId());
    projectPartnerData.put("partner_type", projectPartner.getType());
    projectPartnerData.put("responsabilities", projectPartner.getResponsabilities());
    // Logs data
    projectPartnerData.put("modified_by", user.getId());
    projectPartnerData.put("modification_justification", justification);

    int result = projecPartnerDAO.saveProjectPartner(projectPartnerData);
    if (result > 0) {
      LOG.debug("saveProjectPartner > New Project Partner added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveProjectPartner > Project partner with id={} was updated", projectPartner.getId());
    } else {
      LOG.error("saveProjectPartner > There was an error trying to save/update a project partner from projectId={}",
        projectId);
    }

    return result;
  }

  @Override
  public boolean saveProjectPartners(int projectId, List<ProjectPartner> projectPartners, User user,
    String justification) {
    boolean allSaved = true;
    Map<String, Object> projectPartnerData = new HashMap<>();
    for (ProjectPartner projectPartner : projectPartners) {
      projectPartnerData.clear();

      // Remove invalid project partners
      if (projectPartner.getInstitution() == null) {
        continue;
      } else if (projectPartner.getInstitution().getId() == -1) {
        continue;
      }

      // if is a new project partner, do not assign an id.
      if (projectPartner.getId() > 0) {
        projectPartnerData.put("id", projectPartner.getId());
      }
      projectPartnerData.put("project_id", projectId);
      projectPartnerData.put("partner_id", projectPartner.getInstitution().getId());
      projectPartnerData.put("user_id", projectPartner.getUser().getId());
      projectPartnerData.put("partner_type", projectPartner.getType());
      projectPartnerData.put("responsabilities", projectPartner.getResponsabilities());
      // Logs data
      projectPartnerData.put("modified_by", user.getId());
      projectPartnerData.put("modification_justification", justification);

      int result = projecPartnerDAO.saveProjectPartner(projectPartnerData);
      if (result > 0) {
        LOG.debug("saveProjectPartner > New Project Partner added with id {}", result);
      } else if (result == 0) {
        LOG.debug("saveProjectPartner > Project partner with id={} was updated", projectPartner.getId());
      } else {
        LOG.error("saveProjectPartner > There was an error trying to save/update a project partner from projectId={}",
          projectId);
        allSaved = false;
      }
    }
    return allSaved;
  }

}
