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
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPProgram;

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

  // Model
  private List<IPElement> outputs;
  private List<IPElement> outputsFromDatabase;
  private List<IPElement> midOutcomesList;
  private List<IPProgram> flagshipsList;

  @Inject
  public OutputsPreplanningAction(APConfig config, IPElementManager ipElementManager, IPProgramManager ipProgramManager) {
    super(config);
    this.ipElementManager = ipElementManager;
    this.ipProgramManager = ipProgramManager;
  }

  public List<IPProgram> getFlagshipsList() {
    return flagshipsList;
  }

  public List<IPElement> getMidOutcomesList() {
    return midOutcomesList;
  }

  public List<IPElement> getOutputs() {
    return outputs;
  }

  @Override
  public void prepare() throws Exception {
    IPProgram program = getCurrentUser().getCurrentInstitution().getProgram();

    // Create an element type for midOutcomes
    IPElementType midOutcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);

    // Create an element type for outputs
    IPElementType outputsType = new IPElementType(APConstants.ELEMENT_TYPE_OUTPUTS);

    midOutcomesList = ipElementManager.getIPElements(program, midOutcomesType);
    outputs = ipElementManager.getIPElements(program, outputsType);

    flagshipsList = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (outputs != null) {
        outputs.clear();
      }
    }
  }

  @Override
  public String save() {
    IPProgram program = new IPProgram();
    program.setId(1);

    IPElementType type = new IPElementType();
    type.setId(4);

    for (IPElement output : outputs) {
      if (output.getProgram() == null) {
        output.setProgram(program);
      }
      if (output.getType() == null) {
        output.setType(type);
      }

      if (output.getContributesTo() != null) {
        String[] values = new String[output.getContributesTo().size()];
        for (int i = 0; i < output.getContributesTo().size(); i++) {
          values[i] = String.valueOf(output.getContributesTo().get(i).getId());
        }
        output.setContributesTo(ipElementManager.getIPElementList(values));
      }
    }

    // Remove records already present in the database
    ipElementManager.deleteIPElements(program, type);
    ipElementManager.saveIPElements(outputs);
    return SUCCESS;
  }

  public void setMidOutcomesList(List<IPElement> midOutcomes) {
    this.midOutcomesList = midOutcomes;
  }

  public void setOutputs(List<IPElement> outputs) {
    this.outputs = outputs;
  }

}