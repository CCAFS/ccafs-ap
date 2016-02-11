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

import org.cgiar.ccafs.ap.data.dao.ProjectLevarageDAO;
import org.cgiar.ccafs.ap.data.model.ProjectLeverage;

import java.util.List;

import com.google.inject.Inject;

public class ProjectLeverageMySQLDAO implements ProjectLevarageDAO {

  private StandardDAO dao;

  @Inject
  public ProjectLeverageMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteLeverage(int leverageId, int userID, String justification) {

    ProjectLeverage projectLeverage = this.find(leverageId);
    projectLeverage.setIsActive(false);
    projectLeverage.setModifiedBy(new Long(userID));
    projectLeverage.setModificationJustification(justification);
    return this.save(projectLeverage) == 1;

  }

  @Override
  public boolean deleteLeveragesByProject(int projectID) {
    String query = "from " + ProjectLeverage.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<ProjectLeverage> list = dao.findAll(query);
    boolean saved = true;
    for (ProjectLeverage projectLeverage : list) {
      projectLeverage.setIsActive(false);
      if (this.save(projectLeverage) == -1) {
        saved = false;
      }
    }
    return saved;
  }

  @Override
  public boolean existLeverage(int leverageID) {
    ProjectLeverage projectLeverage = this.find(leverageID);
    if (projectLeverage == null) {
      return false;
    }
    return true;
  }

  @Override
  public ProjectLeverage find(int id) {
    return dao.<ProjectLeverage>find(ProjectLeverage.class, new Integer(id));
  }


  @Override
  public List<ProjectLeverage> getLeveragesByProject(int projectID) {
    String query = "from " + ProjectLeverage.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<ProjectLeverage> list = dao.findAll(query);

    return list;

  }

  @Override
  public int save(ProjectLeverage projectLeverage) {
    try {

      dao.saveOrUpdate(projectLeverage);


      return projectLeverage.getId();
    } catch (Exception e) {
      return -1;
    }

  }
}