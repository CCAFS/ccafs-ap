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
import org.cgiar.ccafs.ap.data.model.CaseStudieIndicators;
import org.cgiar.ccafs.ap.data.model.CasesStudies;

import java.util.Iterator;
import java.util.List;

import com.google.inject.Inject;

public class ProjectCaseStudiesMySQLDAO implements ProjectCaseStudiesDAO {

  private StandardDAO dao;

  @Inject
  public ProjectCaseStudiesMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteCaseStudie(int caseStudyId, int userID, String justification) {
    CasesStudies project = this.find(caseStudyId);
    project.setIsActive(false);
    project.setModifiedBy(new Long(userID));
    project.setModificationJustification(justification);
    return this.save(project) == 1; // TODO To Review
  }

  @Override
  public boolean deleteCaseStudiesByProject(int projectID) {
    String query = "from " + CasesStudies.class.getName() + " where project_id=" + projectID + " and is_active=1";
    List<CasesStudies> list = dao.findAll(query);
    boolean save = true;
    for (CasesStudies projectHighligths : list) {
      projectHighligths.setIsActive(false);
      if (this.save(projectHighligths) == -1) { // TODO To review
        save = false;
      }
    }
    return save;
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
    return dao.find(CasesStudies.class, id);
  }


  public CaseStudieIndicators findCaseStudieIndicador(int caseStudieId, int indicator) {
    String query = "from " + CaseStudieIndicators.class.getName() + " where id_case_studie=" + caseStudieId
      + " and id_indicator=" + indicator;

    List<CaseStudieIndicators> list = dao.findAll(query);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }


  @Override
  public List<CasesStudies> getCaseStudiesByProject(int projectID) {
    String query = "from " + CasesStudies.class.getName() + " where project_id=" + projectID + " and is_active=1";
    return dao.findAll(query);
  }

  @Override
  public int save(CasesStudies casesStudies) {
    try {
      CasesStudies casesStudiesPrev = null; // TODO To review
      if (casesStudies.getId() != null) {
        casesStudiesPrev = this.find(casesStudies.getId());
      }
      dao.saveOrUpdate(casesStudies);
      if (casesStudies.isIsActive()) {

        // Adding new CaseStudieIndicators
        CaseStudieIndicators caseStudieIndicators;
        CaseStudieIndicators existing;
        Iterator<CaseStudieIndicators> indicatorIndicator = casesStudies.getCaseStudieIndicatorses().iterator();
        while (indicatorIndicator.hasNext()) {
          caseStudieIndicators = indicatorIndicator.next();
          existing = this.findCaseStudieIndicador(casesStudies.getId(), caseStudieIndicators.getIdIndicator());

          if (existing == null) {
            caseStudieIndicators.setId(null);
            dao.saveOrUpdate(caseStudieIndicators);
          }

        }
        // Removing CaseStudieIndicators do not select

        // Deleting ProjectHighligthsTypes no selected
        if (casesStudiesPrev != null) {
          Iterator<CaseStudieIndicators> previousCaseStudieIndicatorsIterator =
            casesStudiesPrev.getCaseStudieIndicatorses().iterator();
          while (previousCaseStudieIndicatorsIterator.hasNext()) {
            caseStudieIndicators = previousCaseStudieIndicatorsIterator.next();
            if (!casesStudies.getCaseStudieIndicatorses().contains(caseStudieIndicators)) {
              dao.delete(caseStudieIndicators);
            }
          }
        }
      }

      return casesStudies.getId(); // TODO To review
    } catch (

    Exception e)

    {
      e.printStackTrace();
      return -1;
    }

  }
}