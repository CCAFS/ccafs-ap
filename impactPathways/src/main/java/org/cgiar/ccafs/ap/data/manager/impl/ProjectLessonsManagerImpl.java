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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.ProjectLessonsDAO;
import org.cgiar.ccafs.ap.data.dao.ProjectLessonsManager;
import org.cgiar.ccafs.ap.data.model.ComponentLesson;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class ProjectLessonsManagerImpl implements ProjectLessonsManager {

  private ProjectLessonsDAO lessonDAO;

  @Inject
  public ProjectLessonsManagerImpl(ProjectLessonsDAO lessonDAO) {
    this.lessonDAO = lessonDAO;
  }

  @Override
  public ComponentLesson getProjectComponentLesson(int projectID, String componentName, int year) {
    ComponentLesson lesson = new ComponentLesson();
    Map<String, String> lessonData = lessonDAO.getProjectComponentLesson(projectID, componentName, year);

    if (!lessonData.isEmpty()) {
      lesson.setId(Integer.parseInt(lessonData.get("id")));
      lesson.setComponentName(componentName);
      lesson.setLessons(lessonData.get("lessons"));
      lesson.setYear(year);
    }

    return lesson;
  }

  @Override
  public boolean saveProjectComponentLesson(ComponentLesson lesson, Project project, User user, String justification) {
    Map<String, Object> lessonData = new HashMap<>();

    if (lesson.getId() != -1) {
      lessonData.put("id", lesson.getId());
    } else {
      lessonData.put("id", null);
    }

    lessonData.put("project_id", project.getId());
    lessonData.put("component_name", lesson.getComponentName());
    lessonData.put("lessons", lesson.getLessons());
    lessonData.put("year", lesson.getYear());
    lessonData.put("created_by", user.getId());
    lessonData.put("modified_by", user.getId());
    lessonData.put("justification", justification);

    return lessonDAO.saveProjectComponentLesson(lessonData);
  }

}
