/*
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
 */

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLSubmissionDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLSubmissionDAO.class)
public interface SubmissionDAO {

  /**
   * Get if there is a submission by the given leader
   * corresponding to the given logframe in the given section.
   * 
   * @param activityLeaderId - Leader identifier
   * @param logframeId - Logframe identifier
   * @param section
   * @return true if the workplan have been submitted. False otherwise.
   */
  public Map<String, String> getSubmission(int activityLeaderId, int logframeId, String section);

  /**
   * Submit the leader's workplan for the logframe given in the section indicated.
   * 
   * @param activityLeaderId - Leader identifier
   * @param logframeId - Logframe identifier
   * @return true if the submission was successfully saved. False otherwise.
   */
  public boolean submit(int activityLeaderId, int logframeId, String section);

}
