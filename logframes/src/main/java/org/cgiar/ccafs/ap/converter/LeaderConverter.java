package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.LeaderManager;
import org.cgiar.ccafs.ap.data.model.Leader;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class LeaderConverter extends StrutsTypeConverter {

  private LeaderManager leaderManager;

  @Inject
  public LeaderConverter(LeaderManager leaderManager) {
    this.leaderManager = leaderManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Leader.class) {
      Leader[] leaders = leaderManager.getAllLeaders();
      for (Leader leader : leaders) {
        if (leader.getId() == Integer.parseInt(values[0])) {
          return leader;
        }
      }
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    if (o instanceof Leader) {
      Leader leader = (Leader) o;
      return leader.getId() + " - " + leader.getAcronym();
    }
    return null;
  }

}
