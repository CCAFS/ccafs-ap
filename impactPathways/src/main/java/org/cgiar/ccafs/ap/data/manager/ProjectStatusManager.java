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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectStatusManagerImpl;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectStatus;

import com.google.inject.ImplementedBy;

@ImplementedBy(ProjectStatusManagerImpl.class)
public interface ProjectStatusManager {

  /**
   * This method gets the project status of a given project in a given cycle.
   * 
   * @param project is the project related to the status.
   * @param cycle is the project cycle (Planning or Reporting).
   * @return ProjectStatus object with all the information encapsulated on it.
   */
  public ProjectStatus getProjectStatus(Project project, String cycle);

  /**
   * This method saves into the database the current project status with regards the missing fields.
   * 
   * @param status - corresponds to a given project status
   * @param project - is the project where the status will be related to.
   * @return a number greater than 0 meaning the identifier of the new record that was added, 0 if the nformation was
   *         updated, or -1 if some error occurred.
   */
  public int saveProjectStatus(ProjectStatus status, Project project);
}
