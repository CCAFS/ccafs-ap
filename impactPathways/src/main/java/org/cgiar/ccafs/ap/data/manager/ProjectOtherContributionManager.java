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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectOtherContributionManagerImpl;
import org.cgiar.ccafs.ap.data.model.OtherContribution;
import org.cgiar.ccafs.ap.data.model.ProjecteOtherContributions;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
@ImplementedBy(ProjectOtherContributionManagerImpl.class)
public interface ProjectOtherContributionManager {

  /**
   * This method gets all the IP Other Contribution information by a given IP Other Contribution ID.
   * 
   * @param ipOtherContributionID is the IP Other Contribution identifier.
   * @return an IP Other Contributions object, or null if no information were found.
   */
  public OtherContribution getIPOtherContributionById(int ipOtherContributionID);


  /**
   * This method gets all the IP Other Contributions information by a given project Id
   * 
   * @param projectID - is the Id of the project
   * @return an IPOtherContribution object with the Information related to the project
   */
  public OtherContribution getIPOtherContributionByProjectId(int projectID);


  /**
   * This method gets all the Other Contributions information by a given project Id
   * 
   * @param projectID - is the Id of the project
   * @return an OtherContributions object with the Information related to the project
   */
  public List<ProjecteOtherContributions> getOtherContributionsByProjectId(int projectID);


  /**
   * This method saves the information of the given IP Other Contribution that belongs to a specific project into the
   * database.
   * 
   * @param projectID - is the Id of the project
   * @param ipOtherContribution
   * @param user - the user who is making the change
   * @param justification
   * @return true if the IP Other Contribution was saved successfully, false otherwise.
   */
  public boolean saveIPOtherContribution(int projectID, OtherContribution ipOtherContribution, User user,
    String justification);

  public int saveOtherContributionsList(int projectID, List<ProjecteOtherContributions> OtherContributionsList, User user,
    String justification);


}
