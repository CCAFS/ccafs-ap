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
package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.dao.RoleDAO;
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
 * @author Javier Andrés Gallego
 * @author Hernán David Carvajal
 */
public class MySQLRoleDAO implements RoleDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLRoleDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLRoleDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteRole(int userID, int roleID) {
    StringBuilder query = new StringBuilder();
    try (Connection conn = databaseManager.getConnection()) {
      int result;
      if (roleID != APConstants.ROLE_PROJECT_LEADER) {
        query.append("DELETE FROM user_roles WHERE user_id = ? AND role_id = ?");
      } else {
        query.append("SELECT COUNT(*) FROM project_partner_persons WHERE user_id = ");
        query.append(userID);
        query.append(" AND contact_type = '");
        query.append(APConstants.PROJECT_PARTNER_PL);
        query.append("'");
        ResultSet resultData = databaseManager.makeQuery(query.toString(), conn);
        if (resultData.next()) {
          result = resultData.getInt(1);
          if (result == 1) {
            // To delete the project leader role, we should verify that the user is not leader of any project.
            query.setLength(0);
            query.append("DELETE FROM user_roles WHERE user_id = ? AND role_id = ? ");
            result = databaseManager.delete(query.toString(), new Object[] {userID, roleID});
            return result != -1;
          }
        }
      }
    } catch (SQLException e) {
      LOG.error("-- deleteRole() > There was an error. {}.", e);
    }
    return false;
  }

  @Override
  public List<Map<String, String>> getAllRoles() {
    LOG.debug(">> getAllRoles()");
    List<Map<String, String>> roleList = new ArrayList<>();
    try (Connection connection = databaseManager.getConnection()) {

      StringBuilder query = new StringBuilder();
      query.append("SELECT * ");
      query.append("FROM roles ");
      query.append("ORDER BY name ");

      ResultSet rs = databaseManager.makeQuery(query.toString(), connection);
      while (rs.next()) {
        Map<String, String> roleData = new HashMap<>();
        roleData.put("id", rs.getString("id"));
        roleData.put("name", rs.getString("name"));
        roleData.put("acronym", rs.getString("acronym"));
        roleList.add(roleData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getAllRoles() > There was an error getting the data for Employees {}.", e);
      return null;
    }
    LOG.debug("<< getAllRoles():{}", roleList);
    return roleList;
  }


  @Override
  public boolean saveRole(int userID, int roleID) {
    String query = "INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (?, ?)";
    int result = databaseManager.saveData(query, new Object[] {userID, roleID});
    return result != -1;
  }
}
