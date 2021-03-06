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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MidOutcomesPreplanningAction extends BaseAction {

  public static Logger LOG = LoggerFactory.getLogger(MidOutcomesPreplanningAction.class);
  private static final long serialVersionUID = 8352553096226822850L;

  // Managers
  private IPElementManager ipElementManager;
  private IPElementRelationManager ipElementRelationManager;
  private IPIndicatorManager ipIndicatorManager;
  private IPProgramManager ipProgramManager;

  // Model
  private List<IPElement> midOutcomes;
  private List<IPElement> outcomesList;
  private List<IPElement> midOutcomesFromDatabase;
  private List<IPProgram> flagshipsList;
  private int programID;
  private IPProgram program;

  @Inject
  public MidOutcomesPreplanningAction(APConfig config, IPElementManager ipElementManager,
    IPIndicatorManager ipIndicatorManager, IPProgramManager ipProgramManager,
    IPElementRelationManager ipElementRelationManager) {
    super(config);
    this.ipElementManager = ipElementManager;
    this.ipIndicatorManager = ipIndicatorManager;
    this.ipProgramManager = ipProgramManager;
    this.ipElementRelationManager = ipElementRelationManager;
  }

  public int getElementTypeID() {
    return APConstants.ELEMENT_TYPE_OUTCOME2019;
  }

  public int getFlagshipProgramTypeID() {
    return APConstants.FLAGSHIP_PROGRAM_TYPE;
  }

  public List<IPProgram> getFlagshipsList() {
    return flagshipsList;
  }

  public List<IPElement> getMidOutcomes() {
    return midOutcomes;
  }

  public String getNextActionName() {
    if (securityContext.isFPL()) {
      return "outputs";
    } else {
      return "outputsRPL";
    }
  }

  public List<IPElement> getOutcomesList() {
    return outcomesList;
  }

  public IPProgram getProgram() {
    return program;
  }

  public int getProgramID() {
    return programID;
  }

  public int getRegionProgramTypeID() {
    return APConstants.REGION_PROGRAM_TYPE;
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
    programID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.PROGRAM_REQUEST_ID)));
    program = ipProgramManager.getIPProgramById(programID);

    // Create an ipElementType with the identifier of the outcomes 2019 type
    IPElementType outcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2025);

    // Create an ipElementType with the identifier of the outcomes 2025 type
    IPElementType midOutcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);

    flagshipsList = new ArrayList<>();

    // Fake flagship to add as a placeholder
    IPProgram flagship = new IPProgram(-1);
    flagship.setName(this.getText("preplanning.midOutcomesRPL.selectFlagship"));
    flagshipsList.add(flagship);

    midOutcomes = ipElementManager.getIPElements(program, midOutcomesType);
    outcomesList = ipElementManager.getIPElements(program, outcomesType);
    flagshipsList.addAll(ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE));

    midOutcomesFromDatabase = new ArrayList<>();
    midOutcomesFromDatabase.addAll(midOutcomes);

    if (this.getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (midOutcomes != null) {
        midOutcomes.clear();
      }
    }
  }

  @Override
  public String save() {
    // First, remove of the database the elements that user deleted
    for (int i = 0; i < midOutcomesFromDatabase.size(); i++) {
      IPElement midOutcome = midOutcomesFromDatabase.get(i);
      // If all the midOutcomes were removed, we should remove all the records
      // brought from the database
      if (midOutcomes.isEmpty()) {
        ipElementManager.deleteIPElement(midOutcome);
        continue;
      }

      // Check if the user delete a midOutcome in the interface
      if (!midOutcomes.contains(midOutcome)) {
        // Before delete the ipElement we should delete the child elements
        ipElementManager.deleteChildIPElements(midOutcome);
        // Delete the element
        ipElementManager.deleteIPElement(midOutcome);
      } else {
        // Remove the relations and indicators of the midOutcome
        ipIndicatorManager.removeElementIndicators(midOutcome);
        ipElementRelationManager.deleteRelationsByChildElement(midOutcome);
      }
    }

    // Then adjust the indicators existent
    for (IPElement midOutcome : midOutcomes) {
      if (midOutcome.getIndicators() != null) {
        for (int i = 0; i < midOutcome.getIndicators().size(); i++) {
          if (securityContext.isRPL()) {
            if (midOutcome.getIndicators().get(i).getId() == 0) {
              midOutcome.getIndicators().get(i).setId(-1);
            }
          }
        }
      }
    }

    // Save all the elements brougth from the user interface
    if (ipElementManager.saveIPElements(midOutcomes, this.getCurrentUser(), this.getJustification())) {
      this
        .addActionMessage(this.getText("saving.success", new String[] {this.getText("preplanning.midOutcomes.title")}));
      return SUCCESS;
    } else {
      this.addActionError(this.getText("saving.problem"));
      return INPUT;
    }
  }

  public void setMidOutcomes(List<IPElement> midOutcomes) {
    this.midOutcomes = midOutcomes;
  }

  public void setOutcomesList(List<IPElement> outcomes) {
    this.outcomesList = outcomes;
  }

  public void setProgram(IPProgram program) {
    this.program = program;
  }

  public void setProgramID(int programID) {
    this.programID = programID;
  }
}