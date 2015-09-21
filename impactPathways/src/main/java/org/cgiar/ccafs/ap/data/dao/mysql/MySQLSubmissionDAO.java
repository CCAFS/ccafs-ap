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

import org.cgiar.ccafs.ap.data.dao.SubmissionDAO;
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
 * @author Héctor Fabio Tobón R. - CIAT/CCAFS
 */

public class MySQLSubmissionDAO implements SubmissionDAO {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(MySQLSubmissionDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLSubmissionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getProjectSubmissions(int projectID) {
    LOG.debug(">> getProjectSubmissions projectID = {} )", projectID);

    StringBuilder query = new StringBuilder();
    query.append("SELECT * ");
    query.append("FROM project_submissions ");
    query.append("WHERE project_id = ");
    query.append(projectID);

    LOG.debug("<< getProjectSubmissions() > Calling method executeQuery to get the results");

    List<Map<String, String>> submissionsList = new ArrayList<>();

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> submissionData = new HashMap<>();
        submissionData.put("id", rs.getString("id"));
        submissionData.put("cycle", rs.getString("cycle"));
        submissionData.put("year", rs.getString("year"));
        submissionData.put("user_id", rs.getString("user_id"));
        submissionData.put("date_time", rs.getDate("date_time").toString());
        submissionsList.add(submissionData);
      }
      rs.close();
      rs = null; // For the garbage collector to find it easily.
    } catch (SQLException e) {
      String exceptionMessage = "-- executeQuery() > Exception raised trying ";
      exceptionMessage += "to execute the following query " + query;
      LOG.error(exceptionMessage, e);
      return null;
    }
    return submissionsList;
  }

  @Override
  public int saveProjectSubmission(int projectID, Map<String, String> submissionData) {
    // TODO Auto-generated method stub
    return 0;
  }

}
