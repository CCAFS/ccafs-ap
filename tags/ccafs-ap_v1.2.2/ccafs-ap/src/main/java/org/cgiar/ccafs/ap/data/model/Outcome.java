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


public class Outcome {

  private int id;
  private String title;
  private String outcome;
  private String outputs;
  private String partners;
  private String outputUser;
  private String howUsed;
  private String evidence;
  private Logframe logframe;
  private Leader leader;

  public Outcome() {
  }

  public String getEvidence() {
    return evidence;
  }

  public String getHowUsed() {
    return howUsed;
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

  public String getOutcome() {
    return outcome;
  }

  public String getOutputs() {
    return outputs;
  }

  public String getOutputUser() {
    return outputUser;
  }

  public String getPartners() {
    return partners;
  }

  public String getTitle() {
    return title;
  }

  public void setEvidence(String evidence) {
    this.evidence = evidence;
  }

  public void setHowUsed(String howUsed) {
    this.howUsed = howUsed;
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

  public void setOutcome(String outcome) {
    this.outcome = outcome;
  }

  public void setOutputs(String outputs) {
    this.outputs = outputs;
  }

  public void setOutputUser(String outputUser) {
    this.outputUser = outputUser;
  }

  public void setPartners(String partners) {
    this.partners = partners;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}