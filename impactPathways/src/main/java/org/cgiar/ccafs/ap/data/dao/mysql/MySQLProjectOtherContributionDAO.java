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

import org.cgiar.ccafs.ap.data.dao.ProjectOtherContributionDAO;
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
 * @author Javier Andrés Gallego B.
 */
public class MySQLProjectOtherContributionDAO implements ProjectOtherContributionDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLProjectOtherContributionDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLProjectOtherContributionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getIPOtherContributionById(int ipOtherContributionId) {
    Map<String, String> ipOtherContributionData = new HashMap<String, String>();
    LOG.debug(">> getIPOtherContributionById( ipOtherContributionId = {} )", ipOtherContributionId);
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipo.*   ");
    query.append("FROM project_other_contributions as ipo ");
    query.append("WHERE ipo.id=  ");
    query.append(ipOtherContributionId);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        ipOtherContributionData.put("id", rs.getString("id"));
        ipOtherContributionData.put("project_id", rs.getString("project_id"));
        ipOtherContributionData.put("contribution", rs.getString("contribution"));
        ipOtherContributionData.put("additional_contribution", rs.getString("additional_contribution"));
        ipOtherContributionData.put("crp_contributions_nature", rs.getString("crp_contributions_nature"));
      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the IP Other Contribution {}.", ipOtherContributionId, e);
    }
    LOG.debug("-- getIPOtherContributionById() > Calling method executeQuery to get the results");
    return ipOtherContributionData;
  }

  @Override
  public Map<String, String> getIPOtherContributionByProjectId(int projectID) {
    LOG.debug(">> getIPOtherContributionByProjectId (projectID = {} )", projectID);
    Map<String, String> ipOtherContributionData = new HashMap<String, String>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipo.*   ");
    query.append("FROM project_other_contributions as ipo ");
    query.append("INNER JOIN projects p ON ipo.project_id = p.id ");
    query.append("WHERE ipo.project_id=  ");
    query.append(projectID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        ipOtherContributionData.put("id", rs.getString("id"));
        ipOtherContributionData.put("project_id", rs.getString("project_id"));
        ipOtherContributionData.put("contribution", rs.getString("contribution"));
        ipOtherContributionData.put("additional_contribution", rs.getString("additional_contribution"));
        ipOtherContributionData.put("crp_contributions_nature", rs.getString("crp_contributions_nature"));

      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the IP Other Contribution by the projectID {}.", projectID, e);
    }
    LOG.debug("-- getIPOtherContributionByProjectId() : {}", ipOtherContributionData);
    return ipOtherContributionData;


  }

  @Override
  public int saveIPOtherContribution(int projectID, Map<String, Object> ipOtherContributionData) {
    LOG.debug(">> saveIPOtherContribution(ipOtherContributionDataData={})", ipOtherContributionData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (ipOtherContributionData.get("id") == null) {
      // Insert new IP Other Contribution record
      query.append("INSERT INTO project_other_contributions (project_id, contribution, additional_contribution, ");
      query.append("crp_contributions_nature, created_by, modified_by, modification_justification)  ");
      query.append("VALUES (?,?,?,?,?,?,?) ");
      values = new Object[7];
      values[0] = projectID;
      values[1] = ipOtherContributionData.get("contribution");
      values[2] = ipOtherContributionData.get("additional_contribution");
      values[3] = ipOtherContributionData.get("crp_contributions_nature");
      values[4] = ipOtherContributionData.get("user_id");
      values[5] = ipOtherContributionData.get("user_id");
      values[6] = ipOtherContributionData.get("justification");

      result = databaseManager.saveData(query.toString(), values);
      if (result <= 0) {
        LOG.error("A problem happened trying to add a new IP Other Contribution  with project id={}", projectID);
        return -1;
      }
    } else {
      // update IP Other Contribution record
      query.append("UPDATE project_other_contributions SET project_id = ?, contribution = ?, ");
      query.append("additional_contribution = ?, crp_contributions_nature = ?, modified_by = ?, ");
      query.append("modification_justification = ? WHERE id = ? ");
      values = new Object[7];
      values[0] = projectID;
      values[1] = ipOtherContributionData.get("contribution");
      values[2] = ipOtherContributionData.get("additional_contribution");
      values[3] = ipOtherContributionData.get("crp_contributions_nature");
      values[4] = ipOtherContributionData.get("user_id");
      values[5] = ipOtherContributionData.get("justification");


      values[6] = ipOtherContributionData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the IP Other Contribution identified with the id = {}",
          ipOtherContributionData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveIPOtherContribution():{}", result);
    return result;
  }


}
