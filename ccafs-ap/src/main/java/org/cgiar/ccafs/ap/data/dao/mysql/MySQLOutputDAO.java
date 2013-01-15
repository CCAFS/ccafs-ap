package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.OutputDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLOutputDAO implements OutputDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLOutputDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getOutputsList(int activityLeaderId) {
    List<Map<String, String>> outputDataList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT DISTINCT op.id, op.code, op.description, th.id theme_id, th.code theme_code, "
          + "ob.id objective_id, ob.code objective_code "
          + "FROM outputs op INNER JOIN objectives ob ON op.objective_id=ob.id "
          + "INNER JOIN themes th ON ob.theme_id=th.id INNER JOIN milestones ms ON op.id=ms.output_id "
          + "INNER JOIN activities ac ON ms.id=ac.milestone_id LEFT JOIN output_summaries os ON op.id=os.output_id "
          + "WHERE ac.activity_leader_id=" + activityLeaderId + " ORDER BY th.code, ob.code, op.code;";

      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> outputData = new HashMap<String, String>();
        outputData.put("id", rs.getString("id"));
        outputData.put("code", rs.getString("code"));
        outputData.put("description", rs.getString("description"));
        outputData.put("theme_id", rs.getString("theme_id"));
        outputData.put("theme_code", rs.getString("theme_code"));
        outputData.put("objective_id", rs.getString("objective_id"));
        outputData.put("objective_code", rs.getString("objective_code"));
        outputDataList.add(outputData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto generated catch block
      e.printStackTrace();
    }

    if (outputDataList.isEmpty()) {
      return null;
    }

    return outputDataList;
  }
}
