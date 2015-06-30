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

import org.cgiar.ccafs.ap.data.dao.IPIndicatorDAO;
import org.cgiar.ccafs.ap.data.manager.IPIndicatorManager;
import org.cgiar.ccafs.ap.data.model.IPElement;
import org.cgiar.ccafs.ap.data.model.IPIndicator;
import org.cgiar.ccafs.ap.data.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class IPIndicatorManagerImpl implements IPIndicatorManager {

  private IPIndicatorDAO indicatorDAO;

  @Inject
  public IPIndicatorManagerImpl(IPIndicatorDAO indicatorDAO) {
    this.indicatorDAO = indicatorDAO;
  }

  @Override
  public List<IPIndicator> getElementIndicators(IPElement element) {
    List<IPIndicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsDataList = indicatorDAO.getIndicatorsByElementID(element.getId());

    for (Map<String, String> iData : indicatorsDataList) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));

      // Parent indicator
      if (iData.get("parent_id") != null) {
        IPIndicator parent = new IPIndicator();
        parent.setId(Integer.parseInt(iData.get("parent_id")));
        parent.setDescription(iData.get("parent_description"));
        indicator.setParent(parent);
      }

      indicators.add(indicator);
    }

    return indicators;
  }

  @Override
  public IPIndicator getIndicator(int indicatorID) {
    Map<String, String> iData = indicatorDAO.getIndicator(indicatorID);

    if (!iData.isEmpty()) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));

      // Parent indicator
      if (iData.get("parent_id") != null) {
        IPIndicator parent = new IPIndicator();
        parent.setId(Integer.parseInt(iData.get("parent_id")));
        parent.setDescription(iData.get("parent_description"));
        indicator.setParent(parent);
      }

      return indicator;
    }
    return null;
  }

  @Override
  public List<IPIndicator> getIndicatorsByParent(IPIndicator indicator) {
    List<IPIndicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsDataList = indicatorDAO.getIndicatorsByParent(indicator.getId());

    for (Map<String, String> iData : indicatorsDataList) {
      IPIndicator _indicator = new IPIndicator();
      _indicator.setId(Integer.parseInt(iData.get("id")));
      _indicator.setDescription(iData.get("description"));
      _indicator.setTarget(iData.get("target"));

      // Parent indicator
      if (iData.get("parent_id") != null) {
        IPIndicator parent = new IPIndicator();
        parent.setId(Integer.parseInt(iData.get("parent_id")));
        parent.setDescription(iData.get("parent_description"));
        _indicator.setParent(parent);
      }

      indicators.add(_indicator);
    }

    return indicators;
  }

  @Override
  public List<IPIndicator> getIndicatorsByProject(Project project) {
    List<IPIndicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsDataList = indicatorDAO.getIndicatorsByProjectID(project.getId());

    for (Map<String, String> iData : indicatorsDataList) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));

      // Parent indicator
      if (iData.get("parent_id") != null) {
        IPIndicator parent = new IPIndicator();
        parent.setId(Integer.parseInt(iData.get("parent_id")));
        parent.setDescription(iData.get("parent_description"));
        indicator.setParent(parent);
      }

      // Outcome
      if (iData.get("outcome_id") != null) {
        IPElement outcome = new IPElement();
        outcome.setId(Integer.parseInt(iData.get("outcome_id")));
        outcome.setDescription(iData.get("outcome_description"));
        indicator.setOutcome(outcome);
      }

      indicators.add(indicator);
    }

    return indicators;
  }

  @Override
  public List<IPIndicator> getIndicatorsList() {
    List<IPIndicator> indicators = new ArrayList<>();
    List<Map<String, String>> indicatorsDataList = indicatorDAO.getIndicatorsList();

    for (Map<String, String> iData : indicatorsDataList) {
      IPIndicator indicator = new IPIndicator();
      indicator.setId(Integer.parseInt(iData.get("id")));
      indicator.setDescription(iData.get("description"));
      indicator.setTarget(iData.get("target"));

      // Parent indicator
      if (iData.get("parent_id") != null) {
        IPIndicator parent = new IPIndicator();
        parent.setId(Integer.parseInt(iData.get("parent_id")));
        parent.setDescription(iData.get("parent_description"));
        indicator.setParent(parent);
      }

      indicators.add(indicator);
    }
    return indicators;
  }

  @Override
  public List<IPIndicator> getIndicatorsList(String[] indicatorsIDs) {
    List<IPIndicator> allIndicators = this.getIndicatorsList();
    List<IPIndicator> indicators = new ArrayList<>();

    for (IPIndicator indicator : allIndicators) {
      for (String id : indicatorsIDs) {
        if (Integer.parseInt(id) == indicator.getId()) {
          indicators.add(indicator);
        }
      }
    }
    return indicators;
  }

  @Override
  public boolean removeElementIndicators(IPElement element) {
    return indicatorDAO.deleteIpElementIndicators(element.getId());
  }

}
