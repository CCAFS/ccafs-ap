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

import org.cgiar.ccafs.ap.data.dao.ProjectHighlightDAO;
import org.cgiar.ccafs.ap.data.model.ProjectHighligths;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsCountry;
import org.cgiar.ccafs.ap.data.model.ProjectHighligthsTypes;

import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;

public class ProjectHightLihgtMySQLDAO implements ProjectHighlightDAO {

  private StandardDAO dao;

  @Inject
  public ProjectHightLihgtMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteHighLight(int highLightId, int userID, String justification) {

    ProjectHighligths project = this.find(highLightId);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) > 0;

  }

  @Override
  public boolean deleteHighLightsByProject(int projectID) {
    String query = "from " + ProjectHighligths.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<ProjectHighligths> list = dao.findAll(query);
    boolean saved = true;
    for (ProjectHighligths projectHighligths : list) {
      projectHighligths.setIsActive(false);
      if (this.save(projectHighligths) == -1) {
        saved = false;
      }
    }
    return saved;
  }

  @Override
  public boolean existHighLight(int highLightID) {
    ProjectHighligths project = this.find(highLightID);
    if (project == null) {
      return false;
    }
    return true;
  }

  @Override
  public ProjectHighligths find(int id) {
    return dao.<ProjectHighligths>find(ProjectHighligths.class, new Integer(id));
  }


  @Override
  public ProjectHighligthsCountry findProjectHighligthsCountries(int highlighid, int country) {
    String query = "from " + ProjectHighligthsCountry.class.getName() + " where project_highlights_id=" + highlighid
      + " and id_country=" + country;

    List<ProjectHighligthsCountry> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }


  @Override
  public ProjectHighligthsTypes findProjectHighligthsTypes(int highlighid, int type) {
    String query = "from " + ProjectHighligthsTypes.class.getName() + " where project_highlights_id=" + highlighid
      + " and id_type=" + type;

    List<ProjectHighligthsTypes> list = dao.findAll(query);

    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }


  @Override
  public List<ProjectHighligths> getHighLightsByProject(int projectID) {
    String query = "from " + ProjectHighligths.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<ProjectHighligths> list = dao.findAll(query);

    return list;

  }

  @Override
  public int save(ProjectHighligths projectHighlihts) {
    try {
      ProjectHighligths projectHighlihtsPrev = this.find(projectHighlihts.getId());
      if (projectHighlihts.getId() == -1) {
        projectHighlihts.setId(null);
      }

      dao.saveOrUpdate(projectHighlihts);
      if (projectHighlihts.isIsActive()) {
        // Adding new ProjectHighligthsTypes
        Iterator<ProjectHighligthsTypes> typeIterator = projectHighlihts.getProjectHighligthsTypeses().iterator();
        while (typeIterator.hasNext()) {
          ProjectHighligthsTypes projectHighligthsTypes = typeIterator.next();
          ProjectHighligthsTypes existing = this.findProjectHighligthsTypes(
            projectHighligthsTypes.getProjectHighligths().getId(), projectHighligthsTypes.getIdType());
          if (existing == null) {
            projectHighligthsTypes.setId(null);
            dao.saveOrUpdate(projectHighligthsTypes);
          }
        }
        // Adding new ProjectHighligthsCountry
        Iterator<ProjectHighligthsCountry> countriesIterator =
          projectHighlihts.getProjectHighligthsCountries().iterator();
        while (countriesIterator.hasNext()) {
          ProjectHighligthsCountry projectHighligthsTypes = countriesIterator.next();

          ProjectHighligthsCountry existing = this.findProjectHighligthsCountries(
            projectHighligthsTypes.getProjectHighligths().getId(), projectHighligthsTypes.getIdCountry());
          if (existing == null) {
            projectHighligthsTypes.setId(null);
            dao.saveOrUpdate(projectHighligthsTypes);
          }
        }
        // Deleting ProjectHighligthsTypes no selected
        if (projectHighlihtsPrev != null) {
          Iterator<ProjectHighligthsTypes> previousTypeIterator =
            projectHighlihtsPrev.getProjectHighligthsTypeses().iterator();
          while (previousTypeIterator.hasNext()) {
            ProjectHighligthsTypes projectHighligthsTypes = previousTypeIterator.next();
            if (!projectHighlihts.getProjectHighligthsTypeses().contains(projectHighligthsTypes)) {
              dao.delete(projectHighligthsTypes);
            }
          }
          // Deleting ProjectHighligthsCountry no selected
          Iterator<ProjectHighligthsCountry> previousCountriesIterator =
            projectHighlihtsPrev.getProjectHighligthsCountries().iterator();
          while (previousCountriesIterator.hasNext()) {
            ProjectHighligthsCountry projectHighligthsTypes = previousCountriesIterator.next();

            if (!projectHighlihts.getProjectHighligthsCountries().contains(projectHighligthsTypes)) {
              dao.delete(projectHighligthsTypes);
            }
          }
        }
      }

      return projectHighlihts.getId();
    } catch (Exception e) {
      e.printStackTrace();
      return -1;
    }

  }
}