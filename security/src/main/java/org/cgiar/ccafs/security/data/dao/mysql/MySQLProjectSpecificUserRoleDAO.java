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

package org.cgiar.ccafs.security.data.dao.mysql;

import org.cgiar.ccafs.security.data.dao.ProjectSpecificUserRoleDAO;
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
 * @author Hernán David Carvajal
 */

public class MySQLProjectSpecificUserRoleDAO implements ProjectSpecificUserRoleDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLProjectSpecificUserRoleDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLProjectSpecificUserRoleDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getProjectSpecificUserRoles(int userID) {
    List<Map<String, String>> projectUserRoleDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT pr.*, r.name as 'role_name', r.acronym as 'role_acronym' ");
    query.append("FROM project_roles pr ");
    query.append("INNER JOIN roles r ON r.id = pr.role_id ");
    query.append("WHERE pr.user_id = ");
    query.append(userID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> projectUserRoleData = new HashMap<>();
        projectUserRoleData.put("id", rs.getString("id"));
        projectUserRoleData.put("project_id", rs.getString("project_id"));
        projectUserRoleData.put("user_id", rs.getString("user_id"));
        projectUserRoleData.put("role_id", rs.getString("role_id"));
        projectUserRoleData.put("role_name", rs.getString("role_name"));
        projectUserRoleData.put("role_acronym", rs.getString("role_acronym"));
        projectUserRoleDataList.add(projectUserRoleData);
      }
    } catch (SQLException e) {
      LOG.error("getProjectSpecificUserRoles() > There was an error getting the information of the project_role  {}",
        userID, e);
    }
    return projectUserRoleDataList;
  }


}
