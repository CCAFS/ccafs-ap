/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.ProjectPartnerManagerImpl;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 */
@ImplementedBy(ProjectPartnerManagerImpl.class)
public interface ProjectPartnerManager {

  /**
   * This method deletes a specific project partner from the database
   *
   * @param id of the project partner that will be deleted.
   * @return true if the project partner was deleted or false otherwise.
   */
  public boolean deleteProjectPartner(int id);

  /**
   * Delete a Project Partners information which belongs to the project Id and Institution Id given
   *
   * @param projectId is the id of the project
   * @param partnerId from institution
   * @return true if the deletion process was successful or false otherwise.
   */
  public boolean deleteProjectPartner(Project project, Institution partner);


  /**
   * This method is used to get the list of Project Partners that belongs to a specific project.
   *
   * @param projectId is the id of the project.
   * @return a List of ProjectPartner objects that belongs to the project identified with the given id. If no projects
   *         are found, this method will return an empty list.
   */
  public List<ProjectPartner> getProjectPartners(int projectId);

  /**
   * This method is used to get the list of Project Partners that belongs to a specific project and type (PL, PP, PPA,
   * PC, etc.)
   * 
   * @param projectId is the id of the project.
   * @param projectPartnerType is the type of the Project Partner and can be found from constant variables within the
   *        class APConstants ("Type of Project Partners")
   * @return List of ProjectPartner objects that belongs to the project identified with the given id and type. If no
   *         projects are found, this method will return an empty list.
   */
  public List<ProjectPartner> getProjectPartners(int projectId, String projectPartnerType);

  /**
   * This method saves a Project Partner individually.
   * This method could be used for saving a Project Leader or a Project Coordinator.
   * 
   * @param projectId is the project identifier
   * @param partner is the partner object that is going to be saved.
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return the id of the project partner inserted, 0 if the record was updated and -1 if some error occurred.
   */
  public int saveProjectPartner(int projectId, ProjectPartner partner, User user, String justification);

  /**
   * This method save the project partner of a specific project
   *
   * @param projectId is the project identifier in which these projects partners belong to.
   * @param projectpartnerData is the information to be saved
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return true if all partners were successfully saved; or false otherwise.
   */
  public boolean saveProjectPartners(int projectId, List<ProjectPartner> partners, User user, String justification);


}
