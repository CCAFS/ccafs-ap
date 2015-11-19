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

import org.cgiar.ccafs.security.data.dao.UserRoleDAO;
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

public class MySQLUserRoleDAO implements UserRoleDAO {

  public static Logger LOG = LoggerFactory.getLogger(MySQLUserRoleDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLUserRoleDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getContactPointProjects(int userID) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT r.id, r.name, r.acronym, p.id as 'project_id' ");
    query.append("FROM roles r, projects p ");
    query.append("INNER JOIN liaison_institutions li ON p.liaison_institution_id = li.id ");
    query.append("INNER JOIN institutions i ON li.institution_id = i.id ");
    query.append("INNER JOIN liaison_institutions uli ON i.id = uli.institution_id ");
    query.append("INNER JOIN liaison_users lu ON uli.id = lu.institution_id ");
    query.append("WHERE p.is_active = 1 AND r.id = 4 AND lu.user_id = ");
    query.append(userID);

    return this.setData(query.toString());
  }

  @Override
  public List<Map<String, String>> getManagementLiaisonProjects(int userID) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT r.id, r.name, r.acronym, p.id as 'project_id' ");
    query.append("FROM roles r, projects p ");
    query.append("INNER JOIN liaison_institutions li ON p.liaison_institution_id = li.id ");
    query.append("INNER JOIN institutions i ON li.institution_id = i.id ");
    query.append("INNER JOIN liaison_institutions uli ON i.id = uli.institution_id ");
    query.append("INNER JOIN liaison_users lu ON uli.id = lu.institution_id ");
    query.append("WHERE p.is_active = 1 AND r.id = 2 AND lu.user_id =  ");
    query.append(userID);

    return this.setData(query.toString());
  }

  @Override
  public List<Map<String, String>> getProjectLeaderProjects(int userID) {
    StringBuilder query = new StringBuilder();
    query.append("SELECT r.id, r.name, r.acronym, project_id ");
    query.append("FROM  roles r, project_partners pp ");
    query.append("INNER JOIN project_partner_persons ppp ON pp.id = ppp.project_partner_id ");
    query.append("WHERE ( ppp.contact_type = 'PL' OR ppp.contact_type = 'PC' ) ");
    query.append("AND pp.is_active = 1 AND ppp.is_active = 1 AND r.id = 7 AND ppp.user_id = ");
    query.append(userID);

    return this.setData(query.toString());
  }

  @Override
  public List<String> getRolePermissions(String roleID) {
    List<String> permissions = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT p.permission FROM role_permissions rp ");
    query.append("INNER JOIN permissions p ON rp.permission_id = p.id ");
    query.append("WHERE role_id = ");
    query.append(roleID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        permissions.add(rs.getString("permission"));
      }
    } catch (SQLException e) {
      LOG.error("getRolePermissions() > There was an error getting the permissions assigned to the role {}", roleID, e);
    }
    return permissions;
  }

  @Override
  public Map<String, String> getUserRole(int roleID) {
    Map<String, String> roleData = new HashMap<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT r.id, r.name, r.acronym FROM roles r ");
    query.append("WHERE r.id = ");
    query.append(roleID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        roleData.put("id", rs.getString("id"));
        roleData.put("name", rs.getString("name"));
        roleData.put("acronym", rs.getString("acronym"));
      }
    } catch (SQLException e) {
      LOG.error("getUserRole() > There was an error getting the role with ID = {}", roleID, e);
    }
    return roleData;
  }

  @Override
  public List<Map<String, String>> getUserRolesByUserID(String userID) {
    List<Map<String, String>> userRolesList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT r.id, r.name, r.acronym FROM user_roles ur ");
    query.append("INNER JOIN roles r ON ur.role_id = r.id ");
    query.append("WHERE ur.user_id = ");
    query.append(userID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> userRole = new HashMap<>();
        userRole.put("id", rs.getString("id"));
        userRole.put("name", rs.getString("name"));
        userRole.put("acronym", rs.getString("acronym"));
        userRolesList.add(userRole);
      }
    } catch (SQLException e) {
      LOG.error("verifiyCredentials() > There was an error verifiying the credentials of {}", userID, e);
    }
    return userRolesList;
  }

  @Override
  public List<Map<String, String>> setData(String query) {
    List<Map<String, String>> userRolesList = new ArrayList<>();

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> userRole = new HashMap<>();
        userRole.put("id", rs.getString("id"));
        userRole.put("name", rs.getString("name"));
        userRole.put("acronym", rs.getString("acronym"));
        userRole.put("project_id", rs.getString("project_id"));
        userRolesList.add(userRole);
      }
    } catch (SQLException e) {
      LOG.error("verifiyCredentials() > There was an error running a query.", e);
    }
    return userRolesList;
  }

}
