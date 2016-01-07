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

import org.cgiar.ccafs.ap.data.dao.OtherContributionsDAO;
import org.cgiar.ccafs.ap.data.model.OtherContributions;

import java.util.List;

import com.google.inject.Inject;

public class OtherContributionsMySQLDAO implements OtherContributionsDAO {

  private StandardDAO dao;

  @Inject
  public OtherContributionsMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteOtherContributions(int crossCuttingContributionID, int userID, String justification) {
    OtherContributions project = this.find(crossCuttingContributionID);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) == 1; // TODO To review
  }


  @Override
  public boolean existOtherContributions(int crossCuttingContributionID) {
    OtherContributions project = this.find(crossCuttingContributionID);
    if (project == null) {
      return false;
    }
    return true;
  }

  @Override
  public OtherContributions find(int id) {
    return dao.find(OtherContributions.class, id);
  }


  @Override
  public List<OtherContributions> getOtherContributionsByProject(int projectID) {
    String query = "from " + OtherContributions.class.getName() + " where project_id=" + projectID + " and is_active=1";
    return dao.findAll(query);
  }

  @Override
  public int save(OtherContributions projectHighlihts) {
    dao.saveOrUpdate(projectHighlihts); // TODO review
    return projectHighlihts.getId(); // TODO To review
  }
}