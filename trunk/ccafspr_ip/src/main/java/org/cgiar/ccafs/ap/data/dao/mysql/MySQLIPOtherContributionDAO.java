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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.IPOtherContributionDAO;

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
 * @author Javier Andrés Gallego B.
 */
public class MySQLIPOtherContributionDAO implements IPOtherContributionDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLIPOtherContributionDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLIPOtherContributionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteIPOtherContribution(int ipOtherContributionId) {
    LOG.debug(">> deleteIPOtherContributionByActivityId(id={})", ipOtherContributionId);

    String query = "DELETE FROM ip_other_contributions WHERE id= ?";

    int rowsDeleted = databaseManager.delete(query, new Object[] {ipOtherContributionId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteIPOtherContributionByActivityId():{}", true);
      return true;
    }

    LOG.debug("<< deleteIPOtherContributionByActivityId:{}", false);
    return false;
  }

  @Override
  public boolean deleteIPOtherContributionsByActivityId(int activityID) {
    LOG.debug(">> deleteIPOtherContribution(activityID={})", activityID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE ipo FROM ip_other_contributions ipo ");
    query.append("WHERE ipo.activity_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {activityID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteIPOtherContribution():{}", true);
      return true;
    }
    LOG.debug("<< deleteIPOtherContribution():{}", false);
    return false;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> ipOtherContributionList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> ipOtherContributionData = new HashMap<String, String>();
        ipOtherContributionData.put("id", rs.getString("id"));
        ipOtherContributionData.put("activity_id", rs.getString("activity_id"));
        ipOtherContributionData.put("contribution", rs.getString("contribution"));
        ipOtherContributionData.put("additional_contribution", rs.getString("additional_contribution"));

        ipOtherContributionList.add(ipOtherContributionData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():ipOtherContributionList.size={}", ipOtherContributionList.size());
    return ipOtherContributionList;
  }

  @Override
  public Map<String, String> getIPOtherContributionById(int ipOtherContributionId) {
    Map<String, String> ipOtherContributionData = new HashMap<String, String>();
    LOG.debug(">> getIPOtherContributionById( ipOtherContributionId = {} )", ipOtherContributionId);
    StringBuilder query = new StringBuilder();
    query.append("SELECT ipo.*   ");
    query.append("FROM ip_other_contributions as ipo ");
    query.append("WHERE ipo.id=  ");
    query.append(ipOtherContributionId);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        ipOtherContributionData.put("id", rs.getString("id"));
        ipOtherContributionData.put("activity_id", rs.getString("activity_id"));
        ipOtherContributionData.put("contribution", rs.getString("contribution"));
        ipOtherContributionData.put("additional_contribution", rs.getString("additional_contribution"));

      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", ipOtherContributionId, e);
    }
    LOG.debug("-- getIPOtherContributionById() > Calling method executeQuery to get the results");
    return ipOtherContributionData;
  }

  @Override
  public List<Map<String, String>> getIPOtherContributionsByActivityId(int activityID) {
    LOG.debug(">> getIPOtherContributionsByActivityId activityID = {} )", activityID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ipo.*   ");
    query.append("FROM ip_other_contributions as ipo ");
    query.append("INNER JOIN activities a ON ipo.activity_id = a.id ");
    query.append("WHERE ipo.activity_id=  ");
    query.append(activityID);


    LOG.debug("-- getIPOtherContributionsByActivityId() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  @Override
  public int saveIPOtherContribution(int activityID, Map<String, Object> ipOtherContributionData) {
    LOG.debug(">> saveIPOtherContribution(ipOtherContributionDataData={})", ipOtherContributionData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (ipOtherContributionData.get("id") == null) {
      // Insert new IP Other Contribution record
      query.append("INSERT INTO ip_other_contributions (activity_id, contribution, additional_contribution) ");
      query.append("VALUES (?,?,?) ");
      values = new Object[3];
      values[0] = activityID;
      values[1] = ipOtherContributionData.get("contribution");
      values[2] = ipOtherContributionData.get("additional_contribution");
      result = databaseManager.saveData(query.toString(), values);
      if (result <= 0) {
        LOG.error("A problem happened trying to add a new IP Other Contribution  with activity id={}", activityID);
        return -1;
      }
    } else {
      // update IP Other Contribution record
      query.append("UPDATE ip_other_contributions SET activity_id = ?, contribution = ?, additional_contribution = ? ");
      query.append("WHERE id = ? ");
      values = new Object[4];
      values[0] = activityID;
      values[1] = ipOtherContributionData.get("contribution");
      values[2] = ipOtherContributionData.get("additional_contribution");
      values[3] = ipOtherContributionData.get("id");
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
