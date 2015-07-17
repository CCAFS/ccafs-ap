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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectOutcomeDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego.
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLProjectOutcomeDAO.class)
public interface ProjectOutcomeDAO {

  /**
   * This method gets all the Project Outcome Information by a given project ID
   * 
   * @param projectID - is the id of the project
   * @return a list of Map of the Budgets related with the budget type id and the project id
   */
  public List<Map<String, String>> getProjectOutcomesByProject(int projectID);

  /**
   * This method gets all the Project Outcome information by a given project Id and a year
   * 
   * @param projectID - is the id of the project
   * @param year - is the year of the budget
   * @return a list of Map of the Project Outcome related with the year and the project id
   */
  public Map<String, String> getProjectOutcomesByYear(int projectID, int year);

  /**
   * This method saves the Project Outcome
   * 
   * @param projectOutcomeData
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveProjectOutcome(int projectID, Map<String, Object> projectOutcomeData);


}
