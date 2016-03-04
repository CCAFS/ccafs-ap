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

package org.cgiar.ccafs.ap.validation.synthesis;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.data.manager.IndicatorReportManager;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.IndicatorType;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class ProjectCrpIndicatorsValidator extends BaseValidator {

  private IndicatorReportManager indicatorsReportManager;

  private int c = 0;

  private List<IndicatorType> indicatorsType;

  @Inject
  public ProjectCrpIndicatorsValidator(IndicatorReportManager indicatorsReportManager) {
    super();
    this.indicatorsReportManager = indicatorsReportManager;

  }

  public void validate(BaseAction action, List<IndicatorReport> indicator) {
    String msjFinal = "";
    indicatorsType = indicatorsReportManager.getIndicatorReportsType();
    for (IndicatorReport indicatorReport : indicator) {
      try {
        this.validateActualAchieved(action, Integer.parseInt(indicatorReport.getActual()),
          indicatorReport.getIndicator().getName());
      } catch (NumberFormatException e) {
        this.addMessage(indicatorReport.getIndicator().getName() + ": Actual Achieved");
      }
      try {
        this.validateExpectedTarget(action, Integer.parseInt(indicatorReport.getNextYearTarget()),
          indicatorReport.getIndicator().getName());
      } catch (NumberFormatException e) {
        this.addMessage(indicatorReport.getIndicator().getName() + ": Expected Target");
      }
      this.validateLink(action, indicatorReport.getSupportLinks(), indicatorReport.getIndicator().getName());
      this.validateDeviation(action, indicatorReport.getDeviation(), indicatorReport.getIndicator().getName());


      if (validationMessage.length() > 0) {
        int index = indicatorsType.indexOf(indicatorReport.getIndicator().getType());
        IndicatorType indicatorTypeMsj = indicatorsType.get(index);
        if (indicatorTypeMsj.getMessageError().equals("")) {
          indicatorTypeMsj.setMessageError("Indicator " + indicatorReport.getIndicator().getId());
        } else {
          indicatorTypeMsj.setMessageError(
            indicatorTypeMsj.getMessageError() + ", Indicator " + indicatorReport.getIndicator().getId());
        }


      }
      validationMessage = new StringBuilder();
    }
    for (IndicatorType indicatorTypeMsj : indicatorsType) {
      if (indicatorTypeMsj.getMessageError().length() > 0) {
        msjFinal = msjFinal + "</br><p align='left'> - " + indicatorTypeMsj.getName() + ": "
          + indicatorTypeMsj.getMessageError() + "</p>";
      }

    }

    if (!action.getFieldErrors().isEmpty()) {
      action.addActionError(action.getText("saving.fields.required"));
    } else if (msjFinal.length() > 0) {
      action.addActionMessage(" " + action.getText("saving.missingFields", new String[] {msjFinal}));
    }


  }


  private void validateActualAchieved(BaseAction action, int actualAchieved, String indicator) {
    if (!(actualAchieved >= 0)) {
      this.addMessage(indicator + ": Actual Achieved");

    }
  }

  private void validateDeviation(BaseAction action, String deviation, String indicator) {
    if (!(this.isValidString(deviation))) {
      this.addMessage(indicator + ": Deviation");

    }
  }


  private void validateExpectedTarget(BaseAction action, int expectedTarget, String indicator) {
    if (!(expectedTarget >= 0)) {
      this.addMessage(indicator + ": Expected Target");

    }
  }

  private void validateLink(BaseAction action, String link, String indicator) {
    if (!(this.isValidString(link))) {
      this.addMessage(indicator + ": Link to supporting databases");

    }
  }
}
