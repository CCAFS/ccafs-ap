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

import java.util.List;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectPartnerManagerImpl;

import com.google.inject.ImplementedBy;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
@ImplementedBy(ProjectPartnerManagerImpl.class)
public interface ProjectPartnerManager {

  /**
   * This method is used to identify the project partner leader of a specific project
   *
   * @param projectId is the id of the project
   * @return a ProjectPartner object that represents the leader of the project, or NULL if nothing found.
   */
  public ProjectPartner getProjectPartnerLeader(int projectId);

  /**
   * This method is used to get the list of Project Partners that belongs to a specific project.
   *
   * @param projectId is the id of the project.
   * @return a List of ProjectPartner objects that belongs to the project identified with the given id. If no projects
   *         are found, this method will return an empty list.
   */
  public List<ProjectPartner> getProjectPartners(int projectId);

}
