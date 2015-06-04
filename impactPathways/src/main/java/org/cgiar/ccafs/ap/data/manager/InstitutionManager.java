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
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Hernán David Carvajal B.
 */

@ImplementedBy(InstitutionManagerImpl.class)
public interface InstitutionManager {

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
   * This method returns the user's main institution defined in the database.
   * 
   * @param user
   * @return an Institution Object with the information
   */
  public Institution getUserMainInstitution(User user);
}