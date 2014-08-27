package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.OutcomeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLOutcomeDAO implements OutcomeDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLOutcomeDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLOutcomeDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public boolean addOutcomes(List<Map<String, String>> newOutcomes) {
    LOG.debug(">> addOutcomes(newOutcomes={})", newOutcomes);
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String preparedQuery;
      Object[] values;
      for (Map<String, String> outcomeData : newOutcomes) {
        preparedQuery =
          "INSERT INTO outcomes (id, outcome, outputs, partners, output_user, how_used, evidence, logframe_id, activity_leader_id) VALUES (?,?,?,?,?,?,?,?,?)";
        values = new Object[9];
        values[0] = outcomeData.get("id"); // identifier
        values[1] = outcomeData.get("outcome"); // outcome
        values[2] = outcomeData.get("outputs"); // outputs
        values[3] = outcomeData.get("partners"); // partners
        values[4] = outcomeData.get("output_user"); // output_user
        values[5] = outcomeData.get("how_used"); // how_used
        values[6] = outcomeData.get("evidence"); // evidence
        values[7] = outcomeData.get("logframe_id"); // logframe_id
        values[8] = outcomeData.get("activity_leader_id"); // activity_leader_id
        int rows = dbManager.makeChangeSecure(connection, preparedQuery, values);
        if (rows < 1) {
          LOG.warn("There was an error saving the data into 'outcomes' table");
          problem = true;
        }
      }
    } catch (SQLException e) {
      LOG.error("-- addOutcomes() > There was an error saving new 'outcomes'.", e);
    }

    LOG.debug("<< addOutcomes():{}", !problem);
    return !problem;
  }

  @Override
  public List<Map<String, String>> getOutcomes(int leader_id, int logframe_id) {
    LOG.debug(">> getOutcomes(leader_id={}, logframe_id={})", leader_id, logframe_id);
    List<Map<String, String>> outcomes = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT * FROM outcomes WHERE activity_leader_id = " + leader_id + " AND logframe_id = " + logframe_id;
      ResultSet rs = dbManager.makeQuery(query, connection);
      Map<String, String> outcomeData;
      while (rs.next()) {
        outcomeData = new HashMap<>();
        outcomeData.put("id", rs.getString("id"));
        outcomeData.put("outcome", rs.getString("outcome"));
        outcomeData.put("outputs", rs.getString("outputs"));
        outcomeData.put("partners", rs.getString("partners"));
        outcomeData.put("output_user", rs.getString("output_user"));
        outcomeData.put("how_used", rs.getString("how_used"));
        outcomeData.put("evidence", rs.getString("evidence"));
        outcomes.add(outcomeData);
      }
      rs.close();
    } catch (SQLException e) {
      Object[] errorParams = {leader_id, logframe_id, e};
      LOG.error("-- getOutcomes() > There was an error getting the outcomes list for leader {} and logframe {}",
        errorParams);
    }

    LOG.debug("<< getOutcomes():outcomes.size={}", outcomes.size());
    return outcomes;
  }

  @Override
  public boolean removeOutcomes(int leader_id, int logframe_id) {
    LOG.debug(">> removeOutcomes(leader_id={}, logframe_id={})");
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String removeQuery =
        "DELETE FROM outcomes WHERE activity_leader_id = " + leader_id + " AND logframe_id = " + logframe_id;
      int rows = dbManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        problem = true;
      }
    } catch (SQLException e) {
      Object[] errorParams = {leader_id, logframe_id, e};
      LOG.error("-- removeOutcomes() > There was an error deleting the outcomes list for leader {} and logframe {}",
        errorParams);
    }
    LOG.debug("<< removeOutcomes():{}", !problem);
    return !problem;
  }

}