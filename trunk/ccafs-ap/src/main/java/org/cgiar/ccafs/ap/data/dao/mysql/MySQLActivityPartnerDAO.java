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
        Map<String, String> activityPartnerData = new HashMap();
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

    if (activityPartnerList.size() == 0) {
      return null;
    }
    return activityPartnerList;
  }

}
