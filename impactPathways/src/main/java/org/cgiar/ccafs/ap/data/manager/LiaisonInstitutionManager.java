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

import org.cgiar.ccafs.ap.data.manager.impl.LiaisonInstitutionManagerImpl;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(LiaisonInstitutionManagerImpl.class)
public interface LiaisonInstitutionManager {

  /**
   * This method gets the liaison institution identified by the value received by parameter.
   * 
   * @param liaisionInstitutionID - Liaison institution identifier
   * @return a liaison institution object.
   */
  public LiaisonInstitution getLiaisonInstitution(int liaisionInstitutionID);

  /**
   * This method gets the liaison institution to which the user identified by the value received by parameter is linked
   * if any
   * 
   * @param userID - user identifier
   * @return a list for liaison institution object if the user is linked to any. False otherwise.
   */
  public List<LiaisonInstitution> getLiaisonInstitutionByUser(int userID);

  /**
   * This method return all the liaison institutions contained in the database.
   * 
   * @return a List of maps with the information.
   */
  public List<LiaisonInstitution> getLiaisonInstitutions();

  /**
   * This method return all the center in the database.
   * 
   * @return a List of maps with the information.
   */
  public List<LiaisonInstitution> getLiaisonInstitutionsCenter();

  /**
   * This method return all the center for crps in the database.
   * 
   * @return a List of maps with the information.
   */
  public List<LiaisonInstitution> getLiaisonInstitutionsCrpsIndicator();

  /**
   * This method return all the center for crps in the database.
   * 
   * @return a List of maps with the information.
   */
  public List<LiaisonInstitution> getLiaisonInstitutionSynthesis();
}
