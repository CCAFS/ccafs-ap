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


public class RPLSynthesisReport {

  private int id;
  private String ccafsSites;
  private String crossCenter;
  private String regional;
  private String decisionSupport;
  private Leader leader;
  private Logframe logframe;

  public RPLSynthesisReport() {
  }

  public String getCcafsSites() {
    return ccafsSites;
  }

  public String getCrossCenter() {
    return crossCenter;
  }

  public String getDecisionSupport() {
    return decisionSupport;
  }

  public int getId() {
    return id;
  }

  public Leader getLeader() {
    return leader;
  }

  public Logframe getLogframe() {
    return logframe;
  }

  public String getRegional() {
    return regional;
  }

  public void setCcafsSites(String ccafsSites) {
    this.ccafsSites = ccafsSites;
  }

  public void setCrossCenter(String crossCenter) {
    this.crossCenter = crossCenter;
  }

  public void setDecisionSupport(String decisionSupport) {
    this.decisionSupport = decisionSupport;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setLogframe(Logframe logframe) {
    this.logframe = logframe;
  }

  public void setRegional(String regional) {
    this.regional = regional;
  }
}
