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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.OutcomeSynthesis;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class OutcomeSynthesisValidator extends BaseValidator {


  private int c = 0;
  private IPElementManager ipElementManager;
  private IPIndicatorManager ipIndicatorManager;

  private IPProgramManager ipProgramManager;

  @Inject
  public OutcomeSynthesisValidator(IPElementManager ipElementManager, IPIndicatorManager ipIndicatorManager,
    IPProgramManager ipProgramManager) {
    super();
    this.ipIndicatorManager = ipIndicatorManager;
    this.ipProgramManager = ipProgramManager;
    this.ipElementManager = ipElementManager;
  }

  public void validate(BaseAction action, List<OutcomeSynthesis> synthesis) {
    String msjFinal = "";
    int indicatorFP1 = 1;
    int indicatorFP2 = 1;
    int indicatorFP3 = 1;
    int indicatorFP4 = 1;
    List<IPProgram> flagships = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);
    for (OutcomeSynthesis synthe : synthesis) {
      IPElement midOutcome = ipElementManager.getIPElement(synthe.getMidOutcomeId());
      IPIndicator indicator = ipIndicatorManager.getIndicatorFlgship(synthe.getIndicadorId());


      try {

        this.validateActualAchieved(action, synthe.getAchieved(), indicator.getDescription(),
          midOutcome.getComposedId());
      } catch (Exception e) {
        this.addMessage(indicator.getDescription() + ": Achieved target ");
      }
      this.validateSynthesisAnual(action, synthe.getSynthesisAnual(), indicator.getDescription(),
        midOutcome.getComposedId());
      this.validateSynthesisGender(action, synthe.getSynthesisGender(), indicator.getDescription(),
        midOutcome.getComposedId());
      if (validationMessage.length() > 0) {

        int number = 0;
        if (indicator.getAcronym() != null) {
          switch (indicator.getAcronym()) {
            case "FP1":
              number = indicatorFP1;
              break;
            case "FP2":
              number = indicatorFP2;
              break;
            case "FP3":
              number = indicatorFP3;
              break;
            case "FP4":
              number = indicatorFP4;

              break;
            default:
              break;
          }
        }
        int ipProgram = Integer.parseInt(indicator.getAcronym().replace("FP", ""));

        IPProgram programAux = new IPProgram();
        programAux.setId(ipProgram);
        int index = flagships.indexOf(programAux);
        IPProgram ipProgramMsj = flagships.get(index);
        if (ipProgramMsj.getError().equals("")) {
          ipProgramMsj.setError("Indicator " + number);
        } else {
          ipProgramMsj.setError(ipProgramMsj.getError() + ", Indicator " + number);
        }


      }
      if (indicator.getAcronym() != null) {
        switch (indicator.getAcronym()) {
          case "FP1":

            indicatorFP1++;
            break;
          case "FP2":

            indicatorFP2++;
            break;
          case "FP3":

            indicatorFP3++;
            break;
          case "FP4":

            indicatorFP4++;
            break;
          default:
            break;
        }
      }
      validationMessage = new StringBuilder();
    }

    for (IPProgram ipProgram : flagships) {
      if (ipProgram.getError().length() > 0) {
        msjFinal = msjFinal + "</br><p align='left'>- " + ipProgram.getAcronym() + ": " + ipProgram.getError() + "</p>";
      }
    }

    if (!action.getFieldErrors().isEmpty()) {
      action.addActionError(action.getText("saving.fields.required"));
    } else if (msjFinal.length() > 0) {
      action.addActionMessage(" " + action.getText("saving.missingFields", new String[] {msjFinal}));
    }


  }


  private void validateActualAchieved(BaseAction action, float actualAchieved, String indicator, String midOutcome) {
    if (!(actualAchieved >= 0)) {
      this.addMessage(midOutcome + ": " + indicator + ": Achieved target ");

    }
  }

  private void validateSynthesisAnual(BaseAction action, String synthesisAnual, String indicator, String midOutcome) {
    if (!(this.isValidString(synthesisAnual) && this.wordCount(synthesisAnual) <= 200)) {
      this.addMessage(midOutcome + ": " + indicator + ": Synthesis of annual progress towards ");

    }
  }

  private void validateSynthesisGender(BaseAction action, String synthesisGender, String indicator, String midOutcome) {
    if (!(this.isValidString(synthesisGender) && this.wordCount(synthesisGender) <= 200)) {
      this.addMessage(midOutcome + ": " + indicator + ": Synthesis of annual progress gender ");

    }
  }


}
