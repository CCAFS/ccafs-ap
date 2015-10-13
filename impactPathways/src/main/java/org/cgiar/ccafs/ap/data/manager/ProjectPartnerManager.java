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
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
@ImplementedBy(ProjectPartnerManagerImpl.class)
public interface ProjectPartnerManager {

  /**
   * This method delete the project partner received by parameter from the database.
   * 
   * @param projectPartner - Project partner object
   * @param currentUser - User who is making the change
   * @param justification
   * @return true if the projectPartner was deleted successfully. False otherwise.
   */
  public boolean deleteProjectPartner(ProjectPartner projectPartner, User user, String justification);

  /**
   * This method de-active all the contributions made to the project partner identified by the value received by
   * parameter.
   * 
   * @param projectPartner - Project partner object
   * @return true if the contributions was deleted successfully. False otherwise.
   */
  public boolean deleteProjectPartnerContributions(ProjectPartner projectPartner);

  /**
   * This is an auxiliar method used for to get the partner person list of the project partners
   * 
   * @return
   */
  public Map<String, String> getAllProjectPartnersPersonWithTheirPartners();

  /**
   * This method is used to get a specific Project Partner identified by the given ID.
   * 
   * @param partnerID is a partner identifier.
   * @return The ProjectPartner object identified by the given id. If no project partner is found, this method will
   *         return null value.
   */
  public ProjectPartner getProjectPartner(int partnerID);

  /**
   * This method gets the project partner to which the partner person identified by the values received by parameter is
   * linked to.
   * 
   * @param projectPartnerPersonID - Project partner person identifier
   * @return Project partner object with the information, the map also will include the partner person information. If
   *         no
   *         information was found this method will return null.
   */
  public ProjectPartner getProjectPartnerByPersonID(int projectPartnerPersonID);

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
   * This method saves into the database the contributions made by some project partner(s) to the partner received by
   * parameter
   * 
   * @param projectID - Project identifier
   * @param projectPartner - Project partner object with the information to be saved
   * @param user - User who is making the change
   * @param justification
   * @return true if the information was saved successfully, false otherwise.
   */
  public boolean saveProjectPartnerContributions(int projectID, ProjectPartner projectPartner, User user,
    String justification);


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

}
