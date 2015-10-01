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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLCrpDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */
@ImplementedBy(MySQLCrpDAO.class)
public interface CrpDAO {

  /**
   * This method gets the information of an specific CRP by its ID
   * 
   * @param crpID
   * @return a Map with the information of an specific CRP by its ID
   */
  public Map<String, String> getCRPById(int crpID);

  /**
   * This method gets the list of CRPs that are linked to the project identified by the value received by parameter.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCrpContributions(int projectID);

  /**
   * This method gets the list of Nature of Collaboration that a CRPs collaborate to Other Contribution linked to the
   * project identified by the value received by parameter.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCrpContributionsNature(int projectID);

  /**
   * This method gets all the list of CRPs.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getCRPsList();

  /**
   * This method removes the project contribution to the CRP according to the values received by parameter.
   * 
   * @param projectID - Project identifier
   * @param crpID - CRP Identifier
   * @param userID - User identifier
   * @param justification
   * @return true if the information was saved succesfully. False otherwise.
   */
  public boolean removeCrpContributionNature(int projectID, int crpID, int userID, String justification);

  /**
   * This method saves into the database the CRP contributions made by the project received by parameter.
   * 
   * @param projectID - Project identifier
   * @param contributionData - Information to save
   * @return True if the information was saved successfully. False otherwise.
   */
  public boolean saveCrpContributions(int projectID, Map<String, Object> contributionData);

  /**
   * This method saves into the database the CRP contributions nature made by the project received by parameter.
   * 
   * @param projectID - Project identifier
   * @param contributionNatureData - Information to save
   * @return True if the information was saved successfully. False otherwise.
   */
  public boolean saveCrpContributionsNature(int projectID, Map<String, Object> contributionData);

}
