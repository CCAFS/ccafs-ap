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
   * This method is used to get a specific Project Partner identified by the given ID.
   *
   * @param partnerID is a partner identifier.
   * @return The ProjectPartner object identified by the given id. If no project partner is found, this method will
   *         return null value.
   */
  public ProjectPartner getProjectPartner(int partnerID);

  /**
   * Get the list of Project Partner whose a given partner is contributing to.
   * 
   * @param projectPartner is the partner object where we want to know which partners are contributing to it.
   * @return a list of ProjectPartner objects, an empty list if nothing found or a null reference if an error occur.
   */
  public List<ProjectPartner> getProjectPartnerContributors(ProjectPartner projectPartner);

  /**
   * This method returns all the project partners that belong to a specific project.
   * 
   * @param project is the project object which we want to get the list of partners.
   * @return a list of project partners, an empty list if nothing found or null if some error occur.
   */
  public List<ProjectPartner> getProjectPartners(Project project);

  /**
   * This method adds or updates a given project partner.
   * 
   * @param project is the Project where this partner belongs to.
   * @param projectPartner is the Project Partner to be saved. If its id is -1, the method will add it into the
   *        database, if the id is > 0, the project partner will be updated.
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return the id of the project partner inserted, 0 if the record was updated and -1 if some error occurred.
   */
  public int saveProjectPartner(Project project, ProjectPartner projectPartner, User user, String justification);

  /**
   * This method saves a list of project partners that belongs to a specific project
   * 
   * @param project is the project to which these project partners belong.
   * @param partners is a list of ProjectPartner objects with the information to be saved.
   * @param user is the user that is making the change.
   * @param justification is the justification statement.
   * @return true if all partners were successfully saved; false otherwise.
   */
  public boolean saveProjectPartners(Project project, List<ProjectPartner> projectPartners, User user,
    String justification);

  /**
   * TODO - To Review
   * This method deletes a specific project partner from the database
   *
   * @param id of the project partner that will be deleted.
   * @param user the user that is deleting the record.
   * @param justification is the justification statement.
   * @return true if the project partner was deleted or false otherwise.
   */
  @Deprecated
  public boolean z_old_deleteProjectPartner(int id, User user, String justifications);

  /**
   * TODO - To Review
   * Delete a Project Partners information which belongs to the project Id and Institution Id given
   *
   * @param projectId is the id of the project
   * @param partnerId from institution
   * @return true if the deletion process was successful or false otherwise.
   * @deprecated This method must not be used since institutions can now be repeated per project. Thus, the only
   *             differentiation between partners would be the identifier. Please use deleteProjectPartner(id).
   */
  @Deprecated
  public boolean z_old_deleteProjectPartner(Project project, Institution partner);


  /**
   * TODO - To Review
   * This method is used to get the Project Partner that belongs to a specific project.
   *
   * @param partnerId is the id of the partner.
   * @return The ProjectPartner object that belongs to the project identified with the given id. If no projects
   *         are found, this method will return an empty list.
   */
  @Deprecated
  public ProjectPartner z_old_getProjectPartnerById(int partnerId);

  /**
   * TODO - To Review
   * This method is used to get the list of Project Partners that belongs to a specific project.
   *
   * @param projectId is the id of the project.
   * @return a List of ProjectPartner objects that belongs to the project identified with the given id. If no projects
   *         are found, this method will return an empty list.
   */
  @Deprecated
  public List<ProjectPartner> z_old_getProjectPartners(int projectId);

  /**
   * TODO - To Review
   * This method is used to get the list of Project Partners that belongs to a specific project and type (PL, PP, PPA,
   * PC, etc.)
   * 
   * @param projectId is the id of the project.
   * @param projectPartnerType is the type of the Project Partner and can be found from constant variables within the
   *        class APConstants ("Type of Project Partners")
   * @return List of ProjectPartner objects that belongs to the project identified with the given id and type. If no
   *         projects are found, this method will return an empty list.
   * @deprecated
   */
  @Deprecated
  public List<ProjectPartner> z_old_getProjectPartners(int projectId, String projectPartnerType);

  /**
   * TODO - To Review
   * This method saves a Project Partner individually.
   * This method could be used for saving a Project Leader or a Project Coordinator.
   * 
   * @param projectId is the project identifier
   * @param partner is the partner object that is going to be saved.
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return the id of the project partner inserted, 0 if the record was updated and -1 if some error occurred.
   */
  @Deprecated
  public int z_old_saveProjectPartner(int projectId, ProjectPartner partner, User user, String justification);

  /**
   * TODO - To Review
   * This method save the project partner of a specific project
   *
   * @param projectId is the project identifier in which these projects partners belong to.
   * @param partners is the information to be saved
   * @param user is the user that is making the change.
   * @param justification is the justification for the change made.
   * @return true if all partners were successfully saved; or false otherwise.
   */
  @Deprecated
  public boolean z_old_saveProjectPartners(int projectId, List<ProjectPartner> partners, User user,
    String justification);


}
