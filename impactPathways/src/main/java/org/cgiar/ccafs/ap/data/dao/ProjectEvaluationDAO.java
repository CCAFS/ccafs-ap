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


package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysqlhiberate.ProjectEvaluationMySQLDAO;
import org.cgiar.ccafs.ap.data.model.ProjectEvaluation;

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectEvaluationMySQLDAO.class)
public interface ProjectEvaluationDAO {

  /**
   * Execute the query and get the evaluation project information by the project and user Id's
   * 
   * @param projectId - is the ID of the project.
   * @param userId - is the ID of the user
   * @return
   */
  public ProjectEvaluation getEvaluationProjectByUser(int projectId, int userId);

  /**
   * Execute the query and get all evaluations of specific project.
   * 
   * @param projectId - is the ID of the project to Query
   * @return
   */
  public List<ProjectEvaluation> getEvaluationsProject(int projectId);

  /**
   * Execute the query and get only submit evaluations of the project.
   * 
   * @param projectId - is the ID of the project.
   * @return
   */
  public List<ProjectEvaluation> getSubmitedEvaluations(int projectId);

  /**
   * Execute the insert sentence to save the Project Evaluation information to the data base.
   * 
   * @param projectEvaluation - the project evaluation to save.
   * @return
   */
  public int save(ProjectEvaluation projectEvaluation);
}

