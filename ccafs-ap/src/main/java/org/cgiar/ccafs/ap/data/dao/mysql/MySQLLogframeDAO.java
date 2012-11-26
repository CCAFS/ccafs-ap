package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.DAOManager;
import org.cgiar.ccafs.ap.data.dao.LogframeDAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MySQLLogframeDAO implements LogframeDAO {

  private static final Logger LOG = LoggerFactory.getLogger(MySQLLogframeDAO.class);

  private DAOManager databaseManager;

  @Inject
  public MySQLLogframeDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public Map<String, String> getLogframe(int year) {
    Map<String, String> logframe = new HashMap<>();

    try (Connection con = databaseManager.getConnection()) {
      String query = "SELECT * FROM logframes WHERE year = " + year;
      ResultSet rs = databaseManager.makeQuery(query, con);
      if (rs.next()) {
        logframe.put("id", rs.getString("id"));
        logframe.put("year", rs.getString("year"));
        logframe.put("name", rs.getString("name"));
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    return logframe;
  }

}
