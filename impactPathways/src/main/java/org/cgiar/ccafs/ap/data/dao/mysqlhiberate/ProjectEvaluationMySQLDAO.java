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


import org.cgiar.ccafs.ap.data.dao.ProjectEvaluationDAO;
import org.cgiar.ccafs.ap.data.model.ProjectEvaluation;

import java.util.List;

import com.google.inject.Inject;

public class ProjectEvaluationMySQLDAO implements ProjectEvaluationDAO {


  private final StandardDAO dao;

  @Inject
  public ProjectEvaluationMySQLDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public ProjectEvaluation getEvaluationProjectByUser(int projectId, String type, Integer programId) {
    String sql = "from " + ProjectEvaluation.class.getName() + " where project_id=" + projectId
      + " and type_evaluation='" + type + "'";


    if (programId != null) {
      sql = sql + " and program_id=" + programId;
    }

    final List<ProjectEvaluation> list = dao.findAll(sql);
    if (list.size() > 0) {
      return list.get(0);
    }
    return null;
  }

  @Override
  public List<ProjectEvaluation> getEvaluationsProject(int projectId) {
    String sql = "from " + ProjectEvaluation.class.getName() + " where project_id=" + projectId;
    final List<ProjectEvaluation> list = dao.findAll(sql);
    return list;
  }

  @Override
  public List<ProjectEvaluation> getEvaluationsProjectExceptUserId(int projectId, String type, Integer programId) {
    String sql = "from " + ProjectEvaluation.class.getName() + " where project_id=" + projectId
      + " and type_evaluation <>'" + type + "'";

    if (programId != null) {
      sql = sql + " and program_id <>" + programId + " or program_id is null";
    }

    final List<ProjectEvaluation> list = dao.findAll(sql);
    return list;
  }

  @Override
  public List<ProjectEvaluation> getSubmitedEvaluations(int projectId) {
    final String sql =
      "from " + ProjectEvaluation.class.getName() + " where project_id=" + projectId + " and is_submited=(1)";
    final List<ProjectEvaluation> list = dao.findAll(sql);
    return list;
  }

  @Override
  public int save(ProjectEvaluation projectEvaluation) {
    dao.saveOrUpdate(projectEvaluation);
    return projectEvaluation.getId().intValue();
  }


}