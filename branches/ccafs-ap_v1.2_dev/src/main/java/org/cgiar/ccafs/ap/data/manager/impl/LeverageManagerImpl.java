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
      leverage.setId(Integer.parseInt(leverageData.get("")));
      leverage.setStart_year(Integer.parseInt(leverageData.get("")));
      leverage.setEnd_year(Integer.parseInt(leverageData.get("")));
      leverage.setBudget(Double.parseDouble(leverageData.get("")));
      leverage.setLeader(leader);
      leverage.setPartnerName(leverageData.get(""));
      leverage.setTitle(leverageData.get(""));

      // Theme
      Theme theme = new Theme();
      theme.setCode(leverageData.get(""));
      theme.setDescription(leverageData.get(""));
      theme.setId(Integer.parseInt(leverageData.get("")));

      leverages.add(leverage);
    }
    return leverages;
  }

  @Override
  public boolean saveLeverages(List<Leverage> leverages, Leader leader) {
    List<Map<String, String>> leverageDataList = new ArrayList<>();

    for (Leverage leverage : leverages) {
      Map<String, String> leverageData = new HashMap<String, String>();
      leverageData.put("id", String.valueOf(leverage.getId()));
      leverageData.put("title", leverage.getTitle());
      leverageData.put("budget", String.valueOf(leverage.getBudget()));
      leverageData.put("start_year", String.valueOf(leverage.getStart_year()));
      leverageData.put("end_year", String.valueOf(leverage.getEnd_year()));
      leverageData.put("theme_id", String.valueOf(leverage.getTheme().getId()));
      leverageData.put("partner_name", leverage.getPartnerName());

      leverageDataList.add(leverageData);
    }

    return leverageDAO.saveLeverages(leverageDataList, leader.getId());
  }
}
