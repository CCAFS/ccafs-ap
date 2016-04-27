<<<<<<< HEAD
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


  public ProjectEvaluation getEvaluationProjectByUser(int projectId, int userId);

  public List<ProjectEvaluation> getEvaluationsProject(int projectId);

  public List<ProjectEvaluation> getSubmitedEvaluations(int projectId);

  public int save(ProjectEvaluation projectEvaluation);
}
=======
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
   * This method gets the Project Evaluation information by a given project id and user id
   * 
   * @param projectId - is the Id of the project
   * @param userId - is the Id of the user
   * @return a ProjectEvaluation with the Information related with the project id and user id, null if ProjectEvaluation
   *         doesn't exist
   */
  public ProjectEvaluation getEvaluationProjectByUser(int projectId, int userId);


  /**
   * This method gets all the ProjectEvaluations information by a given project id
   * 
   * @param projectId - is the Id of the project
   * @return a List of ProjectEvaluation with the Information related with the project id
   */
  public List<ProjectEvaluation> getEvaluationsProject(int projectId);

  /**
   * This method saves the information of the given ProjectEvalution
   */
  public int save(ProjectEvaluation projectEvaluation);
}
>>>>>>> 20cb6d7c4796b8db5858db7e38e88c59871edda5
