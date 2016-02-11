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

import org.cgiar.ccafs.ap.data.manager.impl.CaseStudiesManagerImpl;
import org.cgiar.ccafs.ap.data.model.CasesStudies;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;

/**
 * @author Christian Garcia
 */
@ImplementedBy(CaseStudiesManagerImpl.class)
public interface CaseStudiesManager {


  /**
   * This method removes a specific caseStudy value from the database.
   * 
   * @param caseStudyId is the caseStudy identifier.
   * @param user - the user that is deleting the caseStudy.
   * @param justification - the justification statement.
   * @return true if the caseStudy was successfully deleted, false otherwise.
   */
  public boolean deleteCaseStudy(int caseStudyId, User user, String justification);


  /**
   * This method removes a set of caseStudys that belongs to a specific project.
   * 
   * @param projectID is the project identifier.
   * @return true if the set of caseStudys were successfully deleted, false otherwise.
   */
  public boolean deleteCaseStudysByProject(int projectID);

  /**
   * This method validate if the caseStudy identify with the given id exists in the system.
   * 
   * @param caseStudyID is a caseStudy identifier.
   * @return true if the caseStudy exists, false otherwise.
   */
  public boolean existCaseStudy(int caseStudyID);

  /**
   * This method gets a caseStudy object by a given caseStudy identifier.
   * 
   * @param caseStudyID is the caseStudy identifier.
   * @return a CaseStudy object.
   */
  public CasesStudies getCaseStudyById(int caseStudyID);


  /**
   * This method gets all the caseStudys information by a given project identifier.
   * 
   * @param projectID - is the Id of the project
   * @return a List of caseStudys with the Information related with the project
   */
  public List<CasesStudies> getCaseStudysByProject(int projectID);


  /**
   * This method saves the information of the given caseStudy that belong to a specific project into the database.
   * 
   * @param projectID is the project id where the caseStudy belongs to.
   * @param caseStudy - is the caseStudy object with the new information to be added/updated.
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return a number greater than 0 representing the new ID assigned by the database, 0 if the caseStudy was updated
   *         or -1 is some error occurred.
   */
  public int saveCaseStudy(int projectID, CasesStudies caseStudy, User user, String justification);

  /**
   * This method saves the CaseStudy Contribution relation
   * 
   * @param caseStudyID - is the Id of the caseStudy
   * @param outputID - is the Id of the output (MOG)
   * @param user - is the user that is making the change.
   * @param justification - is the justification statement.
   * @return true if the relation CaseStudy Contribution is successfully saved,
   *         false otherwise
   */

}
