package org.cgiar.ccafs.ap.data.dao.mysql;

import org.cgiar.ccafs.ap.data.dao.ActivityPartnerDAO;
import org.cgiar.ccafs.ap.data.dao.DAOManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.inject.Inject;


public class MySQLActivityPartnerDAO implements ActivityPartnerDAO {

  private DAOManager databaseManager;

  @Inject
  public MySQLActivityPartnerDAO(DAOManager databaseManager) {
    this.databaseManager = databaseManager;
  }

  @Override
  public List<Map<String, String>> getActivityPartnersList(int activityID) {
    List<Map<String, String>> activityPartnerList = new ArrayList<>();
    try (Connection con = databaseManager.getConnection()) {
      String query =
        "SELECT ap.id, ap.contact_name, ap.contact_email, p.acronym, p.id as 'partner_id', p.acronym as 'partner_acronym', p.name as 'partner_name' "
          + "FROM activity_partners ap "
          + "INNER JOIN partners p ON p.id = ap.partner_id "
          + "WHERE ap.activity_id = "
          + activityID;
      ResultSet rs = databaseManager.makeQuery(query, con);
      while (rs.next()) {
        Map<String, String> activityPartnerData = new HashMap<>();
        activityPartnerData.put("id", rs.getString("id"));
        activityPartnerData.put("contact_name", rs.getString("contact_name"));
        activityPartnerData.put("contact_email", rs.getString("contact_email"));
        activityPartnerData.put("partner_id", rs.getString("partner_id"));
        activityPartnerData.put("partner_acronym", rs.getString("partner_acronym"));
        activityPartnerData.put("partner_name", rs.getString("partner_name"));
        activityPartnerList.add(activityPartnerData);
      }
      rs.close();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
    return activityPartnerList;
  }

  @Override
  public int getPartnersCount(int activityID) {
    int partnersCount = 0;
    try (Connection connection = databaseManager.getConnection()) {
      String query = "SELECT COUNT(id) FROM activity_partners WHERE activity_id = " + activityID;
      ResultSet rs = databaseManager.makeQuery(query, connection);
      if (rs.next()) {
        partnersCount = rs.getInt(1);
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return partnersCount;
  }

  @Override
  public boolean removeActivityPartners(int activityID) {
    boolean problem = false;
    try (Connection connection = databaseManager.getConnection()) {
      String removeQuery = "DELETE FROM activity_partners WHERE activity_id = " + activityID;
      int rows = databaseManager.makeChange(removeQuery, connection);
      if (rows < 0) {
        problem = true;
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return !problem;
  }

  @Override
  public boolean saveActivityPartnerList(List<Map<String, Object>> activityPartnersData) {
    boolean problem = false;
    try (Connection connection = databaseManager.getConnection()) {
      for (Map<String, Object> cpData : activityPartnersData) {
        String preparedQuery =
          "INSERT INTO activity_partners (id, partner_id, activity_id, contact_name, contact_email) VALUES (?, ?, ?, ?, ?)";
        Object[] data = new Object[5];
        data[0] = cpData.get("id");
        data[1] = cpData.get("partner_id");
        data[2] = cpData.get("activity_id");
        data[3] = cpData.get("contact_name");
        data[4] = cpData.get("contact_email");
        int rows = databaseManager.makeChangeSecure(connection, preparedQuery, data);
        if (rows < 0) {
          problem = true;
          // TODO - Add log about the problem?
        }
      }
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return !problem;
  }

}
