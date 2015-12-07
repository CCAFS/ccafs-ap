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


package org.cgiar.ccafs.ap.hibernate.dao;

import org.cgiar.ccafs.ap.hibernate.model.ProjectHighligths;
import org.cgiar.ccafs.ap.hibernate.model.ProjectHighligthsCountry;
import org.cgiar.ccafs.ap.hibernate.model.ProjectHighligthsTypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;

public class ProjectHightLihgtDAO extends StandardDao {

  public boolean deleteHighLight(int highLightId, int userID, String justification) {

    ProjectHighligths project = this.find(highLightId);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) == 1;

  }

  public boolean deleteHighLightsByProject(int projectID) {
    List<ProjectHighligths> list_programs = new ArrayList<>();

    try {
      this.getSession();
      this.InitTransaction();
      Query query = this.getSession().createQuery(
        "from " + ProjectHighligths.class.getName() + " where project_id=" + projectID + " and is_active=1");
      list_programs.addAll(query.list());

      for (ProjectHighligths projectHighligths : list_programs) {
        projectHighligths.setIsActive(false);
        this.save(projectHighligths);
      }
      this.CommitTransaction();
      return true;
    } catch (

    HibernateException e)

    {
      this.RollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return false;
  }

  public boolean existHighLight(int highLightID) {
    ProjectHighligths project = this.find(highLightID);
    if (project == null) {
      return false;
    }
    return true;
  }

  public ProjectHighligths find(int id) {
    return (ProjectHighligths) this.find(ProjectHighligths.class, new Integer(id));
  }


  public ProjectHighligthsCountry findProjectHighligthsCountries(int highlighid, int country) {
    List<ProjectHighligthsCountry> list_programs = new ArrayList<>();

    try {
      this.getSession();
      this.InitTransaction();
      this.CommitTransaction();
      Query query = this.getSession().createQuery("from " + ProjectHighligthsCountry.class.getName()
        + " where project_highlights_id=" + highlighid + " and id_country=" + country);
      list_programs.addAll(query.list());


      if (list_programs.size() > 0) {
        return list_programs.get(0);
      }
      return null;
    } catch (HibernateException e) {
      this.RollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return null;
  }


  public ProjectHighligthsTypes findProjectHighligthsTypes(int highlighid, int type) {
    List<ProjectHighligthsTypes> list_programs = new ArrayList<>();

    try {
      this.getSession();
      this.InitTransaction();
      this.CommitTransaction();
      Query query = this.getSession().createQuery("from " + ProjectHighligthsTypes.class.getName()
        + " where project_highlights_id=" + highlighid + " and id_type=" + type);
      list_programs.addAll(query.list());


      if (list_programs.size() > 0) {
        return list_programs.get(0);
      }
      return null;
    } catch (

    HibernateException e)

    {
      this.RollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return null;
  }


  public List<ProjectHighligths> getHighLightsByProject(int projectID) {
    List<ProjectHighligths> list_programs = new ArrayList<>();

    try {
      this.getSession();
      this.InitTransaction();
      this.CommitTransaction();
      Query query = this.getSession().createQuery(
        "from " + ProjectHighligths.class.getName() + " where project_id=" + projectID + " and is_active=1");
      list_programs.addAll(query.list());


      return list_programs;
    } catch (

    HibernateException e)

    {
      this.RollBackTransaction();
    } finally

    {
      this.closeSession();
    }
    return null;
  }

  public int save(ProjectHighligths projectHighlihts) {
    try {
      ProjectHighligths projectHighlihtsPrev = this.find(projectHighlihts.getId());
      if (projectHighlihts.getId() == -1) {
        projectHighlihts.setId(null);
      }

      super.saveOrUpdate(projectHighlihts);

      Iterator<ProjectHighligthsTypes> iter = projectHighlihts.getProjectHighligthsTypeses().iterator();
      while (iter.hasNext()) {
        ProjectHighligthsTypes projectHighligthsTypes = iter.next();
        ProjectHighligthsTypes existing = this.findProjectHighligthsTypes(
          projectHighligthsTypes.getProjectHighligths().getId(), projectHighligthsTypes.getIdType());
        if (existing == null) {
          projectHighligthsTypes.setId(null);
          super.saveOrUpdate(projectHighligthsTypes);

        }

      }


      Iterator<ProjectHighligthsCountry> iter_con = projectHighlihts.getProjectHighligthsCountries().iterator();
      while (iter_con.hasNext()) {
        ProjectHighligthsCountry projectHighligthsTypes = iter_con.next();

        ProjectHighligthsCountry existing = this.findProjectHighligthsCountries(
          projectHighligthsTypes.getProjectHighligths().getId(), projectHighligthsTypes.getIdCountry());
        if (existing == null) {
          projectHighligthsTypes.setId(null);
          super.saveOrUpdate(projectHighligthsTypes);

        }
      }


      if (projectHighlihtsPrev != null) {
        Iterator<ProjectHighligthsTypes> iter_prev = projectHighlihtsPrev.getProjectHighligthsTypeses().iterator();
        while (iter_prev.hasNext()) {
          ProjectHighligthsTypes projectHighligthsTypes = iter_prev.next();

          if (!projectHighlihts.getProjectHighligthsTypeses().contains(projectHighligthsTypes)) {
            this.delete(projectHighligthsTypes);
          }
        }

        Iterator<ProjectHighligthsCountry> iter_con_2 = projectHighlihtsPrev.getProjectHighligthsCountries().iterator();
        while (iter_con_2.hasNext()) {
          ProjectHighligthsCountry projectHighligthsTypes = iter_con_2.next();

          if (!projectHighlihts.getProjectHighligthsCountries().contains(projectHighligthsTypes)) {
            this.delete(projectHighligthsTypes);
          }
        }
      }


      return projectHighlihts.getId();
    } catch (Exception e) {

      return 0;
    }

  }
}
