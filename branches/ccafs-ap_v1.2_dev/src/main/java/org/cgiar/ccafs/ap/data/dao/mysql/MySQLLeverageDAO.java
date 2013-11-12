package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.LeverageDAO;

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


public class MySQLLeverageDAO implements LeverageDAO {

  private static Logger LOG = LoggerFactory.getLogger(MySQLLeverageDAO.class);
  private DAOManager databaseManager;

  @Inject
  public MySQLLeverageDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getLeverages(int leader_id, int logframe_id) {
    LOG.debug(">> getLeverages(leader_id={}, logframe_id={})", leader_id, logframe_id);

    List<Map<String, String>> leverageDataList = new ArrayList<>();
    StringBuilder query = new StringBuilder();
    query.append("SELECT l.`id`, l.`title`, l.`budget`, l.`start_year`, l.`end_year`, l.`partner_name`, ");
    query.append("t.`id` as 'theme_id', t.`code` as 'theme_code' ");
    query.append("FROM leverages l ");
    query.append("INNER JOIN themes t ON l.theme_id = t.id ");
    query.append("INNER JOIN logframes lo ON t.logframe_id = lo.id ");
    query.append("WHERE l.activity_leader_id = ");
    query.append(leader_id);
    query.append(" AND lo.id = ");
    query.append(logframe_id);
    query.append(" ORDER BY l.id ");

    try (Connection con = databaseManager.getConnection()) {
      ResultSet rs = databaseManager.makeQuery(query.toString(), con);
      while (rs.next()) {
        Map<String, String> leverageData = new HashMap<String, String>();
        leverageData.put("id", rs.getString("id"));
        leverageData.put("title", rs.getString("title"));
        leverageData.put("budget", rs.getString("budget"));
        leverageData.put("start_year", rs.getString("start_year"));
        leverageData.put("end_year", rs.getString("end_year"));
        leverageData.put("partner_name", rs.getString("partner_name"));
        leverageData.put("theme_id", rs.getString("theme_id"));
        leverageData.put("theme_code", rs.getString("theme_code"));

        leverageDataList.add(leverageData);
      }
      rs.close();
    } catch (SQLException e) {
      LOG.error("There was an exception trying to get the leverages for leader {} and the logframe {}", leader_id,
        logframe_id);
    }

    LOG.debug("<< getLeverages():leveragesDataList.size={}", leverageDataList.size());
    return leverageDataList;
  }

  @Override
  public boolean saveLeverages(List<Map<String, String>> leverages, int activity_leader_id) {
    boolean saved = true;

    StringBuilder query = new StringBuilder();
    query.append("INSERT INTO `Leverages` (`id`, `title`, `budget`, `start_year`, `end_year`, ");
    query.append("`theme_id`, `activity_leader_id`, `partner_name`) VALUES (?, ?, ?, ?, ?, ?, ?, ?) ");
    query.append("ON DUPLICATE KEY UPDATE id = VALUES(id), title = VALUES(title), budget = VALUES(budget), ");
    query.append("start_year = VALUES(start_year), end_year = VALUES(end_year), theme_id = VALUES(theme_id), ");
    query.append("activity_leader_id = VALUES(activity_leader_id), partner_name = VALUES(partner_name); ");

    Object[] values;
    for (Map<String, String> leverage : leverages) {
      values = new Object[8];
      values[0] = leverage.get("id");
      values[1] = leverage.get("title");
      values[2] = leverage.get("budget");
      values[3] = leverage.get("start_year");
      values[4] = leverage.get("end_year");
      values[5] = leverage.get("theme_id");
      values[6] = activity_leader_id;
      values[7] = leverage.get("partner_name");

      try (Connection con = databaseManager.getConnection()) {
        int rows = databaseManager.makeChangeSecure(con, query.toString(), values);

        if (rows < 0) {
          saved = false;
          LOG.error("There was an error saving the leverages of leader {} for logframe {}.", activity_leader_id);
        }
      } catch (SQLException e) {
        LOG.error("There was an exception saving the leverages of leader {}.", activity_leader_id, e);
      }
    }

    return saved;
  }
}
