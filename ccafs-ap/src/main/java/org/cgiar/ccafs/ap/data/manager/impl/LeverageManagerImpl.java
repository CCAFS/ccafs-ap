/*
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
 */

package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.LeverageDAO;
import org.cgiar.ccafs.ap.data.manager.LeverageManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Leverage;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class LeverageManagerImpl implements LeverageManager {

  private LeverageDAO leverageDAO;

  @Inject
  public LeverageManagerImpl(LeverageDAO leverageDAO) {
    this.leverageDAO = leverageDAO;
  }

  @Override
  public List<Leverage> getLeverages(Leader leader, Logframe logframe) {
    List<Leverage> leverages = new ArrayList<>();
    List<Map<String, String>> leveragesDataList = leverageDAO.getLeverages(leader.getId(), logframe.getId());

    for (Map<String, String> leverageData : leveragesDataList) {
      Leverage leverage = new Leverage();
      leverage.setId(Integer.parseInt(leverageData.get("id")));
      leverage.setStartYear(Integer.parseInt(leverageData.get("start_year")));
      leverage.setEndYear(Integer.parseInt(leverageData.get("end_year")));
      leverage.setBudget(Double.parseDouble(leverageData.get("budget")));
      leverage.setLeader(leader);
      leverage.setPartnerName(leverageData.get("partner_name"));
      leverage.setTitle(leverageData.get("title"));

      // Theme
      Theme theme = new Theme();
      theme.setCode(leverageData.get("theme_code"));
      theme.setId(Integer.parseInt(leverageData.get("theme_id")));
      leverage.setTheme(theme);

      leverages.add(leverage);
    }
    return leverages;
  }

  @Override
  public boolean removeLeverages(Leader leader, Logframe logframe) {
    return leverageDAO.removeLeverages(leader.getId(), logframe.getId());
  }

  @Override
  public boolean saveLeverages(List<Leverage> leverages, Leader leader) {
    List<Map<String, String>> leverageDataList = new ArrayList<>();

    for (Leverage leverage : leverages) {
      Map<String, String> leverageData = new HashMap<String, String>();

      if (leverage.getId() != -1) {
        leverageData.put("id", String.valueOf(leverage.getId()));
      } else {
        leverageData.put("id", null);
      }

      String title = (leverage.getTitle() == null) ? "" : leverage.getTitle();
      leverageData.put("title", title);
      leverageData.put("budget", String.valueOf(leverage.getBudget()));
      leverageData.put("start_year", String.valueOf(leverage.getStartYear()));
      leverageData.put("end_year", String.valueOf(leverage.getEndYear()));
      leverageData.put("theme_id", String.valueOf(leverage.getTheme().getId()));
      leverageData.put("partner_name", leverage.getPartnerName());

      leverageDataList.add(leverageData);
    }

    return leverageDAO.saveLeverages(leverageDataList, leader.getId());
  }
}
