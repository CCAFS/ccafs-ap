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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectStatusDAO;

import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
@ImplementedBy(MySQLProjectStatusDAO.class)
public interface ProjectStatusDAO {

  /**
   * This method gets the project status information of a given project in a given cycle.
   * 
   * @param projectID is the project identifier related to the status.
   * @param cycle is the project cycle (Planning or Reporting).
   * @return a Map with the information requested. An empty Map if no data was found, or null if some error occurred.
   */
  public Map<String, String> getProjectStatus(int projectID, String cycle);

  /**
   * This method saves into the database the current project status with regards the missing fields.
   * 
   * @param statusData - corresponds to a given project status information to be saved into the database.
   * @return a number greater than 0 meaning the identifier of the new record that was added, 0 if the information was
   *         updated, or -1 if some error occurred.
   */
  public int saveProjectStatus(Map<String, Object> statusData);
}
