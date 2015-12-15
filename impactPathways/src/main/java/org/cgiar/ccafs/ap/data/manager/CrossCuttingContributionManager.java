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

import org.cgiar.ccafs.ap.data.manager.impl.CrossCuttingContributionManagerImpl;
import org.cgiar.ccafs.ap.data.model.CrossCuttingContribution;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(CrossCuttingContributionManagerImpl.class)
public interface CrossCuttingContributionManager {


  /**
   * This method removes a specific crossCuttingContribution value from the database.
   * 
   * @param crossCuttingContributionId is the crossCuttingContribution identifier.
   * @param user - the user that is deleting the crossCuttingContribution.
   * @param justification - the justification statement.
   * @return true if the crossCuttingContribution was successfully deleted, false otherwise.
   */
  public boolean deleteCrossCuttingContribution(int crossCuttingContributionId, User user, String justification);


  /**
   * This method validate if the crossCuttingContribution identify with the given id exists in the system.
   * 
   * @param crossCuttingContributionID is a crossCuttingContribution identifier.
   * @return true if the crossCuttingContribution exists, false otherwise.
   */
  public boolean existCrossCuttingContribution(int crossCuttingContributionID);

  /**
   * This method gets a crossCuttingContribution object by a given crossCuttingContribution identifier.
   * 
   * @param crossCuttingContributionID is the crossCuttingContribution identifier.
   * @return a CrossCuttingContribution object.
   */
  public CrossCuttingContribution getCrossCuttingContributionById(int crossCuttingContributionID);


  /**
   * This method gets all the crossCuttingContributions information by a given project identifier.
   * 
   * @param projectID - is the Id of the project
   * @return a List of crossCuttingContributions with the Information related with the project
   */
  public List<CrossCuttingContribution> getCrossCuttingContributionsByProject(int projectID);


  /**
   * This method saves the information of the given crossCuttingContribution that belong to a specific project into the
   * database.
   * 
   * @param projectID is the project id where the crossCuttingContribution belongs to.
   * @param crossCuttingContribution - is the crossCuttingContribution object with the new information to be
   *        added/updated.
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the crossCuttingContribution
   *         was updated
   *         or -1 is some error occurred.
   */
  public int saveCrossCuttingContribution(int projectID, CrossCuttingContribution crossCuttingContribution, User user,
    String justification);


}
