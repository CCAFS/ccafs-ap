package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.MilestoneDAO;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.Theme;

import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MilestoneManagerImpl implements MilestoneManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(MilestoneManagerImpl.class);
  private MilestoneDAO milestoneDAO;

  @Inject
  public MilestoneManagerImpl(MilestoneDAO milestoneDAO) {
    this.milestoneDAO = milestoneDAO;
  }

  @Override
  public Milestone getMilestone(int milestoneID) {
    Map<String, String> milestoneDB = milestoneDAO.getMilestone(milestoneID);

    if (milestoneDB != null) {

      // Logframe
      Logframe logframe = new Logframe();
      logframe.setName(milestoneDB.get("logframe_name"));

      // Theme
      Theme theme = new Theme();
      theme.setCode(milestoneDB.get("theme_code"));
      theme.setDescription(milestoneDB.get("theme_description"));
      theme.setLogframe(logframe);

      // Objective
      Objective objective = new Objective();
      objective.setCode(milestoneDB.get("objective_code"));
      objective.setDescription(milestoneDB.get("objective_description"));
      objective.setOutcomeDescription(milestoneDB.get("objective_outcome_description"));
      objective.setTheme(theme);

      // Output
      Output output = new Output();
      output.setCode(milestoneDB.get("output_code"));
      output.setDescription(milestoneDB.get("output_description"));
      output.setObjective(objective);

      // Milestone
      Milestone milestone = new Milestone();
      milestone.setId(milestoneID);
      milestone.setCode(milestoneDB.get("code"));
      milestone.setYear(Integer.parseInt(milestoneDB.get("year")));
      milestone.setDescription(milestoneDB.get("description"));
      milestone.setOutput(output);

      return milestone;
    }

    LOG.warn("Milestone identified by {} wasn't found", milestoneID);
    return null;
  }

  @Override
  public Milestone[] getMilestoneList(Logframe logframe) {
    List<Map<String, String>> milestoneDataList = milestoneDAO.getMilestoneList(logframe.getId());
    Milestone[] milestones = new Milestone[milestoneDataList.size()];

    for (int c = 0; c < milestoneDataList.size(); c++) {
      Milestone milestone = new Milestone();
      milestone.setId(Integer.parseInt(milestoneDataList.get(c).get("id")));
      milestone.setCode(milestoneDataList.get(c).get("code"));
      milestone.setYear(Integer.parseInt(milestoneDataList.get(c).get("year")));
      milestone.setDescription(milestoneDataList.get(c).get("Description"));
      milestones[c] = milestone;
    }

    return milestones;
  }
}
