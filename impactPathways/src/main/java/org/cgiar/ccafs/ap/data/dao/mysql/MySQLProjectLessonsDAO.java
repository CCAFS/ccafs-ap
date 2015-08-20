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

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ProjectLessonsDAO;
import org.cgiar.ccafs.utils.db.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class MySQLProjectLessonsDAO implements ProjectLessonsDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectLessonsDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLProjectLessonsDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public Map<String, String> getProjectComponentLesson(int projectID, String componentName, int year) {
    Map<String, String> componentLesson = new HashMap<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM project_component_lessons ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND component_name = '");
    query.append(componentName);
    query.append("' AND year = ");
    query.append(year);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        componentLesson.put("id", rs.getString("id"));
        componentLesson.put("lessons", rs.getString("lessons"));
        componentLesson.put("year", rs.getString("year"));
        componentLesson.put("component_name", rs.getString("component_name"));
      }
    } catch (SQLException e) {
      String msg = "getProjectComponentLesson()> Exception raised trying to get the component lessons for project {} ";
      msg += "in the section {} and for the year {}.";
      LOG.error(msg, new Object[] {projectID, componentName, year, e});
    }

    return componentLesson;
  }

  @Override
  public boolean saveProjectComponentLesson(Map<String, Object> lessonData) {
    String query = "INSERT INTO project_component_lessons (id, project_id, component_name, lessons, year, created_by, ";
    query += "modified_by, modification_justification) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";
    query += "ON DUPLICATE KEY UPDATE is_active=TRUE, lessons = VALUES(lessons), modified_by=VALUES(modified_by), ";
    query += "modification_justification=VALUES(modification_justification) ";

    Object[] values = new Object[8];
    values[0] = lessonData.get("id");
    values[1] = lessonData.get("project_id");
    values[2] = lessonData.get("component_name");
    values[3] = lessonData.get("lessons");
    values[4] = lessonData.get("year");
    values[5] = lessonData.get("created_by");
    values[6] = lessonData.get("modified_by");
    values[7] = lessonData.get("justification");

    int result = daoManager.saveData(query, values);
    return result != -1;
  }

}
