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
import org.cgiar.ccafs.ap.data.manager.ProjectManager;
import org.cgiar.ccafs.ap.data.model.Activity;
import org.cgiar.ccafs.ap.data.model.ActivityPartner;
import org.cgiar.ccafs.ap.data.model.Budget;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.Institution;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Javier Andrés Gallego B.
 * @author Héctor Fabio Tobón R.
 */
public class ActivityBudgetAction extends BaseAction {

  private static final long serialVersionUID = -5205284667878145240L;

  public static Logger LOG = LoggerFactory.getLogger(ActivityBudgetAction.class);

  // Model for the back-end
  private int activityID;
  private Activity activity;
  private int year;


  // Model for the front-end
  private List<Integer> allYears;
  private List<Institution> allInstitutions;
  private List<ActivityPartner> activityPartners;
  private User activityLeader;
  private boolean hasLeader;
  private boolean invalidYear;
  private Map<String, Budget> mapBudgets;
  private double totalActivitiesBudget;
  private double totalActivitiesBudgetByYear;
  private Project project;

  // Managers
  private ActivityManager activityManager;
  private BudgetManager budgetManager;
  private InstitutionManager institutionManager;
  private ActivityPartnerManager partnerManager;
  private ProjectManager projectManager;

  @Inject
  public ActivityBudgetAction(APConfig config, ActivityManager activityManager, BudgetManager budgetManager,
    InstitutionManager institutionManager, ActivityPartnerManager partnerManager, ProjectManager projectManager) {
    super(config);
    this.activityManager = activityManager;
    this.budgetManager = budgetManager;
    this.institutionManager = institutionManager;
    this.partnerManager = partnerManager;
    this.projectManager = projectManager;
    this.hasLeader = true;
    this.invalidYear = false;
  }

  /**
   * This method returns a Map of Budgets. The key of this map is a composition of the year, the institution id and the
   * type.
   * e.g. 2014-9-ActivityID
   * Where 2014 is the year, 9 is the institution identifier and ActivityID is the budget type.
   * If the budget is not in the database, this method will create a new one with an id=-1 and amount=0.
   *
   * @return a Map of budgets as was described above.
   */
  private Map<String, Budget> generateMapBudgets(int year) {
    Map<String, Budget> budgetsMap = new HashMap<String, Budget>();

    // activity partners
    for (ActivityPartner activityPartner : activityPartners) {
      boolean activtyBudget = false;
      for (Budget budget : activity.getBudgets()) {
        if (budget.getInstitution().getId() == activityPartner.getPartner().getId() && budget.getYear() == year) {
          if (budget.getType().getValue() == BudgetType.ACTIVITY.getValue()) {
            activtyBudget = true;
            budgetsMap
              .put(year + "-" + activityPartner.getPartner().getId() + "-" + BudgetType.ACTIVITY.name(), budget);
          }
        }
      }
      if (activtyBudget == false) {
        Budget newBudget = new Budget();
        newBudget.setId(-1);
        newBudget.setInstitution(activityPartner.getPartner());
        newBudget.setType(BudgetType.ACTIVITY);
        newBudget.setAmount(0);
        newBudget.setYear(year);
        budgetsMap.put(year + "-" + activityPartner.getPartner().getId() + "-" + BudgetType.ACTIVITY.name(), newBudget);
      }

    }
    // Activity leader
    boolean activtyBudget = false;
    for (Budget budget : activity.getBudgets()) {
      if (budget.getInstitution().getId() == activity.getLeader().getCurrentInstitution().getId()
        && budget.getYear() == year) {
        if (budget.getType().getValue() == BudgetType.ACTIVITY.getValue()) {
          activtyBudget = true;
          budgetsMap.put(
            year + "-" + activity.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.ACTIVITY.name(),
            budget);
        }
      }
    }
    if (activtyBudget == false) {
      Budget newBudget = new Budget();
      newBudget.setId(-1);
      newBudget.setInstitution(activity.getLeader().getCurrentInstitution());
      newBudget.setType(BudgetType.ACTIVITY);
      newBudget.setAmount(0);
      newBudget.setYear(year);
      budgetsMap
        .put(year + "-" + activity.getLeader().getCurrentInstitution().getId() + "-" + BudgetType.ACTIVITY.name(),
          newBudget);
    }
    return budgetsMap;
  }

  public Activity getActivity() {
    return activity;
  }

  public int getActivityID() {
    return activityID;
  }

  public User getActivityLeader() {
    return activityLeader;
  }

  public List<ActivityPartner> getActivityPartners() {
    return activityPartners;
  }

  public String getActivityRequest() {
    return APConstants.ACTIVITY_REQUEST_ID;
  }

  public List<Institution> getAllInstitutions() {
    return allInstitutions;
  }

  public List<Integer> getAllYears() {
    return allYears;
  }

  public Map<String, Budget> getMapBudgets() {
    return mapBudgets;
  }

  public Project getProject() {
    return project;
  }

  public double getTotalActivitiesBudget() {
    return totalActivitiesBudget;
  }

  public double getTotalActivitiesBudgetByYear() {
    return totalActivitiesBudgetByYear;
  }

  public int getYear() {
    return year;
  }

  public boolean isHasLeader() {
    return hasLeader;
  }

  public boolean isInvalidYear() {
    return invalidYear;
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

    // Getting the activity id from the URL parameters.
    // It's assumed that the project parameter is ok. (@See ValidateProjectParameterInterceptor)
    String parameter;
    activityID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter(APConstants.ACTIVITY_REQUEST_ID)));

    // Getting the activity identified with the id parameter.
    activity = activityManager.getActivityById(activityID);
    // Getting the project where this activity belongs to.
    project = projectManager.getProjectFromActivityId(activityID);

    // Getting all the years of the activity.
    allYears = activity.getAllYears();
    // If there are not years, we stop here.
    if (allYears.size() > 0) {

      // Getting the year from the URL parameters.
      try {
        parameter = this.getRequest().getParameter(APConstants.YEAR_REQUEST);
        if (parameter != null) {
          year = Integer.parseInt(StringUtils.trim(parameter));
        } else {
          year = allYears.get(0);
        }
      } catch (NumberFormatException e) {
        LOG.error("-- prepare() > There was an error parsing the year '{}'.", year);
        return; // Stop here and go to the execute method.
      }

      // Validating if the year requested in the URL is part of the activity years.
      if (allYears.contains(new Integer(year))) {
        // We validate if the partner leader is already in the employees table. If so, we get this
        // information. If not, we load the information from expected project leader.
        User activityLeader = activityManager.getActivityLeader(activity.getId());
        // if the official leader is defined.
        if (activityLeader != null) {
          activity.setLeader(activityLeader);
        } else {
          activity.setLeader(activityManager.getExpectedActivityLeader(activityID));
        }
        // if the project leader is still not defined, stop here.
        if (activity.getLeader() != null) {

          // Getting the Total Overall Project Budget
          totalActivitiesBudget = budgetManager.calculateTotalActivityBudget(activityID);
          totalActivitiesBudgetByYear = budgetManager.calculateTotalActivityBudgetByYear(activityID, year);

          // Getting all the activity partners.
          activityPartners = partnerManager.getActivityPartnersByActivity(activityID);

          // Getting all the institutions.
          allInstitutions = institutionManager.getAllInstitutions();
          // Adding header - place holder.
          Institution headerInstitution = new Institution();
          headerInstitution.setId(-1);
          headerInstitution.setName(getText("preplanning.projectBudget.institutionList.header"));
          allInstitutions.add(0, headerInstitution);

          // Removing the institution that is already added as project partner:
          allInstitutions.remove(activity.getLeader().getCurrentInstitution());
          // Removing those institutions that were added in project partners.
          for (ActivityPartner activityParner : activityPartners) {
            allInstitutions.remove(activityParner.getPartner());
          }

          // Getting the list of budgets.
          activity.setBudgets(budgetManager.getActivityBudgetsByYear(activity.getId(), year));
          // Creating budgets that do not exist.
          mapBudgets = generateMapBudgets(year);

          if (getRequest().getMethod().equalsIgnoreCase("post")) {
            // Clear out the list if it has some element
            if (activity.getBudgets() != null) {
              activity.getBudgets().clear();
            }
          }

        } else {
          hasLeader = false;
        }
      } else {
        invalidYear = true;
      }
    }
  }


  @Override
  public String save() {
    if (this.isSaveable()) {
      boolean success = true;

      // Saving project budgets
      for (Budget budget : activity.getBudgets()) {
        boolean saved = budgetManager.saveActivityBudget(activityID, budget);
        if (!saved) {
          success = false;
        }
      }

      if (!success) {
        addActionError(getText("saving.problem"));
        return BaseAction.INPUT;
      } else {
        addActionMessage(getText("saving.success", new String[] {getText("planning.activityBudget.title")}));
        return BaseAction.SUCCESS;
      }
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


  public void setActivityLeader(User activityLeader) {
    this.activityLeader = activityLeader;
  }


  public void setActivityPartners(List<ActivityPartner> activityPartners) {
    this.activityPartners = activityPartners;
  }


  public void setAllInstitutions(List<Institution> allInstitutions) {
    this.allInstitutions = allInstitutions;
  }


  public void setAllYears(List<Integer> allYears) {
    this.allYears = allYears;
  }


  public void setHasLeader(boolean hasLeader) {
    this.hasLeader = hasLeader;
  }


  public void setInvalidYear(boolean invalidYear) {
    this.invalidYear = invalidYear;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setTotalActivitiesBudget(double totalActivitiesBudget) {
    this.totalActivitiesBudget = totalActivitiesBudget;
  }

  public void setTotalActivitiesBudgetByYear(double totalActivitiesBudgetByYear) {
    this.totalActivitiesBudgetByYear = totalActivitiesBudgetByYear;
  }

  public void setYear(int year) {
    this.year = year;
  }

}
