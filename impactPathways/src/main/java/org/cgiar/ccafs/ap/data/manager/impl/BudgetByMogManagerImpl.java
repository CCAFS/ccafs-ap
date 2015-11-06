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

import org.cgiar.ccafs.ap.data.dao.BudgetByMogDAO;
import org.cgiar.ccafs.ap.data.manager.BudgetByMogManager;
import org.cgiar.ccafs.ap.data.model.BudgetType;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.OutputBudget;
import org.cgiar.ccafs.ap.data.model.Project;
import org.cgiar.ccafs.ap.data.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


/**
 * @author Hernán David Carvajal B. - CIAT/CCAFS
 */

public class BudgetByMogManagerImpl implements BudgetByMogManager {

  private BudgetByMogDAO budgetByMogDAO;

  @Inject
  public BudgetByMogManagerImpl(BudgetByMogDAO budgetByMOGDAO) {
    this.budgetByMogDAO = budgetByMOGDAO;
  }

  @Override
  public List<OutputBudget> getProjectOutputsBudget(int projectID) {
    List<OutputBudget> outputBudgets = new ArrayList<>();
    List<Map<String, String>> budgetByMogData = budgetByMogDAO.getProjectOutputsBudget(projectID);

    for (Map<String, String> data : budgetByMogData) {
      OutputBudget oBudget = new OutputBudget();
      oBudget.setId(Integer.parseInt(data.get("id")));
      oBudget.setTotalContribution(Double.parseDouble(data.get("total_contribution")));
      oBudget.setGenderContribution(Double.parseDouble(data.get("gender_contribution")));
      oBudget.setYear(Integer.parseInt(data.get("year")));

      IPElement output = new IPElement();
      output.setId(Integer.parseInt(data.get("output_id")));
      output.setDescription(data.get("output_description"));
      oBudget.setOutput(output);

      outputBudgets.add(oBudget);
    }

    return outputBudgets;
  }

  @Override
  public List<OutputBudget> getProjectOutputsBudgetByTypeAndYear(int projectID, int budgetTypeID, int year) {
    List<OutputBudget> outputBudgets = new ArrayList<>();
    List<Map<String, String>> budgetByMogData =
      budgetByMogDAO.getProjectOutputsBudgetByYear(projectID, budgetTypeID, year);

    for (Map<String, String> data : budgetByMogData) {
      OutputBudget oBudget = new OutputBudget();
      oBudget.setId(Integer.parseInt(data.get("id")));
      oBudget.setTotalContribution(Double.parseDouble(data.get("total_contribution")));
      oBudget.setGenderContribution(Double.parseDouble(data.get("gender_contribution")));
      oBudget.setType(BudgetType.getBudgetType(Integer.parseInt(data.get("budget_type"))));
      oBudget.setYear(Integer.parseInt(data.get("year")));

      IPElement output = new IPElement();
      output.setId(Integer.parseInt(data.get("output_id")));
      output.setDescription(data.get("output_description"));
      oBudget.setOutput(output);

      outputBudgets.add(oBudget);
    }

    return outputBudgets;

  }

  @Override
  public boolean saveProjectOutputsBudget(Project project, User user, String justification) {
    boolean saved = true;

    for (OutputBudget ob : project.getOutputsBudgets()) {
      Map<String, Object> data = new HashMap<>();
      data.put("id", ob.getId());

      data.put("project_id", project.getId());
      data.put("mog_id", ob.getOutput().getId());
      data.put("budget_type", ob.getType().getValue());
      data.put("year", ob.getYear());
      data.put("total_contribution", ob.getTotalContribution());
      data.put("gender_contribution", ob.getGenderContribution());

      saved = saved && budgetByMogDAO.saveProjectOutputsBudget(data, user.getId(), justification);
    }

    return saved;
  }
}
