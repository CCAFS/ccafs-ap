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
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;

import java.util.List;

import org.cgiar.ccafs.ap.data.manager.ActivityManager;

import org.cgiar.ccafs.ap.data.model.Activity;
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
  private ActivityManager activityManager;
  private ActivityPartnerManager activityPartnerManager;
  private InstitutionManager institutionManager;
  private LocationManager locationManager;

  // Model for the back-end
  private Activity activity;

  // Model for the front-end
  private int activityID;
  private List<Institution> allPartners;
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;


  @Inject
  public ActivityPartnersAction(APConfig config, ActivityManager activityManager,
    InstitutionManager institutionManager, ActivityPartnerManager activityPartnerManager,
    LocationManager locationManager) {
    super(config);
    this.institutionManager = institutionManager;
    this.activityPartnerManager = activityPartnerManager;
    this.locationManager = locationManager;
    this.activityManager = activityManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public List<Institution> getAllPartners() {
    return allPartners;
  }


  public List<Country> getCountries() {
    return countries;
  }

  public List<InstitutionType> getPartnerTypes() {
    return partnerTypes;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    // Getting the activity ID parameter.
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityById(activityID);

    // Getting the activity partners
    List<ActivityPartner> activityPartners = activityPartnerManager.getActivityPartnersByActivity(activityID);
    activity.setActivityPartners(activityPartners);

    // Getting the List of all institutions
    allPartners = institutionManager.getAllInstitutions();

    // Getting all the countries
    countries = locationManager.getInstitutionCountries();

    // Getting all partner types
    partnerTypes = institutionManager.getAllInstitutionTypes();

    if (getRequest().getMethod().equalsIgnoreCase("post")) {
      // Clear out the list if it has some element
      if (activity.getActivityPartners() != null) {
        activity.getActivityPartners().clear();
      }
    }

    // TODO HT - TEST (To Remove)
    // this.setSaveable(false);

  }

  @Override
  public String save() {
    System.out.println(activity.getActivityPartners());
    // TODO HT - To Complete.
    return BaseAction.SUCCESS;
  }

  public void setActivity(Activity activity) {
    this.activity = activity;
  }

  public void setActivityID(int activityID) {
    this.activityID = activityID;
  }

  public void setAllPartners(List<Institution> allPartners) {
    this.allPartners = allPartners;
  }

}