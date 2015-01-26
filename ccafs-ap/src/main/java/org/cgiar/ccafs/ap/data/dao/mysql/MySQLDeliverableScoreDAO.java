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

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.DeliverableScoreDAO;

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

public class MySQLDeliverableScoreDAO implements DeliverableScoreDAO {

  private DAOManager daoManager;
  public static Logger LOG = LoggerFactory.getLogger(MySQLDeliverableScoreDAO.class);

  @Inject
  public MySQLDeliverableScoreDAO(DAOManager daoManager) {
    this.daoManager = daoManager;
  }

  @Override
  public List<Map<String, String>> getDeliverableScores(int deliverableID) {
    LOG.debug(">> getDeliverableScores(deliverableID = {})", deliverableID);
    List<Map<String, String>> scores = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT * FROM deliverable_scores WHERE deliverable_id = ");
    query.append(deliverableID);

    try (Connection con = daoManager.getConnection()) {
      ResultSet rs = daoManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> score = new HashMap<String, String>();
        score.put("activity_leader_id", rs.getString("activity_leader_id"));
        score.put("score", rs.getString("score"));

        scores.add(score);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("-- getDeliverableScores(): There was an exception trying to get the scores of the deliverable {}.",
        deliverableID, e);
    }

    LOG.debug("<< getDeliverableScores():scores.size={}", scores.size());
    return scores;
  }

  @Override
  public boolean saveDeliverableScore(int deliverableID, int activityLeaderID, double score) {
    LOG.debug(">> saveDeliverableScore(deliverableID={}, activityLeaderID={}, score={})", new Object[] {deliverableID,
      activityLeaderID, score});

    boolean saved = false;
    String query = "INSERT INTO deliverable_scores (deliverable_id, activity_leader_id, score) ";
    query += "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE score = VALUES(score) ";

    Object[] values = new Object[3];
    values[0] = deliverableID;
    values[1] = activityLeaderID;
    values[2] = score;

    try (Connection con = daoManager.getConnection()) {
      int rowsChanged = daoManager.makeChangeSecure(con, query, values);
      if (rowsChanged < 0) {
        LOG.error("-- saveDeliverableScores(): There was a problem trying to save the scores of the deliverable {}.",
          deliverableID);
      } else {
        saved = true;
      }

    } catch (SQLException e) {
      LOG.error("-- saveDeliverableScores(): There was an exception trying to save the scores of the deliverable {}.",
        deliverableID, e);
    }

    LOG.debug("<< saveDeliverableScore():{}", saved);
    return saved;
  }

}
