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


package org.cgiar.ccafs.ap.data.dao.mysqlhiberate;

import org.cgiar.ccafs.ap.data.dao.ProjectNextUserDAO;
import org.cgiar.ccafs.ap.data.model.ProjectNextUser;

import java.util.List;

import com.google.inject.Inject;

public class ProjectNextUserMySQLDAO implements ProjectNextUserDAO {

  private StandardDAO dao;

  @Inject
  public ProjectNextUserMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteNextUser(int nextUserId, int userID, String justification) {

    ProjectNextUser projectNextUser = this.find(nextUserId);
    projectNextUser.setIsActive(false);
    projectNextUser.setModifiedBy(new Long(userID));
    projectNextUser.setModificationJustification(justification);
    return this.save(projectNextUser) == 1;

  }

  @Override
  public boolean deleteNextUsersByProject(int projectID) {
    String query = "from " + ProjectNextUser.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<ProjectNextUser> list = dao.findAll(query);
    boolean saved = true;
    for (ProjectNextUser projectNextUser : list) {
      projectNextUser.setIsActive(false);
      if (this.save(projectNextUser) == -1) {
        saved = false;
      }
    }
    return saved;
  }

  @Override
  public boolean existNextUser(int nextUserID) {
    ProjectNextUser projectNextUser = this.find(nextUserID);
    if (projectNextUser == null) {
      return false;
    }
    return true;
  }

  @Override
  public ProjectNextUser find(int id) {
    return dao.<ProjectNextUser>find(ProjectNextUser.class, new Integer(id));
  }


  @Override
  public List<ProjectNextUser> getNextUsersByProject(int projectID) {
    String query = "from " + ProjectNextUser.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<ProjectNextUser> list = dao.findAll(query);

    return list;

  }

  @Override
  public int save(ProjectNextUser projectNextUser) {
    try {

      dao.saveOrUpdate(projectNextUser);


      return projectNextUser.getId();
    } catch (Exception e) {
      return -1;
    }

  }
}