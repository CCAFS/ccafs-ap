package org.cgiar.ccafs.ap.converter;

import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.model.Milestone;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.struts2.util.StrutsTypeConverter;


public class MilestoneConverter extends StrutsTypeConverter {

  private MilestoneManager milestoneManager;

  @Inject
  public MilestoneConverter(MilestoneManager milestoneManager) {
    this.milestoneManager = milestoneManager;
  }

  @Override
  public Object convertFromString(Map context, String[] values, Class toClass) {
    if (toClass == Milestone.class) {
      return milestoneManager.getMilestone(Integer.parseInt(values[0]));
    }
    return null;
  }

  @Override
  public String convertToString(Map context, Object o) {
    Milestone milestone = (Milestone) o;
    return milestone.getId() + "";
  }


}
