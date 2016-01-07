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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class OtherContributionsMySQLDAO extends StandardDAO implements OtherContributionsDAO {

  @Override
  public boolean deleteOtherContributions(int crossCuttingContributionID, int userID, String justification) {

    OtherContributions project = this.find(crossCuttingContributionID);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) == 1;

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
    return (OtherContributions) this.find(OtherContributions.class, new Integer(id));
  }


  @Override
  public List<OtherContributions> getOtherContributionsByProject(int projectID) {
    List<OtherContributions> list = new ArrayList<>();

    try {
      this.getSession();
      this.initTransaction();
      this.commitTransaction();
      Query query = this.getSession().createQuery(
        "from " + OtherContributions.class.getName() + " where project_id=" + projectID + " and is_active=1");
      list.addAll(query.list());


      return list;
    } catch (HibernateException e) {
      this.rollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return null;
  }

  @Override
  public int save(OtherContributions projectHighlihts) {
    try {


      super.saveOrUpdate(projectHighlihts);


      return projectHighlihts.getId();
    } catch (Exception e) {
      e.printStackTrace();
      return 0;
    }

  }
}