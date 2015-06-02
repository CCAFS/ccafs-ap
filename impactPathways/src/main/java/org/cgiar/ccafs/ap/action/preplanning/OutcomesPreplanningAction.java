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
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutcomesPreplanningAction extends BaseAction {

  // Logger
  private static Logger LOG = LoggerFactory.getLogger(OutcomesPreplanningAction.class);
  private static final long serialVersionUID = -4683268058827119069L;

  // Managers
  private IPElementManager ipElementManager;
  private IPIndicatorManager ipIndicatorManager;
  private IPElementRelationManager ipElementRelationManager;
  private IPProgramManager ipProgramManager;

  // Model
  private List<IPElement> ccafsIDOs;
  private List<IPElement> consortiumIDOs;
  private List<IPElement> outcomes;
  private List<IPElement> outcomesFromDatabase;
  private List<IPIndicator> fplOutcomesIndicators;


  @Inject
  public OutcomesPreplanningAction(APConfig config, IPElementManager ipElementManager,
    IPIndicatorManager ipIndicatorManager, IPElementRelationManager ipElementRelationManager,
    IPProgramManager ipProgramManager) {
    super(config);
    this.ipElementManager = ipElementManager;
    this.ipIndicatorManager = ipIndicatorManager;
    this.ipElementRelationManager = ipElementRelationManager;
    this.ipProgramManager = ipProgramManager;
  }

  @Override
  public String execute() throws Exception {
    return super.execute();
  }

  public List<IPElement> getCcafsIDOs() {
    return ccafsIDOs;
  }

  public List<IPElement> getConsortiumIDOs() {
    return consortiumIDOs;
  }

  public int getElementTypeID() {
    return APConstants.ELEMENT_TYPE_OUTCOME2025;
  }

  public List<IPIndicator> getFplOutcomesIndicators() {
    return fplOutcomesIndicators;
  }

  public String getNextActionName() {
    if (securityContext.isFPL()) {
      return "midOutcomes";
    } else {
      return "midOutcomesRPL";
    }
  }

  public List<IPElement> getOutcomes() {
    return outcomes;
  }

  @Override
  public String next() {
    String result = save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    } else {
      return result;
    }
  }

  @Override
  public void prepare() throws Exception {
    IPElementType type = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2025);

    // The Consortium IDOs are created by the system administrator
    IPProgram systemProgram = new IPProgram(APConstants.SYSTEM_ADMIN_PROGRAM);
    IPProgram ccafsProgram = new IPProgram(APConstants.CCAFS_PROGRAM);

    // Set the element type for IDOs
    IPElementType idoType = new IPElementType(APConstants.ELEMENT_TYPE_IDOS);

    consortiumIDOs = ipElementManager.getIPElements(systemProgram, idoType);
    ccafsIDOs = ipElementManager.getIPElements(ccafsProgram, idoType);

    outcomes = ipElementManager.getIPElements(getCurrentUser().getCurrentInstitution().getProgram(), type);

    // Keep the id of all outcomes which come from the database
    outcomesFromDatabase = new ArrayList<>();
    outcomesFromDatabase.addAll(outcomes);

    // Create empty outcome if the list is empty
    if (outcomes.isEmpty()) {
      IPElement outcome2025 = new IPElement();
      outcome2025.setId(-1);

      // Indicator
      IPIndicator indicator = new IPIndicator(-1);
      ArrayList<IPIndicator> iList = new ArrayList<>();
      iList.add(indicator);
      outcome2025.setIndicators(iList);

      outcome2025.setContributesTo(new ArrayList<IPElement>());

      outcomes.add(outcome2025);
    }

    // If the user is RPL they should see a list with all the indicators
    // filled by the FPL
    if (securityContext.isRPL()) {
      fplOutcomesIndicators = new ArrayList<>();
      List<IPProgram> flagshipPrograms = ipProgramManager.getProgramsByType(APConstants.FLAGSHIP_PROGRAM_TYPE);
      IPElementType outcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2025);

      for (IPProgram program : flagshipPrograms) {
        List<IPElement> elements = ipElementManager.getIPElements(program, outcomesType);
        for (IPElement e : elements) {
          for (IPIndicator indicator : e.getIndicators()) {
            if (indicator.getParent() == null) {
              fplOutcomesIndicators.add(indicator);
            }
          }
        }
      }
    }

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (outcomes != null) {
        for (IPElement outcome : outcomes) {
          outcome.getIndicators().clear();
          outcome.getContributesTo().clear();
        }
      }
    }
  }

  @Override
  public String save() {
    for (int i = 0; i < outcomesFromDatabase.size(); i++) {
      IPElement outcome = outcomesFromDatabase.get(i);
      // If all the outcomes were removed, we should remove all the records
      // brought from the database
      if (outcomes.isEmpty()) {
        ipElementManager.deleteIPElement(outcome, getCurrentUser().getCurrentInstitution().getProgram());
        continue;
      }

      // Check if the user delete a midOutcome in the interface
      if (!outcomes.contains(outcome)) {
        ipElementManager.deleteIPElement(outcome, getCurrentUser().getCurrentInstitution().getProgram());
      } else {
        ipElementRelationManager.deleteRelationsByChildElement(outcome);
      }
    }

    if (ipElementManager.saveIPElements(outcomes)) {
      if (securityContext.isFPL()) {
        addActionMessage(getText("saving.success", new String[] {getText("preplanning.outcomes.title")}));
      } else if (securityContext.isRPL()) {
        addActionMessage(getText("saving.success", new String[] {getText("preplanning.outcomes.titleRPL")}));
      }
      return SUCCESS;
    } else {
      addActionError(getText("saving.problem"));
      return INPUT;
    }
  }

  public void setOutcomes(List<IPElement> outcomes) {
    this.outcomes = outcomes;
  }
}
