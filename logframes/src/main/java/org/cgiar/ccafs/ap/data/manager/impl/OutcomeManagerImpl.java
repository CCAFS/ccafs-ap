package org.cgiar.ccafs.ap.data.manager.impl;

import org.cgiar.ccafs.ap.data.dao.OutcomeDAO;
import org.cgiar.ccafs.ap.data.manager.OutcomeManager;
import org.cgiar.ccafs.ap.data.model.Leader;
import org.cgiar.ccafs.ap.data.model.Logframe;
import org.cgiar.ccafs.ap.data.model.Outcome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class OutcomeManagerImpl implements OutcomeManager {

  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(OutcomeManagerImpl.class);
  private OutcomeDAO outcomeDAO;

  @Inject
  public OutcomeManagerImpl(OutcomeDAO outcomeDAO) {
    this.outcomeDAO = outcomeDAO;
  }

  @Override
  public boolean addOutcomes(List<Outcome> newOutcomes, Leader leader, Logframe logframe) {
    List<Map<String, String>> outcomes = new ArrayList<>();
    Map<String, String> outcomeData;
    for (Outcome outcome : newOutcomes) {
      outcomeData = new HashMap<>();
      if (outcome.getId() != -1) {
        outcomeData.put("id", outcome.getId() + "");
      } else {
        outcomeData.put("id", null);
      }
      outcomeData.put("title", outcome.getTitle());
      outcomeData.put("outcome", outcome.getOutcome());
      outcomeData.put("outputs", outcome.getOutputs());
      outcomeData.put("partners", outcome.getPartners());
      outcomeData.put("output_user", outcome.getOutputUser());
      outcomeData.put("how_used", outcome.getHowUsed());
      outcomeData.put("evidence", outcome.getEvidence());
      outcomeData.put("activities", outcome.getActivities());
      outcomeData.put("non_research_partners", outcome.getNonResearchPartners());
      outcomeData.put("logframe_id", logframe.getId() + "");
      outcomeData.put("activity_leader_id", leader.getId() + "");
      outcomes.add(outcomeData);
    }
    return outcomeDAO.addOutcomes(outcomes);
  }

  @Override
  public List<Outcome> getOutcomes(Leader leader, Logframe logframe) {
    List<Outcome> outcomes = new ArrayList<>();
    List<Map<String, String>> outcomesData = outcomeDAO.getOutcomes(leader.getId(), logframe.getId());
    for (Map<String, String> outcomeData : outcomesData) {
      Outcome outcome = new Outcome();
      outcome.setId(Integer.parseInt(outcomeData.get("id")));
      outcome.setTitle(outcomeData.get("title"));
      outcome.setOutcome(outcomeData.get("outcome"));
      outcome.setOutputs(outcomeData.get("outputs"));
      outcome.setPartners(outcomeData.get("partners"));
      outcome.setOutputUser(outcomeData.get("output_user"));
      outcome.setHowUsed(outcomeData.get("how_used"));
      outcome.setEvidence(outcomeData.get("evidence"));
      outcome.setActivities(outcomeData.get("activities"));
      outcome.setNonResearchPartners(outcomeData.get("non_research_partners"));
      outcome.setLogframe(logframe);
      outcome.setLeader(leader);
      outcomes.add(outcome);
    }
    return outcomes;
  }

  @Override
  public List<Outcome> getOutcomesForSummary(Leader leader, Logframe logframe) {
    List<Outcome> outcomes = new ArrayList<>();
    List<Map<String, String>> outcomesData = outcomeDAO.getOutcomesListForSummary(leader.getId(), logframe.getId());

    Leader tempLeader;
    for (Map<String, String> outcomeData : outcomesData) {
      Outcome outcome = new Outcome();
      outcome.setId(Integer.parseInt(outcomeData.get("id")));
      outcome.setTitle(outcomeData.get("title"));
      outcome.setOutcome(outcomeData.get("outcome"));

      tempLeader = new Leader();
      tempLeader.setId(Integer.parseInt(outcomeData.get("leader_id")));
      tempLeader.setAcronym(outcomeData.get("leader_acronym"));

      outcome.setLeader(tempLeader);
      outcomes.add(outcome);
    }
    return outcomes;
  }

  @Override
  public boolean removeOutcomes(Leader leader, Logframe logframe) {
    return outcomeDAO.removeOutcomes(leader.getId(), logframe.getId());
  }

}
