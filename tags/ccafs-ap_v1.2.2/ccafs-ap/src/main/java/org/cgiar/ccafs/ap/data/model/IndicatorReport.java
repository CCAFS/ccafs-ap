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


public class IndicatorReport {

  private int id;
  private int year;
  private String target;
  private String nextYearTarget;
  private String actual;
  private String supportLinks;
  private String deviation;
  private Leader leader;
  private Indicator indicator;

  public String getActual() {
    return actual;
  }

  public String getDeviation() {
    return deviation;
  }

  public int getId() {
    return id;
  }

  public Indicator getIndicator() {
    return indicator;
  }

  public Leader getLeader() {
    return leader;
  }

  public String getNextYearTarget() {
    return nextYearTarget;
  }

  public String getSupportLinks() {
    return supportLinks;
  }

  public String getTarget() {
    return target;
  }

  public int getYear() {
    return year;
  }

  public void setActual(String actual) {
    this.actual = actual;
  }

  public void setDeviation(String deviation) {
    this.deviation = deviation;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setIndicator(Indicator indicator) {
    this.indicator = indicator;
  }

  public void setLeader(Leader leader) {
    this.leader = leader;
  }

  public void setNextYearTarget(String nextYearTarget) {
    this.nextYearTarget = nextYearTarget;
  }

  public void setSupportLinks(String supportLinks) {
    this.supportLinks = supportLinks;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public void setYear(int year) {
    this.year = year;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}