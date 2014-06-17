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

package org.cgiar.ccafs.ap.data.model;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class OutcomeIndicatorReport {

  private int id;
  private String achievements;
  private String evidence;
  private OutcomeIndicator outcomeIndicator;
  private Leader leader;

  public String getAchievements() {
    return achievements;
  }

  public String getEvidence() {
    return evidence;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public OutcomeIndicator getOutcomeIndicator() {
    return outcomeIndicator;
  }

  public void setAchievements(String achievements) {
    this.achievements = achievements;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setOutcomeIndicator(OutcomeIndicator outcomeIndicator) {
    this.outcomeIndicator = outcomeIndicator;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}