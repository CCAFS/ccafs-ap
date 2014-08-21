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
package org.cgiar.ccafs.ap.action.planning;

import org.cgiar.ccafs.ap.action.BaseAction;
import org.cgiar.ccafs.ap.config.APConfig;
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Institution;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 */
public class ActivityPartnersAction extends BaseAction {

  private static final long serialVersionUID = -7191071015257932492L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityPartnersAction.class);

  // Manager
  private ActivityPartnerManager activityPartnerManager;
  private InstitutionManager institutionManager;

  // Model for the back-end
  private List<ActivityPartner> activityPartners;

  // Model for the front-end
  private int activityID;
  private List<Institution> allPartners;


  @Inject
  public ActivityPartnersAction(APConfig config, InstitutionManager institutionManager,
    ActivityPartnerManager activityPartnerManager) {
    super(config);
    this.institutionManager = institutionManager;
    this.activityPartnerManager = activityPartnerManager;
  }


  public int getActivityID() {
    return activityID;
  }

  public List<ActivityPartner> getActivityPartners() {
    return activityPartners;
  }


  public List<Institution> getAllPartners() {
    return allPartners;
  }


  @Override
  public void prepare() throws Exception {
    super.prepare();
    try {
      activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    } catch (NumberFormatException e) {
      LOG.error("-- prepare() > There was an error parsing the activity identifier '{}'.", activityID, e);
      activityID = -1;
      return; // Stop here and go to execute method.
    }

    // Getting the information for the activity partner
    activityPartners = activityPartnerManager.getActivityPartnersByActivity(activityID);
    // Getting the List of Institutions
    allPartners = institutionManager.getAllInstitutions();
  }


// @Override
// public String save() {
// boolean success = true;
// // Saving Project Outcome
//
// //boolean saved = activityPartnerManager.saveActivityPartner(activityID, activityPartner);
// if (!saved) {
// success = false;
// }
// return INPUT;
// }


  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }


  public void setActivityPartners(List<ActivityPartner> activityPartners) {
    this.activityPartners = activityPartners;
  }


  public void setAllPartners(List<Institution> allPartners) {
    this.allPartners = allPartners;
  }

}