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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPElementRelationManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutputsPreplanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(OutputsPreplanningAction.class);
  private static final long serialVersionUID = 8352553096226822850L;

  // Managers
  private IPElementManager ipElementManager;
  private IPProgramManager ipProgramManager;
  private IPIndicatorManager ipIndicatorManager;
  private IPElementRelationManager ipElementRelationManager;

  // Model
  private List<IPElement> outputs;
  private List<IPElement> outputsFromDatabase;
  private List<IPElement> midOutcomesList;
  private List<IPProgram> flagshipsList;

  @Inject
  public OutputsPreplanningAction(APConfig config, IPElementManager ipElementManager,
    IPProgramManager ipProgramManager, IPIndicatorManager ipIndicatorManager,
    IPElementRelationManager ipElementRelationManager) {
    super(config);
    this.ipElementManager = ipElementManager;
    this.ipProgramManager = ipProgramManager;
    this.ipIndicatorManager = ipIndicatorManager;
    this.ipElementRelationManager = ipElementRelationManager;
  }

  public int getElementTypeID() {
    return APConstants.ELEMENT_TYPE_OUTPUTS;
  }

  public List<IPProgram> getFlagshipsList() {
    return flagshipsList;
  }

  public List<IPElement> getMidOutcomesList() {
    return midOutcomesList;
  }

  public int getMidOutcomeTypeID() {
    return APConstants.ELEMENT_TYPE_OUTCOME2019;
  }

  public List<IPElement> getOutputs() {
    return outputs;
  }

  @Override
  public String next() {
    String result = this.save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    IPProgram program = this.getCurrentUser().getCurrentInstitution().getProgram();

    // Create an element type for midOutcomes
    IPElementType midOutcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);

    // Create an element type for outputs
    IPElementType outputsType = new IPElementType(APConstants.ELEMENT_TYPE_OUTPUTS);

    flagshipsList = new ArrayList<>();
    midOutcomesList = new ArrayList<>();

    // Fake flagship to add as a placeholder
    IPProgram flagship = new IPProgram(-1);
    flagship.setName(this.getText("preplanning.midOutcomesRPL.selectFlagship"));
    flagshipsList.add(flagship);

    // Fake midOutcome to add as a placeholder if user is a FPL
    IPElement outcome = new IPElement(-1);
    outcome.setDescription(this.getText("preplanning.midOutcomesRPL.selectMidOutcome"));
    midOutcomesList.add(outcome);

    flagshipsList.addAll(ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE));
    midOutcomesList.addAll(ipElementManager.getIPElements(program, midOutcomesType));
    outputs = ipElementManager.getIPElements(program, outputsType);

    // Set the parents for the translated outputs
    for (int i = 0; i < outputs.size(); i++) {
      if (!outputs.get(i).getTranslatedOf().isEmpty()) {
        int[] intIds = outputs.get(i).getTranslatedOfIDs();
        String[] stringIds = new String[intIds.length];
        for (int c = 0; c < intIds.length; c++) {
          stringIds[c] = String.valueOf(intIds[c]);
        }

        outputs.get(i).setTranslatedOf(ipElementManager.getIPElementList(stringIds));
      }
    }

    outputsFromDatabase = new ArrayList<>();
    outputsFromDatabase.addAll(outputs);

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (outputs != null) {
        outputs.clear();
      }
    }
  }

  @Override
  public String save() {
    // First, remove of the database the elements that user deleted
    for (IPElement output : outputsFromDatabase) {
      // If user removed all the outputs in the interface
      // we should do the same in the database
      if (outputs.isEmpty()) {
        ipElementManager.deleteIPElement(output);
        continue;
      }

      // Check if the user delete an output in the interface
      if (!outputs.contains(output)) {
        ipElementManager.deleteIPElement(output);
      } else {
        // Remove the relations of the outputs that were not removed
        ipIndicatorManager.removeElementIndicators(output, this.getCurrentUser().getCurrentInstitution().getProgram());
        ipElementRelationManager.deleteRelationsByChildElement(output);
      }
    }

    // Remove records already present in the database
    if (ipElementManager.saveIPElements(outputs)) {
      this.addActionMessage(this.getText("saving.success", new String[] {this.getText("preplanning.outputs.title")}));
      return SUCCESS;
    } else {
      this.addActionError(this.getText("saving.problem"));
      return INPUT;
    }
  }

  public void setMidOutcomesList(List<IPElement> midOutcomes) {
    this.midOutcomesList = midOutcomes;
  }

  public void setOutputs(List<IPElement> outputs) {
    this.outputs = outputs;
  }

}