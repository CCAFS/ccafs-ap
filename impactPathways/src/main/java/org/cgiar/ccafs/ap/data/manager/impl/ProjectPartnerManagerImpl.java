/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectPartnerDAO;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.PartnerPersonManager;
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

import com.google.inject.Inject;
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
  private PartnerPersonManager partnerPersonManager;
  private InstitutionManager institutionManager;
  private UserManager userManager;

  @Inject
  public ProjectPartnerManagerImpl(ProjectPartnerDAO projectPartnerDAO, PartnerPersonManager partnerPersonManager,
    InstitutionManager institutionManager, UserManager userManager) {
    this.projecPartnerDAO = projectPartnerDAO;
    this.partnerPersonManager = partnerPersonManager;
    this.institutionManager = institutionManager;
    this.setUserManager(userManager);
  }

  @Override
  public ProjectPartner getProjectPartner(int partnerID) {
    ProjectPartner projectPartner = new ProjectPartner();
    Map<String, String> projectPartnerData = projecPartnerDAO.getProjectPartner(partnerID);
    if (projectPartnerData != null && projectPartnerData.size() > 0) {
      projectPartner.setId(Integer.parseInt(projectPartnerData.get("id")));
      projectPartner
        .setInstitution(institutionManager.getInstitution(Integer.parseInt(projectPartnerData.get("institution_id"))));
      projectPartner.setPartnerPersons(partnerPersonManager.getPartnerPersons(projectPartner));
      // We just need to get the partner contributors if the institution is not a PPA.
      if (projectPartner.getInstitution().isPPA() == false) {
        projectPartner.setPartnerContributors(this.getProjectPartnerContributors(projectPartner));
      }
      return projectPartner;
    }
    return null;
  }


  @Override
  public List<ProjectPartner> getProjectPartnerContributors(ProjectPartner projectPartner) {
    List<ProjectPartner> partnerContributors = new ArrayList<>();
    List<Map<String, String>> partnerContributorsDataList =
      projecPartnerDAO.getProjectPartnerContributors(projectPartner.getId());
    for (Map<String, String> pData : partnerContributorsDataList) {
      ProjectPartner partnerContributor =
        this.getProjectPartner(Integer.parseInt(pData.get("project_partner_contributor_id")));
      partnerContributors.add(partnerContributor);
    }
    return partnerContributors;
  }

  @Override
  public List<ProjectPartner> getProjectPartners(Project project) {
    List<ProjectPartner> partners = new ArrayList<>();
    List<Map<String, String>> projectPartnerDataList = projecPartnerDAO.getProjectPartners(project.getId());
    for (Map<String, String> projectPartnerData : projectPartnerDataList) {
      ProjectPartner projectPartner = new ProjectPartner();
      projectPartner.setId(Integer.parseInt(projectPartnerData.get("id")));
      projectPartner
        .setInstitution(institutionManager.getInstitution(Integer.parseInt(projectPartnerData.get("institution_id"))));
      projectPartner.setPartnerPersons(partnerPersonManager.getPartnerPersons(projectPartner));
      // We just need to get the partner contributors if its institution is not a PPA.
      if (projectPartner.getInstitution().isPPA() == false) {
        projectPartner.setPartnerContributors(this.getProjectPartnerContributors(projectPartner));
      }
      partners.add(projectPartner);
    }
    return partners;
  }

  public UserManager getUserManager() {
    return userManager;
  }

  @Override
  public int saveProjectPartner(Project project, ProjectPartner projectPartner, User user, String justification) {
    Map<String, Object> projectPartnerData = new HashMap<>();

    // Project partners must have an institution associated.
    if (projectPartner.getInstitution() == null || projectPartner.getInstitution().getId() == -1) {
      return -1;
    }

    // if this is a new project partner, do not assign an id.
    if (projectPartner.getId() > 0) {
      projectPartnerData.put("id", projectPartner.getId());
    } else {
      // otherwise will be a new record so we need to include the creator.
      projectPartnerData.put("created_by", user.getId());
    }
    projectPartnerData.put("project_id", project.getId());
    projectPartnerData.put("institution_id", projectPartner.getInstitution().getId());
    projectPartnerData.put("modified_by", user.getId());
    projectPartnerData.put("modification_justification", justification);

    int result = projecPartnerDAO.saveProjectPartner(projectPartnerData);
    if (result > 0) {
      LOG.debug("saveProjectPartner > New Project Partner added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveProjectPartner > Project partner with id={} was updated", projectPartner.getId());
    } else {
      LOG.error("saveProjectPartner > There was an error trying to save/update a project partner from projectId={}",
        project.getId());
    }

    return result;
  }

  @Override
  public boolean saveProjectPartners(Project project, List<ProjectPartner> projectPartners, User user,
    String justification) {
    boolean result = true;
    for (ProjectPartner partner : projectPartners) {
      if (this.saveProjectPartner(project, partner, user, justification) == -1) {
        result = false;
      }
    }
    return result;
  }

  public void setUserManager(UserManager userManager) {
    this.userManager = userManager;
  }

  @Override
  public boolean z_old_deleteProjectPartner(int id, User user, String justification) {
    return projecPartnerDAO.deleteProjectPartner(id, user.getId(), justification);
  }

  @Override
  public boolean z_old_deleteProjectPartner(Project projectId, Institution partnerId) {
    return projecPartnerDAO.deleteProjectPartner(projectId.getId(), partnerId.getId());
  }

  @Override
  public ProjectPartner z_old_getProjectPartnerById(int partnerId) {
    ProjectPartner projectPartner = new ProjectPartner();
    Map<String, String> projectPartnerData = projecPartnerDAO.getProjectPartner(partnerId);
    projectPartner.setId(Integer.parseInt(projectPartnerData.get("id")));
    // Partner type (PPA, PL, PP, etc.)
    // projectPartner.setType(projectPartnerData.get("partner_type"));
    // User as user_id
    // if (projectPartnerData.get("user_id") != null) {
    // projectPartner.setUser(userManager.getUser(Integer.parseInt(projectPartnerData.get("user_id"))));
    // } else {
    // User user = new User();
    // user.setId(-1);
    // projectPartner.setUser(user);
    // }
    // projectPartner.setResponsabilities(projectPartnerData.get("responsabilities"));

    // Institution as partner_id
    projectPartner
    .setInstitution(institutionManager.getInstitution(Integer.parseInt(projectPartnerData.get("partner_id"))));

    // Getting the institutions which this partner is contributing to.
    // projectPartner
    // .setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(projectPartner));

    // adding information of the object to the array
    return projectPartner;
  }

  @Override
  public List<ProjectPartner> z_old_getProjectPartners(int projectId) {
    List<ProjectPartner> projectPartners = new ArrayList<>();
    List<Map<String, String>> projectPartnerDataList = projecPartnerDAO.getProjectPartners(projectId);
    for (Map<String, String> pData : projectPartnerDataList) {
      ProjectPartner projectPartner = new ProjectPartner();
      projectPartner.setId(Integer.parseInt(pData.get("id")));
      // projectPartner.setResponsabilities(pData.get("responsabilities"));
      // // Partner type (PPA, PL, PP, etc.)
      // projectPartner.setType(pData.get("partner_type"));
      // // User as user_id
      // projectPartner
      // .setUser(userManager.getUser(pData.get("user_id") == null ? -1 : Integer.parseInt(pData.get("user_id"))));
      // // Institution as partner_id
      // projectPartner.setInstitution(institutionManager.getInstitution(Integer.parseInt(pData.get("partner_id"))));
      // // Getting the institutions which this partner is contributing to.
      // projectPartner
      // .setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(projectPartner));
      // adding information of the object to the array
      projectPartners.add(projectPartner);
    }
    return projectPartners;
  }

  @Override
  public List<ProjectPartner> z_old_getProjectPartners(int projectId, String projectPartnerType) {
    List<ProjectPartner> projectPartners = new ArrayList<>();
    List<Map<String, String>> projectPartnerDataList =
      projecPartnerDAO.getProjectPartners(projectId, projectPartnerType);
    for (Map<String, String> pData : projectPartnerDataList) {
      ProjectPartner projectPartner = new ProjectPartner();
      projectPartner.setId(Integer.parseInt(pData.get("id")));
      // Partner type (PPA, PL, PP, etc.)
      // projectPartner.setType(pData.get("partner_type"));
      // // User as user_id
      // if (pData.get("user_id") != null) {
      // projectPartner.setUser(userManager.getUser(Integer.parseInt(pData.get("user_id"))));
      // } else {
      // User user = new User();
      // user.setId(-1);
      // projectPartner.setUser(user);
      // }
      // projectPartner.setResponsabilities(pData.get("responsabilities"));

      // Institution as partner_id
      projectPartner.setInstitution(institutionManager.getInstitution(Integer.parseInt(pData.get("partner_id"))));

      // Getting the institutions which this partner is contributing to.
      // projectPartner
      // .setContributeInstitutions(institutionManager.getProjectPartnerContributeInstitutions(projectPartner));

      // adding information of the object to the array
      projectPartners.add(projectPartner);
    }
    return projectPartners;
  }

  @Override
  public int z_old_saveProjectPartner(int projectId, ProjectPartner projectPartner, User user, String justification) {
    Map<String, Object> projectPartnerData = new HashMap<>();

    // Project partners must have an institution associated.
    if (projectPartner.getInstitution() == null || projectPartner.getInstitution().getId() == -1) {
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
    // projectPartnerData.put("user_id", (projectPartner.getUser() == null || projectPartner.getUser().getId() == -1)
    // ? null : projectPartner.getUser().getId());
    // projectPartnerData.put("partner_type", projectPartner.getType());
    // projectPartnerData.put("responsabilities", projectPartner.getResponsabilities());
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
  public boolean z_old_saveProjectPartners(int projectId, List<ProjectPartner> projectPartners, User user,
    String justification) {
    boolean allSaved = true;
    int result;
    for (ProjectPartner projectPartner : projectPartners) {
      result = this.z_old_saveProjectPartner(projectId, projectPartner, user, justification);
      if (result == -1) {
        allSaved = false;
      }
    }
    return allSaved;
  }

}
