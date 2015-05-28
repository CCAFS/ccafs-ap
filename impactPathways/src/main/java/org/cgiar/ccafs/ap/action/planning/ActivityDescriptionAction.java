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
import org.cgiar.ccafs.ap.config.APConstants;
import org.cgiar.ccafs.ap.data.manager.ActivityManager;
import org.cgiar.ccafs.ap.data.manager.ActivityPartnerManager;
import org.cgiar.ccafs.ap.data.manager.BudgetManager;
import org.cgiar.ccafs.ap.data.manager.IPCrossCuttingManager;
import org.cgiar.ccafs.ap.data.manager.InstitutionManager;
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.IPCrossCutting;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;
import org.cgiar.ccafs.utils.APConfig;

import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Galllego B.
 */
public class ActivityDescriptionAction extends BaseAction {

  private static final long serialVersionUID = -2523743477705227748L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(ActivityDescriptionAction.class);

  // Manager
  private ActivityManager activityManager;
  private ActivityPartnerManager activityPartnerManager;
  private InstitutionManager institutionManager;
  private IPCrossCuttingManager ipCrossCuttingManager;
  private ProjectManager projectManager;
  private BudgetManager budgetManager;

  // Model for the back-end
  private Activity activity;
  private boolean isOfficialLeader;

  // Model for the front-end
  private Project project;
  private int activityID;
  private boolean isExpected;
  private List<IPCrossCutting> ipCrossCuttings;
  private List<Institution> allPartners;

  @Inject
  public ActivityDescriptionAction(APConfig config, ActivityManager activityManager,
    InstitutionManager institutionManager, IPCrossCuttingManager ipCrossCuttingManager, ProjectManager projectManager,
    BudgetManager budgetManager, ActivityPartnerManager activityPartnerManager) {
    super(config);
    this.activityManager = activityManager;
    this.institutionManager = institutionManager;
    this.ipCrossCuttingManager = ipCrossCuttingManager;
    this.projectManager = projectManager;
    this.budgetManager = budgetManager;
    this.activityPartnerManager = activityPartnerManager;
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


  /**
   * This method returns an array of cross cutting ids depending on the project.crossCuttings attribute.
   * 
   * @return an array of integers.
   */
  public int[] getCrossCuttingIds() {
    if (this.activity.getCrossCuttings() != null) {
      int[] ids = new int[this.activity.getCrossCuttings().size()];
      for (int c = 0; c < ids.length; c++) {
        ids[c] = this.activity.getCrossCuttings().get(c).getId();
      }
      return ids;
    }
    return null;
  }

  public int getEndYear() {
    return config.getEndYear();
  }


  public List<IPCrossCutting> getIpCrossCuttings() {
    return ipCrossCuttings;
  }

  public Project getProject() {
    return project;
  }

  public int getStartYear() {
    return config.getStartYear();
  }

  public boolean isExpected() {
    return isExpected;
  }


  public boolean isOfficialLeader() {
    return isOfficialLeader;
  }


  @Override
  public String next() {
    String result = save();
    if (result.equals(BaseAction.SUCCESS)) {
      return BaseAction.NEXT;
    }
    return result;
  }

  @Override
  public void prepare() throws Exception {
    super.prepare();

    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));

    activity = activityManager.getActivityById(activityID);
    project = projectManager.getProjectFromActivityId(activityID);

    if (activity.getLeader() != null) {
      isExpected = false;
    } else {
      isExpected = true;
      // Let's create an empty user.
      if (activity.getExpectedLeader() == null) {
        User expectedLeader = new User();
        expectedLeader.setId(-1);
        activity.setExpectedLeader(expectedLeader);
      } else {
        // Setting isOfficialLeader variable.
        isOfficialLeader = activityManager.isOfficialExpectedLeader(activityID);
      }
    }

    // Getting the activity outcome information
    activity.setOutcome(activityManager.getActivityOutcome(activityID));

    // Getting the list of Cross Cutting Themes
    ipCrossCuttings = ipCrossCuttingManager.getIPCrossCuttings();

    // Getting the List of all Institutions
    allPartners = institutionManager.getAllInstitutions();

    // Getting the information of the Cross Cutting Themes associated with the project
    activity.setCrossCuttings(ipCrossCuttingManager.getIPCrossCuttingByActivityID(activityID));

  }

  @Override
  public String save() {
    // If user has enough privileges to save.
    if (this.isSaveable()) {
      boolean success = true;

      // Reviewing if there were any change in the year range from start date to end date in order to reflect those
      // modifications in the activity budget section.
      List<Integer> currentYears = activity.getAllYears();
      List<Integer> previousYears = activityManager.getActivityById(activity.getId()).getAllYears();
      // Deleting unused years from activity budget.
      for (Integer previousYear : previousYears) {
        if (!currentYears.contains(previousYear)) {
          budgetManager.deleteActivityBudgetByYear(activityID, previousYear.intValue());
        }
      }

      // Reviewing if there were any change in the institution of the activity leader.
      Institution previousLeadInstitution = null;
      if (isExpected) {
        User previousExpectedLeader = activityManager.getExpectedActivityLeader(activity.getId());
        if (previousExpectedLeader != null) {
          previousLeadInstitution = previousExpectedLeader.getCurrentInstitution();
        }
      } else {
        previousLeadInstitution = activityManager.getActivityLeader(activity.getId()).getCurrentInstitution();
      }
      if (previousLeadInstitution != null) {
        Institution currentLeadInstitution;
        if (isExpected) {
          currentLeadInstitution = activity.getExpectedLeader().getCurrentInstitution();
        } else {
          currentLeadInstitution = activity.getLeader().getCurrentInstitution();
        }
        if (!currentLeadInstitution.equals(previousLeadInstitution)) {
          budgetManager.deleteActivityBudgetsByInstitution(activity.getId(), previousLeadInstitution.getId());
        }
      }

      if (activity.getExpectedLeader() != null) {
        // Saving the information of the expected leader.
        int result =
          activityManager.saveExpectedActivityLeader(activityID, activity.getExpectedLeader(), isOfficialLeader);
        if (result > 0) {
          // if new record was added, we need to assign this new id to the activity.
          activity.getExpectedLeader().setId(result);
        }
      }
      // then, save the full information of the activity description, included the expected activity leader, if applies.
      int result = activityManager.saveActivity(project.getId(), activity);
      if (result < 0) {
        success = false;
      }

      // Saving Cross Cutting elements.
      ipCrossCuttingManager.deleteCrossCuttingsByActivity(activity.getId());
      for (IPCrossCutting ipCrossTheme : activity.getCrossCuttings()) {
        ipCrossCuttingManager.saveCrossCutting(activityID, ipCrossTheme.getId());
      }

      if (!activityManager.saveActivityOutcome(activity)) {
        success = false;
      }

      if (success == false) {
        addActionError(getText("saving.problem"));
        return BaseAction.INPUT;
      }
      addActionMessage(getText("saving.success", new String[] {getText("planning.activityDescription")}));
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

  public void setExpected(boolean isExpected) {
    this.isExpected = isExpected;
  }

  public void setIpCrossCuttings(List<IPCrossCutting> ipCrossCuttings) {
    this.ipCrossCuttings = ipCrossCuttings;
  }

  public void setOfficialLeader(boolean isOfficialLeader) {
    this.isOfficialLeader = isOfficialLeader;
  }

  @Override
  public void validate() {
    // Validate if the activity leader organization already exists as activity partner.
    boolean problem = false;

    Institution ledIntitution = null;
    if (activity.getLeader() != null) {
      ledIntitution = activity.getLeader().getCurrentInstitution();
    } else if (activity.getExpectedLeader() != null) {
      ledIntitution = activity.getExpectedLeader().getCurrentInstitution();
    }

    if (ledIntitution != null) {
      for (ActivityPartner partner : activityPartnerManager.getActivityPartnersByActivity(activity.getId())) {
        if (partner.getPartner().equals(ledIntitution)) {
          problem = true;
          addFieldError("activity.expectedLeader.currentInstitution",
            getText("preplanning.projectPartners.duplicatedInstitution.field"));
        }
      }
    }

    if (problem) {
      addActionError(getText("planning.activityDescription.duplicatedInstitution",
        new String[] {ledIntitution.getName()}));
    }

    super.validate();
  }
}
