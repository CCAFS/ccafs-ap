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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPIndicatorDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Hernán David Carvajal.
 */
@ImplementedBy(MySQLIPIndicatorDAO.class)
public interface IPIndicatorDAO {

  /**
   * This method removes all the indicators related with the ipElement
   * identified by the parameter received.
   * 
   * @param ipElementID - ipElement identifier
   * @return true if the information was successfully removed, false otherwise.
   */
  public boolean deleteIpElementIndicators(int ipElementID);

  /**
   * This method returns from the database the information of the indicator
   * identified by the value received as parameter.
   * 
   * @param indicatorID - indicator identifier
   * @return a map with the information.
   */
  public Map<String, String> getIndicator(int indicatorID);

  /**
   * This function returns all the indicators corresponding to the given
   * ip element
   * 
   * @param ipProgramElementID - IP Program Element identifier
   * @return a list of maps with the information.
   */
  @Deprecated
  public List<Map<String, String>> getIndicatorsByIpProgramElementID(int ipProgramElementID);

  /**
   * This method returns a list of indicators which have as parent the
   * indicator identified by the id received as parameter.
   * 
   * @param parentIndicatorID - parent indicator identifier
   * @return a list of maps with the information
   */
  public List<Map<String, String>> getIndicatorsByParent(int parentIndicatorID);

  /**
   * This method gets all the indicators related with the project identifier
   * received as parameter.
   * 
   * @param projectID - Project identifier
   * @return a list of ip indicators object with the information.
   */
  public List<Map<String, String>> getIndicatorsByProjectID(int projectID);

  /**
   * This method returns a list with all the indicators present
   * in the database.
   * 
   * @return a list of maps with the information.
   */
  public List<Map<String, String>> getIndicatorsList();

  /**
   * This method save the information of the indicator.
   * 
   * @param indicatorData - the information to be saved
   * @return the last inserted id if any or 0 if some record was updated or -1 if any error occurred.
   */
  public int saveIndicator(Map<String, Object> indicatorData);

}
