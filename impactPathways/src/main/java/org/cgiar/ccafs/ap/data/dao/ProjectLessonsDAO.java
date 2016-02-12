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

package org.cgiar.ccafs.ap.data.dao;

import org.cgiar.ccafs.ap.data.dao.mysql.MySQLProjectLessonsDAO;

import java.util.List;
import java.util.Map;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

@ImplementedBy(MySQLProjectLessonsDAO.class)
public interface ProjectLessonsDAO {


  /**
   * This method get the component lessons list for a specific project.
   * 
   * @param projectID - Project identifier
   * @return a map list with the information.
   */
  public List<Map<String, String>> getComponentLessonByProject(int projectID);

  /**
   * This method get the component lessons list for a specific project and cycle.
   * 
   * @param projectID - Project identifier
   * @param cycle -- Cycle name
   * @return a map list with the information.
   */
  public List<Map<String, String>> getComponentLessonByProjectAndCycle(int projectID, String cycle);

  /**
   * This method get the lessons on a given component for a specific project.
   * 
   * @param componentName - The name of the component
   * @param projectID - Project identifier
   * @param year
   * @return a map with the information.
   */
  public Map<String, String> getProjectComponentLesson(int projectID, String componentName, int year, String cycle);


  /**
   * This method saves the lessons of a project component in the database.
   * 
   * @param lessonData - Info to be saved
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveProjectComponentLesson(Map<String, Object> lessonData);

}
