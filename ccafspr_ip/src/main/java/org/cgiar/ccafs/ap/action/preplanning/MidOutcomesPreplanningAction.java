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
package org.cgiar.ccafs.ap.action.preplanning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MidOutcomesPreplanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(MidOutcomesPreplanningAction.class);
  private static final long serialVersionUID = 8352553096226822850L;

  // Managers
  IPElementManager ipElementManager;
  IPIndicatorManager ipIndicatorManager;
  IPProgramManager ipProgramManager;

  // Model
  List<IPElement> midOutcomes;
  List<IPElement> outcomesList;
  List<IPElement> midOutcomesFromDatabase;
  List<IPProgram> flagshipsList;

  @Inject
  public MidOutcomesPreplanningAction(APConfig config, IPElementManager ipElementManager,
    IPIndicatorManager ipIndicatorManager, IPProgramManager ipProgramManager) {
    super(config);
    this.ipElementManager = ipElementManager;
    this.ipIndicatorManager = ipIndicatorManager;
    this.ipProgramManager = ipProgramManager;
  }

  public int getElementTypeID() {
    return APConstants.ELEMENT_TYPE_OUTCOME2019;
  }

  public List<IPProgram> getFlagshipsList() {
    return flagshipsList;
  }

  public List<IPElement> getMidOutcomes() {
    return midOutcomes;
  }

  public List<IPElement> getOutcomesList() {
    return outcomesList;
  }

  @Override
  public void prepare() throws Exception {
    IPProgram program = getCurrentUser().getCurrentInstitution().getProgram();

    // Create an ipElementType with the identifier of the outcomes 2019 type
    IPElementType outcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2025);

    // Create an ipElementType with the identifier of the outcomes 2025 type
    IPElementType midOutcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);

    midOutcomes = ipElementManager.getIPElements(program, midOutcomesType);
    outcomesList = ipElementManager.getIPElements(program, outcomesType);
    flagshipsList = ipProgramManager.getProgramsByType(1);

    midOutcomesFromDatabase = new ArrayList<>();
    midOutcomesFromDatabase.addAll(midOutcomes);

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (midOutcomes != null) {
        midOutcomes.clear();
      }
    }
  }

  @Override
  public String save() {

    if (!midOutcomes.isEmpty()) {

      for (IPElement midOutcome : midOutcomes) {
        if (midOutcome.getIndicators() != null) {
          for (int i = 0; i < midOutcome.getIndicators().size(); i++) {
            if (midOutcome.getIndicators().get(i).getDescription().isEmpty()) {
              midOutcome.getIndicators().remove(i);
            }
          }
        }

        if (midOutcome.getContributesTo() != null) {
          String[] values = new String[midOutcome.getContributesTo().size()];
          for (int i = 0; i < midOutcome.getContributesTo().size(); i++) {
            values[i] = String.valueOf(midOutcome.getContributesTo().get(i).getId());
          }
          midOutcome.setContributesTo(ipElementManager.getIPElementList(values));
        }

        // If the user removed the outcome we should delete it from the database
        if (!midOutcomesFromDatabase.contains(midOutcome)) {
          ipElementManager.deleteIPElement(midOutcome, getCurrentUser().getCurrentInstitution().getProgram());
        } else {
          // We should remove the ipIndicators of the database if the outcome wasn't removed
          ipIndicatorManager.removeElementIndicators(midOutcome, getCurrentUser().getCurrentInstitution().getProgram());
        }
      }

    } else {
      // If all the midOutcomes were removed, we should remove all the records
      // brought from the database
      for (IPElement midOutcome : midOutcomesFromDatabase) {
        ipElementManager.deleteIPElement(midOutcome, getCurrentUser().getCurrentInstitution().getProgram());
      }
    }

    // Remove records already present in the database
    ipElementManager.saveIPElements(midOutcomes);
    return INPUT;
  }

  public void setMidOutcomes(List<IPElement> midOutcomes) {
    this.midOutcomes = midOutcomes;
  }

  public void setOutcomesList(List<IPElement> outcomes) {
    this.outcomesList = outcomes;
  }
}