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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLDeliverableScoreDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal
 */

@ImplementedBy(MySQLDeliverableScoreDAO.class)
public interface DeliverableScoreDAO {

  /**
   * This method get all the scores given by the activity leaders
   * to the deliverable identified by the value received as
   * parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @return A list of maps with the information
   */
  public List<Map<String, String>> getDeliverableScores(int deliverableID);

  /**
   * This method save into the database the score given by an activity leader
   * to the deliverable identified by the value received as parameter.
   * 
   * @param deliverableID - Deliverable identifier
   * @param activityLeaderID - activity leader identifier
   * @param score
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveDeliverableScore(int deliverableID, int activityLeaderID, double score);
}
