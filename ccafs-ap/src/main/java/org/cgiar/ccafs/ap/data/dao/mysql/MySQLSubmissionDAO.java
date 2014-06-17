/*
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
 */

package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.SubmissionDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLSubmissionDAO implements SubmissionDAO {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MySQLSubmissionDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLSubmissionDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getSubmission(int activity_leader_id, int logframe_id, String section) {
    Object[] params = new Object[] {activity_leader_id, logframe_id, section};
    LOG.debug(">> getSubmission(activity_leader_id={}, logframe_id={}, section={})", params);
    Map<String, String> submission = new HashMap<>();

    StringBuilder query = new StringBuilder();
    query.append("SELECT s.id, l.id as 'logframe_id', l.name as 'logframe_name', ");
    query.append("l.year as 'logframe_year', al.id as 'leader_id', ");
    query.append("al.acronym as 'leader_acronym' ");
    query.append("FROM submissions s ");
    query.append("INNER JOIN logframes l ON s.logframe_id = l.id ");
    query.append("INNER JOIN activity_leaders al ON s.activity_leader_id = al.id ");
    query.append("WHERE `activity_leader_id` = ");
    query.append(activity_leader_id);
    query.append(" AND `logframe_id` = ");
    query.append(logframe_id);
    query.append(" AND `section` = '" + section + "'; ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      if (rs.next()) {
        submission.put("id", rs.getString("id"));
        submission.put("logframe_id", rs.getString("logframe_id"));
        submission.put("logframe_name", rs.getString("logframe_name"));
        submission.put("logframe_year", rs.getString("logframe_year"));
        submission.put("leader_id", rs.getString("leader_id"));
        submission.put("leader_acronym", rs.getString("leader_acronym"));
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getSubmission() > There was an error getting if the workplan have been submitted.", e);
    }

    LOG.debug("<< getSubmission():{}", submission);
    return submission;
  }

  @Override
  public boolean submit(int activity_leader_id, int logframe_id, String section) {
    Object[] params = new Object[] {activity_leader_id, logframe_id, section};
    LOG.debug(">> submit(activity_leader_id={}, logframe_id={}, section={})", params);
    boolean submitted = false;

    Object[] values = new Object[3];
    values[0] = activity_leader_id;
    values[1] = logframe_id;
    values[2] = section;

    String query = "INSERT INTO `submissions` (`activity_leader_id`, `logframe_id`, `section`) VALUES (?, ?, ?);";

    try (Connection con = databaseManager.getConnection()) {
      int rows = databaseManager.makeChangeSecure(con, query, values);
      if (rows < 0) {
        LOG.error("There was a problem making the submission.");
        LOG.error("Query: {}", query);
        LOG.error("Values: {}", Arrays.toString(values));
      } else {
        submitted = true;
      }
    } catch (SQLException e) {
      LOG.error("-- submit() > There was an error making the submission. ", e);
    }
    LOG.debug("<< submit():{}", submitted);
    return submitted;
  }
}
