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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.DeliverableScoreDAO;
import org.cgiar.ccafs.ap.data.manager.DeliverableScoreManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal
 */

public class DeliverableScoreManagerImpl implements DeliverableScoreManager {

  private DeliverableScoreDAO deliverableScoreDAO;

  @Inject
  public DeliverableScoreManagerImpl(DeliverableScoreDAO deliverableScoreDAO) {
    this.deliverableScoreDAO = deliverableScoreDAO;
  }

  @Override
  public Map<Integer, Double> getDeliverableScores(int deliverableID) {
    List<Map<String, String>> scoresData = deliverableScoreDAO.getDeliverableScores(deliverableID);
    Map<Integer, Double> scores = new HashMap<Integer, Double>(scoresData.size());

    for (Map<String, String> sData : scoresData) {
      int leaderID = Integer.parseInt(sData.get("activity_leader_id"));
      double score = Double.parseDouble(sData.get("score"));

      scores.put(leaderID, score);
    }

    return scores;
  }

  @Override
  public boolean saveDeliverableScore(int deliverableID, int activityLeaderID, double score) {
    return deliverableScoreDAO.saveDeliverableScore(deliverableID, activityLeaderID, score);
  }

}
