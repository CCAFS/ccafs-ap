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

package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.HistoryManagerImpl;
import org.cgiar.ccafs.ap.data.model.LogHistory;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(HistoryManagerImpl.class)
public interface HistoryManager {

  /**
   * This method returns the last five changes made in the interface of
   * activities to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getActivitiesHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of project CCAFS outcomes to the project identified
   * by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public List<LogHistory> getCCAFSOutcomesHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of project budget by MOGs to the project
   * identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public List<LogHistory> getProjectBudgetByMogHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of project budget to the project
   * identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<LogHistory> getProjectBudgetHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of
   * project case studies to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getProjectCaseStudyHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of project deliverables to the deliverable
   * identified by the value received by parameter.
   * 
   * @param deliverableID
   * @return
   */
  public List<LogHistory> getProjectDeliverablesHistory(int deliverableID);

  /**
   * This method returns the last five changes made in the interface of
   * project description to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getProjectDescriptionHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of
   * project highlights to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getProjectHighLights(int projectID);

  /**
   * This method returns the last five changes made in the interface of project other contribution to the project
   * identified by the value received by parameter.
   * 
   * @param projectID
   * @return
   */
  public List<LogHistory> getProjectIPOtherContributionHistory(int projectID);


  /**
   * This method returns the last five changes made in the interface of
   * project leverage to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getProjectLeverage(int projectID);

  /**
   * This method returns the last five changes made in the interface of
   * project locations to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getProjectLocationsHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of
   * project next study to the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public <T> List<LogHistory> getProjectNextUser(int projectID);

  /**
   * This method returns the last five changes made in the interface of project outcomes to the project identified by
   * the
   * value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public List<LogHistory> getProjectOutcomeHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of project outputs (overviewByMOGs) to the project
   * identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of logHistory objects that contains the information.
   */
  public List<LogHistory> getProjectOutputsHistory(int projectID);

  /**
   * This method returns the last five changes made in the interface of project partners (Partner lead) to the project
   * identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of maps with the information
   */
  public List<LogHistory> getProjectPartnersHistory(int projectID);
}
