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
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.MogSynthesis;
import org.cgiar.ccafs.ap.validation.BaseValidator;

import java.util.List;

import com.google.inject.Inject;


/**
 * @author Christian David Garcia Oviedo. - CIAT/CCAFS
 */

public class MogSynthesisValidator extends BaseValidator {


  private int c = 0;
  private IPElementManager ipElementManager;


  @Inject
  public MogSynthesisValidator(IPElementManager ipElementManager, IPIndicatorManager ipIndicatorManager) {
    super();


    this.ipElementManager = ipElementManager;
  }

  public void validate(BaseAction action, List<MogSynthesis> synthesis) {
    String msjFinal = "";

    int indicatorFP1 = 1;
    int indicatorFP2 = 1;
    int indicatorFP3 = 1;
    int indicatorFP4 = 1;
    for (MogSynthesis synthe : synthesis) {
      IPElement midOutcome = ipElementManager.getIPElement(synthe.getMogId());


      this.validateSynthesisAnual(action, synthe.getSynthesisReport(), midOutcome.getComposedId());
      this.validateSynthesisGender(action, synthe.getSynthesisGender(), midOutcome.getComposedId());
      if (validationMessage.length() > 0) {
        int number = 0;
        switch (midOutcome.getProgram().getId()) {
          case 1:
            number = indicatorFP1;
            indicatorFP1++;
            break;
          case 2:
            number = indicatorFP2;
            indicatorFP2++;
            break;
          case 3:
            number = indicatorFP3;
            indicatorFP3++;
            break;
          case 4:
            number = indicatorFP4;
            indicatorFP4++;
            break;
          default:
            break;
        }
        msjFinal = msjFinal + "</br><p align='left'>-" + midOutcome.getComposedId() + " " + number + " </p>";


      }
      c++;
      validationMessage = new StringBuilder();
    }
    if (!action.getFieldErrors().isEmpty()) {
      action.addActionError(action.getText("saving.fields.required"));
    } else if (msjFinal.length() > 0) {
      action.addActionMessage(" " + action.getText("saving.missingFields", new String[] {msjFinal}));
    }


  }


  private void validateSynthesisAnual(BaseAction action, String synthesisAnual, String midOutcome) {
    if (!(this.isValidString(synthesisAnual) && this.wordCount(synthesisAnual) <= 100)) {
      this.addMessage(midOutcome + ": " + " Synthesis of annual progress towards ");

    }
  }

  private void validateSynthesisGender(BaseAction action, String synthesisGender, String midOutcome) {
    if (!(this.isValidString(synthesisGender) && this.wordCount(synthesisGender) <= 100)) {
      this.addMessage(midOutcome + ": Synthesis of annual progress gender ");

    }
  }


}
