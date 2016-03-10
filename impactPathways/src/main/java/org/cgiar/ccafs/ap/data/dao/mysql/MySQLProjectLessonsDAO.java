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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 * @author Jorge Leonardo Solis B.-- CIAT/CCAFS.
 */

public class MySQLProjectLessonsDAO implements ProjectLessonsDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectLessonsDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLProjectLessonsDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getComponentLessonByProject(int projectID) {

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM project_component_lessons ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND is_active = 1");

    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> componentList = new ArrayList<>();

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      Map<String, String> componentData;
      while (rs.next()) {
        componentData = new HashMap<String, String>();
        componentData.put("id", rs.getString("id"));
        componentData.put("project_id", rs.getString("project_id"));
        componentData.put("lessons", rs.getString("lessons"));
        componentData.put("year", rs.getString("year"));
        componentData.put("component_name", rs.getString("component_name"));
        componentList.add(componentData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():componentList.size={}", componentList.size());
    return componentList;


  }

  @Override
  public List<Map<String, String>> getComponentLessonByProjectAndCycle(int projectID, String cycle) {

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM project_component_lessons ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' AND is_active = 1");

    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> componentList = new ArrayList<>();

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      Map<String, String> componentData;
      while (rs.next()) {
        componentData = new HashMap<String, String>();
        componentData.put("id", rs.getString("id"));
        componentData.put("project_id", rs.getString("project_id"));
        componentData.put("lessons", rs.getString("lessons"));
        componentData.put("year", rs.getString("year"));
        componentData.put("component_name", rs.getString("component_name"));
        componentList.add(componentData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():componentList.size={}", componentList.size());
    return componentList;


  }

  @Override
  public Map<String, String> getProjectComponentLesson(int projectID, String componentName, int year, String cycle) {
    Map<String, String> componentLesson = new HashMap<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM project_component_lessons ");
    query.append("WHERE project_id = ");
    query.append(projectID);
    query.append(" AND component_name = '");
    query.append(componentName);
    query.append("' AND year = ");
    query.append(year);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' ");

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
  public Map<String, String> getProjectComponentLessonSynthesis(int programId, String componentName, int year,
    String cycle) {
    Map<String, String> componentLesson = new HashMap<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM project_component_lessons ");
    query.append("WHERE ip_program_id = ");
    query.append(programId);
    query.append(" AND component_name = '");
    query.append(componentName);
    query.append("' AND year = ");
    query.append(year);
    query.append(" AND cycle = '");
    query.append(cycle);
    query.append("' ");

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
      LOG.error(msg, new Object[] {programId, componentName, year, e});
    }

    return componentLesson;
  }

  @Override
  public boolean saveProjectComponentLesson(Map<String, Object> lessonData) {

    if (lessonData.get("project_id") != null) {
      String query =
        "INSERT INTO project_component_lessons (id, project_id, component_name, lessons, year, created_by, ";
      query += "modified_by, modification_justification,cycle) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?) ";
      query +=
        "ON DUPLICATE KEY UPDATE is_active=TRUE, lessons = VALUES(lessons), cycle = VALUES(cycle), modified_by=VALUES(modified_by), ";
      query += "modification_justification=VALUES(modification_justification) ";

      Object[] values = new Object[9];
      values[0] = lessonData.get("id");
      values[1] = lessonData.get("project_id");
      values[2] = lessonData.get("component_name");
      values[3] = lessonData.get("lessons");
      values[4] = lessonData.get("year");
      values[5] = lessonData.get("created_by");
      values[6] = lessonData.get("modified_by");
      values[7] = lessonData.get("justification");
      values[8] = lessonData.get("cycle");
      int result = daoManager.saveData(query, values);
      return result != -1;
    } else {
      String query =
        "INSERT INTO project_component_lessons (id, ip_program_id, component_name, lessons, year, created_by, ";
      query += "modified_by, modification_justification,cycle) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?) ";
      query +=
        "ON DUPLICATE KEY UPDATE is_active=TRUE, lessons = VALUES(lessons), cycle = VALUES(cycle), modified_by=VALUES(modified_by), ";
      query += "modification_justification=VALUES(modification_justification) ";

      Object[] values = new Object[9];
      values[0] = lessonData.get("id");
      values[1] = lessonData.get("ip_program_id");
      values[2] = lessonData.get("component_name");
      values[3] = lessonData.get("lessons");
      values[4] = lessonData.get("year");
      values[5] = lessonData.get("created_by");
      values[6] = lessonData.get("modified_by");
      values[7] = lessonData.get("justification");
      values[8] = lessonData.get("cycle");
      int result = daoManager.saveData(query, values);
      return result != -1;
    }

  }

}
