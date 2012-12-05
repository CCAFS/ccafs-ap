package org.cgiar.ccafs.ap.data.dao.mysql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.MilestoneDAO;

import com.google.inject.Inject;


public class MySQLMilestoneDAO implements MilestoneDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLMilestoneDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getMilestone(int milestoneID) {
    Map<String, String> milestone = new HashMap<>();
    try (Connection con = databaseManager.getConnection()) {
      // Querying milestone record.
      String query =
        "SELECT m.id, m.code, m.year, m.description, op.code as 'output_code', "
          + "op.description as 'output_description', obj.code as 'objective_code', "
          + "obj.description as 'objective_description', obj.outcome_description as 'objective_outcome_description', "
          + "th.code as 'theme_code', th.description as 'theme_description', lf.name as 'logframe_name' "
          + "FROM milestones m, outputs op, objectives obj, themes th, logframes lf "
          + "WHERE m.output_id = op.id AND op.objective_id = obj.id AND obj.theme_id = th.id "
          + "AND th.logframe_id = lf.id " + "AND m.id=" + milestoneID;
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        milestone.put("id", rs.getString("id"));
        milestone.put("code", rs.getString("code"));
        milestone.put("description", rs.getString("description"));
        milestone.put("year", rs.getString("year"));
        milestone.put("output_code", rs.getString("output_code"));
        milestone.put("output_description", rs.getString("output_description"));
        milestone.put("objective_code", rs.getString("objective_code"));
        milestone.put("objective_description", rs.getString("objective_description"));
        milestone.put("outcome_description", rs.getString("objective_outcome_description"));
        milestone.put("theme_code", rs.getString("theme_code"));
        milestone.put("theme_description", rs.getString("theme_description"));
        milestone.put("logframe_name", rs.getString("logframe_name"));
      }
      rs.close();

    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (milestone.isEmpty()) {
      return null;
    } else {
      return milestone;
    }
  }
}
