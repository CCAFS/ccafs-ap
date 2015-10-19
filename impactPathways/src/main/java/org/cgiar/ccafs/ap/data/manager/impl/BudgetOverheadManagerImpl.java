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

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.BudgetOverheadDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetOverheadManager;
import org.cgiar.ccafs.ap.data.model.BudgetOverhead;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class BudgetOverheadManagerImpl implements BudgetOverheadManager {

  private BudgetOverheadDAO overheadDAO;

  @Inject
  public BudgetOverheadManagerImpl(BudgetOverheadDAO overheadDAO) {
    this.overheadDAO = overheadDAO;
  }

  @Override
  public BudgetOverhead getProjectBudgetOverhead(int projectID) {
    BudgetOverhead budgetOverhead = new BudgetOverhead();
    Map<String, String> overheadData = overheadDAO.getProjectBudgetOverhead(projectID);

    if (!overheadData.isEmpty()) {
      budgetOverhead.setId(Integer.parseInt(overheadData.get("id")));
      budgetOverhead.setBilateralCostRecovered(overheadData.get("cost_recovered").equals("1"));
      budgetOverhead.setContractedOverhead(Double.parseDouble(overheadData.get("contracted_overhead")));
    }

    return budgetOverhead;
  }

  @Override
  public boolean saveProjectBudgetOverhead(Project project, User user, String justification) {
    Map<String, Object> overheadData = new HashMap<>();
    overheadData.put("project_id", project.getId());
    overheadData.put("cost_recovered", project.getOverhead().isBilateralCostRecovered());
    overheadData.put("contracted_overhead", project.getOverhead().getContractedOverhead());
    overheadData.put("user_id", user.getId());
    overheadData.put("justification", justification);

    return overheadDAO.saveProjectBudgetOverhead(overheadData);
  }
}
