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
import org.cgiar.ccafs.ap.data.manager.IPProgramManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.manager.MogSynthesisManager;
import org.cgiar.ccafs.ap.data.manager.ProjectContributionOverviewManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPProgram;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.data.model.MogSynthesis;
import org.cgiar.ccafs.ap.data.model.OutputOverview;
import org.cgiar.ccafs.ap.validation.synthesis.MogSynthesisValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Sebastian Amariles Garcia - CIAT/CCAFS
 */

public class SynthesisByMogAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(SynthesisByMogAction.class);
  private static final long serialVersionUID = -3179251766947184219L;

  // Manager
  private MogSynthesisValidator validator;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private IPProgramManager ipProgramManager;
  private IPElementManager ipElementManager;
  private ProjectContributionOverviewManager overviewManager;
  // Model for the front-end
  private List<LiaisonInstitution> liaisonInstitutions;
  private LiaisonInstitution currentLiaisonInstitution;
  private List<IPElement> mogs;
  private IPProgram program;
  private List<MogSynthesis> synthesis;
  private MogSynthesisManager mogSynthesisManager;
  private int liaisonInstitutionID;


  @Inject
  public SynthesisByMogAction(APConfig config, HistoryManager historyManager,
    LiaisonInstitutionManager liaisonInstitutionManager, IPProgramManager ipProgramManager,
    IPElementManager ipElementManager, ProjectContributionOverviewManager overviewManager,
    MogSynthesisManager mogSynthesisManager, MogSynthesisValidator validator) {
    super(config);
    this.overviewManager = overviewManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.ipProgramManager = ipProgramManager;
    this.ipElementManager = ipElementManager;
    this.mogSynthesisManager = mogSynthesisManager;
    this.validator = validator;
  }

  public LiaisonInstitution getCurrentLiaisonInstitution() {
    return currentLiaisonInstitution;
  }


  public int getIndex(int midoutcome, int program) {
    MogSynthesis synthe = new MogSynthesis();

    synthe.setMogId(midoutcome);
    synthe.setProgramId(program);

    int index = synthesis.indexOf(synthe);
    return index;

  }

  public int getLiaisonInstitutionID() {
    return liaisonInstitutionID;
  }

  public List<LiaisonInstitution> getLiaisonInstitutions() {
    return liaisonInstitutions;
  }

  public List<IPElement> getMogs() {
    return mogs;
  }

  public IPProgram getProgram() {
    return program;
  }

  public List<OutputOverview> getProjectOutputOverviews(int mogId) {
    return overviewManager.getProjectContributionOverviewsSytnhesis(mogId, config.getReportingCurrentYear(),
      program.getId());
  }

  public List<MogSynthesis> getRegionalSynthesis(int midoutcome) {
    List<MogSynthesis> list = mogSynthesisManager.getMogSynthesisRegions(midoutcome);
    for (MogSynthesis mogSynthesis : list) {
      mogSynthesis.setIpProgam(ipProgramManager.getIPProgramById(mogSynthesis.getProgramId()));
    }
    return list;
  }

  public List<MogSynthesis> getSynthesis() {
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

        liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().getId();

      } else {
        liaisonInstitutionID = 7;
      }
    }

    // Get the list of liaison institutions.
    liaisonInstitutions = liaisonInstitutionManager.getLiaisonInstitutionSynthesis();

    // Get currentLiaisonInstitution
    currentLiaisonInstitution = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);


    int programID;
    try {
      programID = Integer.parseInt(currentLiaisonInstitution.getIpProgram());
    } catch (Exception e) {
      programID = 1;
      liaisonInstitutionID = 2;
      currentLiaisonInstitution = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);

    }
    program = ipProgramManager.getIPProgramById(programID);

    // Get all MOGs manually

    mogs = ipElementManager.getIPElementListForSynthesis(program);
    synthesis = mogSynthesisManager.getMogSynthesis(programID);

    for (IPElement mog : mogs) {

      if (this.getIndex(mog.getId(), programID) == -1) {
        MogSynthesis synthe = new MogSynthesis();

        synthe.setMogId(mog.getId());
        synthe.setProgramId(program.getId());
        synthe.setYear(this.getCurrentReportingYear());
        synthe.setId(null);
        synthesis.add(synthe);

      }

    }
  }


  @Override
  public String save() {

    for (MogSynthesis synthe : synthesis) {

      mogSynthesisManager.saveMogSynthesis(synthe);

    }

    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("saving.saved") + validationMessage);
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

  public void setMogs(List<IPElement> mogs) {
    this.mogs = mogs;
  }

  public void setProgram(IPProgram program) {
    this.program = program;
  }

  public void setSynthesis(List<MogSynthesis> synthesis) {
    this.synthesis = synthesis;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, synthesis);
    }
  }
}