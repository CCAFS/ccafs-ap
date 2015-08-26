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

import org.cgiar.ccafs.ap.data.dao.PartnerPersonDAO;
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
 * @author Héctor Fabio Tobón R.
 */
public class MySQLPartnerPersonDAO implements PartnerPersonDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLPartnerPersonDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLPartnerPersonDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  private List<Map<String, String>> getData(String query) {
    LOG.debug(">> executeQuery(query='{}')", query);
    List<Map<String, String>> personsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> personsDataList = new HashMap<String, String>();
        personsDataList.put("id", rs.getString("id"));
        personsDataList.put("project_partner_id", rs.getString("project_partner_id"));
        personsDataList.put("user_id", rs.getString("user_id"));
        personsDataList.put("contact_type", rs.getString("contact_type"));
        personsDataList.put("responsibilities", rs.getString("responsibilities"));

        personsList.add(personsDataList);
      }
      rs.close();
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;

      LOG.error(exceptionMessage, e);
      return null;
    }
    LOG.debug("<< executeQuery():personsDataList.size={}", personsList.size());
    return personsList;
  }

  @Override
  public Map<String, String> getPartnerPerson(int partnerPersonID) {
    LOG.debug(">> getPartnerPerson partnerPersonID = {} )", partnerPersonID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT *   ");
    query.append("FROM project_partner_persons ");
    query.append("WHERE id =  ");
    query.append(partnerPersonID);
    query.append(" AND is_active = 1");


    LOG.debug("-- getPartnerPerson() > Calling method executeQuery to get the results");
    List<Map<String, String>> data = this.getData(query.toString());
    if (data.size() != 0) {
      return data.get(0);
    }
    return new HashMap<String, String>();
  }

  @Override
  public List<Map<String, String>> getPartnerPersonsByPartnerID(int projectPartnerID) {
    LOG.debug(">> getPartnerPersonsByPartnerID projectPartnerID = {} )", projectPartnerID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT *   ");
    query.append("FROM project_partner_persons ");
    query.append("WHERE project_partner_id =  ");
    query.append(projectPartnerID);
    query.append(" AND is_active = 1");


    LOG.debug("-- getPartnerPersonsByPartnerID() > Calling method executeQuery to get the results");
    return this.getData(query.toString());
  }

}