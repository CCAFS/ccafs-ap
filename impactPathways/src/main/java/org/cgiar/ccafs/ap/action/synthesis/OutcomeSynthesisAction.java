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
package org.cgiar.ccafs.ap.action.synthesis;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.HistoryManager;
import org.cgiar.ccafs.ap.data.manager.IPElementManager;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.validation.projects.ProjectLeverageValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Sebastian Amariles Garcia - CIAT/CCAFS
 */

public class OutcomeSynthesisAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(OutcomeSynthesisAction.class);
  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private ProjectLeverageValidator validator;
  private HistoryManager historyManager;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private IPProgramManager ipProgramManager;
  private IPElementManager ipElementManager;
  private IPIndicatorManager ipIndicatorManager;
  // Model for the front-end
  private List<LiaisonInstitution> liaisonInstitutions;
  private LiaisonInstitution currentLiaisonInstitution;
  private List<IPElement> midOutcomes;
  private IPProgram program;

  private int liaisonInstitutionID;


  @Inject
  public OutcomeSynthesisAction(APConfig config, HistoryManager historyManager,
    LiaisonInstitutionManager liaisonInstitutionManager, IPProgramManager ipProgramManager,
    IPElementManager ipElementManager, IPIndicatorManager ipIndicatorManager, ProjectLeverageValidator validator) {
    super(config);
    this.validator = validator;
    this.historyManager = historyManager;
    this.ipIndicatorManager = ipIndicatorManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.ipProgramManager = ipProgramManager;
    this.ipElementManager = ipElementManager;
  }

  public LiaisonInstitution getCurrentLiaisonInstitution() {
    return currentLiaisonInstitution;
  }


  public int getLiaisonInstitutionID() {
    return liaisonInstitutionID;
  }


  public List<LiaisonInstitution> getLiaisonInstitutions() {
    return liaisonInstitutions;
  }

  public List<IPElement> getMidOutcomes() {
    return midOutcomes;
  }

  public IPProgram getProgram() {
    return program;
  }

  public List<IPIndicator> getProjectIndicators(int year, int indicator) {
    return ipIndicatorManager.getIndicatorsSyntesis(year, indicator);
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
    super.prepare();

    try {
      liaisonInstitutionID =
        Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.LIAISON_INSTITUTION_REQUEST_ID)));
    } catch (NumberFormatException e) {
      if (this.getCurrentUser().getLiaisonInstitution() != null) {
        liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().getId();
      } else {
        liaisonInstitutionID = 1;
      }
    }

    // Get the list of liaison institutions.
    liaisonInstitutions = liaisonInstitutionManager.getLiaisonInstitutions();

    // Get currentLiaisonInstitution
    currentLiaisonInstitution = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);

    // Create an ipElementType with the identifier of the outcomes 2019 type
    IPElementType midOutcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);

    // TODO: Create a function for getting an IPProgram by liaisonInstitutionID
    // Temporally i (sebas) will use RP LAM that the IpProgram ID is 5

    int programID = 1; // FP1
    switch (liaisonInstitutionID) {
      case 2:
        programID = 1; // FP1
        break;
      case 3:
        programID = 2; // FP2
        break;
      case 4:
        programID = 3; // FP3
        break;
      case 5:
        programID = 4; // FP4
        break;
      case 6:
        programID = 6; // RP EA
        break;
      case 7:
        programID = 5; // RP LAM
        break;
      case 8:
        programID = 8; // RP SAs
        break;
      case 9:
        programID = 9; // RP SEA
        break;
      case 10:
        programID = 7; // RP WA
        break;
      default:
        break;
    }

    program = ipProgramManager.getIPProgramById(programID);

    // Get Outcomes 2019 of current IPProgram
    midOutcomes = ipElementManager.getIPElements(program, midOutcomesType);


  }

  @Override
  public String save() {
    return SUCCESS;
  }

  public void setCurrentLiaisonInstitution(LiaisonInstitution currentLiaisonInstitution) {
    this.currentLiaisonInstitution = currentLiaisonInstitution;
  }

  public void setLiaisonInstitutionID(int liaisonInstitutionID) {
    this.liaisonInstitutionID = liaisonInstitutionID;
  }

  public void setMidOutcomes(List<IPElement> midOutcomes) {
    this.midOutcomes = midOutcomes;
  }

  public void setProgram(IPProgram program) {
    this.program = program;
  }

  @Override
  public void validate() {
    if (save) {
    }
  }
}