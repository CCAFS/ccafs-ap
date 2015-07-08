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

import org.cgiar.ccafs.ap.data.manager.impl.CRPManagerImpl;
import org.cgiar.ccafs.ap.data.model.CRP;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(CRPManagerImpl.class)
public interface CRPManager {

  /**
   * This method gets the list of CRPs that are linked to the project identified by the value received by parameter.
   * 
   * @return a list of CRPs objects with the information.
   */
  public List<CRP> getCrpContributions(int projectID);

  /**
   * This method gets all the list of CRPs.
   * 
   * @return a list of CRP objects with the information.
   */
  public List<CRP> getCRPsList();

  /**
   * This method removes the project contribution to the CRP according to the values received by parameter.
   * 
   * @param projectID - Project identifier
   * @param crpID - CRP Identifier
   * @param userID - User identifier
   * @param justification
   * @return true if the information was saved succesfully. False otherwise.
   */
  public boolean removeCrpContribution(int projectID, int crpID, int userID, String justification);

  /**
   * This method saved the list of CRPs that are linked to the project received by parameter.
   * 
   * @param project - Project object with the information to save
   * @param user - User who is making the change
   * @param Justification
   * @return
   */
  public boolean saveCrpContributions(Project project, User user, String Justification);
}
