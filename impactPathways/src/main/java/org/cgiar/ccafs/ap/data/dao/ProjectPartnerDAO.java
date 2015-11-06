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
   * This method deactive all the contributions to a project partner identified by the value received by parameter.
   * 
   * @param projectPartnerID - project partner identifier.
   * @return true if the query was executed successfully. False otherwise.
   */
  public boolean deleteProjectPartnerContributions(int projectPartnerID);

  /**
   * This method returns the information of the project partners with the information of their institutions name
   * 
   * @return a list of Map with the information requested, or an empty List if nothing found. Or null if some error
   *         occurs.
   */
  public List<Map<String, String>> getAllProjectPartnersPersonsWithTheirInstitution();

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
   * This method gets the project partner to which the partner person identified by the values received by parameter is
   * linked to.
   * 
   * @param projectPartnerPersonID - Project partner person identifier
   * @return a map with the project partner information, the map also will include the partner person information. If no
   *         information was found this method will return an empty map.
   */
  public Map<String, String> getProjectPartnerByPersonID(int projectPartnerPersonID);

  /**
   * Get the list of project partners that are contributing to a given project partner.
   * 
   * @param projectPartnerID is the project partner identifier whose project partners are contributing to
   * @return a list of Maps with the information requested, an empty List if nothing found and null if an error occur.
   */
  public List<Map<String, String>> getProjectPartnerContributors(int projectPartnerID);


  /**
   * This method gets the project partners information given the project Id
   * 
   * @param projectId - is the id of a project
   * @return a list of project partners that belong to the given project; an empty list if nothing was found or NULL if
   *         an error occurred.
   */
  public List<Map<String, String>> getProjectPartners(int projectId);

  /**
   * This method saves into the database a new Project Partner
   * 
   * @param projectPartnerData - Information to be saved
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveProjectPartner(Map<String, Object> projectPartnerData);

  /**
   * This method saves into the database a new project partner contribution
   * 
   * @param partnerContributionData- Information to be saved.
   * @return The last inserted id if there was a new record, 0 if the record was updated or -1 if any error happened.
   */
  public int saveProjectPartnerContribution(Map<String, Object> partnerContributionData);

  /**
   * This method is used to export all the current and active partners that are working with CCAFS in an XML format for
   * the CCAFS Web-site.
   * 
   * @return a List of Maps with the information populated on it.
   */
  public List<Map<String, Object>> summaryGetActivePartners();

  /**
   * This method is used to export all the partners not logged in P&R in an XML format for
   * the CCAFS Web-site.
   * 
   * @return a List of Maps with the information populated on it.
   */
  public List<Map<String, Object>> summaryGetNotLoggedInPartners();
}
