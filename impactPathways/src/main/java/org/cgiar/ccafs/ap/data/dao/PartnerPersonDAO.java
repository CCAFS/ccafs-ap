/*****************************************************************
 * This file is part of CCAFS Planning and Reporting Platform. CCAFS P&R is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or at your option) any later version. CCAFS P&R is distributed in the hope that it
 * will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a copy of the GNU
 * General Public License along with CCAFS P&R. If not, see <http://www.gnu.org/licenses/>.
 * ***************************************************************
 */
package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPartnerPersonDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLPartnerPersonDAO.class)
public interface PartnerPersonDAO {

  /**
   * This method gets a specific partner person identified with the given id.
   * 
   * @param partnerPersonID is the partner person identifier.
   * @return an object PartnerPerson. An empty list if nothing found or null if some error occur.
   */
  public Map<String, String> getPartnerPerson(int partnerPersonID);

  /**
   * This method gets the partner persons that belongs to a given partner.
   * 
   * @param projectPartnerID is the project partner identifier.
   * @return a list of maps with the information requested, an empty list if nothing found or null if a an error occur.
   */
  public List<Map<String, String>> getPartnerPersonsByPartnerID(int projectPartnerID);


}