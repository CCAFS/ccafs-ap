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

import org.cgiar.ccafs.ap.data.manager.impl.InstitutionManagerImpl;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */

@ImplementedBy(InstitutionManagerImpl.class)
public interface InstitutionManager {

  /**
   * This method deletes a project partner contribution.
   * 
   * @param projectPartnerID is the project partner id
   * @param institutionID is the institution id.
   * @return true if the information was successfully deleted, false otherwise.
   */
  public boolean deleteProjectPartnerContributeInstitution(int projectPartnerID, int institutionID);

  /**
   * This method deletes a project partner contribution from a given project partner and institution.
   * 
   * @param projectPartner is a project partner
   * @param institution is an institution.
   * @return true if the records could be successfully deleted, false otherwise.
   */
  public boolean deleteProjectPartnerContributeInstitution(ProjectPartner projectPartner, Institution institution);

  /**
   * Return all the institutions.
   * 
   * @return an Institution object or null if the id does not exist in the database.
   */
  public List<Institution> getAllInstitutions();

  /**
   * This method get all the institutions types that manage the platform.
   * 
   * @return a List of InstitutionType objects.
   */
  public List<InstitutionType> getAllInstitutionTypes();

  /**
   * Get all the institutions that are part of the CCAFS PPA (Program Participant Agreement)
   * 
   * @return the list of PPA institutions or an empty list if nothing found.
   */
  public List<Institution> getAllPPAInstitutions();

  /**
   * Get an institution identified with the given id.
   * 
   * @param institutionId is an integer that represents the id of some institution.
   * @return an Institution object or null if the id does not exist in the database.
   */
  public Institution getInstitution(int institutionId);

  /**
   * This method returns all the institutions that have the
   * same type as received as parameter and that are located
   * in the same country received by parameter.
   * 
   * @param type - Institution type object
   * @param country - Country object
   * @return a list of Institution objects
   */
  public List<Institution> getInstitutionsByTypeAndCountry(InstitutionType type, Country country);

  /**
   * This method gets the institutions related with the user given
   * 
   * @param user
   * @return a list of institution objects with the information
   */
  public List<Institution> getInstitutionsByUser(User user);

  /**
   * This method gets the information of an Institution Type by a give Institution Type ID
   * 
   * @param institutionTypeId
   * @return an Institution Type Object with the information
   */
  public InstitutionType getInstitutionType(int institutionTypeId);

  /**
   * This method gets the institutions leading projects
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary report project leading institutions.
   * 
   * @return a list of objects with the leading project institutions information
   */
  public List<Map<String, Object>> getProjectLeadingInstitutions();

  /**
   * This method will return the list of all the institutions that are project partners.
   * This method also gets all the information in one single query to the database, improving its performance and
   * letting the charge to the RAM memory.
   * This method should be used to the summary report project partner institutions.
   * 
   * @return a list of objects with the Project Partner Institutions information.
   */
  public List<Map<String, Object>> getProjectPartnerInstitutions();

  /**
   * This method returns the user's main institution defined in the database.
   * 
   * @param user
   * @return an Institution Object with the information
   */
  public Institution getUserMainInstitution(User user);

  /**
   * Save a single project partner contribution.
   * 
   * @param projectPartnerID is the project partner id.
   * @param institutionID is the institution id
   * @return 0 if the record was saved or -1 if some error occurred.
   */
  public int saveProjectPartnerContributeInstitution(int projectPartnerID, int institutionID);

  /**
   * This method adds or updates a project partner contributions.
   * 
   * @param projectPartnerID is the project partner id
   * @param contributeInstitutions is the list of institutions that this project partner is contributing to.
   * @return true if the information was successfully saved, false otherwise.
   */
  public boolean
    saveProjectPartnerContributeInstitutions(int projectPartnerID, List<Institution> contributeInstitutions);

  /**
   * This method validates if the institution of the given projectPartner is the lastone institution of all the other
   * partners.
   * 
   * @param projectPartnerID is the project partner identifier.
   * @return true if is the last one, false otherwise.
   */
  public boolean validateLastOneInstitution(int projectPartnerID);
}