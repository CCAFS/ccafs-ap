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

import org.cgiar.ccafs.ap.data.dao.ProjectEvaluationDAO;
import org.cgiar.ccafs.ap.data.manager.ProjectEvalutionManager;
import org.cgiar.ccafs.ap.data.model.ProjectEvaluation;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.Inject;

public class ProjectEvaluationManagerImpl implements ProjectEvalutionManager {


  public ProjectEvaluationDAO dao;

  @Inject

  public ProjectEvaluationManagerImpl(ProjectEvaluationDAO dao) {
    this.dao = dao;
  }


  @Override
  public ProjectEvaluation getEvaluationProjectByUser(int projectId, int userId) {
    return dao.getEvaluationProjectByUser(projectId, userId);
  }

  @Override
  public List<ProjectEvaluation> getEvaluationsProject(int projectId) {
    return dao.getEvaluationsProject(projectId);
  }

  @Override
  public int saveProjectEvalution(ProjectEvaluation projectEvaluation, User user, String justification) {

    if (projectEvaluation.getId() == null) {
      projectEvaluation.setCreatedBy(Long.parseLong(user.getId() + ""));
    }
    projectEvaluation.setModifiedBy(Long.parseLong(user.getId() + ""));
    projectEvaluation.setModificationJustification(justification);
    projectEvaluation.setIsActive(true);
    return dao.save(projectEvaluation);
  }

}
