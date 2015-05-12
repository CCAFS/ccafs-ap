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

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
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
 * @author Javier Andrés Gallego B.
 */
public class MySQLActivityPartnerDAO implements ActivityPartnerDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLActivityPartnerDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLActivityPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public boolean deleteActivityPartner(int activityPartnerId) {
    LOG.debug(">> deleteActivityPartner(id={})", activityPartnerId);

    String query = "DELETE FROM activity_partners WHERE id= ?";

    int rowsDeleted = databaseManager.delete(query, new Object[] {activityPartnerId});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteActivityPartner():{}", true);
      return true;
    }

    LOG.debug("<< deleteActivityPartner:{}", false);
    return false;
  }

  @Override
  public boolean deleteActivityPartnerByActivityId(int activityID) {
    LOG.debug(">> deleteActivityPartnerByActivityId(activityID={})", activityID);

    StringBuilder query = new StringBuilder();
    query.append("DELETE ap FROM activity_partners ap ");
    query.append("WHERE ap.activity_id = ? ");

    int rowsDeleted = databaseManager.delete(query.toString(), new Object[] {activityID});
    if (rowsDeleted >= 0) {
      LOG.debug("<< deleteActivityPartnerByActivityId():{}", true);
      return true;
    }
    LOG.debug("<< deleteActivityPartnerByActivityId():{}", false);
    return false;
  }

  @Override
  public Map<String, String> getActivityPartnerById(int activityPartnerID) {
    Map<String, String> activityPartnerData = new HashMap<String, String>();
    LOG.debug(">> getActivityPartnerById( activityPartnerID = {} )", activityPartnerID);
    StringBuilder query = new StringBuilder();
    query.append("SELECT ap.*   ");
    query.append("FROM activity_partners as ap ");
    query.append("WHERE ap.id=  ");
    query.append(activityPartnerID);
    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        activityPartnerData.put("id", rs.getString("id"));
        activityPartnerData.put("institution_id", rs.getString("institution_id"));
        activityPartnerData.put("activity_id", rs.getString("activity_id"));
        activityPartnerData.put("contact_name", rs.getString("contact_name"));
        activityPartnerData.put("contact_email", rs.getString("contact_email"));
        activityPartnerData.put("contribution", rs.getString("contribution"));

      }
      con.close();
    } catch (SQLException e) {
      LOG.error("Exception arised getting the activity {}.", activityPartnerID, e);
    }
    LOG.debug("-- getActivityPartnerById() > Calling method executeQuery to get the results");
    return activityPartnerData;
  }

  @Override
  public List<Map<String, String>> getActivityPartnersByActivity(int activityID) {
    LOG.debug(">> getActivityPartnersByActivity activityID = {} )", activityID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT ap.*   ");
    query.append("FROM activity_partners as ap ");
    query.append("INNER JOIN activities a ON ap.activity_id = a.id ");
    query.append("WHERE ap.activity_id=  ");
    query.append(activityID);


    LOG.debug("-- getActivityPartnersByActivity() > Calling method executeQuery to get the results");
    return getData(query.toString());
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> activityPartnersList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activityPartnerData = new HashMap<String, String>();
        activityPartnerData.put("id", rs.getString("id"));
        activityPartnerData.put("institution_id", rs.getString("institution_id"));
        activityPartnerData.put("activity_id", rs.getString("activity_id"));
        activityPartnerData.put("contact_name", rs.getString("contact_name"));
        activityPartnerData.put("contact_email", rs.getString("contact_email"));
        activityPartnerData.put("contribution", rs.getString("contribution"));

        activityPartnersList.add(activityPartnerData);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():activityPartnersList.size={}", activityPartnersList.size());
    return activityPartnersList;
  }


  @Override
  public int saveActivityPartner(int activityID, Map<String, Object> activityPartnerData) {
    LOG.debug(">> saveActivity(activityData={})", activityPartnerData);
    StringBuilder query = new StringBuilder();
    int result = -1;
    Object[] values;
    if (activityPartnerData.get("id") == null) {
      // Insert new activity partners record
      query
        .append("INSERT INTO activity_partners (institution_id, activity_id, contact_name, contact_email, contribution) ");
      query.append("VALUES (?,?,?,?,?) ");
      values = new Object[5];
      values[0] = activityPartnerData.get("institution_id");
      values[1] = activityID;
      values[2] = activityPartnerData.get("contact_name");
      values[3] = activityPartnerData.get("contact_email");
      values[4] = activityPartnerData.get("contribution");
      result = databaseManager.saveData(query.toString(), values);

    } else {
      // update activity partners record
      query
        .append("UPDATE activity_partners SET institution_id = ?, activity_id = ?, contact_name = ?, contact_email = ?, contribution = ? ");
      query.append("WHERE id = ? ");
      values = new Object[6];
      values[0] = activityPartnerData.get("institution_id");
      values[1] = activityID;
      values[2] = activityPartnerData.get("contact_name");
      values[3] = activityPartnerData.get("contact_email");
      values[4] = activityPartnerData.get("contribution");
      values[5] = activityPartnerData.get("id");
      result = databaseManager.saveData(query.toString(), values);
      if (result == -1) {
        LOG.error("A problem happened trying to update the activity identified with the id = {}",
          activityPartnerData.get("id"));
        return -1;
      }
    }
    LOG.debug("<< saveActivity():{}", result);
    return result;
  }
}
