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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLSubmissionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
@ImplementedBy(MySQLSubmissionDAO.class)
public interface SubmissionDAO {

  /**
   * This method returns a list of submissions data from a specific project.
   * 
   * @param projectID is a project identifier.
   * @return a list of Map objects with the information requested, an empty list if nothing found or null if some error
   *         occurred.
   */
  public List<Map<String, String>> getProjectSubmissions(int projectID);

  /**
   * This method saves a specific submission data into the project_submissions table.
   * 
   * @param projectID is a project identifier where it is going to be saved the submission data.
   * @param submissionData is the submission information to be saved.
   * @return a number greater than 0 with the id of the new record added, a number equal to 0 if the information was
   *         updated, or -1 if some error occurred.
   */
  public int saveProjectSubmission(int projectID, Map<String, String> submissionData);
}
