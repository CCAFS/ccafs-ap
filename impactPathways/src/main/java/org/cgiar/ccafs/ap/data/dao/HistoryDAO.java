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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLHistoryDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLHistoryDAO.class)
public interface HistoryDAO {

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project CCAFS outcomes to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getCCAFSOutcomesHistory(int projectID);

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project outputs (overview by MOGs) to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectBudgetHistory(int projectID);

  /**
   * This method return the last five changes made in the interface of project deliverables to the deliverable
   * identified by the value received by parameter.
   * 
   * @param deliverableID
   * @return
   */
  public List<Map<String, String>> getProjectDeliverablesHistory(int deliverableID);

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project description to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectDescriptionHistory(int projectID);

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project Other contributions to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectIPOtherContributionHistory(int projectID);


  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project locations to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectLocationsHistory(int projectID);

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project outcomes to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectOutcomeHistory(int projectID);

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project outputs (overview by MOGs) to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectOutputsHistory(int projectID);

  /**
   * This method return the last five changes (only the user, date, action and justification) made in the interface of
   * project partners (Partner lead) to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @param partnerType - Array of string with the type of partners (ex. 'PL', 'PPA')
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getProjectPartnerHistory(int projectID, String[] partnerType);
}
