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

import org.cgiar.ccafs.ap.data.dao.CrpDAO;
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
 */

public class MySQLCrpDAO implements CrpDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLCrpDAO.class);
  private DAOManager daoManager;

  @Inject
  public MySQLCrpDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public Map<String, String> getCRPById(int crpID) {
    Map<String, String> crpData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT c.*   ");
    query.append("FROM crps as c ");
    query.append("WHERE c.id =  ");
    query.append(crpID);
    query.append(" AND c.is_active = 1");
    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        crpData.put("id", rs.getString("id"));
        crpData.put("name", rs.getString("name"));
        crpData.put("acronym", rs.getString("acronym"));
      }
    } catch (SQLException e) {
      LOG.error("getCrpsList() > Exception raised trying to get the list of CRPs.", e);
    }

    return crpData;
  }

  @Override
  public List<Map<String, String>> getCrpContributions(int projectID) {
    List<Map<String, String>> crps = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM crps c ");
    query.append("INNER JOIN project_crp_contributions pcc ON c.id = pcc.crp_id ");
    query.append("WHERE ");
    query.append("project_id = ");
    query.append(projectID);
    query.append(" AND pcc.is_active = TRUE ");
    query.append(" AND c.is_active = TRUE ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> crp = new HashMap<>();
        crp.put("id", rs.getString("id"));
        crp.put("name", rs.getString("name"));
        crp.put("acronym", rs.getString("acronym"));
        crps.add(crp);
      }
    } catch (SQLException e) {
      LOG.error("getCrpsList() > Exception raised trying to get the list of CRPs.", e);
    }

    return crps;
  }

  @Override
  public List<Map<String, String>> getCrpContributionsNature(int projectID) {
    List<Map<String, String>> crpCollaborationsNature = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM project_crp_contributions pcc ");
    query.append("INNER JOIN crps c ON c.id = pcc.crp_id ");
    query.append("WHERE ");
    query.append("project_id = ");
    query.append(projectID);
    query.append(" AND pcc.is_active = TRUE ");
    query.append(" AND c.is_active = TRUE ");

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> collaborationNature = new HashMap<>();
        collaborationNature.put("id", rs.getString("id"));
        collaborationNature.put("crp_id", rs.getString("crp_id"));
        collaborationNature.put("collaboration_nature", rs.getString("collaboration_nature"));
        crpCollaborationsNature.add(collaborationNature);
      }
    } catch (SQLException e) {
      LOG.error("getCrpsList() > Exception raised trying to get the list of Collaborations Nature of the CRPs.", e);
    }

    return crpCollaborationsNature;
  }

  @Override
  public List<Map<String, String>> getCRPsList() {
    List<Map<String, String>> crps = new ArrayList<>();
    String query = "SELECT * FROM crps WHERE is_active = TRUE";

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> crp = new HashMap<>();
        crp.put("id", rs.getString("id"));
        crp.put("name", rs.getString("name"));
        crp.put("acronym", rs.getString("acronym"));
        crps.add(crp);
      }
    } catch (SQLException e) {
      LOG.error("getCrpsList() > Exception raised trying to get the list of CRPs.", e);
    }

    return crps;
  }

  @Override
  public boolean removeCrpContributionNature(int projectID, int crpID, int userID, String justification) {
    StringBuilder query = new StringBuilder();
    query.append("UPDATE project_crp_contributions SET is_active = FALSE, ");
    query.append("modified_by = ?, modification_justification = ? ");
    query.append("WHERE project_id = ? AND crp_id = ? ");

    Object[] values = new Object[4];
    values[0] = userID;
    values[1] = justification;
    values[2] = projectID;
    values[3] = crpID;

    int result = daoManager.delete(query.toString(), values);
    return (result == -1) ? false : true;
  }

  @Override
  public boolean saveCrpContributions(int projectID, Map<String, Object> contributionData) {

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_crp_contributions (project_id, crp_id, created_by, modified_by, ");
    query.append("modification_justification) VALUES (?,?,?,?,?) ");
    query.append("ON DUPLICATE KEY UPDATE is_active = TRUE, modified_by = VALUES(modified_by), ");
    query.append("modification_justification = VALUES(modification_justification) ");

    Object[] values = new Object[5];
    values[0] = projectID;
    values[1] = contributionData.get("crp_id");
    values[2] = contributionData.get("user_id");
    values[3] = contributionData.get("user_id");
    values[4] = contributionData.get("justification");

    int result = daoManager.saveData(query.toString(), values);
    return (result == -1) ? false : true;
  }

  @Override
  public boolean saveCrpContributionsNature(int projectID, Map<String, Object> contributionData) {
    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO project_crp_contributions (project_id, crp_id, collaboration_nature, created_by, ");
    query.append(" modified_by, modification_justification) VALUES (?,?,?,?,?,?) ");
    query.append("ON DUPLICATE KEY UPDATE is_active = TRUE, modified_by = VALUES(modified_by), ");
    query.append("modification_justification = VALUES(modification_justification) ");

    Object[] values = new Object[5];
    values[0] = projectID;
    values[1] = contributionData.get("crp_id");
    values[2] = contributionData.get("collaboration_nature");
    values[3] = contributionData.get("user_id");
    values[4] = contributionData.get("user_id");
    values[5] = contributionData.get("justification");

    int result = daoManager.saveData(query.toString(), values);
    return (result == -1) ? false : true;
  }
}
