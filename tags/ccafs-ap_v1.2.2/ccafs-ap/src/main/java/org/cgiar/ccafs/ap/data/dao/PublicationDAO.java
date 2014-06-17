/*
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
 */

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLPublicationDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(MySQLPublicationDAO.class)
public interface PublicationDAO {

  /**
   * get a list of publications depending on a given leader and logframe identifiers.
   * 
   * @param leaderId - leader identifier
   * @param logframeId - logframe identifier
   * @return a List of Maps with the information of each publications.
   */
  public List<Map<String, String>> getPublications(int leaderId, int logframeId);

  /**
   * Remove all the publications that belong to a specific leader in a certain logframe.
   * 
   * @param leaderId - leader identifier.
   * @param logframeId - logframe identifier.
   * @return true if the remove was successfully made, false if any problem occur.
   */
  public boolean removeAllPublications(int leaderId, int logframeId);

  /**
   * Save a list of publications into the database.
   * 
   * @param publications - List of Maps with all the information of publications.
   * @return the identifier assigned to the new record.
   */
  public int savePublication(Map<String, String> publications);

}
