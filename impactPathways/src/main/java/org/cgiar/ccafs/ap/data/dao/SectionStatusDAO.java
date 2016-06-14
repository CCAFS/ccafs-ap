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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLSectionStatusDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */
@ImplementedBy(MySQLSectionStatusDAO.class)
public interface SectionStatusDAO {

  /**
   * This method gets the section status information of a given deliverable in a given cycle.
   * 
   * @param deliverableID is the deliverable identifier related to the status.
   * @param cycle is the project cycle (Planning or Reporting).
   * @param section is the name of the section
   * @param year the year of the cycle
   * @return a Map with the information requested. An empty Map if no data was found, or null if some error occurred.
   */
  public Map<String, String> getDeliverableSectionStatus(int deliverableID, String cycle, String section, int year);

  /**
   * This method gets the section status information of a given project in a given cycle.
   * 
   * @param projectID is the project identifier related to the status.
   * @param cycle is the project cycle (Planning or Reporting).
   * @param section is the name of the section
   * @param year the year of the cycle
   * @return a Map with the information requested. An empty Map if no data was found, or null if some error occurred.
   */
  public Map<String, String> getProjectSectionStatus(int projectID, String cycle, String section, int year);

  /**
   * This method gets a list of Section Status data from a specific project in a specific cycle.
   * 
   * @param projectID is some project identifier.
   * @param cycle is the name of the cycle ('Planning' or 'Reporting').
   * @return a list of Map with the information requested.
   */
  public List<Map<String, String>> getProjectSectionStatuses(int projectID, String cycle, int year);

  /**
   * This method saves into the database the current section status with regards the missing fields.
   * 
   * @param statusData - corresponds to a given deliverable status information to be saved into the database.
   * @return a number greater than 0 meaning the identifier of the new record that was added, 0 if the information was
   *         updated, or -1 if some error occurred.
   */
  public int saveDeliverableSectionStatus(Map<String, Object> statusData);

  /**
   * This method saves into the database the current section status with regards the missing fields.
   * 
   * @param statusData - corresponds to a given project status information to be saved into the database.
   * @return a number greater than 0 meaning the identifier of the new record that was added, 0 if the information was
   *         updated, or -1 if some error occurred.
   */
  public int saveProjectSectionStatus(Map<String, Object> statusData);
}
