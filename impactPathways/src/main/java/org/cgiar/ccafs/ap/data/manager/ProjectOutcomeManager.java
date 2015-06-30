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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectOutcomeManagerImpl;
import org.cgiar.ccafs.ap.data.model.ProjectOutcome;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
@ImplementedBy(ProjectOutcomeManagerImpl.class)
public interface ProjectOutcomeManager {

  /**
   * This method gets all the projectOutcomes specified for the year received by parameter and that belongs to the
   * project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @param year
   * @return a ProjectOutcome object
   */
  public ProjectOutcome getProjectOutcomeByYear(int projectID, int year);

  /**
   * This method gets all the project outcomes linked with the project identified by the value received by parameter.
   * 
   * @param projectID - Project identifier
   * @return A map composed by <Year, ProjectOutcome Object>
   */
  public Map<String, ProjectOutcome> getProjectOutcomesByProject(int projectID);

  /**
   * This method save/update the project outcome received by parameter that belongs to the project received by
   * parameter.
   * 
   * @param projectID - Project identifier
   * @param projectOutcomeData - Project outcome to save
   * @param user - The user who call the method.
   * @param justification - Why the insert/update is made.
   * @return
   */
  public boolean saveProjectOutcome(int projectID, ProjectOutcome projectOutcomeData, User user, String justification);


}
