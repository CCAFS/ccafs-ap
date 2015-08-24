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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectPartnerDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Héctor Fabio Tobón R.
 * @author Javier Andrés Gallego.
 */
@ImplementedBy(MySQLProjectPartnerDAO.class)
public interface ProjectPartnerDAO {

  /**
   * This method deletes the project partner given the project Id and the institution Id
   *
   * @param projectId - is the id of a project
   * @param partnerId - is the id related to a Institution
   * @return true if the elements were deleted successfully. False otherwise
   */
  public boolean deleteProjectPartner(int projectId, int partnerId);

  /**
   * This method deletes a project partner from the database.
   *
   * @param id of the project partner to be deleted.
   * @param userID is the user identifier that wants to delete it.
   * @param justification is the justification statement.
   * @return true if the record could be successfully deleted, false otherwise.
   */
  public boolean deleteProjectPartner(int id, int userID, String justification);

  /**
   * This method gets a project partner information identified with the given ID.
   *
   * @param partnerID - is a partner identifier
   * @return a Map with the information of a project partner identified with the given id; an empty list if nothing was
   *         found or NULL if
   *         an error occurred.
   */
  public Map<String, String> getProjectPartner(int partnerID);

  /**
   * This method gets the project partners information given the project Id
   *
   * @param projectId - is the id of a project
   * @return a list of project partners that belong to the given project; an empty list if nothing was found or NULL if
   *         an error occurred.
   */
  public List<Map<String, String>> getProjectPartners(int projectId);

  /**
   * This method gets the project partners information given the project Id and type of project partner
   * 
   * @param projectId is the id of a project
   * @param projectPartnerType is the type of a project partner (PPA, PL, PC, etc.).
   * @return a list of project partners that belong to the given project and project partner type; an empty list if
   *         nothing was found or NULL if an error occurred.
   */
  public List<Map<String, String>> getProjectPartners(int projectId, String projectPartnerType);


  /**
   * This method saves into the database a new Project Partner
   *
   * @param projectPartnerData - Information to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveProjectPartner(Map<String, Object> projectPartnerData);


}
