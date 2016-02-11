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

import org.cgiar.ccafs.ap.data.manager.impl.ProjectLessonsManagerImpl;
import org.cgiar.ccafs.ap.data.model.ComponentLesson;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.List;

import com.google.inject.ImplementedBy;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Jorge Leonardo Solis B. - CIAT/CCAFS
 */
@ImplementedBy(ProjectLessonsManagerImpl.class)
public interface ProjectLessonsManager {

  /**
   * This method get the list of lessons on a given component for a specific project.
   * 
   * @param projectID - Project identifier
   * @return A ComponentLesson list with the information.
   */
  public List<ComponentLesson> getComponentLessonsByProject(int projectID);

  /**
   * This method get the lessons on a given component for a specific project.
   * 
   * @param componentName - The name of the component
   * @param projectID - Project identifier
   * @param year
   * @return A ComponentLesson object with the information.
   */
  public ComponentLesson getProjectComponentLesson(int projectID, String componentName, int year, String cycle);


  /**
   * This method saves the lessons of a project component in the database.
   * 
   * @param lesson - The object with the information to be saved
   * @param project - The project to which belongs the lesson
   * @param user - The user who is making the change
   * @param justification
   * @param cycle Planning or Reporting
   * @return true if the information was saved successfully. False otherwise.
   */
  public boolean saveProjectComponentLesson(ComponentLesson lesson, int projectID, User user, String justification,
    String cycle);
}
