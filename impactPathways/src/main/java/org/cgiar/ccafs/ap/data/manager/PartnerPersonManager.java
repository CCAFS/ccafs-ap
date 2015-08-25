/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.PartnerPersonManagerImpl;
import org.cgiar.ccafs.ap.data.model.PartnerPerson;
import org.cgiar.ccafs.ap.data.model.ProjectPartner;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
@ImplementedBy(PartnerPersonManagerImpl.class)
public interface PartnerPersonManager {

  /**
   * This method gets a specific partner person identified with the given ID.
   * 
   * @param partnerPersonID is a partner person identifier.
   * @return a PartnerPerson object with the information requested. Or null if nothing found.
   */
  public PartnerPerson getPartnerPerson(int partnerPersonID);

  /**
   * This method gets all the partner persons that are part of a specific project partner.
   * 
   * @param projectPartner is the project partner where the partner persons are contributing.
   * @return a list of partner person objects, an empty list if nothing found or null if some error occurr.
   */
  public List<PartnerPerson> getPartnerPersons(ProjectPartner projectPartner);


}
