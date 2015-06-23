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
}
