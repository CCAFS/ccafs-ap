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

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLIPProgramDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;

/**
 * @author Javier Andrés Gallego
 * @author Héctor Fabio Tobón R.
 */
@ImplementedBy(MySQLIPProgramDAO.class)
public interface IPProgramDAO {

  /**
   * This method gets the information of a IP Program by a given ID
   *
   * @param ipProgramID - is the ID of the program
   * @return a Map with the IP Program information.
   */
  public Map<String, String> getIPProgramById(int ipProgramID);


  /**
   * TODO JG/HT - To Document.
   *
   * @param projectID
   * @return
   */
  public Map<String, String> getIPProgramByProjectId(int projectID);

  /**
   * This method return the Programs Type identified as Flagship
   * indicated by parameter.
   *
   * @param none
   * @return a list of maps with the information of all IP elements returned.
   */
  public List<Map<String, String>> getProgramsByType(int typeId);

  /**
   * This method return all the Program Type from an specified Project which belongs to the program
   * indicated by parameter.
   *
   * @param programID, identifier of the program
   * @return a list of maps with the information of all IP elements returned.
   */
  public List<Map<String, String>> getProgramType(int programID, int typeProgramId);

  /**
   * This method gets all the information of a Program related with a Project by a given project Id and a type Id, from
   * the table project_focuses.
   *
   * @param projectID - is the id of the project
   * @param typeID - is the id of a program type
   * @return a List of Map with the information of a Program related with a Project
   */
  public List<Map<String, String>> getProjectFocuses(int projectID, int typeID);

  /**
   * This method creates or update into the table project_focuses new relations between a project and a program.
   *
   * @param ipElementData - a Map with the information that is going to be saved.
   * @return true if the list were successfully saved, false otherwise.
   */
  public boolean saveProjectFocuses(Map<String, Object> ipElementData);


}
