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

import org.cgiar.ccafs.ap.data.manager.impl.MySQLDeliverableTrafficLightDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(MySQLDeliverableTrafficLightDAO.class)
public interface DeliverableTrafficLightDAO {

  /**
   * Get the traffic light information of the deliverable
   * identified with the value received as parameter.
   * 
   * @param deliverableID - deliverable identifier
   * @return a map with the information.
   */
  public Map<String, String> getTrafficLightData(int deliverableID);

  /**
   * Save the traffic light information in the database.
   * 
   * @param trafficLightData - information to save
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveDeliverableTrafficLight(Map<String, Object> trafficLightData);
}
