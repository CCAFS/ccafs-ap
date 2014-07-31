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

import java.util.List;

import com.google.inject.ImplementedBy;

@ImplementedBy(IPProgramManagerImpl.class)
public interface IPProgramManager {

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

}
