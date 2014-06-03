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
          "INSERT INTO outcomes (id, title, outcome, outputs, partners, output_user, how_used, evidence, logframe_id, activity_leader_id) VALUES (?,?,?,?,?,?,?,?,?,?)";
        values = new Object[10];
        values[0] = outcomeData.get("id"); // identifier
        values[1] = outcomeData.get("title"); // title
        values[2] = outcomeData.get("outcome"); // outcome
        values[3] = outcomeData.get("outputs"); // outputs
        values[4] = outcomeData.get("partners"); // partners
        values[5] = outcomeData.get("output_user"); // output_user
        values[6] = outcomeData.get("how_used"); // how_used
        values[7] = outcomeData.get("evidence"); // evidence
        values[8] = outcomeData.get("logframe_id"); // logframe_id
        values[9] = outcomeData.get("activity_leader_id"); // activity_leader_id
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
        outcomeData.put("title", rs.getString("title"));
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
  public List<Map<String, String>> getOutcomesListForSummary(int leader_id, int logframe_id) {
    LOG.debug(">> getOutcomesListForSummary(leader_id={}, logframe_id={})", leader_id, logframe_id);
    List<Map<String, String>> outcomes = new ArrayList<>();

    try (Connection connection = dbManager.getConnection()) {
      StringBuilder query = new StringBuilder();
      query.append("SELECT o.id, o.title, o.outcome, al.id as 'leader_id', al.acronym as 'leader_acronym' ");
      query.append("FROM outcomes o ");
      query.append("INNER JOIN activity_leaders al ON o.activity_leader_id = al.id ");
      query.append("INNER JOIN logframes l ON o.logframe_id = l.id ");

      if (leader_id != -1) {
        query.append("AND activity_leader_id = " + leader_id + " ");
      }

      if (logframe_id != -1) {
        query.append(" AND logframe_id = " + logframe_id + " ");
      }
      query.append("ORDER BY al.acronym");


      ResultSet rs = dbManager.makeQuery(query.toString(), connection);
      Map<String, String> outcomeData;
      while (rs.next()) {
        outcomeData = new HashMap<>();
        outcomeData.put("id", rs.getString("id"));
        outcomeData.put("title", rs.getString("title"));
        outcomeData.put("outcome", rs.getString("outcome"));
        outcomeData.put("leader_id", rs.getString("leader_id"));
        outcomeData.put("leader_acronym", rs.getString("leader_acronym"));
        outcomes.add(outcomeData);
      }
      rs.close();
    } catch (SQLException e) {
      Object[] errorParams = {leader_id, logframe_id, e};
      LOG.error(
        "-- getOutcomesListForSummary() > There was an error getting the outcomes list for leader {} and logframe {}",
        errorParams);
    }

    LOG.debug("<< getOutcomesListForSummary():outcomes.size={}", outcomes.size());
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
