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

package org.cgiar.ccafs.ap.data.model;


/**
 * This enum contains the the weight that contains every questions in the project evaluation.
 * The values is the decimal representation of percentage values (%)
 * 
 * @author Hermes Jimenez - CIAT/CCAFS
 */
public enum EvaluationValueQuestions {

  RANKING_OUTPUTS(0.20), RANKING_OUTCOMES(0.35), RANKING_PARTERNSHIP(0.15), RANKING_RESPONSE_TEAM(0.15),
  RANKING_QUALITY(0.15);

  private double value;

  private EvaluationValueQuestions(double value) {
    this.value = value;
  }


  public double getValue() {
    return value;
  }


  public void setValue(double value) {
    this.value = value;
  }


}
