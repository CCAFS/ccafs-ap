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

import org.cgiar.ccafs.ap.data.dao.ProjectCaseStudiesDAO;
import org.cgiar.ccafs.ap.data.model.CasesStudies;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class ProjectCaseStudiesMySQLDAO extends StandardDao implements ProjectCaseStudiesDAO {

  @Override
  public boolean deleteCaseStudie(int caseStudyId, int userID, String justification) {

    CasesStudies project = this.find(caseStudyId);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) == 1;

  }

  @Override
  public boolean deleteCaseStudiesByProject(int projectID) {
    List<CasesStudies> list = new ArrayList<>();

    try {
      this.getSession();
      this.InitTransaction();
      Query query = this.getSession()
        .createQuery("from " + CasesStudies.class.getName() + " where project_id=" + projectID + " and is_active=1");
      list.addAll(query.list());

      for (CasesStudies projectHighligths : list) {
        projectHighligths.setIsActive(false);
        this.save(projectHighligths);
      }
      this.CommitTransaction();
      return true;
    } catch (HibernateException e)

    {
      this.RollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return false;
  }

  @Override
  public boolean existCaseStudy(int caseStudyID) {
    CasesStudies project = this.find(caseStudyID);
    if (project == null) {
      return false;
    }
    return true;
  }

  @Override
  public CasesStudies find(int id) {
    return (CasesStudies) this.find(CasesStudies.class, new Integer(id));
  }


  @Override
  public List<CasesStudies> getCaseStudiesByProject(int projectID) {
    List<CasesStudies> list = new ArrayList<>();

    try {
      this.getSession();
      this.InitTransaction();
      this.CommitTransaction();
      Query query = this.getSession()
        .createQuery("from " + CasesStudies.class.getName() + " where project_id=" + projectID + " and is_active=1");
      list.addAll(query.list());


      return list;
    } catch (HibernateException e) {
      this.RollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return null;
  }

  @Override
  public int save(CasesStudies casesStudies) {
    try {

      CasesStudies casesStudiesPrev = null;
      if (casesStudies.getId() != null) {
        casesStudiesPrev = this.find(casesStudies.getId());

      }

      super.saveOrUpdate(casesStudies);


      return casesStudies.getId();
    } catch (Exception e) {

      return 0;
    }

  }
}