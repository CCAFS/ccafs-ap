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

import org.cgiar.ccafs.ap.data.manager.impl.IPProgramManagerImpl;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPProgramManagerImpl.class)
public interface IPProgramManager {

  /**
   * this method removes a specific project focus.
   * 
   * @param projectId is the project identifier where the project focus to be removed belongs to.
   * @param ipProgramID is the program identifier where the project focus to be removed is related to.
   * @return true if the project focus was deleted, false otherwise.
   */
  public boolean deleteProjectFocus(int projectId, int ipProgramID);

  /**
   * This method gets the information of an IP Program given an Id
   * 
   * @param ipProgramID - is the Id of the IP Program.
   * @return an IPProgram object with the result information.
   */
  public IPProgram getIPProgramById(int ipProgramID);

  /**
   * This method gets the IP Program information by a given project Id
   * 
   * @param projectID - is the id of the project
   * @return an IPProgram object with the result information.
   */
  public IPProgram getIPProgramByProjectId(int projectID);


  /**
   * This method gets from the database a list of ipElements
   * which have the same type as passed as parameter
   * 
   * @param ipProgramTypeID - IP Program type identifier
   * @return a list of IPProgram objects with the information.
   */
  public List<IPProgram> getProgramsByType(int ipProgramTypeID);


  /**
   * This method gets from the database a list of ipElements
   * which have the same type as passed as parameter in a list<Map>
   * 
   * @param ipProgramTypeID - IP Program type identifier
   * @return a list map of IPProgram objects with the information.
   */
  public Map<String, Object> getProgramsByTypeMap(int ipProgramTypeID);


  /**
   * This method gets all the IPPrograms where the projec is focusing (project_focuses).
   * 
   * @param projectID - is the id of the project.
   * @param typeID - is the id of a program type. Can be Flagship Program or Regional Program.
   * @return a List of IPProgram objects that correspond to Project focuses initially saved in the Project Description
   *         section in Pre-planning.
   */
  public List<IPProgram> getProjectFocuses(int projectID, int typeID);

  /**
   * This method will assign IPPrograms to a specific project and will save them as "Project Focuses".
   * 
   * @param projectID is the project identifier.
   * @param programID is the program identifier.
   * @param user is the user who is making the change.
   * @param justification text explaining the change.
   * @return true if the information was successfully saved, or false if any error happened.
   */
  public boolean saveProjectFocus(int projectID, int programID, User user, String justification);


}
