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


import org.cgiar.ccafs.ap.data.dao.ProjectCaseStudiesDAO;
import org.cgiar.ccafs.ap.data.manager.CaseStudiesManager;
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Garcia
 */
public class CaseStudiesManagerImpl implements CaseStudiesManager {

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(CaseStudiesManagerImpl.class);

  // DAO's
  private ProjectCaseStudiesDAO caseStudyDAO;
  // Managers


  @Inject
  public CaseStudiesManagerImpl(ProjectCaseStudiesDAO caseStudyDAO) {
    this.caseStudyDAO = caseStudyDAO;


  }

  @Override
  public boolean deleteCaseStudy(int caseStudyID, User user, String justification) {
    boolean problem = false;
    // Deleting caseStudy.
    boolean deleted = caseStudyDAO.deleteCaseStudie(caseStudyID, user.getId(), justification);
    if (!deleted) {
      problem = true;
    }


    return !problem;
  }


  @Override
  public boolean deleteCaseStudysByProject(int projectID) {
    return caseStudyDAO.deleteCaseStudiesByProject(projectID);
  }


  @Override
  public boolean existCaseStudy(int caseStudyID) {
    return caseStudyDAO.existCaseStudy(caseStudyID);
  }

  @Override
  public CasesStudies getCaseStudyById(int caseStudyID) {

    CasesStudies caseStudy = caseStudyDAO.find(caseStudyID);


    return caseStudy;


  }


  @Override
  public List<CasesStudies> getCaseStudysByProject(int projectID) {
    List<CasesStudies> caseStudyList = caseStudyDAO.getCaseStudiesByProject(projectID);


    return caseStudyList;
  }


  @Override
  public int saveCaseStudy(int projectID, CasesStudies caseStudy, User user, String justification) {
    if (caseStudy.getId() == null || caseStudy.getId() == -1) {
      caseStudy.setCreatedBy(Long.parseLong(user.getId() + ""));
      // caseStudy.setActiveSince(new Date());
      caseStudy.setId(null);
    }
    caseStudy.setProjectId(projectID);
    caseStudy.setModifiedBy(Long.parseLong(user.getId() + ""));
    caseStudy.setModificationJustification(justification);
    int result = caseStudyDAO.save(caseStudy);

    if (result > 0) {
      LOG.debug("saveCaseStudy > New CaseStudy added with id {}", result);
    } else if (result == 0) {
      LOG.debug("saveCaseStudy > CaseStudy with id={} was updated", caseStudy.getId());
    } else {
      LOG.error("saveCaseStudy > There was an error trying to save/update a CaseStudy from projectId={}", projectID);
    }
    return result;
  }


}
