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
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLInstitutionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLInstitutionDAO.class)
public interface InstitutionDAO {

  /**
   * This method returns all the Institutions
   * indicated by parameter.
   *
   * @return a list of maps with the information of all Institutions returned .
   */

  public List<Map<String, String>> getAllInstitutions();

  /**
   * This method returns all the information from the Institutions Type
   *
   * @return a list of map with the information of the institution
   */
  public List<Map<String, String>> getAllInstitutionTypes();

  /**
   * This method returns the information from Institution given by an institutionID
   *
   * @param institutionID, identifier of the institution
   * @return a list of maps with the information of all IP elements returned.
   */

  public Map<String, String> getInstitution(int institutionID);

  /**
   * This method gets the information of the institutions related to the user
   * given
   *
   * @param userID - User identifier
   * @return a list of maps with the information with the Institution related by the user
   */
  public List<Map<String, String>> getInstitutionsByUser(int userID);

  /**
   * This method returns the information of an Institution Type given by and InstitutionType ID
   *
   * @param institutionTypeID
   * @return a map with the information of the Institution Type
   */
  public Map<String, String> getInstitutionType(int institutionTypeID);

  /**
   * This method returns the information of the institution marked as
   * main for the user identified with the id given
   *
   * @param userID - User identifier
   * @return a list of maps with the information of the Institution
   */
  public Map<String, String> getUserMainInstitution(int userID);
}
