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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLLiaisonInstitutionDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLLiaisonInstitutionDAO.class)
public interface LiaisonInstitutionDAO {

  /**
   * This method gets the liaison institution identified by the value received by parameter.
   * 
   * @param liaisionInstitutionID - Liaison institution identifier
   * @return a Map with the information of the liaison institution.
   */
  public Map<String, String> getLiaisonInstitution(int liaisionInstitutionID);

  /**
   * This method gets the liaison institution to which the user identified by the value received by parameter is linked
   * to
   * 
   * @param userID - User identifier
   * @return a Map with the information.
   */
  public Map<String, String> getLiaisonInstitutionByUser(int userID);

  /**
   * This method return all the liaison institutions contained in the database.
   * 
   * @return a List of maps with the information.
   */
  public List<Map<String, String>> getLiaisonInstitutions();

  /**
   * This method return all the center contained in the database.
   * 
   * @return a List of maps with the information.
   */
  public List<Map<String, String>> getLiaisonInstitutionsCenter();
}
