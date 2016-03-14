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
import org.cgiar.ccafs.ap.data.manager.IndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.IndicatorType;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.ap.validation.synthesis.ProjectCrpIndicatorsValidator;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author Sebastian Amariles Garcia - CIAT/CCAFS
 */

public class CrpIndicatorsAction extends BaseAction {

  private static Logger LOG = LoggerFactory.getLogger(CrpIndicatorsAction.class);
  private static final long serialVersionUID = -3179251766947184219L;

  // Manager

  private ProjectCrpIndicatorsValidator validator;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private IndicatorReportManager indicatorsReportManager;
  // Model for the front-end
  private List<LiaisonInstitution> liaisonInstitutions;
  private List<IndicatorReport> indicatorReports;
  private List<IndicatorType> indicatorsType;

  private int liaisonInstitutionID;


  private int indicatorTypeID;

  @Inject
  public CrpIndicatorsAction(APConfig config, ProjectCrpIndicatorsValidator validator,
    LiaisonInstitutionManager liaisonInstitutionManager, IndicatorReportManager indicatorsReportManager) {
    super(config);

    this.validator = validator;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.indicatorsReportManager = indicatorsReportManager;
  }

  public List<IndicatorReport> getCrpIndicatorsByType(int type) {
    List<IndicatorReport> lst = new ArrayList<IndicatorReport>();
    for (IndicatorReport indicatorReport : indicatorReports) {
      if (indicatorReport.getIndicator().getType().getId() == type) {
        lst.add(indicatorReport);
      }
    }
    return lst;

  }


  public int getIndicatorIndex(int id, int type) {
    int c = 0;
    for (IndicatorReport indicatorReport : indicatorReports) {
      if (indicatorReport.getIndicator().getId() == id && indicatorReport.getIndicator().getType().getId() == type) {
        return c;
      }
      c++;
    }
    return -1;
  }

  public List<IndicatorReport> getIndicatorReports() {
    return indicatorReports;
  }

  public List<IndicatorType> getIndicatorsType() {
    return indicatorsType;
  }


  public int getIndicatorTypeID() {
    return indicatorTypeID;
  }

  public int getLiaisonInstitutionID() {
    return liaisonInstitutionID;
  }

  public List<LiaisonInstitution> getLiaisonInstitutions() {
    return liaisonInstitutions;
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
        liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().get(0).getId();
        if (this.hasSynthesisPermission("update", liaisonInstitutionID)) {
          liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().get(0).getId();
          if (liaisonInstitutionID == 1) {
            liaisonInstitutionID = 2;
          }
        } else {
          liaisonInstitutionID = 2;
        }
      } else {
        liaisonInstitutionID = 2;
      }
    }

    try {
      indicatorTypeID =
        Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.INDICATOR_TYPE_REQUEST_ID)));
    } catch (NumberFormatException e) {
      indicatorTypeID = 1;
    }
    indicatorReports =
      indicatorsReportManager.getIndicatorReportsList(liaisonInstitutionID, this.getCurrentReportingYear());
    // Get the list of liaison institutions.
    liaisonInstitutions = liaisonInstitutionManager.getLiaisonInstitutionsCrpsIndicator();
    indicatorsType = indicatorsReportManager.getIndicatorReportsType();
    // indicatorReports = indicatorReportManager.getIndicatorReports(liaisonInstitutionID,
    // this.getCurrentReportingYear(), indicatorTypeID);

  }


  @Override
  public String save() {

    LiaisonInstitution leader = liaisonInstitutionManager.getLiaisonInstitution(liaisonInstitutionID);
    for (IndicatorReport indicatorReport : indicatorReports) {
      indicatorReport.setLiaisonInstitution(leader);
    }
    indicatorsReportManager.saveIndicatorReportsList(indicatorReports, leader);
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

  public void setIndicatorsType(List<IndicatorType> indicatorsType) {
    this.indicatorsType = indicatorsType;
  }

  public void setLiaisonInstitutionID(int liaisonInstitutionID) {
    this.liaisonInstitutionID = liaisonInstitutionID;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, indicatorReports);
    }
  }
}