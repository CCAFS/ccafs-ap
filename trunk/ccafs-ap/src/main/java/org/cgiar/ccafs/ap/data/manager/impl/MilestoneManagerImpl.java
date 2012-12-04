package org.cgiar.ccafs.ap.data.manager.impl;

import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.MilestoneDAO;
import org.cgiar.ccafs.ap.data.manager.MilestoneManager;
import org.cgiar.ccafs.ap.data.model.Milestone;
import org.cgiar.ccafs.ap.data.model.Objective;
import org.cgiar.ccafs.ap.data.model.Output;
import org.cgiar.ccafs.ap.data.model.Theme;

import com.google.inject.Inject;


public class MilestoneManagerImpl implements MilestoneManager {

  private MilestoneDAO milestoneDAO;

  @Inject
  public MilestoneManagerImpl(MilestoneDAO milestoneDAO) {
    this.milestoneDAO = milestoneDAO;
  }

  @Override
  public Milestone getMilestone(int milestoneID) {
    Map<String, String> milestoneDB = milestoneDAO.getMilestone(milestoneID);

    if (milestoneDB != null) {

      Milestone milestone = new Milestone();
      milestone.setId(milestoneID);
      milestone.setCode(milestoneDB.get("code"));
      milestone.setYear(Integer.parseInt(milestoneDB.get("year")));
      milestone.setDescription(milestoneDB.get("description"));

      // Theme
      Theme theme = new Theme();
      theme.setCode(milestoneDB.get("theme_code"));
      theme.setDescription(milestoneDB.get("theme_description"));

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

      milestone.setOutput(output);

      return milestone;
    }
    return null;
  }

}
