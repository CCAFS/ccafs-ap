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
import org.cgiar.ccafs.ap.data.manager.IndicatorReportManager;
import org.cgiar.ccafs.ap.data.manager.LiaisonInstitutionManager;
import org.cgiar.ccafs.ap.data.model.Indicator;
import org.cgiar.ccafs.ap.data.model.IndicatorReport;
import org.cgiar.ccafs.ap.data.model.IndicatorType;
import org.cgiar.ccafs.ap.data.model.LiaisonInstitution;
import org.cgiar.ccafs.utils.APConfig;

import java.util.ArrayList;
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

  private HistoryManager historyManager;
  private LiaisonInstitutionManager liaisonInstitutionManager;
  private IndicatorReportManager indicatorsReportManager;
  // Model for the front-end
  private List<LiaisonInstitution> liaisonInstitutions;
  private List<IndicatorReport> indicatorReports;

  private int liaisonInstitutionID;
  private int indicatorTypeID;


  @Inject
  public CrpIndicatorsAction(APConfig config, HistoryManager historyManager,
    LiaisonInstitutionManager liaisonInstitutionManager, IndicatorReportManager indicatorsReportManager) {
    super(config);

    this.historyManager = historyManager;
    this.liaisonInstitutionManager = liaisonInstitutionManager;
    this.indicatorsReportManager = indicatorsReportManager;
  }


  public List<IndicatorReport> getCrpIndicators(int instituion, int type) {
    List<IndicatorReport> list =
      indicatorsReportManager.getIndicatorReportsList(instituion, this.getCurrentReportingYear(), type);
    return list;

  }


  public List<IndicatorReport> getIndicatorReports() {
    return indicatorReports;
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
        liaisonInstitutionID = this.getCurrentUser().getLiaisonInstitution().getId();
      } else {
        liaisonInstitutionID = 1;
      }
    }

    try {
      indicatorTypeID =
        Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.INDICATOR_TYPE_REQUEST_ID)));
    } catch (NumberFormatException e) {
      indicatorTypeID = 1;
    }
    indicatorReports = this.getCrpIndicators(liaisonInstitutionID, indicatorTypeID);
    // Get the list of liaison institutions.
    liaisonInstitutions = liaisonInstitutionManager.getLiaisonInstitutions();

    // indicatorReports = indicatorReportManager.getIndicatorReports(liaisonInstitutionID,
    // this.getCurrentReportingYear(), indicatorTypeID);
    indicatorReports = new ArrayList<>();
    for (int i = 1; i <= 5; i++) {
      IndicatorReport indicatorReport = new IndicatorReport();
      indicatorReport.setId(i);
      indicatorReport.setYear(this.getCurrentReportingYear());
      indicatorReport.setTarget("target" + i);
      indicatorReport.setDeviation("deviation" + i);
      indicatorReport.setNextYearTarget("nextYearTarget" + i);
      indicatorReport.setActual("actual" + i);
      Indicator indicator = new Indicator();
      indicator.setId(100 + i);
      indicator.setName(
        "Number of multi-stakeholder R4D innovation platforms established for the targeted agro-ecosystems by the CRPs #"
          + (100 + i));
      indicator.setDescription("Description #" + (100 + i));
      IndicatorType type = new IndicatorType(1000 + i, "Type 1");
      indicator.setType(type);
      indicatorReport.setIndicator(indicator);

      indicatorReports.add(indicatorReport);
    }

  }

  @Override
  public String save() {
    return SUCCESS;
  }

  public void setLiaisonInstitutionID(int liaisonInstitutionID) {
    this.liaisonInstitutionID = liaisonInstitutionID;
  }

  @Override
  public void validate() {
    if (save) {
    }
  }
}