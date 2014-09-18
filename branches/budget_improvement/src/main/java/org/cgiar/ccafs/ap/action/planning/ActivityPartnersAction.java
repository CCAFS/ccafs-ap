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
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.LocationManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Country;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.InstitutionType;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
  private ProjectManager projectManager;
  private LocationManager locationManager;
  private BudgetManager budgetManager;

  // Model for the back-end
  private Activity activity;

  // Model for the front-end
  private Project project;
  private int activityID;
  private List<Institution> allPartners;
  private List<InstitutionType> partnerTypes;
  private List<Country> countries;

  @Inject
  public ActivityPartnersAction(APConfig config, ActivityManager activityManager,
    InstitutionManager institutionManager, ActivityPartnerManager activityPartnerManager,
    LocationManager locationManager, ProjectManager projectManager, BudgetManager budgetManager) {
    super(config);
    this.institutionManager = institutionManager;
    this.activityPartnerManager = activityPartnerManager;
    this.activityManager = activityManager;
    this.projectManager = projectManager;
    this.locationManager = locationManager;
    this.budgetManager = budgetManager;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public String getActivityRequest() {
    return APConstants.ACTIVITY_REQUEST_ID;
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

  public Project getProject() {
    return project;
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
    super.prepare();

    // Getting the activity ID parameter.
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));
    activity = activityManager.getActivityById(activityID);

    // Getting the project object of activity.
    project = projectManager.getProjectFromActivityId(activityID);

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
    // If user has privileges to save.
    if (this.isSaveable()) {
      boolean success = true;
      boolean saved;

      // ------------------ ACTIVIY PARTNERS -------------------
      // Getting previous Activity Partners so we can identify which of them should be removed from the database.
      List<ActivityPartner> previousActivityPartners = activityPartnerManager.getActivityPartnersByActivity(activityID);

      // Deleting activity partners
      for (ActivityPartner activityPartner : previousActivityPartners) {
        if (!activity.getActivityPartners().contains(activityPartner)) {
          boolean deleted = activityPartnerManager.deleteActivityPartner(activityPartner.getId());
          if (!deleted) {
            success = false;
          }
        }
      }

      for (ActivityPartner activityPartner : activity.getActivityPartners()) {
        saved = activityPartnerManager.saveActivityPartner(activityID, activityPartner);
        if (!saved) {
          success = false;
        }
      }

      // --------------------------------------------------------

      // --------------- ACTIVITY BUDGETS -----------------------

      // Getting Activity Institutions
      List<Institution> activityInstitutions = new ArrayList<>();
      for (ActivityPartner activityPartner : activity.getActivityPartners()) {
        activityInstitutions.add(activityPartner.getPartner());
      }
      // Adding also the activity leader institution.
      if (activity.getLeader() != null) {
        activityInstitutions.add(activity.getLeader().getCurrentInstitution());
      } else if (activity.getExpectedLeader() != null) {
        activityInstitutions.add(activity.getExpectedLeader().getCurrentInstitution());
      }

      // Getting all the current budget institutions for the activity.
      List<Institution> budgetInstitutions = budgetManager.getActivityInstitutionsBudgets(activity.getId());

      // Deleting Institutions from budget section
      for (Institution institutionToDelete : budgetInstitutions) {
        if (!activityInstitutions.contains(institutionToDelete)) {
          budgetManager.deleteActivityBudgetsByInstitution(activity.getId(), institutionToDelete.getId());
        }
      }

      // -----------------------------------------------------------


      if (success == false) {
        addActionError(getText("saving.problem"));
        return BaseAction.INPUT;
      }
      addActionMessage(getText("saving.success", new String[] {getText("planning.activityPartner.title")}));
      return BaseAction.SUCCESS;
    } else {
      return BaseAction.NOT_AUTHORIZED;
    }

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

  @Override
  public void validate() {// Validate if there are duplicate institutions.
    boolean problem = false;
    Set<Institution> institutions = new HashSet<>();
    if (activity.getLeader() != null) {
      institutions.add(activity.getLeader().getCurrentInstitution());
    } else if (activity.getExpectedLeader() != null) {
      institutions.add(activity.getExpectedLeader().getCurrentInstitution());
    }
    for (int c = 0; c < activity.getActivityPartners().size(); c++) {
      if (!institutions.add(activity.getActivityPartners().get(c).getPartner())) {
        addFieldError("activity.activityPartners[" + c + "].partner",
          getText("planning.activityPartner.duplicatedInstitution.field"));
        problem = true;
      }
    }

    if (problem) {
      addActionError(getText("planning.activityPartner.duplicatedInstitution.general"));
    }

    super.validate();
  }

}