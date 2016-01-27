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


import org.cgiar.ccafs.ap.data.dao.ProjectNextUserDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectNextUserManager;
import org.cgiar.ccafs.ap.data.model.ProjectNextUser;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Date;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Garcia
 */
public class ProjectNextUserManagerImpl implements ProjectNextUserManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ProjectNextUserManagerImpl.class);

  // DAO's
  private ProjectNextUserDAO projectNextUsersDAO;


  @Inject
  public ProjectNextUserManagerImpl(ProjectNextUserDAO projectNextUsersDAO) {
    this.projectNextUsersDAO = projectNextUsersDAO;
  }

  @Override
  public boolean deleteProjectNextUser(int nextUserID, User user, String justification) {
    boolean problem = false;
    // Deleting nextUser.
    boolean deleted = projectNextUsersDAO.deleteNextUser(nextUserID, user.getId(), justification);
    if (!deleted) {
      problem = true;
    }


    return !problem;
  }


  @Override
  public boolean deleteProjectNextUsersByProject(int projectID) {
    return projectNextUsersDAO.deleteNextUsersByProject(projectID);
  }


  @Override
  public boolean existProjectNextUser(int nextUserID) {
    return projectNextUsersDAO.existNextUser(nextUserID);
  }

  @Override
  public ProjectNextUser getProjectNextUserById(int nextUserID) {

    ProjectNextUser nextUser = projectNextUsersDAO.find(nextUserID);


    return nextUser;


  }


  @Override
  public List<ProjectNextUser> getProjectNextUserProject(int projectID) {
    List<ProjectNextUser> nextUserList = projectNextUsersDAO.getNextUsersByProject(projectID);


    return nextUserList;
  }


  @Override
  public int saveProjectNextUser(int projectID, ProjectNextUser nextUser, User user, String justification) {
    if (nextUser.getId() == 0) {
      nextUser.setCreatedBy(Long.parseLong(user.getId() + ""));
      nextUser.setActiveSince(new Date());
    }
    nextUser.setIsActive(true);
    nextUser.setModifiedBy(Long.parseLong(user.getId() + ""));
    nextUser.setModificationJustification(justification);
    int result = projectNextUsersDAO.save(nextUser);

    if (result > 0) {
      LOG.debug("saveProjectNextUser > New ProjectNextUser added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveProjectNextUser > ProjectNextUser with id={} was updated", nextUser.getId());
    } else {
      LOG.error("saveProjectNextUser > There was an error trying to save/update a ProjectNextUser from projectId={}",
        projectID);
    }
    return result;
  }


}
