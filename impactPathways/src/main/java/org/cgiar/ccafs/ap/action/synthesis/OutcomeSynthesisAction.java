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
import org.cgiar.ccafs.ap.data.manager.OutcomeSynthesisManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPElementType;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.OutcomeSynthesis;
import org.cgiar.ccafs.ap.validation.synthesis.OutcomeSynthesisValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Sebastian Amariles Garcia - CIAT/CCAFS
 * @author Christian david Garcia - CIAT/CCAFS
 */

public class OutcomeSynthesisAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(OutcomeSynthesisAction.class);
  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private OutcomeSynthesisValidator validator;
  private HistoryManager historyManager;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private IPProgramManager ipProgramManager;
  private IPElementManager ipElementManager;
  private OutcomeSynthesisManager outcomeSynthesisManager;
  private IPIndicatorManager ipIndicatorManager;
  // Model for the front-end
  private List<LiaisonInstitution> liaisonInstitutions;
  private LiaisonInstitution currentLiaisonInstitution;
  private List<IPElement> midOutcomes;
  private List<OutcomeSynthesis> synthesis;


  private IPProgram program;


  private int liaisonInstitutionID;

  @Inject
  public OutcomeSynthesisAction(APConfig config, HistoryManager historyManager,
    LiaisonInstitutionManager liaisonInstitutionManager, IPProgramManager ipProgramManager,
    IPElementManager ipElementManager, IPIndicatorManager ipIndicatorManager, OutcomeSynthesisValidator validator,
    OutcomeSynthesisManager outcomeSynthesisManager) {
    super(config);
    this.validator = validator;
    this.historyManager = historyManager;
    this.ipIndicatorManager = ipIndicatorManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.ipProgramManager = ipProgramManager;
    this.ipElementManager = ipElementManager;
    this.outcomeSynthesisManager = outcomeSynthesisManager;
  }


  public String fmt(double d) {
    if (d == (long) d) {
      return String.format("%d", (long) d);
    } else {
      return String.format("%s", d);
    }
  }

  public String fmt(float d) {
    if (d == (long) d) {
      return String.format("%d", (long) d);
    } else {
      return String.format("%s", d);
    }
  }


  public LiaisonInstitution getCurrentLiaisonInstitution() {
    return currentLiaisonInstitution;
  }

  public int getIndex(int indicator, int midoutcome, int program) {
    OutcomeSynthesis synthe = new OutcomeSynthesis();
    synthe.setIndicadorId(indicator);
    synthe.setMidOutcomeId(midoutcome);
    synthe.setIpProgamId(program);

    int index = synthesis.indexOf(synthe);
    return index;

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

  public List<IPIndicator> getProjectIndicators(int year, int indicator, int midOutcome) {
    return ipIndicatorManager.getIndicatorsSyntesis(year, indicator, program.getId(), midOutcome);
  }

  public List<OutcomeSynthesis> getRegionalSynthesis(int indicator, int midoutcome) {
    List<OutcomeSynthesis> list = outcomeSynthesisManager.getOutcomeSynthesis(midoutcome, indicator);
    for (OutcomeSynthesis outcomeSynthesis : list) {
      outcomeSynthesis.setIpprogram(ipProgramManager.getIPProgramById(outcomeSynthesis.getIpProgamId()));
    }
    return list;
  }

  public List<OutcomeSynthesis> getSynthesis() {
    return synthesis;
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
    } catch (Exception e) {
      if (this.getCurrentUser().getLiaisonInstitution() != null) {

        liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().get(0).getId();

      } else {
        liaisonInstitutionID = 7;
      }
    }

    // Get the list of liaison institutions.
    liaisonInstitutions = liaisonInstitutionManager.getLiaisonInstitutionSynthesis();

    // Get currentLiaisonInstitution
    currentLiaisonInstitution = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);

    // Create an ipElementType with the identifier of the outcomes 2019 type
    IPElementType midOutcomesType = new IPElementType(APConstants.ELEMENT_TYPE_OUTCOME2019);

    int programID;
    try {
      programID = Integer.parseInt(currentLiaisonInstitution.getIpProgram());
    } catch (Exception e) {
      programID = 1;
      liaisonInstitutionID = 2;
      currentLiaisonInstitution = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);
    }
    program = ipProgramManager.getIPProgramById(programID);

    // Get Outcomes 2019 of current IPProgram
    midOutcomes = ipElementManager.getIPElements(program, midOutcomesType);
    synthesis = outcomeSynthesisManager.getOutcomeSynthesis(programID);

    for (OutcomeSynthesis synthe : synthesis) {
      if (synthe.getAchieved() != null) {
        synthe.setAchievedText(this.fmt(synthe.getAchieved()).replace(",", "."));

      }
      synthe.setAchievedExpectedText(this.fmt(synthe.getAchievedExpected()).replace(",", "."));
    }
    for (IPElement midoutcome : midOutcomes) {
      for (IPIndicator indicator : midoutcome.getIndicators()) {
        int indicatorId = indicator.getId();
        if (indicator.getParent() != null) {
          indicatorId = indicator.getParent().getId();
        }
        if (this.getIndex(indicatorId, midoutcome.getId(), program.getId()) == -1) {
          OutcomeSynthesis synthe = new OutcomeSynthesis();
          synthe.setIndicadorId(indicatorId);
          synthe.setMidOutcomeId(midoutcome.getId());
          synthe.setIpProgamId(program.getId());
          synthe.setYear(this.getCurrentReportingYear());
          synthe.setId(null);
          synthesis.add(synthe);

        }

      }
    }

  }


  @Override
  public String save() {
    for (OutcomeSynthesis synthe : synthesis) {
      try {
        synthe.setAchieved(Float.parseFloat(synthe.getAchievedText()));
      } catch (NumberFormatException e) {
        synthe.setAchieved(null);
      }
      outcomeSynthesisManager.saveOutcomeSynthesis(synthe);

    }

    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("saving.saved") + "</br>" + validationMessage);
    } else {
      this.addActionMessage("All required fields are filled. You've successfully completed your work. Thank you!");
    }

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


  public void setSynthesis(List<OutcomeSynthesis> synthesis) {
    this.synthesis = synthesis;
  }

  @Override
  public void validate() {
    if (save) {
      for (OutcomeSynthesis synthe : synthesis) {
        try {
          synthe.setAchieved(Float.parseFloat(synthe.getAchievedText()));
        } catch (NumberFormatException e) {
          synthe.setAchieved(null);
        }
      }

      validator.validate(this, synthesis);
    }
  }
}