package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.manager.impl.TLOutputSummaryDAO;

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


public class MySQLTLOutputSummaryDAO implements TLOutputSummaryDAO {

  // Loggin
  private static final Logger LOG = LoggerFactory.getLogger(MySQLTLOutputSummaryDAO.class);
  private DAOManager dbManager;

  @Inject
  public MySQLTLOutputSummaryDAO(DAOManager dbManager) {
    this.dbManager = dbManager;
  }

  @Override
  public List<Map<String, Object>> getTLOutputSummaries(int leader_id, int logframe_id) {
    List<Map<String, Object>> tlSummariesData = new ArrayList<>();
    try (Connection connection = dbManager.getConnection()) {
      String query =
        "SELECT os.id, os.description, m.code as 'milestone_code', o.id as 'output_id', "
          + "o.description as 'output_description' "
          + "FROM tl_output_summaries os RIGHT JOIN outputs o on os.output_id = o.id "
          + "INNER JOIN milestones m on o.id = m.output_id " + "INNER JOIN activities a on m.id = a.milestone_id "
          + "INNER JOIN activity_leaders al on a.activity_leader_id = al.id "
          + "INNER JOIN objectives obj on o.objective_id = obj.id " + "INNER JOIN themes th on obj.theme_id = th.id "
          + "INNER JOIN logframes l on th.logframe_id = l.id " + "WHERE al.id = " + leader_id + " and l.id = "
          + logframe_id + " GROUP BY o.id ";
      ResultSet rs = dbManager.makeQuery(query, connection);
      while (rs.next()) {
        Map<String, Object> summaryData = new HashMap<String, Object>();
        summaryData.put("id", rs.getString("id"));
        summaryData.put("description", rs.getString("description"));
        summaryData.put("milestone_code", rs.getString("milestone_code"));
        summaryData.put("output_id", rs.getString("output_id"));
        summaryData.put("output_description", rs.getString("output_description"));
        tlSummariesData.add(summaryData);
      }
    } catch (SQLException e) {
      LOG.error("There was an error getting the data from activity_status table.", e);
    }
    return tlSummariesData;
  }

  @Override
  public boolean saveTLOutputSummaries(List<Map<String, Object>> outputs) {
    boolean problem = false;
    try (Connection connection = dbManager.getConnection()) {
      String query;
      Object[] values;
      int rows;
      for (Map<String, Object> outputData : outputs) {
        // If there is not an id defined, just add as a new record. Otherwise, update it.
        if (((int) outputData.get("id")) == -1) {
          query = "INSERT INTO tl_output_summaries (output_id, activity_leader_id, description) VALUES (?,?,?)";
          values = new Object[3];
          values[0] = outputData.get("output_id");
          values[1] = outputData.get("activity_leader_id");
          values[2] = outputData.get("description");
          rows = dbManager.makeChangeSecure(connection, query, values);
          if (rows <= 0) {
            problem = true;
          }
        } else {
          query = "UPDATE tl_output_summaries SET description = ? WHERE id = " + outputData.get("id");
          values = new Object[1];
          values[0] = outputData.get("description");
          rows = dbManager.makeChangeSecure(connection, query, values);
          if (rows < 0) {
            problem = true;
          }
        }
      }
    } catch (SQLException e) {
      LOG.error("There was an error trying to save a list of outputs in the tl_output_summaries table.", e);
    }
    return !problem;
  }
}
