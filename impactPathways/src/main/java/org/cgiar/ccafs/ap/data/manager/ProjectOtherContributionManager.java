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
package org.cgiar.ccafs.ap.data.manager;

import org.cgiar.ccafs.ap.data.manager.impl.IPOtherContributionManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPOtherContribution;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
@ImplementedBy(IPOtherContributionManagerImpl.class)
public interface IPOtherContributionManager {


  /**
   * This method removes a specific IP Other Contribution value from the database.
   * 
   * @param ipOtherContributionId is the IP Other Contribution identifier.
   * @return true if the IP Other Contribution was successfully deleted, false otherwise.
   */
  public boolean deleteIPOtherContribution(int ipOtherContributionId);

  /**
   * This method removes a set of IP Other Contributions that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of IP Other Contributions were successfully deleted, false otherwise.
   */
  public boolean deleteIPOtherContributionsByProjectId(int projectID);

  /**
   * This method gets all the IP Other Contribution information by a given IP Other Contribution ID.
   * 
   * @param ipOtherContributionID is the IP Other Contribution identifier.
   * @return an IP Other Contributions object, or null if no information were found.
   */
  public IPOtherContribution getIPOtherContributionById(int ipOtherContributionID);


  /**
   * This method gets all the IP Other Contributions information by a given project Id
   * 
   * @param activityID - is the Id of the project
   * @return a List of IP Other Contributions with the Information related to the project
   */
  public IPOtherContribution getIPOtherContributionByProjectId(int projectID);

  /**
   * This method saves the information of the given IP Other Contribution that belongs to a specific project into the
   * database.
   * 
   * @param projectID - is the Id of the project
   * @param ipOtherContribution
   * @return true if the IP Other Contribution was saved successfully, false otherwise.
   */
  public boolean saveIPOtherContribution(int projectID, IPOtherContribution ipOtherContribution);


}
