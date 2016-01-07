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

import org.cgiar.ccafs.ap.data.dao.CrossCuttingContributionDAO;
import org.cgiar.ccafs.ap.data.model.CrossCuttingContribution;

import java.util.List;

import com.google.inject.Inject;

public class CrossCuttingContributionMySQLDAO implements CrossCuttingContributionDAO {


  private StandardDAO dao;

  @Inject
  public CrossCuttingContributionMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteCrossCuttingContribution(int crossCuttingContributionID, int userID, String justification) {

    CrossCuttingContribution project = this.find(crossCuttingContributionID);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) == 1;

  }


  @Override
  public boolean existCrossCuttingContribution(int crossCuttingContributionID) {
    CrossCuttingContribution project = this.find(crossCuttingContributionID);
    if (project == null) {
      return false;
    }
    return true;
  }

  @Override
  public CrossCuttingContribution find(int id) {
    return dao.find(CrossCuttingContribution.class, new Integer(id));
  }


  @Override
  public List<CrossCuttingContribution> getCrossCuttingContributionByProject(int projectID) {

    String sql =
      "from " + CrossCuttingContribution.class.getName() + " where project_id=" + projectID + " and is_active=1";

    List<CrossCuttingContribution> list = dao.customFindAll(sql);

    return list;

  }

  @Override
  public int save(CrossCuttingContribution projectHighlihts) {
    try {


      dao.saveOrUpdate(projectHighlihts);


      return projectHighlihts.getId();
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }

  }
}